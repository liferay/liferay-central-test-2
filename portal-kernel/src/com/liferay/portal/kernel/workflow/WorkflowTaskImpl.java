/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <a href="WorkflowTaskImpl.java.html}"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class WorkflowTaskImpl implements WorkflowTask, Serializable {
	
	public WorkflowTaskImpl(
		long workflowTaskId, long workflowInstanceId, long assigneeRoleId,
		long assigneeUserId, boolean asynchronous, Date completionDate,
		Date createDate, String description, Date dueDate, String name,
		Map<String, Object> optionalAttributes,
		long workflowDefinitionId, String workflowDefinitionName,
		int workflowDefinitionVersion) {
		
		_assigneeRoleId = assigneeRoleId;
		_assigneeUserId = assigneeUserId;
		_asynchronous = asynchronous;
		_completionDate = completionDate;
		_createDate = createDate;
		_description = description;
		_dueDate = dueDate;
		_name = name;
		_optionalAttributes = optionalAttributes;
		_workflowDefinitionId = workflowDefinitionId;
		_workflowDefinitionName = workflowDefinitionName;
		_workflowDefinitionVersion = workflowDefinitionVersion;
		_workflowInstanceId = workflowInstanceId;
		_workflowTaskId = workflowTaskId;
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
