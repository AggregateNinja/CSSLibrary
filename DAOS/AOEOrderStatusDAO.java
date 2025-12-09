/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOEOrderStatus;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class AOEOrderStatusDAO implements IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeOrderStatus`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public AOEOrderStatusDAO()
    {
        fields.add("orderId");
        fields.add("sendoutDepartmentNo");
        fields.add("totalQuestionCount");
        fields.add("unansweredQuestionCount");
        fields.add("isMissingPatientData");
        fields.add("dataValid");
        fields.add("approved");
    } 
   
    public AOEOrderStatus GetByID(int idAoeOrderStatus)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        AOEOrderStatus aoeOrderStatus = null;
        try
        {
            String sql = "SELECT * FROM " + table +
                    " WHERE `idAoeOrderStatus` = " + idAoeOrderStatus;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next())
            {                 
                aoeOrderStatus = PopulateFromResultset(
                        new AOEOrderStatus(), rs);                
            }
            
            rs.close();
            stmt.close();
            
        }
        catch (SQLException ex)
        {
            System.out.println("AOEOrderStatusDAO::GetByID: Can't " +
                    "retrieve aoeOrderStatus from ID: " + idAoeOrderStatus);
            return null;
        }
        return aoeOrderStatus;
    }
    
    public ArrayList<AOEOrderStatus> GetByOrderID(int orderId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }        
        ArrayList<AOEOrderStatus> results;
        try
        {
            String sql = "SELECT * FROM " + table +
                    "WHERE `orderId` = " + orderId;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            results = new ArrayList<>();
            
            while (rs.next())
            {
                results.add(PopulateFromResultset(new AOEOrderStatus(), rs));        
            }
            
            rs.close();
            stmt.close();            
        }
        catch (SQLException ex)
        {
            System.out.println("AOEOrderStatusDAO::GetByID: Cannot retrieve" +
                    "aoeOrderStatus for OrderId :" + orderId);
            return null;
        }
        return results;        
    }
    
    public AOEOrderStatus GetByOrderIDDepartmentID(int orderId, int departmentNo)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        AOEOrderStatus aoeOrderStatus = null;
        
        try
        {
            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderId
                    + " AND `sendoutDepartmentNo` = " + departmentNo;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
            {
                aoeOrderStatus = PopulateFromResultset(
                    new AOEOrderStatus(), rs);
            }
            
            rs.close();
            stmt.close();
            
        }
        catch (SQLException ex)
        {
            System.out.println("AOEOrderStatusDAO::GetByOrderIDDepartmentID: " +
                    "Unable to get by orderId: " + orderId + " and departmentNo " +
                    departmentNo + "    " + ex.getMessage());
            return null;
        }
        return aoeOrderStatus;
    }

    public boolean Update(AOEOrderStatus aoeOrderStatus)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        try
        {
            String sql = GenerateUpdateStatement(fields) +
                    " WHERE `idAoeOrderStatus` = " + aoeOrderStatus.getId();
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetInteger(pStmt, 1, aoeOrderStatus.getOrderId());
            SQLUtil.SafeSetInteger(pStmt, 2, aoeOrderStatus.getSendoutDepartmentNumber());
            SQLUtil.SafeSetRangeInteger(pStmt, 3, aoeOrderStatus.getTotalQuestionCount());
            SQLUtil.SafeSetRangeInteger(pStmt, 4, aoeOrderStatus.getUnansweredQuestionCount());
            SQLUtil.SafeSetBoolean(pStmt, 5, aoeOrderStatus.isMissingPatientData());
            SQLUtil.SafeSetBoolean(pStmt, 6, aoeOrderStatus.isDataValid());
            SQLUtil.SafeSetBoolean(pStmt, 7, aoeOrderStatus.isApproved());
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AOEOrderStatusDAO::Update: Unable to " +
                    "update idAoeOrderStatus=" + aoeOrderStatus.getId());
            return false;
        }
        
        return true;
    }
    
    public boolean Insert(AOEOrderStatus aoeOrderStatus)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        try
        {
            String sql = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(sql);
            
            SQLUtil.SafeSetInteger(pStmt, 1, aoeOrderStatus.getOrderId());
            SQLUtil.SafeSetInteger(pStmt, 2, aoeOrderStatus.getSendoutDepartmentNumber());
            SQLUtil.SafeSetRangeInteger(pStmt, 3, aoeOrderStatus.getTotalQuestionCount());
            SQLUtil.SafeSetRangeInteger(pStmt, 4, aoeOrderStatus.getUnansweredQuestionCount());
            SQLUtil.SafeSetBoolean(pStmt, 5, aoeOrderStatus.isMissingPatientData());
            SQLUtil.SafeSetBoolean(pStmt, 6, aoeOrderStatus.isDataValid());
            SQLUtil.SafeSetBoolean(pStmt, 7, aoeOrderStatus.isApproved());
            
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AOEOrderStatusDAO::Insert: Error while inserting " +
                    "aoe status for idOrders " + aoeOrderStatus.getOrderId() + 
                    " / departmentId " + aoeOrderStatus.getSendoutDepartmentNumber());
            return false;
        }
        // Everything was fine.
        return true;
    }
   
    private AOEOrderStatus PopulateFromResultset(
            AOEOrderStatus aoeOrderStatus,
            ResultSet rs) throws SQLException
    {
        aoeOrderStatus.setId(rs.getInt("idAoeOrderStatus"));
        aoeOrderStatus.setOrderId(rs.getInt("orderId"));
        aoeOrderStatus.setSendoutDepartmentNumber(rs.getInt("sendoutDepartmentNo"));
        aoeOrderStatus.setTotalQuestionCount(rs.getInt("totalQuestionCount"));
        aoeOrderStatus.setUnansweredQuestionCount(rs.getInt("unansweredQuestionCount"));
        aoeOrderStatus.setIsMissingPatientData(rs.getBoolean("isMissingPatientData"));
        aoeOrderStatus.setDataValid(rs.getBoolean("dataValid"));
        aoeOrderStatus.setApproved(rs.getBoolean("approved"));

        return aoeOrderStatus;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
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
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
