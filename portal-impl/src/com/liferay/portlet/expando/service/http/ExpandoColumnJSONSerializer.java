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

package com.liferay.portlet.expando.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnJSONSerializer {
	public static JSONObject toJSONObject(ExpandoColumn model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("columnId", model.getColumnId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("tableId", model.getTableId());
		jsonObject.put("name", model.getName());
		jsonObject.put("type", model.getType());
		jsonObject.put("defaultData", model.getDefaultData());
		jsonObject.put("typeSettings", model.getTypeSettings());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoColumn[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoColumn model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoColumn[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoColumn[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.expando.model.ExpandoColumn> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoColumn model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}