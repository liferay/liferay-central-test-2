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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryJSONSerializer {
	public static JSONObject toJSONObject(DLFileEntry model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uuid", model.getUuid());
		jsonObject.put("fileEntryId", model.getFileEntryId());
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());
		jsonObject.put("versionUserId", model.getVersionUserId());
		jsonObject.put("versionUserName", model.getVersionUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObject.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObject.put("modifiedDate", modifiedDateJSON);
		jsonObject.put("repositoryId", model.getRepositoryId());
		jsonObject.put("folderId", model.getFolderId());
		jsonObject.put("name", model.getName());
		jsonObject.put("extension", model.getExtension());
		jsonObject.put("mimeType", model.getMimeType());
		jsonObject.put("title", model.getTitle());
		jsonObject.put("description", model.getDescription());
		jsonObject.put("extraSettings", model.getExtraSettings());
		jsonObject.put("version", model.getVersion());
		jsonObject.put("size", model.getSize());
		jsonObject.put("readCount", model.getReadCount());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.documentlibrary.model.DLFileEntry[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.documentlibrary.model.DLFileEntry[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileEntry[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.documentlibrary.model.DLFileEntry> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DLFileEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}