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

import com.liferay.portal.model.Branch;

/**
 * The persistence interface for the branch service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link BranchUtil} to access the branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BranchPersistenceImpl
 * @see BranchUtil
 * @generated
 */
public interface BranchPersistence extends BasePersistence<Branch> {
	/**
	* Caches the branch in the entity cache if it is enabled.
	*
	* @param branch the branch to cache
	*/
	public void cacheResult(com.liferay.portal.model.Branch branch);

	/**
	* Caches the branchs in the entity cache if it is enabled.
	*
	* @param branchs the branchs to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Branch> branchs);

	/**
	* Creates a new branch with the primary key. Does not add the branch to the database.
	*
	* @param branchId the primary key for the new branch
	* @return the new branch
	*/
	public com.liferay.portal.model.Branch create(long branchId);

	/**
	* Removes the branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param branchId the primary key of the branch to remove
	* @return the branch that was removed
	* @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch remove(long branchId)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Branch updateImpl(
		com.liferay.portal.model.Branch branch, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branch with the primary key or throws a {@link com.liferay.portal.NoSuchBranchException} if it could not be found.
	*
	* @param branchId the primary key of the branch to find
	* @return the branch
	* @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch findByPrimaryKey(long branchId)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param branchId the primary key of the branch to find
	* @return the branch, or <code>null</code> if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch fetchByPrimaryKey(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findByG(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of branchs to return
	* @param end the upper bound of the range of branchs to return (not inclusive)
	* @return the range of matching branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findByG(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of branchs to return
	* @param end the upper bound of the range of branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findByG(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching branch
	* @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch findByG_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching branch
	* @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch findByG_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branchs before and after the current branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the primary key of the current branch
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next branch
	* @throws com.liferay.portal.NoSuchBranchException if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch[] findByG_PrevAndNext(
		long branchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branch where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchBranchException} if it could not be found.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching branch
	* @throws com.liferay.portal.NoSuchBranchException if a matching branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching branch, or <code>null</code> if a matching branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch fetchByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching branch, or <code>null</code> if a matching branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the branchs.
	*
	* @return the branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of branchs to return
	* @param end the upper bound of the range of branchs to return (not inclusive)
	* @return the range of branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of branchs to return
	* @param end the upper bound of the range of branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the branchs where groupId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the branch where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.NoSuchBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the branchs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the branchs where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the number of matching branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the branchs.
	*
	* @return the number of branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}