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

import com.liferay.portal.model.Subscription;

/**
 * <a href="SubscriptionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SubscriptionPersistenceImpl
 * @see       SubscriptionUtil
 * @generated
 */
public interface SubscriptionPersistence extends BasePersistence<Subscription> {
	public void cacheResult(com.liferay.portal.model.Subscription subscription);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Subscription> subscriptions);

	public com.liferay.portal.model.Subscription create(long subscriptionId);

	public com.liferay.portal.model.Subscription remove(long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription updateImpl(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByPrimaryKey(
		long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription fetchByPrimaryKey(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription[] findByUserId_PrevAndNext(
		long subscriptionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByU_C_First(long userId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByU_C_Last(long userId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription[] findByU_C_PrevAndNext(
		long subscriptionId, long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription[] findByC_C_C_PrevAndNext(
		long subscriptionId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription findByC_U_C_C(long companyId,
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_C(long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_U_C_C(long companyId, long userId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_C(long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_U_C_C(long companyId, long userId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}