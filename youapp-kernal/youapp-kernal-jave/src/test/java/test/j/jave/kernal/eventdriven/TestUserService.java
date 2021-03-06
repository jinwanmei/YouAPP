package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.transaction.JTransactional;

@JTransactional
public class TestUserService extends JServiceFactorySupport<TestUserService> implements JService , UserFindListener {

	public TestUserService() {
		super(TestUserService.class);
	}

	@Override
	public Object trigger(UserFindEvent event) {
		return "jia.zhong.jin".equalsIgnoreCase(event.getName())+"[jia.zhong.jin]";
	}

	@Override
	protected TestUserService doGetService() {
		return new TestUserService();
	}
	
	public void save(String name){
		System.out.println("----------save name: "+name+" ---------------");
		throw new RuntimeException("-------rollback...------------");
	}
	
}
