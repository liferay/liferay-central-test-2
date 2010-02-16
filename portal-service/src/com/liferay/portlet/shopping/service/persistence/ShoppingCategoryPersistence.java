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

import com.liferay.portlet.shopping.model.ShoppingCategory;

/**
 * <a href="ShoppingCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryPersistenceImpl
 * @see       ShoppingCategoryUtil
 * @generated
 */
public interface ShoppingCategoryPersistence extends BasePersistence<ShoppingCategory> {
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory);

	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> shoppingCategories);

	public com.liferay.portlet.shopping.model.ShoppingCategory create(
		long categoryId);

	public com.liferay.portlet.shopping.model.ShoppingCategory remove(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public com.liferay.portlet.shopping.model.ShoppingCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}