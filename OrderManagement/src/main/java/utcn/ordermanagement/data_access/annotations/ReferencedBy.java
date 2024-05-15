package utcn.ordermanagement.data_access.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation lets the query builder know that the table in the database is referenced by another one
 *
 * @implNote It is used to annotate a class
 * Example usage:
 *  {@literal @}ReferencedBy(table = Department)
 *  public class Employee {
 *      // Class implementation...
 *   }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReferencedBy {
    /**
     * Specifies the referenced table in the database.
     *
     * @return The referenced table as a Class object.
     */
    Class<?> table();
}
