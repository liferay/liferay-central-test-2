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

package com.liferay.portlet.messageboards;

import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.BaseFriendlyURLMapper;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="MBFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class MBFriendlyURLMapper extends BaseFriendlyURLMapper {

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

		String tabs2 = url.getParameter("tabs2");

		if (Validator.isNotNull(tabs2)) {
			return null;
		}

		String strutsAction = GetterUtil.getString(
			url.getParameter("struts_action"));

		if (strutsAction.equals("/message_boards/view")) {
			String categoryId = url.getParameter("categoryId");

			if (Validator.isNotNull(categoryId)) {
				friendlyURLPath = "/message_boards/category/" + categoryId;

				url.addParameterIncludedInPath("categoryId");
			}
		}
		else if (strutsAction.equals("/message_boards/view_message")) {
			String messageId = url.getParameter("messageId");

			if (Validator.isNotNull(messageId)) {
				friendlyURLPath = "/message_boards/message/" + messageId;

				url.addParameterIncludedInPath("messageId");
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

		int x = friendlyURLPath.indexOf("/", 1);
		int y = friendlyURLPath.indexOf("/", x + 1);

		if (y == -1) {
			addParam(params, "struts_action", "/message_boards/view");
			addParam(
				params, "categoryId",
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

			return;
		}

		String type = friendlyURLPath.substring(x + 1, y);

		if (type.equals("category")) {
			String categoryId =
				friendlyURLPath.substring(y + 1, friendlyURLPath.length());

			addParam(params, "struts_action", "/message_boards/view");
			addParam(params, "categoryId", categoryId);
		}
		else if (type.equals("message")) {
			String messageId =
				friendlyURLPath.substring(y + 1, friendlyURLPath.length());

			addParam(params, "struts_action", "/message_boards/view_message");
			addParam(params, "messageId", messageId);
		}
	}

	private static final String _MAPPING = "message_boards";

	private static final String _PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

}