package Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Simple File Writer Class for writing log from instrument interface.
 *
 * Date: 03/26/2014
 *
 * @author Ryan
 */
public class WriteTextFile
{

    private final String path;
    private boolean append = true;

    /**
     * Writes to file. If file exists it will over write
     *
     * @param filePath String of complete file path
     */
    public WriteTextFile(String filePath)
    {
        path = filePath;
    }

    /**
     * Writes to file. If file exists it will append to it as a new line
     *
     * @param filePath String value of text file's CanonicalPath.
     * @param appendValue Boolean value, True to append False to overwrite.
     */
    public WriteTextFile(String filePath, boolean appendValue)
    {
        path = filePath;
        append = appendValue;
    }

    /**
     * Does the actual writing to file. Behaves dependant upon constructor used.
     *
     * @param textLine String of text you wish to write.
     */
    public void write(String textLine)
    {
        Date date = new Date();
        String strDate = date.toString();
        try
        {
            FileWriter write = new FileWriter(path, append);
            PrintWriter print_line = new PrintWriter(write);

            print_line.printf(strDate + ": %s" + "%n", textLine);

            print_line.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    /**
     * Returns the path (including filename) to the WriteTextFile.
     * @return path to the WriteTextFile
     */
    public String getPath()
    {
        return path;
    }
}
