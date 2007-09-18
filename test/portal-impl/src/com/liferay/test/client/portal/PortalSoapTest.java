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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.test.TestConstants;
import com.liferay.test.TestProps;
import com.liferay.test.client.BaseSoapTest;

import java.util.Calendar;

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
		}
		catch (Exception e) {
			fail(e);
		}
	}

	public void testAddUser() {
		try {
			addUser(getUserService(), 0);
		}
		catch (Exception e) {
			fail(e);
		}
	}

	public void testConcurrentAddUser() {
		int threadCount = GetterUtil.getInteger(
			TestProps.get("portal.soap.add.user.thread.count"));

		AddUserWorker[] workers = new AddUserWorker[threadCount];

		try {
			for (int i = 0; i < threadCount; i++) {
				workers[i] = new AddUserWorker(getUserService(), i);

				workers[i].start();
			}

			while (true) {
				int activeThreads = 0;

				for (int i = 0; i < threadCount; i++) {
					if (workers[i].isAlive()) {
						activeThreads++;
					}
				}

				if (activeThreads == 0) {
					break;
				}
			}

			for (int i = 0; i < threadCount; i++) {
				Exception e = workers[i].getException();

				if (e != null) {
					throw e;
				}
			}
		}
		catch (Exception e) {
			fail(e);
		}
	}

	protected static void addUser(UserServiceSoap userService, int seed)
		throws Exception {

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = "";
		String emailAddress = "PortalSoapTest" + seed + "@liferay.com";
		String locale = LocaleUtil.getDefault().toString();
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

		UserSoap user = userService.addUser(
			TestConstants.COMPANY_ID, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			sendMail);

		System.out.println("Creating user " + emailAddress);

		user = userService.getUserByEmailAddress(
			TestConstants.COMPANY_ID, emailAddress);

		userService.deleteUser(user.getUserId());
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