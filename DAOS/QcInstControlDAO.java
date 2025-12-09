package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcInstControls;
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
 * @date:   Jun 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: QcInstControlDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QcInstControlDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcInstControls`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public QcInstControlDAO()
    {
        fields.add("idInst");
        fields.add("idqcLot");
        fields.add("level");
        fields.add("createdBy");
        fields.add("created");
        fields.add("updatedBy");
        fields.add("updatedDate");
        fields.add("specimenType");
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
            QcInstControls qcic = (QcInstControls)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(qcic, pStmt);
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
            QcInstControls qcic = (QcInstControls)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + qcic.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(qcic, pStmt);
            
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
    
    public Boolean InsertUpdate(QcInstControls qic){
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

        try {
            String stmt = "INSERT INTO `qcInstControls` ( "
                    + "`idInst`, "
                    + "`idqcLot`, "
                    + "`level`, "
                    + "`createdBy`, "
                    + "`created`, "
                    + "`updatedBy`, "
                    + "`updatedDate`, "
                    + "`specimenType` "
                    + ") VALUES ( " + qic.getIdInst() + ", "
                    + qic.getIdqcLot() + ", "
                    + qic.getLevel() + ", "
                    + qic.getCreatedBy() + ", '"
                    + Convert.ToSQLDateTime(qic.getCreated()) + "', "
                    + qic.getUpdatedBy() + ", '"
                    + Convert.ToSQLDateTime(qic.getUpdatedDate()) + "', "
                    + qic.getSpecimenType() + ") "
                    + "ON DUPLICATE KEY UPDATE "
                    + "`idqcLot` = " + qic.getIdqcLot() + ", "
                    + "`updatedBy` = " + qic.getUpdatedBy() + ", "
                    + "`updatedDate` = '" + Convert.ToSQLDateTime(qic.getUpdatedDate()) + "'";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (Exception ex) {
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
            QcInstControls qcic = new QcInstControls();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcInstControlsFromResultSet(qcic, rs);
            }

            rs.close();
            stmt.close();
            
            return qcic;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByInstIdAndLevel(Integer instID, Integer level)
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
            QcInstControls qcic = new QcInstControls();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idInst` = " + instID
                    + " AND `level` = " + level;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcInstControlsFromResultSet(qcic, rs);
            }

            rs.close();
            stmt.close();
            
            return qcic;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByInstIdLevelAndSpecimen(Integer instID, Integer level, Integer specimen)
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
            QcInstControls qcic = new QcInstControls();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idInst` = " + instID
                    + " AND `level` = " + level
                    + " AND `specimenType` = " + specimen;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcInstControlsFromResultSet(qcic, rs);
            }

            rs.close();
            stmt.close();
            
            return qcic;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }
    }
    
    public Serializable getByInstIdLevelAndLot(Integer instID, Integer level, Integer lot)
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
            QcInstControls qcic = new QcInstControls();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idInst` = " + instID
                    + " AND `level` = " + level
                    + " AND `idqcLot` = " + lot;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetQcInstControlsFromResultSet(qcic, rs);
            }

            rs.close();
            stmt.close();
            
            return qcic;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcInstControls> GetAllControlsForInst(Integer instId)
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
            ArrayList<QcInstControls> qcicList = new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idInst` = " + instId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                QcInstControls qcic = new QcInstControls();
                SetQcInstControlsFromResultSet(qcic, rs);
                qcicList.add(qcic);
            }

            rs.close();
            stmt.close();
            
            return qcicList;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<QcInstControls> GetAllControlsForLot(Integer lotId)
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
            ArrayList<QcInstControls> qcicList = new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idqcLot` = " + lotId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                QcInstControls qcic = new QcInstControls();
                SetQcInstControlsFromResultSet(qcic, rs);
                qcicList.add(qcic);
            }

            rs.close();
            stmt.close();
            
            return qcicList;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
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
    
    private QcInstControls SetQcInstControlsFromResultSet(QcInstControls obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdInst(rs.getInt("idInst"));
        obj.setIdqcLot(rs.getInt("idqcLot"));
        obj.setLevel(rs.getInt("level"));
        obj.setCreatedBy(rs.getInt("createdBy"));
        obj.setCreated(rs.getDate("created"));
        obj.setUpdatedBy(rs.getInt("updatedBy"));
        obj.setUpdatedDate(rs.getDate("updatedDate"));
        obj.setSpecimenType(rs.getInt("specimenType"));
        return obj;
    }
    
    private PreparedStatement SetStatement(QcInstControls obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdInst());
        pStmt.setInt(2, obj.getIdqcLot());
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getLevel());
        pStmt.setInt(4, obj.getCreatedBy());
        pStmt.setDate(5, Convert.ToSQLDate(obj.getCreated()));
        pStmt.setInt(6, obj.getUpdatedBy());
        pStmt.setDate(7, Convert.ToSQLDate(obj.getUpdatedDate()));
        pStmt.setInt(8, obj.getSpecimenType());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
    
