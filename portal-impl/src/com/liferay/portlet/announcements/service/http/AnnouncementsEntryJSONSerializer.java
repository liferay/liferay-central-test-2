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

package com.liferay.portlet.announcements.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.announcements.model.AnnouncementsEntry;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsEntryJSONSerializer {
	public static JSONObject toJSONObject(AnnouncementsEntry model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("entryId", model.getEntryId());
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
		jsonObj.put("title", model.getTitle());
		jsonObj.put("content", model.getContent());
		jsonObj.put("url", model.getUrl());
		jsonObj.put("type", model.getType());

		Date displayDate = model.getDisplayDate();

		String displayDateJSON = StringPool.BLANK;

		if (displayDate != null) {
			displayDateJSON = String.valueOf(displayDate.getTime());
		}

		jsonObj.put("displayDate", displayDateJSON);

		Date expirationDate = model.getExpirationDate();

		String expirationDateJSON = StringPool.BLANK;

		if (expirationDate != null) {
			expirationDateJSON = String.valueOf(expirationDate.getTime());
		}

		jsonObj.put("expirationDate", expirationDateJSON);
		jsonObj.put("priority", model.getPriority());
		jsonObj.put("alert", model.getAlert());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.announcements.model.AnnouncementsEntry[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AnnouncementsEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.announcements.model.AnnouncementsEntry[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AnnouncementsEntry[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.announcements.model.AnnouncementsEntry> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AnnouncementsEntry model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}