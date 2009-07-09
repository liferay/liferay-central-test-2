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
 */
public class WorkflowUtil {

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
     * The workflow definition registry can be used for mapping a domain class
     * to a certain workflow definition id.
     * 
     * @return the workflow registry
     */
    public static WorkflowDefinitionRegistry getWorkflowDefinitionRegistry() {
        return _workflowDefinitionRegistry;
    }

    /**
     * Accessor method to set the managers to make them available through the
     * static methods provided by this utility.
     * 
     * @param workflowDefinitionManager
     * @param workflowInstanceManager
     * @param taskInstanceManager
     * @param workflowRegistry
     */
    public static void setManagers(
            WorkflowDefinitionManager workflowDefinitionManager,
            WorkflowInstanceManager workflowInstanceManager,
            TaskInstanceManager taskInstanceManager,
            WorkflowDefinitionRegistry workflowRegistry) {
        _workflowDefinitionManager = workflowDefinitionManager;
        _workflowInstanceManager = workflowInstanceManager;
        _taskInstanceManager = taskInstanceManager;
        _workflowDefinitionRegistry = workflowRegistry;
    }

    private static WorkflowInstanceManager _workflowInstanceManager;
    private static TaskInstanceManager _taskInstanceManager;
    private static WorkflowDefinitionManager _workflowDefinitionManager;
    private static WorkflowDefinitionRegistry _workflowDefinitionRegistry;
}