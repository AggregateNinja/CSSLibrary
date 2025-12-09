/*
 * Computer Service & Support, Inc.  All Rights Reserved Mar 20, 2015
 */
package InstrumentIO;

import DAOS.QCInstDAO;
import DAOS.UserDAO;
import DOS.IDOS.BaseInstRes;
import DOS.Instruments;
import DOS.QcInst;
import DOS.Users;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @date: Mar 20, 2015 5:33:59 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: AxionProcessQC.java (UTF-8)
 *
 * @Description:
 *
 */
public class AxionProcessQC
{

    private Instruments INST;
    private Users USER;
    private int SPECIMEN_TYPE;

    /**
     * *******CONTROL REFERENCES*************
     */
    public final char lvl1 = 'A';
    public final char lvl2 = 'B';
    public final char lvl3 = 'C';
    public final char lvl4 = 'D';

    public AxionProcessQC(Instruments inst, Users user, String specimen)
    {
        INST = inst;
        USER = user;

        if (specimen == null)
        { //Default to Urine
            SPECIMEN_TYPE = 1;
        }
        else if (specimen.equalsIgnoreCase("Saliva"))
        {
            SPECIMEN_TYPE = 2;
        }
        else if (specimen.equalsIgnoreCase("Urine"))
        {
            SPECIMEN_TYPE = 1;
        }
    }

    public AxionProcessQC(Instruments inst, String specimen)
    {
        try
        {
            INST = inst;
            UserDAO udao = new UserDAO();
            Users u = udao.GetUserByID(2);
            USER = u;

            if (specimen == null)
            { //Default to Urine
                SPECIMEN_TYPE = 1;
            }
            else if (specimen.equalsIgnoreCase("Saliva"))
            {
                SPECIMEN_TYPE = 2;
            }
            else if (specimen.equalsIgnoreCase("Urine"))
            {
                SPECIMEN_TYPE = 1;
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void ProcessQC(File file)
    {
        QcInst qc = new QcInst();
        QCInstDAO dao = new QCInstDAO();
        String values[];
        java.util.Date date = new java.util.Date();
        int linesRead = 0;
        String qcId = "";
        int lvl = 0;

        try
        {
            CSVReader reader = new CSVReader(new FileReader(file), ',', '\"', 1);

            while ((values = reader.readNext()) != null)
            {
                linesRead++;

                String[] line = values[0].split("_");

                //Get the Acession or QC value
                qcId = line[2].trim();

                //Now check if it is a QC file or an Accession
                //If it is an Accession stop now
                if (IsQCData(qcId))
                {
                    lvl = GetQCLevel(qcId);
                }
                else
                { //Not QC file
                    return;
                }
                try
                {
                    qc = new QcInst();

                    qc.setName(values[1].trim());
                    qc.setLevel(lvl);
                    qc.setControl(line[2]);
                    qc.setResult(values[2].trim());
                    qc.setFileName(file.getName());
                    qc.setSpecimenType(SPECIMEN_TYPE);
                    qc.setCreatedDate(date);

                    try
                    {
                        dao.InsertQCData(INST.getQcTable(), qc);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("AxionProcessQC:: Could Not insert qcInst - " + ex.toString());
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("AxionProcessQC:: Could Not parse line - " + ex.toString());
                }
            }
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("AxionProcessQC:: File Not Found - " + fnfe.toString());
        }
        catch (IOException ex)
        {
            System.out.println("AxionProcessQC:: IO Exception - " + ex.toString());
        }
    }

    public boolean IsQCData(String str)
    {
        return str.startsWith("QC");
    }

    public int GetQCLevel(String str)
    {
        int level = 0;

        switch (str.charAt(str.length()-1))
        {
            case lvl1:
                level = 1;
                break;
            case lvl2:
                level = 2;
                break;
            case lvl3:
                level = 3;
                break;
            case lvl4:
                level = 4;
                break;
            default:
                break;
        }

        return level;
    }
}
