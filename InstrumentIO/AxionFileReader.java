/*
 * Copyright (C) 2015 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package InstrumentIO;

import BL.InstrumentInterfaceBL;
import DAOS.InstResDAO;
import DAOS.InstrumentDAO;
import DAOS.SysOpDAO;
import DAOS.UserDAO;
import DOS.IDOS.BaseInstRes;
import DOS.Instruments;
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

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 02/25/2015
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class AxionFileReader {

    private int INST;
    private Users USER;
    private String INST_SERIAL;
    private String RUN_DATE;
    private boolean ORAL_RESULTS = false;

    /**
     * Constructor that is passed the Instrument Number and a User for logging
     * purposes.
     *
     * @param inst int value for Instrument Number
     * @param user int value for User ID
     */
    public AxionFileReader(int inst, int user) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(user);
            USER = u;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Constructor that is passed the Instrument Number and a User for logging
     * purposes.
     *
     * @param inst int value for Instrument Number
     */
    public AxionFileReader(int inst) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Returns the number of files in the Instrument's results direcotry.
     *
     * @return int Count of result files.
     */
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
        String filePath = inst.getFilePath();
        BaseInstRes res = new BaseInstRes() {
        };
        InstResDAO idao = new InstResDAO();
        String values[];
        File folder = new File(inst.getFilePath());
        File[] list = folder.listFiles();
        int fileCount = 0;
        int linesRead = 0;
        int x = 0;
        String specimen_type = null;

        if ((inst.getFileRegExpr() == null) || (inst.getFileRegExpr().isEmpty())) {
            for (File file : list) {
                System.out.println(file.getName());

                String accession = "";
                linesRead = 0;

                //Make sure not to pick do anything with directories
                if (file.isDirectory()) {
                    continue;
                } else {

                    try {
                        
                        /**
                         * This instrument currently only does one specimen type
                         */
                        specimen_type = "Urine";
                        
                        CSVReader reader = new CSVReader(new FileReader(file), ',', '\"', 1);

                        while ((values = reader.readNext()) != null) {
                            res = new BaseInstRes() {
                            };
                            linesRead++;

                            //Get the Accession number
                            String[] line = values[0].split("_");
                            accession = line[2];
                            
                            if (accession.contains("-"))
                            {
                                int dash = accession.indexOf("-");
                                accession = accession.substring(0, dash);
                            }
                            res.setAccession(accession);
                            res.setName(values[1].trim());

                            try {
                                if (values[2].trim().isEmpty()) {
                                    //res.setResult(values[7].trim());
                                    //If there is no result in the file, leave the result set to null
                                } else {
                                    String strippedNum = values[2].trim().replaceAll("[<>=,]", "");
                                    double d = Double.parseDouble(strippedNum);
                                    int whole = (int) Math.round(d);
                                    res.setResult("" + whole);
                                }
                            } catch (Exception ex) {

                                System.out.println(ex.getMessage());
                                //Catches a parse exception. Just continue to next line
                                continue;
                            }

                            res.setUnits(values[3].trim());

                            //Currently only runs urine
                            res.setSpecimenType(specimen_type);

                            Date date = new Date();
                            res.setPostedDate(date);
                            res.setUser(USER.getLogon());
                            res.setPosted(false);
                            res.setFileName(file.getName());

                            try {
                                idao.InsertLog(inst.getResTable(), res);

                            } catch (SQLException ex1) {
                                System.out.println("Could not insert result into database: " + ex1.toString());
                            }
                        }
                      
                        //If QC is enabled run it
                        SysOpDAO sodao = new SysOpDAO();
                        Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                        QC = (QC != null ? QC : false);
                        if (QC) {
                            if ((inst.getQcTable() != null) || (inst.getQcTable().isEmpty() == false)) {
                                AxionProcessQC axQC = new AxionProcessQC(inst, specimen_type);
                                axQC.ProcessQC(file);
                            }
                        }

                        file.setWritable(true);
                        file.setReadable(true);
                        file.setExecutable(true);
                        //Backup and move the processed file.
                        boolean moved = FileUtil.moveFile(file, new File(inst.getProcessedPath()));

                        if (moved) {
                            System.out.println(file.getName() + " has been processed and backed up.");
                        } else {
                            System.out.println(file.getName() + " has been processed but move failed!");
                        }

                        /*
                         if (file.renameTo(new File(inst.getProcessedPath() + File.separator + file.getName()))) {
                         System.out.println(file.getName() + " has been processed and backed up.");
                         } else {
                         System.out.println(file.getName() + " has been processed but move failed!");
                         }
                         */
                    } catch (IOException ex) {
                        System.out.println("Could not read/process file: " + ex.toString());
                        //return 0;
                    }
                }
            }

            //Remove all result rows that may be calibration, standards, or blanks. 
            int removed = CleanUpResults();
            //Remove any duplicate rows
            //idao.CleanUpDuplicates(inst.getResTable());

            return linesRead - removed;

        } else {
            File[] files = GetFiles();

            for (File f : files) {
                System.out.println(f.getName());
                String accession = "";
                linesRead = 0;

                /**
                 * This instrument currently only does one specimen type
                 */
                specimen_type = "Urine";
                
                
                CSVReader reader = new CSVReader(new FileReader(f), ',', '\"', 1);

                while ((values = reader.readNext()) != null) {
                    res = new BaseInstRes() {
                    };
                    linesRead++;

                    //Get the Accession number
                    String[] line = values[0].split("_");
                    accession = line[2];

                    if (accession.contains("-"))
                    {
                        int dash = accession.indexOf("-");
                        accession = accession.substring(0, dash);
                    }
                    res.setAccession(accession);
                    res.setName(values[1].trim());

                    try {
                        if (values[2].trim().isEmpty()) {
                            //res.setResult(values[7].trim());
                            //If there is no result in the file, leave the result set to null
                        } else {
                            String strippedNum = values[2].trim().replaceAll("[<>=,]", "");
                            double d = Double.parseDouble(strippedNum);
                            int whole = (int) Math.round(d);
                            res.setResult("" + whole);
                        }
                    } catch (Exception ex) {

                        System.out.println(ex.getMessage());
                        //Catches a parse exception. Just continue to next line
                        continue;
                    }

                    res.setUnits(values[3].trim());
                    //Currently only runs urine
                    res.setSpecimenType(specimen_type);

                    Date date = new Date();
                    res.setPostedDate(date);
                    res.setUser(USER.getLogon());
                    res.setPosted(false);
                    res.setFileName(f.getName());

                    try {
                        idao.InsertLog(inst.getResTable(), res);

                    } catch (SQLException ex1) {
                        System.out.println("Could not insert result into database: " + ex1.toString());
                    }
                }
                    
                //If QC is enabled run it
                SysOpDAO sodao = new SysOpDAO();
                Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                QC = (QC != null ? QC : false);
                if (QC) {
                    if ((inst.getQcTable() != null) || (inst.getQcTable().isEmpty() == false)) {
                        AxionProcessQC axQC = new AxionProcessQC(inst, specimen_type);
                        axQC.ProcessQC(f);
                    }
                }

                f.setWritable(true);
                f.setReadable(true);
                f.setExecutable(true);
                //Backup and move the processed file.
                boolean moved = FileUtil.moveFile(f, new File(inst.getProcessedPath()));

                if (moved) {
                    System.out.println(f.getName() + " has been processed and backed up.");
                } else {
                    System.out.println(f.getName() + " has been processed but move failed!");
                }
                /*
                 if (f.renameTo(new File(inst.getProcessedPath() + File.separator + f.getName()))) {
                 System.out.println(f.getName() + " has been processed and backed up.");
                 } else {
                 System.out.println(f.getName() + " has been processed but move failed!");
                 }
                 */
            }
            //Remove all result rows that may be calibration, standards, or blanks. 
            int removed = CleanUpResults();
            //Remove any duplicate rows
            //idao.CleanUpDuplicates(inst.getResTable());

            return linesRead - removed;
        }
    }

    /**
     * Gets all result files from instrument's result directory that match the
     * instruments file name regex.
     *
     * @return File[] of result files
     */
    public File[] GetFiles() {
        try {
            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            final Pattern pattern = Pattern.compile(inst.getFileRegExpr());
            File path = new File(inst.getFilePath());
            return path.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {

                    if (file.isDirectory()) {
                        return false;
                    }

                    return pattern.matcher(file.getName()).matches();
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Deletes all rows that were inserted and are not valid
     *
     * @return int Number of rows deleted.
     */
    public int CleanUpResults() {
        try {
            int badLines = 0;

            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            InstResDAO idao = new InstResDAO();

            badLines = badLines + idao.RemoveEmptyNameRows(inst.getResTable());
            //badLines = badLines + idao.RemoveEmptyRows(inst.getResTable());

            //InstrumentInterfaceBL ibl = new InstrumentInterfaceBL();
            //ibl.DeleteRowsWithNoAccession(inst.getResTable());

            return badLines;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
}
