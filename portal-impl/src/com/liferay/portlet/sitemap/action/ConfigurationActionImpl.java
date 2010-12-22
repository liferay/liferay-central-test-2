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

package com.liferay.portlet.sitemap.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		String rootLayoutUuid = ParamUtil.getString(
			actionRequest, "rootLayoutUuid");
		String displayDepth = ParamUtil.getString(
			actionRequest, "displayDepth");
		boolean includeRootInTree = ParamUtil.getBoolean(
			actionRequest, "includeRootInTree");
		boolean showCurrentPage = ParamUtil.getBoolean(
			actionRequest, "showCurrentPage");
		boolean useHtmlTitle = ParamUtil.getBoolean(
			actionRequest, "useHtmlTitle");
		boolean showHiddenPages = ParamUtil.getBoolean(
			actionRequest, "showHiddenPages");

		if (Validator.isNull(rootLayoutUuid)) {
			includeRootInTree = false;
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		preferences.setValue("root-layout-uuid", rootLayoutUuid);
		preferences.setValue("display-depth", displayDepth);
		preferences.setValue(
			"include-root-in-tree", String.valueOf(includeRootInTree));
		preferences.setValue(
			"show-current-page", String.valueOf(showCurrentPage));
		preferences.setValue("use-html-title", String.valueOf(useHtmlTitle));
		preferences.setValue(
			"show-hidden-pages", String.valueOf(showHiddenPages));

		preferences.store();

		SessionMessages.add(
			actionRequest, portletConfig.getPortletName() + ".doConfigure");
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/site_map/configuration.jsp";
	}

}