/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringMaker;
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
 *
 */
public class WikiFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/wiki/view")) {
			String nodeId = portletURL.getParameter("nodeId");
			String nodeName = portletURL.getParameter("nodeName");
			String title = portletURL.getParameter("title");

			if (Validator.isNotNull(nodeId) || Validator.isNotNull(nodeName)) {
				StringMaker sm = new StringMaker();

				sm.append(StringPool.SLASH);
				sm.append(_MAPPING);
				sm.append(StringPool.SLASH);

				if (Validator.isNotNull(nodeId)) {
					sm.append(nodeId);

					portletURL.addParameterIncludedInPath("nodeId");
				}
				else if (Validator.isNotNull(nodeName)) {
					sm.append(nodeName);

					portletURL.addParameterIncludedInPath("nodeName");
				}

				if (Validator.isNotNull(title)) {
					sm.append(StringPool.SLASH);
					sm.append(HttpUtil.encodeURL(title));

					portletURL.addParameterIncludedInPath("title");

					WindowState windowState = portletURL.getWindowState();

					if (!windowState.equals(WindowState.NORMAL)) {
						sm.append(StringPool.SLASH);
						sm.append(windowState);
					}
				}

				friendlyURLPath = sm.toString();
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
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
			String nodeId = urlFragments[0];

			if (Validator.isNumber(nodeId)) {
				addParam(params, "nodeId", nodeId);
			}
			else {
				addParam(params, "nodeName", nodeId);
			}

			if (urlFragments.length >= 2) {
				String title = HttpUtil.decodeURL(urlFragments[1]);

				addParam(params, "title", title);

				if (urlFragments.length >= 3) {
					String windowState = urlFragments[2];

					addParam(params, "p_p_state", windowState);
				}
			}
		}
	}

	private static final String _MAPPING = "wiki";

	private static final String _PORTLET_ID = PortletKeys.WIKI;

}