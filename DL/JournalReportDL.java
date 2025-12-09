/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/25/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import DAOS.*;
import DOS.*;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import EPOS.OrderLine;
import EPOS.ArchiveOrderLine;
import EPOS.JournalReportLine;
import Utility.Convert;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalReportDL {

    DateFormat Dateformatter = new SimpleDateFormat("MM/dd/yyyy");
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        //int inserts = 0;
        //int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        //DetaillinesDAO dDAO = new DetaillinesDAO();
        PaymentsDAO pDAO = new PaymentsDAO();
        WriteOffsDAO wDAO = new WriteOffsDAO();
        //AdjustmentsDAO aDAO = new AdjustmentsDAO();
        
        //Detaillines detailLine;
        //Payments payment;
        //Writeoffs writeoff;
        //Adjustments adjustment;
        JournalReportLine line;
        String[] values;
        int x;
        
        BigDecimal paid;
        BigDecimal woff;
        BigDecimal trans;
        BigDecimal billed;
        
        //Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new JournalReportLine();
            x=0;
            count++;
            
            try
            {
                //Re init detailLine
                //detailLine = new Detaillines();
                //payment = new Payments();
                //writeoff = new Writeoffs();
                
                line.setARNumber(Integer.parseInt(values[x]));
                x++;
                line.setLastName(values[x]);
                x++;
                line.setFirstName(values[x]);
                x++;
                try{
                    line.setDatePaid((Date)Dateformatter.parse(values[x]));
                }catch(Exception e){
                    System.out.println(e.toString());
                    line.setDatePaid(null);
                }
                x++;
                paid = new BigDecimal(values[x]);
                line.setPaid(paid);
                x++;
                woff = new BigDecimal(values[x]);
                line.setWriteOff(woff);
                x++;
                trans = new BigDecimal(values[x]);
                line.setTransfered(trans);
                x++;
                billed = new BigDecimal(values[x]);
                line.setBilled(billed);
                x++;
                line.setInsurance(Integer.parseInt(values[x]));
                x++;
                line.setUser(values[x]);
                x++;
                line.setJournalCode(values[x]);
                x++;
                line.setPercentage(values[x]);
                x++;
                line.setSalesman(Integer.parseInt(values[x]));
                x++;
                line.setClientNumber(Integer.parseInt(values[x]));
                
                try
                {
                    int pID = pDAO.GetIdFromJournalExport(line.getPaid(), line.getDatePaid(), line.getARNumber(), line.getBilled());
                    if( pID > 0)
                    {
                        //Update Payment
                        pDAO.AddUser(line.getUser().toLowerCase(), pID);
                    }
                    
                    int wID = wDAO.GetIdFromJournalExport(line.getPaid(), line.getDatePaid(), line.getARNumber(), line.getBilled());
                    if( wID > 0)
                    {
                        //Update writeoffs
                        wDAO.AddUser(line.getUser(), wID);
                    }
                }catch(Exception e)
                {
                    failures++;
                    System.out.println("Error adding User. MSG: " + e.toString());
                }
            }catch(Exception ex)
            {
                unreadableLines++;
                System.out.println("Unable to parse line. Count = " + count);
            }
        }
    }
    
    private Writeoffs Line2WriteOff(JournalReportLine line)
    {
        Writeoffs wo = new Writeoffs();
        
        wo.setAmount(line.getWriteOff());
        wo.setEntered(line.getDatePaid());
        wo.setUser(line.getUser());
        
        return wo;
    }
    
    private Payments Line2Payment(JournalReportLine line)
    {
        Payments pay = new Payments();
        
        pay.setAmount((line.getPaid()));
        pay.setEntered(line.getDatePaid());
        pay.setUser(line.getUser());
        
        return pay;
    }
}
