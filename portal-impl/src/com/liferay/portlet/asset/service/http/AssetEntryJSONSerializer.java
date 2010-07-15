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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryJSONSerializer {
	public static JSONObject toJSONObject(AssetEntry model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

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
		jsonObj.put("classNameId", model.getClassNameId());
		jsonObj.put("classPK", model.getClassPK());
		jsonObj.put("classUuid", model.getClassUuid());
		jsonObj.put("visible", model.getVisible());

		Date startDate = model.getStartDate();

		String startDateJSON = StringPool.BLANK;

		if (startDate != null) {
			startDateJSON = String.valueOf(startDate.getTime());
		}

		jsonObj.put("startDate", startDateJSON);

		Date endDate = model.getEndDate();

		String endDateJSON = StringPool.BLANK;

		if (endDate != null) {
			endDateJSON = String.valueOf(endDate.getTime());
		}

		jsonObj.put("endDate", endDateJSON);

		Date publishDate = model.getPublishDate();

		String publishDateJSON = StringPool.BLANK;

		if (publishDate != null) {
			publishDateJSON = String.valueOf(publishDate.getTime());
		}

		jsonObj.put("publishDate", publishDateJSON);

		Date expirationDate = model.getExpirationDate();

		String expirationDateJSON = StringPool.BLANK;

		if (expirationDate != null) {
			expirationDateJSON = String.valueOf(expirationDate.getTime());
		}

		jsonObj.put("expirationDate", expirationDateJSON);
		jsonObj.put("mimeType", model.getMimeType());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("summary", model.getSummary());
		jsonObj.put("url", model.getUrl());
		jsonObj.put("height", model.getHeight());
		jsonObj.put("width", model.getWidth());
		jsonObj.put("priority", model.getPriority());
		jsonObj.put("viewCount", model.getViewCount());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.asset.model.AssetEntry[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.asset.model.AssetEntry[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetEntry[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.asset.model.AssetEntry> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}