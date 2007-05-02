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
 * <a href="ClassNameUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ClassNameUtil {
	public static com.liferay.portal.model.ClassName create(long classNameId) {
		return getPersistence().create(classNameId);
	}

	public static com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchClassNameException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(classNameId));
		}

		com.liferay.portal.model.ClassName className = getPersistence().remove(classNameId);

		if (listener != null) {
			listener.onAfterRemove(className);
		}

		return className;
	}

	public static com.liferay.portal.model.ClassName remove(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(className);
		}

		className = getPersistence().remove(className);

		if (listener != null) {
			listener.onAfterRemove(className);
		}

		return className;
	}

	public static com.liferay.portal.model.ClassName update(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = className.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(className);
			}
			else {
				listener.onBeforeUpdate(className);
			}
		}

		className = getPersistence().update(className);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(className);
			}
			else {
				listener.onAfterUpdate(className);
			}
		}

		return className;
	}

	public static com.liferay.portal.model.ClassName update(
		com.liferay.portal.model.ClassName className, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = className.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(className);
			}
			else {
				listener.onBeforeUpdate(className);
			}
		}

		className = getPersistence().update(className, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(className);
			}
			else {
				listener.onAfterUpdate(className);
			}
		}

		return className;
	}

	public static com.liferay.portal.model.ClassName findByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchClassNameException {
		return getPersistence().findByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchClassNameException {
		return getPersistence().findByValue(value);
	}

	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByValue(value);
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

	public static void removeByValue(java.lang.String value)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchClassNameException {
		getPersistence().removeByValue(value);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByValue(java.lang.String value)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByValue(value);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ClassNamePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ClassNamePersistence persistence) {
		_persistence = persistence;
	}

	private static ClassNameUtil _getUtil() {
		if (_util == null) {
			_util = (ClassNameUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = ClassNameUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.ClassName"));
	private static Log _log = LogFactory.getLog(ClassNameUtil.class);
	private static ClassNameUtil _util;
	private ClassNamePersistence _persistence;
}