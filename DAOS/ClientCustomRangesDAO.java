package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientCustomRanges;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientCustomRangesDAO implements IStructureCheckable {

    public static final String table = "`clientCustomRanges`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,ClientCustomRanges)">
    public static ClientCustomRanges insert(Connection con, ClientCustomRanges obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::Insert: Received a NULL ClientCustomRanges object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientCustomRangesDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  clientId,"
                + "  testNumber,"
                + "  lowNormal,"
                + "  highNormal,"
                + "  alertLow,"
                + "  alertHigh,"
                + "  criticalLow,"
                + "  criticalHigh,"
                + "  printNormals,"
                + "  units,"
                + "  useMaximums,"
                + "  maxLowResult,"
                + "  maxHighResult,"
                + "  maxLowNumeric,"
                + "  maxHighNumeric"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getLowNormal());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getHighNormal());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getAlertLow());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getAlertHigh());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getCriticalLow());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getCriticalHigh());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPrintNormals());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getUnits());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isUseMaximums());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMaxLowResult());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMaxHighResult());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getMaxLowNumeric());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getMaxHighNumeric());

            sql = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (ClientCustomRanges)">
    public static ClientCustomRanges insert(ClientCustomRanges obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::Insert: Received a NULL ClientCustomRanges object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="insert (ClientCustomRanges)">
    public static void delete(ClientCustomRanges obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::Insert: Received a NULL ClientCustomRanges object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "DELETE FROM " + table + " WHERE clientId = " + String.valueOf(obj.getClientId()) + " and testNumber = " + String.valueOf(obj.getTestNumber());
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            sql = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, ClientCustomRanges)">
    public static void update(Connection con, ClientCustomRanges obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::Update: Received a NULL ClientCustomRanges object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientCustomRangesDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " clientId = ?,"
                + " testNumber = ?,"
                + " lowNormal = ?,"
                + " highNormal = ?,"
                + " alertLow = ?,"
                + " alertHigh = ?,"
                + " criticalLow = ?,"
                + " criticalHigh = ?,"
                + " printNormals = ?,"
                + " units = ?,"
                + " useMaximums = ?,"
                + " maxLowResult = ?,"
                + " maxHighResult = ?,"
                + " maxLowNumeric = ?,"
                + " maxHighNumeric = ?"
                + " WHERE clientId = " + obj.getClientId() + " and testNumber = " + obj.getTestNumber();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getLowNormal());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getHighNormal());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getAlertLow());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getAlertHigh());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getCriticalLow());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getCriticalHigh());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPrintNormals());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getUnits());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isUseMaximums());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMaxLowResult());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getMaxHighResult());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getMaxLowNumeric());
            SQLUtil.SafeSetDouble(pStmt, ++i, obj.getMaxHighNumeric());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (ClientCustomRanges)">
    public static void update(ClientCustomRanges obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::Update: Received a NULL ClientCustomRanges object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, ClientCustomRanges, boolean (forUpdate))">
    public static ClientCustomRanges get(Connection con, Integer clientId, Integer testNumber, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (clientId == null || clientId <= 0 || testNumber == null || testNumber <= 0) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::get: Received a NULL or empty ClientCustomRanges object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientCustomRangesDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(clientId) + " AND testNumber = " + String.valueOf(testNumber);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        ClientCustomRanges obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                obj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (ClientCustomRanges)">
    public static ClientCustomRanges get(Integer clientId, Integer testNumber) throws SQLException, IllegalArgumentException, NullPointerException {
        if (clientId == null || clientId <= 0 || testNumber == null || testNumber <= 0) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::get: Received a NULL or empty ClientCustomRanges object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, clientId, testNumber, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllForClient (List<ClientCustomRanges>)">
    public static List<ClientCustomRanges> getAllForClient(Integer clientId) throws SQLException, IllegalArgumentException, NullPointerException {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("ClientCustomRangesDAO::getAllForClient: Received a NULL or empty clientId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(clientId);

        List<ClientCustomRanges> clientCustomRangeList = new ArrayList<ClientCustomRanges>();
        ClientCustomRanges obj = null;
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                obj = objectFromResultSet(rs);
                clientCustomRangeList.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return clientCustomRangeList;
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static ClientCustomRanges objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        ClientCustomRanges obj = new ClientCustomRanges();
        obj.setClientId(SQLUtil.getInteger(rs, "clientId"));
        obj.setTestNumber(SQLUtil.getInteger(rs, "testNumber"));
        obj.setLowNormal(rs.getDouble("lowNormal"));
        obj.setHighNormal(rs.getDouble("highNormal"));
        obj.setAlertLow(rs.getDouble("alertLow"));
        obj.setAlertHigh(rs.getDouble("alertHigh"));
        obj.setCriticalLow(rs.getDouble("criticalLow"));
        obj.setCriticalHigh(rs.getDouble("criticalHigh"));
        obj.setPrintNormals(rs.getString("printNormals"));
        obj.setUnits(rs.getString("units"));
        obj.setUseMaximums(rs.getBoolean("useMaximums"));
        obj.setMaxLowResult(rs.getString("maxLowResult"));
        obj.setMaxHighResult(rs.getString("maxHighResult"));
        obj.setMaxLowNumeric(rs.getDouble("maxLowNumeric"));
        obj.setMaxHighNumeric(rs.getDouble("maxHighNumeric"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `clientCustomRanges`.`clientId`,\n"
                + "    `clientCustomRanges`.`testNumber`,\n"
                + "    `clientCustomRanges`.`lowNormal`,\n"
                + "    `clientCustomRanges`.`highNormal`,\n"
                + "    `clientCustomRanges`.`alertLow`,\n"
                + "    `clientCustomRanges`.`alertHigh`,\n"
                + "    `clientCustomRanges`.`criticalLow`,\n"
                + "    `clientCustomRanges`.`criticalHigh`,\n"
                + "    `clientCustomRanges`.`printNormals`,\n"
                + "    `clientCustomRanges`.`units`,\n"
                + "    `clientCustomRanges`.`useMaximums`,\n"
                + "    `clientCustomRanges`.`maxLowResult`,\n"
                + "    `clientCustomRanges`.`maxHighResult`,\n"
                + "    `clientCustomRanges`.`maxLowNumeric`,\n"
                + "    `clientCustomRanges`.`maxHighNumeric`\n"
                + "FROM `css`.`clientCustomRanges` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
