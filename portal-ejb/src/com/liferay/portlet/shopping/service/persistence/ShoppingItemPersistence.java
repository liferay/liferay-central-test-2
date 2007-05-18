/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="ShoppingItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ShoppingItemPersistence {
	public com.liferay.portlet.shopping.model.ShoppingItem create(
		java.lang.String itemId);

	public com.liferay.portlet.shopping.model.ShoppingItem remove(
		java.lang.String itemId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem remove(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem update(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		java.lang.String itemId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		java.lang.String itemId) throws com.liferay.portal.SystemException;

	public java.util.List findByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_First(
		java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByCategoryId_Last(
		java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem[] findByCategoryId_PrevAndNext(
		java.lang.String itemId, java.lang.String categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findAll() throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException;

	public void removeByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException;

	public int countByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List getShoppingItemPrices(java.lang.String pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public java.util.List getShoppingItemPrices(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public java.util.List getShoppingItemPrices(java.lang.String pk, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchItemException;

	public int getShoppingItemPricesSize(java.lang.String pk)
		throws com.liferay.portal.SystemException;

	public boolean containsShoppingItemPrice(java.lang.String pk,
		java.lang.String shoppingItemPricePK)
		throws com.liferay.portal.SystemException;

	public boolean containsShoppingItemPrices(java.lang.String pk)
		throws com.liferay.portal.SystemException;
}