/*
 * Computer Service & Support, Inc.  All Rights Reserved Mar 18, 2015
 */

package InstrumentIO;

import DAOS.QCInstDAO;
import DAOS.UserDAO;
import DOS.Instruments;
import DOS.QcInst;
import DOS.Users;
import InstrumentIO.QC.QCProperties;
import Utility.WriteTextFile;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Mar 18, 2015  4:42:27 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: AgilentProcessQC.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class AgilentProcessQC {

    private Instruments INST;
    private Users USER;
    private int SPECIMEN_TYPE;
    
    /*********CONTROL REFERENCES**************/
    private static List<String> lvl1Controls;
    private static List<String> lvl2Controls;
    private static List<String> lvl3Controls;
    private static List<String> lvl4Controls;
    private static List<String> lvl5Controls;
    
    private WriteTextFile log;
    
    public AgilentProcessQC(Instruments inst, Users user, String specimen){
        INST = inst;
        USER = user;
                
        try {
            QCProperties qcp = QCProperties.FetchProperties(INST.getInstNo());
            if (qcp != null)
            {
                lvl1Controls = Arrays.asList(qcp.getLVL_1().split(","));
                lvl2Controls = Arrays.asList(qcp.getLVL_2().split(","));
                lvl3Controls = Arrays.asList(qcp.getLVL_3().split(","));
                lvl4Controls = Arrays.asList(qcp.getLVL_4().split(","));
                lvl5Controls = Arrays.asList(qcp.getLVL_5().split(","));
            }
            else
            {
                lvl1Controls = new ArrayList<>();
                lvl2Controls = new ArrayList<>();
                lvl3Controls = new ArrayList<>();
                lvl4Controls = new ArrayList<>();
                lvl5Controls = new ArrayList<>();
                lvl1Controls.add("QC1");
                lvl2Controls.add("QC2");
                lvl3Controls.add("QC3");
                lvl4Controls.add("QC4");
                lvl5Controls.add("QC5");
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            this.log.write("Exception in Constructor(1): " + ex.toString());
            //System.out.println("Exception in Constructor(1): " + ex.toString());
            Logger.getLogger(AbSciexProcessQC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(specimen == null){ //Default to Urine
            SPECIMEN_TYPE = 1;
        } else if (specimen.equalsIgnoreCase("Saliva")){
            SPECIMEN_TYPE = 2;
        } else if (specimen.equalsIgnoreCase("Urine")){
            SPECIMEN_TYPE = 1;
        }
    }
    
    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst Instrument Class Object
     */
    public AgilentProcessQC(Instruments inst, String specimen, WriteTextFile log) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
            
            this.log = log;

            try {
                QCProperties qcp = QCProperties.FetchProperties(INST.getInstNo());
                if (qcp != null)
                {
                    lvl1Controls = Arrays.asList(qcp.getLVL_1().split(","));
                    lvl2Controls = Arrays.asList(qcp.getLVL_2().split(","));
                    lvl3Controls = Arrays.asList(qcp.getLVL_3().split(","));
                    lvl4Controls = Arrays.asList(qcp.getLVL_4().split(","));
                    lvl5Controls = Arrays.asList(qcp.getLVL_5().split(","));
                }
                else
                {
                    lvl1Controls = new ArrayList<>();
                    lvl2Controls = new ArrayList<>();
                    lvl3Controls = new ArrayList<>();
                    lvl4Controls = new ArrayList<>();
                    lvl5Controls = new ArrayList<>();
                    lvl1Controls.add("QC1");
                    lvl2Controls.add("QC2");
                    lvl3Controls.add("QC3");
                    lvl4Controls.add("QC4");
                    lvl5Controls.add("QC5");
                }
            } catch (FileNotFoundException | NumberFormatException ex) {
                this.log.write("Exception in Constructor(1): " + ex.toString());
                //System.out.println("Exception in Constructor(1): " + ex.toString());
                Logger.getLogger(AbSciexProcessQC.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (specimen == null) { //Default to Urine
                SPECIMEN_TYPE = 1;
            } else if (specimen.equalsIgnoreCase("Saliva")) {
                SPECIMEN_TYPE = 2;
            } else if (specimen.equalsIgnoreCase("Urine")) {
                SPECIMEN_TYPE = 1;
            }
        } catch (SQLException ex) {
            this.log.write(ex.toString());
            //System.out.println(ex.toString());
        }
    }
    
    public void ProcessQC(File file){
        QcInst qc = new QcInst();
        QCInstDAO dao = new QCInstDAO();
        dao.DeleteByFilename(INST.getQcTable(), file.getName());
        String values[];
//        int accCount = 0;
        java.util.Date date = new java.util.Date();
        java.util.Date acqDate = null;
        
        CSVReader reader;
        try
        {
            reader = new CSVReader(new FileReader(file), ',', '\"', 0);
            values = reader.readNext();
            String acquisitionDateStr = values[1].split("\\.")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
            acqDate = sdf.parse(acquisitionDateStr);
            reader.close();
        }
        catch(FileNotFoundException fnfe)
        {
            this.log.write("AgilentProcessQC:: File Not Found - " + fnfe.toString());
            //System.out.println("AgilentProcessQC:: File Not Found - " + fnfe.toString());
        }
        catch(Exception ex)
        {
            this.log.write("AgilentProcessQC:: Could Not parse acquisition date - " + ex.toString());
            //System.out.println("AgilentProcessQC:: Could Not parse acquisition date - " + ex.toString());
        }
        
        try{
            reader = new CSVReader(new FileReader(file), ',', '\"', 1);
            
            while ((values = reader.readNext()) != null) {
                qc = new QcInst();
                
                //If we have read in at least 5 all numeric numbers than it
                //is safe to assume the QC Data is done, and we can stop reading
//                if(accCount > 5){
//                    break;
//                }
                
                
                
                try{
                    //If it is not QC Line, skip to next line
                    if (IsQCData(values[0].trim()) == false) {
                        //Check if this line is an accession for counting.
//                        if (isAccession(values[0].trim())) {
//                            accCount++;
//                        }
                        this.log.write("AgilentProcessQC ProcessQC: Not QC Data: " + values[0].trim());
                        //System.out.println("AgilentProcessQC ProcessQC: Not QC Data: " + values[0].trim());
                        continue;
                    }
                    //Make sure we get a valid level, if not skip line
                    String qcLevel = values[0].trim();
                    int lvl = GetQCLevel(qcLevel);
                    if(lvl == 0){
                        this.log.write("AgilentProcessQC ProcessQC: Invalid level: " + lvl);
                        //System.out.println("AgilentProcessQC ProcessQC: Invalid level: " + lvl);
                        continue;
                    }
                    
                    //Line passed validation, it is QC data. Save it.
                    qc.setName(values[1].trim());
                    qc.setLevel(lvl);
                    qc.setControl(qcLevel);
                    qc.setResult(values[4].trim());
                    qc.setAcquisitionDate(acqDate == null ? date : acqDate);
                    qc.setFileName(file.getName());
                    qc.setSpecimenType(SPECIMEN_TYPE);
                    qc.setCreatedDate(acqDate == null ? date : acqDate);
                    
                    this.log.write("Name: " + values[1] + ", Level: " + qcLevel + ", Result: " + values[4].trim());
                    
                    try{
                        dao.InsertQCData(INST.getQcTable(), qc);
                        this.log.write("QC Data Inserted");
                    }catch(Exception ex){
                        this.log.write("AgilentProcessQC:: Could Not insert qcInst - " + ex.toString());
                       //System.out.println("AgilentProcessQC:: Could Not insert qcInst - " + ex.toString());
                    }
                }catch(Exception ex){
                    this.log.write("AgilentProcessQC:: Could Not parse line - " + ex.toString());
                    //System.out.println("AgilentProcessQC:: Could Not parse line - " + ex.toString());
                }
            }
            
        }catch(FileNotFoundException fnfe){
            this.log.write("AgilentProcessQC:: File Not Found - " + fnfe.toString());
            //System.out.println("AgilentProcessQC:: File Not Found - " + fnfe.toString());
        }catch(IOException ex){
            this.log.write("AgilentProcessQC:: Could Not parse line2 - " + ex.toString());
            //System.out.println("AgilentProcessQC:: Could Not parse line2 - " + ex.toString());
        }
    }
    
    public void ProcessQCMountaineer(File file){
        QcInst qc = new QcInst();
        QCInstDAO dao = new QCInstDAO();
        dao.DeleteByFilename(INST.getQcTable(), file.getName());
        String values[];
//        int accCount = 0;
        java.util.Date date = new java.util.Date();
        java.util.Date acqDate = null;
        
        CSVReader reader;
        try
        {
            reader = new CSVReader(new FileReader(file), ',', '\"', 0);
            values = reader.readNext();
            String acquisitionDateStr = values[1].split("\\.")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
            acqDate = sdf.parse(acquisitionDateStr);
            reader.close();
        }
        catch(FileNotFoundException fnfe)
        {
            this.log.write("AgilentProcessQC:: File Not Found - " + fnfe.toString());
            //System.out.println("AgilentProcessQC:: File Not Found - " + fnfe.toString());
        }
        catch(Exception ex)
        {
            this.log.write("AgilentProcessQC:: Could Not parse acquisition date - " + ex.toString());
            //System.out.println("AgilentProcessQC:: Could Not parse acquisition date - " + ex.toString());
        }
        
        try{
            reader = new CSVReader(new FileReader(file), ',', '\"', 1);
            
            while ((values = reader.readNext()) != null) {
                qc = new QcInst();
                
                //If we have read in at least 5 all numeric numbers than it
                //is safe to assume the QC Data is done, and we can stop reading
//                if(accCount > 5){
//                    break;
//                }
                
                
                
                try{
                    //If it is not QC Line, skip to next line
                    if (IsQCData(values[0].trim()) == false) {
                        //Check if this line is an accession for counting.
//                        if (isAccession(values[0].trim())) {
//                            accCount++;
//                        }
                        this.log.write("AgilentProcessQC ProcessQC: Not QC Data: " + values[0].trim());
                        //System.out.println("AgilentProcessQC ProcessQC: Not QC Data: " + values[0].trim());
                        continue;
                    }
                    //Make sure we get a valid level, if not skip line
                    String qcLevel = values[0].trim();
                    int lvl = GetQCLevelMountaineer(qcLevel);
                    if(lvl == 0){
                        this.log.write("AgilentProcessQC ProcessQC: Invalid level: " + lvl);
                        //System.out.println("AgilentProcessQC ProcessQC: Invalid level: " + lvl);
                        continue;
                    }
                    
                    //Line passed validation, it is QC data. Save it.
                    qc.setName(values[1].trim());
                    qc.setLevel(lvl);
                    qc.setControl(qcLevel);
                    
                    //qc.setResult(values[2].trim());
                    String[] resultUnits = values[2].split(" ");
                    if (resultUnits.length > 0) {
                        qc.setResult(resultUnits[0].trim());
                    } else {
                        qc.setResult(values[2].trim());
                    }
                                        
                    qc.setAcquisitionDate(acqDate == null ? date : acqDate);
                    qc.setFileName(file.getName());
                    qc.setSpecimenType(SPECIMEN_TYPE);
                    qc.setCreatedDate(acqDate == null ? date : acqDate);
                    
                    this.log.write("Name: " + values[1] + ", Level: " + qcLevel + ", Result: " + values[4].trim());
                    
                    try{
                        dao.InsertQCData(INST.getQcTable(), qc);
                        this.log.write("QC Data Inserted");
                    }catch(Exception ex){
                        this.log.write("AgilentProcessQC:: Could Not insert qcInst - " + ex.toString());
                       //System.out.println("AgilentProcessQC:: Could Not insert qcInst - " + ex.toString());
                    }
                }catch(Exception ex){
                    this.log.write("AgilentProcessQC:: Could Not parse line - " + ex.toString());
                    //System.out.println("AgilentProcessQC:: Could Not parse line - " + ex.toString());
                }
            }
            
        }catch(FileNotFoundException fnfe){
            this.log.write("AgilentProcessQC:: File Not Found - " + fnfe.toString());
            //System.out.println("AgilentProcessQC:: File Not Found - " + fnfe.toString());
        }catch(IOException ex){
            this.log.write("AgilentProcessQC:: Could Not parse line2 - " + ex.toString());
            //System.out.println("AgilentProcessQC:: Could Not parse line2 - " + ex.toString());
        }
    }
    
    public boolean IsQCData(String str) {
        boolean result = false;
        if (lvl1Controls.contains(str) || lvl2Controls.contains(str) || lvl3Controls.contains(str) ||
            lvl4Controls.contains(str) || lvl5Controls.contains(str)) {
            result = true;
        }

        return result;
    }
    
    public int GetQCLevel(String str){
        int level = 0;
        
        if (lvl1Controls.contains(str))
            level = 1;
        else if (lvl2Controls.contains(str))
            level = 2;
        else if (lvl3Controls.contains(str))
            level = 3;
        else if (lvl4Controls.contains(str))
            level = 4;
        else if (lvl5Controls.contains(str))
            level = 5;
        
        return level;
    }
    
    public int GetQCLevelMountaineer(String str){
        int level = 0;
        
        if (str.contains("Low")) {
            level = 1;
        } else if (str.contains("Mid")) {
            level = 2;
        } else if (str.contains("High")) {
            level = 3;
        }
        
        return level;
    }
    
    public boolean isAccession(String str){
        try{
            double i = Double.parseDouble(str);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
