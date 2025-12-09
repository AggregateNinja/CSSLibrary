package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcResultViolation;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @date:   Jun 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: QcResultViolationDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QcResultViolationDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcResultViolation`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public QcResultViolationDAO()
    {
        fields.add("idqcResults");
        fields.add("idqcRules");
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
            QcResultViolation qcrv = (QcResultViolation)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setInt(1, qcrv.getIdqcResults());
            pStmt.setInt(2, qcrv.getIdqcRules());
            
            try
            {
                pStmt.executeUpdate();
            }
            catch (Exception ex)
            {
                boolean isAutoIncrement = CheckIDColumnAutoIncrement();
                if (!isAutoIncrement)
                {
                    UpdateIDColumnAutoIncrement();
                    pStmt.executeUpdate();
                }
            }
            
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
            QcResultViolation qcrv = (QcResultViolation)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + qcrv.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setInt(1, qcrv.getIdqcResults());
            pStmt.setInt(2, qcrv.getIdqcRules());
            pStmt.setInt(3, qcrv.getId());
            
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
            QcResultViolation qcrv = new QcResultViolation();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcResultViolationFromResultSet(qcrv, rs);
            }

            rs.close();
            stmt.close();
            
            return qcrv;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public QcResultViolation GetByResultId(int idqcResults)
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
            QcResultViolation qcrv = new QcResultViolation();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idqcResults` = " + idqcResults;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcResultViolationFromResultSet(qcrv, rs);
            }

            rs.close();
            stmt.close();
            
            return qcrv;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcResultViolation> GetViolationsByID(int idqcResults)
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
            ArrayList<QcResultViolation> qcrList = new ArrayList<>();
            Statement stmt = con.createStatement();

            
            String query = "SELECT * FROM " + table
                    + " WHERE `idqcResults` = " + idqcResults;

            ResultSet rs = stmt.executeQuery(query);

            Integer id = null;
            while (rs.next())
            {
                QcResultViolation qcrv = new QcResultViolation();
                SetQcResultViolationFromResultSet(qcrv, rs);
                qcrList.add(qcrv);
            }

            rs.close();
            stmt.close();
            
            return qcrList;
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
        String stmt = "INSERT INTO " + table + " (";
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

    public QcResultViolation SetQcResultViolationFromResultSet(QcResultViolation obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdqcResults(rs.getInt("idqcResults"));
        obj.setIdqcRules(rs.getInt("idqcRules"));
        return obj;
    }
    
    public boolean CheckIDColumnAutoIncrement()
    {
        boolean isAutoIncrement = false;
        try
        {
            String stmt = "SHOW COLUMNS FROM " + table + " WHERE Field = 'id'";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next())
            {
                String extra = rs.getString("Extra");
                isAutoIncrement = extra.contains("auto_increment");
            }
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
        }
        return isAutoIncrement;
    }
    
    public void UpdateIDColumnAutoIncrement()
    {
        try
        {
            String stmt = "ALTER TABLE " + table + " MODIFY COLUMN `id` INT(10) AUTO_INCREMENT";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            int update = pStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
        }
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
