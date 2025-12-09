package DAOS;

import Billing.Database.BillingDatabaseSingleton;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClaimAdjustmentReasonCodes;
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
import java.util.List;

import static Utility.SQLUtil.createStatement;

public class ClaimAdjustmentReasonCodesDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`claimAdjustmentReasonCodes`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ClaimAdjustmentReasonCodes)">
	public static ClaimAdjustmentReasonCodes insert(Connection con, ClaimAdjustmentReasonCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::Insert: Received a NULL ClaimAdjustmentReasonCodes object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  reasonCode,"
			+ "  insuranceId,"
			+ "  description"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getReasonCode());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());

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
				throw new NullPointerException("ClaimAdjustmentReasonCodesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdClaimAdjustmentReasonCodes(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ClaimAdjustmentReasonCodes)">
	public static ClaimAdjustmentReasonCodes insert(ClaimAdjustmentReasonCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::Insert: Received a NULL ClaimAdjustmentReasonCodes object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (Connection, ClaimAdjustmentReasonCodes)">
	public static void update(Connection con, ClaimAdjustmentReasonCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::Update: Received a NULL ClaimAdjustmentReasonCodes object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " reasonCode = ?,"
			+ " insuranceId = ?,"
			+ " description = ?"
			+ " WHERE idClaimAdjustmentReasonCodes = " + obj.getIdClaimAdjustmentReasonCodes();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getReasonCode());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
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

	//<editor-fold defaultstate="collapsed" desc="update (ClaimAdjustmentReasonCodes)">
	public static void update(ClaimAdjustmentReasonCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::Update: Received a NULL ClaimAdjustmentReasonCodes object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ClaimAdjustmentReasonCodes, boolean (forUpdate))">
	public static ClaimAdjustmentReasonCodes get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::get: Received a NULL or empty ClaimAdjustmentReasonCodes object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idClaimAdjustmentReasonCodes = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ClaimAdjustmentReasonCodes obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ClaimAdjustmentReasonCodes)">
	public static ClaimAdjustmentReasonCodes get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ClaimAdjustmentReasonCodesDAO::get: Received a NULL or empty ClaimAdjustmentReasonCodes object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static ClaimAdjustmentReasonCodes getByReasonCode (String reasonCode)
        {
            ClaimAdjustmentReasonCodes obj = null;
            
            try
            {
                Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
                Connection con = dbs.getConnection(true);
                if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con); 
                
                String sql = "SELECT * FROM " + table + " WHERE reasonCode = ?";
                PreparedStatement pStmt = createStatement(con, sql, reasonCode);//con.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery();
                    if (rs.next())
                    {
                            obj = objectFromResultSet(rs);
                    }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
            }
            
            return obj;
        }
        
	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ClaimAdjustmentReasonCodes objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ClaimAdjustmentReasonCodes obj = new ClaimAdjustmentReasonCodes();
		obj.setIdClaimAdjustmentReasonCodes(SQLUtil.getInteger(rs,"idClaimAdjustmentReasonCodes"));
		obj.setReasonCode(rs.getString("reasonCode"));
		obj.setInsuranceId(SQLUtil.getInteger(rs,"insuranceId"));
		obj.setDescription(rs.getString("description"));

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="getCode (int)">
        public ClaimAdjustmentReasonCodes getCode(int reasonId) throws SQLException
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
                ClaimAdjustmentReasonCodes reason = new ClaimAdjustmentReasonCodes();
                Statement stmt = con.createStatement();
                
                String query = "SELECT * from " + table + " WHERE idClaimAdjustmentReasonCodes = " + reasonId;
                
                ResultSet rs = stmt.executeQuery(query);
                
                if(rs.next())
                {
                    SetReasonFromResultSet(reason, rs);
                }
                
                rs.close();
                stmt.close();
                return reason;
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
                return null;
            }
        }
        //</editor-fold>
        
	//<editor-fold defaultstate="collapsed" desc="SetReasonFromResultSet (ClaimAdjustmentReasonCodes, ResultSet)">
        public static ClaimAdjustmentReasonCodes SetReasonFromResultSet(ClaimAdjustmentReasonCodes reason, ResultSet rs) throws SQLException
        {
            reason.setIdClaimAdjustmentReasonCodes(rs.getInt("idClaimAdjustmentReasonCodes"));
            reason.setReasonCode(rs.getString("reasonCode"));
            reason.setInsuranceId(rs.getInt("insuranceId"));
            reason.setDescription(rs.getString("description"));
            return reason;
        }
        //</editor-fold>
        
	//<editor-fold defaultstate="collapsed" desc="SearchReasonByCode (String,boolean)">
        public List<ClaimAdjustmentReasonCodes> SearchReasonByCode(String CodeFragment, boolean CaseSensitive)
        {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            
            try 
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(BillingDatabaseSingleton.ref, con);
                }
            } catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
                
            List<ClaimAdjustmentReasonCodes> reasonList = new ArrayList<>();
            try
            {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                
                //stmt = con.createStatement();
                
                String query = "SELECT * FROM " + table + " ";
                if(CaseSensitive == false)
                {
                    query += "WHERE (LOWER(`reasonCode`) LIKE LOWER(?) "
                            + "OR LOWER(`reasonCode`) LIKE LOWER(?)) ";
                }
                else 
                {
                query += "WHERE (`groupCode` LIKE ? "
                        + "OR `groupCode` LIKE ?) ";
                }
                
                String searchParam = SQLUtil.createSearchParam(CodeFragment);
                stmt = createStatement(con, query, searchParam, searchParam);
                rs = stmt.executeQuery();
                
                while(rs.next())
                {
                    ClaimAdjustmentReasonCodes reason = new ClaimAdjustmentReasonCodes();
                    reasonList.add(SetReasonFromResultSet(reason, rs));
                }
                
                rs.close();
                stmt.close();
                return reasonList;
            }
            catch(SQLException ex)
            {
                System.out.println(ex.toString());
                return null;
            }
        }
        //</editor-fold>
        
        public static List<ClaimAdjustmentReasonCodes> getAllReasonCodes()
        {
            List<ClaimAdjustmentReasonCodes> reasonCodes = new ArrayList<>();
            
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
                Statement stmt =con.createStatement();
                
                String query = "SELECT * from " + table;
                
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next())
                {
                    ClaimAdjustmentReasonCodes reason = new ClaimAdjustmentReasonCodes();
                    reasonCodes.add(SetReasonFromResultSet(reason, rs));
                }
                
                rs.close();
                stmt.close();
                return reasonCodes;
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
                return null;
            }
        }
        
    @Override
    public String structureCheck() {
        String query = "SELECT `claimAdjustmentReasonCodes`.`idClaimAdjustmentReasonCodes`,\n"
                + "    `claimAdjustmentReasonCodes`.`reasonCode`,\n"
                + "    `claimAdjustmentReasonCodes`.`insuranceId`,\n"
                + "    `claimAdjustmentReasonCodes`.`description`\n"
                + "FROM `cssbilling`.`claimAdjustmentReasonCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
