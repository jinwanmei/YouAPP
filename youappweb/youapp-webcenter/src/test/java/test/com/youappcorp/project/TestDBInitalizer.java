package test.com.youappcorp.project;

import j.jave.kernal.sqlloader.ddl.JPropertiesSQLDDLCreateFactory;
import j.jave.kernal.sqlloader.ddl.JSQLDDLCreateFactory;
import j.jave.kernal.sqlloader.dml.JPropertiesSQLDMLCreateFactory;
import j.jave.kernal.sqlloader.dml.JSQLDMLCreateFactory;

public class TestDBInitalizer {

	public static void main(String[] args) {
		JSQLDDLCreateFactory ddlCreateFactory=new JPropertiesSQLDDLCreateFactory();
		ddlCreateFactory.getObject().create();
		
		JSQLDMLCreateFactory dmlCreateFactory=new JPropertiesSQLDMLCreateFactory();
		dmlCreateFactory.getObject().create();
		
	}
}