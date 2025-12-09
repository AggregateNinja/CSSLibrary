/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReferenceLabReport;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author TomR
 */
public class ReferenceLabReportDAO implements Serializable, DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`ReferenceLabReport`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    private boolean useReportName;
    
    public ReferenceLabReportDAO()
    {
        useReportName = new PreferencesDAO().getBoolean("UseRefLabReportNames", false);
        // Don't add the unique identifier.
        fields.add("orderId");
        if(useReportName) {
            fields.add("reportName");
        }
        fields.add("report");
        fields.add("md5");
        fields.add("created");
        fields.add("userId");
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    private String GenerateMD5Hash(byte[] byteArray)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            String hex = (new HexBinaryAdapter()).marshal(md.digest(byteArray));
            return hex;
            
        } catch (NoSuchAlgorithmException ex)
        {
            System.out.println("Unable to find appropriate method for " +
                    "MD5 hash of Reference Lab Report.");
            return null;
        }

    }
    
    /**
     * Checks to make sure that the hash of this report is unique for the
     *  given order ID
     * @param report Reference Lab Report file
     * @return Boolean; null on error.
     */
    public Boolean ReferenceLabReportExists(ReferenceLabReport report)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            report.setMd5(GenerateMD5Hash(report.getReport()));
            
            
            String query = "SELECT COUNT(*) " +
                    "FROM ReferenceLabReport " +
                    "WHERE md5 = '" + report.getMd5() + "'";
            
            PreparedStatement stmt = con.prepareStatement(query);
            int count = 0;
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
            }            

            stmt.close();

            return (count > 0);
        }
        catch (SQLException sqlException)
        {
            String message = sqlException.toString();
            System.out.println(message);
            return null;
        }
        
    }

    private ReferenceLabReport FromResultSet(ReferenceLabReport obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdOrders(rs.getInt("orderId"));
        if(useReportName) {
            obj.setReportName(rs.getString("reportName"));
        }
        else {
            obj.setReportName(rs.getString("md5"));
        }
        obj.setReport(rs.getBytes("report"));
        obj.setMd5(rs.getString("md5"));
        obj.setCreated(rs.getDate("created"));
        obj.setUserId(rs.getInt("userId"));
        return obj;
    }

    private PreparedStatement SetStatement(ReferenceLabReport obj, PreparedStatement pStmt) throws SQLException
    {
        int i = 1;
        SQLUtil.SafeSetInteger(pStmt, i++, obj.getIdOrders());
        if(useReportName) {
            SQLUtil.SafeSetString(pStmt, i++, obj.getReportName());
        }
        SQLUtil.SafeSetBytes(pStmt, i++, obj.getReport());
        SQLUtil.SafeSetString(pStmt, i++, obj.getMd5());
        SQLUtil.SafeSetDate(pStmt, i++, Convert.ToSQLDate(obj.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, i++, obj.getUserId());

        return pStmt;
    }    
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            ReferenceLabReport rlr = (ReferenceLabReport) obj;
            
            // Generate the MD5 hash before inserting.
            rlr.setMd5(GenerateMD5Hash(rlr.getReport()));            
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rlr, pStmt);
            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (SQLException sqlException)
        {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            ReferenceLabReport rlr = (ReferenceLabReport) obj;
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(rlr, pStmt);
            SQLUtil.SafeSetInteger(pStmt, fields.size() + 1, rlr.getId());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException sqlException)
        {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ReferenceLabReport rlr = new ReferenceLabReport();

            String query = "SELECT * FROM " + table
                    + "WHERE `id` = ?";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, ID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(rlr, rs);
            }

            rs.close();
            stmt.close();

            return rlr;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public ArrayList<ReferenceLabReport> GetReportsByOrderID(Integer orderID)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            
            String query = "SELECT * FROM " + table
                    + "WHERE `orderId` = ? " +
                    "ORDER BY Created DESC";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, orderID);

            ResultSet rs = stmt.executeQuery();

             ArrayList<ReferenceLabReport> results = new 
                    ArrayList<>();
            while (rs.next())
            {
               ReferenceLabReport rlr = new ReferenceLabReport();
               results.add(FromResultSet(rlr, rs));
            }

            rs.close();
            stmt.close();

            return results;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
    public ReferenceLabReport GetMostRecentReportByOrderID(Integer orderID)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ReferenceLabReport rlr = new ReferenceLabReport();

            String query = "SELECT * FROM " + table
                    + "WHERE `orderId` = ? "
                    + "ORDER BY `created` DESC "
                    + "LIMIT 1";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, orderID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(rlr, rs);
            }

            rs.close();
            stmt.close();

            return rlr;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
}
