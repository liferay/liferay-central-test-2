/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="UserJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.UserServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.UserServiceJSON
 *
 */
public class UserJSONSerializer {
	public static JSONObject toJSONObject(User model) {
		JSONObject jsonObj = new JSONObject();
		String userId = model.getUserId();

		if (userId == null) {
			jsonObj.put("userId", StringPool.BLANK);
		}
		else {
			jsonObj.put("userId", userId.toString());
		}

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		String contactId = model.getContactId();

		if (contactId == null) {
			jsonObj.put("contactId", StringPool.BLANK);
		}
		else {
			jsonObj.put("contactId", contactId.toString());
		}

		String password = model.getPassword();

		if (password == null) {
			jsonObj.put("password", StringPool.BLANK);
		}
		else {
			jsonObj.put("password", password.toString());
		}

		jsonObj.put("passwordEncrypted", model.getPasswordEncrypted());

		Date passwordExpirationDate = model.getPasswordExpirationDate();

		if (passwordExpirationDate == null) {
			jsonObj.put("passwordExpirationDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("passwordExpirationDate",
				passwordExpirationDate.toString());
		}

		jsonObj.put("passwordReset", model.getPasswordReset());

		String screenName = model.getScreenName();

		if (screenName == null) {
			jsonObj.put("screenName", StringPool.BLANK);
		}
		else {
			jsonObj.put("screenName", screenName.toString());
		}

		String emailAddress = model.getEmailAddress();

		if (emailAddress == null) {
			jsonObj.put("emailAddress", StringPool.BLANK);
		}
		else {
			jsonObj.put("emailAddress", emailAddress.toString());
		}

		String languageId = model.getLanguageId();

		if (languageId == null) {
			jsonObj.put("languageId", StringPool.BLANK);
		}
		else {
			jsonObj.put("languageId", languageId.toString());
		}

		String timeZoneId = model.getTimeZoneId();

		if (timeZoneId == null) {
			jsonObj.put("timeZoneId", StringPool.BLANK);
		}
		else {
			jsonObj.put("timeZoneId", timeZoneId.toString());
		}

		String greeting = model.getGreeting();

		if (greeting == null) {
			jsonObj.put("greeting", StringPool.BLANK);
		}
		else {
			jsonObj.put("greeting", greeting.toString());
		}

		String resolution = model.getResolution();

		if (resolution == null) {
			jsonObj.put("resolution", StringPool.BLANK);
		}
		else {
			jsonObj.put("resolution", resolution.toString());
		}

		String comments = model.getComments();

		if (comments == null) {
			jsonObj.put("comments", StringPool.BLANK);
		}
		else {
			jsonObj.put("comments", comments.toString());
		}

		Date loginDate = model.getLoginDate();

		if (loginDate == null) {
			jsonObj.put("loginDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("loginDate", loginDate.toString());
		}

		String loginIP = model.getLoginIP();

		if (loginIP == null) {
			jsonObj.put("loginIP", StringPool.BLANK);
		}
		else {
			jsonObj.put("loginIP", loginIP.toString());
		}

		Date lastLoginDate = model.getLastLoginDate();

		if (lastLoginDate == null) {
			jsonObj.put("lastLoginDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("lastLoginDate", lastLoginDate.toString());
		}

		String lastLoginIP = model.getLastLoginIP();

		if (lastLoginIP == null) {
			jsonObj.put("lastLoginIP", StringPool.BLANK);
		}
		else {
			jsonObj.put("lastLoginIP", lastLoginIP.toString());
		}

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