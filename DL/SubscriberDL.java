package DL;

import DAOS.SubscriberDAO;
import DOS.Subscriber;
import EPOS.SubscriberLine;
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

public class SubscriberDL 
{
public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        SubscriberDAO rd = new SubscriberDAO();
        
        Subscriber subscriber;
        SubscriberLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new SubscriberLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[0]);
                        
            try{
                line.setMasterNumber(Integer.parseInt(values[x]));
                ++x;
                line.setLastName(values[x]);
                ++x;
                line.setFirstName(values[x]);
                ++x;
                line.setMiddleInitial(values[x].charAt(0));
                ++x;
                line.setPatientSex(values[x].charAt(0));
                ++x;
                line.setSocialSecurityNumber(Integer.parseInt(values[x]));
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
                line.setZipCode(Integer.parseInt(values[x]));
                ++x;
                line.setHomePhone(values[x]);
                ++x;
                line.setInsuranceNumber(Integer.parseInt(values[x]));
                ++x;
                line.setSecondaryInsNumber(Integer.parseInt(values[x]));
                ++x;
                line.setMedicare(values[x]);
                ++x;
                line.setMedicaid1(values[x]);
                ++x;
                line.setMedicaid2(values[x]);
                ++x;
                line.setAgreement1(values[x]);
                ++x;
                line.setAgreement2(values[x]);
                ++x;
                line.setGroup1(values[x]);
                ++x;
                line.setGroup2(values[x]);
                
                
                try{
                    subscriber = EPO2DO(line);
                    rd.InsertSubscriber(subscriber);
                    inserts++;
                }catch(Exception ex1){
                    try{
                        System.out.println("Insert SubscriberDL: AR:" + line.getMasterNumber() + " Count: " + count);
                        System.out.println(ex1.toString());
                        subscriber = EPO2DO(line);
                        rd.UpdateSubscriber(subscriber);
                        update++;
                    }catch(Exception ex2)
                    {
                        System.out.println("Update SubscriberDL: AR:" + line.getMasterNumber() + " Count: " + count);
                        System.out.println(ex2.toString());
                        failures++;
                    }
                }
                
            }catch(Exception ex){
                System.out.println("Parse SubscriberDL: AR:" + line.getMasterNumber() + " Count:Col: " + count + ":" + x);
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Subscriber EPO2DO(SubscriberLine line)
    {
        try{
        Subscriber sub = new Subscriber();
        
        
        sub.setArNo(String.valueOf(line.getMasterNumber()));
        sub.setLastName(line.getLastName());
        sub.setFirstName(line.getFirstName());
        sub.setMiddleName(String.valueOf(line.getMiddleInitial()));
        sub.setSex(String.valueOf(line.getPatientSex()));
        sub.setSsn(String.valueOf(line.getSocialSecurityNumber()));
        sub.setDob(line.getDateofBirth());
        sub.setAddressStreet(line.getAddress1());
        sub.setAddressStreet2(line.getAddress2());
        sub.setAddressCity(line.getCity());
        sub.setAddressState(line.getState());
        sub.setAddressZip(String.valueOf(line.getZipCode()));
        sub.setPhone(line.getHomePhone());
        sub.setInsurance(line.getInsuranceNumber());
        sub.setSecondaryInsurance(line.getSecondaryInsNumber());
        sub.setMedicareNumber(line.getMedicare());
        if(line.getMedicaid1().isEmpty() == false)
            sub.setMedicaidNumber(line.getMedicaid1());
        else
            sub.setMedicaidNumber(line.getMedicaid2());
        sub.setPolicyNumber(line.getAgreement1());
        sub.setSecondaryPolicyNumber(line.getAgreement2());
        sub.setGroupNumber(line.getGroup1());
        sub.setSecondaryGroupNumber(line.getGroup2());
        sub.setActive(true);
        sub.setDeactivatedDate(null);
        
        return sub;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
}
