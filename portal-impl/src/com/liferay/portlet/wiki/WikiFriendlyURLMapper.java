/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.BaseFriendlyURLMapper;
import com.liferay.portlet.PortletURLImpl;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * <a href="WikiFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class WikiFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public String buildPath(PortletURL portletURL) {
		if (!(portletURL instanceof PortletURLImpl)) {
			return null;
		}

		PortletURLImpl url = (PortletURLImpl)portletURL;

		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			url.getParameter("struts_action"));

		if (strutsAction.equals("/wiki/view")) {
			String title = url.getParameter("title");
			String nodeId = url.getParameter("nodeId");

			if (Validator.isNotNull(nodeId)) {
				StringMaker sm = new StringMaker();

				sm.append(StringPool.SLASH);
				sm.append(_MAPPING);
				sm.append(StringPool.SLASH);
				sm.append(nodeId);

				url.addParameterIncludedInPath("nodeId");

				if (Validator.isNotNull(title)) {
					sm.append(StringPool.SLASH);
					sm.append(title);

					url.addParameterIncludedInPath("title");
				}

				friendlyURLPath = sm.toString();
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			url.addParameterIncludedInPath("p_p_id");
			url.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public void populateParams(String friendlyURLPath, Map params) {
		params.put("p_p_id", _PORTLET_ID);
		params.put("p_p_action", "0");
		params.put("p_p_state", WindowState.NORMAL.toString());
		params.put("p_p_mode", PortletMode.VIEW.toString());
		addParam(params, "struts_action", "/wiki/view");

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		if (urlFragments.length >= 1) {
			String nodeId = urlFragments[0];

			addParam(params, "nodeId", nodeId);

			if (urlFragments.length >= 2) {
				String title = urlFragments[1];

				addParam(params, "title", title);
			}
		}
	}

	private static final String _MAPPING = "wikipage";

	private static final String _PORTLET_ID = PortletKeys.WIKI;

}