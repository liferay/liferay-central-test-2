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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.model.ShoppingOrder;

/**
 * <a href="ShoppingOrderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderPersistenceImpl
 * @see       ShoppingOrderUtil
 * @generated
 */
public interface ShoppingOrderPersistence extends BasePersistence<ShoppingOrder> {
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder);

	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> shoppingOrders);

	public com.liferay.portlet.shopping.model.ShoppingOrder create(long orderId);

	public com.liferay.portlet.shopping.model.ShoppingOrder remove(long orderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByPrimaryKey(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPrimaryKey(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder[] findByGroupId_PrevAndNext(
		long orderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByNumber(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByNumber(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByNumber(
		java.lang.String number, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByPPTxnId(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPPTxnId(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPPTxnId(
		java.lang.String ppTxnId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_First(
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_Last(
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder[] findByG_U_PPPS_PrevAndNext(
		long orderId, long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByNumber(java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public void removeByPPTxnId(java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public void removeByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByNumber(java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByPPTxnId(java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}