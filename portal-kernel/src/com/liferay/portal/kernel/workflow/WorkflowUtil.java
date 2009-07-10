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
 * <p>
 * This utility class defines the main entry point to the workflow system in
 * Liferay as it provides static access to different manager of the workflow
 * system. A more detailed description can be found on the manager interfaces
 * being returned by the static accessory methods in this utility class.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public class WorkflowUtil {

	/**
	 * The task instance manager is the entry point to deal with tasks created
	 * out of a workflow definition, always attached to a workflow instance. The
	 * manager supports method to retrieve, assign and approve tasks.
	 *
	 * @return the task instance manager
	 */
	public static TaskInstanceManager getTaskInstanceManager() {
		return _taskInstanceManager;
	}

	/**
	 * The workflow definition manager is the entry point to deploy workflow
	 * definitions. A workflow definition is the model used to describe the
	 * workflow.
	 *
	 * @return the workflow definition manager
	 */
	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		return _workflowDefinitionManager;
	}

	/**
	 * The workflow definition mapper can be used for mapping a domain class to
	 * a certain workflow definition name.
	 *
	 * @return the workflow mapper
	 */
	public static WorkflowDefinitionMapper getWorkflowDefinitionMapper() {
		return _workflowDefinitionMapper;
	}

	/**
	 * The workflow instance manager is the entry point to deal with existing
	 * workflow instances and how to retrieve them. More details about a
	 * workflow instance and how to start them and retrieve them can be read
	 * within the javadoc of {@link WorkflowInstanceManager}.
	 *
	 * @return the workflow instance manager
	 */
	public static WorkflowInstanceManager getWorkflowInstanceManager() {
		return _workflowInstanceManager;
	}

	/**
	 * @param taskInstanceManager the task manager to be made available through
	 *			this utility
	 */
	public static void setTaskInstanceManager(
		TaskInstanceManager taskInstanceManager) {

		_taskInstanceManager = taskInstanceManager;
	}

	/**
	 * @param workflowDefinitionManager the definition manager to be made
	 *			available through this utility
	 */
	public static void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	/**
	 * @param workflowDefinitionMapper the definition mapper to be made
	 *			available through this utility
	 */
	public static void setWorkflowDefinitionMapper(
			WorkflowDefinitionMapper workflowDefinitionMapper) {

		_workflowDefinitionMapper = workflowDefinitionMapper;
	}

	/**
	 * @param workflowInstanceManager the instance manager to be made available
	 *			through this utility
	 */
	public static void setWorkflowInstanceManager(
		WorkflowInstanceManager workflowInstanceManager) {

		_workflowInstanceManager = workflowInstanceManager;
	}

	private static TaskInstanceManager _taskInstanceManager;
	private static WorkflowDefinitionManager _workflowDefinitionManager;
	private static WorkflowDefinitionMapper _workflowDefinitionMapper;
	private static WorkflowInstanceManager _workflowInstanceManager;

}