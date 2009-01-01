/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.mail.service;

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.mail.MailMessage;

import java.util.List;

/**
 * <a href="MailServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MailServiceUtil {

	public static void addForward(
		long userId, List<Filter> filters, List<String> emailAddresses,
		boolean leaveCopy) {

		_service.addForward(userId, filters, emailAddresses, leaveCopy);
	}

	public static void addUser(
		long userId, String password, String firstName, String middleName,
		String lastName, String emailAddress) {

		_service.addUser(
			userId, password, firstName, middleName, lastName, emailAddress);
	}

	public static void addVacationMessage(
		long userId, String emailAddress, String vacationMessage) {

		_service.addVacationMessage(userId, emailAddress, vacationMessage);
	}

	public static void deleteEmailAddress(long userId) {
		_service.deleteEmailAddress(userId);
	}

	public static void deleteUser(long userId, String companyMx) {
		_service.deleteUser(userId, companyMx);
	}

	public static void sendEmail(MailMessage mailMessage) {
		_service.sendEmail(mailMessage);
	}

	public static void updateBlocked(long userId, List<String> blocked) {
		_service.updateBlocked(userId, blocked);
	}

	public static void updateEmailAddress(long userId, String emailAddress) {
		_service.updateEmailAddress(userId, emailAddress);
	}

	public static void updatePassword(long userId, String password) {
		_service.updatePassword(userId, password);
	}

	public void setService(MailService service) {
		_service = service;
	}

	private static MailService _service;

}