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
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroup
 * @generated
 */
public class UserGroupWrapper implements UserGroup {
	public UserGroupWrapper(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	public long getPrimaryKey() {
		return _userGroup.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userGroup.setPrimaryKey(pk);
	}

	public long getUserGroupId() {
		return _userGroup.getUserGroupId();
	}

	public void setUserGroupId(long userGroupId) {
		_userGroup.setUserGroupId(userGroupId);
	}

	public long getCompanyId() {
		return _userGroup.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_userGroup.setCompanyId(companyId);
	}

	public long getParentUserGroupId() {
		return _userGroup.getParentUserGroupId();
	}

	public void setParentUserGroupId(long parentUserGroupId) {
		_userGroup.setParentUserGroupId(parentUserGroupId);
	}

	public java.lang.String getName() {
		return _userGroup.getName();
	}

	public void setName(java.lang.String name) {
		_userGroup.setName(name);
	}

	public java.lang.String getDescription() {
		return _userGroup.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_userGroup.setDescription(description);
	}

	public com.liferay.portal.model.UserGroup toEscapedModel() {
		return _userGroup.toEscapedModel();
	}

	public boolean isNew() {
		return _userGroup.isNew();
	}

	public void setNew(boolean n) {
		_userGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroup.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroup.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroup.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroup.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userGroup.clone();
	}

	public int compareTo(com.liferay.portal.model.UserGroup userGroup) {
		return _userGroup.compareTo(userGroup);
	}

	public int hashCode() {
		return _userGroup.hashCode();
	}

	public java.lang.String toString() {
		return _userGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroup.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getGroup();
	}

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPrivateLayoutsPageCount();
	}

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPrivateLayouts();
	}

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPublicLayoutsPageCount();
	}

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPublicLayouts();
	}

	public UserGroup getWrappedUserGroup() {
		return _userGroup;
	}

	private UserGroup _userGroup;
}