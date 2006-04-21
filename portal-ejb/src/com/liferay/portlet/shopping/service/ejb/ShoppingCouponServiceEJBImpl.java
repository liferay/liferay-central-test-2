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

import com.liferay.portlet.shopping.service.spring.ShoppingCouponService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCouponServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponServiceEJBImpl implements ShoppingCouponService,
	SessionBean {
	public static final String CLASS_NAME = ShoppingCouponService.class.getName() +
		".transaction";

	public static ShoppingCouponService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingCouponService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		java.lang.String couponId, boolean autoCouponId, java.lang.String name,
		java.lang.String description, int startMonth, int startDay,
		int startYear, int endMonth, int endDay, int endYear,
		boolean neverExpires, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addCoupon(couponId, autoCouponId, name,
			description, startMonth, startDay, startYear, endMonth, endDay,
			endYear, neverExpires, active, limitCategories, limitSkus,
			minOrder, discount, discountType);
	}

	public void deleteCoupon(java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteCoupon(couponId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCoupon(couponId);
	}

	public java.util.List getCoupons(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCoupons(companyId);
	}

	public java.util.List getCoupons(java.lang.String companyId,
		boolean active, java.lang.String discountType, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCoupons(companyId, active, discountType, begin,
			end);
	}

	public int getCouponsSize(java.lang.String companyId, boolean active,
		java.lang.String discountType)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCouponsSize(companyId, active, discountType);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		java.lang.String couponId, java.lang.String name,
		java.lang.String description, int startMonth, int startDay,
		int startYear, int endMonth, int endDay, int endYear,
		boolean neverExpires, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateCoupon(couponId, name, description,
			startMonth, startDay, startYear, endMonth, endDay, endYear,
			neverExpires, active, limitCategories, limitSkus, minOrder,
			discount, discountType);
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