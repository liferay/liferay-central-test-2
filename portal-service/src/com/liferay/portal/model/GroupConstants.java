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

package com.liferay.portal.model;

/**
 * <a href="GroupConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GroupConstants {

	public static final long DEFAULT_PARENT_GROUP_ID = 0;

	public static final long DEFAULT_LIVE_GROUP_ID = 0;

	public static final String CONTROL_PANEL = "Control Panel";

	public static final String CONTROL_PANEL_FRIENDLY_URL = "/control_panel";

	public static final String GUEST = "Guest";

	public static final String[] SYSTEM_GROUPS = {
		CONTROL_PANEL, GUEST
	};

	public static final int TYPE_COMMUNITY_OPEN = 1;

	public static final String TYPE_COMMUNITY_OPEN_LABEL = "open";

	public static final int TYPE_COMMUNITY_PRIVATE = 3;

	public static final String TYPE_COMMUNITY_PRIVATE_LABEL = "private";

	public static final int TYPE_COMMUNITY_RESTRICTED = 2;

	public static final String TYPE_COMMUNITY_RESTRICTED_LABEL = "restricted";

	public static String getTypeLabel(int type) {
		if (type == TYPE_COMMUNITY_OPEN) {
			return TYPE_COMMUNITY_OPEN_LABEL;
		}
		else if (type == TYPE_COMMUNITY_PRIVATE) {
			return TYPE_COMMUNITY_PRIVATE_LABEL;
		}
		else {
			return TYPE_COMMUNITY_RESTRICTED_LABEL;
		}
	}

}