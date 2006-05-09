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

import com.liferay.portlet.shopping.service.spring.ShoppingCartLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCartLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartLocalServiceEJBImpl implements ShoppingCartLocalService,
	SessionBean {
	public static final String CLASS_NAME = ShoppingCartLocalService.class.getName() +
		".transaction";

	public static ShoppingCartLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingCartLocalService)ctx.getBean(CLASS_NAME);
	}

	public void deleteGroupCarts(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteGroupCarts(groupId);
	}

	public void deleteUserCarts(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getService().deleteUserCarts(userId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getCart(
		java.lang.String cartId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getCart(cartId);
	}

	public java.util.Map getItems(java.lang.String groupId,
		java.lang.String itemIds) throws com.liferay.portal.SystemException {
		return getService().getItems(groupId, itemIds);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		java.lang.String userId, java.lang.String groupId,
		java.lang.String cartId, java.lang.String itemIds,
		java.lang.String couponIds, int altShipping, boolean insure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateCart(userId, groupId, cartId, itemIds,
			couponIds, altShipping, insure);
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