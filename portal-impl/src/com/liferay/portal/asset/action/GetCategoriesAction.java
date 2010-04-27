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

package com.liferay.portal.asset.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetCategoriesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class GetCategoriesAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		long categoryId = ParamUtil.getLong(request, "categoryId");

		long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> categories = null;

		if (Validator.isNotNull(categoryId)) {
			categories = AssetCategoryLocalServiceUtil.getChildCategories(
				categoryId);
		}
		else if (Validator.isNotNull(vocabularyId)) {
			long parentCategoryId =
				ParamUtil.getLong(request, "parentCategoryId",
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

			categories = AssetCategoryLocalServiceUtil.getVocabularyCategories(
				parentCategoryId, vocabularyId);
		}

		if (categories == null) {
			return jsonArray.toString();
		}

		int start = ParamUtil.getInteger(request, "start");

		int limit = ParamUtil.getInteger(request, "limit");

		if (limit > 0) {
			categories = ListUtil.subList(categories, start, start + limit);
		}

		for (AssetCategory category : categories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			boolean hasChildren = false;

			List<AssetCategory> children =
				AssetCategoryLocalServiceUtil.getChildCategories(
					category.getCategoryId());

			if (children.size() > 0) {
				hasChildren = true;
			}

			jsonObject.put("categoryId", category.getCategoryId());
			jsonObject.put("hasChildren", hasChildren);
			jsonObject.put("name", category.getName());
			jsonObject.put("parentCategoryId", category.getParentCategoryId());
			jsonObject.put("title", category.getTitle());

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

}