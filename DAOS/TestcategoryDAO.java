package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Testcategory;
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
public class TestcategoryDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`testcategory`";

    public boolean InsertTestCategory(Testcategory testcategory) throws SQLException {
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
                    + " `idtestcategory`,"
                    + " `testId`,"
                    + " `label`)"
                    + " values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, testcategory.getIdtestcategory());
            pStmt.setInt(2, testcategory.getTestId());
            pStmt.setString(3, testcategory.getLabel());

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

    public boolean UpdateTestCategory(Testcategory testcategory) throws SQLException {
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
                    + " `label` = ? "
                    + "WHERE `idtestcategory` = " + testcategory.getIdtestcategory();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, testcategory.getTestId());
            pStmt.setString(2, testcategory.getLabel());

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

    public Testcategory GetTestCategory(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Testcategory testcategory = new Testcategory();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idtestcategory` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                testcategory.setIdtestcategory(rs.getInt("idtestcategory"));
                testcategory.setTestId(rs.getInt("testId"));
                testcategory.setLabel(rs.getString("label"));

            }

            rs.close();
            stmt.close();

            return testcategory;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean TestCategoryExists(String label, Integer testId) {
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
            String query;

            if (testId == null) {
                query = "SELECT COUNT(*) FROM " + table
                        + " WHERE `label` = " + label
                        + " AND `testId` IS NULL;";
            } else if (label == null) {
                query = "SELECT COUNT(*) FROM " + table
                        + " WHERE `label` IS NULL AND `testId` = " + testId + ";";
            } else {
                query = "SELECT COUNT(*) FROM " + table
                        + " WHERE `label` = " + label
                        + " AND `testId` = " + testId + ";";
            }

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

    public int GetTestCategoryId(String label, Integer testId) {
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

            String query;

            if (testId == null) {
                query = "SELECT `idtestcategory` FROM " + table
                        + " WHERE `label` = " + label
                        + " AND `testId` IS NULL;";
            } else if (label == null) {
                query = "SELECT `idtestcategory` FROM " + table
                        + " WHERE `label` IS NULL AND `testId` = " + testId + ";";
            } else {
                query = "SELECT `idtestcategory` FROM " + table
                        + " WHERE `label` = " + label
                        + " AND `testId` = " + testId + ";";
            }

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
    
    @Override
    public String structureCheck() {
        String query = "SELECT `testCategories`.`idCategories`,\n"
                + "    `testCategories`.`categoryNumber`,\n"
                + "    `testCategories`.`categoryName`,\n"
                + "    `testCategories`.`categoryOrder`\n"
                + "FROM `css`.`testCategories` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
