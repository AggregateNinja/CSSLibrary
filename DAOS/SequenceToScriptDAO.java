/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DOS.SequenceToScript;
import Database.CheckDBConnection;
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
import java.util.List;
import java.util.prefs.Preferences;

/**
 *
 * @author eboss
 */
public class SequenceToScriptDAO implements DAOInterface {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`seq2script`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public SequenceToScriptDAO() {
        fields.add("id");
        fields.add("idOrders");
        fields.add("accession");
        fields.add("created");
        fields.add("transmitted");
        fields.add("transDate");
        fields.add("instPath");
        fields.add("transReady");
        fields.add("s2sDate");
        fields.add("s2sPath");
        fields.add("reportGen");
        fields.add("reportGenPath");
        fields.add("reportGenDate");
    }
    
    public Boolean SetReportGeneratedById(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String query = "UPDATE " + table + " " +
                "SET reportGen = ?, reportGenDate = ? " +
                "WHERE id = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, true);            
            pStmt.setTimestamp(2, Convert.ToSQLDateTime(new Date()));
            pStmt.setInt(3, id);
            
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public List<SequenceToScript> GetReadyToTransmit() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        List<SequenceToScript> aryS2S = new ArrayList<>();
        SequenceToScript s2s;
        
        try {
            Statement stmt = con.createStatement();
            
            String query = "SELECT id, idOrders, accession, created, transmitted, transDate, instPath, " +
                "transReady, s2sDate, s2sPath, reportGen, reportGenPath, reportGenDate " +
                "FROM " + table + " " + 
                "WHERE transmitted = false AND transReady = true";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                s2s = new SequenceToScript();

                setFromResultSet(s2s, rs);

                aryS2S.add(s2s);
            }
            
                
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        
        return aryS2S;        
    }
    
    public List<SequenceToScript> GetReportGenReady(String instPath) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        List<SequenceToScript> aryS2S = new ArrayList<>();
        SequenceToScript s2s;
        
        try {
            Statement stmt = con.createStatement();
            
            String query = "SELECT id, idOrders, accession, created, transmitted, transDate, instPath, " +
                "transReady, s2sDate, s2sPath, reportGen, reportGenPath, reportGenDate " +
                "FROM " + table + " " + 
                "WHERE transmitted = true AND transReady = true AND reportGen = false AND instPath = '" + instPath + "'";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                s2s = new SequenceToScript();

                setFromResultSet(s2s, rs);

                aryS2S.add(s2s);
            }
            
                
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        
        return aryS2S;        
    }
    
    public Boolean UpdateTransmittedById(Integer id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String query = "UPDATE " + table + " " +
                "SET transmitted = ?, transDate = ? " +
                "WHERE id = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, true);            
            pStmt.setTimestamp(2, Convert.ToSQLDateTime(new Date()));
            pStmt.setInt(3, id);
            
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    private SequenceToScript setFromResultSet(SequenceToScript s2s, ResultSet rs) throws SQLException {
        s2s.setId(rs.getInt("id"));
        s2s.setIdOrders(rs.getInt("idOrders"));
        s2s.setAccession(rs.getString("accession"));
        s2s.setCreated(rs.getTimestamp("created"));
        s2s.setTransmitted(rs.getBoolean("transmitted"));
        s2s.setTransDate(rs.getTimestamp("transDate"));
        s2s.setInstPath(rs.getString("instPath"));
        s2s.setTranReady(rs.getBoolean("transReady"));
        s2s.setS2SDate(rs.getTimestamp("s2sDate"));
        s2s.setS2SPath(rs.getString("s2sPath"));
        s2s.setReportGen(rs.getBoolean("reportGen"));
        s2s.setReportGenPath(rs.getString("reportGenPath"));
        s2s.setReportGenDate(rs.getTimestamp("reportGenDate"));        
        return s2s;
    }
    
    public boolean InsertS2S(SequenceToScript s2s) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        String stmt = "INSERT INTO " + table + "("
                + " `idOrders`,"
                + " `accession`,"
                + " `created`,"
                + " `transmitted`,"
                + " `transDate`,"
                + " `instPath`,"
                + " `transReady`,"
                + " `s2sDate`,"
                + " `s2sPath`,"
                + " `reportGen`,"
                + " `reportGenPath`,"
                + " `reportGenDate`"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = null;

        try {
            pStmt = con.prepareStatement(stmt);
            pStmt.setInt(1, s2s.getIdOrders());
            pStmt.setString(2, s2s.getAccession());
            pStmt.setTimestamp(3, Convert.ToSQLDateTime(s2s.getCreated()));
            SQLUtil.SafeSetBoolean(pStmt, 4, s2s.getTransmitted());
            pStmt.setTimestamp(5, Convert.ToSQLDateTime(s2s.getTransDate()));
            SQLUtil.SafeSetString(pStmt, 6, s2s.getInstPath());
            SQLUtil.SafeSetBoolean(pStmt, 7, s2s.getTransReady());
            pStmt.setTimestamp(8, Convert.ToSQLDateTime(s2s.getS2SDate()));
            SQLUtil.SafeSetString(pStmt, 9, s2s.getS2SPath());
            SQLUtil.SafeSetBoolean(pStmt, 10, s2s.getReportGen());
            SQLUtil.SafeSetString(pStmt, 11, s2s.getReportGenPath());
            pStmt.setTimestamp(12, Convert.ToSQLDateTime(s2s.getReportGenDate()));
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            int userId = Preferences.userRoot().getInt("id", 0);
            SysLogDAO.Add(userId, "SequenceToScriptDAO::InsertS2S: idOrders " + s2s.getIdOrders(), ex.getMessage());
            System.out.println(message);
            if (pStmt != null) {
                pStmt.close();
            }
            return false;
        }
    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            SequenceToScript s2s = (SequenceToScript) obj;
            return InsertS2S(s2s);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
