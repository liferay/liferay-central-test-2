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
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;

import java.util.List;

import javax.mail.Session;

public class MailServiceUtil {

	public static void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		_service.addForward(
			companyId, userId, filters, emailAddresses, leaveCopy);
	}

	public static void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		_service.addUser(
			companyId, userId, password, firstName, middleName, lastName,
			emailAddress);
	}

	public static void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		_service.addVacationMessage(
			companyId, userId, emailAddress, vacationMessage);
	}

	public static void clearSession() {
		_service.clearSession();
	}

	public static void deleteEmailAddress(long companyId, long userId) {
		_service.deleteEmailAddress(companyId, userId);
	}

	public static void deleteUser(long companyId, long userId) {
		_service.deleteUser(companyId, userId);
	}

	public static Session getSession() throws SystemException {
		return _service.getSession();
	}

	public static void sendEmail(MailMessage mailMessage) {
		_service.sendEmail(mailMessage);
	}

	public static void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		_service.updateBlocked(companyId, userId, blocked);
	}

	public static void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		_service.updateEmailAddress(companyId, userId, emailAddress);
	}

	public static void updatePassword(
		long companyId, long userId, String password) {

		_service.updatePassword(companyId, userId, password);
	}

	public void setService(MailService service) {
		_service = service;
	}

	private static MailService _service;

}