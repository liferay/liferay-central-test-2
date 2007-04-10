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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCFrameworkVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCFrameworkVersionPersistence extends BasePersistence {
	public SCFrameworkVersion create(long frameworkVersionId) {
		SCFrameworkVersion scFrameworkVersion = new SCFrameworkVersionImpl();
		scFrameworkVersion.setNew(true);
		scFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return scFrameworkVersion;
	}

	public SCFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
					new Long(frameworkVersionId));

			if (scFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCFrameworkVersion exists with the primary key " +
						frameworkVersionId);
				}

				throw new NoSuchFrameworkVersionException(
					"No SCFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			return remove(scFrameworkVersion);
		}
		catch (NoSuchFrameworkVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion remove(SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(scFrameworkVersion);
			session.flush();

			return scFrameworkVersion;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion update(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		return update(scFrameworkVersion, false);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion update(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(scFrameworkVersion);
			}
			else {
				if (scFrameworkVersion.isNew()) {
					session.save(scFrameworkVersion);
				}
			}

			session.flush();
			scFrameworkVersion.setNew(false);

			return scFrameworkVersion;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(frameworkVersionId);

		if (scFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCFrameworkVersion exists with the primary key " +
					frameworkVersionId);
			}

			throw new NoSuchFrameworkVersionException(
				"No SCFrameworkVersion exists with the primary key " +
				frameworkVersionId);
		}

		return scFrameworkVersion;
	}

	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
				new Long(frameworkVersionId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scFrameworkVersion);
			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];
			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion findByCompanyId_First(String companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion findByCompanyId_Last(String companyId,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, String companyId, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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
					scFrameworkVersion);
			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];
			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A(long groupId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("priority ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_A(long groupId, boolean active, int begin, int end)
		throws SystemException {
		return findByG_A(groupId, active, begin, end, null);
	}

	public List findByG_A(long groupId, boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		List list = findByG_A(groupId, active, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByG_A(groupId, active);
		List list = findByG_A(groupId, active, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCFrameworkVersion exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("groupId=");
			msg.append(groupId);
			msg.append(", ");
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return (SCFrameworkVersion)list.get(0);
		}
	}

	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active, OrderByComparator obc)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);
		int count = countByG_A(groupId, active);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("priority ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scFrameworkVersion);
			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];
			array[0] = (SCFrameworkVersion)objArray[0];
			array[1] = (SCFrameworkVersion)objArray[1];
			array[2] = (SCFrameworkVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		Iterator itr = findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)itr.next();
			remove(scFrameworkVersion);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)itr.next();
			remove(scFrameworkVersion);
		}
	}

	public void removeByG_A(long groupId, boolean active)
		throws SystemException {
		Iterator itr = findByG_A(groupId, active).iterator();

		while (itr.hasNext()) {
			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)itr.next();
			remove(scFrameworkVersion);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SCFrameworkVersion)itr.next());
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_A(long groupId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion WHERE ");
			query.append("groupId = ?");
			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, groupId);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
				"FROM com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(SCFrameworkVersionPersistence.class);
}