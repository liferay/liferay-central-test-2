/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.selenium;

import com.liferay.portalweb.util.TestPropsValues;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class DeleteEmailsTestCase extends BaseSeleniumTestCase {

	@Test
	public void testDeleteMails() throws Exception {
		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_1, TestPropsValues.EMAIL_PASSWORD_1);

		selenium.deleteAllEmails();

		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_2, TestPropsValues.EMAIL_PASSWORD_2);

		selenium.deleteAllEmails();
	}

	@Test
	public void testFailDeleteMails() throws Exception {
		String expectedException = "Command failure \"deleteAllEmails\": null";

		try {
			selenium.deleteAllEmails();
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

}