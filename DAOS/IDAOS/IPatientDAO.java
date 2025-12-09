package DAOS.IDAOS;

import DAOS.*;
import DOS.Patients;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.*;
import java.util.Calendar;

/**
 * @date:   Mar 12, 2012
 * @author: CSS Dev
 * 
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: IPatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface IPatientDAO 
{
    public boolean InsertPatient(Patients patient) throws SQLException;
    
    public boolean UpdatePatient(Patients patient) throws SQLException;
    
    public Patients GetPatient(String MasterNumber) throws SQLException;
    
    public Patients GetPatientById(int Id) throws SQLException;
    
    public int GetPatientIdByAR(int ar);
    
    public int GetPatientIdByAR(String ar);
    
    public Patients GetTemporaryPatient(Date OrderDate, int Accession);
    
    public boolean PatientExistsByAr(int ar);
    
    public boolean PatientExistsByID(int id);
    
    public ResultSet GetResultSetByQuery(String Select, String Where);
    
    
}
