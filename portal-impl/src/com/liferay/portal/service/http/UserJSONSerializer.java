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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;

import java.util.Date;
import java.util.List;

/**
 * <a href="UserJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link UserServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.UserServiceJSON
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
		jsonObj.put("reminderQueryQuestion", model.getReminderQueryQuestion());
		jsonObj.put("reminderQueryAnswer", model.getReminderQueryAnswer());
		jsonObj.put("graceLoginCount", model.getGraceLoginCount());
		jsonObj.put("screenName", model.getScreenName());
		jsonObj.put("emailAddress", model.getEmailAddress());
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