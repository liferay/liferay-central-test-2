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

import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowDefinitionManagerUtil.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * The utility class supporting static access to all methods for the {@link
 * WorkflowDefinitionManager} interface. The target manager object is injected
 * using the {@link #setWorkflowDefinitionManager(WorkflowDefinitionManager)}
 * method. Besides the static method access, it is also available through {@link
 * #getWorkflowDefinitionManager()}.
 * </p>
 *
 * @author Micha Kiener
 */
public class WorkflowDefinitionManagerUtil {

	/**
	 * @see WorkflowDefinitionManager#deployWorkflowDefinition(
	 *		WorkflowDefinition, long, Map)
	 */
	public static void deployWorkflowDefinition(
			WorkflowDefinition workflowDefinition, long callingUserId,
			Map<String, Object> parameters)
		throws WorkflowException {

		_workflowDefinitionManager.deployWorkflowDefinition(
			workflowDefinition, callingUserId, parameters);
	}

	/**
	 * @see WorkflowDefinitionManager#getWorkflowDefinitionCount()
	 */
	public static int getWorkflowDefinitionCount() throws WorkflowException {
		return _workflowDefinitionManager.getWorkflowDefinitionCount();
	}

	/**
	 * @see WorkflowDefinitionManager#getWorkflowDefinitionCount(String)
	 */
	public static int getWorkflowDefinitionCount(
			String workflowDefinitionName)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitionCount(
			workflowDefinitionName);
	}

	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		return _workflowDefinitionManager;
	}

	/**
	 * @see WorkflowDefinitionManager#getWorkflowDefinitions(int, int,
	 *		OrderByComparator)
	 */
	public static List<WorkflowDefinition> getWorkflowDefinitions(
		int start, int end, OrderByComparator orderByComparator) {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			start, end, orderByComparator);
	}

	/**
	 * @see WorkflowDefinitionManager#getWorkflowDefinitions(String, int, int,
	 *		OrderByComparator)
	 */
	public static List<WorkflowDefinition> getWorkflowDefinitions(
		String workflowDefinitionName, int start, int end,
		OrderByComparator orderByComparator) {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			workflowDefinitionName, start, end, orderByComparator);
	}


	public void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	private static WorkflowDefinitionManager _workflowDefinitionManager;

}