/**
 * 
 */
package j.jave.framework.components.memcached;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Administrator
 *
 */
public class JMemcachedDist implements JMemcachedDistService {
	
	private LinkedHashMap<Integer, JMemcached> store=new LinkedHashMap<Integer, JMemcached>();
	private JMemcached firstStoreJMemcached=null;

	private LinkedHashMap<Integer, JMemcached> backupStore=new LinkedHashMap<Integer, JMemcached>();
	private JMemcached firstBackupStoreJMemcached=null;
	
	private static JMemcachedDist jMemcachedDist;
	
	private Object sync=new Object();
	
	public JMemcachedDist(Map<String, List<String>> storeAddes,Map<String, List<String>> backupAddes){
		try {
			synchronized (sync) {
				if(jMemcachedDist==null) {
					if(storeAddes!=null){
						generateStore(storeAddes);
					}
					if(backupAddes!=null){
						generateBackupStore(backupAddes);
					}
					jMemcachedDist=this;
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * return fisrt element . 
	 * @param store
	 * @param storeAddes
	 * @return
	 */
	private JMemcached generate(LinkedHashMap<Integer, JMemcached> store,  Map<String, List<String>> storeAddes) {
		JMemcached first=null;
		List<Integer> tempIntegers=new ArrayList<Integer>();
		HashMap<Integer, JMemcached> temp=new HashMap<Integer, JMemcached>();
		for (Iterator<Entry<String, List<String>>> iterator = storeAddes.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<String, List<String>> entry = iterator.next();
			String keyString="";
			List<InetSocketAddress> inetSocketAddresses =new ArrayList<InetSocketAddress>();
			List<String> addes=entry.getValue();
			for(String add:addes){
				keyString=keyString+","+add;
				String[] adds=add.split(":");
				inetSocketAddresses.add(new InetSocketAddress(adds[0], Integer.valueOf(adds[1])));
			}
			keyString=keyString.replaceFirst(",", "");
			System.out.println(" one group("+entry.getKey()+") contains "+keyString);
			tempIntegers.add(keyString.hashCode());
			temp.put(keyString.hashCode(), new JMemcached(inetSocketAddresses));
		}
		Collections.sort(tempIntegers,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		for (Iterator<Integer> iterator = tempIntegers.iterator(); iterator.hasNext();) {
			Integer integer =  iterator.next();
			if(first==null)
				first=temp.get(integer);
			store.put(integer, temp.get(integer));
		}
		return first;
	}
	
	private void generateBackupStore(Map<String, List<String>> storeAddes) {
		JMemcached first=generate(backupStore, storeAddes);
		firstBackupStoreJMemcached=first;
	}
	
	private void generateStore(Map<String, List<String>> storeAddes) {
		JMemcached first=generate(store, storeAddes);
		firstStoreJMemcached=first;
	}
	
	public void set(String key , int expiry, Object value){
		
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
			jMemcached.set(key, expiry, value);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
			jMemcached.set(key, expiry, value);
		
	}

	
	private JMemcached getStoreJMemcached(String value) {
		return getJMemcached(store, value, firstStoreJMemcached);
	}
	
	private JMemcached getBackupJMemcached(String value) {
		return getJMemcached(backupStore, value, firstBackupStoreJMemcached);
	}
	
	

	private JMemcached getJMemcached(LinkedHashMap<Integer, JMemcached> maps , String value,JMemcached defaultJMemcached) {
		JMemcached jMemcached=null;
		int hashValue=value.hashCode();
		for (Iterator<Entry<Integer, JMemcached>> iterator = maps.entrySet().iterator(); iterator.hasNext();) {
			Entry<Integer, JMemcached> entry = iterator.next();
			if(entry.getKey()>hashValue){
				jMemcached=entry.getValue();
				break;
			}
		}
		
		if(jMemcached==null){
			jMemcached=defaultJMemcached;
		}
		return jMemcached;
	}
	
	public Object get(String key){
		
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		Object obj=null;
		if(jMemcached!=null)
			obj=jMemcached.get(key);
		if(obj==null){
			jMemcached = getBackupJMemcached(key);
			if(jMemcached!=null)
				obj=jMemcached.get(key);
			
			if(obj!=null){
				jMemcached = getStoreJMemcached(key);
				if(jMemcached!=null)
					jMemcached.set(key, 0, obj);
			}
		}
		return obj;
	}

	public void add(String key , int expiry, Object value){
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
		jMemcached.add(key, expiry, value);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
		jMemcached.add(key, expiry, value);
	}
	
	public void delete(String key){
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
		jMemcached.delete(key);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
		jMemcached.delete(key);
	}
	
	
	
	public JMemcachedDist(){
		
		
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
