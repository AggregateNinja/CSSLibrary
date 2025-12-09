/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Reschedule;
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
import java.util.Date;
import org.joda.time.LocalDate;

/**
 *
 * @author TomR
 */
public class RescheduleDAO implements DAOInterface, IStructureCheckable
{
 Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reschedules`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public RescheduleDAO()
    {
        fields.add("phlebotomyId");
        fields.add("phlebotomistId");
        fields.add("advancedOrderId");
        fields.add("clientId");        
        fields.add("reassignedToPhlebotomistId");
        fields.add("originalDate");
        fields.add("rescheduleDate");
        fields.add("rescheduleComment");
        fields.add("rescheduledBy");
        fields.add("rescheduledOn");
    }
    
    public boolean InsertReschedule(Reschedule reschedule) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromReschedule(reschedule, pStmt);
            pStmt.executeUpdate();
            pStmt.close();
            return true;
        } catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }        
        
    }
    
    public boolean UpdateReschedule(Reschedule reschedule) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idReschedules` = " + reschedule.getIdReschedules();

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromReschedule(reschedule, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }        
    }
    
    /**
     * Removes a reschedule from the database.
     * @param idReschedule
     * @return 
     */
    public boolean DeleteRescheduleById(int idReschedule)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Reschedule reschedule = new Reschedule();
            Statement stmt = con.createStatement();

            String query = "DELETE FROM " + table + " WHERE"
                    + " `idReschedules` = " + idReschedule;

            stmt.execute(query);

            stmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }        
    }
    
    
    public Reschedule GetRescheduleById(int idReschedule)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Reschedule reschedule = new Reschedule();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idReschedules` = " + idReschedule;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                GetRescheduleFromResultSet(reschedule, rs);
            }

            rs.close();
            stmt.close();

            return reschedule;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }
    
    public Reschedule GetRescheduleByAdvancedDateAdvancedOrderId(Date date, Integer advancedOrderId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Reschedule r = null;
        try {
            
            LocalDate localdate = new LocalDate(date);
            
            Reschedule reschedule = new Reschedule();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `rescheduleDate` = '" + localdate.toString() + "'"
                    + " AND advancedOrderId = " + advancedOrderId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                GetRescheduleFromResultSet(reschedule, rs);
            }

            rs.close();
            stmt.close();

            return reschedule;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }                
    }
    
    public Reschedule GetRescheduleFromResultSet(Reschedule r, ResultSet rs) throws SQLException
    {
        r.setIdReschedules(rs.getInt("idReschedules"));
        r.setPhlebotomyId(rs.getInt("phlebotomyId"));
        r.setPhlebotomistId(rs.getInt("phlebotomistId"));
        r.setAdvancedOrderId(rs.getInt("advancedOrderId"));
        r.setClientId(rs.getInt("clientId"));
        r.setReassignedToPhlebotomistId(rs.getInt("reassignedToPhlebotomistId"));
        if(r.getReassignedToPhlebotomistId() == 0)
            r.setReassignedToPhlebotomistId(null);
        
        r.setOriginalDate(rs.getTimestamp("originalDate"));
        r.setRescheduleDate(rs.getTimestamp("rescheduleDate"));
        r.setRescheduleComment(rs.getString("rescheduleComment"));
        r.setRescheduledBy(rs.getInt("rescheduledBy"));
        r.setRescheduledOn(rs.getTimestamp("rescheduledOn"));
        return r;
    }
    
    private PreparedStatement SetStatementFromReschedule(Reschedule r, PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, r.getPhlebotomyId());
        SQLUtil.SafeSetInteger(pStmt, ++i, r.getPhlebotomistId());
        SQLUtil.SafeSetInteger(pStmt, ++i, r.getAdvancedOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, r.getClientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, r.getReassignedToPhlebotomistId());
        pStmt.setTimestamp(++i, Convert.ToSQLDateTime(r.getOriginalDate()));
        pStmt.setTimestamp(++i, Convert.ToSQLDateTime(r.getRescheduleDate()));
        pStmt.setString(++i, r.getRescheduleComment());
        pStmt.setInt(++i, r.getRescheduledBy());
        pStmt.setTimestamp(++i, Convert.ToSQLDateTime(r.getRescheduledOn()));
        
        return pStmt;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

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

    private String GenerateUpdateStatement(ArrayList<String> fields) {

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
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }        
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
