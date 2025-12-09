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

import DAOS.*;
import DOS.*;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import EPOS.OrderLine;
import Utility.Convert;
import java.util.Date;

public class OrderDL 
{
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        OrderDAO rd = new OrderDAO();
        
        Orders order;
        OrderLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new OrderLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
            try{
                
                
                //line 0
                line.setAccession(values[x]);
                x++;
                line.setClientID(Integer.parseInt(values[x]));
                x++;
                line.setDoctorID(Integer.parseInt(values[x]));
                x++;
                line.setLocationID(Integer.parseInt(values[x]));
                x++;
                if("00/00/0000_0000".equals(values[x]) == false)
                {
                    line.setOrderDate(Convert.ToDate(values[x], "dd/MM/yyyy_HHmm"));
                }
                else
                {
                    line.setOrderDate(new Date());
                }
                x++;
                if("00/00/0000_0000".equals(values[x]) == false)
                {
                    line.setSpecimenDate(Convert.ToDate(values[x], "dd/MM/yyyy_HHmm"));
                }
                else
                {
                    line.setSpecimenDate(new Date());
                }
                x++;
                line.setPatientID(values[x]);
                x++;
                line.setWorksheetComment(values[x]);
                x++;
                line.setReportedComment(values[x]);
                x++;
                try
                {
                    line.setInsurance(Integer.parseInt(values[x]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setInsurance(0);
                }
                x++;
                line.setMedicare(values[x]);
                x++;
                line.setMedicaid1(values[x]);
                x++;
                line.setNoCharge(("1".equals(values[x])));
                x++;
                line.setHistory(("1".equals(values[x])));
                x++;
                line.setBilled(("1".equals(values[x])));
                x++;
                line.setBillable(("1".equals(values[x])));
                x++;
                line.setBillOnly(("1".equals(values[x])));
                //x++;
                //line.setSpeciesID(Integer.parseInt(values[x]));
                
                try{
                    order = EPO2DO(line);
                    //if(!rd.OrderExists(order.getAccession(), order.getPatientId()))
                    if(!rd.OrderExists(order.getAccession(), order.getOrderDate()))
                    {
                        rd.InsertOrder(order);
                        inserts++;
                    }
                    else
                    {
                        int r = rd.GetOrderIdByOrderDate(order.getAccession(),order.getOrderDate());
                        order.setIdOrders(r);

                        rd.UpdateOrder(order);
                        update++;
                    }
                }catch(Exception ex1){
                        
                    System.out.println("Order Insert/Update Failure: Acc: " + line.getAccession() + " " + ex1.getMessage());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println("Parse OrderDL: Acc:" + line.getAccession() + " Count:Col: " + count + ":" + x);
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Order EPO to the Order DO
    public Orders EPO2DO(OrderLine line)
    {
        try{
        Orders order = new Orders();
        ClientDAO clientdao = new ClientDAO();
        DoctorDAO doctordao = new DoctorDAO();
        PatientDAO patientdao = new PatientDAO();
        LocationDAO locationdao = new LocationDAO();
        
        Clients client = clientdao.GetClient(line.getClientID());
        Doctors doctor = doctordao.GetDoctor(line.getDoctorID());
        
        //Determine if we have a patient record to update.
        
        Patients patient = patientdao.GetPatient(line.getPatientID());
        Locations location = locationdao.GetLocation(line.getLocationID());
        
        order.setAccession(line.getAccession());
        order.setClientId(client.getIdClients());
        order.setDoctorId(doctor.getIddoctors());
        order.setLocationId(location.getIdLocation());
        order.setOrderDate(line.getOrderDate());
        order.setSpecimenDate(line.getSpecimenDate());
        order.setPatientId(patient.getIdPatients());
        if(line.getInsurance() != 0)
            order.setInsurance(line.getInsurance());
        else
            order.setInsurance(null);
        order.setInternalComment(line.getWorksheetComment());
        order.setResultComment(line.getReportedComment());
        order.setMedicaidNumber(line.getMedicaid1());
        order.setMedicareNumber(line.getMedicare());
        order.setActive(true);
        order.setBillOnly(line.getBillOnly());
        order.setHold(false);
        order.setStage(1);
        order.setPayment(0.00);
        order.setHl7Transmitted(false);
        order.setBillable(line.getBillable());
        order.setEmrOrderId(null);
        
        return order;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
}
