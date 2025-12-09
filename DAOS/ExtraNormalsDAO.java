/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DAOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 10/09/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.ExtraNormals;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtraNormalsDAO implements DAOInterface, IStructureCheckable {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    private final String table = "`extranormals`";
    
    // in workbench, right click table -> copy to clipboard -> select all statement
    public void sanityCheck() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return;
        }
                
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `extranormals`.`idextraNormals`,\n" +
                    "    `extranormals`.`test`,\n" +
                    "    `extranormals`.`species`,\n" +
                    "    `extranormals`.`lowNormal`,\n" +
                    "    `extranormals`.`highNormal`,\n" +
                    "    `extranormals`.`alertLo`,\n" +
                    "    `extranormals`.`alertHigh`,\n" +
                    "    `extranormals`.`criticalLo`,\n" +
                    "    `extranormals`.`criticalHigh`,\n" +
                    "    `extranormals`.`ageLow`,\n" +
                    "    `extranormals`.`ageUnitsLow`,\n" +
                    "    `extranormals`.`ageHigh`,\n" +
                    "    `extranormals`.`ageUnitsHigh`,\n" +
                    "    `extranormals`.`sex`,\n" +
                    "    `extranormals`.`active`,\n" +
                    "    `extranormals`.`deactivatedDate`,\n" +
                    "    `extranormals`.`printNormals`,\n" +
                    "    `extranormals`.`type`\n" +
                    "FROM `css`.`extranormals` LIMIT 1;";

            stmt.executeQuery(query);
            stmt.close();
            
            return;
            
        } catch (Exception ex) {
            Logger.getLogger(ExtraNormalsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
    
    public boolean InsertENormal(ExtraNormals normal) throws SQLException
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
            String stmt = "INSERT INTO " + table + "("
                    + "`test`,"
                    + "`species`,"
                    + "`lowNormal`,"
                    + "`highNormal`,"
                    + "`alertLow`,"
                    + "`alertHigh`,"
                    + "`criticalLow`,"
                    + "`criticalHigh`,"
                    + "`ageLow`,"
                    + "`ageUnitsLow`,"
                    + "`ageHigh`,"
                    + "`ageUnitsHigh`,"
                    + "`sex`,"
                    + "`active`,"
                    + "`printNormals`,"
                    + "`type`) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetRangeInteger(pStmt, 1, normal.getTest());
            SQLUtil.SafeSetRangeInteger(pStmt, 2, normal.getSpecies());
            SQLUtil.SafeSetRangeDouble(pStmt, 3, normal.getLowNormal());
            SQLUtil.SafeSetRangeDouble(pStmt, 4, normal.getHighNormal());
            SQLUtil.SafeSetRangeDouble(pStmt, 5, normal.getAlertLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 6, normal.getAlertHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 7, normal.getCriticalLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 8, normal.getCriticalHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 9, normal.getAgeLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 10, normal.getAgeUnitsLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 11, normal.getAgeHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 12, normal.getAgeUnitsHigh());
            SQLUtil.SafeSetString(pStmt, 13, normal.getSex());
            pStmt.setBoolean(14, normal.getActive());
            if(normal.getPrintNormals() == null || normal.getPrintNormals().isEmpty()){
                pStmt.setString(15, " ");
            }else{
                SQLUtil.SafeSetString(pStmt, 15, normal.getPrintNormals());
            }
            SQLUtil.SafeSetInteger(pStmt, 16, normal.getType());
            //SQLUtil.SafeSetTimeStamp(pStmt, 13, Convert.ToSQLDate(normal.getDeactivatedDate()));
            //pStmt.setDate(13, Convert.ToSQLDate(normal.getDeactivatedDate()));
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public int InsertENormalWithKey(ExtraNormals normal) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        try
        {
            /*
            Statement stmt = con.createStatement();
            
            String query = "INSERT INTO " + table + "("
                    + "`test`,"
                    + "`species`,"
                    + "`lowNormal`,"
                    + "`highNormal`,"
                    + "`alertLow`,"
                    + "`alertHigh`,"
                    + "`criticalLow`,"
                    + "`criticalHigh`,"
                    + "`ageLow`,"
                    + "`ageHigh`,"
                    + "`sex`,"
                    + "`active`,"
                    + "`printNormals`) "
                    + "values ("
                    + normal.getTest()+","
                    + normal.getSpecies()+","
                    + normal.getLowNormal()+","
                    + normal.getHighNormal()+","
                    + normal.getAlertLow()+","
                    + normal.getAlertHigh()+","
                    + normal.getCriticalLow()+","
                    + normal.getCriticalHigh()+","
                    + normal.getAgeLow()+","
                    + normal.getAgeHigh()+","
                    + normal.getSex()+","
                    + normal.getActive()+",'"
                    + normal.getPrintNormals()+"')";

            int key = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            stmt.close();
            */
            String query = "INSERT INTO " + table + "("
                    + "`test`,"
                    + "`species`,"
                    + "`lowNormal`,"
                    + "`highNormal`,"
                    + "`alertLow`,"
                    + "`alertHigh`,"
                    + "`criticalLow`,"
                    + "`criticalHigh`,"
                    + "`ageLow`,"
                    + "`ageUnitsLow`,"
                    + "`ageHigh`,"
                    + "`ageUnitsHigh`,"
                    + "`sex`,"
                    + "`active`,"
                    + "`printNormals`,"
                    + "`type`) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            //PreparedStatement pStmt = con.prepareStatement(stmt);
            PreparedStatement pStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            SQLUtil.SafeSetRangeInteger(pStmt, 1, normal.getTest());
            SQLUtil.SafeSetRangeInteger(pStmt, 2, normal.getSpecies());
            SQLUtil.SafeSetRangeDouble(pStmt, 3, normal.getLowNormal());
            SQLUtil.SafeSetRangeDouble(pStmt, 4, normal.getHighNormal());
            SQLUtil.SafeSetRangeDouble(pStmt, 5, normal.getAlertLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 6, normal.getAlertHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 7, normal.getCriticalLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 8, normal.getCriticalHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 9, normal.getAgeLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 10, normal.getAgeUnitsLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 11, normal.getAgeHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 12, normal.getAgeUnitsHigh());
            SQLUtil.SafeSetString(pStmt, 13, normal.getSex());
            pStmt.setBoolean(14, normal.getActive());
            if(normal.getPrintNormals() == null || normal.getPrintNormals().isEmpty()){
                pStmt.setString(15, " ");
            }else{
                SQLUtil.SafeSetString(pStmt, 15, normal.getPrintNormals());
            }
            SQLUtil.SafeSetInteger(pStmt, 16, normal.getType());
            
            int number = pStmt.executeUpdate();
            int key = 0;
            
            ResultSet rs = pStmt.getGeneratedKeys();
            if(rs.next()){
                key=rs.getInt(1);
            }
            
            return key;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public boolean UpdateENormal(ExtraNormals normal) throws SQLException
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
            String stmt = "UPDATE " + table + " SET "
                    + "`test` = ?,"
                    + "`species` = ?,"
                    + "`lowNormal` = ?,"
                    + "`highNormal` = ?,"
                    + "`alertLow` = ?,"
                    + "`alertHigh` = ?,"
                    + "`criticalLow` = ?,"
                    + "`criticalHigh` = ?,"
                    + "`ageLow` = ?,"
                    + "`ageUnitsLow` = ?,"
                    + "`ageHigh` = ?,"
                    + "`ageUnitsHigh` = ?,"
                    + "`sex` = ?,"
                    + "`active` = ?,"
                    + "`deactivatedDate` = ?,"
                    + "`printNormals` = ?, "
                    + "`type` = ? "
                    + "WHERE `idextraNormals` = " + normal.getIdextraNormals();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, normal.getTest());
            SQLUtil.SafeSetInteger(pStmt, 2, normal.getSpecies());
            SQLUtil.SafeSetDouble(pStmt, 3, normal.getLowNormal());
            SQLUtil.SafeSetDouble(pStmt, 4, normal.getHighNormal());
            SQLUtil.SafeSetDouble(pStmt, 5, normal.getAlertLow());
            SQLUtil.SafeSetDouble(pStmt, 6, normal.getAlertHigh());
            SQLUtil.SafeSetDouble(pStmt, 7, normal.getCriticalLow());
            SQLUtil.SafeSetDouble(pStmt, 8, normal.getCriticalHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 9, normal.getAgeLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 10, normal.getAgeUnitsLow());
            SQLUtil.SafeSetRangeInteger(pStmt, 11, normal.getAgeHigh());
            SQLUtil.SafeSetRangeInteger(pStmt, 12, normal.getAgeUnitsHigh());
            SQLUtil.SafeSetString(pStmt, 13, normal.getSex());
            pStmt.setBoolean(14, normal.getActive());
            SQLUtil.SafeSetDate(pStmt, 15, normal.getDeactivatedDate());
            //pStmt.setDate(13, Convert.ToSQLDate(normal.getDeactivatedDate()));
            if(normal.getPrintNormals() == null || normal.getPrintNormals().isEmpty()){
                pStmt.setString(16, " ");
            }else{
                SQLUtil.SafeSetString(pStmt, 16, normal.getPrintNormals());
            }
            SQLUtil.SafeSetInteger(pStmt, 17, normal.getType());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public ExtraNormals GetNormalByID(int ID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            ExtraNormals normal = new ExtraNormals();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idextraNormals` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetFromResultSet(normal, rs);
            }
            
            rs.close();
            stmt.close();
            
            return normal;
            
        } catch (Exception ex) {
            Logger.getLogger(ExtraNormalsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<ExtraNormals> GetNormalsByTestID(int ID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            ArrayList<ExtraNormals> list = new ArrayList<ExtraNormals>();
            Statement stmt = con.createStatement();
            
            String query = " SELECT * FROM " + table + " "
                    + "WHERE `test` = " + ID + " "
                    + "AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                ExtraNormals en = new ExtraNormals();
                SetFromResultSet(en, rs);
                list.add(en);
            }
            
            return list;
        }catch (Exception ex) {
            Logger.getLogger(ExtraNormalsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public java.sql.Date DeactivateByTestID(int ID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            java.util.Date uDate = new java.util.Date();
            java.sql.Date sDate = Convert.ToSQLDate(uDate);
            
            String stmt = "UPDATE " + table + " SET "
                    + "`active` = ?, "
                    + "`deactivatedDate` = ? "
                    + "WHERE `active` = ? "
                    + "AND `test` = " + ID;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setBoolean(1, false);
            pStmt.setDate(2, sDate);
            pStmt.setBoolean(3, true);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return sDate;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Gets ExtraNormals based on TestID, Age and Sex. Can return anywhere from 1 or more elements if successful.
     * @param TestID
     * @param Age
     * @param Sex If you pass in null, it will not look by sex. If you pass in the String 'null', it will query for nulls in the DB.
     * @return 
     */
    public ArrayList<ExtraNormals> GetNormalsFromStats(Integer TestID, Date patDob, Date compareDate, String Sex)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try
        {
            ArrayList<ExtraNormals> list = new ArrayList<>();
            ExtraNormals normal;
            
            String query = " SELECT * FROM " + table + " "
                        + "WHERE `test` = " + TestID + " "
                        + "AND `active` = 1 ";
            
            if(patDob != null)
            {
                query += "AND F_DOB_BETWEEN(?, ?, `ageLow`, `ageHigh`, `ageUnitsLow`, `ageUnitsHigh`) ";
            }
            if(Sex != null)
            {
                switch(Sex.toLowerCase())
                {
                    // 'OR `type` = 1' needs to be added because if someone passes in a sex to this method, any extranormal which is by age only would be ignored. 
                    case "male":
                        query += "AND (`sex` = 'Male' OR `type` = 1) ";
                        break;
                    case "female":
                        query += "AND (`sex` = 'Female' OR `type` = 1) ";
                        break;
                    case "n/a":
                    case "null":
                        query += "AND (`sex` IS NULL OR `type` = 1) ";
                        break;
                }
            }
            PreparedStatement stmt = con.prepareStatement(query);
            SQLUtil.SafeSetDate(stmt, 1, patDob);
            SQLUtil.SafeSetDate(stmt, 2, compareDate);
            ResultSet rs = stmt.executeQuery();
                
            while(rs.next())
            {
                ExtraNormals en = new ExtraNormals();
                SetFromResultSet(en, rs);
                list.add(en);
            }
            
            return list;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ExtraNormalsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ExtraNormals SetFromResultSet(ExtraNormals normal, ResultSet rs) throws SQLException
    {
        normal.setIdextraNormals(rs.getInt("idextraNormals"));
        normal.setTest(rs.getInt("test"));
        normal.setSpecies(rs.getInt("species"));
        normal.setLowNormal(rs.getDouble("lowNormal"));
        normal.setHighNormal(rs.getDouble("highNormal"));
        normal.setAlertLow(rs.getDouble("alertLow"));
        normal.setAlertHigh(rs.getDouble("alertHigh"));
        normal.setCriticalLow(rs.getDouble("criticalLow"));
        normal.setCriticalHigh(rs.getDouble("criticalHigh"));
        normal.setAgeLow(rs.getInt("ageLow"));
        normal.setAgeUnitsLow(rs.getInt("ageUnitsLow"));
        normal.setAgeHigh(rs.getInt("ageHigh"));
        normal.setAgeUnitsHigh(rs.getInt("ageUnitsHigh"));
        normal.setSex(rs.getString("sex"));
        normal.setActive(rs.getBoolean("active"));
        normal.setDeactivatedDate(rs.getDate("deactivatedDate"));
        normal.setPrintNormals(rs.getString("printNormals"));
        normal.setType(rs.getInt("type"));
        
        return normal;
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
        String query = "SELECT `extranormals`.`idextraNormals`,\n"
                + "    `extranormals`.`test`,\n"
                + "    `extranormals`.`species`,\n"
                + "    `extranormals`.`lowNormal`,\n"
                + "    `extranormals`.`highNormal`,\n"
                + "    `extranormals`.`alertLow`,\n"
                + "    `extranormals`.`alertHigh`,\n"
                + "    `extranormals`.`criticalLow`,\n"
                + "    `extranormals`.`criticalHigh`,\n"
                + "    `extranormals`.`ageLow`,\n"
                + "    `extranormals`.`ageUnitsLow`,\n"
                + "    `extranormals`.`ageHigh`,\n"
                + "    `extranormals`.`ageUnitsHigh`,\n"
                + "    `extranormals`.`sex`,\n"
                + "    `extranormals`.`active`,\n"
                + "    `extranormals`.`deactivatedDate`,\n"
                + "    `extranormals`.`printNormals`,\n"
                + "    `extranormals`.`type`\n"
                + "FROM `css`.`extranormals` LIMIT 1;";
       return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
