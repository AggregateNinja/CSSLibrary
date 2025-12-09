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
 * @date: December 16, 2014 9:00:49 AM
 * @author: Mike Douglass miked@csslis.com
 *
 * @project: CSSLibrary
 * @file name: LifeQuantFileReader.java (UTF-8)
 *
 * @Description: Class for reading and processing files from a LifeQuant
 * Genetics Instrument
 *
 */
/******************************************************************************/
public class LifeQuantFileReader
{

    /**************************************************************************/
    private int INST;
    private Users USER;
    private final Integer TransDept;
    private ArrayList<String> ACCESSIONS;

    /**************************************************************************/
    /* Constructor that is passed the Instrument Number and a User for logging
     * purposes.
     *
     * @param inst int value for Instrument Number
     * @param user int value for User ID
     */
    public LifeQuantFileReader(int inst, int user) throws Exception
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

    /**************************************************************************/    
    /**
     * Constructor that is only called by applications that have no user
     * activity. Logs everything as a System Process other than an User
     * operation
     *
     * @param inst int value of Instrument Number
     */
    public LifeQuantFileReader(int inst) throws Exception
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

    /**************************************************************************/
    private Integer GetTranslationalDepartment()
    {
        PreferencesDAO pdao = new PreferencesDAO();
        Integer dept = pdao.getInteger("TranslationalSoftwareDepartment");

        return dept;
    }

    /**************************************************************************/
    public int GetFileCount()
    {
        InstrumentDAO idao = new InstrumentDAO();
        Instruments o_inst = idao.GetInstrumentByNumber(INST);

        File folder = new File(o_inst.getFilePath());

        File[] list = folder.listFiles(new FileUtil.DirectoryFilter());

        return list.length;
    }

