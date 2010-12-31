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
import com.liferay.portal.model.PasswordPolicy;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyJSONSerializer {
	public static JSONObject toJSONObject(PasswordPolicy model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("passwordPolicyId", model.getPasswordPolicyId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());

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
		jsonObject.put("defaultPolicy", model.getDefaultPolicy());
		jsonObject.put("name", model.getName());
		jsonObject.put("description", model.getDescription());
		jsonObject.put("changeable", model.getChangeable());
		jsonObject.put("changeRequired", model.getChangeRequired());
		jsonObject.put("minAge", model.getMinAge());
		jsonObject.put("checkSyntax", model.getCheckSyntax());
		jsonObject.put("allowDictionaryWords", model.getAllowDictionaryWords());
		jsonObject.put("minAlphanumeric", model.getMinAlphanumeric());
		jsonObject.put("minLength", model.getMinLength());
		jsonObject.put("minLowerCase", model.getMinLowerCase());
		jsonObject.put("minNumbers", model.getMinNumbers());
		jsonObject.put("minSymbols", model.getMinSymbols());
		jsonObject.put("minUpperCase", model.getMinUpperCase());
		jsonObject.put("history", model.getHistory());
		jsonObject.put("historyCount", model.getHistoryCount());
		jsonObject.put("expireable", model.getExpireable());
		jsonObject.put("maxAge", model.getMaxAge());
		jsonObject.put("warningTime", model.getWarningTime());
		jsonObject.put("graceLimit", model.getGraceLimit());
		jsonObject.put("lockout", model.getLockout());
		jsonObject.put("maxFailure", model.getMaxFailure());
		jsonObject.put("lockoutDuration", model.getLockoutDuration());
		jsonObject.put("requireUnlock", model.getRequireUnlock());
		jsonObject.put("resetFailureCount", model.getResetFailureCount());
		jsonObject.put("resetTicketMaxAge", model.getResetTicketMaxAge());

		return jsonObject;
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