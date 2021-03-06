package com.youappcorp.project.tablemanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.platform.data.web.model.Criteria;

import java.util.List;

import com.youappcorp.project.tablemanager.model.Column;
import com.youappcorp.project.tablemanager.model.Record;
import com.youappcorp.project.tablemanager.model.Table;

/**
 * 
 * @author J
 */
public interface TableManagerService {
	
	/**
	 * get table
	 * @return
	 */
	public List<Table> getTables();

	/**
	 * get all columns names of a table
	 * 
	 * @param tableName
	 * @return
	 */
	public List<Column> getColumnsByTable( String tableName);
	
	/**
	 * get all columns names of a table
	 * 
	 * @param modelName
	 * @return
	 */
	public List<Column> getColumnsByModelName( String modelName);
	
	/**
	 * query one record .
	 * 
	 * @param model
	 * @return
	 */
	public Record getRecord( JBaseModel model);
	
	/**
	 * query one record .
	 * 
	 * @param modelName
	 * @param id
	 * @return
	 */
	public Record getRecord( String modelName,String id);
	
	/**
	 * query records.
	 * @param searchCreiteria
	 * @return
	 */
	public List<Record> getRecords( Criteria model);
	
	/**
	 * update record. 
	 * 
	 * @param record
	 */
	public void updateRecord(Record record) throws JServiceException;
	
	
	
}
