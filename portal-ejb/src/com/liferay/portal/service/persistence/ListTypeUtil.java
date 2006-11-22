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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="ListTypeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ListTypeUtil {
	public static com.liferay.portal.model.ListType create(
		java.lang.String listTypeId) {
		return getPersistence().create(listTypeId);
	}

	public static com.liferay.portal.model.ListType remove(
		java.lang.String listTypeId)
		throws com.liferay.portal.NoSuchListTypeException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(listTypeId));
		}

		com.liferay.portal.model.ListType listType = getPersistence().remove(listTypeId);

		if (listener != null) {
			listener.onAfterRemove(listType);
		}

		return listType;
	}

	public static com.liferay.portal.model.ListType remove(
		com.liferay.portal.model.ListType listType)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(listType);
		}

		listType = getPersistence().remove(listType);

		if (listener != null) {
			listener.onAfterRemove(listType);
		}

		return listType;
	}

	public static com.liferay.portal.model.ListType update(
		com.liferay.portal.model.ListType listType)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = listType.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(listType);
			}
			else {
				listener.onBeforeUpdate(listType);
			}
		}

		listType = getPersistence().update(listType);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(listType);
			}
			else {
				listener.onAfterUpdate(listType);
			}
		}

		return listType;
	}

	public static com.liferay.portal.model.ListType update(
		com.liferay.portal.model.ListType listType, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = listType.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(listType);
			}
			else {
				listener.onBeforeUpdate(listType);
			}
		}

		listType = getPersistence().update(listType, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(listType);
			}
			else {
				listener.onAfterUpdate(listType);
			}
		}

		return listType;
	}

	public static com.liferay.portal.model.ListType findByPrimaryKey(
		java.lang.String listTypeId)
		throws com.liferay.portal.NoSuchListTypeException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(listTypeId);
	}

	public static com.liferay.portal.model.ListType fetchByPrimaryKey(
		java.lang.String listTypeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(listTypeId);
	}

	public static java.util.List findByType(java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type);
	}

	public static java.util.List findByType(java.lang.String type, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, begin, end);
	}

	public static java.util.List findByType(java.lang.String type, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, begin, end, obc);
	}

	public static com.liferay.portal.model.ListType findByType_First(
		java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByType_First(type, obc);
	}

	public static com.liferay.portal.model.ListType findByType_Last(
		java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByType_Last(type, obc);
	}

	public static com.liferay.portal.model.ListType[] findByType_PrevAndNext(
		java.lang.String listTypeId, java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByType_PrevAndNext(listTypeId, type, obc);
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

	public static void removeByType(java.lang.String type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByType(type);
	}

	public static int countByType(java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByType(type);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ListTypePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ListTypePersistence persistence) {
		_persistence = persistence;
	}

	private static ListTypeUtil _getUtil() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (ListTypeUtil)ctx.getBean(_UTIL);
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

	private static final String _UTIL = ListTypeUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ListType"));
	private static Log _log = LogFactory.getLog(ListTypeUtil.class);
	private static ListTypeUtil _util;
	private ListTypePersistence _persistence;
}