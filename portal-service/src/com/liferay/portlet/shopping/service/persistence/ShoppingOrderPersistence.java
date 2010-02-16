/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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