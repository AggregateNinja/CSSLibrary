
package Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Given two lists and a comparator, returns an object that
 *  contains two lists:
 * 
 *  added   = represents items that exist in the second list only
 *  removed = represents items that exists in the first list only 
 * 
 * This can be used to log changes to data attached in a many-to-one relationship
 *  to a table. Equality is determined by the comparator, not by equals
 *  methods of the objects. This allows the caller to define their own means
 *  of assessing equality, which allows for composite key checking for tables
 *  without their own unique database identifier.
 * 
 *  Note that this method allows for the possibility of having duplicate items
 *  in either list, and will mark those duplicates as added or removed if they are
 *  present/not present in the other list.
 *  
 *  e.g. if you have a duplicate of an item in the first list, and the duplicate
 *  does not exist in the second, the duplicate will be marked as removed.
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class DiffListUtil
{
    // Returned output
    public static class DiffListOutput<T>
    {
        private List<T> added = new ArrayList<>();
        private List<T> removed = new ArrayList<>();
        
        public DiffListOutput(List<T> added, List<T> removed)
        {
            if (added == null) added = new ArrayList<>();
            if (removed == null) removed = new ArrayList<>();
            
            this.added = added;
            this.removed = removed;
        }
        
        public List<T> getAdded()
        {
            return added;
        }
        
        public List<T> getRemoved()
        {
            return removed;
        }
        
        public boolean hasDifferences()
        {
            return (added.isEmpty() == false || removed.isEmpty() == false);
        }
    }
    
    public static <T> DiffListOutput<T> getListDiffs(
            List<T> originalList,
            List<T> currentList,
            Comparator<T> comparator)
            throws IllegalArgumentException, Exception
    {
        if (comparator == null) throw new IllegalArgumentException(
                "DiffListUtil::getListDiffs: Received a [NULL] comparator argument");
        
        if (originalList == null) originalList = new ArrayList<>();
        if (currentList == null) currentList = new ArrayList<>();
        
        List<T> addedItems = new ArrayList<>();
        List<T> removedItems = new ArrayList<>();
        
        Collections.sort(originalList, comparator);
        Collections.sort(currentList, comparator);
        
        int originalListIndex = 0;
        int currentListIndex = 0;
        while (true)
        {
            // If we've exhausted our original list, mark any remaining items in
            // the current list as "added" and finish
            if (originalListIndex >= originalList.size())
            {
                for (int i = currentListIndex; i < currentList.size(); i++)
                {
                    addedItems.add(currentList.get(i));
                }
                break;
            }
            
            // If we've exhausted our current list, mark any remaining items in
            // the original list as "removed" and finish
            if (currentListIndex >= currentList.size())
            {
                for (int i = originalListIndex; i < originalList.size(); i++)
                {
                    removedItems.add(originalList.get(i));
                }
                break;
            }
            
            T originalListItem = originalList.get(originalListIndex);
            T currentListItem = currentList.get(currentListIndex);
            int comparison = comparator.compare(originalListItem, currentListItem);
            
            if (comparison < 0)
            {
                originalListIndex += 1;
                removedItems.add(originalListItem);
            }
            else if (comparison > 0)
            {
                currentListIndex += 1;
                addedItems.add(currentListItem);
            }
            else // These items are equivalent, move both pointers
            {
                originalListIndex += 1;
                currentListIndex += 1;
            }
        }
        return new DiffListOutput(addedItems, removedItems);
    }
}
