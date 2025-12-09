package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SpecialPriceTests;
import DOS.Tests;
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


public class SpecialPriceTestDAO implements IStructureCheckable
{
	public static final String table = "`specialPriceTests`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,SpecialPriceTests)">
	public static SpecialPriceTests insert(Connection con, SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO::Insert: Received a NULL SpecialPriceTests object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  clientId,"
			+ "  testNumber,"
			+ "  cost"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getCost());

			sql = pStmt.toString();
			pStmt.executeUpdate();
                        
                        ResultSet rs = pStmt.getGeneratedKeys();
                        
                        if (rs.next())
                            obj.setIdspecialPriceTests(rs.getInt(1));
                }
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (SpecialPriceTests)">
	public static SpecialPriceTests insert(SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO::Insert: Received a NULL SpecialPriceTests object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, SpecialPriceTests)">
	public static void update(Connection con, SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SpecialPriceTestDAO::Update: Received a NULL SpecialPriceTests object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " idspecialPriceTests = ?,"
			+ " clientId = ?,"
			+ " testNumber = ?,"
			+ " cost = ?"
			+ " WHERE idSpecialPriceTests = " + obj.getIdspecialPriceTests();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdspecialPriceTests());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getCost());
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

	//<editor-fold defaultstate="collapsed" desc="update (SpecialPriceTests)">
	public static void update(SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SpecialPriceTestDAO::Update: Received a NULL SpecialPriceTests object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="delete (Connection, SpecialPriceTests)">
        public static void delete(Connection con, SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
        {
            if (obj == null) throw new IllegalArgumentException("SpecialPriceTestDAO::Delete: Received a NULL SpecialPriceTests object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO:Delete: Received a [NULL] or invalid Connection object");
		}

		String sql = "DELETE FROM " + table + " WHERE "
			+ " idspecialPriceTests = ?";

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdspecialPriceTests());
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
        
        // <editor-fold defaultstate="collapsed" desc="delete (SpecialPriceTests)">
        public static void delete(SpecialPriceTests obj) throws SQLException, IllegalArgumentException, NullPointerException
        {
            if (obj == null) throw new IllegalArgumentException("SpecialPriceTestDAO::Delete: Received a NULL SpecialPriceTests object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		delete(con, obj);
        }
        //</editor-fold>
        
	//<editor-fold defaultstate="collapsed" desc="get (Connection, SpecialPriceTests, boolean (forUpdate))">
	public static SpecialPriceTests get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO::get: Received a NULL or empty SpecialPriceTests object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE  = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		SpecialPriceTests obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (SpecialPriceTests)">
	public static SpecialPriceTests get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SpecialPriceTestDAO::get: Received a NULL or empty SpecialPriceTests object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="getByClientIdAndTestNumber(int clientId, int testNumber)">
        public static SpecialPriceTests getByClientIdAndTestNumber(int clientId, int testNumber) throws SQLException
        {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM "  + table + " WHERE clientId = " +
                    clientId + " AND testNumber = " + testNumber + "";
            Statement stmt = con.prepareStatement(query);
            
            try(ResultSet rs = stmt.executeQuery(query))
            {
                if (rs.next())
                    return objectFromResultSet(rs);
                else
                    return null;
            }
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static SpecialPriceTests objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		SpecialPriceTests obj = new SpecialPriceTests();
		obj.setIdspecialPriceTests(SQLUtil.getInteger(rs,"idspecialPriceTests"));
		obj.setClientId(SQLUtil.getInteger(rs,"clientId"));
		obj.setTestNumber(SQLUtil.getInteger(rs,"testNumber"));
		obj.setCost(rs.getBigDecimal("cost"));

		return obj;
	}
	//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="deleteAllSpecialTestsForClientId(int clientId)">
        public static boolean deleteAllSpecialTestsForClientId(int clientId) throws SQLException
        {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            try
            {
                String sql = "DELETE FROM " + table + " WHERE clientId = " + clientId;
                Statement stmt = con.prepareStatement(sql);
                stmt.executeUpdate(sql);
                return true;
            }
            catch (SQLException ex)
            {
                System.out.println("SpecialPriceTestDAO.java line 215: Error " +
                    "deleting special tests for clientId - " + clientId + ". " +
                    ex.getMessage());
                return false;
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="getAllSpecialTestsForClientId(int clientId)">
        public static List<Tests> getAllSpecialTestsForClientId(int clientId) throws SQLException
        {
            List<Tests> tests = new ArrayList<>();
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String sql = "SELECT * FROM " + table + " WHERE clientId = " + clientId;
            try(Statement stmt = con.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery(sql);
                TestDAO tdao = new TestDAO();
                
                while (rs.next())
                {
                    Integer testNumber = rs.getInt("testNumber");
                    if (testNumber == 0) throw new SQLException("SpecialPriceTestDAO::getAllSpecialTestsForClientId:"
                        + " returned test number was [NULL] for clientId " + clientId);
                    
                    Tests t = tdao.GetTestByNumber(testNumber);
                    if (t == null || t.getIdtests() == null || t.getIdtests().equals(0))
                    {
                        throw new SQLException("SpecialPriceTestDAO::getAllSpecialTestsForClientId:"
                                + " retrieved [NULL] or empty test object when loading test number " + testNumber + " for clientId " + clientId);
                    }
                    tests.add(t);
                }
            }
            return tests;
        }
        //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `specialPriceTests`.`idspecialPriceTests`,\n"
                + "    `specialPriceTests`.`clientId`,\n"
                + "    `specialPriceTests`.`testNumber`,\n"
                + "    `specialPriceTests`.`cost`\n"
                + "FROM `css`.`specialPriceTests` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
