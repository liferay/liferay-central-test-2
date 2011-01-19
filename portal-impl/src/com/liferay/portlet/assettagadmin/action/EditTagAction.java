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

package com.liferay.portlet.assettagadmin.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditTagAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				jsonObject = updateTag(actionRequest);
			}
			else if (cmd.equals(Constants.MERGE)) {
				jsonObject = mergeTag(actionRequest);
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

		ActionUtil.getTag(renderRequest);

		return mapping.findForward(
			getForward(renderRequest, "portlet.asset_tag_admin.edit_tag"));
	}

	protected String[] getTagProperties(ActionRequest actionRequest) {
		int[] tagPropertiesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "tagPropertiesIndexes"), 0);

		String[] tagProperties = new String[tagPropertiesIndexes.length];

		for (int i = 0; i < tagPropertiesIndexes.length; i++) {
			int tagPropertiesIndex = tagPropertiesIndexes[i];

			String key = ParamUtil.getString(
				actionRequest, "key" + tagPropertiesIndex);

			if (Validator.isNull(key)) {
				continue;
			}

			String value = ParamUtil.getString(
				actionRequest, "value" + tagPropertiesIndex);

			tagProperties[i] = key + StringPool.COLON + value;
		}

		return tagProperties;
	}

	protected JSONObject mergeTag(ActionRequest actionRequest)
		throws Exception {

		long fromTagId = ParamUtil.getLong(actionRequest, "fromTagId");
		long toTagId = ParamUtil.getLong(actionRequest, "toTagId");

		AssetTagServiceUtil.mergeTags(fromTagId, toTagId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("tagId", toTagId);

		return jsonObject;
	}

	protected JSONObject updateTag(ActionRequest actionRequest)
		throws Exception {

		long tagId = ParamUtil.getLong(actionRequest, "tagId");

		String name = ParamUtil.getString(actionRequest, "name");

		String[] tagProperties = getTagProperties(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetTag.class.getName(), actionRequest);

		AssetTag tag = null;

		if (tagId <= 0) {

			// Add tag

			tag = AssetTagServiceUtil.addTag(
				name, tagProperties, serviceContext);
		}
		else {

			// Update tag

			tag = AssetTagServiceUtil.updateTag(
				tagId, name, tagProperties, serviceContext);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("tagId", tag.getTagId());

		return jsonObject;
	}

}