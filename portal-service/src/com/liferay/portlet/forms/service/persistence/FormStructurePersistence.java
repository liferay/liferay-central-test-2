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

import com.liferay.portlet.forms.model.FormStructure;

/**
 * The persistence interface for the form structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructurePersistenceImpl
 * @see FormStructureUtil
 * @generated
 */
public interface FormStructurePersistence extends BasePersistence<FormStructure> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormStructureUtil} to access the form structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the form structure in the entity cache if it is enabled.
	*
	* @param formStructure the form structure to cache
	*/
	public void cacheResult(
		com.liferay.portlet.forms.model.FormStructure formStructure);

	/**
	* Caches the form structures in the entity cache if it is enabled.
	*
	* @param formStructures the form structures to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.forms.model.FormStructure> formStructures);

	/**
	* Creates a new form structure with the primary key. Does not add the form structure to the database.
	*
	* @param id the primary key for the new form structure
	* @return the new form structure
	*/
	public com.liferay.portlet.forms.model.FormStructure create(long id);

	/**
	* Removes the form structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the form structure to remove
	* @return the form structure that was removed
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure remove(long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	public com.liferay.portlet.forms.model.FormStructure updateImpl(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the form structure with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	*
	* @param id the primary key of the form structure to find
	* @return the form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structure with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the form structure to find
	* @return the form structure, or <code>null</code> if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the form structures where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the form structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the form structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first form structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the last form structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structures before and after the current form structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current form structure
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the form structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the form structures where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the form structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the form structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first form structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the last form structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structures before and after the current form structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current form structure
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Filters by the user's permissions and finds all the form structures where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching form structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the form structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of matching form structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the form structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching form structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the form structure where groupId = &#63; and formStructureId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param formStructureId the form structure ID to search with
	* @return the matching form structure
	* @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure findByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Finds the form structure where groupId = &#63; and formStructureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param formStructureId the form structure ID to search with
	* @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the form structure where groupId = &#63; and formStructureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param formStructureId the form structure ID to search with
	* @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the form structures.
	*
	* @return the form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the form structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the form structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the form structures where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the form structure where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Removes all the form structures where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the form structure where groupId = &#63; and formStructureId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param formStructureId the form structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F(long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureException;

	/**
	* Removes all the form structures from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the form structures where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the form structures where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the form structures where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the form structures where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching form structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the form structures where groupId = &#63; and formStructureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param formStructureId the form structure ID to search with
	* @return the number of matching form structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F(long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the form structures.
	*
	* @return the number of form structures
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public FormStructure remove(FormStructure formStructure)
		throws SystemException;
}