package j.jave.module.crawl.node;

import j.jave.module.crawl.kernel.JCrawlContext;

public class JNodeGetterUtil {

	public static final String XPATH_PROTOCOL="xpath";
	public static final String TAG_PROTOCOL="tag";
	public static final String NAME_PROTOCOL="name";
	public static final String CLASS_PROTOCOL="class";
	public static final String ID_PROTOCOL="id";
	public static final String MIXED_PROTOCOL="mixed";
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getNodeGetter(String protocol){
		
		if(XPATH_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getXPathGetter();
		}
		else if(TAG_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getTagNameGetter();
		}
		else if(NAME_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getNameGetter();
		}
		else if(CLASS_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getClassNameGetter();
		}
		else if(ID_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getClassNameGetter();
		}
		else if(MIXED_PROTOCOL.equals(protocol)){
			return (T) JCrawlContext.get().getMixedGetter();
		}
		return null;
	}
	
	
	
	
}