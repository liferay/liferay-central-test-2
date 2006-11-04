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

package com.liferay.portlet.workflow.service.ejb;

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.workflow.service.spring.WorkflowComponentService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="WorkflowComponentServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowComponentServiceEJBImpl implements WorkflowComponentService,
	SessionBean {
	public static final String CLASS_NAME = WorkflowComponentService.class.getName() +
		".transaction";

	public static WorkflowComponentService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (WorkflowComponentService)ctx.getBean(CLASS_NAME);
	}

	public java.util.List getCurrentTasks(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCurrentTasks(instanceId, tokenId);
	}

	public java.lang.String getCurrentTasksXml(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCurrentTasksXml(instanceId, tokenId);
	}

	public java.lang.String deploy(java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().deploy(xml);
	}

	public java.util.List getDefinitions(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getDefinitions(definitionId, name, begin, end);
	}

	public java.lang.String getDefinitionsXml(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getDefinitionsXml(definitionId, name, begin, end);
	}

	public int getDefinitionsCount(long definitionId, java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getDefinitionsCount(definitionId, name);
	}

	public java.lang.String getDefinitionsCountXml(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getDefinitionsCountXml(definitionId, name);
	}

	public java.util.List getInstances(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getInstances(definitionId, instanceId,
			workflowName, workflowVersion, gtStartDate, ltStartDate, gtEndDate,
			ltEndDate, hideEndedTasks, andOperator, begin, end);
	}

	public int getInstancesCount(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getInstancesCount(definitionId, instanceId,
			workflowName, workflowVersion, gtStartDate, ltStartDate, gtEndDate,
			ltEndDate, hideEndedTasks, andOperator);
	}

	public java.lang.String getInstancesCountXml(long definitionId,
		long instanceId, java.lang.String workflowName,
		java.lang.String workflowVersion, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getInstancesCountXml(definitionId, instanceId,
			workflowName, workflowVersion, gtStartDate, ltStartDate, gtEndDate,
			ltEndDate, hideEndedTasks, andOperator);
	}

	public java.lang.String getInstancesXml(long definitionId, long instanceId,
		java.lang.String workflowName, java.lang.String workflowVersion,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getInstancesXml(definitionId, instanceId,
			workflowName, workflowVersion, gtStartDate, ltStartDate, gtEndDate,
			ltEndDate, hideEndedTasks, andOperator, begin, end);
	}

	public java.util.List getTaskFormElements(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getTaskFormElements(taskId);
	}

	public java.lang.String getTaskFormElementsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getTaskFormElementsXml(taskId);
	}

	public java.util.List getTaskTransitions(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getTaskTransitions(taskId);
	}

	public java.lang.String getTaskTransitionsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getTaskTransitionsXml(taskId);
	}

	public java.util.List getUserTasks(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getUserTasks(instanceId, taskName, workflowName,
			assignedTo, gtCreateDate, ltCreateDate, gtStartDate, ltStartDate,
			gtEndDate, ltEndDate, hideEndedTasks, andOperator, begin, end);
	}

	public int getUserTasksCount(long instanceId, java.lang.String taskName,
		java.lang.String workflowName, java.lang.String assignedTo,
		java.lang.String gtCreateDate, java.lang.String ltCreateDate,
		java.lang.String gtStartDate, java.lang.String ltStartDate,
		java.lang.String gtEndDate, java.lang.String ltEndDate,
		boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getUserTasksCount(instanceId, taskName,
			workflowName, assignedTo, gtCreateDate, ltCreateDate, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator);
	}

	public java.lang.String getUserTasksCountXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getUserTasksCountXml(instanceId, taskName,
			workflowName, assignedTo, gtCreateDate, ltCreateDate, gtStartDate,
			ltStartDate, gtEndDate, ltEndDate, hideEndedTasks, andOperator);
	}

	public java.lang.String getUserTasksXml(long instanceId,
		java.lang.String taskName, java.lang.String workflowName,
		java.lang.String assignedTo, java.lang.String gtCreateDate,
		java.lang.String ltCreateDate, java.lang.String gtStartDate,
		java.lang.String ltStartDate, java.lang.String gtEndDate,
		java.lang.String ltEndDate, boolean hideEndedTasks,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getUserTasksXml(instanceId, taskName, workflowName,
			assignedTo, gtCreateDate, ltCreateDate, gtStartDate, ltStartDate,
			gtEndDate, ltEndDate, hideEndedTasks, andOperator, begin, end);
	}

	public void signalInstance(long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().signalInstance(instanceId);
	}

	public void signalToken(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().signalToken(instanceId, tokenId);
	}

	public java.lang.String startWorkflow(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().startWorkflow(definitionId);
	}

	public java.util.Map updateTask(long taskId, java.lang.String transition,
		java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateTask(taskId, transition, parameterMap);
	}

	public java.lang.String updateTaskXml(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException, 
			java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateTaskXml(taskId, transition, parameterMap);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}