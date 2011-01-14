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
import com.liferay.portal.model.Resource;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ResourceJSONSerializer {
	public static JSONObject toJSONObject(Resource model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("resourceId", model.getResourceId());
		jsonObject.put("codeId", model.getCodeId());
		jsonObject.put("primKey", model.getPrimKey());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Resource[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Resource model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Resource[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Resource[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Resource> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Resource model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}