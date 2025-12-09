package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailCptCode;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class DetailCptCodeDAO implements IStructureCheckable
{

    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailCptCodes`";

    public static DetailCptCode insert(DetailCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        return insert(con, obj);
    }
    
    public static DetailCptCode insert(Connection con, DetailCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::Insert: Received a NULL DetailCptCode object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailOrderId,"
                + "  testId,"
                + "  cptCodeId,"
                + "  alias,"
                + "  quantity,"
                + "  billAmount,"
                + "  paid,"
                + "  lastPaymentDate,"
                + "  paymentTypeId,"
                + "  placeOfServiceId,"
                + "  cost,"
                + "  transferredAmount,"
                + "  transferredTo"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getTestId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAlias());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
        pStmt.setBigDecimal(++i, obj.getBillAmount());
        pStmt.setBigDecimal(++i, obj.getPaid());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getLastPaymentDate()));
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPlaceOfServiceId());
        pStmt.setBigDecimal(++i, obj.getCost());
        pStmt.setBigDecimal(++i, obj.getTransferredAmount());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTransferredTo());

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("DetailCptCodeDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIddetailCptCodes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    /**
     * Gets the detail cpt code for the provided unique database identifier through
     *  the supplied connection.
     * 
     *  If forUpdate is true, query is submitted in anticipation for an update
     *  to the row. Should be used for transactional work, if possible.
     * 
     * @param con
     * @param id
     * @param forUpdate
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static DetailCptCode get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Connection object argument");
        }
        
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Integer id argument");
        }
        
        if (con.isValid(2) == false)
        {
            throw new SQLException("DetailCptCodeDAO::get(Connection,Integer,boolean):"
                    + " Supplied Connection object was invalid (database connection was lost?)");
        }
        
        String sql = "SELECT * FROM " + table + " WHERE iddetailCptCodes = " + String.valueOf(id);
        if (forUpdate) sql += " FOR UPDATE";
        
        System.out.println(sql);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailCptCode obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;        
        
    }
    
    public static void update(DetailCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::Update: Received a NULL DetailCptCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " detailOrderId = ?,"
                + " testId = ?,"
                + " cptCodeId = ?,"
                + " alias = ?,"
                + " quantity = ?,"
                + " billAmount = ?,"
                + " paid = ?,"
                + " lastPaymentDate = ?,"
                + " paymentTypeId = ?,"
                + " placeOfServiceId = ?,"
                + " cost = ?,"
                + " transferredAmount = ?,"
                + " transferredTo = ?"
                + " WHERE iddetailCptCodes = " + obj.getIddetailCptCodes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getTestId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
            pStmt.setBigDecimal(++i, obj.getBillAmount());
            pStmt.setBigDecimal(++i, obj.getPaid());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getLastPaymentDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPlaceOfServiceId());
            pStmt.setBigDecimal(++i, obj.getCost());
            pStmt.setBigDecimal(++i, obj.getTransferredAmount());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTransferredTo());
            sqlOutput = pStmt.toString();
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new Exception("DetailCptCodeDAO::Update:"
                        + " Update should have affected one row, affected: "
                        + rowsAffected);
            }
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }
    
    public static void update(Connection con, DetailCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::update(Connection,DetailOrder):"
                    + " Received a [NULL] Connection argument");
        }
        
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::Update: Received a NULL DetailCptCode object.");
        }

        
        if (con.isValid(2) == false)
        {
            throw new SQLException("DetailCptCodeDAO::update(Connection,DetailOrder):"
                    + " Connection object is invalid (database connection may have been lost)");
        }

        String sql = "UPDATE " + table + " SET "
                + " detailOrderId = ?,"
                + " testId = ?,"
                + " cptCodeId = ?,"
                + " alias = ?,"
                + " quantity = ?,"
                + " billAmount = ?,"
                + " paid = ?,"
                + " lastPaymentDate = ?,"
                + " paymentTypeId = ?,"
                + " placeOfServiceId = ?,"
                + " cost = ?,"
                + " transferredAmount = ?,"
                + " transferredTo = ?"
                + " WHERE iddetailCptCodes = " + obj.getIddetailCptCodes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getTestId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
            pStmt.setBigDecimal(++i, obj.getBillAmount());
            pStmt.setBigDecimal(++i, obj.getPaid());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getLastPaymentDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPaymentTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPlaceOfServiceId());
            pStmt.setBigDecimal(++i, obj.getCost());
            pStmt.setBigDecimal(++i, obj.getTransferredAmount());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTransferredTo());
            sqlOutput = pStmt.toString();
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new Exception("DetailCptCodeDAO::Update:"
                        + " Update should have affected one row, affected: "
                        + rowsAffected);
            }
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }

    public static DetailCptCode get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::Get: Received a NULL or empty DetailCptCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE iddetailCptCodes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailCptCode obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }
    
    public static Collection<DetailCptCode> getByDetailOrderId(Connection con, Integer detailOrderId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailOrderId == null || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::getByDetailOrderId:"
                    + " Received a [NULL] or invalid detailOrderId object.");
        }

        String sql = "SELECT * FROM " + table + " WHERE detailOrderId = " + String.valueOf(detailOrderId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Collection<DetailCptCode> detailCptCodes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                detailCptCodes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return detailCptCodes;
    }
    
    public static Collection<DetailCptCode> getByDetailOrderId(Integer detailOrderId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailOrderId == null || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("DetailCptCodeDAO::getByDetailOrderId:"
                    + " Received a [NULL] or invalid detailOrderId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE detailOrderId = " + String.valueOf(detailOrderId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Collection<DetailCptCode> detailCptCodes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                detailCptCodes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return detailCptCodes;        
    }

    public static DetailCptCode ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        DetailCptCode obj = new DetailCptCode();
        obj.setIddetailCptCodes(rs.getInt("iddetailCptCodes"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setTestId(SQLUtil.getInteger(rs, "testId"));
        obj.setCptCodeId(rs.getInt("cptCodeId"));
        obj.setAlias(rs.getString("alias"));
        obj.setQuantity(rs.getInt("quantity"));
        obj.setBillAmount(rs.getBigDecimal("billAmount"));
        obj.setPaid(rs.getBigDecimal("paid"));
        obj.setLastPaymentDate(rs.getDate("lastPaymentDate"));
        obj.setPaymentTypeId(rs.getInt("paymentTypeId"));
        obj.setPlaceOfServiceId(rs.getInt("placeOfServiceId"));
        obj.setCost(rs.getBigDecimal("cost"));
        obj.setCost(rs.getBigDecimal("transferredAmount"));
        obj.setTransferredTo(rs.getInt("transferredTo"));
        
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailCptCodes`.`iddetailCptCodes`,\n"
                + "    `detailCptCodes`.`detailOrderId`,\n"
                + "    `detailCptCodes`.`testId`,\n"
                + "    `detailCptCodes`.`cptCodeId`,\n"
                + "    `detailCptCodes`.`alias`,\n"
                + "    `detailCptCodes`.`quantity`,\n"
                + "    `detailCptCodes`.`billAmount`,\n"
                + "    `detailCptCodes`.`paid`,\n"
                + "    `detailCptCodes`.`lastPaymentDate`,\n"
                + "    `detailCptCodes`.`paymentTypeId`,\n"
                + "    `detailCptCodes`.`placeOfServiceId`,\n"
                + "    `detailCptCodes`.`cost`,\n"
                + "    `detailCptCodes`.`transferredAmount`,\n"
                + "    `detailCptCodes`.`transferredTo`\n"
                + "FROM `cssbilling`.`detailCptCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
