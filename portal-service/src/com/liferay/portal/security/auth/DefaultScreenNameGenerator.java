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

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * <a href="DefaultScreenNameGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DefaultScreenNameGenerator implements ScreenNameGenerator {

	public String generate(long companyId, long userId, String emailAddress)
		throws Exception {

		String screenName = null;

		if (Validator.isNotNull(emailAddress)) {
			screenName = StringUtil.extractFirst(
				emailAddress, StringPool.AT).toLowerCase();

			screenName = StringUtil.replace(
				screenName,
				new String[] {StringPool.SLASH, StringPool.UNDERLINE},
				new String[] {StringPool.PERIOD, StringPool.PERIOD});

			if (screenName.equals(DefaultScreenNameValidator.CYRUS) ||
				screenName.equals(DefaultScreenNameValidator.POSTFIX)) {

				screenName += StringPool.PERIOD + userId;
			}
		}
		else {
			screenName = String.valueOf(userId);
		}

		try {
			UserLocalServiceUtil.getUserByScreenName(companyId, screenName);
		}
		catch (NoSuchUserException nsue) {
			try {
				GroupLocalServiceUtil.getFriendlyURLGroup(
					companyId, StringPool.SLASH + screenName);
			}
			catch (NoSuchGroupException nsge) {
				return screenName;
			}
		}

		for (int i = 1;; i++) {
			String tempScreenName = screenName + StringPool.PERIOD + i;

			try {
				UserLocalServiceUtil.getUserByScreenName(
					companyId, tempScreenName);
			}
			catch (NoSuchUserException nsue) {
				try {
					GroupLocalServiceUtil.getFriendlyURLGroup(
						companyId, StringPool.SLASH + tempScreenName);
				}
				catch (NoSuchGroupException nsge) {
					screenName = tempScreenName;

					break;
				}
			}
		}

		return screenName;
	}

}