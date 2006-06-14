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

import com.liferay.portlet.journal.NoSuchStructureException;

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
 * <a href="JournalStructurePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructurePersistence extends BasePersistence {
	public com.liferay.portlet.journal.model.JournalStructure create(
		JournalStructurePK journalStructurePK) {
		JournalStructureHBM journalStructureHBM = new JournalStructureHBM();
		journalStructureHBM.setNew(true);
		journalStructureHBM.setPrimaryKey(journalStructurePK);

		return JournalStructureHBMUtil.model(journalStructureHBM);
	}

	public com.liferay.portlet.journal.model.JournalStructure remove(
		JournalStructurePK journalStructurePK)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructureHBM journalStructureHBM = (JournalStructureHBM)session.get(JournalStructureHBM.class,
					journalStructurePK);

			if (journalStructureHBM == null) {
				_log.warn("No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
				throw new NoSuchStructureException(
					"No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
			}

			session.delete(journalStructureHBM);
			session.flush();

			return JournalStructureHBMUtil.model(journalStructureHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalStructure update(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws SystemException {
		Session session = null;

		try {
			if (journalStructure.isNew() || journalStructure.isModified()) {
				session = openSession();

				if (journalStructure.isNew()) {
					JournalStructureHBM journalStructureHBM = new JournalStructureHBM();
					journalStructureHBM.setCompanyId(journalStructure.getCompanyId());
					journalStructureHBM.setStructureId(journalStructure.getStructureId());
					journalStructureHBM.setGroupId(journalStructure.getGroupId());
					journalStructureHBM.setUserId(journalStructure.getUserId());
					journalStructureHBM.setUserName(journalStructure.getUserName());
					journalStructureHBM.setCreateDate(journalStructure.getCreateDate());
					journalStructureHBM.setModifiedDate(journalStructure.getModifiedDate());
					journalStructureHBM.setName(journalStructure.getName());
					journalStructureHBM.setDescription(journalStructure.getDescription());
					journalStructureHBM.setXsd(journalStructure.getXsd());
					session.save(journalStructureHBM);
					session.flush();
				}
				else {
					JournalStructureHBM journalStructureHBM = (JournalStructureHBM)session.get(JournalStructureHBM.class,
							journalStructure.getPrimaryKey());

					if (journalStructureHBM != null) {
						journalStructureHBM.setGroupId(journalStructure.getGroupId());
						journalStructureHBM.setUserId(journalStructure.getUserId());
						journalStructureHBM.setUserName(journalStructure.getUserName());
						journalStructureHBM.setCreateDate(journalStructure.getCreateDate());
						journalStructureHBM.setModifiedDate(journalStructure.getModifiedDate());
						journalStructureHBM.setName(journalStructure.getName());
						journalStructureHBM.setDescription(journalStructure.getDescription());
						journalStructureHBM.setXsd(journalStructure.getXsd());
						session.flush();
					}
					else {
						journalStructureHBM = new JournalStructureHBM();
						journalStructureHBM.setCompanyId(journalStructure.getCompanyId());
						journalStructureHBM.setStructureId(journalStructure.getStructureId());
						journalStructureHBM.setGroupId(journalStructure.getGroupId());
						journalStructureHBM.setUserId(journalStructure.getUserId());
						journalStructureHBM.setUserName(journalStructure.getUserName());
						journalStructureHBM.setCreateDate(journalStructure.getCreateDate());
						journalStructureHBM.setModifiedDate(journalStructure.getModifiedDate());
						journalStructureHBM.setName(journalStructure.getName());
						journalStructureHBM.setDescription(journalStructure.getDescription());
						journalStructureHBM.setXsd(journalStructure.getXsd());
						session.save(journalStructureHBM);
						session.flush();
					}
				}

				journalStructure.setNew(false);
				journalStructure.setModified(false);
			}

			return journalStructure;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.journal.model.JournalStructure findByPrimaryKey(
		JournalStructurePK journalStructurePK)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructureHBM journalStructureHBM = (JournalStructureHBM)session.get(JournalStructureHBM.class,
					journalStructurePK);

			if (journalStructureHBM == null) {
				_log.warn("No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
				throw new NoSuchStructureException(
					"No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
			}

			return JournalStructureHBMUtil.model(journalStructureHBM);
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
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalStructureHBM journalStructureHBM = (JournalStructureHBM)itr.next();
				list.add(JournalStructureHBMUtil.model(journalStructureHBM));
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
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				JournalStructureHBM journalStructureHBM = (JournalStructureHBM)itr.next();
				list.add(JournalStructureHBMUtil.model(journalStructureHBM));
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

	public com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchStructureException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No JournalStructure exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStructureException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalStructure)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchStructureException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No JournalStructure exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchStructureException(msg);
		}
		else {
			return (com.liferay.portlet.journal.model.JournalStructure)list.get(0);
		}
	}

	public com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		JournalStructurePK journalStructurePK, String groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		com.liferay.portlet.journal.model.JournalStructure journalStructure = findByPrimaryKey(journalStructurePK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalStructure, JournalStructureHBMUtil.getInstance());
			com.liferay.portlet.journal.model.JournalStructure[] array = new com.liferay.portlet.journal.model.JournalStructure[3];
			array[0] = (com.liferay.portlet.journal.model.JournalStructure)objArray[0];
			array[1] = (com.liferay.portlet.journal.model.JournalStructure)objArray[1];
			array[2] = (com.liferay.portlet.journal.model.JournalStructure)objArray[2];

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
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				JournalStructureHBM journalStructureHBM = (JournalStructureHBM)itr.next();
				list.add(JournalStructureHBMUtil.model(journalStructureHBM));
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
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalStructureHBM journalStructureHBM = (JournalStructureHBM)itr.next();
				session.delete(journalStructureHBM);
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
				"FROM JournalStructure IN CLASS com.liferay.portlet.journal.service.persistence.JournalStructureHBM WHERE ");
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

	private static Log _log = LogFactory.getLog(JournalStructurePersistence.class);
}