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
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowRequest;

/**
 * <a href="WorkflowDefinitionManagerProxyBean.java.html"><b><i>View Source</i>
 * </b></a>
 * 
 * <p>
 * The default implementation for the {@link WorkflowDefinitionManager} to be
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
public class WorkflowDefinitionManagerProxyBean
	extends BaseProxyBean implements WorkflowDefinitionManager {

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#deployWorkflowDefinition(com.liferay.portal.kernel.workflow.WorkflowDefinition,
	 *      long, java.util.Map)
	 */
	public void deployWorkflowDefinition(
		WorkflowDefinition workflowDefinition, long callingUserId,
		Map<String, Object> parameters) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#getWorkflowDefinitionCount()
	 */
	public int getWorkflowDefinitionCount() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#getWorkflowDefinitionCount(java.lang.String)
	 */
	public int getWorkflowDefinitionCount(String workflowDefinitionName) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#getWorkflowDefinitions(int,
	 *      int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowDefinition> getWorkflowDefinitions(
		int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#getWorkflowDefinitions(java.lang.String,
	 *      int, int, com.liferay.portal.kernel.util.OrderByComparator)
	 */
	public List<WorkflowDefinition> getWorkflowDefinitions(
		String workflowDefinitionName, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @see com.liferay.portal.kernel.workflow.WorkflowDefinitionManager#isSupportsVersioning()
	 */
	public boolean isSupportsVersioning() {
		throw new UnsupportedOperationException();
	}

}