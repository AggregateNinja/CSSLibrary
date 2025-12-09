package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 09/09/2013  
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
import EPOS.AdjustmentLine;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class AdjustmentDL
{
    DateFormat Dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void ImportFromCSV(String filePath) throws FileNotFoundException, 
                IOException, ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        AdjustmentsCSSSchemaDAO adjDAO = new AdjustmentsCSSSchemaDAO();
        AdjustmentLine adjLine;
        AdjustmentsCSSSchema adjustment;
        
        String[] values;
        
        BigDecimal amount;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 0);
        
        while((values = reader.readNext()) != null)
        {
            adjLine = new AdjustmentLine();
            count++;
            
            try
            {
               adjLine.setARNumber(Integer.parseInt(values[0]));
               adjLine.setAccessionNumber(values[1]);
               adjLine.setTestNumber(Integer.parseInt(values[2]));
               adjLine.setSubTestNumber(Integer.parseInt(values[3]));
               
               amount = new BigDecimal(values[4]);
               adjLine.setAdjustmentAmount(amount);
               
               adjLine.setUserName(values[5]);
               
                try
                {
                    adjLine.setAdjustmentDate((Date)Dateformatter.parse(values[6]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    adjLine.setAdjustmentDate(null);
                }  
                
                adjLine.setAdjustmentType(values[7]);
                adjLine.setAdjustmentReason(values[8]);
                
                try 
                {
                    adjustment = EPO2DO(adjLine);
                    adjDAO.InsertAdjustment(adjustment);
                }
                catch(Exception ex1)
                {
                    System.out.println("Adjustment Insert Failure: " + ex1.getMessage());
                    System.out.println("Filename: " + filePath);
                    System.out.println("ARNumber: " + adjLine.getARNumber());
                    System.out.println("Accession: " + adjLine.getAccessionNumber());
                    System.out.println("Test: " + adjLine.getTestNumber());
                    System.out.println("SubTest: " + adjLine.getSubTestNumber());
                    System.out.println("Amount: " + adjLine.getAdjustmentAmount());
                    System.out.println("Date: " + adjLine.getAdjustmentDate());
                    System.out.println("Type: " + adjLine.getAdjustmentType());
                    System.out.println("Reason: " + adjLine.getAdjustmentReason());
                    
                    failures++;
                }
            }  
            catch(Exception ex)
            {
                unreadableLines++;
                System.out.println("Unable to parse line. File: " + filePath + " Count = " + count);
            }
        }
        
    }
    
    public AdjustmentsCSSSchema EPO2DO(AdjustmentLine adjLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        int detailId = 0;

        try
        {
            AdjustmentsCSSSchema adjustment = new AdjustmentsCSSSchema();
            
            // try and get the detail id
            try
            {
                detailId = detailDAO.GetDetailId(adjLine.getARNumber(), 
                        adjLine.getAccessionNumber(), adjLine.getTestNumber(), 
                        adjLine.getSubTestNumber());
            }
            catch(Exception e)
            {
                System.out.println("Error Getting Detail Id. MSG: " + e.toString());
                System.out.println("ARNumber: " + adjLine.getARNumber());
                System.out.println("Accession: " + adjLine.getAccessionNumber());
                System.out.println("Test: " + adjLine.getTestNumber());
                System.out.println("SubTest: " + adjLine.getSubTestNumber());
                System.out.println("Date: " + adjLine.getAdjustmentDate());
                System.out.println("Amount: " + adjLine.getAdjustmentAmount());
                System.out.println("Type: " + adjLine.getAdjustmentType());
                System.out.println("Reason: " + adjLine.getAdjustmentReason());
            }
            
            adjustment.setDetaillines(detailId);
            adjustment.setAmount(adjLine.getAdjustmentAmount());
            adjustment.setType(adjLine.getAdjustmentType());
            adjustment.setEntered(adjLine.getAdjustmentDate());
            adjustment.setReason(adjLine.getAdjustmentReason());
            adjustment.setUser(adjLine.getUserName());
            
            return adjustment;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }
        
        
        
        
        
        
        
    
    
    
    
    
}
