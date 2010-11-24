/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link UserGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRole
 * @generated
 */
public class UserGroupRoleWrapper implements UserGroupRole {
	public UserGroupRoleWrapper(UserGroupRole userGroupRole) {
		_userGroupRole = userGroupRole;
	}

	/**
	* Gets the primary key of this user group role.
	*
	* @return the primary key of this user group role
	*/
	public com.liferay.portal.service.persistence.UserGroupRolePK getPrimaryKey() {
		return _userGroupRole.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user group role
	*
	* @param pk the primary key of this user group role
	*/
	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK pk) {
		_userGroupRole.setPrimaryKey(pk);
	}

	/**
	* Gets the user id of this user group role.
	*
	* @return the user id of this user group role
	*/
	public long getUserId() {
		return _userGroupRole.getUserId();
	}

	/**
	* Sets the user id of this user group role.
	*
	* @param userId the user id of this user group role
	*/
	public void setUserId(long userId) {
		_userGroupRole.setUserId(userId);
	}

	/**
	* Gets the user uuid of this user group role.
	*
	* @return the user uuid of this user group role
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUserUuid();
	}

	/**
	* Sets the user uuid of this user group role.
	*
	* @param userUuid the user uuid of this user group role
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_userGroupRole.setUserUuid(userUuid);
	}

	/**
	* Gets the group id of this user group role.
	*
	* @return the group id of this user group role
	*/
	public long getGroupId() {
		return _userGroupRole.getGroupId();
	}

	/**
	* Sets the group id of this user group role.
	*
	* @param groupId the group id of this user group role
	*/
	public void setGroupId(long groupId) {
		_userGroupRole.setGroupId(groupId);
	}

	/**
	* Gets the role id of this user group role.
	*
	* @return the role id of this user group role
	*/
	public long getRoleId() {
		return _userGroupRole.getRoleId();
	}

	/**
	* Sets the role id of this user group role.
	*
	* @param roleId the role id of this user group role
	*/
	public void setRoleId(long roleId) {
		_userGroupRole.setRoleId(roleId);
	}

	public boolean isNew() {
		return _userGroupRole.isNew();
	}

	public void setNew(boolean n) {
		_userGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new UserGroupRoleWrapper((UserGroupRole)_userGroupRole.clone());
	}

	public int compareTo(com.liferay.portal.model.UserGroupRole userGroupRole) {
		return _userGroupRole.compareTo(userGroupRole);
	}

	public int hashCode() {
		return _userGroupRole.hashCode();
	}

	public com.liferay.portal.model.UserGroupRole toEscapedModel() {
		return new UserGroupRoleWrapper(_userGroupRole.toEscapedModel());
	}

	public java.lang.String toString() {
		return _userGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroupRole.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getGroup();
	}

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getRole();
	}

	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUser();
	}

	public UserGroupRole getWrappedUserGroupRole() {
		return _userGroupRole;
	}

	private UserGroupRole _userGroupRole;
}