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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ShoppingCouponServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShoppingCouponService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponService
 * @generated
 */
public class ShoppingCouponServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		java.lang.String code, boolean autoCode, java.lang.String name,
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
				   .addCoupon(code, autoCode, name, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			endDateHour, endDateMinute, neverExpire, active, limitCategories,
			limitSkus, minOrder, discount, discountType, serviceContext);
	}

	public static void deleteCoupon(long groupId, long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCoupon(groupId, couponId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		long groupId, long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoupon(groupId, couponId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(groupId, companyId, code, active, discountType,
			andOperator, start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		long couponId, java.lang.String name, java.lang.String description,
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
				   .updateCoupon(couponId, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder,
			discount, discountType, serviceContext);
	}

	public static ShoppingCouponService getService() {
		if (_service == null) {
			_service = (ShoppingCouponService)PortalBeanLocatorUtil.locate(ShoppingCouponService.class.getName());
		}

		return _service;
	}

	public void setService(ShoppingCouponService service) {
		_service = service;
	}

	private static ShoppingCouponService _service;
}