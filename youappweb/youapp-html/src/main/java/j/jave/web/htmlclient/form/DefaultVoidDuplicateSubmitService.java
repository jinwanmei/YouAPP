package j.jave.web.htmlclient.form;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.concurrent.locks.ReentrantLock;

public class DefaultVoidDuplicateSubmitService extends JServiceFactorySupport<VoidDuplicateSubmitService>
implements VoidDuplicateSubmitService {
	
	private TokenStorageService tokenStorageService=TokenStorageServiceUtil.get();
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public boolean validate(String formId, String token) {
		try{
			lock.lockInterruptibly();
			FormIdentification formIdentification=tokenStorageService.getToken(formId);
			if(formIdentification==null){
				throw new JValidatingException("the form ["+formId+"] is invlaid, check if the form need validate.");
			}
			String storageToken=formIdentification.getToken();
			if(JStringUtils.isNotNullOrEmpty(storageToken)){
				boolean valid= storageToken.equals(token);
				tokenStorageService.removeByFormId(formId);
				return valid;
			}
			else{
				throw new JValidatingException("the form ["+formId+"] is invlaid, "+
						(token==null?"token is missing.":("token : "+token))
						);
			}
		}catch(InterruptedException e){
			return validate(formId, token);
		}catch(Exception e){
			if(JValidatingException.class.isInstance(e)){
				throw (JValidatingException)e;
			}
			throw new JValidatingException(e);
		}finally{
			if(lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
	}
	
	@Override
	public boolean validate(FormIdentification formIdentification) {
		return validate(formIdentification.getFormId(),formIdentification.getToken());
	}
	
	@Override
	public boolean cleanup(String sessionId) {
		return tokenStorageService.removeBySessionId(sessionId);
	}
	
	
	@Override
	public FormIdentification newFormIdentification() {
		FormIdentification formIdentification=new FormIdentification();
		String sessionId=tokenStorageService.getSessionId();
		JAssert.isNotNull(sessionId);
		JAssert.isNotEmpty(sessionId);
		formIdentification.setSessionId(sessionId);
		String appendUnique=JUniqueUtils.unique();
		String formIId=sessionId+"_"+appendUnique;
		formIdentification.setFormId(formIId);
		formIdentification.setToken(JUniqueUtils.unique());
		
		//store ...
		tokenStorageService.store(formIdentification);
		
		return formIdentification;
	}
	
	@Override
	protected VoidDuplicateSubmitService doGetService() {
		return this;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return getClass();
	}
}
