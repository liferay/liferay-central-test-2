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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutRevision;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class LayoutRevisionJSONSerializer {
	public static JSONObject toJSONObject(LayoutRevision model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("layoutRevisionId", model.getLayoutRevisionId());
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());

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
		jsonObject.put("layoutSetBranchId", model.getLayoutSetBranchId());
		jsonObject.put("parentLayoutRevisionId",
			model.getParentLayoutRevisionId());
		jsonObject.put("head", model.getHead());
		jsonObject.put("major", model.getMajor());
		jsonObject.put("plid", model.getPlid());
		jsonObject.put("privateLayout", model.getPrivateLayout());
		jsonObject.put("name", model.getName());
		jsonObject.put("title", model.getTitle());
		jsonObject.put("description", model.getDescription());
		jsonObject.put("keywords", model.getKeywords());
		jsonObject.put("robots", model.getRobots());
		jsonObject.put("typeSettings", model.getTypeSettings());
		jsonObject.put("iconImage", model.getIconImage());
		jsonObject.put("iconImageId", model.getIconImageId());
		jsonObject.put("themeId", model.getThemeId());
		jsonObject.put("colorSchemeId", model.getColorSchemeId());
		jsonObject.put("wapThemeId", model.getWapThemeId());
		jsonObject.put("wapColorSchemeId", model.getWapColorSchemeId());
		jsonObject.put("css", model.getCss());
		jsonObject.put("status", model.getStatus());
		jsonObject.put("statusByUserId", model.getStatusByUserId());
		jsonObject.put("statusByUserName", model.getStatusByUserName());

		Date statusDate = model.getStatusDate();

		String statusDateJSON = StringPool.BLANK;

		if (statusDate != null) {
			statusDateJSON = String.valueOf(statusDate.getTime());
		}

		jsonObject.put("statusDate", statusDateJSON);

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.LayoutRevision[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (LayoutRevision model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.LayoutRevision[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (LayoutRevision[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.LayoutRevision> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (LayoutRevision model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}