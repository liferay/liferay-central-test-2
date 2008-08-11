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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.MailAddressException;
import com.liferay.portlet.messageboards.MailInServerNameException;
import com.liferay.portlet.messageboards.MailInUserNameException;
import com.liferay.portlet.messageboards.MailOutServerNameException;
import com.liferay.portlet.messageboards.MailOutUserNameException;
import com.liferay.portlet.messageboards.MailingListAddressException;
import com.liferay.portlet.messageboards.NoSuchMailingException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMailing;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;

/**
 * <a href="MBCategoryServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MBCategoryServiceTest extends BaseServiceTestCase {

	public void testAddCategoryWithoutMailing()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";

		MBCategory category =
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null);

		try {
			MBMailingServiceUtil.getMailingByCategory(category.getCategoryId());
			fail("The mailing should not be created");
		}
		catch (NoSuchMailingException e) {
			assertTrue(true);
		}
	}

	public void testAddCategoryWithMailing()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		MBCategory category =
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

		try {
			MBMailing mailing = MBMailingServiceUtil.getMailingByCategory(
				category.getCategoryId());
			assertNotNull(mailing);
		}
		catch (Exception e) {
			fail("The mailing should be created");
		}
	}

	public void testAddCategoryWithMailingAndMailingListAddressException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = null;
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailingListAddressException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailingListAddressException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndMailAddressException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = null;
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailAddressException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailAddressException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndMailInServerNameException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = null;
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailInServerNameException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailInServerNameException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndMailInUserNameException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = null;
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailInUserNameException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailInUserNameException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndMailOutServerNameException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.FALSE;
		String mailOutServerName = null;
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = "user.test";
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailOutServerNameException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailOutServerNameException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndMailOutUserNameException()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.FALSE;
		String mailOutServerName = "smtp.gmail.com";
		Boolean mailOutUseSSL = Boolean.TRUE;
		Integer mailOutServerPort = 110;
		String mailOutUserName = null;
		String mailOutPassword = "password";

		try {
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

			fail("A " + MailOutUserNameException.class.getName() +
				" should be throwed.");
		}
		catch (Exception e) {
			assertEquals(MailOutUserNameException.class, e.getClass());
		}
	}

	public void testAddCategoryWithMailingAndDefaultOutgoingServer()
		throws Exception {

		String name = "Test Category";
		String description = "This is a test category.";
		String mailingListAddress = "test@gmail.com";
		String mailAddress = "user.test@gmail.com";
		// Incoming
		String mailInServerName = "pop.gmail.com";
		String mailInProtocol = "pop3";
		Boolean mailInUseSSL = Boolean.TRUE;
		Integer mailInserverPort = 995;
		String mailInUserName = "user.test";
		String mailInPassword = "password";
		Integer mailInReadInterval = 5;
		// outgoing
		Boolean mailOutConfigured = Boolean.TRUE;
		String mailOutServerName = null;
		Boolean mailOutUseSSL = null;
		Integer mailOutServerPort = null;
		String mailOutUserName = null;
		String mailOutPassword = null;

		MBCategory category =
			MBCategoryServiceUtil.addCategory(
				TestPropsValues.LAYOUT_PLID,
				MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInserverPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword, null,
				null);

		try {
			MBMailing mailing =
				MBMailingServiceUtil.getMailingByCategory(
					category.getCategoryId());

			assertNotNull(mailing);
		}
		catch (NoSuchMailingException e) {
			fail(e.getMessage());
		}
	}

}