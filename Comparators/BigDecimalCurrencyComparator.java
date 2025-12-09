/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package Comparators;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Rob
 */
public class BigDecimalCurrencyComparator implements Comparator<BigDecimal>
{
    @Override
    public int compare(BigDecimal first, BigDecimal second) {
        if (first == null) return 1;
        if (second == null) return -1;
        if (first.equals(second)) return 0;
        
        return first.compareTo(second);
    }
}
