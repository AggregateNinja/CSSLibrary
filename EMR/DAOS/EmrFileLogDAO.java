package EMR.DAOS;

import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.EmrFileLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import EMR.DOS.EmrFile;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmrFileLogDAO implements IStructureCheckable
{
    private static final String table = "`emrorders`.`emrFileLog`";

    public static EmrFileLog insert(EmrFileLog obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EmrFileLogDAO::Insert: Received a NULL EmrFileLog object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  fileName,"
                + "  idOrders,"
                + "  emrOrderId,"
                + "  description,"
                + "  created,"
                + "  emrInterface,"
                + "  controlId,"
                + "  errorAcknowledged,"
                + "  acknowledgedById,"
                + "  acknowledgedDate"
                + ")"
                + "VALUES (?,?,?,?,NOW(),?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getFileName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getEmrOrderId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdOrders());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEmrInterface());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getControlId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isErrorAcknowledged());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAcknowledgedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getAcknowledgedDate());
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
                throw new NullPointerException("EmrFileLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setId(newId);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(EmrFileLog obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EmrFileLogDAO::Update: Received a NULL EmrFileLog object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " fileName = ?,"
                + " emrOrderId = ?,"
                + " description = ?,"
                + " idOrders = ?,"
                + " emrInterface = ?,"
                + " controlId = ?,"
                + " errorAcknowledged = ?,"
                + " acknowledgedById = ?,"
                + " acknowledgedDate = ?"
                + " WHERE id = " + obj.getId();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getFileName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getEmrOrderId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEmrInterface());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getControlId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isErrorAcknowledged());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAcknowledgedById());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getAcknowledgedDate());
            
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static EmrFileLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("EmrFileLogDAO::Get: Received a NULL or empty EmrFileLog object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE id = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        EmrFileLog obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
    
    public static EmrFileLog getMostRecent(Integer idOrders)
             throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (idOrders == null || idOrders <= 0)
        {
            throw new IllegalArgumentException("EmrFileLogDAO::getMostRecent: Received a NULL or empty idOrders object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idOrders = " + String.valueOf(idOrders)
                + " ORDER BY created DESC LIMIT 1";

        PreparedStatement pStmt = con.prepareStatement(sql);

        EmrFileLog obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;        
    }

    public static Collection<EmrFileLog> getByOrderId(Integer idOrders)
             throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (idOrders == null || idOrders <= 0)
        {
            throw new IllegalArgumentException("EmrFileLogDAO::getByOrderId: Received a NULL or empty idOrders argument.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idOrders = " + String.valueOf(idOrders);

        PreparedStatement pStmt = con.prepareStatement(sql);

        EmrFile obj = null;
        List<EmrFileLog> emrFileLogs = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                emrFileLogs.add(ObjectFromResultSet(rs));
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return emrFileLogs;
    }

    public static EmrFileLog ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        EmrFileLog obj = new EmrFileLog();
        obj.setId(rs.getInt("id"));
        obj.setFileName(rs.getString("fileName"));
        obj.setEmrOrderId(rs.getString("emrOrderId"));
        obj.setDescription(rs.getString("description"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setCreated(rs.getDate("created"));
        obj.setEmrInterface(rs.getInt("emrInterface"));
        obj.setControlId(rs.getString("controlId"));
        obj.setErrorAcknowledged(rs.getBoolean("errorAcknowledged"));
        obj.setAcknowledgedById(SQLUtil.getInteger(rs, "acknowledgedById"));
        obj.setAcknowledgedDate(rs.getDate("acknowledgedDate"));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `emrFileLog`.`id`,\n"
                + "    `emrFileLog`.`fileName`,\n"
                + "    `emrFileLog`.`emrOrderId`,\n"
                + "    `emrFileLog`.`description`,\n"
                + "    `emrFileLog`.`idOrders`,\n"
                + "    `emrFileLog`.`created`,\n"
                + "    `emrFileLog`.`emrInterface`,\n"
                + "    `emrFileLog`.`controlId`,\n"
                + "    `emrFileLog`.`errorAcknowledged`,\n"
                + "    `emrFileLog`.`acknowledgedById`,\n"
                + "    `emrFileLog`.`acknowledgedDate`\n"
                + "FROM `emrorders`.`emrFileLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
