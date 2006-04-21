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
	public com.liferay.portlet.documentlibrary.model.DLFileEntry create(
		DLFileEntryPK dlFileEntryPK) {
		DLFileEntryHBM dlFileEntryHBM = new DLFileEntryHBM();
		dlFileEntryHBM.setNew(true);
		dlFileEntryHBM.setPrimaryKey(dlFileEntryPK);

		return DLFileEntryHBMUtil.model(dlFileEntryHBM);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry remove(
		DLFileEntryPK dlFileEntryPK)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)session.get(DLFileEntryHBM.class,
					dlFileEntryPK);

			if (dlFileEntryHBM == null) {
				_log.warn("No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
				throw new NoSuchFileEntryException(
					"No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
			}

			session.delete(dlFileEntryHBM);
			session.flush();

			return DLFileEntryHBMUtil.model(dlFileEntryHBM);
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
					DLFileEntryHBM dlFileEntryHBM = new DLFileEntryHBM();
					dlFileEntryHBM.setFolderId(dlFileEntry.getFolderId());
					dlFileEntryHBM.setName(dlFileEntry.getName());
					dlFileEntryHBM.setCompanyId(dlFileEntry.getCompanyId());
					dlFileEntryHBM.setUserId(dlFileEntry.getUserId());
					dlFileEntryHBM.setUserName(dlFileEntry.getUserName());
					dlFileEntryHBM.setVersionUserId(dlFileEntry.getVersionUserId());
					dlFileEntryHBM.setVersionUserName(dlFileEntry.getVersionUserName());
					dlFileEntryHBM.setCreateDate(dlFileEntry.getCreateDate());
					dlFileEntryHBM.setModifiedDate(dlFileEntry.getModifiedDate());
					dlFileEntryHBM.setTitle(dlFileEntry.getTitle());
					dlFileEntryHBM.setDescription(dlFileEntry.getDescription());
					dlFileEntryHBM.setVersion(dlFileEntry.getVersion());
					dlFileEntryHBM.setSize(dlFileEntry.getSize());
					dlFileEntryHBM.setReadCount(dlFileEntry.getReadCount());
					session.save(dlFileEntryHBM);
					session.flush();
				}
				else {
					DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)session.get(DLFileEntryHBM.class,
							dlFileEntry.getPrimaryKey());

					if (dlFileEntryHBM != null) {
						dlFileEntryHBM.setCompanyId(dlFileEntry.getCompanyId());
						dlFileEntryHBM.setUserId(dlFileEntry.getUserId());
						dlFileEntryHBM.setUserName(dlFileEntry.getUserName());
						dlFileEntryHBM.setVersionUserId(dlFileEntry.getVersionUserId());
						dlFileEntryHBM.setVersionUserName(dlFileEntry.getVersionUserName());
						dlFileEntryHBM.setCreateDate(dlFileEntry.getCreateDate());
						dlFileEntryHBM.setModifiedDate(dlFileEntry.getModifiedDate());
						dlFileEntryHBM.setTitle(dlFileEntry.getTitle());
						dlFileEntryHBM.setDescription(dlFileEntry.getDescription());
						dlFileEntryHBM.setVersion(dlFileEntry.getVersion());
						dlFileEntryHBM.setSize(dlFileEntry.getSize());
						dlFileEntryHBM.setReadCount(dlFileEntry.getReadCount());
						session.flush();
					}
					else {
						dlFileEntryHBM = new DLFileEntryHBM();
						dlFileEntryHBM.setFolderId(dlFileEntry.getFolderId());
						dlFileEntryHBM.setName(dlFileEntry.getName());
						dlFileEntryHBM.setCompanyId(dlFileEntry.getCompanyId());
						dlFileEntryHBM.setUserId(dlFileEntry.getUserId());
						dlFileEntryHBM.setUserName(dlFileEntry.getUserName());
						dlFileEntryHBM.setVersionUserId(dlFileEntry.getVersionUserId());
						dlFileEntryHBM.setVersionUserName(dlFileEntry.getVersionUserName());
						dlFileEntryHBM.setCreateDate(dlFileEntry.getCreateDate());
						dlFileEntryHBM.setModifiedDate(dlFileEntry.getModifiedDate());
						dlFileEntryHBM.setTitle(dlFileEntry.getTitle());
						dlFileEntryHBM.setDescription(dlFileEntry.getDescription());
						dlFileEntryHBM.setVersion(dlFileEntry.getVersion());
						dlFileEntryHBM.setSize(dlFileEntry.getSize());
						dlFileEntryHBM.setReadCount(dlFileEntry.getReadCount());
						session.save(dlFileEntryHBM);
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

	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByPrimaryKey(
		DLFileEntryPK dlFileEntryPK)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)session.get(DLFileEntryHBM.class,
					dlFileEntryPK);

			if (dlFileEntryHBM == null) {
				_log.warn("No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
				throw new NoSuchFileEntryException(
					"No DLFileEntry exists with the primary key " +
					dlFileEntryPK.toString());
			}

			return DLFileEntryHBMUtil.model(dlFileEntryHBM);
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
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)itr.next();
				list.add(DLFileEntryHBMUtil.model(dlFileEntryHBM));
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
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)itr.next();
				list.add(DLFileEntryHBMUtil.model(dlFileEntryHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByFolderId_First(
		String folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
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
			return (com.liferay.portlet.documentlibrary.model.DLFileEntry)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByFolderId_Last(
		String folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
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
			return (com.liferay.portlet.documentlibrary.model.DLFileEntry)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByFolderId_PrevAndNext(
		DLFileEntryPK dlFileEntryPK, String folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry = findByPrimaryKey(dlFileEntryPK);
		int count = countByFolderId(folderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry, DLFileEntryHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFileEntry[] array = new com.liferay.portlet.documentlibrary.model.DLFileEntry[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFileEntry)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFileEntry)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFileEntry)objArray[2];

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
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)itr.next();
				list.add(DLFileEntryHBMUtil.model(dlFileEntryHBM));
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

	public void removeByFolderId(String folderId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFileEntryHBM dlFileEntryHBM = (DLFileEntryHBM)itr.next();
				session.delete(dlFileEntryHBM);
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
				"FROM DLFileEntry IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryHBM WHERE ");
			query.append("folderId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, folderId);

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

	private static Log _log = LogFactory.getLog(DLFileEntryPersistence.class);
}