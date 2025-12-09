/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.util.Date;
import java.io.Serializable;

public class PreferencesLog implements Serializable {
    
    private static final long serialversionUID = 42L;
    
    private int idPreferencesLog;
    private String preferenceName;
    private String preValue;
    private String postValue;
    private int performedByUserId;
    private Date date;
    
    //<editor-fold defaultstate="collapsed" desc="Class getters/setters">
    public int getIdPreferencesLog()
    {
        return idPreferencesLog;
    }
    
    public void setIdPreferencesLog(int idPreferencesLog)
    {
        this.idPreferencesLog = idPreferencesLog;
    }
    
    public String getPreferenceName()
    {
        return preferenceName;
    }
    
    public void setPreferenceName(String preferenceName)
    {
        this.preferenceName = preferenceName;
    }
    
    public String getPreValue()
    {
        return preValue;
    }
    
    public void setPreValue(String preValue)
    {
        this.preValue = preValue;
    }
    
    public String getPostValue()
    {
        return postValue;
    }
    
    public void setPostValue(String postValue)
    {
        this.postValue = postValue;
    }
    
    public int getPerformedByUserId()
    {
        return performedByUserId;
    }
    
    public void setPerformedByUserId(int performedByUserId)
    {
        this.performedByUserId = performedByUserId;
    }
    
    public Date getDate()
    {
        return date;
    }
    
    public void setDate(Date date)
    {
        this.date = date;
    }
    //</editor-fold>
}
