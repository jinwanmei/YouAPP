package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JURIInfo;
import me.bunny.kernel.container.JExecutableURIUtil.Type;
import me.bunny.kernel.jave.exception.JOperationNotSupportedException;
import me.bunny.kernel.jave.support._resource.JResourceStreamException;
import me.bunny.kernel.jave.utils.JIOUtils;

public class JJarFileProcessorService implements JResourceProcessorService {
	
	@Override
	public Object process(URI uri,Object object) {
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		if(!Type.GET.getValue().equals(uriInfo.getPath())){
			throw new JOperationNotSupportedException("only support get : "+uriInfo.getWholeUri());
		}
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			bytes=JIOUtils.getBytes(new URI(queryPath).toURL().openConnection().getInputStream());
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}

}