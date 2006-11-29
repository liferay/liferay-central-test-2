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

import com.liferay.portlet.shopping.service.ShoppingOrderLocalService;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingOrderLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderLocalServiceEJBImpl
	implements ShoppingOrderLocalService, SessionBean {
	public void completeOrder(java.lang.String orderId,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail, boolean updateInventory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalServiceFactory.getTxImpl().completeOrder(orderId,
			ppTxnId, ppPaymentStatus, ppPaymentGross, ppReceiverEmail,
			ppPayerEmail, updateInventory);
	}

	public void deleteOrder(java.lang.String orderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalServiceFactory.getTxImpl().deleteOrder(orderId);
	}

	public void deleteOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalServiceFactory.getTxImpl().deleteOrder(order);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getLatestOrder(
		java.lang.String userId, java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().getLatestOrder(userId,
			groupId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		java.lang.String orderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().getOrder(orderId);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder saveLatestOrder(
		com.liferay.portlet.shopping.model.ShoppingCart cart)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().saveLatestOrder(cart);
	}

	public java.util.List search(java.lang.String orderId,
		java.lang.String groupId, java.lang.String companyId,
		java.lang.String userId, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator, int begin,
		int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().search(orderId,
			groupId, companyId, userId, billingFirstName, billingLastName,
			billingEmailAddress, shippingFirstName, shippingLastName,
			shippingEmailAddress, ppPaymentStatus, andOperator, begin, end);
	}

	public int searchCount(java.lang.String orderId, java.lang.String groupId,
		java.lang.String companyId, java.lang.String userId,
		java.lang.String billingFirstName, java.lang.String billingLastName,
		java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().searchCount(orderId,
			groupId, companyId, userId, billingFirstName, billingLastName,
			billingEmailAddress, shippingFirstName, shippingLastName,
			shippingEmailAddress, ppPaymentStatus, andOperator);
	}

	public void sendEmail(java.lang.String orderId, java.lang.String emailType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalServiceFactory.getTxImpl().sendEmail(orderId,
			emailType);
	}

	public void sendEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order,
		java.lang.String emailType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalServiceFactory.getTxImpl().sendEmail(order, emailType);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateLatestOrder(
		java.lang.String userId, java.lang.String groupId,
		java.lang.String billingFirstName, java.lang.String billingLastName,
		java.lang.String billingEmailAddress, java.lang.String billingCompany,
		java.lang.String billingStreet, java.lang.String billingCity,
		java.lang.String billingState, java.lang.String billingZip,
		java.lang.String billingCountry, java.lang.String billingPhone,
		boolean shipToBilling, java.lang.String shippingFirstName,
		java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String shippingCompany, java.lang.String shippingStreet,
		java.lang.String shippingCity, java.lang.String shippingState,
		java.lang.String shippingZip, java.lang.String shippingCountry,
		java.lang.String shippingPhone, java.lang.String ccName,
		java.lang.String ccType, java.lang.String ccNumber, int ccExpMonth,
		int ccExpYear, java.lang.String ccVerNumber, java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().updateLatestOrder(userId,
			groupId, billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		java.lang.String orderId, java.lang.String billingFirstName,
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
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().updateOrder(orderId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		java.lang.String orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingOrderLocalServiceFactory.getTxImpl().updateOrder(orderId,
			ppTxnId, ppPaymentStatus, ppPaymentGross, ppReceiverEmail,
			ppPayerEmail);
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