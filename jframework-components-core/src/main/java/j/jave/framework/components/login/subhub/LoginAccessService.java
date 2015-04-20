/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.servicehub.JService;

/**
 * @author Administrator
 *
 */
public interface LoginAccessService  extends JService {
	
	/**
	 * if its valid user, return an unique string. 
	 * else return empty. 
	 * @param name
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public String validate(String name,String password) throws ServiceException;
	
	/**
	 * return true if need login , else return false. 
	 * @param url
	 * @return
	 * @throws ServiceException
	 */
	public boolean  isNeedLoginRole(String url) throws ServiceException;
	
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void register(ServiceContext context,User user) throws ServiceException;
	
	
	/**
	 * if its valid user, return an unique string.  additional do some logic. 
	 * may throws {@link ServiceException} i.e.  用户不存在
	 * @param name
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public String login(String name,String password) throws ServiceException;
	
	
	/**
	 * check whether the resource is authorized. 
	 * @param resource
	 * @param name
	 * @return
	 */
	public boolean authorizeOnUserName(String resource,String name);
	
	
	/**
	 * check whether the resource is authorized. 
	 * @param resource
	 * @param userId
	 * @return
	 */
	public boolean authorizeOnUserId(String resource,String userId);
	
}
