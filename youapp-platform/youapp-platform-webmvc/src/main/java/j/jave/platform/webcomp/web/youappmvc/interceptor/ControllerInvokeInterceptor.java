package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.RequestContext;
import j.jave.platform.webcomp.web.youappmvc.ResponseContext;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.jsonview.JSONServletViewHandler;
import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.net.URI;

public class ControllerInvokeInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerInvokeInterceptor.class);
	
	private JServletViewHandler servletViewHandler=new JSONServletViewHandler();
	
	private HttpInvokeContainerDelegateService httpInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		RequestContext req=servletRequestInvocation.getRequestContext();
		ResponseContext resp=servletRequestInvocation.getResponseContext();
		HttpContext httpContext=servletRequestInvocation.getHttpContext();
		try{
			String target=httpContext.getVerMappingMeta().getMappingPath();
			Object navigate=
					httpInvokeContainerDelegateService.execute(
							new URI(httpInvokeContainerDelegateService.getExecuteRequestURI(httpContext.getVerMappingMeta().getUnique(), target))
							,httpContext,httpContext.getVerMappingMeta().getUnique());
					
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("the response of "+httpContext.getUrl().toString()+" is ok!");
			}
			// if response for download.
			if(JFile.class.isInstance(navigate)){
				JFile file=(JFile)navigate;
				return file;
			}
			else{
				return servletViewHandler.handleNavigate(req, resp,httpContext, navigate);
			}
		}
		catch(JServiceException e){
			LOGGER.error(e.getMessage(),e);
			return servletViewHandler.handleServiceExcepion(req, resp, httpContext, e);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return servletViewHandler.handleExcepion(req, resp,httpContext,  e);
		}
		
	}
	
	
	
}
