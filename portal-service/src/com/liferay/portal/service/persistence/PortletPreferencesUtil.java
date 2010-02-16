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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PortletPreferences;

import java.util.List;

/**
 * <a href="PortletPreferencesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesPersistence
 * @see       PortletPreferencesPersistenceImpl
 * @generated
 */
public class PortletPreferencesUtil {
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
	public static PortletPreferences remove(
		PortletPreferences portletPreferences) throws SystemException {
		return getPersistence().remove(portletPreferences);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PortletPreferences update(
		PortletPreferences portletPreferences, boolean merge)
		throws SystemException {
		return getPersistence().update(portletPreferences, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		getPersistence().cacheResult(portletPreferences);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PortletPreferences> portletPreferenceses) {
		getPersistence().cacheResult(portletPreferenceses);
	}

	public static com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId) {
		return getPersistence().create(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portletPreferences, merge);
	}

	public static com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(portletPreferencesId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByPlid_First(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid_First(plid, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByPlid_Last(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid_Last(plid, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlid_PrevAndNext(portletPreferencesId, plid, obc);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_P(plid, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_P(plid, portletId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_P(plid, portletId, start, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_P_First(plid, portletId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_P_Last(plid, portletId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_P_PrevAndNext(portletPreferencesId, plid,
			portletId, obc);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByO_O_P(ownerId, ownerType, plid);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByO_O_P(ownerId, ownerType, plid, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByO_O_P(ownerId, ownerType, plid, start, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByO_O_P_First(ownerId, ownerType, plid, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByO_O_P_Last(ownerId, ownerType, plid, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByO_O_P_PrevAndNext(portletPreferencesId, ownerId,
			ownerType, plid, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByO_O_P_P(ownerId, ownerType, plid, portletId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlid(plid);
	}

	public static void removeByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_P(plid, portletId);
	}

	public static void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByO_O_P(ownerId, ownerType, plid);
	}

	public static void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPlid(plid);
	}

	public static int countByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_P(plid, portletId);
	}

	public static int countByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByO_O_P(ownerId, ownerType, plid);
	}

	public static int countByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortletPreferencesPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PortletPreferencesPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletPreferencesPersistence _persistence;
}