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

package com.liferay.portlet.shopping.service;


/**
 * <a href="ShoppingOrderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingOrderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderLocalService
 * @generated
 */
public class ShoppingOrderLocalServiceWrapper
	implements ShoppingOrderLocalService {
	public ShoppingOrderLocalServiceWrapper(
		ShoppingOrderLocalService shoppingOrderLocalService) {
		_shoppingOrderLocalService = shoppingOrderLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder addShoppingOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.addShoppingOrder(shoppingOrder);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder createShoppingOrder(
		long orderId) {
		return _shoppingOrderLocalService.createShoppingOrder(orderId);
	}

	public void deleteShoppingOrder(long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.deleteShoppingOrder(orderId);
	}

	public void deleteShoppingOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.deleteShoppingOrder(shoppingOrder);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getShoppingOrder(
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getShoppingOrder(orderId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> getShoppingOrders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getShoppingOrders(start, end);
	}

	public int getShoppingOrdersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getShoppingOrdersCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateShoppingOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.updateShoppingOrder(shoppingOrder);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateShoppingOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.updateShoppingOrder(shoppingOrder,
			merge);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder addLatestOrder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.addLatestOrder(userId, groupId);
	}

	public void completeOrder(java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail, boolean updateInventory)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.completeOrder(number, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail,
			updateInventory);
	}

	public void deleteOrder(long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.deleteOrder(orderId);
	}

	public void deleteOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.deleteOrder(order);
	}

	public void deleteOrders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.deleteOrders(groupId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getLatestOrder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getLatestOrder(userId, groupId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getOrder(orderId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getOrder(number);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getPayPalTxnIdOrder(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.getPayPalTxnIdOrder(ppTxnId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder saveLatestOrder(
		com.liferay.portlet.shopping.model.ShoppingCart cart)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.saveLatestOrder(cart);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> search(
		long groupId, long companyId, long userId, java.lang.String number,
		java.lang.String billingFirstName, java.lang.String billingLastName,
		java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.search(groupId, companyId, userId,
			number, billingFirstName, billingLastName, billingEmailAddress,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			ppPaymentStatus, andOperator, start, end);
	}

	public int searchCount(long groupId, long companyId, long userId,
		java.lang.String number, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.searchCount(groupId, companyId,
			userId, number, billingFirstName, billingLastName,
			billingEmailAddress, shippingFirstName, shippingLastName,
			shippingEmailAddress, ppPaymentStatus, andOperator);
	}

	public void sendEmail(long orderId, java.lang.String emailType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.sendEmail(orderId, emailType);
	}

	public void sendEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order,
		java.lang.String emailType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderLocalService.sendEmail(order, emailType);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateLatestOrder(
		long userId, long groupId, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String billingCompany, java.lang.String billingStreet,
		java.lang.String billingCity, java.lang.String billingState,
		java.lang.String billingZip, java.lang.String billingCountry,
		java.lang.String billingPhone, boolean shipToBilling,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String shippingCompany, java.lang.String shippingStreet,
		java.lang.String shippingCity, java.lang.String shippingState,
		java.lang.String shippingZip, java.lang.String shippingCountry,
		java.lang.String shippingPhone, java.lang.String ccName,
		java.lang.String ccType, java.lang.String ccNumber, int ccExpMonth,
		int ccExpYear, java.lang.String ccVerNumber, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.updateLatestOrder(userId, groupId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.updateOrder(orderId, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long orderId, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String billingCompany, java.lang.String billingStreet,
		java.lang.String billingCity, java.lang.String billingState,
		java.lang.String billingZip, java.lang.String billingCountry,
		java.lang.String billingPhone, boolean shipToBilling,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String shippingCompany, java.lang.String shippingStreet,
		java.lang.String shippingCity, java.lang.String shippingState,
		java.lang.String shippingZip, java.lang.String shippingCountry,
		java.lang.String shippingPhone, java.lang.String ccName,
		java.lang.String ccType, java.lang.String ccNumber, int ccExpMonth,
		int ccExpYear, java.lang.String ccVerNumber, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderLocalService.updateOrder(orderId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public ShoppingOrderLocalService getWrappedShoppingOrderLocalService() {
		return _shoppingOrderLocalService;
	}

	private ShoppingOrderLocalService _shoppingOrderLocalService;
}