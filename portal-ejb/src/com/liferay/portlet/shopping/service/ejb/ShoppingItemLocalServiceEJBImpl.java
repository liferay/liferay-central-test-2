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

package com.liferay.portlet.shopping.service.ejb;

import com.liferay.portlet.shopping.service.ShoppingItemLocalService;
import com.liferay.portlet.shopping.service.ShoppingItemLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingItemLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingItemLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingItemLocalServiceUtil
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingItemLocalServiceEJB
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingItemLocalServiceHome
 * @see com.liferay.portlet.shopping.service.impl.ShoppingItemLocalServiceImpl
 *
 */
public class ShoppingItemLocalServiceEJBImpl implements ShoppingItemLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void addBookItems(long userId, java.lang.String categoryId,
		java.lang.String[] isbns)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().addBookItems(userId,
			categoryId, isbns);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		long userId, java.lang.String categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List itemFields, java.util.List itemPrices,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().addItem(userId,
			categoryId, sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		long userId, java.lang.String categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List itemFields, java.util.List itemPrices,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().addItem(userId,
			categoryId, sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		long userId, java.lang.String categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List itemFields, java.util.List itemPrices,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().addItem(userId,
			categoryId, sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addItemResources(java.lang.String itemId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().addItemResources(itemId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		com.liferay.portlet.shopping.model.ShoppingItem item,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().addItemResources(category,
			item, addCommunityPermissions, addGuestPermissions);
	}

	public void addItemResources(java.lang.String itemId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().addItemResources(itemId,
			communityPermissions, guestPermissions);
	}

	public void addItemResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		com.liferay.portlet.shopping.model.ShoppingItem item,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().addItemResources(category,
			item, communityPermissions, guestPermissions);
	}

	public void deleteItem(java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().deleteItem(itemId);
	}

	public void deleteItem(com.liferay.portlet.shopping.model.ShoppingItem item)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().deleteItem(item);
	}

	public void deleteItems(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingItemLocalServiceFactory.getTxImpl().deleteItems(categoryId);
	}

	public int getCategoriesItemsCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl()
											  .getCategoriesItemsCount(categoryIds);
	}

	public java.util.List getFeaturedItems(long groupId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getFeaturedItems(groupId,
			categoryId, numOfItems);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(
		java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItem(itemId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItem(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItem(companyId,
			sku);
	}

	public java.util.List getItems(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItems(categoryId);
	}

	public java.util.List getItems(java.lang.String categoryId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItems(categoryId,
			begin, end, obc);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		java.lang.String itemId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItemsPrevAndNext(itemId,
			obc);
	}

	public int getItemsCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getItemsCount(categoryId);
	}

	public java.util.List getSaleItems(long groupId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().getSaleItems(groupId,
			categoryId, numOfItems);
	}

	public java.util.List search(long groupId, java.lang.String[] categoryIds,
		java.lang.String keywords, int begin, int end)
		throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().search(groupId,
			categoryIds, keywords, begin, end);
	}

	public int searchCount(long groupId, java.lang.String[] categoryIds,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().searchCount(groupId,
			categoryIds, keywords);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateItem(
		long userId, java.lang.String itemId, java.lang.String categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile, java.util.List itemFields,
		java.util.List itemPrices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingItemLocalServiceFactory.getTxImpl().updateItem(userId,
			itemId, categoryId, sku, name, description, properties,
			fieldsQuantities, requiresShipping, stockQuantity, featured, sale,
			smallImage, smallImageURL, smallFile, mediumImage, mediumImageURL,
			mediumFile, largeImage, largeImageURL, largeFile, itemFields,
			itemPrices);
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