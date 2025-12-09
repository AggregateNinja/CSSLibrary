package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeScheduleAction;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FeeScheduleActionDAO implements IStructureCheckable
{

    private static final String table = "`feeScheduleActions`";

    public static FeeScheduleAction insert(FeeScheduleAction obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::Insert: Received a NULL FeeScheduleActions object");
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
                + "  systemName,"
                + "  sortOrder,"
                + "  actionTypeName"
                + ")"
                + "VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSortOrder());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getActionTypeName());

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
                throw new NullPointerException("FeeScheduleActionsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeScheduleActions(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(FeeScheduleAction obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::Update: Received a NULL FeeScheduleActions object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " systemName = ?,"
                + " sortOrder = ?,"
                + " actionTypeName = ?"
                + " WHERE idFeeScheduleActions = " + obj.getIdFeeScheduleActions();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSortOrder());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getActionTypeName());
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

    public static FeeScheduleAction get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::Get: Received a NULL or empty FeeScheduleActions object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeScheduleActions = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        FeeScheduleAction obj = null;
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

    public static FeeScheduleAction getBySystemNameTypeName(FeeScheduleAction.Action action, FeeScheduleAction.TypeName typeName)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (action == null)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::getBySystemNameTypeName: Received a NULL or empty FeeScheduleAction.Action object.");
        }

        if (typeName == null)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::getBySystemNameTypeName: Received a NULL or empty FeeScheduleAction.TypeName object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE systemName = ? AND actionTypeName = ?";

        FeeScheduleAction obj = null;
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            pStmt.setString(1, action.getSystemName());
            pStmt.setString(2, typeName.getTypeName());
            
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
        
        if (obj == null || obj.getIdFeeScheduleActions() == null)
        {
            throw new SQLException("FeeScheduleActionDAO::getBySystemNameTypeName:"
                    + " Could not find a row in the feeScheduleActions table to match"
                    + " provided arguments: FeeScheduleAction system name: " + action.getSystemName() + " Fee Schedule Action type name: " + typeName.getTypeName());
        }
        
        return obj;
    }

    public static List<FeeScheduleAction> getAllByTypeName(FeeScheduleAction.TypeName typeName)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (typeName == null)
        {
            throw new IllegalArgumentException("FeeScheduleActionsDAO::getAllByTypeName: Received a NULL or empty FeeScheduleAction.TypeName object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE actionTypeName = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, typeName.getTypeName());
        List<FeeScheduleAction> actions = new LinkedList<>();
        FeeScheduleAction obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                actions.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return actions;
    }

    public static List<FeeScheduleAction> getAll()
            throws SQLException, IllegalArgumentException, NullPointerException
    {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;

        PreparedStatement pStmt = con.prepareStatement(sql);
        List<FeeScheduleAction> actions = new LinkedList<>();
        FeeScheduleAction obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                actions.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return actions;
    }

    private static FeeScheduleAction ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        FeeScheduleAction obj = new FeeScheduleAction();
        obj.setIdFeeScheduleActions(rs.getInt("idFeeScheduleActions"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setSortOrder(rs.getInt("sortOrder"));
        obj.setActionTypeName(rs.getString("actionTypeName"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `feeScheduleActions`.`idFeeScheduleActions`,\n"
                + "    `feeScheduleActions`.`name`,\n"
                + "    `feeScheduleActions`.`systemName`,\n"
                + "    `feeScheduleActions`.`sortOrder`,\n"
                + "    `feeScheduleActions`.`actionTypeName`\n"
                + "FROM `css`.`feeScheduleActions` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
