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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="UserTrackerPathUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserTrackerPathUtil {
	public static final String CLASS_NAME = UserTrackerPathUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.UserTrackerPath"));

	public static com.liferay.portal.model.UserTrackerPath create(
		java.lang.String userTrackerPathId) {
		return getPersistence().create(userTrackerPathId);
	}

	public static com.liferay.portal.model.UserTrackerPath remove(
		java.lang.String userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException, 
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
			listener.onBeforeRemove(findByPrimaryKey(userTrackerPathId));
		}

		com.liferay.portal.model.UserTrackerPath userTrackerPath = getPersistence()
																	   .remove(userTrackerPathId);

		if (listener != null) {
			listener.onAfterRemove(userTrackerPath);
		}

		return userTrackerPath;
	}

	public static com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
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

	public static com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		java.lang.String userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException, 
			com.liferay.portal.SystemException {
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId(userTrackerId, begin, end,
			obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		java.lang.String userTrackerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId_First(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		java.lang.String userTrackerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId_Last(userTrackerId, obc);
	}

	public static com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		java.lang.String userTrackerPathId, java.lang.String userTrackerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserTrackerId_PrevAndNext(userTrackerPathId,
			userTrackerId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByUserTrackerId(java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserTrackerId(userTrackerId);
	}

	public static int countByUserTrackerId(java.lang.String userTrackerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserTrackerId(userTrackerId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static UserTrackerPathPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		UserTrackerPathUtil util = (UserTrackerPathUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(UserTrackerPathPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(UserTrackerPathUtil.class);
	private UserTrackerPathPersistence _persistence;
}