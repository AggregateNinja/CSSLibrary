/*
 * Copyright (C) 2015 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package InstrumentIO;

import DAOS.InstResDAO;
import DAOS.InstrumentDAO;
import DAOS.QCInstDAO;
import DAOS.UserDAO;
import DOS.IDOS.BaseInstRes;
import DOS.Instruments;
import DOS.QcInst;
import DOS.Users;
import Utility.FileUtil;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Pattern;
import Utility.WriteTextFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Waters LCMS Instrument Interface. Looks for new result files and processes
 * them into the instRes_# DB.
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build 1.001 05/05/2015
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class WatersFileReader {

    public static WriteTextFile writeLog;
    private int INST;
    private Users USER;
    private String INST_SERIAL;
    private String RUN_DATE;
    private boolean ORAL_RESULTS = false;
    private boolean ORAL_URINE_FILENAME = false;

    /**
     * Constructor that is passed the Instrument Number and a User for logging
     * purposes.
     *
     * @param inst int value for Instrument Number
     * @param user int value for User ID
     * @param log WriteTextFile used for logging to file
     */
    public WatersFileReader(int inst, int user, WriteTextFile log, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(user);
            USER = u;
            writeLog = log;
            ORAL_URINE_FILENAME = fileNameOralUrine;
        } catch (SQLException ex) {
            System.out.println("Exception in WatersFileReader Contructor 1: " + ex.toString());
            writeLog.write("Exception in WatersFileReader Contructor 1: " + ex.toString());
        }
    }

    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst int value of Instrument Number
     * @param log WriteTextFile used for logging to file
     */
    public WatersFileReader(int inst, WriteTextFile log, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
            writeLog = log;
            ORAL_URINE_FILENAME = fileNameOralUrine;
        } catch (SQLException ex) {
            System.out.println("Exception in WatersFileReader Contructor 2: " + ex.toString());
            writeLog.write("Exception in WatersFileReader Contructor 2: " + ex.toString());
        }
    }

    public int GetFileCount() {
        InstrumentDAO idao = new InstrumentDAO();
        Instruments o_inst = idao.GetInstrumentByNumber(INST);

        File folder = new File(o_inst.getFilePath());

        File[] list = folder.listFiles(new FileUtil.DirectoryFilter());

        return list.length;
    }

    public int ProcessFile() throws FileNotFoundException, IOException {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        BaseInstRes res;
        InstResDAO idao = new InstResDAO();
        String values[];
        String accession = "";
        String acquisitionDate = "";
        int linesRead = 0;
        int resultsSaved = 0;
        QCInstDAO qciDAO = new QCInstDAO();
        boolean isQCDuplicate = false;
        boolean isQC = false;

        //Get the new File(s) to process
        File[] files = GetFiles(inst);

        for (File f : files) {

            //Get the Specimen Type
            if (ORAL_URINE_FILENAME == true) {
                String prefix = f.getName().substring(0, 2);
                ORAL_RESULTS = prefix.toLowerCase().equals("of");
            }

            isQCDuplicate = qciDAO.IsFileADuplicate(inst.getQcTable(), f.getName());
            
            try {
                CSVReader reader = new CSVReader(new FileReader(f), ',', '\"', 17);

                while ((values = reader.readNext()) != null) {
                    res = new BaseInstRes() {
                    };
                    linesRead++;

                    try {
                        //Don't process an empty line
                        if (values.length < 4) {
                            continue;
                        }
                        //Check if it is a new accession
                        if (values.length == 9) {
                            accession = values[1].trim();
                            acquisitionDate = values[7].trim() + " " + values[8].trim();
                            
                            //Don't process calibrators or QC lines as results
                            if (accession.equals("Blank")){
                                continue;
                            }
                            //Debug
                            //System.out.println("Line: " + linesRead + "\n" + "Proceesing Accession: " + accession);
                        } else if (accession.isEmpty() == false && !accession.equals("Blank")) {
                            isQC = accession.startsWith("QC");
                            
                            if (!isQC)
                            {
                                //Set Accession
                                res.setAccession(accession);

                                //Analyte Name / Test Name
                                res.setName(values[1].trim());

                                //Calculated Concentration / Result
                                if (values[9].trim().equals("N/A")) {
                                    res.setResult("0");
                                } else if (values[9].trim().equals("") || values[9].isEmpty()) {
                                    res.setResult("0");
                                } else if (values[9] == null) {
                                    res.setResult("0");
                                } else {
                                    String strippedNum = values[9].trim().replaceAll("[<>=]", "");
                                    Double d = Double.parseDouble(strippedNum);
                                    //int whole = (int) Math.round(d);
                                    //res.setResult("" + whole);
                                    res.setResult(String.valueOf(d));
                                }

                                //Set other values
                                if (ORAL_RESULTS) {
                                    res.setSpecimenType("Saliva");
                                } else {
                                    res.setSpecimenType("Urine");
                                }
                                Date date = new Date();
                                res.setPostedDate(date);
                                res.setUser(USER.getLogon());
                                res.setPosted(false);
                                res.setFileName(f.getName());

                                try {
                                    idao.InsertLog(inst.getResTable(), res);
                                    resultsSaved++;
                                } catch (SQLException ex1) {
                                    System.out.println("Could not insert result into database: " + ex1.toString());
                                    writeLog.write("Could not insert result into database: " + ex1.toString());
                                }
                            }
                            else if (!isQCDuplicate)
                            {
                                boolean inserted = insertQCResult(values, accession, acquisitionDate, f.getName(), inst);
                                if (inserted)
                                {
                                    resultsSaved++;
                                }
                            }
                        }
                    } catch (Exception e1) {
                        //System.out.println("Could parse line: " + e1.toString());
                        //writeLog.write("Could parse line: " + e1.toString());
                    }
                }
                
                f.setWritable(true);
                f.setReadable(true);
                f.setExecutable(true);
                //Backup and move the processed file.
                boolean moved = FileUtil.moveFile(f, new File(inst.getProcessedPath()));

                if (moved) {
                    System.out.println(f.getName() + " has been processed and backed up.");
                    writeLog.write(f.getName() + " has been processed and backed up.");
                } else {
                    System.out.println(f.getName() + " has been processed but move failed!");
                    writeLog.write(f.getName() + " has been processed but move failed!");
                }

            } catch (IOException ex) {
                //    System.out.println("Could not read/process file: " + ex.toString());
                //    writeLog.write("Could not read/process file: " + ex.toString());
            }
        }
        //Remove all result rows that may be calibration, standards, or blanks. 
        int removed = CleanUpResults();

        return linesRead - removed;
    }

    public boolean insertQCResult(String[] values, String accession, String acquisitionDate, String filename, Instruments inst)
    {
        QcInst qc = new QcInst();
        QCInstDAO qciDAO = new QCInstDAO();
        
        String levelStr = accession.substring(accession.length()-1, accession.length());
        Integer level;
        try
        {
            level = Integer.parseInt(levelStr);
        }
        catch (NumberFormatException ex)
        {
            System.out.println("Could not parse QC level from sample id: " + accession + ": " + ex.toString());
            writeLog.write("Could not parse QC level from sample id: " + accession + ": " + ex.toString());
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MMM-dd HH:mm:ss");
        Date acqDate = new Date();
        try
        {
            acqDate = sdf.parse(acquisitionDate);
        }
        catch (ParseException ex)
        {
            System.out.println("Could not parse acquisition date: " + ex.toString());
            writeLog.write("Could not parse acquisition date: " + ex.toString());
        }
        qc.setControl(accession);
        qc.setLevel(level);
        
        qc.setAcquisitionDate(acqDate);
        qc.setFileName(filename);
        qc.setCreatedDate(new Date());
        qc.setName(values[1].trim());
        //Calculated Concentration / Result
        if (values[9].trim().equals("N/A")) {
            qc.setResult("0");
        } else if (values[9].trim().equals("") || values[9].isEmpty()) {
            qc.setResult("0");
        } else if (values[9] == null) {
            qc.setResult("0");
        } else {
            String strippedNum = values[9].trim().replaceAll("[<>=]", "");
            Double d = Double.parseDouble(strippedNum);
            //int whole = (int) Math.round(d);
            //res.setResult("" + whole);
            qc.setResult(String.valueOf(d));
        }
        //Set other values
        if (ORAL_RESULTS) {
            qc.setSpecimenType(2);
        } else {
            qc.setSpecimenType(1);
        }
        
        boolean inserted = false;
        try
        {
            inserted = qciDAO.InsertQCData(inst.getQcTable(), qc);
        }
        catch (Exception ex)
        {
            System.out.println("Could not insert QC data for sample id: " + accession + ": " + ex.toString());
            writeLog.write("Could not insert QC data for sample id: " + accession + ": " + ex.toString());
        }
        return inserted;
    }
    
    public File[] GetFiles(Instruments inst) {
        try {
            File path = new File(inst.getFilePath());

            if ((inst.getFileRegExpr() == null) || (inst.getFileRegExpr().isEmpty())) {
                File folder = new File(inst.getFilePath());
                File[] tmp = folder.listFiles();
                ArrayList<File> list = new ArrayList<>();

                for (File f : tmp) {
                    if (f.isDirectory() == false) {
                        list.add(f);
                    }
                }

                File[] fileList = new File[list.size()];
                return list.toArray(fileList);

            } else {

                final Pattern pattern = Pattern.compile(inst.getFileRegExpr());
                return path.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        //return pattern.matcher(file.getName()).matches();
                        if (file.isDirectory()) {
                            return false;
                        }

                        return pattern.matcher(file.getName()).matches();
                    }
                });
            }
        } catch (Exception ex) {
            System.out.println("Exception getting files: " + ex.toString());
            writeLog.write("Exception getting files: " + ex.toString());
            return null;
        }
    }

    public int CleanUpResults() {
        try {
            int badLines = 0;

            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            InstResDAO idao = new InstResDAO();

            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "Blank%");
            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "STD%");
            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "%Control");
            badLines = badLines + idao.RemoveEmptyRows(inst.getResTable());

            //InstrumentInterfaceBL ibl = new InstrumentInterfaceBL();
            //ibl.DeleteRowsWithNoAccession(inst.getResTable());
            return badLines;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public int getINST() {
        return INST;
    }

    public void setINST(int INST) {
        this.INST = INST;
    }

    public Users getUSER() {
        return USER;
    }

    public void setUSER(Users USER) {
        this.USER = USER;
    }

    public String getINST_SERIAL() {
        return INST_SERIAL;
    }

    public void setINST_SERIAL(String INST_SERIAL) {
        this.INST_SERIAL = INST_SERIAL;
    }

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }

    public boolean isORAL_RESULTS() {
        return ORAL_RESULTS;
    }

    public void setORAL_RESULTS(boolean ORAL_RESULTS) {
        this.ORAL_RESULTS = ORAL_RESULTS;
    }
}
