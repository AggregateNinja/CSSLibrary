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
import DOS.Writeoffs;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.*;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.math.BigDecimal;

import static Utility.SQLUtil.createStatement;

public class WriteOffsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`writeoffs`";

    public boolean InsertWriteOff(Writeoffs writeoff) throws SQLException {
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
                    + "`entered`, "
                    + "`user`)"
                    + "values (?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, writeoff.getDetaillines());
            pStmt.setBigDecimal(2, writeoff.getAmount());
            pStmt.setTimestamp(3, Convert.ToSQLDateTime(writeoff.getEntered()));
            //pStmt.setDate(3, Convert.ToSQLDate(writeoff.getEntered()));
            SQLUtil.SafeSetString(pStmt, 4, writeoff.getUser());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateWriteOff(Writeoffs writeoff) throws SQLException {
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
                    + " `detail` = ?,"
                    + " `amount` = ?,"
                    + " `entered` = ?,"
                    + " `user` = ? "
                    + "WHERE `idtransactions` = " + writeoff.getIdtransactions();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, writeoff.getDetaillines());
            pStmt.setBigDecimal(2, writeoff.getAmount());
            pStmt.setTimestamp(3, Convert.ToSQLDateTime(writeoff.getEntered()));
            pStmt.setString(4, writeoff.getUser());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean AddUser(String user, int id) throws SQLException {
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
                    + " `user` = ?"
                    + " WHERE `idtransactions` = " + id;

            PreparedStatement pStmt = createStatement(con, stmt, user);//con.prepareStatement(stmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Unable to update Writeoff user: " + user + " id: " + id + " MSG: "
                    + ex.toString());
            return false;
        }
    }

    public Writeoffs GetWriteOffByTransaction(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Writeoffs writeoff = new Writeoffs();

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idtransactions` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                writeoff = new Writeoffs();

                writeoff.setIdtransactions(rs.getInt("idtransactions"));
                writeoff.setDetaillines(rs.getInt("detail"));
                writeoff.setAmount(rs.getBigDecimal("amount"));
                writeoff.setEntered(rs.getTimestamp("entered"));
                writeoff.setUser(rs.getString("user"));
            }

            rs.close();
            stmt.close();

            return writeoff;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int GetIdFromJournalExport(BigDecimal payment, java.util.Date entered, int arNo, BigDecimal amtBilled) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id;
        String ids = "";

        try {
            Statement stmt = con.createStatement();

            String query = "SELECT idtransactions"
                    + " FROM " + table
                    + " WHERE amount = " + payment.toString()
                    + " AND entered = '" + Convert.ToSQLDate(entered) + "'"
                    + " AND detail = "
                    + " (SELECT d.detail"
                    + " FROM detaillines d"
                    + " LEFT OUTER JOIN"
                    + " results r ON r.idResults = d.detail "
                    + " LEFT OUTER JOIN"
                    + " orders o ON r.orderId = o.idOrders"
                    + " LEFT OUTER JOIN"
                    + " patients p ON o.patientId = p.idPatients"
                    + " WHERE p.arNo = " + arNo
                    + " AND d.price = " + amtBilled.toString() + ")";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            } else {
                id = 0;
            }

            rs.close();
            stmt.close();

            return id;
        } catch (Exception ex) {
            System.out.println("Failed finding writeoff transaction id. Message: " + ex.toString());
            return 0;
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
        String query = "SELECT `writeoffs`.`idtransactions`,\n"
                + "    `writeoffs`.`detail`,\n"
                + "    `writeoffs`.`amount`,\n"
                + "    `writeoffs`.`entered`,\n"
                + "    `writeoffs`.`user`\n"
                + "FROM `css`.`writeoffs` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
