package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.modelprotocol.JProtocolConstants;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.http.JResponseHandler;

public class HttpClientSender extends Sender {

	private static JResponseHandler<byte[]> responseHandler=new JResponseHandler<byte[]>(){
		public byte[] process(byte[] bytes) throws JResponseHandler.ProcessException {
			return bytes;
		};
	};
	
	
	protected byte[] doSend(Message message) throws Exception {
		byte[] data=getSenderData(message);
		byte[] bytes=(byte[]) JHttpFactoryProvider.getHttpFactory().getHttpPost()
				.setUrl(message.getUrl())
				.setEntry(data)
				.setResponseHandler(responseHandler)
				.setRetry(0)
				.putHead(JProtocolConstants.PROTOCOL_HEAD, "JSON")
				.execute();
		return bytes;
	};
	
}