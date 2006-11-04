/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingItemLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingItemLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemLocalServiceEJBImpl implements ShoppingItemLocalService,
	SessionBean {
	public static final String CLASS_NAME = ShoppingItemLocalService.class.getName() +
		".transaction";

	public static ShoppingItemLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingItemLocalService)ctx.getBean(CLASS_NAME);
	}

	public void addBookItems(java.lang.String userId,
		java.lang.String categoryId, java.lang.String[] isbns)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addBookItems(userId, categoryId, isbns);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile, java.util.List itemFields,
		java.util.List itemPrices, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addItem(userId, categoryId, sku, name, description,
			properties, fieldsQuantities, requiresShipping, stockQuantity,
			featured, sale, smallImage, smallImageURL, smallFile, mediumImage,
			mediumImageURL, mediumFile, largeImage, largeImageURL, largeFile,
			itemFields, itemPrices, addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(java.lang.String itemId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addItemResources(itemId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addItemResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		com.liferay.portlet.shopping.model.ShoppingItem item,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addItemResources(category, item, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteItem(java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteItem(itemId);
	}

	public void deleteItem(com.liferay.portlet.shopping.model.ShoppingItem item)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteItem(item);
	}

	public void deleteItems(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteItems(categoryId);
	}

	public int getCategoriesItemsCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesItemsCount(categoryIds);
	}

	public java.util.List getFeaturedItems(java.lang.String groupId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException {
		return getService().getFeaturedItems(groupId, categoryId, numOfItems);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(
		java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getItem(itemId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getItem(companyId, sku);
	}

	public java.util.List getItems(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getService().getItems(categoryId);
	}

	public java.util.List getItems(java.lang.String categoryId, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getItems(categoryId, begin, end, obc);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		java.lang.String itemId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getItemsPrevAndNext(itemId, obc);
	}

	public int getItemsCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getService().getItemsCount(categoryId);
	}

	public java.util.List getSaleItems(java.lang.String groupId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException {
		return getService().getSaleItems(groupId, categoryId, numOfItems);
	}

	public java.util.List search(java.lang.String groupId,
		java.lang.String[] categoryIds, java.lang.String keywords, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().search(groupId, categoryIds, keywords, begin, end);
	}

	public int searchCount(java.lang.String groupId,
		java.lang.String[] categoryIds, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		return getService().searchCount(groupId, categoryIds, keywords);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateItem(
		java.lang.String userId, java.lang.String itemId,
		java.lang.String categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List itemFields, java.util.List itemPrices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateItem(userId, itemId, categoryId, sku, name,
			description, properties, fieldsQuantities, requiresShipping,
			stockQuantity, featured, sale, smallImage, smallImageURL,
			smallFile, mediumImage, mediumImageURL, mediumFile, largeImage,
			largeImageURL, largeFile, itemFields, itemPrices);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}