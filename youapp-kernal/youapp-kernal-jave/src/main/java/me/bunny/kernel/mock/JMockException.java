package me.bunny.kernel.mock;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class JMockException extends JNestedRuntimeException {
	
	public JMockException(String message){
		super(message);
	} 
	
	public JMockException(Exception e){
		super(e);
	} 
	
}