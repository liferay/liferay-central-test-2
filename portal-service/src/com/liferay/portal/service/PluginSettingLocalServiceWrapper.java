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

package com.liferay.portal.service;


/**
 * <a href="PluginSettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PluginSettingLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingLocalService
 * @generated
 */
public class PluginSettingLocalServiceWrapper
	implements PluginSettingLocalService {
	public PluginSettingLocalServiceWrapper(
		PluginSettingLocalService pluginSettingLocalService) {
		_pluginSettingLocalService = pluginSettingLocalService;
	}

	public com.liferay.portal.model.PluginSetting addPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.addPluginSetting(pluginSetting);
	}

	public com.liferay.portal.model.PluginSetting createPluginSetting(
		long pluginSettingId) {
		return _pluginSettingLocalService.createPluginSetting(pluginSettingId);
	}

	public void deletePluginSetting(long pluginSettingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_pluginSettingLocalService.deletePluginSetting(pluginSettingId);
	}

	public void deletePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		_pluginSettingLocalService.deletePluginSetting(pluginSetting);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.PluginSetting getPluginSetting(
		long pluginSettingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pluginSettingLocalService.getPluginSetting(pluginSettingId);
	}

	public java.util.List<com.liferay.portal.model.PluginSetting> getPluginSettings(
		int start, int end) throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.getPluginSettings(start, end);
	}

	public int getPluginSettingsCount()
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.getPluginSettingsCount();
	}

	public com.liferay.portal.model.PluginSetting updatePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.updatePluginSetting(pluginSetting);
	}

	public com.liferay.portal.model.PluginSetting updatePluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.updatePluginSetting(pluginSetting,
			merge);
	}

	public void checkPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType) throws com.liferay.portal.PortalException {
		_pluginSettingLocalService.checkPermission(userId, pluginId, pluginType);
	}

	public com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		return _pluginSettingLocalService.getDefaultPluginSetting();
	}

	public com.liferay.portal.model.PluginSetting getPluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.getPluginSetting(companyId, pluginId,
			pluginType);
	}

	public boolean hasPermission(long userId, java.lang.String pluginId,
		java.lang.String pluginType) {
		return _pluginSettingLocalService.hasPermission(userId, pluginId,
			pluginType);
	}

	public com.liferay.portal.model.PluginSetting updatePluginSetting(
		long companyId, java.lang.String pluginId, java.lang.String pluginType,
		java.lang.String roles, boolean active)
		throws com.liferay.portal.SystemException {
		return _pluginSettingLocalService.updatePluginSetting(companyId,
			pluginId, pluginType, roles, active);
	}

	public PluginSettingLocalService getWrappedPluginSettingLocalService() {
		return _pluginSettingLocalService;
	}

	private PluginSettingLocalService _pluginSettingLocalService;
}