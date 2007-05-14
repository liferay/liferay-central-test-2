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

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch = new JournalContentSearchImpl();
		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		return journalContentSearch;
	}

	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
					new Long(contentSearchId));

			if (journalContentSearch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalContentSearch exists with the primary key " +
						contentSearchId);
				}

				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			return remove(journalContentSearch);
		}
		catch (NoSuchContentSearchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPrimaryKey(contentSearchId);

		if (journalContentSearch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			throw new NoSuchContentSearchException(
				"No JournalContentSearch exists with the primary key " +
				contentSearchId);
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByPrimaryKey(long contentSearchId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalContentSearch)session.get(JournalContentSearchImpl.class,
				new Long(contentSearchId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(long groupId, boolean privateLayout, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, privateLayout, begin, end, null);
	}

	public List findByG_P(long groupId, boolean privateLayout, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByG_P(groupId, privateLayout, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P(groupId, privateLayout);
		List list = findByG_P(groupId, privateLayout, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_PrevAndNext(long contentSearchId,
		long groupId, boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);
		int count = countByG_P(groupId, privateLayout);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_G_A(long companyId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("companyId = ?");
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
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_G_A(long companyId, long groupId, String articleId,
		int begin, int end) throws SystemException {
		return findByC_G_A(companyId, groupId, articleId, begin, end, null);
	}

	public List findByC_G_A(long companyId, long groupId, String articleId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("companyId = ?");
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
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByC_G_A_First(long companyId, long groupId,
		String articleId, OrderByComparator obc)
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

	public JournalContentSearch findByC_G_A_Last(long companyId, long groupId,
		String articleId, OrderByComparator obc)
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
		long contentSearchId, long companyId, long groupId, String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);
		int count = countByC_G_A(companyId, groupId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("companyId = ?");
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
			q.setLong(queryPos++, companyId);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_L(long groupId, boolean privateLayout, long layoutId,
		int begin, int end) throws SystemException {
		return findByG_P_L(groupId, privateLayout, layoutId, begin, end, null);
	}

	public List findByG_P_L(long groupId, boolean privateLayout, long layoutId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByG_P_L(groupId, privateLayout, layoutId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(", ");
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_L(groupId, privateLayout, layoutId);
		List list = findByG_P_L(groupId, privateLayout, layoutId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(", ");
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return (JournalContentSearch)list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);
		int count = countByG_P_L(groupId, privateLayout, layoutId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);
			JournalContentSearch[] array = new JournalContentSearchImpl[3];
			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setBoolean(queryPos++, privateLayout);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P_A(long groupId, boolean privateLayout,
		String articleId, int begin, int end) throws SystemException {
		return findByG_P_A(groupId, privateLayout, articleId, begin, end, null);
	}

	public List findByG_P_A(long groupId, boolean privateLayout,
		String articleId, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByG_P_A(groupId, privateLayout, articleId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
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

	public JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_A(groupId, privateLayout, articleId);
		List list = findByG_P_A(groupId, privateLayout, articleId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
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

	public JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);
		int count = countByG_P_A(groupId, privateLayout, articleId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalContentSearch exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("privateLayout=");
			msg.append(privateLayout);
			msg.append(", ");
			msg.append("layoutId=");
			msg.append(layoutId);
			msg.append(", ");
			msg.append("portletId=");
			msg.append(portletId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentSearchException(msg.toString());
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

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
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			if (portletId != null) {
				q.setString(queryPos++, portletId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			JournalContentSearch journalContentSearch = (JournalContentSearch)list.get(0);

			return journalContentSearch;
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
				"FROM com.liferay.portlet.journal.model.JournalContentSearch ");

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

	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Iterator itr = findByG_P(groupId, privateLayout).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByC_G_A(long companyId, long groupId, String articleId)
		throws SystemException {
		Iterator itr = findByC_G_A(companyId, groupId, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Iterator itr = findByG_P_L(groupId, privateLayout, layoutId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		Iterator itr = findByG_P_A(groupId, privateLayout, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)itr.next();
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);
		remove(journalContentSearch);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((JournalContentSearch)itr.next());
		}
	}

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);

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

	public int countByC_G_A(long companyId, long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("companyId = ?");
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
			q.setLong(queryPos++, companyId);
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

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

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

	public int countByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
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
			q.setBoolean(queryPos++, privateLayout);

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

	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("privateLayout = ?");
			query.append(" AND ");
			query.append("layoutId = ?");
			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

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
			q.setBoolean(queryPos++, privateLayout);
			q.setLong(queryPos++, layoutId);

			if (portletId != null) {
				q.setString(queryPos++, portletId);
			}

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(JournalContentSearchPersistence.class);
}