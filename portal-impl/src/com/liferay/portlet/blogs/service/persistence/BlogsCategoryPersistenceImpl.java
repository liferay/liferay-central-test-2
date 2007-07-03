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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.blogs.NoSuchCategoryException;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.impl.BlogsCategoryImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsCategoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsCategoryPersistenceImpl extends BasePersistence
	implements BlogsCategoryPersistence {
	public BlogsCategory create(long categoryId) {
		BlogsCategory blogsCategory = new BlogsCategoryImpl();
		blogsCategory.setNew(true);
		blogsCategory.setPrimaryKey(categoryId);

		return blogsCategory;
	}

	public BlogsCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsCategory blogsCategory = (BlogsCategory)session.get(BlogsCategoryImpl.class,
					new Long(categoryId));

			if (blogsCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BlogsCategory exists with the primary key " +
						categoryId);
				}

				throw new NoSuchCategoryException(
					"No BlogsCategory exists with the primary key " +
					categoryId);
			}

			return remove(blogsCategory);
		}
		catch (NoSuchCategoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory remove(BlogsCategory blogsCategory)
		throws SystemException {
		FinderCache.clearCache(BlogsCategory.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(blogsCategory);
			session.flush();

			return blogsCategory;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory)
		throws SystemException {
		return update(blogsCategory, false);
	}

	public BlogsCategory update(
		com.liferay.portlet.blogs.model.BlogsCategory blogsCategory,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(BlogsCategory.class.getName());

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		BlogsCategory blogsCategory = fetchByPrimaryKey(categoryId);

		if (blogsCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BlogsCategory exists with the primary key " +
					categoryId);
			}

			throw new NoSuchCategoryException(
				"No BlogsCategory exists with the primary key " + categoryId);
		}

		return blogsCategory;
	}

	public BlogsCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (BlogsCategory)session.get(BlogsCategoryImpl.class,
				new Long(categoryId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, parentCategoryId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByParentCategoryId(long parentCategoryId, int begin, int end)
		throws SystemException {
		return findByParentCategoryId(parentCategoryId, begin, end, null);
	}

	public List findByParentCategoryId(long parentCategoryId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, parentCategoryId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsCategory findByParentCategoryId_First(long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List list = findByParentCategoryId(parentCategoryId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No BlogsCategory exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return (BlogsCategory)list.get(0);
		}
	}

	public BlogsCategory findByParentCategoryId_Last(long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByParentCategoryId(parentCategoryId);
		List list = findByParentCategoryId(parentCategoryId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No BlogsCategory exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return (BlogsCategory)list.get(0);
		}
	}

	public BlogsCategory[] findByParentCategoryId_PrevAndNext(long categoryId,
		long parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		BlogsCategory blogsCategory = findByPrimaryKey(categoryId);
		int count = countByParentCategoryId(parentCategoryId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, parentCategoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsCategory);
			BlogsCategory[] array = new BlogsCategoryImpl[3];
			array[0] = (BlogsCategory)objArray[0];
			array[1] = (BlogsCategory)objArray[1];
			array[2] = (BlogsCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portlet.blogs.model.BlogsCategory ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Iterator itr = findByParentCategoryId(parentCategoryId).iterator();

		while (itr.hasNext()) {
			BlogsCategory blogsCategory = (BlogsCategory)itr.next();
			remove(blogsCategory);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((BlogsCategory)itr.next());
		}
	}

	public int countByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsCategory WHERE ");
			query.append("parentCategoryId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, parentCategoryId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portlet.blogs.model.BlogsCategory");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(BlogsCategoryPersistenceImpl.class);
}