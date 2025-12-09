package DAOS;

import Billing.Database.BillingDatabaseSingleton;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Adjustments;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Utility.SQLUtil.createStatement;

public class AdjustmentsDAO implements IStructureCheckable {

    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustments`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,Adjustments)">
    public static Adjustments insert(Connection con, Adjustments obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentsDAO::Insert: Received a NULL Adjustments object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentTypeId,"
                + "  amount,"
                + "  adjustmentApplicationMethodId,"
                + "  date,"
                + "  userId,"
                + "  transactionId,"
                + "  batchId,"
                + "  paymentMethodId,"
                + "  paymentDate"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentTypeId());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getAmount());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentApplicationMethodId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTransactionId() == null ? UUID.randomUUID().toString().substring(0,9) : obj.getTransactionId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getBatchId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getPaymentDate());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("AdjustmentsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdAdjustments(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (Adjustments)">
    public static Adjustments insert(Adjustments obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentsDAO::Insert: Received a NULL Adjustments object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, Adjustments)">
    public static void update(Connection con, Adjustments obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentsDAO::Update: Received a NULL Adjustments object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " adjustmentTypeId = ?,"
                + " amount = ?,"
                + " adjustmentApplicationMethodId = ?,"
                + " date = ?,"
                + " userId = ?,"
                + " transactionId = ?,"
                + " batchId = ?,"
                + " paymentMethodId = ?,"
                + " paymentDate = ?"
                + " WHERE idAdjustments = " + obj.getIdAdjustments();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentTypeId());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getAmount());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentApplicationMethodId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTransactionId() == null ? UUID.randomUUID().toString().substring(0,9) : obj.getTransactionId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getBatchId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentMethodId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getPaymentDate());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Adjustments)">
    public static void update(Adjustments obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentsDAO::Update: Received a NULL Adjustments object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, Adjustments, boolean (forUpdate))">
    public static Adjustments get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentsDAO::get: Received a NULL or empty Adjustments object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idAdjustments = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        Adjustments obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (Adjustments)">
    public static Adjustments get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentsDAO::get: Received a NULL or empty Adjustments object.");
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
    public static Adjustments objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        Adjustments obj = new Adjustments();
        obj.setIdAdjustments(SQLUtil.getInteger(rs, "idAdjustments"));
        obj.setAdjustmentTypeId(SQLUtil.getInteger(rs, "adjustmentTypeId"));
        obj.setAmount(rs.getBigDecimal("amount"));
        obj.setAdjustmentApplicationMethodId(SQLUtil.getInteger(rs, "adjustmentApplicationMethodId"));
        obj.setDate(rs.getTimestamp( "date"));
        obj.setUserId(SQLUtil.getInteger(rs, "userId"));
        obj.setTransactionId(rs.getString("transactionId"));
        obj.setBatchId(rs.getString("batchId"));
        obj.setPaymentMethodId(rs.getInt("paymentMethodId"));
        obj.setPaymentDate(rs.getDate("paymentDate"));

        return obj;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCode (int)">
    public Adjustments getCode(int adjustmentId) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(BillingDatabaseSingleton.ref, con);
                }
            }
            catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
            
            try
            {
                Adjustments adjustment = new Adjustments();
                Statement stmt = con.createStatement();
                
                String query = "SELECT * from " + table + " WHERE idAdjustments = " + adjustmentId;
                
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next())
                {
                    adjustment = objectFromResultSet(rs);
                }
                
                rs.close();
                stmt.close();
                return adjustment;
            }
            catch(Exception ex)
            {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SearchTransIdByAdjustmentId (String, boolean)">
    public List<Adjustments> SearchAdjustmentFromTransId(String IDFragment, boolean CaseSensitive)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            
            try 
            {
                if (con.isClosed()) 
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } 
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }

        List<Adjustments> transList = new ArrayList<>();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            //stmt = con.createStatement();
            String query = "SELECT * from " + table + " ";
            if(CaseSensitive == false)
            {
                query += "WHERE LOWER(`transactionId`) LIKE LOWER(?) ";
            }
            else
            {
                query += "WHERE `transactionId` LIKE ? ";
            }
            query += "ORDER BY `transactionId`";
            
            String searchParam = SQLUtil.createSearchParam(IDFragment);
            
            stmt = createStatement(con, query, searchParam);
            
            rs = stmt.executeQuery();
            
            while(rs.next())
            {
                transList.add(objectFromResultSet(rs));
            }
            
            rs.close();
            stmt.close();
            
            return transList;
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getAdjustmentsByBatchId (String)">
    public static List<Adjustments> getAdjustmentsByBatchId(String batchId) throws IllegalArgumentException, SQLException
    {
        List<Adjustments> adjs = new ArrayList<>();
        
        if (batchId == null || batchId.isEmpty()) {
            throw new IllegalArgumentException("AdjustmentsDAO::getAdjustmentsById: Received NULL or empty BatchId");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String query = "SELECT * FROM " + table + " WHERE batchId = \"" + batchId + "\"";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next())
        {
            adjs.add(objectFromResultSet(rs));
        }
        
        return adjs;
    }
    //</editor-fold>
    
    @Override
    public String structureCheck() {
        String query = "SELECT `adjustments`.`idAdjustments`,\n"
                + "    `adjustments`.`adjustmentTypeId`,\n"
                + "    `adjustments`.`amount`,\n"
                + "    `adjustments`.`adjustmentApplicationMethodId`,\n"
                + "    `adjustments`.`date`,\n"
                + "    `adjustments`.`userId`,\n"
                + "    `adjustments`.`transactionId`,\n"
                + "    `adjustments`.`batchId`,\n"
                + "    `adjustments`.`paymentMethodId`,\n"
                + "    `adjustments`.`paymentDate`\n"
                + "FROM `cssbilling`.`adjustments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
