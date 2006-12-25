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

import com.liferay.portlet.softwarerepository.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarerepository.model.SRFrameworkVersion;
import com.liferay.portlet.softwarerepository.model.impl.SRFrameworkVersionImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRFrameworkVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionPersistence extends BasePersistence {
	public SRFrameworkVersion create(long frameworkVersionId) {
		SRFrameworkVersion srFrameworkVersion = new SRFrameworkVersionImpl();
		srFrameworkVersion.setNew(true);
		srFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return srFrameworkVersion;
	}

	public SRFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SRFrameworkVersion srFrameworkVersion = (SRFrameworkVersion)session.get(SRFrameworkVersionImpl.class,
					new Long(frameworkVersionId));

			if (srFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SRFrameworkVersion exists with the primary key " +
						frameworkVersionId);
				}

				throw new NoSuchFrameworkVersionException(
					"No SRFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			return remove(srFrameworkVersion);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRFrameworkVersion remove(SRFrameworkVersion srFrameworkVersion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(srFrameworkVersion);
			session.flush();

			return srFrameworkVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion update(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion)
		throws SystemException {
		return update(srFrameworkVersion, false);
	}

	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion update(
		com.liferay.portlet.softwarerepository.model.SRFrameworkVersion srFrameworkVersion,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(srFrameworkVersion);
			}
			else {
				if (srFrameworkVersion.isNew()) {
					session.save(srFrameworkVersion);
				}
			}

			session.flush();
			srFrameworkVersion.setNew(false);

			return srFrameworkVersion;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		SRFrameworkVersion srFrameworkVersion = fetchByPrimaryKey(frameworkVersionId);

		if (srFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SRFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			throw new NoSuchFrameworkVersionException(
				"No SRFrameworkVersion exists with the primary key " +
				frameworkVersionId);
		}

		return srFrameworkVersion;
	}

	public SRFrameworkVersion fetchByPrimaryKey(long frameworkVersionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SRFrameworkVersion)session.get(SRFrameworkVersionImpl.class,
				new Long(frameworkVersionId));
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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority ASC").append(", ");
			query.append("name ASC");

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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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
				query.append("priority ASC").append(", ");
				query.append("name ASC");
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

	public SRFrameworkVersion findByGroupId_First(String groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion findByGroupId_Last(String groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, String groupId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SRFrameworkVersion srFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srFrameworkVersion);
			SRFrameworkVersion[] array = new SRFrameworkVersionImpl[3];
			array[0] = (SRFrameworkVersion)objArray[0];
			array[1] = (SRFrameworkVersion)objArray[1];
			array[2] = (SRFrameworkVersion)objArray[2];

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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority ASC").append(", ");
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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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
				query.append("priority ASC").append(", ");
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

	public SRFrameworkVersion findByCompanyId_First(String companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion findByCompanyId_Last(String companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, String companyId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SRFrameworkVersion srFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srFrameworkVersion);
			SRFrameworkVersion[] array = new SRFrameworkVersionImpl[3];
			array[0] = (SRFrameworkVersion)objArray[0];
			array[1] = (SRFrameworkVersion)objArray[1];
			array[2] = (SRFrameworkVersion)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A(String groupId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A(String groupId, boolean active, int begin, int end)
		throws SystemException {
		return findByG_A(groupId, active, begin, end, null);
	}

	public List findByG_A(String groupId, boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRFrameworkVersion findByG_A_First(String groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByG_A(groupId, active, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion findByG_A_Last(String groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByG_A(groupId, active);
		List list = findByG_A(groupId, active, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRFrameworkVersion exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchFrameworkVersionException(msg);
		}
		else {
			return (SRFrameworkVersion)list.get(0);
		}
	}

	public SRFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		String groupId, boolean active, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SRFrameworkVersion srFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByG_A(groupId, active);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srFrameworkVersion);
			SRFrameworkVersion[] array = new SRFrameworkVersionImpl[3];
			array[0] = (SRFrameworkVersion)objArray[0];
			array[1] = (SRFrameworkVersion)objArray[1];
			array[2] = (SRFrameworkVersion)objArray[2];

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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
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

	public void removeByGroupId(String groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			SRFrameworkVersion srFrameworkVersion = (SRFrameworkVersion)itr.next();
			remove(srFrameworkVersion);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			SRFrameworkVersion srFrameworkVersion = (SRFrameworkVersion)itr.next();
			remove(srFrameworkVersion);
		}
	}

	public void removeByG_A(String groupId, boolean active)
		throws SystemException {
		Iterator itr = findByG_A(groupId, active).iterator();

		while (itr.hasNext()) {
			SRFrameworkVersion srFrameworkVersion = (SRFrameworkVersion)itr.next();
			remove(srFrameworkVersion);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SRFrameworkVersion)itr.next());
		}
	}

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

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

	public int countByG_A(String groupId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			q.setBoolean(queryPos++, active);

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
				"FROM com.liferay.portlet.softwarerepository.model.SRFrameworkVersion");

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

	private static Log _log = LogFactory.getLog(SRFrameworkVersionPersistence.class);
}