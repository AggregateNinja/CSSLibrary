package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailOrderStatementDetails;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DetailOrderStatementDetailsDAO implements IStructureCheckable {

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailOrderStatementDetails`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailOrderStatementDetails)">
    public static DetailOrderStatementDetails insert(Connection con, DetailOrderStatementDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::Insert: Received a NULL DetailOrderStatementDetails object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailOrderStatementId,"
                + "  detailOrderId,"
                + "  balance,"
                + "  submissionQueueId,"
                + "  created"
                + ")"
                + "VALUES (?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderStatementId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBalance());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionQueueId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCreated()));

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("DetailOrderStatementDetailsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdDetailOrderStatementDetails(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (DetailOrderStatementDetails)">
    public static DetailOrderStatementDetails insert(DetailOrderStatementDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::Insert: Received a NULL DetailOrderStatementDetails object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="insert (Integer detailOrderStatementId, Integer detailOrderId, BigDecimal balance, Integer submissionQueueId, Date created)">
    public static Integer insert(Integer detailOrderStatementId, Integer detailOrderId, BigDecimal balance, Integer submissionQueueId, Date created) throws SQLException
    {
        DetailOrderStatementDetails details = new DetailOrderStatementDetails();
        details.setBalance(balance);
        details.setCreated(created == null ? new Date() : created);
        details.setDetailOrderId(detailOrderId);
        details.setDetailOrderStatementId(detailOrderStatementId);
        details.setSubmissionQueueId(submissionQueueId);
        
        details = insert(details);
        
        return details == null ? null : details.getIdDetailOrderStatementDetails();
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, DetailOrderStatementDetails)">
    public static void update(Connection con, DetailOrderStatementDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::Update: Received a NULL DetailOrderStatementDetails object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " detailOrderStatementId = ?,"
                + " detailOrderId = ?,"
                + " balance = ?,"
                + " submissionQueueId = ?,"
                + " created = ?"
                + " WHERE idDetailOrderStatementDetails = " + obj.getIdDetailOrderStatementDetails();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderStatementId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBalance());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionQueueId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCreated()));
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (DetailOrderStatementDetails)">
    public static void update(DetailOrderStatementDetails obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::Update: Received a NULL DetailOrderStatementDetails object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, DetailOrderStatementDetails, boolean (forUpdate))">
    public static DetailOrderStatementDetails get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::get: Received a NULL or empty DetailOrderStatementDetails object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idDetailOrderStatementDetails = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        DetailOrderStatementDetails obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (DetailOrderStatementDetails)">
    public static DetailOrderStatementDetails get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailOrderStatementDetailsDAO::get: Received a NULL or empty DetailOrderStatementDetails object.");
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
    public static DetailOrderStatementDetails objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        DetailOrderStatementDetails obj = new DetailOrderStatementDetails();
        obj.setIdDetailOrderStatementDetails(SQLUtil.getInteger(rs, "idDetailOrderStatementDetails"));
        obj.setDetailOrderStatementId(SQLUtil.getInteger(rs, "detailOrderStatementId"));
        obj.setDetailOrderId(SQLUtil.getInteger(rs, "detailOrderId"));
        obj.setBalance(rs.getBigDecimal("balance"));
        obj.setSubmissionQueueId(SQLUtil.getInteger(rs, "submissionQueueId"));
        obj.setCreated(rs.getDate("created"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `detailOrderStatementDetails`.`idDetailOrderStatementDetails`,\n"
                + "    `detailOrderStatementDetails`.`detailOrderStatementId`,\n"
                + "    `detailOrderStatementDetails`.`detailOrderId`,\n"
                + "    `detailOrderStatementDetails`.`balance`,\n"
                + "    `detailOrderStatementDetails`.`submissionQueueId`,\n"
                + "    `detailOrderStatementDetails`.`created`\n"
                + "FROM `cssbilling`.`detailOrderStatementDetails` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
