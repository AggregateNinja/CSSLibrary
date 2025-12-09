/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PatientSMSQueue;
import DOS.Patients;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Edd
 */
public class PatientSMSDAO implements IStructureCheckable {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private static final String table = "`patientSMSQueue`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    
    public PatientSMSDAO() {
        fields.add("idSMSQueue");
        fields.add("orderId");
        fields.add("patientId");
        fields.add("sent");
        fields.add("dateCreated");
    }

    public Integer Insert(PatientSMSQueue smsQueue) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        String stmt = "INSERT INTO " + this.table + " (orderId, patientId, messageTypeId) VALUES (?, ?, ?)";
        PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
       
        try {            
            pStmt.setInt(1, smsQueue.getOrderId());
            pStmt.setInt(2, smsQueue.getPatientId());
            pStmt.setInt(3, smsQueue.getMessageTypeId());
            
            pStmt.executeUpdate();
            
            ResultSet rs = pStmt.getGeneratedKeys();
            Integer lastInsertedId = null;
            if (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }

            pStmt.close();
            
            return lastInsertedId;
        } catch (SQLException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString();
            System.out.println("Exception: " + sStackTrace);
            return null;
        }
    }
    
    public boolean updateSentFlag(Integer idSMSQueue) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String stmt = "UPDATE " + this.table + " SET sent = b'0' WHERE idSMSQueue = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.setInt(1, idSMSQueue);
            pStmt.executeUpdate();
            pStmt.close();
            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
        }
        
      
        return false;
    }

    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Serializable getByID(Integer ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public PatientSMSQueue getByOrderId(Integer orderId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            String stmt = "SELECT s.idSMSQueue, s.orderId, s.patientId, s.messageTypeId, s.sent, s.dateCreated "
                + "FROM " + this.table + " s "
                + "WHERE s.orderId = ? "
                + "ORDER BY s.idSMSQueue DESC "
                + "LIMIT 1";
            PreparedStatement pStmt = con.prepareCall(stmt);
            pStmt.setInt(1, orderId);
            ResultSet rs = pStmt.executeQuery();

            PatientSMSQueue smsQueue;
                    
            if (rs.next()) {
                smsQueue = new PatientSMSQueue(
                        rs.getInt("idSMSQueue"),
                        rs.getInt("orderId"),
                        rs.getInt("patientId"),
                        rs.getBoolean("sent"),
                        rs.getDate("dateCreated")
                );
                smsQueue.setMessageTypeId(rs.getInt("messageTypeId"));
                
                rs.close();
                pStmt.close();
            
                return smsQueue;
            }
            
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
        }
        
        return null;
    }
    
    public boolean IsUnsubscribed(int patientId) throws SQLException {
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String stmt = "SELECT idUnsubscribed, dateCreated FROM css.patientSMSUnsubscribed WHERE patientId = ?";
            PreparedStatement pStmt = con.prepareCall(stmt);
            pStmt.setInt(1, patientId);
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next()) {
                rs.close();
                pStmt.close();
                return true;
            }
            
            
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
        }
        
        return false;
    }
    
    
    
    public boolean InsertLog(PatientSMSQueue smsQueue) throws SQLException {
        try {
            String stmt = "INSERT INTO css.patientSMSLog ("
                    + "smsQueueId, logTypeId, orderId, userId, "
                    + "patientId, patientArNo, patientLastName, patientFirstName, "
                    + "patientMiddleName, patientPhone, messageTypeId) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pStmt = con.prepareStatement(stmt);
                        
            pStmt.setInt(1, smsQueue.getIdSMSQueue());
            
            Integer logTypeId = smsQueue.getLogTypeId();
            if (logTypeId != null) {
                pStmt.setInt(2, logTypeId);
            } else {
                pStmt.setInt(2, 1);
            }
            
            pStmt.setInt(3, smsQueue.getOrderId());
            pStmt.setInt(4, smsQueue.getUserId());
            pStmt.setInt(5, smsQueue.getPatientId());
            pStmt.setString(6, smsQueue.getPatient().getArNo());
            pStmt.setString(7, smsQueue.getPatient().getLastName());
            pStmt.setString(8, smsQueue.getPatient().getFirstName());
            pStmt.setString(9, smsQueue.getPatient().getMiddleName());
            pStmt.setString(10, smsQueue.getPatient().getPhone());
            pStmt.setInt(11, smsQueue.getMessageTypeId());
            
            pStmt.executeUpdate();
                        
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            System.out.println("Failed Adding Log: " + ex.toString());            
        }
        
        return false;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
