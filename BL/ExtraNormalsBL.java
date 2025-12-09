/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 15, 2014
 */

package BL;

import DAOS.ExtraNormalsDAO;
import DAOS.ExtraNormalsLogDAO;
import DAOS.TestDAO;
import DOS.ExtraNormals;
import DOS.ExtraNormalsLog;
import DOS.Tests;
import DOS.Users;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * @date:   Jun 15, 2014  10:58:43 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ExtraNormalsBL.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class ExtraNormalsBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public ExtraNormalsBL(){
        
    }
    
    /**
     * Creates and Inserts an ExtraNormalsLog for Inserts
     * @param en New ExtraNormals object to log
     * @param event String Event Type (ADD NORMAL, UPDATE NORMAL, DEACTIVATE NORMAL, DELETE NORMAL)
     * @param user Users object of user that is making the change.
     */
    public void CreateLog(ExtraNormals en, String event, Users user){
        
        ExtraNormalsLog log = new ExtraNormalsLog();
        //Get the test for this extra normal
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestByID(en.getTest());
        //Date to be used for created
        java.util.Date date = new java.util.Date();
        
        
        log.setEventType(event);
        log.setTestNumber(test.getNumber());
        log.setTestName(test.getName());
        log.setIdtests(test.getIdtests());
        log.setNewIdExtraNormals(en.getIdextraNormals());
        log.setSpecies(en.getSpecies());
        log.setLowNormal(en.getLowNormal());
        log.setHighNormal(en.getHighNormal());
        log.setAlertLow(en.getAlertLow());
        log.setAlertHigh(en.getAlertHigh());
        log.setCriticalLow(en.getCriticalLow());
        log.setCriticalHigh(en.getCriticalHigh());
        log.setSex(en.getSex());
        log.setAgeLow(en.getAgeLow());
        log.setAgeUnitsLow(en.getAgeUnitsLow());
        log.setAgeHigh(en.getAgeHigh());
        log.setAgeUnitsHigh(en.getAgeUnitsHigh());
        log.setPrintNormals(en.getPrintNormals());
        log.setActive(en.getActive());
        log.setUser(user.getIdUser());
        log.setCreated(date);
        
        try{
            ExtraNormalsLogDAO enldoa = new ExtraNormalsLogDAO();
            enldoa.Insert((Serializable)log);
        }catch(Exception ex){
            System.out.println("Exception Inserting ExtraNormalsBL CreateLog: "
                    + ex.toString());
        }
    }
    
    /**
     * Creates and Inserts a List of ExtraNormalsLog for Inserts
     * @param list ArrayList<ExtraNormals> of New ExtraNormals objects
     * @param event String Event Type (ADD NORMAL, UPDATE NORMAL, DEACTIVATE NORMAL, DELETE NORMAL)
     * @param user Users object of user that is making the change.
     */
    public void CreateMultipleLogs(ArrayList<ExtraNormals> list, String event, Users user) {
        //Date to be used for created
        java.util.Date date = new java.util.Date();
        TestDAO tdao = new TestDAO();

        for (ExtraNormals en : list) {
            ExtraNormalsLog log = new ExtraNormalsLog();
            //Get the test for this extra normal
            Tests test = tdao.GetTestByID(en.getTest());

            log.setEventType(event);
            log.setTestNumber(test.getNumber());
            log.setTestName(test.getName());
            log.setIdtests(test.getIdtests());
            log.setNewIdExtraNormals(en.getIdextraNormals());
            log.setSpecies(en.getSpecies());
            log.setLowNormal(en.getLowNormal());
            log.setHighNormal(en.getHighNormal());
            log.setAlertLow(en.getAlertLow());
            log.setAlertHigh(en.getAlertHigh());
            log.setCriticalLow(en.getCriticalLow());
            log.setCriticalHigh(en.getCriticalHigh());
            log.setSex(en.getSex());
            log.setAgeLow(en.getAgeLow());
            log.setAgeUnitsLow(en.getAgeUnitsLow());
            log.setAgeHigh(en.getAgeHigh());
            log.setAgeUnitsHigh(en.getAgeUnitsHigh());
            log.setPrintNormals(en.getPrintNormals());
            log.setActive(en.getActive());
            log.setUser(user.getIdUser());
            log.setCreated(date);

            try {
                ExtraNormalsLogDAO enldoa = new ExtraNormalsLogDAO();
                enldoa.Insert((Serializable) log);
            } catch (Exception ex) {
                System.out.println("Exception Inserting ExtraNormalsBL CreateLog: "
                        + ex.toString());
            }
        }
    }
    
    /**
     * Creates a Log entry for when an ExtraNormal has changed
     * @param en ExtraNormals object that has changed
     * @param event String Event Type (ADD NORMAL, UPDATE NORMAL, DEACTIVATE NORMAL, DELETE NORMAL)
     * @param change String Description of change
     * @param user Users object of user that is making the change.
     */
    public void CreateUpdateLog(ExtraNormals en, String event, String change, Users user){
        
        ExtraNormalsLog log = new ExtraNormalsLog();
        //Get the test for this extra normal
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestByID(en.getTest());
        //Date to be used for created
        java.util.Date date = new java.util.Date();
        ExtraNormalsDAO endao = new ExtraNormalsDAO();
        ExtraNormals oldEn = endao.GetNormalByID(en.getIdextraNormals());
        
        
        log.setEventType(event);
        log.setChangeType(change);
        log.setTestNumber(test.getNumber());
        log.setTestName(test.getName());
        log.setIdtests(test.getIdtests());
        log.setOldIdExtraNormals(oldEn.getIdextraNormals());
        log.setNewIdExtraNormals(en.getIdextraNormals());
        log.setSpecies(en.getSpecies());
        log.setLowNormal(en.getLowNormal());
        log.setHighNormal(en.getHighNormal());
        log.setAlertLow(en.getAlertLow());
        log.setAlertHigh(en.getAlertHigh());
        log.setCriticalLow(en.getCriticalLow());
        log.setCriticalHigh(en.getCriticalHigh());
        log.setSex(en.getSex());
        log.setAgeLow(en.getAgeLow());
        log.setAgeUnitsLow(en.getAgeUnitsLow());
        log.setAgeHigh(en.getAgeHigh());
        log.setAgeUnitsHigh(en.getAgeUnitsHigh());
        log.setPrintNormals(en.getPrintNormals());
        log.setActive(en.getActive());
        log.setDeactivatedDate(en.getDeactivatedDate());
        log.setUser(user.getIdUser());
        log.setCreated(date);
        
        try{
            ExtraNormalsLogDAO enldoa = new ExtraNormalsLogDAO();
            enldoa.Insert((Serializable)log);
        }catch(Exception ex){
            System.out.println("Exception Inserting ExtraNormalsBL CreateLog: "
                    + ex.toString());
        }
    }
}
