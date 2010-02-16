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

package com.liferay.portlet.shopping.service;


/**
 * <a href="ShoppingOrderServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingOrderService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderService
 * @generated
 */
public class ShoppingOrderServiceWrapper implements ShoppingOrderService {
	public ShoppingOrderServiceWrapper(
		ShoppingOrderService shoppingOrderService) {
		_shoppingOrderService = shoppingOrderService;
	}

	public void completeOrder(long groupId, java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderService.completeOrder(groupId, number, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	public void deleteOrder(long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderService.deleteOrder(groupId, orderId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderService.getOrder(groupId, orderId);
	}

	public void sendEmail(long groupId, long orderId, java.lang.String emailType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_shoppingOrderService.sendEmail(groupId, orderId, emailType);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingOrderService.updateOrder(groupId, orderId, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, java.lang.String billingFirstName,
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
		return _shoppingOrderService.updateOrder(groupId, orderId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public ShoppingOrderService getWrappedShoppingOrderService() {
		return _shoppingOrderService;
	}

	private ShoppingOrderService _shoppingOrderService;
}