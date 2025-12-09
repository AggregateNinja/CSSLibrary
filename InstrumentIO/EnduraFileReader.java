/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package InstrumentIO;

import BL.InstrumentInterfaceBL;
import DAOS.InstResDAO;
import DAOS.InstrumentDAO;
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
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/08/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class EnduraFileReader {

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
     * @param fileNameOralUrine boolean if the specimen type is specified by the
     * file name
     */
    public EnduraFileReader(int inst, int user, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(user);
            this.ORAL_URINE_FILENAME = fileNameOralUrine;
            USER = u;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Constructor that is passed the Instrument Number. The user is defaulted
     * to a System user.
     *
     * @param inst int value for Instrument Number
     * @param fileNameOralUrine boolean if the specimen type is specified by the
     * file name
     */
    public EnduraFileReader(int inst, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            this.ORAL_URINE_FILENAME = fileNameOralUrine;
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
    public EnduraFileReader(int inst) {
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
        File[] list = folder.listFiles();

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

        for (File file : list) {

            //Make sure not to pick do anything with directories
            if (file.isDirectory()) {
                continue;
            }
            //String path = filePath + file.toString().trim();
            //GetFileMetaData(path);
            if (inst.getFileRegExpr() == null) {

                try {
                    CSVReader reader = new CSVReader(new FileReader(file), ',');

                    while ((values = reader.readNext()) != null) {
                        res = new BaseInstRes() {
                        };
                        linesRead++;

                        try {
                            //SampleName
                            res.setAccession(values[0]);
                            
                            //Calclulated Amt. (Numeric Result)
                            res.setResult(values[1]);
                            
                            //CompundName
                            res.setName(values[2]);

                            //Sample Type
                            res.setIdentifier(values[4]);

                            //Set other values
                            Date date = new Date();
                            res.setPostedDate(date);
                            res.setUser(USER.getLogon());
                            res.setPosted(false);
                            if (ORAL_URINE_FILENAME == true) {
                                String prefix = file.getName().substring(0, 2);
                                if (prefix.toLowerCase().equals("of")) {
                                    res.setSpecimenType("Saliva");
                                } else if (prefix.toLowerCase().equals("ur")) {
                                    res.setSpecimenType("Urine");
                                } else {
                                    res.setSpecimenType(null);
                                }
                            }

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

                //return linesRead;
            } else {
                File[] files = GetFiles();

                for (File f : files) {
                    try {
                        CSVReader reader = new CSVReader(new FileReader(f), ',', '\"', 1);

                        while ((values = reader.readNext()) != null) {
                            res = new BaseInstRes() {
                            };
                            linesRead++;

                            try {
                                //SampleName
                                res.setAccession(values[0]);

                                //Calclulated Amt. (Numeric Result)
                                res.setResult(values[1]);

                                //CompundName
                                res.setName(values[2]);

                                //Sample Type
                                res.setIdentifier(values[4]);
                                //Set other values
                                Date date = new Date();
                                res.setPostedDate(date);
                                res.setUser(USER.getLogon());
                                res.setPosted(false);
                                if (ORAL_URINE_FILENAME == true) {
                                    String prefix = f.getName().substring(0, 2);
                                    if (prefix.toLowerCase().equals("of")) {
                                        res.setSpecimenType("Saliva");
                                    } else if (prefix.toLowerCase().equals("ur")) {
                                        res.setSpecimenType("Urine");
                                    } else {
                                        res.setSpecimenType(null);
                                    }
                                }

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
                //return linesRead;
            }
        }
        //Remove all result rows that may be calibration, standards, or blanks. 
        int removed = CleanUpResults();
        //Remove any duplicate rows
        //idao.CleanUpDuplicates(inst.getResTable());

        return linesRead - removed;
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
                    return pattern.matcher(file.getName()).matches();
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public void GetFileMetaData(String file) throws FileNotFoundException {
        Scanner in = new Scanner(new File(file));

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

            int updates = idao.UpdateReruns(inst.getResTable());

            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "Blank%");
            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "STD%");
            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "Cal_%");
            //badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "%R");
            badLines = badLines + idao.RemoveEmptyRows(inst.getResTable());
            
            InstrumentInterfaceBL ibl = new InstrumentInterfaceBL();
            ibl.DeleteRowsWithNoAccession(inst.getResTable());
            ibl.SetNFtoZero(inst.getResTable());

            return badLines;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
}
