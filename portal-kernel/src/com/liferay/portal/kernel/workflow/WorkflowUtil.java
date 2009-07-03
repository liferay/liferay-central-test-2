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

/**
 * <a href="WorkflowUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowUtil {

	public static ProcessInstanceManager getProcessInstanceManager() {
		return _processInstanceManager;
	}

	public static TaskInstanceManager getTaskInstanceManager() {
		return _taskInstanceManager;
	}

	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		return _workflowDefinitionManager;
	}

	public static WorkflowRegistry getWorkflowRegistry() {
		return _workflowRegistry;
	}

	public WorkflowUtil(
			WorkflowDefinitionManager workflowDefinitionManager,
			ProcessInstanceManager processInstanceManager,
			TaskInstanceManager taskInstanceManager,
			WorkflowRegistry workflowRegistry){
			_workflowDefinitionManager = workflowDefinitionManager;
			_processInstanceManager = processInstanceManager;
			_taskInstanceManager = taskInstanceManager;
			_workflowRegistry = workflowRegistry;
	}

	private static ProcessInstanceManager _processInstanceManager;
	private static TaskInstanceManager _taskInstanceManager;
	private static WorkflowDefinitionManager _workflowDefinitionManager;
	private static WorkflowRegistry _workflowRegistry;

}