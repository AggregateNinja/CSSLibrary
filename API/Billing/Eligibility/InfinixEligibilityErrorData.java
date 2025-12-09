/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing.Eligibility;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Matt
 */
public class InfinixEligibilityErrorData implements Serializable{
    
    private static final long serialVersionUID = 42L;
    
    String status;
    String timestamp;
    String path;
    List<Errors> errors;
    
    String accession; // Not read from JSON
    
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public String getTimestamp() {return timestamp;}
    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}
    public List<Errors> getErrors() {return errors;}
    public void setErrors(List<Errors> errors) {this.errors = errors;}
    
    public String getAccession() {return accession;}
    public void setAccession(String accession) {this.accession = accession;}
    
    public static class Errors
    {
        String field;
        String errorCode;
        String errorType;
        String errorDescription;
        
        public String getField() {return field;}
        public void setField(String field) {this.field = field;}
        public String getErrorCode() {return errorCode;}
        public void setErrorCode(String errorCode) {this.errorCode = errorCode;}
        public String getErrorType() {return errorType;}
        public void setErrorType(String errorType) {this.errorType = errorType;}
        public String getErrorDescription() {return errorDescription;}
        public void setErrorDescription(String errorDescription) {this.errorDescription = errorDescription;}
    }
}
