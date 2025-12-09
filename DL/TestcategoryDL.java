package DL;

import DAOS.TestDAO;
import DAOS.TestcategoryDAO;
import DOS.Testcategory;
import DOS.Tests;
import EPOS.TestcategoryLine;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

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
 * TestcategoryDL.java
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
public class TestcategoryDL
{

    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
                                                      ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;

        TestcategoryDAO rd = new TestcategoryDAO();

        Testcategory category;
        TestcategoryLine line;
        ArrayList<TestcategoryLine> categories = new ArrayList<TestcategoryLine>();
        String[] values;
        String LongName;
        int namebreak;
        int x;


        CSVReader reader = new CSVReader(new FileReader(filePath), '|', '\"', 1);

        while ((values = reader.readNext()) != null)
        {
            line = new TestcategoryLine();
            try
            {

                x = 0;
                count++;

                line.setNumber(Integer.parseInt(values[x]));
                ++x;
                line.setCategory(values[x]);

                if (categories.contains(line) == false)
                {
                    categories.add(line);
                }

            }
            catch (Exception ex)
            {
                System.out.println("Testcategory " + count + ": " + ex.toString());
                unreadableLines++;
            }
        }

        for (TestcategoryLine cat : categories)
        {
            category = EPO2DO(cat);
            if (category != null)
            {
                try
                {
                    if (!rd.TestCategoryExists(category.getLabel(), category.getTestId()))
                    {
                        rd.InsertTestCategory(category);
                        inserts++;
                    }
                    else
                    {
                        rd.UpdateTestCategory(category);
                        update++;
                    }

                }
                catch (Exception ex1)
                {
                    System.out.println("Testcategory " + category.getLabel() + ": " + ex1.toString());
                    failures++;
                }
            }
        }

    }

    //Converts the Remark EPO to the Remark DO
    public Testcategory EPO2DO(TestcategoryLine line)
    {
        try
        {
            Testcategory category = new Testcategory();

            
            category.setLabel(line.getCategory());
            /*if(line.getNumber() != 0)
             {
             // Not implemented in Map file yet.
             TestDAO tDAO = new TestDAO();
             Tests test = 
             }
             else*/
            {
                category.setTestId(null);
            }

            return category;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return null;
        }

    }
}
