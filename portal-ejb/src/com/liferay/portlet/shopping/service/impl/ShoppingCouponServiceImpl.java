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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCouponService;

import java.util.List;

/**
 * <a href="ShoppingCouponServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponServiceImpl
	extends PrincipalBean implements ShoppingCouponService {

	public ShoppingCoupon addCoupon(
			String plid, String couponId, boolean autoCouponId, String name,
			String description, int startDateMonth, int startDateDay,
			int startDateYear, int startDateHour, int startDateMinute,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverExpire, boolean active,
			String limitCategories, String limitSkus, double minOrder,
			double discount, String discountType)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.SHOPPING,
			ActionKeys.MANAGE_COUPONS);

		return ShoppingCouponLocalServiceUtil.addCoupon(
			getUserId(), plid, couponId, autoCouponId, name, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, neverExpire, active, limitCategories, limitSkus,
			minOrder, discount, discountType);
	}

	public void deleteCoupon(String plid, String couponId)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.SHOPPING,
			ActionKeys.MANAGE_COUPONS);

		ShoppingCouponLocalServiceUtil.deleteCoupon(couponId);
	}

	public ShoppingCoupon getCoupon(String plid, String couponId)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.SHOPPING,
			ActionKeys.MANAGE_COUPONS);

		return ShoppingCouponLocalServiceUtil.getCoupon(couponId);
	}

	public List search(
			String couponId, String plid, String companyId, boolean active,
			String discountType, boolean andOperator, int begin, int end)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.SHOPPING,
			ActionKeys.MANAGE_COUPONS);

		return ShoppingCouponLocalServiceUtil.search(
			couponId, plid, companyId, active, discountType, andOperator, begin,
			end);
	}

	public ShoppingCoupon updateCoupon(
			String plid, String couponId, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.SHOPPING,
			ActionKeys.MANAGE_COUPONS);

		return ShoppingCouponLocalServiceUtil.updateCoupon(
			getUserId(), couponId, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder, discount,
			discountType);
	}

}