
package DL;

/**
 * @date:   Mar 6, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: RemarkDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import DOS.Remarks;
import DAOS.RemarkDAO;
import EPOS.RemarkLine;
import java.util.HashMap;

    

public class RemarkDL {

    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        int specials = 0;
        HashMap<Integer, String> special = new HashMap<Integer, String>();
        
        RemarkDAO rd = new RemarkDAO();
        
        Remarks remark;
        RemarkLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            try{
                line = new RemarkLine();
                x = 0;
                count++;
                
                //line 0
                line.setRemarkNumber(Integer.parseInt(values[x]));
                x++;
                line.setRemarkText(values[x]);
                x++;
                line.setSortSequence(values[x]);
                x++;
                line.setDepartment(Integer.parseInt(values[x]));
                x++;
                line.setNoBillFlag(Integer.parseInt(values[x]) !=0 ?true:false);
                x++;
                line.setTestNumber(Integer.parseInt(values[x]));
                x++;
                line.setSubtestNumber(Integer.parseInt(values[x]));
                
                // Handle special remarks
                if(line.getRemarkNumber() <= 100)
                {
                    try
                    {
                        String[] specialRemark = line.getRemarkText().split(" ");
                        special.put(Integer.parseInt(specialRemark[0]), specialRemark[1]);
                        ++specials;
                    }
                    catch (NumberFormatException numberFormatException)
                    {
                        System.out.println("Special Remark " + line.getRemarkNumber() + " not added. " + numberFormatException.getMessage());
                        failures++;
                    }
                    continue;
                }
                
                if(special.get(line.getRemarkNumber()) != null)
                {
                    line.setSortSequence(special.get(line.getRemarkNumber()));
                }
                
                try{
                    remark = EPO2DO(line);
                    //int rr = rd.GetRemarkID(remark.getRemarkNo());
                    if(rd.GetRemarkID(remark.getRemarkNo()) == 0)
                    {
                        rd.InsertRemark(remark);
                        inserts++;
                    }
                    else
                    {
                        rd.UpdateRemark(remark);
                        update++;
                    }
                }catch(Exception ex1){
                    /*try{
                        System.out.println(ex1.toString());
                        remark = EPO2DO(line);
                        rd.UpdateRemark(remark);
                        update++;
                    }catch(Exception ex2)
                    {
                        System.out.println(ex2.toString());
                        failures++;
                    }*/
                    System.out.println("Remark " + line.getRemarkNumber() + " failed. " + ex1.toString());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
        System.out.println("Inserts: " + inserts);
        System.out.println("Updates: " + update);
        System.out.println("Failures: " + failures);
        System.out.println("Unreadable Lines: " + unreadableLines);
        System.out.println("Special Remarks: " + specials);

    }
    
    //Converts the Remark EPO to the Remark DO
    public Remarks EPO2DO(RemarkLine line)
    {
        try{
        Remarks remark = new Remarks();
        byte[] text = line.getRemarkText().getBytes();
        
        remark.setRemarkNo(line.getRemarkNumber());
        String name;
        if(line.getSortSequence().trim().isEmpty() == true)
        {
            if(line.getRemarkText().length() > 45)
            {
                name = line.getRemarkText().substring(0, 45).trim() + "...";
            }
            else
            {
                name = line.getRemarkText();
            }
        }
        else
        {
            name = line.getSortSequence();
        }
        remark.setRemarkName(name);
        if(name.length() > 10)
        {
            remark.setRemarkAbbr(name.substring(0, 10));
        }
        else
        {
            remark.setRemarkAbbr(name);
        }
        //Currently not being used.  For future use.
        remark.setRemarkType(0);
        remark.setRemarkText(text);
        //Currently not being used.  For future use.
        remark.setIsAbnormal(false);
        remark.setRemarkDepartment(line.getDepartment());
        remark.setNoCharge(line.getNoBillFlag());
        remark.setTestId(line.getTestNumber() + line.getSubtestNumber());
        
        return remark;
        }catch(Exception e){
            System.out.println("Remark " + line.getRemarkNumber() + ": " + e.toString());
            return null;
        }
        
    }
}
