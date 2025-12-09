package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmrOrderLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Apr 21, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: EmrOrderLogDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class EmrOrderLogDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrOrderLog`";
    /**
     * All fields except idemrReports
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public EmrOrderLogDAO()
    {
        fields.add("idEmrXref");
        fields.add("emrIdOrders");
        fields.add("lisIdOrders");
        fields.add("status");
        fields.add("created");
        fields.add("error");
        fields.add("description");
    }
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        EmrOrderLog eol = (EmrOrderLog)obj;
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromEmrOrderLog(eol, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        EmrOrderLog eol = (EmrOrderLog)obj;
        try
        {
            
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idemrOrderLog` = " + eol.getIdemrOrderLog();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromEmrOrderLog(eol, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            EmrOrderLog eol = new EmrOrderLog();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idemrReports` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                
                SetEmrOrderLogFromResultSet(eol, rs);
            }
            
            rs.close();
            stmt.close();
            return eol;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    private EmrOrderLog SetEmrOrderLogFromResultSet(EmrOrderLog eol, ResultSet rs) throws SQLException
    {
        eol.setIdemrOrderLog(rs.getInt("idemrOrderLog"));
        eol.setEmrIdOrders(rs.getInt("emrIdOrders"));
        eol.setLisIdOrders(rs.getInt("lisidOrders"));
        eol.setStatus(rs.getString("status"));
        eol.setCreated(rs.getTimestamp("created"));
        eol.setError(rs.getString("error"));
        eol.setDescription(rs.getString("description"));
        return eol;
    }
    
    private PreparedStatement SetStatementFromEmrOrderLog(EmrOrderLog eol, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, eol.getIdemrOrderLog());
        pStmt.setInt(2, eol.getIdEmrXref());
        pStmt.setInt(3, eol.getEmrIdOrders());
        SQLUtil.SafeSetInteger(pStmt, 4, eol.getLisIdOrders());
        SQLUtil.SafeSetString(pStmt, 5, eol.getStatus());
        pStmt.setTimestamp(6, Convert.ToSQLDateTime(eol.getCreated()));
        SQLUtil.SafeSetString(pStmt, 7, eol.getError());
        SQLUtil.SafeSetString(pStmt, 8, eol.getDescription());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
