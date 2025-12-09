package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcSteps;
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

/**
 * @date:   Jun 9, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: QcStepsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QcStepsDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcSteps`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public QcStepsDAO()
    {
        fields.add("idqcRules");
        fields.add("direction");
        fields.add("operator");
        fields.add("value");
        fields.add("sign");
        fields.add("coefficient");
        fields.add("runNumber");
        fields.add("created");
        fields.add("createdBy");
    }
    
    @Override
    public Boolean Insert(Serializable obj)
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
            QcSteps step = (QcSteps)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(step, pStmt);
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
    public Boolean Update(Serializable obj)
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
            QcSteps step = (QcSteps)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idqcSteps` = " + step.getIdqcSteps();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(step, pStmt);
            
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
    public Boolean Delete(Serializable obj)
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
            QcSteps steps = (QcSteps)obj;
            Statement stmt = con.createStatement();

            String query = "DELETE FROM " + table
                    + " WHERE `idqcSteps` = " + steps.getIdqcSteps();

            int rows = stmt.executeUpdate(query);
            
            stmt.close();
            
            return rows > 0;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Serializable getByID(Integer ID)
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
            QcSteps steps = new QcSteps();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idqcSteps` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcStepsFromResultSet(steps, rs);
            }

            rs.close();
            stmt.close();
            
            return steps;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcSteps> GetStepsForRule(int ruleID)
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
            ArrayList<QcSteps> stepsList = new ArrayList<QcSteps>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idqcRules` = " + ruleID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                QcSteps steps = new QcSteps();
                SetQcStepsFromResultSet(steps, rs);
                stepsList.add(steps);
            }

            rs.close();
            stmt.close();
            
            return stepsList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
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
    
    private QcSteps SetQcStepsFromResultSet(QcSteps obj, ResultSet rs) throws SQLException
    {
        obj.setIdqcSteps(rs.getInt("idqcSteps"));
        obj.setIdqcRules(rs.getInt("idqcRules"));
        obj.setDirection(rs.getString("direction"));
        obj.setOperator(rs.getString("operator"));
        obj.setValue(rs.getDouble("value"));
        obj.setSign(rs.getString("sign"));
        obj.setCoefficient(rs.getDouble("coefficient"));
        obj.setRunNumber(rs.getInt("runNumber"));
        obj.setCreated(rs.getDate("created"));
        obj.setCreatedBy(rs.getInt("createdBy"));
        
        return obj;
    }
    
    private PreparedStatement SetStatement(QcSteps obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdqcRules());
        SQLUtil.SafeSetString(pStmt, 2, obj.getDirection());
        SQLUtil.SafeSetString(pStmt, 3, obj.getOperator());
        SQLUtil.SafeSetDouble(pStmt, 4, obj.getValue());
        SQLUtil.SafeSetString(pStmt, 5, obj.getSign());
        SQLUtil.SafeSetDouble(pStmt, 6, obj.getCoefficient());
        SQLUtil.SafeSetInteger(pStmt, 7, obj.getRunNumber());
        SQLUtil.SafeSetTimeStamp(pStmt, 8, Convert.ToSQLDate(obj.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, 9, obj.getCreatedBy());
        return pStmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
