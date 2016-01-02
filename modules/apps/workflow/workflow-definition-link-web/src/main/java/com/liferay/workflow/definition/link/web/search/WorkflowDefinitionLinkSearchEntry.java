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

package com.liferay.workflow.definition.link.web.search;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkSearchEntry {

	public WorkflowDefinitionLinkSearchEntry(
		String className, String resource, String workflowDefinitionName) {

		_className = className;
		_resource = resource;
		_workflowDefinitionName = workflowDefinitionName;
	}

	public String getClassName() {
		return _className;
	}

	public String getResource() {
		return _resource;
	}

	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	private final String _className;
	private final String _resource;
	private final String _workflowDefinitionName;

}