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
 * This class is a wrapper for {@link ResourcePermission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermission
 * @generated
 */
public class ResourcePermissionWrapper implements ResourcePermission {
	public ResourcePermissionWrapper(ResourcePermission resourcePermission) {
		_resourcePermission = resourcePermission;
	}

	public long getPrimaryKey() {
		return _resourcePermission.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_resourcePermission.setPrimaryKey(pk);
	}

	public long getResourcePermissionId() {
		return _resourcePermission.getResourcePermissionId();
	}

	public void setResourcePermissionId(long resourcePermissionId) {
		_resourcePermission.setResourcePermissionId(resourcePermissionId);
	}

	public long getCompanyId() {
		return _resourcePermission.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_resourcePermission.setCompanyId(companyId);
	}

	public java.lang.String getName() {
		return _resourcePermission.getName();
	}

	public void setName(java.lang.String name) {
		_resourcePermission.setName(name);
	}

	public int getScope() {
		return _resourcePermission.getScope();
	}

	public void setScope(int scope) {
		_resourcePermission.setScope(scope);
	}

	public java.lang.String getPrimKey() {
		return _resourcePermission.getPrimKey();
	}

	public void setPrimKey(java.lang.String primKey) {
		_resourcePermission.setPrimKey(primKey);
	}

	public long getRoleId() {
		return _resourcePermission.getRoleId();
	}

	public void setRoleId(long roleId) {
		_resourcePermission.setRoleId(roleId);
	}

	public long getActionIds() {
		return _resourcePermission.getActionIds();
	}

	public void setActionIds(long actionIds) {
		_resourcePermission.setActionIds(actionIds);
	}

	public com.liferay.portal.model.ResourcePermission toEscapedModel() {
		return _resourcePermission.toEscapedModel();
	}

	public boolean isNew() {
		return _resourcePermission.isNew();
	}

	public void setNew(boolean n) {
		_resourcePermission.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourcePermission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourcePermission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourcePermission.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resourcePermission.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourcePermission.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourcePermission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourcePermission.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _resourcePermission.clone();
	}

	public int compareTo(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		return _resourcePermission.compareTo(resourcePermission);
	}

	public int hashCode() {
		return _resourcePermission.hashCode();
	}

	public java.lang.String toString() {
		return _resourcePermission.toString();
	}

	public java.lang.String toXmlString() {
		return _resourcePermission.toXmlString();
	}

	public ResourcePermission getWrappedResourcePermission() {
		return _resourcePermission;
	}

	private ResourcePermission _resourcePermission;
}