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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class ActionUtil {

	public static void getWorkflowDefinition(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getWorkflowDefinition(request);
	}

	public static void getWorkflowDefinition(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getWorkflowDefinition(request);
	}

	public static void getWorkflowDefinition(HttpServletRequest request)
		throws Exception {

		if (request.getAttribute(WebKeys.WORKFLOW_DEFINITION) != null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(request, "name");
		int version = ParamUtil.getInteger(request, "version");

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				themeDisplay.getCompanyId(), name, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			if (version == workflowDefinition.getVersion()) {
				request.setAttribute(
					WebKeys.WORKFLOW_DEFINITION, workflowDefinition);

				break;
			}
		}
	}

}