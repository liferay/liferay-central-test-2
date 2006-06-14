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

import com.liferay.portlet.journal.NoSuchTemplateException;

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
 * <a href="JournalTemplatePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplatePersistence extends BasePersistence {
	public com.liferay.portlet.journal.model.JournalTemplate create(
		JournalTemplatePK journalTemplatePK) {
		JournalTemplateHBM journalTemplateHBM = new JournalTemplateHBM();
		journalTemplateHBM.setNew(true);
		journalTemplateHBM.setPrimaryKey(journalTemplatePK);

		return JournalTemplateHBMUtil.model(journalTemplateHBM);
	}

	public com.liferay.portlet.journal.model.JournalTemplate remove(
		JournalTemplatePK journalTemplatePK)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)session.get(JournalTemplateHBM.class,
					journalTemplatePK);

			if (journalTemplateHBM == null) {
				_log.warn("No JournalTemplate exists with the primary key " +
					journalTemplatePK.toString());
				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the primary key " +
					journalTemplatePK.toString());
			}

			session.delete(journalTemplateHBM);
			session.flush();

			return JournalTemplateHBMUtil.model(journalTemplateHBM);
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
		Session session = null;

		try {
			if (journalTemplate.isNew() || journalTemplate.isModified()) {
				session = openSession();

				if (journalTemplate.isNew()) {
					JournalTemplateHBM journalTemplateHBM = new JournalTemplateHBM();
					journalTemplateHBM.setCompanyId(journalTemplate.getCompanyId());
					journalTemplateHBM.setTemplateId(journalTemplate.getTemplateId());
					journalTemplateHBM.setGroupId(journalTemplate.getGroupId());
					journalTemplateHBM.setUserId(journalTemplate.getUserId());
					journalTemplateHBM.setUserName(journalTemplate.getUserName());
					journalTemplateHBM.setCreateDate(journalTemplate.getCreateDate());
					journalTemplateHBM.setModifiedDate(journalTemplate.getModifiedDate());
					journalTemplateHBM.setStructureId(journalTemplate.getStructureId());
					journalTemplateHBM.setName(journalTemplate.getName());
					journalTemplateHBM.setDescription(journalTemplate.getDescription());
					journalTemplateHBM.setXsl(journalTemplate.getXsl());
					journalTemplateHBM.setLangType(journalTemplate.getLangType());
					journalTemplateHBM.setSmallImage(journalTemplate.getSmallImage());
					journalTemplateHBM.setSmallImageURL(journalTemplate.getSmallImageURL());
					session.save(journalTemplateHBM);
					session.flush();
				}
				else {
					JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)session.get(JournalTemplateHBM.class,
							journalTemplate.getPrimaryKey());

					if (journalTemplateHBM != null) {
						journalTemplateHBM.setGroupId(journalTemplate.getGroupId());
						journalTemplateHBM.setUserId(journalTemplate.getUserId());
						journalTemplateHBM.setUserName(journalTemplate.getUserName());
						journalTemplateHBM.setCreateDate(journalTemplate.getCreateDate());
						journalTemplateHBM.setModifiedDate(journalTemplate.getModifiedDate());
						journalTemplateHBM.setStructureId(journalTemplate.getStructureId());
						journalTemplateHBM.setName(journalTemplate.getName());
						journalTemplateHBM.setDescription(journalTemplate.getDescription());
						journalTemplateHBM.setXsl(journalTemplate.getXsl());
						journalTemplateHBM.setLangType(journalTemplate.getLangType());
						journalTemplateHBM.setSmallImage(journalTemplate.getSmallImage());
						journalTemplateHBM.setSmallImageURL(journalTemplate.getSmallImageURL());
						session.flush();
					}
					else {
						journalTemplateHBM = new JournalTemplateHBM();
						journalTemplateHBM.setCompanyId(journalTemplate.getCompanyId());
						journalTemplateHBM.setTemplateId(journalTemplate.getTemplateId());
						journalTemplateHBM.setGroupId(journalTemplate.getGroupId());
						journalTemplateHBM.setUserId(journalTemplate.getUserId());
						journalTemplateHBM.setUserName(journalTemplate.getUserName());
						journalTemplateHBM.setCreateDate(journalTemplate.getCreateDate());
						journalTemplateHBM.setModifiedDate(journalTemplate.getModifiedDate());
						journalTemplateHBM.setStructureId(journalTemplate.getStructureId());
						journalTemplateHBM.setName(journalTemplate.getName());
						journalTemplateHBM.setDescription(journalTemplate.getDescription());
						journalTemplateHBM.setXsl(journalTemplate.getXsl());
						journalTemplateHBM.setLangType(journalTemplate.getLangType());
						journalTemplateHBM.setSmallImage(journalTemplate.getSmallImage());
						journalTemplateHBM.setSmallImageURL(journalTemplate.getSmallImageURL());
						session.save(journalTemplateHBM);
						session.flush();
					}
				}

				journalTemplate.setNew(false);
				journalTemplate.setModified(false);
			}

			return journalTemplate;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		JournalTemplatePK journalTemplatePK)
		throws NoSuchTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)session.get(JournalTemplateHBM.class,
					journalTemplatePK);

			if (journalTemplateHBM == null) {
				_log.warn("No JournalTemplate exists with the primary key " +
					journalTemplatePK.toString());
				throw new NoSuchTemplateException(
					"No JournalTemplate exists with the primary key " +
					journalTemplatePK.toString());
			}

			return JournalTemplateHBMUtil.model(journalTemplateHBM);
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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				list.add(JournalTemplateHBMUtil.model(journalTemplateHBM));
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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				list.add(JournalTemplateHBMUtil.model(journalTemplateHBM));
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

	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
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
			return (com.liferay.portlet.journal.model.JournalTemplate)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
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
			return (com.liferay.portlet.journal.model.JournalTemplate)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		JournalTemplatePK journalTemplatePK, String groupId,
		OrderByComparator obc) throws NoSuchTemplateException, SystemException {
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate = findByPrimaryKey(journalTemplatePK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate, JournalTemplateHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalTemplate[] array = new com.liferay.portlet.journal.model.JournalTemplate[3];
			array[0] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[2];

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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				list.add(JournalTemplateHBMUtil.model(journalTemplateHBM));
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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				list.add(JournalTemplateHBMUtil.model(journalTemplateHBM));
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

	public com.liferay.portlet.journal.model.JournalTemplate findByC_S_First(
		String companyId, String structureId, OrderByComparator obc)
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
			return (com.liferay.portlet.journal.model.JournalTemplate)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate findByC_S_Last(
		String companyId, String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
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
			return (com.liferay.portlet.journal.model.JournalTemplate)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalTemplate[] findByC_S_PrevAndNext(
		JournalTemplatePK journalTemplatePK, String companyId,
		String structureId, OrderByComparator obc)
		throws NoSuchTemplateException, SystemException {
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate = findByPrimaryKey(journalTemplatePK);
		int count = countByC_S(companyId, structureId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("templateId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalTemplate, JournalTemplateHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalTemplate[] array = new com.liferay.portlet.journal.model.JournalTemplate[3];
			array[0] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalTemplate)objArray[2];

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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				list.add(JournalTemplateHBMUtil.model(journalTemplateHBM));
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				session.delete(journalTemplateHBM);
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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("structureId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("templateId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, structureId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalTemplateHBM journalTemplateHBM = (JournalTemplateHBM)itr.next();
				session.delete(journalTemplateHBM);
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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

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
				"FROM JournalTemplate IN CLASS com.liferay.portlet.journal.service.persistence.JournalTemplateHBM WHERE ");
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

	private static Log _log = LogFactory.getLog(JournalTemplatePersistence.class);
}