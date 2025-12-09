
package DL;

/**
 * @date:   Mar 13, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: TestDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.BillingTestCrossReferenceDAO;
import DAOS.MultichoiceDAO;
import DAOS.PanelDAO;
import DAOS.RemarkDAO;
import DAOS.TestDAO;
import DOS.BillingTestCrossReference;
import DOS.Multichoice;
import DOS.Panels;
import DOS.Tests;
import EPOS.TestLine;
import Utility.Convert;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TestDL {
    
    RemarkDAO rDAO = new RemarkDAO();
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException,
                                                      IOException, ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int panIns = 0;
        int panUps = 0;
        int panFails = 0;
        int billIns = 0;
        int billUps = 0;
        int billFails = 0;
        int multIns = 0;
        int multUps = 0;
        int multFails = 0;
        int unreadableLines = 0;

        TestDAO td = new TestDAO();
        PanelDAO pd = new PanelDAO();
        MultichoiceDAO md = new MultichoiceDAO();

        Tests test;
        Panels panel;
        Multichoice choice;
        List<Panels> groups = new ArrayList<Panels>();
        TempPanel temp;
        List<TempPanel> grps = new ArrayList<TempPanel>();
        TestLine line;
        String[] values;
        int x = 0;

        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);

        while ((values = reader.readNext()) != null)
        {
            line = new TestLine();
            x = 0;
            count++;
            try
            {

                int testNO = Integer.parseInt(values[x]);
                if(testNO == 93)
                    testNO = 93;

                //line 0
                line.setTestNumber(Integer.parseInt(values[x]));
                x++;
                //line 1
                line.setSubtestNumber(Integer.parseInt(values[x]));
                x++;
                //line 2
                line.setResno(Integer.parseInt(values[x]));
                x++;
                //line 3
                line.setTestName(values[x].trim());
                x++;
                //line 4
                line.setAbbreviation(values[x].trim());
                x++;
                //line 5
                line.setType(Integer.parseInt(values[x]));
                x++;
                //line 6
                line.setTypeText(values[x]);
                x++;
                //line 7
                try
                {
                    line.setDepartment(Integer.parseInt(values[x]));
                }
                catch (Exception r)
                {
                    line.setDepartment(0);
                }
                x++;
                //line 8
                line.setInstrument(Integer.parseInt(values[x]));
                x++;
                //line 9
                line.setOnlineCode(values[x]);
                x++;
                //line 10
                try
                {
                    Integer printOrder = Integer.parseInt(values[x]);
                    line.setPrintOrderFlag(printOrder);
                }
                catch (NumberFormatException numberFormatException)
                {
                    line.setPrintOrderFlag(16000);
                }
                x++;
                //line 11
                int resType;
                try
                {
                    resType = Integer.parseInt(values[x]);
                }
                catch (NumberFormatException ex)
                {
                    resType = 0;
                }
                switch (resType)
                {
                    case 62:
                    case 60:
                    case 78:
                    case 71:
                    case 76:
                    case 87:
                        line.setResultType("Numeric");
                        break;
                    case 82:
                        line.setResultType("Remark");
                        break;
                    case 65:
                    case 68:
                        line.setResultType("Displacement");
                        break;
                    case 77:
                    case 81:
                        line.setResultType("Mixed");
                        break;
                    case 84:
                        line.setResultType("Text");
                        break;
                    default:
                        line.setResultType("");

                }
                x++;
                //line 12
                line.setDecimalPosition(Integer.parseInt(values[x]));
                x++;
                //line 13
                line.setRequired(Integer.parseInt(values[x]));
                x++;
                //line 14
                line.setStat(values[x]);
                x++;
                //line 15
                line.setCycles(Integer.parseInt(values[x]));
                x++;
                //line 16
                line.setMultiplier(Integer.parseInt(values[x]));
                x++;
                //line 17
                line.setUnitOfMeasure(values[x]);
                x++;
                //line 18
                line.setNormalRange(values[x]);
                x++;
                //line 19
                line.setNormalsLow(Integer.parseInt(values[x]));
                x++;
                //line 20
                line.setNormalsHigh(Integer.parseInt(values[x]));
                x++;
                //line 21
                line.setPanicLow(Integer.parseInt(values[x]));
                x++;
                //line 22
                line.setPanicHigh(Integer.parseInt(values[x]));
                x++;
                //line 23
                line.setWorksheetNumber(Integer.parseInt(values[x]));
                x++;
                //line 24
                line.setColumn(Integer.parseInt(values[x]));
                x++;
                //line 25
                try
                {
                    line.setDegreeOfDiff(Integer.parseInt(values[x]));
                }
                catch (Exception rsp)
                {
                    line.setDegreeOfDiff(0);
                }
                x++;
                //line 26
                line.setTCDRemark(Integer.parseInt(values[x]));
                x++;
                //line 27
                //line.setHasExraNormals(Integer.parseInt(values[x]));
                line.setHasExraNormals(0);
                x++;
                //line 28
                line.setGroupItemCount(Integer.parseInt(values[x]));
                x++;
                //line 29
                line.setGroupNumbers(values[x]);

                //If is a single test
                if (line.getType() == 5)
                {
                    try
                    {
                        test = TestLine2Single(line);

                        if (td.GetTestID(test.getNumber()) == 0)
                        {
                            td.InsertTest(test);
                            inserts++;
                        }
                        else
                        {
                            td.UpdateTest(test);
                            System.out.println(line.getTestNumber() + ": (" + count + ") Updated.");
                            update++;
                        }
                    }
                    catch (Exception ex2)
                    {
                        System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + ex2.toString());
                        failures++;
                    }
                }

                //If test is a composite header
                if (line.getType() == 2)
                {
                    try
                    {
                        test = TestLine2Composite(line);

                        if (td.GetTestID(test.getNumber()) == 0)
                        {
                            td.InsertTest(test);
                            inserts++;
                        }
                        else
                        {
                            td.UpdateTest(test);
                            System.out.println(line.getTestNumber() + ": (" + count + ") Updated.");
                            update++;
                        }
                        
                    }
                    catch (Exception ex4)
                    {
                        System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + ex4.toString());
                        failures++;
                    }
                }

                //If test is a subtest
                if (line.getType() == 3)
                {
                    try
                    {
                        int oldTestNum = line.getTestNumber();
                        int oldSubNum = line.getSubtestNumber();
                        
                        test = TestLine2Subtest(line);

                        if (td.GetTestID(test.getNumber()) == 0)
                        {
                            //First Insert The Subtest
                            td.InsertTest(test);
                            inserts++;
                        }
                        else
                        {
                            td.UpdateTest(test);
                            System.out.println(test.getNumber() + ": (" + count + ") Updated.");
                            update++;
                        }
                        
                        // Composites, in the new system, are being phased out in place of Panels
                        // So we save this test off to the Panel buffer
                        temp = new TempPanel();
                        temp.setGroupNumber(oldTestNum);
                        temp.setSubtestNumber(test.getNumber());
                        temp.setPrintOrder(oldSubNum);

                        grps.add(temp);
                        
                        // Create a billing cross reference object to the test.
                        try
                        {
                            BillingTestCrossReference xref = new BillingTestCrossReference();
                            BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
                            Integer tID = td.GetTestID(test.getNumber());
                            xref.setIdTests(tID);
                            xref.setTestNumber(oldTestNum);
                            xref.setSubTestNumber(oldSubNum);
                            if (btcrDAO.Exists(oldTestNum, oldSubNum) == false)
                            {
                                btcrDAO.InsertTest(xref);
                                ++billIns;
                            }
                            else
                            {
                                xref.setIdbillingTestCrossReference(btcrDAO.GetBillingTestCrossReferenceID(oldTestNum, oldSubNum, tID));
                                btcrDAO.UpdateBillingTestCrossReference(xref);
                                ++billUps;
                            }
                        }
                        catch (SQLException sqlException)
                        {
                            System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") -BILL- " + sqlException.toString());
                            billFails++;
                        }
                    }
                    catch (Exception ex6)
                    {
                        System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + ex6.toString());
                        failures++;
                    }
                }

                //If Test is a Group we have to treat it a little different 
                //since all of it's compnents might not yet be in the test table,
                //there fore it would violate the constraints.  We have to hold
                //all groups untill after the entire file has been parsed and the
                //table updated, then add/update the group panels
                if (line.getType() == 1)
                {
                    try
                    {
                        //First Take care of the Group Header
                        test = TestLine2Composite(line);

                        if (td.GetTestID(test.getNumber()) == 0)
                        {
                            td.InsertTest(test);
                            inserts++;
                        }
                        else
                        {
                            td.UpdateTest(test);
                            System.out.println(line.getTestNumber() + ": (" + count + ") Updated.");
                            update++;
                        }
                    }
                    catch (Exception ex11)
                    {
                        System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + ex11.toString());
                        failures++;
                    }

                    //Now add the Panles Objects to the List
                    int[] members = GetGroupTests(line.getGroupNumbers());

                    /*
                     for(int i=0;i<members.length;i++)
                     {
                     panel = new Panels();
                     //panel.setIdpanels(line.getTestNumber());
                     //panel.setIdpanels(td.GetTestID(members[i], 0));
                     panel.setIdpanels(td.GetTestID(test.getNumber(), 0));
                     //panel.setSubtestId(members[i]);
                     panel.setSubtestId(td.GetTestID(members[i], 0));
                     panel.setSubtestOrder(i);
                        
                     groups.add(panel);
                     } 
                     */

                    for (int i = 0; i < members.length; i++)
                    {
                        temp = new TempPanel();
                        temp.setGroupNumber(line.getTestNumber());
                        temp.setSubtestNumber(members[i]);
                        temp.setPrintOrder(i);

                        grps.add(temp);
                    }
                }

                // Result Type, AKA Multichoice
                if (line.getType() == 4)
                {
                    try
                    {
                        choice = TestLine2Choice(line);

                        if (md.GetChoiceId(choice.getTestId(), choice.getChoiceOrder()) == 0)
                        {
                            md.InsertMultichoice(choice);
                            multIns++;
                        }
                        else
                        {
                            md.UpdateMultichoice(choice);
                            multUps++;
                        }
                    }
                    catch (Exception ex12)
                    {
                        System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + ex12.toString());
                        multFails++;
                    }
                }


            }
            catch (Exception e)
            {
                System.out.println(line.getTestNumber() + ": (" + count + "," + x + ") " + "ParseException: " + e.toString());
                unreadableLines++;
            }
        }


        //Now time to go back and add all the group panel rows
        /*for(Panels rp : groups)
         {
            try
            {
                pd.InsertPanel(rp);
            }catch(Exception ex)
            {
                try
                {
                    pd.UpdatePanel(rp);
                }catch(Exception ex1)
                {
                    System.out.println(ex1.toString());
                }
            }   
         }
         */

        for (TempPanel tp : grps)
        {
            panel = new Panels();
            panel = Temp2Panel(tp);

            if(pd.Exists(panel.getIdpanels(), panel.getSubtestId()) == false)
            {
                try
                {
                    pd.InsertPanel(panel);
                    ++panIns;
                }
                catch (Exception rsp)
                {
                    System.out.println("Panel " + tp.getGroupNumber() + ":" + tp.getSubtestNumber() + "(" + count + ") " + rsp.toString());
                    ++panFails;
                }
            }
            else
            {
                try
                {
                    pd.UpdatePanel(panel);
                    ++panUps;
                }
                catch (Exception rsp1)
                {
                    System.out.println("Panel " + tp.getGroupNumber() + ":" + tp.getSubtestNumber() + "(" + count + ") " + rsp1.toString());
                    ++panFails;
                }
            }
        }

        System.out.println("---------------------------");
        System.out.println("Tests Inserted: " + inserts);
        System.out.println("Tests Updated: " + update);
        System.out.println("Tests Failures: " + failures);
        System.out.println("---------------------------");
        System.out.println("Panels Inserted: " + panIns);
        System.out.println("Panels Updated: " + panUps);
        System.out.println("Panels Failures: " + panFails);
        System.out.println("---------------------------");
        System.out.println("Bill Codes Inserted: " + billIns);
        System.out.println("Bill Codes Updated: " + billUps);
        System.out.println("Bill Codes Failures: " + billFails);
        System.out.println("---------------------------");
        System.out.println("MultiChoice Inserted: " + multIns);
        System.out.println("MultiChoice Updated: " + multUps);
        System.out.println("MultiChoice Failures: " + multFails);
        System.out.println("---------------------------");
        System.out.println("Unreadable Line: " + unreadableLines);
    }

    public Tests TestLine2Single(TestLine line)
    {
        try
        {
            Tests test = new Tests();

            test.setNumber(line.getTestNumber());
            test.setName(line.getTestName());
            test.setAbbr(line.getAbbreviation());
            test.setTestType(1);
            test.setResultType(line.getResultType());
            test.setLowNormal(line.getNormalLow());
            test.setHighNormal(line.getNormalsHigh());
            test.setCriticalLow(line.getPanicLow());
            test.setCriticalHigh(line.getPanicHigh());
            test.setPrintNormals(line.getNormalRange());
            test.setUnits(line.getUnitOfMeasure());
            int GetRemarkID = rDAO.GetRemarkID(line.getTCDRemark());
            test.setRemark(GetRemarkID);
            test.setDepartment(line.getDepartment());
            test.setInstrument(line.getInstrument());
            test.setOnlineCode1(line.getOnlineCode());
            test.setDecimalPositions(line.getDecimalPosition());
            java.util.Date dt = new java.util.Date();
            test.setCreated(Convert.ToSQLDate(dt));

            //Set nulls to something
            test.setAlertLow(0);
            test.setAlertHigh(0);
            test.setSubtest(0);

            test.setPrintOrder(line.getPrintOrderFlag());
            test.setSpecimenType(1);
            test.setLoinc(null);
            
            test.setBillingOnly(false);
            test.setLabelPrint(true);
            test.setTubeTypeId(null);
            //test.setTubeType(null);
            test.setHeaderPrint(false);
            test.setActive(true);
            test.setDeactivatedDate(null);
            test.setTestComment(null);
            test.setExtraNormals(false); // Load Extra Normals afterwards
            test.setAOE(false);
            test.setCycles(line.getCycles());
            test.setStat(false);
            test.setHeader(false);
            try
            {
                Integer Nstat = Integer.parseInt(line.getStat());
                char Cstat = (char) Nstat.intValue();
                test.setStat((Character.isWhitespace(Cstat) == false ? true : false));
            }
            catch (NumberFormatException numberFormatException)
            {
                test.setStat(false);
            }

            return test;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Tests TestLine2Composite(TestLine line)
    {
        try
        {
            Tests test = new Tests();

            test.setNumber(line.getTestNumber());
            test.setName(line.getTestName());
            test.setAbbr(line.getAbbreviation());
            test.setTestType(0);
            test.setResultType("Panel Header");
            test.setUnits(line.getUnitOfMeasure());
            int GetRemarkID = rDAO.GetRemarkID(line.getTCDRemark());
            test.setRemark(GetRemarkID);
            test.setDepartment(line.getDepartment());
            test.setInstrument(line.getInstrument());
            test.setOnlineCode1(line.getOnlineCode());
            java.util.Date dt = new java.util.Date();
            test.setCreated(Convert.ToSQLDate(dt));

            //Set nulls to something
            test.setAlertLow(0);
            test.setAlertHigh(0);
            test.setLowNormal(0);
            test.setHighNormal(0);
            test.setCriticalLow(0);
            test.setCriticalHigh(0);
            test.setSubtest(0);

            test.setPrintOrder(line.getPrintOrderFlag());
            test.setSpecimenType(1);
            test.setLoinc(null);
            
            test.setBillingOnly(false);
            test.setLabelPrint(true);
            test.setTubeTypeId(null);
            //test.setTubeType(null);
            test.setHeaderPrint(false);
            test.setActive(true);
            test.setDeactivatedDate(null);
            test.setTestComment(null);
            test.setExtraNormals(false);
            test.setAOE(false);
            test.setCycles(line.getCycles());
            test.setStat(false);
            test.setHeader(false);
            try
            {
                Integer Nstat = Integer.parseInt(line.getStat());
                char Cstat = (char) Nstat.intValue();
                test.setStat((Character.isWhitespace(Cstat) == false ? true : false));
            }
            catch (NumberFormatException numberFormatException)
            {
                test.setStat(false);
            }

            return test;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    int newNum = 4000;
    public Tests TestLine2Subtest(TestLine line)
    {
        try
        {
            TestDAO tDAO = new TestDAO();
            Tests test = new Tests();
            
            while(tDAO.GetTestID(newNum) != 0)
            {
                ++newNum;
            }

            test.setNumber(newNum);
            test.setSubtest(0);
            test.setName(line.getTestName());
            test.setAbbr(line.getAbbreviation());
            test.setTestType(1);
            test.setResultType(line.getResultType());
            test.setLowNormal(line.getNormalLow());
            test.setHighNormal(line.getNormalsHigh());
            test.setCriticalLow(line.getPanicLow());
            test.setCriticalHigh(line.getPanicHigh());
            test.setPrintNormals(line.getNormalRange());
            test.setUnits(line.getUnitOfMeasure());
            int GetRemarkID = rDAO.GetRemarkID(line.getTCDRemark());
            test.setRemark(GetRemarkID);
            test.setDepartment(line.getDepartment());
            test.setInstrument(line.getInstrument());
            test.setOnlineCode1(line.getOnlineCode());
            test.setDecimalPositions(line.getDecimalPosition());
            test.setSubtest(line.getSubtestNumber());
            java.util.Date dt = new java.util.Date();
            test.setCreated(Convert.ToSQLDate(dt));

            //Set nulls to something
            test.setAlertLow(0);
            test.setAlertHigh(0);

            test.setPrintOrder(line.getPrintOrderFlag());
            test.setSpecimenType(1);
            test.setLoinc(null);
            
            test.setBillingOnly(false);
            test.setLabelPrint(true);
            test.setTubeTypeId(null);
            //test.setTubeType(null);
            test.setHeaderPrint(false);
            test.setActive(true);
            test.setDeactivatedDate(null);
            test.setTestComment(null);
            test.setExtraNormals(false);
            test.setAOE(false);
            test.setCycles(line.getCycles());
            test.setStat(false);
            test.setHeader(false);
            try
            {
                Integer Nstat = Integer.parseInt(line.getStat());
                char Cstat = (char) Nstat.intValue();
                test.setStat((Character.isWhitespace(Cstat) == false ? true : false));
            }
            catch (NumberFormatException numberFormatException)
            {
                test.setStat(false);
            }
            ++newNum; // Go to the next, save an iteration in the while-loop.
            return test;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Multichoice TestLine2Choice(TestLine line)
    {
        try
        {
            Multichoice mc = null;
            Tests test = new TestDAO().GetTestByNumber(line.getTestNumber(), line.getSubtestNumber());
            if (test != null)
            {
                mc = new Multichoice();
                mc.setTestId(test.getIdtests());
                mc.setChoice(line.getTestName());
                mc.setIsAbnormal(false);
                mc.setChoiceOrder(line.getResno());
            }
            return mc;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public int[] GetGroupTests(String grp)
    {
        String[] temp;
        String delimiter = ":";

        temp = grp.split(delimiter);
        int[] tests = new int[temp.length];

        for (int i = 0; i < temp.length; i++)
        {
            tests[i] = Integer.parseInt(temp[i]);
        }

        return tests;
    }

    public Panels Temp2Panel(TempPanel tPanel) throws SQLException
    {
        TestDAO td = new TestDAO();
        Panels p = new Panels();
        p.setIdpanels(td.GetTestID(tPanel.GroupNumber));
        p.setSubtestId(td.GetTestID(tPanel.SubtestNumber));
        p.setSubtestOrder(tPanel.PrintOrder);

        return p;
    }

    //Temp Class to hold values to be used for group tests.
    public class TempPanel
    {

        public int GroupNumber;
        public int SubtestNumber;
        public int PrintOrder;

        public TempPanel()
        {
        }

        public TempPanel(int GroupNumber, int SubtestNumber, int PrintOrder)
        {
            this.GroupNumber = GroupNumber;
            this.SubtestNumber = SubtestNumber;
            this.PrintOrder = PrintOrder;
        }

        public int getGroupNumber()
        {
            return GroupNumber;
        }

        public void setGroupNumber(int GroupNumber)
        {
            this.GroupNumber = GroupNumber;
        }

        public int getPrintOrder()
        {
            return PrintOrder;
        }

        public void setPrintOrder(int PrintOrder)
        {
            this.PrintOrder = PrintOrder;
        }

        public int getSubtestNumber()
        {
            return SubtestNumber;
        }

        public void setSubtestNumber(int SubtestNumber)
        {
            this.SubtestNumber = SubtestNumber;
        }
        
        
    }
    
    

}
