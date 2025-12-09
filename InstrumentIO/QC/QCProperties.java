/*
 * Computer Service & Support, Inc.  All Rights Reserved Apr 8, 2015
 */

package InstrumentIO.QC;

import Database.DatabaseProperties;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Apr 8, 2015  11:35:28 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QCProperties.java  (UTF-8)
 *
 * @Description: The class hold and fetches QC data for a given
 *               instrument from the QCProperties.xml file.
 *
 */

public class QCProperties {

    String LVL_1 = null;
    String LVL_2 = null;
    String LVL_3 = null;
    String LVL_4 = null;
    String LVL_5 = null;
    String LEVEL_ID = null;
    String TEST_NAME = null;
    String RESULT_VALUE = null;
    OpSys OS = null;
    
    public static enum OpSys{
        WINDOWS,LINUX;
    }
    
    /**
     * Gets the path to the QCProperties.xml file if if exists for 
     * passed in instrument number and current OS.
     * @param inst int Instrument Number
     * @return Full path to the QCProperties.xml file
     */
    public static String getPropertiesFile(int inst)
    {
        String OSName = System.getProperty("os.name");
        if(OSName.startsWith("Windows"))
        {
            return System.getenv("APPDATA") + File.separator + ".hybrid" + 
                    File.separator + "inst" + inst + File.separator + "QCProperties.xml";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            return "/usr/hybrid/instruments/inst" + inst + "/QCProperties.xml";
        }
        return "";
    }
    
    /**
     * Gets the path to the QCProperties.xml file if if exists for 
     * passed in instrument number and current OS.
     * @param inst int Instrument number
     * @param instance int instance number of current Avalon running config
     * @return Full path to the QCProperties.xml file
     */
    public static String getPropertiesFile(int inst, Integer instance)
    {
        if (instance == null || instance < 2) {
            return getPropertiesFile(inst);
        } else {
            String OSName = System.getProperty("os.name");
            if (OSName.startsWith("Windows")) {
                return System.getenv("APPDATA") + File.separator + ".hybrid" + 
                        File.separator + "inst" + inst + File.separator + "QCProperties.xml";
            } else if (OSName.startsWith("AIX") || OSName.startsWith("Linux")) {
                return "/usr/hybrid" + instance + "/instruments/inst" + inst + "/QCProperties.xml";
            }
            return "";
        }
    }
    
    /**
     * Creates a new QCProperties.xml file from the passed in variables
     * @param inst int Instrument Number 
     * @param lvl1 String QC Level 1 identifier
     * @param lvl2 String QC Level 2 identifier
     * @param lvl3 String QC Level 3 identifier
     * @param lvl4 String QC Level 4 identifier
     * @param lvl5 String QC Level 5 identifier
     * @return True if created
     */
    public static boolean CreatePropertiesFile(int inst, String lvl1, String lvl2,
            String lvl3, String lvl4, String lvl5, String levelId, String testName,
            String resultValue){
        
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        QCProperties qcp = new QCProperties();
        qcp.LVL_1 = lvl1;
        qcp.LVL_2 = lvl2;
        qcp.LVL_3 = lvl3;
        qcp.LVL_4 = lvl4;
        qcp.LVL_5 = lvl5;
        //Get OS
        String OSName = System.getProperty("os.name");
        if(OSName.startsWith("Windows"))
        {
            qcp.OS = OpSys.WINDOWS;
        }else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            qcp.OS = OpSys.LINUX;
        }
        qcp.LEVEL_ID = levelId;
        qcp.TEST_NAME = testName;
        qcp.RESULT_VALUE = resultValue;
        
        String toXML = xs.toXML(qcp);
        String appDataDirectory;
        String hybridDirectory = null;
        String instrumentDirectory;
        
        //Determine path depending on OS
        if(qcp.OS == OpSys.WINDOWS)
        {
            appDataDirectory = System.getenv("APPDATA");
            hybridDirectory = ".hybrid" + File.separator + "inst" + inst;
            instrumentDirectory = "inst" + inst;
        }
        else if(qcp.OS == OpSys.LINUX)
        {
            appDataDirectory = File.separator + "usr" + File.separator +
                    "hybrid" + File.separator + "instruments";
            instrumentDirectory = "inst" + inst;
        }
        else
        {
            return false;
        }
        
