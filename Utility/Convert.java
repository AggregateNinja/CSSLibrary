package Utility;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: Utility
 * @file name: Convert.java
 *
 * @Description: Contains static conversion methods for non-implicit conversions.
 *
 * Computer Service & Support, Inc.  All Rights Reserved
*/

public class Convert 
{
    
    private Convert() {}
    
    
    // <editor-fold defaultstate="collapsed" desc="SQL Date">
    //Converts java.util.date to java.sql.date
    public static java.sql.Date ToSQLDate(java.util.Date UtilDate)
    {
        if(UtilDate == null)
            return null;
        //java.sql.Date empty = new java.sql.Date("0002-11-30");
        java.sql.Date empty = java.sql.Date.valueOf("0002-11-30");
        java.sql.Date date = new java.sql.Date(UtilDate.getTime());
        if(date.toString().equals(empty.toString()))
            return null;
        else
            return date;
    }
    
    //Converts date string to Date object
    // - format used in CSV Imports: dd/MM/yyyy_HHmm
    public static java.sql.Date ToSQLDate(String value, String Format)
    {
        if(value == null)
            return null;
        try
        {
            DateFormat formatter = new SimpleDateFormat(Format);
            java.sql.Date date = (java.sql.Date)formatter.parse(value);
            return date;
        }
        catch (ParseException e)
        {
            String message = e.toString();
            System.out.print(message);
            return null;
        }
    }
    
    //Converts java.util.date with time to java.sql.TimeStamp
    public static java.sql.Timestamp ToSQLDateTime(java.util.Date UtilDate)
    {
        if(UtilDate == null)
            return null;
        java.sql.Timestamp  ts = new java.sql.Timestamp(UtilDate.getTime());
        return ts;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Util Date">
    //Converts date string to Date object
    // - format used in CSV Imports: dd/MM/yyyy_HHmm
    public static java.util.Date ToDate(String value, String Format)
    {
        if(value == null)
            return null;
        try
        {
            DateFormat formatter = new SimpleDateFormat(Format);
            java.util.Date date = (java.util.Date)formatter.parse(value);
            return date;
        }
        catch (ParseException e)
        {
            String message = e.toString();
            System.out.print(message);
            return null;
        }
    }
    
    //TODO Different Formats
    public static String FromDate(java.util.Date UtilDate)
    {
        if(UtilDate == null)
            return null;
        
        String date = UtilDate.toString();
        return date;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean">
    //Converts int value to true or false accordingly
    public static boolean ToBool(int x)
    {
        if(x == 0)
            return false;
        else
            return true;
    }
    
    //Converts boolean value to 1 or 0 accordingly.
    public static int FromBool(boolean x)
    {
        if(x)
            return 1;
        else
            return 0;
    }
    // </editor-fold>
    
    // <edit-fold defaultstate="collapsed" desc="SQL WHERE">
    public static String BetweenMonthWhere(int month, int year){
    
        String where = " BETWEEN '";
        
        switch(month){
            case 1:
                where += year+"-01-01 00:00:00' AND '"+year+"-01-31 23:59:59' ";
                break;
            case 2:
                where += year+"-02-01 00:00:00' AND '"+year+"-02-28 23:59:59' ";
                break;
            case 3:
                where += year+"-03-01 00:00:00' AND '"+year+"-03-31 23:59:59' ";
                break;
            case 4:
                where += year+"-04-01 00:00:00' AND '"+year+"-04-30 23:59:59' ";
                break;
            case 5:
                where += year+"-05-01 00:00:00' AND '"+year+"-05-31 23:59:59' ";
                break;
            case 6:
                where += year+"-06-01 00:00:00' AND '"+year+"-06-30 23:59:59' ";
                break;
            case 7:
                where += year+"-07-01 00:00:00' AND '"+year+"-07-31 23:59:59' ";
                break;
            case 8:
                where += year+"-08-01 00:00:00' AND '"+year+"-08-31 23:59:59' ";
                break;
            case 9:
                where += year+"-09-01 00:00:00' AND '"+year+"-09-30 23:59:59' ";
                break;
            case 10:
                where += year+"-10-01 00:00:00' AND '"+year+"-10-31 23:59:59' ";
                break;
            case 11:
                where += year+"-11-01 00:00:00' AND '"+year+"-11-30 23:59:59' ";
                break;
            case 12:
                where += year+"-12-01 00:00:00' AND '"+year+"-12-31 23:59:59' ";
        }
        
        return where;
    }
    // </editor-fold>
    
    public static double ToMoney(double x)
    {
        double d = x/100;
        DecimalFormat df = new DecimalFormat("###0.00");
        //System.out.println(df.format(d));
        String r = df.format(d);
        double s = Double.parseDouble(r);
        
        return s;
    }
    
    /**
     * Converts an ArrayList of bytes to a byte[].
     * Throws null pointer if passed a null ArrayList
     * @param in List<byte>
     * @return byte[] equal to the List passed in
     */
    public static byte[] ToByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    /**
     * Converts byte[] to a List<byte>.
     * @param in byte[]
     * @return List<bytes>
     */
    public static List<Byte> ToByteList(Byte[] in){
        List<Byte> list = Arrays.asList(in);
        return list;
    }
    
    /**
     * Converts a primitive byte[] to an array of ByteObjects, Byte[].
     * @param prim byte[]
     * @return Byte[]
     */
    public static Byte[] ByteFromPrimitive(byte[] prim){
        Byte[] byteObjects = new Byte[prim.length];
        int i = 0;
        for (byte b : prim){
            byteObjects[i] = b;
            i++;
        }
        return byteObjects;
    }
    
    /**
     * Converts Byte[] objects to primitive byte[].
     * @param obj Byte[]
     * @return byte[]
     */
    public static byte[] PrimitiveFromByte(Byte[] obj){
        byte[] bytes = new byte[obj.length];
        int i = 0;
        for(Byte b : obj){
            bytes[i] = b.byteValue();
            i++;
        }
        return bytes;
    }
    
    /**
     * Converts a decimal value in the form of an int and converts it to a
     * String representation of it's HEX value.  This is different than the
     * standard Integer.toHexString() method in that it adds a
     * preceeding 0 if needed, and capitalizes the alphabetic characters.
     * @param dec int
     * @return String
     */
    public static String IntToHex(int dec){
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(dec));
        if (sb.length() < 2) {
            sb.insert(0, '0'); // pad with leading zero if needed
        }
        String hex = sb.toString().toUpperCase();
        
        return hex;
    }
}
