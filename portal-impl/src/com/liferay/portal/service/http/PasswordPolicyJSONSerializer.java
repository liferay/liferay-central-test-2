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
import com.liferay.portal.model.PasswordPolicy;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyJSONSerializer {
	public static JSONObject toJSONObject(PasswordPolicy model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("passwordPolicyId", model.getPasswordPolicyId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());

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
		jsonObj.put("defaultPolicy", model.getDefaultPolicy());
		jsonObj.put("name", model.getName());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("changeable", model.getChangeable());
		jsonObj.put("changeRequired", model.getChangeRequired());
		jsonObj.put("minAge", model.getMinAge());
		jsonObj.put("checkSyntax", model.getCheckSyntax());
		jsonObj.put("allowDictionaryWords", model.getAllowDictionaryWords());
		jsonObj.put("minAlphanumeric", model.getMinAlphanumeric());
		jsonObj.put("minLength", model.getMinLength());
		jsonObj.put("minLowerCase", model.getMinLowerCase());
		jsonObj.put("minNumbers", model.getMinNumbers());
		jsonObj.put("minSymbols", model.getMinSymbols());
		jsonObj.put("minUpperCase", model.getMinUpperCase());
		jsonObj.put("history", model.getHistory());
		jsonObj.put("historyCount", model.getHistoryCount());
		jsonObj.put("expireable", model.getExpireable());
		jsonObj.put("maxAge", model.getMaxAge());
		jsonObj.put("warningTime", model.getWarningTime());
		jsonObj.put("graceLimit", model.getGraceLimit());
		jsonObj.put("lockout", model.getLockout());
		jsonObj.put("maxFailure", model.getMaxFailure());
		jsonObj.put("lockoutDuration", model.getLockoutDuration());
		jsonObj.put("requireUnlock", model.getRequireUnlock());
		jsonObj.put("resetFailureCount", model.getResetFailureCount());
		jsonObj.put("resetTicketMaxAge", model.getResetTicketMaxAge());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.PasswordPolicy[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PasswordPolicy model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.PasswordPolicy[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PasswordPolicy[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.PasswordPolicy> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PasswordPolicy model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}