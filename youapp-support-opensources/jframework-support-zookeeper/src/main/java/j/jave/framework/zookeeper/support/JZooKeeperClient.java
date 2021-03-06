package j.jave.framework.zookeeper.support;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.utils.JCollectionUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

public class JZooKeeperClient {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(JZooKeeperClient.class);

	final String hostPort;
	
	private ZooKeeper zooKeeper;
	
	private final JWatch watch;
	
	public JZooKeeperClient(String hostPort,JWatch watch){
		this.hostPort=hostPort;
		this.watch=watch;
		init();
	}
	public JZooKeeperClient(final String hostPort){
		this.hostPort=hostPort;
		this.watch=new JWatch() {
			@Override
			public void process(WatchedEvent event) {
				LOGGER.info("["+hostPort+"]type->"+event.getType()+";status->"+event.getState());
			}
		};
		init();
	}
	private void init(){
		try {
			zooKeeper=new ZooKeeper(hostPort, 30000, watch);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * schema :  auth, digest,world,ip
	 * id: name:password
	 * @param schema
	 * @param id
	 */
	public void addAuth(String schema,String id){
		zooKeeper.addAuthInfo(schema, id.getBytes());
	}
	
	public void addAuth(JBaseAuth baseAuth){
		try{
			zooKeeper.addAuthInfo(baseAuth.getSchema(), baseAuth.authorizingId().getBytes("utf-8"));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * create node with all permission
	 * @param zooKeeperNode
	 */
	public void createNode(JZooKeeperNode zooKeeperNode){
		try {
			List<ACL> intoAcls=null;
			List<JACL> predefinedACLs=zooKeeperNode.getAcls();
			if(JCollectionUtils.hasInCollect(predefinedACLs)){
				JACL[] jacls=new JACL[predefinedACLs.size()];
				zooKeeperNode.getAcls().toArray(jacls);
				intoAcls =  getACL(jacls);
			}
			if(!JCollectionUtils.hasInCollect(intoAcls)){
				intoAcls=Ids.OPEN_ACL_UNSAFE;
			}
			zooKeeper.create(zooKeeperNode.getZooNodePath().getPath(), zooKeeperNode.getInstance().getValue(),
					intoAcls, zooKeeperNode.getCreateMode().mapping());
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}catch (Exception e) {
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * create not with ACL defined.
	 * @param zooKeeperNode
	 * @param acls override the default ACL in the {@link JZooKeeperNode#getAcls()} if the argument is not empty.
	 */
	public void createNode(JZooKeeperNode zooKeeperNode,JACL... acls){
		try {
			List<ACL> intoAcls = getACL(acls);
			if(!JCollectionUtils.hasInCollect(intoAcls)){
				List<JACL> predefinedACLs=zooKeeperNode.getAcls();
				JACL[] jacls=new JACL[predefinedACLs.size()];
				zooKeeperNode.getAcls().toArray(jacls);
				intoAcls = getACL(jacls);
			}
			if(!JCollectionUtils.hasInCollect(intoAcls)){
				intoAcls=Ids.OPEN_ACL_UNSAFE;
			}
			zooKeeper.create(zooKeeperNode.getZooNodePath().getPath(), zooKeeperNode.getInstance().getValue(),
					intoAcls, zooKeeperNode.getCreateMode().mapping());
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}catch (Exception e) {
			throw new JZooKepperException(e);
		}
	}
	private List<ACL> getACL(JACL... acls) throws NoSuchAlgorithmException {
		List<ACL> intoAcls=new ArrayList<ACL>();
		if(acls!=null){
			for (int i = 0; i < acls.length; i++) {
				JACL jacl=acls[i];
				Id id=new Id();
				id.setScheme(jacl.getBaseAuth().getSchema());
				id.setId(jacl.getBaseAuth().authorizingIdSHA1());
				ACL acl=new ACL(jacl.getPerm(), id);
				intoAcls.add(acl);
			}
		}
		return intoAcls;
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode){
		Stat stat = new Stat();
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			return zooKeeper.getData(zooNodePath.getPath(), null, stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,JWatch watch){
		Stat stat = new Stat();
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			return zooKeeper.getData(zooNodePath.getPath(), watch, stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public JValue getNodeStat(JZooKeeperNode zooKeeperNode,JWatch watch){
		
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			Stat stat = new Stat();
			byte[] bytes= zooKeeper.getData(zooNodePath.getPath(), watch, stat);
			JValue value=new JValue();
			value.setBytes(bytes);
			JStatWrapper statWrapper=new JStatWrapper();
			statWrapper.setStat(stat);
			value.setStatWrapper(statWrapper);
			return value;
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode,int version){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			zooKeeper.delete(zooNodePath.getPath(), version);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode){
		deleteNode(zooKeeperNode, -1);
	}
	
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes,int version){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			zooKeeper.setData(zooNodePath.getPath(), bytes, version);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes){
		setNode(zooKeeperNode, bytes, -1);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode){
		return exist(zooKeeperNode, null);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode,JWatch watch){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			Stat stat=null;
			if(watch==null){
				stat=zooKeeper.exists(zooNodePath.getPath(), false);
			}
			else{
				stat=zooKeeper.exists(zooNodePath.getPath(), watch);
			}
			return stat!=null;
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	
	public JStatWrapper getLatest(JZooKeeperNode zooKeeperNode){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			Stat stat=zooKeeper.exists(zooNodePath.getPath(), false);
			return stat==null?null:new JStatWrapper(stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	
}
