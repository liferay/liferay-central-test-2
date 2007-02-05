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
 * <a href="UserTrackerPathUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserTrackerPathUtil {
	public static com.liferay.portal.model.UserTrackerPath create(
		java.lang.String userTrackerPathId) {
		return getPersistence().create(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath remove(
		java.lang.String userTrackerPathId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserTrackerPathException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(userTrackerPathId));
		}

		com.liferay.portal.model.UserTrackerPath userTrackerPath = getPersistence()
																	   .remove(userTrackerPathId);

		if (listener != null) {
			listener.onAfterRemove(userTrackerPath);
		}

		return userTrackerPath;
	}

	public static com.liferay.portal.model.UserTrackerPath remove(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(userTrackerPath);
		}

		userTrackerPath = getPersistence().remove(userTrackerPath);

		if (listener != null) {
			listener.onAfterRemove(userTrackerPath);
		}

		return userTrackerPath;
	}

	public static com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userTrackerPath.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userTrackerPath);
			}
			else {
				listener.onBeforeUpdate(userTrackerPath);
			}
		}

		userTrackerPath = getPersistence().update(userTrackerPath);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userTrackerPath);
			}
			else {
				listener.onAfterUpdate(userTrackerPath);
			}
		}

		return userTrackerPath;
	}

	public static com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userTrackerPath.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userTrackerPath);
			}
			else {
				listener.onBeforeUpdate(userTrackerPath);
			}
		}

		userTrackerPath = getPersistence().update(userTrackerPath, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userTrackerPath);
			}
			else {
				listener.onAfterUpdate(userTrackerPath);
			}
		}

		return userTrackerPath;
	}

	public static com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		java.lang.String userTrackerPathId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().findByPrimaryKey(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath fetchByPrimaryKey(
		java.lang.String userTrackerPathId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userTrackerPathId);
	}

	public static java.util.List findByUserTrackerId(
		java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId);
	}

	public static java.util.List findByUserTrackerId(
		java.lang.String userTrackerId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId, begin, end);
	}

	public static java.util.List findByUserTrackerId(
		java.lang.String userTrackerId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId, begin, end,
			obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		java.lang.String userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().findByUserTrackerId_First(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		java.lang.String userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().findByUserTrackerId_Last(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		java.lang.String userTrackerPathId, java.lang.String userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().findByUserTrackerId_PrevAndNext(userTrackerPathId,
			userTrackerId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
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

	public static void removeByUserTrackerId(java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserTrackerId(userTrackerId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserTrackerId(java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserTrackerId(userTrackerId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static UserTrackerPathPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(UserTrackerPathPersistence persistence) {
		_persistence = persistence;
	}

	private static UserTrackerPathUtil _getUtil() {
		if (_util == null) {
			_util = (UserTrackerPathUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = UserTrackerPathUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.UserTrackerPath"));
	private static Log _log = LogFactory.getLog(UserTrackerPathUtil.class);
	private static UserTrackerPathUtil _util;
	private UserTrackerPathPersistence _persistence;
}