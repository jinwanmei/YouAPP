package j.jave.kernal.container._resource;

import j.jave.kernal.container.JScheme;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

import java.net.URI;

public class JJarFileProcessorServiceFactory
extends JServiceFactorySupport<JJarFileProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JJarFileProcessorService> ,JService
{

	@Override
	public JJarFileProcessorService getSchemeProcessorService() {
		return new JJarFileProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.JAR.getValue().equals(uri.getScheme())
				;
	}


	@Override
	protected JJarFileProcessorServiceFactory doGetService() {
		return this;
	}
	

}
