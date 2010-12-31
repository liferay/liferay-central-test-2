/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class UserJSONSerializer {
	public static JSONObject toJSONObject(User model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uuid", model.getUuid());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("companyId", model.getCompanyId());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObject.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObject.put("modifiedDate", modifiedDateJSON);
		jsonObject.put("defaultUser", model.getDefaultUser());
		jsonObject.put("contactId", model.getContactId());
		jsonObject.put("password", model.getPassword());
		jsonObject.put("passwordEncrypted", model.getPasswordEncrypted());
		jsonObject.put("passwordReset", model.getPasswordReset());

		Date passwordModifiedDate = model.getPasswordModifiedDate();

		String passwordModifiedDateJSON = StringPool.BLANK;

		if (passwordModifiedDate != null) {
			passwordModifiedDateJSON = String.valueOf(passwordModifiedDate.getTime());
		}

		jsonObject.put("passwordModifiedDate", passwordModifiedDateJSON);
		jsonObject.put("digest", model.getDigest());
		jsonObject.put("reminderQueryQuestion", model.getReminderQueryQuestion());
		jsonObject.put("reminderQueryAnswer", model.getReminderQueryAnswer());
		jsonObject.put("graceLoginCount", model.getGraceLoginCount());
		jsonObject.put("screenName", model.getScreenName());
		jsonObject.put("emailAddress", model.getEmailAddress());
		jsonObject.put("facebookId", model.getFacebookId());
		jsonObject.put("openId", model.getOpenId());
		jsonObject.put("portraitId", model.getPortraitId());
		jsonObject.put("languageId", model.getLanguageId());
		jsonObject.put("timeZoneId", model.getTimeZoneId());
		jsonObject.put("greeting", model.getGreeting());
		jsonObject.put("comments", model.getComments());
		jsonObject.put("firstName", model.getFirstName());
		jsonObject.put("middleName", model.getMiddleName());
		jsonObject.put("lastName", model.getLastName());
		jsonObject.put("jobTitle", model.getJobTitle());

		Date loginDate = model.getLoginDate();

		String loginDateJSON = StringPool.BLANK;

		if (loginDate != null) {
			loginDateJSON = String.valueOf(loginDate.getTime());
		}

		jsonObject.put("loginDate", loginDateJSON);
		jsonObject.put("loginIP", model.getLoginIP());

		Date lastLoginDate = model.getLastLoginDate();

		String lastLoginDateJSON = StringPool.BLANK;

		if (lastLoginDate != null) {
			lastLoginDateJSON = String.valueOf(lastLoginDate.getTime());
		}

		jsonObject.put("lastLoginDate", lastLoginDateJSON);
		jsonObject.put("lastLoginIP", model.getLastLoginIP());

		Date lastFailedLoginDate = model.getLastFailedLoginDate();

		String lastFailedLoginDateJSON = StringPool.BLANK;

		if (lastFailedLoginDate != null) {
			lastFailedLoginDateJSON = String.valueOf(lastFailedLoginDate.getTime());
		}

		jsonObject.put("lastFailedLoginDate", lastFailedLoginDateJSON);
		jsonObject.put("failedLoginAttempts", model.getFailedLoginAttempts());
		jsonObject.put("lockout", model.getLockout());

		Date lockoutDate = model.getLockoutDate();

		String lockoutDateJSON = StringPool.BLANK;

		if (lockoutDate != null) {
			lockoutDateJSON = String.valueOf(lockoutDate.getTime());
		}

		jsonObject.put("lockoutDate", lockoutDateJSON);
		jsonObject.put("agreedToTermsOfUse", model.getAgreedToTermsOfUse());
		jsonObject.put("active", model.getActive());

		return jsonObject;
	}

	public static JSONArray toJSONArray(com.liferay.portal.model.User[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.User[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.User> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}