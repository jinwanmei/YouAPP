package j.jave.platform.sps.multiv;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support._resource.JJARResourceURIScanner;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.sps.core.SpringDynamicJARApplicationContext;
import j.jave.platform.sps.core.SpringDynamicJARApplicationContext.JARScan;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport.Component;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport.ComponentProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

public class DynamicComponentVersionApplication extends ComponentVersionApplication {
	
	public static final JLogger LOGGER=JLoggerFactory.getLogger(ComponentVersionSpringApplicationSupport.class);
	
	private final URL[] jarUrls;
	
	private final URLClassLoader urlClassLoader;

	private SpringDynamicJARApplicationContext applicationContext;
	
	public SpringDynamicJARApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public URLClassLoader getUrlClassLoader() {
		return urlClassLoader;
	}
	
	public URL[] getJarUrls() {
		return jarUrls;
	}
	
	public DynamicComponentVersionApplication(ApplicationContext parent,URL[] jarUrls){
		this.jarUrls=jarUrls;
		applicationContext=new SpringDynamicJARApplicationContext(parent, jarUrls);
		urlClassLoader=applicationContext.getUrlClassLoader();
		initContext();
	}

	public String unique(){
		return ComponentVersionSpringApplicationSupport.unique(app, component, version);
	}
	
	private void initContext(){
		
		findComponentInfo();
		
		applicationContext.setJarScan(new JARScan() {
			@Override
			public List<URI> scan(URL jar, URLClassLoader classLoader) throws Exception {
				JJARResourceURIScanner jarResourceScan=new JJARResourceURIScanner(jar.toURI());
				jarResourceScan.setRelativePath(Component.SPRING_LOCATION);
				return jarResourceScan.scan();
			}
		});
	}
	
	// get all info from component-version.properties.
	private void findComponentInfo(){
		
		InputStream inputStream=null;
		try {
			inputStream=urlClassLoader.findResource(ComponentProperties.PROPERTY_LOCATION).openStream();
			JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(inputStream);
			Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
			app=JPropertiesUtils.getKey(ComponentMetaNames.APP_NAME, properties);
			component=JPropertiesUtils.getKey(ComponentMetaNames.COMPONENT_NAME, properties);
			version=JPropertiesUtils.getKey(ComponentMetaNames.COMPONENT_VERSION, properties);
			urlPrefix=JPropertiesUtils.getKey(ComponentMetaNames.COMPONENT_URL_PREFIX, properties);
			
			if(JStringUtils.isNotNullOrEmpty(urlPrefix)
					&&!(urlPrefix.startsWith("/")&&urlPrefix.endsWith("/"))){
				throw new JInitializationException(" the url prefix format must be '/..../'");
			}
		} catch (Exception e) {
			throw new JInitializationException(e);
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	SpringDynamicJARApplicationContext load(){
		applicationContext.setUnique(unique());
		applicationContext.setComponentVersionApplication(this);
		applicationContext.refresh();
		return applicationContext;
	}
	

	
	
}
