/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/14/2013  
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
import Utility.Convert;

public class ArchiveOrderDL {
    
    public void ImportFromArchive(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        OrderDAO rd = new OrderDAO();
        ReportedDateDAO rdd = new ReportedDateDAO();
        
        Orders order;
        Orders tempOrd;
        Reporteddate repD;
        ArchiveOrderLine line;
        String[] values;
        int x;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), '|');
        
        while((values = reader.readNext()) != null)
        {
            line = new ArchiveOrderLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
            
            //if (values.length == 43) 
              if(values.length > 0 && values.length < 50)
              {
                try {
                    //Segment 1
                    line.setExportKey(values[x]);
                    x++;
                    //Segment 2
                    line.setAccession(values[x]);
                    x++;
                    //Segment 3
                    line.setClientNumber(Integer.parseInt(values[x]));
                    x++;
                    //Segment 4
                    line.setPatientAge(Integer.parseInt(values[x]));
                    x++;
                    //Segment 5
                    line.setPatientPhone(values[x]);
                    x++;
                    //Segment 6
                    line.setInsuranceNumber(Integer.parseInt(values[x]));
                    x++;
                    //Segment 7
                    line.setSubscriberARNumber(Integer.parseInt(values[x]));
                    x++;
                    //Segment 8
                    line.setPatientZip(Integer.parseInt(values[x]));
                    x++;
                    //Segment 9
                    line.setDoctorNumber(Integer.parseInt(values[x]));
                    x++;
                    //Segment 10
                    line.setRelationshipCode(Integer.parseInt(values[x]));
                    x++;
                    //Segment 11
                    line.setFaxNumber(values[x]);
                    x++;
                    //Segment 12
                    line.setPatientARNumber(Integer.parseInt(values[x]));
                    x++;
                    //Segement 13
                    line.setPatientSex(values[x].charAt(0));
                    x++;
                    //Segment 14
                    line.setBillType(values[x]);
                    x++;
                    //Segment 15
                    line.setReqFlag(values[x]);
                    x++;
                    //Segment 16
                    line.setSpecies(values[x]);
                    x++;
                    //Segment 17
                    line.setLocation(Integer.parseInt(values[x]));
                    x++;
                    //Segment 18
                    if (values[x].length() > 0) {
                        line.setPatientMiddleInitial(values[x].charAt(0));
                    }
                    x++;
                    //Segment 19
                    line.setPatientLastName(values[x]);
                    x++;
                    //Segment 20
                    line.setPatientFirstName(values[x]);
                    x++;
                    //Segment 21
                    line.setPatientId(values[x]);
                    x++;
                    //Segment 22
                    line.setPatientAddress(values[x]);
                    x++;
                    //Segment 23
                    line.setPatientCity(values[x]);
                    x++;
                    //Segment 24
                    line.setPatientState(values[x]);
                    x++;
                    //Segment 25
                    line.setMedicaidId(values[x]);
                    x++;
                    //Segment 26
                    line.setMedicareNumber(values[x]);
                    x++;
                    //Segment 27
                    line.setPhlebot(values[x]);
                    x++;
                    //Segment 28
                    line.setResultComment(values[x]);
                    x++;
                    //Segment 29
                    line.setWorksheetComment(values[x]);
                    x++;
                    //Segment 30
                    line.setEAO(values[x]);
                    x++;
                    //Segment 31
                    line.setAuxCode(values[x]);
                    x++;
                    //Segment 32
                    line.setOrderDate(Convert.ToDate(values[x], "MM/dd/yyyy :HHmm"));
                    x++;
                    //Segment 33
                    line.setSpecimenDate(Convert.ToDate(values[x], "MM/dd/yyyy :HHmm"));
                    x++;
                    //Segment 34
                    line.setDOB(Convert.ToDate(values[x], "MM/dd/yyyy"));
                    x++;
                    //Segment 35
                    line.setExtraOrderDate(Convert.ToDate(values[x], "MM/dd/yyyy"));
                    x++;
                    //Segment 36
                    line.setExtraSpecimenDate(Convert.ToDate(values[x], "MM/dd/yyyy"));
                    x++;
                    //Segment 37
                    line.setExtraDOB(Convert.ToDate(values[x], "MM/dd/yyyy"));
                    x++;
                    //Segment 38
                    line.setExtraReportedDate(Convert.ToDate(values[x], "MM/dd/yyyy"));
                    x++;
                    //Segment 39
                    line.setOrderCommentPointer(values[x]);
                    x++;
                    //Segment40
                    line.setReportType(values[x]);
                    x++;
                    //Segment 41
                    if(values[x] != null)
                    {
                        line.setCallComment1(HandleQuotes(values[x]));
                    }
                    x++;
                    //Segment 42
                    if(values[x] != null)
                    {
                        line.setCallComment2(HandleQuotes(values[x]));
                    }

                    try {
                        order = EPO2DO(line);
                        if (!rd.OrderExists(order.getAccession(), order.getPatientId())) {
                            rd.InsertOrder(order);
                            inserts++;
                        } else {
                            if (rd.AccessionCount(order.getAccession()) > 1) {
                                //int r = rd.GetOrderIdByOrderDate(order.getAccession(), order.getOrderDate());
                                order.setIdOrders(rd.GetOrderIdByOrderDate(order.getAccession(), order.getOrderDate()));
                                rd.UpdateOrder(order);
                                update++;
                            } 
                        }
                    } catch (Exception ex1) {

                        System.out.println("Order Fail Line " + count + " Column " + x + ": " + ex1.toString());
                        failures++;
                    }
                    
                    try
                    {
                        tempOrd = rd.GetOrder(line.getAccession(), "" + line.getPatientARNumber());
                        repD = CreateReportedDate(tempOrd.getIdOrders(), line.getExtraReportedDate());
                        Reporteddate temp = rdd.GetReportedDate(tempOrd.getIdOrders());
                        if(temp == null || temp.getIdreporteddate() == null)
                        {
                            rdd.InsertRow(repD);
                        }
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Reported Date Fail Line " + count + " Column " + x + ": " + ex.toString());
                        failures++;
                    }
                } catch (Exception ex) {
                    System.out.println("Parse ArchiveOrderDL: Acc:" + line.getAccession() + " Count:Col: " + count + ":" + x);
                    System.out.println(ex.toString());
                    unreadableLines++;
                }
            }
            else
            {
                System.out.println("Parse ArchiveOrderDL: Malformed line length: " + values.length);
                unreadableLines++;
            }
        }
    }
    
    public String HandleQuotes(String str)
    {
        if(str == null)
        {
            return null;
        }
        String x = str.replace('"', ' ');
        return x;
    }
    
    public Reporteddate CreateReportedDate(int id, java.util.Date date)
    {
        Reporteddate rep = new Reporteddate();
        rep.setIdreporteddate(id);
        rep.setDateReported(date);
        
        return rep;
    }
    
    public Orders EPO2DO(ArchiveOrderLine line) throws SQLException
    {
        try{
            
        Orders order = new Orders();
        ClientDAO clientdao = new ClientDAO();
        DoctorDAO doctordao = new DoctorDAO();
        PatientDAO patientdao = new PatientDAO();
        LocationDAO locationdao = new LocationDAO();
        
        Clients client = clientdao.GetClient(line.getClientNumber());
        Doctors doctor = doctordao.GetDoctor(line.getDoctorNumber());
        String patAR = "" + line.getPatientARNumber();
        Patients patient = patientdao.GetPatient(patAR.trim());
        Locations location;
        if(line.getLocation() >= 30)
        {
            char[] intToChar = Character.toChars(line.getLocation());
            String str = String.valueOf(intToChar);
            int loc = Integer.parseInt(str);
            location = locationdao.GetLocation(loc);
        }
        else
        {
            location = locationdao.GetLocation(line.getLocation());
        }
        
        order.setAccession(line.getAccession());
        order.setClientId(client.getIdClients());
        if(doctor != null)
        {
            order.setDoctorId(doctor.getIddoctors());
        }
        else
        {
            order.setDoctorId(null);
        }
        order.setLocationId(location.getIdLocation());
        order.setOrderDate(line.getOrderDate());
        order.setSpecimenDate(line.getSpecimenDate());
        order.setPatientId(patient.getIdPatients());
        
        return order;
        }catch(Exception e){
            System.out.println("EPO2DO: " + e.toString());
            return null;
        }
    }
}
