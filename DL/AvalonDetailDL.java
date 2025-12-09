package DL;
/*
import Billing.DAOS.AccessionXrefDAO;
import DAOS.CptDAO;
import DAOS.DetaillinesDAO;
import DAOS.OrderDAO;
import DAOS.PaymentsDAO;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DAOS.TestXrefDAO;
import DAOS.WriteOffsDAO;
import DAOS.XrefsDAO;
import DAOS.DetailImportLogDAO;
import DOS.Cpt;
import DOS.Detaillines;
import DOS.Orders;
import DOS.Payments;
import DOS.Results;
import DOS.TestXref;
import DOS.Writeoffs;
import DOS.Xrefs;
import DOS.DetailImportLog;
import Database.CheckDBConnection;
import EPOS.BulkDetailLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AvalonDetailDL 
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    //--------------------------------------------------------------------------
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
                ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int dtlinserts = 0;
        int cptinserts = 0;
        int payinserts = 0;
        int woinserts = 0;
        int dtlfailures = 0;
        int cptfailures = 0;
        int payfailures = 0;
        int wofailures = 0;
        int unreadableLines = 0;
        
        boolean fail;
        
        SimpleDateFormat Dateformatter = new SimpleDateFormat("MM/dd/yyyy");
        HashMap<String, Integer> testmap = GetTestXRef();
        
        AccessionXrefDAO axrDAO = new AccessionXrefDAO();
        DetaillinesDAO dDAO = new DetaillinesDAO();
        PaymentsDAO pDAO = new PaymentsDAO();
        WriteOffsDAO wDAO = new WriteOffsDAO();
        ResultDAO rDAO = new ResultDAO();
        OrderDAO oDAO = new OrderDAO();
        TestDAO tDAO = new TestDAO();
        CptDAO cDAO = new CptDAO();
        
        Detaillines detailLine;
        Cpt cpt;
        Payments payment;
        Writeoffs writeoff;
        
        BulkDetailLine line;
        String[] values;
        int x = 0;
        BigDecimal amtBill;
        BigDecimal amtPaid;
        BigDecimal amtApproved;        
        BigDecimal amtWriteOff;
        
        //Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            fail = false;
            line = new BulkDetailLine();
            x=0;
            count++;
            String failMessage;
            String failStatus;
            
            try 
            {
                //Re init detailLine
                detailLine = new Detaillines();
                payment = new Payments();
                writeoff = new Writeoffs();
                
                //Start Parsing the line
                line.setArNumber(Integer.parseInt(values[x]));
                x++;
                line.setAccession(values[x]);
                x++;
                line.setInusrance(Integer.parseInt(values[x]));
                x++;
                line.setTestNumber(Integer.parseInt(values[x]));
                x++;
                line.setSubTestNumber(Integer.parseInt(values[x]));
                x++;
                line.setClientNumber(Integer.parseInt(values[x]));
                x++;
                line.setDoctorNumber(Integer.parseInt(values[x]));
                x++;
                amtBill = new BigDecimal(values[x]);
                line.setAmountOfBill(amtBill);
                x++;
                amtPaid = new BigDecimal(values[x]);
                line.setAmountPaid(amtPaid);
                x++;
                amtApproved = new BigDecimal(values[x]);
                line.setAmountApproved(amtApproved);
                x++;
                amtWriteOff = new BigDecimal(values[x]);
                line.setWriteOffAmount(amtWriteOff);
                x++;
                line.setProcCode(values[x]);
                x++;
                line.setProcModifier(values[x]);
                x++;
                line.setProcLetter(values[x]);
                x++;
                line.setJournalCode(values[x]);
                x++;
                line.setNumberOfServices(Integer.parseInt(values[x]));
                x++;
                line.setPlaceOfService(Integer.parseInt(values[x]));
                x++;
                line.setDiagnosisCode(values[x]);
                x++;
                line.setEmployAccidentOther(values[x]);
                x++;
                line.setLocationNumber(Integer.parseInt(values[x]));
                x++;
                
                try
                {
                    line.setDateOfService((Date)Dateformatter.parse(values[x]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    line.setDateOfService(null);
                }
                x++;
                
                try
                {
                    line.setDateBilled((Date)Dateformatter.parse(values[x]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    line.setDateBilled(null);
                }
                x++;
                
                try
                {
                    line.setDatePayment((Date)Dateformatter.parse(values[x]));
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    line.setDatePayment(null);
                }
                x++;
                
                line.setComment(values[x]);
                
                // Lets make a Detail Line.
                try
                {
                    String avalonAcc = axrDAO.GetAvalonAccession(line.Accession);
                    Orders order = oDAO.GetOrder(avalonAcc);
                    if(order != null && order.getIdOrders() != null)
                    {
                        Integer testNo = line.TestNumber;
                        String testCode = String.format("%d.%02d", testNo, line.SubTestNumber);
                        if(testmap.get(testCode) != null)
                        {
                            testNo = testmap.get(testCode);
                        }
                        Integer resId = GetResultIDByOrderIDTestNumber(order.getIdOrders(), testNo);
                        if(resId != null)
                        {
                            //Create a Detaillines object from the line
                            detailLine = Line2Detail(line);
                            //Add the retrieved detial number to the detailline
                            detailLine.setDetail(resId);
                            //Insert the new detail line
                            try 
                            {
                                // Check to see if we alread have the detail line in
                                //DetailExists(int detail)
                                if(!dDAO.DetailExists(resId))
                                {
                                    if(dDAO.InsertBulkDetail(detailLine))
                                    {
                                        ArrayList<Integer> gIDList = dDAO.GetGeneratedIDs();
                                        Integer detId = gIDList.get(0);
                                        ++dtlinserts;
                                        try
                                        {
                                            cpt = Line2CPT(line);
                                            cpt.setDetaillines(detId);
                                            cDAO.InsertCPT(cpt);
                                            ++cptinserts;
                                        }
                                        catch (Exception e5)
                                        {
                                            cptfailures++;
                                            failMessage = "FAIL (CPT Insert):" + e5.toString();
                                            failStatus = "CPT Insert";
                                            System.out.println(failMessage);  
                                        
                                            LogError(filePath, count, "root", failStatus, failMessage, 
                                                    line.getArNumber(), Integer.parseInt(line.getAccession()),
                                                    testCode, line.getDateOfService(), line.getDateBilled(),
                                                    line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                                    line.getAmountPaid().doubleValue(),
                                                    line.getWriteOffAmount().doubleValue(), avalonAcc, detId, 
                                                    resId, testNo);
                                        }
                                    
                                        //If a payment exists insert it
                                        if(line.getAmountPaid().compareTo(BigDecimal.ZERO) > 0)
                                        {
                                            //Create Payments Object from the line
                                            payment = Line2Payment(line);
                                            payment.setDetaillines(detId);
                                            try
                                            {
                                                pDAO.InsertPayment(payment);
                                                ++payinserts;
                                            }
                                            catch (Exception e2)
                                            {
                                                payfailures++;
                                                
                                                failMessage = "FAIL (Payment Insert):" + e2.toString();
                                                failStatus = "Payment Insert";
                                                System.out.println(failMessage);
                                                
                                                LogError(filePath, count,  "root", failStatus, failMessage, 
                                                        line.getArNumber(), Integer.parseInt(line.getAccession()),
                                                        testCode, line.getDateOfService(), line.getDateBilled(),
                                                        line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                                        line.getAmountPaid().doubleValue(),
                                                        line.getWriteOffAmount().doubleValue(), avalonAcc, detId, 
                                                        resId, testNo);
                                                
                                            }
                                        }
                                        
                                        //If a writeoff exists create and insert it
                                        if(line.getWriteOffAmount().compareTo(BigDecimal.ZERO) > 0)
                                        {
                                            //Create Writeoffs Object from line
                                            writeoff = Line2WriteOff(line);
                                            writeoff.setDetaillines(detId);
                                            try
                                            {
                                                wDAO.InsertWriteOff(writeoff);
                                                ++woinserts;
                                            }
                                            catch (Exception e3)
                                            {
                                                wofailures++;
                                                failMessage = "FAIL (Writeoff Insert):" + e3.toString();
                                                failStatus = "Writeoff Insert";
                                                System.out.println(failMessage);
                                                
                                                LogError(filePath,  count, "root", failStatus, failMessage, 
                                                        line.getArNumber(), Integer.parseInt(line.getAccession()),
                                                        testCode, line.getDateOfService(), line.getDateBilled(),
                                                        line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                                        line.getAmountPaid().doubleValue(),
                                                        line.getWriteOffAmount().doubleValue(), avalonAcc, detId, 
                                                        resId, testNo);
                                                
                                            }
                                        }       
                                    }
                                    else
                                    {
                                        dtlfailures++;
                                        failMessage = "FAIL (Detail Insert):";
                                        failStatus = "Detail Insert";
                                        System.out.println(failMessage);
                                        
                                        LogError(filePath,  count, "root", failStatus, failMessage, 
                                                line.getArNumber(), Integer.parseInt(line.getAccession()),
                                                testCode, line.getDateOfService(), line.getDateBilled(),
                                                line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                                line.getAmountPaid().doubleValue(),
                                                line.getWriteOffAmount().doubleValue(), avalonAcc, null, 
                                                resId, testNo);                                    
                                    } 
                                }
                                else // Detail Exists
                                {
                                    dtlfailures++;
                                    failMessage = "FAIL (Duplicate Detail):";
                                    failStatus = "Duplicate Detail";
                                    System.out.println(failMessage);

                                    LogError(filePath,  count, "root", failStatus, failMessage, 
                                            line.getArNumber(), Integer.parseInt(line.getAccession()),
                                            testCode, line.getDateOfService(), line.getDateBilled(),
                                            line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                            line.getAmountPaid().doubleValue(),
                                            line.getWriteOffAmount().doubleValue(), avalonAcc, null, 
                                            resId, testNo);                                                                        
                                }
                            } 
                            catch (Exception e1) 
                            {
                                dtlfailures++;                                
                                failMessage = "FAIL (Detail Insert):" + e1.toString();
                                failStatus = "Detail Insert";
                                System.out.println(failMessage);
                                
                                LogError(filePath,  count, "root", failStatus, failMessage, 
                                        line.getArNumber(), Integer.parseInt(line.getAccession()),
                                        testCode, line.getDateOfService(), line.getDateBilled(),
                                        line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                        line.getAmountPaid().doubleValue(),
                                        line.getWriteOffAmount().doubleValue(), avalonAcc, null, 
                                        resId, testNo);
                            }
                        }
                        else
                        {
                            dtlfailures++;
                            failMessage = "FAIL (Get Result ID):";
                            failStatus = "Get Result ID";
                            System.out.println(failMessage);
                                                        
                            LogError(filePath,  count, "root", failStatus, failMessage, 
                                    line.getArNumber(), Integer.parseInt(line.getAccession()),
                                    testCode, line.getDateOfService(), line.getDateBilled(),
                                    line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                    line.getAmountPaid().doubleValue(),
                                    line.getWriteOffAmount().doubleValue(), avalonAcc, null, 
                                    null, testNo);                            
                        }
                    }
                    else
                    {
                        dtlfailures++;
                        failMessage = "FAIL (Get Avalon Order):";
                        failStatus = "Get Avalon Order";
                        System.out.println(failMessage);
                        
                        LogError(filePath,  count, "root", failStatus, failMessage, 
                                line.getArNumber(), Integer.parseInt(line.getAccession()),
                                String.format("%d.%02d", line.TestNumber, line.SubTestNumber), 
                                line.getDateOfService(), line.getDateBilled(),
                                line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                                line.getAmountPaid().doubleValue(),
                                line.getWriteOffAmount().doubleValue(), null, null, 
                                null, null);  
                    }                    
                }
                catch(Exception ex)
                {
                    dtlfailures++;
                    failMessage = "FAIL (Create Detail):" + ex.toString();
                    failStatus = "Create Detail";
                    System.out.println(failMessage);
                    
                    LogError(filePath,  count, "root", failStatus, failMessage, 
                            line.getArNumber(), Integer.parseInt(line.getAccession()),
                            String.format("%d.%02d", line.TestNumber, line.SubTestNumber), 
                            line.getDateOfService(), line.getDateBilled(),
                            line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                            line.getAmountPaid().doubleValue(),
                            line.getWriteOffAmount().doubleValue(), null, null, 
                            null, null);  
                }             
            }
            catch(Exception ex)
            {
                unreadableLines++;
                failMessage = "FAIL (Parse Line) - Column(" + x + "):" + ex.toString();
                failStatus = "Parse Line";
                System.out.println(failMessage);
                                
                LogError(filePath,  count, "root", failStatus, failMessage, 
                        line.getArNumber(), Integer.parseInt(line.getAccession()),
                        String.format("%d.%02d", line.TestNumber, line.SubTestNumber), 
                        line.getDateOfService(), line.getDateBilled(),
                        line.getAmountOfBill().doubleValue(), line.getDatePayment(), 
                        line.getAmountPaid().doubleValue(),
                        line.getWriteOffAmount().doubleValue(), null, null, 
                        null, null);  
                
            }
            //System.out.println("Line Number: " + count + " A/R #: " + line.getArNumber() + "  Billed: " + line.getAmountOfBill().toString());
            System.out.println("Line Number: " + count);
        }
        
        System.out.println("Import Complete: DTL: " + Ratio(dtlinserts, dtlfailures) + " CPT: " + Ratio(cptinserts, cptfailures) + " Pay: " + Ratio(payinserts, payfailures) + " WO: " + Ratio(woinserts, wofailures));
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
    private Integer GetResultIDByOrderIDTestNumber(int orderID, int testNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }


        try {
            Integer resID = null;
            Statement stmt = con.createStatement();

            String query = "SELECT r.idResults FROM results r"
                    + " LEFT JOIN tests t"
                    + " ON t.idTests = r.testId" 
                    + " WHERE r.`orderId` = " + orderID
                    + " AND t.`number` = " + testNo;
            
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                resID = rs.getInt("idResults");
            }

            rs.close();
            stmt.close();

            return resID;

        } catch (Exception ex2) {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    private Detaillines Line2Detail(BulkDetailLine line)
    {
        Detaillines det = new Detaillines();
        
        det.setPrice(line.getAmountApproved());
        det.setCreated(line.getDateOfService());
        det.setInsurance(line.getInusrance());
        det.setCpt(line.getProcCode());
        det.setCptMultiplier(line.getNumberOfServices());
        det.setModifier(line.getProcModifier());
        
        return det;
    }
    
    //--------------------------------------------------------------------------
    private Payments Line2Payment(BulkDetailLine line)
    {
        Payments pay = new Payments();
        
        pay.setAmount(line.getAmountPaid());
        pay.setEntered(line.getDatePayment());
        pay.setUser("unknown");
        
        return pay;
    }
    
    //--------------------------------------------------------------------------
    private Writeoffs Line2WriteOff(BulkDetailLine line)
    {
        Writeoffs wo = new Writeoffs();
        
        wo.setAmount(line.getWriteOffAmount());
        wo.setEntered(line.getDatePayment());
        wo.setUser("unknown");
        
        return wo;
    }
    
    //--------------------------------------------------------------------------
    private Cpt Line2CPT(BulkDetailLine line)
    {
        Cpt cpt = new Cpt();
        
        cpt.setCpt(line.getProcCode());
        cpt.setModifier(line.getProcModifier());
        cpt.setQuantity(line.getNumberOfServices());
        
        return cpt;
    }
    
    //--------------------------------------------------------------------------
    private String Ratio(int A, int B)
    {
        return String.format("%d:%d", A, B);
    }    
    
    //--------------------------------------------------------------------------
    private void LogError(String Filename, Integer LineNumber, String User, String Status, 
            String Message, Integer MasterNum,
            Integer AccessionNum, String TestCode, Date ServiceDate, Date BillDate,
            Double BillAmount, Date PaymentDate, Double PaymentAmount, Double WriteOffAmount,
            String AvalonAccession, Integer DetailId, Integer ResultId, Integer TestId)
    {
        DetailImportLogDAO dilDAO = new DetailImportLogDAO();        
        DetailImportLog dil = new DetailImportLog();

        try
        {
            dil.setFilename(Filename);
            dil.setLineNumber(LineNumber);
            dil.setUser(User);
            dil.setStatus(Status);
            dil.setMessage(Message);
            dil.setCssMasterNumber(MasterNum);
            dil.setCssAccession(AccessionNum);
            dil.setCssTest(TestCode);
            dil.setServiceDate(ServiceDate);
            dil.setBillDate(BillDate);
            dil.setBillAmount(BillAmount);
            dil.setTransDate(PaymentDate);
            dil.setPaidAmount(PaymentAmount);
            dil.setWriteOffAmount(WriteOffAmount);
            dil.setAccession(AvalonAccession);
            dil.setIdDetails(DetailId);
            dil.setIdResults(ResultId);
            dil.setIdTests(TestId);
       
            dilDAO.Insert(dil);        
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}
*/