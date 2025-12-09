package DL;

/**
 * @date:   Mar 15, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: OrderDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.BillingTestCrossReferenceDAO;
import DAOS.OrderDAO;
import DAOS.PanelDAO;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import DOS.Results;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DOS.BillingTestCrossReference;
import DOS.Panels;
import EPOS.ResultLine;
import Utility.Convert;
import java.util.List;

public class ResultBulkDL 
{
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        ResultDAO rd = new ResultDAO();
        
        Results result;
        ResultLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            try{
                line = new ResultLine();
                x = 0;
                count++;
                System.out.println("Count: " + count);
                
                //TODO For the sake of time, just jump right to the values.
                line.setOrderID(values[13]);
                line.setARNumber(values[2]);
                if(values[5].isEmpty() == false)
                    line.setDateOfService(Convert.ToDate(values[5], "yyyy-MM-dd HH:mm:ss"));
                line.setTestID(Integer.parseInt(values[15]));
                line.setSubtestID(Integer.parseInt(values[16]));
                try
                {
                    line.setResultNumber(Double.parseDouble(values[17]));
                }
                catch(NumberFormatException e)
                {
                    line.setResultText(values[17]);
                }
                
                try
                {
                    line.setIsAbnormal(Integer.parseInt(values[23]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setIsAbnormal(0);
                }
                try
                {
                    line.setIsLow(Integer.parseInt(values[24]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setIsLow(0);
                }
                try
                {
                    line.setIsHigh(Integer.parseInt(values[25]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setIsHigh(0);
                }
                try
                {
                    line.setIsCIDLow(Integer.parseInt(values[26]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setIsCIDLow(0);
                }
                try
                {
                    line.setIsCIDHigh(Integer.parseInt(values[27]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setIsCIDHigh(0);
                }
                line.setResultText(values[28]);
                
                if(values[31].isEmpty() == false)
                    line.setReportedDate(Convert.ToDate(values[31], "yyyy-MM-dd HH:mm:ss"));
                if(values[38].isEmpty() == false)
                    line.setInvalidatedDate(Convert.ToDate(values[38], "yyyy-MM-dd HH:mm:ss"));
                boolean Inval = Boolean.parseBoolean(values[39]);
                line.setIsInvalidated((Inval==true?1:0));
                line.setInvalidatedBy(values[40]);
      
                try{
                    result = EPO2DO(line);
                    rd.InsertResult(result);
                    inserts++;
                }catch(Exception ex1){
                    try{
                        System.out.println(ex1.toString());
                        result = EPO2DO(line);
                        rd.UpdateResult(result);
                        update++;
                    }catch(Exception ex2)
                    {
                        System.out.println(ex2.toString());
                        failures++;
                    }
                }
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Result EPO to the Result DO
    public Results EPO2DO(ResultLine line)
    {
        try{
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        Results result = new Results();
        OrderDAO orderdao = new OrderDAO();
        TestDAO testDAO = new TestDAO();
        PanelDAO panelDAO = new PanelDAO();
        List<Panels> panel;
        
        result.setOrderId(orderdao.GetOrder(line.getOrderID()).getIdOrders());
        int testId;
        if(line.getSubtestID() !=0)
        {
            BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestID(), line.getSubtestID());
            testId = btcr.getIdTests();
        }
        else
        {
            testId = testDAO.GetTestID(line.getTestID(), false);
        }
        result.setTestId(testId);
        panel = panelDAO.GetPanels(line.getPanelID());
        if(panel != null && panel.size() > 0)
            result.setPanelId(panel.get(0).getId());
        result.setResultNo(line.getResultNumber());
        result.setResultText(line.getResultText());
        result.setResultRemark(line.getResultRemark());
        result.setResultChoice(line.getResultChoice());
        if(line.getIsApproved() == 0){
            result.setIsApproved(false);
        }else{
            result.setIsApproved(true);
        }
        
        result.setApprovedBy(0);
        result.setApprovedDate(line.getApprovedDate());
        if(line.getIsInvalidated() == 0){
            result.setIsInvalidated(false);
        }else{
            result.setIsInvalidated(true);
        }

        result.setInvalidatedBy(0);
        result.setInvalidatedDate(line.getInvalidatedDate());
        if(line.getIsUpdated() == 0){
            result.setIsUpdated(false);
        }else{
            result.setIsUpdated(true);
        }

        result.setUpdatedBy(0);
        result.setUpdatedDate(line.getUpdatedDate());
        
        result.setIsAbnormal((line.getIsAbnormal()==1));
        result.setIsLow((line.getIsLow()==1));
        result.setIsHigh((line.getIsHigh()==1));
        result.setIsCIDLow((line.getIsCIDLow()==1));
        result.setIsCIDHigh((line.getIsCIDHigh()==1));
        
        result.setNoCharge(false);
        result.setHl7Transmitted(true);
        result.setPrintAndTransmitted(true);
        
        return result;
        }catch(Exception e){
            System.out.println("Bad line: " + e.toString());
            return null;
        }
        
    }
}
