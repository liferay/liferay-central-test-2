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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFolderPersistence extends BasePersistence {
	public DLFolder create(String folderId) {
		DLFolder dlFolder = new DLFolderImpl();
		dlFolder.setNew(true);
		dlFolder.setPrimaryKey(folderId);

		return dlFolder;
	}

	public DLFolder remove(String folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolder dlFolder = (DLFolder)session.get(DLFolderImpl.class,
					folderId);

			if (dlFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFolder exists with the primary key " +
						folderId);
				}

				throw new NoSuchFolderException(
					"No DLFolder exists with the primary key " + folderId);
			}

			return remove(dlFolder);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder remove(DLFolder dlFolder) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(dlFolder);
			session.flush();

			return dlFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder update(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws SystemException {
		return update(dlFolder, false);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder update(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(dlFolder);
			}
			else {
				if (dlFolder.isNew()) {
					session.save(dlFolder);
				}
			}

			session.flush();
			dlFolder.setNew(false);

			return dlFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder findByPrimaryKey(String folderId)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFolder exists with the primary key " +
					folderId);
			}

			throw new NoSuchFolderException(
				"No DLFolder exists with the primary key " + folderId);
		}

		return dlFolder;
	}

	public DLFolder fetchByPrimaryKey(String folderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DLFolder)session.get(DLFolderImpl.class, folderId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(long groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder[] findByGroupId_PrevAndNext(String folderId, long groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);
			DLFolder[] array = new DLFolderImpl[3];
			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public DLFolder findByCompanyId_First(String companyId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFolderException(msg);
		}
		else {
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder[] findByCompanyId_PrevAndNext(String folderId,
		String companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);
			DLFolder[] array = new DLFolderImpl[3];
			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(long groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public List findByG_P(long groupId, String parentFolderId, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, begin, end, null);
	}

	public List findByG_P(long groupId, String parentFolderId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public DLFolder findByG_P_First(long groupId, String parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List list = findByG_P(groupId, parentFolderId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
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
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder findByG_P_Last(long groupId, String parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);
		List list = findByG_P(groupId, parentFolderId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No DLFolder exists with the key ";
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
			return (DLFolder)list.get(0);
		}
	}

	public DLFolder[] findByG_P_PrevAndNext(String folderId, long groupId,
		String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByG_P(groupId, parentFolderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, dlFolder);
			DLFolder[] array = new DLFolderImpl[3];
			array[0] = (DLFolder)objArray[0];
			array[1] = (DLFolder)objArray[1];
			array[2] = (DLFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFolder findByP_N(String parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = fetchByP_N(parentFolderId, name);

		if (dlFolder == null) {
			String msg = "No DLFolder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "parentFolderId=";
			msg += parentFolderId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchFolderException(msg);
		}

		return dlFolder;
	}

	public DLFolder fetchByP_N(String parentFolderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
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
			query.append("parentFolderId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			DLFolder dlFolder = (DLFolder)list.get(0);

			return dlFolder;
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
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentFolderId ASC").append(", ");
				query.append("name ASC");
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

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			DLFolder dlFolder = (DLFolder)itr.next();
			remove(dlFolder);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			DLFolder dlFolder = (DLFolder)itr.next();
			remove(dlFolder);
		}
	}

	public void removeByG_P(long groupId, String parentFolderId)
		throws SystemException {
		Iterator itr = findByG_P(groupId, parentFolderId).iterator();

		while (itr.hasNext()) {
			DLFolder dlFolder = (DLFolder)itr.next();
			remove(dlFolder);
		}
	}

	public void removeByP_N(String parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		DLFolder dlFolder = findByP_N(parentFolderId, name);
		remove(dlFolder);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((DLFolder)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public int countByG_P(long groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public int countByP_N(String parentFolderId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder WHERE ");

			if (parentFolderId == null) {
				query.append("parentFolderId IS NULL");
			}
			else {
				query.append("parentFolderId = ?");
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

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFolder");

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

	private static Log _log = LogFactory.getLog(DLFolderPersistence.class);
}