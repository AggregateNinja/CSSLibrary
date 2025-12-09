package EMR.DAOS;

import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import EMR.DOS.EmrFile;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class EmrFileDAO implements IStructureCheckable
{

    private static final String table = "`emrorders`.`emrFiles`";

    public static EmrFile insert(EmrFile obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EmrFilesDAO::Insert: Received a NULL EmrFiles object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  idEmrFileLog,"
                + "  hl7File"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdEmrFileLog());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getHl7File());

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
                throw new NullPointerException("EmrFilesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setId(newId);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(EmrFile obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EmrFilesDAO::Update: Received a NULL EmrFiles object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " idEmrFileLog = ?,"
                + " hl7File = ?"
                + " WHERE id = " + obj.getId();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdEmrFileLog());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getHl7File());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
    
    public static EmrFile get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("EmrFilesDAO::Get: Received a NULL or empty id.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE id = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        EmrFile obj = null;
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

    public static EmrFile getByEmrFileLogId(Integer emrFileLogId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (emrFileLogId == null || emrFileLogId <= 0)
        {
            throw new IllegalArgumentException("EmrFilesDAO::getByEmrFileLogId:"
                    + " Received a NULL or empty emrFileLogId.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idEmrFileLog = " + String.valueOf(emrFileLogId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        EmrFile obj = null;
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
    
    
    public static EmrFile ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        EmrFile obj = new EmrFile();
        obj.setId(rs.getInt("id"));
        obj.setIdEmrFileLog(rs.getInt("idEmrFileLog"));
        obj.setHl7File(rs.getString("hl7File"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `emrFiles`.`id`,\n"
                + "    `emrFiles`.`idEmrFileLog`,\n"
                + "    `emrFiles`.`hl7File`\n"
                + "FROM `emrorders`.`emrFiles` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
