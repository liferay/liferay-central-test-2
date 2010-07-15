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

import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class JournalTemplateJSONSerializer {
	public static JSONObject toJSONObject(JournalTemplate model) {
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
		jsonObj.put("templateId", model.getTemplateId());
		jsonObj.put("structureId", model.getStructureId());
		jsonObj.put("name", model.getName());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("xsl", model.getXsl());
		jsonObj.put("langType", model.getLangType());
		jsonObj.put("cacheable", model.getCacheable());
		jsonObj.put("smallImage", model.getSmallImage());
		jsonObj.put("smallImageId", model.getSmallImageId());
		jsonObj.put("smallImageURL", model.getSmallImageURL());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalTemplate[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalTemplate model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalTemplate[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalTemplate[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.journal.model.JournalTemplate> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalTemplate model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}