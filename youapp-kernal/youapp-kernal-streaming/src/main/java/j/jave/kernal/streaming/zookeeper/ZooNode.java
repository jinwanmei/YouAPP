package j.jave.kernal.streaming.zookeeper;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class ZooNode implements JModel{

	private String path;
	
	private byte[] data;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getData() {
		return data;
	}
	
	public byte[] getDataAsPossible(ZookeeperExecutor executor) {
		byte[] bytes=getData();
		if(bytes==null){
			bytes=executor.getPath(getPath());
		}
		return bytes;
	}
	

	public void setData(byte[] data) {
		this.data = data;
	}
	
	@Deprecated
	public String getStringData(){
		byte[] bytes=getData();
		if(bytes==null){
			return null;
		}
		return JStringUtils.utf8(getData());
	}
	

}
