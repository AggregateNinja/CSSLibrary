package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.InsuranceSubmissionType;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
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

public class InsuranceSubmissionTypeDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`insuranceSubmissionTypes`";

    public enum InsuranceSubmissionStatus
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public enum InsuranceSubmissionTypes
    {
        CLEARING_HOUSE("clearinghouse"),
        DIRECT("direct"),
        PAPER_FORM("paperform"),
        CLIENT_BILLING("client"),
        PATIENT_BILLING("patient"),
        NO_SUBMISSION("nosubmit");
        
        public final String systemName;
        
        InsuranceSubmissionTypes(String systemName)
        {
            this.systemName = systemName;
        }
    }
    
    public static InsuranceSubmissionType insert(InsuranceSubmissionType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::Insert:"
                    + " Received a NULL InsuranceSubmissionTypes object");
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
                throw new NullPointerException("InsuranceSubmissionTypesDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdInsuranceSubmissionTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(InsuranceSubmissionType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::Update:"
                    + " Received a NULL InsuranceSubmissionTypes object.");
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
                + " WHERE idInsuranceSubmissionTypes = " + obj.getIdInsuranceSubmissionTypes();

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
        pStmt.close();
    }

    public static InsuranceSubmissionType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::Get:"
                    + " Received a NULL or empty InsuranceSubmissionTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idInsuranceSubmissionTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        InsuranceSubmissionType obj = null;
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
    
    public static Collection<InsuranceSubmissionType> get(InsuranceSubmissionStatus status)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::Get:"
                    + " Received a NULL or empty InsuranceSubmissionStatus object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (status == InsuranceSubmissionStatus.ACTIVE_ONLY)
        {
            sql += " WHERE active = 1";
        }
        
        if (status == InsuranceSubmissionStatus.INACTIVE_ONLY)
        {
            sql += " WHERE active = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<InsuranceSubmissionType> insuranceSubmissionTypes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                insuranceSubmissionTypes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return insuranceSubmissionTypes;
    }
    
    /**
     * Gets an insurance submission type row based on the argument enum.
     *  Enum has the system name, which should be unique and should never
     *  be changed.
     * 
     * @param type
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static InsuranceSubmissionType get(InsuranceSubmissionTypes type)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (type == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypeDAO::get(InsuranceSubmissionTypes enum)"
                    + " Received a [NULL] InsuranceSubmissionTypes enum argument");
        }
        
        InsuranceSubmissionType insuranceSubmissionType = null;
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = "SELECT * FROM " + table + " WHERE `systemName` = ?";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            pStmt.setString(1, type.systemName);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                insuranceSubmissionType = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = "InsuranceSubmissionTypeDAO::get(InsuranceSubmissionTypes enum)"
                    + " Could not retrieve line for system name: " + type.systemName + ". Check"
                    + " the " + table + " for an entry. " + (ex.getMessage() == null ? "" : ex.getMessage());
            throw new SQLException(errorMessage);
        }
        return insuranceSubmissionType;
    }
    
    public static InsuranceSubmissionType getBySystemName(String name)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (name == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::getByName:"
                    + " Received a NULL or empty name argument.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE `systemName` = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetString(pStmt, 1, name);
        ResultSet rs = pStmt.executeQuery();
        
        InsuranceSubmissionType insuranceSubmissionType = null;
        if (rs.next())
        {
            insuranceSubmissionType = ObjectFromResultSet(rs);
        }
        pStmt.close();
        return insuranceSubmissionType;
    }
    
    public static InsuranceSubmissionType getByName(String name)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (name == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypesDAO::getByName:"
                    + " Received a NULL or empty name argument.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE `name` = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetString(pStmt, 1, name);
        ResultSet rs = pStmt.executeQuery();
        
        InsuranceSubmissionType insuranceSubmissionType = null;
        if (rs.next())
        {
            insuranceSubmissionType = ObjectFromResultSet(rs);
        }
        pStmt.close();
        return insuranceSubmissionType;
    }
    
    
    /**
     * Returns a map of < idInsuranceSubmissionTypes --> InsuranceSubmissionType >
     * @param status
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Map<Integer, InsuranceSubmissionType> getIdMap(InsuranceSubmissionStatus status)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceSubmissionTypeDAO::getIdMap:"
                    + " Received a NULL or empty InsuranceSubmissionStatus object.");
        }
        
        Map<Integer, InsuranceSubmissionType> insuranceSubmissionMap = new HashMap<>();
        
        Collection<InsuranceSubmissionType> insuranceSubmissionTypes = get(status);
        
        for (InsuranceSubmissionType insuranceSubmissionType : insuranceSubmissionTypes)
        {
            if (insuranceSubmissionType.getSystemName() != null && insuranceSubmissionType.getSystemName().isEmpty() == false)
            {
                insuranceSubmissionMap.put(insuranceSubmissionType.getIdInsuranceSubmissionTypes(), insuranceSubmissionType);
            }
        }
        return insuranceSubmissionMap;
    }

    public static InsuranceSubmissionType ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        InsuranceSubmissionType obj = new InsuranceSubmissionType();
        obj.setIdInsuranceSubmissionTypes(rs.getInt("idInsuranceSubmissionTypes"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `insuranceSubmissionTypes`.`idInsuranceSubmissionTypes`,\n"
                + "    `insuranceSubmissionTypes`.`name`,\n"
                + "    `insuranceSubmissionTypes`.`systemName`,\n"
                + "    `insuranceSubmissionTypes`.`active`\n"
                + "FROM `css`.`insuranceSubmissionTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
