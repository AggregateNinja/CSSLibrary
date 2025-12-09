/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.AssignedTests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignedTestsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`assignedTests`";

    public boolean InsertAssignedTest(AssignedTests at) throws SQLException {
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
                    + " `idTests`, "
                    + "`idassignedWorksheets`)values(?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, at.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 2, at.getIdassignedWorksheets());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateAssignedTest(AssignedTests at) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`idTests` = ?, "
                    + "`idassignedWorksheets` = ? "
                    + "WHERE `idassignedTets` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, at.getIdTests());
            SQLUtil.SafeSetInteger(pStmt, 2, at.getIdassignedWorksheets());
            SQLUtil.SafeSetInteger(pStmt, 3, at.getIdassignedTests());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public AssignedTests GetAssignedTestByID(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedTests at = new AssignedTests();

        try {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `idassignedTests` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, id);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                at.setIdassignedTests(rs.getInt("idassignedTests"));
                at.setIdTests(rs.getInt("idTests"));
                at.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
            }

            rs.close();
            pStmt.close();

            return at;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<AssignedTests> GetAssignedTestByWorksheet(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedTests at;

        try {
            ArrayList<AssignedTests> aList = new ArrayList<AssignedTests>();
            String stmt = "SELECT * FROM " + table
                    + " WHERE `idassignedWorksheets` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, id);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                at = new AssignedTests();

                at.setIdassignedTests(rs.getInt("idassignedTests"));
                at.setIdTests(rs.getInt("idTests"));
                at.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aList.add(at);
            }

            rs.close();
            pStmt.close();

            return aList;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int GetWorkSheetIDBtTestID(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int value = 0;
        try {
            String query = "SELECT `idassignedWorksheets` FROM " + table
                    + "WHERE `idTests` = " + ID;

            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                value = rs.getInt("idassignedWorksheets");
            }

            rs.close();
            pStmt.close();

            return value;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return value;
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
        String query = "SELECT `assignedTests`.`idassignedTests`,\n"
                + "    `assignedTests`.`idTests`,\n"
                + "    `assignedTests`.`idassignedWorksheets`\n"
                + "FROM `css`.`assignedTests` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
