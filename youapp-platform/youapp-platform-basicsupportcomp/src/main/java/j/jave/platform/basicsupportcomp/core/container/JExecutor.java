package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

public interface JExecutor {

	public boolean accept(URI uri);
	
	public Object execute(URI uri,Object object);
}
