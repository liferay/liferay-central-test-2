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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="RatingsStatsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsStatsUtil {
	public static com.liferay.portlet.ratings.model.RatingsStats create(
		long statsId) {
		return getPersistence().create(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats remove(
		long statsId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchStatsException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(statsId));
		}

		com.liferay.portlet.ratings.model.RatingsStats ratingsStats = getPersistence()
																		  .remove(statsId);

		if (listener != null) {
			listener.onAfterRemove(ratingsStats);
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats remove(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(ratingsStats);
		}

		ratingsStats = getPersistence().remove(ratingsStats);

		if (listener != null) {
			listener.onAfterRemove(ratingsStats);
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = ratingsStats.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsStats);
			}
			else {
				listener.onBeforeUpdate(ratingsStats);
			}
		}

		ratingsStats = getPersistence().update(ratingsStats);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsStats);
			}
			else {
				listener.onAfterUpdate(ratingsStats);
			}
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = ratingsStats.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsStats);
			}
			else {
				listener.onBeforeUpdate(ratingsStats);
			}
		}

		ratingsStats = getPersistence().update(ratingsStats, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsStats);
			}
			else {
				listener.onAfterUpdate(ratingsStats);
			}
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats findByPrimaryKey(
		long statsId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchStatsException {
		return getPersistence().findByPrimaryKey(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats fetchByPrimaryKey(
		long statsId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchStatsException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
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

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchStatsException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static RatingsStatsPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(RatingsStatsPersistence persistence) {
		_persistence = persistence;
	}

	private static RatingsStatsUtil _getUtil() {
		if (_util == null) {
			_util = (RatingsStatsUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = RatingsStatsUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.ratings.model.RatingsStats"));
	private static Log _log = LogFactory.getLog(RatingsStatsUtil.class);
	private static RatingsStatsUtil _util;
	private RatingsStatsPersistence _persistence;
}