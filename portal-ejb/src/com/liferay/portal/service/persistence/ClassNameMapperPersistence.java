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

import com.liferay.portal.NoSuchClassNameMapperException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClassNameMapper;
import com.liferay.portal.model.impl.ClassNameMapperImpl;
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
 * <a href="ClassNameMapperPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ClassNameMapperPersistence extends BasePersistence {
	public ClassNameMapper create(long classNameMapperId) {
		ClassNameMapper classNameMapper = new ClassNameMapperImpl();
		classNameMapper.setNew(true);
		classNameMapper.setPrimaryKey(classNameMapperId);

		return classNameMapper;
	}

	public ClassNameMapper remove(long classNameMapperId)
		throws NoSuchClassNameMapperException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ClassNameMapper classNameMapper = (ClassNameMapper)session.get(ClassNameMapperImpl.class,
					new Long(classNameMapperId));

			if (classNameMapper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ClassNameMapper exists with the primary key " +
						classNameMapperId);
				}

				throw new NoSuchClassNameMapperException(
					"No ClassNameMapper exists with the primary key " +
					classNameMapperId);
			}

			return remove(classNameMapper);
		}
		catch (NoSuchClassNameMapperException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ClassNameMapper remove(ClassNameMapper classNameMapper)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(classNameMapper);
			session.flush();

			return classNameMapper;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.ClassNameMapper update(
		com.liferay.portal.model.ClassNameMapper classNameMapper)
		throws SystemException {
		return update(classNameMapper, false);
	}

	public com.liferay.portal.model.ClassNameMapper update(
		com.liferay.portal.model.ClassNameMapper classNameMapper,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(classNameMapper);
			}
			else {
				if (classNameMapper.isNew()) {
					session.save(classNameMapper);
				}
			}

			session.flush();
			classNameMapper.setNew(false);

			return classNameMapper;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ClassNameMapper findByPrimaryKey(long classNameMapperId)
		throws NoSuchClassNameMapperException, SystemException {
		ClassNameMapper classNameMapper = fetchByPrimaryKey(classNameMapperId);

		if (classNameMapper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ClassNameMapper exists with the primary key " +
					classNameMapperId);
			}

			throw new NoSuchClassNameMapperException(
				"No ClassNameMapper exists with the primary key " +
				classNameMapperId);
		}

		return classNameMapper;
	}

	public ClassNameMapper fetchByPrimaryKey(long classNameMapperId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ClassNameMapper)session.get(ClassNameMapperImpl.class,
				new Long(classNameMapperId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ClassNameMapper findByClassName(String className)
		throws NoSuchClassNameMapperException, SystemException {
		ClassNameMapper classNameMapper = fetchByClassName(className);

		if (classNameMapper == null) {
			StringMaker msg = new StringMaker();
			msg.append("No ClassNameMapper exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("className=");
			msg.append(className);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchClassNameMapperException(msg.toString());
		}

		return classNameMapper;
	}

	public ClassNameMapper fetchByClassName(String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.ClassNameMapper WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			ClassNameMapper classNameMapper = (ClassNameMapper)list.get(0);

			return classNameMapper;
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
			query.append("FROM com.liferay.portal.model.ClassNameMapper ");

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

	public void removeByClassName(String className)
		throws NoSuchClassNameMapperException, SystemException {
		ClassNameMapper classNameMapper = findByClassName(className);
		remove(classNameMapper);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((ClassNameMapper)itr.next());
		}
	}

	public int countByClassName(String className) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.ClassNameMapper WHERE ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (className != null) {
				q.setString(queryPos++, className);
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
			query.append("FROM com.liferay.portal.model.ClassNameMapper");

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

	private static Log _log = LogFactory.getLog(ClassNameMapperPersistence.class);
}