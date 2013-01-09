/**
 * 
 */
package jabara.sample.service.impl;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jabaraster
 */
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MainTransactional {
    //
}
