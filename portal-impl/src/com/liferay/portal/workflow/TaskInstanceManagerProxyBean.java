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

package com.liferay.portal.workflow;

import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowRequest;

/**
 * <a href="TaskInstanceManagerProxyBean.java.html"><b><i>View Source</i></b>
 * </a>
 * 
 * <p>
 * The default implementation for the {@link TaskInstanceManager} to be
 * instrumented by Spring where every method of the interface is going to be
 * injected using a generic advice ({@link WorkflowProxyAdvice}) to wrap the
 * method and its arguments into a {@link WorkflowRequest}. So actually this
 * implementation does nothing but throwing
 * {@link UnsupportedOperationException} to make sure the advice has been
 * injected by Spring.
 * </p>
 * 
 * @author Micha Kiener
 */
public class TaskInstanceManagerProxyBean
	extends BaseProxyBean implements TaskInstanceManager {

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#assignTaskInstanceToRole(long,
	 *      long, java.lang.String, java.util.Map, long, java.util.Map)
	 */
	public TaskInstanceInfo assignTaskInstanceToRole(
		long taskInstanceId, long roleId, String comment,
		Map<String, Object> attributes, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#assignTaskInstanceToUser(long,
	 *      com.liferay.portal.kernel.workflow.UserCredential, java.lang.String,
	 *      java.util.Map, long, java.util.Map)
	 */
	public TaskInstanceInfo assignTaskInstanceToUser(
		long taskInstanceId, UserCredential userCredential, String comment,
		Map<String, Object> attributes, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#completeTaskInstance(long,
	 *      long, java.lang.String, java.util.Map, java.util.Map)
	 */
	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String comment,
		Map<String, Object> attributes, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#completeTaskInstance(long,
	 *      long, java.lang.String, java.lang.String, java.util.Map,
	 *      java.util.Map)
	 */
	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String activityName, String comment,
		Map<String, Object> attributes, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getPossibleNextActivityNames(long,
	 *      long, java.util.Map)
	 */
	public List<String> getPossibleNextActivityNames(
		long taskInstanceId, long userId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByCredential(com.liferay.portal.kernel.workflow.UserCredential)
	 */
	public int getTaskInstanceInfoCountByCredential(
		UserCredential userCredential) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByCredential(com.liferay.portal.kernel.workflow.UserCredential,
	 *      boolean)
	 */
	public int getTaskInstanceInfoCountByCredential(
		UserCredential userCredential, boolean completed) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByRole(long)
	 */
	public int getTaskInstanceInfoCountByRole(long roleId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByRole(long,
	 *      boolean)
	 */
	public int getTaskInstanceInfoCountByRole(long roleId, boolean completed) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByUser(long)
	 */
	public int getTaskInstanceInfoCountByUser(long userId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByUser(long,
	 *      boolean)
	 */
	public int getTaskInstanceInfoCountByUser(long userId, boolean completed) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByWorkflowInstance(long)
	 */
	public int getTaskInstanceInfoCountByWorkflowInstance(
		long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfoCountByWorkflowInstance(long,
	 *      boolean)
	 */
	public int getTaskInstanceInfoCountByWorkflowInstance(
		long workflowInstanceId, boolean completed) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByCredential(com.liferay.portal.kernel.workflow.UserCredential,
	 *      boolean, int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByCredential(com.liferay.portal.kernel.workflow.UserCredential,
	 *      int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByRole(long,
	 *      boolean, int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByRole(long,
	 *      int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByUser(long,
	 *      boolean, int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByUser(long,
	 *      int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *      boolean, int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.TaskInstanceManager#getTaskInstanceInfosByWorkflowInstance(long,
	 *      int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

}