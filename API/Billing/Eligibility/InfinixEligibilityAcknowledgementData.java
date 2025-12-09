/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing.Eligibility;

/**
 *
 * @author Matt
 */
public class InfinixEligibilityAcknowledgementData {
    
    String caseNumber;
    String caseUrl;
    String message;
    
    public String getCaseNumber(){return caseNumber;}
    public void setCaseNumber(String caseNumber) {this.caseNumber = caseNumber;}
    public String getCaseURL(){return caseUrl;}
    public void setCaseURL(String caseUrl) {this.caseUrl = caseUrl;}
    public String getMessage(){return message;}
    public void setMessage(String message) {this.message = message;}
}
