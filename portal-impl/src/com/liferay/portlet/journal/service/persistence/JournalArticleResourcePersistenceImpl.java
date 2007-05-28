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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalArticleResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleResourcePersistenceImpl extends BasePersistence
	implements JournalArticleResourcePersistence {
	public JournalArticleResource create(long resourcePrimKey) {
		JournalArticleResource journalArticleResource = new JournalArticleResourceImpl();
		journalArticleResource.setNew(true);
		journalArticleResource.setPrimaryKey(resourcePrimKey);

		return journalArticleResource;
	}

	public JournalArticleResource remove(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleResource journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
					new Long(resourcePrimKey));

			if (journalArticleResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalArticleResource exists with the primary key " +
						resourcePrimKey);
				}

				throw new NoSuchArticleResourceException(
					"No JournalArticleResource exists with the primary key " +
					resourcePrimKey);
			}

			return remove(journalArticleResource);
		}
		catch (NoSuchArticleResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleResource remove(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(journalArticleResource);
			session.flush();

			return journalArticleResource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticleResource update(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws SystemException {
		return update(journalArticleResource, false);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource update(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(journalArticleResource);
			}
			else {
				if (journalArticleResource.isNew()) {
					session.save(journalArticleResource);
				}
			}

			session.flush();
			journalArticleResource.setNew(false);

			return journalArticleResource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByPrimaryKey(resourcePrimKey);

		if (journalArticleResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalArticleResource exists with the primary key " +
					resourcePrimKey);
			}

			throw new NoSuchArticleResourceException(
				"No JournalArticleResource exists with the primary key " +
				resourcePrimKey);
		}

		return journalArticleResource;
	}

	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
				new Long(resourcePrimKey));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleResource findByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByG_A(groupId,
				articleId);

		if (journalArticleResource == null) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticleResource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleResourceException(msg.toString());
		}

		return journalArticleResource;
	}

	public JournalArticleResource fetchByG_A(long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			JournalArticleResource journalArticleResource = (JournalArticleResource)list.get(0);

			return journalArticleResource;
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
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticleResource ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByG_A(groupId,
				articleId);
		remove(journalArticleResource);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((JournalArticleResource)itr.next());
		}
	}

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
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
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticleResource");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(JournalArticleResourcePersistenceImpl.class);
}