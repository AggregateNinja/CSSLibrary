/*
 * CSS, Inc. 2014
 */

package InstrumentIO;

/**
 * Purpose: This houses any and all instrument check sums.  If adding
 *          a new check sum method or algorithm, please make sure o
 *          comment on the instrument/software it is connecting to.
 * 
 * @author Ryan Piper
 * Date: 04/30/2014
 */
public class CheckSums {
    
    /**
     * ASTM E 1391-94 Check Sum Algorithm b.
     * Used by Instrument Manager by Roche(Originally programmed for)
     * @param bytes byte[] message needing checksum
     * @param ctrlCharCount int number of control characters in bytes(ACK,NAK,STX...)
     * @return int Check Sum
     */
    public static int AlgorithmB(byte[] bytes, int ctrlCharCount){
        //Example:
        //CS = (Byte2 + Byte3 + ... + ByteLength-4) modulo 256
        
        int checksum = 0;
        int v_length = bytes.length - ctrlCharCount;
       
        for(int x = 1; x < v_length; x++){
            checksum = checksum + bytes[x];
        }
        
        //checksum = checksum + v_length;
        
        checksum = checksum % 256;
        
        return checksum;
    }
    
    /**
     * Creates the CheckSum for the OlympusAU460 for responses
     * to a query in Bi-Directional Interface
     * @param bytes byte[] of Response
     * @return int value of the checksum
     */
    public static int OlympusAU640(byte[] bytes){
        
        int checksum = 0;
        int v_length = bytes.length;
        
        //Skip the 1st and last bytes. STX and ETX
        //Doesn't include Control Characters
        
        for(int x = 1; x < v_length; x++){
            checksum = checksum ^ bytes[x];
        }
        
        return  checksum;
    }
    
    /**
     * Creates the CheckSum for the DimensionRL responses
     * @param bytes byte array message that is being sent
     * @return int value of check sum
     */
    public static int DimensionRL(byte[] bytes){
        int cs = 0;
        int v_length = bytes.length;
        
        for(int x = 1; x < v_length; x++){
            cs += bytes[x] & 0x7f;
            cs &= 0xff;
        }
        
        return cs;
    }
}
