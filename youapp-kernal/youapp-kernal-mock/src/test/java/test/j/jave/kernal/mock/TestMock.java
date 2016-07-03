package test.j.jave.kernal.mock;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.utils.JURIUtils;
import j.jave.kernal.mock.JDefaultJSONMockService;
import j.jave.kernal.mock.JDefaultMockURIPrefix;
import j.jave.kernal.mock.JJSONMockModelParser;
import j.jave.kernal.mock.JJSONMockService;
import j.jave.kernal.mock.JMethodNameAsFileParser;
import j.jave.kernal.mock.JMockContext;
import j.jave.kernal.mock.JMockModel;
import j.jave.kernal.mock.JMockProperties;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class TestMock extends TestCase {

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();

	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	@Before
	public void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
	@Test
	public void testMock() throws Exception{
		JConfiguration configuration=JConfiguration.get();
		JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(JURIUtils.getURIRoot(
				configuration.getString(JMockProperties.YOUAPP_MOCK_URI_ROOT), Thread.currentThread().getContextClassLoader()));
		JJSONMockModelParser mockModelParser=new JMethodNameAsFileParser(pathRoot);
		JMockContext context=new JMockContext();
		JDefaultMethodMeta methodMeta=new JDefaultMethodMeta();
		methodMeta.setClazz(this.getClass());
		methodMeta.setMethodName("testMock");
		JMockModel mockModel=mockModelParser.parse(methodMeta, context);
		System.out.println(mockModel); 
		
		JJSONMockService jsonMockService= new JDefaultJSONMockService();
		String data=jsonMockService.mockData(mockModel);
		System.out.println(data);
		
	}
	
	
}
