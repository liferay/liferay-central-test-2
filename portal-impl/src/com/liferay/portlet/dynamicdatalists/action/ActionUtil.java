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

package com.liferay.portlet.dynamicdatalists.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;
import com.liferay.portlet.dynamicdatalists.service.DDLEntryItemLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLEntryLocalServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class ActionUtil {

	public static void getEntry(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String entryKey = ParamUtil.getString(request, "entryKey");

		DDLEntry entry = null;

		if (Validator.isNotNull(entryKey)) {
			entry = DDLEntryLocalServiceUtil.getEntry(groupId, entryKey);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_ENTRY, entry);
	}

	public static void getEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEntry(request);
	}

	public static void getEntryItem(HttpServletRequest request)
		throws Exception {

		long entryItemId = ParamUtil.getLong(request, "entryItemId");

		DDLEntryItem entryItem = null;

		if (entryItemId > 0) {
			entryItem = DDLEntryItemLocalServiceUtil.getEntryItem(entryItemId);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_ENTRY_ITEM, entryItem);
	}

	public static void getEntryItem(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEntryItem(request);
	}

}