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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="UserTrackerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserTrackerUtil {
	public static com.liferay.portal.model.UserTracker create(
		java.lang.String userTrackerId) {
		return getPersistence().create(userTrackerId);
	}

	public static com.liferay.portal.model.UserTracker remove(
		java.lang.String userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(userTrackerId));
		}

		com.liferay.portal.model.UserTracker userTracker = getPersistence()
															   .remove(userTrackerId);

		if (listener != null) {
			listener.onAfterRemove(userTracker);
		}

		return userTracker;
	}

	public static com.liferay.portal.model.UserTracker remove(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(userTracker);
		}

		userTracker = getPersistence().remove(userTracker);

		if (listener != null) {
			listener.onAfterRemove(userTracker);
		}

		return userTracker;
	}

	public static com.liferay.portal.model.UserTracker update(
		com.liferay.portal.model.UserTracker userTracker)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userTracker.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userTracker);
			}
			else {
				listener.onBeforeUpdate(userTracker);
			}
		}

		userTracker = getPersistence().update(userTracker);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userTracker);
			}
			else {
				listener.onAfterUpdate(userTracker);
			}
		}

		return userTracker;
	}

	public static com.liferay.portal.model.UserTracker update(
		com.liferay.portal.model.UserTracker userTracker, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = userTracker.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(userTracker);
			}
			else {
				listener.onBeforeUpdate(userTracker);
			}
		}

		userTracker = getPersistence().update(userTracker, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(userTracker);
			}
			else {
				listener.onAfterUpdate(userTracker);
			}
		}

		return userTracker;
	}

	public static com.liferay.portal.model.UserTracker findByPrimaryKey(
		java.lang.String userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(userTrackerId);
	}

	public static com.liferay.portal.model.UserTracker fetchByPrimaryKey(
		java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userTrackerId);
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

	public static com.liferay.portal.model.UserTracker findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.UserTracker findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.UserTracker[] findByCompanyId_PrevAndNext(
		java.lang.String userTrackerId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(userTrackerId,
			companyId, obc);
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
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.UserTracker findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.UserTracker findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.UserTracker[] findByUserId_PrevAndNext(
		java.lang.String userTrackerId, java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(userTrackerId, userId,
			obc);
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
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

	public static void initDao() {
		getPersistence().initDao();
	}

	public static UserTrackerPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(UserTrackerPersistence persistence) {
		_persistence = persistence;
	}

	private static UserTrackerUtil _getUtil() {
		if (_util == null) {
			_util = (UserTrackerUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = UserTrackerUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.UserTracker"));
	private static Log _log = LogFactory.getLog(UserTrackerUtil.class);
	private static UserTrackerUtil _util;
	private UserTrackerPersistence _persistence;
}