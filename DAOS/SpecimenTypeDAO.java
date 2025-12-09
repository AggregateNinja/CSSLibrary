/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.SpecimenTypes;
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

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 10/17/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class SpecimenTypeDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`specimenTypes`";

    public boolean InsertSpecimen(SpecimenTypes specimen) throws SQLException {
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
                    + "`name`, "
                    + "`code`) "
                    + "values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, specimen.getName());
            SQLUtil.SafeSetString(pStmt, 2, specimen.getCode());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateSpecimen(SpecimenTypes specimen) throws SQLException {
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
                    + "`name` = ?, "
                    + "`code` = ? "
                    + "WHERE `idspecimenTypes` = " + specimen.getIdspecimenTypes();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, specimen.getName());
            SQLUtil.SafeSetString(pStmt, 2, specimen.getCode());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public ArrayList<SpecimenTypes> GetSpecimenTypes() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<SpecimenTypes> list = new ArrayList<SpecimenTypes>();
            Statement stmt = con.createStatement();

            String query = "SELECT `idspecimenTypes`, "
                    + "`name`, "
                    + "`code` "
                    + " FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                SpecimenTypes st = new SpecimenTypes();
                st.setIdspecimenTypes(rs.getInt("idspecimenTypes"));
                st.setName(rs.getString("name"));
                st.setCode(rs.getString("code"));
                list.add(st);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public SpecimenTypes GetSpecimenTypeByName(String name) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            SpecimenTypes st = new SpecimenTypes();
            String query = "SELECT `idspecimenTypes`, "
                    + "`name`, "
                    + "`code` "
                    + " FROM " + table
                    + " WHERE `name` = ?";

            PreparedStatement stmt = createStatement(con, query, name);//con.createStatement();
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                st.setIdspecimenTypes(rs.getInt("idspecimenTypes"));
                st.setName(rs.getString("name"));
                st.setCode(rs.getString("code"));
            }

            rs.close();
            stmt.close();

            return st;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public SpecimenTypes GetSpecimenTypeByID(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            SpecimenTypes st = new SpecimenTypes();
            String query = "SELECT `idspecimenTypes`, "
                    + "`name`, "
                    + "`code` "
                    + " FROM " + table
                    + " WHERE `idspecimenTypes` = " + id;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                st.setIdspecimenTypes(rs.getInt("idspecimenTypes"));
                st.setName(rs.getString("name"));
                st.setCode(rs.getString("code"));
            }

            rs.close();
            stmt.close();

            return st;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public SpecimenTypes GetSpecimenTypeByCode(String code) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            SpecimenTypes st = new SpecimenTypes();
            String query = "SELECT `idspecimenTypes`, "
                    + "`name`, "
                    + "`code` "
                    + " FROM " + table
                    + " WHERE `code` = ?";

//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
            
            PreparedStatement stmt = createStatement(con, query, code);//con.createStatement();
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                st.setIdspecimenTypes(rs.getInt("idspecimenTypes"));
                st.setName(rs.getString("name"));
                st.setCode(rs.getString("code"));
            }

            rs.close();
            stmt.close();

            return st;

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
        String query = "SELECT `specimenTypes`.`idspecimenTypes`,\n"
                + "    `specimenTypes`.`name`,\n"
                + "    `specimenTypes`.`code`\n"
                + "FROM `css`.`specimenTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
