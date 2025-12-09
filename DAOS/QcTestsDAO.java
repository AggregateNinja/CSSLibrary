package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcTests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date:   Aug 25, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: QcTestsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QcTestsDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcTests`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();
    
    //--------------------------------------------------------------------------
    public QcTestsDAO()
    {
        fields.add("number");
        fields.add("reagent");
        fields.add("control1");
        fields.add("control2");
        fields.add("control3");
        fields.add("control4");
        fields.add("control5");
        fields.add("controlRuns");
    }
    
    //--------------------------------------------------------------------------
    @Override
    public Boolean Insert(Serializable obj)
    {
        QcTests qct = (QcTests)obj;
        
        try
        {
            if(con.isClosed())
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
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
            SetStatement(qct, pStmt);
            pStmt.executeUpdate();
            
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                generatedIDs.add(rs.getInt(1));
            }
            rs.close();

            pStmt.close();

            return !generatedIDs.isEmpty();
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Update(Serializable obj)
    {
        QcTests qct = (QcTests)obj;
        
        try
        {
            if(con.isClosed())
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
            String stmt = GenerateUpdateStatement(fields) + " WHERE `idqcTests` = " + qct.getIdqcTests();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(qct, pStmt);
            
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    //--------------------------------------------------------------------------
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            
            QcTests qct = new QcTests();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `idqcTests` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                FromResultSet(qct, rs);
            }
            
            rs.close();
            
            stmt.close();
            
            return qct;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public Boolean UpdateControlValue(int oldQCVID, int newQCVID)
    {
        try
        {
            if(con.isClosed())
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
            String stmt = "UPDATE " + table + " SET\n" +
                "  control1 = CASE WHEN control1 = " + oldQCVID + " THEN " + newQCVID + " ELSE control1 END,\n" +
                "  control2 = CASE WHEN control2 = " + oldQCVID + " THEN " + newQCVID + " ELSE control2 END,\n" +
                "  control3 = CASE WHEN control3 = " + oldQCVID + " THEN " + newQCVID + " ELSE control3 END,\n" +
                "  control4 = CASE WHEN control4 = " + oldQCVID + " THEN " + newQCVID + " ELSE control4 END,\n" +
                "  control5 = CASE WHEN control5 = " + oldQCVID + " THEN " + newQCVID + " ELSE control5 END";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
    }
    
    //--------------------------------------------------------------------------
    public List<QcTests> GetAllQcTests()
    {
        List<QcTests> qctList = new ArrayList<QcTests>();
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                QcTests qct = new QcTests();
                FromResultSet(qct, rs);
                qctList.add(qct);
            }
            
            rs.close();
            
            stmt.close();
            
            return qctList;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return qctList;
        }
    }
    
    //--------------------------------------------------------------------------
    public List<QcTests> GetAllForControlValue(Integer controlValue)
    {
        List<QcTests> qctList = new ArrayList<QcTests>();
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return qctList;
            }
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `control1` = " + controlValue +
                    " OR `control2` = " + controlValue +
                    " OR `control3` = " + controlValue +
                    " OR `control4` = " + controlValue +
                    " OR `control5` = " + controlValue;
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                QcTests qct = new QcTests();
                FromResultSet(qct, rs);
                qctList.add(qct);
            }
            
            rs.close();
            
            stmt.close();
            
            return qctList;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return qctList;
        }
    }
    
    //--------------------------------------------------------------------------
    public QcTests GetByTestNumber(Integer testNumber)
    {
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            QcTests qct = new QcTests();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `number` = " + testNumber;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                FromResultSet(qct, rs);
            }
            
            rs.close();
            
            stmt.close();
            
            return qct;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<QcTests> GetByReagent(Integer reagent)
    {
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            ArrayList<QcTests> qctList = new ArrayList<>();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `reagent` = " + reagent;
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                QcTests qct = new QcTests();
                FromResultSet(qct, rs);
                qctList.add(qct);
            }
            
            rs.close();
            
            stmt.close();
            
            return qctList;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public QcTests GetByReagentAndTest(Integer reagent, Integer testNumber)
    {
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            QcTests qct = new QcTests();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `reagent` = " + reagent +
                    " AND `number` = " + testNumber;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                FromResultSet(qct, rs);
            }
            
            rs.close();
            
            stmt.close();
            
            return qct;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<Integer> GetGeneratedIDs()
    {
        return generatedIDs;
    }

    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
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
    
    //--------------------------------------------------------------------------
    private QcTests FromResultSet(QcTests obj, ResultSet rs) throws SQLException
    {
        obj.setIdqcTests(rs.getInt("idqcTests"));
        obj.setNumber(rs.getInt("number"));
        obj.setReagent(rs.getInt("reagent"));
        obj.setControl1(rs.getInt("control1"));
        obj.setControl2(rs.getInt("control2"));
        obj.setControl3(rs.getInt("control3"));
        obj.setControl4(rs.getInt("control4"));
        obj.setControl5(rs.getInt("control5"));
        obj.setControlRuns(rs.getInt("controlRuns"));
        
        return obj;
    }
    
    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(QcTests obj, PreparedStatement pStmt) throws SQLException
    {
        
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getNumber());
        SQLUtil.SafeSetInteger(pStmt, 2, obj.getReagent());
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getControl1());
        SQLUtil.SafeSetInteger(pStmt, 4, obj.getControl2());
        SQLUtil.SafeSetInteger(pStmt, 5, obj.getControl3());
        SQLUtil.SafeSetInteger(pStmt, 6, obj.getControl4());
        SQLUtil.SafeSetInteger(pStmt, 7, obj.getControl5());
        SQLUtil.SafeSetInteger(pStmt, 8, obj.getControlRuns());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
