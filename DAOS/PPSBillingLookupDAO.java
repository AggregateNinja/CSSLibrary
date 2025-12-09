package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PPSBillingLookup;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class PPSBillingLookupDAO implements IStructureCheckable
{
	public static final String table = "`ppsBillingLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PpsBillingLookup)">
	public static PPSBillingLookup insert(Connection con, PPSBillingLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PPSBillingLookupDAO::Insert: Received a NULL PpsBillingLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PPSBillingLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  orderId,"
			+ "  dateAdded,"
                        + "  user"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateAdded()));
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());

			Integer newId = null;
			sql = pStmt.toString();
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next())
			{
				newId = rs.getInt(1);
			}
			if (newId == null || newId <= 0)
			{
				throw new NullPointerException("PpsBillingLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdppsBillingLookup(newId);
                        rs.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PpsBillingLookup)">
	public static PPSBillingLookup insert(PPSBillingLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PpsBillingLookupDAO::Insert: Received a NULL PpsBillingLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PpsBillingLookup)">
	public static void update(Connection con, PPSBillingLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PpsBillingLookupDAO::Update: Received a NULL PpsBillingLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PpsBillingLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " orderId = ?,"
			+ " dateAdded = ?,"
                        + " user = ?,"
                        + " dateApproved = ?,"
                        + " approvedBy = ?,"
                        + " approved = ?,"
                        + " approvedReason = ?"
			+ " WHERE idppsBillingLookup = " + obj.getIdppsBillingLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateAdded()));
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());
                        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateApproved()));
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getApprovedBy());
                        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isApproved());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getApprovedReason());
			sqlOutput = pStmt.toString();
			pStmt.executeUpdate();
		}
		catch (Exception ex)
		{
			String errorMsg = ex.getMessage() + " " + sqlOutput;
			System.out.println(errorMsg);
			throw new SQLException(errorMsg);
		}
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (PpsBillingLookup)">
	public static void update(PPSBillingLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PpsBillingLookupDAO::Update: Received a NULL PpsBillingLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PpsBillingLookup, boolean (forUpdate))">
	public static PPSBillingLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PpsBillingLookupDAO::get: Received a NULL or empty PpsBillingLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PpsBillingLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idppsBillingLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PPSBillingLookup obj = null;

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{

			ResultSet rs = pStmt.executeQuery();
			if (rs.next())
			{
				obj = objectFromResultSet(rs);
			}
                        rs.close();
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw new SQLException(ex.getMessage() + " " + sql);
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (PpsBillingLookup)">
	public static PPSBillingLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PpsBillingLookupDAO::get: Received a NULL or empty PpsBillingLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="getByOrderId (OrderId)">
        public static PPSBillingLookup getByOrderId(int orderId) throws SQLException
        {
            PPSBillingLookup ppsLookup = null;
            
            Database.DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (!con.isValid(2)) con = CheckDBConnection.Check(dbs, con);
            
            String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(orderId);
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next())
                ppsLookup = objectFromResultSet(rs);
            
            rs.close();
            pStmt.close();
            
            return ppsLookup;
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PPSBillingLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PPSBillingLookup obj = new PPSBillingLookup();
		obj.setIdppsBillingLookup(SQLUtil.getInteger(rs,"idppsBillingLookup"));
		obj.setOrderId(SQLUtil.getInteger(rs,"orderId"));
		obj.setDateAdded(rs.getTimestamp("dateAdded"));
                obj.setUser(rs.getInt("user"));
                obj.setDateApproved(rs.getDate("dateApproved"));
                obj.setApprovedBy(rs.getInt("approvedBy"));
                obj.setApproved(rs.getBoolean("approved"));
                obj.setApprovedReason(rs.getString("approvedReason"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `ppsBillingLookup`.`idppsBillingLookup`,\n"
                + "    `ppsBillingLookup`.`orderId`,\n"
                + "    `ppsBillingLookup`.`dateAdded`,\n"
                + "    `ppsBillingLookup`.`user`,\n"
                + "    `ppsBillingLookup`.`dateApproved`,\n"
                + "    `ppsBillingLookup`.`approvedBy`,\n"
                + "    `ppsBillingLookup`.`approved`,\n"
                + "    `ppsBillingLookup`.`approvedReason`\n"
                + "FROM `css`.`ppsBillingLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
        
}
