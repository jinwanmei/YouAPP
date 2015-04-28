/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.model.JPagination;

import java.util.List;

/**
 * @author J
 */
public interface UserService extends Service<User> {

	/**
	 * get user by name & password 
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUserByNameAndPassword(String userName,String password);
	
	/**
	 * 
	 * @param context 
	 * @param user
	 * @throws ServiceException
	 */
	public void saveUser(ServiceContext context, User user) throws ServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void updateUser(ServiceContext context, User user) throws ServiceException;
	
	/**
	 * get user by name 
	 * @param context
	 * @param userName
	 * @return
	 */
	public User getUserByName(ServiceContext context, String userName);

	/**
	 * search user 
	 * @param context
	 * @param pagination
	 * @return
	 */
	public List<User> getUsersByPage(ServiceContext context, JPagination pagination) ;
	
	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public User getUserById(ServiceContext context, String id);
	
	/**
	 * all users (not deleted) .
	 * @return
	 */
	public List<User> getUsers();
}
