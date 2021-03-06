package j.jave.kernal.container._resource;

import j.jave.kernal.container.JScheme;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

import java.net.URI;

public class JHttpURLConnectionProcessorServiceFactory
extends JServiceFactorySupport<JHttpURLConnectionProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JHttpURLConnectionProcessorService> 
,JService{

	@Override
	public JHttpURLConnectionProcessorService getSchemeProcessorService() {
		return new JHttpURLConnectionProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.HTTP.getValue().equals(uri.getScheme())
				||JScheme.HTTPS.getValue().equals(uri.getScheme())
				;
	}


	@Override
	protected JHttpURLConnectionProcessorServiceFactory doGetService() {
		return this;
	}
	

}
