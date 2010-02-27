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

import com.liferay.portal.model.WorkflowDefinitionLink;

/**
 * <a href="WorkflowDefinitionLinkPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkPersistenceImpl
 * @see       WorkflowDefinitionLinkUtil
 * @generated
 */
public interface WorkflowDefinitionLinkPersistence extends BasePersistence<WorkflowDefinitionLink> {
	public void cacheResult(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> workflowDefinitionLinks);

	public com.liferay.portal.model.WorkflowDefinitionLink create(
		long workflowDefinitionLinkId);

	public com.liferay.portal.model.WorkflowDefinitionLink remove(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink updateImpl(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink findByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_C(long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_C(long groupId, long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}