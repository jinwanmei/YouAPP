/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support._package.JClassProvidedScanner;
import j.jave.kernal.jave.support._package.JClassesScanConfig;
import j.jave.kernal.jave.support._package.JClassesScanDefaultConfiguration;
import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default scan all classes in the CLASSPATH to detect the method . 
 *<p> you also can define customized implementation of {@code JPackageScan}  to provide whose classes need be processed.
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JMethodInfoGen }
 */
public class JMethodDetector<T> extends JClassesScanDefaultConfiguration 
	implements JClassesScanConfig ,JMethodInfoProvider<T> {

	public static interface JMethodFilter{
		
		/**
		 * do with the method or not,  then method can be done next if false.
		 * skip the method if true.
		 * @param method method signature information.
		 * @param classIncudeMethod class information which includes the method above
		 * @return
		 */
		boolean filter(Method method,Class<?> classIncudeMethod);
		
		/**
		 * do with the class or not, then class can be done next if false.
		 * skip the class if true.
		 * @param clazz
		 * @return
		 */
		boolean filter(Class<?> clazz);
		
		/**
		 * scan all methods whose modify is in the scope of , then do filter using {@code filter(Method method,Class<?> classIncudeMethod)}
		 * @return
		 */
		int[] methodModifiers();
		
	}
	
	private static final JMethodFilter defaultMethodFilter=new JDefaultMethodFilter();
	
	private JMethodInfoGen<T> methodInfoGen;
	
//	private static final JMethodInfoGen<String> defaultMethodInfo=new JMethodInfoGen<String>() {
//		@Override
//		public String getInfo(Method method, Class<?> classIncudeMethod) {
//			return method.getName();
//		}
//	};
	
	private JMethodFilter methodFilter=null;
	
	public JMethodDetector(JMethodInfoGen<T> methodInfoGen) {
		methodFilter=defaultMethodFilter;
		this.methodInfoGen=methodInfoGen;
	} 
	
	/**
	 * 
	 * @param methodFilter implementation of {@link JMethodFilter}
	 * @param methodInfoGen implementation of {@link JMethodInfoGen}
	 */
	public JMethodDetector(JMethodFilter methodFilter,JMethodInfoGen<T> methodInfoGen) {
		this.methodFilter=methodFilter;
		this.methodInfoGen=methodInfoGen;
	} 
	
	/**
	 * @param methodFilter the methodFilter to set
	 */
	public void setMethodFilter(JMethodFilter methodFilter) {
		this.methodFilter = methodFilter;
	}
	
	/**
	 * @param methodInfoGen the methodInfoGen to set
	 */
	public void setMethodInfo(JMethodInfoGen<T> methodInfoGen) {
		this.methodInfoGen = methodInfoGen;
	}
	
	
	private JClassesScanner packageScan=null;
	
	/**
	 * @param packageScan the packageScan to set
	 */
	public void setPackageScan(JClassesScanner packageScan) {
		this.packageScan = packageScan;
	}
	
	/**
	 * ANY expected value from {@link JMethodInfoGen}
	 */
	private List<T> methodInfos=new ArrayList<T>();
	
	/**
	 * KEY : class type . 
	 * VALUE : ANY expected value from {@link JMethodInfoGen}.
	 */
	private Map<Class<?>, List<T>> classMethodInfos=new ConcurrentHashMap<Class<?>, List<T>>();
	
	/**
	 * @return the methodInfos
	 */
	public List<T> getMethodInfos() {
		return  methodInfos;
	}
	
	/**
	 * @return the classMethodInfos
	 */
	public Map<Class<?>, List<T>> getClassMethodInfos() {
		return classMethodInfos;
	}
	
	/**
	 * detect classes via {@code packageScan} field, which set previously.
	 * @param superClass
	 */
	public void detect(){
		Set<Class<?>> classes=packageScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JClassProvidedScanner }.
	 * @param clazz
	 */
	public void detect(Class<?>... clazz){
		JClassProvidedScanner classProvidedScan = new JClassProvidedScanner(clazz);
		Set<Class<?>> classes=classProvidedScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JDefaultClassesScanner }.
	 * @param superClass
	 * @param scanOnClasspath  only true accepted.
	 */
	public void detect(Class<?> superClass,boolean scanOnClasspath){
		if(superClass==null)
			throw new IllegalArgumentException("null param not supported.");
		if(!scanOnClasspath){
			throw new IllegalArgumentException("only true supported.");
		}
		// initialize all 
		JDefaultClassesScanner defaultPackageScan = new JDefaultClassesScanner(
				superClass);
		defaultPackageScan.setIncludePackages(includePackages);
		defaultPackageScan.setExpression(expression);
		defaultPackageScan.setIncludeClassNames(includeClassNames);
		defaultPackageScan.setClassLoader(classLoader);
		Set<Class<?>> classes=defaultPackageScan.scan();
		
		// filter on the classes 
		processClasses(classes);
	}

	private void processClasses(Set<Class<?>> classes) {
		if(classes!=null){ 
			for (Iterator<Class<?>>  iterator = classes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(!methodFilter.filter(clazz)){
					List<Method> methods=JClassUtils.getMethods(clazz, true, methodFilter.methodModifiers());
					for(int i=0;i<methods.size();i++){
						Method method=methods.get(i);
						if(!methodFilter.filter(method, clazz)){
							T obj= methodInfoGen.getInfo(method, clazz);
							methodInfos.add(obj);
							
							putClassObjects(clazz, obj);
						}
					}
				}
			}
		}
	}

	private void putClassObjects(Class<?> clazz, T obj) {
		List<T> infos=classMethodInfos.get(clazz);
		if(infos!=null){
			infos.add(obj);
		}
		else{
			infos=new ArrayList<T>();
			infos.add(obj);
			classMethodInfos.put(clazz, infos);
		}
	}
	
}
