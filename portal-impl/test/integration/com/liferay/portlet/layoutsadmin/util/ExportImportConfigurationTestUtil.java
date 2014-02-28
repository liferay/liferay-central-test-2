/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationTestUtil {

	public static ExportImportConfiguration addConfiguration(
			long groupId, int type)
		throws Exception {

		Map<String, Serializable> settingsMap = getDefaultSettingsMap(
			TestPropsValues.getUserId(), groupId);

		return addConfiguration(groupId, type, settingsMap);
	}

	public static ExportImportConfiguration addConfiguration(
			long groupId, int type, Map<String, Serializable> settingsMap)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, groupId, "name", "description", type, settingsMap,
				serviceContext);
	}

	public static Map<String, Serializable> getDefaultSettingsMap(
			long userId, long groupId)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		Date startDate = new Date();
		Date endDate = startDate;

		return ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
			userId, groupId, groupId, false, null, null, startDate, endDate,
			user.getLocale(), user.getTimeZone());
	}

}