/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.test.client.portal;

import com.liferay.client.portal.model.UserSoap;
import com.liferay.client.portal.service.http.PortalServiceSoap;
import com.liferay.client.portal.service.http.PortalServiceSoapServiceLocator;
import com.liferay.client.portal.service.http.UserServiceSoap;
import com.liferay.client.portal.service.http.UserServiceSoapServiceLocator;
import com.liferay.test.TestConstants;
import com.liferay.test.client.BaseSoapTest;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="PortalSoapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalSoapTest extends BaseSoapTest {

	public void test() {
		try {
			getPortalService().test();

			boolean autoPassword = true;
			String password1 = null;
			String password2 = null;
			boolean passwordReset = false;
			boolean autoScreenName = true;
			String screenName = "";
			String emailAddress = "PortalSoapTest@liferay.com";
			String locale = Locale.getDefault().toString();
			String firstName = "PortalSoapTest";
			String middleName = "";
			String lastName = "PortalSoapTest";
			int prefixId = 0;
			int suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = null;
			long organizationId = 0;
			long locationId = 0;
			boolean sendMail = false;

			UserSoap user = getUserService().addUser(
				TestConstants.COMPANY_ID, autoPassword, password1, password2,
				passwordReset, autoScreenName, screenName, emailAddress, locale,
				firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				organizationId, locationId, false);

			user = getUserService().getUserByEmailAddress(
				TestConstants.COMPANY_ID, emailAddress);

			getUserService().deleteUser(user.getUserId());
		}
		catch (Exception e) {
			fail(e);
		}
	}

	protected PortalServiceSoap getPortalService() throws Exception {
		PortalServiceSoapServiceLocator locator =
			new PortalServiceSoapServiceLocator();

		PortalServiceSoap service = locator.getPortal_PortalService(
			getURL("Portal_PortalService"));

		return service;
	}

	protected UserServiceSoap getUserService() throws Exception {
		UserServiceSoapServiceLocator locator =
			new UserServiceSoapServiceLocator();

		UserServiceSoap service = locator.getPortal_UserService(
			getURL("Portal_UserService"));

		return service;
	}

}