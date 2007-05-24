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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePersistenceImpl extends BasePersistence
	implements ResourcePersistence {
	public Resource create(long resourceId) {
		Resource resource = new ResourceImpl();
		resource.setNew(true);
		resource.setPrimaryKey(resourceId);

		return resource;
	}

	public Resource remove(long resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(ResourceImpl.class,
					new Long(resourceId));

			if (resource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Resource exists with the primary key " +
						resourceId);
				}

				throw new NoSuchResourceException(
					"No Resource exists with the primary key " + resourceId);
			}

			return remove(resource);
		}
		catch (NoSuchResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource remove(Resource resource) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(resource);
			session.flush();

			return resource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Resource update(
		com.liferay.portal.model.Resource resource) throws SystemException {
		return update(resource, false);
	}

	public com.liferay.portal.model.Resource update(
		com.liferay.portal.model.Resource resource, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(resource);
			}
			else {
				if (resource.isNew()) {
					session.save(resource);
				}
			}

			session.flush();
			resource.setNew(false);

			return resource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByPrimaryKey(long resourceId)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByPrimaryKey(resourceId);

		if (resource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Resource exists with the primary key " +
					resourceId);
			}

			throw new NoSuchResourceException(
				"No Resource exists with the primary key " + resourceId);
		}

		return resource;
	}

	public Resource fetchByPrimaryKey(long resourceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Resource)session.get(ResourceImpl.class,
				new Long(resourceId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCodeId(long codeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCodeId(long codeId, int begin, int end)
		throws SystemException {
		return findByCodeId(codeId, begin, end, null);
	}

	public List findByCodeId(long codeId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByCodeId_First(long codeId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List list = findByCodeId(codeId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("codeId=");
			msg.append(codeId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByCodeId_Last(long codeId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByCodeId(codeId);
		List list = findByCodeId(codeId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("codeId=");
			msg.append(codeId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByCodeId_PrevAndNext(long resourceId, long codeId,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByCodeId(codeId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new ResourceImpl[3];
			array[0] = (Resource)objArray[0];
			array[1] = (Resource)objArray[1];
			array[2] = (Resource)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_P(codeId, primKey);

		if (resource == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("codeId=");
			msg.append(codeId);
			msg.append(", ");
			msg.append("primKey=");
			msg.append(primKey);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceException(msg.toString());
		}

		return resource;
	}

	public Resource fetchByC_P(long codeId, String primKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey IS NULL");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

			if (primKey != null) {
				q.setString(queryPos++, primKey);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Resource resource = (Resource)list.get(0);

			return resource;
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
			query.append("FROM com.liferay.portal.model.Resource ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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

	public void removeByCodeId(long codeId) throws SystemException {
		Iterator itr = findByCodeId(codeId).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_P(codeId, primKey);
		remove(resource);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Resource)itr.next());
		}
	}

	public int countByCodeId(long codeId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

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

	public int countByC_P(long codeId, String primKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("codeId = ?");
			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey IS NULL");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, codeId);

			if (primKey != null) {
				q.setString(queryPos++, primKey);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource");

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

	private static Log _log = LogFactory.getLog(ResourcePersistenceImpl.class);
}