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

import com.liferay.portal.model.ServiceComponent;

/**
 * <a href="ServiceComponentPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentPersistenceImpl
 * @see       ServiceComponentUtil
 * @generated
 */
public interface ServiceComponentPersistence extends BasePersistence<ServiceComponent> {
	public void cacheResult(
		com.liferay.portal.model.ServiceComponent serviceComponent);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ServiceComponent> serviceComponents);

	public com.liferay.portal.model.ServiceComponent create(
		long serviceComponentId);

	public com.liferay.portal.model.ServiceComponent remove(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent findByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByBNS_BNU(java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}