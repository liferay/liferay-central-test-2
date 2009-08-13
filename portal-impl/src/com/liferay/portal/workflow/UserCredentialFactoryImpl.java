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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.UserCredentialFactory;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * <a href="UserCredentialFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The implementation of the factory creating {@link UserCredential} used by the
 * workflow proxy.
 * </p>
 *
 * @author Micha Kiener
 */
public class UserCredentialFactoryImpl implements UserCredentialFactory {

	/**
	 * @see com.liferay.portal.kernel.workflow.UserCredentialFactory#
	 *		createCredential(long)
	 */
	public UserCredential createCredential(long userId)
		throws WorkflowException {

		if (userId <= 0) {
			return null;
		}

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			UserCredential userCredential = new UserCredential();

			userCredential.setCompanyId(user.getCompanyId());
			userCredential.setEmailAddress(user.getEmailAddress());
			userCredential.setLocale(user.getLocale());
			userCredential.setLogin(user.getLogin());
			userCredential.setRoleIds(SetUtil.fromArray(user.getRoleIds()));
			userCredential.setScreenName(user.getScreenName());
			userCredential.setUserId(user.getUserId());

			return userCredential;
		}
		catch (Exception e) {
			throw new WorkflowException(
				"Error requesting user credentials for user id " + userId, e);
		}
	}

}