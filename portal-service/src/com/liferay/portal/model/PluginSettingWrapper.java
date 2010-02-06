/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;


/**
 * <a href="PluginSettingSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _pluginSetting.setNew(n);
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