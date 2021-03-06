package j.jave.platform.webcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.exception.JConcurrentException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.interceptor.JDefaultModelInvocation;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.data.web.model.SimplePageRequest;
import j.jave.platform.jpa.springjpa.query.JSingleEntityQuery;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public abstract class InternalServiceSupport<T extends JBaseModel> implements InternalService<T,String>{
	
	protected final JLogger logger=JLoggerFactory.getLogger(getClass());
	
	protected Class<?> entityClass=null;
	
	public InternalServiceSupport() {
		ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass=(Class<T>) type.getActualTypeArguments()[0];
	}
	
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOnly( T object)
			throws JServiceException {
		proxyOnSave(getRepo(), ServiceContextHolder.get(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOnly( T object)
			throws JServiceException {
		proxyOnUpdate(getRepo(), ServiceContextHolder.get(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete( String id) {
		getRepo().markModelDeleted(id);
	}
	
	@Override
	public void delete( T id) {
		getRepo().markModelDeleted(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getById( String id) {
		return getRepo().getModel(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JPage<T> getsByPage( JPageable pagination) {
		JPageImpl<T> page=new JPageImpl<T>();
		List<T> records=getRepo().getModelsByPage(pagination);
		page.setPageable(pagination);
		page.setTotalRecordNumber(records.size());
		page.setContent(records);
		page.setTotalPageNumber(JPageImpl.caculateTotalPageNumber(records.size(), pagination.getPageSize()));
		return page;
	}

	public abstract JIPersist<?,T,String> getRepo();
	
	
	/**
	 * fill in common info.  to execute 
	 * @param jMapper
	 * @param authorizer    generally its login user 
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T proxyOnSave(JIPersist<?, T,String> repo, ServiceContext authorizer, JBaseModel baseModel){
		baseModel.setCreateId(authorizer.getUserId());
		baseModel.setCreateTime(new Timestamp(new Date().getTime()));
		baseModel.setUpdateId(authorizer.getUserId());
		baseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		baseModel.setVersion(1);
		baseModel.setDeleted("N");
		baseModel.setId(JUniqueUtils.unique());
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(baseModel).proceed();
		
		repo.saveModel((T) baseModel);
		return (T) baseModel;
	}
	
	private T get(JIPersist<?, T,String> repo,String id){
		return repo.getModel(id);
	}
	
	/**
	 * fill in common info.
	 * also validate whether the version changes, then to execute 
	 * @param jMapper
	 * @param user
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T proxyOnUpdate(JIPersist<?, T,String> repo, ServiceContext authorizer, JBaseModel baseModel){
		baseModel.setUpdateId(authorizer.getUserId());
		baseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(baseModel).proceed();
		
		JBaseModel dbModel=get(repo, baseModel.getId());
		if(dbModel.getVersion()!=baseModel.getVersion()){
			throw new JConcurrentException("version chaged , db verion is "+dbModel.getVersion()
					+" , but current version is  "+baseModel.getVersion());
		}
		baseModel.setCreateTime(dbModel.getCreateTime());
		baseModel.setCreateId(dbModel.getCreateId());
		baseModel.setVersion(baseModel.getVersion()+1);
		int affect=repo.updateModel((T) baseModel);
		if(affect==0) throw new JConcurrentException(
				"record conflict on "+baseModel.getId()+" of "+baseModel.getClass().getName());
		return (T) baseModel;
	}
	
	/**
	 * return the complete {@link JPage} instance.
	 * @param returnPage  THE {@link Page} instance returned from underlying ORM
	 * @param pageable the passing pagination parameters
	 * @return
	 */
	protected <M extends JModel> JPage<M> toJPage(Page<M> returnPage,JPageable pageable){
		return ServiceSupportUtil.toJPage(returnPage, pageable);
	}
	
	protected SimplePageRequest toPageRequest(JPageable pageable){
		return ServiceSupportUtil.toPageRequest(pageable);
	}
	
	public List<T> getAllModels(ServiceContext context){
		return getRepo().getAllModels();
	}
	
	
	public JSingleEntityQuery singleEntityQuery(){
		return new JSingleEntityQuery(entityClass, em);
	}
	
}
