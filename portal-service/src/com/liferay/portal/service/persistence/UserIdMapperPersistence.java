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

import com.liferay.portal.model.UserIdMapper;

/**
 * The persistence interface for the user id mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperPersistenceImpl
 * @see UserIdMapperUtil
 * @generated
 */
public interface UserIdMapperPersistence extends BasePersistence<UserIdMapper> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserIdMapperUtil} to access the user id mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user id mapper in the entity cache if it is enabled.
	*
	* @param userIdMapper the user id mapper to cache
	*/
	public void cacheResult(com.liferay.portal.model.UserIdMapper userIdMapper);

	/**
	* Caches the user id mappers in the entity cache if it is enabled.
	*
	* @param userIdMappers the user id mappers to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserIdMapper> userIdMappers);

	/**
	* Creates a new user id mapper with the primary key. Does not add the user id mapper to the database.
	*
	* @param userIdMapperId the primary key for the new user id mapper
	* @return the new user id mapper
	*/
	public com.liferay.portal.model.UserIdMapper create(long userIdMapperId);

	/**
	* Removes the user id mapper with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapperId the primary key of the user id mapper to remove
	* @return the user id mapper that was removed
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper remove(long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper with the primary key or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userIdMapperId the primary key of the user id mapper to find
	* @return the user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userIdMapperId the primary key of the user id mapper to find
	* @return the user id mapper, or <code>null</code> if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user id mappers where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user id mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of user id mappers to return
	* @param end the upper bound of the range of user id mappers to return (not inclusive)
	* @return the range of matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user id mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of user id mappers to return
	* @param end the upper bound of the range of user id mappers to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user id mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user id mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mappers before and after the current user id mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userIdMapperId the primary key of the current user id mapper
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper[] findByUserId_PrevAndNext(
		long userIdMapperId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where userId = &#63; and type = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userId the user ID to search with
	* @param type the type to search with
	* @return the matching user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID to search with
	* @param type the type to search with
	* @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID to search with
	* @param type the type to search with
	* @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where type = &#63; and externalUserId = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param type the type to search with
	* @param externalUserId the external user ID to search with
	* @return the matching user id mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param type the type to search with
	* @param externalUserId the external user ID to search with
	* @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user id mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param type the type to search with
	* @param externalUserId the external user ID to search with
	* @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user id mappers.
	*
	* @return the user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user id mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user id mappers to return
	* @param end the upper bound of the range of user id mappers to return (not inclusive)
	* @return the range of user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user id mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user id mappers to return
	* @param end the upper bound of the range of user id mappers to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user id mappers where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user id mapper where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @param type the type to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user id mapper where type = &#63; and externalUserId = &#63; from the database.
	*
	* @param type the type to search with
	* @param externalUserId the external user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_E(java.lang.String type,
		java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user id mappers from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user id mappers where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user id mappers where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID to search with
	* @param type the type to search with
	* @return the number of matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user id mappers where type = &#63; and externalUserId = &#63;.
	*
	* @param type the type to search with
	* @param externalUserId the external user ID to search with
	* @return the number of matching user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_E(java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user id mappers.
	*
	* @return the number of user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}