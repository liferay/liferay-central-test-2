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

package com.liferay.portlet.iframe.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * <a href="IFrameUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 */
public class IFrameUtil {

	public static String getPassword(
		PortletRequest portletRequest, String password) {

		if (!isPasswordTokenEnabled(portletRequest)) {
			return StringPool.BLANK;
		}

		if (Validator.isNull(password) || password.equals("@password@")) {
			password = PortalUtil.getUserPassword(portletRequest);
		}

		return password;
	}

	public static String getUserName(
			PortletRequest portletRequest, String userName)
		throws PortalException, SystemException {

		User user = PortalUtil.getUser(portletRequest);

		if (user == null) {
			return userName;
		}

		if (Validator.isNull(userName) || userName.equals("@user_id@")) {
			userName = portletRequest.getRemoteUser();
		}
		else if (userName.equals("@email_address@")) {
			userName = user.getEmailAddress();
		}
		else if (userName.equals("@screen_name@")) {
			userName = user.getScreenName();
		}

		return userName;
	}

	public static boolean isPasswordTokenEnabled(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String roleName = PropsValues.IFRAME_PASSWORD_PASSWORD_TOKEN_ROLE;

		if (Validator.isNull(roleName)) {
			return true;
		}

		if (layout.isPrivateLayout() && layout.getGroup().isUser()) {
			return true;
		}

		try {
			Role role = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), roleName);

			if (UserLocalServiceUtil.hasRoleUser(
					role.getRoleId(), themeDisplay.getUserId())) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Error getting role " + roleName + ". The password token " +
						"will be disabled.");
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(IFrameUtil.class);

}