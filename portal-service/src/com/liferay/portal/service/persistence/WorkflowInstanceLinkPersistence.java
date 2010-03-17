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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.WorkflowInstanceLink;

/**
 * <a href="WorkflowInstanceLinkPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkPersistenceImpl
 * @see       WorkflowInstanceLinkUtil
 * @generated
 */
public interface WorkflowInstanceLinkPersistence extends BasePersistence<WorkflowInstanceLink> {
	public void cacheResult(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowInstanceLink> workflowInstanceLinks);

	public com.liferay.portal.model.WorkflowInstanceLink create(
		long workflowInstanceLinkId);

	public com.liferay.portal.model.WorkflowInstanceLink remove(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink findByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}