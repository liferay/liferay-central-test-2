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

import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class MBMessageJSONSerializer {
	public static JSONObject toJSONObject(MBMessage model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("messageId", model.getMessageId());
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
		jsonObj.put("categoryId", model.getCategoryId());
		jsonObj.put("threadId", model.getThreadId());
		jsonObj.put("rootMessageId", model.getRootMessageId());
		jsonObj.put("parentMessageId", model.getParentMessageId());
		jsonObj.put("subject", model.getSubject());
		jsonObj.put("body", model.getBody());
		jsonObj.put("attachments", model.getAttachments());
		jsonObj.put("anonymous", model.getAnonymous());
		jsonObj.put("priority", model.getPriority());
		jsonObj.put("allowPingbacks", model.getAllowPingbacks());
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
		com.liferay.portlet.messageboards.model.MBMessage[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBMessage model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.messageboards.model.MBMessage[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBMessage[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.messageboards.model.MBMessage> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBMessage model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}