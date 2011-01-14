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

package com.liferay.portlet.deletion.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.deletion.model.DeletionEntry;

/**
 * The persistence interface for the deletion entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DeletionEntryPersistenceImpl
 * @see DeletionEntryUtil
 * @generated
 */
public interface DeletionEntryPersistence extends BasePersistence<DeletionEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DeletionEntryUtil} to access the deletion entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	public DeletionEntry remove(DeletionEntry deletionEntry)
		throws SystemException;

	/**
	* Caches the deletion entry in the entity cache if it is enabled.
	*
	* @param deletionEntry the deletion entry to cache
	*/
	public void cacheResult(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry);

	/**
	* Caches the deletion entries in the entity cache if it is enabled.
	*
	* @param deletionEntries the deletion entries to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> deletionEntries);

	/**
	* Creates a new deletion entry with the primary key. Does not add the deletion entry to the database.
	*
	* @param entryId the primary key for the new deletion entry
	* @return the new deletion entry
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry create(long entryId);

	/**
	* Removes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the deletion entry to remove
	* @return the deletion entry that was removed
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry remove(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	public com.liferay.portlet.deletion.model.DeletionEntry updateImpl(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the deletion entry with the primary key or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the deletion entry to find
	* @return the deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the deletion entry to find
	* @return the deletion entry, or <code>null</code> if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the deletion entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds all the deletion entries where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_PrevAndNext(
		long entryId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_First(
		long groupId, java.util.Date createDate, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_Last(
		long groupId, java.util.Date createDate, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_C_PrevAndNext(
		long entryId, long groupId, java.util.Date createDate,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_P_First(
		long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_P_Last(
		long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_P_PrevAndNext(
		long entryId, long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_P_First(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_P_Last(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_C_P_PrevAndNext(
		long entryId, long groupId, java.util.Date createDate,
		long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Finds all the deletion entries.
	*
	* @return the deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the deletion entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the deletion entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the deletion entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException;

	/**
	* Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_C(long groupId, java.util.Date createDate,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_P(long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_C_P(long groupId, java.util.Date createDate,
		long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the deletion entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C(long groupId, java.util.Date createDate,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_P(long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C_P(long groupId, java.util.Date createDate,
		long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the deletion entries.
	*
	* @return the number of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}