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
import com.liferay.portal.model.ServiceComponent;

import java.util.List;

/**
 * <a href="ServiceComponentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentPersistence
 * @see       ServiceComponentPersistenceImpl
 * @generated
 */
public class ServiceComponentUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ServiceComponent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ServiceComponent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ServiceComponent remove(ServiceComponent serviceComponent)
		throws SystemException {
		return getPersistence().remove(serviceComponent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ServiceComponent update(ServiceComponent serviceComponent,
		boolean merge) throws SystemException {
		return getPersistence().update(serviceComponent, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.ServiceComponent serviceComponent) {
		getPersistence().cacheResult(serviceComponent);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ServiceComponent> serviceComponents) {
		getPersistence().cacheResult(serviceComponents);
	}

	public static com.liferay.portal.model.ServiceComponent create(
		long serviceComponentId) {
		return getPersistence().create(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent remove(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(serviceComponent, merge);
	}

	public static com.liferay.portal.model.ServiceComponent findByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent fetchByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(serviceComponentId);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBuildNamespace(buildNamespace);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBuildNamespace(buildNamespace, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBuildNamespace(buildNamespace, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBuildNamespace_First(buildNamespace, orderByComparator);
	}

	public static com.liferay.portal.model.ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBuildNamespace_Last(buildNamespace, orderByComparator);
	}

	public static com.liferay.portal.model.ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBuildNamespace_PrevAndNext(serviceComponentId,
			buildNamespace, orderByComparator);
	}

	public static com.liferay.portal.model.ServiceComponent findByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBNS_BNU(buildNamespace, buildNumber);
	}

	public static com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByBNS_BNU(buildNamespace, buildNumber);
	}

	public static com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByBNS_BNU(buildNamespace, buildNumber,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByBuildNamespace(buildNamespace);
	}

	public static void removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByBNS_BNU(buildNamespace, buildNumber);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByBuildNamespace(buildNamespace);
	}

	public static int countByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByBNS_BNU(buildNamespace, buildNumber);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ServiceComponentPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ServiceComponentPersistence persistence) {
		_persistence = persistence;
	}

	private static ServiceComponentPersistence _persistence;
}