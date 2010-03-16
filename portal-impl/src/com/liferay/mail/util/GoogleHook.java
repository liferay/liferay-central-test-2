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

package com.liferay.mail.util;

import com.liferay.mail.model.Filter;
import com.liferay.portal.googleapps.GoogleApps;
import com.liferay.portal.googleapps.GoogleAppsFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;

/**
 * <a href="GoogleHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GoogleHook implements Hook {

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {
	}

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		try {
			String nickname = _getNickname(emailAddress);

			GoogleApps googleApps = GoogleAppsFactory.getGoogleApps(companyId);

			googleApps.addUser(userId, password, firstName, lastName);
			googleApps.addNickname(userId, nickname);
			googleApps.addSendAs(
				userId,
				FullNameGeneratorFactory.getInstance().getFullName(
					firstName, middleName, lastName),
				emailAddress);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {
	}

	public void deleteEmailAddress(long companyId, long userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			String nickname = _getNickname(user.getEmailAddress());

			GoogleApps googleApps = GoogleAppsFactory.getGoogleApps(companyId);

			googleApps.deleteNickname(nickname);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void deleteUser(long companyId, long userId) {
		try {
			GoogleApps googleApps = GoogleAppsFactory.getGoogleApps(companyId);

			googleApps.deleteUser(userId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {
	}

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			deleteEmailAddress(companyId, userId);

			GoogleApps googleApps = GoogleAppsFactory.getGoogleApps(companyId);

			googleApps.addNickname(userId, emailAddress);
			googleApps.addSendAs(userId, user.getFullName(), emailAddress);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updatePassword(long companyId, long userId, String password) {
		try {
			GoogleApps googleApps = GoogleAppsFactory.getGoogleApps(companyId);

			googleApps.updatePassword(userId, password);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private String _getNickname(String emailAddress) {
		int pos = emailAddress.indexOf(StringPool.AT);

		return emailAddress.substring(0, pos);
	}

	private static Log _log = LogFactoryUtil.getLog(GoogleHook.class);

}