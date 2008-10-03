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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portlet.portletsharing.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShareWidgetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neel Haldar
 *
 */
public class ShareWidgetUtil {

	public ShareWidgetUtil(HttpServletRequest req) {
		try {
			userId = PortalUtil.getUserId(req);
			groupId = PortalUtil.getPortletGroupId(req);

			currentLRUser = UserLocalServiceUtil.getUserById(
				PortalUtil.getUserId(req));

			fullUserName = currentLRUser.getFullName();

			themeDisplay = (ThemeDisplay) req.getAttribute(
				WebKeys.THEME_DISPLAY);

		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public List<User> getFriends() {
		int i = 0;

		try {
			int friendsCount = UserLocalServiceUtil.getSocialUsersCount(userId);

			socialUsers = UserLocalServiceUtil.getSocialUsers(
				userId, 0, friendsCount, null);

		}
		catch (Exception ex) {
			_log.error(ex, ex);
		}

		return socialUsers;
	}

	public void processShareRequest(
			String portletId, String portletTitle, String[] selectedFriends) {

		String className = PortalRequestInterpreter.class.getName();

		long classPK = 0;

		int type = ShareRequestKeys.SHARE_REQUEST;

		try {
			classPK = Long.parseLong(portletId);
		}
		catch (NumberFormatException nfx) {
			// All non-core portlets have String portletIds, get an unique long
			classPK = PortalUtil.getClassNameId(portletId);
		}

		String extraData = StringUtil.add(
			portletId, portletTitle,
			ShareRequestKeys.SHARE_EXTRA_DATA_DELIMETER);

		int len = selectedFriends.length;

		long[] shareFriendUserIds = new long[len];

		for (int i = 0; i < selectedFriends.length; i++) {
			try {
				shareFriendUserIds[i] = Long.parseLong(selectedFriends[i]);
			}
			catch (NumberFormatException nfe) {
				_log.error(nfe, nfe);
			}
		}

		for (int j = 0; j < shareFriendUserIds.length; j++) {
			try {
				groupId = 0;

				// classPK = classPK + receiverUserId makes an unique key;
				SocialRequestLocalServiceUtil.addRequest(
					userId, groupId, className,
					classPK + shareFriendUserIds[j], type, extraData,
					shareFriendUserIds[j]);
			}
			catch (Exception ex) {
				_log.error(ex, ex);
			}
		}
	}

	User currentLRUser = null;
	String fullUserName = null;
	long groupId = 0;
	long requestId = 0;
	List<User> socialUsers = null;
	ThemeDisplay themeDisplay = null;
	long userId = 0;

	private static Log _log = LogFactory.getLog(ShareWidgetUtil.class);

}
