/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Manufacturer;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/21/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class ManufacturerDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`manufacturer`";

    public boolean InsertManufacturer(Manufacturer man) throws SQLException {
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
                    + "`name`, "
                    + "`ID`, "
                    + "`abbreviation`, "
                    + "`address1`, "
                    + "`address2`, "
                    + "`city`,"
                    + "`state`,"
                    + "`zip`, "
                    + "`phone`, "
                    + "`contactName`, "
                    + "`comment`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, man.getName());
            SQLUtil.SafeSetString(pStmt, 2, man.getId());
            SQLUtil.SafeSetString(pStmt, 3, man.getAbbreviation());
            SQLUtil.SafeSetString(pStmt, 4, man.getAddress1());
            SQLUtil.SafeSetString(pStmt, 5, man.getAddress2());
            SQLUtil.SafeSetString(pStmt, 6, man.getCity());
            SQLUtil.SafeSetString(pStmt, 7, man.getState());
            SQLUtil.SafeSetString(pStmt, 8, man.getZip());
            SQLUtil.SafeSetString(pStmt, 9, man.getPhone());
            SQLUtil.SafeSetString(pStmt, 10, man.getContactName());
            SQLUtil.SafeSetString(pStmt, 11, man.getComment());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateManufacturer(Manufacturer man) throws SQLException {
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
                    + "`name` = ?, "
                    + "`ID` = ?, "
                    + "`abbreviation` = ?, "
                    + "`address1` = ?, "
                    + "`address2` = ?, "
                    + "`city` = ?, "
                    + "`state` = ?, "
                    + "`zip` = ?, "
                    + "`phone` = ?, "
                    + "`contactName` = ?, "
                    + "`comment` = ? "
                    + "WHERE `idmanufacturer` = " + man.getIdmanufacturer();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, man.getName());
            SQLUtil.SafeSetString(pStmt, 2, man.getId());
            SQLUtil.SafeSetString(pStmt, 3, man.getAbbreviation());
            SQLUtil.SafeSetString(pStmt, 4, man.getAddress1());
            SQLUtil.SafeSetString(pStmt, 5, man.getAddress2());
            SQLUtil.SafeSetString(pStmt, 6, man.getCity());
            SQLUtil.SafeSetString(pStmt, 7, man.getState());
            SQLUtil.SafeSetString(pStmt, 8, man.getZip());
            SQLUtil.SafeSetString(pStmt, 9, man.getPhone());
            SQLUtil.SafeSetString(pStmt, 10, man.getContactName());
            SQLUtil.SafeSetString(pStmt, 11, man.getComment());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public Manufacturer GetManufacturerByName(String name) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PreparedStatement stmt = null;//con.createStatement();
            Manufacturer man = new Manufacturer();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `name` = ?";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                man = ManufacturerFromResultSet(man, rs);
            }

            rs.close();
            stmt.close();

            return man;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Manufacturer GetManufacturerByDBID(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = con.createStatement();
            Manufacturer man = new Manufacturer();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idmanufacturer` = " + id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                man = ManufacturerFromResultSet(man, rs);
            }

            rs.close();
            stmt.close();

            return man;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Manufacturer GetManufacturerByStringID(String id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PreparedStatement stmt = null; //con.createStatement();
            Manufacturer man = new Manufacturer();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `ID` = ?";

            stmt = createStatement(con, query, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                man = ManufacturerFromResultSet(man, rs);
            }

            rs.close();
            stmt.close();

            return man;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Manufacturer> GetAllManufacturers() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Manufacturer> list = new ArrayList<Manufacturer>();
            Statement stmt = con.createStatement();
            Manufacturer man = new Manufacturer();
            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                man = new Manufacturer();
                man = ManufacturerFromResultSet(man, rs);
                list.add(man);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Manufacturer ManufacturerFromResultSet(Manufacturer man, ResultSet rs) throws SQLException {
        man.setIdmanufacturer(rs.getInt("idmanufacturer"));
        man.setName(rs.getString("name"));
        man.setId(rs.getString("ID"));
        man.setAbbreviation(rs.getString("abbreviation"));
        man.setAddress1(rs.getString("address1"));
        man.setAddress2(rs.getString("address2"));
        man.setCity(rs.getString("city"));
        man.setState(rs.getString("state"));
        man.setZip(rs.getString("zip"));
        man.setPhone(rs.getString("phone"));
        man.setContactName(rs.getString("contactName"));
        man.setComment(rs.getString("comment"));

        return man;
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
        String query = "SELECT `manufacturer`.`idmanufacturer`,\n"
                + "    `manufacturer`.`name`,\n"
                + "    `manufacturer`.`ID`,\n"
                + "    `manufacturer`.`abbreviation`,\n"
                + "    `manufacturer`.`address1`,\n"
                + "    `manufacturer`.`address2`,\n"
                + "    `manufacturer`.`city`,\n"
                + "    `manufacturer`.`state`,\n"
                + "    `manufacturer`.`zip`,\n"
                + "    `manufacturer`.`phone`,\n"
                + "    `manufacturer`.`contactName`,\n"
                + "    `manufacturer`.`comment`\n"
                + "FROM `css`.`manufacturer` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
