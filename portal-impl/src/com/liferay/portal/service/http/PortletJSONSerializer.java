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
import com.liferay.portal.model.Portlet;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class PortletJSONSerializer {
	public static JSONObject toJSONObject(Portlet model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("id", model.getId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("portletId", model.getPortletId());
		jsonObj.put("roles", model.getRoles());
		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Portlet[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Portlet model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Portlet[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Portlet[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Portlet> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Portlet model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}