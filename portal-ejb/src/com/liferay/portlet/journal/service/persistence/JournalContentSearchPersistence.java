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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;

import com.liferay.util.StringMaker;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalContentSearchPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalContentSearchPersistence extends BasePersistence {
	public JournalContentSearch create(
		JournalContentSearchPK journalContentSearchPK) {
		JournalContentSearch journalContentSearch = new JournalContentSearchImpl();
		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(journalContentSearchPK);

		return journalContentSearch;
	}

	public JournalContentSearch remove(
		JournalContentSearchPK journalContentSearchPK)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
					journalContentSearchPK);

			if (journalContentSearch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalContentSearch exists with the primary key " +
						journalContentSearchPK);
				}

				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK);
			}

			return remove(journalContentSearch);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch remove(
		JournalContentSearch journalContentSearch) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(journalContentSearch);
			session.flush();

			return journalContentSearch;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch update(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws SystemException {
		return update(journalContentSearch, false);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch update(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(journalContentSearch);
			}
			else {
				if (journalContentSearch.isNew()) {
					session.save(journalContentSearch);
				}
			}

			session.flush();
			journalContentSearch.setNew(false);

			return journalContentSearch;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByPrimaryKey(
		JournalContentSearchPK journalContentSearchPK)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPrimaryKey(journalContentSearchPK);

		if (journalContentSearch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK);
			}

			throw new NoSuchContentSearchException(
				"No JournalContentSearch exists with the primary key " +
				journalContentSearchPK);
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByPrimaryKey(
		JournalContentSearchPK journalContentSearchPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalContentSearch)session.get(JournalContentSearchImpl.class,
				journalContentSearchPK);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByGroupId_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, long groupId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(journalContentSearchPK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public List findByOwnerId(String ownerId, int begin, int end)
		throws SystemException {
		return findByOwnerId(ownerId, begin, end, null);
	}

	public List findByOwnerId(String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public JournalContentSearch findByOwnerId_First(String ownerId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByOwnerId(ownerId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByOwnerId_Last(String ownerId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByOwnerId(ownerId);
		List list = findByOwnerId(ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByOwnerId_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String ownerId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(journalContentSearchPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public List findByL_O(String layoutId, String ownerId, int begin, int end)
		throws SystemException {
		return findByL_O(layoutId, ownerId, begin, end, null);
	}

	public List findByL_O(String layoutId, String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public JournalContentSearch findByL_O_First(String layoutId,
		String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByL_O(layoutId, ownerId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(", ");
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByL_O_Last(String layoutId, String ownerId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByL_O(layoutId, ownerId);
		List list = findByL_O(layoutId, ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(", ");
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByL_O_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String layoutId,
		String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(journalContentSearchPK);
		int count = countByL_O(layoutId, ownerId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_G_A(String ownerId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");
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

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
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

	public List findByO_G_A(String ownerId, long groupId, String articleId,
		int begin, int end) throws SystemException {
		return findByO_G_A(ownerId, groupId, articleId, begin, end, null);
	}

	public List findByO_G_A(String ownerId, long groupId, String articleId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
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

	public JournalContentSearch findByO_G_A_First(String ownerId, long groupId,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByO_G_A(ownerId, groupId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByO_G_A_Last(String ownerId, long groupId,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByO_G_A(ownerId, groupId, articleId);
		List list = findByO_G_A(ownerId, groupId, articleId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("ownerId=");
			msg.append(ownerId);
			msg.append(", ");
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByO_G_A_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String ownerId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(journalContentSearchPK);
		int count = countByO_G_A(ownerId, groupId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_G_A(String companyId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");
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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
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

	public List findByC_G_A(String companyId, long groupId, String articleId,
		int begin, int end) throws SystemException {
		return findByC_G_A(companyId, groupId, articleId, begin, end, null);
	}

	public List findByC_G_A(String companyId, long groupId, String articleId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
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

	public JournalContentSearch findByC_G_A_First(String companyId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByC_G_A(companyId, groupId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByC_G_A_Last(String companyId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByC_G_A(companyId, groupId, articleId);
		List list = findByC_G_A(companyId, groupId, articleId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByC_G_A_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String companyId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(journalContentSearchPK);
		int count = countByC_G_A(companyId, groupId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByOwnerId(String ownerId) throws SystemException {
		Iterator itr = findByOwnerId(ownerId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByL_O(String layoutId, String ownerId)
		throws SystemException {
		Iterator itr = findByL_O(layoutId, ownerId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByO_G_A(String ownerId, long groupId, String articleId)
		throws SystemException {
		Iterator itr = findByO_G_A(ownerId, groupId, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByC_G_A(String companyId, long groupId, String articleId)
		throws SystemException {
		Iterator itr = findByC_G_A(companyId, groupId, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((JournalContentSearch)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

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

	public int countByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public int countByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (layoutId == null) {
				query.append("layoutId IS NULL");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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

	public int countByO_G_A(String ownerId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (ownerId == null) {
				query.append("ownerId IS NULL");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");
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

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_G_A(String companyId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");
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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
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
				"FROM com.liferay.portlet.journal.model.JournalContentSearch");

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(JournalContentSearchPersistence.class);
}