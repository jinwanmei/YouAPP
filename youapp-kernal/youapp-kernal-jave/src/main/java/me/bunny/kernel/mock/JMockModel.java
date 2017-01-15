package me.bunny.kernel.mock;

import me.bunny.kernel._c.model.JModel;

public class JMockModel implements JModel {

	/**
	 * the data
	 */
	private Object data ;
	
	/**
	 * where the data locates, its higher than {@link #data()}
	 */
	private String uri;
	
	/**
	 * whether to mock the target or not
	 */
	boolean mock=true;
	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}
	
}
