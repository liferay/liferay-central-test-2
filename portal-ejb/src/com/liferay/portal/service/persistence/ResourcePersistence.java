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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

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
 * <a href="ResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ResourcePersistence extends BasePersistence {
	public com.liferay.portal.model.Resource create(String resourceId) {
		ResourceHBM resourceHBM = new ResourceHBM();
		resourceHBM.setNew(true);
		resourceHBM.setPrimaryKey(resourceId);

		return ResourceHBMUtil.model(resourceHBM);
	}

	public com.liferay.portal.model.Resource remove(String resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceHBM resourceHBM = (ResourceHBM)session.get(ResourceHBM.class,
					resourceId);

			if (resourceHBM == null) {
				_log.warn("No Resource exists with the primary key " +
					resourceId.toString());
				throw new NoSuchResourceException(
					"No Resource exists with the primary key " +
					resourceId.toString());
			}

			session.delete(resourceHBM);
			session.flush();

			return ResourceHBMUtil.model(resourceHBM);
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
		Session session = null;

		try {
			if (resource.isNew() || resource.isModified()) {
				session = openSession();

				if (resource.isNew()) {
					ResourceHBM resourceHBM = new ResourceHBM();
					resourceHBM.setResourceId(resource.getResourceId());
					resourceHBM.setCompanyId(resource.getCompanyId());
					resourceHBM.setName(resource.getName());
					resourceHBM.setTypeId(resource.getTypeId());
					resourceHBM.setScope(resource.getScope());
					resourceHBM.setPrimKey(resource.getPrimKey());
					session.save(resourceHBM);
					session.flush();
				}
				else {
					ResourceHBM resourceHBM = (ResourceHBM)session.get(ResourceHBM.class,
							resource.getPrimaryKey());

					if (resourceHBM != null) {
						resourceHBM.setCompanyId(resource.getCompanyId());
						resourceHBM.setName(resource.getName());
						resourceHBM.setTypeId(resource.getTypeId());
						resourceHBM.setScope(resource.getScope());
						resourceHBM.setPrimKey(resource.getPrimKey());
						session.flush();
					}
					else {
						resourceHBM = new ResourceHBM();
						resourceHBM.setResourceId(resource.getResourceId());
						resourceHBM.setCompanyId(resource.getCompanyId());
						resourceHBM.setName(resource.getName());
						resourceHBM.setTypeId(resource.getTypeId());
						resourceHBM.setScope(resource.getScope());
						resourceHBM.setPrimKey(resource.getPrimKey());
						session.save(resourceHBM);
						session.flush();
					}
				}

				resource.setNew(false);
				resource.setModified(false);
			}

			return resource;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Resource findByPrimaryKey(String resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceHBM resourceHBM = (ResourceHBM)session.get(ResourceHBM.class,
					resourceId);

			if (resourceHBM == null) {
				_log.warn("No Resource exists with the primary key " +
					resourceId.toString());
				throw new NoSuchResourceException(
					"No Resource exists with the primary key " +
					resourceId.toString());
			}

			return ResourceHBMUtil.model(resourceHBM);
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
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

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
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

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
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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

	public com.liferay.portal.model.Resource findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource findByCompanyId_Last(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource[] findByCompanyId_PrevAndNext(
		String resourceId, String companyId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		com.liferay.portal.model.Resource resource = findByPrimaryKey(resourceId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

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
					resource, ResourceHBMUtil.getInstance());
			com.liferay.portal.model.Resource[] array = new com.liferay.portal.model.Resource[3];
			array[0] = (com.liferay.portal.model.Resource)objArray[0];
			array[1] = (com.liferay.portal.model.Resource)objArray[1];
			array[2] = (com.liferay.portal.model.Resource)objArray[2];

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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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

	public com.liferay.portal.model.Resource findByName_First(String name,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource findByName_Last(String name,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource[] findByName_PrevAndNext(
		String resourceId, String name, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		com.liferay.portal.model.Resource resource = findByPrimaryKey(resourceId);
		int count = countByName(name);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resource, ResourceHBMUtil.getInstance());
			com.liferay.portal.model.Resource[] array = new com.liferay.portal.model.Resource[3];
			array[0] = (com.liferay.portal.model.Resource)objArray[0];
			array[1] = (com.liferay.portal.model.Resource)objArray[1];
			array[2] = (com.liferay.portal.model.Resource)objArray[2];

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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
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

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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

	public com.liferay.portal.model.Resource findByC_N_T_S_First(
		String companyId, String name, String typeId, String scope,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource findByC_N_T_S_Last(
		String companyId, String name, String typeId, String scope,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource[] findByC_N_T_S_PrevAndNext(
		String resourceId, String companyId, String name, String typeId,
		String scope, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		com.liferay.portal.model.Resource resource = findByPrimaryKey(resourceId);
		int count = countByC_N_T_S(companyId, name, typeId, scope);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
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

			if (name != null) {
				q.setString(queryPos++, name);
			}

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resource, ResourceHBMUtil.getInstance());
			com.liferay.portal.model.Resource[] array = new com.liferay.portal.model.Resource[3];
			array[0] = (com.liferay.portal.model.Resource)objArray[0];
			array[1] = (com.liferay.portal.model.Resource)objArray[1];
			array[2] = (com.liferay.portal.model.Resource)objArray[2];

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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
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

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			if (primKey != null) {
				q.setString(queryPos++, primKey);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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

	public com.liferay.portal.model.Resource findByC_T_S_P_First(
		String companyId, String typeId, String scope, String primKey,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource findByC_T_S_P_Last(
		String companyId, String typeId, String scope, String primKey,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
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
			return (com.liferay.portal.model.Resource)list.get(0);
		}
	}

	public com.liferay.portal.model.Resource[] findByC_T_S_P_PrevAndNext(
		String resourceId, String companyId, String typeId, String scope,
		String primKey, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		com.liferay.portal.model.Resource resource = findByPrimaryKey(resourceId);
		int count = countByC_T_S_P(companyId, typeId, scope, primKey);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
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

			if (typeId != null) {
				q.setString(queryPos++, typeId);
			}

			if (scope != null) {
				q.setString(queryPos++, scope);
			}

			if (primKey != null) {
				q.setString(queryPos++, primKey);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resource, ResourceHBMUtil.getInstance());
			com.liferay.portal.model.Resource[] array = new com.liferay.portal.model.Resource[3];
			array[0] = (com.liferay.portal.model.Resource)objArray[0];
			array[1] = (com.liferay.portal.model.Resource)objArray[1];
			array[2] = (com.liferay.portal.model.Resource)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Resource findByC_N_T_S_P(String companyId,
		String name, String typeId, String scope, String primKey)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			if (!itr.hasNext()) {
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
				throw new NoSuchResourceException(msg);
			}

			ResourceHBM resourceHBM = (ResourceHBM)itr.next();

			return ResourceHBMUtil.model(resourceHBM);
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
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				list.add(ResourceHBMUtil.model(resourceHBM));
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

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
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				session.delete(resourceHBM);
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

	public void removeByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				session.delete(resourceHBM);
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

	public void removeByC_N_T_S(String companyId, String name, String typeId,
		String scope) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				session.delete(resourceHBM);
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

	public void removeByC_T_S_P(String companyId, String typeId, String scope,
		String primKey) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				session.delete(resourceHBM);
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

	public void removeByC_N_T_S_P(String companyId, String name, String typeId,
		String scope, String primKey)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

			while (itr.hasNext()) {
				ResourceHBM resourceHBM = (ResourceHBM)itr.next();
				session.delete(resourceHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
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
				throw new NoSuchResourceException(msg);
			}
			else {
				throw new SystemException(he);
			}
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
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

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

	public int countByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Resource_ IN CLASS com.liferay.portal.service.persistence.ResourceHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name is null");
			}
			else {
				query.append("name = ?");
			}

			query.append(" AND ");

			if (typeId == null) {
				query.append("typeId is null");
			}
			else {
				query.append("typeId = ?");
			}

			query.append(" AND ");

			if (scope == null) {
				query.append("scope is null");
			}
			else {
				query.append("scope = ?");
			}

			query.append(" AND ");

			if (primKey == null) {
				query.append("primKey is null");
			}
			else {
				query.append("primKey = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	private static Log _log = LogFactory.getLog(ResourcePersistence.class);
}