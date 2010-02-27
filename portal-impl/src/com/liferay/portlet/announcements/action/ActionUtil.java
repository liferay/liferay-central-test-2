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

package com.liferay.portlet.announcements.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ActionUtil {

	public static void getEntry(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getEntry(request);
	}

	public static void getEntry(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getEntry(request);
	}

	public static void getEntry(HttpServletRequest request) throws Exception {
		long entryId = ParamUtil.getLong(request, "entryId");

		AnnouncementsEntry entry = null;

		if (entryId > 0) {
			entry = AnnouncementsEntryLocalServiceUtil.getEntry(entryId);
		}

		request.setAttribute(WebKeys.ANNOUNCEMENTS_ENTRY, entry);
	}

}