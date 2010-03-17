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

package com.liferay.portlet.workflowadmin.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.ReferencedWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditWorkflowDefinitionAction.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 */
public class EditWorkflowDefinitionAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateWorkflowDefinition(actionRequest);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.RESTORE)) {

				deleteWorkflowDefinition(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof FileNotFoundException ||
				e instanceof WorkflowDefinitionFileException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof WorkflowException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.workflow_admin.error");
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
			ActionUtil.getWorkflowDefinition(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof WorkflowException) {
				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.workflow_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.workflow_admin.edit_workflow_definition"));
	}

	protected void deleteWorkflowDefinition(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		int version = ParamUtil.getInteger(actionRequest, "version");

		if (cmd.equals(Constants.DEACTIVATE) ||
			cmd.equals(Constants.RESTORE)) {

			boolean active = !cmd.equals(Constants.DEACTIVATE);

			if (!active) {
				int references = 
					WorkflowDefinitionLinkLocalServiceUtil.searchCount(
						name, version);

				if (references >= 1) {
					throw new ReferencedWorkflowDefinitionException();
				}
			}

			WorkflowDefinitionManagerUtil.updateActive(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, active);
		}
		else {
			WorkflowDefinitionManagerUtil.undeployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version);
		}
	}

	protected void updateWorkflowDefinition(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		File file = uploadRequest.getFile("file");

		if (!file.exists()) {
			throw new WorkflowDefinitionFileException();
		}

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				name, new FileInputStream(file));

		actionRequest.setAttribute(
			WebKeys.WORKFLOW_DEFINITION, workflowDefinition);
	}

}