
package Utility;

import java.util.Comparator;

/**
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class AccessionComparator  implements Comparator<String>
{
    @Override
    public int compare(String s1, String s2)
    {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return 1;
        if (s2 == null) return -1;
        
        // Sort number accessions first, then accessions containing alphas (if present)        
        int firstAcc = -1;
        try { firstAcc = Integer.valueOf(s1); } catch (NumberFormatException ex) {}
        
        int secondAcc = -1;
        try { secondAcc = Integer.valueOf(s2); } catch (NumberFormatException ex) {}
        
        // Both numeric, sort by value
        if (firstAcc >= 0 && secondAcc >= 0)
        {
            if (firstAcc == secondAcc) return 0;
            if (firstAcc > secondAcc) return 1;
            if (firstAcc < secondAcc) return -1;
        }
        
        // Otherwise just use the string comparison
        return s1.compareTo(s2);
    }
}
