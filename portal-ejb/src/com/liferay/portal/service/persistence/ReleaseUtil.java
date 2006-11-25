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
 * <a href="ReleaseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ReleaseUtil {
	public static com.liferay.portal.model.Release create(
		java.lang.String releaseId) {
		return getPersistence().create(releaseId);
	}

	public static com.liferay.portal.model.Release remove(
		java.lang.String releaseId)
		throws com.liferay.portal.NoSuchReleaseException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(releaseId));
		}

		com.liferay.portal.model.Release release = getPersistence().remove(releaseId);

		if (listener != null) {
			listener.onAfterRemove(release);
		}

		return release;
	}

	public static com.liferay.portal.model.Release remove(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(release);
		}

		release = getPersistence().remove(release);

		if (listener != null) {
			listener.onAfterRemove(release);
		}

		return release;
	}

	public static com.liferay.portal.model.Release update(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = release.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(release);
			}
			else {
				listener.onBeforeUpdate(release);
			}
		}

		release = getPersistence().update(release);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(release);
			}
			else {
				listener.onAfterUpdate(release);
			}
		}

		return release;
	}

	public static com.liferay.portal.model.Release update(
		com.liferay.portal.model.Release release, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = release.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(release);
			}
			else {
				listener.onBeforeUpdate(release);
			}
		}

		release = getPersistence().update(release, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(release);
			}
			else {
				listener.onAfterUpdate(release);
			}
		}

		return release;
	}

	public static com.liferay.portal.model.Release findByPrimaryKey(
		java.lang.String releaseId)
		throws com.liferay.portal.NoSuchReleaseException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(releaseId);
	}

	public static com.liferay.portal.model.Release fetchByPrimaryKey(
		java.lang.String releaseId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(releaseId);
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

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ReleasePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ReleasePersistence persistence) {
		_persistence = persistence;
	}

	private static ReleaseUtil _getUtil() {
		if (_util == null) {
			_util = (ReleaseUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = ReleaseUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Release"));
	private static Log _log = LogFactory.getLog(ReleaseUtil.class);
	private static ReleaseUtil _util;
	private ReleasePersistence _persistence;
}