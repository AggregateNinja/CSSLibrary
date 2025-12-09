package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeSchedule;
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

public class FeeScheduleDAO implements IStructureCheckable
{

    private static final String table = "`feeSchedules`";

    public static FeeSchedule insert(FeeSchedule obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleDAO::Insert: Received a NULL FeeSchedule object");
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
                + "  description,"
                + "  quantifyLines"
                + ")"
                + "VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getQuantifyLines());

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
                throw new NullPointerException("FeeScheduleDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeSchedules(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(FeeSchedule obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleDAO::Update: Received a NULL FeeSchedule object.");
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
                + " description = ?,"
                + " quantifyLines = ?"
                + " WHERE idFeeSchedules = " + obj.getIdFeeSchedules();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbr());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getQuantifyLines());
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

    public static FeeSchedule get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleDAO::Get: Received a NULL or empty FeeSchedule object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeSchedules = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        FeeSchedule obj = null;
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

    public static FeeSchedule getByAbbreviation(String abbreviation)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (abbreviation == null || abbreviation.isEmpty())
        {
            return null;
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `abbr` = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, abbreviation);

        FeeSchedule obj = null;
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

    public static FeeSchedule getByName(String name)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (name == null || name.isEmpty())
        {
            return null;
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `name` = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);

        FeeSchedule obj = null;
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

    public static List<FeeSchedule> searchByNameFragment(String nameFragment)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        List<FeeSchedule> schedules = new LinkedList<>();

        if (nameFragment == null || nameFragment.isEmpty())
        {
            return schedules;
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        nameFragment = nameFragment.replace("%", "");
        nameFragment = "%" + nameFragment + "%";
        String sql = "SELECT * FROM " + table + " WHERE `name` like ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, nameFragment);

        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                schedules.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw new SQLException(ex.getMessage() + " " + pStmt.toString());
        }
        pStmt.close();
        return schedules;
    }

    private static FeeSchedule ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        FeeSchedule obj = new FeeSchedule();
        obj.setIdFeeSchedules(rs.getInt("idFeeSchedules"));
        obj.setAbbr(rs.getString("abbr"));
        obj.setName(rs.getString("name"));
        obj.setDescription(rs.getString("description"));
        obj.setQuantifyLines(rs.getBoolean("quantifyLines"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `feeSchedules`.`idFeeSchedules`,\n"
                + "    `feeSchedules`.`abbr`,\n"
                + "    `feeSchedules`.`name`,\n"
                + "    `feeSchedules`.`description`,\n"
                + "    `feeSchedules`.`quantifyLines`\n"
                + "FROM `css`.`feeSchedules` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
