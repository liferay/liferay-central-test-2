/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingItemService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingItemServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemServiceEJBImpl implements ShoppingItemService,
	SessionBean {
	public static final String CLASS_NAME = ShoppingItemService.class.getName() +
		".transaction";

	public static ShoppingItemService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingItemService)ctx.getBean(CLASS_NAME);
	}

	public void addBookItems(java.lang.String categoryId,
		java.lang.String[] isbnArray)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().addBookItems(categoryId, isbnArray);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem addItem(
		java.lang.String categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String supplierUserId,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile, java.util.List itemFields,
		java.util.List itemPrices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addItem(categoryId, sku, name, description,
			properties, supplierUserId, fieldsQuantities, requiresShipping,
			stockQuantity, featured, sale, smallImage, smallImageURL,
			smallFile, mediumImage, mediumImageURL, mediumFile, largeImage,
			largeImageURL, largeFile, itemFields, itemPrices);
	}

	public void deleteItem(java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteItem(itemId);
	}

	public void deleteItem(com.liferay.portlet.shopping.model.ShoppingItem item)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteItem(item);
	}

	public java.util.List getFeaturedItems(java.lang.String companyId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getFeaturedItems(companyId, categoryId, numOfItems);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItemById(
		java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItemById(itemId);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem getItemBySKU(
		java.lang.String companyId, java.lang.String sku)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItemBySKU(companyId, sku);
	}

	public java.util.List getItems(java.lang.String companyId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItems(companyId);
	}

	public java.util.List getItems(java.lang.String companyId,
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItems(companyId, categoryId);
	}

	public java.util.List getItems(java.lang.String companyId,
		java.lang.String categoryId, int begin, int end,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItems(companyId, categoryId, begin, end, prefs);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		java.lang.String itemId, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItemsPrevAndNext(itemId, prefs);
	}

	public int getItemsSize(java.lang.String companyId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItemsSize(companyId);
	}

	public int getItemsSize(java.lang.String companyId,
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getItemsSize(companyId, categoryId);
	}

	public java.util.List getSaleItems(java.lang.String companyId,
		java.lang.String categoryId, int numOfItems)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getSaleItems(companyId, categoryId, numOfItems);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String[] categoryIds, java.lang.String keywords, int begin,
		int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().search(companyId, categoryIds, keywords, begin, end);
	}

	public int searchSize(java.lang.String companyId,
		java.lang.String[] categoryIds, java.lang.String keywords)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().searchSize(companyId, categoryIds, keywords);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem updateItem(
		java.lang.String itemId, java.lang.String categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String supplierUserId, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeFile,
		java.util.List itemFields, java.util.List itemPrices)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateItem(itemId, categoryId, sku, name,
			description, properties, supplierUserId, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallFile, mediumImage, mediumImageURL, mediumFile,
			largeImage, largeImageURL, largeFile, itemFields, itemPrices);
	}

	public boolean hasAdmin(java.lang.String itemId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().hasAdmin(itemId);
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