package j.jave.platform.webcomp.web.youappmvc;

import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModel;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.SessionUser;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

public interface HttpContext {
	
	URI getUrl();
	
	/**
	 * @return
	 */
	String getRequestPath();

	public abstract String getParameter(String key);

	public abstract String getHead(String headName);

	public abstract String[] getParameterValues(String key);

	public abstract Object getParameterValue(String key);

	/**
	 * {@link #parameters}, which store query string parameter or any other added by program later.
	 * @return the parameters
	 */
	public abstract Map<String, Object> getParameters();
	
	Collection<String> getKeys();

	public abstract HttpClientInfo getClientInfo();

	public abstract ServiceContext getServiceContext();
	
	public abstract ServiceContext getServiceContext(boolean refresh);
	
	public SessionUser getUser();

	public void setUser(SessionUser user);

	VerMappingMeta getVerMappingMeta();
	
	void setVerMappingMeta(VerMappingMeta verMappingMeta);
	
	JObjectTransModelProtocol getProtocol();
	
	void setProtocol(JObjectTransModelProtocol protocol);
	
	JObjectTransModel getObjectTransModel();
	
	JObjectTransModel setObjectTransModel(JObjectTransModel objectTransModel);
	
}