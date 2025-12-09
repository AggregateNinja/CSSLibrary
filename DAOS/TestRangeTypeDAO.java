package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TestRangeType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TestRangeTypeDAO implements IStructureCheckable
{

    private static final String table = "`testRangeTypes`";

    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public static TestRangeType insert(TestRangeType obj) 
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("TestRangeTypesDAO::Insert: Received a NULL TestRangeTypes object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  rangeNumber,"
                + "  rangeName,"
                + "  systemName,"
                + "  isActive"
                + ")"
                + "VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRangeNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getRangeName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isIsActive());

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
                throw new NullPointerException("TestRangeTypesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdRangeTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(TestRangeType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("TestRangeTypesDAO::Update: Received a NULL TestRangeTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " rangeNumber = ?,"
                + " rangeName = ?,"
                + " systemName = ?,"
                + " isActive = ?"
                + " WHERE idRangeTypes = " + obj.getIdRangeTypes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRangeNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getRangeName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isIsActive());
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

    public static TestRangeType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("TestRangeTypesDAO::Get: Received a NULL or empty TestRangeTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idRangeTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        TestRangeType obj = null;
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

        return obj;
    }
    
    public static Collection<TestRangeType> get(SearchType searchType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException("TestRangeTypeDAO::get:"
                    + " Received a [NULL] SearchType argument (enum)");
        }
        

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (searchType.equals(SearchType.ACTIVE_ONLY))
        {
            sql += " WHERE isActive = 1";
        }

        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " WHERE isActive = 0";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);

        List<TestRangeType> testRangeTypes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                testRangeTypes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return testRangeTypes;
    }
    
    public static TestRangeType ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        TestRangeType obj = new TestRangeType();
        obj.setIdRangeTypes(rs.getInt("idRangeTypes"));
        obj.setRangeNumber(rs.getInt("rangeNumber"));
        obj.setRangeName(rs.getString("rangeName"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setIsActive(rs.getBoolean("isActive"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `testRangeTypes`.`idRangeTypes`,\n"
                + "    `testRangeTypes`.`rangeNumber`,\n"
                + "    `testRangeTypes`.`rangeName`,\n"
                + "    `testRangeTypes`.`systemName`,\n"
                + "    `testRangeTypes`.`isActive`\n"
                + "FROM `css`.`testRangeTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
