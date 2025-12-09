/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InterpSteps;
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
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Dec 28, 2015
 */
public class InterpStepsDAO implements DAOInterface, IStructureCheckable {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`interpSteps`";
    private final List<String> fields = new ArrayList<String>();
    
    public InterpStepsDAO()
    {
        fields.add("ruleId");
        fields.add("operatorId");
        fields.add("testNum");
        fields.add("testAbbr");
        fields.add("variableId");
        fields.add("insuranceId");
        fields.add("clientNum");
        fields.add("numericValue");
        fields.add("valueId");
        fields.add("stepOrder");
    }

    private void getDBConnection()
    {
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                getDBConnection();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("InterpStepsDAO::Insert - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpSteps interpSteps = (InterpSteps) obj;
            String stmt = getInsertStatement();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpSteps, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpStepsDAO::Insert - Exception - " + ex.toString());
            return false;
        }

        return true;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                getDBConnection();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("InterpStepsDAO::Update - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpSteps interpSteps = (InterpSteps) obj;
            String stmt = getUpdateStatement() + " WHERE `idInterpSteps` = " + interpSteps.getIdInterpSteps();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpSteps, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpStepsDAO::Update - Exception - " + ex.toString());
            return false;
        }

        return true;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                getDBConnection();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("InterpStepsDAO::Delete - SQLException - " + ex.toString());
            return false;
        }
        try
        {
            InterpSteps interpSteps = (InterpSteps) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idInterpSteps` = " + interpSteps.getIdInterpSteps();

            int result = stmt.executeUpdate(query);

            stmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println("InterpStepsDAO::getByID - Exception - " + ex.toString());
            return false;
        }
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            if (con.isClosed())
            {
                getDBConnection();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("InterpRulesDAO::getByID - SQLException - " + ex.toString());
            return null;
        }

        try
        {
            InterpSteps interpSteps = new InterpSteps();
            String query = "SELECT * FROM " + table
                    + " WHERE `idInterpSteps` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpStepsFromResultSet(interpSteps, rs);
            }

            rs.close();
            pStmt.close();

            return interpSteps;
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::getByID - Exception - " + ex.toString());
            return null;
        }
    }

    private String getUpdateStatement()
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

    private String getInsertStatement()
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

    private InterpSteps setInterpStepsFromResultSet(InterpSteps interpSteps, ResultSet rs) throws SQLException
    {
        interpSteps.setRuleId(rs.getInt("ruleId"));
        interpSteps.setOperatorId(rs.getInt("operatorId"));
        interpSteps.setTestNum(rs.getInt("testNum"));
        interpSteps.setTestAbbr(rs.getString("testAbbr"));
        interpSteps.setVariableId(rs.getInt("variableId"));
        interpSteps.setInsuranceId(rs.getInt("insuranceId"));
        interpSteps.setClientNum(rs.getInt("clientNum"));
        interpSteps.setNumericValue(rs.getDouble("numericValue"));
        interpSteps.setVariableId(rs.getInt("variableId"));
        interpSteps.setStepOrder(rs.getInt("stepOrder"));

        return interpSteps;
    }

    private void setStatement(InterpSteps interpSteps, PreparedStatement pStmt)
    {
        try
        {
            SQLUtil.SafeSetInteger(pStmt, 1, interpSteps.getRuleId());
            SQLUtil.SafeSetInteger(pStmt, 2, interpSteps.getOperatorId());
            SQLUtil.SafeSetInteger(pStmt, 3, interpSteps.getTestNum());
            SQLUtil.SafeSetString(pStmt, 4, interpSteps.getTestAbbr());
            SQLUtil.SafeSetInteger(pStmt, 5, interpSteps.getVariableId());
            SQLUtil.SafeSetInteger(pStmt, 6, interpSteps.getInsuranceId());
            SQLUtil.SafeSetInteger(pStmt, 7, interpSteps.getClientNum());
            SQLUtil.SafeSetRangeDouble(pStmt, 8, interpSteps.getNumericValue());
            SQLUtil.SafeSetInteger(pStmt, 9, interpSteps.getVariableId());
            SQLUtil.SafeSetInteger(pStmt, 10, interpSteps.getStepOrder());
        }
        catch (SQLException ex)
        {
            System.out.println("InterpStepsDAO::setStatement - SQLException - " + ex.toString());
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
