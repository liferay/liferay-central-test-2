/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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