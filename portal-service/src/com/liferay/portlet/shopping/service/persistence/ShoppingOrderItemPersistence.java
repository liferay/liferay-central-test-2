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

import com.liferay.portlet.shopping.model.ShoppingOrderItem;

/**
 * <a href="ShoppingOrderItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderItemPersistenceImpl
 * @see       ShoppingOrderItemUtil
 * @generated
 */
public interface ShoppingOrderItemPersistence extends BasePersistence<ShoppingOrderItem> {
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem);

	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> shoppingOrderItems);

	public com.liferay.portlet.shopping.model.ShoppingOrderItem create(
		long orderItemId);

	public com.liferay.portlet.shopping.model.ShoppingOrderItem remove(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderItemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem findByPrimaryKey(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderItemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem fetchByPrimaryKey(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findByOrderId(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findByOrderId(
		long orderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findByOrderId(
		long orderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem findByOrderId_First(
		long orderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderItemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem findByOrderId_Last(
		long orderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderItemException;

	public com.liferay.portlet.shopping.model.ShoppingOrderItem[] findByOrderId_PrevAndNext(
		long orderItemId, long orderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderItemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByOrderId(long orderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByOrderId(long orderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}