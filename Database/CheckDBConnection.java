/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package Database;

import Billing.Database.BillingDatabaseSingleton;
import EMR.Database.EMRDatabaseSingleton;
import Web.Database.WebDatabaseSingleton;
import java.sql.Connection;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 02/11/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class CheckDBConnection {
    
    public static Connection Check(DatabaseSingleton dbs, Connection con)
    {
        try {
            dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            con = dbs.getConnection(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return con;
    }
    
    public static Connection Check(EMRDatabaseSingleton dbs, Connection con)
    {
        try {
            dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
            con = dbs.getConnection(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return con;
    }
    
    public static Connection Check(WebDatabaseSingleton dbs, Connection con)
    {
        try {
            dbs = Web.Database.WebDatabaseSingleton.getDatabaseSingleton();
            con = dbs.getConnection(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return con;
    }
    
    public static Connection Check(BillingDatabaseSingleton dbs, Connection con)
    {
        try {
            dbs = Billing.Database.BillingDatabaseSingleton.getDatabaseSingleton();
            con = dbs.getConnection(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return con;
    }
}
