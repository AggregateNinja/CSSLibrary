
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
import DOS.Species;
import DAOS.SpeciesDAO;
import EPOS.SpeciesLine;

public class SpeciesDL {
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        SpeciesDAO rd = new SpeciesDAO();
        
        Species species;
        SpeciesLine line;
        String[] values;
        int x;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            try
            {
                line = new SpeciesLine();
                x = 0;
                count++;
                
                //line 0
                line.setNumber(Integer.parseInt(values[x]));
                x++;
                line.setName(values[x]);
                
                try
                {
                    species = EPO2DO(line);
                    
                    try
                    {
                        rd.InsertSpecies(species);
                        inserts++;
                    }catch(Exception e2)
                    {
                        rd.UpdateSpecies(species);
                        update++;
                    }
                }catch(Exception e3)
                {
                    failures++;
                }
            }catch(Exception ex)
            {
                System.out.println(ex.toString());
                unreadableLines++;
            } 
        }
        
        System.out.println("Inserts: " + inserts);
        System.out.println("Updates: " + update);
        System.out.println("Failures: " + failures);
        System.out.println("Unreadable Lines: " + unreadableLines);
    }
    
    public Species EPO2DO(SpeciesLine line)
    {
        try
        {
            Species species = new Species();
            
            species.setIdspecies(line.getNumber());
            species.setName(line.getName());
            
            return species;
        }catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }
}
