package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.State;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StateDAO implements IStructureCheckable
{

    public static final String table = "`states`";

    public static State insert(State state) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (state == null)
        {
            throw new IllegalArgumentException("StateDAO::Insert: Received a NULL State object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  name,"
                + "  abbr,"
                + "  active"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, state.getName());
        SQLUtil.SafeSetString(pStmt, ++i, state.getAbbr());
        SQLUtil.SafeSetInteger(pStmt, ++i, state.getActive());

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
                throw new NullPointerException("StateDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            state.setIdStates(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return state;
    }

    public static void update(State state) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (state == null)
        {
            throw new IllegalArgumentException("StateDAO::Update: Received a NULL State object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " abbr = ?,"
                + " active = ?"
                + " WHERE idStates = " + state.getIdStates();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, state.getName());
            SQLUtil.SafeSetString(pStmt, ++i, state.getAbbr());
            SQLUtil.SafeSetInteger(pStmt, ++i, state.getActive());
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

    public static State get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("StateDAO::get: Received a NULL or empty integer identifier");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idStates = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        State state = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                state = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return state;
    }
    
    public static State getByAbbr(String abbr)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (abbr == null || abbr.isEmpty())
        {
            throw new IllegalArgumentException("StateDAO::getByAbbr: Received a NULL or empty abbr object.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE abbr = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, abbr);
        State state = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                state = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw new SQLException(ex.getMessage() + " " + pStmt.toString());
        }
        pStmt.close();
        return state;
    }

    public static State ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        State state = new State();
        state.setIdStates(rs.getInt("idStates"));
        state.setName(rs.getString("name"));
        state.setAbbr(rs.getString("abbr"));
        state.setActive(rs.getInt("active"));

        return state;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `states`.`idStates`,\n"
                + "    `states`.`name`,\n"
                + "    `states`.`abbr`,\n"
                + "    `states`.`active`\n"
                + "FROM `css`.`states` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
