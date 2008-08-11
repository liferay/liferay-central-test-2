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

import com.liferay.portlet.messageboards.util.MBUtil;

import junit.framework.TestCase;

/**
 * <a href="MBUtilTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */

public class MBUtilTest extends TestCase {

	public void testGetMessageId() {

		int categoryId = 10700;
		int messageId = 10702;
		String mailId =
			"<mb." + categoryId + "." + messageId + "@events.liferay.com>";

		assertEquals(messageId, MBUtil.getMessageId(mailId));
	}

	public void testAddEmailReceivedByMailing() {

		String messageBody = "messsage body test";

		MBUtil.addEmailReceivedByMailing(messageBody);

		assertTrue(MBUtil.isEmailReceivedByMailing(messageBody));
	}

	public void testIsEmailReceivedByMailing() {

		String messageBody = "messsage body test";

		assertFalse(MBUtil.isEmailReceivedByMailing(messageBody));

		MBUtil.addEmailReceivedByMailing(messageBody);

		assertTrue(MBUtil.isEmailReceivedByMailing(messageBody));

		/*
		 * second call to this method MUST return false because the
		 * MBUtil.isEmailReceivedByMailing method remove the messageBody from
		 * the Hash if it find one that match!
		 */

		assertFalse(MBUtil.isEmailReceivedByMailing(messageBody));

	}

	public void testAddEmailReceivedByMailingArgumentNull() {

		try {
			MBUtil.addEmailReceivedByMailing(null);
			fail("Must throw a IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	public void testIsEmailReceivedByMailingArgumentNull() {

		try {
			MBUtil.isEmailReceivedByMailing(null);
			fail("Must throw a IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

}