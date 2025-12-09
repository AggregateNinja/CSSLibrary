package Billing.DAOS;

import Billing.DOS.AccessionXref;
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Routes;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Aug 1, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Billing.DAOS
 * @file name: AccessionXrefDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class AccessionXrefDAO implements DAOInterface, IStructureCheckable
{
    Billing.Database.BillingDatabaseSingleton dbs = Billing.Database.BillingDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`accessionXref`";

    private final ArrayList<String> fields = new ArrayList<>();

    /**
     * All fields except id
     */
    public AccessionXrefDAO()
    {
        fields.add("avalonAccession");
        fields.add("billingAccession");
        fields.add("orderDate");
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
    
    public String GetAvalonAccession (String CSSAccession)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            AccessionXref axr = new AccessionXref();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `billingAccession` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, CSSAccession);

            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                FromResultSet(axr, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return axr.getAvalonAccession();
        }catch(SQLException ex){
            Logger.getLogger(AccessionXref.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception GetAvalonAccession (" + CSSAccession + "): " + ex.toString());
            return null;
        }
    }
    
    public String GetCSSAccession (String AvalonAccession)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            AccessionXref axr = new AccessionXref();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `avalonAccession` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, AvalonAccession);

            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                FromResultSet(axr, rs);
            }
            
            rs.close();
            pStmt.close();
            
            return axr.getAvalonAccession();
        }catch(SQLException ex){
            Logger.getLogger(AccessionXref.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception GetAvalonAccession (" + AvalonAccession + "): " + ex.toString());
            return null;
        }
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

    private AccessionXref FromResultSet(AccessionXref obj, ResultSet rs) throws SQLException {
        obj.setId(rs.getInt("id"));
        obj.setAvalonAccession(rs.getString("avalonAccession"));
        obj.setBillingAccession(rs.getString("billingAccession"));
        obj.setOrderDate(rs.getTimestamp("orderDate"));

        return obj;
    }

    private PreparedStatement SetStatement(AccessionXref obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, obj.getAvalonAccession());
        SQLUtil.SafeSetString(pStmt, 2, obj.getBillingAccession());
        SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(obj.getOrderDate()));

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
