package DAOS;

import DAOS.IDAOS.DAOInterface;
import DOS.Testcatmap;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

/**
 * @date: Jun 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: Prescription.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class TestCatMapDAO implements DAOInterface {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`testcatmap`";

    public boolean InsertTestCatMap(Testcatmap testcatmap) throws SQLException {
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
                    + " `testId`,"
                    + " `testcategoryId`,"
                    + " `position`,"
                    + " `indent`,"
                    + " `text`)"
                    + " values (?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, testcatmap.getTestId());
            pStmt.setInt(2, testcatmap.getTestcategoryId());
            pStmt.setInt(3, testcatmap.getPosition());
            pStmt.setInt(4, testcatmap.getIndent());
            pStmt.setString(5, testcatmap.getText());

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

    public boolean UpdateTestCatMap(Testcatmap testcatmap) throws SQLException {
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
                    + " `testId` = ?,"
                    + " `testcategoryId` = ?,"
                    + " `position` = ?,"
                    + " `indent` = ?,"
                    + " `text` = ?"
                    + "WHERE `idtestcatmap` = " + testcatmap.getIdtestcatmap();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, testcatmap.getTestId());
            pStmt.setInt(2, testcatmap.getTestcategoryId());
            pStmt.setInt(3, testcatmap.getPosition());
            pStmt.setInt(4, testcatmap.getIndent());
            pStmt.setString(5, testcatmap.getText());

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

    public Testcatmap GetTestCatMap(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Testcatmap testcatmap = new Testcatmap();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idtestcatmap` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                testcatmap.setIdtestcatmap(rs.getInt("idtestcatmap"));
                testcatmap.setTestId(rs.getInt("testId"));
                testcatmap.setTestcategoryId(rs.getInt("testcategoryId"));
                testcatmap.setTestcategoryId(rs.getInt("position"));
                testcatmap.setTestcategoryId(rs.getInt("indent"));
                testcatmap.setText(rs.getString("text"));

            }

            rs.close();
            stmt.close();

            return testcatmap;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean TestCatMapExists(Integer testId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs;
            int rowCount;
            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `testId` = " + testId + ";";

            rs = stmt.executeQuery(query);
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
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    public int GetTestCatMapId(Integer testId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs;
            int ID;

            String query = "SELECT `idtestcatmap` FROM " + table
                    + " WHERE `testId` = " + testId + ";";

            rs = stmt.executeQuery(query);
            rs.next();
            ID = rs.getInt(1);

            rs.close();
            stmt.close();

            if (ID > 0) {
                return ID;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return -1;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
