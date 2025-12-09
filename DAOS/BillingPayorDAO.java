package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.BillingPayor;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPayorDAO implements IStructureCheckable
{

    private static final String table = "`billingPayors`";

    public static BillingPayor insert(BillingPayor obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("BillingPayorDAO::Insert: Received a NULL BillingPayor object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  insuranceId,"
                + "  clientId,"
                + "  patientId"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPatientId());

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
                throw new NullPointerException("BillingPayorDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdbillingPayors(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static BillingPayor get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("BillingPayorDAO::Get: Received a NULL or empty BillingPayor object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idbillingPayors = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        BillingPayor obj = null;
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
    
    public static BillingPayor getByInsuranceId(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("BillingPayorDAO::getByInsuranceId: Received a NULL or empty insuranceId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE insuranceId = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        BillingPayor obj = null;
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

        // It's possible that this payor doesn't exist; create it if necessary
        if (obj == null)
        {
            obj = new BillingPayor();
            obj.setInsuranceId(id);
            obj = insert(obj);
        }
        pStmt.close();
        return obj;
    }
    
    
    public static BillingPayor getByClientId(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("BillingPayorDAO::getByClientId: Received a NULL or empty clientId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        BillingPayor obj = null;
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
        
        // It's possible that this payor doesn't exist; create it if necessary
        if (obj == null)
        {
            obj = new BillingPayor();
            obj.setClientId(id);
            obj = insert(obj);
        }        
        pStmt.close();
        return obj;
    }    
    
    public static BillingPayor getByPatientId(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("BillingPayorDAO::getByPatientId: Received a NULL or empty patientId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE patientId = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        BillingPayor obj = null;
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
        
        // It's possible that this payor doesn't exist; create it if necessary
        if (obj == null)
        {
            obj = new BillingPayor();
            obj.setPatientId(id);
            obj = insert(obj);
        }
        pStmt.close();
        return obj;
    }
    
    public static String equalsOrIs(Object obj)
    {
        return obj == null ? "IS" : "=";
    }

    public static BillingPayor getByAllId(Integer insuranceId, Integer clientId, Integer patientId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE insuranceId " + equalsOrIs(insuranceId) + " " + String.valueOf(insuranceId) +
                " AND clientId " + equalsOrIs(clientId) + " " + String.valueOf(clientId) + " AND patientId " + equalsOrIs(patientId) + " " + String.valueOf(patientId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        BillingPayor obj = null;
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

    private static BillingPayor ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        BillingPayor obj = new BillingPayor();
        
        obj.setIdbillingPayors(rs.getInt("idbillingPayors"));
        if (rs.wasNull()) obj.setIdbillingPayors(null);
        obj.setInsuranceId(rs.getInt("insuranceId"));
        if (rs.wasNull()) obj.setInsuranceId(null);
        obj.setClientId(rs.getInt("clientId"));
        if (rs.wasNull()) obj.setClientId(null);
        obj.setPatientId(rs.getInt("patientId"));
        if (rs.wasNull()) obj.setPatientId(null);
        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `billingPayors`.`idbillingPayors`,\n"
                + "    `billingPayors`.`insuranceId`,\n"
                + "    `billingPayors`.`clientId`,\n"
                + "    `billingPayors`.`patientId`\n"
                + "FROM `css`.`billingPayors` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
