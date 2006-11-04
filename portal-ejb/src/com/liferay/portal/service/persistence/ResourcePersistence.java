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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
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
 * @author  Brian Wing Shun Chan
 *
 */
public class ResourcePersistence extends BasePersistence {
	public Resource create(String resourceId) {
		Resource resource = new Resource();
		resource.setNew(true);
		resource.setPrimaryKey(resourceId);

		return resource;
	}

	public Resource remove(String resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(Resource.class, resourceId);

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

	public Resource findByPrimaryKey(String resourceId)
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

	public Resource fetchByPrimaryKey(String resourceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Resource)session.get(Resource.class, resourceId);
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
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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

	public Resource findByCompanyId_First(String companyId,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByCompanyId_PrevAndNext(String resourceId,
		String companyId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new Resource[3];
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

	public List findByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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

			if (name != null) {
				q.setString(queryPos++, name);
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

	public List findByName(String name, int begin, int end)
		throws SystemException {
		return findByName(name, begin, end, null);
	}

	public List findByName(String name, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
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

	public Resource findByName_First(String name, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List list = findByName(name, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByName_Last(String name, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByName(name);
		List list = findByName(name, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByName_PrevAndNext(String resourceId, String name,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByName(name);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new Resource[3];
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

	public List findByC_N_T_S(String companyId, String name, String typeId,
		String scope) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
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

	public List findByC_N_T_S(String companyId, String name, String typeId,
		String scope, int begin, int end) throws SystemException {
		return findByC_N_T_S(companyId, name, typeId, scope, begin, end, null);
	}

	public List findByC_N_T_S(String companyId, String name, String typeId,
		String scope, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
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

	public Resource findByC_N_T_S_First(String companyId, String name,
		String typeId, String scope, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List list = findByC_N_T_S(companyId, name, typeId, scope, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += ", ";
			msg += "typeId=";
			msg += typeId;
			msg += ", ";
			msg += "scope=";
			msg += scope;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByC_N_T_S_Last(String companyId, String name,
		String typeId, String scope, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByC_N_T_S(companyId, name, typeId, scope);
		List list = findByC_N_T_S(companyId, name, typeId, scope, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += ", ";
			msg += "typeId=";
			msg += typeId;
			msg += ", ";
			msg += "scope=";
			msg += scope;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByC_N_T_S_PrevAndNext(String resourceId,
		String companyId, String name, String typeId, String scope,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByC_N_T_S(companyId, name, typeId, scope);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new Resource[3];
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

	public List findByC_T_S_P(String companyId, String typeId, String scope,
		String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			if (primKey != null) {
				q.setString(queryPos++, primKey);
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

	public List findByC_T_S_P(String companyId, String typeId, String scope,
		String primKey, int begin, int end) throws SystemException {
		return findByC_T_S_P(companyId, typeId, scope, primKey, begin, end, null);
	}

	public List findByC_T_S_P(String companyId, String typeId, String scope,
		String primKey, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey IS NULL");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			if (primKey != null) {
				q.setString(queryPos++, primKey);
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

	public Resource findByC_T_S_P_First(String companyId, String typeId,
		String scope, String primKey, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List list = findByC_T_S_P(companyId, typeId, scope, primKey, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "typeId=";
			msg += typeId;
			msg += ", ";
			msg += "scope=";
			msg += scope;
			msg += ", ";
			msg += "primKey=";
			msg += primKey;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource findByC_T_S_P_Last(String companyId, String typeId,
		String scope, String primKey, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByC_T_S_P(companyId, typeId, scope, primKey);
		List list = findByC_T_S_P(companyId, typeId, scope, primKey, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "typeId=";
			msg += typeId;
			msg += ", ";
			msg += "scope=";
			msg += scope;
			msg += ", ";
			msg += "primKey=";
			msg += primKey;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchResourceException(msg);
		}
		else {
			return (Resource)list.get(0);
		}
	}

	public Resource[] findByC_T_S_P_PrevAndNext(String resourceId,
		String companyId, String typeId, String scope, String primKey,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);
		int count = countByC_T_S_P(companyId, typeId, scope, primKey);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey IS NULL");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			if (primKey != null) {
				q.setString(queryPos++, primKey);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);
			Resource[] array = new Resource[3];
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

	public Resource findByC_N_T_S_P(String companyId, String name,
		String typeId, String scope, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_N_T_S_P(companyId, name, typeId, scope,
				primKey);

		if (resource == null) {
			String msg = "No Resource exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += ", ";
			msg += "typeId=";
			msg += typeId;
			msg += ", ";
			msg += "scope=";
			msg += scope;
			msg += ", ";
			msg += "primKey=";
			msg += primKey;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchResourceException(msg);
		}

		return resource;
	}

	public Resource fetchByC_N_T_S_P(String companyId, String name,
		String typeId, String scope, String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

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
			query.append("FROM com.liferay.portal.model.Resource ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByName(String name) throws SystemException {
		Iterator itr = findByName(name).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByC_N_T_S(String companyId, String name, String typeId,
		String scope) throws SystemException {
		Iterator itr = findByC_N_T_S(companyId, name, typeId, scope).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByC_T_S_P(String companyId, String typeId, String scope,
		String primKey) throws SystemException {
		Iterator itr = findByC_T_S_P(companyId, typeId, scope, primKey)
						   .iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();
			remove(resource);
		}
	}

	public void removeByC_N_T_S_P(String companyId, String name, String typeId,
		String scope, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_N_T_S_P(companyId, name, typeId, scope,
				primKey);
		remove(resource);
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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

	public int countByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

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

	public int countByC_N_T_S(String companyId, String name, String typeId,
		String scope) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
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

	public int countByC_T_S_P(String companyId, String typeId, String scope,
		String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

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

	public int countByC_N_T_S_P(String companyId, String name, String typeId,
		String scope, String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId IS NULL");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope IS NULL");
			}
			else {
				query.append("scope = ?");
			}

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

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ResourcePersistence.class);
}