package j.jave.kernal.jave.persist;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.JUNDeletable;

import java.io.Serializable;
import java.util.List;

/**
 * the common entry to the database platform.  
 * such as Hibernate, Mybatis. 
 * @author J
 *@param <T> the implementation class. <M>  the model
 */
public interface JIPersist<T,M extends JBaseModel,ID extends Serializable> {
	
	/**
	 * insert new record 
	 * @param baseModel
	 */
	public void saveModel(M baseModel); 
	
	/**
	 * update the existing record, maybe return affect record count, if API support. 
	 * @param baseModel
	 * @return {@link Integer} the affect record count
	 */
	public int updateModel(M baseModel);
	
	/**
	 * get the certain record according to the primary key.
	 * @param id primary key
	 * @param entryName  it maybe a full qualified class name mapping to table.
	 * @return
	 */
	public M getModel(ID id,String... entryName);
	
	/**
	 * delete the existing record permanently . <br/>
	 * note: <br/> the record can not be recovered agian later. 
	 * @param baseModel
	 */
	public void deleteModel(M baseModel);
	
	/**
	 * delete the existing record through marking the column named "deleted";
	 * all implementation should check whether the model implements {@link JUNDeletable}<br/>
	 * if yes , the method should throw an exception shows developer the table does not contain delete column
	 * @param baseModel
	 * @see {@link JUNDeletable}
	 */
	public void markModelDeleted(M baseModel);
	
	/**
	 * get the current instance 
	 * @return
	 */
	public T getInstance();
	
	/**
	 * delete the existing record through marking the column named "deleted";
	 * all implementation should check whether the model implements {@link JUNDeletable}<br/>
	 * if yes , the method should throw an exception shows developer the table does not contain delete column
	 * @param id
	 * @see {@link JUNDeletable}
	 */
	public void markModelDeleted(ID id);
	
	/**
	 * get records thats matches the criteria , the sub-implementation must be for pagination.
	 * <p>To the Mybatis, the SQL with the id of *ByPage can be intercepted by {@link JPagePlugin},
	 * <p>in the case, the method uses to be for searching a single table. like (select * from dual ). to manager data of a table. 
	 * @param pagination
	 * @return 
	 */
	public List<M> getModelsByPage(JPageable pagination);
	
	/**
	 * all recoreds return.
	 * @return
	 */
	public List<M> getAllModels();
	
}
