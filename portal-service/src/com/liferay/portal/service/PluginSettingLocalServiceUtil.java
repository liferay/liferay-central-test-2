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

package com.liferay.portal.service;

/**
 * <a href="PluginSettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PluginSettingLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static void checkPermission(java.lang.String userId,
		java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.PortalException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();
		pluginSettingLocalService.checkPermission(userId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getDefaultPluginSetting();
	}

	public static com.liferay.portal.model.PluginSetting getSettingOrDefault(
		java.lang.String companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getSettingOrDefault(companyId,
			pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting getByC_I_T(
		java.lang.String companyId, java.lang.String pluginId,
		java.lang.String pluginType) throws com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.getByC_I_T(companyId, pluginId,
			pluginType);
	}

	public static boolean hasPermission(java.lang.String userId,
		java.lang.String pluginId, java.lang.String pluginType) {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.hasPermission(userId, pluginId,
			pluginType);
	}

	public static com.liferay.portal.model.PluginSetting updatePluginSetting(
		java.lang.String companyId, java.lang.String pluginId,
		java.lang.String pluginType, java.lang.String roles, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PluginSettingLocalService pluginSettingLocalService = PluginSettingLocalServiceFactory.getService();

		return pluginSettingLocalService.updatePluginSetting(companyId,
			pluginId, pluginType, roles, active);
	}
}