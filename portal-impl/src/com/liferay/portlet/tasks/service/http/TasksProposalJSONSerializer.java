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

package com.liferay.portlet.tasks.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.tasks.model.TasksProposal;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class TasksProposalJSONSerializer {
	public static JSONObject toJSONObject(TasksProposal model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("proposalId", model.getProposalId());
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
		jsonObj.put("name", model.getName());
		jsonObj.put("description", model.getDescription());

		Date publishDate = model.getPublishDate();

		String publishDateJSON = StringPool.BLANK;

		if (publishDate != null) {
			publishDateJSON = String.valueOf(publishDate.getTime());
		}

		jsonObj.put("publishDate", publishDateJSON);

		Date dueDate = model.getDueDate();

		String dueDateJSON = StringPool.BLANK;

		if (dueDate != null) {
			dueDateJSON = String.valueOf(dueDate.getTime());
		}

		jsonObj.put("dueDate", dueDateJSON);

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.tasks.model.TasksProposal[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TasksProposal model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.tasks.model.TasksProposal[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TasksProposal[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.tasks.model.TasksProposal> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (TasksProposal model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}