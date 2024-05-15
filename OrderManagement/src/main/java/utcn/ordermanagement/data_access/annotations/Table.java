package utcn.ordermanagement.data_access.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation lets the query builder know that the table in the database is mapped by this class
 *
 * @implNote It is used to annotate a class
 * Example usage:
 *  {@literal @}Table(name = "department")
 *  public class Employee {
 *      // Class implementation...
 *   }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * Specifies the table name in the database.
     *
     * @return The name of the table as a String object
     */
    String name() default "Invalid";
}
