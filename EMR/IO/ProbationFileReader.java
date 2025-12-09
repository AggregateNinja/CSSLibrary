//------------------------------------------------------------------------------
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//------------------------------------------------------------------------------
package EMR.IO;

import DAOS.ClientXrefDAO;
import DAOS.TestXrefDAO;
import DOS.ClientXref;
import DOS.Orders;
import DOS.Patients;
import DOS.Results;
import DOS.Subscriber;
import DOS.TestXref;
import EMR.DAOS.OrderDAO;
import EMR.DAOS.PatientDAO;
import EMR.DAOS.ResultDAO;
import EMR.DAOS.SubscriberDAO;
import Utility.FileUtil;
import Utility.WriteTextFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.coury.jfilehelpers.engines.FileHelperEngine;

//------------------------------------------------------------------------------
/**
 *
 * @author Michael
 */
//------------------------------------------------------------------------------
public class ProbationFileReader
{
    //--------------------------------------------------------------------------
    private String m_FilesDir;
    private String m_ProcDir;
    
    //--------------------------------------------------------------------------
    public ProbationFileReader(String FilesDir, String ProcDir) throws Exception
    {
        try
        {
            m_FilesDir = FilesDir;
            m_ProcDir = ProcDir;
        }
        catch(Exception ex)
        {
            System.out.println("ProbationFileReader:ProbationFileReader:" + ex.toString());
        }
    }
    
    //--------------------------------------------------------------------------
    public int GetFileCount()
    {
        File folder = new File(m_FilesDir);
        File[] list = folder.listFiles(new FileUtil.DirectoryFilter());
        
        return list.length;
    }
    
    //--------------------------------------------------------------------------
    public int ProcessFile(WriteTextFile log) throws FileNotFoundException, IOException
    {
        int linesRead = 0;
        
        File folder = new File(m_FilesDir);
        File[] fileList = folder.listFiles();
        
        for(File file : fileList)
        {
            if(file.isDirectory())
            {
                continue;
            }
            else
            {
                String tmpFilename = file.getPath();
                
                FileHelperEngine<ProbationRecord> engine = new FileHelperEngine<>(ProbationRecord.class);
                List<ProbationRecord> probOrders = new ArrayList<>();
                
                probOrders = engine.readFile(tmpFilename);
                
                // Iterate through all the Probation Orders in the file
                for(ProbationRecord pRec : probOrders)
                {
                    linesRead++;
                    
                    // Check to Make sure that this is an order record
                    if(pRec.getRecordType().equalsIgnoreCase("O"))
                    {                                                
                        // Get the Client Cross Reference for this Record
                        ClientXrefDAO cliXrefDAO = new ClientXrefDAO();
                        ClientXref cliXref = cliXrefDAO.GetClientXrefByTransInAndXrefId(pRec.getClientCode(), 3);

                        // Add Subscriber Record to EMR Database
                        Subscriber subRecord = new Subscriber();
                        subRecord.setActive(Boolean.TRUE);
                        subRecord.setArNo(pRec.getMasterNumber());
                        
                        // Old Way setting the OrderId to be the last and first name
                        //subRecord.setLastName(pRec.getPatientId());
                        //subRecord.setFirstName(pRec.getPatientId());
                        // New way setting the PIN to be the last and first name
                        subRecord.setLastName(pRec.getMasterNumber());
                        subRecord.setFirstName(pRec.getMasterNumber());
                        
                        subRecord.setInsurance(2);
                        subRecord.setSecondaryInsurance(0);
                        int subscriberId = AddSubscriberToEMR(subRecord);
                                                                       
                        // Add Patient Record to EMR Database
                        Patients patRecord = new Patients();
                        patRecord.setActive(Boolean.TRUE);
                        patRecord.setArNo(pRec.getMasterNumber());
                        patRecord.setPatientMRN(pRec.getPatientId());
                        
                        // Old way setting the order id to be the last and first name
                        //patRecord.setLastName(pRec.getPatientId());
                        //patRecord.setFirstName(pRec.getPatientId());
                        
                        // New way of setting the pin number to be the last and first name
                        patRecord.setLastName(pRec.getMasterNumber());
                        patRecord.setFirstName(pRec.getMasterNumber());
                        
                        patRecord.setSubscriber(subscriberId);
                        patRecord.setRelationship("Self");
                        patRecord.setHeight(0);
                        patRecord.setWeight(0);
                        patRecord.setSmoker(Boolean.FALSE);
                        
                        int patientId = AddPatientToEMR(patRecord);
                                                                        
                        // Add Order Record to EMR Database
                        Orders ordRecord = new Orders();
                        ordRecord.setActive(true);
                        ordRecord.setClientId(cliXref.getIdClients());
                        ordRecord.setLocationId(0);
                        ordRecord.setOrderDate(new Date());
                        ordRecord.setSubscriberId(subscriberId);
                        ordRecord.setPatientId(patientId);
                        ordRecord.setInsurance(2);
                        ordRecord.setSecondaryInsurance(0);
                        ordRecord.setPayment(0.00);
                        ordRecord.setAccession("EMR-" + pRec.getPatientId());
                        ordRecord.setEmrOrderId(pRec.getPatientId());
                        
                        // Format the Specimen Date/Time
                        String tmpDateTime = pRec.getSpecimenDate() + " " + pRec.getSpecimenTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

                        try
                        {
                            Date dteSpecimen = sdf.parse(tmpDateTime);
                            ordRecord.setSpecimenDate(dteSpecimen);
                        }
                        catch (ParseException ex)
                        {
                            System.out.println("ProbationFileReader:ProcessFile - Format Specimen Date " + ex.toString());
                            ordRecord.setSpecimenDate(new Date());
                        }

                        ordRecord.setResultComment(pRec.getResultComment());
                        ordRecord.setInternalComment(pRec.getDrawComment());
                        
                        // Mike D: Added 11/03/2015 11:42 
                        ordRecord.setRoom(pRec.getEnterUser());
                        ordRecord.setReportType(5);
                        
                        int orderId = AddOrderToEMR(ordRecord);
                        

                        // Add Result Line to that Database
                        Results resRecord = new Results();
                        
                        TestXrefDAO tstXrefDAO = new TestXrefDAO();
                        TestXref tstXref = tstXrefDAO.GetTestXrefByTransInAndName(pRec.getTestCode(), cliXref.getDescription());
                        
                        resRecord.setOrderId(orderId);
                        resRecord.setTestId(Integer.parseInt(tstXref.getTransformedOut()));
                        resRecord.setCreated(new Date());
                        
                        int resultId = AddResultsToEMR(resRecord);
                        
                        // Add Result Lines to EMR Database                                               
                        // Debug Output to make sure that the file is being read in correctly
                        //System.out.println("MasterNumber : " + pRec.getMasterNumber());
                        //System.out.println("PatientId : " + pRec.getPatientId());
                        //System.out.println("SpecimenDate :" + pRec.getSpecimenDate());
                        //System.out.println("RecordType : " + pRec.getRecordType());
                        //System.out.println("ClientCode : " + pRec.getClientCode());
                        //System.out.println("EnterUser : " + pRec.getEnterUser());
                        //System.out.println("SpecimenTime : " + pRec.getSpecimenTime());
                        //System.out.println("OrderDate : " + pRec.getOrderDate());
                        //System.out.println("TestCode : " + pRec.getTestCode());
                        //System.out.println("ResultComment : " + pRec.getResultComment());
                        //System.out.println("DrawComment : " + pRec.getDrawComment());                        
                    }                    
                }
                
                // Backup and move the processed file
                engine.close();
                file.setWritable(true);
                file.setReadable(true);
                file.setExecutable(true);
                
                SimpleDateFormat dteFormat = new SimpleDateFormat("yyyyMMdd");
                Date curDate = new Date();
                
                File movedFile = new File(m_ProcDir + "/" + file.getName() + "_" + dteFormat.format(curDate));
                boolean moved = file.renameTo(movedFile);
                //boolean moved = FileUtil.moveFile(movedFile, new File(m_ProcDir));

                if (moved)
                {
                    System.out.println(file.getName() + " has been processed and backed up.");
                    log.write(file.getName() + " has been processed and backed up.");
                }
                else
                {
                    System.out.println(file.getName() + " has been processed but move failed!");
                    log.write(file.getName() + " has been processed but move failed!");
                }                                               
            }                        
        }
        
        return linesRead;
    }

