/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourceCodeException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.ResourceCodeImpl;
import com.liferay.portal.model.impl.ResourceCodeModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ResourceCodePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceCodePersistenceImpl extends BasePersistence
	implements ResourceCodePersistence {
	public ResourceCode create(long codeId) {
		ResourceCode resourceCode = new ResourceCodeImpl();

		resourceCode.setNew(true);
		resourceCode.setPrimaryKey(codeId);

		return resourceCode;
	}

	public ResourceCode remove(long codeId)
		throws NoSuchResourceCodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceCode resourceCode = (ResourceCode)session.get(ResourceCodeImpl.class,
					new Long(codeId));

			if (resourceCode == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ResourceCode exists with the primary key " +
						codeId);
				}

				throw new NoSuchResourceCodeException(
					"No ResourceCode exists with the primary key " + codeId);
			}

			return remove(resourceCode);
		}
		catch (NoSuchResourceCodeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceCode remove(ResourceCode resourceCode)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(resourceCode);
			}
		}

		resourceCode = removeImpl(resourceCode);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(resourceCode);
			}
		}

		return resourceCode;
	}

	protected ResourceCode removeImpl(ResourceCode resourceCode)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(resourceCode);

			session.flush();

			return resourceCode;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ResourceCode.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ResourceCode resourceCode, boolean merge)</code>.
	 */
	public ResourceCode update(ResourceCode resourceCode)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ResourceCode resourceCode) method. Use update(ResourceCode resourceCode, boolean merge) instead.");
		}

		return update(resourceCode, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        resourceCode the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when resourceCode is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ResourceCode update(ResourceCode resourceCode, boolean merge)
		throws SystemException {
		boolean isNew = resourceCode.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(resourceCode);
				}
				else {
					listener.onBeforeUpdate(resourceCode);
				}
			}
		}

		resourceCode = updateImpl(resourceCode, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(resourceCode);
				}
				else {
					listener.onAfterUpdate(resourceCode);
				}
			}
		}

		return resourceCode;
	}

	public ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(resourceCode);
			}
			else {
				if (resourceCode.isNew()) {
					session.save(resourceCode);
				}
			}

			session.flush();

			resourceCode.setNew(false);

			return resourceCode;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(ResourceCode.class.getName());
		}
	}

	public ResourceCode findByPrimaryKey(long codeId)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = fetchByPrimaryKey(codeId);

		if (resourceCode == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ResourceCode exists with the primary key " +
					codeId);
			}

			throw new NoSuchResourceCodeException(
				"No ResourceCode exists with the primary key " + codeId);
		}

		return resourceCode;
	}

	public ResourceCode fetchByPrimaryKey(long codeId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ResourceCode)session.get(ResourceCodeImpl.class,
				new Long(codeId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourceCode> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<ResourceCode> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ResourceCode>)result;
		}
	}

	public List<ResourceCode> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<ResourceCode> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<ResourceCode> list = (List<ResourceCode>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ResourceCode>)result;
		}
	}

	public ResourceCode findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		List<ResourceCode> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ResourceCode exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		int count = countByCompanyId(companyId);

		List<ResourceCode> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ResourceCode exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode[] findByCompanyId_PrevAndNext(long codeId,
		long companyId, OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByPrimaryKey(codeId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ResourceCode WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resourceCode);

			ResourceCode[] array = new ResourceCodeImpl[3];

			array[0] = (ResourceCode)objArray[0];
			array[1] = (ResourceCode)objArray[1];
			array[2] = (ResourceCode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourceCode> findByName(String name) throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "findByName";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				List<ResourceCode> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ResourceCode>)result;
		}
	}

	public List<ResourceCode> findByName(String name, int start, int end)
		throws SystemException {
		return findByName(name, start, end, null);
	}

	public List<ResourceCode> findByName(String name, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "findByName";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				List<ResourceCode> list = (List<ResourceCode>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ResourceCode>)result;
		}
	}

	public ResourceCode findByName_First(String name, OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		List<ResourceCode> list = findByName(name, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ResourceCode exists with the key {");

			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode findByName_Last(String name, OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		int count = countByName(name);

		List<ResourceCode> list = findByName(name, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No ResourceCode exists with the key {");

			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode[] findByName_PrevAndNext(long codeId, String name,
		OrderByComparator obc)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByPrimaryKey(codeId);

		int count = countByName(name);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.ResourceCode WHERE ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resourceCode);

			ResourceCode[] array = new ResourceCodeImpl[3];

			array[0] = (ResourceCode)objArray[0];
			array[1] = (ResourceCode)objArray[1];
			array[2] = (ResourceCode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceCode findByC_N_S(long companyId, String name, int scope)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = fetchByC_N_S(companyId, name, scope);

		if (resourceCode == null) {
			StringMaker msg = new StringMaker();

			msg.append("No ResourceCode exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(", ");
			msg.append("scope=" + scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceCodeException(msg.toString());
		}

		return resourceCode;
	}

	public ResourceCode fetchByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "fetchByC_N_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" AND ");

				query.append("scope = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				List<ResourceCode> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ResourceCode> list = (List<ResourceCode>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ResourceCode> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
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

	public List<ResourceCode> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourceCode> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ResourceCode> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ResourceCode> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.ResourceCode ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ResourceCode> list = (List<ResourceCode>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ResourceCode>)result;
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (ResourceCode resourceCode : findByCompanyId(companyId)) {
			remove(resourceCode);
		}
	}

	public void removeByName(String name) throws SystemException {
		for (ResourceCode resourceCode : findByName(name)) {
			remove(resourceCode);
		}
	}

	public void removeByC_N_S(long companyId, String name, int scope)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByC_N_S(companyId, name, scope);

		remove(resourceCode);
	}

	public void removeAll() throws SystemException {
		for (ResourceCode resourceCode : findAll()) {
			remove(resourceCode);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByName(String name) throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "countByName";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "countByC_N_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourceCode WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" AND ");

				query.append("scope = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ResourceCodeModelImpl.CACHE_ENABLED;
		String finderClassName = ResourceCode.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.ResourceCode");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceCode")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(ResourceCodePersistenceImpl.class);
	private ModelListener[] _listeners;
}