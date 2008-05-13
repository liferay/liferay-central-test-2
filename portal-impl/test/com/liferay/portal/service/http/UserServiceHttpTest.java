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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.TestPropsValues;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="UserServiceHttpTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserServiceHttpTest extends BaseServiceHttpTestCase {

	public void testAddUser() throws Exception {
		addUser();
	}

	public void testDeleteUser() throws Exception {
		User user = addUser();

		UserServiceHttp.deleteUser(getHttpPrincipal(), user.getUserId());
	}

	public void testGetUser() throws Exception {
		User user = addUser();

		UserServiceHttp.getUserByEmailAddress(
			getHttpPrincipal(), TestPropsValues.COMPANY_ID,
			user.getEmailAddress());
	}

	protected User addUser() throws Exception {
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = "";
		String emailAddress =
			"UserServiceHttpTest." + nextLong() + "@liferay.com";
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceHttpTest";
		String middleName = "";
		String lastName = "UserServiceHttpTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] organizationIds = new long[0];
		boolean sendMail = false;

		return UserServiceHttp.addUser(
			getHttpPrincipal(), TestPropsValues.COMPANY_ID, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			organizationIds, sendMail);
	}

}