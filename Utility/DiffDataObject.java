
package Utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Static utility class to compare the return values from two data objects (DOs)
 *  and return differences. Requires the @Diff annotation on getter methods, and
 *  will only work on objects whose return types can be evaluated using its
 *  .toString() method.
 *  
 * @author TomR
 */
public class DiffDataObject
{
    /**
     * Wrapper class returned to calling code. Provides the field name as
     *  defined by the 'fieldName' annotation value, and the two values.
     */
    public static class Diff
    {
        private final String fieldName;
        private final String firstVal;
        private final String secondVal;
        private final boolean isUniqueIdentifier;
        private final boolean isUserVisible;
        
        public Diff(String fieldName,
                String firstVal, String secondVal,
                boolean isUniqueIdentifier, boolean isUserVisible)
        {
            this.fieldName = fieldName;
            this.firstVal = firstVal;
            this.secondVal = secondVal;
            this.isUniqueIdentifier = isUniqueIdentifier;
            this.isUserVisible = isUserVisible;
        }

        public String getFieldName()
        {
            return fieldName;
        }

        public String getFirstVal()
        {
            return firstVal;
        }

        public String getSecondVal()
        {
            return secondVal;
        }
        
        public boolean isUniqueIdentifier()
        {
            return isUniqueIdentifier;
        }
        
        public boolean isUserVisible()
        {
            return isUserVisible;
        }
        
        @Override
        public String toString()
        {
            return ToStringBuilder.reflectionToString(this);
        }
    }
    
    /**
     * Compares the method results of the supplied annotated data objects (DOs)
     *  and returns a list of any differences.
     * 
     *  Object arguments should be DOs with getter methods annotated with @Diff
     *  and with the fieldName specified.
     * 
     *  Supplied Objects should be the same type, and getter methods should only return
     *  values that can be compared using the toString() method. A handful of
     *  other types of comparisons are supported; see GetStringRepresentation
     *  method for those types.
     * 
     * @param firstObj First Data Object to compare
     * @param secondObj Second Data Object to compare
     * @return List of Diff objects representing the method return value differences, NULL on error
     */
    public static List<Diff> GetDiffs(Object firstObj, Object secondObj)
    {
        Class firstClass = firstObj.getClass();
        Class secondClass = secondObj.getClass();

        // Objects must be of the same type
        if (firstClass.equals(secondClass) == false)
        {
            System.out.println("Object types did not match: "
                    + firstClass.toString() + " / "
                    + secondClass.toString());
            return null;
        }

        // Return list
        ArrayList<Diff> diffs = new ArrayList<>();

        try
        {
            // Grab the methods for this class
            Method[] methods = firstClass.getMethods();
            for( Method method : methods )
            {
                // Retrieve annotations
                Annotation[] annotations  = method.getAnnotations();     
                for (Annotation annot : annotations)
                {
                    // Is this a Diff annotation?
                    if (annot.annotationType() == Utility.Diff.class)
                    {
                        // Invoke both Data Object getter methods.
                        // These methods should either return a primitive type,
                        // a boxed primitive, or a type which produces its value
                        // in the toString() method.
                        Object firstResult = method.invoke(firstObj, new Object[0]);
                        Object secondResult = method.invoke(secondObj, new Object[0]);

                        // Compare results

                        // Both NULL, skip
                        if (firstResult == null && secondResult == null) continue;

                        String firstResultString = "";
                        String secondResultString = "";
                        
                        if (firstResult != null) firstResultString = GetStringRepresentation(firstResult);
                        if (secondResult != null) secondResultString = GetStringRepresentation(secondResult);

                        // Both have the same non-null value, skip
                        if (firstResultString != null && secondResultString != null &&
                                firstResultString.equals(secondResultString))
                        {
                            continue;
                        }

                        Class<? extends Annotation> type = annot.annotationType();
                        Utility.Diff cc = (Utility.Diff)annot;
                        
                        boolean isUniqueIdentifier = false;
                        boolean isUserVisible = false;
                        String fieldName = "";
                        
                        // Get the annotation values for field name and unique id
                        for (Method annotationMethod : type.getDeclaredMethods())
                        {
                            
                            Object methodOutputObj = annotationMethod.invoke(cc, new Object[0]);
                            switch (annotationMethod.getName())
                            {
                                
                                case "fieldName":
                                    // We need a field name for the log
                                    if (methodOutputObj == null)
                                    {
                                        System.out.println("Annotation name is incorrect."
                                                + " Please ensure that your annotation looks"
                                                + " like '@Diff(fieldName = \"name_of_column_changing\")' ");
                                        return null;                                
                                    }
                                    fieldName = methodOutputObj.toString();                                    
                                    break;
                                    
                                    
                                case "isUniqueId":
                                    isUniqueIdentifier = (methodOutputObj != null
                                            && methodOutputObj instanceof Boolean
                                            && ((Boolean)methodOutputObj) == true);
                                    break;
                                    
                                case "isUserVisible":
                                    isUserVisible = (methodOutputObj != null
                                            && methodOutputObj instanceof Boolean
                                            && ((Boolean)methodOutputObj) == true);
                                    break;
                            }
                        }
                        // Put the unique identifier diff first (if present) so
                        // we can use it to determine the diff action
                        if (isUniqueIdentifier)
                        {
                            diffs.add(0, new Diff(fieldName, firstResultString, secondResultString, isUniqueIdentifier, isUserVisible));
                        }
                        else
                        {
                            diffs.add(new Diff(fieldName, firstResultString, secondResultString, isUniqueIdentifier, isUserVisible));
                        }
                    }
                }
            }
        }
        catch (IllegalAccessException ex)
        {
            System.out.println("Illegal Access Exception performing diff: " + ex.getMessage());
            return null;
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println("Illegal Argument Exception performing diff. This is likely because the annotation was put on a setter method by accident!: " + ex.getMessage());
            return null;
        }
        catch (SecurityException ex)
        {
            System.out.println("Illegal Access Exception performing diff: " + ex.getMessage());
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("General Exception performing diff: " + ex.getMessage());
            return null;            
        }
        return diffs;
    }
    
    /**
     * Returns a string representation of the supplied object.
     * Handles some return types that cannot be obtained by a toString(), so 
     *  that blob data types can be used in diffs.
     * 
     * This method cannot catch every scenario, but should probably handle basic
     *  array types.
     * @param obj
     * @return String value
     */
    private static String GetStringRepresentation(Object obj)
    {
        String value;
        if (obj instanceof byte[])
        {
            byte[] byteRepresentation = (byte[])obj;
            value = new String(byteRepresentation);
        }
        else if (obj instanceof int[])
        {
            int[] intRepresentation = (int[])obj;
            value = Arrays.toString(intRepresentation);
        }
        else // Regular string
        {
            value = obj.toString();
        }
        
        return value;
    }    
}