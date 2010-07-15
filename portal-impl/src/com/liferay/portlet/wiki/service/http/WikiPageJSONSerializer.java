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

package com.liferay.portlet.wiki.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.wiki.model.WikiPage;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class WikiPageJSONSerializer {
	public static JSONObject toJSONObject(WikiPage model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("pageId", model.getPageId());
		jsonObj.put("resourcePrimKey", model.getResourcePrimKey());
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
		jsonObj.put("nodeId", model.getNodeId());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("version", model.getVersion());
		jsonObj.put("minorEdit", model.getMinorEdit());
		jsonObj.put("content", model.getContent());
		jsonObj.put("summary", model.getSummary());
		jsonObj.put("format", model.getFormat());
		jsonObj.put("head", model.getHead());
		jsonObj.put("parentTitle", model.getParentTitle());
		jsonObj.put("redirectTitle", model.getRedirectTitle());
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
		com.liferay.portlet.wiki.model.WikiPage[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (WikiPage model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.wiki.model.WikiPage[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (WikiPage[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.wiki.model.WikiPage> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (WikiPage model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}