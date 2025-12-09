/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/29/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Cpt;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.*;
import Utility.SQLUtil;
import java.io.Serializable;

public class CptDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`cpt`";

    public boolean InsertCPT(Cpt cpt) throws SQLException {
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = "INSERT INTO " + table + " ("
                    + "`iddetailline`, "
                    + "`cpt`, "
                    + "`quantity`, "
                    + "`modifier`) "
                    + "values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, cpt.getDetaillines());
            pStmt.setString(2, cpt.getCpt());
            if (cpt.getQuantity() == 0) {
                pStmt.setInt(3, 1);
            } else {
                pStmt.setInt(3, cpt.getQuantity());
            }
            SQLUtil.SafeSetString(pStmt, 4, cpt.getModifier());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Adding CPT: " + ex.toString());
            return false;
        }
    }

    public Cpt GetCptByDetailID(int detailID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Cpt cpt = new Cpt();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `iddetailline` = " + detailID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                cpt = new Cpt();

                cpt.setIdcpt(rs.getInt("idcpt"));
                cpt.setDetaillines(rs.getInt("iddetailline"));
                cpt.setCpt(rs.getString("cpt"));
                cpt.setQuantity(rs.getInt("quantity"));
                cpt.setModifier(rs.getString("modifier"));
            }

            rs.close();
            stmt.close();

            return cpt;
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
        String query = "SELECT `cpt`.`idcpt`,\n"
                + "    `cpt`.`iddetailline`,\n"
                + "    `cpt`.`cpt`,\n"
                + "    `cpt`.`quantity`,\n"
                + "    `cpt`.`modifier`\n"
                + "FROM `css`.`cpt` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
