/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder.*;

/**
 *
 * @author rypip_000
 */
public class FileUtil {

    public static boolean moveFile(File current, File destination) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File afile = current;
            String fileName = current.getName();
            File bfile = new File(destination, fileName);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            //delete the original file
            afile.setWritable(true);
            afile.delete();

            return true;
        } catch (IOException e) {
            System.out.println("File not moved!\n" + e.toString());
            return false;
        }
    }

    /**
     * Encodes a file to Base64 encoded string
     *
     * @param fileName String of full path to File
     * @return String of base64encoded file
     * @throws IOException
     */
    public static String encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);
        return encodeFileToBase64Binary(file);
    }
    
    public static String encodeByteArrayToBase64Binary(byte[] bytes)
            throws IOException
    {
        byte[] encoded = Base64.encodeBase64(bytes);
        String encodedString = new String(encoded);
        return encodedString;
    }
    
    public static String encodeFileToBase64Binary(File file)
            throws IOException
    {
        byte[] bytes = loadFile(file);
        return encodeByteArrayToBase64Binary(bytes);
    }
    
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
    
    /**
     * Used to filter out directories when listing files from a path
     */
    public static class DirectoryFilter implements FileFilter {

        public boolean accept(File pathname) {
            return !pathname.isDirectory();
        }
    }
    
}
