/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;

import java.util.List;

/**
 * The persistence utility for the d d m structure entry link service. This utility wraps {@link DDMStructureEntryLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLinkPersistence
 * @see DDMStructureEntryLinkPersistenceImpl
 * @generated
 */
public class DDMStructureEntryLinkUtil {
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
	public static void clearCache(DDMStructureEntryLink ddmStructureEntryLink) {
		getPersistence().clearCache(ddmStructureEntryLink);
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
	public static List<DDMStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructureEntryLink remove(
		DDMStructureEntryLink ddmStructureEntryLink) throws SystemException {
		return getPersistence().remove(ddmStructureEntryLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DDMStructureEntryLink update(
		DDMStructureEntryLink ddmStructureEntryLink, boolean merge)
		throws SystemException {
		return getPersistence().update(ddmStructureEntryLink, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DDMStructureEntryLink update(
		DDMStructureEntryLink ddmStructureEntryLink, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(ddmStructureEntryLink, merge, serviceContext);
	}

	/**
	* Caches the d d m structure entry link in the entity cache if it is enabled.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink) {
		getPersistence().cacheResult(ddmStructureEntryLink);
	}

	/**
	* Caches the d d m structure entry links in the entity cache if it is enabled.
	*
	* @param ddmStructureEntryLinks the d d m structure entry links to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> ddmStructureEntryLinks) {
		getPersistence().cacheResult(ddmStructureEntryLinks);
	}

	/**
	* Creates a new d d m structure entry link with the primary key. Does not add the d d m structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new d d m structure entry link
	* @return the new d d m structure entry link
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink create(
		long structureEntryLinkId) {
		return getPersistence().create(structureEntryLinkId);
	}

	/**
	* Removes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to remove
	* @return the d d m structure entry link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink remove(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence().remove(structureEntryLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddmStructureEntryLink, merge);
	}

	/**
	* Finds the d d m structure entry link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to find
	* @return the d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence().findByPrimaryKey(structureEntryLinkId);
	}

	/**
	* Finds the d d m structure entry link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to find
	* @return the d d m structure entry link, or <code>null</code> if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(structureEntryLinkId);
	}

	/**
	* Finds all the d d m structure entry links where structureKey = &#63;.
	*
	* @param structureKey the structure key to search with
	* @return the matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureKey(
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureKey(structureKey);
	}

	/**
	* Finds a range of all the d d m structure entry links where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureKey(
		java.lang.String structureKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureKey(structureKey, start, end);
	}

	/**
	* Finds an ordered range of all the d d m structure entry links where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureKey(
		java.lang.String structureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureKey(structureKey, start, end,
			orderByComparator);
	}

	/**
	* Finds the first d d m structure entry link in the ordered set where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByStructureKey_First(
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureKey_First(structureKey, orderByComparator);
	}

	/**
	* Finds the last d d m structure entry link in the ordered set where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByStructureKey_Last(
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureKey_Last(structureKey, orderByComparator);
	}

	/**
	* Finds the d d m structure entry links before and after the current d d m structure entry link in the ordered set where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current d d m structure entry link
	* @param structureKey the structure key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink[] findByStructureKey_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureKey_PrevAndNext(structureEntryLinkId,
			structureKey, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the d d m structure entry links where structureKey = &#63;.
	*
	* @param structureKey the structure key to search with
	* @return the matching d d m structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> filterFindByStructureKey(
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByStructureKey(structureKey);
	}

	/**
	* Filters by the user's permissions and finds a range of all the d d m structure entry links where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of matching d d m structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> filterFindByStructureKey(
		java.lang.String structureKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByStructureKey(structureKey, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the d d m structure entry links where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureKey the structure key to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> filterFindByStructureKey(
		java.lang.String structureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByStructureKey(structureKey, start, end,
			orderByComparator);
	}

	/**
	* Filters the d d m structure entry links before and after the current d d m structure entry link in the ordered set where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current d d m structure entry link
	* @param structureKey the structure key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink[] filterFindByStructureKey_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .filterFindByStructureKey_PrevAndNext(structureEntryLinkId,
			structureKey, orderByComparator);
	}

	/**
	* Finds the d d m structure entry link where structureKey = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureKey the structure key to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByS_C_C(
		java.lang.String structureKey, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		return getPersistence().findByS_C_C(structureKey, className, classPK);
	}

	/**
	* Finds the d d m structure entry link where structureKey = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureKey the structure key to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByS_C_C(
		java.lang.String structureKey, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByS_C_C(structureKey, className, classPK);
	}

	/**
	* Finds the d d m structure entry link where structureKey = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureKey the structure key to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByS_C_C(
		java.lang.String structureKey, java.lang.String className,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByS_C_C(structureKey, className, classPK,
			retrieveFromCache);
	}

	/**
	* Finds all the d d m structure entry links.
	*
	* @return the d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d d m structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d d m structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d m structure entry links where structureKey = &#63; from the database.
	*
	* @param structureKey the structure key to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureKey(java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureKey(structureKey);
	}

	/**
	* Removes the d d m structure entry link where structureKey = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param structureKey the structure key to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByS_C_C(java.lang.String structureKey,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException {
		getPersistence().removeByS_C_C(structureKey, className, classPK);
	}

	/**
	* Removes all the d d m structure entry links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d d m structure entry links where structureKey = &#63;.
	*
	* @param structureKey the structure key to search with
	* @return the number of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureKey(java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureKey(structureKey);
	}

	/**
	* Filters by the user's permissions and counts all the d d m structure entry links where structureKey = &#63;.
	*
	* @param structureKey the structure key to search with
	* @return the number of matching d d m structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByStructureKey(java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByStructureKey(structureKey);
	}

	/**
	* Counts all the d d m structure entry links where structureKey = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param structureKey the structure key to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the number of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByS_C_C(java.lang.String structureKey,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByS_C_C(structureKey, className, classPK);
	}

	/**
	* Counts all the d d m structure entry links.
	*
	* @return the number of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDMStructureEntryLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDMStructureEntryLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureEntryLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(DDMStructureEntryLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DDMStructureEntryLinkPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DDMStructureEntryLinkUtil.class,
			"_persistence");
	}

	private static DDMStructureEntryLinkPersistence _persistence;
}