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

package com.liferay.portlet.dynamicdatamapping.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;

import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DDMStructureLinkJSONSerializer {
	public static JSONObject toJSONObject(DDMStructureLink model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("structureLinkId", model.getStructureLinkId());
		jsonObject.put("structureKey", model.getStructureKey());
		jsonObject.put("className", model.getClassName());
		jsonObject.put("classPK", model.getClassPK());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMStructureLink model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMStructureLink[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMStructureLink model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}