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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserGroup;

/**
 * The persistence interface for the user group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupPersistenceImpl
 * @see UserGroupUtil
 * @generated
 */
public interface UserGroupPersistence extends BasePersistence<UserGroup> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupUtil} to access the user group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user group in the entity cache if it is enabled.
	*
	* @param userGroup the user group to cache
	*/
	public void cacheResult(com.liferay.portal.model.UserGroup userGroup);

	/**
	* Caches the user groups in the entity cache if it is enabled.
	*
	* @param userGroups the user groups to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Creates a new user group with the primary key. Does not add the user group to the database.
	*
	* @param userGroupId the primary key for the new user group
	* @return the new user group
	*/
	public com.liferay.portal.model.UserGroup create(long userGroupId);

	/**
	* Removes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupId the primary key of the user group to remove
	* @return the user group that was removed
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup remove(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup updateImpl(
		com.liferay.portal.model.UserGroup userGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param userGroupId the primary key of the user group to find
	* @return the user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByPrimaryKey(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userGroupId the primary key of the user group to find
	* @return the user group, or <code>null</code> if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup fetchByPrimaryKey(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user groups before and after the current user group in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup[] findByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the user groups before and after the current user group in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @return the matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByC_P_First(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByC_P_Last(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user groups before and after the current user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup[] findByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @return the matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the user groups before and after the current user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @return the matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user groups.
	*
	* @return the user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user groups where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user groups where companyId = &#63; and parentUserGroupId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user group where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user groups from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param parentUserGroupId the parent user group ID to search with
	* @return the number of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user groups where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user groups.
	*
	* @return the number of user groups
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets all the groups associated with the user group.
	*
	* @param pk the primary key of the user group to get the associated groups for
	* @return the groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the groups associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated groups for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets an ordered range of all the groups associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated groups for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of groups associated with the user group.
	*
	* @param pk the primary key of the user group to get the number of associated groups for
	* @return the number of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the group is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the user group has any groups associated with it.
	*
	* @param pk the primary key of the user group to check for associations with groups
	* @return <code>true</code> if the user group has any groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user group and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated groups from
	* @throws SystemException if a system exception occurred
	*/
	public void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param groupPKs the primary keys of the groups to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param groups the groups to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets all the teams associated with the user group.
	*
	* @param pk the primary key of the user group to get the associated teams for
	* @return the teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the teams associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated teams for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets an ordered range of all the teams associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated teams for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of teams associated with the user group.
	*
	* @param pk the primary key of the user group to get the number of associated teams for
	* @return the number of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public int getTeamsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the team is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the user group has any teams associated with it.
	*
	* @param pk the primary key of the user group to check for associations with teams
	* @return <code>true</code> if the user group has any teams associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public void addTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public void addTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public void addTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user group and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated teams from
	* @throws SystemException if a system exception occurred
	*/
	public void clearTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param teamPKs the primary keys of the teams to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param teams the teams to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets all the users associated with the user group.
	*
	* @param pk the primary key of the user group to get the associated users for
	* @return the users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the users associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated users for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @return the range of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets an ordered range of all the users associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user group to get the associated users for
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of users associated with the user group.
	*
	* @param pk the primary key of the user group to get the number of associated users for
	* @return the number of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the user is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Determines if the user group has any users associated with it.
	*
	* @param pk the primary key of the user group to check for associations with users
	* @return <code>true</code> if the user group has any users associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated users from
	* @throws SystemException if a system exception occurred
	*/
	public void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param userPKs the primary keys of the users to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to set the associations for
	* @param users the users to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public UserGroup remove(UserGroup userGroup) throws SystemException;
}