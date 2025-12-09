package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ServiceType;
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

public class ServiceTypeDAO implements IStructureCheckable
{

    private static final String table = "`serviceTypes`";

    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public static ServiceType insert(ServiceType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("ServiceTypesDAO::Insert: Received a NULL ServiceTypes object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  abbr,"
                + "  name,"
                + "  systemName,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
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
                throw new NullPointerException("ServiceTypeDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdServiceTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(ServiceType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("ServiceTypeDAO::Update: Received a NULL ServiceTypes object.");
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
                + " active = ?"
                + " WHERE idServiceTypes = " + obj.getIdServiceTypes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
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

    public static ServiceType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("ServiceTypeDAO::Get:"
                    + " Received a NULL or empty ServiceTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idServiceTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        ServiceType obj = null;
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
    
    public static Collection<ServiceType> get(SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException("ServiceTypeDAO::Get:"
                    + " Received a NULL or empty SearchType object.");
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

        List<ServiceType> serviceTypes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                serviceTypes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return serviceTypes;
    }

    private static ServiceType ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        ServiceType obj = new ServiceType();
        obj.setIdServiceTypes(rs.getInt("idServiceTypes"));
        obj.setAbbr(rs.getString("abbr"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `serviceTypes`.`idServiceTypes`,\n"
                + "    `serviceTypes`.`abbr`,\n"
                + "    `serviceTypes`.`name`,\n"
                + "    `serviceTypes`.`systemName`,\n"
                + "    `serviceTypes`.`active`\n"
                + "FROM `css`.`serviceTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
