package DL;

import DAOS.CounselorDAO;
import DOS.Counselors;
import EPOS.CounselorLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @date:   Mar 13, 2012
 * @author: Keith Maggio
 * 
 * @project:  
 * @package: DL
 * @file name: CounselorDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class CounselorDL 
{
public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        CounselorDAO rd = new CounselorDAO();
        
        Counselors counselor;
        CounselorLine line;
        String[] values;
        String LongName;
        int namebreak;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new CounselorLine();
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
                // Other Counselor Number
                ++x;
                // Sort Sequence
                ++x;
                line.setExternalId(values[x]);
                ++x;
                // Email
                ++x;
                // Phone
                ++x;
                // Counselor Code
                ++x;
                line.setAddress2(values[x]);
                ++x;
                // FAX number
                ++x;
                if(values[x].isEmpty() == false)
                {
                    try
                    {
                        line.setNPI(Integer.parseInt(values[x]));
                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("Counselor " + line.getNumber() + ": " + ex.toString());
                        line.setNPI(0);
                    }
                }
                else
                {
                    line.setNPI(0);
                }
                ++x;
                // Counselor Status
                ++x;
                // Institution
                
                
                try{
                    counselor = EPO2DO(line);
                    if(!rd.CounselorsExists(counselor.getNumber()))
                    {
                        rd.InsertCounselor(counselor);
                        inserts++;
                    }
                    else
                    {
                        counselor.setIdcounselors(rd.GetCounselorID(counselor.getNumber()));
                        rd.UpdateCounselor(counselor);
                        update++;
                    }
                    
                }catch(Exception ex1){
                    System.out.println("Counselor " + line.getNumber() + ": " + ex1.toString());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println("Counselor " + line.getNumber() + ": " + ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the Remark EPO to the Remark DO
    public Counselors EPO2DO(CounselorLine line)
    {
        try{
        Counselors counselor = new Counselors();
        
        counselor.setNumber(line.getNumber());
        counselor.setLastName(line.getLastName());
        counselor.setFirstName(line.getFirstName());
        counselor.setAddress1(line.getAddress1());
        counselor.setAddress2(line.getAddress2());
        counselor.setCity(line.getCity());
        counselor.setState(line.getState());
        counselor.setZip(line.getZip());
        counselor.setUpin(line.getUPIN());
        counselor.setNpi(line.getNPI());
        counselor.setExternalId(line.getExternalId());
        //counselor.setCrossref(line.getCrossRef());
        
        
        return counselor;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
}
