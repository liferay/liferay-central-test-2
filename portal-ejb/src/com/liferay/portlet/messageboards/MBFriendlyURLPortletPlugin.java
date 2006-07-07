/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.servlet.FriendlyURLPortletPlugin;
import com.liferay.portlet.messageboards.model.MBCategory;

/**
 * <a href="MBFriendlyURLPortletPlugin.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBFriendlyURLPortletPlugin implements FriendlyURLPortletPlugin {

	public String getMapping() {
		return _MAPPING;
	}

	public String[] getValues(String url, int pos) {
		String friendlyURL = url.substring(0, pos);

		String queryString = "&p_p_id=19&p_p_action=0&p_p_state=maximized";

		int x = url.indexOf("/", pos + 1);
		int y = url.indexOf("/", x + 1);

		if (y == -1) {
			queryString +=
				"&_19_struts_action=%2Fmessage_boards%2Fview&_19_categoryId=" +
					MBCategory.DEFAULT_PARENT_CATEGORY_ID;

			return new String[] {friendlyURL, queryString};
		}

		String type = url.substring(x + 1, y);

		if (type.equals("category")) {
			String categoryId = url.substring(y + 1, url.length());

			queryString +=
				"&_19_struts_action=%2Fmessage_boards%2Fview&_19_categoryId=" +
					categoryId;
		}
		else if (type.equals("message")) {
			String messageId = url.substring(y + 1, url.length());

			queryString +=
				"&_19_struts_action=%2Fmessage_boards%2Fview_message" +
					"&_19_messageId=" + messageId;
		}

		return new String[] {friendlyURL, queryString};
	}

	public static final String _MAPPING = "message_boards";

}