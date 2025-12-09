package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmrOrderImport;
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
 * @date:   Apr 21, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: EmrOrderImportDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class EmrOrderImportDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrOrderImport`";
    /**
     * All fields except idemrReports
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        EmrOrderImport eoi = (EmrOrderImport)obj;
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
            SetStatementFromEmrOrderLog(eoi, pStmt);
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
        EmrOrderImport eoi = (EmrOrderImport)obj;
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
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idemrOrderImport` = " + eoi.getIdemrOrderImport();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromEmrOrderLog(eoi, pStmt);
            
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
            EmrOrderImport eoi = new EmrOrderImport();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idemrOrderImport` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                
                SetEmrOrderLogFromResultSet(eoi, rs);
            }
            
            rs.close();
            stmt.close();
            return eoi;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<EmrOrderImport> GetAllUnprocessed()
    {
        ArrayList<EmrOrderImport> eoiList = new ArrayList<>();
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
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `processed` = 0";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                EmrOrderImport eoi = new EmrOrderImport();
                SetEmrOrderLogFromResultSet(eoi, rs);
                eoiList.add(eoi);
            }
            
            rs.close();
            stmt.close();
            return eoiList;
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
    
    private EmrOrderImport SetEmrOrderLogFromResultSet(EmrOrderImport eoi, ResultSet rs) throws SQLException
    {
        eoi.setIdemrOrderImport(rs.getInt("idemrOrderImport"));
        eoi.setIdOrders(rs.getInt("IdOrders"));
        eoi.setProcessed(rs.getBoolean("processed"));
        eoi.setCreated(rs.getTimestamp("created"));
        return eoi;
    }
    
    private PreparedStatement SetStatementFromEmrOrderLog(EmrOrderImport eol, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, eol.getIdemrOrderImport());
        pStmt.setInt(2, eol.getIdOrders());
        pStmt.setBoolean(3, eol.getProcessed());
        pStmt.setTimestamp(4, Convert.ToSQLDateTime(eol.getCreated()));
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `emrOrderImport`.`idemrOrderImport`,\n"
                + "    `emrOrderImport`.`idOrders`,\n"
                + "    `emrOrderImport`.`processed`,\n"
                + "    `emrOrderImport`.`created`\n"
                + "FROM `css`.`emrOrderImport` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
