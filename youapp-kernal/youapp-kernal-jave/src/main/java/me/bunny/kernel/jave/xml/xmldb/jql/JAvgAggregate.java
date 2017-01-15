package me.bunny.kernel.jave.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.utils.JStringUtils;

public class JAvgAggregate extends JAggregate {

	private double sum;
	
	private double count;
	
	public JAvgAggregate(String property, String alias) {
		super(property, alias);
		name="Average("+property+")";
	}
	public JAvgAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	public JAvgAggregate(String property) {
		super(property);
		name="Average("+property+")";
	}
	public JAvgAggregate() {
	}
	
	@Override
	public Object execute(Map<String, JBaseModel> models) {
		
		Object obj=value(models);
		String objString=obj==null?"":String.valueOf(obj);
		try{
			if(JStringUtils.isNullOrEmpty(objString)){
				sum=sum+Double.valueOf(String.valueOf(objString));
			}
			count++;
			return sum;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object get() {
		return sum/count;
	}

	@Override
	public void reset() {
		this.sum=0;
		this.count=0;
	}
	
	@Override
	public String jql() {
		return "avg("
				+super.jql()
				+")"
				+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}
	
	
}