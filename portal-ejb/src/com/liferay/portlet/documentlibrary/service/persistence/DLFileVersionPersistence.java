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
import com.liferay.portlet.documentlibrary.model.DLFileVersion;

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
 * <a href="DLFileVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileVersionPersistence extends BasePersistence {
	public DLFileVersion create(DLFileVersionPK dlFileVersionPK) {
		DLFileVersion dlFileVersion = new DLFileVersion();
		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(dlFileVersionPK);

		return dlFileVersion;
	}

	public DLFileVersion remove(DLFileVersionPK dlFileVersionPK)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(DLFileVersion.class,
					dlFileVersionPK);

			if (dlFileVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileVersion exists with the primary key " +
						dlFileVersionPK.toString());
				}

				throw new NoSuchFileVersionException(
					"No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
			}

			return remove(dlFileVersion);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileVersion remove(DLFileVersion dlFileVersion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(dlFileVersion);
			session.flush();

			return dlFileVersion;
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
		return update(dlFileVersion, false);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(dlFileVersion);
			}
			else {
				if (dlFileVersion.isNew()) {
					session.save(dlFileVersion);
				}
			}

			session.flush();
			dlFileVersion.setNew(false);

			return dlFileVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileVersion findByPrimaryKey(DLFileVersionPK dlFileVersionPK)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByPrimaryKey(dlFileVersionPK);

		if (dlFileVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileVersion exists with the primary key " +
					dlFileVersionPK.toString());
			}

			throw new NoSuchFileVersionException(
				"No DLFileVersion exists with the primary key " +
				dlFileVersionPK.toString());
		}

		return dlFileVersion;
	}

	public DLFileVersion fetchByPrimaryKey(DLFileVersionPK dlFileVersionPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DLFileVersion)session.get(DLFileVersion.class,
				dlFileVersionPK);
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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileVersion WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
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
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileVersion WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
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
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
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

	public DLFileVersion findByF_N_First(String folderId, String name,
		OrderByComparator obc)
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
			return (DLFileVersion)list.get(0);
		}
	}

	public DLFileVersion findByF_N_Last(String folderId, String name,
		OrderByComparator obc)
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
			return (DLFileVersion)list.get(0);
		}
	}

	public DLFileVersion[] findByF_N_PrevAndNext(
		DLFileVersionPK dlFileVersionPK, String folderId, String name,
		OrderByComparator obc)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(dlFileVersionPK);
		int count = countByF_N(folderId, name);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileVersion WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
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
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileVersion);
			DLFileVersion[] array = new DLFileVersion[3];
			array[0] = (DLFileVersion)objArray[0];
			array[1] = (DLFileVersion)objArray[1];
			array[2] = (DLFileVersion)objArray[2];

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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileVersion ");

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

	public void removeByF_N(String folderId, String name)
		throws SystemException {
		Iterator itr = findByF_N(folderId, name).iterator();

		while (itr.hasNext()) {
			DLFileVersion dlFileVersion = (DLFileVersion)itr.next();
			remove(dlFileVersion);
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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileVersion WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(DLFileVersionPersistence.class);
}