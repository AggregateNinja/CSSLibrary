/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import Utility.Diff;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author eboss
 */
@Entity
@Table(name = "drugInfo")
public class DrugInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "drugId")
    private String drugId;
    @Column(name = "drug")
    private String drug;
    @Column(name = "strength")
    private String strength;
    @Column(name = "ingredients")
    private String ingredients;
    @Column(name = "formulation")
    private String formulation;
    @Column(name = "rxcui")
    private String rxcui;
    @Column(name = "specific_prod_id")
    private String specific_prod_id;
    @Column(name = "dose_route")
    private String dose_route;
    @Column(name = "dose_amount")
    private String dose_amount;
    @Column(name = "dose_units")
    private String dose_units;
    
    public DrugInfo() {
        
    }
    
    public DrugInfo(Integer id, String drugId, String drug, String strength,
            String ingredients, String formulation, String rxcui, String specific_prod_id, 
            String dose_route, String dose_amount, String dose_units) {
        this.id = id;
        this.drugId = drugId;
        this.drug = drug;
        this.strength = strength;
        this.ingredients = ingredients;
        this.formulation = formulation;
        this.rxcui = rxcui;
        this.specific_prod_id = specific_prod_id;
        this.dose_route = dose_route;
        this.dose_amount = dose_amount;
        this.dose_units = dose_units;        
    }
    
    @Diff(fieldName = "id", isUniqueId = true)
    public Integer getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    
    @Diff(fieldName = "drugId", isUserVisible = true)
    public String getDrugId()
    {
        return drugId;
    }
    public void setDrugId(String drugId)
    {
        this.drugId = drugId;
    }
    
    @Diff(fieldName = "drug", isUserVisible = true)
    public String getDrug()
    {
        return drug;
    }
    public void setDrug(String drug)
    {
        this.drug = drug;
    }
    
    @Diff(fieldName = "strength", isUserVisible = true)
    public String getStrength()
    {
        return strength;
    }
    public void setStrength(String strength)
    {
        this.strength = strength;
    }
    
    @Diff(fieldName = "ingredients", isUserVisible = true)
    public String getIngredients()
    {
        return ingredients;
    }
    public void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }
    
    @Diff(fieldName = "formulation", isUserVisible = true)
    public String getFormulation()
    {
        return formulation;
    }
    public void setFormulation(String formulation)
    {
        this.formulation = formulation;
    }
    
    @Diff(fieldName = "rxcui", isUserVisible = true)
    public String getRxcui()
    {
        return rxcui;
    }
    public void setRxcui(String rxcui)
    {
        this.rxcui = rxcui;
    }
    
    @Diff(fieldName = "specific_prod_id", isUserVisible = true)
    public String getSpecificProdId()
    {
        return specific_prod_id;
    }
    public void setSpecificProdId(String specific_prod_id)
    {
        this.specific_prod_id = specific_prod_id;
    }
    
    @Diff(fieldName = "dose_route", isUserVisible = true)
    public String getDoseRoute()
    {
        return dose_route;
    }
    public void setDoseRoute(String dose_route)
    {
        this.dose_route = dose_route;
    }
    
    @Diff(fieldName = "dose_amount", isUserVisible = true)
    public String getDoseAmount()
    {
        return dose_amount;
    }
    public void setDoseAmount(String dose_amount)
    {
        this.dose_amount = dose_amount;
    }
    
    @Diff(fieldName = "dose_units", isUserVisible = true)
    public String getDoseUnits()
    {
        return dose_units;
    }
    public void setDoseUnits(String dose_units)
    {
        this.dose_units = dose_units;
    }    
}
