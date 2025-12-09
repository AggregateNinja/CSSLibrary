package BL;

import DAOS.PreferenceValueMapDAO;
import DAOS.PreferenceValuesDAO;
import DAOS.UserGroupPreferencesDAO;
import DOS.PreferenceValueMap;
import DOS.PreferenceValues;
import DOS.UserGroupPreferences;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: 10/26/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */

public class UserGroupPreferencesBL {
    
    public UserGroupPreferencesBL(){
        
    }
    
    /**
     * Get the value for the preference given the mapping id of preference to preference value.
     * @param prefValMapId
     * @return the value for the preference associated with the preference map id
     */
    public String GetPreferenceValueFromMapId(int prefValMapId)
    {
        String preferenceValue = "";
        PreferenceValueMapDAO pvmDAO = new PreferenceValueMapDAO();
        PreferenceValuesDAO pvDAO = new PreferenceValuesDAO();
        try
        {
            PreferenceValueMap pvm = pvmDAO.GetPreferenceValueMapById(prefValMapId);
            PreferenceValues pv = pvDAO.GetPreferenceValuesById(pvm.getPreferenceValueId());
            preferenceValue = pv.getValue();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserGroupPreferencesBL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesBL::GetPreferenceValueFromMapId - " + ex.toString());
        }
        return preferenceValue;
    }
    
    /**
     * Get all the preferences associated with the group or user according to the preference key.
     * @param prefKey
     * @param groupId
     * @param userId
     * @return a set of strings containing the values for all the associated preferences
     */
    public Set<String> GetAllPreferencesForUserAndGroup(String prefKey, int groupId, int userId){
        try{
            UserGroupPreferencesDAO ugpDAO = new UserGroupPreferencesDAO();
            List<UserGroupPreferences> groupList = ugpDAO.GetUserGroupPreferencesByKeyAndUserGroup(prefKey, groupId);
            List<UserGroupPreferences> userList = ugpDAO.GetUserGroupPreferencesByKeyAndUser(prefKey, userId);
            Set<String> userGroupPreferences = new HashSet<String>();
            for (UserGroupPreferences ugpVal : groupList)
            {
                userGroupPreferences.add(GetPreferenceValueFromMapId(ugpVal.getPreferenceValueMapId()));
            }
            for (UserGroupPreferences ugpVal : userList)
            {
                userGroupPreferences.add(GetPreferenceValueFromMapId(ugpVal.getPreferenceValueMapId()));
            }
            
            return userGroupPreferences;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
}
