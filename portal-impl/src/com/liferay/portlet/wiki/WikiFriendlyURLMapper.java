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
		String friendlyURLPath, Map<String, String[]> params,
		Map<String, Object> requestContext) {

		addParam(params, "p_p_id", _PORTLET_ID);
		addParam(params, "p_p_lifecycle", "0");
		addParam(params, "p_p_mode", PortletMode.VIEW);

		addParam(params, "struts_action", "/wiki/view");

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String wikiPath = friendlyURLPath.substring(x + 1);

		int lastSlash = wikiPath.lastIndexOf(StringPool.SLASH);

		if (lastSlash != -1) {
			String lastFragment = wikiPath.substring(lastSlash + 1);

			if (lastFragment.equalsIgnoreCase("exclusive") ||
				lastFragment.equalsIgnoreCase("maximized") ||
				lastFragment.equalsIgnoreCase("normal")) {

				addParam(params, "p_p_state", lastFragment);

				wikiPath = wikiPath.substring(0, lastSlash);
			}
		}

		String firstFragment = wikiPath;

		int firstSlash = wikiPath.indexOf(StringPool.SLASH);

		if (firstSlash != -1) {
			firstFragment = wikiPath.substring(0, firstSlash);
			wikiPath = wikiPath.substring(firstSlash + 1);
		}
		else {
			wikiPath = null;
		}

		if (firstFragment.equalsIgnoreCase("tag") && (wikiPath != null)) {
			addParam(params, "struts_action", "/wiki/view_tagged_pages");

			String tag = HttpUtil.decodeURL(wikiPath);

			addParam(params, "tag", tag);
		}
		else {
			if (Validator.isNumber(firstFragment)) {
				addParam(params, "nodeId", firstFragment);
				addParam(params, "nodeName", StringPool.BLANK);
			}
			else {
				addParam(params, "nodeId", StringPool.BLANK);
				addParam(params, "nodeName", firstFragment);
			}

			if (wikiPath != null) {
				if (wikiPath.equals("all_pages") ||
					wikiPath.equals("draft_pages") ||
					wikiPath.equals("orphan_pages") ||
					wikiPath.equals("recent_changes")) {

					addParam(
						params, "struts_action",
						"/wiki/view_" + wikiPath);
				}
				else {
					wikiPath = HttpUtil.decodeURL(wikiPath);

					addParam(params, "title", wikiPath);
				}
			}

			addParam(params, "tag", StringPool.BLANK);
		}
	}

	private static final String _MAPPING = "wiki";

	private static final String _PORTLET_ID = PortletKeys.WIKI;

}