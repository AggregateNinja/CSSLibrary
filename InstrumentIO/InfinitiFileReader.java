/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 11, 2014
 */
package InstrumentIO;

import BL.InstrumentInterfaceBL;
import DAOS.InstResDAO;
import DAOS.InstrumentDAO;
import DAOS.PreferencesDAO;
import DAOS.UserDAO;
import DOS.IDOS.BaseInstRes;
import DOS.Instruments;
import DOS.Users;
import Utility.FileUtil;
import Utility.WriteTextFile;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @date: Aug 11, 2014 3:31:49 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: InfinitiFileReader.java (UTF-8)
 *
 * @Description: Class for reading and processing files from an AutoGenomics
 * Infinity Instrument.
 *
 */
public class InfinitiFileReader
{

    private int INST;
    private Users USER;
    private final Integer TransDept;
    private ArrayList<String> ACCESSIONS;

    /**
     * Constructor that is passed the Instrument Number and a User for logging
     * purposes.
     *
     * @param inst int value for Instrument Number
     * @param user int value for User ID
     */
    public InfinitiFileReader(int inst, int user) throws Exception
    {
        try
        {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(user);
            USER = u;
            ACCESSIONS = new ArrayList<>();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

        TransDept = GetTranslationalDepartment();

        if (TransDept == null)
        {
            throw new Exception("Unable to find Translational Department");
        }
    }

    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst int value of Instrument Number
     */
    public InfinitiFileReader(int inst) throws Exception
    {
        try
        {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;
            ACCESSIONS = new ArrayList<>();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        TransDept = GetTranslationalDepartment();

        if (TransDept == null)
        {
            throw new Exception("Unable to find Translational Department");
        }
    }

    private Integer GetTranslationalDepartment()
    {
        PreferencesDAO pdao = new PreferencesDAO();
        Integer dept = pdao.getInteger("TranslationalSoftwareDepartment");

        return dept;
    }

    public int GetFileCount()
    {
        InstrumentDAO idao = new InstrumentDAO();
        Instruments o_inst = idao.GetInstrumentByNumber(INST);

        File folder = new File(o_inst.getFilePath());

        File[] list = folder.listFiles(new FileUtil.DirectoryFilter());

        return list.length;
    }

    public int ProcessFile(WriteTextFile log) throws FileNotFoundException, IOException
    {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        BaseInstRes res = new BaseInstRes()
        {
        };
        InstResDAO idao = new InstResDAO();
        String values[];
        File folder = new File(inst.getFilePath());
        File[] list = folder.listFiles();
        int linesRead = 0;
        int x = 0;

        for (File file : list)
        {

            //Make sure not to pick do anything with directories
            if (file.isDirectory())
            {
                continue;
            }
            else
            {

                //If instrument DOESN'T HAVE a specific file name (Grab every file regardless of name) 
                if (inst.getFileRegExpr() == null)
                {

                    try
                    {
                        CSVReader reader = new CSVReader(new FileReader(file), ',');

                        while ((values = reader.readNext()) != null)
                        {
                            res = new BaseInstRes()
                            {
                            };
                            linesRead++;

                            try
                            {
                                x = 0;

                                //Accession
                                res.setAccession(values[x].trim());

                                //Analyte Name
                                x++;
                                res.setName(values[x].trim());

                                //Result
                                x++;
                                res.setResult(values[x].trim());
                                res.setComment(values[x].trim());

                                //Set other values
                                Date date = new Date();
                                res.setPostedDate(date);
                                res.setUser(USER.getLogon());
                                res.setPosted(false);
                                res.setFileName(file.getName());

                                try
                                {
                                    idao.InsertLog(inst.getResTable(), res);
                                    //Populate ACCESSIONS arraylist for each accession
                                    //to check if it is complete after everything is read.
                                    //Make sure there is no Duplicate Accessions
                                    if (ACCESSIONS.contains(res.getAccession()) == false)
                                    {
                                        ACCESSIONS.add(res.getAccession());
                                    }

                                }
                                catch (SQLException ex1)
                                {
                                    System.out.println("Could not insert result into database: " + ex1.toString());
                                }

                            }
                            catch (Exception e1)
                            {
                                System.out.println("Could parse line: " + e1.toString());
                            }
                        }

                        file.setWritable(true);
                        file.setReadable(true);
                        file.setExecutable(true);
                        //Backup and move the processed file.
                        boolean moved = FileUtil.moveFile(file, new File(inst.getProcessedPath()));

                        if (moved)
                        {
                            System.out.println(file.getName() + " has been processed and backed up.");
                            log.write(file.getName() + " has been processed and backed up.");
                        }
                        else
                        {
                            System.out.println(file.getName() + " has been processed but move failed!");
                            log.write(file.getName() + " has been processed but move failed!");
                        }

                    }
                    catch (IOException ex)
                    {
                        System.out.println("Could not read/process file: " + ex.toString());
                    }

                }
                else
                { //If there is a file name regular expression
                    File[] files = GetFiles();

                    for (File f : files)
                    {
                        try
                        {
                            CSVReader reader = new CSVReader(new FileReader(f), ',');

                            while ((values = reader.readNext()) != null)
                            {
                                res = new BaseInstRes()
                                {
                                };
                                linesRead++;

                                try
                                {
                                    x = 0;

                                    //Accession
                                    res.setAccession(values[x].trim());

                                    //Analyte Name
                                    x++;
                                    res.setName(values[x].trim());

                                    //Result
                                    x++;
                                    res.setResult(values[x].trim());
                                    res.setComment(values[x].trim());

                                    //Set other values
                                    Date date = new Date();
                                    res.setPostedDate(date);
                                    res.setUser(USER.getLogon());
                                    res.setPosted(false);
                                    res.setFileName(file.getName());

                                    try
                                    {
                                        idao.InsertLog(inst.getResTable(), res);
                                        //Populate ACCESSIONS arraylist for each accession
                                        //to check if it is complete after everything is read.
                                        //Make sure there is no Duplicate Accessions
                                        if (ACCESSIONS.contains(res.getAccession()) == false)
                                        {
                                            ACCESSIONS.add(res.getAccession());
                                        }
                                    }
                                    catch (SQLException ex1)
                                    {
                                        System.out.println("Could not insert result into database: " + ex1.toString());
                                    }

                                }
                                catch (Exception e1)
                                {
                                    System.out.println("Could parse line: " + e1.toString());
                                }
                            }
                            f.setWritable(true);
                            f.setReadable(true);
                            f.setExecutable(true);
                            //Backup and move the processed file.
                            boolean moved = FileUtil.moveFile(f, new File(inst.getProcessedPath()));

                            if (moved)
                            {
                                System.out.println(f.getName() + " has been processed and backed up.");
                                log.write(f.getName() + " has been processed and backed up.");
                            }
                            else
                            {
                                System.out.println(f.getName() + " has been processed but move failed!");
                                log.write(f.getName() + " has been processed but move failed!");
                            }

                        }
                        catch (Exception ex)
                        {
                            System.out.println("Could not read/process file: " + ex.toString());
                            log.write("Could not read/process file: " + ex.toString());
                        }
                    }
                }
            }
        }
        //Remove all result rows that may be calibration, standards, or blanks. 
        int removed = CleanUpResults();
        //Remove any duplicate rows
        //idao.CleanUpDuplicates(inst.getResTable());

        //Now check results for Translational Software Queue Addidition
        //InstrumentInterfaceBL ibl = new InstrumentInterfaceBL();

        //int completed = ibl.CompleteForTranslational(ACCESSIONS);
        //java.util.Date logDate = new java.util.Date();
        //System.out.println("Number of Accessions Completed and Queued: " + completed);
        //log.write(logDate.toString() + "  Number of Accessions Completed and Queued: " + completed);

        return linesRead - removed;
    }

    public File[] GetFiles()
    {
        try
        {
            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            final Pattern pattern = Pattern.compile(inst.getFileRegExpr());
            File path = new File(inst.getFilePath());
            return path.listFiles(new FileFilter()
            {
                @Override
                public boolean accept(File file)
                {

                    if (file.isDirectory())
                    {
                        return false;
                    }

                    return pattern.matcher(file.getName()).matches();
                }
            });
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int CleanUpResults()
    {
        try
        {
            int badLines = 0;

            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);
            InstResDAO idao = new InstResDAO();

            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "B1%");
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "NA%");
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "%-%");

            //Or just get rid of all rows with no valid accession
            InstrumentInterfaceBL bl = new InstrumentInterfaceBL();
            bl.DeleteRowsWithNoAccession(inst.getResTable());

            return badLines;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
}
