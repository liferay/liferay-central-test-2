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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="WikiFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class WikiFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/wiki/view") ||
			strutsAction.equals("/wiki/view_all_pages") ||
			strutsAction.equals("/wiki/view_draft_pages") ||
			strutsAction.equals("/wiki/view_orphan_pages") ||
			strutsAction.equals("/wiki/view_recent_changes")) {

			String nodeId = portletURL.getParameter("nodeId");
			String nodeName = portletURL.getParameter("nodeName");

			if (Validator.isNotNull(nodeId) || Validator.isNotNull(nodeName)) {
				StringBundler sb = new StringBundler();

				sb.append(StringPool.SLASH);
				sb.append(_MAPPING);
				sb.append(StringPool.SLASH);

				if (Validator.isNotNull(nodeId)) {
					sb.append(nodeId);

					portletURL.addParameterIncludedInPath("nodeId");
				}
				else if (Validator.isNotNull(nodeName)) {
					sb.append(nodeName);

					portletURL.addParameterIncludedInPath("nodeName");
				}

				if (strutsAction.equals("/wiki/view")) {
					String title = portletURL.getParameter("title");

					if (Validator.isNotNull(title)) {
						sb.append(StringPool.SLASH);
						sb.append(HttpUtil.encodeURL(title));

						portletURL.addParameterIncludedInPath("title");
					}
				}
				else {
					sb.append(StringPool.SLASH);
					sb.append(strutsAction.substring(11));
				}

				friendlyURLPath = sb.toString();
			}
		}
		else if (strutsAction.equals("/wiki/view_tagged_pages")) {
			String tag = portletURL.getParameter("tag");

			if (Validator.isNotNull(tag)) {
				StringBundler sb = new StringBundler(6);

				sb.append(StringPool.SLASH);
				sb.append(_MAPPING);
				sb.append(StringPool.SLASH);
				sb.append("tag");
				sb.append(StringPool.SLASH);
				sb.append(HttpUtil.encodeURL(tag));

				portletURL.addParameterIncludedInPath("nodeId");
				portletURL.addParameterIncludedInPath("nodeName");
				portletURL.addParameterIncludedInPath("tag");

				friendlyURLPath = sb.toString();
			}
			else {
				friendlyURLPath = StringPool.BLANK;
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			WindowState windowState = portletURL.getWindowState();

			if (!windowState.equals(WindowState.NORMAL)) {
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

		addParam(params, "struts_action", "/wiki/view");

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		if (urlFragments.length >= 1) {
			String urlFragment0 = urlFragments[0];

			if (urlFragment0.equals("tag")) {
				if (urlFragments.length >= 2) {
					addParam(
						params, "struts_action", "/wiki/view_tagged_pages");

					String tag = HttpUtil.decodeURL(urlFragments[1]);

					addParam(params, "tag", tag);
				}
			}
			else {
				if (Validator.isNumber(urlFragment0)) {
					addParam(params, "nodeId", urlFragment0);
					addParam(params, "nodeName", StringPool.BLANK);
				}
				else {
					addParam(params, "nodeId", StringPool.BLANK);
					addParam(params, "nodeName", urlFragment0);
				}

				if (urlFragments.length >= 2) {
					String urlFragments1 = HttpUtil.decodeURL(urlFragments[1]);

					if (urlFragments1.equals("all_pages") ||
						urlFragments1.equals("draft_pages") ||
						urlFragments1.equals("orphan_pages") ||
						urlFragments1.equals("recent_changes")) {

						addParam(
							params, "struts_action",
							"/wiki/view_" + urlFragments1);
					}
					else {
						addParam(params, "title", urlFragments1);
					}
				}

				addParam(params, "tag", StringPool.BLANK);
			}

			if (urlFragments.length >= 3) {
				String windowState = urlFragments[2];

				addParam(params, "p_p_state", windowState);
			}
		}
	}

	private static final String _MAPPING = "wiki";

	private static final String _PORTLET_ID = PortletKeys.WIKI;

}