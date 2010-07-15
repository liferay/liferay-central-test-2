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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class SCFrameworkVersionJSONSerializer {
	public static JSONObject toJSONObject(SCFrameworkVersion model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("frameworkVersionId", model.getFrameworkVersionId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObj.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObj.put("modifiedDate", modifiedDateJSON);
		jsonObj.put("name", model.getName());
		jsonObj.put("url", model.getUrl());
		jsonObj.put("active", model.getActive());
		jsonObj.put("priority", model.getPriority());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCFrameworkVersion model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCFrameworkVersion[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCFrameworkVersion model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}