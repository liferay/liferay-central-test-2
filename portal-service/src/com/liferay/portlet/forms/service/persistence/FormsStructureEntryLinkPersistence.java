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

package com.liferay.portlet.forms.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.forms.model.FormsStructureEntryLink;

/**
 * The persistence interface for the forms structure entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLinkPersistenceImpl
 * @see FormsStructureEntryLinkUtil
 * @generated
 */
public interface FormsStructureEntryLinkPersistence extends BasePersistence<FormsStructureEntryLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormsStructureEntryLinkUtil} to access the forms structure entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the forms structure entry link in the entity cache if it is enabled.
	*
	* @param formsStructureEntryLink the forms structure entry link to cache
	*/
	public void cacheResult(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink);

	/**
	* Caches the forms structure entry links in the entity cache if it is enabled.
	*
	* @param formsStructureEntryLinks the forms structure entry links to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> formsStructureEntryLinks);

	/**
	* Creates a new forms structure entry link with the primary key. Does not add the forms structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new forms structure entry link
	* @return the new forms structure entry link
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink create(
		long structureEntryLinkId);

	/**
	* Removes the forms structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to remove
	* @return the forms structure entry link that was removed
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink remove(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	public com.liferay.portlet.forms.model.FormsStructureEntryLink updateImpl(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the forms structure entry link with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to find
	* @return the forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink findByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Finds the forms structure entry link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to find
	* @return the forms structure entry link, or <code>null</code> if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Finds the last forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Finds the forms structure entry links before and after the current forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current forms structure entry link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink[] findByStructureId_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Filters by the user's permissions and finds all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching forms structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> filterFindByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of matching forms structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> filterFindByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching forms structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> filterFindByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the forms structure entry links before and after the current forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current forms structure entry link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink[] filterFindByStructureId_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink findByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the forms structure entry links.
	*
	* @return the forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the forms structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the forms structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the forms structure entry links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException;

	/**
	* Removes all the forms structure entry links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching forms structure entry links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the forms structure entry links where structureId = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the number of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the forms structure entry links.
	*
	* @return the number of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public FormsStructureEntryLink remove(
		FormsStructureEntryLink formsStructureEntryLink)
		throws SystemException;
}