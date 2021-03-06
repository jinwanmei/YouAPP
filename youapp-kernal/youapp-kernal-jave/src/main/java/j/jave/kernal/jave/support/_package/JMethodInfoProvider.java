/**
 * 
 */
package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.support.JProvider;
import j.jave.kernal.jave.support._package.JAbstractMethodFinder.JMethodFilter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * collect and expose method information. 
 * @author J
 */
public interface JMethodInfoProvider <T> extends JProvider{

	public static interface JMethodInfoGen<T>{
		/**
		 * get method information , if all {@code filter()}  in the @see {@link JMethodFilter} return false. 
		 * @param method
		 * @param classIncudeMethod
		 * @return
		 */
		T getInfo(Method method,Class<?> classIncudeMethod);
	}
	
	/**
	 * get Method Info. ANY expected value of {@link JMethodInfo}
	 * @return
	 */
	public List<T> getMethodInfos() ;
	
	/**
	 * 
	 * KEY : class type . 
	 * VALUE : ANY expected value of {@link JMethodInfo}.
	 * @return
	 */
	public Map<Class<?>, List<T>> getClassMethodInfos() ;
	
}
