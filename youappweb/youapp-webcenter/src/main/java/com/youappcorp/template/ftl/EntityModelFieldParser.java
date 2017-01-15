package com.youappcorp.template.ftl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.bunny.app._c.jpa.springjpa.query.JEntityColumnMeta;
import me.bunny.app._c.jpa.springjpa.query.JEntityModelMeta;
import me.bunny.app._c.jpa.springjpa.query.JEntityUtilService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class EntityModelFieldParser implements ModelFieldParser {

	private JEntityUtilService entityUtilService
	=JServiceHubDelegate.get().getService(this, JEntityUtilService.class);
	
	@Override
	public List<ModelField> parse(Class<?> clazz) throws Exception {
		List<ModelField> modelFields=new ArrayList<ModelField>();
		JEntityModelMeta entityModelMeta=entityUtilService.getEntityModelMeta(clazz);
		Collection<JEntityColumnMeta> entityColumnMetas=entityModelMeta.columnMetas();
		for(JEntityColumnMeta columnMeta:entityColumnMetas){
			ModelField modelField=new ModelField();
			modelField.setProperty(columnMeta.getProperty());
			modelField.setColumn(columnMeta.getColumn());
			modelField.setField(columnMeta.getField());
			modelField.setSetterMethodName(columnMeta.getSetterMethodName());
			modelField.setGetterMethodName(columnMeta.getGetterMethodName());
			modelField.setFieldType(TemplateUtil.type(columnMeta.getField()));
			modelField.setSourceType(KeyNames.SOURCE_TYPE_CLASS);
			modelFields.add(modelField);
		}
		return modelFields;
	}

}
