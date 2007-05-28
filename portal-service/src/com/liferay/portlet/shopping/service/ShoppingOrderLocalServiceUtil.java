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

package com.liferay.portlet.shopping.service;

/**
 * <a href="ShoppingOrderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.shopping.service.ShoppingOrderLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingOrderLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceFactory
 *
 */
public class ShoppingOrderLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static void completeOrder(java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail, boolean updateInventory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();
		shoppingOrderLocalService.completeOrder(number, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail,
			updateInventory);
	}

	public static void deleteOrder(long orderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();
		shoppingOrderLocalService.deleteOrder(orderId);
	}

	public static void deleteOrder(
		com.liferay.portlet.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();
		shoppingOrderLocalService.deleteOrder(order);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder getLatestOrder(
		long userId, long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.getLatestOrder(userId, groupId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		long orderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.getOrder(orderId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		java.lang.String number)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.getOrder(number);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder saveLatestOrder(
		com.liferay.portlet.shopping.model.ShoppingCart cart)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.saveLatestOrder(cart);
	}

	public static java.util.List search(long groupId, long companyId,
		long userId, java.lang.String number,
		java.lang.String billingFirstName, java.lang.String billingLastName,
		java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator, int begin,
		int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.search(groupId, companyId, userId,
			number, billingFirstName, billingLastName, billingEmailAddress,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			ppPaymentStatus, andOperator, begin, end);
	}

	public static int searchCount(long groupId, long companyId, long userId,
		java.lang.String number, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String ppPaymentStatus, boolean andOperator)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.searchCount(groupId, companyId,
			userId, number, billingFirstName, billingLastName,
			billingEmailAddress, shippingFirstName, shippingLastName,
			shippingEmailAddress, ppPaymentStatus, andOperator);
	}

	public static void sendEmail(long orderId, java.lang.String emailType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();
		shoppingOrderLocalService.sendEmail(orderId, emailType);
	}

	public static void sendEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order,
		java.lang.String emailType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();
		shoppingOrderLocalService.sendEmail(order, emailType);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder updateLatestOrder(
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
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.updateLatestOrder(userId, groupId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
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
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.updateOrder(orderId, billingFirstName,
			billingLastName, billingEmailAddress, billingCompany,
			billingStreet, billingCity, billingState, billingZip,
			billingCountry, billingPhone, shipToBilling, shippingFirstName,
			shippingLastName, shippingEmailAddress, shippingCompany,
			shippingStreet, shippingCity, shippingState, shippingZip,
			shippingCountry, shippingPhone, ccName, ccType, ccNumber,
			ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingOrderLocalService shoppingOrderLocalService = ShoppingOrderLocalServiceFactory.getService();

		return shoppingOrderLocalService.updateOrder(orderId, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}
}