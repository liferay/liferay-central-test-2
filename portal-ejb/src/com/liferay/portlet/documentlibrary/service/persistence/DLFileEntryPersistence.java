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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

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
 * <a href="DLFileEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryPersistence extends BasePersistence {
	public DLFileEntry create(DLFileEntryPK dlFileEntryPK) {
		DLFileEntry dlFileEntry = new DLFileEntry();
		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(dlFileEntryPK);

		return dlFileEntry;
	}

	public DLFileEntry remove(DLFileEntryPK dlFileEntryPK)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntry.class,
					dlFileEntryPK);

			if (dlFileEntry == null) {
				_log.warn("No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
				throw new NoSuchFileEntryException(
					"No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
			}

			session.delete(dlFileEntry);
			session.flush();

			return dlFileEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry update(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws SystemException {
		Session session = null;

		try {
			if (dlFileEntry.isNew() || dlFileEntry.isModified()) {
				session = openSession();

				if (dlFileEntry.isNew()) {
					DLFileEntry dlFileEntryModel = new DLFileEntry();
					dlFileEntryModel.setFolderId(dlFileEntry.getFolderId());
					dlFileEntryModel.setName(dlFileEntry.getName());
					dlFileEntryModel.setCompanyId(dlFileEntry.getCompanyId());
					dlFileEntryModel.setUserId(dlFileEntry.getUserId());
					dlFileEntryModel.setUserName(dlFileEntry.getUserName());
					dlFileEntryModel.setVersionUserId(dlFileEntry.getVersionUserId());
					dlFileEntryModel.setVersionUserName(dlFileEntry.getVersionUserName());
					dlFileEntryModel.setCreateDate(dlFileEntry.getCreateDate());
					dlFileEntryModel.setModifiedDate(dlFileEntry.getModifiedDate());
					dlFileEntryModel.setTitle(dlFileEntry.getTitle());
					dlFileEntryModel.setDescription(dlFileEntry.getDescription());
					dlFileEntryModel.setVersion(dlFileEntry.getVersion());
					dlFileEntryModel.setSize(dlFileEntry.getSize());
					dlFileEntryModel.setReadCount(dlFileEntry.getReadCount());
					session.save(dlFileEntryModel);
					session.flush();
				}
				else {
					DLFileEntry dlFileEntryModel = (DLFileEntry)session.get(DLFileEntry.class,
							dlFileEntry.getPrimaryKey());

					if (dlFileEntryModel != null) {
						dlFileEntryModel.setCompanyId(dlFileEntry.getCompanyId());
						dlFileEntryModel.setUserId(dlFileEntry.getUserId());
						dlFileEntryModel.setUserName(dlFileEntry.getUserName());
						dlFileEntryModel.setVersionUserId(dlFileEntry.getVersionUserId());
						dlFileEntryModel.setVersionUserName(dlFileEntry.getVersionUserName());
						dlFileEntryModel.setCreateDate(dlFileEntry.getCreateDate());
						dlFileEntryModel.setModifiedDate(dlFileEntry.getModifiedDate());
						dlFileEntryModel.setTitle(dlFileEntry.getTitle());
						dlFileEntryModel.setDescription(dlFileEntry.getDescription());
						dlFileEntryModel.setVersion(dlFileEntry.getVersion());
						dlFileEntryModel.setSize(dlFileEntry.getSize());
						dlFileEntryModel.setReadCount(dlFileEntry.getReadCount());
						session.flush();
					}
					else {
						dlFileEntryModel = new DLFileEntry();
						dlFileEntryModel.setFolderId(dlFileEntry.getFolderId());
						dlFileEntryModel.setName(dlFileEntry.getName());
						dlFileEntryModel.setCompanyId(dlFileEntry.getCompanyId());
						dlFileEntryModel.setUserId(dlFileEntry.getUserId());
						dlFileEntryModel.setUserName(dlFileEntry.getUserName());
						dlFileEntryModel.setVersionUserId(dlFileEntry.getVersionUserId());
						dlFileEntryModel.setVersionUserName(dlFileEntry.getVersionUserName());
						dlFileEntryModel.setCreateDate(dlFileEntry.getCreateDate());
						dlFileEntryModel.setModifiedDate(dlFileEntry.getModifiedDate());
						dlFileEntryModel.setTitle(dlFileEntry.getTitle());
						dlFileEntryModel.setDescription(dlFileEntry.getDescription());
						dlFileEntryModel.setVersion(dlFileEntry.getVersion());
						dlFileEntryModel.setSize(dlFileEntry.getSize());
						dlFileEntryModel.setReadCount(dlFileEntry.getReadCount());
						session.save(dlFileEntryModel);
						session.flush();
					}
				}

				dlFileEntry.setNew(false);
				dlFileEntry.setModified(false);
			}

			return dlFileEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry findByPrimaryKey(DLFileEntryPK dlFileEntryPK)
		throws NoSuchFileEntryException, SystemException {
		return findByPrimaryKey(dlFileEntryPK, true);
	}

	public DLFileEntry findByPrimaryKey(DLFileEntryPK dlFileEntryPK,
		boolean throwNoSuchObjectException)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntry.class,
					dlFileEntryPK);

			if (dlFileEntry == null) {
				_log.warn("No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());

				if (throwNoSuchObjectException) {
					throw new NoSuchFileEntryException(
						"No DLFileEntry exists with the primary key " +
						dlFileEntryPK.toString());
				}
			}

			return dlFileEntry;
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

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
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

	public DLFileEntry findByFolderId_First(String folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List list = findByFolderId(folderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFileEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileEntryException(msg);
		}
		else {
			return (DLFileEntry)list.get(0);
		}
	}

	public DLFileEntry findByFolderId_Last(String folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByFolderId(folderId);
		List list = findByFolderId(folderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFileEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileEntryException(msg);
		}
		else {
			return (DLFileEntry)list.get(0);
		}
	}

	public DLFileEntry[] findByFolderId_PrevAndNext(
		DLFileEntryPK dlFileEntryPK, String folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(dlFileEntryPK);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);
			DLFileEntry[] array = new DLFileEntry[3];
			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry ");

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

	public void removeByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (folderId != null) {
				q.setString(queryPos++, folderId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFileEntry dlFileEntry = (DLFileEntry)itr.next();
				session.delete(dlFileEntry);
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

	public int countByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileEntry WHERE ");

			if (folderId == null) {
				query.append("folderId IS NULL");
			}
			else {
				query.append("folderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(DLFileEntryPersistence.class);
}