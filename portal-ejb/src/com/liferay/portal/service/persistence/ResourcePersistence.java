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

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePersistence extends BasePersistence {
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCode(long code) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, code);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCode(long code, int begin, int end)
		throws SystemException {
		return findByCode(code, begin, end, null);
	}

	public List findByCode(long code, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, code);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByCode_First(long code, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List list = findByCode(code, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("code=");
			msg.append(code);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByCode_Last(long code, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByCode(code);
		List list = findByCode(code, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("code=");
			msg.append(code);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByCode_PrevAndNext(long resourceId, long code,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByCode(code);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, code);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new ResourceImpl[3];
			array[0] = (Resource)objArray[0];
			array[1] = (Resource)objArray[1];
			array[2] = (Resource)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByC_P(long code, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_P(code, primKey);

		if (resource == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Resource exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("code=");
			msg.append(code);
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

	public Resource fetchByC_P(long code, String primKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
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
			q.setLong(queryPos++, code);

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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCode(long code) throws SystemException {
		Iterator itr = findByCode(code).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByC_P(long code, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_P(code, primKey);
		remove(resource);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Resource)itr.next());
		}
	}

	public int countByCode(long code) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, code);

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

	public int countByC_P(long code, String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");
			query.append("code = ?");
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
			q.setLong(queryPos++, code);

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ResourcePersistence.class);
}