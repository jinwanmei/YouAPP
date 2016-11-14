package j.jave.kernal.jave.s_deprecated;

public interface JObjectInstantiator<T> {

	 /**
	    * Returns a new instance of an object. The returned object's class is defined by the
	    * implementation.
	    * 
	    * @return A new instance of an object.
	    */
	   T newInstance();
	   
}
