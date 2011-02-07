/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sitesettings.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.action.ActionUtil;
import com.liferay.portlet.layoutsadmin.action.EditLayoutsAction;
import com.liferay.util.servlet.UploadException;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditSettingsAction extends EditLayoutsAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("logo")) {
				updateLogo(actionRequest);
			}
			else if (cmd.equals("monitoring")) {
				updateMonitoring(actionRequest);
			}
			else if (cmd.equals("robots")) {
				updateRobots(actionRequest);
			}
			else if (cmd.equals("staging")) {
				StagingUtil.updateStaging(actionRequest);
			}
			else if (cmd.equals("virtual_host")) {
				updateVirtualHost(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.site_settings.error");
			}
			else if (e instanceof ImageTypeException ||
					 e instanceof UploadException ||
					 e instanceof SystemException) {

					SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.site_settings.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.site_settings.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.site_settings.edit_settings"));
	}

	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		Group group = ActionUtil.getGroup(portletRequest);

		if (group != null) {
			return group;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		group = themeDisplay.getScopeGroup();

		portletRequest.setAttribute(WebKeys.GROUP, group);

		return group;
	}

	protected void updateLogo(ActionRequest actionRequest) throws Exception {
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		updateLogo(actionRequest, liveGroupId, stagingGroupId, false);
		updateLogo(actionRequest, liveGroupId, stagingGroupId, true);
	}

	protected void updateLogo(
			ActionRequest actionRequest, long liveGroupId, long stagingGroupId,
			boolean privateLayout)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		String privateLayoutPrefix = "public";

		if (privateLayout) {
			privateLayoutPrefix = "private";
		}

		boolean logo = ParamUtil.getBoolean(
			actionRequest, privateLayoutPrefix + "Logo");

		File file = uploadRequest.getFile(privateLayoutPrefix + "LogoFileName");
		byte[] bytes = FileUtil.getBytes(file);

		if (logo && ((bytes == null) || (bytes.length == 0))) {
			throw new UploadException();
		}

		LayoutSetServiceUtil.updateLogo(liveGroupId, privateLayout, logo, file);

		if (stagingGroupId > 0) {
			LayoutSetServiceUtil.updateLogo(
				stagingGroupId, privateLayout, logo, file);
		}
	}

	protected void updateMonitoring(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		String googleAnalyticsId = ParamUtil.getString(
			actionRequest, "googleAnalyticsId");

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"googleAnalyticsId", googleAnalyticsId);

		GroupServiceUtil.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateRobots(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		String publicRobots = ParamUtil.getString(
			actionRequest, "publicRobots");
		String privateRobots = ParamUtil.getString(
			actionRequest, "privateRobots");

		updateRobots(liveGroupId, false, publicRobots);
		updateRobots(liveGroupId, true, privateRobots);
	}

	protected void updateRobots(
			long groupId, boolean privateLayout, String robots)
		throws Exception {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.setProperty(privateLayout + "-robots.txt", robots);

		layoutSet.setSettingsProperties(settingsProperties);

		LayoutSetLocalServiceUtil.updateSettings(
			groupId, privateLayout, layoutSet.getSettings());
	}

	protected void updateVirtualHost(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		String publicVirtualHost = ParamUtil.getString(
			actionRequest, "publicVirtualHost");
		String privateVirtualHost = ParamUtil.getString(
			actionRequest, "privateVirtualHost");
		String friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, false, publicVirtualHost);

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, true, privateVirtualHost);

		GroupServiceUtil.updateFriendlyURL(liveGroupId, friendlyURL);

		Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			publicVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPublicVirtualHost");
			privateVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPrivateVirtualHost");
			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL");

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), false, publicVirtualHost);

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), true, privateVirtualHost);

			GroupServiceUtil.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);
		}
	}

}