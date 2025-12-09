package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PaymentMethodFields;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMethodFieldsDAO implements IStructureCheckable {

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`paymentMethodFields`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,PaymentMethodFields)">
    public static PaymentMethodFields insert(Connection con, PaymentMethodFields obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::Insert: Received a NULL PaymentMethodFields object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  paymentMethodId,"
                + "  name,"
                + "  isRequired"
                + ")"
                + "VALUES (?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsRequired());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("PaymentMethodFieldsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdPaymentMethodFields(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (PaymentMethodFields)">
    public static PaymentMethodFields insert(PaymentMethodFields obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::Insert: Received a NULL PaymentMethodFields object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, PaymentMethodFields)">
    public static void update(Connection con, PaymentMethodFields obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::Update: Received a NULL PaymentMethodFields object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " paymentMethodId = ?,"
                + " name = ?,"
                + " isRequired = ?"
                + " WHERE idPaymentMethodFields = " + obj.getIdPaymentMethodFields();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsRequired());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (PaymentMethodFields)">
    public static void update(PaymentMethodFields obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::Update: Received a NULL PaymentMethodFields object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, PaymentMethodFields, boolean (forUpdate))">
    public static PaymentMethodFields get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::get: Received a NULL or empty PaymentMethodFields object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idPaymentMethodFields = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        PaymentMethodFields obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (PaymentMethodFields)">
    public static PaymentMethodFields get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("PaymentMethodFieldsDAO::get: Received a NULL or empty PaymentMethodFields object.");
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
    public static PaymentMethodFields objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        PaymentMethodFields obj = new PaymentMethodFields();
        obj.setIdPaymentMethodFields(SQLUtil.getInteger(rs, "idPaymentMethodFields"));
        obj.setPaymentMethodId(SQLUtil.getInteger(rs, "paymentMethodId"));
        obj.setName(rs.getString("name"));
        obj.setIsRequired(SQLUtil.getInteger(rs, "isRequired"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `paymentMethodFields`.`idPaymentMethodFields`,\n"
                + "    `paymentMethodFields`.`paymentMethodId`,\n"
                + "    `paymentMethodFields`.`name`,\n"
                + "    `paymentMethodFields`.`isRequired`\n"
                + "FROM `cssbilling`.`paymentMethodFields` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
