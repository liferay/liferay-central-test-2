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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.NoSuchCategoryException;

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
 * <a href="MBCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBCategoryPersistence extends BasePersistence {
	public com.liferay.portlet.messageboards.model.MBCategory create(
		String categoryId) {
		MBCategoryHBM mbCategoryHBM = new MBCategoryHBM();
		mbCategoryHBM.setNew(true);
		mbCategoryHBM.setPrimaryKey(categoryId);

		return MBCategoryHBMUtil.model(mbCategoryHBM);
	}

	public com.liferay.portlet.messageboards.model.MBCategory remove(
		String categoryId) throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)session.get(MBCategoryHBM.class,
					categoryId);

			if (mbCategoryHBM == null) {
				_log.warn("No MBCategory exists with the primary key " +
					categoryId.toString());
				throw new NoSuchCategoryException(
					"No MBCategory exists with the primary key " +
					categoryId.toString());
			}

			session.delete(mbCategoryHBM);
			session.flush();

			return MBCategoryHBMUtil.model(mbCategoryHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory update(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws SystemException {
		Session session = null;

		try {
			if (mbCategory.isNew() || mbCategory.isModified()) {
				session = openSession();

				if (mbCategory.isNew()) {
					MBCategoryHBM mbCategoryHBM = new MBCategoryHBM();
					mbCategoryHBM.setCategoryId(mbCategory.getCategoryId());
					mbCategoryHBM.setGroupId(mbCategory.getGroupId());
					mbCategoryHBM.setCompanyId(mbCategory.getCompanyId());
					mbCategoryHBM.setUserId(mbCategory.getUserId());
					mbCategoryHBM.setUserName(mbCategory.getUserName());
					mbCategoryHBM.setCreateDate(mbCategory.getCreateDate());
					mbCategoryHBM.setModifiedDate(mbCategory.getModifiedDate());
					mbCategoryHBM.setParentCategoryId(mbCategory.getParentCategoryId());
					mbCategoryHBM.setName(mbCategory.getName());
					mbCategoryHBM.setDescription(mbCategory.getDescription());
					mbCategoryHBM.setLastPostDate(mbCategory.getLastPostDate());
					session.save(mbCategoryHBM);
					session.flush();
				}
				else {
					MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)session.get(MBCategoryHBM.class,
							mbCategory.getPrimaryKey());

					if (mbCategoryHBM != null) {
						mbCategoryHBM.setGroupId(mbCategory.getGroupId());
						mbCategoryHBM.setCompanyId(mbCategory.getCompanyId());
						mbCategoryHBM.setUserId(mbCategory.getUserId());
						mbCategoryHBM.setUserName(mbCategory.getUserName());
						mbCategoryHBM.setCreateDate(mbCategory.getCreateDate());
						mbCategoryHBM.setModifiedDate(mbCategory.getModifiedDate());
						mbCategoryHBM.setParentCategoryId(mbCategory.getParentCategoryId());
						mbCategoryHBM.setName(mbCategory.getName());
						mbCategoryHBM.setDescription(mbCategory.getDescription());
						mbCategoryHBM.setLastPostDate(mbCategory.getLastPostDate());
						session.flush();
					}
					else {
						mbCategoryHBM = new MBCategoryHBM();
						mbCategoryHBM.setCategoryId(mbCategory.getCategoryId());
						mbCategoryHBM.setGroupId(mbCategory.getGroupId());
						mbCategoryHBM.setCompanyId(mbCategory.getCompanyId());
						mbCategoryHBM.setUserId(mbCategory.getUserId());
						mbCategoryHBM.setUserName(mbCategory.getUserName());
						mbCategoryHBM.setCreateDate(mbCategory.getCreateDate());
						mbCategoryHBM.setModifiedDate(mbCategory.getModifiedDate());
						mbCategoryHBM.setParentCategoryId(mbCategory.getParentCategoryId());
						mbCategoryHBM.setName(mbCategory.getName());
						mbCategoryHBM.setDescription(mbCategory.getDescription());
						mbCategoryHBM.setLastPostDate(mbCategory.getLastPostDate());
						session.save(mbCategoryHBM);
						session.flush();
					}
				}

				mbCategory.setNew(false);
				mbCategory.setModified(false);
			}

			return mbCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		String categoryId) throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)session.get(MBCategoryHBM.class,
					categoryId);

			if (mbCategoryHBM == null) {
				_log.warn("No MBCategory exists with the primary key " +
					categoryId.toString());
				throw new NoSuchCategoryException(
					"No MBCategory exists with the primary key " +
					categoryId.toString());
			}

			return MBCategoryHBMUtil.model(mbCategoryHBM);
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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

	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		String categoryId, String groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		com.liferay.portlet.messageboards.model.MBCategory mbCategory = findByPrimaryKey(categoryId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory, MBCategoryHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBCategory[] array = new com.liferay.portlet.messageboards.model.MBCategory[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[2];

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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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

	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		String categoryId, String companyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		com.liferay.portlet.messageboards.model.MBCategory mbCategory = findByPrimaryKey(categoryId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory, MBCategoryHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBCategory[] array = new com.liferay.portlet.messageboards.model.MBCategory[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId is null");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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

	public List findByG_P(String groupId, String parentCategoryId, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, parentCategoryId, begin, end, null);
	}

	public List findByG_P(String groupId, String parentCategoryId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId is null");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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

	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		String groupId, String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List list = findByG_P(groupId, parentCategoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		String groupId, String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByG_P(groupId, parentCategoryId);
		List list = findByG_P(groupId, parentCategoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBCategory)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		String categoryId, String groupId, String parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		com.liferay.portlet.messageboards.model.MBCategory mbCategory = findByPrimaryKey(categoryId);
		int count = countByG_P(groupId, parentCategoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId is null");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory, MBCategoryHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBCategory[] array = new com.liferay.portlet.messageboards.model.MBCategory[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBCategory)objArray[2];

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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				list.add(MBCategoryHBMUtil.model(mbCategoryHBM));
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				session.delete(mbCategoryHBM);
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				session.delete(mbCategoryHBM);
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

	public void removeByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId is null");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBCategoryHBM mbCategoryHBM = (MBCategoryHBM)itr.next();
				session.delete(mbCategoryHBM);
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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

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

	public int countByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBCategory IN CLASS com.liferay.portlet.messageboards.service.persistence.MBCategoryHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId is null");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
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

	private static Log _log = LogFactory.getLog(MBCategoryPersistence.class);
}