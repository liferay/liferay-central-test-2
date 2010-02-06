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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.shopping.model.ShoppingItem;

import java.util.List;

/**
 * <a href="ShoppingItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPersistence
 * @see       ShoppingItemPersistenceImpl
 * @generated
 */
public class ShoppingItemUtil {
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
	public static ShoppingItem remove(ShoppingItem shoppingItem)
		throws SystemException {
		return getPersistence().remove(shoppingItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ShoppingItem update(ShoppingItem shoppingItem, boolean merge)
		throws SystemException {
		return getPersistence().update(shoppingItem, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem) {
		getPersistence().cacheResult(shoppingItem);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> shoppingItems) {
		getPersistence().cacheResult(shoppingItems);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem create(
		long itemId) {
		return getPersistence().create(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().remove(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(shoppingItem, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		long itemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByPrimaryKey(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		long itemId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByMediumImageId(mediumImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByMediumImageId(mediumImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByMediumImageId(mediumImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByLargeImageId(largeImageId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByG_C_First(groupId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByG_C_Last(groupId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByG_C_PrevAndNext(
		long itemId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(itemId, groupId, categoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByC_S(companyId, sku);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_S(companyId, sku);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_S(companyId, sku, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	public static void removeByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByMediumImageId(mediumImageId);
	}

	public static void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByLargeImageId(largeImageId);
	}

	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	public static void removeByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		getPersistence().removeByC_S(companyId, sku);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	public static int countByMediumImageId(long mediumImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByMediumImageId(mediumImageId);
	}

	public static int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLargeImageId(largeImageId);
	}

	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	public static int countByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, sku);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPrices(pk, start, end, obc);
	}

	public static int getShoppingItemPricesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getShoppingItemPricesSize(pk);
	}

	public static boolean containsShoppingItemPrice(long pk,
		long shoppingItemPricePK) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .containsShoppingItemPrice(pk, shoppingItemPricePK);
	}

	public static boolean containsShoppingItemPrices(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsShoppingItemPrices(pk);
	}

	public static ShoppingItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingItemPersistence)PortalBeanLocatorUtil.locate(ShoppingItemPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ShoppingItemPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingItemPersistence _persistence;
}