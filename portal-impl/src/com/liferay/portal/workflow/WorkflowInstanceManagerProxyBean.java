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
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowRequest;

/**
 * <a href="WorkflowInstanceManagerProxyBean.java.html"><b><i>View Source</i>
 * </b></a>
 * 
 * <p>
 * The default implementation for the {@link WorkflowInstanceManager} to be
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
public class WorkflowInstanceManagerProxyBean
	extends BaseProxyBean implements WorkflowInstanceManager {

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#addContextInformation(long,
	 *      java.util.Map)
	 */
	public WorkflowInstanceInfo addContextInformation(
		long workflowInstanceId, Map<String, Object> context) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getPossibleNextActivityNames(long,
	 *      long, java.util.Map)
	 */
	public List<String> getPossibleNextActivityNames(
		long workflowInstanceId, long userId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceHistory(long,
	 *      boolean, int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
		long workflowInstanceId, boolean includeChildren, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceHistoryCount(long,
	 *      boolean)
	 */
	public int getWorkflowInstanceHistoryCount(
		long workflowInstanceId, boolean includeChildren) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfo(long,
	 *      boolean)
	 */
	public WorkflowInstanceInfo getWorkflowInstanceInfo(
		long workflowInstanceId, boolean retrieveChildrenInfo) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfo(java.lang.String,
	 *      long, boolean)
	 */
	public WorkflowInstanceInfo getWorkflowInstanceInfo(
		String relationType, long relationId, boolean retrieveChildrenInfo) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfoCount(java.lang.String,
	 *      java.lang.Integer)
	 */
	public int getWorkflowInstanceInfoCount(
		String workflowDefinitionName, Integer workflowDefinitionVersion) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfoCount(java.lang.String,
	 *      java.lang.Integer, boolean)
	 */
	public int getWorkflowInstanceInfoCount(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean finished) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfoCount(java.lang.String,
	 *      long)
	 */
	public int getWorkflowInstanceInfoCount(
		String relationType, long relationId) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfos(java.lang.String,
	 *      java.lang.Integer, boolean, boolean, int, int,
	 *      com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean finished, boolean retrieveChildrenInfo, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfos(java.lang.String,
	 *      java.lang.Integer, boolean, int, int,
	 *      com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		boolean retrieveChildrenInfo, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#getWorkflowInstanceInfos(java.lang.String,
	 *      long, boolean, int, int,
	 *      com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
		String relationType, long relationId, boolean retrieveChildrenInfo,
		int start, int end,	OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#removeWorkflowInstance(long)
	 */
	public void removeWorkflowInstance(long workflowInstanceId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#signalWorkflowInstance(long,
	 *      java.util.Map, long, java.util.Map)
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
		long workflowInstanceId, Map<String, Object> attributes,
		long callingUserId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#signalWorkflowInstance(long,
	 *      java.lang.String, java.util.Map, long, java.util.Map)
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
		long workflowInstanceId, String activityName,
		Map<String, Object> attributes, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#startWorkflowInstance(java.lang.String,
	 *      java.lang.Integer, java.util.Map, long, java.util.Map)
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Map<String, Object> context, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#startWorkflowInstance(java.lang.String,
	 *      java.lang.Integer, java.util.Map, long, java.lang.String,
	 *      java.util.Map)
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Map<String, Object> context, long callingUserId, String activityName,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#startWorkflowInstance(java.lang.String,
	 *      java.lang.Integer, java.lang.String, long, java.util.Map, long,
	 *      java.util.Map)
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowInstanceManager#startWorkflowInstance(java.lang.String,
	 *      java.lang.Integer, java.lang.String, long, java.util.Map, long,
	 *      java.lang.String, java.util.Map)
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String relationType, long relationId, Map<String, Object> context,
		long callingUserId, String activityName, Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

}