
package DL;

/**
 * @date:   Mar 20, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: BulkMasterDL.java
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
import EPOS.BulkMasterLine;
import DOS.Patients;
import DOS.Subscriber;
import DAOS.PatientDAO;
import DAOS.SubscriberDAO;
import Utility.Convert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BulkMasterDL {
    
        DateFormat DOBformatter = new SimpleDateFormat("MM/dd/yyyy");

    public void ImportFromCSV(String filepath) throws FileNotFoundException,
            IOException, SQLException, ClassNotFoundException, ParseException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        int id = 0;
        
        PatientDAO pd = new PatientDAO();
        SubscriberDAO sd = new SubscriberDAO();
        
        Patients patient;
        Subscriber subscriber;
        BulkMasterLine line;
        String[] values;
        int x = 0;
        
        //Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filepath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new BulkMasterLine();
            x=0;
            count++;
            try
            {
                //line 0
                line.setArnum(values[x]);
                x++;
                //line 1
                line.setLname(values[x]);
                x++;
                //line 2
                line.setFname(values[x]);
                x++;
                //line 3
                line.setMname(values[x]);
                x++;
                //line 4
                line.setAddr(values[x]);
                x++;
                //line 5
                line.setCity(values[x]);
                x++;
                //line 6
                line.setState(values[x]);
                x++;
                //line 7
                line.setZip(values[x]);
                x++;
                //line 8
                line.setHphone(values[x]);
                x++;
                //line 9
                line.setWphone(values[x]);
                x++;
                //line 10
                try{
                    line.setDob((Date)DOBformatter.parse(values[x]));
                }catch(Exception e){
                    line.setDob(null);
                }
                x++;
                //line 11
                line.setMedicare(values[x]);
                x++;
                //line 12
                line.setMedicaid1(values[x]);
                x++;
                //line 13
                line.setMedicaid2(values[x]);
                x++;
                //line 14
                line.setGroup1(values[x]);
                x++;
                //line 15
                line.setAgree1(values[x]);
                x++;
                //line 16
                line.setGroup2(values[x]);
                x++;
                //line 17
                line.setAgree2(values[x]);
                x++;
                //line 18
                line.setSinsurnum(Integer.parseInt(values[x]));
                x++;
                //line 19
                line.setInsnum(Integer.parseInt(values[x]));
                x++;
                //line 20
                line.setAge(Integer.parseInt(values[x]));
                x++;
                //line 21
                line.setAge_units(values[x]);
                x++;
                //line 22
                line.setClinum(Integer.parseInt(values[x]));
                x++;
                //line 23
                line.setDocnum(Integer.parseInt(values[x]));
                x++;
                //line 24
                line.setBill_type(values[x]);
                x++;
                //line 25
                try{
                    line.setCollect((Date)DOBformatter.parse(values[x]));
                }catch(Exception f){
                    line.setCollect(null);
                }
                x++;
                //line 26
                line.setColl_flag(Integer.parseInt(values[x]));
                x++;
                //line 27
                line.setSex(values[x]);
                x++;
                //line 28
                try{
                    line.setLdos((Date)DOBformatter.parse(values[x]));
                }catch(Exception g){
                    line.setLdos(null);
                }
                x++;
                //line 29
                line.setEmployer(values[x]);
                x++;
                //line 30
                line.setSnfid(values[x]);
                x++;
                //line 31
                try{
                    line.setAu_exp((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_exp(null);
                }
                x++;
                //line 32
                line.setSubs_arnum(Integer.parseInt(values[x]));
                x++;
                //line 33
                line.setRelation(values[x]);
                x++;
                //line 34
                line.setAt0(Integer.parseInt(values[x]));
                x++;
                //line 35
                line.setAt1(Integer.parseInt(values[x]));
                x++;
                //line 36
                line.setAt2(Integer.parseInt(values[x]));
                x++;
                //line 37
                line.setAt3(Integer.parseInt(values[x]));
                x++;
                //line 38
                line.setAt4(Integer.parseInt(values[x]));
                x++;
                //line 39
                line.setAt5(Integer.parseInt(values[x]));
                x++;
                //line 40
                line.setAt6(Integer.parseInt(values[x]));
                x++;
                //line 41
                line.setAt7(Integer.parseInt(values[x]));
                x++;
                //line 42
                try{
                    line.setLast_edit((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setLast_edit(null);
                }
                x++;
                //line 43
                try{
                    line.setAu_beg((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_beg(null);
                }
                x++;
                //line 44
                line.setAutest0(Integer.parseInt(values[x]));
                x++;
                //line 45
                line.setAutest1(Integer.parseInt(values[x]));
                x++;
                //line 46
                line.setAutest2(Integer.parseInt(values[x]));
                x++;
                //line 47
                line.setAutest3(Integer.parseInt(values[x]));
                x++;
                //line 48
                line.setAutest4(Integer.parseInt(values[x]));
                x++;
                //line 49
                line.setAutest5(Integer.parseInt(values[x]));
                x++;
                //line 50
                line.setAutest6(Integer.parseInt(values[x]));
                x++;
                //line 51
                line.setAutest7(Integer.parseInt(values[x]));
                x++;
                //line 52
                line.setAutest8(Integer.parseInt(values[x]));
                x++;
                //line 53
                line.setAutest9(Integer.parseInt(values[x]));
                x++;
                //line 54
                line.setAutest10(Integer.parseInt(values[x]));
                x++;
                //line 55
                line.setAutest11(Integer.parseInt(values[x]));
                x++;
                //line 56
                line.setAutest12(Integer.parseInt(values[x]));
                x++;
                //line 57
                line.setAutest13(Integer.parseInt(values[x]));
                x++;
                //line 58
                line.setAutest14(Integer.parseInt(values[x]));
                x++;
                //line 59
                line.setAutest15(Integer.parseInt(values[x]));
                x++;
                //line 60
                line.setDef_diag(values[x]);
                x++;
                //line 61
                line.setSsn(Integer.parseInt(values[x]));
                x++;
                //line 62
                line.setPatid(values[x]);
                
                //Insert into subscriber DB, grab the new ID number
                //then insert into patient DB.  Everyone has a self relationship
                //for the bulk import.
                try
                {
                    //Subscriber First
                    subscriber = Line2Subscriber(line);
                    sd.InsertSubscriber(subscriber);
                    id = sd.GetLastInsertedID();
                    
                    patient = Line2Patient(line, id);
                    pd.InsertPatient(patient);
                    inserts++;
                }catch(Exception exc)
                {
                    try
                    {
                        //Try Updating
                        //Subscriber First
                        subscriber = Line2Subscriber(line);
                        sd.UpdateSubscriber(subscriber);
                        id = sd.GetSubscriberIdByAR(line.getArnum());
                    
                        patient = Line2Patient(line, id);
                        pd.UpdatePatient(patient);
                        update++;
                    }catch(Exception exce)
                    {
                        System.out.println("AR " + line.getArnum() + " Line: " + count + ": " + exce.toString());
                        failures++;
                    }
                }
            }catch(Exception ex){
                System.out.println(count + ": " + ex.toString());
                unreadableLines++;
            }
        }
    }
    
    public void ImportSubscribersCSV(String filepath) throws FileNotFoundException,
            IOException, SQLException, ClassNotFoundException, ParseException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        SubscriberDAO sd = new SubscriberDAO();

        Subscriber subscriber;
        BulkMasterLine line;
        String[] values;
        int x = 0;
        
        //Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filepath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new BulkMasterLine();
            x=0;
            count++;
            try
            {
                //line 0
                line.setArnum(values[x]);
                x++;
                //line 1
                line.setLname(values[x]);
                x++;
                //line 2
                line.setFname(values[x]);
                x++;
                //line 3
                line.setMname(values[x]);
                x++;
                //line 4
                line.setAddr(values[x]);
                x++;
                //line 5
                line.setCity(values[x]);
                x++;
                //line 6
                line.setState(values[x]);
                x++;
                //line 7
                line.setZip(values[x]);
                x++;
                //line 8
                line.setHphone(values[x]);
                x++;
                //line 9
                line.setWphone(values[x]);
                x++;
                //line 10
                try{
                    line.setDob((Date)DOBformatter.parse(values[x]));
                }catch(Exception e){
                    line.setDob(null);
                }
                x++;
                //line 11
                line.setMedicare(values[x]);
                x++;
                //line 12
                line.setMedicaid1(values[x]);
                x++;
                //line 13
                line.setMedicaid2(values[x]);
                x++;
                //line 14
                line.setGroup1(values[x]);
                x++;
                //line 15
                line.setAgree1(values[x]);
                x++;
                //line 16
                line.setGroup2(values[x]);
                x++;
                //line 17
                line.setAgree2(values[x]);
                x++;
                //line 18
                line.setSinsurnum(Integer.parseInt(values[x]));
                x++;
                //line 19
                line.setInsnum(Integer.parseInt(values[x]));
                x++;
                //line 20
                line.setAge(Integer.parseInt(values[x]));
                x++;
                //line 21
                line.setAge_units(values[x]);
                x++;
                //line 22
                line.setClinum(Integer.parseInt(values[x]));
                x++;
                //line 23
                line.setDocnum(Integer.parseInt(values[x]));
                x++;
                //line 24
                line.setBill_type(values[x]);
                x++;
                //line 25
                try{
                    line.setCollect((Date)DOBformatter.parse(values[x]));
                }catch(Exception f){
                    line.setCollect(null);
                }
                x++;
                //line 26
                line.setColl_flag(Integer.parseInt(values[x]));
                x++;
                //line 27
                line.setSex(values[x]);
                x++;
                //line 28
                try{
                    line.setLdos((Date)DOBformatter.parse(values[x]));
                }catch(Exception g){
                    line.setLdos(null);
                }
                x++;
                //line 29
                line.setEmployer(values[x]);
                x++;
                //line 30
                line.setSnfid(values[x]);
                x++;
                //line 31
                try{
                    line.setAu_exp((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_exp(null);
                }
                x++;
                //line 32
                line.setSubs_arnum(Integer.parseInt(values[x]));
                x++;
                //line 33
                line.setRelation(values[x]);
                x++;
                //line 34
                line.setAt0(Integer.parseInt(values[x]));
                x++;
                //line 35
                line.setAt1(Integer.parseInt(values[x]));
                x++;
                //line 36
                line.setAt2(Integer.parseInt(values[x]));
                x++;
                //line 37
                line.setAt3(Integer.parseInt(values[x]));
                x++;
                //line 38
                line.setAt4(Integer.parseInt(values[x]));
                x++;
                //line 39
                line.setAt5(Integer.parseInt(values[x]));
                x++;
                //line 40
                line.setAt6(Integer.parseInt(values[x]));
                x++;
                //line 41
                line.setAt7(Integer.parseInt(values[x]));
                x++;
                //line 42
                try{
                    line.setLast_edit((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setLast_edit(null);
                }
                x++;
                //line 43
                try{
                    line.setAu_beg((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_beg(null);
                }
                x++;
                //line 44
                line.setAutest0(Integer.parseInt(values[x]));
                x++;
                //line 45
                line.setAutest1(Integer.parseInt(values[x]));
                x++;
                //line 46
                line.setAutest2(Integer.parseInt(values[x]));
                x++;
                //line 47
                line.setAutest3(Integer.parseInt(values[x]));
                x++;
                //line 48
                line.setAutest4(Integer.parseInt(values[x]));
                x++;
                //line 49
                line.setAutest5(Integer.parseInt(values[x]));
                x++;
                //line 50
                line.setAutest6(Integer.parseInt(values[x]));
                x++;
                //line 51
                line.setAutest7(Integer.parseInt(values[x]));
                x++;
                //line 52
                line.setAutest8(Integer.parseInt(values[x]));
                x++;
                //line 53
                line.setAutest9(Integer.parseInt(values[x]));
                x++;
                //line 54
                line.setAutest10(Integer.parseInt(values[x]));
                x++;
                //line 55
                line.setAutest11(Integer.parseInt(values[x]));
                x++;
                //line 56
                line.setAutest12(Integer.parseInt(values[x]));
                x++;
                //line 57
                line.setAutest13(Integer.parseInt(values[x]));
                x++;
                //line 58
                line.setAutest14(Integer.parseInt(values[x]));
                x++;
                //line 59
                line.setAutest15(Integer.parseInt(values[x]));
                x++;
                //line 60
                line.setDef_diag(values[x]);
                x++;
                //line 61
                line.setSsn(Integer.parseInt(values[x]));
                x++;
                //line 62
                line.setPatid(values[x]);
                
                //Insert into subscriber DB, grab the new ID number
                //then insert into patient DB.  Everyone has a self relationship
                //for the bulk import.
                try
                {
                    //Subscriber First
                    subscriber = Line2Subscriber(line);

                    if(!sd.SubscriberExists(subscriber.getArNo()))
                    {
                        sd.InsertSubscriber(subscriber);
                        inserts++;
                    }
                    else
                    {
                        sd.UpdateSubscriber(subscriber);
                        update++;
                    }
                }catch(Exception exc)
                {
                    System.out.println("AR " + line.getArnum() + " Line: " + count + ": " + exc.toString());
                    failures++;
                }
            }catch(Exception ex){
                System.out.println(" Line: " + count + ": " + ex.toString());
                unreadableLines++;
            }
        }
    }
    
    public void ImportPatientCSV(String filepath) throws FileNotFoundException,
            IOException, SQLException, ClassNotFoundException, ParseException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        PatientDAO pd = new PatientDAO();
        
        
        Patients patient;
        BulkMasterLine line;
        String[] values;

        
        //Read in the file with the CSVReader
        CSVReader reader = new CSVReader(new FileReader(filepath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            line = new BulkMasterLine();
            int x=0;
            int id = 0;
            count++;
            try
            {
                //line 0
                line.setArnum(values[x]);
                x++;
                //line 1
                line.setLname(values[x]);
                x++;
                //line 2
                line.setFname(values[x]);
                x++;
                //line 3
                line.setMname(values[x]);
                x++;
                //line 4
                line.setAddr(values[x]);
                x++;
                //line 5
                line.setCity(values[x]);
                x++;
                //line 6
                line.setState(values[x]);
                x++;
                //line 7
                line.setZip(values[x]);
                x++;
                //line 8
                line.setHphone(values[x]);
                x++;
                //line 9
                line.setWphone(values[x]);
                x++;
                //line 10
                try{
                    line.setDob((Date)DOBformatter.parse(values[x]));
                }catch(Exception e){
                    line.setDob(null);
                }
                x++;
                //line 11
                line.setMedicare(values[x]);
                x++;
                //line 12
                line.setMedicaid1(values[x]);
                x++;
                //line 13
                line.setMedicaid2(values[x]);
                x++;
                //line 14
                line.setGroup1(values[x]);
                x++;
                //line 15
                line.setAgree1(values[x]);
                x++;
                //line 16
                line.setGroup2(values[x]);
                x++;
                //line 17
                line.setAgree2(values[x]);
                x++;
                //line 18
                line.setSinsurnum(Integer.parseInt(values[x]));
                x++;
                //line 19
                line.setInsnum(Integer.parseInt(values[x]));
                x++;
                //line 20
                line.setAge(Integer.parseInt(values[x]));
                x++;
                //line 21
                line.setAge_units(values[x]);
                x++;
                //line 22
                line.setClinum(Integer.parseInt(values[x]));
                x++;
                //line 23
                line.setDocnum(Integer.parseInt(values[x]));
                x++;
                //line 24
                line.setBill_type(values[x]);
                x++;
                //line 25
                try{
                    line.setCollect((Date)DOBformatter.parse(values[x]));
                }catch(Exception f){
                    line.setCollect(null);
                }
                x++;
                //line 26
                line.setColl_flag(Integer.parseInt(values[x]));
                x++;
                //line 27
                line.setSex(values[x]);
                x++;
                //line 28
                try{
                    line.setLdos((Date)DOBformatter.parse(values[x]));
                }catch(Exception g){
                    line.setLdos(null);
                }
                x++;
                //line 29
                line.setEmployer(values[x]);
                x++;
                //line 30
                line.setSnfid(values[x]);
                x++;
                //line 31
                try{
                    line.setAu_exp((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_exp(null);
                }
                x++;
                //line 32
                line.setSubs_arnum(Integer.parseInt(values[x]));
                x++;
                //line 33
                line.setRelation(values[x]);
                x++;
                //line 34
                line.setAt0(Integer.parseInt(values[x]));
                x++;
                //line 35
                line.setAt1(Integer.parseInt(values[x]));
                x++;
                //line 36
                line.setAt2(Integer.parseInt(values[x]));
                x++;
                //line 37
                line.setAt3(Integer.parseInt(values[x]));
                x++;
                //line 38
                line.setAt4(Integer.parseInt(values[x]));
                x++;
                //line 39
                line.setAt5(Integer.parseInt(values[x]));
                x++;
                //line 40
                line.setAt6(Integer.parseInt(values[x]));
                x++;
                //line 41
                line.setAt7(Integer.parseInt(values[x]));
                x++;
                //line 42
                try{
                    line.setLast_edit((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setLast_edit(null);
                }
                x++;
                //line 43
                try{
                    line.setAu_beg((Date)DOBformatter.parse(values[x]));
                }catch(Exception h){
                    line.setAu_beg(null);
                }
                x++;
                //line 44
                line.setAutest0(Integer.parseInt(values[x]));
                x++;
                //line 45
                line.setAutest1(Integer.parseInt(values[x]));
                x++;
                //line 46
                line.setAutest2(Integer.parseInt(values[x]));
                x++;
                //line 47
                line.setAutest3(Integer.parseInt(values[x]));
                x++;
                //line 48
                line.setAutest4(Integer.parseInt(values[x]));
                x++;
                //line 49
                line.setAutest5(Integer.parseInt(values[x]));
                x++;
                //line 50
                line.setAutest6(Integer.parseInt(values[x]));
                x++;
                //line 51
                line.setAutest7(Integer.parseInt(values[x]));
                x++;
                //line 52
                line.setAutest8(Integer.parseInt(values[x]));
                x++;
                //line 53
                line.setAutest9(Integer.parseInt(values[x]));
                x++;
                //line 54
                line.setAutest10(Integer.parseInt(values[x]));
                x++;
                //line 55
                line.setAutest11(Integer.parseInt(values[x]));
                x++;
                //line 56
                line.setAutest12(Integer.parseInt(values[x]));
                x++;
                //line 57
                line.setAutest13(Integer.parseInt(values[x]));
                x++;
                //line 58
                line.setAutest14(Integer.parseInt(values[x]));
                x++;
                //line 59
                line.setAutest15(Integer.parseInt(values[x]));
                x++;
                //line 60
                line.setDef_diag(values[x]);
                x++;
                //line 61
                line.setSsn(Integer.parseInt(values[x]));
                x++;
                //line 62
                line.setPatid(values[x]);
                
                //Insert into subscriber DB, grab the new ID number
                //then insert into patient DB.  Everyone has a self relationship
                //for the bulk import.
                try
                {
                    SubscriberDAO sd = new SubscriberDAO();
                    id = sd.GetSubscriberIdByAR(line.getArnum());
                    
                    patient = Line2Patient(line, id);
                    
                    if (!pd.PatientExistsByID(id)) 
                    {
                        pd.InsertPatient(patient);
                        inserts++;
                    }
                    else
                    {
                        pd.UpdatePatient(patient);
                        update++;
                    }
                }catch(Exception exc)
                {
                    System.out.println("AR " + line.getArnum() + " Line: " + count + ": " + exc.toString());
                    failures++;
                }
            }catch(Exception ex){
                System.out.println(" Line: " + count + ": " + ex.toString());
                unreadableLines++;
            }
        }
    }
    
    private Patients Line2Patient(BulkMasterLine line, int subscriber)
    {
        Patients pat = new Patients();
        
        pat.setArNo("" + line.getArnum());
        pat.setLastName(line.getLname());
        pat.setFirstName(line.getFname());
        pat.setMiddleName(line.getMname());
        pat.setSex(line.getSex());
        pat.setSsn("" + line.getSsn());
        pat.setDob(line.getDob());
        pat.setAddressStreet(line.getAddr());
        pat.setAddressCity(line.getCity());
        pat.setAddressState(line.getState());
        pat.setAddressZip(line.getZip());
        pat.setPhone(line.getHphone());
        pat.setWorkPhone(line.getWphone());
        pat.setSubscriber(subscriber);
        pat.setRelationship("self");
        
        return pat;
    }
    
    private Subscriber Line2Subscriber(BulkMasterLine line)
    {
        Subscriber sub = new Subscriber();
        
        sub.setArNo("" + line.getArnum());
        sub.setLastName(line.getLname());
        sub.setFirstName(line.getFname());
        sub.setMiddleName(line.getMname());
        sub.setSex(line.getSex());
        sub.setSsn("" + line.getSsn());
        sub.setDob(line.getDob());
        sub.setAddressStreet(line.getAddr());
        sub.setAddressCity(line.getCity());
        sub.setAddressState(line.getState());
        sub.setAddressZip(line.getZip());
        sub.setPhone(line.getHphone());
        sub.setWorkPhone(line.getWphone());
        sub.setInsurance(line.getInsnum());
        sub.setSecondaryInsurance(line.getSinsurnum());
        sub.setPolicyNumber(line.getAgree1());
        sub.setGroupNumber(line.getGroup1());
        sub.setSecondaryPolicyNumber(line.getAgree2());
        sub.setSecondaryGroupNumber(line.getGroup2());
        sub.setMedicareNumber(line.getMedicare());
        //Medicaid Number Logic
        if(line.getMedicaid2().isEmpty() && line.getMedicaid1().length() > 0)
        {
            sub.setMedicaidNumber(line.getMedicaid1());
        }   
        else
        {
            if(line.getMedicaid1().isEmpty() && line.getMedicaid2().length() > 0)
            {
                sub.setMedicaidNumber(line.getMedicaid2());
            }
            else
            {
                sub.setMedicaidNumber(line.getMedicaid1());
            }
        }
        
        return sub;
        
    }
}
