/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;
import j.jave.framework.servicehub.filedistribute.JDefaultLocalFileDistService;
import j.jave.framework.servicehub.filedistribute.JDefaultLocalFilePathStrategy;
import j.jave.framework.utils.JStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="fileDisServiceFactory")
public class FileDisServiceFactory extends SpringServiceFactorySupport<FileDisService> {
	
	public FileDisServiceFactory() {
		super(FileDisService.class);
	}
	
	@Autowired(required=false)
	private DefaultLocalFileDistServiceConfiguration defaultLocalFileDistServiceConfiguration;
	
	private FileDisService fileDisService;
	
	private Object sync=new Object();
	
	@Override
	public FileDisService getService() {
		
		if(fileDisService==null){
			synchronized (sync) {
				DefaultLocalFileDistServiceImpl defaultLocalFileDistServiceImpl=  (DefaultLocalFileDistServiceImpl) getBeanByName("defaultLocalFileDistServiceImpl"); 
				
				if(defaultLocalFileDistServiceConfiguration==null){
					defaultLocalFileDistServiceConfiguration=new DefaultLocalFileDistServiceConfiguration();
					LOGGER.info("use default local fle distribute configuration."); 
				}
				
				JDefaultLocalFileDistService defaultLocalFileDistService=new JDefaultLocalFileDistService();
				
				// configure directory which file saves in 
				if(JStringUtils.isNotNullOrEmpty(defaultLocalFileDistServiceConfiguration.getLocalDirectory())){
					defaultLocalFileDistService.setLocalDirectory(defaultLocalFileDistServiceConfiguration.getLocalDirectory());
				}
				
				// configure the strategy where to locate the file.
				if(defaultLocalFileDistServiceConfiguration.getDefaultLocalFilePathStrategy()!=null){
					defaultLocalFileDistService.setJDefaultLocalFilePathStrategy(defaultLocalFileDistServiceConfiguration.getDefaultLocalFilePathStrategy());
				}
				else{
					defaultLocalFileDistService.setJDefaultLocalFilePathStrategy(new JDefaultLocalFilePathStrategy());
				}
				
				defaultLocalFileDistServiceImpl.setDefaultLocalFileDistService(defaultLocalFileDistService);
					
				this.fileDisService= defaultLocalFileDistServiceImpl;
			}
		}
		return fileDisService;
		
	}
	
	@Override
	public String getName() {
		return "File Distribute Provider";
	}
}
