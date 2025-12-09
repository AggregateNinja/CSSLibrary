/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package InstrumentIO;

import DAOS.InstResDAO;
import DAOS.InstrumentDAO;
import DAOS.SysOpDAO;
import DAOS.UserDAO;
import DOS.IDOS.BaseInstRes;
import DOS.Instruments;
import DOS.Users;
import Utility.FileUtil;
import Utility.WriteTextFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Jun 16, 2016
 */
public class WatersTargetLynxFileReader {

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
     * @param fileNameOralUrine
     */
    public WatersTargetLynxFileReader(int inst, int user, WriteTextFile log, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(user);
            USER = u;
            writeLog = log;
            ORAL_URINE_FILENAME = fileNameOralUrine;
        } catch (SQLException ex) {
            System.out.println("Exception in WatersTargetLynxFileReader Contructor 1: " + ex.toString());
            writeLog.write("Exception in WatersTargetLynxFileReader Contructor 1: " + ex.toString());
        }
    }

    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst int value of Instrument Number
     * @param log WriteTextFile used for logging to file
     * @param fileNameOralUrine
     */
    public WatersTargetLynxFileReader(int inst, WriteTextFile log, boolean fileNameOralUrine) {
        try {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
            writeLog = log;
            ORAL_URINE_FILENAME = fileNameOralUrine;
        } catch (SQLException ex) {
            System.out.println("Exception in WatersTargetLynxFileReader Contructor 2: " + ex.toString());
            writeLog.write("Exception in WatersTargetLynxFileReader Contructor 2: " + ex.toString());
        }
    }

    public int GetFileCount() {
        InstrumentDAO idao = new InstrumentDAO();
        Instruments o_inst = idao.GetInstrumentByNumber(INST);

        File folder = new File(o_inst.getFilePath());

        File[] list = folder.listFiles(new FileUtil.DirectoryFilter());

        return list.length;
    }

    public int ProcessFile() throws IOException {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        BaseInstRes res;
        InstResDAO idao = new InstResDAO();
        String accession = "";
        String testCode = "";
        int resultsSaved = 0;
        int xmlEvent;
        String text = null;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty("javax.xml.stream.isValidating", false);
        factory.setProperty("javax.xml.stream.supportDTD", false);

        //Get the new File(s) to process
        File[] files = GetFiles(inst);

        for (File f : files) {

            //Get the Specimen Type
            if (ORAL_URINE_FILENAME == true) {
                String prefix = f.getName().substring(0, 2);
                ORAL_RESULTS = prefix.toLowerCase().equals("of");
            }

            try {
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));
                XMLStreamReader xmlReader = factory.createXMLStreamReader(is);

                while (xmlReader.hasNext()) {
                    xmlEvent = xmlReader.next();
                    
                    res = new BaseInstRes() {
                    };
                    
                    switch (xmlEvent)
                    {
                        case XMLStreamReader.START_ELEMENT:
                            text = xmlReader.getLocalName();
                            
                            switch (text)
                            {
                                case "COMPOUND":
                                    testCode = xmlReader.getAttributeValue(null, "name");
                                    accession = xmlReader.getAttributeValue(null, "sampleid");
                                    //Don't process calibrators or QC lines as results
                                    if (accession != null && (accession.equals("Blank") || accession.startsWith("QC_"))) {
                                        continue;
                                    }
                                    break;
                                case "PEAK":
                                    String noResultFlag = xmlReader.getAttributeValue(null, "nosolflag");
                                    if (noResultFlag != null && !noResultFlag.equals("1"))
                                    {
                                        String result = xmlReader.getAttributeValue(null, "analconc");
                                        res.setAccession(accession);
                                        res.setName(testCode);
                                        res.setResult(result);
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
                                default:
                                    break;
                            }
                        default:
                            break;
                    }
                }

                //If QC is enabled run it
//                SysOpDAO sodao = new SysOpDAO();
//                Boolean QC = sodao.getBoolean("ModuleQCEnabled");
//                QC = (QC != null ? QC : false);
//                if (QC) {
//                    if ((inst.getQcTable() != null) && (inst.getQcTable().isEmpty() == false)) {
//                        AbSciexProcessQC absQc = new AbSciexProcessQC(inst);
//                        absQc.ProcessQC(f);
//                    }
//                }
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

            }
            catch (XMLStreamException ex)
            {
                System.out.println("Could not read/process file: " + ex.toString());
                writeLog.write("Could not read/process file: " + ex.toString());
            }
        }

        return resultsSaved;
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
