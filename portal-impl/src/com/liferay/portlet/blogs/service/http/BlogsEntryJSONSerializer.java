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

package com.liferay.portlet.blogs.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryJSONSerializer {
	public static JSONObject toJSONObject(BlogsEntry model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("entryId", model.getEntryId());
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
		jsonObj.put("title", model.getTitle());
		jsonObj.put("urlTitle", model.getUrlTitle());
		jsonObj.put("content", model.getContent());

		Date displayDate = model.getDisplayDate();

		String displayDateJSON = StringPool.BLANK;

		if (displayDate != null) {
			displayDateJSON = String.valueOf(displayDate.getTime());
		}

		jsonObj.put("displayDate", displayDateJSON);
		jsonObj.put("allowPingbacks", model.getAllowPingbacks());
		jsonObj.put("allowTrackbacks", model.getAllowTrackbacks());
		jsonObj.put("trackbacks", model.getTrackbacks());
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
		com.liferay.portlet.blogs.model.BlogsEntry[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (BlogsEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.blogs.model.BlogsEntry[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (BlogsEntry[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.blogs.model.BlogsEntry> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (BlogsEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}