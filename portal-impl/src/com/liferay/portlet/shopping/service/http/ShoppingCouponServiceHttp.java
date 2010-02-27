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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;

/**
 * <a href="ShoppingCouponServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCouponServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil
 * @generated
 */
public class ShoppingCouponServiceHttp {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		HttpPrincipal httpPrincipal, java.lang.String code, boolean autoCode,
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
		try {
			Object paramObj0 = code;

			if (code == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = new BooleanWrapper(autoCode);

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

			Object paramObj21 = serviceContext;

			if (serviceContext == null) {
				paramObj21 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
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
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCoupon(HttpPrincipal httpPrincipal, long groupId,
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(couponId);

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"deleteCoupon", new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		HttpPrincipal httpPrincipal, long groupId, long couponId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(couponId);

			MethodWrapper methodWrapper = new MethodWrapper(ShoppingCouponServiceUtil.class.getName(),
					"getCoupon", new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		HttpPrincipal httpPrincipal, long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(companyId);

			Object paramObj2 = code;

			if (code == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(active);

			Object paramObj4 = discountType;

			if (discountType == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new BooleanWrapper(andOperator);

			Object paramObj6 = new IntegerWrapper(start);

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
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		HttpPrincipal httpPrincipal, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(couponId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = description;

			if (description == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(startDateMonth);

			Object paramObj4 = new IntegerWrapper(startDateDay);

			Object paramObj5 = new IntegerWrapper(startDateYear);

			Object paramObj6 = new IntegerWrapper(startDateHour);

			Object paramObj7 = new IntegerWrapper(startDateMinute);

			Object paramObj8 = new IntegerWrapper(endDateMonth);

			Object paramObj9 = new IntegerWrapper(endDateDay);

			Object paramObj10 = new IntegerWrapper(endDateYear);

			Object paramObj11 = new IntegerWrapper(endDateHour);

			Object paramObj12 = new IntegerWrapper(endDateMinute);

			Object paramObj13 = new BooleanWrapper(neverExpire);

			Object paramObj14 = new BooleanWrapper(active);

			Object paramObj15 = limitCategories;

			if (limitCategories == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = limitSkus;

			if (limitSkus == null) {
				paramObj16 = new NullWrapper("java.lang.String");
			}

			Object paramObj17 = new DoubleWrapper(minOrder);

			Object paramObj18 = new DoubleWrapper(discount);

			Object paramObj19 = discountType;

			if (discountType == null) {
				paramObj19 = new NullWrapper("java.lang.String");
			}

			Object paramObj20 = serviceContext;

			if (serviceContext == null) {
				paramObj20 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
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
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponServiceHttp.class);
}