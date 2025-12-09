package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailInvoiceStatementLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class DetailInvoiceStatementLookupDAO implements IStructureCheckable
{
	public static final String table = "`cssbilling`.`detailInvoiceStatementLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailInvoiceStatementLookup)">
	public static DetailInvoiceStatementLookup insert(Connection con, DetailInvoiceStatementLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::Insert: Received a NULL DetailInvoiceStatementLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  invoiceNum,"
			+ "  invoiceDate,"
			+ "  detailOrderId"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInvoiceNum());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getInvoiceDate()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());

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
				throw new NullPointerException("DetailInvoiceStatementLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIddetailInvoiceStatementLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (DetailInvoiceStatementLookup)">
	public static DetailInvoiceStatementLookup insert(DetailInvoiceStatementLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::Insert: Received a NULL DetailInvoiceStatementLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, DetailInvoiceStatementLookup)">
	public static void update(Connection con, DetailInvoiceStatementLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::Update: Received a NULL DetailInvoiceStatementLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " invoiceNum = ?,"
			+ " invoiceDate = ?,"
			+ " detailOrderId = ?"
			+ " WHERE iddetailInvoiceStatementLookup = " + obj.getIddetailInvoiceStatementLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInvoiceNum());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getInvoiceDate()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
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

	//<editor-fold defaultstate="collapsed" desc="update (DetailInvoiceStatementLookup)">
	public static void update(DetailInvoiceStatementLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::Update: Received a NULL DetailInvoiceStatementLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, DetailInvoiceStatementLookup, boolean (forUpdate))">
	public static DetailInvoiceStatementLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::get: Received a NULL or empty DetailInvoiceStatementLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE iddetailInvoiceStatementLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		DetailInvoiceStatementLookup obj = null;

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{

			ResultSet rs = pStmt.executeQuery();
			if (rs.next())
			{
				obj = objectFromResultSet(rs);
			}
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw new SQLException(ex.getMessage() + " " + sql);
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (DetailInvoiceStatementLookup)">
	public static DetailInvoiceStatementLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailInvoiceStatementLookupDAO::get: Received a NULL or empty DetailInvoiceStatementLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static DetailInvoiceStatementLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		DetailInvoiceStatementLookup obj = new DetailInvoiceStatementLookup();
		obj.setIddetailInvoiceStatementLookup(SQLUtil.getInteger(rs,"iddetailInvoiceStatementLookup"));
		obj.setInvoiceNum(SQLUtil.getInteger(rs,"invoiceNum"));
		obj.setInvoiceDate(rs.getDate("invoiceDate"));
		obj.setDetailOrderId(SQLUtil.getInteger(rs,"detailOrderId"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `detailInvoiceStatementLookup`.`iddetailInvoiceStatementLookup`,\n"
                + "    `detailInvoiceStatementLookup`.`invoiceNum`,\n"
                + "    `detailInvoiceStatementLookup`.`invoiceDate`,\n"
                + "    `detailInvoiceStatementLookup`.`detailOrderId`\n"
                + "FROM `cssbilling`.`detailInvoiceStatementLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
