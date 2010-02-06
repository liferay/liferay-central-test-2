/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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