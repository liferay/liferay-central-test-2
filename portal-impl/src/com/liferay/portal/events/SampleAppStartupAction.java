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

package com.liferay.portal.events;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="SampleAppStartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class can be used to populate an empty database programmatically. This
 * allows a developer to create sample data without relying on native SQL.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SampleAppStartupAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);

			doRun(companyId);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(long companyId) throws Exception {
		try {
			UserLocalServiceUtil.getUserByScreenName(companyId, "paul");

			// Do not populate the sample database if Paul already exists

			return;
		}
		catch (NoSuchUserException nsue) {
		}

		long creatorUserId = 0;
		boolean autoPassword = false;
		String password1 = "test";
		String password2 = password1;
		boolean autoScreenName = false;
		String screenName = "paul";
		String emailAddress = "paul@liferay.com";
		String openId = StringPool.BLANK;
		Locale locale = Locale.US;
		String firstName = "Paul";
		String middleName = StringPool.BLANK;
		String lastName = "Smith";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		User paulUser = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, openId, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				paulUser.getFullName() + " was created with user id " +
					paulUser.getUserId());
		}

		screenName = "jane";
		emailAddress = "jane@liferay.com";
		firstName = "Jane";

		User janeUser = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, openId, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				janeUser.getFullName() + " was created with user id " +
					janeUser.getUserId());
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(SampleAppStartupAction.class);

}