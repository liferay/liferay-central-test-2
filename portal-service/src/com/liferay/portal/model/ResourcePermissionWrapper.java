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

	/**
	* Gets the primary key of this resource permission.
	*
	* @return the primary key of this resource permission
	*/
	public long getPrimaryKey() {
		return _resourcePermission.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource permission
	*
	* @param pk the primary key of this resource permission
	*/
	public void setPrimaryKey(long pk) {
		_resourcePermission.setPrimaryKey(pk);
	}

	/**
	* Gets the resource permission id of this resource permission.
	*
	* @return the resource permission id of this resource permission
	*/
	public long getResourcePermissionId() {
		return _resourcePermission.getResourcePermissionId();
	}

	/**
	* Sets the resource permission id of this resource permission.
	*
	* @param resourcePermissionId the resource permission id of this resource permission
	*/
	public void setResourcePermissionId(long resourcePermissionId) {
		_resourcePermission.setResourcePermissionId(resourcePermissionId);
	}

	/**
	* Gets the company id of this resource permission.
	*
	* @return the company id of this resource permission
	*/
	public long getCompanyId() {
		return _resourcePermission.getCompanyId();
	}

	/**
	* Sets the company id of this resource permission.
	*
	* @param companyId the company id of this resource permission
	*/
	public void setCompanyId(long companyId) {
		_resourcePermission.setCompanyId(companyId);
	}

	/**
	* Gets the name of this resource permission.
	*
	* @return the name of this resource permission
	*/
	public java.lang.String getName() {
		return _resourcePermission.getName();
	}

	/**
	* Sets the name of this resource permission.
	*
	* @param name the name of this resource permission
	*/
	public void setName(java.lang.String name) {
		_resourcePermission.setName(name);
	}

	/**
	* Gets the scope of this resource permission.
	*
	* @return the scope of this resource permission
	*/
	public int getScope() {
		return _resourcePermission.getScope();
	}

	/**
	* Sets the scope of this resource permission.
	*
	* @param scope the scope of this resource permission
	*/
	public void setScope(int scope) {
		_resourcePermission.setScope(scope);
	}

	/**
	* Gets the prim key of this resource permission.
	*
	* @return the prim key of this resource permission
	*/
	public java.lang.String getPrimKey() {
		return _resourcePermission.getPrimKey();
	}

	/**
	* Sets the prim key of this resource permission.
	*
	* @param primKey the prim key of this resource permission
	*/
	public void setPrimKey(java.lang.String primKey) {
		_resourcePermission.setPrimKey(primKey);
	}

	/**
	* Gets the role id of this resource permission.
	*
	* @return the role id of this resource permission
	*/
	public long getRoleId() {
		return _resourcePermission.getRoleId();
	}

	/**
	* Sets the role id of this resource permission.
	*
	* @param roleId the role id of this resource permission
	*/
	public void setRoleId(long roleId) {
		_resourcePermission.setRoleId(roleId);
	}

	/**
	* Gets the action ids of this resource permission.
	*
	* @return the action ids of this resource permission
	*/
	public long getActionIds() {
		return _resourcePermission.getActionIds();
	}

	/**
	* Sets the action ids of this resource permission.
	*
	* @param actionIds the action ids of this resource permission
	*/
	public void setActionIds(long actionIds) {
		_resourcePermission.setActionIds(actionIds);
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
		return new ResourcePermissionWrapper((ResourcePermission)_resourcePermission.clone());
	}

	public int compareTo(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		return _resourcePermission.compareTo(resourcePermission);
	}

	public int hashCode() {
		return _resourcePermission.hashCode();
	}

	public com.liferay.portal.model.ResourcePermission toEscapedModel() {
		return new ResourcePermissionWrapper(_resourcePermission.toEscapedModel());
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