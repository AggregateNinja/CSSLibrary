package DAOS.IDAOS;

import DAOS.*;
import DOS.Subscriber;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.*;

/**
 * @date:   Mar 12, 2012
 * @author: CSS Dev
 * 
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public interface ISubscriberDAO 
{
    public boolean InsertSubscriber(Subscriber subscriber) throws SQLException;
    
    public boolean UpdateSubscriber(Subscriber subscriber) throws SQLException;
    
    public Subscriber GetSubscriber(String MasterNumber) throws SQLException;
    
    public Subscriber GetSubscriberById(int Id) throws SQLException;
    
    public int GetLastInsertedID() throws SQLException;
    
    //Only to be used during bulk import
    public int GetSubscriberIdByAR(String ar);
    
    public boolean SubscriberExists(String ar);
    
    public ResultSet GetResultSetByQuery(String Select, String Where);
}
