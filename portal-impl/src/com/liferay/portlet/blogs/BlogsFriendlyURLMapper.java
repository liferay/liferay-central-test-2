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

package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="BlogsFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/blogs/rss")) {
			friendlyURLPath = "/blogs/rss";
		}
		else if (strutsAction.equals("/blogs/view_entry")) {
			String entryId = portletURL.getParameter("entryId");

			String urlTitle = portletURL.getParameter("urlTitle");

			if (Validator.isNotNull(entryId)) {
				friendlyURLPath = "/blogs/" + entryId;

				portletURL.addParameterIncludedInPath("entryId");
			}
			else if (Validator.isNotNull(urlTitle)) {
				friendlyURLPath = "/blogs/" + HttpUtil.encodeURL(urlTitle);

				portletURL.addParameterIncludedInPath("urlTitle");
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			WindowState windowState = portletURL.getWindowState();

			if (windowState.equals(WindowState.MAXIMIZED)) {
				friendlyURLPath += StringPool.SLASH + windowState;
			}

			portletURL.addParameterIncludedInPath("p_p_id");

			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		addParam(params, "p_p_id", _PORTLET_ID);
		addParam(params, "p_p_lifecycle", "0");
		addParam(params, "p_p_mode", PortletMode.VIEW);

		int x = friendlyURLPath.indexOf("/", 1);
		int y = friendlyURLPath.indexOf("/", x + 1);

		if (y == -1) {
			y = friendlyURLPath.length();
		}

		if ((x + 1) == friendlyURLPath.length()) {
			addParam(params, "struts_action", "/blogs/view");

			return;
		}

		String type = friendlyURLPath.substring(x + 1, y);

		if (type.equals("rss")) {
			addParam(params, "p_p_lifecycle", "1");
			addParam(params, "p_p_state", LiferayWindowState.EXCLUSIVE);

			addParam(params, "struts_action", "/blogs/rss");
		}
		else if (type.equals("trackback")) {
			addParam(params, "p_p_lifecycle", "1");
			addParam(params, "p_p_state", LiferayWindowState.EXCLUSIVE);

			addParam(params, "struts_action", "/blogs/trackback");

			type = friendlyURLPath.substring(y + 1);

			addParam(params, getEntryIdParam(type), type);
		}
		else {
			addParam(params, "struts_action", "/blogs/view_entry");

			addParam(params, getEntryIdParam(type), type);
		}

		if (friendlyURLPath.indexOf("maximized", x) != -1) {
			addParam(params, "p_p_state", WindowState.MAXIMIZED);
		}
	}

	protected String getEntryIdParam(String type) {
		if (Validator.isNumber(type)) {
			return "entryId";
		}
		else {
			return "urlTitle";
		}
	}

	private static final String _MAPPING = "blogs";

	private static final String _PORTLET_ID = PortletKeys.BLOGS;

}