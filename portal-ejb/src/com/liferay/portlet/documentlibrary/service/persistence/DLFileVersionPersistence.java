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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

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
 * <a href="DLFileVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileVersionPersistence extends BasePersistence {
	public com.liferay.portlet.documentlibrary.model.DLFileVersion create(
		DLFileVersionPK dlFileVersionPK) {
		DLFileVersionHBM dlFileVersionHBM = new DLFileVersionHBM();
		dlFileVersionHBM.setNew(true);
		dlFileVersionHBM.setPrimaryKey(dlFileVersionPK);

		return DLFileVersionHBMUtil.model(dlFileVersionHBM);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		DLFileVersionPK dlFileVersionPK)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)session.get(DLFileVersionHBM.class,
					dlFileVersionPK);

			if (dlFileVersionHBM == null) {
				_log.warn("No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
				throw new NoSuchFileVersionException(
					"No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
			}

			session.delete(dlFileVersionHBM);
			session.flush();

			return DLFileVersionHBMUtil.model(dlFileVersionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws SystemException {
		Session session = null;

		try {
			if (dlFileVersion.isNew() || dlFileVersion.isModified()) {
				session = openSession();

				if (dlFileVersion.isNew()) {
					DLFileVersionHBM dlFileVersionHBM = new DLFileVersionHBM();
					dlFileVersionHBM.setFolderId(dlFileVersion.getFolderId());
					dlFileVersionHBM.setName(dlFileVersion.getName());
					dlFileVersionHBM.setVersion(dlFileVersion.getVersion());
					dlFileVersionHBM.setCompanyId(dlFileVersion.getCompanyId());
					dlFileVersionHBM.setUserId(dlFileVersion.getUserId());
					dlFileVersionHBM.setUserName(dlFileVersion.getUserName());
					dlFileVersionHBM.setCreateDate(dlFileVersion.getCreateDate());
					dlFileVersionHBM.setSize(dlFileVersion.getSize());
					session.save(dlFileVersionHBM);
					session.flush();
				}
				else {
					DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)session.get(DLFileVersionHBM.class,
							dlFileVersion.getPrimaryKey());

					if (dlFileVersionHBM != null) {
						dlFileVersionHBM.setCompanyId(dlFileVersion.getCompanyId());
						dlFileVersionHBM.setUserId(dlFileVersion.getUserId());
						dlFileVersionHBM.setUserName(dlFileVersion.getUserName());
						dlFileVersionHBM.setCreateDate(dlFileVersion.getCreateDate());
						dlFileVersionHBM.setSize(dlFileVersion.getSize());
						session.flush();
					}
					else {
						dlFileVersionHBM = new DLFileVersionHBM();
						dlFileVersionHBM.setFolderId(dlFileVersion.getFolderId());
						dlFileVersionHBM.setName(dlFileVersion.getName());
						dlFileVersionHBM.setVersion(dlFileVersion.getVersion());
						dlFileVersionHBM.setCompanyId(dlFileVersion.getCompanyId());
						dlFileVersionHBM.setUserId(dlFileVersion.getUserId());
						dlFileVersionHBM.setUserName(dlFileVersion.getUserName());
						dlFileVersionHBM.setCreateDate(dlFileVersion.getCreateDate());
						dlFileVersionHBM.setSize(dlFileVersion.getSize());
						session.save(dlFileVersionHBM);
						session.flush();
					}
				}

				dlFileVersion.setNew(false);
				dlFileVersion.setModified(false);
			}

			return dlFileVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByPrimaryKey(
		DLFileVersionPK dlFileVersionPK)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)session.get(DLFileVersionHBM.class,
					dlFileVersionPK);

			if (dlFileVersionHBM == null) {
				_log.warn("No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
				throw new NoSuchFileVersionException(
					"No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
			}

			return DLFileVersionHBMUtil.model(dlFileVersionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByF_N(String folderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("folderId DESC").append(", ");
			query.append("name DESC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)itr.next();
				list.add(DLFileVersionHBMUtil.model(dlFileVersionHBM));
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

	public List findByF_N(String folderId, String name, int begin, int end)
		throws SystemException {
		return findByF_N(folderId, name, begin, end, null);
	}

	public List findByF_N(String folderId, String name, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("folderId DESC").append(", ");
				query.append("name DESC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)itr.next();
				list.add(DLFileVersionHBMUtil.model(dlFileVersionHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_First(
		String folderId, String name, OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		List list = findByF_N(folderId, name, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFileVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileVersionException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileVersion)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_Last(
		String folderId, String name, OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		int count = countByF_N(folderId, name);
		List list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFileVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileVersionException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileVersion)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByF_N_PrevAndNext(
		DLFileVersionPK dlFileVersionPK, String folderId, String name,
		OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion = findByPrimaryKey(dlFileVersionPK);
		int count = countByF_N(folderId, name);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("folderId DESC").append(", ");
				query.append("name DESC").append(", ");
				query.append("version DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileVersion, DLFileVersionHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFileVersion[] array = new com.liferay.portlet.documentlibrary.model.DLFileVersion[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFileVersion)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFileVersion)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFileVersion)objArray[2];

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
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM ");
			query.append("ORDER BY ");
			query.append("folderId DESC").append(", ");
			query.append("name DESC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)itr.next();
				list.add(DLFileVersionHBMUtil.model(dlFileVersionHBM));
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

	public void removeByF_N(String folderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("folderId DESC").append(", ");
			query.append("name DESC").append(", ");
			query.append("version DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFileVersionHBM dlFileVersionHBM = (DLFileVersionHBM)itr.next();
				session.delete(dlFileVersionHBM);
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

	public int countByF_N(String folderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM DLFileVersion IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionHBM WHERE ");

			if (folderId == null) {
				query.append("folderId is null");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
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

	private static Log _log = LogFactory.getLog(DLFileVersionPersistence.class);
}