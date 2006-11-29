/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service;

/**
 * <a href="WorkflowComponentServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowComponentServiceUtil {
	public static java.util.List getCurrentTasks(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getCurrentTasks(instanceId, tokenId);
	}

	public static java.lang.String getCurrentTasksXml(long instanceId,
		long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getCurrentTasksXml(instanceId, tokenId);
	}

	public static java.lang.String deploy(java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.deploy(xml);
	}

	public static java.util.List getDefinitions(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getDefinitions(definitionId, name,
			begin, end);
	}

	public static java.lang.String getDefinitionsXml(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getDefinitionsXml(definitionId, name,
			begin, end);
	}

	public static int getDefinitionsCount(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getDefinitionsCount(definitionId, name);
	}

	public static java.lang.String getDefinitionsCountXml(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getDefinitionsCountXml(definitionId,
			name);
	}

	public static java.util.List getInstances(long definitionId,
		long instanceId, java.lang.String workflowName,
		java.lang.String workflowVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getInstances(definitionId, instanceId,
			workflowName, workflowVersion, gtStartDate, ltStartDate, gtEndDate,
			ltEndDate, hideEndedTasks, andOperator, begin, end);
	}

	public static int getInstancesCount(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getInstancesCount(definitionId,
			instanceId, workflowName, workflowVersion, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator);
	}

	public static java.lang.String getInstancesCountXml(long definitionId,
		long instanceId, java.lang.String workflowName,
		java.lang.String workflowVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getInstancesCountXml(definitionId,
			instanceId, workflowName, workflowVersion, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator);
	}

	public static java.lang.String getInstancesXml(long definitionId,
		long instanceId, java.lang.String workflowName,
		java.lang.String workflowVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getInstancesXml(definitionId,
			instanceId, workflowName, workflowVersion, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator,
			begin, end);
	}

	public static java.util.List getTaskFormElements(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getTaskFormElements(taskId);
	}

	public static java.lang.String getTaskFormElementsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getTaskFormElementsXml(taskId);
	}

	public static java.util.List getTaskTransitions(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getTaskTransitions(taskId);
	}

	public static java.lang.String getTaskTransitionsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getTaskTransitionsXml(taskId);
	}

	public static java.util.List getUserTasks(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getUserTasks(instanceId, taskName,
			workflowName, assignedTo, gtCreateDate, ltCreateDate, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator,
			begin, end);
	}

	public static int getUserTasksCount(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getUserTasksCount(instanceId, taskName,
			workflowName, assignedTo, gtCreateDate, ltCreateDate, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator);
	}

	public static java.lang.String getUserTasksCountXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getUserTasksCountXml(instanceId,
			taskName, workflowName, assignedTo, gtCreateDate, ltCreateDate,
			gtStartDate, ltStartDate, gtEndDate, ltEndDate, hideEndedTasks,
			andOperator);
	}

	public static java.lang.String getUserTasksXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.getUserTasksXml(instanceId, taskName,
			workflowName, assignedTo, gtCreateDate, ltCreateDate, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator,
			begin, end);
	}

	public static void signalInstance(long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();
		workflowComponentService.signalInstance(instanceId);
	}

	public static void signalToken(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();
		workflowComponentService.signalToken(instanceId, tokenId);
	}

	public static java.lang.String startWorkflow(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.startWorkflow(definitionId);
	}

	public static java.util.Map updateTask(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.updateTask(taskId, transition,
			parameterMap);
	}

	public static java.lang.String updateTaskXml(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		WorkflowComponentService workflowComponentService = WorkflowComponentServiceFactory.getService();

		return workflowComponentService.updateTaskXml(taskId, transition,
			parameterMap);
	}
}