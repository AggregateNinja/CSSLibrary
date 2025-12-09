package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PaymentDetails;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDetailsDAO implements IStructureCheckable {

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`paymentDetails`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,PaymentDetails)">
    public static PaymentDetails insert(Connection con, PaymentDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentDetailsDAO::Insert: Received a NULL PaymentDetails object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentDetailsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentId,"
                + "  billingPayorId,"
                + "  paymentReceivedDate,"
                + "  paymentMethodId"
                + ")"
                + "VALUES (?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBillingPayorId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPaymentReceivedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("PaymentDetailsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdPaymentDetails(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (PaymentDetails)">
    public static PaymentDetails insert(PaymentDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentDetailsDAO::Insert: Received a NULL PaymentDetails object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, PaymentDetails)">
    public static void update(Connection con, PaymentDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentDetailsDAO::Update: Received a NULL PaymentDetails object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentDetailsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " adjustmentId = ?,"
                + " billingPayorId = ?,"
                + " paymentReceivedDate = ?,"
                + " paymentMethodId = ?"
                + " WHERE idPaymentDetails = " + obj.getIdPaymentDetails();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBillingPayorId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPaymentReceivedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (PaymentDetails)">
    public static void update(PaymentDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentDetailsDAO::Update: Received a NULL PaymentDetails object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, PaymentDetails, boolean (forUpdate))">
    public static PaymentDetails get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("PaymentDetailsDAO::get: Received a NULL or empty PaymentDetails object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentDetailsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idPaymentDetails = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        PaymentDetails obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (PaymentDetails)">
    public static PaymentDetails get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("PaymentDetailsDAO::get: Received a NULL or empty PaymentDetails object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static PaymentDetails objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        PaymentDetails obj = new PaymentDetails();
        obj.setIdPaymentDetails(SQLUtil.getInteger(rs, "idPaymentDetails"));
        obj.setAdjustmentId(SQLUtil.getInteger(rs, "adjustmentId"));
        obj.setBillingPayorId(SQLUtil.getInteger(rs, "billingPayorId"));
        obj.setPaymentReceivedDate(rs.getDate("paymentReceivedDate"));
        obj.setPaymentMethodId(SQLUtil.getInteger(rs, "paymentMethodId"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `paymentDetails`.`idPaymentDetails`,\n"
                + "    `paymentDetails`.`adjustmentId`,\n"
                + "    `paymentDetails`.`billingPayorId`,\n"
                + "    `paymentDetails`.`paymentReceivedDate`,\n"
                + "    `paymentDetails`.`paymentMethodId`\n"
                + "FROM `cssbilling`.`paymentDetails` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
