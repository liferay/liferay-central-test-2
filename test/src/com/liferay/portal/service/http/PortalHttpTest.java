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

package com.liferay.portal.service.http;

import com.liferay.portal.model.User;
import com.liferay.test.TestConstants;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="PortalHttpTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortalHttpTest extends BaseHttpTest {

	public void test() {
		try {
			boolean autoUserId = true;
			String userId = "";
			boolean autoPassword = true;
			String password1 = null;
			String password2 = null;
			boolean passwordReset = false;
			String emailAddress = "UserServiceHttpTest@liferay.com";
			Locale locale = Locale.getDefault();
			String firstName = "UserServiceHttpTest";
			String middleName = "";
			String lastName = "UserServiceHttpTest";
			String nickName = null;
			String prefixId = null;
			String suffixId = null;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = null;
			String organizationId = null;
			String locationId = null;

			User user = UserServiceHttp.addUser(
				getHttpPrincipal(), TestConstants.COMPANY_ID, autoUserId,
				userId, autoPassword, password1, password2, passwordReset,
				emailAddress, locale, firstName, middleName, lastName, nickName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, organizationId, locationId);

			UserServiceHttp.deleteUser(getHttpPrincipal(), user.getUserId());
		}
		catch (Exception e) {
			fail();
		}
	}

}