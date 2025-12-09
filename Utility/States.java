/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package Utility;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to return the US states in various formats and ways.
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 05/20/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class States {
    
    private static final String StateAbbrList[] = 
    //<editor-fold defaultstate="collapsed" desc="State Abbrs">
    {
        "AL",
        "AK",
        "AZ",
        "AR",
        "CA",
        "CO",
        "CT",
        "DC",
        "DE",
        "FL",
        "GA",
        "HI",
        "ID",
        "IL",
        "IN",
        "IA",
        "KS",
        "KY",
        "LA",
        "ME",
        "MD",
        "MA",
        "MI",
        "MN",
        "MS",
        "MO",
        "MT",
        "NE",
        "NV",
        "NH",
        "NJ",
        "NM",
        "NY",
        "NC",
        "ND",
        "OH",
        "OK",
        "OR",
        "PA",
        "PR",
        "RI",
        "SC",
        "SD",
        "TN",
        "TX",
        "UT",
        "VT",
        "VA",
        "WA",
        "WV",
        "WI",
        "WY"
    };
    //</editor-fold>
    
    private static final String StateFullList[] =
    //<editor-fold defaultstate="collapsed" desc="Full State Name">
    {
        "ALABAMA",
        "ALASKA",
        "ARIZONA",
        "ARKANSAS",
        "CALIFORNIA",
        "COLORADO",
        "CONNECTICUT",
        "WASHINGTON DC",
        "DELAWARE",
        "FLORIDA",
        "GEORGIA",
        "HAWAII",
        "IDAHO",
        "ILLINOIS",
        "INDIANA",
        "IOWA",
        "KANSAS",
        "KENTUCKY",
        "LOUISIANA",
        "MAINE",
        "MARYLAND",
        "MASSACHUSETTS",
        "MICHIGAN",
        "MINNESOTA",
        "MISSISSIPPI",
        "MISSOURI",
        "MONTANA",
        "NEBRASKA",
        "NEVADA",
        "NEW HAMPSHIRE",
        "NEW JERSEY",
        "NEW MEXICO",
        "NEW YORK",
        "NORTH CAROLINA",
        "NORTH DAKOTA",
        "OHIO",
        "OKLAHOMA",
        "OREGON",
        "PENNSYLVANIA",
        "PUERTO RICO",
        "RHODE ISLAND",
        "SOUTH CAROLINA",
        "SOUTH DAKOTA",
        "TENNESSEE",
        "TEXAS",
        "UTAH",
        "VERMONT",
        "VIRGINIA",
        "WASHINGTON",
        "WEST VIRGINIA",
        "WISCONSIN",
        "WYOMING"
    };
    //</editor-fold>
    
    public States()
    {}
    
    // Abbreviations
    public Choice StatesByChoice()
    {
        Choice states = new Choice();
        
        states.removeAll();
        for(String state : StateAbbrList)
        {
            states.add(state);
        }
        
        return states;
    }
    
    public String[] StatesStrArray()
    {      
        return StateAbbrList;
    }
    
    public ArrayList<String> StatesByList()
    {
        ArrayList<String> states = new ArrayList<>();
        states.addAll(Arrays.asList(StateAbbrList));
        
        return states;
    }
    
    // Full States
    public Choice FullStatesByChoice()
    {
        Choice states = new Choice();
        
        states.removeAll();
        for(String state : StateFullList)
        {
            states.add(state);
        }
        
        return states;
    }
    
    public String[] FullStatesStrArray()
    {      
        return StateFullList;
    }
    
    public ArrayList<String> FullStatesByList()
    {
        ArrayList<String> states = new ArrayList<>();
        states.addAll(Arrays.asList(StateFullList));
        
        return states;
    }
}
