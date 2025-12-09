package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.BillingTestCrossReference;
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

/**
 * @date: Oct 16, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: BillingTestCrossReferenceDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class BillingTestCrossReferenceDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`billingTestCrossReference`";

    public boolean InsertTest(BillingTestCrossReference xref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + " ("
                    + "`idTests`, "
                    + "`testNumber`, "
                    + "`subTestNumber`) "
                    + "VALUES "
                    + "(?,?,?);";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, xref.getIdTests());
            pStmt.setInt(2, xref.getTestNumber());
            pStmt.setInt(3, xref.getSubTestNumber());

            String temp = pStmt.toString();

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(xref.getTestNumber() + ":" + xref.getSubTestNumber() + "(" + xref.getIdTests() + ") " + ex.toString());
            return false;
        }
    }

    public boolean UpdateBillingTestCrossReference(BillingTestCrossReference xref) throws SQLException {
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
                    + "`idTests` = ?, "
                    + "`testNumber` = ?, "
                    + "`subTestNumber` = ? "
                    + "WHERE `idbillingTestCrossReference` = " + xref.getIdbillingTestCrossReference();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, xref.getIdTests());
            pStmt.setInt(2, xref.getTestNumber());
            pStmt.setInt(3, xref.getSubTestNumber());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(xref.getTestNumber() + ":" + xref.getSubTestNumber() + "(" + xref.getIdTests() + ") " + ex.toString());
            return false;
        }
    }

    public boolean Exists(int testNumber, int subTestNumber) {
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
                    + "`testNumber` = " + testNumber
                    + " AND `subTestNumber` = " + subTestNumber);
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

    public int GetBillingTestCrossReferenceID(int testNo, int subNo, int IdNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT `idbillingTestCrossReference` FROM "
                    + table
                    + " WHERE `testNumber` = " + testNo
                    + " AND `subTestNumber` = " + subNo
                    + " AND `idTests` = " + IdNo;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }

    }

    public BillingTestCrossReference GetBillingTestCrossReference(int testNo, int subNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BillingTestCrossReference btcr = new BillingTestCrossReference();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM "
                    + table
                    + " WHERE `testNumber` = " + testNo
                    + " AND `subTestNumber` = " + subNo;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                btcr.setIdbillingTestCrossReference(rs.getInt("idbillingTestCrossReference"));
                btcr.setTestNumber(rs.getInt("testNumber"));
                btcr.setSubTestNumber(rs.getInt("subTestNumber"));
                btcr.setIdTests(rs.getInt("idTests"));
            }

            rs.close();
            stmt.close();

            return btcr;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
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
        String query = "SELECT `billingTestCrossReference`.`idbillingTestCrossReference`,\n"
                + "    `billingTestCrossReference`.`idTests`,\n"
                + "    `billingTestCrossReference`.`testNumber`,\n"
                + "    `billingTestCrossReference`.`subTestNumber`\n"
                + "FROM `css`.`billingTestCrossReference` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
