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

import com.liferay.portal.NoSuchOrgGroupPermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.model.impl.OrgGroupPermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="OrgGroupPermissionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrgGroupPermissionPersistenceImpl extends BasePersistenceImpl
	implements OrgGroupPermissionPersistence {
	public OrgGroupPermission create(OrgGroupPermissionPK orgGroupPermissionPK) {
		OrgGroupPermission orgGroupPermission = new OrgGroupPermissionImpl();

		orgGroupPermission.setNew(true);
		orgGroupPermission.setPrimaryKey(orgGroupPermissionPK);

		return orgGroupPermission;
	}

	public OrgGroupPermission remove(OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
					orgGroupPermissionPK);

			if (orgGroupPermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No OrgGroupPermission exists with the primary key " +
						orgGroupPermissionPK);
				}

				throw new NoSuchOrgGroupPermissionException(
					"No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK);
			}

			return remove(orgGroupPermission);
		}
		catch (NoSuchOrgGroupPermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgGroupPermission remove(OrgGroupPermission orgGroupPermission)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(orgGroupPermission);
			}
		}

		orgGroupPermission = removeImpl(orgGroupPermission);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(orgGroupPermission);
			}
		}

		return orgGroupPermission;
	}

	protected OrgGroupPermission removeImpl(
		OrgGroupPermission orgGroupPermission) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(orgGroupPermission);

			session.flush();

			return orgGroupPermission;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(OrgGroupPermission.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(OrgGroupPermission orgGroupPermission, boolean merge)</code>.
	 */
	public OrgGroupPermission update(OrgGroupPermission orgGroupPermission)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(OrgGroupPermission orgGroupPermission) method. Use update(OrgGroupPermission orgGroupPermission, boolean merge) instead.");
		}

		return update(orgGroupPermission, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        orgGroupPermission the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when orgGroupPermission is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public OrgGroupPermission update(OrgGroupPermission orgGroupPermission,
		boolean merge) throws SystemException {
		boolean isNew = orgGroupPermission.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(orgGroupPermission);
				}
				else {
					listener.onBeforeUpdate(orgGroupPermission);
				}
			}
		}

		orgGroupPermission = updateImpl(orgGroupPermission, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(orgGroupPermission);
				}
				else {
					listener.onAfterUpdate(orgGroupPermission);
				}
			}
		}

		return orgGroupPermission;
	}

	public OrgGroupPermission updateImpl(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(orgGroupPermission);
			}
			else {
				if (orgGroupPermission.isNew()) {
					session.save(orgGroupPermission);
				}
			}

			session.flush();

			orgGroupPermission.setNew(false);

			return orgGroupPermission;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(OrgGroupPermission.class.getName());
		}
	}

	public OrgGroupPermission findByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = fetchByPrimaryKey(orgGroupPermissionPK);

		if (orgGroupPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No OrgGroupPermission exists with the primary key " +
					orgGroupPermissionPK);
			}

			throw new NoSuchOrgGroupPermissionException(
				"No OrgGroupPermission exists with the primary key " +
				orgGroupPermissionPK);
		}

		return orgGroupPermission;
	}

	public OrgGroupPermission fetchByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
				orgGroupPermissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupPermission> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<OrgGroupPermission> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<OrgGroupPermission>)result;
		}
	}

	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<OrgGroupPermission> list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<OrgGroupPermission>)result;
		}
	}

	public OrgGroupPermission findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupPermission exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByGroupId(groupId);

		List<OrgGroupPermission> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupPermission exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission[] findByGroupId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long groupId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

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
					orgGroupPermission);

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = (OrgGroupPermission)objArray[0];
			array[1] = (OrgGroupPermission)objArray[1];
			array[2] = (OrgGroupPermission)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "findByPermissionId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(permissionId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

				query.append("permissionId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				List<OrgGroupPermission> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<OrgGroupPermission>)result;
		}
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end) throws SystemException {
		return findByPermissionId(permissionId, start, end, null);
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "findByPermissionId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(permissionId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

				query.append("permissionId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				List<OrgGroupPermission> list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<OrgGroupPermission>)result;
		}
	}

	public OrgGroupPermission findByPermissionId_First(long permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByPermissionId(permissionId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupPermission exists with the key {");

			msg.append("permissionId=" + permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission findByPermissionId_Last(long permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByPermissionId(permissionId);

		List<OrgGroupPermission> list = findByPermissionId(permissionId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgGroupPermission exists with the key {");

			msg.append("permissionId=" + permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission[] findByPermissionId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long permissionId,
		OrderByComparator obc)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		int count = countByPermissionId(permissionId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

			query.append("permissionId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(permissionId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					orgGroupPermission);

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = (OrgGroupPermission)objArray[0];
			array[1] = (OrgGroupPermission)objArray[1];
			array[2] = (OrgGroupPermission)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<OrgGroupPermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<OrgGroupPermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<OrgGroupPermission> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<OrgGroupPermission> list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<OrgGroupPermission>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByGroupId(groupId)) {
			remove(orgGroupPermission);
		}
	}

	public void removeByPermissionId(long permissionId)
		throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByPermissionId(
				permissionId)) {
			remove(orgGroupPermission);
		}
	}

	public void removeAll() throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findAll()) {
			remove(orgGroupPermission);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

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

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByPermissionId(long permissionId) throws SystemException {
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "countByPermissionId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(permissionId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.OrgGroupPermission WHERE ");

				query.append("permissionId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
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
		boolean finderClassNameCacheEnabled = OrgGroupPermissionModelImpl.CACHE_ENABLED;
		String finderClassName = OrgGroupPermission.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.OrgGroupPermission");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
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
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.OrgGroupPermission")));

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

	private static Log _log = LogFactory.getLog(OrgGroupPermissionPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}