/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.FolderNameException;
import com.liferay.portlet.journal.InvalidDDMStructureException;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class EditFolderAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFolder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFolders(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFolders(actionRequest, true);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeFolder(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeFolder(actionRequest);
			}
			else if (cmd.equals("updateWorkflowDefinitions")) {
				updateWorkflowDefinitions(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateFolderNameException ||
					 e instanceof FolderNameException ||
					 e instanceof InvalidDDMStructureException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFolder(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_folder"));
	}

	protected void deleteFolders(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteFolderIds = null;

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		if (folderId > 0) {
			deleteFolderIds = new long[] {folderId};
		}
		else {
			deleteFolderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "folderIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				JournalFolder folder =
					JournalFolderServiceUtil.moveFolderToTrash(deleteFolderId);

				trashedModels.add(folder);
			}
			else {
				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected void subscribeFolder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		JournalFolderServiceUtil.subscribe(
			themeDisplay.getScopeGroupId(), folderId);
	}

	protected void unsubscribeFolder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		JournalFolderServiceUtil.unsubscribe(
			themeDisplay.getScopeGroupId(), folderId);
	}

	protected void updateFolder(ActionRequest actionRequest) throws Exception {
		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		long parentFolderId = ParamUtil.getLong(
			actionRequest, "parentFolderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		boolean mergeWithParentFolder = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentFolder");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFolder.class.getName(), actionRequest);

		if (folderId <= 0) {

			// Add folder

			JournalFolderServiceUtil.addFolder(
				serviceContext.getScopeGroupId(), parentFolderId, name,
				description, serviceContext);
		}
		else {

			// Update folder

			long[] ddmStructureIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "ddmStructuresSearchContainerPrimaryKeys"),
				0L);
			int restrinctionType = ParamUtil.getInteger(
				actionRequest, "restrictionType");

			JournalFolderServiceUtil.updateFolder(
				folderId, parentFolderId, name, description, ddmStructureIds,
				restrinctionType, mergeWithParentFolder, serviceContext);
		}
	}

	protected void updateWorkflowDefinitions(ActionRequest actionRequest)
		throws Exception {

		long[] ddmStructureIds = StringUtil.split(
			ParamUtil.getString(
				actionRequest, "ddmStructuresSearchContainerPrimaryKeys"),
			0L);
		int restrinctionType = ParamUtil.getInteger(
			actionRequest, "restrictionType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFolder.class.getName(), actionRequest);

		JournalFolderServiceUtil.updateFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			ddmStructureIds, restrinctionType, false, serviceContext);
	}

}