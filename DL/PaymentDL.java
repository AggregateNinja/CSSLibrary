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
import EPOS.PaymentLine;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PaymentDL 
{
    DateFormat Dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void ImportFromCSV(String filePath) throws FileNotFoundException, 
                IOException, ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        PaymentsDAO payDAO = new PaymentsDAO();        
        PaymentLine payLine;
        Payments payment;
  
        String[] values;
  
        BigDecimal amount;
        
        // Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 0);
        
        while((values = reader.readNext()) != null)
        {
            payLine = new PaymentLine();
            count++;
            
            try
            {
                payLine.setARNumber(Integer.parseInt(values[0]));
                payLine.setAccessionNumber(values[1]);
                payLine.setTestNumber(Integer.parseInt(values[2]));
                payLine.setSubTestNumber(Integer.parseInt(values[3]));
                
                amount = new BigDecimal(values[4]);
                payLine.setPaymentAmount(amount);
                
                payLine.setUserName(values[5]);
                
                // Try and Convert the Payment Date
                try
                {
                    payLine.setPaymentDate((Date)Dateformatter.parse(values[6]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    payLine.setPaymentDate(null);
                }  
                
                try
                {
                    payment = EPO2DO(payLine);
                    payDAO.InsertPayment(payment);
                    inserts++;
                }
                catch(Exception ex1)
                {
                    System.out.println("Payment Insert Failure: " + ex1.getMessage());
                    System.out.println("Filename: " + filePath);
                    System.out.println("ARNumber: " + payLine.getARNumber());
                    System.out.println("Accession: " + payLine.getAccessionNumber());
                    System.out.println("Test: " + payLine.getTestNumber());
                    System.out.println("SubTest: " + payLine.getSubTestNumber());
                    System.out.println("Amount: " + payLine.getPaymentAmount());
                    System.out.println("Date: " + payLine.getPaymentDate());
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
    
    public Payments EPO2DO(PaymentLine payLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        int detailId;

        try
        {  
            Payments payment = new Payments();
         
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
                System.out.println("Date: " + payLine.getPaymentDate());
                System.out.println("Amount: " + payLine.getPaymentAmount());
                return null;
            }
            
            payment.setDetaillines(detailId);
            payment.setAmount(payLine.getPaymentAmount());
            payment.setEntered(payLine.getPaymentDate());
            payment.setUser(payLine.getUserName());

            return payment;            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }  
}
