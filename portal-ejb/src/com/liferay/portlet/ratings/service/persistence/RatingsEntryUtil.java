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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="RatingsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsEntryUtil {
	public static com.liferay.portlet.ratings.model.RatingsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(entryId));
		}

		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry = getPersistence()
																		  .remove(entryId);

		if (listener != null) {
			listener.onAfterRemove(ratingsEntry);
		}

		return ratingsEntry;
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(ratingsEntry);
		}

		ratingsEntry = getPersistence().remove(ratingsEntry);

		if (listener != null) {
			listener.onAfterRemove(ratingsEntry);
		}

		return ratingsEntry;
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry update(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = ratingsEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsEntry);
			}
			else {
				listener.onBeforeUpdate(ratingsEntry);
			}
		}

		ratingsEntry = getPersistence().update(ratingsEntry);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsEntry);
			}
			else {
				listener.onAfterUpdate(ratingsEntry);
			}
		}

		return ratingsEntry;
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry update(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = ratingsEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsEntry);
			}
			else {
				listener.onBeforeUpdate(ratingsEntry);
			}
		}

		ratingsEntry = getPersistence().update(ratingsEntry, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsEntry);
			}
			else {
				listener.onAfterUpdate(ratingsEntry);
			}
		}

		return ratingsEntry;
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK, begin, end);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK, begin, end, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_First(
		java.lang.String className, java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_First(className, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_Last(
		java.lang.String className, java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_Last(className, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_PrevAndNext(
		long entryId, java.lang.String className, java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_PrevAndNext(entryId, className,
			classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByU_C_C(
		long userId, java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByU_C_C(userId, className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_C_C(userId, className, classPK);
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

	public static void removeByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(className, classPK);
	}

	public static void removeByU_C_C(long userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.ratings.NoSuchEntryException {
		getPersistence().removeByU_C_C(userId, className, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(className, classPK);
	}

	public static int countByU_C_C(long userId, java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByU_C_C(userId, className, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static RatingsEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(RatingsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static RatingsEntryUtil _getUtil() {
		if (_util == null) {
			_util = (RatingsEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = RatingsEntryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.ratings.model.RatingsEntry"));
	private static Log _log = LogFactory.getLog(RatingsEntryUtil.class);
	private static RatingsEntryUtil _util;
	private RatingsEntryPersistence _persistence;
}