/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.InterpOperators;
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
public class InterpOperatorsDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`interpOperators`";
    private final List<String> fields = new ArrayList<String>();

    public InterpOperatorsDAO()
    {
        fields.add("name");
        fields.add("isLogical");
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
            System.out.println("InterpOperatorsDAO::Insert - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpOperators interpOperators = (InterpOperators) obj;
            String stmt = getInsertStatement();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpOperators, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpOperatorsDAO::Insert - Exception - " + ex.toString());
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
            System.out.println("InterpOperatorsDAO::Update - SQLException - " + ex.toString());
            return false;
        }

        try
        {
            InterpOperators interpOperators = (InterpOperators) obj;
            String stmt = getUpdateStatement() + " WHERE `idInterpOperators` = " + interpOperators.getIdInterpOperators();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            setStatement(interpOperators, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("InterpOperatorsDAO::Update - Exception - " + ex.toString());
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
            System.out.println("InterpOperatorsDAO::Delete - SQLException - " + ex.toString());
            return false;
        }
        try
        {
            InterpOperators interpOperators = (InterpOperators) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idInterpOperators` = " + interpOperators.getIdInterpOperators();

            int result = stmt.executeUpdate(query);

            stmt.close();

            return true;
        }
        catch (Exception ex)
        {
            System.out.println("InterpOperatorsDAO::getByID - Exception - " + ex.toString());
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
            System.out.println("InterpOperatorsDAO::getByID - SQLException - " + ex.toString());
            return null;
        }

        try
        {
            InterpOperators interpOperators = new InterpOperators();
            String query = "SELECT * FROM " + table
                    + " WHERE `idInterpOperators` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                setInterpOperatorsFromResultSet(interpOperators, rs);
            }

            rs.close();
            pStmt.close();

            return interpOperators;
        }
        catch (Exception ex)
        {
            System.out.println("InterpOperatorsDAO::getByID - Exception - " + ex.toString());
            return null;
        }
    }
    
    public List<InterpOperators> getAllActive(boolean active)
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
            System.out.println("InterpOperatorsDAO::getAll - SQLException - " + ex.toString());
            return null;
        }
        
        List<InterpOperators> interpOperatorsList = new ArrayList<InterpOperators>();
        try
        {
            InterpOperators interpOperators;
            String query = "SELECT * FROM " + table
                    + " WHERE `inactive` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, !active);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                interpOperators = new InterpOperators();
                setInterpOperatorsFromResultSet(interpOperators, rs);
                interpOperatorsList.add(interpOperators);
            }

            rs.close();
            pStmt.close();

            return interpOperatorsList;
        }
        catch (Exception ex)
        {
            System.out.println("InterpOperatorsDAO::getByID - Exception - " + ex.toString());
            return interpOperatorsList;
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

    private InterpOperators setInterpOperatorsFromResultSet(InterpOperators interpOperators, ResultSet rs) throws SQLException
    {
        interpOperators.setIdInterpOperators(rs.getInt("idInterpOperators"));
        interpOperators.setName(rs.getString("name"));
        interpOperators.setIsLogical(rs.getBoolean("isLogical"));
        interpOperators.setInactive(rs.getBoolean("inactive"));
        interpOperators.setInactivatedBy(rs.getInt("inactivatedBy"));
        interpOperators.setInactivatedDate(rs.getDate("inactivatedDate"));

        return interpOperators;
    }

    private void setStatement(InterpOperators interpOperators, PreparedStatement pStmt)
    {
        try
        {
            SQLUtil.SafeSetString(pStmt, 1, interpOperators.getName());
            SQLUtil.SafeSetBoolean(pStmt, 2, interpOperators.isLogical());
            SQLUtil.SafeSetBoolean(pStmt, 3, interpOperators.isInactive());
            SQLUtil.SafeSetInteger(pStmt, 4, interpOperators.getInactivatedBy());
            SQLUtil.SafeSetDate(pStmt, 5, interpOperators.getInactivatedDate());
        }
        catch (SQLException ex)
        {
            System.out.println("InterpOperatorsDAO::setStatement - SQLException - " + ex.toString());
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
