package j.jave.kernal.jave.aop;

public interface JTargetSource {

	/**
	 * Return the target class behind the implementing object
	 * (typically a proxy configuration or an actual proxy).
	 * @return the target Class, or {@code null} if not known
	 */
	Class<?> getTargetClass();
	
	/**
	 * Will all calls to {@link #getTarget()} return the same object?
	 * <p>In that case, there will be no need to invoke
	 * {@link #releaseTarget(Object)}, and the AOP framework can cache
	 * the return value of {@link #getTarget()}.
	 * @return {@code true} if the target is immutable
	 * @see #getTarget
	 */
	boolean isStatic();

	/**
	 * Return a target instance. Invoked immediately before the
	 * AOP framework calls the "target" of an AOP method invocation.
	 * @return the target object, which contains the joinpoint
	 * @throws Exception if the target object can't be resolved
	 */
	Object getTarget() throws Exception;

	/**
	 * Release the given target object obtained from the
	 * {@link #getTarget()} method.
	 * @param target object obtained from a call to {@link #getTarget()}
	 * @throws Exception if the object can't be released
	 */
	void releaseTarget(Object target) throws Exception;
}
