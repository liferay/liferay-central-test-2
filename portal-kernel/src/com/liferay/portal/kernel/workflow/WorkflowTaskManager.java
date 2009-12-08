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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

@MessagingProxy(mode = ProxyMode.SYNC)
/**
 * <a href="WorkflowTaskManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public interface WorkflowTaskManager {

	public WorkflowTask assignWorkflowTaskToRole(
			long userId, long workflowTaskId, long roleId, String comment,
			Map<String, Object> context)
		throws WorkflowException;

	public WorkflowTask assignWorkflowTaskToUser(
			long userId, long workflowTaskId, long assigneeUserId,
			String comment, Map<String, Object> context)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long userId, long workflowTaskId, String transitionName,
			String comment, Map<String, Object> context)
		throws WorkflowException;

	public List<String> getNextTransitionNames(long userId, long workflowTaskId)
		throws WorkflowException;

	public long[] getPooledActorsIds(long workflowTaskId)
		throws WorkflowException;

	public WorkflowTask getWorkflowTask(long workflowTaskId)
		throws WorkflowException;

	public int getWorkflowTaskCount(Boolean completed) throws WorkflowException;

	public int getWorkflowTaskCountByRole(long roleId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUser(long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByUserRoles(long userId, Boolean completed)
		throws WorkflowException;

	public int getWorkflowTaskCountByWorkflowInstance(
			long workflowInstanceId, Boolean completed)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasks(
			Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByRole(
			long roleId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUser(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByUserRoles(
			long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	public List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long workflowInstanceId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

}