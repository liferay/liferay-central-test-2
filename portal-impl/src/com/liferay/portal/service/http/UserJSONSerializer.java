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
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("companyId", model.getCompanyId());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObj.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObj.put("modifiedDate", modifiedDateJSON);
		jsonObj.put("defaultUser", model.getDefaultUser());
		jsonObj.put("contactId", model.getContactId());
		jsonObj.put("password", model.getPassword());
		jsonObj.put("passwordEncrypted", model.getPasswordEncrypted());
		jsonObj.put("passwordReset", model.getPasswordReset());

		Date passwordModifiedDate = model.getPasswordModifiedDate();

		String passwordModifiedDateJSON = StringPool.BLANK;

		if (passwordModifiedDate != null) {
			passwordModifiedDateJSON = String.valueOf(passwordModifiedDate.getTime());
		}

		jsonObj.put("passwordModifiedDate", passwordModifiedDateJSON);
		jsonObj.put("digest", model.getDigest());
		jsonObj.put("reminderQueryQuestion", model.getReminderQueryQuestion());
		jsonObj.put("reminderQueryAnswer", model.getReminderQueryAnswer());
		jsonObj.put("graceLoginCount", model.getGraceLoginCount());
		jsonObj.put("screenName", model.getScreenName());
		jsonObj.put("emailAddress", model.getEmailAddress());
		jsonObj.put("facebookId", model.getFacebookId());
		jsonObj.put("openId", model.getOpenId());
		jsonObj.put("portraitId", model.getPortraitId());
		jsonObj.put("languageId", model.getLanguageId());
		jsonObj.put("timeZoneId", model.getTimeZoneId());
		jsonObj.put("greeting", model.getGreeting());
		jsonObj.put("comments", model.getComments());
		jsonObj.put("firstName", model.getFirstName());
		jsonObj.put("middleName", model.getMiddleName());
		jsonObj.put("lastName", model.getLastName());
		jsonObj.put("jobTitle", model.getJobTitle());

		Date loginDate = model.getLoginDate();

		String loginDateJSON = StringPool.BLANK;

		if (loginDate != null) {
			loginDateJSON = String.valueOf(loginDate.getTime());
		}

		jsonObj.put("loginDate", loginDateJSON);
		jsonObj.put("loginIP", model.getLoginIP());

		Date lastLoginDate = model.getLastLoginDate();

		String lastLoginDateJSON = StringPool.BLANK;

		if (lastLoginDate != null) {
			lastLoginDateJSON = String.valueOf(lastLoginDate.getTime());
		}

		jsonObj.put("lastLoginDate", lastLoginDateJSON);
		jsonObj.put("lastLoginIP", model.getLastLoginIP());

		Date lastFailedLoginDate = model.getLastFailedLoginDate();

		String lastFailedLoginDateJSON = StringPool.BLANK;

		if (lastFailedLoginDate != null) {
			lastFailedLoginDateJSON = String.valueOf(lastFailedLoginDate.getTime());
		}

		jsonObj.put("lastFailedLoginDate", lastFailedLoginDateJSON);
		jsonObj.put("failedLoginAttempts", model.getFailedLoginAttempts());
		jsonObj.put("lockout", model.getLockout());

		Date lockoutDate = model.getLockoutDate();

		String lockoutDateJSON = StringPool.BLANK;

		if (lockoutDate != null) {
			lockoutDateJSON = String.valueOf(lockoutDate.getTime());
		}

		jsonObj.put("lockoutDate", lockoutDateJSON);
		jsonObj.put("agreedToTermsOfUse", model.getAgreedToTermsOfUse());
		jsonObj.put("active", model.getActive());

		return jsonObj;
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