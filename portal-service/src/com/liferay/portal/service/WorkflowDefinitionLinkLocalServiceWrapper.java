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
 * <a href="WorkflowDefinitionLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowDefinitionLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkLocalService
 * @generated
 */
public class WorkflowDefinitionLinkLocalServiceWrapper
	implements WorkflowDefinitionLinkLocalService {
	public WorkflowDefinitionLinkLocalServiceWrapper(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		_workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	public com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink createWorkflowDefinitionLink(
		long workflowDefinitionLinkId) {
		return _workflowDefinitionLinkLocalService.createWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public void deleteWorkflowDefinitionLink(long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public void deleteWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> getWorkflowDefinitionLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinks(start,
			end);
	}

	public int getWorkflowDefinitionLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount();
	}

	public com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(workflowDefinitionLink,
			merge);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(userId,
			companyId, groupId, className, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	public void deleteWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(companyId,
			groupId, className);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(companyId,
			groupId, className);
	}

	public int getWorkflowDefinitionLinksCount(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(companyId,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	public boolean hasWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(companyId,
			groupId, className);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(userId,
			companyId, groupId, className, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	public WorkflowDefinitionLinkLocalService getWrappedWorkflowDefinitionLinkLocalService() {
		return _workflowDefinitionLinkLocalService;
	}

	private WorkflowDefinitionLinkLocalService _workflowDefinitionLinkLocalService;
}