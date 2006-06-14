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

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsCategoryPersistence extends BasePersistence {
	public com.liferay.portlet.blogs.model.BlogsCategory create(
		String categoryId) {
		BlogsCategoryHBM blogsCategoryHBM = new BlogsCategoryHBM();
		blogsCategoryHBM.setNew(true);
		blogsCategoryHBM.setPrimaryKey(categoryId);

		return BlogsCategoryHBMUtil.model(blogsCategoryHBM);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory remove(
		String categoryId) throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)session.get(BlogsCategoryHBM.class,
					categoryId);

			if (blogsCategoryHBM == null) {
				_log.warn("No BlogsCategory exists with the primary key " +
					categoryId.toString());
				throw new NoSuchCategoryException(
					"No BlogsCategory exists with the primary key " +
					categoryId.toString());
			}

			session.delete(blogsCategoryHBM);
			session.flush();

			return BlogsCategoryHBMUtil.model(blogsCategoryHBM);
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
		Session session = null;

		try {
			if (blogsCategory.isNew() || blogsCategory.isModified()) {
				session = openSession();

				if (blogsCategory.isNew()) {
					BlogsCategoryHBM blogsCategoryHBM = new BlogsCategoryHBM();
					blogsCategoryHBM.setCategoryId(blogsCategory.getCategoryId());
					blogsCategoryHBM.setCompanyId(blogsCategory.getCompanyId());
					blogsCategoryHBM.setUserId(blogsCategory.getUserId());
					blogsCategoryHBM.setUserName(blogsCategory.getUserName());
					blogsCategoryHBM.setCreateDate(blogsCategory.getCreateDate());
					blogsCategoryHBM.setModifiedDate(blogsCategory.getModifiedDate());
					blogsCategoryHBM.setParentCategoryId(blogsCategory.getParentCategoryId());
					blogsCategoryHBM.setName(blogsCategory.getName());
					blogsCategoryHBM.setDescription(blogsCategory.getDescription());
					session.save(blogsCategoryHBM);
					session.flush();
				}
				else {
					BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)session.get(BlogsCategoryHBM.class,
							blogsCategory.getPrimaryKey());

					if (blogsCategoryHBM != null) {
						blogsCategoryHBM.setCompanyId(blogsCategory.getCompanyId());
						blogsCategoryHBM.setUserId(blogsCategory.getUserId());
						blogsCategoryHBM.setUserName(blogsCategory.getUserName());
						blogsCategoryHBM.setCreateDate(blogsCategory.getCreateDate());
						blogsCategoryHBM.setModifiedDate(blogsCategory.getModifiedDate());
						blogsCategoryHBM.setParentCategoryId(blogsCategory.getParentCategoryId());
						blogsCategoryHBM.setName(blogsCategory.getName());
						blogsCategoryHBM.setDescription(blogsCategory.getDescription());
						session.flush();
					}
					else {
						blogsCategoryHBM = new BlogsCategoryHBM();
						blogsCategoryHBM.setCategoryId(blogsCategory.getCategoryId());
						blogsCategoryHBM.setCompanyId(blogsCategory.getCompanyId());
						blogsCategoryHBM.setUserId(blogsCategory.getUserId());
						blogsCategoryHBM.setUserName(blogsCategory.getUserName());
						blogsCategoryHBM.setCreateDate(blogsCategory.getCreateDate());
						blogsCategoryHBM.setModifiedDate(blogsCategory.getModifiedDate());
						blogsCategoryHBM.setParentCategoryId(blogsCategory.getParentCategoryId());
						blogsCategoryHBM.setName(blogsCategory.getName());
						blogsCategoryHBM.setDescription(blogsCategory.getDescription());
						session.save(blogsCategoryHBM);
						session.flush();
					}
				}

				blogsCategory.setNew(false);
				blogsCategory.setModified(false);
			}

			return blogsCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsCategory findByPrimaryKey(
		String categoryId) throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)session.get(BlogsCategoryHBM.class,
					categoryId);

			if (blogsCategoryHBM == null) {
				_log.warn("No BlogsCategory exists with the primary key " +
					categoryId.toString());
				throw new NoSuchCategoryException(
					"No BlogsCategory exists with the primary key " +
					categoryId.toString());
			}

			return BlogsCategoryHBMUtil.model(blogsCategoryHBM);
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
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, parentCategoryId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)itr.next();
				list.add(BlogsCategoryHBMUtil.model(blogsCategoryHBM));
			}

			return list;
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
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, parentCategoryId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)itr.next();
				list.add(BlogsCategoryHBMUtil.model(blogsCategoryHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_First(
		String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
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
			return (com.liferay.portlet.blogs.model.BlogsCategory)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsCategory findByParentCategoryId_Last(
		String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
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
			return (com.liferay.portlet.blogs.model.BlogsCategory)list.get(0);
		}
	}

	public com.liferay.portlet.blogs.model.BlogsCategory[] findByParentCategoryId_PrevAndNext(
		String categoryId, String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory = findByPrimaryKey(categoryId);
		int count = countByParentCategoryId(parentCategoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, parentCategoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsCategory, BlogsCategoryHBMUtil.getInstance());
			com.liferay.portlet.blogs.model.BlogsCategory[] array = new com.liferay.portlet.blogs.model.BlogsCategory[3];
			array[0] = (com.liferay.portlet.blogs.model.BlogsCategory)objArray[0];
			array[1] = (com.liferay.portlet.blogs.model.BlogsCategory)objArray[1];
			array[2] = (com.liferay.portlet.blogs.model.BlogsCategory)objArray[2];

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
			query.append(
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)itr.next();
				list.add(BlogsCategoryHBMUtil.model(blogsCategoryHBM));
			}

			return list;
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
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, parentCategoryId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				BlogsCategoryHBM blogsCategoryHBM = (BlogsCategoryHBM)itr.next();
				session.delete(blogsCategoryHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
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
				"FROM BlogsCategory IN CLASS com.liferay.portlet.blogs.service.persistence.BlogsCategoryHBM WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, parentCategoryId);

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

	private static Log _log = LogFactory.getLog(BlogsCategoryPersistence.class);
}