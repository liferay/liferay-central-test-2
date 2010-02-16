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
import com.liferay.portal.model.UserTrackerPath;

import java.util.List;

/**
 * <a href="UserTrackerPathUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathPersistence
 * @see       UserTrackerPathPersistenceImpl
 * @generated
 */
public class UserTrackerPathUtil {
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
	public static UserTrackerPath remove(UserTrackerPath userTrackerPath)
		throws SystemException {
		return getPersistence().remove(userTrackerPath);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserTrackerPath update(UserTrackerPath userTrackerPath,
		boolean merge) throws SystemException {
		return getPersistence().update(userTrackerPath, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.UserTrackerPath userTrackerPath) {
		getPersistence().cacheResult(userTrackerPath);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserTrackerPath> userTrackerPaths) {
		getPersistence().cacheResult(userTrackerPaths);
	}

	public static com.liferay.portal.model.UserTrackerPath create(
		long userTrackerPathId) {
		return getPersistence().create(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath remove(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userTrackerPath, merge);
	}

	public static com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath fetchByPrimaryKey(
		long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userTrackerPathId);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserTrackerId(userTrackerId, start, end, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		long userTrackerId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserTrackerId_First(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		long userTrackerId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserTrackerId_Last(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserTrackerId_PrevAndNext(userTrackerPathId,
			userTrackerId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserTrackerId(long userTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserTrackerId(userTrackerId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserTrackerId(long userTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserTrackerId(userTrackerId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserTrackerPathPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserTrackerPathPersistence persistence) {
		_persistence = persistence;
	}

	private static UserTrackerPathPersistence _persistence;
}