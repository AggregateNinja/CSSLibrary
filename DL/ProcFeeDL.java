
package DL;

/**
 * @date:   Mar 26, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: ProcFeeDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.BillingTestCrossReferenceDAO;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import DOS.Fee;
import DOS.Procedurecodes;
import DAOS.FeeDAO;
import DAOS.ProcedureCodeDAO;
import EPOS.FeeLine;
import DAOS.TestDAO;
import DOS.BillingTestCrossReference;
import Utility.Convert;
import java.util.ArrayList;
import java.util.List;

public class ProcFeeDL {
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        FeeDAO fdao = new FeeDAO();
        ProcedureCodeDAO pdao = new ProcedureCodeDAO();
        
        Fee fee;
        Procedurecodes proc;
        FeeLine line;
        String[] values;
        int x;
        
        List<Procedurecodes> plist = new ArrayList<Procedurecodes>();
        List<Fee> flist = new ArrayList<Fee>();
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            try
            {
                line = new FeeLine();
                x = 0;
                count++;
                
                //line 0
                line.setTestNo((Integer.parseInt(values[x])));
                x++;
                line.setSubtestNo(Integer.parseInt(values[x]));
                x++;
                line.setProcLetter1(values[x]);
                x++;
                line.setFeeCode1(values[x]);
                x++;
                line.setProcCode1(values[x]);
                x++;
                line.setMod1(values[x]);
                x++;
                line.setProcLetter2(values[x]);
                x++;
                line.setFeeCode2(values[x]);
                x++;
                line.setProcCode2(values[x]);
                x++;
                line.setMod2(values[x]);
                x++;
                line.setProcLetter3(values[x]);
                x++;
                line.setFeeCode3(values[x]);
                x++;
                line.setProcCode3(values[x]);
                x++;
                line.setMod3(values[x]);
                x++;
                line.setFeeA(Integer.parseInt(values[x]));
                x++;
                line.setFeeb(Integer.parseInt(values[x]));
                x++;
                line.setFeeC(Integer.parseInt(values[x]));
                x++;
                line.setFeeD(Integer.parseInt(values[x]));
                x++;
                line.setFeeE(Integer.parseInt(values[x]));
                x++;
                line.setFeeF(Integer.parseInt(values[x]));
                x++;
                line.setFeeG(Integer.parseInt(values[x]));
                
                //First handle the procedure codes
                plist = Line2Procs(line);
                int c = 0;
                while(c < plist.size())
                {
                    try
                    {
                        pdao.InsertProcCode(plist.get(c));
                        inserts++;
                    }catch(Exception e1)
                    {
                        try
                        {
                            pdao.UpdateProcCode(plist.get(c));
                            update++;
                        }catch(Exception e2)
                        {
                            System.out.println(e2.toString());
                            failures++;
                        }
                    }
                    
                    c++;
                }
                
                //Second handle the Fee Schedules
                 flist = Line2Fees(line);
                 int d = 0;
                 while(d < flist.size())
                 {
                     try
                     {
                         fdao.InsertFee(flist.get(d));
                         inserts++;
                     }catch(Exception e3)
                     {
                         try
                         {
                             fdao.UpdateFee(flist.get(d));
                             update++;
                         }catch(Exception e4)
                         {
                             System.out.println(e4.toString());
                             failures++;
                         }
                     }
                     d++;
                 }
            }catch(Exception ex)
            {
                System.out.println(ex.toString());
                ++unreadableLines;
            }
        }
    }
   
    
    private List<Procedurecodes> Line2Procs(FeeLine line) throws SQLException
    {
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        List<Procedurecodes> ls = new ArrayList<Procedurecodes>();
        Procedurecodes pcode;
        TestDAO tdao = new TestDAO();
        int testId;
        if(line.getSubtestNo() !=0)
        {
            BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestNo(), line.getSubtestNo());
            testId = btcr.getIdTests();
        }
        else
        {
            testId = tdao.GetTestID(line.getTestNo());
        }
        
        for(int i = 0; i < 3; i++)
        {
            pcode = new Procedurecodes();
            
            if(i == 0)
            {
                pcode.setName("1");
                pcode.setCpt(line.getProcCode1());
                pcode.setModifier1(line.getMod1());
                pcode.setTest(testId);
                
                ls.add(pcode);
            }
            
            if(i == 1)
            {
                pcode.setName("2");
                pcode.setCpt(line.getProcCode2());
                pcode.setModifier1(line.getMod2());
                pcode.setTest(testId);
                
                ls.add(pcode);
            }
            
            if(i == 2)
            {
                pcode.setName("3");
                pcode.setCpt(line.getProcCode3());
                pcode.setModifier1(line.getMod3());
                pcode.setTest(testId);
                
                ls.add(pcode);
            }
        }
        
        return ls;
    }
    
    private List<Fee> Line2Fees(FeeLine line) throws SQLException
    {
        BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
        List<Fee> ls = new ArrayList<Fee>();
        Fee f;
        TestDAO tdao = new TestDAO();
        int testId;
        if(line.getSubtestNo() !=0)
        {
            BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestNo(), line.getSubtestNo());
            testId = btcr.getIdTests();
        }
        else
        {
            testId = tdao.GetTestID(line.getTestNo());
        }
        double y;
        
        
        //A-G 
        for(int i = 0; i < 7; i++)
        {
            f = new Fee();
            
            //Fee A
            if(i == 0)
            {
                f.setFeeName("A");
                f.setTest(testId);
                y = line.getFeeA();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee B
            if(i == 1)
            {
                f.setFeeName("B");
                f.setTest(testId);
                y = line.getFeeb();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee C
            if(i == 2)
            {
                f.setFeeName("C");
                f.setTest(testId);
                y = line.getFeeC();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee D
            if(i == 3)
            {
                f.setFeeName("D");
                f.setTest(testId);
                y = line.getFeeD();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee E
            if(i == 4)
            {
                f.setFeeName("E");
                f.setTest(testId);
                y = line.getFeeE();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee F
            if(i == 5)
            {
                f.setFeeName("F");
                f.setTest(testId);
                y = line.getFeeF();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
            
            //Fee G
            if(i == 6)
            {
                f.setFeeName("G");
                f.setTest(testId);
                y = line.getFeeG();
                f.setFee(Convert.ToMoney(y));
                
                ls.add(f);
            }
        }
        
        return ls;
    }

}
