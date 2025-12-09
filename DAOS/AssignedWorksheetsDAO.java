/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.AssignedWorksheets;
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

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class AssignedWorksheetsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`assignedWorksheets`";

    public boolean InsertAssignedWorksheet(AssignedWorksheets aw) {
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
                    + " `number`, "
                    + "`name`, "
                    + "`department`, "
                    + "`created`) values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, aw.getNumber());
            SQLUtil.SafeSetString(pStmt, 2, aw.getName());
            SQLUtil.SafeSetInteger(pStmt, 3, aw.getDepartment());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, aw.getCreated());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AssignedWorksheetsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean UpdateAssignedWorksheet(AssignedWorksheets aw) {
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
                    + "`number` = ?, "
                    + "`name` = ?, "
                    + "`department` = ?, "
                    + "`created` = ? "
                    + "WHERE `idassignedWorksheets` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, aw.getNumber());
            SQLUtil.SafeSetString(pStmt, 2, aw.getName());
            SQLUtil.SafeSetInteger(pStmt, 3, aw.getDepartment());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, aw.getCreated());
            SQLUtil.SafeSetInteger(pStmt, 5, aw.getIdassignedWorksheets());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AssignedWorksheetsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public AssignedWorksheets GetAssignedWorksheetByNumber(int num) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedWorksheets aw = new AssignedWorksheets();

        try {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `number` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetInteger(pStmt, 1, num);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                aw.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aw.setNumber(rs.getInt("number"));
                aw.setName(rs.getString("name"));
                aw.setDepartment(rs.getInt("department"));
                aw.setCreated(rs.getTimestamp("created"));
            }

            rs.close();
            pStmt.close();

            return aw;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public AssignedWorksheets GetAssignedWorksheetByName(String name) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedWorksheets aw = new AssignedWorksheets();

        try {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetString(pStmt, 1, name);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                aw.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aw.setNumber(rs.getInt("number"));
                aw.setName(rs.getString("name"));
                aw.setDepartment(rs.getInt("department"));
                aw.setCreated(rs.getTimestamp("created"));
            }

            rs.close();
            pStmt.close();

            return aw;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public AssignedWorksheets GetAssignedWorksheetByID(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedWorksheets aw = new AssignedWorksheets();

        try {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `idassignedWorksheets` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetInteger(pStmt, 1, id);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                aw.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aw.setNumber(rs.getInt("number"));
                aw.setName(rs.getString("name"));
                aw.setDepartment(rs.getInt("department"));
                aw.setCreated(rs.getTimestamp("created"));
            }

            rs.close();
            pStmt.close();

            return aw;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<AssignedWorksheets> GetAllAssignedWorksheets() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedWorksheets aw;

        try {
            ArrayList<AssignedWorksheets> aList = new ArrayList<AssignedWorksheets>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                aw = new AssignedWorksheets();

                aw.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aw.setNumber(rs.getInt("number"));
                aw.setName(rs.getString("name"));
                aw.setDepartment(rs.getInt("department"));
                aw.setCreated(rs.getTimestamp("created"));

                aList.add(aw);
            }

            rs.close();
            pStmt.close();

            return aList;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<AssignedWorksheets> AssignedWorksheetsByDepartmentId(int deptId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AssignedWorksheets aw;

        try {
            ArrayList<AssignedWorksheets> aList = new ArrayList<AssignedWorksheets>();
            String query = "SELECT * FROM " + table + " WHERE "
                    + "`department` = " + deptId;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                aw = new AssignedWorksheets();

                aw.setIdassignedWorksheets(rs.getInt("idassignedWorksheets"));
                aw.setNumber(rs.getInt("number"));
                aw.setName(rs.getString("name"));
                aw.setDepartment(rs.getInt("department"));
                aw.setCreated(rs.getTimestamp("created"));

                aList.add(aw);
            }

            rs.close();
            pStmt.close();

            return aList;
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
        String query = "SELECT `assignedWorksheets`.`idassignedWorksheets`,\n"
                + "    `assignedWorksheets`.`number`,\n"
                + "    `assignedWorksheets`.`name`,\n"
                + "    `assignedWorksheets`.`department`,\n"
                + "    `assignedWorksheets`.`created`\n"
                + "FROM `css`.`assignedWorksheets` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
