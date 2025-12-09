package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TestRemarkRange;
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

public class TestRemarkRangeDAO implements IStructureCheckable
{

    private static final String table = "`testRemarkRanges`";

    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public static TestRemarkRange insert(TestRemarkRange obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("TestRemarkRangeDAO::Insert:"
                    + " Received a NULL TestRemarkRanges object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  testId,"
                + "  remarkId,"
                + "  rangeTypeId,"
                + "  rank,"
                + "  isActive"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRangeTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

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
                throw new NullPointerException("TestRemarkRangesDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdRemarkRanges(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(TestRemarkRange obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("TestRemarkRangesDAO::Update:"
                    + " Received a NULL TestRemarkRanges object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " testId = ?,"
                + " remarkId = ?,"
                + " rangeTypeId = ?,"
                + " rank = ?,"
                + " isActive = ?"
                + " WHERE idRemarkRanges = " + obj.getIdRemarkRanges();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRangeTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
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

    public static TestRemarkRange get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("TestRemarkRangesDAO::Get:"
                    + " Received a NULL or empty TestRemarkRanges object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idRemarkRanges = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        TestRemarkRange obj = null;
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

    public static Collection<TestRemarkRange> getByTestId(Integer testId, SearchType searchType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (testId == null || testId < 0)
        {
            throw new IllegalArgumentException("TestRemarkRangeDAO::getByTestId"
                    + " Received a [NULL] or invalid testId integer argument");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException("TestRemarkRangeDAO::getByTestId"
                    + " Received a [NULL] searchType object argument (enum)");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE testId = ?";

        if (searchType.equals(SearchType.ACTIVE_ONLY))
        {
            sql += " AND isActive = 1";
        }
        
        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " AND isActive = 0";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, testId);
        
        List<TestRemarkRange> testRemarkRanges = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                testRemarkRanges.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return testRemarkRanges;
    }
    
    
    public static TestRemarkRange ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        TestRemarkRange obj = new TestRemarkRange();
        obj.setIdRemarkRanges(rs.getInt("idRemarkRanges"));
        obj.setTestId(rs.getInt("testId"));
        obj.setRemarkId(rs.getInt("remarkId"));
        obj.setRangeTypeId(rs.getInt("rangeTypeId"));
        obj.setRank(rs.getInt("rank"));
        obj.setActive(rs.getBoolean("isActive"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `testRemarkRanges`.`idRemarkRanges`,\n"
                + "    `testRemarkRanges`.`testId`,\n"
                + "    `testRemarkRanges`.`remarkId`,\n"
                + "    `testRemarkRanges`.`rangeTypeId`,\n"
                + "    `testRemarkRanges`.`rank`,\n"
                + "    `testRemarkRanges`.`isActive`\n"
                + "FROM `css`.`testRemarkRanges` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
