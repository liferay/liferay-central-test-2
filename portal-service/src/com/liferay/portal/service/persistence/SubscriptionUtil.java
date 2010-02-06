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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Subscription;

import java.util.List;

/**
 * <a href="SubscriptionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SubscriptionPersistence
 * @see       SubscriptionPersistenceImpl
 * @generated
 */
public class SubscriptionUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Subscription remove(Subscription subscription)
		throws SystemException {
		return getPersistence().remove(subscription);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Subscription update(Subscription subscription, boolean merge)
		throws SystemException {
		return getPersistence().update(subscription, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.Subscription subscription) {
		getPersistence().cacheResult(subscription);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Subscription> subscriptions) {
		getPersistence().cacheResult(subscriptions);
	}

	public static com.liferay.portal.model.Subscription create(
		long subscriptionId) {
		return getPersistence().create(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription remove(
		long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription updateImpl(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(subscription, merge);
	}

	public static com.liferay.portal.model.Subscription findByPrimaryKey(
		long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription fetchByPrimaryKey(
		long subscriptionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(subscriptionId);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.Subscription findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Subscription findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Subscription[] findByUserId_PrevAndNext(
		long subscriptionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(subscriptionId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_C(userId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_C(userId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_C(userId, classNameId, start, end, obc);
	}

	public static com.liferay.portal.model.Subscription findByU_C_First(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByU_C_First(userId, classNameId, obc);
	}

	public static com.liferay.portal.model.Subscription findByU_C_Last(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByU_C_Last(userId, classNameId, obc);
	}

	public static com.liferay.portal.model.Subscription[] findByU_C_PrevAndNext(
		long subscriptionId, long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByU_C_PrevAndNext(subscriptionId, userId, classNameId,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_First(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_Last(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Subscription[] findByC_C_C_PrevAndNext(
		long subscriptionId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_PrevAndNext(subscriptionId, companyId,
			classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Subscription findByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_U_C_C(companyId, userId, classNameId, classPK,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByU_C(long userId, long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU_C(userId, classNameId);
	}

	public static void removeByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	public static void removeByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByU_C(long userId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_C(userId, classNameId);
	}

	public static int countByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	public static int countByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static SubscriptionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SubscriptionPersistence persistence) {
		_persistence = persistence;
	}

	private static SubscriptionPersistence _persistence;
}