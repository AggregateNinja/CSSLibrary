/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 22, 2015
 */

package BL;

import DAOS.ClientCopyDAO;
import DOS.CarbonCopies;
import DOS.ClientCopy;
import java.util.ArrayList;

/**
 * @date:   Jun 22, 2015  2:46:33 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: CarbonCopyBL.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class CarbonCopyBL {

    public CarbonCopyBL(){
        
    }
    
    /**
     * Returns an ArrayList of CarbonCopy class objects for a client based on
     * their static saved Client Copies.
     * @param ID Integer Client ID
     * @return 
     */
    public ArrayList<CarbonCopies> GetCCFromClientCopy(Integer ID){
        ArrayList<CarbonCopies> carbonCopies = new ArrayList<>();
        
        try{
            ClientCopyDAO cliCopyDAO = new ClientCopyDAO();
            ArrayList<ClientCopy> clientCopies = cliCopyDAO.GetAllForClient(ID);
            
            for(ClientCopy copy : clientCopies){
                carbonCopies.add(ConvertClientCopyToCarbonCopy(copy));
            }
        }catch(Exception ex){
            System.out.println("CarbonCopyBL::ConvertClientCopyToCarbonCopy - Exception: " + ex.toString());
        }
        
        return carbonCopies;
    }
    
    /**
     * Converts a ClientCopy Object to a CarbonCopies object
     * @param cliCopy ClientCopy Object.
     * @return 
     */
    public CarbonCopies ConvertClientCopyToCarbonCopy(ClientCopy cliCopy){
        CarbonCopies copy = new CarbonCopies();
        
        copy.setFaxName(cliCopy.getFaxName());
        copy.setFaxNumber(cliCopy.getFaxNo());
        copy.setDoctor(cliCopy.getDoctor());
        copy.setClient(cliCopy.getClient());
        
        return copy;
    }
}
