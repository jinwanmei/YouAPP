/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation uses to make the program know what event listener on. 
 * it is the bridge of {@link JYouAPPListener} and {@link JYouAPPEvent}
 * <strong> Note that the class only used together with {@link JYouAPPListener} </strong>
 * @author J
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JEventOnListener {

	Class<? extends JYouAPPEvent<?>> name();
	
}
