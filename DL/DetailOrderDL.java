package DL;

/**
 * @date:   Mar 15, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: DetailOrderDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.*;
import DOS.*;
import EPOS.DetailOrderLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import Utility.Convert;

public class DetailOrderDL 
{
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        OrderDAO orderdao = new OrderDAO();
        ResultDAO resultdao = new ResultDAO();
        
        Orders order;
        Results results[];
        DetailOrderLine line = null;
        String[] values;
        int x;
        
        String LastARAcession = "";
        String ARAcession;
        boolean NewOrder = true;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        int debug = 0;
        while((values = reader.readNext()) != null)
        {
            try{
                
                ARAcession = values[0] + ":" + values[1];
                System.out.println("Importing " + ARAcession);
                if(ARAcession.equals("48:221674"))
                    ++debug;
                if(!LastARAcession.equals(ARAcession))
                {
                    if(line != null)
                    {
                        // New Order - Save the old one.
                        try
                        {
                            order = EPO2OrderDO(line);
                            orderdao.InsertOrder(order);
                            inserts++;
                        }
                        catch(Exception ex1)
                        {
                            try
                            {
                                System.out.println(ex1.toString());
                                order = EPO2OrderDO(line);
                                orderdao.UpdateOrder(order);
                                update++;
                            }
                            catch(Exception ex2)
                            {
                                System.out.println(ex2.toString());
                                failures++;
                            }
                        }
                        // Now, the Results
                        try
                        {
                            results = EPO2ResultDO(line);
                            for(Results r : results)
                            {
                                try
                                {
                                    resultdao.InsertResult(r);
                                    inserts++;
                                }
                                catch (Exception rex)
                                {
                                    System.out.println(rex.toString());
                                    resultdao.UpdateResult(r);
                                    update++;
                                }
                            }
                            
                        }
                        catch(Exception ex1)
                        {
                            System.out.println(ex1.toString());
                            failures++;
                        }

                    }
                    
                    line = new DetailOrderLine();
                    NewOrder = true;
                    LastARAcession = ARAcession;
                }
                
                x = 0;
                count++;
                
                if(NewOrder == true)
                {
                    line.setArNumber(values[0]);
                    line.setAccession(values[1]);
                    line.setInsurance(Integer.parseInt(values[2]));
                    line.setClientNumber(Integer.parseInt(values[5]));
                    line.setDoctorNumber(Integer.parseInt(values[6]));
                    line.setLocationNumber(Integer.parseInt(values[19]));
                    line.setDateOfService(Convert.ToDate(values[20], "dd/MM/yyyy"));
                    
                    NewOrder = false;
                }
                DetailOrderLine.Detail detail;
                detail = new DetailOrderLine.Detail();

                detail.setTestNum(Integer.parseInt(values[3]));
                detail.setSubTestNum(Integer.parseInt(values[4]));
                detail.setAmountOfBill(Float.parseFloat(values[7]));
                detail.setAmountPaid(Float.parseFloat(values[8]));
                detail.setAmountApproved(Float.parseFloat(values[9]));
                detail.setWriteOffAmount(Float.parseFloat(values[10]));
                detail.setProceedureCode(Integer.parseInt(values[11]));
                if(values[12].trim().isEmpty() == false)
                    detail.setProcModifier(Integer.parseInt(values[12]));
                if(values[13].length() > 0)
                    detail.setProcLetter(values[13].charAt(0));
                if(values[14].length() > 0)
                    detail.setJournalCode(values[14].charAt(0));
                detail.setNumServices(Integer.parseInt(values[15]));
                detail.setPlaceOfService(values[16]);
                detail.setDiagnosisCode(values[17].trim());
                detail.setEmployAccidentOther(values[18]);
                if("00/00/00".equals(values[21]))
                    detail.setDateBilled(null);
                else
                    detail.setDateBilled(Convert.ToDate(values[21], "dd/MM/yyyy"));
                if("00/00/00".equals(values[22]))
                    detail.setDateBilled(null);
                else
                    detail.setDateBilled(Convert.ToDate(values[22], "dd/MM/yyyy"));
                detail.setComment(values[23]);

                line.getDetails().add(detail);
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
        // Grab the last line
        if(line != null)
        {
            // New Order - Save the old one.
            try
            {
                order = EPO2OrderDO(line);
                if(orderdao.GetOrder(order.getAccession()).getIdOrders() == 0)
                {
                    orderdao.InsertOrder(order);
                    inserts++;
                }
                else
                {
                    orderdao.UpdateOrder(order);
                    update++;
                }
            }
            catch(Exception ex1)
            {
                failures++;
            }
            // Now, the Results
            try
            {
                results = EPO2ResultDO(line);
                for(Results r : results)
                {
                    try
                    {
                        if(resultdao.GetResultIdByOrderIdTestId(r.getOrderId(), r.getTestId()) == 0)
                        {
                            resultdao.InsertResult(r);
                            inserts++;
                        }
                        else
                        {
                            resultdao.UpdateResult(r);
                            update++;
                        }
                    }
                    catch (Exception rex)
                    {
                        System.out.println(rex.toString());
                        failures++;
                    }
                }

            }
            catch(Exception ex1)
            {
                System.out.println(ex1.toString());
                failures++;
            }

        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Orders EPO2OrderDO(DetailOrderLine line)
    {
        try{
        Orders order = new Orders();
        ClientDAO clientdao = new ClientDAO();
        DoctorDAO doctordao = new DoctorDAO();
        PatientDAO patientdao = new PatientDAO();
        LocationDAO locationdao = new LocationDAO();
        
        Clients client = clientdao.GetClient(line.getClientNumber());
        Doctors doctor = doctordao.GetDoctor(line.getDoctorNumber());
        Patients patient = patientdao.GetPatient(line.getArNumber());
        Locations location = locationdao.GetLocation(line.getLocationNumber());
        
        order.setAccession(line.getAccession());
        order.setClientId(client.getIdClients());
        order.setDoctorId(doctor.getIddoctors());
        order.setLocationId(location.getIdLocation());
        order.setOrderDate(line.getDateOfService());
        order.setSpecimenDate(line.getDateOfService());
        order.setPatientId(patient.getIdPatients());
        
        return order;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
    
    public Results[] EPO2ResultDO(DetailOrderLine line)
    {
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        int size = line.getDetails().size();
        Results result[] = new Results[size];
        try{
            OrderDAO orderdao = new OrderDAO();
            TestDAO testdao = new TestDAO();
            Tests test;
            for(int i = 0; i < size; ++i)
            {
                DetailOrderLine.Detail detail = line.getDetails().get(i);
                result[i] = new Results();
                if(detail.getSubTestNum() !=0)
                {
                    int testID;
                    BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(detail.getTestNum(), detail.getSubTestNum());
                    testID = btcr.getIdTests();
                    test = testdao.GetTestByID(testID);
                }
                else
                {
                    test = testdao.GetTestByNumber(detail.getTestNum());
                }
                result[i].setOrderId(orderdao.GetOrder(line.getAccession()).getIdOrders());
                result[i].setTestId(test.getIdtests());
                result[i].setPanelId(null);
                result[i].setResultNo(null);
                result[i].setResultText(null);
                result[i].setResultRemark(null);
                result[i].setResultChoice(null);
                result[i].setIsApproved(false);
                result[i].setApprovedBy(null);
                result[i].setApprovedDate(null);
                result[i].setIsInvalidated(false);
                result[i].setInvalidatedBy(null);
                result[i].setInvalidatedDate(null);
                result[i].setIsUpdated(false);
                result[i].setUpdatedBy(null);
                result[i].setUpdatedDate(null);
                
                test = null;
            }
            
            return result;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
