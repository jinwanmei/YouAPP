package me.bunny.kernel._c.xml.node;

import java.util.List;


public interface JXPathGetter extends JNodeGetter {

	public List<?> getNodesByXPath(String xpath);
	
}