        File appData = new File(appDataDirectory);
        if(appData.exists() && appData.isDirectory())
        {
            File instDir;
            if(qcp.OS == OpSys.WINDOWS){
                instDir = new File(appData, hybridDirectory);
            } else {
                instDir = new File(appData, instrumentDirectory);
            }
            
            if(!instDir.exists())
            {
                instDir.mkdir();
                instDir.setWritable(true);
                instDir.setReadable(true);
            }
            File qcproperties = new File(instDir, "QCProperties.xml");
            if(!qcproperties.exists())
            {
                try {
                    qcproperties.createNewFile();
                    qcproperties.setWritable(true);
                    qcproperties.setReadable(true);
                    System.out.println(qcproperties.getCanonicalPath() + " doesn't exist. Creating file...");
                }
                catch (IOException ex) {
                    System.out.println("Exception creating new file. QCProperties File: " + ex.toString());
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            else
            {
                try
                {
                    FileOutputStream eraser = new FileOutputStream(qcproperties);
                    eraser.write((new String()).getBytes()); 
                    eraser.close();
                }
                catch (FileNotFoundException ex)
                {
                    System.out.println("Exception finding file to write to QCProperties.xml: " + ex.toString());
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                catch (IOException ex)
                {
                    System.out.println("Exception writing to QCProperties.xml: " + ex.toString());
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            
            try
            {
                FileOutputStream writer = new FileOutputStream(qcproperties);
                writer.write(toXML.getBytes());
                writer.close();
            }
            catch (IOException ex)
            {
                System.out.println("Exception(2) writing to QCProperties.xml: " + ex.toString());
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            return true;
        }
        
        return false;
    }
    
    /**
     * Returns the QCProperties for an instrument from it's XML file
     * @param inst int Number of the instrument
     * @return QCProperties class object 
     * @throws FileNotFoundException 
     */
    public static QCProperties FetchProperties(int inst) throws FileNotFoundException{
        
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        
        String qcConfig = getPropertiesFile(inst);
        FileInputStream fis = new FileInputStream(qcConfig);
        xs.aliasField("LVL_1", QCProperties.class, "LVL_1");
        xs.alias("QCProperties", QCProperties.class);
        
        QCProperties qcp = (QCProperties) xs.fromXML(fis);
        
        //Write to terminal for debugging
        //System.out.println(qcp.toString());
        
        return qcp;
    }
    
    /**
     * Returns the QCProperties for an instrument from it's XML file
     * @param inst int Number of the instrument
     * @param instance int instance number of this avalon server config
     * @return QCProperties class object 
     * @throws FileNotFoundException 
     */
    public static QCProperties FetchProperties(int inst, int instance) throws FileNotFoundException{
        
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        
        String qcConfig = getPropertiesFile(inst, instance);
        FileInputStream fis = new FileInputStream(qcConfig);
        xs.aliasField("LVL_1", QCProperties.class, "LVL_1");
        xs.alias("QCProperties", QCProperties.class);
        
        QCProperties qcp = (QCProperties) xs.fromXML(fis);
        
        //Write to terminal for debugging
        //System.out.println(qcp.toString());
        
        return qcp;
    }

    public String getLVL_1() {
        return LVL_1;
    }

    public void setLVL_1(String LVL_1) {
        this.LVL_1 = LVL_1;
    }

    public String getLVL_2() {
        return LVL_2;
    }

    public void setLVL_2(String LVL_2) {
        this.LVL_2 = LVL_2;
    }

    public String getLVL_3() {
        return LVL_3;
    }   

    public void setLVL_3(String LVL_3) {
        this.LVL_3 = LVL_3;
    }

    public String getLVL_4() {
        return LVL_4;
    }

    public void setLVL_4(String LVL_4) {
        this.LVL_4 = LVL_4;
    }

    public String getLVL_5() {
        return LVL_5;
    }

    public void setLVL_5(String LVL_5) {
        this.LVL_5 = LVL_5;
    }

    public OpSys getOS() {
        return OS;
    }

    public void setOS(OpSys OS) {
        this.OS = OS;
    }

    public String getLEVEL_ID() {
        return LEVEL_ID;
    }

    public void setLEVEL_ID(String LEVEL_ID) {
        this.LEVEL_ID = LEVEL_ID;
    }

    public String getTEST_NAME() {
        return TEST_NAME;
    }

    public void setTEST_NAME(String TEST_NAME) {
        this.TEST_NAME = TEST_NAME;
    }

    public String getRESULT_VALUE() {
        return RESULT_VALUE;
    }

    public void setRESULT_VALUE(String RESULT_VALUE) {
        this.RESULT_VALUE = RESULT_VALUE;
    } 
}
