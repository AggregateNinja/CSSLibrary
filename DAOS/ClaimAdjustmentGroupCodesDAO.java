package DAOS;

import Billing.Database.BillingDatabaseSingleton;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClaimAdjustmentGroupCodes;
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

public class ClaimAdjustmentGroupCodesDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`claimAdjustmentGroupCodes`";
        
        
	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ClaimAdjustmentGroupCodes)">
	public static ClaimAdjustmentGroupCodes insert(Connection con, ClaimAdjustmentGroupCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::Insert: Received a NULL ClaimAdjustmentGroupCodes object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  groupCode,"
			+ "  insuranceId,"
			+ "  description"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupCode());
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
				throw new NullPointerException("ClaimAdjustmentGroupCodesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdClaimAdjustmentGroupCodes(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ClaimAdjustmentGroupCodes)">
	public static ClaimAdjustmentGroupCodes insert(ClaimAdjustmentGroupCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::Insert: Received a NULL ClaimAdjustmentGroupCodes object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (Connection, ClaimAdjustmentGroupCodes)">
	public static void update(Connection con, ClaimAdjustmentGroupCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::Update: Received a NULL ClaimAdjustmentGroupCodes object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " groupCode = ?,"
			+ " insuranceId = ?,"
			+ " description = ?"
			+ " WHERE idClaimAdjustmentGroupCodes = " + obj.getIdClaimAdjustmentGroupCodes();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getGroupCode());
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

	//<editor-fold defaultstate="collapsed" desc="update (ClaimAdjustmentGroupCodes)">
	public static void update(ClaimAdjustmentGroupCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::Update: Received a NULL ClaimAdjustmentGroupCodes object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ClaimAdjustmentGroupCodes, boolean (forUpdate))">
	public static ClaimAdjustmentGroupCodes get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::get: Received a NULL or empty ClaimAdjustmentGroupCodes object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idClaimAdjustmentGroupCodes = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ClaimAdjustmentGroupCodes obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ClaimAdjustmentGroupCodes)">
	public static ClaimAdjustmentGroupCodes get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO::get: Received a NULL or empty ClaimAdjustmentGroupCodes object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static ClaimAdjustmentGroupCodes getByGroupCode(String groupCode) throws SQLException, IllegalArgumentException
        {
            if (groupCode == null || groupCode.isEmpty())
                throw new IllegalArgumentException("ClaimAdjustmentGroupCodesDAO received NULL or empty Group Code.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
                
            ClaimAdjustmentGroupCodes obj = null;
            
            String query = "SELECT * FROM " + table + " WHERE groupCode = ?";
            PreparedStatement stmt = createStatement(con, query, groupCode);//con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
                obj = objectFromResultSet(rs);
            
            stmt.close();
            
            return obj;
        }
        
	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ClaimAdjustmentGroupCodes objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ClaimAdjustmentGroupCodes obj = new ClaimAdjustmentGroupCodes();
		obj.setIdClaimAdjustmentGroupCodes(SQLUtil.getInteger(rs,"idClaimAdjustmentGroupCodes"));
		obj.setGroupCode(rs.getString("groupCode"));
		obj.setInsuranceId(SQLUtil.getInteger(rs,"insuranceId"));
		obj.setDescription(rs.getString("description"));

		return obj;
	}
	//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="getCode (int)">
        public ClaimAdjustmentGroupCodes getCode(int groupId) throws SQLException
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
                ClaimAdjustmentGroupCodes group = new ClaimAdjustmentGroupCodes();
                Statement stmt =con.createStatement();
                
                String query = "SELECT * from " + table + " WHERE idClaimAdjustmentGroupCodes = " + groupId;
                
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next())
                {
                    SetGroupFromResultSet(group, rs);
                }
                
                rs.close();
                stmt.close();
                return group;
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
                return null;
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="setGroupFromResultSet (ClaimAdjustmentGroupCodes, ResultSet)">
        public static ClaimAdjustmentGroupCodes SetGroupFromResultSet(ClaimAdjustmentGroupCodes group, ResultSet rs) throws SQLException
        {
            group.setIdClaimAdjustmentGroupCodes(rs.getInt("idClaimAdjustmentGroupCodes"));
            group.setGroupCode(rs.getString("groupCode"));
            group.setInsuranceId(rs.getInt("insuranceId"));
            group.setDescription(rs.getString("description"));
            
            return group;
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="SearchGroupByCode (String, boolean)">
        public List<ClaimAdjustmentGroupCodes> SearchGroupByCode(String CodeFragment, boolean CaseSensitive) {
        
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

        List<ClaimAdjustmentGroupCodes> groupList = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " ";
            if (CaseSensitive == false) {
                query += "WHERE (LOWER(`groupCode`) LIKE LOWER(?) "
                        + "OR LOWER(`groupCode`) LIKE LOWER(?)) ";
            } else {
                query += "WHERE (`groupCode` LIKE ? "
                        + "OR `groupCode` LIKE ?) ";
            }

            String searchParam = SQLUtil.createSearchParam(CodeFragment);
            stmt = createStatement(con, query, searchParam, searchParam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClaimAdjustmentGroupCodes group = new ClaimAdjustmentGroupCodes();
                groupList.add(SetGroupFromResultSet(group, rs));
            }

            rs.close();
            stmt.close();
            return groupList;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="getAllGroupCodes">
        public static List<ClaimAdjustmentGroupCodes> getAllGroupCodes()
        {
            List<ClaimAdjustmentGroupCodes> groupCodes = new ArrayList<>();
            
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
                    ClaimAdjustmentGroupCodes group = new ClaimAdjustmentGroupCodes();
                    groupCodes.add(SetGroupFromResultSet(group, rs));
                }
                
                rs.close();
                stmt.close();
                return groupCodes;
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
                return null;
            }
        }
        //</editor-fold>
   
    @Override
    public String structureCheck() {
        String query = "SELECT `claimAdjustmentGroupCodes`.`idClaimAdjustmentGroupCodes`,\n"
                + "    `claimAdjustmentGroupCodes`.`groupCode`,\n"
                + "    `claimAdjustmentGroupCodes`.`insuranceId`,\n"
                + "    `claimAdjustmentGroupCodes`.`description`\n"
                + "FROM `cssbilling`.`claimAdjustmentGroupCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }

}
