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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the virtual host service. This utility wraps {@link VirtualHostPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistence
 * @see VirtualHostPersistenceImpl
 * @generated
 */
public class VirtualHostUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(VirtualHost virtualHost) {
		getPersistence().clearCache(virtualHost);
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
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static VirtualHost remove(VirtualHost virtualHost)
		throws SystemException {
		return getPersistence().remove(virtualHost);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static VirtualHost update(VirtualHost virtualHost, boolean merge)
		throws SystemException {
		return getPersistence().update(virtualHost, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static VirtualHost update(VirtualHost virtualHost, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(virtualHost, merge, serviceContext);
	}

	/**
	* Caches the virtual host in the entity cache if it is enabled.
	*
	* @param virtualHost the virtual host to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.VirtualHost virtualHost) {
		getPersistence().cacheResult(virtualHost);
	}

	/**
	* Caches the virtual hosts in the entity cache if it is enabled.
	*
	* @param virtualHosts the virtual hosts to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.VirtualHost> virtualHosts) {
		getPersistence().cacheResult(virtualHosts);
	}

	/**
	* Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	*
	* @param virtualHostId the primary key for the new virtual host
	* @return the new virtual host
	*/
	public static com.liferay.portal.model.VirtualHost create(
		long virtualHostId) {
		return getPersistence().create(virtualHostId);
	}

	/**
	* Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHostId the primary key of the virtual host to remove
	* @return the virtual host that was removed
	* @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost remove(
		long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(virtualHostId);
	}

	public static com.liferay.portal.model.VirtualHost updateImpl(
		com.liferay.portal.model.VirtualHost virtualHost, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(virtualHost, merge);
	}

	/**
	* Finds the virtual host with the primary key or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host to find
	* @return the virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost findByPrimaryKey(
		long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(virtualHostId);
	}

	/**
	* Finds the virtual host with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host to find
	* @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost fetchByPrimaryKey(
		long virtualHostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(virtualHostId);
	}

	/**
	* Finds the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param companyId the company id to search with
	* @param layoutSetId the layout set id to search with
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost findByC_L(
		long companyId, long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_L(companyId, layoutSetId);
	}

	/**
	* Finds the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company id to search with
	* @param layoutSetId the layout set id to search with
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost fetchByC_L(
		long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_L(companyId, layoutSetId);
	}

	/**
	* Finds the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company id to search with
	* @param layoutSetId the layout set id to search with
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost fetchByC_L(
		long companyId, long layoutSetId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_L(companyId, layoutSetId, retrieveFromCache);
	}

	/**
	* Finds the virtual host where virtualHostName = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param virtualHostName the virtual host name to search with
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost findByVirtualHostName(
		java.lang.String virtualHostName)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVirtualHostName(virtualHostName);
	}

	/**
	* Finds the virtual host where virtualHostName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param virtualHostName the virtual host name to search with
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost fetchByVirtualHostName(
		java.lang.String virtualHostName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByVirtualHostName(virtualHostName);
	}

	/**
	* Finds the virtual host where virtualHostName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param virtualHostName the virtual host name to search with
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.VirtualHost fetchByVirtualHostName(
		java.lang.String virtualHostName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByVirtualHostName(virtualHostName, retrieveFromCache);
	}

	/**
	* Finds all the virtual hosts.
	*
	* @return the virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.VirtualHost> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts to return
	* @param end the upper bound of the range of virtual hosts to return (not inclusive)
	* @return the range of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts to return
	* @param end the upper bound of the range of virtual hosts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param layoutSetId the layout set id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_L(long companyId, long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_L(companyId, layoutSetId);
	}

	/**
	* Removes the virtual host where virtualHostName = &#63; from the database.
	*
	* @param virtualHostName the virtual host name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByVirtualHostName(java.lang.String virtualHostName)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByVirtualHostName(virtualHostName);
	}

	/**
	* Removes all the virtual hosts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	*
	* @param companyId the company id to search with
	* @param layoutSetId the layout set id to search with
	* @return the number of matching virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_L(long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_L(companyId, layoutSetId);
	}

	/**
	* Counts all the virtual hosts where virtualHostName = &#63;.
	*
	* @param virtualHostName the virtual host name to search with
	* @return the number of matching virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByVirtualHostName(java.lang.String virtualHostName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByVirtualHostName(virtualHostName);
	}

	/**
	* Counts all the virtual hosts.
	*
	* @return the number of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static VirtualHostPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (VirtualHostPersistence)PortalBeanLocatorUtil.locate(VirtualHostPersistence.class.getName());

			ReferenceRegistry.registerReference(VirtualHostUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(VirtualHostPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(VirtualHostUtil.class,
			"_persistence");
	}

	private static VirtualHostPersistence _persistence;
}