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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;

/**
 * The persistence interface for the d d m structure entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLinkPersistenceImpl
 * @see DDMStructureEntryLinkUtil
 * @generated
 */
public interface DDMStructureEntryLinkPersistence extends BasePersistence<DDMStructureEntryLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureEntryLinkUtil} to access the d d m structure entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m structure entry link in the entity cache if it is enabled.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to cache
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink);

	/**
	* Caches the d d m structure entry links in the entity cache if it is enabled.
	*
	* @param ddmStructureEntryLinks the d d m structure entry links to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> ddmStructureEntryLinks);

	/**
	* Creates a new d d m structure entry link with the primary key. Does not add the d d m structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new d d m structure entry link
	* @return the new d d m structure entry link
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink create(
		long structureEntryLinkId);

	/**
	* Removes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to remove
	* @return the d d m structure entry link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink remove(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d d m structure entry link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to find
	* @return the d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Finds the d d m structure entry link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to find
	* @return the d d m structure entry link, or <code>null</code> if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d d m structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d d m structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d d m structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d d m structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Finds the last d d m structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Finds the d d m structure entry links before and after the current d d m structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current d d m structure entry link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink[] findByStructureId_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink findByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching d d m structure entry link, or <code>null</code> if a matching d d m structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d d m structure entry links.
	*
	* @return the d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structure entry links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryLinkException;

	/**
	* Removes all the d d m structure entry links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure entry links where structureId = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the number of matching d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure entry links.
	*
	* @return the number of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DDMStructureEntryLink remove(
		DDMStructureEntryLink ddmStructureEntryLink) throws SystemException;
}