/**
 * 
 */
package j.jave.kernal.jave.support.databind;

/**
 * @author J
 */
public class JDataBindingException extends RuntimeException {

	public JDataBindingException(String message){
		super(message);
	}
	
	public JDataBindingException(Exception e){
		super(e);
	}
	
	
}
