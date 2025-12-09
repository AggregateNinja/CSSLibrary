/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 09/06/2013  
 * @author Michael Douglass <miked@csslis.com >
 */

import DAOS.*;
import DOS.*;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import EPOS.WriteOffLine;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteOffDL 
{
    DateFormat Dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void ImportFromCSV(String filePath) throws FileNotFoundException, 
                IOException, ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        WriteOffsDAO payDAO = new WriteOffsDAO();        
        WriteOffLine payLine;
        Writeoffs writeoff;
  
        String[] values;
  
        BigDecimal amount;
        
        // Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 0);
        
        while((values = reader.readNext()) != null)
        {
            payLine = new WriteOffLine();
            count++;
            
            try
            {
                payLine.setARNumber(Integer.parseInt(values[0]));
                payLine.setAccessionNumber(values[1]);
                payLine.setTestNumber(Integer.parseInt(values[2]));
                payLine.setSubTestNumber(Integer.parseInt(values[3]));
                
                amount = new BigDecimal(values[4]);
                payLine.setWriteOffAmount(amount);
                
                payLine.setUserName(values[5]);
                
                // Try and Convert the WriteOff Date
                try
                {
                    payLine.setWriteOffDate((Date)Dateformatter.parse(values[6]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    payLine.setWriteOffDate(null);
                }  
                
                try
                {
                    writeoff = EPO2DO(payLine);
                    payDAO.InsertWriteOff(writeoff);
                    inserts++;
                }
                catch(Exception ex1)
                {
                    System.out.println("WriteOff Insert Failure: " + ex1.getMessage());
                    System.out.println("Filename: " + filePath);
                    System.out.println("ARNumber: " + payLine.getARNumber());
                    System.out.println("Accession: " + payLine.getAccessionNumber());
                    System.out.println("Test: " + payLine.getTestNumber());
                    System.out.println("SubTest: " + payLine.getSubTestNumber());
                    System.out.println("Amount: " + payLine.getWriteOffAmount());
                    System.out.println("Date: " + payLine.getWriteOffDate());
                    
                    failures++;
                }
            }
	    catch(Exception ex)
            {
                unreadableLines++;
                System.out.println("Unable to parse line. File: " + filePath + " Count = " + count);
            }
        } 
        
        System.out.println("Total Count: " + count);
        System.out.println("Inserts: " + inserts);
        System.out.println("Failures: " + failures);
        System.out.println("Unreadable Lines: " + unreadableLines);
        
    }
    
    public Writeoffs EPO2DO(WriteOffLine payLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        int detailId;

        try
        {  
            Writeoffs writeoff = new Writeoffs();
         
            // Try and get the detail id
            try
            {
                // Get the Detail Id by AR, Accession, Test and SubTest
                detailId = detailDAO.GetDetailId(payLine.getARNumber(), 
                        payLine.getAccessionNumber(), payLine.getTestNumber(), 
                        payLine.getSubTestNumber());
            }
            catch(Exception e)
            {
                System.out.println("Error Getting Detail Id. MSG: " + e.toString());
                System.out.println("ARNumber: " + payLine.getARNumber());
                System.out.println("Accession: " + payLine.getAccessionNumber());
                System.out.println("Test: " + payLine.getTestNumber());
                System.out.println("SubTest: " + payLine.getSubTestNumber());
                System.out.println("Date: " + payLine.getWriteOffDate());
                System.out.println("Amount: " + payLine.getWriteOffAmount());
                
                return null;
            }
            
            writeoff.setDetaillines(detailId);
            writeoff.setAmount(payLine.getWriteOffAmount());
            writeoff.setEntered(payLine.getWriteOffDate());
            writeoff.setUser(payLine.getUserName());

            return writeoff;            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }  
}
