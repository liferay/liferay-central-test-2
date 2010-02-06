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

package com.liferay.portlet.shopping.service;


/**
 * <a href="ShoppingCouponLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCouponLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponLocalService
 * @generated
 */
public class ShoppingCouponLocalServiceWrapper
	implements ShoppingCouponLocalService {
	public ShoppingCouponLocalServiceWrapper(
		ShoppingCouponLocalService shoppingCouponLocalService) {
		_shoppingCouponLocalService = shoppingCouponLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.addShoppingCoupon(shoppingCoupon);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon createShoppingCoupon(
		long couponId) {
		return _shoppingCouponLocalService.createShoppingCoupon(couponId);
	}

	public void deleteShoppingCoupon(long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCouponLocalService.deleteShoppingCoupon(couponId);
	}

	public void deleteShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		_shoppingCouponLocalService.deleteShoppingCoupon(shoppingCoupon);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon getShoppingCoupon(
		long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.getShoppingCoupon(couponId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> getShoppingCoupons(
		int start, int end) throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.getShoppingCoupons(start, end);
	}

	public int getShoppingCouponsCount()
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.getShoppingCouponsCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.updateShoppingCoupon(shoppingCoupon);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge) throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.updateShoppingCoupon(shoppingCoupon,
			merge);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		long userId, java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.addCoupon(userId, code, autoCode,
			name, description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType,
			serviceContext);
	}

	public void deleteCoupon(long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCouponLocalService.deleteCoupon(couponId);
	}

	public void deleteCoupons(long groupId)
		throws com.liferay.portal.SystemException {
		_shoppingCouponLocalService.deleteCoupons(groupId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.getCoupon(couponId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String code)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.getCoupon(code);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end)
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.search(groupId, companyId, code,
			active, discountType, andOperator, start, end);
	}

	public int searchCount(long groupId, long companyId, java.lang.String code,
		boolean active, java.lang.String discountType, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.searchCount(groupId, companyId,
			code, active, discountType, andOperator);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		long userId, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCouponLocalService.updateCoupon(userId, couponId, name,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType,
			serviceContext);
	}

	public ShoppingCouponLocalService getWrappedShoppingCouponLocalService() {
		return _shoppingCouponLocalService;
	}

	private ShoppingCouponLocalService _shoppingCouponLocalService;
}