/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.GroupPolicies;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/27/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class GroupPoliciesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`groupPolicies`";

    public boolean InsertGroupPolicy(GroupPolicies gp) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "Insert INTO " + table + "("
                    + "`module`, "
                    + "`group1`, "
                    + "`group2`, "
                    + "`group3`, "
                    + "`group4`, "
                    + "`group5`, "
                    + "`group6`, "
                    + "`group7`, "
                    + "`group8`, "
                    + "`group9`, "
                    + "`group10`) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, gp.getModule());
            pStmt.setBoolean(2, gp.getGroup1());
            pStmt.setBoolean(3, gp.getGroup2());
            pStmt.setBoolean(4, gp.getGroup3());
            pStmt.setBoolean(5, gp.getGroup4());
            pStmt.setBoolean(6, gp.getGroup5());
            pStmt.setBoolean(7, gp.getGroup6());
            pStmt.setBoolean(8, gp.getGroup7());
            pStmt.setBoolean(9, gp.getGroup8());
            pStmt.setBoolean(10, gp.getGroup9());
            pStmt.setBoolean(11, gp.getGroup10());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println();
            ex.toString();
            return false;
        }
    }

    public boolean UpdateGroupPolicy(GroupPolicies gp) throws SQLException {
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
                    + "`module` = ?, "
                    + "`group1` = ?, "
                    + "`group2` = ?, "
                    + "`group3` = ?, "
                    + "`group4` = ?, "
                    + "`group5` = ?, "
                    + "`group6` = ?, "
                    + "`group7` = ?, "
                    + "`group8` = ?, "
                    + "`group9` = ?, "
                    + "`group10` = ?) "
                    + "WHERE `idgroupPolicies` = " + gp.getIdgroupPolicies();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, gp.getModule());
            pStmt.setBoolean(2, gp.getGroup1());
            pStmt.setBoolean(3, gp.getGroup2());
            pStmt.setBoolean(4, gp.getGroup3());
            pStmt.setBoolean(5, gp.getGroup4());
            pStmt.setBoolean(6, gp.getGroup5());
            pStmt.setBoolean(7, gp.getGroup6());
            pStmt.setBoolean(8, gp.getGroup7());
            pStmt.setBoolean(9, gp.getGroup8());
            pStmt.setBoolean(10, gp.getGroup9());
            pStmt.setBoolean(11, gp.getGroup10());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println();
            ex.toString();
            return false;
        }
    }

    public GroupPolicies GetGroupPolicyByModule(String module) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        GroupPolicies gp = new GroupPolicies();

        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `module` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetString(pStmt, 1, module);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                gp.setIdgroupPolicies(rs.getInt("idgroupPolicies"));
                gp.setModule(rs.getString("module"));
                gp.setGroup1(rs.getBoolean("group1"));
                gp.setGroup2(rs.getBoolean("group2"));
                gp.setGroup3(rs.getBoolean("group3"));
                gp.setGroup4(rs.getBoolean("group4"));
                gp.setGroup5(rs.getBoolean("group5"));
                gp.setGroup6(rs.getBoolean("group6"));
                gp.setGroup7(rs.getBoolean("group7"));
                gp.setGroup8(rs.getBoolean("group8"));
                gp.setGroup9(rs.getBoolean("group9"));
                gp.setGroup10(rs.getBoolean("group10"));
            }

            rs.close();
            pStmt.close();

            return gp;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<GroupPolicies> GetAllGroupPolices() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        GroupPolicies gp;

        try {
            ArrayList<GroupPolicies> gList = new ArrayList<GroupPolicies>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                gp = new GroupPolicies();

                gp.setIdgroupPolicies(rs.getInt("idgroupPolicies"));
                gp.setModule(rs.getString("module"));
                gp.setGroup1(rs.getBoolean("group1"));
                gp.setGroup2(rs.getBoolean("group2"));
                gp.setGroup3(rs.getBoolean("group3"));
                gp.setGroup4(rs.getBoolean("group4"));
                gp.setGroup5(rs.getBoolean("group5"));
                gp.setGroup6(rs.getBoolean("group6"));
                gp.setGroup7(rs.getBoolean("group7"));
                gp.setGroup8(rs.getBoolean("group8"));
                gp.setGroup9(rs.getBoolean("group9"));
                gp.setGroup10(rs.getBoolean("group10"));

                gList.add(gp);
            }

            rs.close();
            pStmt.close();

            return gList;
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
        String query = "SELECT `groupPolicies`.`idgroupPolicies`,\n"
                + "    `groupPolicies`.`module`,\n"
                + "    `groupPolicies`.`group1`,\n"
                + "    `groupPolicies`.`group2`,\n"
                + "    `groupPolicies`.`group3`,\n"
                + "    `groupPolicies`.`group4`,\n"
                + "    `groupPolicies`.`group5`,\n"
                + "    `groupPolicies`.`group6`,\n"
                + "    `groupPolicies`.`group7`,\n"
                + "    `groupPolicies`.`group8`,\n"
                + "    `groupPolicies`.`group9`,\n"
                + "    `groupPolicies`.`group10`\n"
                + "FROM `css`.`groupPolicies` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
