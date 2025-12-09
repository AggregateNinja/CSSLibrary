/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/08/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class ABSciexFileReader {

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
    public ABSciexFileReader(int inst, int user) {
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
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst int value of Instrument Number
     */
    public ABSciexFileReader(int inst) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
        } catch (Exception ex) {
            System.out.println(ex.toString());
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
        String accession = null;
        BaseInstRes res = new BaseInstRes() {
        };
        InstResDAO idao = new InstResDAO();
        String values[];
        File folder = new File(inst.getFilePath());
        File[] list = folder.listFiles();
        int fileCount = 0;
        int linesRead = 0;
        int x = 0;

        if ((inst.getFileRegExpr() == null) || (inst.getFileRegExpr().isEmpty())) {
            for (File file : list) {

                //Make sure not to pick do anything with directories
                if (file.isDirectory()) {
                    continue;
                } else {
                    //Get Specimen Type
                    GetFileMetaData2(file);

                    try {
                        CSVReader reader = new CSVReader(new FileReader(file), ',', '\"', 4);

                        while ((values = reader.readNext()) != null) {
                            res = new BaseInstRes() {
                            };
                            linesRead++;

                            try {
                                x = 0;

                                accession = values[x].trim().replaceFirst("^0+(?!$)", "");
                                //Sample Name
                                res.setAccession(accession);
                                x++;
                                //Analyte Name
                                res.setName(values[x].trim());
                                x++;
                                //Analyte Peak Area

                                x++;
                                //Analyte Concentration

                                x++;
                                //Calculated Concentration
                                res.setResult(values[x].trim());

                                try {//If there is an interpertation keep it, else skip it
                                    x++;
                                    //Result
                                    res.setInterpretation(values[x].trim());
                                } catch (Exception ex) {

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
                                res.setFileName(file.getName());

                                try {
                                    idao.InsertLog(inst.getResTable(), res);

                                } catch (SQLException ex1) {
                                    System.out.println("Could not insert result into database: " + ex1.toString());
                                    //return 0;
                                }

                            } catch (Exception e1) {
                                System.out.println("Could parse line: " + e1.toString());
                                //return 0;
                            }
                        }

                        //If QC is enabled run it
                        SysOpDAO sodao = new SysOpDAO();
                        Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                        QC = (QC != null ? QC : false);
                        if (QC) {
                            if ((inst.getQcTable() != null) || (inst.getQcTable().isEmpty() == false)) {
                                AbSciexProcessQC absQc = new AbSciexProcessQC(inst);
                                absQc.ProcessQC(file);
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

                //Get Specimen Type
                GetFileMetaData2(f);
                
                try {
                    CSVReader reader = new CSVReader(new FileReader(f), ',', '\"', 4);

                    while ((values = reader.readNext()) != null) {
                        res = new BaseInstRes() {
                        };
                        linesRead++;

                        try {
                            x = 0;

                            accession = values[x].trim().replaceFirst("^0+(?!$)", "");
                            //Sample Name
                            res.setAccession(accession);
                            x++;
                            //Analyte Name
                            res.setName(values[x].trim());
                            x++;
                            //Analyte Peak Area

                            x++;
                            //Analyte Concentration

                            x++;
                            //Calculated Concentration
                            if (values[x].trim().equals("N/A")) {
                                res.setResult(values[x].trim());
                            } else {
                                String strippedNum = values[x].trim().replaceAll("[<>=]", "");
                                double d = Double.parseDouble(strippedNum);
                                res.setResult("" + d);
                            }

                            try {//If there is an interpertation keep it, else skip it
                                x++;
                                //Result
                                res.setInterpretation(values[x].trim());
                            } catch (Exception ex) {

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

                            } catch (SQLException ex1) {
                                System.out.println("Could not insert result into database: " + ex1.toString());
                                //return 0;
                            }

                        } catch (Exception e1) {
                            System.out.println("Could parse line: " + e1.toString());
                            //return 0;
                        }
                    }

                    //If QC is enabled run it
                    SysOpDAO sodao = new SysOpDAO();
                    Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                    QC = (QC != null ? QC : false);
                    if (QC) {
                        if ((inst.getQcTable() != null) && (inst.getQcTable().isEmpty() == false)) {
                            AbSciexProcessQC absQc = new AbSciexProcessQC(inst);
                            absQc.ProcessQC(f);
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

                } catch (Exception ex) {
                    System.out.println("Could not read/process file: " + ex.toString());
                    //return 0;
                }
            }
            //Remove all result rows that may be calibration, standards, or blanks. 
            int removed = CleanUpResults();
            //Remove any duplicate rows
            //idao.CleanUpDuplicates(inst.getResTable());

            return linesRead - removed;
        }
    }

    /**
     * This version of the ProcessFile method is used for labs using the newer
     * version of the .CSV file. Life Brite was the first lab.
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int ProcessFileVersion2() throws FileNotFoundException, IOException {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        String filePath = inst.getFilePath();
        String accession = null;
        BaseInstRes res = new BaseInstRes() {
        };
        InstResDAO idao = new InstResDAO();
        String values[];
        File folder = new File(inst.getFilePath());
        File[] list = folder.listFiles();
        int fileCount = 0;
        int linesRead = 0;
        int x = 0;

        if ((inst.getFileRegExpr() == null) || (inst.getFileRegExpr().isEmpty())) {
            for (File file : list) {

                //Make sure not to pick do anything with directories
                if (file.isDirectory()) {
                    continue;
                } else {
                    //Get Specimen Type
                    //GetFileMetaData2(file);

                    try {
                        CSVReader reader = new CSVReader(new FileReader(file), ',');

                        while ((values = reader.readNext()) != null) {
                            res = new BaseInstRes() {
                            };
                            linesRead++;

                            try {
                                accession = values[x].trim().replaceFirst("^0+(?!$)", "");
                                //Sample Name
                                res.setAccession(accession);

                                //Analyte Name
                                res.setName(values[2].trim());

                                //Calculated Concentration
                                if (values[3].trim().equals("< 0")) {
                                    res.setResult("N/A");
                                } else {
                                    try {
                                        String strippedNum = values[3].trim().replaceAll("[<>=]", "");
                                        double d = Double.parseDouble(strippedNum);
                                        
                                        res.setResult("" + d);
                                    } catch (Exception ex) {
                                        res.setResult(values[3].trim());
                                    }
                                }

                                res.setSpecimenType("Urine");

                                Date date = new Date();
                                res.setPostedDate(date);
                                res.setUser(USER.getLogon());
                                res.setPosted(false);
                                res.setFileName(file.getName());

                                try {
                                    idao.InsertLog(inst.getResTable(), res);

                                } catch (SQLException ex1) {
                                    System.out.println("Could not insert result into database: " + ex1.toString());
                                    //return 0;
                                }

                            } catch (Exception e1) {
                                System.out.println("Could not parse line: " + e1.toString());
                                //return 0;
                            }
                        }

                        //If QC is enabled run it
                        SysOpDAO sodao = new SysOpDAO();
                        Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                        QC = (QC != null ? QC : false);
                        if (QC) {
                            if ((inst.getQcTable() != null) || (inst.getQcTable().isEmpty() == false)) {
                                AbSciexProcessQC absQc = new AbSciexProcessQC(inst);
                                absQc.ProcessQC(file);
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
                int fileLines = 0;
                //Get Specimen Type
                //GetFileMetaData2(f);
                try {
                    CSVReader reader = new CSVReader(new FileReader(f), ',');

                    while ((values = reader.readNext()) != null) {
                        res = new BaseInstRes() {
                        };
                        linesRead++;
                        fileLines++;

                        if (fileLines > 4) {
                            try {
                                accession = values[x].trim().replaceFirst("^0+(?!$)", "");

                                //Sample Name
                                res.setAccession(accession);

                                //Analyte Name
                                res.setName(values[2].trim());

                                //Calculated Concentration
                                if (values[3].trim().equals("< 0")) {
                                    res.setResult("N/A");
                                } else {
                                    try {
                                        String strippedNum = values[3].trim().replaceAll("[<>=]", "");
                                        double d = Double.parseDouble(strippedNum);                                        
                                        res.setResult("" + d);
                                    } catch (Exception ex) {
                                        res.setResult(values[3].trim());
                                    }
                                }

                                //Set other values
                                res.setSpecimenType("Urine");

                                Date date = new Date();
                                res.setPostedDate(date);
                                res.setUser(USER.getLogon());
                                res.setPosted(false);
                                res.setFileName(f.getName());

                                try {
                                    idao.InsertLog(inst.getResTable(), res);

                                } catch (SQLException ex1) {
                                    System.out.println("Could not insert result into database: " + ex1.toString());
                                    //return 0;
                                }

                            } catch (Exception e1) {
                                //Ignore header lines
                                if(fileLines > 4){
                                    System.out.println("Could not parse line: " + e1.toString());
                                }
                                //return 0;
                            }
                        }
                    }

                    //If QC is enabled run it
                    SysOpDAO sodao = new SysOpDAO();
                    Boolean QC = sodao.getBoolean("ModuleQCEnabled");
                    QC = (QC != null ? QC : false);
                    if (QC) {
                        if ((inst.getQcTable() != null) && (inst.getQcTable().isEmpty() == false)) {
                            AbSciexProcessQC absQc = new AbSciexProcessQC(inst);
                            absQc.ProcessQC(f);
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

                } catch (Exception ex) {
                    System.out.println("Could not read/process file: " + ex.toString());
                    //return 0;
                }
            }
            //Remove all result rows that may be calibration, standards, or blanks. 
            int removed = CleanUpResults();
            //Remove any duplicate rows
            //idao.CleanUpDuplicates(inst.getResTable());

            return linesRead - removed;
        }
    }

    public File[] GetFiles() {
        try {
            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            final Pattern pattern = Pattern.compile(inst.getFileRegExpr());
            File path = new File(inst.getFilePath());
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
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public void GetFileMetaData2(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file.getCanonicalPath())));
            String readLine = null;
            int count = 0;
            boolean firstBlank = false;

            while ((readLine = reader.readLine()) != null) {
                count++;

                if (count == 1 && readLine.isEmpty())
                {
                    firstBlank = true;
                }
                
                //Debug 
                System.out.println("Line #" + count);
                System.out.println(readLine);

                String[] segments = readLine.split(",");

                if ((count == 2 && !firstBlank) || count == 3) {
                    //Debug
                    System.out.println("Seg[0]: " + segments[0].trim());
                    System.out.println("Seg[1]: " + segments[1].trim());
                    System.out.println("Seg[2]: " + segments[2].toUpperCase());

                    INST_SERIAL = segments[0].trim();
                    RUN_DATE = segments[1].trim();

                    String description = segments[2].toUpperCase();
                    if (description.contains("ORAL")) {
                        ORAL_RESULTS = true;
                    }

                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
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

    public int CleanUpResults() {
        try {
            int badLines = 0;

            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            InstResDAO idao = new InstResDAO();

            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "Blank%");
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "STD%");
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "%Control");
            badLines = badLines + idao.RemoveEmptyRows(inst.getResTable());

            InstrumentInterfaceBL ibl = new InstrumentInterfaceBL();
            ibl.DeleteRowsWithNoAccession(inst.getResTable());

            return badLines;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
}
