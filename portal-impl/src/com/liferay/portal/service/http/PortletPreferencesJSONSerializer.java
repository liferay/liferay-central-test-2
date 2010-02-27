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
import com.liferay.portal.model.PortletPreferences;

import java.util.List;

/**
 * <a href="PortletPreferencesJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link PortletPreferencesServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.PortletPreferencesServiceJSON
 * @generated
 */
public class PortletPreferencesJSONSerializer {
	public static JSONObject toJSONObject(PortletPreferences model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("portletPreferencesId", model.getPortletPreferencesId());
		jsonObj.put("ownerId", model.getOwnerId());
		jsonObj.put("ownerType", model.getOwnerType());
		jsonObj.put("plid", model.getPlid());
		jsonObj.put("portletId", model.getPortletId());
		jsonObj.put("preferences", model.getPreferences());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.PortletPreferences[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PortletPreferences model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.PortletPreferences[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PortletPreferences[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.PortletPreferences> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PortletPreferences model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}