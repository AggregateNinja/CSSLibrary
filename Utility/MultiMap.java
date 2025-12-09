package Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @date:   May 23, 2013
 * @author: CSS Dev
 * 
 * @project:  
 * @package: Utility
 * @file name: MultiMap.java
 *
 * @Description:
 * Example From http://codemanteau.com/coding/java-multimap-for-java
 * Computer Service & Support, Inc.  All Rights Reserved
 * @param <K> 
 * @param <V> 
 */

public class MultiMap <K, V>
{
    private Map<K, List<V>> mLocalMap;
 
    public MultiMap()
    {
        mLocalMap = new HashMap<K, List<V>>();
    }
    
    public MultiMap(boolean preserveOrder)
    {
        if (preserveOrder) mLocalMap = new LinkedHashMap<K, List<V>>();
        else mLocalMap = new HashMap<K, List<V>>();
    }
    
    
    /**
     * Adds a <V> value to MultiMap under a <K> key.
     * If a key doesn't exist, it will add a new keyset.
     * @param key The sort key.
     * @param val The value to load under the key value.
     */
    public void add(K key, V val) 
    {
        if (mLocalMap.containsKey(key)) 
        {
            List<V> list = mLocalMap.get(key);
            if (list != null)
            {
                list.add(val);
            }
        } 
        else 
        {
            List<V> list = new ArrayList<V>();
            list.add(val);
            mLocalMap.put(key, list);
        }
    }
    
    /**
     * Adds a list of values under the specified key.
     * @param key The sort key to add the values to.
     * @param vals The values to add.
     */
    public void addAll(K key, List<V> vals)
    {
        for(V val : vals)
        {
            add(key, val);
        }
    }
    
    /**
     * Merges an entire MultiMap into this MultiMap, keys and all.
     * @param map The MultiMap to merge with.
     */
    public void addAll(MultiMap <K, V> map)
    {
        for (K key : map.keySet()) 
        {
            List<V> list = map.get(key);
            addAll(key, list);
        }
    }
    
    /**
     * Returns all the values under the specified key.
     * @param key The key to get the values from.
     * @return the list of values under the specified key.
     */
    public List<V> get(K key) 
    {
        return mLocalMap.get(key);
    }
    
    /**
     * 
     * @param key
     * @param index
     * @return
     */
    public V get(K key, int index) 
    {
        return mLocalMap.get(key).get(index);
    }
    
    /**
     * Removes the values at key and replaces it with value.
     * @param key The sort key.
     * @param val The value to replace the current values with.
     */
    public void put(K key, V val) 
    {
        remove(key);
        add(key, val);
    }
    
    /**
     * Removes the key and everything under it.
     * @param key The sort key to remove.
     */
    public void remove(K key) 
    {
        mLocalMap.remove(key);
    }
    
    /**
     * Removes the value specified under key
     * @param key The sort key.
     * @param value The value to remove.
     */
    public void remove(K key, V value)
    {
        List<V> list = mLocalMap.get(key);
        list.remove(value);
    }
    
    /**
     * Checks the MultiMap to see if key contains val.
     * @param key The key to check.
     * @param val The value to look for.
     * @return If the key contains val, return true.
     */
    public boolean contains(K key, V val)
    {
        List<V> list = mLocalMap.get(key);
        for(V value : list)
        {
            if(value.equals(val))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * returns the entire size of the MultiMap.
     * @return The entire size of the MultiMap.
     */
    public int size()
    {
        int count = 0;
        for (K key : mLocalMap.keySet()) 
        {
            count += mLocalMap.get(key).size();
        }
        return count;
    }
    
    /**
     * Clears all data from the MultiMap.
     */
    public void clear() 
    {
        mLocalMap.clear();
    }
    
    /**
     * Sorts the values in respect to their keys in the MultiMap.
     * Example:
     *  [key] { values... }
     * Presort:
     *  [A] { 3, 1, 2 }
     *  [B] { 5, 4, 1 }
     * Sort (Ascending):
     *  [A] { 1, 2, 3 }
     *  [B] { 1, 4, 5 }
     * @param comp The Comparator function to compare the lists with.
     */
    public void sort(Comparator<V> comp)
    {
        for (K key : mLocalMap.keySet()) 
        {
            List<V> list = mLocalMap.get(key);
            Collections.sort(list, comp);
        }
    }
    
    /**
     * Returns a string representation of the MultiMap.
     * @return A string representation of the MultiMap.
     */
    @Override
    public String toString() 
    {
        StringBuilder str = new StringBuilder();
        for (K key : mLocalMap.keySet()) 
        {
            str.append("[ ");
            str.append(key);
            str.append(" : ");
            str.append(java.util.Arrays.toString(mLocalMap.get(key).toArray()));
            str.append(" ]");
        }
        return str.toString();
    }
    
    /**
     * Retrieves all the keys in the MultiMap.
     * @return An Iterable of all the keys in the MultiMap.
     */
    public Iterable<K> keySet() 
    {
        return mLocalMap.keySet();
    }
    
    /**
     * Returns the Entry Set of the MultiMap.
     * @return The Entry Set of the MultiMap.
     */
    public Set<Entry<K,List<V>>> entrySet() 
    {
        return mLocalMap.entrySet();
    }
    
    /**
     * Returns all the values in the MultiMap condensed into one array.
     * @return All the values.
     */
    public List<V> values()
    {
        List<V> allvalues = new ArrayList<V>();
        Collection<List<V>> valuesCollection = mLocalMap.values();
        for(List<V> list : valuesCollection)
        {
            for(V value : list)
            {
                allvalues.add(value);
            }
        }
        return allvalues;
    }
    
    /**
     * Find the key under which the first instance of value lies.
     * @param value The value to use to look up the key
     * @return The key value was found under, or null if it was not found.
     */
    public K find(V value)
    {
        for (K key : mLocalMap.keySet()) 
        {
            List<V> list = mLocalMap.get(key);
            for(V v : list)
            {
                if(v.equals(value))
                {
                    return key;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Find all the keys that store value
     * @param value The value to use to look up the key
     * @return A list of all the keys value was found under.
     */
    public List<K> findAll(V value)
    {
        List<K> keys = new ArrayList<K>(); 
        
        for (K key : mLocalMap.keySet()) 
        {
            List<V> list = mLocalMap.get(key);
            for(V v : list)
            {
                if(v.equals(value))
                {
                    keys.add(key);
                }
            }
        }
        
        return keys;
    }
}
