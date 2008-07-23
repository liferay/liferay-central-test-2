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

package com.liferay.portlet.social.util;

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

	long userId = 0;
	long groupId = 0;
	User currentLRUser = null;
	List<User> socialUsers = null;
	String fullUserName = null;
	ThemeDisplay themeDisplay = null;
	long requestId = 0;

	public ShareWidgetUtil(HttpServletRequest req) {
		try {
			userId = PortalUtil.getUserId(req);
			groupId = PortalUtil.getPortletGroupId(req);
			currentLRUser = UserLocalServiceUtil.getUserById(
					PortalUtil.getUserId(req));
			fullUserName = currentLRUser.getFullName();
			themeDisplay = (ThemeDisplay) req.getAttribute(
					WebKeys.THEME_DISPLAY);
		} catch (Exception e) {
			_log.error(e, e);
		}
	}

	public List<User> getFriends() {

		int i = 0;
		try {
			int friendsCount = UserLocalServiceUtil.getSocialUsersCount(userId);
			socialUsers = UserLocalServiceUtil.getSocialUsers(
					userId, 0, friendsCount, null);
		} catch (Exception ex) {
			_log.error(ex, ex);
		}
		return socialUsers;
	}

	public void processShareRequest(
			String portletId, String portletTitle, String[] selectedFriends) {

		String className = ShareRequestInterpreter.class.getName();
		long classPK = 0;
		int type = ShareRequestKeys.SHARE_REQUEST;
		try {
			classPK = Long.parseLong(portletId);
		} catch (NumberFormatException nfx) {
			// All non-core portlets have String portletIds, get an unique long
			classPK = PortalUtil.getClassNameId(portletId);
		}
		String data = themeDisplay.translate(
				"request-share-widget-message",
					new Object[]{fullUserName, portletTitle});
		String extraData = StringUtil.add(
				data, portletTitle,
					ShareRequestKeys.SHARE_EXTRA_DATA_DELIMETER);
		int len = selectedFriends.length;
		long[] shareFriendUserIds = new long[len];
		for (int i = 0; i < selectedFriends.length; i++) {
			try {
				shareFriendUserIds[i] = Long.parseLong(selectedFriends[i]);
			} catch (NumberFormatException nfe) {
				_log.error(nfe, nfe);
			}
		}
		for (int j = 0; j < shareFriendUserIds.length; j++) {
			try {
				groupId = 0;
				//classPK = classPK + receiverUserId makes an unique key;
				SocialRequestLocalServiceUtil.addRequest(
						userId, groupId, className, 
							classPK + shareFriendUserIds[j], type, extraData,
								shareFriendUserIds[j]);
			} catch (Exception ex) {
				ex.printStackTrace();
				_log.error(ex, ex);
			}
		}
	}
	private static Log _log = LogFactory.getLog(ShareWidgetUtil.class);
	
}