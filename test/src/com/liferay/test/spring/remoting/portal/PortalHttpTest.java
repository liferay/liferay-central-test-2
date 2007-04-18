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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserService;
import com.liferay.test.TestCase;
import com.liferay.test.TestConstants;
import com.liferay.test.spring.remoting.SpringRemotingTests;

import java.rmi.RemoteException;

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

	public void testUserRetrieval()
		throws SystemException, PortalException, RemoteException {
		String address = null;
		User user = null;

		UserService userService = getUserService();

		address = "test@" + TestConstants.COMPANY_ID;

		user = userService.getUserByEmailAddress(
			TestConstants.COMPANY_ID, address);

		assertEquals(address, user.getEmailAddress());
	}

	public void testUserCreationAndDeletion()
		throws SystemException, PortalException, RemoteException {
		UserService userService = getUserService();
		User user = userService.addUser(
			TestConstants.COMPANY_ID, autoPassword, password1, password2,
			passwordReset, autoScreenName, screenName, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, false);

		user = userService.getUserByEmailAddress(
			TestConstants.COMPANY_ID, emailAddress);

		userService.deleteUser(user.getUserId());
	}

	private UserService getUserService() {
		return (UserService) getBeanFactory().getBean(
			"userService" + SpringRemotingTests.getProtocol() + "SecureProxy");
	}

	private XmlBeanFactory getBeanFactory() {
		return new XmlBeanFactory(new ClassPathResource(
			"/portal-services.xml",
			PortalHttpTest.class));
	}

	private boolean autoScreenName = true;
	private String screenName = "UserServiceSpringRemotingTest";
	private boolean autoPassword = true;
	private String password1 = null;
	private String password2 = null;
	private boolean passwordReset = false;
	private String emailAddress = "UserServiceSpringRemotingTest@liferay.com";
	private Locale locale = Locale.getDefault();
	private String firstName = "UserServiceSpringRemotingTest";
	private String middleName = "";
	private String lastName = "UserServiceSpringRemotingTest";
	private String nickName = null;
	private int prefixId = 0;
	private int suffixId = 0;
	private boolean male = true;
	private int birthdayMonth = Calendar.JANUARY;
	private int birthdayDay = 1;
	private int birthdayYear = 1970;
	private String jobTitle = null;
	private String organizationId = null;
	private String locationId = null;

}