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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="BlogsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryUtil {
	public static com.liferay.portlet.blogs.model.BlogsEntry create(
		java.lang.String entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry remove(
		java.lang.String entryId)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(entryId));
		}

		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry = getPersistence()
																	.remove(entryId);

		if (listener != null) {
			listener.onAfterRemove(blogsEntry);
		}

		return blogsEntry;
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry remove(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(blogsEntry);
		}

		blogsEntry = getPersistence().remove(blogsEntry);

		if (listener != null) {
			listener.onAfterRemove(blogsEntry);
		}

		return blogsEntry;
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = blogsEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(blogsEntry);
			}
			else {
				listener.onBeforeUpdate(blogsEntry);
			}
		}

		blogsEntry = getPersistence().update(blogsEntry);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(blogsEntry);
			}
			else {
				listener.onAfterUpdate(blogsEntry);
			}
		}

		return blogsEntry;
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = blogsEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(blogsEntry);
			}
			else {
				listener.onBeforeUpdate(blogsEntry);
			}
		}

		blogsEntry = getPersistence().update(blogsEntry, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(blogsEntry);
			}
			else {
				listener.onAfterUpdate(blogsEntry);
			}
		}

		return blogsEntry;
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByPrimaryKey(
		java.lang.String entryId)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByPrimaryKey(
		java.lang.String entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByGroupId_PrevAndNext(
		java.lang.String entryId, java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(entryId, groupId, obc);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByCompanyId_PrevAndNext(
		java.lang.String entryId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(entryId, companyId,
			obc);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end);
	}

	public static java.util.List findByCategoryId(java.lang.String categoryId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId(categoryId, begin, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCategoryId_First(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_First(categoryId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCategoryId_Last(
		java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_Last(categoryId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByCategoryId_PrevAndNext(
		java.lang.String entryId, java.lang.String categoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchEntryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCategoryId_PrevAndNext(entryId,
			categoryId, obc);
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

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByCategoryId(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static BlogsEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(BlogsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static BlogsEntryUtil _getUtil() {
		if (_util == null) {
			_util = (BlogsEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = BlogsEntryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.blogs.model.BlogsEntry"));
	private static Log _log = LogFactory.getLog(BlogsEntryUtil.class);
	private static BlogsEntryUtil _util;
	private BlogsEntryPersistence _persistence;
}