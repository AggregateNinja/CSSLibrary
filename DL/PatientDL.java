package DL;

import DAOS.PatientDAO;
import DAOS.SpeciesDAO;
import DAOS.SubscriberDAO;
import DOS.Patients;
import EPOS.PatientLine;
import Utility.Convert;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project:  CSSLibrary
 * @package: DL
 * @file name: PatientDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class PatientDL 
{
public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        PatientDAO rd = new PatientDAO();
        
        Patients patient;
        PatientLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new PatientLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
            try{
                
                
                /*//line 0
                line.setAccession(Integer.parseInt(values[x]));
                x++;
                line.setClientID(Integer.parseInt(values[x]));
                x++;
                line.setDoctorID(Integer.parseInt(values[x]));
                x++;
                line.setLocationID(Integer.parseInt(values[x]));
                x++;
                line.setOrderDate(Convert.FromString(values[x], "dd/MM/yyyy_HHmm"));
                x++;
                line.setSpecimenDate(Convert.FromString(values[x], "dd/MM/yyyy_HHmm"));
                x++;
                line.setPatientID(Integer.parseInt(values[x]));
                x++;
                line.setSpeciesID(Integer.parseInt(values[x]));*/
                
                line.setMasterNumber(values[x]);
                ++x;
                line.setLastName(values[x]);
                ++x;
                line.setFirstName(values[x]);
                ++x;
                line.setMiddleInitial(values[x].charAt(0));
                ++x;
                line.setPatientSex(values[x].charAt(0));
                ++x;
                try
                {
                    line.setSocialSecurityNumber(Integer.parseInt(values[x]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setSocialSecurityNumber(0);
                }
                ++x;
                //line.setDateofBirth(Convert.FromString(values[x], "dd/MM/yyyy_HHmm"));
                if("00/00/0000_0000".equals(values[x]) == false)
                {
                    line.setDateofBirth(Convert.ToDate(values[x], "dd/MM/yyyy_HHmm"));
                }
                else
                {
                    line.setDateofBirth(null);
                }
                ++x;
                line.setAddress1(values[x]);
                ++x;
                line.setAddress2(values[x]);
                ++x;
                line.setCity(values[x]);
                ++x;
                line.setState(values[x]);
                ++x;
                try
                {
                    line.setZipCode(Integer.parseInt(values[x]));
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setZipCode(0);
                }
                ++x;
                line.setHomePhone(values[x]);
                ++x;
                line.setSubscriberId(values[x]);
                ++x;
                line.setSubscriberRelationship(values[x]);
                
                ++x;
                if(values.length > x && values[x] != null)
                {
                    try
                    {
                        line.setCounselor(Integer.parseInt(values[x]));
                    }
                    catch (NumberFormatException numberFormatException)
                    {
                        line.setCounselor(0);
                    }
                }
                try{
                    patient = EPO2DO(line);
                    rd.InsertPatient(patient);
                    inserts++;
                }catch(Exception ex1){
                    try{
                        System.out.println("Insert PatientDL: AR:" + line.getMasterNumber() + " Count: " + count);
                        System.out.println(ex1.toString());
                        patient = EPO2DO(line);
                        rd.UpdatePatient(patient);
                        update++;
                    }catch(Exception ex2)
                    {
                        System.out.println("Update PatientDL: AR:" + line.getMasterNumber() + " Count: " + count);
                        System.out.println(ex2.toString());
                        failures++;
                    }
                }
                
            }catch(Exception ex){
                System.out.println("Parse PatientDL: AR:" + line.getMasterNumber() + " Count:Col: " + count + ":" + x);
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Patients EPO2DO(PatientLine line)
    {
        try{
        Patients patient = new Patients();
        SubscriberDAO subDAO = new SubscriberDAO();
        SpeciesDAO speciesDAO = new SpeciesDAO();
        
        
        patient.setArNo(line.getMasterNumber());
        patient.setLastName(line.getLastName());
        patient.setFirstName(line.getFirstName());
        patient.setMiddleName(String.valueOf(line.getMiddleInitial()));
        patient.setSex(String.valueOf(line.getPatientSex()));
        patient.setSsn(String.valueOf(line.getSocialSecurityNumber()));
        patient.setDob(line.getDateofBirth());
        patient.setAddressStreet(line.getAddress1());
        patient.setAddressStreet2(line.getAddress2());
        patient.setAddressCity(line.getCity());
        patient.setAddressState(line.getState());
        patient.setAddressZip(String.valueOf(line.getZipCode()));
        patient.setPhone(line.getHomePhone());
        
        if(line.getSubscriberId().isEmpty() == false && line.getSubscriberId().equals("0"))
        {
            patient.setSubscriber(subDAO.GetSubscriber(line.getSubscriberId()).getIdSubscriber());
        }
        else
        {
            patient.setSubscriber(null);
        }
        
        patient.setRelationship(line.getSubscriberRelationship());
        try
        {
            patient.setSpecies(speciesDAO.GetSpecies(line.getSpecies()).getIdspecies());
        }
        catch(Exception ex)
        {
            patient.setSpecies(65); // Default Human (years)
        }
        
        patient.setHeight(0);
        patient.setWeight(0);
        patient.setEthnicity("Other");
        patient.setSmoker(false);
        patient.setActive(true);
        patient.setDeactivatedDate(null);
        patient.setPatientMRN(null);
        
        return patient;
        }catch(Exception e){
            System.out.println("EPO2DO PatientDL: AR:" + line.getMasterNumber());
            System.out.println(e.toString());
            return null;
        }
        
    }
}
