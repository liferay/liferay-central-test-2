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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.util.RSSUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			ServletResponseUtil.sendFile(
				request, response, null, getRSS(request),
				ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, null, getRSS(request),
				ContentTypes.TEXT_XML_UTF8);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long plid = ParamUtil.getLong(request, "p_l_id");
		long companyId = ParamUtil.getLong(request, "companyId");
		long groupId = ParamUtil.getLong(request, "groupId");
		long organizationId = ParamUtil.getLong(request, "organizationId");
		int status = WorkflowConstants.STATUS_APPROVED;
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.DEFAULT_TYPE);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.DEFAULT_VERSION);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

		String feedURL =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/blogs/find_entry?";

		String entryURL = feedURL;

		String rss = StringPool.BLANK;

		if (companyId > 0) {
			feedURL = StringPool.BLANK;

			rss = BlogsEntryServiceUtil.getCompanyEntriesRSS(
				companyId, status, max, type, version, displayStyle, feedURL,
				entryURL, themeDisplay);
		}
		else if (groupId > 0) {
			feedURL += "p_l_id=" + plid;

			entryURL = feedURL;

			rss = BlogsEntryServiceUtil.getGroupEntriesRSS(
				groupId, status, max, type, version, displayStyle, feedURL,
				entryURL, themeDisplay);
		}
		else if (organizationId > 0) {
			feedURL = StringPool.BLANK;

			rss = BlogsEntryServiceUtil.getOrganizationEntriesRSS(
				organizationId, status, max, type, version, displayStyle,
				feedURL, entryURL, themeDisplay);
		}
		else if (layout != null) {
			if (layout.hasScopeGroup()) {
				groupId = layout.getScopeGroup().getGroupId();
			}
			else {
				groupId = layout.getGroupId();
			}

			feedURL =
				PortalUtil.getLayoutFullURL(themeDisplay) +
					Portal.FRIENDLY_URL_SEPARATOR + "blogs/rss";

			entryURL = feedURL;

			rss = BlogsEntryServiceUtil.getGroupEntriesRSS(
				groupId, status, max, type, version, displayStyle, feedURL,
				entryURL, themeDisplay);
		}

		return rss.getBytes(StringPool.UTF8);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}