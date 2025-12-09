/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package Utility;

import DAOS.GroupPoliciesDAO;
import DAOS.SysOpDAO;
import DAOS.UserDAO;
import DOS.GroupPolicies;
import DOS.Users;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;
//In JDK8 this will be replaced by java.util.Base64
import sun.misc.BASE64Encoder;


/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 05/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class UserValidation {

    public static int ValidateLogin(String name, char[] pass) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        UserDAO udao = new UserDAO();
        Users user = udao.GetUserByLogon(name);
        
        if(user.getIdUser() == null)
        {
            return 5000;
        }
        
        String p = new String(pass);
        String p2 = Encrypt(p);
        
        if(user.getPassword().equals(p2))
        {
            return user.getIdUser();
        }else
        {
            return 6000;
        }
    }
    public static Users ValidateLogonOLD() throws SQLException 
    {
        Preferences prefs = Preferences.systemRoot();
        UserDAO dao = new UserDAO();
        String pUser = prefs.get("techuser", "");
        String pPass = prefs.get("techpass", "");
        Users user = dao.GetUserByLogon(pUser);
        //Users user = dao.GetUserByID(id);
        
        
        if (user.getLogon().equals(pUser)) 
        {
            char[] passFromPref = pPass.toCharArray();
            char[] passFromDB = user.getPassword().toCharArray();

            int correctCount = 0;

            if (passFromPref.length != passFromDB.length) 
            {
                return null;
            }
            
            for (int i = 0; i < passFromPref.length; i++) 
            {
                if (passFromPref[i] == passFromDB[i]) 
                {
                    correctCount++;
                }
            }
            
            if (passFromPref.length == correctCount) 
            {
                return user;
            }
            else
            {
                return null;
            }
        } 
        else 
        {
            return null;
        }
     
    }
    
    //Gets the usergroup of the user, then checks if the user group is allowed
    //access to the module;
    public static boolean AllowedAccess(int id, String module) throws SQLException
    {
        boolean allowed = false;
        UserDAO uDAO = new UserDAO();
        Users user = uDAO.GetUserByID(id);
        //String gname;
        
        GroupPoliciesDAO gpDAO = new GroupPoliciesDAO();
        GroupPolicies gp = gpDAO.GetGroupPolicyByModule(module);
        
        switch(user.getUGroups())
        {
            case 1:
                //gname = "group1";
                //administrators
                if(gp.getGroup1())
                    allowed = true;
                break;
            case 2:
                //gname = "group2";
                if(gp.getGroup2())
                    allowed = true;
                break;
            case 3:
                //gname = "group3";
                if(gp.getGroup3())
                    allowed = true;
                break;
            case 4:
                //gname = "group4";
                if(gp.getGroup4())
                    allowed = true;
                break;
            case 5:
                //gname = "group5";
                if(gp.getGroup5())
                    allowed = true;
                break;
            case 6:
                //gname = "group6";
                if(gp.getGroup6())
                    allowed = true;
                break;
            case 7:
                //gname = "group7";
                if(gp.getGroup7())
                    allowed = true;
                break;
            case 8:
                //gname = "group8";
                if(gp.getGroup8())
                    allowed = true;
                break;
            case 9:
                //gname = "group9";
                if(gp.getGroup9())
                    allowed = true;
                break;
            case 10:
                //gname = "group10";
                if(gp.getGroup10())
                    allowed = true;
                break;
        }
        
        return allowed;
    }
    
    private static String byteArrToString(byte[] b) 
    {
        String res = null;
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) 
        {
            int j = b[i] & 0xff;
            if (j < 16) 
            {
                sb.append('0');
            }
            sb.append(Integer.toHexString(j));
        }
        res = sb.toString();
        return res.toUpperCase();
    }
    
    //Password Encryption
    public static String Encrypt(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA"); 
        md.update(plaintext.getBytes("UTF-8")); 
        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw); 
        return hash; 
    }
    
    //Pass in a String and it returns the MD5 encrypted version.
    private static String EncryptPassword(String pass)
    {
        try
        {
            String passwordMD5 = null;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] tmp = pass.getBytes();
            md5.update(tmp);
            passwordMD5 = byteArrToString(md5.digest());
            
            return passwordMD5;
        }catch(NoSuchAlgorithmException ax){
            System.out.println(ax.toString());
            return null;
        }
    }
    
    /**
     * Return the user Ids of all superusers AKA CSS administrators. Searches
     *  the Sys_Op settings first ("SuperUserIds"), but will default to "admin"
     *  if that setting is not present.
     * @return 
     */
    public static List<Integer> getSuperuserIds()
    {
        List<Integer> superUserIds = new LinkedList<>();
        // check the sys_op setting for the superuser id
        SysOpDAO sodao = new SysOpDAO();
        try
        {
            // CSV identifiers
            String superUserIdsStr = sodao.getString("SuperUserIds");
            if (superUserIdsStr != null)
            {
                String[] superUserIdsArray = superUserIdsStr.split(",");
                
                for (String superUserIdStr : superUserIdsArray)
                {
                    if (superUserIdStr != null)
                    {
                        try
                        {
                            Integer superUserId = Integer.valueOf(superUserIdStr.trim());
                            superUserIds.add(superUserId);
                            //if (superUserId.equals(userId)) return true;
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Can't parse out user id from " + superUserIdStr + " to determine whether user is a super user Id");
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
           System.out.println("The superuser identifier is not set; checking for 'admin'");
        }
        
        if (superUserIds.size() > 0) return superUserIds;
        
        // otherwise see if the userId is our 'admin' account
        UserDAO udao = new UserDAO();
        try
        {
            Users user = udao.GetUserByLogon("admin");
            if (user != null && user.getIdUser() != null && user.getIdUser() > 0)
            {
                superUserIds.add(user.getIdUser());
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Cannot obtain user for provided id");
        }
        
        return superUserIds;
    }
    
    /**
     * Superuser is the CSS administrator login that will grant privileges that
     *  users should not control, like logging users out. Default is 'admin', but
     *  the presence of a Sys_Op for SuperUserId can change the superuser account.
     * @param userId
     * @return 
     */
    public static boolean isSuperuser(int userId)
    {
        List<Integer> superUserIds = getSuperuserIds();
        for (Integer superUserId : superUserIds)
        {
            if (superUserId.equals(userId)) return true;
        }
        return false;
    }
}
