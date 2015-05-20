package j.jave.framework.components.web.action;

import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.model.JPage;
import j.jave.framework.utils.JStringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic action for view controller.
 * @author J
 */
public abstract class AbstractAction implements Action {
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	protected HTTPContext httpContext;
	
	/**
	 * {@link #setHttpContext(HTTPContext)}
	 */
	public static final String httpContextCallBackName="setHttpContext";
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String EDIT_SUCCESS="更新成功";

	public HTTPContext getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(HTTPContext httpContext) {
		this.httpContext = httpContext;
	}
	
	protected void setAttribute(String key,Object obj){
		httpContext.setAttribute(key, obj);
	}
	
	public String getParameter(String key){
		return httpContext.getParameter(key);
	}
	
	public void setCookie(String key,String value){
		httpContext.setCookie(key, value);
	}
	
	public void setCookie(String key,String value,int maxAge){
		httpContext.setCookie(key, value, maxAge);
	}
	
	public void deleteCookie(String key){
		httpContext.deleteCookie(key);
	}
	
	protected SessionUser getSessionUser(){
		return httpContext.getUser();
	}

	protected void setSuccessMessage(String message){
		if(JStringUtils.isNullOrEmpty(message)){
			message="操作成功";
		}
		setAttribute("successAlertMessage", message);
	}
	
	/**
	 * sub-class should implements the method.
	 * @return
	 */
	protected JPage parseJPage(){
		throw new JOperationNotSupportedException("Not supported,check if the sub-class implements the method.");
	} 
	
	
	
}
