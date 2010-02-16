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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.shopping.model.ShoppingItemField;

import java.util.List;

/**
 * <a href="ShoppingItemFieldUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemFieldPersistence
 * @see       ShoppingItemFieldPersistenceImpl
 * @generated
 */
public class ShoppingItemFieldUtil {
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
	public static ShoppingItemField remove(ShoppingItemField shoppingItemField)
		throws SystemException {
		return getPersistence().remove(shoppingItemField);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ShoppingItemField update(
		ShoppingItemField shoppingItemField, boolean merge)
		throws SystemException {
		return getPersistence().update(shoppingItemField, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField) {
		getPersistence().cacheResult(shoppingItemField);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> shoppingItemFields) {
		getPersistence().cacheResult(shoppingItemFields);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField create(
		long itemFieldId) {
		return getPersistence().create(itemFieldId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField remove(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemFieldException {
		return getPersistence().remove(itemFieldId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingItemField, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField findByPrimaryKey(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemFieldException {
		return getPersistence().findByPrimaryKey(itemFieldId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField fetchByPrimaryKey(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(itemFieldId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findByItemId(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findByItemId(
		long itemId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findByItemId(
		long itemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField findByItemId_First(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemFieldException {
		return getPersistence().findByItemId_First(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField findByItemId_Last(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemFieldException {
		return getPersistence().findByItemId_Last(itemId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemField[] findByItemId_PrevAndNext(
		long itemFieldId, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemFieldException {
		return getPersistence()
				   .findByItemId_PrevAndNext(itemFieldId, itemId, obc);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> findAll(
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

	public static ShoppingItemFieldPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingItemFieldPersistence)PortalBeanLocatorUtil.locate(ShoppingItemFieldPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ShoppingItemFieldPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingItemFieldPersistence _persistence;
}