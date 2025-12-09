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
import EPOS.ResultLine;
import Utility.Convert;
import DAOS.PatientDAO;
import DAOS.RemarkDAO;
import DOS.BillingTestCrossReference;
import DOS.Orders;

public class ResultExportDL 
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
        /*
        OrderDAO od = new OrderDAO();
        PatientDAO pd = new PatientDAO();
        TestDAO td = new TestDAO();
        RemarkDAO remd = new RemarkDAO();
        */
        
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
                
                //TODO For the sake of time, just jump right to the values.
                line.setOrderID(values[5]);
                
                line.setARNumber(values[6]);
                line.setOrderedDate(Convert.ToDate(values[12], "dd/MM/yyyy HH:mm"));
                line.setTestID(Integer.parseInt(values[13]));
                line.setSubtestID(Integer.parseInt(values[14]));
                line.setResultNumber(Double.parseDouble(values[16]));
                try{
                    line.setResultRemark(Integer.parseInt(values[17]));
                }catch(Exception exw){
                    line.setResultRemark(0);
                }
                // TODO Mixed Answer
                line.setResultChoice(0);
                line.setIsAbnormal(Integer.parseInt(values[23]));
                line.setIsLow(Integer.parseInt(values[24]));
                line.setIsHigh(Integer.parseInt(values[25]));
                line.setIsCIDLow(Integer.parseInt(values[26]));
                line.setIsCIDHigh(Integer.parseInt(values[27]));
                line.setResultText(values[28]);
                line.setReportedDate(Convert.ToDate(values[32], "dd/MM/yyyy HH:mm"));
                line.setTextAnswer(values[35]);
                line.setNoCharge(Integer.parseInt(values[51]));
                
                /*
                //Convert Accession to Order ID -- Fetch the Order ID from DB
                try{
                    line.setOrderID(od.GetOrderIdByOrderDate(line.getOrderID(), line.getDateOfService()));
                }catch(Exception e1)
                {
                    System.out.println(e1.toString());
                }
                //Convert AR number to Patient ID --Fetch the Id from DB
                try{
                    int r = Integer.parseInt(line.getARNumber());
                    int r2 = pd.GetPatientIdByAR(r);
                    String s = "" + r2;
                    line.setARNumber(s);
                }catch(Exception e2){
                    System.out.println(e2.toString());
                }
                //Convert the test number to test id
                try{
                    line.setTestID(td.GetTestID(line.getTestID(), line.getSubtestID()));
                }catch(Exception e3){
                    System.out.println(e3.toString());
                }
                //Convert the remark number
                try{
                    line.setResultRemark(remd.GetRemarkID(line.getResultRemark()));
                }catch(Exception e4){
                    System.out.println(e4.toString());
                }
                */
                
                try{
                    result = EPO2DO(line);
                    if(result == null)
                    {
                        System.out.println("Line " + count + " Failed to convert Line to DO.");
                        continue;
                    }
                    //if(rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()) == 0)
                    if(!rd.ResultExists(result.getOrderId(), result.getTestId()))
                    {
                        if(rd.InsertResult(result) == true)
                        {
                            //System.out.println("Line " + count + " Inserted.");
                            inserts++;
                        }
                        else
                        {
                            System.out.println("Line " + count + " Failed To Insert. File: " + filePath);
                        }
                        
                    }
                    else
                    {
                        result.setIdResults(rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()));
                        if(rd.UpdateResult(result) == true)
                        {
                            update++;
                            //System.out.println("Line " + count + " Updated.");
                        }
                        else
                        {
                            System.out.println("Line " + count + " Failed To Update. File: " + filePath);
                        }
                        
                    }
                }catch(Exception ex1){
                    
                    System.out.println(ex1.toString());
                    failures++;
                    System.out.println("Line " + count + " Failed.");
                }
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                unreadableLines++;
                System.out.println("Line " + count + " Unreadable.");
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Results EPO2DO(ResultLine line)
    {
        try{
        Results result = new Results();
        OrderDAO orderdao = new OrderDAO();
        TestDAO testDAO = new TestDAO();
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        PanelDAO panelDAO = new PanelDAO();
        RemarkDAO remDAO = new RemarkDAO();
        //Orders order = orderdao.GetOrder(line.getOrderID(), line.getARNumber());
        Orders order = orderdao.GetOrder(line.getOrderID(), line.getOrderedDate());
        if(order == null || order.getIdOrders() == null)
        {
            System.out.println("No Order In SQL: Acc:" + line.getOrderID() + " OrdDate:" + line.getOrderedDate().toString() + " AR:" + line.getARNumber());
            return null;
        }
        
        result.setOrderId(order.getIdOrders());
        //result.setOrderId(orderdao.GetOrder(line.getOrderID()).getIdOrders());
        int testID = line.getTestID();
        if(line.getSubtestID() != 0)
        {
            BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestID(), line.getSubtestID());
            testID = btcr.getIdTests();
            result.setTestId(testID);
        }
        else
        {
            result.setTestId(testDAO.GetTestID(testID));
        }
        //result.setTestId(testDAO.GetTestByNumber(line.getTestID(), line.getSubtestID()).getIdtests());
        //result.setPanelId(panelDAO.GetPanels(line.getPanelID()).get(0).getId());
        result.setResultNo(line.getResultNumber());
        result.setResultText(line.getResultText());
        int rmkID = 0;
        if(line.getResultRemark() != 0)
        {
            rmkID = remDAO.GetRemarkID(line.getResultRemark());
        }
        if(rmkID != 0)
        {
            result.setResultRemark(rmkID);
        }
        else
        {
            result.setResultRemark(null);
        }
        //result.setResultRemark(line.getResultRemark());
        if (line.getResultChoice() == 0)
        {
            result.setResultChoice(null);
        }
        else
        {
            result.setResultChoice(line.getResultChoice());
        }
        result.setCreated(order.getOrderDate());
        if(line.getIsApproved() == 0){
            result.setIsApproved(false);
        }else{
            result.setIsApproved(true);
        }
        //result.setIsApproved(line.getIsApproved());
        result.setApprovedBy(0);
        result.setApprovedDate(line.getApprovedDate());
        if(line.getIsInvalidated() == 0){
            result.setIsInvalidated(false);
        }else{
            result.setIsInvalidated(true);
        }
        //result.setIsInvalidated(line.getIsInvalidated());
        result.setInvalidatedBy(0);
        result.setInvalidatedDate(line.getInvalidatedDate());
        if(line.getIsUpdated() == 0){
            result.setIsUpdated(false);
        }else{
            result.setIsUpdated(true);
        }
        //result.setIsUpdated(line.getIsUpdated());
        result.setUpdatedBy(0);
        result.setUpdatedDate(line.getUpdatedDate());
        result.setDateReported(line.getReportedDate());
        //result.setIsAbnormal(line.getIsAbnormal());
        //result.setIsLow(line.getIsLow());
        //result.setIsHigh(line.getIsHigh());
        //result.setIsCIDLow(line.getIsCIDLow());
        //result.setIsCIDHigh(line.getIsCIDHigh());
        if(line.getIsAbnormal() == 0){
            result.setIsAbnormal(false);
        }else{
            result.setIsAbnormal(true);
        }

        if(line.getIsLow() == 0){
            result.setIsLow(false);
        }else{
            result.setIsLow(true);
        }
        
        if(line.getIsHigh() == 0){
            result.setIsHigh(false);
        }else{
            result.setIsHigh(true);
        }
        
        if(line.getIsCIDLow() == 0){
            result.setIsCIDLow(false);
        }else{
            result.setIsCIDLow(true);
        }
        
        if(line.getIsCIDHigh() == 0){
            result.setIsCIDHigh(false);
        }else{
            result.setIsCIDHigh(true);
        }
        result.setTextAnswer(line.getTextAnswer());
        result.setNoCharge((line.getNoCharge()!=0?true:false));
        
        return result;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
}
