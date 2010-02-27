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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WorkflowInstanceLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WorkflowInstanceLinkLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkLocalService
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceUtil {
	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWorkflowInstanceLink(workflowInstanceLink);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return getService().createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static void deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static void deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLinks(start, end);
	}

	public static int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLinksCount();
	}

	public static com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWorkflowInstanceLink(workflowInstanceLink);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateWorkflowInstanceLink(workflowInstanceLink, merge);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addWorkflowInstanceLink(userId, companyId, groupId,
			className, classPK, workflowInstanceId);
	}

	public static void deleteWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteWorkflowInstanceLink(companyId, groupId, className, classPK);
	}

	public static java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getState(companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.startWorkflowInstance(companyId, groupId, userId, className,
			classPK);
	}

	public static WorkflowInstanceLinkLocalService getService() {
		if (_service == null) {
			_service = (WorkflowInstanceLinkLocalService)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WorkflowInstanceLinkLocalService service) {
		_service = service;
	}

	private static WorkflowInstanceLinkLocalService _service;
}