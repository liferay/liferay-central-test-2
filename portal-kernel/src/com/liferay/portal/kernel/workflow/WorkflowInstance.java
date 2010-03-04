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
import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstance.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public interface WorkflowInstance {

	public void addChildWorkflowInstance(
		WorkflowInstance childWorkflowInstance);

	public int getChildrenWorkflowInstanceCount();

	public List<WorkflowInstance> getChildrenWorkflowInstances();

	public Map<String, Serializable> getContext();

	public Date getEndDate();

	public WorkflowInstance getParentWorkflowInstance();

	public long getParentWorkflowInstanceId();

	public Date getStartDate();

	public String getState();

	public String getWorkflowDefinitionName();

	public int getWorkflowDefinitionVersion();

	public long getWorkflowInstanceId();

	public void setParentWorkflowInstance(
		WorkflowInstance parentWorkflowInstance);

}