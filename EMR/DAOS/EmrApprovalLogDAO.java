package EMR.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import EMR.DOS.EmrApprovalLog;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import org.eclipse.persistence.platform.database.SQLAnywherePlatform;

/**
 * @date:   Jul 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DAOS
 * @file name: EmrApprovalLogDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class EmrApprovalLogDAO  implements DAOInterface, IStructureCheckable
{
    EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`emrApprovalLog`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public EmrApprovalLogDAO()
    {
        fields.add("idOrders");
        fields.add("idUser");
        fields.add("dateApproved");
        fields.add("approvedAcc");
        fields.add("approvedPatID");
        fields.add("approvedSubID");
        fields.add("rejectCodes");
        fields.add("emrOrderId");
        fields.add("emrPatientId");
        fields.add("emrSubscriberId");
        fields.add("dailyRunNo");
        fields.add("idEmrXref");
        fields.add("idClients");
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
            return null;
        }
        
        try
        {
            EmrApprovalLog ordApp = (EmrApprovalLog)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(ordApp, pStmt);
            
            int x = pStmt.executeUpdate();
            
            pStmt.close();
            
            return (x > 0);
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
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
            return null;
        }
        try
        {
            EmrApprovalLog ordApp = (EmrApprovalLog)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + ordApp.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(ordApp, pStmt);
            
            int x = pStmt.executeUpdate();
            
            pStmt.close();
            
            return (x > 0);
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return null;
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
            EmrApprovalLog ordApp = new EmrApprovalLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetFromResultSet(ordApp, rs);
            }

            rs.close();
            stmt.close();

            return ordApp;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public EmrApprovalLog getByEMROrderID(Integer EmrOrderID)
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
            EmrApprovalLog ordApp = new EmrApprovalLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + EmrOrderID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetFromResultSet(ordApp, rs);
            }

            rs.close();
            stmt.close();

            return ordApp;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public int GetLastRunNumber(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int runNum = 0;
            Statement stmt = con.createStatement();
            
            String query = "SELECT DISTINCT `dailyRunNo` "
                    + "FROM " + table + " "
                    + "WHERE DATE(`dateApproved`) = CURDATE()";
            
            ResultSet rs = stmt.executeQuery(query);

            //Count existing rows if any
            while(rs.next())
            {
                runNum++;
            }
            
            rs.close();
            stmt.close();
            
            return runNum;
        }catch(Exception ex){
            System.out.println("EmrApprovalLogDAO::GetLastRunNo - " + ex.toString());
            return -1;
        }
    }
    
    public int GetNextRunNumber(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int runNum = 0;
            Statement stmt = con.createStatement();
            
            String query = "SELECT DISTINCT `dailyRunNo` "
                    + "FROM " + table + " "
                    + "WHERE DATE(`dateApproved`) = CURDATE()";
            
            ResultSet rs = stmt.executeQuery(query);

            //Count existing rows if any
            while(rs.next())
            {
                runNum++;
            }
            
            //Add one more for the next available Run Number
            runNum++;
            
            rs.close();
            stmt.close();
            
            return runNum;
        }catch(Exception ex){
            System.out.println("EmrApprovalLogDAO::GetNextRunNo - " + ex.toString());
            return -1;
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
    
    private EmrApprovalLog SetFromResultSet(EmrApprovalLog obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setIdUser(rs.getInt("idUser"));
        obj.setDateApproved(rs.getDate("dateApproved"));
        obj.setApprovedAcc(rs.getString("approvedAcc"));
        obj.setApprovedPatID(rs.getString("approvedPatID"));
        obj.setApprovedSubID(rs.getString("approvedSubID"));
        obj.setRejectCodes(rs.getString("rejectCodes"));
        obj.setEmrOrderId(rs.getString("emrOrderId"));
        obj.setEmrPatientId(rs.getString("emrPatientId"));
        obj.setEmrSubscriberId(rs.getString("emrSubscriberId"));
        obj.setDailyRunNo(rs.getInt("dailyRunNo"));
        obj.setIdEmrXref(rs.getInt("idEmrXref"));
        obj.setIdClients(rs.getInt("idClients"));
        return obj;
    }
    
    private PreparedStatement SetStatement(EmrApprovalLog obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdOrders());
        pStmt.setInt(2, obj.getIdUser());
        pStmt.setTimestamp(3, /*new Timestamp(obj.getDateApproved().getTime())*/ Convert.ToSQLDateTime(new Date()));
        SQLUtil.SafeSetString(pStmt, 4, obj.getApprovedAcc());
        SQLUtil.SafeSetString(pStmt, 5, obj.getApprovedPatID());
        SQLUtil.SafeSetString(pStmt, 6, obj.getApprovedSubID());
        SQLUtil.SafeSetString(pStmt, 7, obj.getRejectCodes());
        SQLUtil.SafeSetString(pStmt, 8, obj.getEmrOrderId());
        SQLUtil.SafeSetString(pStmt, 9, obj.getEmrPatientId());
        SQLUtil.SafeSetString(pStmt, 10, obj.getEmrSubscriberId());
        pStmt.setInt(11, obj.getDailyRunNo());
        pStmt.setInt(12, obj.getIdEmrXref());
        pStmt.setInt(13, obj.getIdClients());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
