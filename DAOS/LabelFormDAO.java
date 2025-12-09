package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseLog;
import DOS.LabelForms;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import static Utility.SQLUtil.createStatement;

/**
 * @date:   Jul 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: LabelFormDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class LabelFormDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`labelForms`";
    /**
     * All fields except idemrReports
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public enum SearchType
    {
        ALL,
        VISIBLE_ONLY,
        NOT_VISIBLE_ONLY
    }
    
    public LabelFormDAO()
    {
        fields.add("name");
        fields.add("language");
        fields.add("form");
        fields.add("visible");
    }
    
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            LabelForms lf = (LabelForms)obj;
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            String generatedCol[] = {"id"};
            PreparedStatement pStmt = con.prepareStatement(stmt, generatedCol);
            SetPreparedStatement(lf, pStmt);
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            boolean keyGen = rs.next();
            pStmt.close();
            rs.close();
            return keyGen;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            LabelForms lf = (LabelForms)obj;
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateUpdateStatement(fields) + " WHERE 'id' = " + lf.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetPreparedStatement(lf, pStmt);
            int x = pStmt.executeUpdate();
            pStmt.close();
            return x > 0;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
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
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            LabelForms lf = new LabelForms();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                
                FromResultSet(lf, rs);
            }
            
            rs.close();
            stmt.close();
            return lf;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public LabelForms GetByName(String labelName)
    {
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            LabelForms lf = new LabelForms();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?";
            
            stmt = createStatement(con, query, labelName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                
                FromResultSet(lf, rs);
            }
            
            rs.close();
            stmt.close();
            return lf;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
    public ArrayList<LabelForms> GetLabelForms(SearchType searchType)
    {
        if (searchType == null) searchType = SearchType.ALL;
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            ArrayList<LabelForms> lfList = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            if (searchType == SearchType.NOT_VISIBLE_ONLY)
            {
                query += " WHERE visible = 0";
            }
            
            if (searchType == SearchType.VISIBLE_ONLY)
            {
                query += " WHERE visible = 1";
            }
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                LabelForms lf = new LabelForms();
                FromResultSet(lf, rs);
                lfList.add(lf);
            }
            
            rs.close();
            stmt.close();
            return lfList;
        }
        catch (SQLException ex)
        {
            Integer userId = Preferences.userRoot().getInt("id",0);
            if (userId == 0) userId = 1;
            SysLogDAO.Add(userId, "LabelFormDAO::GetAllLabelForms", ex.getMessage());
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    // Label Name is not being used
    @Deprecated
    public ArrayList<LabelForms> GetAllLabelForms(String labelName)
    {
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            ArrayList<LabelForms> lfList = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                LabelForms lf = new LabelForms();
                FromResultSet(lf, rs);
                lfList.add(lf);
            }
            
            rs.close();
            stmt.close();
            return lfList;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EmrReportDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private LabelForms FromResultSet(LabelForms obj, ResultSet rs) throws SQLException
    {
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setLanguage(rs.getString("language"));
        obj.setForm(rs.getString("form"));
        return obj;
    }
    
    private PreparedStatement SetPreparedStatement(LabelForms obj, PreparedStatement pStmt) throws SQLException
    {
        SQLUtil.SafeSetString(pStmt, 1, obj.getName());
        SQLUtil.SafeSetString(pStmt, 2, obj.getLanguage());
        SQLUtil.SafeSetString(pStmt, 3, obj.getForm());
        SQLUtil.SafeSetBoolean(pStmt, 4, obj.isVisible());
        return pStmt;
    }
    
    public void LogLabelPrint(Integer idUser, String labelType, String formName, Integer idPatients, Integer idClients, Integer idOrders) {
        try
        {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            ArrayList<LabelForms> lfList = new ArrayList<>();
            
            String stmt = "INSERT INTO labelPrintLog (idUser, labelType, formName, idPatients, idClients, idOrders) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, idUser);
            SQLUtil.SafeSetString(pStmt, 2, labelType);
            SQLUtil.SafeSetString(pStmt, 3, formName);
            SQLUtil.SafeSetInteger(pStmt, 4, idPatients);
            SQLUtil.SafeSetInteger(pStmt, 5, idClients);
            SQLUtil.SafeSetInteger(pStmt, 6, idOrders);

            pStmt.executeUpdate();

            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
