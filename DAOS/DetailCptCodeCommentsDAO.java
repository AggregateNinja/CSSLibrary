package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailCptCodeComments;
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
import java.util.ArrayList;
import java.util.Date;


public class DetailCptCodeCommentsDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailCptCodeComments`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailCptCodeComments)">
	public static DetailCptCodeComments insert(Connection con, DetailCptCodeComments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO::Insert: Received a NULL DetailCptCodeComments object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  detailCptCodeId,"
			+ "  commentId"
			+ ")"
			+ "VALUES (?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentId());

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
				throw new NullPointerException("DetailCptCodeCommentsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdDetailCptCodeComments(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (DetailCptCodeComments)">
	public static DetailCptCodeComments insert(DetailCptCodeComments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO::Insert: Received a NULL DetailCptCodeComments object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, DetailCptCodeComments)">
	public static void update(Connection con, DetailCptCodeComments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCodeCommentsDAO::Update: Received a NULL DetailCptCodeComments object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " detailCptCodeId = ?,"
			+ " commentId = ?"
			+ " WHERE idDetailCptCodeComments = " + obj.getIdDetailCptCodeComments();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentId());
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

	//<editor-fold defaultstate="collapsed" desc="update (DetailCptCodeComments)">
	public static void update(DetailCptCodeComments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCodeCommentsDAO::Update: Received a NULL DetailCptCodeComments object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, DetailCptCodeComments, boolean (forUpdate))">
	public static DetailCptCodeComments get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO::get: Received a NULL or empty DetailCptCodeComments object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idDetailCptCodeComments = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		DetailCptCodeComments obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (DetailCptCodeComments)">
	public static DetailCptCodeComments get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCodeCommentsDAO::get: Received a NULL or empty DetailCptCodeComments object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        // getAllForDetail
        public static ArrayList<DetailCptCodeComments> getAllCommentsForDetail (int idDetailCptCode)
        {
            ArrayList<DetailCptCodeComments> comments = new ArrayList<>();
            
            try
            {
                Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
                Connection con = dbs.getConnection(true);
                if (!con.isValid(2)) con = CheckDBConnection.Check(dbs, con);
                
                String sql = "SELECT * FROM " + table + " WHERE detailCptCodeId = " + String.valueOf(idDetailCptCode);
                PreparedStatement pStmt = con.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery();
                
                while (rs.next())
                {
                    comments.add(objectFromResultSet(rs));
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
            }
            
            return comments;
        }

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static DetailCptCodeComments objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		DetailCptCodeComments obj = new DetailCptCodeComments();
		obj.setIdDetailCptCodeComments(SQLUtil.getInteger(rs,"idDetailCptCodeComments"));
		obj.setDetailCptCodeId(SQLUtil.getInteger(rs,"detailCptCodeId"));
		obj.setCommentId(SQLUtil.getInteger(rs,"commentId"));

		return obj;
	}
	//</editor-fold>
        
    @Override
    public String structureCheck() {
        String query = "SELECT `detailCptCodeComments`.`idDetailCptCodeComments`,\n"
                + "    `detailCptCodeComments`.`detailCptCodeId`,\n"
                + "    `detailCptCodeComments`.`commentId`\n"
                + "FROM `cssbilling`.`detailCptCodeComments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
