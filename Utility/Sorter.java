package Utility;

import java.util.List;

/**
 * A class for extra sorting algorithms.
 * See https://en.wikipedia.org/wiki/Sorting_algorithm for a comparison of time and space complexities. 
 * Java's built in sorting methods use Timsort for objects, and Quicksort for primitive types.
 * @author Nick Engell
 */
public class Sorter {
    
    public static <T extends Comparable<? super T>> void heapSort(List<T> list) {
        int listSize = list.size();
        
        for (int i = listSize / 2 - 1; i >= 0; i--) {
            heapify(list, listSize, i);
        }
        
        for (int i = listSize - 1; i > 0; i--) {
            swap(list, 0, i);
            heapify(list, i, 0);
        }            
    }
    
    private static <T extends Comparable<? super T>> void heapify(List<T> list, int heapSize, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < heapSize && list.get(l).compareTo(list.get(largest)) > 0) {
            largest = l;
        }
           
        if (r < heapSize && list.get(r).compareTo(list.get(largest)) > 0) {
            largest = r;
        }
           
        if (largest != i) {
            swap(list, largest, i);
            heapify(list, heapSize, largest);
        }
    }
    
    private static <T extends Comparable<? super T>> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);     
    }
    
}
