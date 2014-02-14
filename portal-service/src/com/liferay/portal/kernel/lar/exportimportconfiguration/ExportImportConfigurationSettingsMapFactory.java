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

package com.liferay.portal.kernel.lar.exportimportconfiguration;

import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationSettingsMapFactory {

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long groupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		Date startDate, Date endDate, Locale locale, TimeZone timeZone) {

		return buildSettingsMap(
			userId, groupId, 0, privateLayout, layoutIdMap, parameterMap,
			startDate, endDate, locale, timeZone);
	}

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Date startDate, Date endDate,
		Locale locale, TimeZone timeZone) {

		Map<String, Serializable> settingsMap = buildSettingsMap(
			userId, sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			startDate, endDate, locale, timeZone);

		settingsMap.put("remoteAddress", remoteAddress);
		settingsMap.put("remoteGroupId", remoteGroupId);
		settingsMap.put("remotePathContext", remotePathContext);
		settingsMap.put("remotePort", remotePort);
		settingsMap.put("remotePrivateLayout", remotePrivateLayout);
		settingsMap.put("secureConnection", secureConnection);

		return settingsMap;
	}

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<Long, Boolean> layoutIdMap,
		Map<String, String[]> parameterMap, Date startDate, Date endDate,
		Locale locale, TimeZone timeZone) {

		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		if (endDate != null) {
			settingsMap.put("endDate", endDate);
		}

		if (MapUtil.isNotEmpty(layoutIdMap)) {
			HashMap<Long, Boolean> serializableLayoutIdMap =
				new HashMap<Long, Boolean>(layoutIdMap);

			settingsMap.put("layoutIdMap", serializableLayoutIdMap);
		}

		settingsMap.put("locale", locale);

		if (parameterMap != null) {
			HashMap<String, String[]> serializableParameterMap =
				new HashMap<String, String[]>(parameterMap);

			settingsMap.put("parameterMap", serializableParameterMap);
		}

		settingsMap.put("privateLayout", privateLayout);
		settingsMap.put("sourceGroupId", sourceGroupId);

		if (startDate != null) {
			settingsMap.put("startDate", startDate);
		}

		if (targetGroupId > 0) {
			settingsMap.put("targetGroupId", targetGroupId);
		}

		settingsMap.put("timezone", timeZone);
		settingsMap.put("userId", userId);

		return settingsMap;
	}

}