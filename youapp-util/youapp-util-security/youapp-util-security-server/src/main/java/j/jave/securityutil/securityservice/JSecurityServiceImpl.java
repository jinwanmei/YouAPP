package j.jave.securityutil.securityservice;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.security.exception.JSecurityException;
import j.jave.kernal.security.service.JDESedeCipherService;
import j.jave.kernal.security.service.JMD5CipherService;
import j.jave.kernal.security.service.JDESedeCipherServiceFactory.JDESedeCipherServiceSupport;
import j.jave.kernal.security.service.JDefaultMD5CipherService.JMD5CipherServiceSupport;

import org.springframework.stereotype.Service;

@Service(value="j.jave.securityutil.securityservice.JSecurityServiceImpl")
public class JSecurityServiceImpl implements JSecurityService, JDESedeCipherServiceSupport ,JMD5CipherServiceSupport{
	
	private JDESedeCipherService desedeCipherService=JServiceHubDelegate.get().getService(this, JDESedeCipherService.class);;
	
	private JMD5CipherService md5CipherService=JServiceHubDelegate.get().getService(this, JMD5CipherService.class);;
	
	public JSecurityServiceImpl(){
		System.out.println("on...");
	}
	
	@Override
	public JDESedeCipherService getDESedeCipherService(){
		return desedeCipherService;
	}
	
	public JMD5CipherService getMd5CipherService() {
		return md5CipherService;
	}
	
	@Override
	public String encryptOnDESede(String plain) throws JSecurityException {
		return getDESedeCipherService().encrypt(plain);
	}

	@Override
	public String decryptOnDESede(String encrypted) throws JSecurityException {
		return getDESedeCipherService().decrypt(encrypted);
	}

	@Override
	public String encryptOnMD5(String plain) throws JSecurityException {
		try {
			return getMd5CipherService().encrypt(plain);
		} catch (Exception e) {
			throw new JSecurityException(e);
		}
	}

}
