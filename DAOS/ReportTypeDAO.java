package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReportType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Sep 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: ReportTypeDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class ReportTypeDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reportType`";

    public boolean InsertReportType(ReportType reportType) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + "`number`, "
                    + "`name`, "
                    + "`filePath`,"
                    + "`selectable`,"
                    + "`format`)"
                    + " values (?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, reportType.getNumber());
            pStmt.setString(2, reportType.getName());
            pStmt.setString(3, reportType.getFilePath());
            pStmt.setBoolean(4, reportType.isSelectable());
            pStmt.setString(5, reportType.getFormat());


            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateReportType(ReportType reportType) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = "UPDATE " + table + " SET"
                    + " `number` = ?,"
                    + " `name` = ?,"
                    + " `filePath` = ?,"
                    + " `selectable` = ?,"
                    + " `format` = ? "
                    + "WHERE `idreportType` = " + reportType.getIdreportType();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, reportType.getNumber());
            pStmt.setString(2, reportType.getName());
            pStmt.setString(3, reportType.getFilePath());
            pStmt.setBoolean(4, reportType.isSelectable());
            pStmt.setString(5, reportType.getFormat());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public ReportType GetReportType(int ReportTypeNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ReportType reportType = new ReportType();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `number` = " + ReportTypeNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                reportType.setIdreportType(rs.getInt("idreportType"));
                reportType.setNumber(rs.getInt("number"));
                reportType.setName(rs.getString("name"));
                reportType.setFilePath(rs.getString("filePath"));
                reportType.setSelectable(rs.getBoolean("selectable"));
                reportType.setFormat(rs.getString("format"));
            }

            rs.close();
            stmt.close();

            return reportType;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public ReportType GetReportTypeById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ReportType reportType = new ReportType();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idreportType` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                reportType.setIdreportType(rs.getInt("idreportType"));
                reportType.setNumber(rs.getInt("number"));
                reportType.setName(rs.getString("name"));
                reportType.setFilePath(rs.getString("filePath"));
                reportType.setSelectable(rs.getBoolean("selectable"));
                reportType.setFormat(rs.getString("format"));
            }

            rs.close();
            stmt.close();

            return reportType;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ReportType GetReportTypeByName(String name)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ReportType reportType = new ReportType();

            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, name);            
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {

                reportType.setIdreportType(rs.getInt("idreportType"));
                reportType.setNumber(rs.getInt("number"));
                reportType.setName(rs.getString("name"));
                reportType.setFilePath(rs.getString("filePath"));
                reportType.setSelectable(rs.getBoolean("selectable"));
                reportType.setFormat(rs.getString("format"));
            }

            rs.close();
            pStmt.close();
            return reportType;
            
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }

    public boolean ReportTypeExists(int ReportTypeNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`number` = " + ReportTypeNo);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    public int GetReportTypeID(int ReportTypeNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT `idreportType` FROM " + table + " "
                    + "WHERE `number` = " + ReportTypeNo;

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                x = rs.getInt("idreportType");
            }

            return x;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    public List<ReportType> GetSelectableReportTypes()
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<ReportType> rtlist = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " WHERE selectable = 1";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ReportType reportType = new ReportType();
                reportType.setIdreportType(rs.getInt("idreportType"));
                reportType.setNumber(rs.getInt("number"));
                reportType.setName(rs.getString("name"));
                reportType.setFilePath(rs.getString("filePath"));
                reportType.setSelectable(rs.getBoolean("selectable"));
                reportType.setFormat(rs.getString("format"));
                rtlist.add(reportType);
            }

            rs.close();
            stmt.close();

            return rtlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }

    public List<ReportType> GetAllReportTypes() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        ArrayList<ReportType> rtlist = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ReportType reportType = new ReportType();
                reportType.setIdreportType(rs.getInt("idreportType"));
                reportType.setNumber(rs.getInt("number"));
                reportType.setName(rs.getString("name"));
                reportType.setFilePath(rs.getString("filePath"));
                reportType.setSelectable(rs.getBoolean("selectable"));
                reportType.setFormat(rs.getString("format"));
                rtlist.add(reportType);
            }

            rs.close();
            stmt.close();

            return rtlist;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertReportType((ReportType)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ReportTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateReportType((ReportType)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ReportTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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
            return GetReportTypeById(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ReportTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `reportType`.`idreportType`,\n"
                + "    `reportType`.`number`,\n"
                + "    `reportType`.`name`,\n"
                + "    `reportType`.`filePath`,\n"
                + "    `reportType`.`selectable`,\n"
                + "    `reportType`.`format`\n"
                + "FROM `css`.`reportType` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
