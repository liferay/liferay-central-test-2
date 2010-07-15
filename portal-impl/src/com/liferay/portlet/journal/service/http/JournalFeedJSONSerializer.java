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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.journal.model.JournalFeed;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class JournalFeedJSONSerializer {
	public static JSONObject toJSONObject(JournalFeed model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("id", model.getId());
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
		jsonObj.put("feedId", model.getFeedId());
		jsonObj.put("name", model.getName());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("type", model.getType());
		jsonObj.put("structureId", model.getStructureId());
		jsonObj.put("templateId", model.getTemplateId());
		jsonObj.put("rendererTemplateId", model.getRendererTemplateId());
		jsonObj.put("delta", model.getDelta());
		jsonObj.put("orderByCol", model.getOrderByCol());
		jsonObj.put("orderByType", model.getOrderByType());
		jsonObj.put("targetLayoutFriendlyUrl",
			model.getTargetLayoutFriendlyUrl());
		jsonObj.put("targetPortletId", model.getTargetPortletId());
		jsonObj.put("contentField", model.getContentField());
		jsonObj.put("feedType", model.getFeedType());
		jsonObj.put("feedVersion", model.getFeedVersion());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalFeed[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalFeed model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalFeed[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalFeed[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.journal.model.JournalFeed> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalFeed model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}