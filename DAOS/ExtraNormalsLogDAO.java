/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 13, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ExtraNormalsLog;
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
 * @date:   Jun 13, 2014  3:01:08 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ExtraNormalsLogDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class ExtraNormalsLogDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`extranormals_log`";
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public ExtraNormalsLogDAO(){

        fields.add("eventType");
        fields.add("changeType");
        fields.add("testNumber");
        fields.add("testName");
        fields.add("idtests");
        fields.add("oldIdExtraNormals");
        fields.add("newIdExtraNormals");
        fields.add("species");
        fields.add("lowNormal");
        fields.add("highNormal");
        fields.add("alertLow");
        fields.add("alertHigh");
        fields.add("criticalLow");
        fields.add("criticalHigh");
        fields.add("ageLow");
        fields.add("ageUnitsLow");
        fields.add("ageHigh");
        fields.add("ageUnitsHigh");
        fields.add("sex");
        fields.add("printNormals");
        fields.add("active");
        fields.add("deactivatedDate");
        fields.add("user");
        fields.add("created");
    }
    
    @Override
    public Boolean Insert(Serializable obj) {
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
        
        try{
            ExtraNormalsLog log = (ExtraNormalsLog)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(log, pStmt);
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println("Exception Inserting ExtraNormals Log: " +ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
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
            ExtraNormalsLog log = (ExtraNormalsLog)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + log.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(log, pStmt);
            
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
    public Boolean Delete(Serializable obj) {
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
    public Serializable getByID(Integer ID) {
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
            ExtraNormalsLog log = new ExtraNormalsLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idq` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetExtraNormalsLogFromResultSet(log, rs);
            }

            rs.close();
            stmt.close();
            
            return log;
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
    
    public ExtraNormalsLog SetExtraNormalsLogFromResultSet(ExtraNormalsLog obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setEventType(rs.getString("EventType"));
        obj.setChangeType(rs.getString("ChangeType"));
        obj.setTestNumber(rs.getInt("testNumber"));
        obj.setTestName(rs.getString("testName"));
        obj.setIdtests(rs.getInt("idtests"));
        obj.setOldIdExtraNormals(rs.getInt("oldIdExtraNormals"));
        obj.setNewIdExtraNormals(rs.getInt("newIdExtraNormals"));
        obj.setSpecies(rs.getInt("species"));
        obj.setLowNormal(rs.getDouble("lowNormal"));
        obj.setHighNormal(rs.getDouble("highNormal"));
        obj.setAlertLow(rs.getDouble("alertLow"));
        obj.setAlertHigh(rs.getDouble("alertHigh"));
        obj.setCriticalLow(rs.getDouble("criticalLow"));
        obj.setCriticalHigh(rs.getDouble("criticalHigh"));
        obj.setAgeLow(rs.getInt("ageLow"));
        obj.setAgeUnitsLow(rs.getInt("ageUnitsLow"));
        obj.setAgeHigh(rs.getInt("ageHigh"));
        obj.setAgeUnitsHigh(rs.getInt("ageUnitsHigh"));
        obj.setSex(rs.getString("sex"));
        obj.setPrintNormals(rs.getString("printNormals"));
        obj.setActive(rs.getBoolean("active"));
        obj.setDeactivatedDate(rs.getTimestamp("deactivatedDate"));
        obj.setUser(rs.getInt(rs.getInt("user")));
        obj.setCreated(rs.getTimestamp("created"));
        
        return obj;
    }

    private PreparedStatement SetStatement(ExtraNormalsLog obj, PreparedStatement pStmt) throws SQLException
    {
        SQLUtil.SafeSetString(pStmt, 1, obj.getEventType());
        SQLUtil.SafeSetString(pStmt, 2, obj.getChangeType());
        pStmt.setInt(3, obj.getTestNumber());
        SQLUtil.SafeSetString(pStmt, 4, obj.getTestName());
        pStmt.setInt(5,obj.getIdtests());
        pStmt.setInt(6,obj.getOldIdExtraNormals());
        pStmt.setInt(7,obj.getNewIdExtraNormals());
        pStmt.setInt(8,obj.getSpecies());
        pStmt.setDouble(9, obj.getLowNormal());
        pStmt.setDouble(10,obj.getHighNormal());
        pStmt.setDouble(11,obj.getAlertLow());
        pStmt.setDouble(12,obj.getAlertHigh());
        pStmt.setDouble(13,obj.getCriticalLow());
        pStmt.setDouble(14,obj.getCriticalHigh());
        pStmt.setInt(15,obj.getAgeLow());
        pStmt.setInt(16,obj.getAgeUnitsLow());
        pStmt.setInt(17,obj.getAgeHigh());
        pStmt.setInt(18,obj.getAgeUnitsHigh());
        SQLUtil.SafeSetString(pStmt, 19, obj.getSex());
        SQLUtil.SafeSetString(pStmt, 20,obj.getPrintNormals());
        SQLUtil.SafeSetBoolean(pStmt, 21, obj.getActive());
        SQLUtil.SafeSetTimeStamp(pStmt, 22, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
        pStmt.setInt(23,obj.getUser());
        SQLUtil.SafeSetTimeStamp(pStmt, 24, Convert.ToSQLDateTime(obj.getCreated()));
        
        return pStmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
