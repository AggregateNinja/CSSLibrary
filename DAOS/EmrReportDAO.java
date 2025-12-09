package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmrReports;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
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
 * @date:   Apr 9, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: EmrReportDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class EmrReportDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrReports`";
    /**
     * All fields except idemrReports
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public EmrReportDAO()
    {
        fields.add("idOrders");
        fields.add("releaseJobId");
        fields.add("created");
        fields.add("report");
    }
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        EmrReports emr = (EmrReports) obj;
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
            SetStatementFromEmrReport(emr, pStmt);
            
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
        EmrReports emr = (EmrReports) obj;
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
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idemrReports` = " + emr.getIdemrReports();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromEmrReport(emr, pStmt);
            
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
            EmrReports emr = new EmrReports();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idemrReports` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                
                SetEmrReportFromResultSet(emr, rs);
            }
            
            rs.close();
            stmt.close();
            return emr;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<EmrReports> GetEmrReportsFromOrderID(Integer ID)
    {
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            ArrayList<EmrReports> emrList = new ArrayList<>();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                EmrReports emr = new EmrReports();
                SetEmrReportFromResultSet(emr, rs);
                emrList.add(emr);
            }
            
            rs.close();
            stmt.close();
            return emrList;
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
    
    private EmrReports SetEmrReportFromResultSet(EmrReports emr, ResultSet rs) throws SQLException
    {
        emr.setIdemrReports(rs.getInt("idemrReports"));
        emr.setIdOrders(rs.getInt("idOrders"));
        emr.setReleaseJobId(rs.getInt("releaseJobId"));
        emr.setCreated(rs.getTimestamp("created"));
        emr.setReport(rs.getBytes("report"));
        return emr;
    }
    
    private PreparedStatement SetStatementFromEmrReport(EmrReports emr, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, emr.getIdOrders());
        pStmt.setInt(2, emr.getReleaseJobId());
        pStmt.setTimestamp(3, Convert.ToSQLDateTime(emr.getCreated()));
        pStmt.setBytes(4, emr.getReport());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
