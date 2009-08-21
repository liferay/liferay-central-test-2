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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;

/**
 * <a href="VerifyUser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VerifyUser extends VerifyProcess {

	protected void doVerify() throws Exception {
		List<User> users = UserLocalServiceUtil.getNoContacts();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + users.size() + " users with no contacts");
		}

		for (User user : users) {
			UserLocalServiceUtil.deleteUser(user.getUserId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Contacts verified for users");
		}

		users = UserLocalServiceUtil.getNoGroups();

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + users.size() + " users with no groups");
		}

		for (User user : users) {
			UserLocalServiceUtil.deleteUser(user.getUserId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Groups verified for users");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyUser.class);

}