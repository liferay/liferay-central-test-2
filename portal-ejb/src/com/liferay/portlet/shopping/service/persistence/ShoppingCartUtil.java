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
 * <a href="ShoppingCartUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartUtil {
	public static final String CLASS_NAME = ShoppingCartUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCart"));

	public static com.liferay.portlet.shopping.model.ShoppingCart create(
		java.lang.String cartId) {
		return getPersistence().create(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		java.lang.String cartId)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
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
			listener.onBeforeRemove(findByPrimaryKey(cartId));
		}

		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart = getPersistence()
																		   .remove(cartId);

		if (listener != null) {
			listener.onAfterRemove(shoppingCart);
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
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

		boolean isNew = shoppingCart.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCart);
			}
			else {
				listener.onBeforeUpdate(shoppingCart);
			}
		}

		shoppingCart = getPersistence().update(shoppingCart);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingCart);
			}
			else {
				listener.onAfterUpdate(shoppingCart);
			}
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		java.lang.String cartId)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(cartId);
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

	public static com.liferay.portlet.shopping.model.ShoppingCart findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByCompanyId_PrevAndNext(
		java.lang.String cartId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(cartId, companyId,
			obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId(
		java.lang.String userId)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portlet.shopping.NoSuchCartException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static ShoppingCartPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ShoppingCartUtil util = (ShoppingCartUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ShoppingCartPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ShoppingCartUtil.class);
	private ShoppingCartPersistence _persistence;
}