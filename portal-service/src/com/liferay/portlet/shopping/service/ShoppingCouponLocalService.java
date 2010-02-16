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

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="ShoppingCouponLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.shopping.service.impl.ShoppingCouponLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingCouponLocalService {
	public com.liferay.portlet.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon createShoppingCoupon(
		long couponId);

	public void deleteShoppingCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.shopping.model.ShoppingCoupon getShoppingCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> getShoppingCoupons(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getShoppingCouponsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteCoupons(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, long companyId, java.lang.String code,
		boolean active, java.lang.String discountType, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		long userId, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}