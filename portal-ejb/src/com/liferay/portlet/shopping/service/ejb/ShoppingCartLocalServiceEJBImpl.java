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

import com.liferay.portlet.shopping.service.ShoppingCartLocalService;
import com.liferay.portlet.shopping.service.ShoppingCartLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCartLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.shopping.service.ShoppingCartLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCartLocalServiceUtil
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCartLocalServiceEJB
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCartLocalServiceHome
 * @see com.liferay.portlet.shopping.service.impl.ShoppingCartLocalServiceImpl
 *
 */
public class ShoppingCartLocalServiceEJBImpl implements ShoppingCartLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return ShoppingCartLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return ShoppingCartLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void deleteGroupCarts(long groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalServiceFactory.getTxImpl().deleteGroupCarts(groupId);
	}

	public void deleteUserCarts(long userId)
		throws com.liferay.portal.SystemException {
		ShoppingCartLocalServiceFactory.getTxImpl().deleteUserCarts(userId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart getCart(
		java.lang.String cartId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCartLocalServiceFactory.getTxImpl().getCart(cartId);
	}

	public java.util.Map getItems(long groupId, java.lang.String itemIds)
		throws com.liferay.portal.SystemException {
		return ShoppingCartLocalServiceFactory.getTxImpl().getItems(groupId,
			itemIds);
	}

	public com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String cartId,
		java.lang.String itemIds, java.lang.String couponIds, int altShipping,
		boolean insure)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCartLocalServiceFactory.getTxImpl().updateCart(userId,
			groupId, cartId, itemIds, couponIds, altShipping, insure);
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