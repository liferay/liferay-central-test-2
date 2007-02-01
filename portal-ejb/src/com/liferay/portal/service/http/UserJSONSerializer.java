/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="UserJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserJSONSerializer {
	public static JSONObject toJSONObject(User model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("contactId", model.getContactId().toString());
		jsonObj.put("password", model.getPassword().toString());
		jsonObj.put("passwordEncrypted", model.getPasswordEncrypted());
		jsonObj.put("passwordExpirationDate",
			model.getPasswordExpirationDate().toString());
		jsonObj.put("passwordReset", model.getPasswordReset());
		jsonObj.put("emailAddress", model.getEmailAddress().toString());
		jsonObj.put("languageId", model.getLanguageId().toString());
		jsonObj.put("timeZoneId", model.getTimeZoneId().toString());
		jsonObj.put("greeting", model.getGreeting().toString());
		jsonObj.put("resolution", model.getResolution().toString());
		jsonObj.put("comments", model.getComments().toString());
		jsonObj.put("loginDate", model.getLoginDate().toString());
		jsonObj.put("loginIP", model.getLoginIP().toString());
		jsonObj.put("lastLoginDate", model.getLastLoginDate().toString());
		jsonObj.put("lastLoginIP", model.getLastLoginIP().toString());
		jsonObj.put("failedLoginAttempts", model.getFailedLoginAttempts());
		jsonObj.put("agreedToTermsOfUse", model.getAgreedToTermsOfUse());
		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			User model = (User)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}