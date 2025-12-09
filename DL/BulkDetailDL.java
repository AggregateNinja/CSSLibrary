/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
/*
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import Utility.Convert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import DAOS.DetaillinesDAO;
import DAOS.PaymentsDAO;
import DAOS.WriteOffsDAO;
import DAOS.AdjustmentsDAO;
import DAOS.BillingTestCrossReferenceDAO;
import DAOS.ClientDAO;
import DAOS.CptDAO;
import DAOS.DoctorDAO;
import DAOS.LocationDAO;
import DAOS.OrderDAO;
import DAOS.PatientDAO;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DOS.Detaillines;
import DOS.Payments;
import DOS.Writeoffs;
import DOS.Adjustments;
import DOS.BillingTestCrossReference;
import DOS.Clients;
import DOS.Cpt;
import DOS.Doctors;
import DOS.Locations;
import DOS.Orders;
import DOS.Patients;
import DOS.Results;
import DOS.Tests;
import EPOS.BulkDetailLine;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BulkDetailDL {
    
    DateFormat Dateformatter = new SimpleDateFormat("MM/dd/yyyy");
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        DetaillinesDAO dDAO = new DetaillinesDAO();
        PaymentsDAO pDAO = new PaymentsDAO();
        WriteOffsDAO wDAO = new WriteOffsDAO();
        AdjustmentsDAO aDAO = new AdjustmentsDAO();
        ResultDAO rDAO = new ResultDAO();
        CptDAO cDAO = new CptDAO();
        
        Detaillines detailLine;
        Cpt cpt;
        Payments payment;
        Writeoffs writeoff;
        Adjustments adjustment;
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
            line = new BulkDetailLine();
            x=0;
            count++;
            
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
                try{
                    line.setDateOfService((Date)Dateformatter.parse(values[x]));
                }catch(Exception e){
                    System.out.println(e.toString());
                    line.setDateOfService(null);
                }
                x++;
                try{
                    line.setDateBilled((Date)Dateformatter.parse(values[x]));
                }catch(Exception e){
                    System.out.println(e.toString());
                    line.setDateBilled(null);
                }
                x++;
                try{
                    line.setDatePayment((Date)Dateformatter.parse(values[x]));
                }catch(Exception e){
                    System.out.println(e.toString());
                    line.setDatePayment(null);
                }
                x++;
                line.setComment(values[x]);
                
                try 
                {
                    //First need to find the result that detailline beloongs too.
                    //Grab the detail number, add it to the detail table, add any 
                    //payments, and then writeoffs.  Adjustments can not be evaluated
                    //for a bulk imports.
                    //int detId = dDAO.GetDetailId(line.getArNumber(), line.getAccession(), line.getTestNumber(), line.getSubTestNumber());
                    int resId = rDAO.GetIDForDetailImport(line.getArNumber(), line.getAccession(), line.getTestNumber(), line.getSubTestNumber());
                    //Check if the detail already exists, if not add, else skip
                    if (!dDAO.DetailExists(resId)) 
                    {
                        //If the result is not in the result table, create it.
                        if(resId == 0)
                        {
                            Results result = Line2Result(line);
                            if(result == null)
                            {
                                failures++;
                                System.out.println("Falied to create Result: " + count + " Acc: " + line.Accession + " Ar: " + 
                                        line.ArNumber + " DOS: " + line.DateOfService + " Test: " + line.TestNumber + "." + line.SubTestNumber);
                                continue;
                            }
                            
                            rDAO.InsertResult(result);
                            resId = rDAO.GetIDForDetailImport(line.getArNumber(), line.getAccession(), line.getTestNumber(), line.getSubTestNumber());
                            if(resId == 0)
                            {
                                failures++;
                                System.out.println("Falied to insert Result: " + count + " Acc: " + line.Accession + " Ar: " + 
                                        line.ArNumber + " DOS: " + line.DateOfService + " Test: " + line.TestNumber + "." + line.SubTestNumber);
                                continue;
                            }
                            System.out.println("Result ADDED : " + count + " ResID #: " + resId + 
                                    "  Acc: " + line.Accession + " Ar: " + line.ArNumber + " DOS: " + line.DateOfService + 
                                    " Test: " + line.TestNumber + "." + line.SubTestNumber);

                        }
                        //Create a Detaillines object from the line
                        detailLine = Line2Detail(line);
                        //Add the retrieved detial number to the detailline
                        detailLine.setDetail(resId);
                        //Insert the new detail line
                        try {
                            dDAO.InsertBulkDetail(detailLine);
                        } catch (Exception e1) {
                            failures++;
                        }
                        int detId = dDAO.GetDetailId(line.getArNumber(), line.getAccession(), line.getTestNumber(), line.getSubTestNumber());
                        //Now add the Procedure/CPT Code info into CPT table
                        try{
                            cpt = Line2CPT(line);
                            cpt.setDetaillines(detId);
                            cDAO.InsertCPT(cpt);
                        }catch(Exception e5){
                            failures++;
                            System.out.println("Falied to insert CPT: " + e5.toString());
                        }
                        //If a payment exists insert it
                        if (!line.getAmountPaid().equals(BigDecimal.ZERO) || !line.getAmountPaid().equals(0.00) || !line.getAmountPaid().equals(0)) {
                            //Create Payments Object from the line
                            payment = Line2Payment(line);
                            //Add the detail number
                                payment.setDetaillines(detId);
                        //        //Insert the paymeny
                                try {
                                    pDAO.InsertPayment(payment);
                                } catch (Exception e2) {
                                failures++;
                                System.out.println("Failed to insert payment: " + e2.toString());
                               }
                            }
                            //If a writeoff exists create and insert it
                            if (!line.getWriteOffAmount().equals(BigDecimal.ZERO) || !line.getWriteOffAmount().equals(0.00) || !line.getWriteOffAmount().equals(0)) {
                                //Create Writeoffs Object from line
                                writeoff = Line2WriteOff(line);
                                //Add the detail number
                                writeoff.setDetaillines(detId);
                                //Insert the writeoff
                                try {
                                    wDAO.InsertWriteOff(writeoff);
                                } catch (Exception e3) {
                                    failures++;
                                    System.out.println("Failed to insert writeoff: " + e3.toString());
                                }
                            }
                            //If the amount billed has been changed, we must add it to the adjustments table
                            //if (line.getAmountApproved() != line.getAmountOfBill()) {
                                //TODO: Not Handled Yet
                            //}
                            
                            System.out.println("Line ADDED : " + count + " A/R #: " + line.getArNumber() + "  Billed: " + line.getAmountOfBill().toString());

                        }
                        

                    } 
                catch (Exception ex1) 
                {
                }
            }
            catch(Exception ex)
            {
                System.out.println("Parse BulkDDetail: AR:" + line.getArNumber() + " Count:Col: " + count + ":" + x);
                System.out.println(ex.toString());
                unreadableLines++;
            }
            
            //System.out.println("Line Number: " + count + " A/R #: " + line.getArNumber() + "  Billed: " + line.getAmountOfBill().toString());
            System.out.println("Line Number: " + count);
        }
        
    }
    
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
    
    private Payments Line2Payment(BulkDetailLine line)
    {
        Payments pay = new Payments();
        
        pay.setAmount(line.getAmountPaid());
        pay.setEntered(line.getDatePayment());
        pay.setUser("unknown");
        
        return pay;
    }
    
    private Writeoffs Line2WriteOff(BulkDetailLine line)
    {
        Writeoffs wo = new Writeoffs();
        
        wo.setAmount(line.getWriteOffAmount());
        wo.setEntered(line.getDatePayment());
        wo.setUser("unknown");
        
        return wo;
    }
    
    private Cpt Line2CPT(BulkDetailLine line)
    {
        Cpt cpt = new Cpt();
        
        cpt.setCpt(line.getProcCode());
        cpt.setModifier(line.getProcModifier());
        cpt.setQuantity(line.getNumberOfServices());
        
        return cpt;
    }
    
    private Results Line2Result(BulkDetailLine line)
    {
        Results result = new Results();
        OrderDAO oDAO = new OrderDAO();
        TestDAO tDAO =  new TestDAO();
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();

        Orders ord;
        Tests test;
        try
        {
            ord = oDAO.GetOrder(line.Accession, String.valueOf(line.ArNumber));
        
            if(ord == null || ord.getIdOrders() == null)
            {
                ord = Line2Order(line);
                if(ord == null)
                {
                    return null;
                }
                
                oDAO.InsertOrder(ord);
                int ordID = oDAO.GetOrder(ord.getAccession(), String.valueOf(line.getArNumber())).getIdOrders();
                ord.setIdOrders(ordID);
            }
            
            result.setOrderId(ord.getIdOrders());

            int testID = line.TestNumber;
            if(line.SubTestNumber != 0)
            {
                BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.TestNumber, line.SubTestNumber);
                testID = btcr.getIdTests();
                result.setTestId(testID);
            }
            else
            {
                result.setTestId(tDAO.GetTestID(testID));
            }
            if(result.getTestId() != 0)
            {
                result.setDateReported(line.DateOfService);
                result.setCreated(line.DateOfService);
                result.setResultText("Bill Only");
            }

            return result;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BulkDetailDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private Orders Line2Order(BulkDetailLine line)
    {
        PatientDAO pDAO = new PatientDAO();
        LocationDAO lDAO = new LocationDAO();
        ClientDAO cDAO = new ClientDAO();
        DoctorDAO dDAO = new DoctorDAO();

        Orders ord = new Orders();
        Clients cli;
        Doctors doc;
        Patients pat;
        Locations loc;
        try
        {
            cli = cDAO.GetClient(line.getClientNumber());
            doc = dDAO.GetDoctor(line.getDoctorNumber());
            pat = pDAO.GetPatient(String.valueOf(line.getArNumber()));
            loc = lDAO.GetLocation(line.getLocationNumber());
            
            ord.setAccession(line.getAccession());
            ord.setDoctorId(doc.getIddoctors());
            ord.setClientId(cli.getIdClients());
            ord.setLocationId(loc.getIdLocation());
            ord.setOrderDate(line.getDateOfService());
            ord.setSpecimenDate(line.getDateOfService());
            ord.setPatientId(pat.getIdPatients());
            ord.setIsAdvancedOrder(false);
            ord.setInsurance(null);
            ord.setSecondaryInsurance(null);
            ord.setPolicyNumber(null);
            ord.setGroupNumber(null);
            ord.setSecondaryPolicyNumber(null);
            ord.setSecondaryGroupNumber(null);
            ord.setMedicareNumber(null);
            ord.setMedicaidNumber(null);
            
            return ord;
            
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BulkDetailDL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
*/