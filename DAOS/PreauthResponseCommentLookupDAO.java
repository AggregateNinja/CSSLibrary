package DAOS;

import DOS.PreauthResponseCommentLookup;
import DAOS.IDAOS.IStructureCheckable;
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
import java.util.Date;
import java.util.List;


public class PreauthResponseCommentLookupDAO implements IStructureCheckable
{
	public static final String table = "`preauthResponseCommentLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthResponseCommentLookup)">
	public static PreauthResponseCommentLookup insert(Connection con, PreauthResponseCommentLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::Insert: Received a NULL PreauthResponseCommentLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthResponseId,"
			+ "  comments,"
			+ "  id,"
			+ "  createdOn"
			+ ")"
			+ "VALUES (?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getComments());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCreatedOn());

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
				throw new NullPointerException("PreauthResponseCommentLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthResponseCommentLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthResponseCommentLookup)">
	public static PreauthResponseCommentLookup insert(PreauthResponseCommentLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::Insert: Received a NULL PreauthResponseCommentLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthResponseCommentLookup)">
	public static void update(Connection con, PreauthResponseCommentLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::Update: Received a NULL PreauthResponseCommentLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthResponseId = ?,"
			+ " comments = ?,"
			+ " id = ?,"
			+ " createdOn = ?"
			+ " WHERE idpreauthResponseCommentLookup = " + obj.getIdpreauthResponseCommentLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getComments());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCreatedOn());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthResponseCommentLookup)">
	public static void update(PreauthResponseCommentLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::Update: Received a NULL PreauthResponseCommentLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthResponseCommentLookup, boolean (forUpdate))">
	public static PreauthResponseCommentLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::get: Received a NULL or empty PreauthResponseCommentLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthResponseCommentLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthResponseCommentLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthResponseCommentLookup)">
	public static PreauthResponseCommentLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseCommentLookupDAO::get: Received a NULL or empty PreauthResponseCommentLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static List<PreauthResponseCommentLookup> getCommentLookupsByResponseId(int preauthResponseId) throws SQLException, NullPointerException
        {
            ArrayList<PreauthResponseCommentLookup> comments = new ArrayList<>();
            
            if (preauthResponseId <= 0)
                throw new NullPointerException("PreauthResponseCommentLookupDAO::deactivateAllForCaseNumber received invalid preauthResponseId.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `preauthResponseId` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, preauthResponseId);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                comments.add(objectFromResultSet(rs));
            }
            
            return comments;
        }
        
	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthResponseCommentLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthResponseCommentLookup obj = new PreauthResponseCommentLookup();
		obj.setIdpreauthResponseCommentLookup(SQLUtil.getInteger(rs,"idpreauthResponseCommentLookup"));
		obj.setPreauthResponseId(SQLUtil.getInteger(rs,"preauthResponseId"));
		obj.setComments(rs.getString("comments"));
		obj.setId(rs.getString("id"));
		obj.setCreatedOn(rs.getString("createdOn"));

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="structureCheck()">
	@Override
	public String structureCheck() {
		String query = "SELECT `preauthResponseCommentLookup`.`idpreauthResponseCommentLookup`,"
			+"	`preauthResponseCommentLookup`.`preauthResponseId`,"
			+"	`preauthResponseCommentLookup`.`comments`,"
			+"	`preauthResponseCommentLookup`.`id`,"
			+"	`preauthResponseCommentLookup`.`createdOn`"
			+ "FROM `css`.`preauthResponseCommentLookup` LIMIT 1";
		return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
	}
	//</editor-fold>

}
