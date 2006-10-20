/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.workflow.service.spring;

/**
 * <a href="WorkflowComponentService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface WorkflowComponentService {
	public java.lang.String deploy(java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.List getDefinitions(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getDefinitionsXml(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public long getDefinitionsCount(long definitionId, java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getDefinitionsCountXml(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.List getInstances(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public long getInstancesCount(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getInstancesCountXml(long definitionId,
		long instanceId, java.lang.String workflowName,
		java.lang.String workflowVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getInstancesXml(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.List getTaskFormElements(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getTaskFormElementsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.List getTaskTransitions(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getTaskTransitionsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.List getUserTasks(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public long getUserTasksCount(long instanceId, java.lang.String taskName,
		java.lang.String workflowName, java.lang.String assignedTo,
		java.lang.String gtCreateDate, java.lang.String ltCreateDate,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getUserTasksCountXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String getUserTasksXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public void signalInstance(long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public void signalToken(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String startWorkflow(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.util.Map updateTask(long taskId, java.lang.String transition,
		java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;

	public java.lang.String updateTaskXml(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException;
}