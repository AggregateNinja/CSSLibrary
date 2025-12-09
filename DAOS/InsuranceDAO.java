package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.IDO.DatabaseSchema;
import DOS.Insurances;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

//******************************************************************************
/**
 * @date: Mar 13, 2012
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: InsuranceDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
//******************************************************************************
public class InsuranceDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    public static final String table = "`insurances`";
    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<>();

    //**************************************************************************
    public InsuranceDAO()
    {
        fields.add("name");
        fields.add("number");
        fields.add("abbreviation");
        fields.add("insuranceTypeId");
        fields.add("insuranceSubmissionTypeId");
        fields.add("insuranceSubmissionModeId");
        fields.add("address");
        fields.add("address2");
        fields.add("city");
        fields.add("state");
        fields.add("zip");
        fields.add("countryId");
        fields.add("phone");
        fields.add("fax");
        fields.add("receiverid");
        fields.add("sourceid1");
        fields.add("sourceid2");
        fields.add("payorid");
        fields.add("serviceTypeId");
        fields.add("accept_assignment");
        fields.add("active");
        fields.add("billSendout");
        fields.add("idinsurances");
    }

    //**************************************************************************
    public boolean InsertInsurance(Insurances insurance) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            String stmt = GenerateInsertStatement(insurance.getDatabaseSchema().getName(), fields);

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatmentFromInsurance(insurance, pStmt, false);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    //**************************************************************************
    
    /**
     * Inserts insurance and returns the object with the new unique database
     *  identifier populated.
     * @param insurance
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public Insurances Insert(Insurances insurance)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (insurance == null) throw new IllegalArgumentException("InsuranceDAO::Insert: Received a NULL insurance object argument!");
        
        
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
            String stmt = GenerateInsertStatement(insurance.getDatabaseSchema().getName(), fields);

            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

            SetStatmentFromInsurance(insurance, pStmt, false);
            pStmt.executeUpdate();

            Integer newId = null;
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }

            if (newId == null) throw new SQLException("InsuranceDAO::Insert: Could not retrieve new identifier for inserted row");
            
            insurance.setIdinsurances(newId);
            
            pStmt.close();

            return insurance;
    }

    //**************************************************************************
    public boolean UpdateInsurance(Insurances obj) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
   
        String sql = "UPDATE " + table + " SET "
                    + " name = ?,"
                    + " number = ?,"
                    + " abbreviation = ?,"
                    + " insuranceTypeId = ?,"
                    + " insuranceSubmissionTypeId = ?,"
                    + " insuranceSubmissionModeId = ?,"
                    + " address = ?,"
                    + " address2 = ?,"
                    + " city = ?,"
                    + " state = ?,"
                    + " zip = ?,"
                    + " countryId = ?,"
                    + " phone = ?,"
                    + " fax = ?,"
                    + " receiverid = ?,"
                    + " sourceid1 = ?,"
                    + " sourceid2 = ?,"
                    + " payorid = ?,"
                    + " serviceTypeId = ?,"
                    + " accept_assignment = ?,"
                    + " active = ?,"
                    + " billSendout = ?"
                    + " WHERE idinsurances = " + obj.getIdinsurances();            

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
                int i=0;
                SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNumber());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getAbbreviation());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceTypeId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionTypeId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionModeId());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getAddress());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getAddress2());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getCity());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getState());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getZip());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCountryId());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getPhone());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getFax());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getReceiverid());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getSourceid1());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getSourceid2());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getPayorId());
                SQLUtil.SafeSetInteger(pStmt, ++i, obj.getServiceTypeId());
                SQLUtil.SafeSetString(pStmt, ++i, obj.getAcceptAssignment());
                SQLUtil.SafeSetBooleanToInt(pStmt, ++i, obj.isActive());
                SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isBillSendout());
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
        return true;
    }

    //**************************************************************************
    public Insurances GetInsurance(DatabaseSchema schema, int insuranceId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs,con);

        if (schema == null) throw new IllegalArgumentException("InsuranceDAO::GetInsurance: Received NULL DatabaseSchema object!");
        //if (insuranceId <= 0) throw new IllegalArgumentException("InsuranceDAO:GetInsurance: Received insuranceId of " + insuranceId);
        
        String query = "SELECT * FROM " + schema.getName() + "." + table
                + " WHERE `idinsurances` = " + insuranceId;
        Insurances insurance = null;
        try
        {
            //Insurances insurance = new Insurances();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                insurance = SetInsuranceFromResultSet(rs);
            }
            rs.close();
            stmt.close();
            
        }
        catch (SQLException | NullPointerException ex)
        {
            System.out.println(query);
            System.out.println(ex.getMessage());
            throw ex;
        }
        return insurance;
    }
    
    //**************************************************************************
    public Insurances GetInsurance(int InsuranceId) throws SQLException
    {
        // Default to main schema
        return GetInsurance(DatabaseSchema.CSS, InsuranceId);
    }

    //**************************************************************************
    public boolean InsuranceExists(int insID)
    {
        return InsuranceExists(DatabaseSchema.CSS, insID);
    }
    
    //**************************************************************************
    public boolean InsuranceExists(DatabaseSchema schema, int insID)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + schema.getName() + "." + table + " WHERE "
                    + "`idinsurances` = " + insID);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling
            return false;
        }
    }

    //**************************************************************************
    public int GetInsuranceID(int insNo) 
    {
        return GetInsuranceID(DatabaseSchema.CSS, insNo);
    }
    
    //**************************************************************************
    public int GetInsuranceID(DatabaseSchema schema, int insNo)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return -1;
        }

        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT * FROM " + schema.getName() + "." + table + ";"
                    + "WHERE `idinsurances` = " + insNo;

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                x = rs.getInt("idinsirances");
            }
            rs.close();
            stmt.close();
            return x;
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    //**************************************************************************
    public Insurances[] GetAllInsurances()
    {
        return GetAllInsurances(DatabaseSchema.CSS);
    }
    
    //**************************************************************************
    public static Collection<Insurances> getInsurances(DatabaseSchema schema, SearchType searchType)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (schema == null)
        {
            throw new IllegalArgumentException("InsuranceDAO::getInsurances:"
                    + " Received a [NULL] SearchSchema enum argument");
        }
        
        if (searchType == null)
        {
            throw new IllegalArgumentException("InsuranceDAO::getInsurances:"
                    + " Received a [NULL] SearchStatus enum argument (e.g. ACTIVE_ONLY, ALL)");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM `" + schema.getName() + "`." + table;
        
        if (searchType.equals(SearchType.ACTIVE_ONLY))
        {
            sql += " AND `active` = 1";
        }
        
        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " AND `active` = 0";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<Insurances> insurances = new LinkedList<>();
        while (rs.next())
        {
            insurances.add(SetInsuranceFromResultSet(rs));
        }
        pStmt.close();
        return insurances;
    }
    
    //**************************************************************************
    public Insurances[] GetAllInsurances(DatabaseSchema schema)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;
            ArrayList<Insurances> insuranceList = new ArrayList<Insurances>();
            stmt = con.createStatement();
            String query = "SELECT * FROM " + schema.getName() + "." + table + ";";

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                insuranceList.add(SetInsuranceFromResultSet(rs));
            }

            if (insuranceList.isEmpty())
            {
                return null;
            }

            rs.close();
            stmt.close();

            Insurances[] outins = new Insurances[insuranceList.size()];
            outins = insuranceList.toArray(outins);
            return outins;
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    //**************************************************************************
    public Integer GetNextId(int startNum)
    {
        return GetNextId(DatabaseSchema.CSS, startNum);
    }
    
    //**************************************************************************
    public Integer GetNextId(DatabaseSchema schema, int startNum)
    {
        try
        {
            if(con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;            
        }
        
        try
        {
            int retval = -1;
            Statement stmt = con.createStatement();
            
            String query = "SELECT " +
                "	ins.idinsurances + 1 AS 'Next' " +
                "FROM " + schema.getName() + ".insurances ins " +
                "LEFT JOIN " + schema.getName() + ".insurances insNext ON insNext.idinsurances = ins.idinsurances + 1 " +
                "WHERE insNext.idinsurances IS NULL " +
                "AND ins.idinsurances > " + startNum + " " +
                "ORDER BY ins.idinsurances " +
                "LIMIT 0, 1;";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                retval = rs.getInt("Next");
            }
            rs.close();
            stmt.close();
            return retval;
        }
        catch (Exception ex)
        {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
        }        
    }
    
    //**************************************************************************
    public Integer GetNextAvailableInsuranceID()
    {
        return GetNextAvailableInsuranceID(DatabaseSchema.CSS);
    }
    
    //**************************************************************************
    public Integer GetNextAvailableInsuranceID(DatabaseSchema schema)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }
        try
        {
            int max = -1;
            Statement stmt = con.createStatement();
            String query = "SELECT MAX(`idinsurances`) AS 'Next' FROM " + schema.getName() + "." + table + ";";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
            {
                max = rs.getInt("Next");
            }
            rs.close();
            stmt.close();
            return max;
        }
        catch (Exception ex)
        {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    //**************************************************************************
    public List<Insurances> SearchInsuranceByNameFragment(
            String name, boolean CaseSensitive)
    {
        return SearchInsuranceByNameFragment(DatabaseSchema.CSS.getName(), name, CaseSensitive, null);
    }
    
    //**************************************************************************
    public List<Insurances> SearchInsuranceByNameFragment(
            String name, boolean CaseSensitive, Integer insuranceSubmissionTypeId)
    {
        return SearchInsuranceByNameFragment(DatabaseSchema.CSS.getName(), name, CaseSensitive, insuranceSubmissionTypeId);
    }
    
    //**************************************************************************

    public List<Insurances> SearchInsuranceByNameFragment(
            DatabaseSchema schema, boolean CaseSensitive)
    {
        return SearchInsuranceByNameFragment(schema.getName(), CaseSensitive);
    }    
    
    //**************************************************************************
    
    public List<Insurances> SearchInsuranceByNameFragment(
            String schemaStr, String name, boolean CaseSensitive, Integer insuranceSubmissionTypeId)
    {
        try
        {
            if (con.isClosed())
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
            List<Insurances> ilist = new ArrayList<>();

            //stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table + " ";
            if (CaseSensitive)
            {
                query += "WHERE `name` LIKE ?";
            }
            else
            {
                query += "WHERE LOWER(`name`) LIKE LOWER(?)";
            }
            if (insuranceSubmissionTypeId != null && insuranceSubmissionTypeId > 0)
            {
                query += " AND `insuranceSubmissionTypeId` = " + insuranceSubmissionTypeId;
            }
            query += " ORDER BY `name` ASC;";

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);

                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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
    //for listing insurances in the order entry module
    public List<Insurances> OrderEntryInsuranceByNameFragment(
            String name, boolean CaseSensitive)
    {
        return SearchInsuranceByNameFragment(DatabaseSchema.CSS.getName(), name, CaseSensitive, null);
    }
    
    //**************************************************************************
    public List<Insurances> OrderEntryInsuranceByNameFragment(
            String name, boolean CaseSensitive, Integer insuranceSubmissionTypeId)
    {
        return SearchInsuranceByNameFragment(DatabaseSchema.CSS.getName(), name, CaseSensitive, insuranceSubmissionTypeId);
    }
    
    //**************************************************************************

    public List<Insurances> OrderEntryInsuranceByNameFragment(
            DatabaseSchema schema, boolean CaseSensitive)
    {
        return SearchInsuranceByNameFragment(schema.getName(), CaseSensitive);
    }    
    
    //**************************************************************************
    public List<Insurances> OrderEntryInsuranceByNameFragment(
            String schemaStr, String name, boolean CaseSensitive, Integer insuranceSubmissionTypeId)
    {
        try
        {
            if (con.isClosed())
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
            List<Insurances> ilist = new ArrayList<>();

            //stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table + " ";
            if (CaseSensitive)
            {
                query += "WHERE `name` LIKE ? and active = 1";
            }
            else
            {
                query += "WHERE LOWER(`name`) LIKE LOWER(?) AND active = 1";
            }
            query += " ORDER BY `name` ASC;";

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);

                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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

    //**************************************************************************
    public List<Insurances> SearchInsuranceByAbbreviationFragment(String abbr, boolean CaseSensitive)
    {
        return SearchInsuranceByAbbreviationFragment(DatabaseSchema.CSS, abbr, CaseSensitive);
    }
    
    public List<Insurances> SearchInsuranceByAbbreviationFragment(
            DatabaseSchema schema, String abbr, boolean CaseSensitive)
    {
        return SearchInsuranceByAbbreviationFragment(schema.getName(), abbr, CaseSensitive);
    }
    
    //**************************************************************************
    public List<Insurances> SearchInsuranceByAbbreviationFragment(
            String schemaStr, String abbr, boolean CaseSensitive)
    {
        try
        {
            if (con.isClosed())
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
            List<Insurances> ilist = new ArrayList<>();

            //stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table + " ";
            if (CaseSensitive)
            {
                query += "WHERE `abbreviation` LIKE ?;";
            }
            else
            {
                query += "WHERE LOWER(`abbreviation`) LIKE LOWER(?);";
            }
            
            String abbrParam = SQLUtil.createSearchParam(abbr);
            stmt = createStatement(con, query, abbrParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);
                
                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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
      public List<Insurances> OrderEntryInsuranceByAbbreviationFragment(String abbr, boolean CaseSensitive)
    {
        return SearchInsuranceByAbbreviationFragment(DatabaseSchema.CSS, abbr, CaseSensitive);
    }
    
    public List<Insurances> OrderEntryInsuranceByAbbreviationFragment(
            DatabaseSchema schema, String abbr, boolean CaseSensitive)
    {
        return SearchInsuranceByAbbreviationFragment(schema.getName(), abbr, CaseSensitive);
    }
    
    //**************************************************************************
    public List<Insurances> OrderEntryInsuranceByAbbreviationFragment(
            String schemaStr, String abbr, boolean CaseSensitive)
    {
        try
        {
            if (con.isClosed())
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
            List<Insurances> ilist = new ArrayList<>();

            //stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table + " ";
            if (CaseSensitive)
            {
                query += "WHERE `abbreviation` LIKE ?;";
            }
            else
            {
                query += "WHERE LOWER(`abbreviation`) LIKE LOWER(?) AND active = 1;";
            }
            
            String abbrParam = SQLUtil.createSearchParam(abbr);
            stmt = createStatement(con, query, abbrParam);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);
                
                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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
    
    

    //**************************************************************************
    public List<Insurances> SearchInsuranceByIDFragment(Integer IDFragment)
    {
        return SearchInsuranceByIDFragment(DatabaseSchema.CSS, IDFragment);
    }

    //**************************************************************************
    public List<Insurances> SearchInsuranceByIDFragment(DatabaseSchema schema, Integer IDFragment)
    {
        return SearchInsuranceByIDFragment(schema.getName(), IDFragment);
    }
    //**************************************************************************
    
    public List<Insurances> SearchInsuranceByIDFragment(String schemaStr, Integer IDFragment)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            List<Insurances> ilist = new ArrayList<>();

            stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table
                    + " WHERE `idinsurances` REGEXP '" + IDFragment + "';";

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);

                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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
    //for listing insurances in the order entry module
    public List<Insurances> OrderSearchInsuranceByIDFragment(Integer IDFragment)
    {
        return SearchInsuranceByIDFragment(DatabaseSchema.CSS, IDFragment);
    }

    //**************************************************************************
    public List<Insurances> OrderSearchInsuranceByIDFragment(DatabaseSchema schema, Integer IDFragment)
    {
        return SearchInsuranceByIDFragment(schema.getName(), IDFragment);
    }
    //**************************************************************************
    
     public List<Insurances> OrderSearchInsuranceByIDFragment(String schemaStr, Integer IDFragment)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }

        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            List<Insurances> ilist = new ArrayList<>();

            stmt = con.createStatement();
            String query = "SELECT * FROM " + schemaStr + "." + table
                    + " WHERE `idinsurances` REGEXP '" + IDFragment + "' AND active = 1;";

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Insurances insurance = SetInsuranceFromResultSet(rs);

                ilist.add(insurance);
            }
            rs.close();
            stmt.close();
            return ilist;
        }
        catch (Exception ex)
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
    //**************************************************************************
    public static void Delete(DatabaseSchema schema, Integer insuranceId)
            throws SQLException, NullPointerException, IllegalArgumentException, UnsupportedOperationException
    {
        
        if (schema == null) throw new IllegalArgumentException(
                "InsuranceDAO::Delete: Received a NULL DatabaseSchema argument");
        
        if (insuranceId == null) throw new IllegalArgumentException(
                "InsuranceDAO::Delete: Received a NULL insurance identifier");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);        
        
        // Only supporting EMR insurances table for now
        if (schema.equals(DatabaseSchema.EMRORDERS) == false)
        {
            throw new UnsupportedOperationException(
                    "InsuranceDAO::Delete: The schema provided does not currently support delete operations");            
        }

        String sql = "DELETE FROM `" + schema.getName() + "`.`insurances` WHERE `idinsurances` = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, insuranceId);
        pStmt.executeUpdate();
        pStmt.close();
    }
    
    /**
     * Any NULL arguments will not be considered in the search except.
     * 
     * NOTE: SearchType is a required argument
     * 
     * @param insuranceNumber
     * @param insuranceName
     * @param insuranceTypeId
     * @param insuranceSubmissionTypeId
     * @param insuranceSubmissionModeId
     * @param searchType Active/Inactive/All ?
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static final List<Insurances> search(
            Integer insuranceNumber,
            String insuranceName,
            Integer insuranceTypeId,
            Integer insuranceSubmissionTypeId,
            Integer insuranceSubmissionModeId,
            SearchType searchType)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (searchType == null)
        {
            throw new IllegalArgumentException("InsuranceDAO::search:"
                    + " Received a [NULL] SearchType enum argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String sql = "SELECT * FROM " + table + " WHERE 1=1 ";
        if (insuranceNumber != null && insuranceNumber >= 0)
        {
            sql += " AND CAST(idinsurances AS CHAR) like '" + insuranceNumber.toString() + "%'";
        }
        
        if (insuranceName != null && insuranceName.isEmpty() == false)
        {
            insuranceName = insuranceName.replace("'", "''");
            sql += " AND `name` like '" + insuranceName + "%'";
        }
        
        if (insuranceTypeId != null && insuranceTypeId > 0)
        {
            sql += " AND insuranceTypeId = " + insuranceTypeId.toString();
        }
        
        if (insuranceSubmissionTypeId != null && insuranceSubmissionTypeId > 0)
        {
            sql += " AND insuranceSubmissionTypeId = " + insuranceSubmissionTypeId.toString();
        }
        
        if (insuranceSubmissionModeId != null && insuranceSubmissionModeId > 0)
        {
            sql += " AND insuranceSubmissionModeId = " + insuranceSubmissionModeId.toString();
        }
        
        if (searchType.equals(SearchType.ACTIVE_ONLY))
        {
            sql += " AND active = 1";
        }
        
        if (searchType.equals(SearchType.INACTIVE_ONLY))
        {
            sql += " AND active = 0";
        }
        
        System.out.println(sql);
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        ResultSet rs = pStmt.executeQuery();
        
        List<Insurances> insurances = new ArrayList<>();
        while (rs.next())
        {
            insurances.add(SetInsuranceFromResultSet(rs));
        }
        pStmt.close();
        return insurances;
    }
    

    //**************************************************************************
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertInsurance((Insurances)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(InsuranceDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //**************************************************************************
    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateInsurance((Insurances) obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(InsuranceDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //**************************************************************************
    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    //**************************************************************************
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return (Serializable)GetInsurance(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(InsuranceDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //**************************************************************************
    private String GenerateInsertStatement(String schema, ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + schema + "." + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    //**************************************************************************
    private String GenerateUpdateStatement(DatabaseSchema schema, ArrayList<String> fields)
    {
        String stmt = "UPDATE " + schema.getName() + "." + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            if (fields.get(i).equals("idinsurances") == false)
            {
                stmt += " `" + fields.get(i) + "` = ?";
                if (i != fields.size() - 1)
                {
                    stmt += ",";
                }
            }
        }
        return stmt;
    }

    //**************************************************************************
    public static Insurances SetInsuranceFromResultSet(ResultSet rs) throws SQLException
    {
        Insurances obj = new Insurances();
        obj.setIdinsurances(rs.getInt("idinsurances"));
        obj.setName(rs.getString("name"));
        obj.setNumber(rs.getInt("number"));
        obj.setAbbreviation(rs.getString("abbreviation"));
        obj.setInsuranceTypeId(rs.getInt("insuranceTypeId"));
        obj.setInsuranceSubmissionTypeId(rs.getInt("insuranceSubmissionTypeId"));
        obj.setInsuranceSubmissionModeId(rs.getInt("insuranceSubmissionModeId"));
        obj.setAddress(rs.getString("address"));
        obj.setAddress2(rs.getString("address2"));
        obj.setCity(rs.getString("city"));
        obj.setState(rs.getString("state"));
        obj.setZip(rs.getString("zip"));
        obj.setCountryId(rs.getInt("countryId"));
        obj.setPhone(rs.getString("phone"));
        obj.setFax(rs.getString("fax"));
        obj.setReceiverid(rs.getString("receiverid"));
        obj.setSourceid1(rs.getString("sourceid1"));
        obj.setSourceid2(rs.getString("sourceid2"));
        obj.setPayorId(rs.getString("payorid"));
        obj.setServiceTypeId(rs.getInt("serviceTypeId"));
        obj.setAcceptAssignment(rs.getString("accept_assignment"));
        obj.setActive(rs.getBoolean("active"));
        obj.setBillSendout(rs.getBoolean("billSendout"));
        return obj;
    }

    //**************************************************************************
    private PreparedStatement SetStatmentFromInsurance(Insurances obj,
            PreparedStatement pStmt, boolean update) throws SQLException
    {
        int i = 0;
        pStmt.setString(++i, obj.getName());
        pStmt.setInt(++i, obj.getNumber());
        pStmt.setString(++i, obj.getAbbreviation());
        pStmt.setInt(++i,obj.getInsuranceTypeId());
        pStmt.setInt(++i, obj.getInsuranceSubmissionTypeId());
        pStmt.setInt(++i, obj.getInsuranceSubmissionModeId());
        pStmt.setString(++i, obj.getAddress());
        pStmt.setString(++i, obj.getAddress2());
        pStmt.setString(++i, obj.getCity());
        pStmt.setString(++i, obj.getState());
        pStmt.setString(++i, obj.getZip());
        pStmt.setInt(++i, obj.getCountryId());
        pStmt.setString(++i, obj.getPhone());
        pStmt.setString(++i, obj.getFax());
        pStmt.setString(++i, obj.getReceiverid());
        pStmt.setString(++i, obj.getSourceid1());
        pStmt.setString(++i, obj.getSourceid2());
        pStmt.setString(++i, obj.getPayorId());
        pStmt.setInt(++i, obj.getServiceTypeId());
        pStmt.setString(++i, obj.getAcceptAssignment());
        pStmt.setInt(++i, obj.isActive() ? 1 : 0);
        pStmt.setBoolean(++i, obj.isBillSendout());
        int x = fields.size();
        if (update == false)
        {
            SQLUtil.SafeSetInteger(pStmt, x, obj.getIdinsurances());
            //pStmt.setInt(x, obj.getIdinsurances());
            ++x;
        }
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}

//******************************************************************************
//******************************************************************************
//******************************************************************************