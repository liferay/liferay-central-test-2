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
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstanceManagerProxyBean.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Micha Kiener
 */
public class WorkflowInstanceManagerProxyBean
	extends BaseProxyBean implements WorkflowInstanceManager {

	public WorkflowInstanceInfo addContextInformation(
		long workflowInstanceId, Map<String, Object> context) {

		throw new UnsupportedOperationException();
	}

	public List<String> getPossibleNextActivityNames(
		long workflowInstanceId, long userId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
		long workflowInstanceId, boolean includeChildren, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public int getWorkflowInstanceHistoryCount(
		long workflowInstanceId, boolean includeChildren) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
		long workflowInstanceId, boolean retrieveChildrenInfo) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo getWorkflowInstanceInfo(
		String relationType, long relationId, boolean retrieveChildrenInfo) {

		throw new UnsupportedOperationException();
	}

	public int getWorkflowInstanceInfoCount(
		String workflowDefinitionName, Integer workflowDefinitionVersion) {

		throw new UnsupportedOperationException();
	}

	public int getWorkflowInstanceInfoCount(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean completed) {

		throw new UnsupportedOperationException();
	}

	public int getWorkflowInstanceInfoCount(
		String relationType, long relationId) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean completed, boolean retrieveChildrenInfo, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean retrieveChildrenInfo, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String relationType, long relationId, boolean retrieveChildrenInfo,
		int start, int end,	OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public void removeWorkflowInstance(long workflowInstanceId) {
		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
		long workflowInstanceId, Map<String, Object> attributes,
		long callingUserId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo signalWorkflowInstance(
		long workflowInstanceId, String activityName,
		Map<String, Object> attributes, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Map<String, Object> context, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Map<String, Object> context, long callingUserId, String activityName,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId, String activityName,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

}