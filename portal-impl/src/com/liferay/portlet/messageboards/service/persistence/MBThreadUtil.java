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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBThreadUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBThreadUtil {
	public static com.liferay.portlet.messageboards.model.MBThread create(
		long threadId) {
		return getPersistence().create(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread remove(
		long threadId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchThreadException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(threadId));
		}

		com.liferay.portlet.messageboards.model.MBThread mbThread = getPersistence()
																		.remove(threadId);

		if (listener != null) {
			listener.onAfterRemove(mbThread);
		}

		return mbThread;
	}

	public static com.liferay.portlet.messageboards.model.MBThread remove(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbThread);
		}

		mbThread = getPersistence().remove(mbThread);

		if (listener != null) {
			listener.onAfterRemove(mbThread);
		}

		return mbThread;
	}

	public static com.liferay.portlet.messageboards.model.MBThread update(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbThread.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbThread);
			}
			else {
				listener.onBeforeUpdate(mbThread);
			}
		}

		mbThread = getPersistence().update(mbThread);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbThread);
			}
			else {
				listener.onAfterUpdate(mbThread);
			}
		}

		return mbThread;
	}

	public static com.liferay.portlet.messageboards.model.MBThread update(
		com.liferay.portlet.messageboards.model.MBThread mbThread,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbThread.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbThread);
			}
			else {
				listener.onBeforeUpdate(mbThread);
			}
		}

		mbThread = getPersistence().update(mbThread, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbThread);
			}
			else {
				listener.onAfterUpdate(mbThread);
			}
		}

		return mbThread;
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByPrimaryKey(
		long threadId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByPrimaryKey(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread fetchByPrimaryKey(
		long threadId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(threadId);
	}

	public static java.util.List findByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List findByCategoryId(long categoryId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end);
	}

	public static java.util.List findByCategoryId(long categoryId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByCategoryId_First(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBThread findByCategoryId_Last(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBThread[] findByCategoryId_PrevAndNext(
		long threadId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchThreadException {
		return getPersistence().findByCategoryId_PrevAndNext(threadId,
			categoryId, obc);
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

	public static void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MBThreadPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBThreadPersistence persistence) {
		_persistence = persistence;
	}

	private static MBThreadUtil _getUtil() {
		if (_util == null) {
			_util = (MBThreadUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBThreadUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBThread"));
	private static Log _log = LogFactory.getLog(MBThreadUtil.class);
	private static MBThreadUtil _util;
	private MBThreadPersistence _persistence;
}