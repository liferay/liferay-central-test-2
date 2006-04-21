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

import com.liferay.portlet.journal.NoSuchArticleException;

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
 * <a href="JournalArticlePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticlePersistence extends BasePersistence {
	public com.liferay.portlet.journal.model.JournalArticle create(
		JournalArticlePK journalArticlePK) {
		JournalArticleHBM journalArticleHBM = new JournalArticleHBM();
		journalArticleHBM.setNew(true);
		journalArticleHBM.setPrimaryKey(journalArticlePK);

		return JournalArticleHBMUtil.model(journalArticleHBM);
	}

	public com.liferay.portlet.journal.model.JournalArticle remove(
		JournalArticlePK journalArticlePK)
		throws NoSuchArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleHBM journalArticleHBM = (JournalArticleHBM)session.get(JournalArticleHBM.class,
					journalArticlePK);

			if (journalArticleHBM == null) {
				_log.warn("No JournalArticle exists with the primary key " +
					journalArticlePK.toString());
				throw new NoSuchArticleException(
					"No JournalArticle exists with the primary key " +
					journalArticlePK.toString());
			}

			session.delete(journalArticleHBM);
			session.flush();

			return JournalArticleHBMUtil.model(journalArticleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws SystemException {
		Session session = null;

		try {
			if (journalArticle.isNew() || journalArticle.isModified()) {
				session = openSession();

				if (journalArticle.isNew()) {
					JournalArticleHBM journalArticleHBM = new JournalArticleHBM();
					journalArticleHBM.setCompanyId(journalArticle.getCompanyId());
					journalArticleHBM.setArticleId(journalArticle.getArticleId());
					journalArticleHBM.setVersion(journalArticle.getVersion());
					journalArticleHBM.setGroupId(journalArticle.getGroupId());
					journalArticleHBM.setUserId(journalArticle.getUserId());
					journalArticleHBM.setUserName(journalArticle.getUserName());
					journalArticleHBM.setCreateDate(journalArticle.getCreateDate());
					journalArticleHBM.setModifiedDate(journalArticle.getModifiedDate());
					journalArticleHBM.setTitle(journalArticle.getTitle());
					journalArticleHBM.setContent(journalArticle.getContent());
					journalArticleHBM.setType(journalArticle.getType());
					journalArticleHBM.setStructureId(journalArticle.getStructureId());
					journalArticleHBM.setTemplateId(journalArticle.getTemplateId());
					journalArticleHBM.setDisplayDate(journalArticle.getDisplayDate());
					journalArticleHBM.setApproved(journalArticle.getApproved());
					journalArticleHBM.setApprovedByUserId(journalArticle.getApprovedByUserId());
					journalArticleHBM.setApprovedByUserName(journalArticle.getApprovedByUserName());
					journalArticleHBM.setApprovedDate(journalArticle.getApprovedDate());
					journalArticleHBM.setExpired(journalArticle.getExpired());
					journalArticleHBM.setExpirationDate(journalArticle.getExpirationDate());
					journalArticleHBM.setReviewDate(journalArticle.getReviewDate());
					session.save(journalArticleHBM);
					session.flush();
				}
				else {
					JournalArticleHBM journalArticleHBM = (JournalArticleHBM)session.get(JournalArticleHBM.class,
							journalArticle.getPrimaryKey());

					if (journalArticleHBM != null) {
						journalArticleHBM.setGroupId(journalArticle.getGroupId());
						journalArticleHBM.setUserId(journalArticle.getUserId());
						journalArticleHBM.setUserName(journalArticle.getUserName());
						journalArticleHBM.setCreateDate(journalArticle.getCreateDate());
						journalArticleHBM.setModifiedDate(journalArticle.getModifiedDate());
						journalArticleHBM.setTitle(journalArticle.getTitle());
						journalArticleHBM.setContent(journalArticle.getContent());
						journalArticleHBM.setType(journalArticle.getType());
						journalArticleHBM.setStructureId(journalArticle.getStructureId());
						journalArticleHBM.setTemplateId(journalArticle.getTemplateId());
						journalArticleHBM.setDisplayDate(journalArticle.getDisplayDate());
						journalArticleHBM.setApproved(journalArticle.getApproved());
						journalArticleHBM.setApprovedByUserId(journalArticle.getApprovedByUserId());
						journalArticleHBM.setApprovedByUserName(journalArticle.getApprovedByUserName());
						journalArticleHBM.setApprovedDate(journalArticle.getApprovedDate());
						journalArticleHBM.setExpired(journalArticle.getExpired());
						journalArticleHBM.setExpirationDate(journalArticle.getExpirationDate());
						journalArticleHBM.setReviewDate(journalArticle.getReviewDate());
						session.flush();
					}
					else {
						journalArticleHBM = new JournalArticleHBM();
						journalArticleHBM.setCompanyId(journalArticle.getCompanyId());
						journalArticleHBM.setArticleId(journalArticle.getArticleId());
						journalArticleHBM.setVersion(journalArticle.getVersion());
						journalArticleHBM.setGroupId(journalArticle.getGroupId());
						journalArticleHBM.setUserId(journalArticle.getUserId());
						journalArticleHBM.setUserName(journalArticle.getUserName());
						journalArticleHBM.setCreateDate(journalArticle.getCreateDate());
						journalArticleHBM.setModifiedDate(journalArticle.getModifiedDate());
						journalArticleHBM.setTitle(journalArticle.getTitle());
						journalArticleHBM.setContent(journalArticle.getContent());
						journalArticleHBM.setType(journalArticle.getType());
						journalArticleHBM.setStructureId(journalArticle.getStructureId());
						journalArticleHBM.setTemplateId(journalArticle.getTemplateId());
						journalArticleHBM.setDisplayDate(journalArticle.getDisplayDate());
						journalArticleHBM.setApproved(journalArticle.getApproved());
						journalArticleHBM.setApprovedByUserId(journalArticle.getApprovedByUserId());
						journalArticleHBM.setApprovedByUserName(journalArticle.getApprovedByUserName());
						journalArticleHBM.setApprovedDate(journalArticle.getApprovedDate());
						journalArticleHBM.setExpired(journalArticle.getExpired());
						journalArticleHBM.setExpirationDate(journalArticle.getExpirationDate());
						journalArticleHBM.setReviewDate(journalArticle.getReviewDate());
						session.save(journalArticleHBM);
						session.flush();
					}
				}

				journalArticle.setNew(false);
				journalArticle.setModified(false);
			}

			return journalArticle;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		JournalArticlePK journalArticlePK)
		throws NoSuchArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleHBM journalArticleHBM = (JournalArticleHBM)session.get(JournalArticleHBM.class,
					journalArticlePK);

			if (journalArticleHBM == null) {
				_log.warn("No JournalArticle exists with the primary key " +
					journalArticlePK.toString());
				throw new NoSuchArticleException(
					"No JournalArticle exists with the primary key " +
					journalArticlePK.toString());
			}

			return JournalArticleHBMUtil.model(journalArticleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByCompanyId_PrevAndNext(
		JournalArticlePK journalArticlePK, String companyId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByGroupId_PrevAndNext(
		JournalArticlePK journalArticlePK, String groupId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

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
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByC_A_First(
		String companyId, String articleId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByC_A(companyId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByC_A_Last(
		String companyId, String articleId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByC_A(companyId, articleId);
		List list = findByC_A(companyId, articleId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_A_PrevAndNext(
		JournalArticlePK journalArticlePK, String companyId, String articleId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByC_A(companyId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_S(String companyId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public List findByC_S(String companyId, String structureId, int begin,
		int end) throws SystemException {
		return findByC_S(companyId, structureId, begin, end, null);
	}

	public List findByC_S(String companyId, String structureId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByC_S_First(
		String companyId, String structureId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByC_S(companyId, structureId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "structureId=";
			msg += structureId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByC_S_Last(
		String companyId, String structureId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByC_S(companyId, structureId);
		List list = findByC_S(companyId, structureId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "structureId=";
			msg += structureId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_S_PrevAndNext(
		JournalArticlePK journalArticlePK, String companyId,
		String structureId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByC_S(companyId, structureId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_T(String companyId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("templateId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, templateId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public List findByC_T(String companyId, String templateId, int begin,
		int end) throws SystemException {
		return findByC_T(companyId, templateId, begin, end, null);
	}

	public List findByC_T(String companyId, String templateId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("templateId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, templateId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByC_T_First(
		String companyId, String templateId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByC_T(companyId, templateId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "templateId=";
			msg += templateId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByC_T_Last(
		String companyId, String templateId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByC_T(companyId, templateId);
		List list = findByC_T(companyId, templateId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "templateId=";
			msg += templateId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_T_PrevAndNext(
		JournalArticlePK journalArticlePK, String companyId, String templateId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByC_T(companyId, templateId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("templateId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, templateId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_A_A(String companyId, String articleId, boolean approved)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);
			q.setBoolean(queryPos++, approved);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public List findByC_A_A(String companyId, String articleId,
		boolean approved, int begin, int end) throws SystemException {
		return findByC_A_A(companyId, articleId, approved, begin, end, null);
	}

	public List findByC_A_A(String companyId, String articleId,
		boolean approved, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);
			q.setBoolean(queryPos++, approved);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public com.liferay.portlet.journal.model.JournalArticle findByC_A_A_First(
		String companyId, String articleId, boolean approved,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByC_A_A(companyId, articleId, approved, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += ", ";
			msg += "approved=";
			msg += approved;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle findByC_A_A_Last(
		String companyId, String articleId, boolean approved,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByC_A_A(companyId, articleId, approved);
		List list = findByC_A_A(companyId, articleId, approved, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No JournalArticle exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "articleId=";
			msg += articleId;
			msg += ", ";
			msg += "approved=";
			msg += approved;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchArticleException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalArticle)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_A_A_PrevAndNext(
		JournalArticlePK journalArticlePK, String companyId, String articleId,
		boolean approved, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		com.liferay.portlet.journal.model.JournalArticle journalArticle = findByPrimaryKey(journalArticlePK);
		int count = countByC_A_A(companyId, articleId, approved);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);
			q.setBoolean(queryPos++, approved);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle, JournalArticleHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalArticle[] array = new com.liferay.portlet.journal.model.JournalArticle[3];
			array[0] = (com.liferay.portlet.journal.model.JournalArticle)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalArticle)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalArticle)objArray[2];

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
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				list.add(JournalArticleHBMUtil.model(journalArticleHBM));
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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

	public void removeByC_S(String companyId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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

	public void removeByC_T(String companyId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("templateId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, templateId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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

	public void removeByC_A_A(String companyId, String articleId,
		boolean approved) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);
			q.setBoolean(queryPos++, approved);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalArticleHBM journalArticleHBM = (JournalArticleHBM)itr.next();
				session.delete(journalArticleHBM);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByC_S(String companyId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByC_T(String companyId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("templateId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, templateId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public int countByC_A_A(String companyId, String articleId, boolean approved)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalArticle IN CLASS com.liferay.portlet.journal.service.persistence.JournalArticleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("articleId = ?");
			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, articleId);
			q.setBoolean(queryPos++, approved);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(JournalArticlePersistence.class);
}