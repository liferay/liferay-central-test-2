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
import com.liferay.portal.model.OrgLabor;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
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