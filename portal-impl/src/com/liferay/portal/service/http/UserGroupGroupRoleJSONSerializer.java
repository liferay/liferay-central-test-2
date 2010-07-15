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
import com.liferay.portal.model.UserGroupGroupRole;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class UserGroupGroupRoleJSONSerializer {
	public static JSONObject toJSONObject(UserGroupGroupRole model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("userGroupId", model.getUserGroupId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("roleId", model.getRoleId());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.UserGroupGroupRole[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (UserGroupGroupRole model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.UserGroupGroupRole[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (UserGroupGroupRole[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.UserGroupGroupRole> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (UserGroupGroupRole model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}