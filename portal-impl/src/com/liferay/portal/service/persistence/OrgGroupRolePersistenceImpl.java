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

import com.liferay.portal.NoSuchOrgGroupRoleException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgGroupRole;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.model.impl.OrgGroupRoleModelImpl;
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
 * <a href="OrgGroupRolePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrgGroupRolePersistenceImpl extends BasePersistence
	implements OrgGroupRolePersistence {
	public OrgGroupRole create(OrgGroupRolePK orgGroupRolePK) {
		OrgGroupRole orgGroupRole = new OrgGroupRoleImpl();

		orgGroupRole.setNew(true);
		orgGroupRole.setPrimaryKey(orgGroupRolePK);

		return orgGroupRole;
	}

	public OrgGroupRole remove(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupRole orgGroupRole = (OrgGroupRole)session.get(OrgGroupRoleImpl.class,
					orgGroupRolePK);

			if (orgGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No OrgGroupRole exists with the primary key " +
						orgGroupRolePK);
				}

				throw new NoSuchOrgGroupRoleException(
					"No OrgGroupRole exists with the primary key " +
					orgGroupRolePK);
			}

			return remove(orgGroupRole);
		}
		catch (NoSuchOrgGroupRoleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgGroupRole remove(OrgGroupRole orgGroupRole)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(orgGroupRole);
			}
		}

		orgGroupRole = removeImpl(orgGroupRole);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(orgGroupRole);
			}
		}

		return orgGroupRole;
	}

	protected OrgGroupRole removeImpl(OrgGroupRole orgGroupRole)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(orgGroupRole);

			session.flush();

			return orgGroupRole;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(OrgGroupRole.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(OrgGroupRole orgGroupRole, boolean merge)</code>.
	 */
	public OrgGroupRole update(OrgGroupRole orgGroupRole)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(OrgGroupRole orgGroupRole) method. Use update(OrgGroupRole orgGroupRole, boolean merge) instead.");
		}

		return update(orgGroupRole, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        orgGroupRole the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when orgGroupRole is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public OrgGroupRole update(OrgGroupRole orgGroupRole, boolean merge)
		throws SystemException {
		boolean isNew = orgGroupRole.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(orgGroupRole);
				}
				else {
					listener.onBeforeUpdate(orgGroupRole);
				}
			}
		}

		orgGroupRole = updateImpl(orgGroupRole, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(orgGroupRole);
				}
				else {
					listener.onAfterUpdate(orgGroupRole);
				}
			}
		}

		return orgGroupRole;
	}

	public OrgGroupRole updateImpl(
		com.liferay.portal.model.OrgGroupRole orgGroupRole, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(orgGroupRole);
			}
			else {
				if (orgGroupRole.isNew()) {
					session.save(orgGroupRole);
				}
			}

			session.flush();

			orgGroupRole.setNew(false);

			return orgGroupRole;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(OrgGroupRole.class.getName());
		}
	}

	public OrgGroupRole findByPrimaryKey(OrgGroupRolePK orgGroupRolePK)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = fetchByPrimaryKey(orgGroupRolePK);

		if (orgGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No OrgGroupRole exists with the primary key " +
					orgGroupRolePK);
			}

			throw new NoSuchOrgGroupRoleException(
				"No OrgGroupRole exists with the primary key " +
				orgGroupRolePK);
		}

		return orgGroupRole;
	}

	public OrgGroupRole fetchByPrimaryKey(OrgGroupRolePK orgGroupRolePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (OrgGroupRole)session.get(OrgGroupRoleImpl.class,
				orgGroupRolePK);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupRole> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<OrgGroupRole> list = q.list();

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
			return (List<OrgGroupRole>)result;
		}
	}

	public List<OrgGroupRole> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<OrgGroupRole> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<OrgGroupRole> list = (List<OrgGroupRole>)QueryUtil.list(q,
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
			return (List<OrgGroupRole>)result;
		}
	}

	public OrgGroupRole findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		List<OrgGroupRole> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupRole findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		int count = countByGroupId(groupId);

		List<OrgGroupRole> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupRole exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupRole[] findByGroupId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, long groupId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupRole);

			OrgGroupRole[] array = new OrgGroupRoleImpl[3];

			array[0] = (OrgGroupRole)objArray[0];
			array[1] = (OrgGroupRole)objArray[1];
			array[2] = (OrgGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupRole> findByRoleId(long roleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "findByRoleId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				List<OrgGroupRole> list = q.list();

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
			return (List<OrgGroupRole>)result;
		}
	}

	public List<OrgGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	public List<OrgGroupRole> findByRoleId(long roleId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "findByRoleId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(roleId),
				
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				List<OrgGroupRole> list = (List<OrgGroupRole>)QueryUtil.list(q,
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
			return (List<OrgGroupRole>)result;
		}
	}

	public OrgGroupRole findByRoleId_First(long roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		List<OrgGroupRole> list = findByRoleId(roleId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupRole findByRoleId_Last(long roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		int count = countByRoleId(roleId);

		List<OrgGroupRole> list = findByRoleId(roleId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupRole exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupRole[] findByRoleId_PrevAndNext(
		OrgGroupRolePK orgGroupRolePK, long roleId, OrderByComparator obc)
		throws NoSuchOrgGroupRoleException, SystemException {
		OrgGroupRole orgGroupRole = findByPrimaryKey(orgGroupRolePK);

		int count = countByRoleId(roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.OrgGroupRole WHERE ");

			query.append("roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupRole);

			OrgGroupRole[] array = new OrgGroupRoleImpl[3];

			array[0] = (OrgGroupRole)objArray[0];
			array[1] = (OrgGroupRole)objArray[1];
			array[2] = (OrgGroupRole)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupRole> findWithDynamicQuery(
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

	public List<OrgGroupRole> findWithDynamicQuery(
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

	public List<OrgGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<OrgGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<OrgGroupRole> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.OrgGroupRole ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<OrgGroupRole> list = (List<OrgGroupRole>)QueryUtil.list(q,
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
			return (List<OrgGroupRole>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (OrgGroupRole orgGroupRole : findByGroupId(groupId)) {
			remove(orgGroupRole);
		}
	}

	public void removeByRoleId(long roleId) throws SystemException {
		for (OrgGroupRole orgGroupRole : findByRoleId(roleId)) {
			remove(orgGroupRole);
		}
	}

	public void removeAll() throws SystemException {
		for (OrgGroupRole orgGroupRole : findAll()) {
			remove(orgGroupRole);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	public int countByRoleId(long roleId) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
		String finderMethodName = "countByRoleId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.OrgGroupRole WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

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
		boolean finderClassNameCacheEnabled = OrgGroupRoleModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupRole.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.OrgGroupRole");

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

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.OrgGroupRole")));

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

	private static Log _log = LogFactory.getLog(OrgGroupRolePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}