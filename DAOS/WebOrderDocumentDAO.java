/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import Database.CheckDBConnection;
import Utility.Convert;
import static Utility.SQLUtil.createStatement;
import Web.DOS.WebOrderDocuments;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eboss
 */
public class WebOrderDocumentDAO implements DAOInterface{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`WebOrderDocuments`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public WebOrderDocumentDAO() {
        fields.add("idDocuments");
        fields.add("webIdOrders");
        fields.add("webAccession");
        fields.add("webUserId");
        fields.add("fileName");
        fields.add("typeName");
        fields.add("dateCreated");
        fields.add("dateUpdated");
        fields.add("isActive");
        fields.add("isSent");
        fields.add("dateSent");
        fields.add("sentDir");
        fields.add("avalonIdOrders");
        fields.add("avalonAccession");
    }
    
    public List<WebOrderDocuments> GetDocsForOrderId(Integer idOrders, boolean isReceipting) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        List<WebOrderDocuments> lstDocs = new ArrayList<>();
        WebOrderDocuments doc;
        try {
            
            String query = "SELECT idDocuments, webIdOrders, webAccession, webUserId, fileName, "
                    + "typeName, dateCreated, dateUpdated, isActive, isSent, "
                    + "dateSent, sentDir, avalonIdOrders, avalonAccession "
                    + "FROM " + table + " " 
                    + "WHERE isSent = true AND typeName = 'pdf' ";
            if (isReceipting) {
                query += "AND webIdOrders = " + idOrders;
            } else {
                query += "AND avalonIdOrders = " + idOrders;
            }
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                doc = new WebOrderDocuments();

                setFromResultSet(doc, rs);

                lstDocs.add(doc);
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
        return lstDocs;
    }
    
    
    public List<WebOrderDocuments> GetAllForOrderId(Integer idOrders, boolean isReceipting) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        List<WebOrderDocuments> lstDocs = new ArrayList<>();
        WebOrderDocuments doc;
        try {
            
            String query = "SELECT idDocuments, webIdOrders, webAccession, webUserId, fileName, "
                    + "typeName, dateCreated, dateUpdated, isActive, isSent, "
                    + "dateSent, sentDir, avalonIdOrders, avalonAccession "
                    + "FROM " + table + " " 
                    + "WHERE ";
            if (isReceipting) {
                query += "webIdOrders = " + idOrders;
            } else {
                query += "avalonIdOrders = " + idOrders;
            }
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                doc = new WebOrderDocuments();

                setFromResultSet(doc, rs);

                lstDocs.add(doc);
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
        return lstDocs;
    }
    
    public List<WebOrderDocuments> GetUnsentPdfDocs() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        List<WebOrderDocuments> lstDocs = new ArrayList<>();
        WebOrderDocuments doc;
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT idDocuments, webIdOrders, webAccession, webUserId, fileName, "
                    + "typeName, dateCreated, dateUpdated, isActive, isSent, "
                    + "dateSent, sentDir, avalonIdOrders, avalonAccession "
                    + "FROM " + table + " " 
                    + "WHERE isSent = false AND typeName = 'pdf'";
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                doc = new WebOrderDocuments();

                setFromResultSet(doc, rs);

                lstDocs.add(doc);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstDocs;
    }
    
    public boolean UpdateSentFlag(Integer idDocuments) throws SQLException {
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
                "SET isSent = ?, dateSent = ? " +
                "WHERE idDocuments = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setBoolean(1, true);            
            pStmt.setTimestamp(2, Convert.ToSQLDateTime(new Date()));
            pStmt.setInt(3, idDocuments);
            
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public boolean UpdateAvalonReceipted(Integer webIdOrders, Integer avalonIdOrders, String avalonAccession) throws SQLException {
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
                "SET avalonIdOrders = ?, avalonAccession = ? " +
                "WHERE webIdOrders = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, avalonIdOrders);            
            pStmt.setString(2, avalonAccession);
            pStmt.setInt(3, webIdOrders);
            
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    private WebOrderDocuments setFromResultSet(WebOrderDocuments doc, ResultSet rs) throws SQLException {
        doc.setIdDocuments(rs.getInt("idDocuments"));
        doc.setWebIdOrders(rs.getInt("webIdOrders"));
        doc.setWebAccession(rs.getString("webAccession"));
        doc.setWebUserId(rs.getInt("webUserId"));
        doc.setFileName(rs.getString("fileName"));
        doc.setTypeName(rs.getString("typeName"));
        doc.setDateCreated(rs.getTimestamp("dateCreated"));
        doc.setDateUpdated(rs.getTimestamp("dateUpdated"));
        doc.setIsActive(rs.getBoolean("isActive"));
        doc.setIsSent(rs.getBoolean("isSent"));
        doc.setDateSent(rs.getTimestamp("dateSent"));
        doc.setSentDir(rs.getString("sentDir"));     
        doc.setAvalonIdOrders(rs.getInt("avalonIdOrders"));
        doc.setAvalonAccession(rs.getString("avalonAccession"));
        return doc;
    }
    
    @Override
    public Boolean Insert(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
