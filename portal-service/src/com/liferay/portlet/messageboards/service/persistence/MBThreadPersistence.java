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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBThread;

/**
 * The persistence interface for the message boards thread service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadPersistenceImpl
 * @see MBThreadUtil
 * @generated
 */
public interface MBThreadPersistence extends BasePersistence<MBThread> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBThreadUtil} to access the message boards thread persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards thread in the entity cache if it is enabled.
	*
	* @param mbThread the message boards thread to cache
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBThread mbThread);

	/**
	* Caches the message boards threads in the entity cache if it is enabled.
	*
	* @param mbThreads the message boards threads to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBThread> mbThreads);

	/**
	* Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	*
	* @param threadId the primary key for the new message boards thread
	* @return the new message boards thread
	*/
	public com.liferay.portlet.messageboards.model.MBThread create(
		long threadId);

	/**
	* Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadId the primary key of the message boards thread to remove
	* @return the message boards thread that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread remove(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	public com.liferay.portlet.messageboards.model.MBThread updateImpl(
		com.liferay.portlet.messageboards.model.MBThread mbThread, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the message boards thread with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadException} if it could not be found.
	*
	* @param threadId the primary key of the message boards thread to find
	* @return the message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param threadId the primary key of the message boards thread to find
	* @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread fetchByPrimaryKey(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the message boards threads where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByGroupId_PrevAndNext(
		long threadId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_C_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_NotC_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_NotC_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_NotC_PrevAndNext(
		long threadId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_S_PrevAndNext(
		long threadId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByC_P(
		long categoryId, double priority, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByC_P_First(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByC_P_Last(
		long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByC_P_PrevAndNext(
		long threadId, long categoryId, double priority,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_L(
		long groupId, long categoryId, java.util.Date lastPostDate, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_L_First(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_L_Last(
		long groupId, long categoryId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_C_L_PrevAndNext(
		long threadId, long groupId, long categoryId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_C_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param status the status to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @return the matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_NotC_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread findByG_NotC_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param threadId the primary key of the current message boards thread
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards thread
	* @throws com.liferay.portlet.messageboards.NoSuchThreadException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread[] findByG_NotC_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchThreadException;

	/**
	* Finds all the message boards threads.
	*
	* @return the message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the message boards threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @return the range of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the message boards threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards threads to return
	* @param end the upper bound of the range of message boards threads to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_NotC(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where categoryId = &#63; and priority = &#63; from the database.
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_NotC_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards threads from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_NotC(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where categoryId = &#63; and priority = &#63;.
	*
	* @param categoryId the category ID to search with
	* @param priority the priority to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param lastPostDate the last post date to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_L(long groupId, long categoryId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryIds the category IDs to search with
	* @param status the status to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_S(long groupId, long[] categoryIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param categoryId the category ID to search with
	* @param status the status to search with
	* @return the number of matching message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_NotC_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the message boards threads.
	*
	* @return the number of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public MBThread remove(MBThread mbThread) throws SystemException;
}