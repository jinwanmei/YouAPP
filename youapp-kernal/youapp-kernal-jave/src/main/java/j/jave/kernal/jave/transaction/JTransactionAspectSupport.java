package j.jave.kernal.jave.transaction;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.aop.JNamedThreadLocal;
import j.jave.kernal.jave.transaction.eventlistener.JTransactionManagerGetEvent;
import j.jave.kernal.jave.utils.JStringUtils;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class JTransactionAspectSupport {

	/**
	 * Key to use to store the default transaction manager.
	 */
	private final Object DEFAULT_TRANSACTION_MANAGER_KEY = new Object();

	// NOTE: This class must not implement Serializable because it serves as base
	// class for AspectJ aspects (which are not allowed to implement Serializable)!

	/**
	 * Holder to support the {@code currentTransactionStatus()} method,
	 * and to support communication between different cooperating advices
	 * (e.g. before and after advice) if the aspect involves more than a
	 * single method (as will be the case for around advice).
	 */
	private static final ThreadLocal<TransactionInfo> transactionInfoHolder =
			new JNamedThreadLocal<TransactionInfo>("Current aspect-driven transaction");


	private final ConcurrentHashMap<Object, JPlatformTransactionManager> transactionManagerCache =
			new ConcurrentHashMap<Object, JPlatformTransactionManager>();

	/**
	 * Subclasses can use this to return the current TransactionInfo.
	 * Only subclasses that cannot handle all operations in one method,
	 * such as an AspectJ aspect involving distinct before and after advice,
	 * need to use this mechanism to get at the current TransactionInfo.
	 * An around advice such as an AOP Alliance MethodInterceptor can hold a
	 * reference to the TransactionInfo throughout the aspect method.
	 * <p>A TransactionInfo will be returned even if no transaction was created.
	 * The {@code TransactionInfo.hasTransaction()} method can be used to query this.
	 * <p>To find out about specific transaction characteristics, consider using
	 * TransactionSynchronizationManager's {@code isSynchronizationActive()}
	 * and/or {@code isActualTransactionActive()} methods.
	 * @return TransactionInfo bound to this thread, or {@code null} if none
	 * @see TransactionInfo#hasTransaction()
	 * @see org.springframework.transaction.support.TransactionSynchronizationManager#isSynchronizationActive()
	 * @see org.springframework.transaction.support.TransactionSynchronizationManager#isActualTransactionActive()
	 */
	protected static TransactionInfo currentTransactionInfo() throws JNoTransactionException {
		return transactionInfoHolder.get();
	}

	/**
	 * Return the transaction status of the current method invocation.
	 * Mainly intended for code that wants to set the current transaction
	 * rollback-only but not throw an application exception.
	 * @throws NoTransactionException if the transaction info cannot be found,
	 * because the method was invoked outside an AOP invocation context
	 */
	public static JTransactionStatus currentTransactionStatus() throws JNoTransactionException {
		TransactionInfo info = currentTransactionInfo();
		if (info == null || info.transactionStatus == null) {
			throw new JNoTransactionException("No transaction aspect-managed TransactionStatus in scope");
		}
		return info.transactionStatus;
	}


	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Default transaction manager bean name.
	 */
	private String transactionManagerBeanName;

	private JTransactionAttributeSource transactionAttributeSource;

	/**
	 * Specify the name of the default transaction manager bean.
	 */
	public void setTransactionManagerBeanName(String transactionManagerBeanName) {
		this.transactionManagerBeanName = transactionManagerBeanName;
	}

	/**
	 * Return the name of the default transaction manager bean.
	 */
	protected final String getTransactionManagerBeanName() {
		return this.transactionManagerBeanName;
	}

	/**
	 * Specify the <em>default</em> transaction manager to use to drive transactions.
	 * <p>The default transaction manager will be used if a <em>qualifier</em>
	 * has not been declared for a given transaction or if an explicit name for the
	 * default transaction manager bean has not been specified.
	 * @see #setTransactionManagerBeanName
	 */
	public void setTransactionManager(JPlatformTransactionManager transactionManager) {
		if (transactionManager != null) {
			this.transactionManagerCache.put(DEFAULT_TRANSACTION_MANAGER_KEY, transactionManager);
		}
	}

	/**
	 * Return the default transaction manager, or {@code null} if unknown.
	 */
	public JPlatformTransactionManager getTransactionManager() {
		return this.transactionManagerCache.get(DEFAULT_TRANSACTION_MANAGER_KEY);
	}

	/**
	 * Set properties with method names as keys and transaction attribute
	 * descriptors (parsed via TransactionAttributeEditor) as values:
	 * e.g. key = "myMethod", value = "PROPAGATION_REQUIRED,readOnly".
	 * <p>Note: Method names are always applied to the target class,
	 * no matter if defined in an interface or the class itself.
	 * <p>Internally, a NameMatchTransactionAttributeSource will be
	 * created from the given properties.
	 * @see #setTransactionAttributeSource
	 * @see TransactionAttributeEditor
	 * @see NameMatchTransactionAttributeSource
	 */
	public void setTransactionAttributes(Properties transactionAttributes) {
		JNameMatchTransactionAttributeSource tas = new JNameMatchTransactionAttributeSource();
		tas.setProperties(transactionAttributes);
		this.transactionAttributeSource = tas;
	}

	/**
	 * Set multiple transaction attribute sources which are used to find transaction
	 * attributes. Will build a CompositeTransactionAttributeSource for the given sources.
	 * @see CompositeTransactionAttributeSource
	 * @see MethodMapTransactionAttributeSource
	 * @see NameMatchTransactionAttributeSource
	 * @see org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
	 */
	public void setTransactionAttributeSources(JTransactionAttributeSource[] transactionAttributeSources) {
		this.transactionAttributeSource = new JCompositeTransactionAttributeSource(transactionAttributeSources);
	}

	/**
	 * Set the transaction attribute source which is used to find transaction
	 * attributes. If specifying a String property value, a PropertyEditor
	 * will create a MethodMapTransactionAttributeSource from the value.
	 * @see TransactionAttributeSourceEditor
	 * @see MethodMapTransactionAttributeSource
	 * @see NameMatchTransactionAttributeSource
	 * @see org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
	 */
	public void setTransactionAttributeSource(JTransactionAttributeSource transactionAttributeSource) {
		this.transactionAttributeSource = transactionAttributeSource;
	}

	/**
	 * Return the transaction attribute source.
	 */
	public JTransactionAttributeSource getTransactionAttributeSource() {
		return this.transactionAttributeSource;
	}


	/**
	 * General delegate for around-advice-based subclasses, delegating to several other template
	 * methods on this class. Able to handle {@link CallbackPreferringPlatformTransactionManager}
	 * as well as regular {@link PlatformTransactionManager} implementations.
	 * @param method the Method being invoked
	 * @param targetClass the target class that we're invoking the method on
	 * @param invocation the callback to use for proceeding with the target invocation
	 * @return the return value of the method, if any
	 * @throws Throwable propagated from the target invocation
	 */
	protected Object invokeWithinTransaction(Method method, Class<?> targetClass, final InvocationCallback invocation)
			throws Throwable {

		// If the transaction attribute is null, the method is non-transactional.
		final JTransactionAttribute txAttr = getTransactionAttributeSource().getTransactionAttribute(method, targetClass);
		final JPlatformTransactionManager tm = determineTransactionManager(txAttr);
		final String joinpointIdentification = methodIdentification(method, targetClass);

		if (txAttr == null || !(tm instanceof JCallbackPreferringPlatformTransactionManager)) {
			// Standard transaction demarcation with getTransaction and commit/rollback calls.
			TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
			Object retVal = null;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceedWithInvocation();
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);
				throw ex;
			}
			finally {
				cleanupTransactionInfo(txInfo);
			}
			commitTransactionAfterReturning(txInfo);
			return retVal;
		}

		else {
			// It's a CallbackPreferringPlatformTransactionManager: pass a TransactionCallback in.
			try {
				Object result = ((JCallbackPreferringPlatformTransactionManager) tm).execute(txAttr,
						new JTransactionCallback<Object>() {
							@Override
							public Object doInTransaction(JTransactionStatus status) {
								TransactionInfo txInfo = prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
								try {
									return invocation.proceedWithInvocation();
								}
								catch (Throwable ex) {
									if (txAttr.rollbackOn(ex)) {
										// A RuntimeException: will lead to a rollback.
										if (ex instanceof RuntimeException) {
											throw (RuntimeException) ex;
										}
										else {
											throw new ThrowableHolderException(ex);
										}
									}
									else {
										// A normal return value: will lead to a commit.
										return new ThrowableHolder(ex);
									}
								}
								finally {
									cleanupTransactionInfo(txInfo);
								}
							}
						});

				// Check result: It might indicate a Throwable to rethrow.
				if (result instanceof ThrowableHolder) {
					throw ((ThrowableHolder) result).getThrowable();
				}
				else {
					return result;
				}
			}
			catch (ThrowableHolderException ex) {
				throw ex.getCause();
			}
		}
	}

	/**
	 * Clear the cache.
	 */
	protected void clearTransactionManagerCache() {
		this.transactionManagerCache.clear();
	}

	/**
	 * Determine the specific transaction manager to use for the given transaction.
	 */
	protected JPlatformTransactionManager determineTransactionManager(JTransactionAttribute txAttr) {
		// Do not attempt to lookup tx manager if no tx attributes are set
		if (txAttr == null
//				|| this.beanFactory == null
				) {
			return getTransactionManager();
		}
		String qualifier = txAttr.getQualifier();
		if (JStringUtils.isNotNullOrEmpty(qualifier)) {
			return determineQualifiedTransactionManager(qualifier);
		}
		else if (JStringUtils.isNotNullOrEmpty(this.transactionManagerBeanName)) {
			return determineQualifiedTransactionManager(this.transactionManagerBeanName);
		}
		else {
			JPlatformTransactionManager defaultTransactionManager = getTransactionManager();
			if (defaultTransactionManager == null) {
				
//				defaultTransactionManager = this.beanFactory.getBean(JPlatformTransactionManager.class);
				defaultTransactionManager=determineTransactionManagerInServiceHub(txAttr);
				this.transactionManagerCache.putIfAbsent(
						DEFAULT_TRANSACTION_MANAGER_KEY, defaultTransactionManager);
			}
			return defaultTransactionManager;
		}
	}
	
	private JPlatformTransactionManager determineTransactionManagerInServiceHub(JTransactionAttribute txAttr){
		return JServiceHubDelegate.get().addImmediateEvent(new JTransactionManagerGetEvent(this, null),JPlatformTransactionManager.class);
	}

	private JPlatformTransactionManager determineQualifiedTransactionManager(String qualifier) {
		JPlatformTransactionManager txManager = this.transactionManagerCache.get(qualifier);
		if (txManager == null) {
//			txManager = BeanFactoryAnnotationUtils.qualifiedBeanOfType(
//					this.beanFactory, PlatformTransactionManager.class, qualifier);
			txManager=determineTransactionManagerInServiceHub(null);
			this.transactionManagerCache.putIfAbsent(qualifier, txManager);
		}
		return txManager;
	}

	/**
	 * Convenience method to return a String representation of this Method
	 * for use in logging. Can be overridden in subclasses to provide a
	 * different identifier for the given method.
	 * @param method the method we're interested in
	 * @param targetClass the class that the method is being invoked on
	 * @return a String representation identifying this method
	 * @see org.springframework.util.ClassUtils#getQualifiedMethodName
	 */
	protected String methodIdentification(Method method, Class<?> targetClass) {
		return (targetClass != null ? targetClass : method.getDeclaringClass()).getName() + "." + method.getName();
	}

	/**
	 * Create a transaction if necessary based on the given TransactionAttribute.
	 * <p>Allows callers to perform custom TransactionAttribute lookups through
	 * the TransactionAttributeSource.
	 * @param txAttr the TransactionAttribute (may be {@code null})
	 * @param joinpointIdentification the fully qualified method name
	 * (used for monitoring and logging purposes)
	 * @return a TransactionInfo object, whether or not a transaction was created.
	 * The {@code hasTransaction()} method on TransactionInfo can be used to
	 * tell if there was a transaction created.
	 * @see #getTransactionAttributeSource()
	 */
	@SuppressWarnings("serial")
	protected TransactionInfo createTransactionIfNecessary(
			JPlatformTransactionManager tm, JTransactionAttribute txAttr, final String joinpointIdentification) {

		// If no name specified, apply method identification as transaction name.
		if (txAttr != null && txAttr.getName() == null) {
			txAttr = new JDelegatingTransactionAttribute(txAttr) {
				@Override
				public String getName() {
					return joinpointIdentification;
				}
			};
		}

		JTransactionStatus status = null;
		if (txAttr != null) {
			if (tm != null) {
				status = tm.getTransaction(txAttr);
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("Skipping transactional joinpoint [" + joinpointIdentification +
							"] because no transaction manager has been configured");
				}
			}
		}
		return prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
	}

	/**
	 * Prepare a TransactionInfo for the given attribute and status object.
	 * @param txAttr the TransactionAttribute (may be {@code null})
	 * @param joinpointIdentification the fully qualified method name
	 * (used for monitoring and logging purposes)
	 * @param status the TransactionStatus for the current transaction
	 * @return the prepared TransactionInfo object
	 */
	protected TransactionInfo prepareTransactionInfo(JPlatformTransactionManager tm,
			JTransactionAttribute txAttr, String joinpointIdentification, JTransactionStatus status) {

		TransactionInfo txInfo = new TransactionInfo(tm, txAttr, joinpointIdentification);
		if (txAttr != null) {
			// We need a transaction for this method
			if (logger.isTraceEnabled()) {
				logger.trace("Getting transaction for [" + txInfo.getJoinpointIdentification() + "]");
			}
			// The transaction manager will flag an error if an incompatible tx already exists
			txInfo.newTransactionStatus(status);
		}
		else {
			// The TransactionInfo.hasTransaction() method will return
			// false. We created it only to preserve the integrity of
			// the ThreadLocal stack maintained in this class.
			if (logger.isTraceEnabled())
				logger.trace("Don't need to create transaction for [" + joinpointIdentification +
						"]: This method isn't transactional.");
		}

		// We always bind the TransactionInfo to the thread, even if we didn't create
		// a new transaction here. This guarantees that the TransactionInfo stack
		// will be managed correctly even if no transaction was created by this aspect.
		txInfo.bindToThread();
		return txInfo;
	}

	/**
	 * Execute after successful completion of call, but not after an exception was handled.
	 * Do nothing if we didn't create a transaction.
	 * @param txInfo information about the current transaction
	 */
	protected void commitTransactionAfterReturning(TransactionInfo txInfo) {
		if (txInfo != null && txInfo.hasTransaction()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() + "]");
			}
			txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
		}
	}

	/**
	 * Handle a throwable, completing the transaction.
	 * We may commit or roll back, depending on the configuration.
	 * @param txInfo information about the current transaction
	 * @param ex throwable encountered
	 */
	protected void completeTransactionAfterThrowing(TransactionInfo txInfo, Throwable ex) {
		if (txInfo != null && txInfo.hasTransaction()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() +
						"] after exception: " + ex);
			}
			if (txInfo.transactionAttribute.rollbackOn(ex)) {
				try {
					txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
				}
				catch (JTransactionSystemException ex2) {
					logger.error("Application exception overridden by rollback exception", ex);
					ex2.initApplicationException(ex);
					throw ex2;
				}
				catch (RuntimeException ex2) {
					logger.error("Application exception overridden by rollback exception", ex);
					throw ex2;
				}
				catch (Error err) {
					logger.error("Application exception overridden by rollback error", ex);
					throw err;
				}
			}
			else {
				// We don't roll back on this exception.
				// Will still roll back if TransactionStatus.isRollbackOnly() is true.
				try {
					txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
				}
				catch (JTransactionSystemException ex2) {
					logger.error("Application exception overridden by commit exception", ex);
					ex2.initApplicationException(ex);
					throw ex2;
				}
				catch (RuntimeException ex2) {
					logger.error("Application exception overridden by commit exception", ex);
					throw ex2;
				}
				catch (Error err) {
					logger.error("Application exception overridden by commit error", ex);
					throw err;
				}
			}
		}
	}

	/**
	 * Reset the TransactionInfo ThreadLocal.
	 * <p>Call this in all cases: exception or normal return!
	 * @param txInfo information about the current transaction (may be {@code null})
	 */
	protected void cleanupTransactionInfo(TransactionInfo txInfo) {
		if (txInfo != null) {
			txInfo.restoreThreadLocalStatus();
		}
	}


	/**
	 * Opaque object used to hold Transaction information. Subclasses
	 * must pass it back to methods on this class, but not see its internals.
	 */
	protected final class TransactionInfo {

		private final JPlatformTransactionManager transactionManager;

		private final JTransactionAttribute transactionAttribute;

		private final String joinpointIdentification;

		private JTransactionStatus transactionStatus;

		private TransactionInfo oldTransactionInfo;

		public TransactionInfo(JPlatformTransactionManager transactionManager,
				JTransactionAttribute transactionAttribute, String joinpointIdentification) {
			this.transactionManager = transactionManager;
			this.transactionAttribute = transactionAttribute;
			this.joinpointIdentification = joinpointIdentification;
		}

		public JPlatformTransactionManager getTransactionManager() {
			return this.transactionManager;
		}

		public JTransactionAttribute getTransactionAttribute() {
			return this.transactionAttribute;
		}

		/**
		 * Return a String representation of this joinpoint (usually a Method call)
		 * for use in logging.
		 */
		public String getJoinpointIdentification() {
			return this.joinpointIdentification;
		}

		public void newTransactionStatus(JTransactionStatus status) {
			this.transactionStatus = status;
		}

		public JTransactionStatus getTransactionStatus() {
			return this.transactionStatus;
		}

		/**
		 * Return whether a transaction was created by this aspect,
		 * or whether we just have a placeholder to keep ThreadLocal stack integrity.
		 */
		public boolean hasTransaction() {
			return (this.transactionStatus != null);
		}

		private void bindToThread() {
			// Expose current TransactionStatus, preserving any existing TransactionStatus
			// for restoration after this transaction is complete.
			this.oldTransactionInfo = transactionInfoHolder.get();
			transactionInfoHolder.set(this);
		}

		private void restoreThreadLocalStatus() {
			// Use stack to restore old transaction TransactionInfo.
			// Will be null if none was set.
			transactionInfoHolder.set(this.oldTransactionInfo);
		}

		@Override
		public String toString() {
			return this.transactionAttribute.toString();
		}
	}


	/**
	 * Simple callback interface for proceeding with the target invocation.
	 * Concrete interceptors/aspects adapt this to their invocation mechanism.
	 */
	protected interface InvocationCallback {

		Object proceedWithInvocation() throws Throwable;
	}


	/**
	 * Internal holder class for a Throwable, used as a return value
	 * from a TransactionCallback (to be subsequently unwrapped again).
	 */
	private static class ThrowableHolder {

		private final Throwable throwable;

		public ThrowableHolder(Throwable throwable) {
			this.throwable = throwable;
		}

		public final Throwable getThrowable() {
			return this.throwable;
		}
	}


	/**
	 * Internal holder class for a Throwable, used as a RuntimeException to be
	 * thrown from a TransactionCallback (and subsequently unwrapped again).
	 */
	@SuppressWarnings("serial")
	private static class ThrowableHolderException extends RuntimeException {

		public ThrowableHolderException(Throwable throwable) {
			super(throwable);
		}

		@Override
		public String toString() {
			return getCause().toString();
		}
	}

}

