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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.shopping.model.ShoppingItemPrice;

import java.util.List;

/**
 * <a href="ShoppingItemPriceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPricePersistence
 * @see       ShoppingItemPricePersistenceImpl
 * @generated
 */
public class ShoppingItemPriceUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingItemPrice remove(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		return getPersistence().remove(shoppingItemPrice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ShoppingItemPrice update(
		ShoppingItemPrice shoppingItemPrice, boolean merge)
		throws SystemException {
		return getPersistence().update(shoppingItemPrice, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		getPersistence().cacheResult(shoppingItemPrice);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> shoppingItemPrices) {
		getPersistence().cacheResult(shoppingItemPrices);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice create(
		long itemPriceId) {
		return getPersistence().create(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().remove(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingItemPrice, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByPrimaryKey(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(itemPriceId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_First(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByItemId_First(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_Last(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByItemId_Last(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice[] findByItemId_PrevAndNext(
		long itemPriceId, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence()
				   .findByItemId_PrevAndNext(itemPriceId, itemId, obc);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByItemId(itemId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByItemId(itemId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingItemPricePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingItemPricePersistence)PortalBeanLocatorUtil.locate(ShoppingItemPricePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ShoppingItemPricePersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingItemPricePersistence _persistence;
}