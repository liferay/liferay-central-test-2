/**
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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
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

	public void deleteWorkflowInstance(long workflowInstanceId) {
		throw new UnsupportedOperationException();
	}

	public List<String> getNextTransitionNames(
		long userId, long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstance getWorkflowInstance(long workflowInstanceId) {
		throw new UnsupportedOperationException();
	}

	public int getWorkflowInstanceCount(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Boolean completed) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowInstance> getWorkflowInstances(
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		Boolean completed, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstance signalWorkflowInstance(
		long userId, long workflowInstanceId, String transitionName,
		Map<String, Object> context) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstance startWorkflowInstance(
		long userId, String workflowDefinitionName,
		Integer workflowDefinitionVersion, String transitionName,
		Map<String, Object> context) {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstance updateContext(
		long workflowInstanceId, Map<String, Object> context) {

		throw new UnsupportedOperationException();
	}

}