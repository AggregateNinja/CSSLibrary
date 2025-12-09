/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcInstrumentInfo;
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

/**
 *
 * @author Ryan
 */
public class QcInstrumentInfoDAO implements DAOInterface, IStructureCheckable {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcInstrumentInfo`";

    public boolean InsertInstrumentInfo(QcInstrumentInfo info){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "INSERT INTO " + table + " ("
                    + "`idInst`, "
                    + "`idqcRules`, "
                    + "`idqcInstControls`) "
                    + "VALUES (?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, info.getIdInst());
            SQLUtil.SafeSetInteger(pStmt, 2, info.getIdqcRules());
            SQLUtil.SafeSetInteger(pStmt, 3, info.getIdqcInstControls());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean UpdateInstrumentInfo(QcInstrumentInfo info) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`idInst` = ?, "
                    + "`idqcRules` = ?, "
                    + "`idqcInstControls` = ? "
                    + "WHERE `idqcInstrumentInfo` = " + info.getIdqcInstrumentInfo();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, info.getIdInst());
            SQLUtil.SafeSetInteger(pStmt, 2, info.getIdqcRules());
            SQLUtil.SafeSetInteger(pStmt, 3, info.getIdqcInstControls());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public QcInstrumentInfo GetInfoById(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcInstrumentInfo info = new QcInstrumentInfo();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idInst` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                info = setQcInstrumentInfoFromResultSet(info, rs);
            }
            
            rs.close();
            stmt.close();
            
            return info;
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<QcInstrumentInfo> GetInfoByInstrumentId(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcInstrumentInfo> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idInst` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcInstrumentInfo info = new QcInstrumentInfo();
                info = setQcInstrumentInfoFromResultSet(info, rs);
                
                list.add(info);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<QcInstrumentInfo> GetInfoByInstrumentIdControlId(int id, int controlId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcInstrumentInfo> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idInst` = " + id + " "
                    + "AND `idqcInstControls` = " + controlId;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcInstrumentInfo info = new QcInstrumentInfo();
                info = setQcInstrumentInfoFromResultSet(info, rs);
                
                list.add(info);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean DeleteByInstRuleAndControl(int inst, int rule, int idInstControls){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `idInst` = ? "
                    + "AND `idqcRules` = ? "
                    + "AND `idqcInstControls` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, inst);
            SQLUtil.SafeSetInteger(pStmt, 2, rule);
            SQLUtil.SafeSetInteger(pStmt, 3, idInstControls);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    private QcInstrumentInfo setQcInstrumentInfoFromResultSet(QcInstrumentInfo info, ResultSet rs) throws SQLException {

        info.setIdqcInstrumentInfo(rs.getInt("idqcInstrumentInfo"));
        info.setIdInst(rs.getInt("idInst"));
        info.setIdqcRules(rs.getInt("idqcRules"));
        info.setIdqcInstControls(rs.getInt("idqcInstControls"));

        return info;
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
    
    @Override
    public String structureCheck() {
        String query = "SELECT `qcInstrumentInfo`.`idqcInstrumentInfo`,\n"
                + "    `qcInstrumentInfo`.`idInst`,\n"
                + "    `qcInstrumentInfo`.`idqcRules`,\n"
                + "    `qcInstrumentInfo`.`idqcInstControls`\n"
                + "FROM `css`.`qcInstrumentInfo` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
