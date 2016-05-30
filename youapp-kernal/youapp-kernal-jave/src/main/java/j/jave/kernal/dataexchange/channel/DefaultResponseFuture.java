package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.EventExecutionResult;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionException;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;


public class DefaultResponseFuture implements ResponseFuture {
	
	private final Channel<?> exchangeChannel;
	
	private Object request;
	
	private Object response;
	
	private MessageSendingEvent event;
	
	public DefaultResponseFuture(Channel<?> exchangeChannel) {
		this.exchangeChannel=exchangeChannel;
	}
	
	@Override
	public Channel<?> channel() {
		return exchangeChannel;
	}
	
	@Override
	public ResponseFuture await() throws InterruptedException {
		for(;;){
			EventExecutionResult executionResult=null;
			try{
				executionResult=JServiceHubDelegate.get().getResultByEventId(event.getUnique());
			}catch(JEventExecutionException e){
				if(JEventExecutionException.EVENT_NOT_COMPLETE.equals(e.getCode())){
				}
				else{
					throw e;
				}
			}
			if(executionResult==null){
				try{
					Thread.sleep(1000l);
				}catch(InterruptedException e){
					//ignore
				}
				continue;
			}
			
			if(executionResult.getException()!=null){
				throw new RuntimeException(executionResult.getException());
			}
			Object[] objects=executionResult.getObjects();
			
			if(objects==null||objects.length==0){
				throw new RuntimeException("execute failly.");
			}
			this.response=objects[0];
			break;
		}
		return this;
	}
	
	@Override
	public Object getResponse() throws Exception {
		return response;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public MessageSendingEvent getEvent() {
		return event;
	}

	public void setEvent(MessageSendingEvent event) {
		this.event = event;
	}

	
}
