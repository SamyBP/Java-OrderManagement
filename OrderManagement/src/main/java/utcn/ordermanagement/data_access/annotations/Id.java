package utcn.ordermanagement.data_access.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to annotate a field as a primary key in a model class and let the query builder know about it
 *
 * @implNote The annotation should be present on the primary key field
 * Example usage:
 *  {@literal @}Id
 *  private Long id;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
