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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ExportLayoutsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		File file = null;

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long[] layoutIds = getLayoutIds(
				groupId, privateLayout,
				ParamUtil.getString(actionRequest, "layoutIds"));
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");
			String range = ParamUtil.getString(actionRequest, "range");

			Date startDate = null;
			Date endDate = null;

			if (range.equals("dateRange")) {
				int startDateMonth = ParamUtil.getInteger(
					actionRequest, "startDateMonth");
				int startDateDay = ParamUtil.getInteger(
					actionRequest, "startDateDay");
				int startDateYear = ParamUtil.getInteger(
					actionRequest, "startDateYear");
				int startDateHour = ParamUtil.getInteger(
					actionRequest, "startDateHour");
				int startDateMinute = ParamUtil.getInteger(
					actionRequest, "startDateMinute");
				int startDateAmPm = ParamUtil.getInteger(
					actionRequest, "startDateAmPm");

				if (startDateAmPm == Calendar.PM) {
					startDateHour += 12;
				}

				startDate = PortalUtil.getDate(
					startDateMonth, startDateDay, startDateYear, startDateHour,
					startDateMinute, themeDisplay.getTimeZone(),
					PortalException.class);

				int endDateMonth = ParamUtil.getInteger(
					actionRequest, "endDateMonth");
				int endDateDay = ParamUtil.getInteger(
					actionRequest, "endDateDay");
				int endDateYear = ParamUtil.getInteger(
					actionRequest, "endDateYear");
				int endDateHour = ParamUtil.getInteger(
					actionRequest, "endDateHour");
				int endDateMinute = ParamUtil.getInteger(
					actionRequest, "endDateMinute");
				int endDateAmPm = ParamUtil.getInteger(
					actionRequest, "endDateAmPm");

				if (endDateAmPm == Calendar.PM) {
					endDateHour += 12;
				}

				endDate = PortalUtil.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, themeDisplay.getTimeZone(),
					PortalException.class);
			}
			else if (range.equals("fromLastPublishDate")) {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				long lastPublishDate = GetterUtil.getLong(
					layoutSet.getSettingsProperty("last-publish-date"));

				if (lastPublishDate > 0) {
					endDate = new Date();

					startDate = new Date(lastPublishDate);
				}
			}
			else if (range.equals("last")) {
				int rangeLast = ParamUtil.getInteger(actionRequest, "last");

				Date now = new Date();

				startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

				endDate = now;
			}

			if (Validator.isNotNull(cmd)) {
				file = LayoutServiceUtil.exportLayoutsAsFile(
					groupId, privateLayout, layoutIds,
					actionRequest.getParameterMap(), startDate, endDate);

				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					actionRequest);
				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				ServletResponseUtil.sendFile(
					request, response, fileName, new FileInputStream(file),
					ContentTypes.APPLICATION_ZIP);

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else {
				actionResponse.setRenderParameter(
					"startDate", String.valueOf(startDate.getTime()));
				actionResponse.setRenderParameter(
					"endDate", String.valueOf(endDate.getTime()));
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());

			String pagesRedirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			sendRedirect(actionRequest, actionResponse, pagesRedirect);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.export_layouts"));
	}

	protected void addLayoutIds(
			List<Long> layoutIds, long groupId, boolean privateLayout,
			long layoutId)
		throws Exception {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, layoutId);

		for (Layout layout : layouts) {
			layoutIds.add(layout.getLayoutId());

			addLayoutIds(
				layoutIds, layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());
		}
	}

	protected long[] getLayoutIds(
			long groupId, boolean privateLayout, String layoutIdsJSON)
		throws Exception {

		if (Validator.isNull(layoutIdsJSON)) {
			return new long[0];
		}

		List<Long> layoutIds = new ArrayList<Long>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutIdsJSON);

		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId > 0) {
				layoutIds.add(layoutId);
			}

			if (jsonObject.getBoolean("includeChildren")) {
				addLayoutIds(layoutIds, groupId, privateLayout, layoutId);
			}
		}

		return ArrayUtil.toArray(layoutIds.toArray(new Long[layoutIds.size()]));
	}

	private static Log _log = LogFactoryUtil.getLog(ExportLayoutsAction.class);

}