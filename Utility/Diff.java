
package Utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DataObject (DO) method Annotation. Flags the method to be tested
 * when checking for diffs between two data objects using the 
 * Utility.DiffDataObject class
 * 
 * Be sure to include the field name in the DO annotations
 * 
 * Flagging a method as a unique identifier will use the values for that method
 * to determine what kind of action is taking place on the object:
 * 
 * NULL unique identifier     --> NOT NULL = inserted
 * NOT NULL unique identifier --> NOT NULL = updated
 * NOT NULL unique identifier --> NULL     = deleted
 * 
 * e.g.:
 * 
 *  @Diff(fieldName = "name_of_column_changing")
 *  public Object getterMethodFromDO()
 *  { }
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Diff
{
    public String fieldName() default "";
    public boolean isUniqueId() default false;
    public boolean isUserVisible() default false;
}
