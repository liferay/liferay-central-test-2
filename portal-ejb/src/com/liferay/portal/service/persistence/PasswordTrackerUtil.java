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

/**
 * <a href="PasswordTrackerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PasswordTrackerUtil {
	public static com.liferay.portal.model.PasswordTracker create(
		java.lang.String passwordTrackerId) {
		return getPersistence().create(passwordTrackerId);
	}

	public static com.liferay.portal.model.PasswordTracker remove(
		java.lang.String passwordTrackerId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordTrackerException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(passwordTrackerId));
		}

		com.liferay.portal.model.PasswordTracker passwordTracker = getPersistence()
																	   .remove(passwordTrackerId);

		if (listener != null) {
			listener.onAfterRemove(passwordTracker);
		}

		return passwordTracker;
	}

	public static com.liferay.portal.model.PasswordTracker remove(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(passwordTracker);
		}

		passwordTracker = getPersistence().remove(passwordTracker);

		if (listener != null) {
			listener.onAfterRemove(passwordTracker);
		}

		return passwordTracker;
	}

	public static com.liferay.portal.model.PasswordTracker update(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordTracker.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordTracker);
			}
			else {
				listener.onBeforeUpdate(passwordTracker);
			}
		}

		passwordTracker = getPersistence().update(passwordTracker);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordTracker);
			}
			else {
				listener.onAfterUpdate(passwordTracker);
			}
		}

		return passwordTracker;
	}

	public static com.liferay.portal.model.PasswordTracker update(
		com.liferay.portal.model.PasswordTracker passwordTracker,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordTracker.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordTracker);
			}
			else {
				listener.onBeforeUpdate(passwordTracker);
			}
		}

		passwordTracker = getPersistence().update(passwordTracker, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordTracker);
			}
			else {
				listener.onAfterUpdate(passwordTracker);
			}
		}

		return passwordTracker;
	}

	public static com.liferay.portal.model.PasswordTracker findByPrimaryKey(
		java.lang.String passwordTrackerId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByPrimaryKey(passwordTrackerId);
	}

	public static com.liferay.portal.model.PasswordTracker fetchByPrimaryKey(
		java.lang.String passwordTrackerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(passwordTrackerId);
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

	public static com.liferay.portal.model.PasswordTracker findByUserId_First(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.PasswordTracker findByUserId_Last(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.PasswordTracker[] findByUserId_PrevAndNext(
		java.lang.String passwordTrackerId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_PrevAndNext(passwordTrackerId,
			userId, obc);
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

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PasswordTrackerPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PasswordTrackerPersistence persistence) {
		_persistence = persistence;
	}

	private static PasswordTrackerUtil _getUtil() {
		if (_util == null) {
			_util = (PasswordTrackerUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = PasswordTrackerUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PasswordTracker"));
	private static Log _log = LogFactory.getLog(PasswordTrackerUtil.class);
	private static PasswordTrackerUtil _util;
	private PasswordTrackerPersistence _persistence;
}