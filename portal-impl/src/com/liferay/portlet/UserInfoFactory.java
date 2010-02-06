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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="UserInfoFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserInfoFactory {

	public static LinkedHashMap<String, String> getUserInfo(
		long userId, Portlet portlet) {

		if (userId <= 0) {
			return null;
		}

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			userInfo = getUserInfo(user, userInfo, portlet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return userInfo;
	}

	public static LinkedHashMap<String, String> getUserInfo(
		HttpServletRequest request, Portlet portlet) {

		if (request.getRemoteUser() == null) {
			return null;
		}

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

		try {
			User user = PortalUtil.getUser(request);

			userInfo = getUserInfo(user, userInfo, portlet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return userInfo;
	}

	public static LinkedHashMap<String, String> getUserInfo(
		User user, LinkedHashMap<String, String> userInfo, Portlet portlet) {

		PortletApp portletApp = portlet.getPortletApp();

		// Liferay user attributes

		try {
			UserAttributes userAttributes = new UserAttributes(user);

			// Mandatory user attributes

			userInfo.put(
				UserAttributes.LIFERAY_COMPANY_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_COMPANY_ID));

			userInfo.put(
				UserAttributes.LIFERAY_USER_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_USER_ID));

			// Portlet user attributes

			for (String attrName : portletApp.getUserAttributes()) {
				String attrValue = userAttributes.getValue(attrName);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		Map<String, String> unmodifiableUserInfo =
			Collections.unmodifiableMap((Map<String, String>)userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> cuaInstances =
			new HashMap<String, CustomUserAttributes>();

		for (Map.Entry<String, String> entry :
				portletApp.getCustomUserAttributes().entrySet()) {

			String attrName = entry.getKey();
			String attrCustomClass = entry.getValue();

			CustomUserAttributes cua = cuaInstances.get(attrCustomClass);

			if (cua == null) {
				if (portletApp.isWARFile()) {
					PortletContextBag portletContextBag =
						PortletContextBagPool.get(
							portletApp.getServletContextName());

					cua = portletContextBag.getCustomUserAttributes().get(
						attrCustomClass);

					cua = (CustomUserAttributes)cua.clone();
				}
				else {
					try {
						cua = (CustomUserAttributes)Class.forName(
							attrCustomClass).newInstance();
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

				cuaInstances.put(attrCustomClass, cua);
			}

			if (cua != null) {
				String attrValue = cua.getValue(attrName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}

		return userInfo;
	}

	private static Log _log = LogFactoryUtil.getLog(UserInfoFactory.class);

}