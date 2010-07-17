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
 * This class is a wrapper for {@link UserGroupGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRole
 * @generated
 */
public class UserGroupGroupRoleWrapper implements UserGroupGroupRole {
	public UserGroupGroupRoleWrapper(UserGroupGroupRole userGroupGroupRole) {
		_userGroupGroupRole = userGroupGroupRole;
	}

	public com.liferay.portal.service.persistence.UserGroupGroupRolePK getPrimaryKey() {
		return _userGroupGroupRole.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK pk) {
		_userGroupGroupRole.setPrimaryKey(pk);
	}

	public long getUserGroupId() {
		return _userGroupGroupRole.getUserGroupId();
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupGroupRole.setUserGroupId(userGroupId);
	}

	public long getGroupId() {
		return _userGroupGroupRole.getGroupId();
	}

	public void setGroupId(long groupId) {
		_userGroupGroupRole.setGroupId(groupId);
	}

	public long getRoleId() {
		return _userGroupGroupRole.getRoleId();
	}

	public void setRoleId(long roleId) {
		_userGroupGroupRole.setRoleId(roleId);
	}

	public com.liferay.portal.model.UserGroupGroupRole toEscapedModel() {
		return _userGroupGroupRole.toEscapedModel();
	}

	public boolean isNew() {
		return _userGroupGroupRole.isNew();
	}

	public void setNew(boolean n) {
		_userGroupGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroupGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroupGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroupGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroupGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userGroupGroupRole.clone();
	}

	public int compareTo(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRole.compareTo(userGroupGroupRole);
	}

	public int hashCode() {
		return _userGroupGroupRole.hashCode();
	}

	public java.lang.String toString() {
		return _userGroupGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroupGroupRole.toXmlString();
	}

	public UserGroupGroupRole getWrappedUserGroupGroupRole() {
		return _userGroupGroupRole;
	}

	private UserGroupGroupRole _userGroupGroupRole;
}