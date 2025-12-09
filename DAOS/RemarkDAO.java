package DAOS;

/**
 * @date: Mar 5, 2012
 * @author: Ryan
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: RemarkDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.RemarkType;
import DOS.Remarks;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

public class RemarkDAO implements DAOInterface, IStructureCheckable
{

    public enum RemarkStatus
    {

        ACTIVE,
        INACTIVE,
        ALL
    }

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`remarks`";

    public boolean InsertRemark(Remarks remark) throws SQLException
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
            String stmt = "INSERT INTO " + table + "("
                    + " `remarkNo`,"
                    + " `remarkName`,"
                    + " `remarkAbbr`,"
                    + " `remarkType`,"
                    + " `remarkText`,"
                    + " `isAbnormal`,"
                    + " `remarkDepartment`,"
                    + " `noCharge`)"
                    + " values (?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, remark.getRemarkNo());
            SQLUtil.SafeSetString(pStmt, 2, remark.getRemarkName());
            SQLUtil.SafeSetString(pStmt, 3, remark.getRemarkAbbr());
            SQLUtil.SafeSetInteger(pStmt, 4, remark.getRemarkType());
            SQLUtil.SafeSetBytes(pStmt, 5, remark.getRemarkText());
            SQLUtil.SafeSetBoolean(pStmt, 6, remark.getIsAbnormal());
            SQLUtil.SafeSetInteger(pStmt, 7, remark.getRemarkDepartment());
            SQLUtil.SafeSetBoolean(pStmt, 8, remark.getNoCharge());

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

    public boolean UpdateRemark(Remarks remark) throws SQLException
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
            String stmt = "UPDATE " + table + " SET"
                    + " `remarkNo` = ?,"
                    + " `remarkName` = ?,"
                    + " `remarkAbbr` = ?,"
                    + " `remarkType` = ?,"
                    + " `remarkText` = ?,"
                    + " `isAbnormal` = ?,"
                    + " `remarkDepartment` = ?,"
                    + " `noCharge` = ? "
                    + "WHERE `remarkNo` = " + remark.getRemarkNo();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, remark.getRemarkNo());
            SQLUtil.SafeSetString(pStmt, 2, remark.getRemarkName());
            SQLUtil.SafeSetString(pStmt, 3, remark.getRemarkAbbr());
            SQLUtil.SafeSetInteger(pStmt, 4, remark.getRemarkType());
            SQLUtil.SafeSetBytes(pStmt, 5, remark.getRemarkText());
            SQLUtil.SafeSetBoolean(pStmt, 6, remark.getIsAbnormal());
            SQLUtil.SafeSetInteger(pStmt, 7, remark.getRemarkDepartment());
            SQLUtil.SafeSetBoolean(pStmt, 8, remark.getNoCharge());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Remarks GetRemark(int remNum) throws SQLException
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
            Remarks remark = new Remarks();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `remarkNo` = " + remNum;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                remark = getRemarkFromResultSet(rs);

            }

            rs.close();
            stmt.close();

            return remark;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Remarks GetRemark(int remNum, boolean activeOnly) throws SQLException
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
            Remarks remark = new Remarks();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `remarkNo` = " + remNum;
            if (activeOnly) query += " AND `active` = b'1'";
            query += " LIMIT 1";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                remark = getRemarkFromResultSet(rs);

            }

            rs.close();
            stmt.close();

            return remark;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Remarks GetRemarkFromID(int remID)
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
            Remarks remark = new Remarks();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idremarks` = " + remID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                remark = getRemarkFromResultSet(rs);

            }

            rs.close();
            stmt.close();

            return remark;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Remarks GetRemarkByName(String name)
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
            Remarks remark = new Remarks();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `remarkName` = ?;";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                remark = getRemarkFromResultSet(rs);
            }

            rs.close();
            stmt.close();

            return remark;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Remarks GetRemarkByAbbr(String abbr)
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
            Remarks remark = new Remarks();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `remarkAbbr` = ?;";

            stmt = createStatement(con, query, abbr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                remark = getRemarkFromResultSet(rs);
            }

            rs.close();
            stmt.close();

            return remark;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
            return null;
        }
    }

    public boolean RemarkExists(int RemNo)
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
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`remarkNo` = " + RemNo);
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

    public int GetRemarkID(int RemNo)
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
            String query = "SELECT `idremarks` FROM " + table + " "
                    + "WHERE `remarkNo` = " + RemNo;

            rs = stmt.executeQuery(query);

            if (rs.next())
            {
                x = rs.getInt("idremarks");
            }
            stmt.close();
            return x;
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    public Remarks[] GetAllRemarks()
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
            ArrayList< Remarks> remarkList = new ArrayList<Remarks>();
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT * FROM " + table + ";";

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                remarkList.add(getRemarkFromResultSet(rs));
            }

            rs.close();
            stmt.close();

            if (remarkList.isEmpty())
            {
                return null;
            }

            Remarks[] outins = new Remarks[remarkList.size()];
            outins = remarkList.toArray(outins);
            return outins;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Searches for any remark that has a number or name that starts with the
     * search argument, and that matches the remark type category supplied.
     *
     * NOTE: Result-category remarks are handled differently, since they were in
     * the system before remark types or categories were added.
     *
     * 'Result' category remarks are defined as any remarks that 1) Don't have a
     * remarkType assigned, or 2) Have a remarkType with a NULL
     * remarkCategoryId, or 3) Have a remarkType with a 'result' remark category
     *
     * This is so all of the default lookup code will still work for resulting
     *
     * @param searchText
     * @param categoryName
     * @param status
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static ResultSet SearchNameNumberByCategory(
            String searchText,
            RemarkType.CategoryName categoryName, RemarkStatus status)
            throws SQLException, NullPointerException, IllegalArgumentException
    {

        if (categoryName == null)
        {
            throw new IllegalArgumentException(
                    "RemarkDAO::SearchNameNumberByCategory: Received a NULL"
                    + " RemarkType.CategoryName argument");
        }

        if (status == null)
        {
            throw new IllegalArgumentException("RemarkDAO::SearchNameNumberByCategory:"
                    + " Received a NULL RemarkStatus enum argument");
        }

        if (searchText == null)
        {
            searchText = "";
        }
        searchText = searchText.replace("%", "");
        searchText += "%";
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "SELECT r.* FROM `remarks` r"
                + " LEFT JOIN remarkTypes rt ON r.remarkType = rt.id"
                + " LEFT JOIN remarkCategories rc ON rt.remarkCategoryId = rc.idremarkCategories";

        // NOTE: Result-category remarks are handled differently, since they
        //   were the only ones in the system when remarkTypes were added (see method documentation above)
        if (categoryName.getCategorySystemName().equals(RemarkType.CategoryName.RESULT.getCategorySystemName()))
        {
            query += " WHERE (rt.remarkCategoryId IS NULL OR rc.systemName = '" + RemarkType.CategoryName.RESULT.getCategorySystemName() + "')";
        }
        else // This is a non-result remark category. The argument needs to match
        {
            query += " WHERE rc.systemName = '" + categoryName.getCategorySystemName() + "'";
        }

        query += " AND (r.`remarkNo` LIKE ? OR r.`remarkName` LIKE ?)";

        if (status == RemarkStatus.ACTIVE)
        {
            query += " AND r.`active` = 1";
        }

        if (status == RemarkStatus.INACTIVE)
        {
            query += " AND r.`active` = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, searchText);
        pStmt.setString(2, searchText);

        return pStmt.executeQuery();
    }

    /**
     * Retrieves a single remark that matches the input string (whitespace is
     * trimmed) If more than one remark of the supplied category matches the
     * text, the first is returned (we should prevent duplicate remarks within a
     * category)
     *
     * @param remarkName
     * @param categoryName
     * @param status
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Remarks getByNameCategory(
            String remarkName, RemarkType.CategoryName categoryName, RemarkStatus status)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (categoryName == null)
        {
            throw new IllegalArgumentException("RemarkDAO::getByNameCategory:"
                    + " Received a [NULL] RemarkType.CategoryName argument");
        }

        if (status == null)
        {
            throw new IllegalArgumentException("RemarkDAO::getByNameCategory:"
                    + " Received a NULL RemarkStatus enum argument");
        }

        if (remarkName == null || remarkName.isEmpty())
        {
            return null;
        }

        remarkName = remarkName.trim();

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "SELECT r.* FROM `remarks` r"
                + " LEFT JOIN remarkTypes rt ON r.remarkType = rt.id"
                + " LEFT JOIN remarkCategories rc ON rt.remarkCategoryId = rc.idremarkCategories";

        // NOTE: Result-category remarks are handled differently, since they
        //   were the only ones in the system when remarkTypes were added (see method documentation above)
        if (categoryName.getCategorySystemName().equals(RemarkType.CategoryName.RESULT.getCategorySystemName()))
        {
            query += " WHERE (rt.remarkCategoryId IS NULL OR rc.systemName = '" + RemarkType.CategoryName.RESULT.getCategorySystemName() + "')";
        }
        else // This is a non-result remark category. The argument needs to match
        {
            query += " WHERE rc.systemName = '" + categoryName.getCategorySystemName() + "'";
        }

        query += " AND r.`remarkName` = ?";

        if (status == RemarkStatus.ACTIVE)
        {
            query += " AND r.`active` = 1";
        }

        if (status == RemarkStatus.INACTIVE)
        {
            query += " AND r.`active` = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, remarkName);
        ResultSet rs = pStmt.executeQuery();

        Remarks remark = null;
        if (rs.next())
        {
            remark = getRemarkFromResultSet(rs);
        }
        pStmt.close();
        return remark;
    }

    public static Remarks getByNumberCategory(Integer remarkNumber,
            RemarkType.CategoryName categoryName, RemarkStatus status)
            throws SQLException, NullPointerException, IllegalArgumentException
    {

        if (categoryName == null)
        {
            throw new IllegalArgumentException(
                    "RemarkDAO::getByNumberCategory: Received a NULL"
                    + " RemarkType.CategoryName argument");
        }

        if (remarkNumber == null)
        {
            throw new IllegalArgumentException(
                    "RemarkDAO::getByNumberCategory: Received a NULL remarkNumber argument");
        }

        if (status == null)
        {
            throw new IllegalArgumentException("RemarkDAO::getByNameCategory:"
                    + " Received a NULL RemarkStatus enum argument");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "SELECT r.* FROM `remarks` r"
                + " LEFT JOIN remarkTypes rt ON r.remarkType = rt.id"
                + " LEFT JOIN remarkCategories rc ON rt.remarkCategoryId = rc.idremarkCategories";

        // NOTE: Result-category remarks are handled differently, since they
        //   were the only ones in the system when remarkTypes were added (see method documentation above)
        if (categoryName.getCategorySystemName().equals(RemarkType.CategoryName.RESULT.getCategorySystemName()))
        {
            query += " WHERE (rt.remarkCategoryId IS NULL OR rc.systemName = '" + RemarkType.CategoryName.RESULT.getCategorySystemName() + "')";
        }
        else // This is a non-result remark category. The argument needs to match
        {
            query += " WHERE rc.systemName = '" + categoryName.getCategorySystemName() + "'";
        }

        query += " AND r.`remarkNo` = ?";

        if (status == RemarkStatus.ACTIVE)
        {
            query += " AND r.`active` = 1";
        }

        if (status == RemarkStatus.INACTIVE)
        {
            query += " AND r.`active` = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(query);

        pStmt.setInt(1, remarkNumber);
        ResultSet rs = pStmt.executeQuery();

        Remarks remark = null;
        if (rs.next())
        {
            remark = getRemarkFromResultSet(rs);
        }
        pStmt.close();
        return remark;
    }

    /**
     * Searches both remark number and remark name for text starting with the
     * argument text.
     *
     * @param searchText The string to search for
     * @param remarkCategory The category of remarks this should search within
     * @param remarkTypeSystemNames List of remark type system names (if NULL or
     * empty, all for the category will be returned)
     * @param status
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static ResultSet SearchNameNumberByCategoryType(
            String searchText, RemarkType.CategoryName remarkCategory,
            List<String> remarkTypeSystemNames, RemarkStatus status)
            throws SQLException, IllegalArgumentException
    {
        if (remarkCategory == null)
        {
            throw new IllegalArgumentException(
                    "RemarkDAO::SearchNameNumberByCategoryType:"
                    + " Received a NULL RemarkType.CategoryName argument");
        }

        if (status == null)
        {
            throw new IllegalArgumentException("RemarkDAO::getByNameCategory:"
                    + " Received a NULL RemarkStatus enum argument");
        }

        if (searchText == null)
        {
            searchText = "";
        }
        searchText = "%" + searchText;
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "SELECT r.* FROM `remarks` r"
                + " LEFT JOIN remarkTypes rt ON r.remarkType = rt.id"
                + " LEFT JOIN remarkCategories rc ON rt.remarkCategoryId = rc.idremarkCategories";

        // NOTE: Result-category remarks are handled differently, since they
        //   were the only ones in the system when remarkTypes were added (see method documentation above)
        if (remarkCategory.getCategorySystemName().equals(RemarkType.CategoryName.RESULT.getCategorySystemName()))
        {
            query += " WHERE (rt.remarkCategoryId IS NULL OR rc.systemName = '" + RemarkType.CategoryName.RESULT.getCategorySystemName() + "')";
        }
        else // This is a non-result remark category. The argument needs to match
        {
            query += " WHERE rc.systemName = '" + remarkCategory.getCategorySystemName() + "'";
        }

        if (remarkTypeSystemNames != null && remarkTypeSystemNames.size() > 0)
        {
            // Add where clause for remark type system names
            String remarkTypeString = "";
            for (String remarkTypeSystemName : remarkTypeSystemNames)
            {
                if (remarkTypeSystemName != null && remarkTypeSystemName.isEmpty() == false)
                {
                    if (remarkTypeString.isEmpty() == false)
                    {
                        remarkTypeString += ",";
                    }
                    remarkTypeString += "'" + remarkTypeSystemName + "'";
                }
            }
            if (remarkTypeString.length() > 0)
            {
                remarkTypeString = " AND rt.systemName IN (" + remarkTypeString + ") ";
                query += remarkTypeString;
            }
        }

        // Only active remark types
        query += " AND rt.active = 1 AND (r.`remarkNo` LIKE ? OR r.`remarkName` LIKE ?)";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, searchText);
        pStmt.setString(2, searchText);

        return pStmt.executeQuery();
    }

    /**
     * Searches for any active remark that has a number or name that starts with
     * the search argument
     *
     * @param searchText
     * @param status
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static ResultSet SearchNameNumber(String searchText,
            RemarkStatus status)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("RemarkDAO::getByNameCategory:"
                    + " Received a NULL RemarkStatus enum argument");
        }

        if (searchText == null)
        {
            searchText = "";
        }
        searchText = "%" + searchText;
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String query = "SELECT r.* FROM `remarks` r "
                + "WHERE (r.`remarkNo` LIKE ? OR r.`remarkName` LIKE ?)";

        if (status == RemarkStatus.ACTIVE)
        {
            query += " AND r.`active` = 1";
        }

        if (status == RemarkStatus.INACTIVE)
        {
            query += " AND r.`active` = 0";
        }

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, searchText);
        pStmt.setString(2, searchText);

        return pStmt.executeQuery();
    }

    public ResultSet SearchByRemarkName(String name) throws SQLException
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
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `remarkNo` AS 'Number', `remarkName` AS 'Name' "
                    + "FROM " + table + " "
                    + "WHERE `remarkName` LIKE ? ";

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            ResultSet rs = stmt.executeQuery();

            return rs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ResultSet SearchByRemarkNumber(String number) throws SQLException
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
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `remarkNo` AS 'Number', `remarkName` AS 'Name' "
                    + "FROM " + table + " "
                    + "WHERE `remarkNo` LIKE ? ";

            String numberParam = SQLUtil.createSearchParam(number);
            stmt = createStatement(con, query, numberParam);
            ResultSet rs = stmt.executeQuery();

            return rs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int GetNextAvailableNumber() throws SQLException
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
            int number = 0;
            Statement stmt = con.createStatement();

            String query = "SELECT MIN(r1.remarkNo + 1) AS 'NextNumber' "
                    + "FROM " + table + " r1 "
                    + "LEFT JOIN " + table + " r2 "
                    + "ON r1.remarkNo + 1 = r2.remarkNo "
                    + "WHERE r2.remarkNo IS NULL";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                number = rs.getInt("NextNumber");
            }

            rs.close();
            stmt.close();

            return number;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public static Remarks getRemarkFromResultSet(ResultSet rs) throws SQLException
    {
        Remarks remark = new Remarks();
        remark.setIdremarks(rs.getInt("idremarks"));
        remark.setRemarkNo(rs.getInt("remarkNo"));
        remark.setRemarkName(rs.getString("remarkName"));
        remark.setRemarkAbbr(rs.getString("remarkAbbr"));
        Integer remarkType = rs.getInt("remarkType");
        if (remarkType == 0)
        {
            remarkType = null;
        }
        remark.setRemarkType(remarkType);
        remark.setRemarkText(rs.getBytes("remarkText"));
        remark.setIsAbnormal(rs.getBoolean("isAbnormal"));
        Integer remarkDepartment = rs.getInt("remarkDepartment");
        if (remarkDepartment == 0)
        {
            remarkDepartment = null;
        }
        remark.setRemarkDepartment(remarkDepartment);
        remark.setNoCharge(rs.getBoolean("noCharge"));
        
        remark.setTestId(rs.getInt("testId"));

        return remark;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertRemark((Remarks) obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RemarkDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateRemark((Remarks) obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RemarkDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        return GetRemarkFromID(ID);
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `remarks`.`idremarks`,\n"
                + "    `remarks`.`remarkNo`,\n"
                + "    `remarks`.`remarkName`,\n"
                + "    `remarks`.`remarkAbbr`,\n"
                + "    `remarks`.`remarkType`,\n"
                + "    `remarks`.`remarkText`,\n"
                + "    `remarks`.`isAbnormal`,\n"
                + "    `remarks`.`remarkDepartment`,\n"
                + "    `remarks`.`noCharge`,\n"
                + "    `remarks`.`testId`,\n"
                + "    `remarks`.`active`\n"
                + "FROM `css`.`remarks` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
