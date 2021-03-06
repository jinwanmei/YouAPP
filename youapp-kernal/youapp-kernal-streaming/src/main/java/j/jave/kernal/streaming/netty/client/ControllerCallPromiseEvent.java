package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

@JListenerOnEvent(name=ControllerCallPromiseListener.class)
public class ControllerCallPromiseEvent  extends JYouAPPEvent<ControllerCallPromiseEvent>{

	private final ControllerCallPromise callPromise;
	
	public ControllerCallPromiseEvent(Object source,ControllerCallPromise callPromise) {
		super(source);
		this.callPromise=callPromise;
	}
	
	ControllerCallPromise getCallPromise() {
		return callPromise;
	}
	

	
	
}
