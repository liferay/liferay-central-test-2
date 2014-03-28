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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Levente Hudák
 */
@ProviderType
public class ExportImportConfigurationHelper {

	public static ExportImportConfiguration
			addExportLayoutExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	public static ExportImportConfiguration
			addPublishLayoutLocalExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL);
	}

	public static ExportImportConfiguration
			addPublishLayoutRemoteExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE);
	}

	public static void exportLayoutsByExportImportConfiguration(
			PortletRequest portletRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		exportLayoutsByExportImportConfiguration(exportImportConfigurationId);
	}

	public static void publishLayoutsByExportImportConfiguration(
			long userId, long exportImportConfigurationId)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		boolean privateLayout = GetterUtil.getBoolean(
			settingsMap.get("privateLayout"));
		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		StagingUtil.publishLayouts(
			userId, sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, dateRange.getStartDate(), dateRange.getEndDate());
	}

	public static ExportImportConfiguration
			updateExportLayoutExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return updateExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	public static ExportImportConfiguration
			updatePublishLayoutLocalExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return updateExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL);
	}

	public static ExportImportConfiguration
			updatePublishLayoutRemoteExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return updateExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE);
	}

	protected static ExportImportConfiguration addExportImportConfiguration(
			PortletRequest portletRequest, int type)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				portletRequest, groupId, type);

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				themeDisplay.getUserId(), groupId, name, description, type,
				settingsMap, new ServiceContext());
	}

	protected static void exportLayoutsByExportImportConfiguration(
			long exportImportConfigurationId)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		long groupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		boolean privateLayout = GetterUtil.getBoolean(
			settingsMap.get("privateLayout"));
		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		StringBundler sb = new StringBundler(4);

		String name = exportImportConfiguration.getName();

		sb.append(name.replace(StringPool.SPACE, StringPool.UNDERLINE));
		sb.append(StringPool.DASH);
		sb.append(Time.getShortTimestamp());
		sb.append(".lar");

		String fileName = sb.toString();

		LayoutServiceUtil.exportLayoutsAsFileInBackground(
			fileName, groupId, privateLayout, layoutIds, parameterMap,
			dateRange.getStartDate(), dateRange.getEndDate(), fileName);
	}

	protected static ExportImportConfiguration updateExportImportConfiguration(
			PortletRequest portletRequest, int type)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long exportImportConfigurationId = ParamUtil.getLong(
			portletRequest, "exportImportConfigurationId");

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				portletRequest, groupId, type);

		return ExportImportConfigurationLocalServiceUtil.
			updateExportImportConfiguration(
				themeDisplay.getUserId(), exportImportConfigurationId, name,
				description, settingsMap, new ServiceContext());
	}

}