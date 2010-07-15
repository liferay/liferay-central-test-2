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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DLFileVersionJSONSerializer {
	public static JSONObject toJSONObject(DLFileVersion model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("fileVersionId", model.getFileVersionId());
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
		jsonObj.put("folderId", model.getFolderId());
		jsonObj.put("name", model.getName());
		jsonObj.put("extension", model.getExtension());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("changeLog", model.getChangeLog());
		jsonObj.put("extraSettings", model.getExtraSettings());
		jsonObj.put("version", model.getVersion());
		jsonObj.put("size", model.getSize());
		jsonObj.put("status", model.getStatus());
		jsonObj.put("statusByUserId", model.getStatusByUserId());
		jsonObj.put("statusByUserName", model.getStatusByUserName());

		Date statusDate = model.getStatusDate();

		String statusDateJSON = StringPool.BLANK;

		if (statusDate != null) {
			statusDateJSON = String.valueOf(statusDate.getTime());
		}

		jsonObj.put("statusDate", statusDateJSON);

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.documentlibrary.model.DLFileVersion[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileVersion model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.documentlibrary.model.DLFileVersion[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileVersion[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.documentlibrary.model.DLFileVersion> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileVersion model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}