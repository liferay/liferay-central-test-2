/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

public class PortletConstants {

	public static final String WAR_SEPARATOR = "_WAR_";

	public static final String INSTANCE_SEPARATOR = "_INSTANCE_";

	public static final String LAYOUT_SEPARATOR = "_LAYOUT_";

	public static final String DEFAULT_PREFERENCES = "<portlet-preferences />";

	public static final String USER_PRINCIPAL_STRATEGY_SCREEN_NAME =
		"screenName";

	public static final String USER_PRINCIPAL_STRATEGY_USER_ID = "userId";

	public static final String FACEBOOK_INTEGRATION_FBML = "fbml";

	public static final String FACEBOOK_INTEGRATION_IFRAME = "iframe";

	public static String getRootPortletId(String portletId) {
		int pos = portletId.indexOf(INSTANCE_SEPARATOR);

		if (pos == -1) {
			return portletId;
		}
		else {
			return portletId.substring(0, pos);
		}
	}

	public static String getInstanceId(String portletId) {
		int pos = portletId.indexOf(INSTANCE_SEPARATOR);

		if (pos == -1) {
			return null;
		}
		else {
			return portletId.substring(
				pos + INSTANCE_SEPARATOR.length(), portletId.length());
		}
	}

}