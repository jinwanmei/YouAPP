package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.aop.JServiceMethodInterceptorFactory;
import j.jave.kernal.jave.aop.JAopConfigException;
import j.jave.kernal.jave.aop.JAspectInstanceFactory;
import j.jave.kernal.jave.utils.JAssert;

public class JServiceFactoryAspectInstanceFactory implements JAspectInstanceFactory {

	private final JServiceFactory<?> interceptorFactory;


	/**
	 * Create a new SimpleAspectInstanceFactory for the given service factory.
	 * @param aspectClass the aspect class
	 */
	public JServiceFactoryAspectInstanceFactory(JServiceFactory<?> interceptorFactory) {
		JAssert.notNull(interceptorFactory, "Aspect interceptor factory must not be null");
		this.interceptorFactory = interceptorFactory;
	}

	/**
	 * Return the specified aspect class (never {@code null}).
	 */
	public final Class<?> getAspectClass() {
		return this.interceptorFactory.getServiceClass();
	}


	@Override
	public final Object getAspectInstance() {
		try {
			JServiceMethodInterceptorFactory<?,?> serviceMethodInterceptorFactory=(JServiceMethodInterceptorFactory<?,?>)interceptorFactory;
			return serviceMethodInterceptorFactory.getInterceptor();
		}
		catch (Exception ex) {
			throw new JAopConfigException("Unable to instantiate aspect class [" + getAspectClass().getName() + "]", ex);
		}
	}

	@Override
	public ClassLoader getAspectClassLoader() {
		return getAspectClass().getClassLoader();
	}

}