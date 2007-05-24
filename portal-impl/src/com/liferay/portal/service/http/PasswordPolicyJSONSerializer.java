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
import com.liferay.portal.model.PasswordPolicy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="PasswordPolicyJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.PasswordPolicyServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.PasswordPolicyServiceJSON
 *
 */
public class PasswordPolicyJSONSerializer {
	public static JSONObject toJSONObject(PasswordPolicy model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("passwordPolicyId", model.getPasswordPolicyId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
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

		jsonObj.put("defaultPolicy", model.isDefaultPolicy());

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String description = model.getDescription();

		if (description == null) {
			jsonObj.put("description", StringPool.BLANK);
		}
		else {
			jsonObj.put("description", description.toString());
		}

		jsonObj.put("changeable", model.isChangeable());
		jsonObj.put("changeRequired", model.isChangeRequired());
		jsonObj.put("minAge", model.getMinAge());
		jsonObj.put("checkSyntax", model.isCheckSyntax());
		jsonObj.put("allowDictionaryWords", model.isAllowDictionaryWords());
		jsonObj.put("minLength", model.getMinLength());
		jsonObj.put("history", model.isHistory());
		jsonObj.put("historyCount", model.getHistoryCount());
		jsonObj.put("expireable", model.isExpireable());
		jsonObj.put("maxAge", model.getMaxAge());
		jsonObj.put("warningTime", model.getWarningTime());
		jsonObj.put("graceLimit", model.getGraceLimit());
		jsonObj.put("lockout", model.isLockout());
		jsonObj.put("maxFailure", model.getMaxFailure());
		jsonObj.put("lockoutDuration", model.getLockoutDuration());
		jsonObj.put("requireUnlock", model.isRequireUnlock());
		jsonObj.put("resetFailureCount", model.getResetFailureCount());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			PasswordPolicy model = (PasswordPolicy)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}