package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.GeneticReport;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @date: Aug 14, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: GeneticReportDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class GeneticReportDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`GeneticReport`";

    ArrayList<Integer> generatedIDs = new ArrayList<>();
    private final ArrayList<String> fields = new ArrayList<>();

    public GeneticReportDAO()
    {
        fields.add("idorders");
        fields.add("idpatients");
        fields.add("reportName");
        fields.add("report");
        fields.add("created");
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
            GeneticReport gr = (GeneticReport) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(gr, pStmt);

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
            GeneticReport gr = (GeneticReport) obj;
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(gr, pStmt);
            SQLUtil.SafeSetInteger(pStmt, 5, gr.getId());
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
    
    public void DeleteByOrderId(Integer orderId)
            throws SQLException, IllegalArgumentException
    {
        if (orderId == null || orderId.equals(0))
        {
            throw new IllegalArgumentException("GeneticReportDAO::Delete: Received a NULL/zero order id");
        }
        
        String sql = "DELETE FROM " + table + " WHERE idorders = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        pStmt.execute();
        pStmt.close();
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
            GeneticReport gr = new GeneticReport();

            String query = "SELECT * FROM " + table
                    + "WHERE `id` = ?";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, ID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(gr, rs);
            }

            rs.close();
            stmt.close();

            return gr;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public GeneticReport GetMostRecentReportByOrderIDReportName(Integer orderID, String reportName) {
        try {
            if (this.con.isClosed())
            {
                this.con = CheckDBConnection.Check(this.dbs, this.con);
            }
        }
        catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        } 

        try {
            GeneticReport gr = new GeneticReport();

            String query = "SELECT * FROM `GeneticReport`WHERE `idorders` = ? AND `reportName` = ? ORDER BY `created` DESC LIMIT 1";

            PreparedStatement stmt = this.con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, orderID);
            SQLUtil.SafeSetString(stmt, 2, reportName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(gr, rs);
            }

            rs.close();
            stmt.close();

            return gr;
        }
        catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        } 
    }

    
    public GeneticReport GetMostRecentReportByOrderID(Integer orderID)
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
            GeneticReport gr = new GeneticReport();

            String query = "SELECT * FROM " + table
                    + "WHERE `idorders` = ? "
                    + "ORDER BY `created` DESC "
                    + "LIMIT 1";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, orderID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(gr, rs);
            }

            rs.close();
            stmt.close();

            return gr;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public GeneticReport GetMostRecentReportByPatientID(Integer patientID)
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
            GeneticReport gr = new GeneticReport();

            String query = "SELECT * FROM " + table
                    + "WHERE `idpatients` = ? "
                    + "ORDER BY `created` DESC "
                    + "LIMIT 1";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, patientID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                FromResultSet(gr, rs);
            }

            rs.close();
            stmt.close();

            return gr;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public ArrayList<GeneticReport> GetAllByOrderID(Integer orderID)
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
            ArrayList<GeneticReport> grList = new ArrayList<>();

            String query = "SELECT * FROM " + table
                    + "WHERE `idorders` = ? ";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, orderID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                GeneticReport gr = new GeneticReport();
                FromResultSet(gr, rs);
                grList.add(gr);
            }

            rs.close();
            stmt.close();

            return grList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public ArrayList<GeneticReport> GetAllByPatientID(Integer patientID)
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
            ArrayList<GeneticReport> grList = new ArrayList<>();

            String query = "SELECT * FROM " + table
                    + "WHERE `idpatients` = ? ";

            PreparedStatement stmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(stmt, 1, patientID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                GeneticReport gr = new GeneticReport();
                FromResultSet(gr, rs);
                grList.add(gr);
            }

            rs.close();
            stmt.close();

            return grList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
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

    private GeneticReport FromResultSet(GeneticReport obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdOrders(rs.getInt("idorders"));
        obj.setIdPatients(rs.getInt("idpatients"));
        obj.setReportName(rs.getString("reportName"));
        obj.setReport(rs.getBytes("report"));
        obj.setCreated(rs.getDate("created"));
        return obj;
    }

    private PreparedStatement SetStatement(GeneticReport obj, PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdOrders());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdPatients());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getReportName());
        SQLUtil.SafeSetBytes(pStmt, ++i, obj.getReport());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDate(obj.getCreated()));
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
