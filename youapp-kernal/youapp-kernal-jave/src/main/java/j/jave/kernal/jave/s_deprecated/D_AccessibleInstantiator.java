package j.jave.kernal.jave.s_deprecated;

public class D_AccessibleInstantiator<T> extends D_ConstructorInstantiator<T> {

	public D_AccessibleInstantiator(Class<T> type) {
	      super(type);
	      if(constructor != null) {
	         constructor.setAccessible(true);
	      }
	   }
	
}
