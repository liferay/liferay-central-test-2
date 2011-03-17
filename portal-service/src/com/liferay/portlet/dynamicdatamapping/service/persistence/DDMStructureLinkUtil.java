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

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;

import java.util.List;

/**
 * The persistence utility for the d d m structure link service. This utility wraps {@link DDMStructureLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @see DDMStructureLinkPersistenceImpl
 * @generated
 */
public class DDMStructureLinkUtil {
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
	public static void clearCache(DDMStructureLink ddmStructureLink) {
		getPersistence().clearCache(ddmStructureLink);
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
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructureLink remove(DDMStructureLink ddmStructureLink)
		throws SystemException {
		return getPersistence().remove(ddmStructureLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink,
		boolean merge) throws SystemException {
		return getPersistence().update(ddmStructureLink, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ddmStructureLink, merge, serviceContext);
	}

	/**
	* Caches the d d m structure link in the entity cache if it is enabled.
	*
	* @param ddmStructureLink the d d m structure link to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink) {
		getPersistence().cacheResult(ddmStructureLink);
	}

	/**
	* Caches the d d m structure links in the entity cache if it is enabled.
	*
	* @param ddmStructureLinks the d d m structure links to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> ddmStructureLinks) {
		getPersistence().cacheResult(ddmStructureLinks);
	}

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink create(
		long structureLinkId) {
		return getPersistence().create(structureLinkId);
	}

	/**
	* Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link to remove
	* @return the d d m structure link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink remove(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().remove(structureLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddmStructureLink, merge);
	}

	/**
	* Finds the d d m structure link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link to find
	* @return the d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().findByPrimaryKey(structureLinkId);
	}

	/**
	* Finds the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link to find
	* @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(structureLinkId);
	}

	/**
	* Finds all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Finds a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Finds an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Finds the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Finds the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Finds the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(structureLinkId, structureId,
			orderByComparator);
	}

	/**
	* Finds the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param structureId the structure ID to search with
	* @return the matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByC_C_S(
		long classNameId, long classPK, long structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().findByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Finds the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param structureId the structure ID to search with
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Finds the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param structureId the structure ID to search with
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_S(classNameId, classPK, structureId,
			retrieveFromCache);
	}

	/**
	* Finds all the d d m structure links.
	*
	* @return the d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d m structure links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Removes the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C_S(long classNameId, long classPK,
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		getPersistence().removeByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Removes all the d d m structure links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Counts all the d d m structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param structureId the structure ID to search with
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_S(long classNameId, long classPK,
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Counts all the d d m structure links.
	*
	* @return the number of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDMStructureLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDMStructureLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(DDMStructureLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DDMStructureLinkPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DDMStructureLinkUtil.class,
			"_persistence");
	}

	private static DDMStructureLinkPersistence _persistence;
}