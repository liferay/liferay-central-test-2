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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SubscriptionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SubscriptionUtil {
	public static com.liferay.portal.model.Subscription create(
		java.lang.String subscriptionId) {
		return getPersistence().create(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription remove(
		java.lang.String subscriptionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(subscriptionId));
		}

		com.liferay.portal.model.Subscription subscription = getPersistence()
																 .remove(subscriptionId);

		if (listener != null) {
			listener.onAfterRemove(subscription);
		}

		return subscription;
	}

	public static com.liferay.portal.model.Subscription remove(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(subscription);
		}

		subscription = getPersistence().remove(subscription);

		if (listener != null) {
			listener.onAfterRemove(subscription);
		}

		return subscription;
	}

	public static com.liferay.portal.model.Subscription update(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = subscription.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(subscription);
			}
			else {
				listener.onBeforeUpdate(subscription);
			}
		}

		subscription = getPersistence().update(subscription);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(subscription);
			}
			else {
				listener.onAfterUpdate(subscription);
			}
		}

		return subscription;
	}

	public static com.liferay.portal.model.Subscription update(
		com.liferay.portal.model.Subscription subscription, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = subscription.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(subscription);
			}
			else {
				listener.onBeforeUpdate(subscription);
			}
		}

		subscription = getPersistence().update(subscription, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(subscription);
			}
			else {
				listener.onAfterUpdate(subscription);
			}
		}

		return subscription;
	}

	public static com.liferay.portal.model.Subscription findByPrimaryKey(
		java.lang.String subscriptionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByPrimaryKey(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription fetchByPrimaryKey(
		java.lang.String subscriptionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(subscriptionId);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.Subscription findByUserId_First(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Subscription findByUserId_Last(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Subscription[] findByUserId_PrevAndNext(
		java.lang.String subscriptionId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByUserId_PrevAndNext(subscriptionId,
			userId, obc);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_C_C_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByC_C_C_First(companyId, className,
			classPK, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_C_C_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByC_C_C_Last(companyId, className, classPK,
			obc);
	}

	public static com.liferay.portal.model.Subscription[] findByC_C_C_PrevAndNext(
		java.lang.String subscriptionId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByC_C_C_PrevAndNext(subscriptionId,
			companyId, className, classPK, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_U_C_C(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		return getPersistence().findByC_U_C_C(companyId, userId, className,
			classPK);
	}

	public static com.liferay.portal.model.Subscription fetchByC_U_C_C(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_U_C_C(companyId, userId, className,
			classPK);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static void removeByC_U_C_C(java.lang.String companyId,
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchSubscriptionException {
		getPersistence().removeByC_U_C_C(companyId, userId, className, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static int countByC_U_C_C(java.lang.String companyId,
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_U_C_C(companyId, userId, className,
			classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static SubscriptionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SubscriptionPersistence persistence) {
		_persistence = persistence;
	}

	private static SubscriptionUtil _getUtil() {
		if (_util == null) {
			_util = (SubscriptionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = SubscriptionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Subscription"));
	private static Log _log = LogFactory.getLog(SubscriptionUtil.class);
	private static SubscriptionUtil _util;
	private SubscriptionPersistence _persistence;
}