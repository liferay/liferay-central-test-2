/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.blogs.NoSuchCategoryException;
import com.liferay.portlet.blogs.model.BlogsCategory;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsCategoryPersistence extends BasePersistence {
	public BlogsCategory create(String categoryId) {
		BlogsCategory blogsCategory = new BlogsCategory();
		blogsCategory.setNew(true);
		blogsCategory.setPrimaryKey(categoryId);

		return blogsCategory;
	}

	public BlogsCategory remove(String categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsCategory blogsCategory = (BlogsCategory)session.get(BlogsCategory.class,
					categoryId);

			if (blogsCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BlogsCategory exists with the primary key " +
						categoryId.toString());
				}

				throw new NoSuchCategoryException(
					"No BlogsCategory exists with the primary key " +
					categoryId.toString());
			}

			return remove(blogsCategory);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory remove(BlogsCategory blogsCategory)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(blogsCategory);
			session.flush();

			return blogsCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory)
		throws SystemException {
		return update(blogsCategory, false);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(blogsCategory);
			}
			else {
				if (blogsCategory.isNew()) {
					session.save(blogsCategory);
				}
			}

			session.flush();
			blogsCategory.setNew(false);

			return blogsCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory findByPrimaryKey(String categoryId)
		throws NoSuchCategoryException, SystemException {
		BlogsCategory blogsCategory = fetchByPrimaryKey(categoryId);

		if (blogsCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BlogsCategory exists with the primary key " +
					categoryId.toString());
			}

			throw new NoSuchCategoryException(
				"No BlogsCategory exists with the primary key " +
				categoryId.toString());
		}

		return blogsCategory;
	}

	public BlogsCategory fetchByPrimaryKey(String categoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (BlogsCategory)session.get(BlogsCategory.class, categoryId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByParentCategoryId(String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByParentCategoryId(String parentCategoryId, int begin,
		int end) throws SystemException {
		return findByParentCategoryId(parentCategoryId, begin, end, null);
	}

	public List findByParentCategoryId(String parentCategoryId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory findByParentCategoryId_First(String parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List list = findByParentCategoryId(parentCategoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No BlogsCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (BlogsCategory)list.get(0);
		}
	}

	public BlogsCategory findByParentCategoryId_Last(String parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByParentCategoryId(parentCategoryId);
		List list = findByParentCategoryId(parentCategoryId, count - 1, count,
				obc);

		if (list.size() == 0) {
			String msg = "No BlogsCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (BlogsCategory)list.get(0);
		}
	}

	public BlogsCategory[] findByParentCategoryId_PrevAndNext(
		String categoryId, String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		BlogsCategory blogsCategory = findByPrimaryKey(categoryId);
		int count = countByParentCategoryId(parentCategoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsCategory);
			BlogsCategory[] array = new BlogsCategory[3];
			array[0] = (BlogsCategory)objArray[0];
			array[1] = (BlogsCategory)objArray[1];
			array[2] = (BlogsCategory)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portlet.blogs.model.BlogsCategory ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByParentCategoryId(String parentCategoryId)
		throws SystemException {
		Iterator itr = findByParentCategoryId(parentCategoryId).iterator();

		while (itr.hasNext()) {
			BlogsCategory blogsCategory = (BlogsCategory)itr.next();
			remove(blogsCategory);
		}
	}

	public int countByParentCategoryId(String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(BlogsCategoryPersistence.class);
}