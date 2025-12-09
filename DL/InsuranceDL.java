package DL;

import DAOS.InsuranceDAO;
import DOS.Insurances;
import EPOS.InsuranceLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @date:   Mar 14, 2012
 * @author: CSS Dev
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: InsuranceDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class InsuranceDL 
{
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        InsuranceDAO rd = new InsuranceDAO();
        
        Insurances insurance;
        InsuranceLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new InsuranceLine();
            x = 0;
            count++;
            try{
                
                
                line.setNumber(Integer.parseInt(values[x]));
                ++x;
                line.setAbbreviation(values[x]);
                ++x;
                line.setName(values[x]);
                ++x;
                line.setExtraName(values[x]);
                ++x;
                line.setAddress1(values[x]);
                ++x;
                line.setAddress2(values[x]);
                ++x;
                line.setCity(values[x]);
                ++x;
                line.setState(values[x]);
                ++x;
                line.setPayorId(values[x]);
                ++x;
                line.setZip(values[x]);
                ++x;
                line.setFeeType(values[x]);
                ++x;
                line.setProcedureCode(Integer.parseInt(values[x]));
                ++x;
                //line.setBillingCodes(values[x]);
                ++x;
                line.setPassword(values[x]);
                ++x;
                line.setExtraService(values[x]);
                ++x;
                line.setType(values[x]);
                ++x;
                line.setActive(values[x]);
                ++x;
                line.setPhone(values[x]);
                ++x;
                line.setPaySource(values[x]);
                ++x;
                line.setInsuranceType(values[x]);
                ++x;
                line.setInternalUseOnly(Boolean.parseBoolean(values[x]));
                ++x;
                line.setReceiverId(values[x]);
                ++x;
                line.setTransmitInsNumber(values[x]);
                ++x;
                line.setCommercialInsurance(values[x]);
                ++x;
                line.setRedForm(values[x]);
                ++x;
                line.setAcceptAssignment(values[x]);
                ++x;
                line.setSourceId1(values[x]);
                ++x;
                line.setSourceId2(values[x]);
                ++x;
                line.setDoctorId(values[x]);
                ++x;
                line.setNumericType(Integer.parseInt(values[x]));
                ++x;
                line.setId(Integer.parseInt(values[x]));
                ++x;
                
                try{
                    insurance = EPO2DO(line);
                    //if(rd.GetInsurance(insurance.getNumber()).getIdinsurances() == 0)
                    if(!rd.InsuranceExists(insurance.getIdinsurances()))
                    {
                        rd.InsertInsurance(insurance);
                        inserts++;
                    }
                    else
                    {
                        rd.UpdateInsurance(insurance);
                        update++;
                    }
                }catch(Exception ex1){
                    System.out.println(line.getNumber() + ": " + ex1.toString());
                    failures++;
                }
                
            }catch(Exception ex){
                System.out.println(line.getNumber() + ": (" + count + "," + x + ") " + ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the InsuranceDL EPO to the InsuranceDL DO
    public Insurances EPO2DO(InsuranceLine line)
    {
        return null;
        //try{
        /*
        Insurances ins = new Insurances();

        //Sample:
        /*
        byte[] text = line.getRemarkText().getBytes();
        
        remark.setRemarkNo(line.getRemarkNumber());
        remark.setRemarkName(line.getSortSequence());
        remark.setRemarkAbbr(line.getSortSequence());
        //Currently not being used.  For future use.
        remark.setRemarkType(0);
        remark.setRemarkText(text);
        //Currently not being used.  For future use.
        remark.setIsAbnormal(0);
        remark.setRemarkDepartment(line.getDepartment());
        remark.setNoCharge(line.getNoBillFlag());
        remark.setTestId(line.getTestNumber() + line.getSubtestNumber());
        
        ins.setIdinsurances(line.getNumber());
        ins.setName(line.getName());
        ins.setAbbreviation(line.getAbbreviation());
        ins.setAddress(line.getAddress1());
        ins.setAddress2(line.getAddress2());
        ins.setCity(line.getCity());
        ins.setState(line.getState());
        ins.setZip(line.getZip());
        ins.setPhone(line.getPhone());
        ins.setReceiverid(line.getReceiverId());
        ins.setSourceid1(line.getSourceId1());
        ins.setSourceid2(line.getSourceId2());
        ins.setPayorid(line.getPayorId());
        ins.setFee(2);
        ins.setProcedureset(1);
        ins.setServicetype(line.getInsuranceType().substring(0, 0));
        /*
        if(line.getRedForm().isEmpty())
        {
            ins.setHcfa(true);
        }
        else
        {
            ins.setHcfa(false);
        }
        
        if( "*".equals(line.getTransmitInsNumber()))
        {
            ins.setCapario(true); 
        }
        else
        {
            ins.setCapario(false); 
        }
        
        
        ins.setInsType(line.getType());
        ins.setAcceptAssignment(line.getAcceptAssignment());
        ins.setInternalOnly(line.isInternalUseOnly());
        
        return ins;
        }catch(Exception e){
            System.out.println(line.getNumber() + ": " + e.toString());
            return null;
        }
        */
        //return null;
    }
}
