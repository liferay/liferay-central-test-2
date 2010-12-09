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
import com.liferay.portal.model.Company;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class CompanyJSONSerializer {
	public static JSONObject toJSONObject(Company model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("accountId", model.getAccountId());
		jsonObject.put("webId", model.getWebId());
		jsonObject.put("key", model.getKey());
		jsonObject.put("mx", model.getMx());
		jsonObject.put("homeURL", model.getHomeURL());
		jsonObject.put("logoId", model.getLogoId());
		jsonObject.put("system", model.getSystem());
		jsonObject.put("maxUsers", model.getMaxUsers());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Company[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Company model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Company[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Company[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Company> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Company model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}