package DAOS.IDAOS;

import DAOS.LabMasterDAO;
import DAOS.OrderDAO;
import DAOS.PatientDAO;
import DAOS.PreferencesDAO;
import DAOS.PrescriptionDAO;
import DAOS.RemarkDAO;
import DAOS.ReportTypeDAO;
import DAOS.ResultDAO;
import DAOS.SubscriberDAO;
import DAOS.TestDAO;

/**
 * @date:   Mar 3, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS.IDAOS
 * @file name: DAOFactory.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class DAOFactory 
{
    public enum TableList
    {
        TESTS("tests"),
        ORDERS("orders"),
        REMARKS("remarks"),
        RESULTS("results"),
        REPORTTYPE("reportType"),
        PATIENTS("patients"),
        SUBSCRIBER("Subscriber"),
        PREFERENCES("preferences"),
        PRESCRIPTIONS("prescriptions"),
        LABMASTER("labMaster")
        ;
        
        String table;
        
        TableList(String str)
        {
            this.table = str;
        }
        
        public boolean equals(String other)
        {
            return table.equals(other);
        }
    }
    
    public static DAOInterface GetDAO(TableList table)
    {
        switch(table)
        {
            case TESTS:
                return new TestDAO();
            case ORDERS:
                return new OrderDAO();
            case REMARKS:
                return new RemarkDAO();
            case RESULTS:
                return new ResultDAO();
            case REPORTTYPE:
                return new ReportTypeDAO();
            case PATIENTS:
                return new PatientDAO();
            case SUBSCRIBER:
                return new SubscriberDAO();
            case PREFERENCES:
                return new PreferencesDAO();
            case PRESCRIPTIONS:
                return new PrescriptionDAO();
            case LABMASTER:
                return new LabMasterDAO();
            default:
                throw new AssertionError(table.name());
        }
    }
}
