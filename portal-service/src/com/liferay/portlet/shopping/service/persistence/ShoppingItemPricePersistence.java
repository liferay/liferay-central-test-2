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

import com.liferay.portlet.shopping.model.ShoppingItemPrice;

/**
 * <a href="ShoppingItemPricePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPricePersistenceImpl
 * @see       ShoppingItemPriceUtil
 * @generated
 */
public interface ShoppingItemPricePersistence extends BasePersistence<ShoppingItemPrice> {
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice);

	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> shoppingItemPrices);

	public com.liferay.portlet.shopping.model.ShoppingItemPrice create(
		long itemPriceId);

	public com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_First(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_Last(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException;

	public com.liferay.portlet.shopping.model.ShoppingItemPrice[] findByItemId_PrevAndNext(
		long itemPriceId, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}