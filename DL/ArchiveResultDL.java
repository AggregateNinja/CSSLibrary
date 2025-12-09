/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/15/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import DAOS.BillingTestCrossReferenceDAO;
import DAOS.OrderDAO;
import DAOS.PanelDAO;
import DAOS.ReportedDateDAO;
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
import DOS.Orders;
import DOS.Panels;
import DOS.Reporteddate;
import EPOS.ArchiveResultLine;
import Utility.Convert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArchiveResultDL {
    
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
        ArchiveResultLine line;
        String[] values;
        int x;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), '|');
        
        while((values = reader.readNext()) != null)
        {
            line = new ArchiveResultLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
            
            try
            {
                //Segment 1
                line.setExportkey(values[x]);
                x++;
                //Segment 2
                line.setTestNumber(Integer.parseInt(values[x]));
                x++;
                //Segment 3
                line.setSubTestNumber(Integer.parseInt(values[x]));
                x++;
                //Segment 4
                line.setTestName(values[x]);
                x++;
                //Segment 5
                line.setResult(values[x]);
                x++;
                //Segment 6
                line.setRange(values[x]);
                x++;
                //Segment 7
                line.setUnits(values[x]);
                x++;
                //Segment 8
                line.setSortIndex(Integer.parseInt(values[x]));
                x++;
                //Segment 9
                line.setAbnormal(Integer.parseInt(values[x]));
                x++;
                //Segment 10
                line.setLow(Integer.parseInt(values[x]));
                x++;
                //Segment 11
                line.setHigh(Integer.parseInt(values[x]));
                x++;
                //Segment 12
                line.setCidLow(Integer.parseInt(values[x]));
                x++;
                //Segment 13
                line.setCidHigh(Integer.parseInt(values[x]));
                x++;
                //Segment 14
                line.setResultText(values[x]);
                x++;
                //Segment 15
                line.setTcdRemark(values[x]);
                x++;
                //Segment 16
                line.setMixedAnswer(values[x]);
                x++;
                //Segment 17
                line.setTextAnswer(values[x]);
                x++;
                //Segment 18
                line.setRemarkAbnormal(Integer.parseInt(values[x]));
                x++;
                //Segment 19
                line.setCptCode(values[x]);
                x++;
                //Segment 20
                //line.setResultAnswer(values[x]); //False. This field is the html remark.
                if(line.getResultText() == null || line.getResultText().trim().isEmpty() == true)
                {
                    line.setResultText(values[x]);
                }
                x++;
                //Segment 21
                //Print Order Flag - WE NEED THIS EVENTUALLY.
                
                result = EPO2DO(line);
                try {
                    if (!rd.ResultExists(result.getOrderId(), result.getTestId())) {
                        rd.InsertResult(result);
                        inserts++;
                    } else {
                        result.setIdResults(rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()));
                        rd.UpdateResult(result);
                        update++;
                    }
                } catch (Exception ex1) {
                    System.out.println("Line " + count + ": " + ex1.toString());
                    failures++;
                }
            } catch (Exception ex) {
                System.out.println("Line " + count + " Row " + x + ": " + ex.toString());
                unreadableLines++;
            }
        }
    }
    
    public void ImportUpdateForBillOnly(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        ResultDAO rd = new ResultDAO();
        
        Results result;
        ArchiveResultLine line;
        String[] values;
        int x;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), '|');
        
        while((values = reader.readNext()) != null)
        {
            line = new ArchiveResultLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
            
            try
            {
                //Segment 1
                line.setExportkey(values[x]);
                x++;
                //Segment 2
                line.setTestNumber(Integer.parseInt(values[x]));
                x++;
                //Segment 3
                line.setSubTestNumber(Integer.parseInt(values[x]));
                x++;
                //Segment 4
                line.setTestName(values[x]);
                x++;
                //Segment 5
                line.setResult(values[x]);
                x++;
                //Segment 6
                line.setRange(values[x]);
                x++;
                //Segment 7
                line.setUnits(values[x]);
                x++;
                //Segment 8
                line.setSortIndex(Integer.parseInt(values[x]));
                x++;
                //Segment 9
                line.setAbnormal(Integer.parseInt(values[x]));
                x++;
                //Segment 10
                line.setLow(Integer.parseInt(values[x]));
                x++;
                //Segment 11
                line.setHigh(Integer.parseInt(values[x]));
                x++;
                //Segment 12
                line.setCidLow(Integer.parseInt(values[x]));
                x++;
                //Segment 13
                line.setCidHigh(Integer.parseInt(values[x]));
                x++;
                //Segment 14
                line.setResultText(values[x]);
                x++;
                //Segment 15
                line.setTcdRemark(values[x]);
                x++;
                //Segment 16
                line.setMixedAnswer(values[x]);
                x++;
                //Segment 17
                line.setTextAnswer(values[x]);
                x++;
                //Segment 18
                line.setRemarkAbnormal(Integer.parseInt(values[x]));
                x++;
                //Segment 19
                line.setCptCode(values[x]);
                x++;
                //Segment 20
                //line.setResultAnswer(values[x]); //False. This field is the html remark.
                if(line.getResultText() == null || line.getResultText().trim().isEmpty() == true)
                {
                    line.setResultText(values[x]);
                }
                x++;
                //Segment 21
                //Print Order Flag - WE NEED THIS EVENTUALLY.
                
                result = EPO2DO(line);
                if(result != null)
                {
                    try {
                        if (!rd.ResultExists(result.getOrderId(), result.getTestId())) 
                        {
                            rd.InsertResult(result);
                            inserts++;
                        } 
                        else 
                        {
                            if(rd.ResultExists(result.getOrderId(), result.getTestId(), "Bill Only"))
                            {
                                result.setIdResults(rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()));
                                rd.UpdateResult(result);
                                update++;
                                System.out.println(update + " Line " + count + " updated result " + result.getIdResults());
                            }
                        }
                    } catch (Exception ex1) {
                        System.out.println(ex1.toString());
                        failures++;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
    }
    
    //Converts the ArchiveResult EPO to the Result DO
    public Results EPO2DO(ArchiveResultLine line)
    {
        try
        {
            int ordID;
            BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
            ReportedDateDAO rdDAO = new ReportedDateDAO();
            Results result = new Results();
            OrderDAO orderdao = new OrderDAO();
            TestDAO testDAO = new TestDAO();
            Orders order = orderdao.GetOrder(GetResultAccession(line.getExportkey()), GetKeyDate(line.getExportkey()));
            //PanelDAO panelDAO = new PanelDAO();
            //List<Panels> panel;

            //result.setOrderId(orderdao.GetOrder(GetResultAccession(line.getExportkey())).getIdOrders());
            ordID = order.getIdOrders();
            try
            {
                result.setOrderId(order.getIdOrders());
            }
            catch (Exception e)
            {
                System.out.println("No Order: " + GetResultAccession(line.getExportkey()) + ", " + GetKeyDate(line.getExportkey()));
                return null;
            }
            try
            {
                if (line.getSubTestNumber() != 0)
                {
                    int testID;
                    BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestNumber(), line.getSubTestNumber());
                    testID = btcr.getIdTests();
                    result.setTestId(testID);
                }
                else
                {
                    result.setTestId(testDAO.GetTestByNumber(line.getTestNumber()).getIdtests());
                }
            }
            catch (Exception e)
            {
                System.out.println("No Test: " + line.getTestNumber() + "." + line.getSubTestNumber());
                return null;
            }
            Reporteddate reportedDate = rdDAO.GetReportedDate(ordID);
            if(reportedDate != null && reportedDate.getIdreporteddate() != null)
            {
                result.setDateReported(reportedDate.getDateReported());
            }
            
            result.setPanelId(null);
            //panel = panelDAO.GetPanels(line.getPanelID());
            //if(panel != null && panel.size() > 0)
            //    result.setPanelId(panel.get(0).getId());
            try
            {
                Double val = Double.parseDouble(line.getResult());
                result.setResultNo(val);
            }
            catch (NumberFormatException e)
            {

            }
            result.setResultText(line.getResultText());
            result.setTextAnswer(line.getTextAnswer());
            //result.setResultRemark(line.getResultRemark());
            result.setResultRemark(null);
            //result.setResultChoice(line.getResultChoice());
            result.setResultChoice(null);
            result.setCreated(order.getOrderDate());
            result.setIsApproved(true);
            result.setApprovedBy(null);
            result.setApprovedDate(new Date());
            //result.setIsInvalidated(line.getIsInvalidated());
            result.setInvalidatedBy(null);
            //result.setInvalidatedDate(line.getInvalidatedDate());
            //result.setIsUpdated(line.getIsUpdated());
            result.setUpdatedBy(null);
            //result.setUpdatedDate(line.getUpdatedDate());
            if (line.getAbnormal() == 0)
            {
                result.setIsAbnormal(false);
            }
            else
            {
                result.setIsAbnormal(true);
            }

            if (line.getLow() == 0)
            {
                result.setIsLow(false);
            }
            else
            {
                result.setIsLow(true);
            }

            if (line.getHigh() == 0)
            {
                result.setIsHigh(false);
            }
            else
            {
                result.setIsHigh(true);
            }

            if (line.getCidLow() == 0)
            {
                result.setIsCIDLow(false);
            }
            else
            {
                result.setIsCIDLow(true);
            }

            if (line.getCidHigh() == 0)
            {
                result.setIsCIDHigh(false);
            }
            else
            {
                result.setIsCIDHigh(true);
            }
            result.setNoCharge(false);
            result.setHl7Transmitted(false);
            result.setPrintAndTransmitted(true);

            return result;
        }
        catch (Exception e)
        {
            System.out.println("Bad line: " + e.toString());
            return null;
        }

    }
    
    //Parses Accession from ExportKey
    public String GetResultAccession(String eKey)
    {
        String acc = eKey.substring(4, 13);
        return acc;
    }
    
    
    public java.util.Date GetKeyDate(String eKey) throws ParseException
    {
        String dStr = eKey.substring(18, 26);
        DateFormat formatter = new SimpleDateFormat("MMddyyyy");
        java.util.Date date = formatter.parse(dStr);
        return date;
    }
    
    
}
