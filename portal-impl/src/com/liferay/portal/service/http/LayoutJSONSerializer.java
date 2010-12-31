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
import com.liferay.portal.model.Layout;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class LayoutJSONSerializer {
	public static JSONObject toJSONObject(Layout model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uuid", model.getUuid());
		jsonObject.put("plid", model.getPlid());
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("privateLayout", model.getPrivateLayout());
		jsonObject.put("layoutId", model.getLayoutId());
		jsonObject.put("parentLayoutId", model.getParentLayoutId());
		jsonObject.put("name", model.getName());
		jsonObject.put("title", model.getTitle());
		jsonObject.put("description", model.getDescription());
		jsonObject.put("type", model.getType());
		jsonObject.put("typeSettings", model.getTypeSettings());
		jsonObject.put("hidden", model.getHidden());
		jsonObject.put("friendlyURL", model.getFriendlyURL());
		jsonObject.put("iconImage", model.getIconImage());
		jsonObject.put("iconImageId", model.getIconImageId());
		jsonObject.put("themeId", model.getThemeId());
		jsonObject.put("colorSchemeId", model.getColorSchemeId());
		jsonObject.put("wapThemeId", model.getWapThemeId());
		jsonObject.put("wapColorSchemeId", model.getWapColorSchemeId());
		jsonObject.put("css", model.getCss());
		jsonObject.put("priority", model.getPriority());
		jsonObject.put("layoutPrototypeId", model.getLayoutPrototypeId());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Layout[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Layout model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Layout[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Layout[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Layout> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Layout model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}