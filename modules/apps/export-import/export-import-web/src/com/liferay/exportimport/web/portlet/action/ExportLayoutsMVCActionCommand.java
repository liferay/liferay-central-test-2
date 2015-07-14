/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.exportimport.web.portlet.action;

import com.liferay.exportimport.web.constants.ExportImportPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.exportimport.LARFileNameException;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.exportimport.service.ExportImportServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=exportLayouts"
	},
	service = MVCActionCommand.class
)
public class ExportLayoutsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return;
		}

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "liveGroupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long[] layoutIds = getLayoutIds(actionRequest);

			String taskName = StringPool.BLANK;

			if (privateLayout) {
				taskName = LanguageUtil.get(
					actionRequest.getLocale(), "private-pages");
			}
			else {
				taskName = LanguageUtil.get(
					actionRequest.getLocale(), "public-pages");
			}

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
					themeDisplay.getUserId(), groupId, privateLayout, layoutIds,
					actionRequest.getParameterMap(), themeDisplay.getLocale(),
					themeDisplay.getTimeZone());

			ServiceContext serviceContext = new ServiceContext();

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addExportImportConfiguration(
						themeDisplay.getUserId(), groupId, taskName,
						StringPool.BLANK,
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						settingsMap, WorkflowConstants.STATUS_DRAFT,
						serviceContext);

			ExportImportServiceUtil.exportLayoutsAsFileInBackground(
				exportImportConfiguration);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			if (!(e instanceof LARFileNameException)) {
				_log.error(e, e);
			}
		}
	}

	protected long[] getLayoutIds(PortletRequest portletRequest)
		throws Exception {

		Set<Layout> layouts = new LinkedHashSet<>();

		Map<Long, Boolean> layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(
			portletRequest);

		for (Map.Entry<Long, Boolean> entry : layoutIdMap.entrySet()) {
			long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
			boolean includeChildren = entry.getValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			if (includeChildren) {
				layouts.addAll(layout.getAllChildren());
			}
		}

		return ExportImportHelperUtil.getLayoutIds(
			new ArrayList<Layout>(layouts));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportLayoutsMVCActionCommand.class);

}