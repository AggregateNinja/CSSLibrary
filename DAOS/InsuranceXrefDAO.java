/*
 * Computer Service & Support, Inc.  All Rights Reserved Oct 23, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InsuranceXref;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @date:   Oct 23, 2014  11:38:18 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: InsuranceXrefDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class InsuranceXrefDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`insuranceXref`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public enum SearchType
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ALL
    }
    
    public InsuranceXrefDAO()
    {
        fields.add("idxrefs");
        fields.add("idinsurances");
        fields.add("transformedIn");
        fields.add("transformedOut");
        fields.add("use1");
        fields.add("use2");
        fields.add("use3");
        fields.add("use4");
        fields.add("use5");
        fields.add("description");
        fields.add("created");
        fields.add("active");
    }
    
    private void CheckDBConnection(){
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    @Override
    public Boolean Insert(Serializable obj) {
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
            InsuranceXref insXref = (InsuranceXref)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(insXref, pStmt);
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
    
    /**
     * Inserts the argument object and returns a version with the new
     *  database identifier included.
     * @param insuranceXref
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public InsuranceXref Insert(InsuranceXref insuranceXref)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (insuranceXref == null) throw new IllegalArgumentException(
                "InsuranceXrefDAO::Insert: Received a NULL InsuranceXref object!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        
        SetStatement(insuranceXref, pStmt);
        pStmt.executeUpdate();
        
        Integer newId = null;
        
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next())
        {
            newId = rs.getInt(1);
        }        
        
        if (newId == null || newId.equals(0))
        {
            throw new SQLException("InsuranceXrefDAO::Insert: "
                    + "Unable to retrieve id for inserted row. "
                    + "The insert may have failed");
        }
        
        pStmt.close();
        
        // Return the record with the new identifier
        insuranceXref.setId(newId);
        
        return insuranceXref;
    }

    @Override
    public Boolean Update(Serializable obj) {
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
            InsuranceXref insXref = (InsuranceXref)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + insXref.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(insXref, pStmt);
            
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

    @Override
    public Boolean Delete(Serializable obj) {
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
        
        return false;
    }

    @Override
    public Serializable getByID(Integer ID) {
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
        
        try{
            InsuranceXref insXref = new InsuranceXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetXrefFromResultSet(insXref, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return insXref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public InsuranceXref GetInsuranceXrefByNumber(int number){
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
        
        try{
            InsuranceXref insXref = new InsuranceXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `insuranceNumber` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, number);
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetXrefFromResultSet(insXref, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return insXref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public InsuranceXref GetInsuranceXrefByInsIDAndXrefId(int num, int id){
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
        
        try{
            InsuranceXref insXref = new InsuranceXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `idinsurances` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setInt(2, num);
            pStmt.setBoolean(3, true);
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetXrefFromResultSet(insXref, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return insXref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public InsuranceXref GetInsuranceXrefByTransInAndXrefId(String in, int id){
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
        
        try{
            InsuranceXref insXref = new InsuranceXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `transformedIn` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            SQLUtil.SafeSetString(pStmt, 2, in);
            pStmt.setBoolean(3, true);
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetXrefFromResultSet(insXref, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return insXref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<InsuranceXref> GetAllByXref(int id){
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
        
        try{
            InsuranceXref insXref = new InsuranceXref();
            ArrayList<InsuranceXref> list = new ArrayList<>();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setBoolean(2, true);
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next()){
                insXref = new InsuranceXref();
                SetXrefFromResultSet(insXref, rs);
                list.add(insXref);
            }
            
            rs.close();
            pStmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet GetRSAllByXref(int id){
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
        
        try{
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setBoolean(2, true);
            
            ResultSet rs = pStmt.executeQuery();

            return rs;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Returns any insurance xref rows that fit the supplied criteria. Only
     *  searchType is required; all other fields are not considered if NULL.
     * 
     * VendorCode searches both the transformedIn and transformedOut fields and
     *  is trimmed before comparison to the database.
     * 
     * @param searchType
     * @param insuranceId
     * @param xRefId
     * @param vendorCode Compared to both transformedIn and transformedOut
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<InsuranceXref> Search(
            SearchType searchType,
            Integer insuranceId,
            Integer xRefId,
            String vendorCode)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (searchType == null) throw new IllegalArgumentException("InsuranceXrefDAO::Search: Received NULL search type argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM `insuranceXref` WHERE 1=1 ";
        
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (insuranceId != null)
        {
            sql += " AND idinsurances = " + insuranceId;
        }
        
        if (xRefId != null)
        {
            sql += " AND idxrefs = " + xRefId;
        }
        
        if (vendorCode != null && vendorCode.isEmpty() == false)
        {
            vendorCode = vendorCode.trim();
            sql += " AND (transformedIn LIKE '" + vendorCode + "' OR transformedOut LIKE '" + vendorCode + "')";
        }
        
        PreparedStatement pStmt = conn.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        List<InsuranceXref> insuranceXrefs = new LinkedList<>();
        InsuranceXref insXref;
        while (rs.next())
        {
            insXref = new InsuranceXref();
            SetXrefFromResultSet(insXref, rs);
            if (insXref.getId() != null && insXref.getId() > 0)
            {
                insuranceXrefs.add(insXref);
            }
        }
        return insuranceXrefs;
    }
    
    /**
     * Returns TransformedOut values from insuranceXref given xrefId, insurance number, and transformed in value.
     * 
     * Used in the AvalonBilling Program.
     * 
     * @param id The xrefId
     * @param insNumber The Insurance Number
     * @param transformedIn Transformed In value
     * @return The transformedOut value from the insuranceXref row found.
     * @throws java.sql.SQLException
    */
    public static String GetTransformedOutByInsuranceTransformedIn(int id, int insNumber, String transformedIn) throws
            SQLException, IllegalArgumentException, NullPointerException
    {
        String retVal = "";
        
        if (id <= 0 || insNumber <= 0 || transformedIn == null || transformedIn.isEmpty()) 
            throw new IllegalArgumentException("InsuranceXrefDAO::GetTransformedOutByInsuranceTransformedIn: Received NULL search type argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String query = "SELECT\n" +
            "xr.transformedOut\n" +
            "FROM css.insuranceXref xr\n" +
            "WHERE xr.idxrefs = ?\n" +
            "AND xr.idinsurances = ?\n" +
            "AND xr.transformedIn = ?;";
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        SQLUtil.SafeSetInteger(ps, 1, id);
        SQLUtil.SafeSetInteger(ps, 2, insNumber);
        SQLUtil.SafeSetString(ps, 3, transformedIn);
        
        ResultSet rs= ps.executeQuery();
        
        if (rs.next())
            retVal = rs.getString("transformedOut");
        
        return retVal;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + table + "(";
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

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    private static InsuranceXref SetXrefFromResultSet(InsuranceXref xref, ResultSet rs) throws SQLException
    {
        xref.setId(rs.getInt("id"));
        xref.setIdxrefs(rs.getInt("idxrefs"));
        xref.setIdinsurances(rs.getInt("idinsurances"));
        xref.setTransformedIn(rs.getString("transformedIn"));
        xref.setTransformedOut(rs.getString("transformedOut"));
        xref.setUse1(rs.getString("use1"));
        xref.setUse2(rs.getString("use2"));
        xref.setUse3(rs.getString("use3"));
        xref.setUse4(rs.getString("use4"));
        xref.setUse5(rs.getString("use5"));
        xref.setDescription(rs.getString("description"));
        xref.setCreated(rs.getTimestamp("created"));
        xref.setActive(rs.getBoolean("active"));
        
        return xref;
    }
    
    private PreparedStatement SetStatement(InsuranceXref obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdxrefs());
        pStmt.setInt(2, obj.getIdinsurances());
        SQLUtil.SafeSetString(pStmt, 3, obj.getTransformedIn());
        SQLUtil.SafeSetString(pStmt, 4, obj.getTransformedOut());
        SQLUtil.SafeSetString(pStmt, 5, obj.getUse1());
        SQLUtil.SafeSetString(pStmt, 6, obj.getUse2());
        SQLUtil.SafeSetString(pStmt, 7, obj.getUse3());
        SQLUtil.SafeSetString(pStmt, 8, obj.getUse4());
        SQLUtil.SafeSetString(pStmt, 9, obj.getUse5());
        SQLUtil.SafeSetString(pStmt, 10, obj.getDescription());
        SQLUtil.SafeSetTimeStamp(pStmt, 11, obj.getCreated());
        SQLUtil.SafeSetBoolean(pStmt, 12, obj.getActive());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