    /**************************************************************************/
    public int ProcessFile(WriteTextFile log) throws FileNotFoundException, IOException
    {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        BaseInstRes res = new BaseInstRes() {};

        InstResDAO idao = new InstResDAO();
        File folder = new File(inst.getFilePath());
        File[] list = folder.listFiles();

        boolean isDataLine = false;
        int linesRead = 0;
        String values[];

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
                        //Fist find out what type of result file this is
                        try
                        {
                            String val[];
                            CSVReader rdr = new CSVReader(new FileReader(file), '\t', '\"');
                            while ((val = rdr.readNext()) != null)
                            {
                                try
                                {
                                    if (val[0].contains("Copy"))
                                    {
                                        int copyLines = 0;
                                        LifeQuantCopyReader copyReader = new LifeQuantCopyReader(INST, USER, TransDept);
                                        copyLines = copyReader.ProcessFile(file, log);
                                        return copyLines;
                                    }
                                    else
                                    {
                                        break;
                                    }
                                    
                                }
                                catch (IOException ex)
                                {
                                    log.write((new Date().toString()) + " Error trying to determine which type of file " + file.getCanonicalPath() + " is :" + ex.getMessage());
                                    System.out.println("Exception parsing line. LifeQuantFileReader::ProcessFile - " + ex.toString());
                                    return -1;
                                }
                            }
                        }
                        catch (IOException ex)
                        {
                            log.write((new Date().toString()) + " Error trying to read " + file.getCanonicalPath() + " :" + ex.getMessage());
                            System.out.println("Exception geting file type. LifeQuantFileReader::ProcessFile - " + ex.toString());
                            return -1;
                        }

                        isDataLine = false;
                        CSVReader reader = new CSVReader(new FileReader(file), '\t', '\"');

                        while ((values = reader.readNext()) != null)
                        {
                            res = new BaseInstRes(){};
                            linesRead++;

                            try
                            {
                                // Check to see if this is a blank line and if it is continue looping
                                if (values.length < 2)
                                    continue;
                                
                                // Check to see if we reached the end of the data that we need
                                // and if we did then break out of the loop
                                if(values[0].equals("Assay ID"))
                                {
                                    break;
                                }
                                                                                              
                                if(isDataLine == true)
                                {
                                    //Analyte Name
                                    res.setName(values[1].trim());

                                    // Gene Symbol
                                    res.setIdentifier(values[2].trim());

                                    // SNP Reference
                                    res.setInterpretation(values[3].trim());

                                    //Accession
                                    String tmpAccession = values[4].trim();
                                    String delims = "[ ]+";
                                    String[] tokens = tmpAccession.split(delims);
                                    res.setAccession(tokens[0].trim());

                                    //Result
                                    res.setResult(values[5].trim());
                                    res.setComment(values[5].trim());

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
                                        log.write((new Date().toString()) + " Error trying to insert row into instRes buffer : " + ex1.getMessage());
                                        System.out.println("Could not insert result into database: " + ex1.toString());
                                    }
                                }
                                
                                // Check to see if we have the header record of the data and
                                // if we do then turn the switch isDataLine on
                                if(values[0].equals("Assay Name"))
                                {
                                    isDataLine = true;                         
                                }
                            }
                            catch (Exception e1)
                            {
                                log.write((new Date().toString()) + " Error trying to read data and create object for insert : " + e1.getMessage());
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
                        log.write((new Date().toString()) + " Error trying to read data and create object for insert : " + ex.getMessage());
                        System.out.println("Could not read/process file: " + ex.toString());
                    }

                }
                else //If there is a file name regular expression
                {
                    File[] files = GetFiles();

                    for (File f : files)
                    {
                        try
                        {
                            // Fist find out what type of result file this is
                            try
                            {
                                String val[];
                                CSVReader rdr = new CSVReader(new FileReader(file), '\t', '\"');
                                while ((val = rdr.readNext()) != null)
                                {
                                    try
                                    {
                                        if (val[0].contains("Copy"))
                                        {
                                            int copyLines = 0;
                                            LifeQuantCopyReader copyReader = new LifeQuantCopyReader(INST, USER, TransDept);
                                            copyLines = copyReader.ProcessFile(f, log);
                                            return copyLines;
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                    catch (IOException ex)
                                    {
                                        log.write((new Date().toString()) + " Error trying to determine file type for " + file.getCanonicalPath() + " : " + ex.getMessage());
                                        System.out.println("Exception parsing line. LifeQuantFileReader::ProcessFile - " + ex.toString());
                                        return -1;
                                    }
                                }
                            }
                            catch (IOException ex)
                            {
                                log.write((new Date().toString()) + " Error getting file type for " + file.getCanonicalPath() + " : " + ex.getMessage());
                                System.out.println("Exception geting file type. LifeQuantFileReader::ProcessFile - " + ex.toString());
                                return -1;
                            }

                            isDataLine = false;
                            CSVReader reader = new CSVReader(new FileReader(f), '\t', '\"');

                            while ((values = reader.readNext()) != null)
                            {
                                res = new BaseInstRes() {};
                                linesRead++;

                                try
                                {
                                    // Check to see if this is a blank line and if it is continue looping
                                    if (values.length < 2)
                                        continue;

                                    // Check to see if we reached the end of the data that we need
                                    // and if we did then break out of the loop
                                    if(values[0].equals("Assay ID"))
                                    {
                                        break;
                                    }
                                
                                    if(isDataLine == true)
                                    {
                                        //Analyte Name
                                        res.setName(values[1].trim());

                                        // Gene Symbol
                                        res.setIdentifier(values[2].trim());

                                        // SNP Reference
                                        res.setInterpretation(values[3].trim());

                                        //Accession
                                        String tmpAccession = values[4].trim();
                                        String delims = "[ ]+";
                                        String[] tokens = tmpAccession.split(delims);
                                        res.setAccession(tokens[0].trim());

                                        //Result
                                        res.setResult(values[5].trim());
                                        res.setComment(values[5].trim());

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
                                                                        
                                    // Check to see if we have the header record of the data and
                                    // if we do then turn the switch isDataLine on
                                    if(values[0].equals("Assay Name"))
                                    {
                                        isDataLine = true;                         
                                    }
                                }
                                catch (Exception e1)
                                {
                                    log.write((new Date().toString()) + " Error parsing line : " + e1.getMessage());
                                    System.out.println("Couldn't parse line: " + e1.toString());
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

        return linesRead - removed;
    }

    /**************************************************************************/
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

    /**************************************************************************/
    public int CleanUpResults()
    {
        try
        {
            int badLines = 0;

            InstrumentDAO instDAO = new InstrumentDAO();
            Instruments inst = instDAO.GetInstrumentByNumber(INST);

            InstResDAO idao = new InstResDAO();
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "ntc%");
            badLines = badLines + idao.RemoveRowsByLikeAccession(inst.getResTable(), "pos%");

            //Get rid of all rows with no valid accession
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

    /**************************************************************************/
}

/******************************************************************************/
/******************************************************************************/
/******************************************************************************/