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

package com.liferay.portlet.dynamicdatamapping.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.model.DDMListItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMListItemLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMListLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class ActionUtil {

	public static void getList(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String listKey = ParamUtil.getString(request, "listKey");

		DDMList list = null;

		if (Validator.isNotNull(listKey)) {
			list = DDMListLocalServiceUtil.getList(groupId, listKey);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_MAPPING_LIST, list);
	}

	public static void getList(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getList(request);
	}

	public static void getListItem(HttpServletRequest request)
		throws Exception {

		long listItemId = ParamUtil.getLong(request, "listItemId");

		DDMListItem listItem = null;

		if (Validator.isNotNull(listItemId)) {
			listItem = DDMListItemLocalServiceUtil.getListItem(listItemId);
		}

		request.setAttribute(
			WebKeys.DYNAMIC_DATA_MAPPING_LIST_ITEM, listItem);
	}

	public static void getListItem(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getListItem(request);
	}

	public static void getStructure(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String structureKey = ParamUtil.getString(request, "structureKey");

		DDMStructure structure = null;

		if (Validator.isNotNull(structureKey)) {
			structure = DDMStructureServiceUtil.getStructure(
				groupId, structureKey);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE, structure);
	}

	public static void getStructure(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getStructure(request);
	}

}