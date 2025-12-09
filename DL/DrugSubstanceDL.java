package DL;

import DAOS.DrugDAO;
import DAOS.SubstanceDAO;
import DOS.Drugs;
import DOS.Substances;
import EPOS.DrugSubstanceLine;
import Utility.Convert;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @date:   Jun 18, 2013
 * @author: CSS Dev
 * 
 * @project: CSSLibrary 
 * @package: DL
 * @file name: DrugSubstanceDL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class DrugSubstanceDL 
{
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        DrugDAO drugDAO = new DrugDAO();
        SubstanceDAO substanceDAO = new SubstanceDAO();
        
        Drugs drug;
        Substances substance;
        DrugSubstanceLine line;
        String[] values;
        int x;
        
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '\"', 1);
        
        while((values = reader.readNext()) != null)
        {
            try{
                line = new DrugSubstanceLine();
                x = 0;
                count++;
                
                line.setIndex(Integer.parseInt(values[x]));
                ++x;
                line.setGenericName(values[x].trim());
                ++x;
                line.setSubstance(values[x].trim());
                
                Integer sid = null;
                if(line.getSubstance().isEmpty() == false && 
                   substanceDAO.SubstanceExists(line.getSubstance()) == true)
                {
                    sid = substanceDAO.GetSubstanceId(line.getSubstance());
                }
                else if(line.getSubstance().isEmpty() == false && 
                   substanceDAO.SubstanceExists(line.getSubstance()) == false)
                {
                    substance = new Substances();
                    substance.setSubstance(line.getSubstance());
                    boolean InsertSubstance = substanceDAO.InsertSubstance(substance);
                    if(InsertSubstance == true)
                    {
                        sid = substanceDAO.GetSubstanceId(line.getSubstance());
                    }
                    else
                    {
                        System.out.println("Drug " + line.getIndex() + " [" + line.getGenericName() + "] Cound not be assigned substance " + line.getSubstance());
                    }
                }
                
                try
                {
                    drug = EPO2DO(line, sid);
                    drugDAO.InsertDrugs(drug);
                    inserts++;
                }
                catch(Exception ex1)
                {
                    try
                    {
                        System.out.println(ex1.toString());
                        drug = EPO2DO(line, sid);
                        drugDAO.InsertDrugs(drug);
                        update++;
                    }
                    catch(Exception ex2)
                    {
                        System.out.println(ex2.toString());
                        failures++;
                    }
                }
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                unreadableLines++;
            }
        }
        
    }
    
    //Converts the DrugSubstanceDL EPO to the DrugSubstanceDL DO
    public Drugs EPO2DO(DrugSubstanceLine line, Integer SubstanceId)
    {
        try{
        Drugs drug = new Drugs();
        
        drug.setGenericName(line.getGenericName());
        drug.setSubstance1(SubstanceId);
        
        return drug;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
    
}
