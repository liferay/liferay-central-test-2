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
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalArticlePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticlePersistenceImpl extends BasePersistence
	implements JournalArticlePersistence {
	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();
		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		return journalArticle;
	}

	public JournalArticle remove(long id)
		throws NoSuchArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticle journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
					new Long(id));

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No JournalArticle exists with the primary key " +
						id);
				}

				throw new NoSuchArticleException(
					"No JournalArticle exists with the primary key " + id);
			}

			return remove(journalArticle);
		}
		catch (NoSuchArticleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		FinderCache.clearCache(JournalArticle.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(journalArticle);
			session.flush();

			return journalArticle;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws SystemException {
		return update(journalArticle, false);
	}

	public JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(JournalArticle.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(journalArticle);
			}
			else {
				if (journalArticle.isNew()) {
					session.save(journalArticle);
				}
			}

			session.flush();
			journalArticle.setNew(false);

			return journalArticle;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByPrimaryKey(id);

		if (journalArticle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalArticle exists with the primary key " +
					id);
			}

			throw new NoSuchArticleException(
				"No JournalArticle exists with the primary key " + id);
		}

		return journalArticle;
	}

	public JournalArticle fetchByPrimaryKey(long id) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalArticle)session.get(JournalArticleImpl.class,
				new Long(id));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(long companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByCompanyId_PrevAndNext(long id,
		long companyId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A(long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
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

	public List findByG_A(long groupId, String articleId, int begin, int end)
		throws SystemException {
		return findByG_A(groupId, articleId, begin, end, null);
	}

	public List findByG_A(long groupId, String articleId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
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
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
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

	public JournalArticle findByG_A_First(long groupId, String articleId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByG_A(groupId, articleId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByG_A_Last(long groupId, String articleId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_A(groupId, articleId);
		List list = findByG_A(groupId, articleId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByG_A_PrevAndNext(long id, long groupId,
		String articleId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByG_A(groupId, articleId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
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
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_S(long groupId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (structureId != null) {
				q.setString(queryPos++, structureId);
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

	public List findByG_S(long groupId, String structureId, int begin, int end)
		throws SystemException {
		return findByG_S(groupId, structureId, begin, end, null);
	}

	public List findByG_S(long groupId, String structureId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (structureId != null) {
				q.setString(queryPos++, structureId);
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

	public JournalArticle findByG_S_First(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByG_S(groupId, structureId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("structureId=");
			msg.append(structureId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByG_S_Last(long groupId, String structureId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_S(groupId, structureId);
		List list = findByG_S(groupId, structureId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("structureId=");
			msg.append(structureId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByG_S(groupId, structureId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (structureId != null) {
				q.setString(queryPos++, structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_T(long groupId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (templateId == null) {
				query.append("templateId IS NULL");
			}
			else {
				query.append("templateId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (templateId != null) {
				q.setString(queryPos++, templateId);
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

	public List findByG_T(long groupId, String templateId, int begin, int end)
		throws SystemException {
		return findByG_T(groupId, templateId, begin, end, null);
	}

	public List findByG_T(long groupId, String templateId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (templateId == null) {
				query.append("templateId IS NULL");
			}
			else {
				query.append("templateId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (templateId != null) {
				q.setString(queryPos++, templateId);
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

	public JournalArticle findByG_T_First(long groupId, String templateId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		List list = findByG_T(groupId, templateId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("templateId=");
			msg.append(templateId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByG_T_Last(long groupId, String templateId,
		OrderByComparator obc) throws NoSuchArticleException, SystemException {
		int count = countByG_T(groupId, templateId);
		List list = findByG_T(groupId, templateId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("templateId=");
			msg.append(templateId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByG_T_PrevAndNext(long id, long groupId,
		String templateId, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByG_T(groupId, templateId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (templateId == null) {
				query.append("templateId IS NULL");
			}
			else {
				query.append("templateId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (templateId != null) {
				q.setString(queryPos++, templateId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByG_A_V(long groupId, String articleId,
		double version) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByG_A_V(groupId, articleId, version);

		if (journalArticle == null) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(", ");
			msg.append("version=");
			msg.append(version);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version) throws SystemException {
		String finderClassName = JournalArticle.class.getName();
		String finderMethodName = "fetchByG_A_V";
		Object[] finderArgs = new Object[] {
				new Long(groupId), articleId, new Double(version)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
				query.append("groupId = ?");
				query.append(" AND ");

				if (articleId == null) {
					query.append("articleId IS NULL");
				}
				else {
					query.append("articleId = ?");
				}

				query.append(" AND ");
				query.append("version = ?");
				query.append(" ");
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, groupId);

				if (articleId != null) {
					q.setString(queryPos++, articleId);
				}

				q.setDouble(queryPos++, version);

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				JournalArticle journalArticle = (JournalArticle)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, journalArticle);

				return journalArticle;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (JournalArticle)result;
		}
	}

	public List findByG_A_A(long groupId, String articleId, boolean approved)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("articleId ASC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			q.setBoolean(queryPos++, approved);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A_A(long groupId, String articleId, boolean approved,
		int begin, int end) throws SystemException {
		return findByG_A_A(groupId, articleId, approved, begin, end, null);
	}

	public List findByG_A_A(long groupId, String articleId, boolean approved,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			q.setBoolean(queryPos++, approved);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByG_A_A_First(long groupId, String articleId,
		boolean approved, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		List list = findByG_A_A(groupId, articleId, approved, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(", ");
			msg.append("approved=");
			msg.append(approved);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle findByG_A_A_Last(long groupId, String articleId,
		boolean approved, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		int count = countByG_A_A(groupId, articleId, approved);
		List list = findByG_A_A(groupId, articleId, approved, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No JournalArticle exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("articleId=");
			msg.append(articleId);
			msg.append(", ");
			msg.append("approved=");
			msg.append(approved);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return (JournalArticle)list.get(0);
		}
	}

	public JournalArticle[] findByG_A_A_PrevAndNext(long id, long groupId,
		String articleId, boolean approved, OrderByComparator obc)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);
		int count = countByG_A_A(groupId, articleId, approved);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			q.setBoolean(queryPos++, approved);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalArticle);
			JournalArticle[] array = new JournalArticleImpl[3];
			array[0] = (JournalArticle)objArray[0];
			array[1] = (JournalArticle)objArray[1];
			array[2] = (JournalArticle)objArray[2];

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
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("articleId ASC").append(", ");
				query.append("version DESC");
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

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		Iterator itr = findByG_A(groupId, articleId).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		Iterator itr = findByG_S(groupId, structureId).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeByG_T(long groupId, String templateId)
		throws SystemException {
		Iterator itr = findByG_T(groupId, templateId).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeByG_A_V(long groupId, String articleId, double version)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByG_A_V(groupId, articleId, version);
		remove(journalArticle);
	}

	public void removeByG_A_A(long groupId, String articleId, boolean approved)
		throws SystemException {
		Iterator itr = findByG_A_A(groupId, articleId, approved).iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = (JournalArticle)itr.next();
			remove(journalArticle);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((JournalArticle)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

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

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
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

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (structureId != null) {
				q.setString(queryPos++, structureId);
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

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (templateId == null) {
				query.append("templateId IS NULL");
			}
			else {
				query.append("templateId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (templateId != null) {
				q.setString(queryPos++, templateId);
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

	public int countByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" AND ");
			query.append("version = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			q.setDouble(queryPos++, version);

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

	public int countByG_A_A(long groupId, String articleId, boolean approved)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticle WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" AND ");
			query.append("approved = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (articleId != null) {
				q.setString(queryPos++, articleId);
			}

			q.setBoolean(queryPos++, approved);

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
				"FROM com.liferay.portlet.journal.model.JournalArticle");

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

	private static Log _log = LogFactory.getLog(JournalArticlePersistenceImpl.class);
}