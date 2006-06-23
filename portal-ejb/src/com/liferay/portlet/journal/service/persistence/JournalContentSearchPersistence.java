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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.NoSuchContentSearchException;

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
 * <a href="JournalContentSearchPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchPersistence extends BasePersistence {
	public com.liferay.portlet.journal.model.JournalContentSearch create(
		JournalContentSearchPK journalContentSearchPK) {
		JournalContentSearchHBM journalContentSearchHBM = new JournalContentSearchHBM();
		journalContentSearchHBM.setNew(true);
		journalContentSearchHBM.setPrimaryKey(journalContentSearchPK);

		return JournalContentSearchHBMUtil.model(journalContentSearchHBM);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch remove(
		JournalContentSearchPK journalContentSearchPK)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)session.get(JournalContentSearchHBM.class,
					journalContentSearchPK);

			if (journalContentSearchHBM == null) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK.toString());
				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK.toString());
			}

			session.delete(journalContentSearchHBM);
			session.flush();

			return JournalContentSearchHBMUtil.model(journalContentSearchHBM);
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
		Session session = null;

		try {
			if (journalContentSearch.isNew() ||
					journalContentSearch.isModified()) {
				session = openSession();

				if (journalContentSearch.isNew()) {
					JournalContentSearchHBM journalContentSearchHBM = new JournalContentSearchHBM();
					journalContentSearchHBM.setPortletId(journalContentSearch.getPortletId());
					journalContentSearchHBM.setLayoutId(journalContentSearch.getLayoutId());
					journalContentSearchHBM.setOwnerId(journalContentSearch.getOwnerId());
					journalContentSearchHBM.setCompanyId(journalContentSearch.getCompanyId());
					journalContentSearchHBM.setArticleId(journalContentSearch.getArticleId());
					session.save(journalContentSearchHBM);
					session.flush();
				}
				else {
					JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)session.get(JournalContentSearchHBM.class,
							journalContentSearch.getPrimaryKey());

					if (journalContentSearchHBM != null) {
						journalContentSearchHBM.setCompanyId(journalContentSearch.getCompanyId());
						journalContentSearchHBM.setArticleId(journalContentSearch.getArticleId());
						session.flush();
					}
					else {
						journalContentSearchHBM = new JournalContentSearchHBM();
						journalContentSearchHBM.setPortletId(journalContentSearch.getPortletId());
						journalContentSearchHBM.setLayoutId(journalContentSearch.getLayoutId());
						journalContentSearchHBM.setOwnerId(journalContentSearch.getOwnerId());
						journalContentSearchHBM.setCompanyId(journalContentSearch.getCompanyId());
						journalContentSearchHBM.setArticleId(journalContentSearch.getArticleId());
						session.save(journalContentSearchHBM);
						session.flush();
					}
				}

				journalContentSearch.setNew(false);
				journalContentSearch.setModified(false);
			}

			return journalContentSearch;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		JournalContentSearchPK journalContentSearchPK)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)session.get(JournalContentSearchHBM.class,
					journalContentSearchPK);

			if (journalContentSearchHBM == null) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK.toString());
				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					journalContentSearchPK.toString());
			}

			return JournalContentSearchHBMUtil.model(journalContentSearchHBM);
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

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public List findByOwnerId(String ownerId, int begin, int end)
		throws SystemException {
		return findByOwnerId(ownerId, begin, end, null);
	}

	public List findByOwnerId(String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_First(
		String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByOwnerId(ownerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_Last(
		String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByOwnerId(ownerId);
		List list = findByOwnerId(ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByOwnerId_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String ownerId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch =
			findByPrimaryKey(journalContentSearchPK);
		int count = countByOwnerId(ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch,
					JournalContentSearchHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalContentSearch[] array = new com.liferay.portlet.journal.model.JournalContentSearch[3];
			array[0] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[2];

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

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public List findByL_O(String layoutId, String ownerId, int begin, int end)
		throws SystemException {
		return findByL_O(layoutId, ownerId, begin, end, null);
	}

	public List findByL_O(String layoutId, String ownerId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public com.liferay.portlet.journal.model.JournalContentSearch findByL_O_First(
		String layoutId, String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByL_O(layoutId, ownerId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += ", ";
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch findByL_O_Last(
		String layoutId, String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByL_O(layoutId, ownerId);
		List list = findByL_O(layoutId, ownerId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "layoutId=";
			msg += layoutId;
			msg += ", ";
			msg += "ownerId=";
			msg += ownerId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByL_O_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String layoutId,
		String ownerId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch =
			findByPrimaryKey(journalContentSearchPK);
		int count = countByL_O(layoutId, ownerId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch,
					JournalContentSearchHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalContentSearch[] array = new com.liferay.portlet.journal.model.JournalContentSearch[3];
			array[0] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByO_A(String ownerId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public List findByO_A(String ownerId, String articleId, int begin, int end)
		throws SystemException {
		return findByO_A(ownerId, articleId, begin, end, null);
	}

	public List findByO_A(String ownerId, String articleId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public com.liferay.portlet.journal.model.JournalContentSearch findByO_A_First(
		String ownerId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByO_A(ownerId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch findByO_A_Last(
		String ownerId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByO_A(ownerId, articleId);
		List list = findByO_A(ownerId, articleId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "ownerId=";
			msg += ownerId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByO_A_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String ownerId,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch =
			findByPrimaryKey(journalContentSearchPK);
		int count = countByO_A(ownerId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch,
					JournalContentSearchHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalContentSearch[] array = new com.liferay.portlet.journal.model.JournalContentSearch[3];
			array[0] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_A(String companyId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public List findByC_A(String companyId, String articleId, int begin, int end)
		throws SystemException {
		return findByC_A(companyId, articleId, begin, end, null);
	}

	public List findByC_A(String companyId, String articleId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public com.liferay.portlet.journal.model.JournalContentSearch findByC_A_First(
		String companyId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List list = findByC_A(companyId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch findByC_A_Last(
		String companyId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByC_A(companyId, articleId);
		List list = findByC_A(companyId, articleId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalContentSearch exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContentSearchException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalContentSearch)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByC_A_PrevAndNext(
		JournalContentSearchPK journalContentSearchPK, String companyId,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch =
			findByPrimaryKey(journalContentSearchPK);
		int count = countByC_A(companyId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch,
					JournalContentSearchHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalContentSearch[] array = new com.liferay.portlet.journal.model.JournalContentSearch[3];
			array[0] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalContentSearch)objArray[2];

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
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				list.add(JournalContentSearchHBMUtil.model(
						journalContentSearchHBM));
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

	public void removeByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				session.delete(journalContentSearchHBM);
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

	public void removeByL_O(String layoutId, String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (layoutId != null) {
				q.setString(queryPos++, layoutId);
			}

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				session.delete(journalContentSearchHBM);
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

	public void removeByO_A(String ownerId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				session.delete(journalContentSearchHBM);
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

	public void removeByC_A(String companyId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalContentSearchHBM journalContentSearchHBM = (JournalContentSearchHBM)itr.next();
				session.delete(journalContentSearchHBM);
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

	public int countByOwnerId(String ownerId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (layoutId == null) {
				query.append("layoutId is null");
			}
			else {
				query.append("layoutId = ?");
			}

			query.append(" AND ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	public int countByO_A(String ownerId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (ownerId == null) {
				query.append("ownerId is null");
			}
			else {
				query.append("ownerId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (ownerId != null) {
				q.setString(queryPos++, ownerId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_A(String companyId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalContentSearch IN CLASS com.liferay.portlet.journal.service.persistence.JournalContentSearchHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId is null");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactory.getLog(JournalContentSearchPersistence.class);
}