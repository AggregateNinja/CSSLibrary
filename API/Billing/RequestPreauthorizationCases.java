/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Matt
 */
public class RequestPreauthorizationCases implements Serializable {
    
    public static enum API_PROVIDER{
        Infinix;
    }
    
    final API_PROVIDER apiProvider;
    final List<String> caseIds;
    
    public RequestPreauthorizationCases(API_PROVIDER apiProvider, List<String> caseIds)
    {
        this.apiProvider = apiProvider;
        this.caseIds = caseIds;
    }
    
    public API_PROVIDER getApiProvider() {return apiProvider;}
    public List<String> getCaseIds() {return caseIds;}
}
