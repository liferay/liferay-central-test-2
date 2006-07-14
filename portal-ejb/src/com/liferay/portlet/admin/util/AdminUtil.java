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

package com.liferay.portlet.admin.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.service.spring.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.util.StringPool;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="AdminUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminUtil {

	public static String getUpdateUserPassword(
		HttpServletRequest req, String userId) {

		String password = PortalUtil.getUserPassword(req);

		if (!userId.equals(PortalUtil.getUserId(req))) {
			password = StringPool.BLANK;
		}

		return password;
	}

	public static String getUpdateUserPassword(
		ActionRequest req, String userId) {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUpdateUserPassword(reqImpl.getHttpServletRequest(), userId);
	}

	public static User updateUser(
			HttpServletRequest req, String userId, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String smsSn, String aimSn,
			String icqSn, String jabberSn, String msnSn, String skypeSn,
			String ymSn)
		throws PortalException, SystemException {

		String password = getUpdateUserPassword(req, userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		Contact contact = user.getContact();

		Calendar birthdayCal = new GregorianCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DATE);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		return UserServiceUtil.updateUser(
			userId, password, emailAddress, languageId, timeZoneId, greeting,
			resolution, comments, contact.getFirstName(),
			contact.getMiddleName(), contact.getLastName(),
			contact.getNickName(), contact.getPrefixId(), contact.getSuffixId(),
			contact.isMale(), birthdayMonth, birthdayDay, birthdayYear, smsSn,
			aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, contact.getJobTitle(),
			user.getOrganization().getOrganizationId(),
			user.getLocation().getOrganizationId());
	}

	public static User updateUser(
			ActionRequest req, String userId, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String smsSn, String aimSn,
			String icqSn, String jabberSn, String msnSn, String skypeSn,
			String ymSn)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return updateUser(
			reqImpl.getHttpServletRequest(), userId, emailAddress, languageId,
			timeZoneId, greeting, resolution, comments, smsSn, aimSn, icqSn,
			jabberSn, msnSn, skypeSn, ymSn);
	}

}