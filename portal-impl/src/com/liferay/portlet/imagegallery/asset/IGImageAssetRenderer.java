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

package com.liferay.portlet.imagegallery.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.permission.IGPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * <a href="IGImageAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class IGImageAssetRenderer extends BaseAssetRenderer {

	public IGImageAssetRenderer(IGImage image) {
		_image = image;
	}

	public long getClassPK() {
		return _image.getImageId();
	}

	public long getGroupId() {
		return _image.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_image.getDescription());
	}

	public String getTitle() {
		return _image.getName();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = null;

		if (IGPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_IMAGE)) {

			editPortletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.IMAGE_GALLERY);

			try {
				editPortletURL.setWindowState(WindowState.MAXIMIZED);
			}
			catch(Exception e){
			}

			editPortletURL.setParameter(
				"struts_action", "/image_gallery/edit_image");
			editPortletURL.setParameter(
				"folderId", String.valueOf(_image.getFolderId()));
 			editPortletURL.setParameter(
				 "imageId", String.valueOf(_image.getImageId()));
		}

		return editPortletURL;
	}

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		PortletURL viewPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.IMAGE_GALLERY);

		viewPortletURL.setWindowState(WindowState.MAXIMIZED);

		viewPortletURL.setParameter("struts_action", "/image_gallery/view");
		viewPortletURL.setParameter(
			"groupId", String.valueOf(_image.getGroupId()));
		viewPortletURL.setParameter(
			"folderId", String.valueOf(_image.getFolderId()));

		return viewPortletURL.toString();
	}

	public long getUserId() {
		return _image.getUserId();
	}

	public String getViewInContextMessage() {
		return "view-album";
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.IMAGE_GALLERY_IMAGE, _image);

			return "/html/portlet/image_gallery/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private IGImage _image;

}