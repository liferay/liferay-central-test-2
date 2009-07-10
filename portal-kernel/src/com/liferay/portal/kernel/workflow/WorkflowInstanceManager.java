/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 *
 */
public interface WorkflowInstanceManager {

	public WorkflowInstance getWorkflowInstance(long workflowInstanceId)
		throws WorkflowException;

	public WorkflowInstance getWorkflowInstance(
			String relationType, long relationId)
		throws WorkflowException;

	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			String workflowDefinitionId, int workflowDefinitionVersion)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			String workflowDefinitionId, int workflowDefinitionVersion,
			boolean finished)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			String relationType, long relationId)
		throws WorkflowException;

	public void removeWorkflowInstance(long workflowInstanceId);

	public WorkflowInstance signalWorkflowInstance(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException;

	public WorkflowInstance signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			Map<String, Object> context)
		throws WorkflowException;

	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionId, int workflowDefinitionVersion,
			Map<String, Object> context, long userId)
		throws WorkflowException;

	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionId, int workflowDefinitionVersion,
			Map<String, Object> context, long userId, String activityName)
		throws WorkflowException;

	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionId, int workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long userId)
		throws WorkflowException;

	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionId, int workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long userId, String activityName)
		throws WorkflowException;

}