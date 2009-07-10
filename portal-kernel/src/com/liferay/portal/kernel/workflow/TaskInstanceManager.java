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
 * <a href="TaskInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 *
 */
public interface TaskInstanceManager {

	public TaskInstance assignTaskInstanceToGroup(
			long taskInstanceId, long groupId, String comment)
		throws WorkflowException;

	public TaskInstance assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment)
		throws WorkflowException;

	public TaskInstance assignTaskInstanceToUser(
			long taskInstanceId, long userId, String comment)
		throws WorkflowException;

	public TaskInstance completeTaskInstance(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByGroup(long groupId)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByGroup(
			long groupId, boolean completed)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByRole(long roleId)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByRole(
			long roleId, boolean completed)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByUser(long userId)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByUser(
			long userId, boolean completed)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByWorkflowInstanceId(
			long workflowInstanceId)
		throws WorkflowException;

	public List<TaskInstance> getTaskInstancesByWorkflowInstanceId(
			long workflowInstanceId, boolean completed)
		throws WorkflowException;

}