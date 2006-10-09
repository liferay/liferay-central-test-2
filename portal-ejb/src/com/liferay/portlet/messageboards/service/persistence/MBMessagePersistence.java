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

import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;

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
 * <a href="MBMessagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessagePersistence extends BasePersistence {
	public MBMessage create(MBMessagePK mbMessagePK) {
		MBMessage mbMessage = new MBMessage();
		mbMessage.setNew(true);
		mbMessage.setPrimaryKey(mbMessagePK);

		return mbMessage;
	}

	public MBMessage remove(MBMessagePK mbMessagePK)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessage mbMessage = (MBMessage)session.get(MBMessage.class,
					mbMessagePK);

			if (mbMessage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBMessage exists with the primary key " +
						mbMessagePK.toString());
				}

				throw new NoSuchMessageException(
					"No MBMessage exists with the primary key " +
					mbMessagePK.toString());
			}

			return remove(mbMessage);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage remove(MBMessage mbMessage) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(mbMessage);
			session.flush();

			return mbMessage;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws SystemException {
		return update(mbMessage, false);
	}

	public com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(mbMessage);
			}
			else {
				if (mbMessage.isNew()) {
					session.save(mbMessage);
				}
			}

			session.flush();
			mbMessage.setNew(false);

			return mbMessage;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage findByPrimaryKey(MBMessagePK mbMessagePK)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByPrimaryKey(mbMessagePK);

		if (mbMessage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBMessage exists with the primary key " +
					mbMessagePK.toString());
			}

			throw new NoSuchMessageException(
				"No MBMessage exists with the primary key " +
				mbMessagePK.toString());
		}

		return mbMessage;
	}

	public MBMessage fetchByPrimaryKey(MBMessagePK mbMessagePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBMessage)session.get(MBMessage.class, mbMessagePK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC").append(", ");
			query.append("messageId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public List findByCategoryId(String categoryId, int begin, int end)
		throws SystemException {
		return findByCategoryId(categoryId, begin, end, null);
	}

	public List findByCategoryId(String categoryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public MBMessage findByCategoryId_First(String categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage findByCategoryId_Last(String categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByCategoryId(categoryId);
		List list = findByCategoryId(categoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage[] findByCategoryId_PrevAndNext(MBMessagePK mbMessagePK,
		String categoryId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(mbMessagePK);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);
			MBMessage[] array = new MBMessage[3];
			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByThreadId(String threadId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC").append(", ");
			query.append("messageId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
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

	public List findByThreadId(String threadId, int begin, int end)
		throws SystemException {
		return findByThreadId(threadId, begin, end, null);
	}

	public List findByThreadId(String threadId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
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

	public MBMessage findByThreadId_First(String threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List list = findByThreadId(threadId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "threadId=";
			msg += threadId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage findByThreadId_Last(String threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByThreadId(threadId);
		List list = findByThreadId(threadId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "threadId=";
			msg += threadId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage[] findByThreadId_PrevAndNext(MBMessagePK mbMessagePK,
		String threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(mbMessagePK);
		int count = countByThreadId(threadId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);
			MBMessage[] array = new MBMessage[3];
			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByT_P(String threadId, String parentMessageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" AND ");

			if (parentMessageId == null) {
				query.append("parentMessageId IS NULL");
			}
			else {
				query.append("parentMessageId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC").append(", ");
			query.append("messageId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
			}

			if (parentMessageId != null) {
				q.setString(queryPos++, parentMessageId);
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

	public List findByT_P(String threadId, String parentMessageId, int begin,
		int end) throws SystemException {
		return findByT_P(threadId, parentMessageId, begin, end, null);
	}

	public List findByT_P(String threadId, String parentMessageId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" AND ");

			if (parentMessageId == null) {
				query.append("parentMessageId IS NULL");
			}
			else {
				query.append("parentMessageId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
			}

			if (parentMessageId != null) {
				q.setString(queryPos++, parentMessageId);
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

	public MBMessage findByT_P_First(String threadId, String parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List list = findByT_P(threadId, parentMessageId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "threadId=";
			msg += threadId;
			msg += ", ";
			msg += "parentMessageId=";
			msg += parentMessageId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage findByT_P_Last(String threadId, String parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByT_P(threadId, parentMessageId);
		List list = findByT_P(threadId, parentMessageId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessage exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "threadId=";
			msg += threadId;
			msg += ", ";
			msg += "parentMessageId=";
			msg += parentMessageId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageException(msg);
		}
		else {
			return (MBMessage)list.get(0);
		}
	}

	public MBMessage[] findByT_P_PrevAndNext(MBMessagePK mbMessagePK,
		String threadId, String parentMessageId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(mbMessagePK);
		int count = countByT_P(threadId, parentMessageId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" AND ");

			if (parentMessageId == null) {
				query.append("parentMessageId IS NULL");
			}
			else {
				query.append("parentMessageId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
			}

			if (parentMessageId != null) {
				q.setString(queryPos++, parentMessageId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);
			MBMessage[] array = new MBMessage[3];
			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

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

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC").append(", ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCategoryId(String categoryId) throws SystemException {
		Iterator itr = findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			MBMessage mbMessage = (MBMessage)itr.next();
			remove(mbMessage);
		}
	}

	public void removeByThreadId(String threadId) throws SystemException {
		Iterator itr = findByThreadId(threadId).iterator();

		while (itr.hasNext()) {
			MBMessage mbMessage = (MBMessage)itr.next();
			remove(mbMessage);
		}
	}

	public void removeByT_P(String threadId, String parentMessageId)
		throws SystemException {
		Iterator itr = findByT_P(threadId, parentMessageId).iterator();

		while (itr.hasNext()) {
			MBMessage mbMessage = (MBMessage)itr.next();
			remove(mbMessage);
		}
	}

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (categoryId == null) {
				query.append("categoryId IS NULL");
			}
			else {
				query.append("categoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (categoryId != null) {
				q.setString(queryPos++, categoryId);
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

	public int countByThreadId(String threadId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
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

	public int countByT_P(String threadId, String parentMessageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			if (threadId == null) {
				query.append("threadId IS NULL");
			}
			else {
				query.append("threadId = ?");
			}

			query.append(" AND ");

			if (parentMessageId == null) {
				query.append("parentMessageId IS NULL");
			}
			else {
				query.append("parentMessageId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (threadId != null) {
				q.setString(queryPos++, threadId);
			}

			if (parentMessageId != null) {
				q.setString(queryPos++, parentMessageId);
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

	private static Log _log = LogFactory.getLog(MBMessagePersistence.class);
}