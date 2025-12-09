package DL;

import DAOS.BillingTestCrossReferenceDAO;
import DAOS.TestDAO;
import DAOS.TestCatMapDAO;
import DAOS.TestcategoryDAO;
import DOS.BillingTestCrossReference;
import DOS.Testcategory;
import DOS.Testcatmap;
import DOS.Tests;
import EPOS.TestCatMapLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @date:
 * Mar
 * 13,
 * 2012
 * @author:
 * Keith
 * Maggio
 *
 * @project:
 * @package:
 * DL
 * @file
 * name:
 * TestcatmapDL.java
 *
 * @Description:
 *
 * Computer
 * Service
 * &
 * Support,
 * Inc.
 * All
 * Rights
 * Reserved
 */
public class TestCatMapDL
{

    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
                                                      ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;

        TestCatMapDAO rd = new TestCatMapDAO();

        Testcatmap entry;
        TestCatMapLine line;
        String[] values;
        String LongName;
        int namebreak;
        int x;


        CSVReader reader = new CSVReader(new FileReader(filePath), '|', '\"', 1);

        while ((values = reader.readNext()) != null)
        {
            line = new TestCatMapLine();
            try
            {

                x = 0;
                count++;

                line.setTestNumber(Integer.parseInt(values[x]));
                ++x;
                line.setSubtestNumber(Integer.parseInt(values[x]));
                ++x;
                line.setCategoryNumber(Integer.parseInt(values[x]));
                ++x;
                line.setPosition(Integer.parseInt(values[x]));
                ++x;
                line.setIndent(Integer.parseInt(values[x]));
                ++x;
                line.setCategory(values[x]);
                ++x;
                line.setTestName(values[x]);
                ++x;
                line.setMetabolite(values[x]);
                ++x;
                line.setText(values[x]);

                entry = EPO2DO(line);
                if (entry != null)
                {
                    try
                    {
                        if (!rd.TestCatMapExists(entry.getTestId()))
                        {
                            rd.InsertTestCatMap(entry);
                            inserts++;
                        }
                        else
                        {
                            entry.setIdtestcatmap(rd.GetTestCatMapId(entry.getTestId()));
                            rd.UpdateTestCatMap(entry);
                            update++;
                        }

                    }
                    catch (Exception ex1)
                    {
                        System.out.println("Error 1. Testcatmap " + count + ": " + ex1.toString());
                        failures++;
                    }
                }

            }
            catch (Exception ex)
            {
                System.out.println("Error 2. Testcatmap " + count + ": " + ex.toString());
                unreadableLines++;
            }
        }

    }

    //Converts the Remark EPO to the Remark DO
    public Testcatmap EPO2DO(TestCatMapLine line)
    {
        try
        {
            BillingTestCrossReferenceDAO btcrDAO = new BillingTestCrossReferenceDAO();
            Testcatmap catmapEntry = new Testcatmap();
            TestDAO tDAO = new TestDAO();
            int testId;
            if(line.getSubtestNumber() !=0)
            {
                BillingTestCrossReference btcr = btcrDAO.GetBillingTestCrossReference(line.getTestNumber(), line.getSubtestNumber());
                testId = btcr.getIdTests();
            }
            else
            {
                testId = tDAO.GetTestID(line.getTestNumber());
            }
            Tests test = tDAO.GetTestByID(testId);
            TestcategoryDAO catDAO = new TestcategoryDAO();
            Testcategory category = catDAO.GetTestCategory(line.getCategoryNumber());

            if (test != null && test.getIdtests() != 0 && 
                category != null && category.getIdtestcategory() != null)
            {
                catmapEntry.setTestId(test.getIdtests());
                catmapEntry.setTestcategoryId(category.getIdtestcategory());
                catmapEntry.setPosition(line.getPosition());
                catmapEntry.setIndent(line.getIndent());
                catmapEntry.setText(line.getText());

                return catmapEntry;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return null;
        }

    }
}
