package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.sps.core.servicehub.SkipServiceNameCheck;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.model.GroupRecord;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.model.RoleGroup;
import com.youappcorp.project.usermanager.model.RoleRecord;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserDetail;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.model.UserGroup;
import com.youappcorp.project.usermanager.model.UserRecord;
import com.youappcorp.project.usermanager.model.UserRole;
import com.youappcorp.project.usermanager.vo.GroupSearchCriteria;
import com.youappcorp.project.usermanager.vo.RoleSearchCriteria;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;

@Service
@SkipServiceNameCheck
public class UserManagerServiceImpl
extends SpringServiceFactorySupport<UserManagerService>
implements UserManagerService {

	@Autowired
	private DefaultUserManagerServiceImpl defaultUserManagerServiceImpl;
	
	
	@Override
	public User getUserByName(ServiceContext serviceContext, String userName) {
		return defaultUserManagerServiceImpl.getUserByName(serviceContext, userName);
	}

	@Override
	public Group getGroupByGroupCode(ServiceContext serviceContext,
			String groupCode) {
		return defaultUserManagerServiceImpl.getGroupByGroupCode(serviceContext, groupCode);
	}

	@Override
	public Group getAdminGroup(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAdminGroup(serviceContext);
	}

	@Override
	public Group getDefaultGroup(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getDefaultGroup(serviceContext);
	}

	@Override
	public JPage<Group> getGroupsByPage(ServiceContext serviceContext,
			GroupSearchCriteria groupSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getGroupsByPage(serviceContext, groupSearchCriteria, simplePageable);
	}

	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAllGroups(serviceContext);
	}

	@Override
	public void saveGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveGroup(serviceContext, group);
	}

	@Override
	public boolean exists(ServiceContext serviceContext, Group group)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists(serviceContext, group);
	}

	@Override
	public void updateGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateGroup(serviceContext, group);
	}

	@Override
	public void deleteGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteGroup(serviceContext, group);
	}

	@Override
	public Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode) {
		return defaultUserManagerServiceImpl.getRoleByRoleCode(serviceContext, roleCode);
	}

	@Override
	public Role getRoleById(ServiceContext serviceContext, String id) {
		return defaultUserManagerServiceImpl.getRoleById(serviceContext, id);
	}

	@Override
	public Role getAdminRole(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAdminRole(serviceContext);
	}

	@Override
	public Role getDefaultRole(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getDefaultRole(serviceContext);
	}

	@Override
	public JPage<Role> getAllRolesByPage(ServiceContext serviceContext,
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getAllRolesByPage(serviceContext, roleSearchCriteria, simplePageable);
	}

	@Override
	public List<Role> getAllRoles(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAllRoles(serviceContext);
	}

	@Override
	public void saveRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveRole(serviceContext, role);
	}

	@Override
	public boolean exists(ServiceContext serviceContext, Role role)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists(serviceContext, role);
	}

	@Override
	public void updateRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateRole(serviceContext, role);
	}

	@Override
	public void deleteRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteRole(serviceContext, role);
	}

	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return defaultUserManagerServiceImpl.getUserByNameAndPassword(userName, password);
	}

	@Override
	public void saveUser(ServiceContext serviceContext, User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUser(serviceContext, user);
	}

	@Override
	public void updateUser(ServiceContext serviceContext, User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUser(serviceContext, user);
	}
	
	@Override
	public void updateUser(ServiceContext serviceContext, User user,
			UserExtend userExtend) throws BusinessException {
		defaultUserManagerServiceImpl.updateUser(serviceContext, user, userExtend);
	}

	@Override
	public JPage<UserRecord> getUsersByPage(ServiceContext serviceContext,
			UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByPage(serviceContext, userSearchCriteria, simplePageable);
	}

	@Override
	public UserRecord getUserById(ServiceContext serviceContext, String id) {
		return defaultUserManagerServiceImpl.getUserById(serviceContext, id);
	}

	@Override
	public List<User> getUsers(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getUsers(serviceContext);
	}

	@Override
	public void register(ServiceContext serviceContext, User user,
			UserExtend userExtend) throws BusinessException {
		defaultUserManagerServiceImpl.register(serviceContext, user, userExtend);
	}

	@Override
	public void resetPassword(ServiceContext serviceContext, String userId,
			String password) {
		defaultUserManagerServiceImpl.resetPassword(serviceContext, userId, password);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByRoleId(serviceContext, roleId);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByGroupId(serviceContext, groupId);
	}

	@Override
	public void bingRoleGroup(ServiceContext serviceContext, String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingRoleGroup(serviceContext, roleId, groupId);
	}

	@Override
	public void unbingRoleGroup(ServiceContext serviceContext, String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingRoleGroup(serviceContext, roleId, groupId);
	}

	@Override
	public boolean isBingRoleAndGroup(ServiceContext serviceContext,
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.isBingRoleAndGroup(serviceContext, roleId, groupId);
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			ServiceContext serviceContext, String roleId, String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupOnRoleIdAndGroupId(serviceContext, roleId, groupId);
	}

	@Override
	public long countOnRoleIdAndGroupId(ServiceContext serviceContext,
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.countOnRoleIdAndGroupId(serviceContext, roleId, groupId);
	}

	@Override
	public List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getUserRolesByUserId(serviceContext, userId);
	}

	@Override
	public void bingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserRole(serviceContext, userId, roleId);
	}

	@Override
	public void unbingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserRole(serviceContext, userId, roleId);
	}

	@Override
	public boolean isBingUserAndRole(ServiceContext serviceContext,
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.isBingUserAndRole(serviceContext, userId, roleId);
	}

	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.getUserRoleOnUserIdAndRoleId(serviceContext, userId, roleId);
	}

	@Override
	public void saveUserExtend(ServiceContext serviceContext, UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUserExtend(serviceContext, userExtend);
	}

	@Override
	public void updateUserExtend(ServiceContext serviceContext, UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUserExtend(serviceContext, userExtend);
	}

	@Override
	public UserExtend getUserExtendByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getUserExtendByUserId(serviceContext, userId);
	}

	@Override
	public UserExtend getUserExtendByNatureName(ServiceContext serviceContext,
			String natureName) {
		return defaultUserManagerServiceImpl.getUserExtendByNatureName(serviceContext, natureName);
	}

	@Override
	public List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getUserGroupsByUserId(serviceContext, userId);
	}

	@Override
	public void bingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserGroup(serviceContext, userId, groupId);
	}

	@Override
	public void unbingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserGroup(serviceContext, userId, groupId);
	}

	@Override
	public boolean isBingUserAndGroup(ServiceContext serviceContext,
			String userId, String groupId) {
		return defaultUserManagerServiceImpl.isBingUserAndGroup(serviceContext, userId, groupId);
	}

	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(
			ServiceContext serviceContext, String userId, String groupId)
			throws BusinessException {
		return defaultUserManagerServiceImpl.getUserGroupOnUserIdAndGroupId(serviceContext, userId, groupId);
	}

	@Override
	public UserDetail getUserDetailByName(ServiceContext serviceContext,
			String userName) {
		return defaultUserManagerServiceImpl.getUserDetailByName(serviceContext, userName);
	}

	@Override
	public UserDetail getUserDetailById(ServiceContext serviceContext, String id) {
		return defaultUserManagerServiceImpl.getUserDetailById(serviceContext, id);
	}

	@Override
	public Group getGroupById(ServiceContext serviceContext, String id) {
		return defaultUserManagerServiceImpl.getGroupById(serviceContext, id);
	}

	@Override
	public JPage<UserRecord> getUsersByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByRoleIdByPage(serviceContext, roleId, simplePageable);
	}

	@Override
	public List<UserRecord> getUsersByRoleId(
			ServiceContext serviceContext, String roleId) {
		return defaultUserManagerServiceImpl.getUsersByRoleId(serviceContext, roleId);
	}

	@Override
	public JPage<UserRecord> getUsersByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByGroupIdByPage(serviceContext, groupId, simplePageable);
	}

	@Override
	public List<UserRecord> getUsersByGroupId(
			ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.getUsersByGroupId(serviceContext, groupId);
	}

	@Override
	public List<RoleRecord> getRolesByGroupId(
			ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.getRolesByGroupId(serviceContext, groupId);
	}

	@Override
	public JPage<RoleRecord> getRolesByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getRolesByGroupIdByPage(serviceContext, groupId, simplePageable);
	}

	@Override
	public List<GroupRecord> getGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return defaultUserManagerServiceImpl.getGroupsByRoleId(serviceContext, roleId);
	}

	@Override
	public JPage<GroupRecord> getGroupsByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getGroupsByRoleIdByPage(serviceContext, roleId, simplePageable);
	}

	@Override
	public List<RoleRecord> getRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getRolesByUserId(serviceContext, userId);
	}

	@Override
	public List<GroupRecord> getGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getGroupsByUserId(serviceContext, userId);
	}

	@Override
	public List<GroupRecord> getUnbingGroupsByRoleId(
			ServiceContext serviceContext, String roleId) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByRoleId(serviceContext, roleId);
	}

	@Override
	public JPage<GroupRecord> getUnbingGroupsByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByRoleIdByPage(serviceContext, roleId, simplePageable);
	}

	@Override
	public List<GroupRecord> getUnbingGroupsByUserId(
			ServiceContext serviceContext, String userId) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByUserId(serviceContext, userId);
	}

	@Override
	public JPage<UserRecord> getUnbingUsersByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingUsersByRoleIdByPage(serviceContext, roleId, simplePageable);
	}

	@Override
	public JPage<UserRecord> getUnbingUsersByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingUsersByGroupIdByPage(serviceContext, groupId, simplePageable);
	}

	@Override
	public List<RoleRecord> getUnbingRolesByUserId(
			ServiceContext serviceContext, String userId) {
		return defaultUserManagerServiceImpl.getUnbingRolesByUserId(serviceContext, userId);
	}

	@Override
	public List<RoleRecord> getUnbingRolesByGroupId(
			ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.getUnbingRolesByGroupId(serviceContext, groupId);
	}

	@Override
	public void deleteUser(ServiceContext serviceContext, String userId) {
		defaultUserManagerServiceImpl.deleteUser(serviceContext, userId);
	}

	@Override
	public boolean isAdminRole(ServiceContext serviceContext, String roleId) {
		return defaultUserManagerServiceImpl.isAdminRole(serviceContext, roleId);
	}

	@Override
	public boolean isDefaultRole(ServiceContext serviceContext, String roleId) {
		return defaultUserManagerServiceImpl.isDefaultRole(serviceContext, roleId);
	}
	
	@Override
	public boolean isAdminRoleCode(ServiceContext serviceContext, String code) {
		return defaultUserManagerServiceImpl.isAdminRoleCode(serviceContext, code);
	}
	
	@Override
	public boolean isDefaultRoleCode(ServiceContext serviceContext, String code) {
		return defaultUserManagerServiceImpl.isDefaultRoleCode(serviceContext, code);
	}

	@Override
	public boolean isDefaultGroup(ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.isDefaultGroup(serviceContext, groupId);
	}

	@Override
	public boolean isDefaultGroupCode(ServiceContext serviceContext, String code) {
		return defaultUserManagerServiceImpl.isDefaultGroupCode(serviceContext, code);
	}

	@Override
	public boolean isAdminGroup(ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.isAdminGroup(serviceContext, groupId);
	}

	@Override
	public boolean isAdminGroupCode(ServiceContext serviceContext, String code) {
		return defaultUserManagerServiceImpl.isAdminGroupCode(serviceContext, code);
	}
	
}