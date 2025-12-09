package DL;

import DAOS.DoctorDAO;
import DOS.Doctors;
import EPOS.DoctorLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @date:   Mar 13, 2012
 * @author: Keith Maggio
 * 
 * @project:  
 * @package: DL
 * @file name: DoctorDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class DoctorDL 
{
public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        DoctorDAO rd = new DoctorDAO();
        
        Doctors doctor;
        DoctorLine line;
        String[] values;
        String LongName;
        int namebreak;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new DoctorLine();
            try{
                
                x = 0;
                count++;
                
                line.setNumber(Integer.parseInt(values[x]));
                ++x;
                LongName = values[x];
                namebreak = LongName.indexOf(',');
                if(namebreak != -1)
                {
                    line.setFirstName(LongName.substring(0, namebreak));
                    line.setLastName(LongName.substring(namebreak));
                }
                else
                {
                    line.setFirstName(LongName);
                    line.setLastName("");
                }
                ++x;
                line.setAddress1(values[x]);
                ++x;
                line.setCity(values[x]);
                ++x;
                line.setState(values[x]);
                ++x;
                line.setZip(values[x]);
                ++x;
                line.setCrossRef(values[x]);
                ++x;
                // License
                ++x;
                line.setUPIN(values[x]);
                ++x;
                // Other Doctor Number
                ++x;
                // Sort Sequence
                ++x;
                line.setExternalId(values[x]);
                ++x;
                line.setEmail(values[x]);
                ++x;
                line.setPhone(values[x]);
                ++x;
                // Doctor Code
                ++x;
                line.setAddress2(values[x]);
                ++x;
                line.setFax(values[x]);
                ++x;
                if(values[x].isEmpty() == false)
                {
                    try
                    {
                        
                        //BigInteger npi = new BigInteger(values[x]);
                        //line.setNPI(BigInteger.parseInt(values[x]));
                        Long.parseLong(values[x]);
                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("Doctor " + line.getNumber() + ": " + ex.toString());
                        line.setNPI(0L);
                    }
                }
                else
                {
                    line.setNPI(0L);
                }
                ++x;
                // Doctor Status
                ++x;
                // Institution
                
                
                try{
                    doctor = EPO2DO(line);
                    if(!rd.DoctorsExists(doctor.getNumber()))
                    {
                        rd.InsertDoctor(doctor);
                        inserts++;
                    }
                    else
                    {
                        doctor.setIddoctors(rd.GetDoctorID(doctor.getNumber()));
                        rd.UpdateDoctor(doctor);
                        update++;
                    }
                    
                }catch(Exception ex1){
                    System.out.println("Doctor " + line.getNumber() + ": " + ex1.toString());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println("Doctor " + line.getNumber() + ": " + ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Doctors EPO2DO(DoctorLine line)
    {
        try{
        Doctors doctor = new Doctors();
        
        doctor.setNumber(line.getNumber());
        doctor.setLastName(line.getLastName());
        doctor.setFirstName(line.getFirstName());
        doctor.setAddress1(line.getAddress1());
        doctor.setAddress2(line.getAddress2());
        doctor.setCity(line.getCity());
        doctor.setState(line.getState());
        doctor.setZip(line.getZip());
        doctor.setUpin(line.getUPIN());
        doctor.setNpi(line.getNPI());
        doctor.setExternalId(line.getExternalId());
        doctor.setCrossref(line.getCrossRef()); // huh?
        
        final String phoneFormat = "(%s)-%s-%s";
        if(!line.getPhone().isEmpty())
        {
            String temp = line.getPhone().substring(1);
            String area = temp.substring(0, 3);
            String p1 = temp.substring(3, 3);
            String p2 = temp.substring(6);
            String phone = String.format(phoneFormat, area, p1, p2);
            doctor.setPhone(phone);
        }
        else
        {
            doctor.setPhone(null);
        }
        
        if(!line.getFax().isEmpty())
        {
            String temp = line.getFax().substring(1);
            String area = temp.substring(0, 3);
            String p1 = temp.substring(3, 3);
            String p2 = temp.substring(6);
            String phone = String.format(phoneFormat, area, p1, p2);
            doctor.setFax(phone);
        }
        else
        {
            doctor.setFax(null);
        }
        
        doctor.setEmail(line.getEmail());
        
        return doctor;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
}
