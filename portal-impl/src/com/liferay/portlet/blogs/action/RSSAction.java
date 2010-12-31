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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.util.RSSUtil;

import java.io.OutputStream;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSAction extends PortletAction {

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		OutputStream outputStream = resourceResponse.getPortletOutputStream();

		try {
			outputStream.write(getRSS(resourceRequest));
		}
		finally {
			outputStream.close();
		}
	}

	protected byte[] getRSS(ResourceRequest resourceRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long plid = ParamUtil.getLong(resourceRequest, "p_l_id");
		long companyId = ParamUtil.getLong(resourceRequest, "companyId");
		long groupId = ParamUtil.getLong(resourceRequest, "groupId");
		long organizationId = ParamUtil.getLong(
			resourceRequest, "organizationId");
		int status = WorkflowConstants.STATUS_APPROVED;
		int max = ParamUtil.getInteger(
			resourceRequest, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			resourceRequest, "type", RSSUtil.DEFAULT_TYPE);
		double version = ParamUtil.getDouble(
			resourceRequest, "version", RSSUtil.DEFAULT_VERSION);
		String displayStyle = ParamUtil.getString(
			resourceRequest, "displayStyle",
			RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

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
			groupId = themeDisplay.getScopeGroupId();

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