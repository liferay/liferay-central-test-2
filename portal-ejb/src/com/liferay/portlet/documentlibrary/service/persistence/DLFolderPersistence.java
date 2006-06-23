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

import com.liferay.portlet.documentlibrary.NoSuchFolderException;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFolderPersistence extends BasePersistence {
	public com.liferay.portlet.documentlibrary.model.DLFolder create(
		String folderId) {
		DLFolderHBM dlFolderHBM = new DLFolderHBM();
		dlFolderHBM.setNew(true);
		dlFolderHBM.setPrimaryKey(folderId);

		return DLFolderHBMUtil.model(dlFolderHBM);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder remove(
		String folderId) throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolderHBM dlFolderHBM = (DLFolderHBM)session.get(DLFolderHBM.class,
					folderId);

			if (dlFolderHBM == null) {
				_log.warn("No DLFolder exists with the primary key " +
					folderId.toString());
				throw new NoSuchFolderException(
					"No DLFolder exists with the primary key " +
					folderId.toString());
			}

			session.delete(dlFolderHBM);
			session.flush();

			return DLFolderHBMUtil.model(dlFolderHBM);
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
		Session session = null;

		try {
			if (dlFolder.isNew() || dlFolder.isModified()) {
				session = openSession();

				if (dlFolder.isNew()) {
					DLFolderHBM dlFolderHBM = new DLFolderHBM();
					dlFolderHBM.setFolderId(dlFolder.getFolderId());
					dlFolderHBM.setGroupId(dlFolder.getGroupId());
					dlFolderHBM.setCompanyId(dlFolder.getCompanyId());
					dlFolderHBM.setUserId(dlFolder.getUserId());
					dlFolderHBM.setUserName(dlFolder.getUserName());
					dlFolderHBM.setCreateDate(dlFolder.getCreateDate());
					dlFolderHBM.setModifiedDate(dlFolder.getModifiedDate());
					dlFolderHBM.setParentFolderId(dlFolder.getParentFolderId());
					dlFolderHBM.setName(dlFolder.getName());
					dlFolderHBM.setDescription(dlFolder.getDescription());
					dlFolderHBM.setLastPostDate(dlFolder.getLastPostDate());
					session.save(dlFolderHBM);
					session.flush();
				}
				else {
					DLFolderHBM dlFolderHBM = (DLFolderHBM)session.get(DLFolderHBM.class,
							dlFolder.getPrimaryKey());

					if (dlFolderHBM != null) {
						dlFolderHBM.setGroupId(dlFolder.getGroupId());
						dlFolderHBM.setCompanyId(dlFolder.getCompanyId());
						dlFolderHBM.setUserId(dlFolder.getUserId());
						dlFolderHBM.setUserName(dlFolder.getUserName());
						dlFolderHBM.setCreateDate(dlFolder.getCreateDate());
						dlFolderHBM.setModifiedDate(dlFolder.getModifiedDate());
						dlFolderHBM.setParentFolderId(dlFolder.getParentFolderId());
						dlFolderHBM.setName(dlFolder.getName());
						dlFolderHBM.setDescription(dlFolder.getDescription());
						dlFolderHBM.setLastPostDate(dlFolder.getLastPostDate());
						session.flush();
					}
					else {
						dlFolderHBM = new DLFolderHBM();
						dlFolderHBM.setFolderId(dlFolder.getFolderId());
						dlFolderHBM.setGroupId(dlFolder.getGroupId());
						dlFolderHBM.setCompanyId(dlFolder.getCompanyId());
						dlFolderHBM.setUserId(dlFolder.getUserId());
						dlFolderHBM.setUserName(dlFolder.getUserName());
						dlFolderHBM.setCreateDate(dlFolder.getCreateDate());
						dlFolderHBM.setModifiedDate(dlFolder.getModifiedDate());
						dlFolderHBM.setParentFolderId(dlFolder.getParentFolderId());
						dlFolderHBM.setName(dlFolder.getName());
						dlFolderHBM.setDescription(dlFolder.getDescription());
						dlFolderHBM.setLastPostDate(dlFolder.getLastPostDate());
						session.save(dlFolderHBM);
						session.flush();
					}
				}

				dlFolder.setNew(false);
				dlFolder.setModified(false);
			}

			return dlFolder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder findByPrimaryKey(
		String folderId) throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFolderHBM dlFolderHBM = (DLFolderHBM)session.get(DLFolderHBM.class,
					folderId);

			if (dlFolderHBM == null) {
				_log.warn("No DLFolder exists with the primary key " +
					folderId.toString());
				throw new NoSuchFolderException(
					"No DLFolder exists with the primary key " +
					folderId.toString());
			}

			return DLFolderHBMUtil.model(dlFolderHBM);
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_First(
		String groupId, OrderByComparator obc)
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_Last(
		String groupId, OrderByComparator obc)
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByGroupId_PrevAndNext(
		String folderId, String groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFolder, DLFolderHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFolder[] array = new com.liferay.portlet.documentlibrary.model.DLFolder[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[2];

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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_Last(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByCompanyId_PrevAndNext(
		String folderId, String companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFolder, DLFolderHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFolder[] array = new com.liferay.portlet.documentlibrary.model.DLFolder[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[2];

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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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

	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_First(
		String groupId, String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_Last(
		String groupId, String parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
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
			return (com.liferay.portlet.documentlibrary.model.DLFolder)list.get(0);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_P_PrevAndNext(
		String folderId, String groupId, String parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder = findByPrimaryKey(folderId);
		int count = countByG_P(groupId, parentFolderId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFolder, DLFolderHBMUtil.getInstance());
			com.liferay.portlet.documentlibrary.model.DLFolder[] array = new com.liferay.portlet.documentlibrary.model.DLFolder[3];
			array[0] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[0];
			array[1] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[1];
			array[2] = (com.liferay.portlet.documentlibrary.model.DLFolder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder findByP_N(
		String parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
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

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No DLFolder exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "parentFolderId=";
				msg += parentFolderId;
				msg += ", ";
				msg += "name=";
				msg += name;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchFolderException(msg);
			}

			DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();

			return DLFolderHBMUtil.model(dlFolderHBM);
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				list.add(DLFolderHBMUtil.model(dlFolderHBM));
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				session.delete(dlFolderHBM);
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				session.delete(dlFolderHBM);
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				session.delete(dlFolderHBM);
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

	public void removeByP_N(String parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
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

			if (parentFolderId != null) {
				q.setString(queryPos++, parentFolderId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				DLFolderHBM dlFolderHBM = (DLFolderHBM)itr.next();
				session.delete(dlFolderHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No DLFolder exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "parentFolderId=";
				msg += parentFolderId;
				msg += ", ";
				msg += "name=";
				msg += name;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchFolderException(msg);
			}
			else {
				throw new SystemException(he);
			}
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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	public int countByG_P(String groupId, String parentFolderId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

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
				"FROM DLFolder IN CLASS com.liferay.portlet.documentlibrary.service.persistence.DLFolderHBM WHERE ");

			if (parentFolderId == null) {
				query.append("parentFolderId is null");
			}
			else {
				query.append("parentFolderId = ?");
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

	private static Log _log = LogFactory.getLog(DLFolderPersistence.class);
}