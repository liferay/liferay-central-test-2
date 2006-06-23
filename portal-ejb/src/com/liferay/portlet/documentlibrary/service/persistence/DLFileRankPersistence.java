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

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;

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
 * <a href="DLFileRankPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileRankPersistence extends BasePersistence {
	public com.liferay.portlet.documentlibrary.model.DLFileRank create(
		DLFileRankPK dlFileRankPK) {
		DLFileRankHBM dlFileRankHBM = new DLFileRankHBM();
		dlFileRankHBM.setNew(true);
		dlFileRankHBM.setPrimaryKey(dlFileRankPK);

		return DLFileRankHBMUtil.model(dlFileRankHBM);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		DLFileRankPK dlFileRankPK)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)session.get(DLFileRankHBM.class,
					dlFileRankPK);

			if (dlFileRankHBM == null) {
				_log.warn("No DLFileRank exists with the primary key " +
					dlFileRankPK.toString());
				throw new NoSuchFileRankException(
					"No DLFileRank exists with the primary key " +
					dlFileRankPK.toString());
			}

			session.delete(dlFileRankHBM);
			session.flush();

			return DLFileRankHBMUtil.model(dlFileRankHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws SystemException {
		Session session = null;

		try {
			if (dlFileRank.isNew() || dlFileRank.isModified()) {
				session = openSession();

				if (dlFileRank.isNew()) {
					DLFileRankHBM dlFileRankHBM = new DLFileRankHBM();
					dlFileRankHBM.setCompanyId(dlFileRank.getCompanyId());
					dlFileRankHBM.setUserId(dlFileRank.getUserId());
					dlFileRankHBM.setFolderId(dlFileRank.getFolderId());
					dlFileRankHBM.setName(dlFileRank.getName());
					dlFileRankHBM.setCreateDate(dlFileRank.getCreateDate());
					session.save(dlFileRankHBM);
					session.flush();
				}
				else {
					DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)session.get(DLFileRankHBM.class,
							dlFileRank.getPrimaryKey());

					if (dlFileRankHBM != null) {
						dlFileRankHBM.setCreateDate(dlFileRank.getCreateDate());
						session.flush();
					}
					else {
						dlFileRankHBM = new DLFileRankHBM();
						dlFileRankHBM.setCompanyId(dlFileRank.getCompanyId());
						dlFileRankHBM.setUserId(dlFileRank.getUserId());
						dlFileRankHBM.setFolderId(dlFileRank.getFolderId());
						dlFileRankHBM.setName(dlFileRank.getName());
						dlFileRankHBM.setCreateDate(dlFileRank.getCreateDate());
						session.save(dlFileRankHBM);
						session.flush();
					}
				}

				dlFileRank.setNew(false);
				dlFileRank.setModified(false);
			}

			return dlFileRank;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		DLFileRankPK dlFileRankPK)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)session.get(DLFileRankHBM.class,
					dlFileRankPK);

			if (dlFileRankHBM == null) {
				_log.warn("No DLFileRank exists with the primary key " +
					dlFileRankPK.toString());
				throw new NoSuchFileRankException(
					"No DLFileRank exists with the primary key " +
					dlFileRankPK.toString());
			}

			return DLFileRankHBMUtil.model(dlFileRankHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				list.add(DLFileRankHBMUtil.model(dlFileRankHBM));
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

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				list.add(DLFileRankHBMUtil.model(dlFileRankHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFileRank exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileRankException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileRank)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFileRank exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileRankException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileRank)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		DLFileRankPK dlFileRankPK, String userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank = findByPrimaryKey(dlFileRankPK);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank, DLFileRankHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFileRank[] array = new com.liferay.portlet.documentlibrary.model.DLFileRank[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[2];

			return array;
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
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

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
			query.append("createDate DESC");

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
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				list.add(DLFileRankHBMUtil.model(dlFileRankHBM));
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
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

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
				query.append("createDate DESC");
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
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				list.add(DLFileRankHBMUtil.model(dlFileRankHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_First(
		String folderId, String name, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		List list = findByF_N(folderId, name, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFileRank exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileRankException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileRank)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_Last(
		String folderId, String name, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		int count = countByF_N(folderId, name);
		List list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFileRank exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "folderId=";
			msg += folderId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFileRankException(msg);
		}
		else {
			return (com.liferay.portlet.documentlibrary.model.DLFileRank)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank[] findByF_N_PrevAndNext(
		DLFileRankPK dlFileRankPK, String folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank = findByPrimaryKey(dlFileRankPK);
		int count = countByF_N(folderId, name);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

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
				query.append("createDate DESC");
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
					dlFileRank, DLFileRankHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFileRank[] array = new com.liferay.portlet.documentlibrary.model.DLFileRank[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFileRank)objArray[2];

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
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				list.add(DLFileRankHBMUtil.model(dlFileRankHBM));
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				session.delete(dlFileRankHBM);
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

	public void removeByF_N(String folderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

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
			query.append("createDate DESC");

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
				DLFileRankHBM dlFileRankHBM = (DLFileRankHBM)itr.next();
				session.delete(dlFileRankHBM);
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

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public int countByF_N(String folderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM DLFileRank IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFileRankHBM WHERE ");

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

	private static Log _log = LogFactory.getLog(DLFileRankPersistence.class);
}