package j.jave.kernal.jave.xml.xmldb.jql;

import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.utils.JNumberUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.xmldb.JSelect;


public class JAscSort extends JSort {
	
	public JAscSort(String property, String alias) {
		super(property, alias);
	}
	/*
	public AscSort(String property, String alias, String name) {
		super(property, alias, name);
	}
	*/
	public JAscSort(String property) {
		super(property);
	}

	@Override
	protected boolean compare(JSQLType dataType, Object thisValue,
			Object beforeValue) {
		
		if(dataType==JSQLType.DOUBLE||dataType==JSQLType.INTEGER){
			return JNumberUtils.ltEqualNumber(thisValue, beforeValue);
		}
		else {
			return JStringUtils.ltEqualString(thisValue, beforeValue);
		}
	}
	
	public String jql(){
		if(!JSelect.DEFAULT_ALIAS.equals(alias)){
			return alias+"."+property+" asc";
		}
		else{
			return property+" asc";
		}
	}

}
