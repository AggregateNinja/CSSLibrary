package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailInsuranceItemAdjustments;
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
import java.util.Collection;


public class DetailInsuranceItemAdjustmentsDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailInsuranceItemAdjustments`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailInsuranceItemAdjustments)">
	public static DetailInsuranceItemAdjustments insert(Connection con, DetailInsuranceItemAdjustments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::Insert: Received a NULL DetailInsuranceItemAdjustments object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  detailInsuranceItemId,"
			+ "  claimAdjustmentGroupCodeId,"
			+ "  claimAdjustmentReasonCodeId,"
			+ "  remittanceAdviceRemarkCodeId"
			+ ")"
			+ "VALUES (?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceItemId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClaimAdjustmentGroupCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClaimAdjustmentReasonCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemittanceAdviceRemarkCodeId());

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
				throw new NullPointerException("DetailInsuranceItemAdjustmentsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdDetailInsuranceItemAdjustments(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (DetailInsuranceItemAdjustments)">
	public static DetailInsuranceItemAdjustments insert(DetailInsuranceItemAdjustments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::Insert: Received a NULL DetailInsuranceItemAdjustments object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, DetailInsuranceItemAdjustments)">
	public static void update(Connection con, DetailInsuranceItemAdjustments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::Update: Received a NULL DetailInsuranceItemAdjustments object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " detailInsuranceItemId = ?,"
			+ " claimAdjustmentGroupCodeId = ?,"
			+ " claimAdjustmentReasonCodeId = ?,"
			+ " remittanceAdviceRemarkCodeId = ?"
			+ " WHERE idDetailInsuranceItemAdjustments = " + obj.getIdDetailInsuranceItemAdjustments();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceItemId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClaimAdjustmentGroupCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClaimAdjustmentReasonCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemittanceAdviceRemarkCodeId());
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

	//<editor-fold defaultstate="collapsed" desc="update (DetailInsuranceItemAdjustments)">
	public static void update(DetailInsuranceItemAdjustments obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::Update: Received a NULL DetailInsuranceItemAdjustments object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, DetailInsuranceItemAdjustments, boolean (forUpdate))">
	public static DetailInsuranceItemAdjustments get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty DetailInsuranceItemAdjustments object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idDetailInsuranceItemAdjustments = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		DetailInsuranceItemAdjustments obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (DetailInsuranceItemAdjustments)">
	public static DetailInsuranceItemAdjustments get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty DetailInsuranceItemAdjustments object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="getAllForDetailInsuranceItemId (detailInsuranceItemId)">
	public static Collection<DetailInsuranceItemAdjustments> getAllForDetailInsuranceItemId(Integer detailInsuranceItemId) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (detailInsuranceItemId == null || detailInsuranceItemId <= 0)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty detailInsuranceItemId object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		String sql = "SELECT * FROM " + table + " WHERE detailInsuranceItemId = " + String.valueOf(detailInsuranceItemId);

                Collection<DetailInsuranceItemAdjustments> objList = new ArrayList<DetailInsuranceItemAdjustments>();
		DetailInsuranceItemAdjustments obj = null;

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{

			ResultSet rs = pStmt.executeQuery();
			while (rs.next())
			{
				obj = objectFromResultSet(rs);
                                objList.add(obj);
			}
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw new SQLException(ex.getMessage() + " " + sql);
		}

		return objList;
	}
	//</editor-fold>
        
	//<editor-fold defaultstate="collapsed" desc="getAllForDetailInsuranceItemId (detailInsuranceItemId)">
	public static Collection<DetailInsuranceItemAdjustments> getAllForDetailInsuranceItemId(Connection con, Integer detailInsuranceItemId) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (detailInsuranceItemId == null || detailInsuranceItemId <= 0)
		{
			throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO::get: Received a NULL or empty detailInsuranceItemId object.");
		}
                
		String sql = "SELECT * FROM " + table + " WHERE detailInsuranceItemId = " + String.valueOf(detailInsuranceItemId);

                Collection<DetailInsuranceItemAdjustments> objList = new ArrayList<DetailInsuranceItemAdjustments>();
		DetailInsuranceItemAdjustments obj = null;

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{

			ResultSet rs = pStmt.executeQuery();
			while (rs.next())
			{
				obj = objectFromResultSet(rs);
                                objList.add(obj);
			}
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw new SQLException(ex.getMessage() + " " + sql);
		}

		return objList;
	}
	//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="deleteForDetailInsuranceItemId (idDetailInsuranceItemAdjustments)">
        public static void deleteForDetailInsuranceItemId (Integer idDetailInsuranceItemAdjustments)
                throws SQLException, IllegalArgumentException
        {
            if (idDetailInsuranceItemAdjustments == null || idDetailInsuranceItemAdjustments <= 0)
                throw new IllegalArgumentException("DetailInsuranceItemAdjustmentsDAO received NULL or empty detailInsuranceItemId.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "DELETE FROM " + table + " WHERE idDetailInsuranceItemAdjustments = " + idDetailInsuranceItemAdjustments;
            Statement stmt = con.prepareStatement(query);
            stmt.executeUpdate(query);
            
            stmt.close();
        }
        //</editor-fold>
        
	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static DetailInsuranceItemAdjustments objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		DetailInsuranceItemAdjustments obj = new DetailInsuranceItemAdjustments();
		obj.setIdDetailInsuranceItemAdjustments(SQLUtil.getInteger(rs,"idDetailInsuranceItemAdjustments"));
		obj.setDetailInsuranceItemId(SQLUtil.getInteger(rs,"detailInsuranceItemId"));
		obj.setClaimAdjustmentGroupCodeId(SQLUtil.getInteger(rs,"claimAdjustmentGroupCodeId"));
		obj.setClaimAdjustmentReasonCodeId(SQLUtil.getInteger(rs,"claimAdjustmentReasonCodeId"));
		obj.setRemittanceAdviceRemarkCodeId(SQLUtil.getInteger(rs,"remittanceAdviceRemarkCodeId"));

		return obj;
	}
	//</editor-fold>

        
    @Override
    public String structureCheck() {
        String query = "SELECT `detailInsuranceItemAdjustments`.`idDetailInsuranceItemAdjustments`,\n"
                + "    `detailInsuranceItemAdjustments`.`detailInsuranceItemId`,\n"
                + "    `detailInsuranceItemAdjustments`.`claimAdjustmentGroupCodeId`,\n"
                + "    `detailInsuranceItemAdjustments`.`claimAdjustmentReasonCodeId`,\n"
                + "    `detailInsuranceItemAdjustments`.`remittanceAdviceRemarkCodeId`\n"
                + "FROM `cssbilling`.`detailInsuranceItemAdjustments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
