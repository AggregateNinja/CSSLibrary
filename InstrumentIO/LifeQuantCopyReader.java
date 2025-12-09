/*
 * Computer Service & Support, Inc.  All Rights Reserved Dec 18, 2014
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
import Utility.WriteTextFile;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Dec 18, 2014 12:17:59 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: LifeQuantCopyReader.java (UTF-8)
 *
 * @Description:
 *
 */
/******************************************************************************/
public class LifeQuantCopyReader
{
    /**************************************************************************/
    private int INST;
    private Users USER;
    private Integer TransDept;
    private ArrayList<String> ACCESSIONS;
    private WriteTextFile LOG;

    /**************************************************************************/
    public LifeQuantCopyReader(int inst, Users user, Integer transDept)
    {

        INST = inst;
        USER = user;
        ACCESSIONS = new ArrayList<>();

        TransDept = transDept;

        if (TransDept == null)
        {
            try
            {
                throw new Exception("Unable to find Translational Department");
            }
            catch (Exception ex)
            {
                Logger.getLogger(LifeQuantCopyReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**************************************************************************/
    public int ProcessFile(File file, WriteTextFile log) throws FileNotFoundException, IOException
    {
        InstrumentDAO instDAO = new InstrumentDAO();
        Instruments inst = instDAO.GetInstrumentByNumber(INST);
        InstResDAO idao = new InstResDAO();

        int linesRead = 0;        
        boolean isDataLine = false;
        
        String values[];

        try
        {
            CSVReader reader = new CSVReader(new FileReader(file), '\t', '\"');
     
            while ((values = reader.readNext()) != null)
            {
                BaseInstRes res = new BaseInstRes(){};
                linesRead++;
                
                try
                {
                    // Check to make sure that we have data in the line
                    if (values.length < 2)
                        continue;
                         
                    if(isDataLine == true)
                    {
                        //Sample Name (Accession)
                        String tmpAccession = values[0].trim();
                        String delims = "[ ]+";
                        String[] tokens = tmpAccession.split(delims);
                        res.setAccession(tokens[0].trim());

                        //Target (Analyte Name)
                        res.setName(values[1].trim());

                        // Reference (Interpretaion)
                        res.setInterpretation(values[2].trim());
                  
                        //CN Predicted (Results)
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
                    if(values[0].equals("Sample Name"))
                    {
                        isDataLine = true;                         
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
        catch (FileNotFoundException ex)
        {
          System.out.println("Could not read/process file: " + ex.toString());
        }
      
        //Remove all result rows that may be calibration, standards, or blanks. 
        int removed = CleanUpResults();

        return linesRead - removed;
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

    /**************************************************************************/
    /**************************************************************************/
}

/******************************************************************************/
/******************************************************************************/
/******************************************************************************/