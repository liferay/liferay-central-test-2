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

import com.liferay.portal.model.ResourcePermission;

/**
 * The persistence interface for the resource permission service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link ResourcePermissionUtil} to access the resource permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionPersistenceImpl
 * @see ResourcePermissionUtil
 * @generated
 */
public interface ResourcePermissionPersistence extends BasePersistence<ResourcePermission> {
	/**
	* Caches the resource permission in the entity cache if it is enabled.
	*
	* @param resourcePermission the resource permission to cache
	*/
	public void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission);

	/**
	* Caches the resource permissions in the entity cache if it is enabled.
	*
	* @param resourcePermissions the resource permissions to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions);

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId);

	/**
	* Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission to remove
	* @return the resource permission that was removed
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission to find
	* @return the resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission to find
	* @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role id to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the resource permissions where roleId = &#63; and scope = &#63;.
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the resource permissions where roleId = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the resource permissions where roleId = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByR_S_First(
		long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByR_S_Last(
		long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByR_S_PrevAndNext(
		long resourcePermissionId, long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role id to search with
	* @return the matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role id to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role id to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the resource permissions.
	*
	* @return the resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where roleId = &#63; from the database.
	*
	* @param roleId the role id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where roleId = &#63; and scope = &#63; from the database.
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByR_S(long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role id to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions where roleId = &#63; and scope = &#63;.
	*
	* @param roleId the role id to search with
	* @param scope the scope to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByR_S(long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S_P(long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* @param companyId the company id to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role id to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}