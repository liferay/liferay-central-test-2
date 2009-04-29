/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * <a href="ShoppingCartUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCartUtil {
	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart) {
		getPersistence().cacheResult(shoppingCart);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> shoppingCarts) {
		getPersistence().cacheResult(shoppingCarts);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart create(
		long cartId) {
		return getPersistence().create(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		long cartId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().remove(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(shoppingCart);
	}

	/**
	 * @deprecated Use <code>update(ShoppingCart shoppingCart, boolean merge)</code>.
	 */
	public static com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(shoppingCart);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingCart the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingCart is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(shoppingCart, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(shoppingCart, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		long cartId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByPrimaryKey(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByPrimaryKey(
		long cartId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(cartId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByGroupId_PrevAndNext(
		long cartId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_PrevAndNext(cartId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByUserId_PrevAndNext(
		long cartId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_PrevAndNext(cartId, userId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingCartPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ShoppingCartPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingCartPersistence _persistence;
}