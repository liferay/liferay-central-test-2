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

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.servlet.TunnelUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingCouponServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCouponServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponServiceHttp {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String couponId, boolean autoCouponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = couponId;

			if (couponId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new BooleanWrapper(autoCouponId);
			Object paramObj3 = name;

			if (name == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = description;

			if (description == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new IntegerWrapper(startDateMonth);
			Object paramObj6 = new IntegerWrapper(startDateDay);
			Object paramObj7 = new IntegerWrapper(startDateYear);
			Object paramObj8 = new IntegerWrapper(startDateHour);
			Object paramObj9 = new IntegerWrapper(startDateMinute);
			Object paramObj10 = new IntegerWrapper(endDateMonth);
			Object paramObj11 = new IntegerWrapper(endDateDay);
			Object paramObj12 = new IntegerWrapper(endDateYear);
			Object paramObj13 = new IntegerWrapper(endDateHour);
			Object paramObj14 = new IntegerWrapper(endDateMinute);
			Object paramObj15 = new BooleanWrapper(neverExpire);
			Object paramObj16 = new BooleanWrapper(active);
			Object paramObj17 = limitCategories;

			if (limitCategories == null) {
				paramObj17 = new NullWrapper("java.lang.String");
			}

			Object paramObj18 = limitSkus;

			if (limitSkus == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = new DoubleWrapper(minOrder);
			Object paramObj20 = new DoubleWrapper(discount);
			Object paramObj21 = discountType;

			if (discountType == null) {
				paramObj21 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"addCoupon",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void deleteCoupon(HttpPrincipal httpPrincipal,
		java.lang.String plid, java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = couponId;

			if (couponId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"deleteCoupon", new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String couponId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = couponId;

			if (couponId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"getCoupon", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List search(HttpPrincipal httpPrincipal,
		java.lang.String couponId, java.lang.String plid,
		java.lang.String companyId, boolean active,
		java.lang.String discountType, boolean andOperator, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = couponId;

			if (couponId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = plid;

			if (plid == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = companyId;

			if (companyId == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(active);
			Object paramObj4 = discountType;

			if (discountType == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new BooleanWrapper(andOperator);
			Object paramObj6 = new IntegerWrapper(begin);
			Object paramObj7 = new IntegerWrapper(end);
			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"search",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = couponId;

			if (couponId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new IntegerWrapper(startDateMonth);
			Object paramObj5 = new IntegerWrapper(startDateDay);
			Object paramObj6 = new IntegerWrapper(startDateYear);
			Object paramObj7 = new IntegerWrapper(startDateHour);
			Object paramObj8 = new IntegerWrapper(startDateMinute);
			Object paramObj9 = new IntegerWrapper(endDateMonth);
			Object paramObj10 = new IntegerWrapper(endDateDay);
			Object paramObj11 = new IntegerWrapper(endDateYear);
			Object paramObj12 = new IntegerWrapper(endDateHour);
			Object paramObj13 = new IntegerWrapper(endDateMinute);
			Object paramObj14 = new BooleanWrapper(neverExpire);
			Object paramObj15 = new BooleanWrapper(active);
			Object paramObj16 = limitCategories;

			if (limitCategories == null) {
				paramObj16 = new NullWrapper("java.lang.String");
			}

			Object paramObj17 = limitSkus;

			if (limitSkus == null) {
				paramObj17 = new NullWrapper("java.lang.String");
			}

			Object paramObj18 = new DoubleWrapper(minOrder);
			Object paramObj19 = new DoubleWrapper(discount);
			Object paramObj20 = discountType;

			if (discountType == null) {
				paramObj20 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"updateCoupon",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20
					});
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactory.getLog(ShoppingCouponServiceHttp.class);
}