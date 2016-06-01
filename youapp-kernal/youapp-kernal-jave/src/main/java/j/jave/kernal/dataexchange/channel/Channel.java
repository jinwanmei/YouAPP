package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.jave.model.JModel;

public interface Channel<T extends JModel> {

	ResponseFuture write(T msg) throws Exception;
	
	Object read() throws Exception;
	
}