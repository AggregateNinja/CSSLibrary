package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import static DAOS.InsuranceSubmissionTypeDAO.get;
import DOS.InsuranceSubmissionMode;
import DOS.InsuranceSubmissionType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InsuranceSubmissionModeDAO implements IStructureCheckable
{

    private static final String table = "`insuranceSubmissionModes`";

    public enum InsuranceSubmissionModeStatus
    {

        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }

    public static InsuranceSubmissionMode insert(InsuranceSubmissionMode obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionModesDAO::Insert:"
                    + " Received a NULL InsuranceSubmissionModes object");
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
                + "  active"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
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
                throw new NullPointerException("InsuranceSubmissionModesDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdInsuranceSubmissionModes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(InsuranceSubmissionMode obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionModesDAO::Update:"
                    + " Received a NULL InsuranceSubmissionModes object.");
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
                + " active = ?"
                + " WHERE idInsuranceSubmissionModes = " + obj.getIdInsuranceSubmissionModes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
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
    }

    public static InsuranceSubmissionMode get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("InsuranceSubmissionModesDAO::Get:"
                    + " Received a NULL or empty InsuranceSubmissionModes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idInsuranceSubmissionModes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        InsuranceSubmissionMode obj = null;
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

    public static Collection<InsuranceSubmissionMode> get(InsuranceSubmissionModeStatus status)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionModesDAO::Get:"
                    + " Received a NULL or empty InsuranceSubmissionModeStatus object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (status == InsuranceSubmissionModeStatus.ACTIVE_ONLY)
        {
            sql += " WHERE active = 1";
        }
        
        if (status == InsuranceSubmissionModeStatus.INACTIVE_ONLY)
        {
            sql += " WHERE active = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<InsuranceSubmissionMode> insuranceSubmissionModes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                insuranceSubmissionModes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return insuranceSubmissionModes;
    }
    
    /**
     * Returns a map of < idInsuranceSubmissionModes --> InsuranceSubmissionMode >
     * @param status
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Map<Integer, InsuranceSubmissionMode> getIdMap(InsuranceSubmissionModeDAO.InsuranceSubmissionModeStatus status)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionModeDAO::getSystemMap:"
                    + " Received a NULL or empty InsuranceSubmissionMode object.");
        }
        
        Map<Integer, InsuranceSubmissionMode> insuranceSubmissionModeMap = new HashMap<>();
        
        Collection<InsuranceSubmissionMode> insuranceSubmissionModes = get(status);
        
        for (InsuranceSubmissionMode insuranceSubmissionMode : insuranceSubmissionModes)
        {
            if (insuranceSubmissionMode.getSystemName() != null && insuranceSubmissionMode.getSystemName().isEmpty() == false)
            {
                insuranceSubmissionModeMap.put(insuranceSubmissionMode.getIdInsuranceSubmissionModes(), insuranceSubmissionMode);
            }
        }
        return insuranceSubmissionModeMap;
    }    

    private static InsuranceSubmissionMode ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        InsuranceSubmissionMode obj = new InsuranceSubmissionMode();
        obj.setIdInsuranceSubmissionModes(rs.getInt("idInsuranceSubmissionModes"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `insuranceSubmissionModes`.`idInsuranceSubmissionModes`,\n"
                + "    `insuranceSubmissionModes`.`name`,\n"
                + "    `insuranceSubmissionModes`.`systemName`,\n"
                + "    `insuranceSubmissionModes`.`active`\n"
                + "FROM `css`.`insuranceSubmissionModes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
