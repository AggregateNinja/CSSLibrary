/*
 * Computer Service & Support, Inc.  All Rights Reserved Sep 2, 2014
 */

package Utility;

import Utility.WriteTextFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

/**
 * @date:   Sep 2, 2014  11:37:38 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: SupportSuite
 * @file name: CMDBackups.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class CMDBackups {
    
    /**
     * Backs Up the entire css Database in SQL using mysqldump.
     * @param log WriteTextFile, this must be logged using the 
     * text writer.
     */
    public static void CompleteBackup(WriteTextFile log){
        
        String s = null;
        
        try{
            java.util.Date date = new java.util.Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmm");
            String path = "/usr/hybrid/utils/DB_Backups/";
            String name = "CSSDB" + formatter.format(date) + ".sql.gz";
            
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c","mysqldump -u backupuser"
                    + " --single-transaction --quick --skip-triggers --force css "
                    + " | gzip > " + path + name});
            
            //BufferedReader stdInput = new BufferedReader(new
            //     InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
            
            // read the output from the command
            //log.write("Here is the standard output of the command:\n");
            //while ((s = stdInput.readLine()) != null) {
            //    log.write(s);
            //}
            
             // read any errors from the attempted command
            log.write("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                log.write(s);
            }
            
        }catch(IOException ex){
            System.out.println("Exception backing up complete DB: "+ex.toString());
            log.write("Exception backing up complete DB: "+ex.toString());
        }
    }

    /**
     * Backs up an individual table from the css Database.
     * @param table String name of the table
     * @param log WriteTextFile, this must be logged
     */
    public static void BackUpTable(String table, WriteTextFile log){
        
        String s = null;
        
        try{
            java.util.Date date = new java.util.Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmm");
            String path = "/usr/hybrid/utils/DB_Backups/";
            String name = table.trim().toUpperCase() + formatter.format(date) + ".sql.gz";
            
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c","mysqldump -u backupuser"
                    + " --single-transaction --quick --skip-triggers --force "
                    + " css " + table + " | gzip > " + path + name});
            
            //BufferedReader stdInput = new BufferedReader(new
            //     InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
            
            // read the output from the command
            //log.write("Here is the standard output of the command:\n");
            //while ((s = stdInput.readLine()) != null) {
            //    log.write(s);
            //}
            
             // read any errors from the attempted command
            log.write("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                log.write(s);
            }
            
        }catch(IOException ex){
            System.out.println("Exception backing up complete DB: "+ex.toString());
            log.write("Exception backing up complete DB: "+ex.toString());
        }
    }
}
