
package BL;

import DAOS.PatientDAO;
import DAOS.PreferencesDAO;
import DAOS.SubscriberDAO;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;


public class PatientBL
{
    public enum ArNoType
    {
        PATIENT_AR,
        SUBSCRIBER_AR;
    }
    /**
     * Returns whether a new (unsaved) AR number is valid
     * @param arNo
     * @param type
     * @return 
     */
    public static Boolean IsNewArNoValid(String arNo, ArNoType type)
    {
        PreferencesDAO pdao = new PreferencesDAO();
        Boolean cssBillingEnabled = pdao.getBoolean("CSSBillingEnabled");
        if (cssBillingEnabled == null) cssBillingEnabled = true;
        
        if (cssBillingEnabled)
        {
            // If css billing, AR must:
            // 1) Be no longer than 9 digits
            // 2) Have no leading zeroes
            // 3) Have no alpha or special characters
            if (arNo.startsWith("0")) return false;
            try
            {
                Integer arNoInt = Integer.valueOf(arNo);
                if (arNoInt < 0 || arNoInt > 999999999) return false;
            }
            catch (NumberFormatException ex) { return false; }
        }
        
        switch (type)
        {
            case PATIENT_AR:
                PatientDAO patientDAO = new PatientDAO();
                if (patientDAO.PatientExistsByAr(arNo)) return false;
                break;
                
            case SUBSCRIBER_AR:
                SubscriberDAO subscriberDAO = new SubscriberDAO();
                if (subscriberDAO.SubscriberExists(arNo)) return false;
                break;
        }

        return true;
    }
    
    /**
     * Generates a random AR number and tests whether it's valid. Returns
     *  a valid AR when one is found.
     * @param type
     * @return 
     */
    public static String GenerateArNo(ArNoType type)
    {
        Integer testAr = null;
        do
        {
            testAr = Utility.RandomsGenerator.getRandomNumberBetween(1,999999999);
        }
        while (IsNewArNoValid(testAr.toString(), type) == false);
        
        return testAr.toString();
    }
    
    public static String GenerateMinimumArNo(ArNoType type, Long[] excludedIds) {
        String newArNo = "";
        Long[] tmpAllArNos;
        Long[] allArNos;
        int arCount = 0;
        
        try {
            switch (type)
            {
                case PATIENT_AR:
                    PatientDAO patientDAO = new PatientDAO();
                    tmpAllArNos = patientDAO.GetAllArNos();
                    arCount = tmpAllArNos.length;
                    break;

                case SUBSCRIBER_AR:
                    SubscriberDAO subscriberDAO = new SubscriberDAO();
                    tmpAllArNos = subscriberDAO.GetAllArNos();
                    arCount = tmpAllArNos.length;
                    break;
                default:
                    tmpAllArNos = new Long[0];
            }
            
            // excludedIds are Locked arNos that are currently in use in order entry, but not yet saved to database
            if (excludedIds.length > 0) {
                allArNos = ArrayUtils.addAll(tmpAllArNos, excludedIds);
                arCount = allArNos.length;
            } else {
                allArNos = tmpAllArNos;
            }

            int ptr = 0;

            // Check if 1 is present in array or not
            for(int i = 0; i < arCount; i++)
            {
                if (allArNos[i] == 1)
                {
                    ptr = 1;
                    break;
                }
            }

            // If 1 is not present
            if (ptr == 0)
                return "1";

            // Changing values to 1
            for(int i = 0; i < arCount; i++)
                if (allArNos[i] <= 0 || allArNos[i] > arCount)
                    allArNos[i] = Long.valueOf(1);

            // Updating indices according to values
            for(int i = 0; i < arCount; i++)
                allArNos[(allArNos[i].intValue() - 1) % arCount] += Long.valueOf(arCount);

            // Finding which index has value less than n
            for(int i = 0; i < arCount; i++)
                if (allArNos[i] <= arCount)
                    return String.valueOf(i + 1);
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
        

        // If array has values from 1 to n
        return String.valueOf(arCount + 1);
        
    }
    
}
