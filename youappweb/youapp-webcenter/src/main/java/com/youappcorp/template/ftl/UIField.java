package com.youappcorp.template.ftl;

import j.jave.kernal.jave.model.JModel;

public class UIField implements JModel {

	private String property;
	
	private String getterMethodName;
	
	private String setterMethodName;

	private String column;
	
	private String label;
	
	/**
	 * {@link KeyNames#FIELD_TYPE_NUMERIC} or {@link KeyNames#FIELD_TYPE_STRING} or 
	 * {@link KeyNames#FIELD_TYPE_DATE} ...   ;
	 */
	private String fieldType;
	
	private ModelField source;
	
	/**
	 * {@link KeyNames#SOURCE_TYPE_APPEND} or 
	 * {@link KeyNames#SOURCE_TYPE_CLASS}  ...
	 */
	private String sourceType;
	
	
	/**
	 * amount of columns , like "col-sm-5"
	 */
	private int colNum;
	
	/**
	 * see display:block;
	 */
	private boolean block;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getGetterMethodName() {
		return getterMethodName;
	}

	public void setGetterMethodName(String getterMethodName) {
		this.getterMethodName = getterMethodName;
	}

	public String getSetterMethodName() {
		return setterMethodName;
	}

	public void setSetterMethodName(String setterMethodName) {
		this.setterMethodName = setterMethodName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public ModelField getSource() {
		return source;
	}

	public void setSource(ModelField source) {
		this.source = source;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getColNum() {
		return colNum;
	}

	/**
	 * amount of columns , like "col-sm-5"
	 */
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	/**
	 * see display:block;
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * see display:block;
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}
	
	
	
}
