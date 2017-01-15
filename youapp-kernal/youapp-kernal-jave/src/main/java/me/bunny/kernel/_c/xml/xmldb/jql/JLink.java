package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;

public abstract class JLink extends JAbstractFilter {
	protected String compare;
	
	public final static String AND="AND";
	public final static String OR="OR";
	
	public String getCompare() {
		return compare;
	}
	public void setCompare(String compare) {
		this.compare = compare;
	}
	@Override
	public String name() {
		return JLink.class.getName();
	}
	
	@Override
	public boolean validate(Map<String, JBaseModel> models) {
		throw new UnsupportedOperationException("Link (AND|OR) DONT SUPPORT VALIDATION.");
	}
	
	@Override
	public void push(JFilter filter) {
		throw new UnsupportedOperationException("Link cannot contain other filter. ");
	}
	
	
	@Override
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}