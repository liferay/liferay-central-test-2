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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.SourceFileNameException;
import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;

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
 * @author Alexander Chow
 */
public class EditFileEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.LOCK)) {
				lockFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.UNLOCK)) {
				unlockFileEntry(actionRequest);
			}
			if (!actionRequest.getWindowState().toString().equals("pop_up")) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateLockException ||
				e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				if (e instanceof DuplicateLockException) {
					DuplicateLockException dle = (DuplicateLockException)e;

					SessionErrors.add(
						actionRequest, dle.getClass().getName(), dle.getLock());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof NoSuchFolderException ||
					 e instanceof SourceFileNameException) {

				if (e instanceof DuplicateFileException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}
				else if (e instanceof FileNameException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_NAME_EXCEPTION);
				}
				else if (e instanceof FileSizeException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_SIZE_EXCEPTION);
				}

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
			ActionUtil.getFileEntry(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_file_entry";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	protected void deleteFileEntries(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			DLAppServiceUtil.deleteFileEntry(fileEntryId);
		}
		else {
			long[] deleteFileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);

			for (int i = 0; i < deleteFileEntryIds.length; i++) {
				DLAppServiceUtil.deleteFileEntry(deleteFileEntryIds[i]);
			}
		}
	}

	protected void lockFileEntry(ActionRequest actionRequest) throws Exception {
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		DLAppServiceUtil.lockFileEntry(fileEntryId);
	}

	protected void moveFileEntry(ActionRequest actionRequest) throws Exception {
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		DLAppServiceUtil.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	protected void revertFileEntry(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String version = ParamUtil.getString(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		DLAppServiceUtil.revertFileEntry(fileEntryId, version, serviceContext);
	}

	protected void unlockFileEntry(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		DLAppServiceUtil.unlockFileEntry(fileEntryId);
	}

	protected void updateFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryId = ParamUtil.getLong(uploadRequest, "fileEntryId");

		long folderId = ParamUtil.getLong(uploadRequest, "folderId");
		String sourceFileName = uploadRequest.getFileName("file");
		String title = ParamUtil.getString(uploadRequest, "title");
		String description = ParamUtil.getString(uploadRequest, "description");
		String changeLog = ParamUtil.getString(
			uploadRequest, "changeLog");
		boolean majorVersion = ParamUtil.getBoolean(
			uploadRequest, "majorVersion");

		File file = uploadRequest.getFile("file");

		if (Validator.isNotNull(sourceFileName) && !file.exists()) {
			file.createNewFile();
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			if (Validator.isNull(title)) {
				title = sourceFileName;
			}

			String extension = FileUtil.getExtension(sourceFileName);

			serviceContext.setAttribute("extension", extension);

			// Add file entry

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				themeDisplay.getScopeGroupId(), folderId, title, description,
				changeLog, file, serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), -1);
		}
		else {
			if (Validator.isNotNull(sourceFileName)) {
				String extension = FileUtil.getExtension(sourceFileName);

				serviceContext.setAttribute("extension", extension);
			}

			// Update file entry

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, sourceFileName, title, description, changeLog,
				majorVersion, file, serviceContext);
		}

		AssetPublisherUtil.addRecentFolderId(
			actionRequest, DLFileEntry.class.getName(), folderId);
	}

}