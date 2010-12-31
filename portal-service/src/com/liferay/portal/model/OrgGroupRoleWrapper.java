/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link OrgGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupRole
 * @generated
 */
public class OrgGroupRoleWrapper implements OrgGroupRole {
	public OrgGroupRoleWrapper(OrgGroupRole orgGroupRole) {
		_orgGroupRole = orgGroupRole;
	}

	/**
	* Gets the primary key of this org group role.
	*
	* @return the primary key of this org group role
	*/
	public com.liferay.portal.service.persistence.OrgGroupRolePK getPrimaryKey() {
		return _orgGroupRole.getPrimaryKey();
	}

	/**
	* Sets the primary key of this org group role
	*
	* @param pk the primary key of this org group role
	*/
	public void setPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK pk) {
		_orgGroupRole.setPrimaryKey(pk);
	}

	/**
	* Gets the organization ID of this org group role.
	*
	* @return the organization ID of this org group role
	*/
	public long getOrganizationId() {
		return _orgGroupRole.getOrganizationId();
	}

	/**
	* Sets the organization ID of this org group role.
	*
	* @param organizationId the organization ID of this org group role
	*/
	public void setOrganizationId(long organizationId) {
		_orgGroupRole.setOrganizationId(organizationId);
	}

	/**
	* Gets the group ID of this org group role.
	*
	* @return the group ID of this org group role
	*/
	public long getGroupId() {
		return _orgGroupRole.getGroupId();
	}

	/**
	* Sets the group ID of this org group role.
	*
	* @param groupId the group ID of this org group role
	*/
	public void setGroupId(long groupId) {
		_orgGroupRole.setGroupId(groupId);
	}

	/**
	* Gets the role ID of this org group role.
	*
	* @return the role ID of this org group role
	*/
	public long getRoleId() {
		return _orgGroupRole.getRoleId();
	}

	/**
	* Sets the role ID of this org group role.
	*
	* @param roleId the role ID of this org group role
	*/
	public void setRoleId(long roleId) {
		_orgGroupRole.setRoleId(roleId);
	}

	public boolean isNew() {
		return _orgGroupRole.isNew();
	}

	public void setNew(boolean n) {
		_orgGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_orgGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new OrgGroupRoleWrapper((OrgGroupRole)_orgGroupRole.clone());
	}

	public int compareTo(com.liferay.portal.model.OrgGroupRole orgGroupRole) {
		return _orgGroupRole.compareTo(orgGroupRole);
	}

	public int hashCode() {
		return _orgGroupRole.hashCode();
	}

	public com.liferay.portal.model.OrgGroupRole toEscapedModel() {
		return new OrgGroupRoleWrapper(_orgGroupRole.toEscapedModel());
	}

	public java.lang.String toString() {
		return _orgGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _orgGroupRole.toXmlString();
	}

	public boolean containsOrganization(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _orgGroupRole.containsOrganization(organizations);
	}

	public boolean containsGroup(
		java.util.List<com.liferay.portal.model.Group> groups) {
		return _orgGroupRole.containsGroup(groups);
	}

	public OrgGroupRole getWrappedOrgGroupRole() {
		return _orgGroupRole;
	}

	private OrgGroupRole _orgGroupRole;
}