package DAOS.IDAOS;

import DAOS.*;
import DOS.Prescriptions;
import java.sql.*;
import java.util.ArrayList;

/**
 * @date:   Jun 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Prescription.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */


public interface IPrescriptionDAO 
{
    public boolean InsertPrescription(Prescriptions prescription) throws SQLException;
    
    public boolean UpdatePrescription(Prescriptions prescription) throws SQLException;
    
    public Prescriptions GetPrescription(int Id) throws SQLException;
    
    public boolean PrescriptionExists(int OrderId, int DrugId);
    
    public int GetPrescriptionId(int OrderId, int DrugId);
    
    public Prescriptions[] GetPrescriptionsByOrderId(int OrderId) throws SQLException;
}
