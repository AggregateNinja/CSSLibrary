package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PlaceOfService;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

public class PlaceOfServiceDAO implements IStructureCheckable
{
    public static enum Visibility
    {
        VISIBLE_ONLY,
        NOT_VISIBLE_ONLY,
        ALL
    }
    
    private static final String table = "`placeOfService`";

    public static PlaceOfService get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("PlaceOfServiceDAO::Get: Received a NULL or empty PlaceOfService object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idPlaceOfService = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        PlaceOfService obj = null;
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
    
    public static PlaceOfService getByName(String posName) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String query = "SELECT * FROM " + table + " WHERE name = ?";
        PreparedStatement pStmt = createStatement(con, query, posName);//con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
            return ObjectFromResultSet(rs);
        else
            return null;
    }
    
    public static List<PlaceOfService> get(Visibility visibility)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (visibility == null)
        {
            throw new IllegalArgumentException("PlaceOfServiceDAO::Get:"
                    + " Received a NULL or empty Visibility enum argument.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (visibility.equals(Visibility.VISIBLE_ONLY))
        {
            sql += " WHERE isUserVisible = 1";
        }
        
        if (visibility.equals(Visibility.NOT_VISIBLE_ONLY))
        {
            sql += " WHERE isUserVisible = 1";
        }

        sql += " ORDER BY idPlaceOfService ASC";
        
        PreparedStatement pStmt = con.prepareStatement(sql);

        List<PlaceOfService> placesOfService = new LinkedList<>();
        
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                placesOfService.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return placesOfService;
    }

    private static PlaceOfService ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        PlaceOfService obj = new PlaceOfService();
        obj.setIdPlaceOfService(rs.getInt("idPlaceOfService"));
        obj.setCode(rs.getString("code"));
        obj.setName(rs.getString("name"));
        obj.setIsUserVisible(rs.getInt("isUserVisible"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `placeOfService`.`idPlaceOfService`,\n"
                + "    `placeOfService`.`code`,\n"
                + "    `placeOfService`.`name`,\n"
                + "    `placeOfService`.`isUserVisible`\n"
                + "FROM `css`.`placeOfService` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
