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
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("layoutRevisionId", model.getLayoutRevisionId());
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
		jsonObj.put("layoutSetBranchId", model.getLayoutSetBranchId());
		jsonObj.put("parentLayoutRevisionId", model.getParentLayoutRevisionId());
		jsonObj.put("head", model.getHead());
		jsonObj.put("plid", model.getPlid());
		jsonObj.put("name", model.getName());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("typeSettings", model.getTypeSettings());
		jsonObj.put("iconImage", model.getIconImage());
		jsonObj.put("iconImageId", model.getIconImageId());
		jsonObj.put("themeId", model.getThemeId());
		jsonObj.put("colorSchemeId", model.getColorSchemeId());
		jsonObj.put("wapThemeId", model.getWapThemeId());
		jsonObj.put("wapColorSchemeId", model.getWapColorSchemeId());
		jsonObj.put("css", model.getCss());
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