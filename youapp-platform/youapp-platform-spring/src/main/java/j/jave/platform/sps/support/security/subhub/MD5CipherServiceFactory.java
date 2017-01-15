package j.jave.platform.sps.support.security.subhub;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel._c.security.exception.JSecurityException;
import me.bunny.kernel.security.service.JDefaultMD5CipherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="MD5CipherServiceFactory")
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
