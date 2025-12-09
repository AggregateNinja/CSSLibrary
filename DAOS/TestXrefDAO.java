/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.TestXref;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 02/27/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class TestXrefDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`testXref`";

    /*
     * All fields except idtestXrefs
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public TestXrefDAO()
    {
        fields.add("name");
        fields.add("testNumber");
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

    private void CheckDBConnection()
    {
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }

    public boolean InsertTestXref(TestXref xref)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, xref.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getTestNumber());
            SQLUtil.SafeSetString(pStmt, 3, xref.getTransformedIn());
            SQLUtil.SafeSetString(pStmt, 4, xref.getTransformedOut());
            SQLUtil.SafeSetString(pStmt, 5, xref.getUse1());
            SQLUtil.SafeSetString(pStmt, 6, xref.getUse2());
            SQLUtil.SafeSetString(pStmt, 7, xref.getUse3());
            SQLUtil.SafeSetString(pStmt, 8, xref.getUse4());
            SQLUtil.SafeSetString(pStmt, 9, xref.getUse5());
            SQLUtil.SafeSetString(pStmt, 10, xref.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(xref.getCreated()));
            SQLUtil.SafeSetBoolean(pStmt, 12, Boolean.TRUE);

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    /**
     * Inserts the provided test cross reference object and returns a copy
     *  with the newly created database identifier.
     * @param testXref
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public TestXref InsertTestXrefGetObject(TestXref testXref)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (testXref == null) throw new IllegalArgumentException(
                "TestXrefDAO::InsertTestXrefGetObject:"
                        + " Received a [NULL] testXref object argument");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        SQLUtil.SafeSetInteger(pStmt, 1, testXref.getName());
        SQLUtil.SafeSetInteger(pStmt, 2, testXref.getTestNumber());
        SQLUtil.SafeSetString(pStmt, 3, testXref.getTransformedIn());
        SQLUtil.SafeSetString(pStmt, 4, testXref.getTransformedOut());
        SQLUtil.SafeSetString(pStmt, 5, testXref.getUse1());
        SQLUtil.SafeSetString(pStmt, 6, testXref.getUse2());
        SQLUtil.SafeSetString(pStmt, 7, testXref.getUse3());
        SQLUtil.SafeSetString(pStmt, 8, testXref.getUse4());
        SQLUtil.SafeSetString(pStmt, 9, testXref.getUse5());
        SQLUtil.SafeSetString(pStmt, 10, testXref.getDescription());
        SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(testXref.getCreated()));
        SQLUtil.SafeSetBoolean(pStmt, 12, Boolean.TRUE);
        
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String errorMsg = "CptCodeDAO::Insert: Row was not inserted!"
                    + "Code being inserted was:" + testXref.getTestNumber();
            throw new SQLException(errorMsg);
        }
        
        Integer newId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                newId = generatedKeys.getInt(1);
            }
            else
            {
                String code = "[NULL]";
                throw new SQLException("CptCodeDAO::Insert: Insert failed!"
                        + "No unique identifier returned."
                        + " Code being inserted was:" + testXref.getTestNumber());
            }
        }
        testXref.setIdtestXref(newId);
        return testXref;
    }

    public boolean UpdateTestXref(TestXref xref)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idtestXref` = " + xref.getIdtestXref();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, xref.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, xref.getTestNumber());
            SQLUtil.SafeSetString(pStmt, 3, xref.getTransformedIn());
            SQLUtil.SafeSetString(pStmt, 4, xref.getTransformedOut());
            SQLUtil.SafeSetString(pStmt, 5, xref.getUse1());
            SQLUtil.SafeSetString(pStmt, 6, xref.getUse2());
            SQLUtil.SafeSetString(pStmt, 7, xref.getUse3());
            SQLUtil.SafeSetString(pStmt, 8, xref.getUse4());
            SQLUtil.SafeSetString(pStmt, 9, xref.getUse5());
            SQLUtil.SafeSetString(pStmt, 10, xref.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(xref.getCreated()));
            SQLUtil.SafeSetBoolean(pStmt, 12, xref.isActive());

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }

    public TestXref GetTestXrefByID(int id)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idtestXref` = " + id
                    + " AND `active` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetXrefFromResultSet(xref, rs);
            }
            rs.close();
            stmt.close();
            return xref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public TestXref GetTestXrefByTestNumAndXrefID(int num, int id)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `testNumber` = " + num
                    + " AND `name` = " + id
                    + " AND `active` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetXrefFromResultSet(xref, rs);
            }
            rs.close();
            stmt.close();
            return xref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public TestXref GetTestXrefByResultID(int idResults, int name)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            Statement stmt = con.createStatement();

            String query = "SELECT tx.* "
                    + "FROM " + table + " tx "
                    + "INNER JOIN tests t ON tx.testNumber = t.number "
                    + "INNER JOIN results r ON t.idtests = r.testId "
                    + "WHERE r.`idResults` = " + idResults + " " 
                    + "AND tx.`name` = " + name + " "
                    + "AND tx.`active` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetXrefFromResultSet(xref, rs);
            }
            rs.close();
            stmt.close();
            return xref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<TestXref> GetAllTestXrefsByName(int name)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            ArrayList<TestXref> list = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = " + name
                    + " AND `active` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                xref = new TestXref();
                SetXrefFromResultSet(xref, rs);
                list.add(xref);
            }
            rs.close();
            stmt.close();
            return list;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ResultSet GetRSTestXrefsByName(int name)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            ArrayList<TestXref> list = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = " + name
                    + " AND `active` = " + true;

            ResultSet rs = stmt.executeQuery(query);

            return rs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public TestXref GetTestXrefByTransInAndXrefId(String in, int id)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            TestXref tstXref = new TestXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `transformedIn` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            SQLUtil.SafeSetString(pStmt, 2, in);
            pStmt.setBoolean(3, true);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(tstXref, rs);
            }

            rs.close();
            pStmt.close();

            return tstXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public TestXref GetTestXrefByTransInAndName(String in, String name)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            TestXref tstXref = new TestXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?"
                    + " AND `active` = ?";
            
            if (in != null) {
                query += " AND `transformedIn` = ?";
            } else {
                query += " AND `transformedIn` IS NULL";
            }
            
            PreparedStatement pStmt = con.prepareStatement(query);
            //pStmt.setInt(1, id);
            SQLUtil.SafeSetString(pStmt, 1, name);
            pStmt.setBoolean(2, true);
            
            if (in != null) {
                SQLUtil.SafeSetString(pStmt, 3, in);
            }            
            
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(tstXref, rs);
            }

            rs.close();
            pStmt.close();

            return tstXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
        
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

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

    public ArrayList<TestXref> SeachByInterfaceName(int name, String test)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            ArrayList<TestXref> list = new ArrayList<>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = " + name
                    + " AND `active` = " + true
                    + " AND `transformedIn` LIKE ?";

            String testParam = SQLUtil.createSearchParam(test);
            stmt = createStatement(con, query, testParam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                xref = new TestXref();
                xref = SetXrefFromResultSet(xref, rs);
                list.add(xref);
            }

            rs.close();
            stmt.close();

            return list;
        }
        catch (Exception ex)
        {
            System.out.println("Exception Searching By INst/Name: " + ex.toString());
            return null;
        }
    }

    public TestXref GetTestXrefByNameAndInterface(int name, String test)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

        try
        {
            TestXref xref = new TestXref();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `name` = " + name
                    + " AND `active` = " + true
                    + " AND `transformedIn` = ?";

            stmt = createStatement(con, query, test);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                xref = new TestXref();
                xref = SetXrefFromResultSet(xref, rs);
            }

            rs.close();
            stmt.close();

            return xref;
        }
        catch (Exception ex)
        {
            System.out.println("Exception Searching By INst/Name: " + ex.toString());
            return null;
        }
    }
    
    public TestXref get(Integer name, Integer testNumber, String transformedIn, String transformedOut, String description)
            throws SQLException, NullPointerException
    {
        
        Connection con = Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table
                + " WHERE `name` = ?"
                + " AND `testNumber` = ?"
                + " AND `transformedIn` = ?"
                + " AND `transformedOut` = ?"
                + " AND `description` = ?"
                + " AND `active` = b'1';";
        
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.setInt(1, name);
        pStmt.setInt(2, testNumber);
        pStmt.setString(3, transformedIn);
        pStmt.setString(4, transformedOut);
        pStmt.setString(5, description);
        
        ResultSet rs = pStmt.executeQuery();
        TestXref testXref = null;
        if (rs.next())
        {
            testXref = SetXrefFromResultSet(new TestXref(),rs);
        }
        if (testXref != null && (testXref.getIdtestXref() == null || testXref.getIdtestXref() <= 0)) testXref = null;
        return testXref;
    }
    
    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        try
        {
            if (con.isClosed())
            {
                CheckDBConnection();
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
        }

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
    
    
    public void Delete(TestXref testXref) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (testXref == null || testXref.getIdtestXref() == null || testXref.getIdtestXref() <= 0)
        {
            throw new IllegalArgumentException("TestXrefDAO::Delete: Received a NULL TestXref object argument");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = "DELETE FROM " + table + " WHERE idtestXref = " + testXref.getIdtestXref().toString();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.execute();
    }
    

    public TestXref SetXrefFromResultSet(TestXref xref, ResultSet rs) throws SQLException
    {
        xref.setIdtestXref(rs.getInt("idtestXref"));
        xref.setName(rs.getInt("name"));
        xref.setTestNumber(rs.getInt("testNumber"));
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

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
