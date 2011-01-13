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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLFileRank;

/**
 * The persistence interface for the d l file rank service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankPersistenceImpl
 * @see DLFileRankUtil
 * @generated
 */
public interface DLFileRankPersistence extends BasePersistence<DLFileRank> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileRankUtil} to access the d l file rank persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	public DLFileRank remove(DLFileRank dlFileRank) throws SystemException;

	/**
	* Caches the d l file rank in the entity cache if it is enabled.
	*
	* @param dlFileRank the d l file rank to cache
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank);

	/**
	* Caches the d l file ranks in the entity cache if it is enabled.
	*
	* @param dlFileRanks the d l file ranks to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dlFileRanks);

	/**
	* Creates a new d l file rank with the primary key. Does not add the d l file rank to the database.
	*
	* @param fileRankId the primary key for the new d l file rank
	* @return the new d l file rank
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank create(
		long fileRankId);

	/**
	* Removes the d l file rank with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileRankId the primary key of the d l file rank to remove
	* @return the d l file rank that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	public com.liferay.portlet.documentlibrary.model.DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file rank with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	*
	* @param fileRankId the primary key of the d l file rank to find
	* @return the d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file rank with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileRankId the primary key of the d l file rank to find
	* @return the d l file rank, or <code>null</code> if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank fetchByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file ranks where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file ranks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @return the range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file ranks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the last d l file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file ranks before and after the current d l file rank in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current d l file rank
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		long fileRankId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds all the d l file ranks where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @return the matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file ranks where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @return the range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file ranks where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the last d l file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file ranks before and after the current d l file rank in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current d l file rank
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank[] findByFileEntryId_PrevAndNext(
		long fileRankId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds all the d l file ranks where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file ranks where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @return the range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file ranks where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the last d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file ranks before and after the current d l file rank in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileRankId the primary key of the current d l file rank
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a d l file rank with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank[] findByG_U_PrevAndNext(
		long fileRankId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileRankException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param fileEntryId the file entry ID to search with
	* @return the matching d l file rank
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileRankException if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank findByC_U_F(
		long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Finds the d l file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param fileEntryId the file entry ID to search with
	* @return the matching d l file rank, or <code>null</code> if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param fileEntryId the file entry ID to search with
	* @return the matching d l file rank, or <code>null</code> if a matching d l file rank could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F(
		long companyId, long userId, long fileEntryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file ranks.
	*
	* @return the d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file ranks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @return the range of d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file ranks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file ranks to return
	* @param end the upper bound of the range of d l file ranks to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file ranks where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file ranks where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file ranks where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d l file rank where companyId = &#63; and userId = &#63; and fileEntryId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param fileEntryId the file entry ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_U_F(long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException;

	/**
	* Removes all the d l file ranks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file ranks where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file ranks where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @return the number of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file ranks where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the number of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file ranks where companyId = &#63; and userId = &#63; and fileEntryId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param fileEntryId the file entry ID to search with
	* @return the number of matching d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_U_F(long companyId, long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file ranks.
	*
	* @return the number of d l file ranks
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}