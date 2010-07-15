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
import com.liferay.portal.model.Permission;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class PermissionJSONSerializer {
	public static JSONObject toJSONObject(Permission model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("permissionId", model.getPermissionId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("actionId", model.getActionId());
		jsonObj.put("resourceId", model.getResourceId());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Permission[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Permission model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Permission[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Permission[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Permission> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Permission model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}