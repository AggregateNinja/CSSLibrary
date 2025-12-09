/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InterpValues;
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
public class InterpValuesDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`interpValues`";
    private final List<String> fields = new ArrayList<String>();

    public InterpValuesDAO()
    {
        fields.add("name");
        fields.add("variableId");
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
            System.out.println("InterpValuesDAO::Insert - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpValues interpValues = (InterpValues) obj;
            String stmt = getInsertStatement();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpValues, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::Insert - Exception - " + ex.toString());
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
            System.out.println("InterpValuesDAO::Update - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpValues interpValues = (InterpValues) obj;
            String stmt = getUpdateStatement() + " WHERE `idInterpValues` = " + interpValues.getIdInterpValues();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpValues, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::Update - Exception - " + ex.toString());
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
            System.out.println("InterpValuesDAO::Delete - SQLException - " + ex.toString());
            return false;
        }
        try
        {
            InterpValues interpValues = (InterpValues) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idInterpValues` = " + interpValues.getIdInterpValues();

            int result = stmt.executeUpdate(query);

            stmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::getByID - Exception - " + ex.toString());
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
            System.out.println("InterpValuesDAO::getByID - SQLException - " + ex.toString());
            return null;
        }

        try
        {
            InterpValues interpValues = new InterpValues();
            String query = "SELECT * FROM " + table
                    + " WHERE `idInterpValues` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpValuesFromResultSet(interpValues, rs);
            }

            rs.close();
            pStmt.close();

            return interpValues;
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::getByID - Exception - " + ex.toString());
            return null;
        }
    }

    public List<InterpValues> getAll()
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
            System.out.println("InterpValuesDAO::getAll - SQLException - " + ex.toString());
            return null;
        }
        
        List<InterpValues> interpValuesList = new ArrayList<InterpValues>();
        try
        {
            InterpValues interpValues;
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                interpValues = new InterpValues();
                setInterpValuesFromResultSet(interpValues, rs);
                interpValuesList.add(interpValues);
            }

            rs.close();
            pStmt.close();

            return interpValuesList;
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::getByID - Exception - " + ex.toString());
            return interpValuesList;
        }
    }
    
    public List<InterpValues> getAllByVariableId(int variableId)
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
            System.out.println("InterpValuesDAO::getAll - SQLException - " + ex.toString());
            return null;
        }
        
        List<InterpValues> interpValuesList = new ArrayList<InterpValues>();
        try
        {
            InterpValues interpValues;
            String query = "SELECT * FROM " + table
                    + " WHERE `variableId` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, variableId);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                interpValues = new InterpValues();
                setInterpValuesFromResultSet(interpValues, rs);
                interpValuesList.add(interpValues);
            }

            rs.close();
            pStmt.close();

            return interpValuesList;
        }
        catch (Exception ex)
        {
            System.out.println("InterpValuesDAO::getByID - Exception - " + ex.toString());
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

    private InterpValues setInterpValuesFromResultSet(InterpValues interpValues, ResultSet rs) throws SQLException
    {
        interpValues.setIdInterpValues(rs.getInt("idInterpValues"));
        interpValues.setName(rs.getString("name"));
        interpValues.setVariableId(rs.getInt("variableId"));

        return interpValues;
    }

    private void setStatement(InterpValues interpValues, PreparedStatement pStmt)
    {
        try
        {
            SQLUtil.SafeSetString(pStmt, 1, interpValues.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, interpValues.getVariableId());
        }
        catch (SQLException ex)
        {
            System.out.println("InterpValuesDAO::setStatement - SQLException - " + ex.toString());
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
