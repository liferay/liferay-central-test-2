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

package com.liferay.test.spring.remoting.portal;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserService;
import com.liferay.test.TestCase;
import com.liferay.test.TestConstants;
import com.liferay.test.spring.remoting.SpringRemotingTests;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * <a href="PortalHttpTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PortalHttpTest extends TestCase {

	public void testUserCreationAndDeletion() throws Exception {
		UserService userService = getUserService();

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean passwordReset = false;
		boolean autoScreenName = true;
		String screenName = "UserServiceSpringRemotingTest";
		String emailAddress = "UserServiceSpringRemotingTest@liferay.com";
		Locale locale = Locale.getDefault();
		String firstName = "UserServiceSpringRemotingTest";
		String middleName = "";
		String lastName = "UserServiceSpringRemotingTest";
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

		User user = userService.addUser(
			TestConstants.COMPANY_ID, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			sendMail);

		user = userService.getUserByEmailAddress(
			TestConstants.COMPANY_ID, emailAddress);

		userService.deleteUser(user.getUserId());
	}

	public void testUserRetrieval() throws Exception {
		UserService userService = getUserService();

		String emailAddress = "test@" + TestConstants.COMPANY_ID;

		User user = userService.getUserByEmailAddress(
			TestConstants.COMPANY_ID, emailAddress);

		assertEquals(emailAddress, user.getEmailAddress());
	}

	protected XmlBeanFactory getBeanFactory() {
		return new XmlBeanFactory(new ClassPathResource(
			"/portal-services.xml", PortalHttpTest.class));
	}

	protected UserService getUserService() {
		return (UserService)getBeanFactory().getBean(
			"userService" + SpringRemotingTests.getProtocol() + "SecureProxy");
	}

}