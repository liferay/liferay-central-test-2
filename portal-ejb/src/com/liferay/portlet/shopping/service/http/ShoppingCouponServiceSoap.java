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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="ShoppingCouponServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponServiceSoap {
	public static com.liferay.portlet.shopping.model.ShoppingCouponSoap addCoupon(
		java.lang.String plid, java.lang.String couponId, boolean autoCouponId,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.addCoupon(plid,
					couponId, autoCouponId, name, description, startDateMonth,
					startDateDay, startDateYear, startDateHour,
					startDateMinute, endDateMonth, endDateDay, endDateYear,
					endDateHour, endDateMinute, neverExpire, active,
					limitCategories, limitSkus, minOrder, discount, discountType);

			return com.liferay.portlet.shopping.model.ShoppingCouponSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteCoupon(java.lang.String plid,
		java.lang.String couponId) throws RemoteException {
		try {
			ShoppingCouponServiceUtil.deleteCoupon(plid, couponId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCouponSoap getCoupon(
		java.lang.String plid, java.lang.String couponId)
		throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.getCoupon(plid,
					couponId);

			return com.liferay.portlet.shopping.model.ShoppingCouponSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCouponSoap[] search(
		java.lang.String couponId, java.lang.String plid,
		java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator, int begin, int end)
		throws RemoteException {
		try {
			java.util.List returnValue = ShoppingCouponServiceUtil.search(couponId,
					plid, companyId, active, discountType, andOperator, begin,
					end);

			return com.liferay.portlet.shopping.model.ShoppingCouponSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCouponSoap updateCoupon(
		java.lang.String plid, java.lang.String couponId,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.updateCoupon(plid,
					couponId, name, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, neverExpire, active, limitCategories,
					limitSkus, minOrder, discount, discountType);

			return com.liferay.portlet.shopping.model.ShoppingCouponSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponServiceSoap.class);
}