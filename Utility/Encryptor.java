
package Utility;

import Database.DatabaseProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Encoder.*;

/**
 *
 * @author Ryan
 */
public class Encryptor {
    
    /**
     * Will encrypt or decrypt a file with default key
     * @param file String containing complete path of file with file name
     * @param encrypt Boolean value. true to encrypt, false decrypt 
     */
    public static void EncryptFile(String file, boolean encrypt) {
        if(encrypt){
            encryptFile(file, "");
        }else{
            decryptFile(file, "");
        }
    } 
    
    /**
     * Will encrypt or decrypt a file with custom key
     * @param file String containing complete path of file with file name
     * @param key Encryption key to be used
     * @param encrypt Boolean value. true to encrypt, false decrypt 
     */
    public static void EncryptFile(String file, String key, boolean encrypt){
        if(encrypt){
            encryptFile(file, key);
        }else{
            decryptFile(file, key);
        }
    }
    
    /**
     * Encrypt a String with the default key
     * @param val String to encrypt or decrypt
     * @param encrypt Boolean value. true to encrypt, false decrypt
     */
    public static String EncryptString(String val, boolean encrypt){
        if(encrypt){
            return encryptString(val, "");
        }else{
            return decryptString(val, "");
        }
    }
    
    /**
     * Encrypt a String with custom key. Key must be at least 8 bytes of
     * data
     * @param val String to encrypt or decrypt
     * @param key Encryption key to be used, at least 8 bytes
     * @param encrypt Boolean value. true to encrypt, false decrypt
     */
    public static String EncryptString(String val, String key, boolean encrypt){
        if(encrypt){
            return encryptString(val, key);
        }else{
            return decryptString(val, key);
        }
    }
    
    /**
     * Private method for encrypting files. Will use default key if
     * passed in key is empty.
     * @param file String value of complete path to file
     * @param key Encryption key to be used, if empty then default key is used.
     * @return true is successful, false if an exception is caught
     */
    private static boolean encryptFile(String file, String key){
        try {
            FileInputStream inFile = new FileInputStream(file);
            FileOutputStream outFile = new FileOutputStream(file + ".des");

            //If key is passed, use it instead of default
            if(key == null || key.isEmpty()){
                key = DatabaseProperties.getKey();
            }

            PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
            SecretKeyFactory keyFactory
                    = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey passwordKey = keyFactory.generateSecret(keySpec);
            
            byte[] salt = new byte[8];
            Random rnd = new Random();
            rnd.nextBytes(salt);
            int iterations = 100;

            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.ENCRYPT_MODE, passwordKey, parameterSpec);
            
            outFile.write(salt);

            byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = inFile.read(input)) != -1)
            {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) outFile.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null) outFile.write(output);

            inFile.close();
            outFile.flush();
            outFile.close();
            
            //Delete Non-Ecrypted original file
            //Declare File Object
            File f = new File(file);
            
            // Make sure the file or directory exists and isn't write protected
            if (!f.exists()){
               System.out.println("Delete: no such file or directory: " + file);
            }else if (!f.canWrite()){//Make sure you have write access
                System.out.println("Delete: write protected: "
                    + file);
            }else if (!f.delete()){//Finally try the delete.
                System.out.println("Delete: deletion failed");
            }
            
            return true;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Private method for decrypting files. Will use the default key
     * if key passed is empty
     * @param file String value of complete path to file
     * @param key Encryption key to be used, if empty then default key is used.
     * @return true is successful, false if an exception is caught 
     */
    private static boolean decryptFile(String file, String key){
        try{
            FileInputStream inFile = new FileInputStream(file);
            FileOutputStream outFile = new FileOutputStream((String)file.replace(".des", ""));

            //If key is passed, use it instead of default
            if(key == null || key.isEmpty()){
                key = DatabaseProperties.getKey();
            }

            PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
            SecretKeyFactory keyFactory
                    = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey passwordKey = keyFactory.generateSecret(keySpec);
            
            byte[] salt = new byte[8];
            inFile.read(salt);
            int iterations = 100;
            
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.DECRYPT_MODE, passwordKey, parameterSpec);
 
            byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = inFile.read(input)) != -1)
            {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    outFile.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                outFile.write(output);

            inFile.close();
            outFile.flush();
            outFile.close();
            
            return true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Private method for encrypting Strings. Will use the default key
     * if key passed is empty
     * @param str String to be encrypted.
     * @param key Encryption key to be used, if empty then default key is used.
     * @return Encrypted String.
     */
    private static String encryptString(String str, String key){
        try{
            
            while(str.length()%8 != 0)
            {
                str += ' ';
            }
            
            byte[] utf8 = str.getBytes("UTF8");
            
            //If key is passed, use it instead of default
            if(key == null || key.isEmpty()){
                key = DatabaseProperties.getKey();
            }
            
            byte[] k = key.getBytes(Charset.forName("UTF8"));
            DESKeySpec keySpec = new DESKeySpec(k);
            SecretKeyFactory keyFactory
                    = SecretKeyFactory.getInstance("DES");
            SecretKey passwordKey = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, passwordKey);
            
            byte[] enc = cipher.doFinal(utf8);
            
            return new sun.misc.BASE64Encoder().encode(enc);
   
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Private method for decrypting Strings.
     * @param str String to be decrypted.
     * @param key Encryption key to be used, if empty then default key is used.
     * @return Decrypted String.
     */
    private static String decryptString(String str, String key){
        try{
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            
            //If key is passed, use it instead of default
            if(key == null || key.isEmpty()){
                key = DatabaseProperties.getKey();
            }
            
            byte[] k = key.getBytes(Charset.forName("UTF8"));
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory
                    = SecretKeyFactory.getInstance("DES");
            SecretKey passwordKey = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, passwordKey);
            
            byte[] utf8 = cipher.doFinal(dec);
            
            return new String(utf8, "UTF8").trim();
   
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    private static byte[] combineByteArrays(byte[] one, byte[] two){
        byte[] combined = new byte[one.length + two.length];
        
        for(int i = 0; i < combined.length; ++i){
            combined[i] = i < one.length ? one[i] : two[i - one.length];
        }
        
        return combined;
    }
}
