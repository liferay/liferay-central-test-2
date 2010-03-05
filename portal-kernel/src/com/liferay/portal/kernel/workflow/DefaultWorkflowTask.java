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

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * <a href="DefaultWorkflowTask.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DefaultWorkflowTask implements Serializable, WorkflowTask {

	public String getAssigneeEmailAddress() {
		return _assigneeEmailAddress;
	}

	public long getAssigneeRoleId() {
		return _assigneeRoleId;
	}

	public long getAssigneeUserId() {
		return _assigneeUserId;
	}

	public Date getCompletionDate() {
		return _completionDate;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public Date getDueDate() {
		return _dueDate;
	}

	public String getName() {
		return _name;
	}

	public Map<String, Object> getOptionalAttributes() {
		return _optionalAttributes;
	}

	public long getWorkflowDefinitionId() {
		return _workflowDefinitionId;
	}

	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	public long getWorkflowInstanceId() {
		return _workflowInstanceId;
	}

	public long getWorkflowTaskId() {
		return _workflowTaskId;
	}

	public boolean isAsynchronous() {
		return _asynchronous;
	}

	public boolean isCompleted() {
		if (_completionDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setAssigneeEmailAddress(String assigneeEmailAddress) {
		_assigneeEmailAddress = assigneeEmailAddress;
	}

	public void setAssigneeRoleId(long assigneeRoleId) {
		_assigneeRoleId = assigneeRoleId;
	}

	public void setAssigneeUserId(long assigneeUserId) {
		_assigneeUserId = assigneeUserId;
	}

	public void setAsynchronous(boolean asynchronous) {
		_asynchronous = asynchronous;
	}

	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDueDate(Date dueDate) {
		_dueDate = dueDate;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOptionalAttributes(Map<String, Object> optionalAttributes) {
		_optionalAttributes = optionalAttributes;
	}

	public void setWorkflowDefinitionId(long workflowDefinitionId) {
		_workflowDefinitionId = workflowDefinitionId;
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceId = workflowInstanceId;
	}

	public void setWorkflowTaskId(long workflowTaskId) {
		_workflowTaskId = workflowTaskId;
	}

	private String _assigneeEmailAddress;
	private long _assigneeRoleId;
	private long _assigneeUserId;
	private boolean _asynchronous;
	private Date _completionDate;
	private Date _createDate;
	private String _description;
	private Date _dueDate;
	private String _name;
	private Map<String, Object> _optionalAttributes;
	private long _workflowDefinitionId;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
	private long _workflowInstanceId;
	private long _workflowTaskId;

}