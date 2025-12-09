
package Comparators;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Comparator;

/**
 * Compares two strings that represent formatted currency amounts
 *  from lowest to highest.
 * 
 * Extracts the floating point numeric portion only, along with an optional
 *  single negative sign if found before the currency number begins.
 * 
 *  "$2,035.31" = valid
 *  "1,234,56" = valid
 *  "$0.35" = valid
 *  "-$20.05" = valid
 *  "$-.55" = valid
 *  
 *  "12.234.5" = invalid
 *  "--25.25" = invalid
 */
public class StringCurrencyComparator implements Comparator<String>
{
    private final DecimalFormat format = new DecimalFormat();
    
    public StringCurrencyComparator()
    {
        this.format.setParseBigDecimal(true);
    }
    
    @Override
    public int compare(String first, String second)
    {
        if (first == null || first.isEmpty()) return 1;
        if (second == null || second.isEmpty()) return -1;
        if (first.equals(second)) return 0;
        
        return parseValue(first).compareTo(parseValue(second));
    }
    
    private BigDecimal parseValue(String value)
    {
        BigDecimal output = BigDecimal.ZERO;
        String cleanedValue = "";
        for (Character c : value.toCharArray())
        {
            // Only return a negative sign, number, or period
            if (Character.isDigit(c) || c.equals('-') || c.equals('.'))
            {
                cleanedValue += c;
            }
        }
        
        try
        {
            output = (BigDecimal)this.format.parse(cleanedValue);
        }
        catch (ParseException ex)
        {
            // It's ok to not be able to parse the string
        }
        
        return output;
    }

}
