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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="BlogsCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsCategoryUtil {
	public static final String CLASS_NAME = BlogsCategoryUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.blogs.model.BlogsCategory"));

	public static com.liferay.portlet.blogs.model.BlogsCategory create(
		java.lang.String categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory remove(
		java.lang.String categoryId)
		throws com.liferay.portlet.blogs.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		throws com.liferay.portlet.blogs.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId, begin,
			end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_First(
		java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId_First(parentCategoryId,
			obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_Last(
		java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId_Last(parentCategoryId,
			obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory[] findByParentCategoryId_PrevAndNext(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.blogs.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByParentCategoryId(
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	public static int countByParentCategoryId(java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static BlogsCategoryPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		BlogsCategoryUtil util = (BlogsCategoryUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(BlogsCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(BlogsCategoryUtil.class);
	private BlogsCategoryPersistence _persistence;
}