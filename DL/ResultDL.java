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
import DAOS.RemarkDAO;
import DOS.BillingTestCrossReference;
import DOS.Orders;
import DOS.Panels;
import EPOS.ResultLine;
import Utility.Convert;
import java.util.List;

public class ResultDL 
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
            line = new ResultLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0] + ":" + values[1]);
            try{
                
                
                //line 0
                line.setOrderID(values[x]);
                x++;
                line.setTestID(Integer.parseInt(values[x]));
                x++;
                line.setPanelID(Integer.parseInt(values[x]));
                x++;
                line.setResultNumber(Double.parseDouble(values[x]));
                x++;
                line.setResultText(values[x]);
                x++;
                line.setResultRemark(Integer.parseInt(values[x]));
                x++;
                line.setResultChoice(Integer.parseInt(values[x]));
                x++;
                line.setReportedBy(values[x]);
                x++;
                line.setReportedDate(Convert.ToDate(values[x], "dd/MM/yyyy"));
                x++;
                line.setIsApproved(Integer.parseInt(values[x]));
                x++;
                line.setApprovedBy(values[x]);
                x++;
                line.setApprovedDate(Convert.ToDate(values[x], "dd/MM/yyyy"));
                x++;
                line.setIsInvalidated(Integer.parseInt(values[x]));
                x++;
                line.setInvalidatedBy(values[x]);
                x++;
                line.setInvalidatedDate(Convert.ToDate(values[x], "dd/MM/yyyy"));
                x++;
                line.setIsUpdated(Integer.parseInt(values[x]));
                x++;
                line.setUpdatedBy(values[x]);
                x++;
                line.setUpdatedDate(Convert.ToDate(values[x], "dd/MM/yyyy"));
                x++;
                line.setARNumber(values[x]);
                x++;
                //Addition of Ordered Date.
                if(x < values.length)
                {
                    line.setOrderedDate(Convert.ToDate(values[x], "dd/MM/yyyy_HHmm"));
                }
                x++;
                line.setNoCharge(Integer.parseInt(values[x]));
                
                boolean isUpdate = false;
                try{
                    result = EPO2DO(line);
                    
                    //if(rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()) == 0)
                    if(!rd.ResultExists(result.getOrderId(), result.getTestId()))
                    {
                        rd.InsertResult(result);
                        inserts++;
                    }
                    else
                    {
                        //Get Existing  Result ID and set it to this class
                        isUpdate = true;
                        int r = rd.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId());
                        result.setIdResults(r);
                        
                        rd.UpdateResult(result);
                        update++;
                    }
                    
                }catch(Exception ex1){
                    System.out.println( (isUpdate==false?"Insert":"Update") + " ResultDL: Order|AR|Test:" + line.getOrderID() + "|" + line.getARNumber() + "|" + line.getTestID() + " Count: " + count);
                    System.out.println(ex1.toString());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println("Parse ResultDL: Acc:" + line.getOrderID() + " Count:Col: " + count + ":" + x);
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Results EPO2DO(ResultLine line)
    {
        int x = 0;
        try{
        Results result = new Results();
        OrderDAO orderdao = new OrderDAO();
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        TestDAO testDAO = new TestDAO();
        PanelDAO panelDAO = new PanelDAO();
        List<Panels> panel;
        RemarkDAO rem = new RemarkDAO();
        
        Orders order;
        order = orderdao.GetOrder(line.getOrderID(), line.getOrderedDate());
        if(order == null || order.getIdOrders() == null)
        {
            order = orderdao.GetOrder(line.getOrderID(), line.getARNumber());
            if(order == null || order.getIdOrders() == null)
            {
                System.out.println("Order Not Found.");
                return null;
            }
        }
        
        result.setOrderId(order.getIdOrders());
        //result.setOrderId(orderdao.GetOrder(line.getOrderID()).getIdOrders());
        ++x;
        
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
        
        ++x;
        panel = panelDAO.GetPanels(line.getPanelID());
        if(panel != null && panel.size() > 0)
        {
            result.setPanelId(panel.get(0).getId());
        }
        ++x;
        result.setResultNo(line.getResultNumber());
        ++x;
        result.setResultText(line.getResultText());
        //result.setResultRemark(line.getResultRemark());
        ++x;
        if(line.getResultRemark() == 0)
        {
            result.setResultRemark(null);
        }
        else
        {
            result.setResultRemark(rem.GetRemarkID(line.getResultRemark()));
        }
        ++x;
        result.setResultChoice(line.getResultChoice());
        ++x;
        result.setCreated(order.getOrderDate());
        ++x;
        if(line.getIsApproved() == 0){
            result.setIsApproved(false);
        }else{
            result.setIsApproved(true);
        }
        ++x;
        result.setApprovedBy(0);
        ++x;
        result.setApprovedDate(line.getApprovedDate());
        ++x;
        if(line.getIsInvalidated() == 0){
            result.setIsInvalidated(false);
        }else{
            result.setIsInvalidated(true);
        }
 
        ++x;
        result.setInvalidatedBy(0);
        ++x;
        result.setInvalidatedDate(line.getInvalidatedDate());
        ++x;
        if(line.getIsUpdated() == 0){
            result.setIsUpdated(false);
        }else{
            result.setIsUpdated(true);
        }

        ++x;
        result.setUpdatedBy(0);
        ++x;
        result.setUpdatedDate(line.getUpdatedDate());
        ++x;
        result.setNoCharge((line.getNoCharge()!=0?true:false));
        
        return result;
        }catch(Exception e){
            System.out.println("Results EPO2DO [" + x + "] " + e.toString());
            return null;
        }
        
    }
}
