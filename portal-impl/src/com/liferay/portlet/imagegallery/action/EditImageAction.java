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

package com.liferay.portlet.imagegallery.action;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.ImageNameException;
import com.liferay.portlet.imagegallery.ImageSizeException;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditImageAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateImage(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteImage(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchImageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.image_gallery.error");
			}
			else if (e instanceof DuplicateImageNameException ||
					 e instanceof ImageNameException ||
					 e instanceof ImageSizeException ||
					 e instanceof NoSuchFolderException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetTagException) {
				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getImage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchImageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.image_gallery.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.image_gallery.edit_image";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	protected void deleteImage(ActionRequest actionRequest) throws Exception {
		long imageId = ParamUtil.getLong(actionRequest, "imageId");

		IGImageServiceUtil.deleteImage(imageId);
	}

	protected String getContentType(
		UploadPortletRequest uploadRequest, File file) {

		String contentType = GetterUtil.getString(
			uploadRequest.getContentType("file"));

		if (contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {
			String ext = GetterUtil.getString(
				FileUtil.getExtension(file.getName())).toLowerCase();

			if (Validator.isNotNull(ext)) {
				contentType = MimeTypesUtil.getContentType(ext);
			}
		}

		return contentType;
	}

	protected void updateImage(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long imageId = ParamUtil.getLong(uploadRequest, "imageId");

		long groupId = themeDisplay.getScopeGroupId();
		long folderId = ParamUtil.getLong(uploadRequest, "folderId");
		String name = ParamUtil.getString(uploadRequest, "name");
		String fileName = uploadRequest.getFileName("file");
		String description = ParamUtil.getString(
			uploadRequest, "description", fileName);

		File file = uploadRequest.getFile("file");
		String contentType = getContentType(uploadRequest, file);

		if (contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {
			String ext = GetterUtil.getString(
				FileUtil.getExtension(file.getName())).toLowerCase();

			if (Validator.isNotNull(ext)) {
				contentType = MimeTypesUtil.getContentType(ext);
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			IGImage.class.getName(), actionRequest);

		if (imageId <= 0) {

			// Add image

			if (Validator.isNull(name)) {
				name = fileName;
			}

			IGImage image = IGImageServiceUtil.addImage(
				groupId, folderId, name, description, file, contentType,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, IGImage.class.getName(), image.getImageId(), -1);
		}
		else {

			// Update image

			if (Validator.isNull(fileName)) {
				file = null;
			}

			IGImageServiceUtil.updateImage(
				imageId, groupId, folderId, name, description, file,
				contentType, serviceContext);
		}

		AssetPublisherUtil.addRecentFolderId(
			actionRequest, IGImage.class.getName(), folderId);
	}

}