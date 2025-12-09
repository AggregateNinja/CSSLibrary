package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailOrderStatements;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DetailOrderStatementsDAO implements IStructureCheckable {

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailOrderStatements`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailOrderStatements)">
    public static DetailOrderStatements insert(Connection con, DetailOrderStatements obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::Insert: Received a NULL DetailOrderStatements object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  clientId,"
                + "  subscriberArNo,"
                + "  statementFilename,"
                + "  balance,"
                + "  userId,"
                + "  invoiceDate,"
                + "  approvedDate,"
                + "  invoiceNumber"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberArNo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getStatementFilename());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBalance());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getInvoiceDate());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInvoiceNumber());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("DetailOrderStatementsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdDetailOrderStatements(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (DetailOrderStatements)">
    public static DetailOrderStatements insert(DetailOrderStatements obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::Insert: Received a NULL DetailOrderStatements object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="insert (String subscriberArNo, String statementFileName, BigDecimal balance, Integer userId, Date invoiceDate, Date approvedDate)">
    public static Integer insert(String subscriberArNo, String statementFileName, BigDecimal balance, Integer userId, Date invoiceDate, Date approvedDate) throws SQLException
    {
        DetailOrderStatements statement = new DetailOrderStatements();
        statement.setApprovedDate(approvedDate);
        statement.setBalance(balance);
        statement.setSubscriberArNo(subscriberArNo);
        statement.setInvoiceDate(invoiceDate);
        statement.setStatementFilename(statementFileName);
        statement.setUserId(userId);
        
        statement = insert(statement);
        
        return statement == null ? null : statement.getIdDetailOrderStatements();
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="insert (Integer clientId, String statementFileName, BigDecimal balance, Integer userId, Date invoiceDate, Date approvedDate)">
    public static Integer insert(Integer clientId, String statementFileName, BigDecimal balance, Integer userId, Date invoiceDate, Date approvedDate, Integer invoiceNumber) throws SQLException
    {
        DetailOrderStatements statement = new DetailOrderStatements();
        statement.setApprovedDate(approvedDate);
        statement.setBalance(balance);
        statement.setClientId(clientId);
        statement.setInvoiceDate(invoiceDate);
        statement.setInvoiceNumber(invoiceNumber);
        statement.setStatementFilename(statementFileName);
        statement.setUserId(userId);
        
        statement = insert(statement);
        
        return statement == null ? null : statement.getIdDetailOrderStatements();
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, DetailOrderStatements)">
    public static void update(Connection con, DetailOrderStatements obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::Update: Received a NULL DetailOrderStatements object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " clientId = ?,"
                + " subscriberArNo = ?,"
                + " statementFilename = ?,"
                + " balance = ?,"
                + " userId = ?,"
                + " invoiceDate = ?,"
                + " approvedDate = ?,"
                + " invoiceNumber = ?"
                + " WHERE idDetailOrderStatements = " + obj.getIdDetailOrderStatements();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberArNo());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getStatementFilename());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBalance());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getInvoiceDate());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInvoiceNumber());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (DetailOrderStatements)">
    public static void update(DetailOrderStatements obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::Update: Received a NULL DetailOrderStatements object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, DetailOrderStatements, boolean (forUpdate))">
    public static DetailOrderStatements get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::get: Received a NULL or empty DetailOrderStatements object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idDetailOrderStatements = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        DetailOrderStatements obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (DetailOrderStatements)">
    public static DetailOrderStatements get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("DetailOrderStatementsDAO::get: Received a NULL or empty DetailOrderStatements object.");
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
    public static DetailOrderStatements objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        DetailOrderStatements obj = new DetailOrderStatements();
        obj.setIdDetailOrderStatements(SQLUtil.getInteger(rs, "idDetailOrderStatements"));
        obj.setClientId(SQLUtil.getInteger(rs, "clientId"));
        obj.setSubscriberArNo(rs.getString("subscriberArNo"));
        obj.setStatementFilename(rs.getString("statementFilename"));
        obj.setBalance(rs.getBigDecimal("balance"));
        obj.setUserId(SQLUtil.getInteger(rs, "userId"));
        obj.setInvoiceDate(rs.getDate("invoiceDate"));
        obj.setApprovedDate(rs.getDate("approvedDate"));
        obj.setInvoiceNumber(rs.getInt("invoiceNumber"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `detailOrderStatements`.`idDetailOrderStatements`,\n"
                + "    `detailOrderStatements`.`clientId`,\n"
                + "    `detailOrderStatements`.`subscriberArNo`,\n"
                + "    `detailOrderStatements`.`statementFilename`,\n"
                + "    `detailOrderStatements`.`balance`,\n"
                + "    `detailOrderStatements`.`userId`,\n"
                + "    `detailOrderStatements`.`invoiceDate`,\n"
                + "    `detailOrderStatements`.`approvedDate`\n"
                + "    `detailOrderStatements`.`invoiceNumber`\n"
                + "FROM `cssbilling`.`detailOrderStatements` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
