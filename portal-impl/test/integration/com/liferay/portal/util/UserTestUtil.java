/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Alberto Chaparro
 * @author Manuel de la Peña
 */
public class UserTestUtil {

	public static User addUser(
			String screenName, boolean autoScreenName, long[] groupIds)
		throws Exception {

		return addUser(
			screenName, autoScreenName, "ServiceTestSuite", "ServiceTestSuite",
			groupIds);
	}

	public static User addUser(
			String screenName, boolean autoScreenName, String firstName,
			String lastName, long[] groupIds)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			TestPropsValues.getCompanyId(), screenName);

		if (user != null) {
			return user;
		}

		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		String emailAddress =
			"ServiceTestSuite." + ServiceTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String middleName = StringPool.BLANK;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		return UserLocalServiceUtil.addUser(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendMail, ServiceTestUtil.getServiceContext());
	}

	public static User addUser(String screenName, long groupId)
		throws Exception {

		if (Validator.isNull(screenName)) {
			return addUser(null, true, new long[] {groupId});
		}
		else {
			return addUser(screenName, false, new long[] {groupId});
		}
	}

}