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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="DLFileShortcutUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileShortcutUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId) {
		return getPersistence().create(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(fileShortcutId));
		}

		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut = getPersistence()
																					  .remove(fileShortcutId);

		if (listener != null) {
			listener.onAfterRemove(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(dlFileShortcut);
		}

		dlFileShortcut = getPersistence().remove(dlFileShortcut);

		if (listener != null) {
			listener.onAfterRemove(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut update(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileShortcut.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileShortcut);
			}
			else {
				listener.onBeforeUpdate(dlFileShortcut);
			}
		}

		dlFileShortcut = getPersistence().update(dlFileShortcut);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileShortcut);
			}
			else {
				listener.onAfterUpdate(dlFileShortcut);
			}
		}

		return dlFileShortcut;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut update(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileShortcut.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileShortcut);
			}
			else {
				listener.onBeforeUpdate(dlFileShortcut);
			}
		}

		dlFileShortcut = getPersistence().update(dlFileShortcut, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileShortcut);
			}
			else {
				listener.onAfterUpdate(dlFileShortcut);
			}
		}

		return dlFileShortcut;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(fileShortcutId);
	}

	public static java.util.List findByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId);
	}

	public static java.util.List findByFolderId(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId, begin, end);
	}

	public static java.util.List findByFolderId(java.lang.String folderId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByFolderId_First(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_First(folderId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByFolderId_Last(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_Last(folderId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByFolderId_PrevAndNext(
		long fileShortcutId, java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_PrevAndNext(fileShortcutId,
			folderId, obc);
	}

	public static java.util.List findByTF_TN(java.lang.String toFolderId,
		java.lang.String toName) throws com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN(toFolderId, toName);
	}

	public static java.util.List findByTF_TN(java.lang.String toFolderId,
		java.lang.String toName, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN(toFolderId, toName, begin, end);
	}

	public static java.util.List findByTF_TN(java.lang.String toFolderId,
		java.lang.String toName, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN(toFolderId, toName, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByTF_TN_First(
		java.lang.String toFolderId, java.lang.String toName,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN_First(toFolderId, toName, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByTF_TN_Last(
		java.lang.String toFolderId, java.lang.String toName,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN_Last(toFolderId, toName, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByTF_TN_PrevAndNext(
		long fileShortcutId, java.lang.String toFolderId,
		java.lang.String toName,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByTF_TN_PrevAndNext(fileShortcutId,
			toFolderId, toName, obc);
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

	public static void removeByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByFolderId(folderId);
	}

	public static void removeByTF_TN(java.lang.String toFolderId,
		java.lang.String toName) throws com.liferay.portal.SystemException {
		getPersistence().removeByTF_TN(toFolderId, toName);
	}

	public static int countByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByFolderId(folderId);
	}

	public static int countByTF_TN(java.lang.String toFolderId,
		java.lang.String toName) throws com.liferay.portal.SystemException {
		return getPersistence().countByTF_TN(toFolderId, toName);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static DLFileShortcutPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(DLFileShortcutPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileShortcutUtil _getUtil() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (DLFileShortcutUtil)ctx.getBean(_UTIL);
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

	private static final String _UTIL = DLFileShortcutUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileShortcut"));
	private static Log _log = LogFactory.getLog(DLFileShortcutUtil.class);
	private static DLFileShortcutUtil _util;
	private DLFileShortcutPersistence _persistence;
}