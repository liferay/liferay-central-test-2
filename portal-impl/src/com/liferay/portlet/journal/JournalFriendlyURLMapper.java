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

package com.liferay.portlet.journal;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;

/**
 * <a href="JournalFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class JournalFriendlyURLMapper implements FriendlyURLMapper {

	public String getMapping() {
		return _MAPPING;
	}

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
				portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/journal/rss") &&
			portletURL.getWindowState() == LiferayWindowState.EXCLUSIVE &&
			!portletURL.isAction()) {

			String groupId = portletURL.getParameter("groupId");
			String feedId = portletURL.getParameter("feedId");

			if (Validator.isNotNull(groupId) &&
					Validator.isNotNull(feedId)) {
				friendlyURLPath = "/journal/rss/" + groupId + "/" + feedId;

				portletURL.addParameterIncludedInPath("groupId");
				portletURL.addParameterIncludedInPath("feedId");
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			portletURL.addParameterIncludedInPath("p_p_id");
			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public void populateParams(String friendlyURLPath, Map params) {
		String[] parts = StringUtil.split(friendlyURLPath, "/");

		if (parts.length >= 4 && parts[2].equals("rss")) {
			processRssPath(parts, params);
		}
	}

	protected void processRssPath(String[] parts, Map params) {
		String namespace =
			StringPool.UNDERLINE + _PORTLET_ID + StringPool.UNDERLINE;

		params.put("p_p_id", _PORTLET_ID);
		params.put("p_p_action", "0");
		params.put("p_p_state", LiferayWindowState.EXCLUSIVE.toString());
		params.put("p_p_mode", PortletMode.VIEW.toString());

		params.put(namespace + "struts_action", "/journal/rss");

		if (parts.length == 4) {
			params.put(namespace + "synFeedId", parts[3]);
		}
		else if (parts.length == 5) {
			params.put(namespace + "groupId", parts[3]);
			params.put(namespace + "feedId", parts[4]);
		}
	}

	private static final String _MAPPING = "journal";

	private static final String _PORTLET_ID = PortletKeys.JOURNAL;

}
