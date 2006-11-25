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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BookmarksEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryUtil {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry create(
		java.lang.String entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		java.lang.String entryId)
		throws com.liferay.portlet.bookmarks.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(entryId));
		}

		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry = getPersistence()
																				.remove(entryId);

		if (listener != null) {
			listener.onAfterRemove(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(bookmarksEntry);
		}

		bookmarksEntry = getPersistence().remove(bookmarksEntry);

		if (listener != null) {
			listener.onAfterRemove(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry update(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = bookmarksEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(bookmarksEntry);
			}
			else {
				listener.onBeforeUpdate(bookmarksEntry);
			}
		}

		bookmarksEntry = getPersistence().update(bookmarksEntry);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(bookmarksEntry);
			}
			else {
				listener.onAfterUpdate(bookmarksEntry);
			}
		}

		return bookmarksEntry;
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry update(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = bookmarksEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(bookmarksEntry);
			}
			else {
				listener.onBeforeUpdate(bookmarksEntry);
			}
		}

		bookmarksEntry = getPersistence().update(bookmarksEntry, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(bookmarksEntry);
			}
			else {
				listener.onAfterUpdate(bookmarksEntry);
			}
		}

		return bookmarksEntry;
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByPrimaryKey(
		java.lang.String entryId)
		throws com.liferay.portlet.bookmarks.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByPrimaryKey(
		java.lang.String entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
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

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_First(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.bookmarks.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_First(folderId, obc);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_Last(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.bookmarks.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_Last(folderId, obc);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByFolderId_PrevAndNext(
		java.lang.String entryId, java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.bookmarks.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_PrevAndNext(entryId, folderId,
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

	public static void removeByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByFolderId(folderId);
	}

	public static int countByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByFolderId(folderId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static BookmarksEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(BookmarksEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static BookmarksEntryUtil _getUtil() {
		if (_util == null) {
			_util = (BookmarksEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = BookmarksEntryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.bookmarks.model.BookmarksEntry"));
	private static Log _log = LogFactory.getLog(BookmarksEntryUtil.class);
	private static BookmarksEntryUtil _util;
	private BookmarksEntryPersistence _persistence;
}