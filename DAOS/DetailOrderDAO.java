package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailOrder;
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
import java.util.List;

public class DetailOrderDAO implements IStructureCheckable
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailOrders`";

    public static DetailOrder insert(DetailOrder obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        return insert(con, obj);
    }
    public static DetailOrder insert(Connection con, DetailOrder obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailOrderDAO::Insert: Received a NULL DetailOrder object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  orderId,"
                + "  dateOfService,"
                + "  balance,"
                + "  feeScheduleId,"
                + "  lastSubmittedOn,"
                + "  lastPaymentDate,"
                + "  active,"
                + "  poNum"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateOfService()));
        pStmt.setBigDecimal(++i, obj.getBalance());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getLastSubmittedOn());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getLastPaymentDate());
        SQLUtil.BooleanToInt(pStmt, ++i, obj.isActive(), false);
        SQLUtil.SafeSetString(pStmt, ++i, obj.getPoNum());
        
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
                throw new NullPointerException("DetailOrderDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIddetailOrders(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }
    
    /**
     * Uses the supplied connection to update the Detail Order object
     * @param con
     * @param obj
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void update(Connection con, DetailOrder obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null)
        {
            throw new IllegalArgumentException("DetailOrderDAO::update(Connection,DetailOrder):"
                    + " Received a [NULL] Connection argument");
        }
        
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailOrderDAO::update(Connection,DetailOrder):"
                    + " Received a [NULL] DetailOrder object");
        }
        
        if (con.isValid(2) == false)
        {
            throw new SQLException("DetailOrderDAO::update(Connection,DetailOrder):"
                    + " Connection object is invalid (database connection may have been lost)");
        }
        
        String sql = "UPDATE " + table + " SET "
                + " orderId = ?,"
                + " dateOfService = ?,"
                + " balance = ?,"
                + " feeScheduleId = ?,"
                + " lastSubmittedOn = ?,"
                + " lastPaymentDate = ?,"
                + " active = ?,"
                + " poNum = ?"
                + " WHERE iddetailOrders = " + obj.getIddetailOrders();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateOfService()));
            pStmt.setBigDecimal(++i, obj.getBalance());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getLastSubmittedOn());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getLastPaymentDate());
            SQLUtil.BooleanToInt(pStmt, ++i, obj.isActive(), false);
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPoNum());
            
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }        
    }

    /**
     * Updates the Detail Order object using the shared singleton database connection
     * 
     * @param obj
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void update(DetailOrder obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailOrderDAO::Update: Received a NULL DetailOrder object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }

    /**
     * Gets the detail order for the provided unique database identifier through
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
    public static DetailOrder get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null)
        {
            throw new IllegalArgumentException("DetailOrderDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Connection object argument");
        }
        
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailOrderDAO::get(Connection,Integer,boolean):"
                    + " Received a [NULL] Integer id argument");
        }
        
        if (con.isValid(2) == false)
        {
            throw new SQLException("DetailOrderDAO::get(Connection,Integer,boolean):"
                    + " Supplied Connection object was invalid (database connection was lost?)");
        }
        
        String sql = "SELECT * FROM " + table + " WHERE iddetailOrders = " + String.valueOf(id);
        if (forUpdate) sql += " FOR UPDATE";

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailOrder obj = null;
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
    
    public static DetailOrder get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailOrderDAO::Get: Received a NULL or empty DetailOrder object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        // Not "for update"
        return get(con, id, false);
    }
    
    /**
     * TODO: now that detailOrders are being deactivated-->created when they
     *  transfer to a new insurance, this needs to return a list of detailOrders
     * @param id
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static DetailOrder getByCssOrderId(Integer id)
             throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailOrderDAO::getByCssOrderId:"
                    + " Received a [NULL] or invalid order Id argument.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailOrder obj = null;
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
    
    public static List<DetailOrder> getAllByCssOrderId(Integer id)
             throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailOrderDAO::getByCssOrderId:"
                    + " Received a [NULL] or invalid order Id argument.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<DetailOrder> objs = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while(rs.next())
            {
                objs.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return objs;        
    }

    private static DetailOrder ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        DetailOrder obj = new DetailOrder();
        obj.setIddetailOrders(rs.getInt("iddetailOrders"));
        obj.setOrderId(rs.getInt("orderId"));
        obj.setDateOfService(rs.getDate("dateOfService"));
        obj.setBalance(rs.getBigDecimal("balance"));
        obj.setFeeScheduleId(rs.getInt("feeScheduleId"));
        obj.setLastSubmittedOn(rs.getDate("lastSubmittedOn"));
        obj.setLastPaymentDate(rs.getDate("lastPaymentDate"));
        Integer activeInt = SQLUtil.getInteger(rs, "active");
        if (activeInt == null) activeInt = 0;
        obj.setActive(activeInt == 1);
        obj.setPoNum(rs.getString("poNum"));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailOrders`.`iddetailOrders`,\n"
                + "    `detailOrders`.`orderId`,\n"
                + "    `detailOrders`.`dateOfService`,\n"
                + "    `detailOrders`.`balance`,\n"
                + "    `detailOrders`.`feeScheduleId`,\n"
                + "    `detailOrders`.`lastSubmittedOn`,\n"
                + "    `detailOrders`.`lastPaymentDate`,\n"
                + "    `detailOrders`.`active`,\n"
                + "    `detailOrders`.`created`,\n"
                + "    `detailOrders`.`poNum`\n"
                + "FROM `cssbilling`.`detailOrders` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