    //--------------------------------------------------------------------------
    private int AddSubscriberToEMR(Subscriber sub)
    {
        SubscriberDAO subDAO = new SubscriberDAO();        
        int subscriberId = 0;
        
        // First we need to check to see if this Subscriber is already on file
        if(subDAO.SubscriberExists(sub.getArNo()))
        {
            // Get the Subscriber Id
            subscriberId = subDAO.GetSubscriberIdByAR(sub.getArNo());                        
        }
        else
        {
            try
            {
                if(subDAO.InsertSubscriber(sub))
                {
                    subscriberId = subDAO.GetLastInsertedID();
                }
                else
                {
                    subscriberId = -1;
                }
            }
            catch (SQLException ex)
            {
                System.out.println("ProbationFileReader:AddSubscriberToEMR : " + ex.toString());
                subscriberId = -1;
            }
        }        
                
        return subscriberId;
    }
        
    //--------------------------------------------------------------------------
    private int AddPatientToEMR(Patients pat)
    {
        PatientDAO patDAO = new PatientDAO();        
        int patientId = 0;
        
        // First we need to check to see if this Patient is already on file
        if(patDAO.PatientExistsByAr(pat.getArNo()))
        {
            // Get the Patient Id
            patientId = patDAO.GetPatientIdByAR(pat.getArNo());
        }
        else
        {
            try
            {
                if(patDAO.InsertPatient(pat))
                {
                   patientId = patDAO.GetLastInsertedID();
                }
                else
                {
                    patientId = -1;
                }
            }
            catch (SQLException ex)
            {
                System.out.println("ProbationFileReader:AddPatientToEMR : " + ex.toString());
                patientId = -1;
            }            
        }
        
        return patientId;
    }
    
    //--------------------------------------------------------------------------
    private int AddOrderToEMR(Orders ord)
    {
        OrderDAO ordDAO = new OrderDAO();        
        int orderId = 0;
        
        try
        {
            if(ordDAO.InsertOrder(ord))
            {
                orderId = ordDAO.GetLastInsertedID();
            }
            else
            {
                orderId = -1;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ProbationFileReader:AddOrderToEMR : " + ex.toString());
            orderId = -1;
        }
        
        return orderId;
    }
    
    //--------------------------------------------------------------------------
    private int AddResultsToEMR(Results res)
    {
        ResultDAO resDAO = new ResultDAO();        
        int resultId = 0;
        
        try
        {
            if(resDAO.InsertResult(res))
            {
                resultId = resDAO.GetLastInsertedID();
            }
            else
            {
                resultId = -1;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ProbationFileReader:AddResultsToEMR : " + ex.toString());
            resultId = -1;
        }
                      
        return resultId;
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------