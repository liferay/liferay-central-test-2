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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Calendar;
import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="AdminUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AdminUtil {

	public static String getUpdateUserPassword(
		HttpServletRequest request, long userId) {

		String password = PortalUtil.getUserPassword(request);

		if (userId != PortalUtil.getUserId(request)) {
			password = StringPool.BLANK;
		}

		return password;
	}

	public static String getUpdateUserPassword(
		ActionRequest actionRequest, long userId) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		return getUpdateUserPassword(request, userId);
	}

	public static User updateUser(
			HttpServletRequest request, long userId, String screenName,
			String emailAddress, String openId, String languageId,
			String timeZoneId, String greeting, String comments, String smsSn,
			String aimSn, String facebookSn, String icqSn, String jabberSn,
			String msnSn, String mySpaceSn, String skypeSn, String twitterSn,
			String ymSn)
		throws PortalException, SystemException {

		String password = getUpdateUserPassword(request, userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DATE);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;
		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.updateUser(
			userId, password, StringPool.BLANK, StringPool.BLANK,
			user.isPasswordReset(), user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), screenName, emailAddress, openId,
			languageId, timeZoneId, greeting, comments, contact.getFirstName(),
			contact.getMiddleName(), contact.getLastName(),
			contact.getPrefixId(), contact.getSuffixId(), contact.isMale(),
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			contact.getJobTitle(), groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);
	}

	public static User updateUser(
			ActionRequest actionRequest, long userId, String screenName,
			String emailAddress, String openId, String languageId,
			String timeZoneId, String greeting, String comments, String smsSn,
			String aimSn, String facebookSn, String icqSn, String jabberSn,
			String msnSn, String mySpaceSn, String skypeSn, String twitterSn,
			String ymSn)
		throws PortalException, SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		return updateUser(
			request, userId, screenName, emailAddress, openId, languageId,
			timeZoneId, greeting, comments, smsSn, aimSn, facebookSn, icqSn,
			jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn);
	}

}