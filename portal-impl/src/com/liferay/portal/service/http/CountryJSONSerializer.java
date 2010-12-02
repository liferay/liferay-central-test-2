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
import com.liferay.portal.model.Country;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class CountryJSONSerializer {
	public static JSONObject toJSONObject(Country model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("countryId", model.getCountryId());
		jsonObject.put("name", model.getName());
		jsonObject.put("a2", model.getA2());
		jsonObject.put("a3", model.getA3());
		jsonObject.put("number", model.getNumber());
		jsonObject.put("idd", model.getIdd());
		jsonObject.put("active", model.getActive());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Country[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Country model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Country[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Country[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Country> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Country model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}