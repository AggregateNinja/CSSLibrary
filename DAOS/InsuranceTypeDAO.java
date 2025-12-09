package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.InsuranceType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static Utility.SQLUtil.createStatement;

public class InsuranceTypeDAO implements IStructureCheckable
{

    public enum InsuranceTypeStatus
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    private static final String table = "`insuranceTypes`";

    public static InsuranceType insert(InsuranceType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceTypeDAO::Insert:"
                    + " Received a NULL InsuranceTypes object");
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
                + "  createdById,"
                + "  created,"
                + "  active"
                + ")"
                + "VALUES (?,?,NOW(),?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedById());
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
                throw new NullPointerException("InsuranceTypesDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdInsuranceTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(InsuranceType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("InsuranceTypesDAO::Update:"
                    + " Received a NULL InsuranceTypes object.");
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
                + " createdById = ?,"
                + " active = ?"
                + " WHERE idInsuranceTypes = " + obj.getIdInsuranceTypes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedById());
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
    
    public static InsuranceType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("InsuranceTypesDAO::Get:"
                    + " Received a NULL or empty InsuranceTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idInsuranceTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        InsuranceType obj = null;
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
    
    public static Collection<InsuranceType> get(String NameFragment)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            if (con.isValid(2) == false)
            {
                con = CheckDBConnection.Check(dbs, con);
             }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            List<InsuranceType> typeList = new ArrayList<>();

            //stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " ";
            query += "WHERE LOWER(`name`) LIKE LOWER(?)";
            query += " ORDER BY `name` ASC;";

            String nameParam = SQLUtil.createSearchParam(NameFragment);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                InsuranceType type = ObjectFromResultSet(rs);

                typeList.add(type);
            }
            rs.close();
            stmt.close();
            return typeList;
        }
        catch (SQLException | NullPointerException ex)
        {
            System.err.println(ex.getMessage());
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            return null;
        }
    }
    
    public static Collection<InsuranceType> get(InsuranceTypeStatus status)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceTypesDAO::Get:"
                    + " Received a NULL or empty InsuranceTypeStatus object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table;
        
        if (status == InsuranceTypeStatus.ACTIVE_ONLY)
        {
            sql += " WHERE active = 1";
        }
        
        if (status == InsuranceTypeStatus.INACTIVE_ONLY)
        {
            sql += " WHERE active = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<InsuranceType> insuranceTypes = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                insuranceTypes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return insuranceTypes;
    }
    
    /**
     * Returns a map of < idInsuranceTypes --> InsuranceType >
     * @param status
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Map<Integer, InsuranceType> getIdMap(InsuranceTypeStatus status)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("InsuranceTypesDAO::getIdMap:"
                    + " Received a NULL or empty InsuranceTypeStatus object.");
        }
        
        Map<Integer, InsuranceType> insuranceTypeMap = new HashMap<>();
        
        Collection<InsuranceType> insuranceTypes = get(status);
        
        for (InsuranceType insuranceType : insuranceTypes)
        {
            if (insuranceType.getSystemName() != null && insuranceType.getSystemName().isEmpty() == false)
            {
                insuranceTypeMap.put(insuranceType.getIdInsuranceTypes(), insuranceType);
            }
        }
        return insuranceTypeMap;
    }
    

    public static InsuranceType ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        InsuranceType obj = new InsuranceType();
        obj.setIdInsuranceTypes(rs.getInt("idInsuranceTypes"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setCreatedById(rs.getInt("createdById"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `insuranceTypes`.`idInsuranceTypes`,\n"
                + "    `insuranceTypes`.`name`,\n"
                + "    `insuranceTypes`.`systemName`,\n"
                + "    `insuranceTypes`.`createdById`,\n"
                + "    `insuranceTypes`.`created`,\n"
                + "    `insuranceTypes`.`active`\n"
                + "FROM `css`.`insuranceTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
