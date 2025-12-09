package DL;

import Billing.DAOS.AccessionXrefDAO;

import DAOS.DetaillinesDAO;
import DAOS.OrderDAO;
import DAOS.PaymentsDAO;
import DAOS.TestXrefDAO;
import DAOS.WriteOffsDAO;
import DAOS.XrefsDAO;
import DAOS.AdjustmentsCSSSchemaDAO;
import DAOS.TransactionImportLogDAO;

import DOS.Orders;
import DOS.Payments;
import DOS.TestXref;
import DOS.Writeoffs;
import DOS.Xrefs;
import DOS.TransactionImportLog;

import DOS.AdjustmentsCSSSchema;
import EPOS.PaymentLine;
import EPOS.WriteOffLine;
import EPOS.AdjustmentLine;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @date:   Aug 7, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DL
 * @file name: AvanlonTransactionDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class AvanlonTransactionDL 
{
    //--------------------------------------------------------------------------
    public static enum TransType 
    {
        Payment,
        WriteOff,
        Adjustment;
    }
    
    //--------------------------------------------------------------------------
    EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    DateFormat Dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    TransType transType;
    AccessionXrefDAO axrDAO = new AccessionXrefDAO();
    HashMap<String, Integer> testmap = GetTestXRef();
    OrderDAO oDAO = new OrderDAO();
    
    String fileName;
    Integer lineNumber;
    
    //--------------------------------------------------------------------------
    public AvanlonTransactionDL(TransType trans)
    {
        transType = trans;
    }
    
    //--------------------------------------------------------------------------
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
                ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        PaymentsDAO payDAO;        
        WriteOffsDAO woDAO;
        AdjustmentsCSSSchemaDAO adjDAO;
        
        Payments payment;
        Writeoffs writeoff;
        AdjustmentsCSSSchema adjustment;
        
        PaymentLine payLine;
  
        String[] values;
  
        BigDecimal amount;
        
        payDAO = new PaymentsDAO();
        woDAO = new WriteOffsDAO();
        adjDAO = new AdjustmentsCSSSchemaDAO();
        
        SetFilename(filePath);
        
        // Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 0);
        
        while((values = reader.readNext()) != null)
        {
            payLine = new PaymentLine();
            count++;
            
            SetLineNumber(count);
            
            try
            {
                payLine.setARNumber(Integer.parseInt(values[0]));
                payLine.setAccessionNumber(values[1]);
                payLine.setTestNumber(Integer.parseInt(values[2]));
                payLine.setSubTestNumber(Integer.parseInt(values[3]));
                
                amount = new BigDecimal(values[4]);
                payLine.setPaymentAmount(amount);
                
                payLine.setUserName(values[5]);
                
                // Try and Convert the Date
                try
                {
                    payLine.setPaymentDate((Date)Dateformatter.parse(values[6]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    payLine.setPaymentDate(null);
                }  
                
                // TODO save data
                switch(transType)
                {
                    case Payment:
                        try
                        {
                            payment = LineToPayment(payLine);
                            
                            if(payment != null)
                            {
                                payDAO.InsertPayment(payment);
                                inserts++;
                            }
                            else
                            {
                                failures++;
                            }
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
                        break;
                    case WriteOff:
                        try
                        {
                            writeoff = LineToWriteOff(payLine);
                            
                            if(writeoff != null)
                            {
                                woDAO.InsertWriteOff(writeoff);
                                inserts++;
                            }
                            else
                                failures++;
                            
                        }
                        catch(Exception ex1)
                        {
                            System.out.println("Writeoff Insert Failure: " + ex1.getMessage());
                            System.out.println("Filename: " + filePath);
                            System.out.println("ARNumber: " + payLine.getARNumber());
                            System.out.println("Accession: " + payLine.getAccessionNumber());
                            System.out.println("Test: " + payLine.getTestNumber());
                            System.out.println("SubTest: " + payLine.getSubTestNumber());
                            System.out.println("Amount: " + payLine.getPaymentAmount());
                            System.out.println("Date: " + payLine.getPaymentDate());
                            failures++;
                        }
                        break;
                    case Adjustment:
                        try
                        {
                            adjustment = LineToAdjustment(payLine);
                            
                            if(adjustment != null)
                            {
                                adjDAO.InsertAdjustment(adjustment);
                                inserts++;                                
                            }
                            else
                                failures++;
                        }
                        catch(Exception ex1)
                        {
                            System.out.println("Adjustment Insert Failure: " + ex1.getMessage());
                            System.out.println("Filename: " + filePath);
                            System.out.println("ARNumber: " + payLine.getARNumber());
                            System.out.println("Accession: " + payLine.getAccessionNumber());
                            System.out.println("Test: " + payLine.getTestNumber());
                            System.out.println("SubTest: " + payLine.getSubTestNumber());
                            System.out.println("Amount: " + payLine.getPaymentAmount());
                            System.out.println("Date: " + payLine.getPaymentDate());
                            failures++;                            
                        }
                        break;
                    default:
                        throw new AssertionError(transType.name());
                }
            }
	    catch(Exception ex)
            {
                unreadableLines++;
                System.out.println("Unable to parse line. File: " + filePath + " Count = " + count);
            }
        }
    }
    
    //--------------------------------------------------------------------------
    public Writeoffs LineToWriteOff(PaymentLine payLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        Integer detailId = 0;

        String failMessage;
        String failStatus;
        Integer testNo = 0;
        String avalonAcc = null;
        
        try
        {  
            Writeoffs writeoff = new Writeoffs();
         
            // Try and get the detail id
            try
            {                                
                avalonAcc = axrDAO.GetAvalonAccession(payLine.getAccessionNumber());
                Orders order = oDAO.GetOrder(avalonAcc);
                if((order == null && order.getIdOrders() == null) || (avalonAcc == null))
                {
                    failMessage = "FAIL (Get Avalon Order):";
                    failStatus = "Get Avalon Order";
                    System.out.println(failMessage);
                    
                    LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "WriteOff",
                            payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                            String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                            payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                            null, null, null);
   
                    //System.out.println("Order " + payLine.getAccessionNumber() + " Not found in Avalon.");                    
                    return null;
                }
                
                testNo = payLine.getTestNumber();
                String testCode = String.format("%d.%02d", testNo, payLine.getSubTestNumber());
                if(testmap.get(testCode) != null)
                {
                    testNo = testmap.get(testCode);
                }
                
                String patID = String.valueOf(payLine.getARNumber());
                // Get the Detail Id by AR, Accession, Test and SubTest
                detailId = detailDAO.GetDetailId(patID, avalonAcc, testNo);
            }
            catch(Exception e)
            {                
                failMessage = "FAIL (Get Detail Id):" + e.toString();
                failStatus = "Get Detail Id";
                System.out.println(failMessage);

                LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "WriteOff",
                        payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                        String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                        payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                        avalonAcc, null, testNo);
                
                //System.out.println("Error Getting Detail Id. MSG: " + e.toString());
                //System.out.println("ARNumber: " + payLine.getARNumber());
                //System.out.println("Accession: " + payLine.getAccessionNumber());
                //System.out.println("Test: " + payLine.getTestNumber());
                //System.out.println("SubTest: " + payLine.getSubTestNumber());
                //System.out.println("Date: " + payLine.getPaymentDate());
                //System.out.println("Amount: " + payLine.getPaymentAmount());
                
                return null;
            }
            
            writeoff.setDetaillines(detailId);
            writeoff.setAmount(payLine.getPaymentAmount());
            writeoff.setEntered(payLine.getPaymentDate());
            writeoff.setUser(payLine.getUserName());

            return writeoff;            
        }
        catch(Exception e)
        {
            failMessage = "FAIL (Insert Writeoff):" + e.toString();
            failStatus = "Insert Writeoff";
            System.out.println(failMessage);

            LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "WriteOff",
                    payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                    String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                    payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                    avalonAcc, detailId, testNo);
            
            //System.out.println(e.toString());
            return null;
        }
    }  
    
    //--------------------------------------------------------------------------
    public Payments LineToPayment(PaymentLine payLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        int detailId = 0;
        
        Integer testNo = 0;
        String failMessage;
        String failStatus;
        String avalonAcc = null;

        try
        {  
            Payments payment = new Payments();
         
            // Try and get the detail id
            try
            {
                avalonAcc = axrDAO.GetAvalonAccession(payLine.getAccessionNumber());
                Orders order = oDAO.GetOrder(avalonAcc);
                if((order == null && order.getIdOrders() == null) || (avalonAcc == null) )
                {
                    failMessage = "FAIL (Get Avalon Order):";
                    failStatus = "Get Avalon Order";
                    System.out.println(failMessage);
                    
                    LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Payment",
                            payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                            String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                            payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                            null, null, null);
                    
                    //System.out.println("Order " + payLine.getAccessionNumber() + " Not found in Avalon.");
                    return null;
                }
                
                testNo = payLine.getTestNumber();
                String testCode = String.format("%d.%02d", testNo, payLine.getSubTestNumber());
                if(testmap.get(testCode) != null)
                {
                    testNo = testmap.get(testCode);
                }
                
                String patID = String.valueOf(payLine.getARNumber());
                // Get the Detail Id by AR, Accession, Test and SubTest
                detailId = detailDAO.GetDetailId(patID, avalonAcc, testNo);
            }
            catch(Exception e)
            {
                failMessage = "FAIL (Get Detail Id):" + e.toString();
                failStatus = "Get Detail Id";
                System.out.println(failMessage);

                LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Payment",
                        payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                        String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                        payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                        avalonAcc, null, testNo);
                
                //System.out.println("Error Getting Detail Id. MSG: " + e.toString());
                //System.out.println("ARNumber: " + payLine.getARNumber());
                //System.out.println("Accession: " + payLine.getAccessionNumber());
                //System.out.println("Test: " + payLine.getTestNumber());
                //System.out.println("SubTest: " + payLine.getSubTestNumber());
                //System.out.println("Date: " + payLine.getPaymentDate());
                //System.out.println("Amount: " + payLine.getPaymentAmount());
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
            failMessage = "FAIL (Insert Payment):" + e.toString();
            failStatus = "Insert Payment";
            System.out.println(failMessage);

            LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Payment",
                    payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                    String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                    payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                    avalonAcc, detailId, testNo);
            
            //System.out.println(e.toString());
            return null;
        }
    }  
    
    //--------------------------------------------------------------------------
    public AdjustmentsCSSSchema LineToAdjustment(PaymentLine payLine)
    {
        DetaillinesDAO detailDAO = new DetaillinesDAO();
        int detailId = 0;
        
        Integer testNo = 0;
        String failMessage;
        String failStatus;
        String avalonAcc = null;
        
        try
        {
            AdjustmentsCSSSchema adjustment = new AdjustmentsCSSSchema();
            
            // Try and get the detail id
            try
            {
                avalonAcc = axrDAO.GetAvalonAccession(payLine.getAccessionNumber());
                Orders order = oDAO.GetOrder(avalonAcc);
                if((order == null && order.getIdOrders() == null) || (avalonAcc == null))
                {
                    failMessage = "FAIL (Get Avalon Order):";
                    failStatus = "Get Avalon Order";
                    System.out.println(failMessage);
                    
                    LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Adjustment",
                            payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                            String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                            payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                            null, null, null);
                    
                    //System.out.println("Order " + payLine.getAccessionNumber() + " Not found in Avalon");
                    return null;
                }
                
                testNo = payLine.getTestNumber();
                String testCode = String.format("%d.%02d", testNo, payLine.getSubTestNumber());
                if(testmap.get(testCode) != null)
                {
                    testNo = testmap.get(testCode);
                }
                
                String patID = String.valueOf(payLine.getARNumber());
                
                // Get the Detail Id by AR, Accession, Test and SubTest
                detailId = detailDAO.GetDetailId(patID, avalonAcc, testNo);
            } // End Try for getting detail id
            catch(Exception e)
            {
                failMessage = "FAIL (Get Detail Id):" + e.toString();
                failStatus = "Get Detail Id";
                System.out.println(failMessage);

                LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Adjustment",
                        payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                        String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                        payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                        avalonAcc, null, testNo);
                                
                //System.out.println("Error Getting Detail Id. MSG: " + e.toString());
                //System.out.println("ARNumber: " + payLine.getARNumber());
                //System.out.println("Accession: " + payLine.getAccessionNumber());
                //System.out.println("Test: " + payLine.getTestNumber());
                //System.out.println("SubTest: " + payLine.getSubTestNumber());
                //System.out.println("Date: " + payLine.getPaymentDate());
                //System.out.println("Amount: " + payLine.getPaymentAmount());
                //System.out.println(e.toString());
                return null;                
            }
            
            adjustment.setDetaillines(detailId);
            adjustment.setAmount(payLine.getPaymentAmount());
            adjustment.setEntered(payLine.getPaymentDate());
            adjustment.setUser(payLine.getUserName());
            
            return adjustment;
        } // End for main try
        catch(Exception e)
        {
            failMessage = "FAIL (Insert Adjustment):" + e.toString();
            failStatus = "Insert Adjustment";
            System.out.println(failMessage);

            LogError(GetFilename(), GetLineNumber(), "root", failStatus, failMessage, "Adjustment",
                    payLine.getARNumber(), Integer.parseInt(payLine.getAccessionNumber()),
                    String.format("%d.%02d", payLine.getTestNumber(), payLine.getSubTestNumber()),
                    payLine.getPaymentDate(), payLine.getPaymentAmount().doubleValue(),
                    avalonAcc, detailId, testNo);            
            
            //System.out.println(e.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public HashMap<String, Integer> GetTestXRef()
    {
        HashMap<String, Integer> map = new HashMap<>();
        XrefsDAO xrefDAO = new XrefsDAO();
        Xrefs billingXref = xrefDAO.GetXrefByName("Avalon CSS Billing");
        
        TestXrefDAO txrDAO = new TestXrefDAO();
        ArrayList<TestXref> testXref = txrDAO.GetAllTestXrefsByName(billingXref.getIdxrefs());
        
        for(TestXref txr : testXref)
        {
            map.put(txr.getTransformedIn(), txr.getTestNumber());
        }
        
        return map;
    }
    
    //--------------------------------------------------------------------------
    private void LogError(String Filename, Integer LineNumber, String User, String Status,
            String Message, String Type, Integer MasterNumber, Integer AccessionNumber, String TestCode,
            Date TransDate, Double TransAmount, String AvalonAccession, 
            Integer DetailId, Integer TestId)
    {
        TransactionImportLogDAO tilDAO = new TransactionImportLogDAO();
        TransactionImportLog til = new TransactionImportLog();
        
        try
        {
            til.setFilename(Filename);
            til.setLineNumber(LineNumber);
            til.setUser(User);
            til.setStatus(Status);
            til.setMessage(Message);
            til.setType(Type);
            til.setCssMasterNumber(MasterNumber);
            til.setCssAccession(AccessionNumber);
            til.setCssTest(TestCode);
            til.setTransDate(TransDate);
            til.setTransAmount(TransAmount);
            til.setAccession(AvalonAccession);
            til.setIdDetails(DetailId);
            til.setIdTests(TestId);
            
            tilDAO.Insert(til);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //--------------------------------------------------------------------------
    private void SetFilename(String Filename)
    {
        this.fileName = Filename;
    }
    
    //--------------------------------------------------------------------------
    private String GetFilename()
    {
        return this.fileName;
    }
    
    //--------------------------------------------------------------------------
    private void SetLineNumber(Integer LineCount)
    {
        this.lineNumber = LineCount;
    }
    
    //--------------------------------------------------------------------------
    private Integer GetLineNumber()
    {
        return this.lineNumber;
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}
