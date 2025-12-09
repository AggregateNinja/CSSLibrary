/*
 * Computer Service & Support, Inc.  All Rights Reserved Nov 26, 2014
 */
package Utility;

import java.util.Random;

/**
 * @date: Nov 26, 2014 11:08:52 AM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: RandomsGenerator.java (UTF-8)
 *
 * @Description:
 *
 */
public class RandomsGenerator {

    /**
     * Generates a Random int between the min and max values
     * @param min int value that the random number needs to be greater than
     * @param max int value that the random number needs to be les than
     * @return Random int between the min and max values
     */
    public static int getRandomNumberBetween(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        if (randomNumber == min) {
            // Since the random number is between the min and max values, add 1
            return min + 1;
        } else {
            return randomNumber;
        }
    }
    
}
