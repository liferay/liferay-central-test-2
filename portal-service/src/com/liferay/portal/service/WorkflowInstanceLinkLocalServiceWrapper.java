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

package com.liferay.portal.service;


/**
 * <a href="WorkflowInstanceLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowInstanceLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkLocalService
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceWrapper
	implements WorkflowInstanceLinkLocalService {
	public WorkflowInstanceLinkLocalServiceWrapper(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(workflowInstanceLink);
	}

	public com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return _workflowInstanceLinkLocalService.createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public void deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public void deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end) throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(start,
			end);
	}

	public int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinksCount();
	}

	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink);
	}

	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge) throws com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink,
			merge);
	}

	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(userId,
			companyId, groupId, className, classPK, workflowInstanceId);
	}

	public void deleteWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.getState(companyId, groupId,
			className, classPK);
	}

	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _workflowInstanceLinkLocalService.hasWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_workflowInstanceLinkLocalService.startWorkflowInstance(companyId,
			groupId, userId, className, classPK);
	}

	public WorkflowInstanceLinkLocalService getWrappedWorkflowInstanceLinkLocalService() {
		return _workflowInstanceLinkLocalService;
	}

	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;
}