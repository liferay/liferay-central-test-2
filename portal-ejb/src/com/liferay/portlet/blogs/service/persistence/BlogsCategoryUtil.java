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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BlogsCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsCategoryUtil {
	public static com.liferay.portlet.blogs.model.BlogsCategory create(
		java.lang.String categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory remove(
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.blogs.NoSuchCategoryException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(categoryId));
		}

		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory = getPersistence()
																		  .remove(categoryId);

		if (listener != null) {
			listener.onAfterRemove(blogsCategory);
		}

		return blogsCategory;
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory remove(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(blogsCategory);
		}

		blogsCategory = getPersistence().remove(blogsCategory);

		if (listener != null) {
			listener.onAfterRemove(blogsCategory);
		}

		return blogsCategory;
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = blogsCategory.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(blogsCategory);
			}
			else {
				listener.onBeforeUpdate(blogsCategory);
			}
		}

		blogsCategory = getPersistence().update(blogsCategory);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(blogsCategory);
			}
			else {
				listener.onAfterUpdate(blogsCategory);
			}
		}

		return blogsCategory;
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = blogsCategory.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(blogsCategory);
			}
			else {
				listener.onBeforeUpdate(blogsCategory);
			}
		}

		blogsCategory = getPersistence().update(blogsCategory, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(blogsCategory);
			}
			else {
				listener.onAfterUpdate(blogsCategory);
			}
		}

		return blogsCategory;
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory findByPrimaryKey(
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.blogs.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory fetchByPrimaryKey(
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.List findByParentCategoryId(
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId);
	}

	public static java.util.List findByParentCategoryId(
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId, begin,
			end);
	}

	public static java.util.List findByParentCategoryId(
		java.lang.String parentCategoryId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId, begin,
			end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_First(
		java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.blogs.NoSuchCategoryException {
		return getPersistence().findByParentCategoryId_First(parentCategoryId,
			obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_Last(
		java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.blogs.NoSuchCategoryException {
		return getPersistence().findByParentCategoryId_Last(parentCategoryId,
			obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory[] findByParentCategoryId_PrevAndNext(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.blogs.NoSuchCategoryException {
		return getPersistence().findByParentCategoryId_PrevAndNext(categoryId,
			parentCategoryId, obc);
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

	public static void removeByParentCategoryId(
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByParentCategoryId(java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static BlogsCategoryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(BlogsCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static BlogsCategoryUtil _getUtil() {
		if (_util == null) {
			_util = (BlogsCategoryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = BlogsCategoryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.blogs.model.BlogsCategory"));
	private static Log _log = LogFactory.getLog(BlogsCategoryUtil.class);
	private static BlogsCategoryUtil _util;
	private BlogsCategoryPersistence _persistence;
}