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

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;

import java.util.List;
import java.util.Map;

/**
 * <a href="TaskInstanceManagerProxyBean.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Micha Kiener
 */
public class TaskInstanceManagerProxyBean
	extends BaseProxyBean implements TaskInstanceManager {

	public TaskInstanceInfo assignTaskInstanceToRole(
		long taskInstanceId, long roleId, String comment,
		Map<String, Object> attributes, long callingUserId) {

		throw new UnsupportedOperationException();
	}

	public TaskInstanceInfo assignTaskInstanceToUser(
		long taskInstanceId, UserCredential userCredential, String comment,
		Map<String, Object> attributes, long callingUserId) {

		throw new UnsupportedOperationException();
	}

	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String comment,
		Map<String, Object> attributes) {

		throw new UnsupportedOperationException();
	}

	public TaskInstanceInfo completeTaskInstance(
		long taskInstanceId, long userId, String activityName, String comment,
		Map<String, Object> attributes) {

		throw new UnsupportedOperationException();
	}

	public List<String> getPossibleNextActivityNames(
		long taskInstanceId, long userId) {

		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByCredential(
		UserCredential userCredential) {

		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByCredential(
		UserCredential userCredential, boolean completed) {

		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByRole(long roleId) {
		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByRole(long roleId, boolean completed) {
		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByUser(long userId) {
		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByUser(long userId, boolean completed) {
		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByWorkflowInstance(
		long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	public int getTaskInstanceInfoCountByWorkflowInstance(
		long workflowInstanceId, boolean completed) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
		UserCredential userCredential, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
		long roleId, int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
		long userId, int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
		long workflowInstanceId, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

}