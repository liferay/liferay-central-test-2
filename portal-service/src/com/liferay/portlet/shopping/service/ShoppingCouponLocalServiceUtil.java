/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.shopping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ShoppingCouponLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShoppingCouponLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponLocalService
 * @generated
 */
public class ShoppingCouponLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShoppingCoupon(shoppingCoupon);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon createShoppingCoupon(
		long couponId) {
		return getService().createShoppingCoupon(couponId);
	}

	public static void deleteShoppingCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCoupon(couponId);
	}

	public static void deleteShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCoupon(shoppingCoupon);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getShoppingCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCoupon(couponId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> getShoppingCoupons(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCoupons(start, end);
	}

	public static int getShoppingCouponsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCouponsCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCoupon(shoppingCoupon);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCoupon(shoppingCoupon, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		long userId, java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCoupon(userId, code, autoCode, name, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			endDateHour, endDateMinute, neverExpire, active, limitCategories,
			limitSkus, minOrder, discount, discountType, serviceContext);
	}

	public static void deleteCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCoupon(couponId);
	}

	public static void deleteCoupons(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCoupons(groupId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoupon(couponId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoupon(code);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(groupId, companyId, code, active, discountType,
			andOperator, start, end);
	}

	public static int searchCount(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(groupId, companyId, code, active, discountType,
			andOperator);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		long userId, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCoupon(userId, couponId, name, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			endDateHour, endDateMinute, neverExpire, active, limitCategories,
			limitSkus, minOrder, discount, discountType, serviceContext);
	}

	public static ShoppingCouponLocalService getService() {
		if (_service == null) {
			_service = (ShoppingCouponLocalService)PortalBeanLocatorUtil.locate(ShoppingCouponLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ShoppingCouponLocalService service) {
		_service = service;
	}

	private static ShoppingCouponLocalService _service;
}