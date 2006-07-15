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
import com.liferay.portlet.journal.model.JournalStructure;

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
 * <a href="JournalStructurePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructurePersistence extends BasePersistence {
	public JournalStructure create(JournalStructurePK journalStructurePK) {
		JournalStructure journalStructure = new JournalStructure();
		journalStructure.setNew(true);
		journalStructure.setPrimaryKey(journalStructurePK);

		return journalStructure;
	}

	public JournalStructure remove(JournalStructurePK journalStructurePK)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructure journalStructure = (JournalStructure)session.get(JournalStructure.class,
					journalStructurePK);

			if (journalStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalStructure exists with the primary key " +
						journalStructurePK.toString());
				}

				throw new NoSuchStructureException(
					"No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
			}

			session.delete(journalStructure);
			session.flush();

			return journalStructure;
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
					JournalStructure journalStructureModel = new JournalStructure();
					journalStructureModel.setCompanyId(journalStructure.getCompanyId());
					journalStructureModel.setStructureId(journalStructure.getStructureId());
					journalStructureModel.setGroupId(journalStructure.getGroupId());
					journalStructureModel.setUserId(journalStructure.getUserId());
					journalStructureModel.setUserName(journalStructure.getUserName());
					journalStructureModel.setCreateDate(journalStructure.getCreateDate());
					journalStructureModel.setModifiedDate(journalStructure.getModifiedDate());
					journalStructureModel.setName(journalStructure.getName());
					journalStructureModel.setDescription(journalStructure.getDescription());
					journalStructureModel.setXsd(journalStructure.getXsd());
					session.save(journalStructureModel);
					session.flush();
				}
				else {
					JournalStructure journalStructureModel = (JournalStructure)session.get(JournalStructure.class,
							journalStructure.getPrimaryKey());

					if (journalStructureModel != null) {
						journalStructureModel.setGroupId(journalStructure.getGroupId());
						journalStructureModel.setUserId(journalStructure.getUserId());
						journalStructureModel.setUserName(journalStructure.getUserName());
						journalStructureModel.setCreateDate(journalStructure.getCreateDate());
						journalStructureModel.setModifiedDate(journalStructure.getModifiedDate());
						journalStructureModel.setName(journalStructure.getName());
						journalStructureModel.setDescription(journalStructure.getDescription());
						journalStructureModel.setXsd(journalStructure.getXsd());
						session.flush();
					}
					else {
						journalStructureModel = new JournalStructure();
						journalStructureModel.setCompanyId(journalStructure.getCompanyId());
						journalStructureModel.setStructureId(journalStructure.getStructureId());
						journalStructureModel.setGroupId(journalStructure.getGroupId());
						journalStructureModel.setUserId(journalStructure.getUserId());
						journalStructureModel.setUserName(journalStructure.getUserName());
						journalStructureModel.setCreateDate(journalStructure.getCreateDate());
						journalStructureModel.setModifiedDate(journalStructure.getModifiedDate());
						journalStructureModel.setName(journalStructure.getName());
						journalStructureModel.setDescription(journalStructure.getDescription());
						journalStructureModel.setXsd(journalStructure.getXsd());
						session.save(journalStructureModel);
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

	public JournalStructure findByPrimaryKey(
		JournalStructurePK journalStructurePK)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructure journalStructure = (JournalStructure)session.get(JournalStructure.class,
					journalStructurePK);

			if (journalStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalStructure exists with the primary key " +
						journalStructurePK.toString());
				}

				throw new NoSuchStructureException(
					"No JournalStructure exists with the primary key " +
					journalStructurePK.toString());
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

	public JournalStructure fetchByPrimaryKey(
		JournalStructurePK journalStructurePK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalStructure)session.get(JournalStructure.class,
				journalStructurePK);
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
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

			Query q = session.createQuery(query.toString());
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
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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
				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());
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

	public JournalStructure findByGroupId_First(String groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
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
			return (JournalStructure)list.get(0);
		}
	}

	public JournalStructure findByGroupId_Last(String groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
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
			return (JournalStructure)list.get(0);
		}
	}

	public JournalStructure[] findByGroupId_PrevAndNext(
		JournalStructurePK journalStructurePK, String groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(journalStructurePK);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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
				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalStructure);
			JournalStructure[] array = new JournalStructure[3];
			array[0] = (JournalStructure)objArray[0];
			array[1] = (JournalStructure)objArray[1];
			array[2] = (JournalStructure)objArray[2];

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
				"FROM com.liferay.portlet.journal.model.JournalStructure ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("structureId ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				JournalStructure journalStructure = (JournalStructure)itr.next();
				session.delete(journalStructure);
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
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(JournalStructurePersistence.class);
}