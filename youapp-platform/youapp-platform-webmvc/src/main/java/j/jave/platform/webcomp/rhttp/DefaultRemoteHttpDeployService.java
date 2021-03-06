package j.jave.platform.webcomp.rhttp;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.rhttp.JRemoteHttpContainerConfig;
import j.jave.kernal.container.rhttp.JRemoteURIInfo;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.http.JHttpType;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.sps.core.container.DynamicSpringContainerConfig;
import j.jave.platform.sps.core.context.SpringContextSupport;
import j.jave.platform.sps.multiv.ComponentVersionTestApplication;
import j.jave.platform.sps.multiv.DynamicComponentVersionApplication;
import j.jave.platform.sps.multiv.RemoteHttpComponentVersionApplication;
import j.jave.platform.webcomp.rhttp.model.AppDeploy;
import j.jave.platform.webcomp.rhttp.model.AppDeployMeta;
import j.jave.platform.webcomp.rhttp.model.URLMappingDeployMeta;
import j.jave.platform.webcomp.web.youappmvc.container.DefaultControllerMockObjectGetter;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeContainer;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeContainerConfig;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeTestContainerConfig;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.springframework.context.ApplicationContext;


/**
 * deploy/register remote HTTP.
 * @author JIAZJ
 *
 */
public class DefaultRemoteHttpDeployService
extends JServiceFactorySupport<DefaultRemoteHttpDeployService>
implements JService {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(DefaultRemoteHttpDeployService.class);
	
	private HttpInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	
	/**
	 * deploy the APP into the container hub.
	 * @param app
	 * @return the container unique.
	 * @throws Exception
	 */
	public String deploy(AppDeploy app) throws Exception{
		
		AppDeployMeta appMeta=app.getAppMeta();
		
		RemoteHttpComponentVersionApplication versionApplication=new RemoteHttpComponentVersionApplication();
		versionApplication.setApp(appMeta.getAppName());
		versionApplication.setComponent(appMeta.getAppCompName());
		versionApplication.setVersion(appMeta.getAppVersion());
		versionApplication.setUrlPrefix("");
		versionApplication.setFriendlyUrl(appMeta.getFriendlyUrl());
		
		JRemoteHttpContainerConfig remoteHttpContainerConfig=new JRemoteHttpContainerConfig();
		remoteHttpContainerConfig.setName(versionApplication.getApp());
		remoteHttpContainerConfig.setUnique(versionApplication.unique());
		remoteHttpContainerConfig.setHost(appMeta.getAppHost());
		String unique=requestInvokeContainerDelegateService.newInstance(remoteHttpContainerConfig);
		LOGGER.info(appMeta.getAppUnique()+" has startup with the unique id ["+unique+"]");
		List<URLMappingDeployMeta> urlMappingMetas=app.getUrlMappingMetas();
		if(JCollectionUtils.hasInCollect(urlMappingMetas)){
			for(URLMappingDeployMeta urlMappingMeta:urlMappingMetas){
				String put=requestInvokeContainerDelegateService
						.getPutRequestURI(unique, urlMappingMeta.getUrl());
				JRemoteURIInfo remoteURIInfo=new JRemoteURIInfo();
				remoteURIInfo.setPath(urlMappingMeta.getUrl());
				remoteURIInfo.setHttpType(JHttpType.valueOf(urlMappingMeta.getUrlType().toUpperCase()));
				requestInvokeContainerDelegateService
					.execute(new URI(put), remoteURIInfo, unique, true);
			}
		}
		return unique;
	}
	
	
	/**
	 * delegate to {@link #deploy(AppDeploy)}
	 * @param appJson  the JSON format of {@link AppDeploy}
	 * @return  the container unique
	 * @throws Exception
	 */
	public String deploy(String appJson) throws Exception{
		return deploy(JJSON.get().parse(appJson, AppDeploy.class));
	}
	
	public String deployJar(String jarUrl) throws Exception{

		ApplicationContext applicationContext=SpringContextSupport.getApplicationContext();

		URL[] jarUrls=new URL[]{new URI(jarUrl).toURL()};
		
		DynamicSpringContainerConfig dynamicSpringContainerConfig=new DynamicSpringContainerConfig();
		dynamicSpringContainerConfig.setJarUrls(jarUrls);
		dynamicSpringContainerConfig.setApplicationContext(applicationContext);
		
		InnerHttpInvokeContainerConfig containerConfig=new InnerHttpInvokeContainerConfig();
		containerConfig.setSpringContainerConfig(dynamicSpringContainerConfig);
		
		DynamicComponentVersionApplication dynamicComponentVersionApplication
			=new DynamicComponentVersionApplication(applicationContext, jarUrls);
		String unique=requestInvokeContainerDelegateService.newInstance(containerConfig, dynamicComponentVersionApplication);
		
		//startup test container.
		
		ComponentVersionTestApplication componentVersionTestApplication
		=new ComponentVersionTestApplication(dynamicComponentVersionApplication.getApp(),
				dynamicComponentVersionApplication.getComponent()+"-TEST", 
				dynamicComponentVersionApplication.getVersion(), 
				dynamicComponentVersionApplication.getUrlPrefix());
		
		InnerHttpInvokeTestContainerConfig testConfig=new InnerHttpInvokeTestContainerConfig();
		testConfig.setSpringContainerConfig(dynamicSpringContainerConfig);
		requestInvokeContainerDelegateService.newInstance(testConfig, componentVersionTestApplication,
				(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
		
		//startup mock container.
		ComponentVersionTestApplication mockApplication
		=new ComponentVersionTestApplication(dynamicComponentVersionApplication.getApp(),
				dynamicComponentVersionApplication.getComponent()+"-MOCK", 
				dynamicComponentVersionApplication.getVersion(), 
				dynamicComponentVersionApplication.getUrlPrefix());
		
		InnerHttpInvokeTestContainerConfig mockConfig=new InnerHttpInvokeTestContainerConfig();
		mockConfig.setSpringContainerConfig(dynamicSpringContainerConfig);
		mockConfig.setControllerObjectGetter(new DefaultControllerMockObjectGetter());
		requestInvokeContainerDelegateService.newInstance(mockConfig, mockApplication,
				(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
		
		return unique;
	}
	
	@Override
	protected DefaultRemoteHttpDeployService doGetService() {
		return this;
	}
}
