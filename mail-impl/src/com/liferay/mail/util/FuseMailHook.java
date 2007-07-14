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

package com.liferay.mail.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.Http;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FuseMailHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FuseMailHook implements Hook {

	public void addForward(
		long userId, List filters, List emailAddresses, boolean leaveCopy) {
	}

	public void addUser(
		long userId, String password, String firstName, String middleName,
		String lastName, String emailAddress) {

		try {
			String mailUserId = getMailUserId(userId);

			StringMaker sm = new StringMaker();

			sm.append(_URL_PREFIX);
			sm.append("&request=order");
			sm.append("&user=");
			sm.append(mailUserId);
			sm.append("&password=");
			sm.append(password);
			sm.append("&first_name=");
			sm.append(firstName);
			sm.append("&last_name=");
			sm.append(lastName);
			sm.append("&account_type=");
			sm.append(_ACCOUNT_TYPE);
			sm.append("&alias[0]");
			sm.append(emailAddress);

			Http.submit(sm.toString());
        }
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addVacationMessage(
		long userId, String emailAddress, String vacationMessage) {
	}

	public void deleteEmailAddress(long userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			String mailUserId = getMailUserId(userId);

			StringMaker sm = new StringMaker();

			sm.append(_URL_PREFIX);
			sm.append("&request=removealias");
			sm.append("&user=");
			sm.append(mailUserId);
			sm.append("&alias");
			sm.append(user.getEmailAddress());

			Http.submit(sm.toString());
        }
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void deleteUser(long userId) {
		try {
			String mailUserId = getMailUserId(userId);

			StringMaker sm = new StringMaker();

			sm.append(_URL_PREFIX);
			sm.append("&request=terminate");
			sm.append("&user=");
			sm.append(mailUserId);

			Http.submit(sm.toString());
        }
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updateBlocked(long userId, List blocked) {
	}

	public void updateEmailAddress(long userId, String emailAddress) {
		try {
			deleteEmailAddress(userId);

			String mailUserId = getMailUserId(userId);

			StringMaker sm = new StringMaker();

			sm.append(_URL_PREFIX);
			sm.append("&request=modify");
			sm.append("&user=");
			sm.append(mailUserId);
			sm.append("&alias[0]");
			sm.append(emailAddress);

			Http.submit(sm.toString());
        }
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updatePassword(long userId, String password) {
		try {
			String mailUserId = getMailUserId(userId);

			StringMaker sm = new StringMaker();

			sm.append(_URL_PREFIX);
			sm.append("&request=modify");
			sm.append("&user=");
			sm.append(mailUserId);
			sm.append("&password=");
			sm.append(password);

			Http.submit(sm.toString());
        }
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getMailUserId(long userId) throws Exception {
		User user = UserLocalServiceUtil.getUserById(userId);

		StringMaker sm = new StringMaker();

		sm.append(user.getCompanyMx());
		sm.append(StringPool.PERIOD);
		sm.append(user.getUserId());

		return sm.toString();
	}

	private static final String _URL = PropsUtil.get(
		PropsUtil.MAIL_HOOK_FUSEMAIL_URL);

	private static final String _USERNAME = PropsUtil.get(
		PropsUtil.MAIL_HOOK_FUSEMAIL_USERNAME);

	private static final String _PASSWORD = PropsUtil.get(
		PropsUtil.MAIL_HOOK_FUSEMAIL_PASSWORD);

	private static final String _URL_PREFIX =
		_URL + "?PlatformUser=" + _USERNAME + "&PlatformPassword=" + _PASSWORD;

	private static final String _ACCOUNT_TYPE = PropsUtil.get(
		PropsUtil.MAIL_HOOK_FUSEMAIL_ACCOUNT_TYPE);

	private static Log _log = LogFactory.getLog(FuseMailHook.class);

}