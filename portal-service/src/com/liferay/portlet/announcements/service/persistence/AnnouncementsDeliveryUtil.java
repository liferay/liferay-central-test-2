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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;

import java.util.List;

/**
 * <a href="AnnouncementsDeliveryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryPersistence
 * @see       AnnouncementsDeliveryPersistenceImpl
 * @generated
 */
public class AnnouncementsDeliveryUtil {
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
	public static AnnouncementsDelivery remove(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		return getPersistence().remove(announcementsDelivery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AnnouncementsDelivery update(
		AnnouncementsDelivery announcementsDelivery, boolean merge)
		throws SystemException {
		return getPersistence().update(announcementsDelivery, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery) {
		getPersistence().cacheResult(announcementsDelivery);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDeliveries) {
		getPersistence().cacheResult(announcementsDeliveries);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery create(
		long deliveryId) {
		return getPersistence().create(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery remove(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().remove(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(announcementsDelivery, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByPrimaryKey(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(deliveryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery[] findByUserId_PrevAndNext(
		long deliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_PrevAndNext(deliveryId, userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByU_T(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_T(userId, type, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		getPersistence().removeByU_T(userId, type);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_T(userId, type);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementsDeliveryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsDeliveryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AnnouncementsDeliveryPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementsDeliveryPersistence _persistence;
}