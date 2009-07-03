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
import java.util.Set;

/**
 * <a href="TaskInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public interface TaskInstanceManager {

	public TaskInstanceInfo assignTaskInstance(
			long taskInstanceId, long userId, String description,
			Map<String, Object> contextInfo)
		throws WorkflowException;

	public TaskInstanceInfo fulfillTaskInstance(
			long taskInstanceId, long userId, String description,
			Map<String, Object> contextInfo)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByProcessInstanceId(
			long processInstanceId)	throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByProcessInstanceId(
			long processInstanceId, boolean finished) throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, boolean finished)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByRoles(Set<Long> roleIds)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByRoles(
			Set<Long> roleIds, boolean finished) throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByTokenId(long tokenId)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByTokenId(
			long tokenId, boolean finished)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByUserId(long userId)
		throws WorkflowException;

	public List<TaskInstanceInfo> getTaskInstanceInfosByUserId(
			long userId, boolean finished)
		throws WorkflowException;

}