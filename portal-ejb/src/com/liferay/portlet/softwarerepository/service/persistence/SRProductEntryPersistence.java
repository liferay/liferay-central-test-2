/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.softwarerepository.NoSuchProductEntryException;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.model.impl.SRProductEntryImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRProductEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryPersistence extends BasePersistence {
	public SRProductEntry create(long productEntryId) {
		SRProductEntry srProductEntry = new SRProductEntryImpl();
		srProductEntry.setNew(true);
		srProductEntry.setPrimaryKey(productEntryId);

		return srProductEntry;
	}

	public SRProductEntry remove(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SRProductEntry srProductEntry = (SRProductEntry)session.get(SRProductEntryImpl.class,
					new Long(productEntryId));

			if (srProductEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SRProductEntry exists with the primary key " +
						productEntryId);
				}

				throw new NoSuchProductEntryException(
					"No SRProductEntry exists with the primary key " +
					productEntryId);
			}

			return remove(srProductEntry);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRProductEntry remove(SRProductEntry srProductEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(srProductEntry);
			session.flush();
			clearSRLicenses.clear(srProductEntry.getPrimaryKey());

			return srProductEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry update(
		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry)
		throws SystemException {
		return update(srProductEntry, false);
	}

	public com.liferay.portlet.softwarerepository.model.SRProductEntry update(
		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(srProductEntry);
			}
			else {
				if (srProductEntry.isNew()) {
					session.save(srProductEntry);
				}
			}

			session.flush();
			srProductEntry.setNew(false);

			return srProductEntry;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRProductEntry findByPrimaryKey(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		SRProductEntry srProductEntry = fetchByPrimaryKey(productEntryId);

		if (srProductEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SRProductEntry exists with the primary key " +
					productEntryId);
			}

			throw new NoSuchProductEntryException(
				"No SRProductEntry exists with the primary key " +
				productEntryId);
		}

		return srProductEntry;
	}

	public SRProductEntry fetchByPrimaryKey(long productEntryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SRProductEntry)session.get(SRProductEntryImpl.class,
				new Long(productEntryId));
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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("modifiedDate DESC").append(", ");
			query.append("name DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public SRProductEntry findByGroupId_First(String groupId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry findByGroupId_Last(String groupId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry[] findByGroupId_PrevAndNext(long productEntryId,
		String groupId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SRProductEntry srProductEntry = findByPrimaryKey(productEntryId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srProductEntry);
			SRProductEntry[] array = new SRProductEntryImpl[3];
			array[0] = (SRProductEntry)objArray[0];
			array[1] = (SRProductEntry)objArray[1];
			array[2] = (SRProductEntry)objArray[2];

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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("modifiedDate DESC").append(", ");
			query.append("name DESC");

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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
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

	public SRProductEntry findByCompanyId_First(String companyId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry findByCompanyId_Last(String companyId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry[] findByCompanyId_PrevAndNext(long productEntryId,
		String companyId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SRProductEntry srProductEntry = findByPrimaryKey(productEntryId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srProductEntry);
			SRProductEntry[] array = new SRProductEntryImpl[3];
			array[0] = (SRProductEntry)objArray[0];
			array[1] = (SRProductEntry)objArray[1];
			array[2] = (SRProductEntry)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_U(String groupId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("modifiedDate DESC").append(", ");
			query.append("name DESC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public List findByG_U(String groupId, String userId, int begin, int end)
		throws SystemException {
		return findByG_U(groupId, userId, begin, end, null);
	}

	public List findByG_U(String groupId, String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public SRProductEntry findByG_U_First(String groupId, String userId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry findByG_U_Last(String groupId, String userId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByG_U(groupId, userId);
		List list = findByG_U(groupId, userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRProductEntry exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchProductEntryException(msg);
		}
		else {
			return (SRProductEntry)list.get(0);
		}
	}

	public SRProductEntry[] findByG_U_PrevAndNext(long productEntryId,
		String groupId, String userId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SRProductEntry srProductEntry = findByPrimaryKey(productEntryId);
		int count = countByG_U(groupId, userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
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
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srProductEntry);
			SRProductEntry[] array = new SRProductEntryImpl[3];
			array[0] = (SRProductEntry)objArray[0];
			array[1] = (SRProductEntry)objArray[1];
			array[2] = (SRProductEntry)objArray[2];

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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("modifiedDate DESC").append(", ");
				query.append("name DESC");
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

	public void removeByGroupId(String groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			SRProductEntry srProductEntry = (SRProductEntry)itr.next();
			remove(srProductEntry);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			SRProductEntry srProductEntry = (SRProductEntry)itr.next();
			remove(srProductEntry);
		}
	}

	public void removeByG_U(String groupId, String userId)
		throws SystemException {
		Iterator itr = findByG_U(groupId, userId).iterator();

		while (itr.hasNext()) {
			SRProductEntry srProductEntry = (SRProductEntry)itr.next();
			remove(srProductEntry);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SRProductEntry)itr.next());
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

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

	public int countByG_U(String groupId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRProductEntry");

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

	public List getSRLicenses(long pk)
		throws NoSuchProductEntryException, SystemException {
		return getSRLicenses(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getSRLicenses(long pk, int begin, int end)
		throws NoSuchProductEntryException, SystemException {
		return getSRLicenses(pk, begin, end, null);
	}

	public List getSRLicenses(long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETSRLICENSES;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "SRLicense.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("SRLicense",
				com.liferay.portlet.softwarerepository.model.impl.SRLicenseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getSRLicensesSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETSRLICENSESSIZE);
			q.setCacheable(false);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

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

	public boolean containsSRLicense(long pk, long srLicensePK)
		throws SystemException {
		try {
			return containsSRLicense.contains(pk, srLicensePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsSRLicenses(long pk) throws SystemException {
		if (getSRLicensesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSRLicense(long pk, long srLicensePK)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			addSRLicense.add(pk, srLicensePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRLicense(long pk,
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			addSRLicense.add(pk, srLicense.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRLicenses(long pk, long[] srLicensePKs)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			for (int i = 0; i < srLicensePKs.length; i++) {
				addSRLicense.add(pk, srLicensePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addSRLicenses(long pk, List srLicenses)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			for (int i = 0; i < srLicenses.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRLicense srLicense =
					(com.liferay.portlet.softwarerepository.model.SRLicense)srLicenses.get(i);
				addSRLicense.add(pk, srLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearSRLicenses(long pk)
		throws NoSuchProductEntryException, SystemException {
		try {
			clearSRLicenses.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRLicense(long pk, long srLicensePK)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			removeSRLicense.remove(pk, srLicensePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRLicense(long pk,
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			removeSRLicense.remove(pk, srLicense.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRLicenses(long pk, long[] srLicensePKs)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			for (int i = 0; i < srLicensePKs.length; i++) {
				removeSRLicense.remove(pk, srLicensePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeSRLicenses(long pk, List srLicenses)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			for (int i = 0; i < srLicenses.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRLicense srLicense =
					(com.liferay.portlet.softwarerepository.model.SRLicense)srLicenses.get(i);
				removeSRLicense.remove(pk, srLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setSRLicenses(long pk, long[] srLicensePKs)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			clearSRLicenses.clear(pk);

			for (int i = 0; i < srLicensePKs.length; i++) {
				addSRLicense.add(pk, srLicensePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setSRLicenses(long pk, List srLicenses)
		throws NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException, 
			SystemException {
		try {
			clearSRLicenses.clear(pk);

			for (int i = 0; i < srLicenses.size(); i++) {
				com.liferay.portlet.softwarerepository.model.SRLicense srLicense =
					(com.liferay.portlet.softwarerepository.model.SRLicense)srLicenses.get(i);
				addSRLicense.add(pk, srLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	protected void initDao() {
		containsSRLicense = new ContainsSRLicense(this);
		addSRLicense = new AddSRLicense(this);
		clearSRLicenses = new ClearSRLicenses(this);
		removeSRLicense = new RemoveSRLicense(this);
	}

	protected ContainsSRLicense containsSRLicense;
	protected AddSRLicense addSRLicense;
	protected ClearSRLicenses clearSRLicenses;
	protected RemoveSRLicense removeSRLicense;

	protected class ContainsSRLicense extends MappingSqlQuery {
		protected ContainsSRLicense(SRProductEntryPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSSRLICENSE);
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long productEntryId, long licenseId) {
			List results = execute(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddSRLicense extends SqlUpdate {
		protected AddSRLicense(SRProductEntryPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO SRLicenses_SRProductEntries (productEntryId, licenseId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected void add(long productEntryId, long licenseId) {
			if (!_persistence.containsSRLicense.contains(productEntryId,
						licenseId)) {
				update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});
			}
		}

		private SRProductEntryPersistence _persistence;
	}

	protected class ClearSRLicenses extends SqlUpdate {
		protected ClearSRLicenses(SRProductEntryPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM SRLicenses_SRProductEntries WHERE productEntryId = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected void clear(long productEntryId) {
			update(new Object[] { new Long(productEntryId) });
		}
	}

	protected class RemoveSRLicense extends SqlUpdate {
		protected RemoveSRLicense(SRProductEntryPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM SRLicenses_SRProductEntries WHERE productEntryId = ? AND licenseId = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected void remove(long productEntryId, long licenseId) {
			update(new Object[] { new Long(productEntryId), new Long(licenseId) });
		}
	}

	private static final String _SQL_GETSRLICENSES = "SELECT {SRLicense.*} FROM SRLicense INNER JOIN SRLicenses_SRProductEntries ON (SRLicenses_SRProductEntries.licenseId = SRLicense.licenseId) WHERE (SRLicenses_SRProductEntries.productEntryId = ?)";
	private static final String _SQL_GETSRLICENSESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SRLicenses_SRProductEntries WHERE productEntryId = ?";
	private static final String _SQL_CONTAINSSRLICENSE = "SELECT COUNT(*) AS COUNT_VALUE FROM SRLicenses_SRProductEntries WHERE productEntryId = ? AND licenseId = ?";
	private static Log _log = LogFactory.getLog(SRProductEntryPersistence.class);
}