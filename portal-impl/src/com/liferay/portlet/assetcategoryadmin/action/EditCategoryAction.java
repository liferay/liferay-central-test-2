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

package com.liferay.portlet.assetcategoryadmin.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditCategoryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				jsonObject = updateCategory(actionRequest);
			}
		}
		catch (Exception e) {
			jsonObject.put("exception", e.getClass() + e.getMessage());
		}

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		ServletResponseUtil.write(response, jsonObject.toString());

		setForward(actionRequest, ActionConstants.COMMON_NULL);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ActionUtil.getCategory(renderRequest);

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.asset_category_admin.edit_category"));
	}

	protected JSONObject updateCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		AssetCategory category = null;

		if (categoryId <= 0) {

			// Add category

			category = AssetCategoryServiceUtil.addCategory(
				parentCategoryId, titleMap, descriptionMap, vocabularyId, null,
				serviceContext);
		}
		else {

			// Update category

			category = AssetCategoryServiceUtil.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, null, serviceContext );
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("categoryId", category.getCategoryId());

		return jsonObject;
	}

}