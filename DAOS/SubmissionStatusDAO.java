package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubmissionStatus;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
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

public class SubmissionStatusDAO implements IStructureCheckable
{
    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`submissionStatus`";

    public enum SubmissionStatuses
    {
        UNSENT("unsent"),
        SENT("sent"),
        RECEIVED_REJECTED("rejected"),
        RECEIVED_UNAPPLIED("unapplied"),
        RECEIVED_APPLIED("applied");
        
        private final String systemName;
        
        @Override
        public String toString()
        {
            return systemName;
        }
        
        SubmissionStatuses(String systemName)
        {
            this.systemName = systemName;
        }
    }
    
    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public static SubmissionStatus insert(SubmissionStatus obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::Insert:"
                    + " Received a NULL SubmissionStatus object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + " ("
                + "  abbr,"
                + "  name,"
                + "  systemName,"
                + "  canSend,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCanSend());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getActive());

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
                throw new NullPointerException("SubmissionStatusDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSubmissionStatus(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(SubmissionStatus obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::Update: Received a NULL SubmissionStatus object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " abbr = ?,"
                + " name = ?,"
                + " systemName = ?,"
                + " canSend = ?,"
                + " active = ?"
                + " WHERE idSubmissionStatus = " + obj.getIdSubmissionStatus();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCanSend());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getActive());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }

    public static SubmissionStatus get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::Get: Received a NULL or empty SubmissionStatus object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idSubmissionStatus = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        SubmissionStatus obj = null;
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
        pStmt.close();
        return obj;
    }
    
    public static Collection<SubmissionStatus> get(SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::Get:"
                    + " Received a NULL SearchType object.");
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
            sql += " WHERE active = 1";
        }

        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " WHERE active = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<SubmissionStatus> submissionStatuses = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                submissionStatuses.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return submissionStatuses;
    }
    
    public static SubmissionStatus get(SubmissionStatuses status, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::getBySystemName:"
                    + " Received a [NULL] SearchType argument.");
        }        
        
        if (status == null)
        {
            throw new IllegalArgumentException("SubmissionStatusDAO::getBySystemName:"
                    + " Received a [NULL] SubmissionStatuses enum.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE systemName = ?";

        if (searchType.equals(SearchType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        
        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, status.systemName);
        
        SubmissionStatus obj = null;
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
        pStmt.close();
        return obj;
    }
    
    private static SubmissionStatus ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        SubmissionStatus obj = new SubmissionStatus();
        obj.setIdSubmissionStatus(rs.getInt("idSubmissionStatus"));
        obj.setAbbr(rs.getString("abbr"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setCanSend(rs.getInt("canSend"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `submissionStatus`.`idSubmissionStatus`,\n"
                + "    `submissionStatus`.`abbr`,\n"
                + "    `submissionStatus`.`name`,\n"
                + "    `submissionStatus`.`systemName`,\n"
                + "    `submissionStatus`.`canSend`,\n"
                + "    `submissionStatus`.`active`\n"
                + "FROM `cssbilling`.`submissionStatus` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
