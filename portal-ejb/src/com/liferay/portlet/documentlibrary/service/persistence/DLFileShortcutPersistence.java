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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;

import com.liferay.util.StringMaker;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileShortcutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileShortcutPersistence extends BasePersistence {
	public DLFileShortcut create(long fileShortcutId) {
		DLFileShortcut dlFileShortcut = new DLFileShortcutImpl();
		dlFileShortcut.setNew(true);
		dlFileShortcut.setPrimaryKey(fileShortcutId);

		return dlFileShortcut;
	}

	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileShortcut dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
					new Long(fileShortcutId));

			if (dlFileShortcut == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileShortcut exists with the primary key " +
						fileShortcutId);
				}

				throw new NoSuchFileShortcutException(
					"No DLFileShortcut exists with the primary key " +
					fileShortcutId);
			}

			return remove(dlFileShortcut);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileShortcut remove(DLFileShortcut dlFileShortcut)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(dlFileShortcut);
			session.flush();

			return dlFileShortcut;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut update(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws SystemException {
		return update(dlFileShortcut, false);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut update(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(dlFileShortcut);
			}
			else {
				if (dlFileShortcut.isNew()) {
					session.save(dlFileShortcut);
				}
			}

			session.flush();
			dlFileShortcut.setNew(false);

			return dlFileShortcut;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByPrimaryKey(fileShortcutId);

		if (dlFileShortcut == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileShortcut exists with the primary key " +
					fileShortcutId);
			}

			throw new NoSuchFileShortcutException(
				"No DLFileShortcut exists with the primary key " +
				fileShortcutId);
		}

		return dlFileShortcut;
	}

	public DLFileShortcut fetchByPrimaryKey(long fileShortcutId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DLFileShortcut)session.get(DLFileShortcutImpl.class,
				new Long(fileShortcutId));
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
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

	public List findByFolderId(String folderId, int begin, int end)
		throws SystemException {
		return findByFolderId(folderId, begin, end, null);
	}

	public List findByFolderId(String folderId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
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

	public DLFileShortcut findByFolderId_First(String folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileShortcut exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return (DLFileShortcut)list.get(0);
		}
	}

	public DLFileShortcut findByFolderId_Last(String folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByFolderId(folderId);
		List list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileShortcut exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return (DLFileShortcut)list.get(0);
		}
	}

	public DLFileShortcut[] findByFolderId_PrevAndNext(long fileShortcutId,
		String folderId, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);
			DLFileShortcut[] array = new DLFileShortcutImpl[3];
			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByTF_TN(String toFolderId, String toName)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (toFolderId == null) {
				query.append("toFolderId IS NULL");
			}
			else {
				query.append("toFolderId = ?");
			}

			query.append(" AND ");

			if (toName == null) {
				query.append("toName IS NULL");
			}
			else {
				query.append("toName = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (toFolderId != null) {
				q.setString(queryPos++, toFolderId);
			}

			if (toName != null) {
				q.setString(queryPos++, toName);
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

	public List findByTF_TN(String toFolderId, String toName, int begin, int end)
		throws SystemException {
		return findByTF_TN(toFolderId, toName, begin, end, null);
	}

	public List findByTF_TN(String toFolderId, String toName, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (toFolderId == null) {
				query.append("toFolderId IS NULL");
			}
			else {
				query.append("toFolderId = ?");
			}

			query.append(" AND ");

			if (toName == null) {
				query.append("toName IS NULL");
			}
			else {
				query.append("toName = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (toFolderId != null) {
				q.setString(queryPos++, toFolderId);
			}

			if (toName != null) {
				q.setString(queryPos++, toName);
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

	public DLFileShortcut findByTF_TN_First(String toFolderId, String toName,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List list = findByTF_TN(toFolderId, toName, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileShortcut exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("toFolderId=");
			msg.append(toFolderId);
			msg.append(", ");
			msg.append("toName=");
			msg.append(toName);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return (DLFileShortcut)list.get(0);
		}
	}

	public DLFileShortcut findByTF_TN_Last(String toFolderId, String toName,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByTF_TN(toFolderId, toName);
		List list = findByTF_TN(toFolderId, toName, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileShortcut exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("toFolderId=");
			msg.append(toFolderId);
			msg.append(", ");
			msg.append("toName=");
			msg.append(toName);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return (DLFileShortcut)list.get(0);
		}
	}

	public DLFileShortcut[] findByTF_TN_PrevAndNext(long fileShortcutId,
		String toFolderId, String toName, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);
		int count = countByTF_TN(toFolderId, toName);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (toFolderId == null) {
				query.append("toFolderId IS NULL");
			}
			else {
				query.append("toFolderId = ?");
			}

			query.append(" AND ");

			if (toName == null) {
				query.append("toName IS NULL");
			}
			else {
				query.append("toName = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (toFolderId != null) {
				q.setString(queryPos++, toFolderId);
			}

			if (toName != null) {
				q.setString(queryPos++, toName);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);
			DLFileShortcut[] array = new DLFileShortcutImpl[3];
			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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

	public void removeByFolderId(String folderId) throws SystemException {
		Iterator itr = findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)itr.next();
			remove(dlFileShortcut);
		}
	}

	public void removeByTF_TN(String toFolderId, String toName)
		throws SystemException {
		Iterator itr = findByTF_TN(toFolderId, toName).iterator();

		while (itr.hasNext()) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)itr.next();
			remove(dlFileShortcut);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((DLFileShortcut)itr.next());
		}
	}

	public int countByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
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

	public int countByTF_TN(String toFolderId, String toName)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (toFolderId == null) {
				query.append("toFolderId IS NULL");
			}
			else {
				query.append("toFolderId = ?");
			}

			query.append(" AND ");

			if (toName == null) {
				query.append("toName IS NULL");
			}
			else {
				query.append("toName = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (toFolderId != null) {
				q.setString(queryPos++, toFolderId);
			}

			if (toName != null) {
				q.setString(queryPos++, toName);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(DLFileShortcutPersistence.class);
}