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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.WorkflowDefinitionLink;

import java.util.List;

/**
 * <a href="WorkflowDefinitionLinkUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkPersistence
 * @see       WorkflowDefinitionLinkPersistenceImpl
 * @generated
 */
public class WorkflowDefinitionLinkUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static WorkflowDefinitionLink remove(
		WorkflowDefinitionLink workflowDefinitionLink)
		throws SystemException {
		return getPersistence().remove(workflowDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WorkflowDefinitionLink update(
		WorkflowDefinitionLink workflowDefinitionLink, boolean merge)
		throws SystemException {
		return getPersistence().update(workflowDefinitionLink, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		getPersistence().cacheResult(workflowDefinitionLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> workflowDefinitionLinks) {
		getPersistence().cacheResult(workflowDefinitionLinks);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink create(
		long workflowDefinitionLinkId) {
		return getPersistence().create(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink remove(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink updateImpl(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(workflowDefinitionLink, merge);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(workflowDefinitionLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(workflowDefinitionLinkId,
			companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink findByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C(
		long groupId, long companyId, long classNameId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C(groupId, companyId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_C_C(long groupId, long companyId,
		long classNameId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_C(groupId, companyId, classNameId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_C_C(long groupId, long companyId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_C(groupId, companyId, classNameId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static WorkflowDefinitionLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WorkflowDefinitionLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WorkflowDefinitionLinkPersistence persistence) {
		_persistence = persistence;
	}

	private static WorkflowDefinitionLinkPersistence _persistence;
}