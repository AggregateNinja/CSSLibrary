package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RemarkCategory;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemarkCategoryDAO implements IStructureCheckable
{

    private static final String table = "`remarkCategories`";

    public static RemarkCategory insert(RemarkCategory obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemarkCategoryDAO::Insert:"
                    + " Received a NULL RemarkCategories object");
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
                + "  systemName"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());

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
                throw new NullPointerException("RemarkCategoriesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdremarkCategories(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(RemarkCategory obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("RemarkCategoriesDAO::Update:"
                    + " Received a NULL RemarkCategories object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " systemName = ?"
                + " WHERE idremarkCategories = " + obj.getIdremarkCategories();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
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

    public static RemarkCategory get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RemarkCategoriesDAO::Get:"
                    + " Received a NULL or empty RemarkCategories object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idremarkCategories = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        RemarkCategory obj = null;
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
    
    public static RemarkCategory getBySystemName(String systemName)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (systemName == null || systemName.isEmpty())
        {
            throw new IllegalArgumentException("RemarkCategoriesDAO::getBySystemName:"
                    + " Received a NULL or empty systemName string argument");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE systemName = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, systemName);
        RemarkCategory obj = null;
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

    public static RemarkCategory ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        RemarkCategory obj = new RemarkCategory();
        obj.setIdremarkCategories(rs.getInt("idremarkCategories"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `remarkCategories`.`idremarkCategories`,\n"
                + "    `remarkCategories`.`name`,\n"
                + "    `remarkCategories`.`systemName`\n"
                + "FROM `css`.`remarkCategories` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
