/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InterpRules;
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
 * @date Dec 24, 2015
 */
public class InterpRulesDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`interpRules`";
    private final List<String> fields = new ArrayList<String>();

    public InterpRulesDAO()
    {
        fields.add("name");
        fields.add("number");
        fields.add("inactive");
        fields.add("inactivedBy");
        fields.add("inactivedDate");
        fields.add("ruleOrder");
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
            System.out.println("InterpRulesDAO::Insert - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpRules interpRules = (InterpRules) obj;
            String stmt = getInsertStatement();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpRules, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::Insert - Exception - " + ex.toString());
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
            System.out.println("InterpRulesDAO::Update - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpRules interpRules = (InterpRules) obj;
            String stmt = getUpdateStatement() + " WHERE `idInterpRules` = " + interpRules.getIdInterpRules();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpRules, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::Update - Exception - " + ex.toString());
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
            System.out.println("InterpRulesDAO::Delete - SQLException - " + ex.toString());
            return false;
        }
        try
        {
            InterpRules interpRules = (InterpRules) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idInterpRules` = " + interpRules.getIdInterpRules();

            int result = stmt.executeUpdate(query);

            stmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::getByID - Exception - " + ex.toString());
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
            InterpRules interpRules = new InterpRules();
            String query = "SELECT * FROM " + table
                    + " WHERE `idInterpRules` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpRulesFromResultSet(interpRules, rs);
            }

            rs.close();
            pStmt.close();

            return interpRules;
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::getByID - Exception - " + ex.toString());
            return null;
        }
    }

    public Serializable getByNumber(int number)
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
            InterpRules interpRules = new InterpRules();
            String query = "SELECT * FROM " + table
                    + " WHERE `number` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, number);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpRulesFromResultSet(interpRules, rs);
            }

            rs.close();
            pStmt.close();

            return interpRules;
        }
        catch (Exception ex)
        {
            System.out.println("InterpRulesDAO::getByNumber - Exception - " + ex.toString());
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

    private InterpRules setInterpRulesFromResultSet(InterpRules interpRules, ResultSet rs) throws SQLException
    {
        interpRules.setIdInterpRules(rs.getInt("idInterpRules"));
        interpRules.setNumber(rs.getInt("number"));
        interpRules.setName(rs.getString("name"));
        interpRules.setInactive(rs.getBoolean("inactive"));
        interpRules.setInactivatedBy(rs.getInt("inactivatedBy"));
        interpRules.setInactivatedDate(rs.getDate("inactivatedDate"));
        interpRules.setRuleOrder(rs.getInt("ruleOrder"));

        return interpRules;
    }

    private void setStatement(InterpRules interpRules, PreparedStatement pStmt)
    {
        try
        {
            SQLUtil.SafeSetString(pStmt, 1, interpRules.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, interpRules.getNumber());
            SQLUtil.SafeSetBoolean(pStmt, 3, interpRules.isInactive());
            SQLUtil.SafeSetInteger(pStmt, 4, interpRules.getInactivatedBy());
            SQLUtil.SafeSetDate(pStmt, 5, interpRules.getInactivatedDate());
            SQLUtil.SafeSetInteger(pStmt, 6, interpRules.getRuleOrder());
        }
        catch (SQLException ex)
        {
            System.out.println("InterpRulesDAO::setStatement - SQLException - " + ex.toString());
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
