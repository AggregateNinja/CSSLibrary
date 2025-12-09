/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BL;

import DAOS.QcControlValuesDAO;
import DOS.QcControlValues;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rypip_000
 */
public class QCReviewBL {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public QCReviewBL(){
        
    }
    
    public QcControlValues GetTestControlByLevel(int testNumber, int level){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcControlValues qcValues = new QcControlValues();
            int valueId = 0;
            String columnName = "";
            
            switch(level){
                case 1:
                    columnName = "control1";
                    break;
                case 2:
                    columnName = "control2";
                    break;
                case 3:
                    columnName = "control3";
                    break;
                case 4:
                    columnName = "control4";
                    break;
                case 5:
                    columnName = "control5";
                    break;
                default:
                    return null;
            }
            
            String query = "SELECT `" + columnName + "` FROM `qcTests` "
                    + "WHERE `number` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, testNumber);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                valueId = rs.getInt(1);
            }
            
            rs.close();
            pStmt.close();
            
            if(valueId > 0){
                QcControlValuesDAO dao = new QcControlValuesDAO();
                qcValues = dao.GetControlValueByID(valueId);
            } else {
                return null;
            }
            
            return qcValues;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
}
