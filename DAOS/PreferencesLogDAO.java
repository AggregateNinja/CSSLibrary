/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PreferencesLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PreferencesLogDAO implements IStructureCheckable {
    
    public static final String table = "preferencesLog";
    
    //<editor-fold defaultstate="collapsed" desc="PreferencesLog insert(Connection con, PreferencesLog obj)">
    public static boolean insert(Connection con, PreferencesLog obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PreferencesLogDAO::Insert: Received a NULL PreferencesLog object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PreferencesLogDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  preferenceName,"
                + "  preValue,"
                + "  postValue,"
                + "  performedByUserId,"
                + "  date"
                + ")"
                + "VALUES (?,?,?,?,NOW())";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPreferenceName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPreValue());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPostValue());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPerformedByUserId());
            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("PreferencesLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdPreferencesLog(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PreferencesLog insert (PreferencesLog obj)">
    public static boolean insert(PreferencesLog obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PreferencesLogDAO::Insert: Received a NULL TransTypeLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `preferencesLog`.`idpreferencesLog`,\n"
                + "    `preferencesLog`.`preferenceName`,\n"
                + "    `preferencesLog`.`preValue`,\n"
                + "    `preferencesLog`.`postValue`,\n"
                + "    `preferencesLog`.`performedbyUserId`,\n"
                + "    `preferencesLog`.`date`\n"
                + "FROM `css`.`preferencesLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }

}
