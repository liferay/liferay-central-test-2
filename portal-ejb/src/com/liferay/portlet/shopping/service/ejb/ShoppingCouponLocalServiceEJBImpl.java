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

import com.liferay.portlet.shopping.service.ShoppingCouponLocalService;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCouponLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.shopping.service.ShoppingCouponLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceUtil
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCouponLocalServiceEJB
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCouponLocalServiceHome
 * @see com.liferay.portlet.shopping.service.impl.ShoppingCouponLocalServiceImpl
 *
 */
public class ShoppingCouponLocalServiceEJBImpl
	implements ShoppingCouponLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		java.lang.String userId, java.lang.String plid,
		java.lang.String couponId, boolean autoCouponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().addCoupon(userId,
			plid, couponId, autoCouponId, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder,
			discount, discountType);
	}

	public void deleteCoupon(java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCouponLocalServiceFactory.getTxImpl().deleteCoupon(couponId);
	}

	public void deleteCoupons(long groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalServiceFactory.getTxImpl().deleteCoupons(groupId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().getCoupon(couponId);
	}

	public java.util.List search(java.lang.String couponId,
		java.lang.String plid, java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().search(couponId,
			plid, companyId, active, discountType, andOperator, begin, end);
	}

	public int searchCount(java.lang.String couponId, long groupId,
		java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().searchCount(couponId,
			groupId, companyId, active, discountType, andOperator);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		java.lang.String userId, java.lang.String couponId,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCouponLocalServiceFactory.getTxImpl().updateCoupon(userId,
			couponId, name, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, endDateHour, endDateMinute, neverExpire,
			active, limitCategories, limitSkus, minOrder, discount, discountType);
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