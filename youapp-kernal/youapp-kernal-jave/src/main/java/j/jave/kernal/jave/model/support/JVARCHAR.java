package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.utils.JStringUtils;

/**
 * VARCHAR Delegate 
 * @author J
 */
public class JVARCHAR extends JAbstractType<String> {
	
	private int length;
	
	public JVARCHAR(JSQLType sqlType,int length) {
		super(sqlType);
		this.length=length;
	}
	
	@Override
	public String name() {
		return sqlType.name();
	}

	@Override
	public boolean defaultValidate(String object) {
		boolean valid=true;
		if(JStringUtils.isNullOrEmpty(object)) return true;
		valid= object.trim().length()<=length;
		if(!valid){
			invalidMessage.append(object+", exceed the length["+length+"]");
		}
		return valid;
	}

	
	@Override
	public Object convert(String string) {
		return string;
	}
	

}
