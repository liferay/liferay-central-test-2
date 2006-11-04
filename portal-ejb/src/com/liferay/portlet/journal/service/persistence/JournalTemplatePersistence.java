/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;

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
 * <a href="JournalTemplatePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplatePersistence extends BasePersistence {
	public JournalTemplate create(JournalTemplatePK journalTemplatePK) {
		JournalTemplate journalTemplate = new JournalTemplate();
		journalTemplate.setNew(true);
		journalTemplate.setPrimaryKey(journalTemplatePK);

		return journalTemplate;
	}

	public JournalTemplate remove(JournalTemplatePK journalTemplatePK)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplate journalTemplate = (JournalTemplate)session.get(JournalTemplate.class,
					journalTemplatePK);

			if (journalTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No JournalTemplate exists with the primary key " +
						journalTemplatePK);
				}

				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the primary key " +
					journalTemplatePK);
			}

			return remove(journalTemplate);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate remove(JournalTemplate journalTemplate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(journalTemplate);
			session.flush();

			return journalTemplate;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws SystemException {
		return update(journalTemplate, false);
	}

	public com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(journalTemplate);
			}
			else {
				if (journalTemplate.isNew()) {
					session.save(journalTemplate);
				}
			}

			session.flush();
			journalTemplate.setNew(false);

			return journalTemplate;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalTemplate findByPrimaryKey(JournalTemplatePK journalTemplatePK)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = fetchByPrimaryKey(journalTemplatePK);

		if (journalTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalTemplate exists with the primary key " +
					journalTemplatePK);
			}

			throw new NoSuchTemplateException(
				"No JournalTemplate exists with the primary key " +
				journalTemplatePK);
		}

		return journalTemplate;
	}

	public JournalTemplate fetchByPrimaryKey(
		JournalTemplatePK journalTemplatePK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalTemplate)session.get(JournalTemplate.class,
				journalTemplatePK);
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
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public JournalTemplate findByGroupId_First(String groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalTemplate exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTemplateException(msg);
		}
		else {
			return (JournalTemplate)list.get(0);
		}
	}

	public JournalTemplate findByGroupId_Last(String groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalTemplate exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTemplateException(msg);
		}
		else {
			return (JournalTemplate)list.get(0);
		}
	}

	public JournalTemplate[] findByGroupId_PrevAndNext(
		JournalTemplatePK journalTemplatePK, String groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(journalTemplatePK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);
			JournalTemplate[] array = new JournalTemplate[3];
			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

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
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (structureId != null) {
				q.setString(queryPos++, structureId);
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
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (structureId != null) {
				q.setString(queryPos++, structureId);
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

	public JournalTemplate findByC_S_First(String companyId,
		String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		List list = findByC_S(companyId, structureId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalTemplate exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "structureId=";
			msg += structureId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTemplateException(msg);
		}
		else {
			return (JournalTemplate)list.get(0);
		}
	}

	public JournalTemplate findByC_S_Last(String companyId, String structureId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		int count = countByC_S(companyId, structureId);
		List list = findByC_S(companyId, structureId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalTemplate exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "structureId=";
			msg += structureId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTemplateException(msg);
		}
		else {
			return (JournalTemplate)list.get(0);
		}
	}

	public JournalTemplate[] findByC_S_PrevAndNext(
		JournalTemplatePK journalTemplatePK, String companyId,
		String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		JournalTemplate journalTemplate = findByPrimaryKey(journalTemplatePK);
		int count = countByC_S(companyId, structureId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (structureId != null) {
				q.setString(queryPos++, structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate);
			JournalTemplate[] array = new JournalTemplate[3];
			array[0] = (JournalTemplate)objArray[0];
			array[1] = (JournalTemplate)objArray[1];
			array[2] = (JournalTemplate)objArray[2];

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
				"FROM com.liferay.portlet.journal.model.JournalTemplate ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
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

	public void removeByGroupId(String groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			JournalTemplate journalTemplate = (JournalTemplate)itr.next();
			remove(journalTemplate);
		}
	}

	public void removeByC_S(String companyId, String structureId)
		throws SystemException {
		Iterator itr = findByC_S(companyId, structureId).iterator();

		while (itr.hasNext()) {
			JournalTemplate journalTemplate = (JournalTemplate)itr.next();
			remove(journalTemplate);
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public int countByC_S(String companyId, String structureId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalTemplate WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(JournalTemplatePersistence.class);
}