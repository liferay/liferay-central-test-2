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

package com.liferay.portlet.imagegallery.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getFolder(HttpServletRequest request) throws Exception {
		long folderId = ParamUtil.getLong(request, "folderId");

		IGFolder folder = null;

		if ((folderId > 0) &&
			(folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = IGFolderServiceUtil.getFolder(folderId);
		}

		request.setAttribute(WebKeys.IMAGE_GALLERY_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

	public static void getImage(HttpServletRequest request) throws Exception {
		long imageId = ParamUtil.getLong(request, "imageId");

		IGImage image = null;

		if (imageId > 0) {
			image = IGImageServiceUtil.getImage(imageId);
		}

		request.setAttribute(WebKeys.IMAGE_GALLERY_IMAGE, image);
	}

	public static void getImage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getImage(request);
	}

}