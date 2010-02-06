/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

/**
 * <a href="DummyHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DummyHook implements Hook {

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		if (_log.isDebugEnabled()) {
			_log.debug("addForward");
		}
	}

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("addUser");
		}
	}

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		if (_log.isDebugEnabled()) {
			_log.debug("addVacationMessage");
		}
	}

	public void deleteEmailAddress(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteEmailAddress");
		}
	}

	public void deleteUser(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteUser");
		}
	}

	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateBlocked");
		}
	}

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateEmailAddress");
		}
	}

	public void updatePassword(long companyId, long userId, String password) {
		if (_log.isDebugEnabled()) {
			_log.debug("updatePassword");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DummyHook.class);

}