/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="ShoppingCouponUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponUtil {
	public static final String CLASS_NAME = ShoppingCouponUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCoupon"));

	public static com.liferay.portlet.shopping.model.ShoppingCoupon create(
		java.lang.String couponId) {
		return getPersistence().create(couponId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon remove(
		java.lang.String couponId)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(couponId));
		}

		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon = getPersistence()
																			   .remove(couponId);

		if (listener != null) {
			listener.onAfterRemove(shoppingCoupon);
		}

		return shoppingCoupon;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = shoppingCoupon.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCoupon);
			}
			else {
				listener.onBeforeUpdate(shoppingCoupon);
			}
		}

		shoppingCoupon = getPersistence().update(shoppingCoupon);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingCoupon);
			}
			else {
				listener.onAfterUpdate(shoppingCoupon);
			}
		}

		return shoppingCoupon;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByPrimaryKey(
		java.lang.String couponId)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(couponId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon[] findByCompanyId_PrevAndNext(
		java.lang.String couponId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(couponId,
			companyId, obc);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		boolean active, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, begin, end);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		boolean active, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByC_A_First(
		java.lang.String companyId, boolean active,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_First(companyId, active, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByC_A_Last(
		java.lang.String companyId, boolean active,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_Last(companyId, active, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon[] findByC_A_PrevAndNext(
		java.lang.String couponId, java.lang.String companyId, boolean active,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_PrevAndNext(couponId, companyId,
			active, obc);
	}

	public static java.util.List findByC_A_D(java.lang.String companyId,
		boolean active, java.lang.String discountType)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D(companyId, active, discountType);
	}

	public static java.util.List findByC_A_D(java.lang.String companyId,
		boolean active, java.lang.String discountType, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D(companyId, active, discountType,
			begin, end);
	}

	public static java.util.List findByC_A_D(java.lang.String companyId,
		boolean active, java.lang.String discountType, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D(companyId, active, discountType,
			begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByC_A_D_First(
		java.lang.String companyId, boolean active,
		java.lang.String discountType,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D_First(companyId, active,
			discountType, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon findByC_A_D_Last(
		java.lang.String companyId, boolean active,
		java.lang.String discountType,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D_Last(companyId, active,
			discountType, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon[] findByC_A_D_PrevAndNext(
		java.lang.String couponId, java.lang.String companyId, boolean active,
		java.lang.String discountType,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCouponException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_D_PrevAndNext(couponId, companyId,
			active, discountType, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_A(java.lang.String companyId, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A(companyId, active);
	}

	public static void removeByC_A_D(java.lang.String companyId,
		boolean active, java.lang.String discountType)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A_D(companyId, active, discountType);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_A(java.lang.String companyId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A(companyId, active);
	}

	public static int countByC_A_D(java.lang.String companyId, boolean active,
		java.lang.String discountType)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A_D(companyId, active, discountType);
	}

	public static ShoppingCouponPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ShoppingCouponUtil util = (ShoppingCouponUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ShoppingCouponPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ShoppingCouponUtil.class);
	private ShoppingCouponPersistence _persistence;
}