/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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