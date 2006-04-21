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

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingOrderLocalService;

import org.springframework.context.ApplicationContext;

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
	public static final String CLASS_NAME = ShoppingOrderLocalService.class.getName() +
		".transaction";

	public static ShoppingOrderLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingOrderLocalService)ctx.getBean(CLASS_NAME);
	}

	public void completeOrder(java.lang.String orderId,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail, boolean updateInventory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().completeOrder(orderId, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail, updateInventory);
	}

	public void sendOrderEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().sendOrderEmail(order);
	}

	public void sendOrderEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order, boolean override)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().sendOrderEmail(order, override);
	}

	public void sendShippingEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().sendShippingEmail(order);
	}

	public void sendShippingEmail(
		com.liferay.portlet.shopping.model.ShoppingOrder order, boolean override)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().sendShippingEmail(order, override);
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