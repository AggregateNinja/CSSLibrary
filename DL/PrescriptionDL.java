/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DL;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/14/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import DAOS.*;
import DOS.*;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import EPOS.ArchivePrescriptionLine;
import EPOS.PrescriptionLine;
import Utility.Convert;
import java.util.ArrayList;

public class PrescriptionDL {
    
    public void ImportFromCSV(String filePath) throws FileNotFoundException, IOException,
            ParseException, ClassNotFoundException, SQLException
    {
        int count = 0;
        int inserts = 0;
        int update = 0;
        int failures = 0;
        int unreadableLines = 0;
        
        PrescriptionDAO pDAO = new PrescriptionDAO();
        
        PrescriptionLine line;
        String[] values;
        int x;
        
        CSVReader reader = new CSVReader(new FileReader(filePath), ',');
        
        while((values = reader.readNext()) != null)
        {
            line = new PrescriptionLine();
            x = 0;
            count++;
            System.out.println("Count: " + count + " " + values[1]);
            
            if (values.length == 5) 
            {
                try {
                    //Segment 1
                    line.setLastName(values[x]);
                    ++x;
                    //Segment 2
                    line.setAccession(values[x]); 
                    ++x;
                    //Segment 3
                    line.setOrderedDate(Convert.ToDate(values[x], "yyyy-MM-dd HH:mm:ss"));
                    ++x;
                    //Segment 4
                    line.setComment(values[x]);
                    ++x;
                    
                    try {
                        ArrayList<Prescriptions> plist = EPO2DO(line);
                        if(plist != null && plist.isEmpty() == false)
                        {
                            for(Prescriptions p : plist)
                            {
                                if(pDAO.PrescriptionExists(p.getOrderId(), p.getDrugId()) == false)
                                {
                                    pDAO.InsertPrescription(p);
                                    ++inserts;
                                }
                                else
                                {
                                    int ID = pDAO.GetPrescriptionId(p.getOrderId(), p.getDrugId());
                                    if(ID > 0)
                                    {
                                        p.setIdprescriptions(ID);
                                        pDAO.UpdatePrescription(p);
                                        ++update;
                                    }
                                    else
                                    {
                                        System.out.println("Could not retrieve ID for " + p.toString());
                                        ++failures;
                                    }
                                }
                            }
                        }
                    } 
                    catch (Exception ex1) 
                    {

                        ++failures;
                    }
                    
                } catch (Exception ex) {
                    System.out.println("Parse PrescriptionDL: Acc:" + line.getAccession() + " Count:Col: " + count + ":" + x);
                    System.out.println(ex.toString());
                    unreadableLines++;
                }
            }
        }
    }
    
    public String HandleQuotes(String str)
    {
        String x = str.replace('"', ' ');
        return x;
    }
    
    public Reporteddate CreateReportedDate(int id, java.util.Date date)
    {
        Reporteddate rep = new Reporteddate();
        rep.setIdreporteddate(id);
        rep.setDateReported(date);
        
        return rep;
    }
    
    public ArrayList<Prescriptions> EPO2DO(PrescriptionLine line) throws SQLException
    {
        OrderDAO oDAO = new OrderDAO();
        DrugDAO dDAO = new DrugDAO();
        ArrayList<Prescriptions> pList = new ArrayList<Prescriptions>();
        try{
        
        Orders order = oDAO.GetOrder(line.getAccession(), line.getOrderedDate());
        if(order == null || order.getIdOrders() == null)
        {
            System.out.println("Order " + line.getAccession() + " not found for " + line.toString());
            return pList;
        }
        String comment = line.getComment();
        if(comment.isEmpty() == false)
        {
            String[] drugs = comment.split(";");
            for(String d : drugs)
            {
                String generic = GetGenericDrugName(d.trim());
                Drugs drug = dDAO.GetDrugsByName(generic);
                if(drug != null && drug.getIddrugs() != null)
                {
                    Prescriptions prescription = new Prescriptions();
                    prescription.setOrderId(order.getIdOrders());
                    prescription.setDrugId(drug.getIddrugs());
                    pList.add(prescription);
                }
                else
                {
                    System.out.println("Drug " + d + " not found for " + line.toString());
                }
                
            }
        }
        
        return pList;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    
    private String GetGenericDrugName(String drug)
    {
        int end = drug.indexOf("(");
        if(end == -1)
        {
            return drug;
        }
        String ret = drug.substring(0, end);
        return ret;
    }
}
