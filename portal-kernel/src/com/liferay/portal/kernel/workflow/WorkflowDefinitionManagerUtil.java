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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * <a href="WorkflowDefinitionManagerUtil.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionManagerUtil {

	public static WorkflowDefinition deployWorkflowDefinition(
			long userId, String name, InputStream inputStream)
		throws WorkflowException {

		return _workflowDefinitionManager.deployWorkflowDefinition(
			userId, name, inputStream);
	}

	public static WorkflowDefinition getWorkflowDefinition(
			String name, int version)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinition(
			name, version);
	}

	public static int getWorkflowDefinitionCount() throws WorkflowException {
		return _workflowDefinitionManager.getWorkflowDefinitionCount();
	}

	public static int getWorkflowDefinitionCount(String name)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitionCount(name);
	}

	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		return _workflowDefinitionManager;
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			int start, int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			start, end, orderByComparator);
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			String name, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			name, start, end, orderByComparator);
	}

	public static void undeployWorkflowDefinition(
			long userId, String name, int version)
		throws WorkflowException {

		_workflowDefinitionManager.undeployWorkflowDefinition(
			userId, name, version);
	}

	public void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	private static WorkflowDefinitionManager _workflowDefinitionManager;

}