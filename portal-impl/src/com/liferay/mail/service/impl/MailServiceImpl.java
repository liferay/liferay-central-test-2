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

package com.liferay.mail.service.impl;

import com.liferay.mail.model.Filter;
import com.liferay.mail.service.MailService;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MailServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MailServiceImpl implements MailService {

	public void addForward(
		long userId, List<Filter> filters, List<String> emailAddresses,
		boolean leaveCopy) {

		if (_log.isDebugEnabled()) {
			_log.debug("addForward");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "addForward",
			new Object[] {
				new LongWrapper(userId), filters, emailAddresses,
				new BooleanWrapper(leaveCopy)
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void addUser(
		long userId, String password, String firstName, String middleName,
		String lastName, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("addUser");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "addUser",
			new Object[] {
				new LongWrapper(userId), password, firstName, middleName,
				lastName, emailAddress
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void addVacationMessage(
		long userId, String emailAddress, String vacationMessage) {

		if (_log.isDebugEnabled()) {
			_log.debug("addVacationMessage");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "addVacationMessage",
			new Object[] {
				new LongWrapper(userId), emailAddress, vacationMessage
			});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void deleteEmailAddress(long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteEmailAddress");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "deleteEmailAddress",
			new Object[] {new LongWrapper(userId)});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void deleteUser(long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteUser");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "deleteUser",
			new Object[] {new LongWrapper(userId)});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void sendEmail(MailMessage mailMessage) {
		if (_log.isDebugEnabled()) {
			_log.debug("sendEmail");
		}

		MessageBusUtil.sendMessage(DestinationNames.MAIL, mailMessage);
	}

	public void updateBlocked(long userId, List<String> blocked) {
		if (_log.isDebugEnabled()) {
			_log.debug("updateBlocked");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "updateBlocked",
			new Object[] {new LongWrapper(userId), blocked});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void updateEmailAddress(long userId, String emailAddress) {
		if (_log.isDebugEnabled()) {
			_log.debug("updateEmailAddress");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "updateEmailAddress",
			new Object[] {new LongWrapper(userId), emailAddress});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	public void updatePassword(long userId, String password) {
		if (_log.isDebugEnabled()) {
			_log.debug("updatePassword");
		}

		MethodWrapper methodWrapper = new MethodWrapper(
			PropsValues.MAIL_HOOK_IMPL, "updatePassword",
			new Object[] {new LongWrapper(userId), password});

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodWrapper);
	}

	private static Log _log = LogFactory.getLog(MailServiceImpl.class);

}