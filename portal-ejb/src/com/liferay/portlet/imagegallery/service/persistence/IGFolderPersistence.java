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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.imagegallery.NoSuchFolderException;

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
 * <a href="IGFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGFolderPersistence extends BasePersistence {
	public com.liferay.portlet.imagegallery.model.IGFolder create(
		String folderId) {
		IGFolderHBM igFolderHBM = new IGFolderHBM();
		igFolderHBM.setNew(true);
		igFolderHBM.setPrimaryKey(folderId);

		return IGFolderHBMUtil.model(igFolderHBM);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder remove(
		String folderId) throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGFolderHBM igFolderHBM = (IGFolderHBM)session.get(IGFolderHBM.class,
					folderId);

			if (igFolderHBM == null) {
				_log.warn("No IGFolder exists with the primary key " +
					folderId.toString());
				throw new NoSuchFolderException(
					"No IGFolder exists with the primary key " +
					folderId.toString());
			}

			session.delete(igFolderHBM);
			session.flush();

			return IGFolderHBMUtil.model(igFolderHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder update(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws SystemException {
		Session session = null;

		try {
			if (igFolder.isNew() || igFolder.isModified()) {
				session = openSession();

				if (igFolder.isNew()) {
					IGFolderHBM igFolderHBM = new IGFolderHBM();
					igFolderHBM.setFolderId(igFolder.getFolderId());
					igFolderHBM.setGroupId(igFolder.getGroupId());
					igFolderHBM.setCompanyId(igFolder.getCompanyId());
					igFolderHBM.setUserId(igFolder.getUserId());
					igFolderHBM.setCreateDate(igFolder.getCreateDate());
					igFolderHBM.setModifiedDate(igFolder.getModifiedDate());
					igFolderHBM.setParentFolderId(igFolder.getParentFolderId());
					igFolderHBM.setName(igFolder.getName());
					igFolderHBM.setDescription(igFolder.getDescription());
					session.save(igFolderHBM);
					session.flush();
				}
				else {
					IGFolderHBM igFolderHBM = (IGFolderHBM)session.get(IGFolderHBM.class,
							igFolder.getPrimaryKey());

					if (igFolderHBM != null) {
						igFolderHBM.setGroupId(igFolder.getGroupId());
						igFolderHBM.setCompanyId(igFolder.getCompanyId());
						igFolderHBM.setUserId(igFolder.getUserId());
						igFolderHBM.setCreateDate(igFolder.getCreateDate());
						igFolderHBM.setModifiedDate(igFolder.getModifiedDate());
						igFolderHBM.setParentFolderId(igFolder.getParentFolderId());
						igFolderHBM.setName(igFolder.getName());
						igFolderHBM.setDescription(igFolder.getDescription());
						session.flush();
					}
					else {
						igFolderHBM = new IGFolderHBM();
						igFolderHBM.setFolderId(igFolder.getFolderId());
						igFolderHBM.setGroupId(igFolder.getGroupId());
						igFolderHBM.setCompanyId(igFolder.getCompanyId());
						igFolderHBM.setUserId(igFolder.getUserId());
						igFolderHBM.setCreateDate(igFolder.getCreateDate());
						igFolderHBM.setModifiedDate(igFolder.getModifiedDate());
						igFolderHBM.setParentFolderId(igFolder.getParentFolderId());
						igFolderHBM.setName(igFolder.getName());
						igFolderHBM.setDescription(igFolder.getDescription());
						session.save(igFolderHBM);
						session.flush();
					}
				}

				igFolder.setNew(false);
				igFolder.setModified(false);
			}

			return igFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder findByPrimaryKey(
		String folderId) throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGFolderHBM igFolderHBM = (IGFolderHBM)session.get(IGFolderHBM.class,
					folderId);

			if (igFolderHBM == null) {
				_log.warn("No IGFolder exists with the primary key " +
					folderId.toString());
				throw new NoSuchFolderException(
					"No IGFolder exists with the primary key " +
					folderId.toString());
			}

			return IGFolderHBMUtil.model(igFolderHBM);
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
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				list.add(IGFolderHBMUtil.model(igFolderHBM));
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
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				list.add(IGFolderHBMUtil.model(igFolderHBM));
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

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No IGFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGFolder)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No IGFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGFolder)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByGroupId_PrevAndNext(
		String folderId, String groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		com.liferay.portlet.imagegallery.model.IGFolder igFolder = findByPrimaryKey(folderId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					igFolder, IGFolderHBMUtil.getInstance());
			com.liferay.portlet.imagegallery.model.IGFolder[] array = new com.liferay.portlet.imagegallery.model.IGFolder[3];
			array[0] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[0];
			array[1] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[1];
			array[2] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("parentFolderId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);
			q.setString(queryPos++, parentFolderId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				list.add(IGFolderHBMUtil.model(igFolderHBM));
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

	public List findByG_P(String groupId, String parentFolderId, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, begin, end, null);
	}

	public List findByG_P(String groupId, String parentFolderId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("parentFolderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);
			q.setString(queryPos++, parentFolderId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				list.add(IGFolderHBMUtil.model(igFolderHBM));
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

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_First(
		String groupId, String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List list = findByG_P(groupId, parentFolderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No IGFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentFolderId=";
			msg += parentFolderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGFolder)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_Last(
		String groupId, String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);
		List list = findByG_P(groupId, parentFolderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No IGFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentFolderId=";
			msg += parentFolderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (com.liferay.portlet.imagegallery.model.IGFolder)list.get(0);
		}
	}

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByG_P_PrevAndNext(
		String folderId, String groupId, String parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		com.liferay.portlet.imagegallery.model.IGFolder igFolder = findByPrimaryKey(folderId);
		int count = countByG_P(groupId, parentFolderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("parentFolderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);
			q.setString(queryPos++, parentFolderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					igFolder, IGFolderHBMUtil.getInstance());
			com.liferay.portlet.imagegallery.model.IGFolder[] array = new com.liferay.portlet.imagegallery.model.IGFolder[3];
			array[0] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[0];
			array[1] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[1];
			array[2] = (com.liferay.portlet.imagegallery.model.IGFolder)objArray[2];

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
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				list.add(IGFolderHBMUtil.model(igFolderHBM));
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
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				session.delete(igFolderHBM);
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

	public void removeByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("parentFolderId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);
			q.setString(queryPos++, parentFolderId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				IGFolderHBM igFolderHBM = (IGFolderHBM)itr.next();
				session.delete(igFolderHBM);
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
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
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

	public int countByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM IGFolder IN CLASS com.liferay.portlet.imagegallery.service.persistence.IGFolderHBM WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("parentFolderId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, groupId);
			q.setString(queryPos++, parentFolderId);

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

	private static Log _log = LogFactory.getLog(IGFolderPersistence.class);
}