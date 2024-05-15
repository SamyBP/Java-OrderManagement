package utcn.ordermanagement.data_access.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark a field as foreign key in a model class and let the query builder know about the relation
 *
 * @implNote The annotated field should be of a model class
 * Example usage:
 *  {@literal @}FK
 *  private Employee employee;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FK {
}
