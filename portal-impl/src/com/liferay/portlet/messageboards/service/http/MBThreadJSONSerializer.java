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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.messageboards.model.MBThread;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class MBThreadJSONSerializer {
	public static JSONObject toJSONObject(MBThread model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("threadId", model.getThreadId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("categoryId", model.getCategoryId());
		jsonObj.put("rootMessageId", model.getRootMessageId());
		jsonObj.put("messageCount", model.getMessageCount());
		jsonObj.put("viewCount", model.getViewCount());
		jsonObj.put("lastPostByUserId", model.getLastPostByUserId());

		Date lastPostDate = model.getLastPostDate();

		String lastPostDateJSON = StringPool.BLANK;

		if (lastPostDate != null) {
			lastPostDateJSON = String.valueOf(lastPostDate.getTime());
		}

		jsonObj.put("lastPostDate", lastPostDateJSON);
		jsonObj.put("priority", model.getPriority());
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
		com.liferay.portlet.messageboards.model.MBThread[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBThread model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.messageboards.model.MBThread[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBThread[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.messageboards.model.MBThread> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBThread model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}