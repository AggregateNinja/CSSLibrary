/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InterpVariables;
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
public class InterpVariablesDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`interpVariables`";
    private final List<String> fields = new ArrayList<String>();

    public InterpVariablesDAO()
    {
        fields.add("name");
        fields.add("isDemographic");
        fields.add("inactive");
        fields.add("inactivedBy");
        fields.add("inactivedDate");
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
            System.out.println("InterpVariablesDAO::Insert - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpVariables interpVariables = (InterpVariables) obj;
            String stmt = getInsertStatement();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpVariables, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpVariablesDAO::Insert - Exception - " + ex.toString());
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
            System.out.println("InterpVariablesDAO::Update - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpVariables interpVariables = (InterpVariables) obj;
            String stmt = getUpdateStatement() + " WHERE `idInterpVariables` = " + interpVariables.getIdInterpVariables();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpVariables, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpVariablesDAO::Update - Exception - " + ex.toString());
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
            System.out.println("InterpVariablesDAO::Delete - SQLException - " + ex.toString());
            return false;
        }
        try
        {
            InterpVariables interpVariables = (InterpVariables) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idInterpVariables` = " + interpVariables.getIdInterpVariables();

            int result = stmt.executeUpdate(query);

            stmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println("InterpVariablesDAO::getByID - Exception - " + ex.toString());
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
            System.out.println("InterpVariablesDAO::getByID - SQLException - " + ex.toString());
            return null;
        }

        try
        {
            InterpVariables interpVariables = new InterpVariables();
            String query = "SELECT * FROM " + table
                    + " WHERE `idInterpVariables` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpVariablesFromResultSet(interpVariables, rs);
            }

            rs.close();
            pStmt.close();

            return interpVariables;
        }
        catch (Exception ex)
        {
            System.out.println("InterpVariablesDAO::getByID - Exception - " + ex.toString());
            return null;
        }
    }

    public List<InterpVariables> getAllActive(boolean active)
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
            System.out.println("InterpVariablesDAO::getAll - SQLException - " + ex.toString());
            return null;
        }
        
        List<InterpVariables> interpVariablesList = new ArrayList<InterpVariables>();
        try
        {
            InterpVariables interpVariables;
            String query = "SELECT * FROM " + table
                    + " WHERE `inactive` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, !active);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                interpVariables = new InterpVariables();
                setInterpVariablesFromResultSet(interpVariables, rs);
                interpVariablesList.add(interpVariables);
            }

            rs.close();
            pStmt.close();

            return interpVariablesList;
        }
        catch (Exception ex)
        {
            System.out.println("InterpVariablesDAO::getByID - Exception - " + ex.toString());
            return interpVariablesList;
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

    private InterpVariables setInterpVariablesFromResultSet(InterpVariables interpVariables, ResultSet rs) throws SQLException
    {
        interpVariables.setIdInterpVariables(rs.getInt("idInterpVariables"));
        interpVariables.setName(rs.getString("name"));
        interpVariables.setIsDemographic(rs.getBoolean("isDemographic"));
        interpVariables.setInactive(rs.getBoolean("inactive"));
        interpVariables.setInactivatedBy(rs.getInt("inactivatedBy"));
        interpVariables.setInactivatedDate(rs.getDate("inactivatedDate"));

        return interpVariables;
    }

    private void setStatement(InterpVariables interpVariables, PreparedStatement pStmt)
    {
        try
        {
            SQLUtil.SafeSetString(pStmt, 1, interpVariables.getName());
            SQLUtil.SafeSetBoolean(pStmt, 2, interpVariables.isDemographic());
            SQLUtil.SafeSetBoolean(pStmt, 3, interpVariables.isInactive());
            SQLUtil.SafeSetInteger(pStmt, 4, interpVariables.getInactivatedBy());
            SQLUtil.SafeSetDate(pStmt, 5, interpVariables.getInactivatedDate());
        }
        catch (SQLException ex)
        {
            System.out.println("InterpVariablesDAO::setStatement - SQLException - " + ex.toString());
        }
    }
  
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
