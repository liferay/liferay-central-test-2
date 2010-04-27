/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(workflowInstanceLink);
	}

	public com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return _workflowInstanceLinkLocalService.createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public void deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public void deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(start,
			end);
	}

	public int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinksCount();
	}

	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink);
	}

	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink,
			merge);
	}

	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(userId,
			companyId, groupId, className, classPK, workflowInstanceId);
	}

	public void deleteWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public void deleteWorkflowInstanceLinks(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	public java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getState(companyId, groupId,
			className, classPK);
	}

	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	public boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.hasWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public boolean isEnded(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.isEnded(companyId, groupId,
			className, classPK);
	}

	public void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.startWorkflowInstance(companyId,
			groupId, userId, className, classPK, workflowContext);
	}

	public void updateClassPK(long companyId, long groupId,
		java.lang.String className, long oldClassPK, long newClassPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.updateClassPK(companyId, groupId,
			className, oldClassPK, newClassPK);
	}

	public WorkflowInstanceLinkLocalService getWrappedWorkflowInstanceLinkLocalService() {
		return _workflowInstanceLinkLocalService;
	}

	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;
}