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
import com.liferay.portal.model.OrgLabor;

import java.util.List;

/**
 * <a href="OrgLaborJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link OrgLaborServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.OrgLaborServiceJSON
 * @generated
 */
public class OrgLaborJSONSerializer {
	public static JSONObject toJSONObject(OrgLabor model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("orgLaborId", model.getOrgLaborId());
		jsonObj.put("organizationId", model.getOrganizationId());
		jsonObj.put("typeId", model.getTypeId());
		jsonObj.put("sunOpen", model.getSunOpen());
		jsonObj.put("sunClose", model.getSunClose());
		jsonObj.put("monOpen", model.getMonOpen());
		jsonObj.put("monClose", model.getMonClose());
		jsonObj.put("tueOpen", model.getTueOpen());
		jsonObj.put("tueClose", model.getTueClose());
		jsonObj.put("wedOpen", model.getWedOpen());
		jsonObj.put("wedClose", model.getWedClose());
		jsonObj.put("thuOpen", model.getThuOpen());
		jsonObj.put("thuClose", model.getThuClose());
		jsonObj.put("friOpen", model.getFriOpen());
		jsonObj.put("friClose", model.getFriClose());
		jsonObj.put("satOpen", model.getSatOpen());
		jsonObj.put("satClose", model.getSatClose());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.OrgLabor[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (OrgLabor model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.OrgLabor[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (OrgLabor[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.OrgLabor> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (OrgLabor model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}