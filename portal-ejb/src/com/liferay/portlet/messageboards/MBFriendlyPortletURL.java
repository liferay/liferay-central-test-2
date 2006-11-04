/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.PortletURLImplWrapper;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

/**
 * <a href="MBFriendlyPortletURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBFriendlyPortletURL extends PortletURLImplWrapper {

	public MBFriendlyPortletURL(ActionResponseImpl res, boolean action) {
		super(res, action);
	}

	public MBFriendlyPortletURL(RenderResponseImpl res, boolean action) {
		super(res, action);
	}

	protected String generateToString() {
		String toString = super.generateToString();

		String layoutFriendlyURL = getLayoutFriendlyURL();
		String tabs2 = getParameter("tabs2");

		if (Validator.isNull(layoutFriendlyURL) || Validator.isNotNull(tabs2)) {
			return toString;
		}

		String friendlyURL = null;

		String strutsAction = GetterUtil.getString(
			getParameter("struts_action"));

		if (strutsAction.equals("/message_boards/view")) {
			String categoryId = getParameter("categoryId");

			if (Validator.isNotNull(categoryId)) {
				friendlyURL = "/message_boards/category/" + categoryId;
			}
		}
		else if (strutsAction.equals("/message_boards/view_message")) {
			String messageId = getParameter("messageId");

			if (Validator.isNotNull(messageId)) {
				friendlyURL = "/message_boards/message/" + messageId;
			}
		}

		if (Validator.isNotNull(friendlyURL)) {
			int pos = toString.indexOf(layoutFriendlyURL);

			toString =
				toString.substring(0, pos + layoutFriendlyURL.length()) +
					friendlyURL;
		}

		return toString;
	}

}