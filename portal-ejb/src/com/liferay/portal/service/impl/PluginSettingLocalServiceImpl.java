/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutTemplateImpl;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.base.PluginSettingLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PluginSettingUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PluginSettingLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PluginSettingLocalServiceImpl
	extends PluginSettingLocalServiceBaseImpl {

	public void checkPermission(
		String userId, String pluginId, String pluginType)
		throws PortalException {

		if (!hasPermission(userId, pluginId, pluginType)) {
			throw new PrincipalException();
		}
	}

	public PluginSetting getDefaultPluginSetting() {
		PluginSettingImpl pluginSetting = new PluginSettingImpl();
		pluginSetting.setRoles("");
		pluginSetting.setActive(true);
		return pluginSetting;
	}

	public PluginSetting getSettingOrDefault(
		String companyId, String pluginId, String pluginType)
		throws SystemException, PortalException {

		PluginSetting pluginSetting =
			getByC_I_T(companyId, pluginId, pluginType);

		if (pluginSetting == null) {
			Plugin plugin = null;

			if (pluginType.equals(ThemeImpl.PLUGIN_TYPE)) {
				plugin = ThemeLocalUtil.getTheme(companyId, pluginId);
			}
			else if (pluginType.equals(LayoutTemplateImpl.PLUGIN_TYPE)) {
				plugin = LayoutTemplateLocalUtil.getLayoutTemplate(
					pluginId, false, null);
			}

			if ((plugin == null)
				|| (plugin.getDefaultPluginSetting() == null)) {
				pluginSetting = getDefaultPluginSetting();
			}
			else {
				pluginSetting = plugin.getDefaultPluginSetting();
			}
		}
		return pluginSetting;
	}

	public PluginSetting getByC_I_T(
		String companyId, String pluginId, String pluginType)
		throws SystemException {
		List pluginSettings = PluginSettingUtil.findByC_I_T(
			companyId, pluginId, pluginType);

		if (pluginSettings.size() < 1) {
			return null;
		}
		else {
			return (PluginSetting) pluginSettings.get(0);
		}
	}

	public boolean hasPermission(
		String userId, String pluginId, String pluginType) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			PluginSetting pluginSetting =
				PluginSettingLocalServiceUtil.getSettingOrDefault(
					user.getCompanyId(), pluginId, pluginType);

			if (!pluginSetting.hasPermission(userId)) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (Exception e) {
			_log.warn(
				"Could not check permissions for " + pluginId, e);
			return false;
		}

	}
	public PluginSetting updatePluginSetting(
			String companyId, String pluginId, String pluginType, String roles,
			boolean active)
		throws PortalException, SystemException {
		pluginId = PortalUtil.getJsSafePortletName(pluginId);

		PluginSetting pluginSetting = getByC_I_T(
			companyId, pluginId, pluginType);
		if (pluginSetting == null) {
			long pluginSettingId = CounterLocalServiceUtil.increment(
				PluginSetting.class.getName());

			pluginSetting = PluginSettingUtil.create(pluginSettingId);
			pluginSetting.setCompanyId(companyId);
			pluginSetting.setPluginId(pluginId);
			pluginSetting.setPluginType(pluginType);
		}

		pluginSetting.setRoles(roles);
		pluginSetting.setActive(active);

		PluginSettingUtil.update(pluginSetting);

		return pluginSetting;

	}

	private static Log _log = LogFactory.getLog(PluginSettingServiceImpl.class);

}