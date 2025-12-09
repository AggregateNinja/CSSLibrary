package EMR.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import EMR.DOS.EmrOrdDiag;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date:   Jun 4, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DAOS
 * @file name: EmrOrdDiagDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class EmrOrdDiagDAO implements DAOInterface, IStructureCheckable
{
    EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrOrdDiag`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public EmrOrdDiagDAO()
    {
        fields.add("id");
        fields.add("idOrders");
        fields.add("DiagnosisCodes");
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
            EmrOrdDiag ordDiag = (EmrOrdDiag)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromEmrOrdDiag(ordDiag, pStmt);
            
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
            EmrOrdDiag ordDiag = (EmrOrdDiag)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + ordDiag.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromEmrOrdDiag(ordDiag, pStmt);
            
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
            return false;
        }
        
        return false;
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
            EmrOrdDiag ordDiag = new EmrOrdDiag();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetEmrOrdDiagFromResultSet(ordDiag, rs);
            }

            rs.close();
            stmt.close();

            return ordDiag;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public List<EmrOrdDiag> getByOrderID(Integer ID)
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
            ArrayList<EmrOrdDiag> list = new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                EmrOrdDiag ordDiag = new EmrOrdDiag();
                SetEmrOrdDiagFromResultSet(ordDiag, rs);
                list.add(ordDiag);
            }

            rs.close();
            stmt.close();

            return list;
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
    
    private EmrOrdDiag SetEmrOrdDiagFromResultSet(EmrOrdDiag obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setDiagnosisCodes(rs.getString("DiagnosisCodes"));
        return obj;
    }
    
    private PreparedStatement SetStatementFromEmrOrdDiag(EmrOrdDiag obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdOrders());
        pStmt.setString(2, obj.getDiagnosisCodes());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
