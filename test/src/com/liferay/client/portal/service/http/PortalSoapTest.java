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

package com.liferay.client.portal.service.http;

import com.liferay.client.portal.model.UserModel;
import com.liferay.test.TestConstants;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="PortalSoapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortalSoapTest extends BaseSoapTest {

	public void test() {
		try {
			boolean autoUserId = true;
			String userId = "";
			boolean autoPassword = true;
			String password1 = null;
			String password2 = null;
			boolean passwordReset = false;
			String emailAddress = "PortalSoapTest@liferay.com";
			String locale = Locale.getDefault().toString();
			String firstName = "PortalSoapTest";
			String middleName = "";
			String lastName = "PortalSoapTest";
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

			UserModel user = getUserService().addUser(
				TestConstants.COMPANY_ID, autoUserId, userId, autoPassword,
				password1, password2, passwordReset, emailAddress,
				locale, firstName, middleName, lastName, nickName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				jobTitle, organizationId, locationId);

			getUserService().deleteUser(user.getUserId());
		}
		catch (Exception e) {
			fail();
		}
	}

	protected UserServiceSoap getUserService() throws Exception {
		UserServiceSoapServiceLocator locator =
			new UserServiceSoapServiceLocator();

		UserServiceSoap service = locator.getPortal_UserService(
			getURL("Portal_UserService"));

		return service;
	}

}