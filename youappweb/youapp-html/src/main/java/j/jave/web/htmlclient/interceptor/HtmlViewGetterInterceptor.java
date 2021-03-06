package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.HtmlService;
import j.jave.web.htmlclient.SyncHtmlModel;
import j.jave.web.htmlclient.SyncHtmlResponseService;
import j.jave.web.htmlclient.request.RequestHtml;
import j.jave.web.htmlclient.response.SyncHtmlResponse;


/**
 * @author JIAZJ
 *
 */
public class HtmlViewGetterInterceptor implements HtmlRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlViewGetterInterceptor.class);
	
	private HtmlService htmlService=JServiceHubDelegate.get().getService(this, HtmlService.class);
	
	private SyncHtmlResponseService syncHtmlResponseService=SyncHtmlResponseService.get();
	
	@Override
	public Object intercept(HtmlRequestServletRequestInvocation servletRequestInvocation) {
		SyncHtmlResponse syncHtmlResponse=null;
		try{
			RequestHtml requestHtml=servletRequestInvocation.getRequestHtml();
			SyncHtmlModel syncHtmlModel= htmlService.getSyncHtmlModel(requestHtml);
	    	syncHtmlResponse= syncHtmlResponseService.getSyncHtmlResponse(requestHtml, syncHtmlModel);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			syncHtmlResponse=syncHtmlResponseService.miniError(e.getMessage());
		}
		return syncHtmlResponse;
	}
	
	
}
