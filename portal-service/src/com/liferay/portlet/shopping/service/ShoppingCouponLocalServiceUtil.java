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
 * <a href="ShoppingCouponLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCouponLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
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
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.addCoupon(userId, plid, couponId,
			autoCouponId, name, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, endDateHour, endDateMinute, neverExpire,
			active, limitCategories, limitSkus, minOrder, discount, discountType);
	}

	public static void deleteCoupon(java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();
		shoppingCouponLocalService.deleteCoupon(couponId);
	}

	public static void deleteCoupons(long groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();
		shoppingCouponLocalService.deleteCoupons(groupId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.getCoupon(couponId);
	}

	public static java.util.List search(java.lang.String couponId,
		java.lang.String plid, java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.search(couponId, plid, companyId,
			active, discountType, andOperator, begin, end);
	}

	public static int searchCount(java.lang.String couponId, long groupId,
		java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.searchCount(couponId, groupId,
			companyId, active, discountType, andOperator);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
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
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.updateCoupon(userId, couponId, name,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType);
	}
}