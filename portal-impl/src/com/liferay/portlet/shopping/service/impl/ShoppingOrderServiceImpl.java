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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.base.ShoppingOrderServiceBaseImpl;
import com.liferay.portlet.shopping.service.permission.ShoppingOrderPermission;
import com.liferay.portlet.shopping.service.permission.ShoppingPermission;

/**
 * <a href="ShoppingOrderServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderServiceImpl extends ShoppingOrderServiceBaseImpl {

	public void completeOrder(
			long groupId, String number, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws PortalException, SystemException {

		ShoppingOrder order = shoppingOrderPersistence.findByNumber(number);

		ShoppingOrderPermission.check(
			getPermissionChecker(), groupId, order.getOrderId(),
			ActionKeys.UPDATE);

		shoppingOrderLocalService.completeOrder(
			number, ppTxnId, ppPaymentStatus, ppPaymentGross, ppReceiverEmail,
			ppPayerEmail, false);
	}

	public void deleteOrder(long groupId, long orderId)
		throws PortalException, SystemException {

		ShoppingOrderPermission.check(
			getPermissionChecker(), groupId, orderId, ActionKeys.DELETE);

		shoppingOrderLocalService.deleteOrder(orderId);
	}

	public ShoppingOrder getOrder(long groupId, long orderId)
		throws PortalException, SystemException {

		ShoppingOrder order = shoppingOrderLocalService.getOrder(orderId);

		if (order.getUserId() == getUserId()) {
			return order;
		}
		else {
			ShoppingPermission.check(
				getPermissionChecker(), groupId, ActionKeys.MANAGE_ORDERS);

			return order;
		}
	}

	public void sendEmail(long groupId, long orderId, String emailType)
		throws PortalException, SystemException {

		ShoppingOrderPermission.check(
			getPermissionChecker(), groupId, orderId, ActionKeys.UPDATE);

		shoppingOrderLocalService.sendEmail(orderId, emailType);
	}

	public ShoppingOrder updateOrder(
			long groupId, long orderId, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws PortalException, SystemException {

		ShoppingOrderPermission.check(
			getPermissionChecker(), groupId, orderId, ActionKeys.UPDATE);

		return shoppingOrderLocalService.updateOrder(
			orderId, ppTxnId, ppPaymentStatus, ppPaymentGross, ppReceiverEmail,
			ppPayerEmail);
	}

	public ShoppingOrder updateOrder(
			long groupId, long orderId, String billingFirstName,
			String billingLastName, String billingEmailAddress,
			String billingCompany, String billingStreet, String billingCity,
			String billingState, String billingZip, String billingCountry,
			String billingPhone, boolean shipToBilling,
			String shippingFirstName, String shippingLastName,
			String shippingEmailAddress, String shippingCompany,
			String shippingStreet, String shippingCity, String shippingState,
			String shippingZip, String shippingCountry, String shippingPhone,
			String ccName, String ccType, String ccNumber, int ccExpMonth,
			int ccExpYear, String ccVerNumber, String comments)
		throws PortalException, SystemException {

		ShoppingOrderPermission.check(
			getPermissionChecker(), groupId, orderId, ActionKeys.UPDATE);

		return shoppingOrderLocalService.updateOrder(
			orderId, billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

}