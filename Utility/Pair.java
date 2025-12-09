package Utility;

import java.io.Serializable;

/**
 * @param <F> First entry
 * @param <S> Second entry
 * @date:   Jan 18, 2013
 * @author: CSS Dev
 * 
 * @project:  
 * @package: org.css.orderentry.v1
 * @file name: Pair.java
 *
 * @Description:
 * http://stackoverflow.com/questions/6044923/generic-pair-class
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */
public class Pair<F, S> implements Serializable
{
    private static final long serialVersionUID = 42L;
    
    private F first; //first member of pair
    private S second; //second member of pair

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
