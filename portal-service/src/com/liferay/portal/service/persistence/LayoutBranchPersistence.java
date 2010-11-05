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

import com.liferay.portal.model.LayoutBranch;

/**
 * The persistence interface for the layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchPersistenceImpl
 * @see LayoutBranchUtil
 * @generated
 */
public interface LayoutBranchPersistence extends BasePersistence<LayoutBranch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutBranchUtil} to access the layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the layout branch in the entity cache if it is enabled.
	*
	* @param layoutBranch the layout branch to cache
	*/
	public void cacheResult(com.liferay.portal.model.LayoutBranch layoutBranch);

	/**
	* Caches the layout branchs in the entity cache if it is enabled.
	*
	* @param layoutBranchs the layout branchs to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutBranch> layoutBranchs);

	/**
	* Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	*
	* @param layoutBranchId the primary key for the new layout branch
	* @return the new layout branch
	*/
	public com.liferay.portal.model.LayoutBranch create(long layoutBranchId);

	/**
	* Removes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranchId the primary key of the layout branch to remove
	* @return the layout branch that was removed
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch remove(long layoutBranchId)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutBranch updateImpl(
		com.liferay.portal.model.LayoutBranch layoutBranch, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	*
	* @param layoutBranchId the primary key of the layout branch to find
	* @return the layout branch
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch findByPrimaryKey(
		long layoutBranchId)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutBranchId the primary key of the layout branch to find
	* @return the layout branch, or <code>null</code> if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch fetchByPrimaryKey(
		long layoutBranchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @return the range of matching layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout branch
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout branch
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branchs before and after the current layout branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutBranchId the primary key of the current layout branch
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout branch
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a layout branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch[] findByGroupId_PrevAndNext(
		long layoutBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the layout branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching layout branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @return the range of matching layout branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the layout branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branch where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutBranchException} if it could not be found.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching layout branch
	* @throws com.liferay.portal.NoSuchLayoutBranchException if a matching layout branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch fetchByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout branch where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the matching layout branch, or <code>null</code> if a matching layout branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutBranch fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout branchs.
	*
	* @return the layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @return the range of layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs to return
	* @param end the upper bound of the range of layout branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutBranch> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout branchs where groupId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout branch where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutBranchException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout branchs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the layout branchs where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching layout branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout branchs where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group id to search with
	* @param name the name to search with
	* @return the number of matching layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout branchs.
	*
	* @return the number of layout branchs
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}