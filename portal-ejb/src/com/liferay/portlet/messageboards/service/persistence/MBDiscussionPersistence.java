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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;

import com.liferay.util.StringPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBDiscussionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBDiscussionPersistence extends BasePersistence {
	public MBDiscussion create(String discussionId) {
		MBDiscussion mbDiscussion = new MBDiscussion();
		mbDiscussion.setNew(true);
		mbDiscussion.setPrimaryKey(discussionId);

		return mbDiscussion;
	}

	public MBDiscussion remove(String discussionId)
		throws NoSuchDiscussionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBDiscussion mbDiscussion = (MBDiscussion)session.get(MBDiscussion.class,
					discussionId);

			if (mbDiscussion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBDiscussion exists with the primary key " +
						discussionId.toString());
				}

				throw new NoSuchDiscussionException(
					"No MBDiscussion exists with the primary key " +
					discussionId.toString());
			}

			return remove(mbDiscussion);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion remove(MBDiscussion mbDiscussion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(mbDiscussion);
			session.flush();
			MBDiscussionPool.removeByC_C(mbDiscussion.getClassName(),
				mbDiscussion.getClassPK());

			return mbDiscussion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.saveOrUpdate(mbDiscussion);
			session.flush();
			mbDiscussion.setNew(false);

			return mbDiscussion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion findByPrimaryKey(String discussionId)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByPrimaryKey(discussionId);

		if (mbDiscussion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBDiscussion exists with the primary key " +
					discussionId.toString());
			}

			throw new NoSuchDiscussionException(
				"No MBDiscussion exists with the primary key " +
				discussionId.toString());
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByPrimaryKey(String discussionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBDiscussion)session.get(MBDiscussion.class, discussionId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion findByC_C(String className, String classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByC_C(className, classPK);

		if (mbDiscussion == null) {
			String msg = "No MBDiscussion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchDiscussionException(msg);
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByC_C(String className, String classPK)
		throws SystemException {
		String pk = MBDiscussionPool.getByC_C(className, classPK);

		if (pk != null) {
			MBDiscussion mbDiscussion = fetchByPrimaryKey(pk);

			if (mbDiscussion != null) {
				return mbDiscussion;
			}
		}

		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			MBDiscussion mbDiscussion = (MBDiscussion)list.get(0);
			MBDiscussionPool.putByC_C(className, classPK,
				mbDiscussion.getPrimaryKey());

			return mbDiscussion;
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
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion ");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C(String className, String classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = findByC_C(className, classPK);
		remove(mbDiscussion);
	}

	public int countByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
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

	private static Log _log = LogFactory.getLog(MBDiscussionPersistence.class);
}