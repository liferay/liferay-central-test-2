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

import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ExpandoValueJSONSerializer {
	public static JSONObject toJSONObject(ExpandoValue model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("valueId", model.getValueId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("tableId", model.getTableId());
		jsonObject.put("columnId", model.getColumnId());
		jsonObject.put("rowId", model.getRowId());
		jsonObject.put("classNameId", model.getClassNameId());
		jsonObject.put("classPK", model.getClassPK());
		jsonObject.put("data", model.getData());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoValue[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoValue[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.expando.model.ExpandoValue> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}