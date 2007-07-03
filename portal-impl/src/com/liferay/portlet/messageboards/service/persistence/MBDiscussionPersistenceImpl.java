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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBDiscussionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBDiscussionPersistenceImpl extends BasePersistence
	implements MBDiscussionPersistence {
	public MBDiscussion create(long discussionId) {
		MBDiscussion mbDiscussion = new MBDiscussionImpl();
		mbDiscussion.setNew(true);
		mbDiscussion.setPrimaryKey(discussionId);

		return mbDiscussion;
	}

	public MBDiscussion remove(long discussionId)
		throws NoSuchDiscussionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBDiscussion mbDiscussion = (MBDiscussion)session.get(MBDiscussionImpl.class,
					new Long(discussionId));

			if (mbDiscussion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBDiscussion exists with the primary key " +
						discussionId);
				}

				throw new NoSuchDiscussionException(
					"No MBDiscussion exists with the primary key " +
					discussionId);
			}

			return remove(mbDiscussion);
		}
		catch (NoSuchDiscussionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion remove(MBDiscussion mbDiscussion)
		throws SystemException {
		FinderCache.clearCache(MBDiscussion.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(mbDiscussion);
			session.flush();

			return mbDiscussion;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws SystemException {
		return update(mbDiscussion, false);
	}

	public MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(MBDiscussion.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(mbDiscussion);
			}
			else {
				if (mbDiscussion.isNew()) {
					session.save(mbDiscussion);
				}
			}

			session.flush();
			mbDiscussion.setNew(false);

			return mbDiscussion;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion findByPrimaryKey(long discussionId)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByPrimaryKey(discussionId);

		if (mbDiscussion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBDiscussion exists with the primary key " +
					discussionId);
			}

			throw new NoSuchDiscussionException(
				"No MBDiscussion exists with the primary key " + discussionId);
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByPrimaryKey(long discussionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBDiscussion)session.get(MBDiscussionImpl.class,
				new Long(discussionId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion findByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByC_C(classNameId, classPK);

		if (mbDiscussion == null) {
			StringMaker msg = new StringMaker();
			msg.append("No MBDiscussion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDiscussionException(msg.toString());
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		String finderClassName = MBDiscussion.class.getName();
		String finderMethodName = "fetchByC_C";
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");
				query.append("classNameId = ?");
				query.append(" AND ");
				query.append("classPK = ?");
				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, classNameId);
				q.setLong(queryPos++, classPK);

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				MBDiscussion mbDiscussion = (MBDiscussion)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, mbDiscussion);

				return mbDiscussion;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (MBDiscussion)result;
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
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = findByC_C(classNameId, classPK);
		remove(mbDiscussion);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((MBDiscussion)itr.next());
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

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
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion");

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

	private static Log _log = LogFactory.getLog(MBDiscussionPersistenceImpl.class);
}