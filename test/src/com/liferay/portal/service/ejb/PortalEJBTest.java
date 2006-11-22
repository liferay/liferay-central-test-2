/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.ejb;

import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.PortalService;
import com.liferay.portal.service.spring.UserService;
import com.liferay.test.TestConstants;

import java.util.Calendar;
import java.util.Locale;

/**
 * <a href="PortalEJBTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortalEJBTest extends BaseEJBTest {

	public void test() {
		try {
			getPortalService().test();

			boolean autoUserId = true;
			String userId = "";
			boolean autoPassword = true;
			String password1 = null;
			String password2 = null;
			boolean passwordReset = false;
			String emailAddress = "UserServiceEJBTest@liferay.com";
			Locale locale = Locale.getDefault();
			String firstName = "UserServiceEJBTest";
			String middleName = "";
			String lastName = "UserServiceEJBTest";
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

			User user = getUserService().addUser(
				TestConstants.COMPANY_ID, autoUserId, userId, autoPassword,
				password1, password2, passwordReset, emailAddress, locale,
				firstName, middleName, lastName, nickName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				organizationId, locationId);

			getUserService().deleteUser(user.getUserId());
		}
		catch (Exception e) {
			fail(e);
		}
	}

	protected PortalService getPortalService() throws Exception {
		PortalServiceHome home = (PortalServiceHome)lookup(
			"com_liferay_portal_service_ejb_PortalServiceEJB",
			PortalServiceHome.class);

		return home.create();
	}

	protected UserService getUserService() throws Exception {
		UserServiceHome home = (UserServiceHome)lookup(
			"com_liferay_portal_service_ejb_UserServiceEJB",
			UserServiceHome.class);

		return home.create();
	}

}