/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 19, 2014
 */

package InstrumentIO;

import DAOS.QCInstDAO;
import DAOS.UserDAO;
import DOS.Instruments;
import DOS.QcInst;
import DOS.Users;
import InstrumentIO.QC.QCProperties;
import au.com.bytecode.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Aug 19, 2014 3:35:36 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: AbSciexAutoDownload
 * @file name: AbSciexProcessQC.java (UTF-8)
 *
 * @Description:
 *
 */
public class AbSciexProcessQC {
    
    private Instruments INST;
    private Users USER;
    private String INST_SERIAL;
    private String RUN_DATE;
    private Date AquisitionDate;
    private boolean ORAL_RESULTS = false;
    private int QC_LEVEL_COUNT;
    private int LEVEL_INDEX;
    private int TEST_INDEX;
    private int RESULT_INDEX;

    /**
     * *******CONTROL REFERENCES*************
     */
    private static List<String> lvl1Controls;
    private static List<String> lvl2Controls;
    private static List<String> lvl3Controls;
    private static List<String> lvl4Controls;
    private static List<String> lvl5Controls;
    
    public AbSciexProcessQC(Instruments inst, Users user) {
        INST = inst;
        USER = user;
        try {
            QCProperties qcp = QCProperties.FetchProperties(INST.getInstNo());
            lvl1Controls = Arrays.asList(qcp.getLVL_1().split(","));
            lvl2Controls = Arrays.asList(qcp.getLVL_2().split(","));
            lvl3Controls = Arrays.asList(qcp.getLVL_3().split(","));
            lvl4Controls = Arrays.asList(qcp.getLVL_4().split(","));
            lvl5Controls = Arrays.asList(qcp.getLVL_5().split(","));
            LEVEL_INDEX = Integer.parseInt(qcp.getLEVEL_ID());
            TEST_INDEX = Integer.parseInt(qcp.getTEST_NAME());
            RESULT_INDEX = Integer.parseInt(qcp.getRESULT_VALUE());

            //Debuging
            System.out.println("Level Array Index: " + LEVEL_INDEX);
            System.out.println("Test Name Array Index: " + TEST_INDEX);
            System.out.println("Result Value Array Index: " + RESULT_INDEX);
            
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println("Exception in Constructor(1): " + ex.toString());
            Logger.getLogger(AbSciexProcessQC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst Instrument Class Object
     */
    public AbSciexProcessQC(Instruments inst) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
            
            QCProperties qcp = QCProperties.FetchProperties(INST.getInstNo());
            lvl1Controls = Arrays.asList(qcp.getLVL_1().split(","));
            lvl2Controls = Arrays.asList(qcp.getLVL_2().split(","));
            lvl3Controls = Arrays.asList(qcp.getLVL_3().split(","));
            lvl4Controls = Arrays.asList(qcp.getLVL_4().split(","));
            lvl5Controls = Arrays.asList(qcp.getLVL_5().split(","));
            LEVEL_INDEX = Integer.parseInt(qcp.getLEVEL_ID());
            TEST_INDEX = Integer.parseInt(qcp.getTEST_NAME());
            RESULT_INDEX = Integer.parseInt(qcp.getRESULT_VALUE());

            //Debuging
            System.out.println("Level Array Index: " + LEVEL_INDEX);
            System.out.println("Test Name Array Index: " + TEST_INDEX);
            System.out.println("Result Value Array Index: " + RESULT_INDEX);
        } catch (Exception ex) {
            System.out.println("Exception in Constructor(1): " + ex.toString());
            Logger.getLogger(AbSciexProcessQC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ProcessQC(File file) {
        QcInst qc;
        QCInstDAO dao = new QCInstDAO();
        String values[];
        java.util.Date date = new java.util.Date();

        //debug
        System.out.println("Checking if this file is a Duplicate...");
        //First check if this file has already been processed
        if (dao.IsFileADuplicate(INST.getQcTable(), file.getName())) {
            //debug
            System.out.println("THIS IS A DUPLICATE FILE! File Name: " + file.getName());
            System.out.println("SKIPPING DUPLICATE FILE.");
            return;
        }
        //debug
        System.out.println("File is not a Duplicate.");
        
        try {
            //debug
            System.out.println("Attempting to get MetaData for File: " + file.getName());
            //Get the Specimen Type
            GetFileMetaData2(file);
            //debug
            System.out.println("MetaData acquired! Specimen Type is " + (ORAL_RESULTS ? "ORAL" : "URINE"));
            
            CSVReader reader = new CSVReader(new FileReader(file), ',', '\"', 4);
            
            while ((values = reader.readNext()) != null) {
                qc = new QcInst();

                try {
                    //If it is not QC Line, skip to next line
                    if (IsQCData(values[LEVEL_INDEX].trim()) == false) {
                        continue;
                    }
                    //Make sure we get a valid level, if not skip line
                    int lvl = GetQCLevel(values[LEVEL_INDEX].trim());
                    if (lvl == 0) {
                        continue;
                    }
                    
                    qc.setName(values[TEST_INDEX].trim());
                    qc.setLevel(lvl);
                    qc.setResult(values[RESULT_INDEX].trim());
                    qc.setFileName(file.getName());
                    qc.setControl(values[LEVEL_INDEX].trim());
                    qc.setCreatedDate(date);
                    qc.setAcquisitionDate(AquisitionDate);
                    qc.setSpecimenType(ORAL_RESULTS ? 2 : 1);
                    
                    try {
                        dao.InsertQCData(INST.getQcTable(), qc);
                    } catch (Exception ex) {
                        System.out.println("AbSciexProcessQC:: Could Not insert qcInst - " + ex.toString());
                    }
                } catch (Exception ex) {
                    System.out.println("AbSciexProcessQC:: Could Not parse line - " + ex.toString());
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("AbSciexProcessQC:: File Not Found - " + fnfe.toString());
        } catch (IOException ex) {
            System.out.println("AbSciexProcessQC:: Could Not parse line2 - " + ex.toString());
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
    
    public int GetQCLevel(String str) {
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
    
    public boolean isAccession(String str) {
        try {
            double i = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public void GetFileMetaData2(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file.getCanonicalPath())));
            String readLine = null;
            int count = 0;
            SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy HH:mm:ss a");
            String InstName = "";
            String AcqDate = "";
            String AcqMethod = "";
            
            boolean firstBlank = false;
            while (((readLine = reader.readLine()) != null) && (count < 5)) {
                count++;

                if (count == 1 && readLine.isEmpty())
                {
                    firstBlank = true;
                }
                
                if ((count == 2 && !firstBlank) || count == 3) {
                    //Debug 
                    System.out.println("Line #" + count);
                    System.out.println(readLine);

                    String[] segments = readLine.split(",");
                    int segCount = segments.length; 
                    
                    if (segCount == 3) {
                        try {
                            if (segments[0] != null || segments[0].isEmpty() == false) {
                                InstName = segments[0].trim();
                            } else {
                                System.out.println("Could not parse Instrument Name in File: " + file.getName());
                            }
                            if (segments[1] != null || segments[1].isEmpty() == false) {
                                AcqDate = segments[1].trim();
                            } else {
                                System.out.println("Could not parse Acquisition Date in File: " + file.getName());
                            }
                            if (segments[2] != null || segments[2].isEmpty() == false) {
                                AcqMethod = segments[2].trim();
                            } else {
                                System.out.println("Could not parse Acquisition Method in File: " + file.getName());
                            }
                        } catch (Exception ex) {
                            System.out.println("Exception Reading Instrument and File Information. - " + ex.toString());
                        }
                    } else {
                        if (segCount == 2) {
                            try {
                                if (segments[0] != null || segments[0].isEmpty() == false) {
                                    InstName = segments[0].trim();
                                } else {
                                    System.out.println("Could not parse Instrument Name in File: " + file.getName());
                                }
                                if (segments[1] != null || segments[1].isEmpty() == false) {
                                    AcqDate = segments[1].trim();
                                } else {
                                    System.out.println("Could not parse Acquisition Date in File: " + file.getName());
                                }
                            } catch (Exception ex) {
                                System.out.println("Exception Reading Instrument and File Information. - " + ex.toString());
                            }
                        } else {
                            //debug
                            System.out.println("File Information is either missing information or formatted "
                                    + "incorrectly.\nFile Name: " + file.getName());
                            System.out.println("Defaulting instrument and specimen information!");
                        }
                    }
                    
                    System.out.println("Seg[0]Instrument Name: " + InstName);
                    System.out.println("Seg[1]AcquisitionDate: " + AcqDate);
                    System.out.println("Seg[2]AquisitionMethod: " + AcqMethod);

                    INST_SERIAL = InstName;
                    RUN_DATE = AcqDate;

                    try {
                        AquisitionDate = format.parse(RUN_DATE);
                    } catch (ParseException pe) {
                        System.out.println("Exception: Cannot parse \"" + RUN_DATE + "\" - " + pe.toString());
                        AquisitionDate = null;
                    }

                    try {
                        if (AcqMethod.toUpperCase().contains("ORAL")) {
                            ORAL_RESULTS = true;
                            //debug
                            System.out.println("Oral Specimen Type for file: "+file.getName());
                            break;
                        }
                    }catch(Exception ex){
                        System.out.println("Connot Interpert Acquisition Method: " + AcqMethod);
                        System.out.println("Defaulting to Urine Specimen Type...");
                        ORAL_RESULTS = false;
                        break;
                    }

                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Excetpion getting file MetaData: " + ex.toString());
        }
    }
    
    @Deprecated
    public void GetFileMetaData(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        
        int i = 1;
        int segment = 1;
        
        while (in.hasNextLine()) {
            if (i == 3) {
                String line = in.nextLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {
                    if (segment == 1) {
                        INST_SERIAL = st.nextToken().trim();
                    }
                    if (segment == 2) {
                        RUN_DATE = st.nextToken().trim();
                    }
                    if (segment == 3) {
                        String oral = "ORAL";
                        String description = st.nextToken().trim();
                        description = description.toUpperCase();
                        if (description.contains(oral)) {
                            ORAL_RESULTS = true;
                        }
                    }
                    segment++;
                }
                break;
            }
            i++;
        }
    }
}
