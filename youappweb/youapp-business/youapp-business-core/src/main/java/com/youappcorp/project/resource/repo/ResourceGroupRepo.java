/**
 * 
 */
package com.youappcorp.project.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;

import com.youappcorp.project.resource.model.ResourceGroup;

import java.util.List;

/**
 * @author J
 *
 */
public interface ResourceGroupRepo<T> extends JIPersist<T,ResourceGroup,String> {

	List<ResourceGroup> getResourceGroupsByResourceId(String resourceId);
	
	int countOnResourceIdAndGroupId(String resourceId,String groupId);
	
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(String resourceId,String groupId);
	
	
}
