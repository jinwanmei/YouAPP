/**
 * 
 */
package j.jave.framework.components.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import j.jave.framework.components.core.context.SpringContextSupport;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.reflect.JReflect;
import j.jave.framework.utils.JDateUtils;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * to find target action , then execute method.  
 * <p>any HTTP context without request also can be executed. 
 * as so , you can test the action methods that <strong>never</strong> use the {@link HttpServletRequest} or {@link HttpServletResponse}
 * 
 * @author J
 */
public class ActionExecutor {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ActionExecutor.class);
	
	public Object execute(HTTPContext httpContext) throws Exception{
		
		String targetPath=httpContext.getTargetPath();
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("path processing : "+targetPath);
		}
		
		// resolve the path , then invoke the target method. 
	    // the path like /login.loginaction/toLogin
		
		String serviceName="";;
		String methodName="";
		if(targetPath.startsWith("/")) targetPath=targetPath.substring(1);
		String[] targets=targetPath.split("/");
		serviceName=targets[0];
		methodName=targets[1];
		AbstractAction object=(AbstractAction) SpringContextSupport.getApplicationContext().getBean(serviceName);
		
		// set HTTP context constructed above.
		object.setHttpContext(httpContext);
		//setting attributes associate to the request. 
		if(httpContext.getRequest()==null){
			HTTPUtils.set(object, httpContext);
		}
		else{
			HTTPUtils.set(object, httpContext.getRequest(), httpContext);
		}
		StopWatch stopWatch=null;
		if(LOGGER.isDebugEnabled()){
			stopWatch=new StopWatch();
			stopWatch.start();
		}
		Object navigate=JReflect.invoke(object, methodName, new Object[]{});
		if(LOGGER.isDebugEnabled()){
			try{
				LOGGER.debug("time of processing request in action is :"+JDateUtils.getTimeOffset(stopWatch.getTime()));
			}catch(Exception e){
				LOGGER.error("error to get time.");
			}
		}
		return  navigate;
	}
	
	private static ActionExecutor actionExecutor;
	
	private ActionExecutor() {
	}
	
	public static ActionExecutor newSingleExecutor(){
		if(actionExecutor==null){
			synchronized (ActionExecutor.class) {
				actionExecutor=new ActionExecutor();
			}
		}
		return actionExecutor;
	}
}
