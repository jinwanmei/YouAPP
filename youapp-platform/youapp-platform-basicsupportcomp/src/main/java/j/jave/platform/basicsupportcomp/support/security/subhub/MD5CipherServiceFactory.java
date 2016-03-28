package j.jave.platform.basicsupportcomp.support.security.subhub;

import j.jave.kernal.jave.security.exception.JSecurityException;
import j.jave.kernal.security.service.JDefaultMD5CipherService;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="j.jave.platform.basicsupportcomp.support.security.subhub.MD5CipherServiceFactory")
public class MD5CipherServiceFactory extends SpringServiceFactorySupport<MD5CipherService> {

	@Autowired(required=false)
	private MD5CipherServiceProvider extMdsCipherService;
	
	private MD5CipherService provideMdsCipherService;
	
	private MD5CipherService delegateMdsCipherService=new MD5CipherService() {
		
		private JDefaultMD5CipherService innerMdsCipherService=new JDefaultMD5CipherService();
		
		@Override
		public String encrypt(String plain) throws JSecurityException {
			return innerMdsCipherService.encrypt(plain);
		}
		
	};

	private Object sync=new Object();
	
	@Override
	public MD5CipherService getService() {
		
		if(provideMdsCipherService==null){
			synchronized (sync) {
				if(provideMdsCipherService==null){
					if(extMdsCipherService!=null){
						provideMdsCipherService=extMdsCipherService;
					}
					else{
						provideMdsCipherService=delegateMdsCipherService;
					}
				}
			}
		}
		return provideMdsCipherService;
	}
	
}
