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
 * This class is a wrapper for {@link PluginSetting}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSetting
 * @generated
 */
public class PluginSettingWrapper implements PluginSetting {
	public PluginSettingWrapper(PluginSetting pluginSetting) {
		_pluginSetting = pluginSetting;
	}

	public long getPrimaryKey() {
		return _pluginSetting.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_pluginSetting.setPrimaryKey(pk);
	}

	public long getPluginSettingId() {
		return _pluginSetting.getPluginSettingId();
	}

	public void setPluginSettingId(long pluginSettingId) {
		_pluginSetting.setPluginSettingId(pluginSettingId);
	}

	public long getCompanyId() {
		return _pluginSetting.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_pluginSetting.setCompanyId(companyId);
	}

	public java.lang.String getPluginId() {
		return _pluginSetting.getPluginId();
	}

	public void setPluginId(java.lang.String pluginId) {
		_pluginSetting.setPluginId(pluginId);
	}

	public java.lang.String getPluginType() {
		return _pluginSetting.getPluginType();
	}

	public void setPluginType(java.lang.String pluginType) {
		_pluginSetting.setPluginType(pluginType);
	}

	public java.lang.String getRoles() {
		return _pluginSetting.getRoles();
	}

	public void setRoles(java.lang.String roles) {
		_pluginSetting.setRoles(roles);
	}

	public boolean getActive() {
		return _pluginSetting.getActive();
	}

	public boolean isActive() {
		return _pluginSetting.isActive();
	}

	public void setActive(boolean active) {
		_pluginSetting.setActive(active);
	}

	public com.liferay.portal.model.PluginSetting toEscapedModel() {
		return _pluginSetting.toEscapedModel();
	}

	public boolean isNew() {
		return _pluginSetting.isNew();
	}

	public void setNew(boolean n) {
		_pluginSetting.setNew(n);
	}

	public boolean isCachedModel() {
		return _pluginSetting.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pluginSetting.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pluginSetting.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_pluginSetting.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pluginSetting.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pluginSetting.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pluginSetting.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _pluginSetting.clone();
	}

	public int compareTo(com.liferay.portal.model.PluginSetting pluginSetting) {
		return _pluginSetting.compareTo(pluginSetting);
	}

	public int hashCode() {
		return _pluginSetting.hashCode();
	}

	public java.lang.String toString() {
		return _pluginSetting.toString();
	}

	public java.lang.String toXmlString() {
		return _pluginSetting.toXmlString();
	}

	public void addRole(java.lang.String role) {
		_pluginSetting.addRole(role);
	}

	public java.lang.String[] getRolesArray() {
		return _pluginSetting.getRolesArray();
	}

	public void setRolesArray(java.lang.String[] rolesArray) {
		_pluginSetting.setRolesArray(rolesArray);
	}

	public boolean hasRoleWithName(java.lang.String roleName) {
		return _pluginSetting.hasRoleWithName(roleName);
	}

	public boolean hasPermission(long userId) {
		return _pluginSetting.hasPermission(userId);
	}

	public PluginSetting getWrappedPluginSetting() {
		return _pluginSetting;
	}

	private PluginSetting _pluginSetting;
}