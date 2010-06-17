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

import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;

import java.io.Serializable;

/**
 * <a href="WorkflowTaskAssignee.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class WorkflowTaskAssignee implements Serializable {

	public static final WorkflowTaskAssignee ROLE(long roleId) {
		return new WorkflowTaskAssignee(Role.class.getName(), roleId);
	}

	public static final WorkflowTaskAssignee USER(long userId) {
		return new WorkflowTaskAssignee(User.class.getName(), userId);
	}

	public WorkflowTaskAssignee(
		String assigneeClassName, long assigneeClassPK) {

		_assigneeClassName = assigneeClassName;
		_assigneeClassPK = assigneeClassPK;
	}

	public String getAssigneeClassName() {
		return _assigneeClassName;
	}

	public long getAssigneeClassPK() {
		return _assigneeClassPK;
	}

	private String _assigneeClassName;
	private long _assigneeClassPK;

}