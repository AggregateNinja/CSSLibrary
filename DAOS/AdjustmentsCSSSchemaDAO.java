/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentsCSSSchema;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.*;
import Utility.Convert;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdjustmentsCSSSchemaDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`adjustments`";

    public boolean InsertAdjustment(AdjustmentsCSSSchema adjustment) throws SQLException {
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
                    + "`detail`, "
                    + "`amount`, "
                    + "`type`, "
                    + "`entered`, "
                    + "`reason`, "
                    + "`user`)"
                    + "values (?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, adjustment.getDetaillines());
            pStmt.setBigDecimal(2, adjustment.getAmount());
            pStmt.setString(3, adjustment.getType());
            pStmt.setTimestamp(4, Convert.ToSQLDateTime(adjustment.getEntered()));
            pStmt.setString(5, adjustment.getReason());
            pStmt.setString(6, adjustment.getUser());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateAdjustment(AdjustmentsCSSSchema adjustment) throws SQLException {
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
                    + "`detail` = ?,"
                    + "`amount` = ?,"
                    + "`type` = ?,"
                    + "`entered` = ?,"
                    + "`reason` = ?,"
                    + "`user`) = ? "
                    + "WHERE `idadjustments` = " + adjustment.getIdadjustments();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, adjustment.getDetaillines());
            pStmt.setBigDecimal(2, adjustment.getAmount());
            pStmt.setString(3, adjustment.getType());
            pStmt.setTimestamp(4, Convert.ToSQLDateTime(adjustment.getEntered()));
            pStmt.setString(5, adjustment.getReason());
            pStmt.setString(6, adjustment.getUser());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public AdjustmentsCSSSchema GetAdjustmentById(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        AdjustmentsCSSSchema adjustment = new AdjustmentsCSSSchema();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idadjustments` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                adjustment = new AdjustmentsCSSSchema();

                adjustment.setIdadjustments(rs.getInt("idadjustments"));
                adjustment.setDetaillines(rs.getInt("detail"));
                adjustment.setAmount(rs.getBigDecimal("amount"));
                adjustment.setType(rs.getString("type"));
                adjustment.setEntered(rs.getTimestamp("entered"));
                adjustment.setReason(rs.getString("reason"));
                adjustment.setUser(rs.getString("user"));
            }

            rs.close();
            stmt.close();

            return adjustment;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertAdjustment((AdjustmentsCSSSchema)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdjustmentsCSSSchemaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateAdjustment((AdjustmentsCSSSchema)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdjustmentsCSSSchemaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return null;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetAdjustmentById(ID.intValue());
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdjustmentsCSSSchemaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `adjustments`.`idadjustments`,\n"
                + "    `adjustments`.`detail`,\n"
                + "    `adjustments`.`amount`,\n"
                + "    `adjustments`.`type`,\n"
                + "    `adjustments`.`entered`,\n"
                + "    `adjustments`.`reason`,\n"
                + "    `adjustments`.`user`\n"
                + "FROM `css`.`adjustments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
