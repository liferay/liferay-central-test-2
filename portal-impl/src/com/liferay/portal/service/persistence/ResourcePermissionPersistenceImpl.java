/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePermissionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionPersistenceImpl extends BasePersistenceImpl
	implements ResourcePermissionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourcePermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_RESOURCEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByResourceId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_RESOURCEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByResourceId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByResourceId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROLEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_R_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByR_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_R_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ResourcePermission resourcePermission) {
		EntityCacheUtil.putResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
			new Object[] {
				new Long(resourcePermission.getResourceId()),
				new Long(resourcePermission.getRoleId())
			}, resourcePermission);
	}

	public void cacheResult(List<ResourcePermission> resourcePermissions) {
		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (EntityCacheUtil.getResult(
						ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourcePermissionImpl.class,
						resourcePermission.getPrimaryKey(), this) == null) {
				cacheResult(resourcePermission);
			}
		}
	}

	public ResourcePermission create(long resourcePermissionId) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setNew(true);
		resourcePermission.setPrimaryKey(resourcePermissionId);

		return resourcePermission;
	}

	public ResourcePermission remove(long resourcePermissionId)
		throws NoSuchResourcePermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourcePermission resourcePermission = (ResourcePermission)session.get(ResourcePermissionImpl.class,
					new Long(resourcePermissionId));

			if (resourcePermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ResourcePermission exists with the primary key " +
						resourcePermissionId);
				}

				throw new NoSuchResourcePermissionException(
					"No ResourcePermission exists with the primary key " +
					resourcePermissionId);
			}

			return remove(resourcePermission);
		}
		catch (NoSuchResourcePermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourcePermission remove(ResourcePermission resourcePermission)
		throws SystemException {
		for (ModelListener<ResourcePermission> listener : listeners) {
			listener.onBeforeRemove(resourcePermission);
		}

		resourcePermission = removeImpl(resourcePermission);

		for (ModelListener<ResourcePermission> listener : listeners) {
			listener.onAfterRemove(resourcePermission);
		}

		return resourcePermission;
	}

	protected ResourcePermission removeImpl(
		ResourcePermission resourcePermission) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (resourcePermission.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ResourcePermissionImpl.class,
						resourcePermission.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(resourcePermission);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourcePermissionModelImpl resourcePermissionModelImpl = (ResourcePermissionModelImpl)resourcePermission;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_R,
			new Object[] {
				new Long(resourcePermissionModelImpl.getOriginalResourceId()),
				new Long(resourcePermissionModelImpl.getOriginalRoleId())
			});

		EntityCacheUtil.removeResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey());

		return resourcePermission;
	}

	/**
	 * @deprecated Use <code>update(ResourcePermission resourcePermission, boolean merge)</code>.
	 */
	public ResourcePermission update(ResourcePermission resourcePermission)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ResourcePermission resourcePermission) method. Use update(ResourcePermission resourcePermission, boolean merge) instead.");
		}

		return update(resourcePermission, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        resourcePermission the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when resourcePermission is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ResourcePermission update(ResourcePermission resourcePermission,
		boolean merge) throws SystemException {
		boolean isNew = resourcePermission.isNew();

		for (ModelListener<ResourcePermission> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(resourcePermission);
			}
			else {
				listener.onBeforeUpdate(resourcePermission);
			}
		}

		resourcePermission = updateImpl(resourcePermission, merge);

		for (ModelListener<ResourcePermission> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(resourcePermission);
			}
			else {
				listener.onAfterUpdate(resourcePermission);
			}
		}

		return resourcePermission;
	}

	public ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws SystemException {
		boolean isNew = resourcePermission.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourcePermission, merge);

			resourcePermission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission);

		ResourcePermissionModelImpl resourcePermissionModelImpl = (ResourcePermissionModelImpl)resourcePermission;

		if (!isNew &&
				((resourcePermission.getResourceId() != resourcePermissionModelImpl.getOriginalResourceId()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_R,
				new Object[] {
					new Long(resourcePermissionModelImpl.getOriginalResourceId()),
					new Long(resourcePermissionModelImpl.getOriginalRoleId())
				});
		}

		if (isNew ||
				((resourcePermission.getResourceId() != resourcePermissionModelImpl.getOriginalResourceId()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
				new Object[] {
					new Long(resourcePermission.getResourceId()),
					new Long(resourcePermission.getRoleId())
				}, resourcePermission);
		}

		return resourcePermission;
	}

	public ResourcePermission findByPrimaryKey(long resourcePermissionId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = fetchByPrimaryKey(resourcePermissionId);

		if (resourcePermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ResourcePermission exists with the primary key " +
					resourcePermissionId);
			}

			throw new NoSuchResourcePermissionException(
				"No ResourcePermission exists with the primary key " +
				resourcePermissionId);
		}

		return resourcePermission;
	}

	public ResourcePermission fetchByPrimaryKey(long resourcePermissionId)
		throws SystemException {
		ResourcePermission resourcePermission = (ResourcePermission)EntityCacheUtil.getResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourcePermissionImpl.class, resourcePermissionId, this);

		if (resourcePermission == null) {
			Session session = null;

			try {
				session = openSession();

				resourcePermission = (ResourcePermission)session.get(ResourcePermissionImpl.class,
						new Long(resourcePermissionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resourcePermission != null) {
					cacheResult(resourcePermission);
				}

				closeSession(session);
			}
		}

		return resourcePermission;
	}

	public List<ResourcePermission> findByResourceId(long resourceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(resourceId) };

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RESOURCEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("resourceId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourcePermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RESOURCEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourcePermission> findByResourceId(long resourceId,
		int start, int end) throws SystemException {
		return findByResourceId(resourceId, start, end, null);
	}

	public List<ResourcePermission> findByResourceId(long resourceId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourceId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_RESOURCEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("resourceId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourcePermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_RESOURCEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourcePermission findByResourceId_First(long resourceId,
		OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByResourceId(resourceId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ResourcePermission exists with the key {");

			msg.append("resourceId=" + resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission findByResourceId_Last(long resourceId,
		OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByResourceId(resourceId);

		List<ResourcePermission> list = findByResourceId(resourceId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ResourcePermission exists with the key {");

			msg.append("resourceId=" + resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission[] findByResourceId_PrevAndNext(
		long resourcePermissionId, long resourceId, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		int count = countByResourceId(resourceId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.ResourcePermission WHERE ");

			query.append("resourceId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourceId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resourcePermission);

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = (ResourcePermission)objArray[0];
			array[1] = (ResourcePermission)objArray[1];
			array[2] = (ResourcePermission)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourcePermission> findByRoleId(long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourcePermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourcePermission> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	public List<ResourcePermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(roleId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourcePermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourcePermission findByRoleId_First(long roleId,
		OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByRoleId(roleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ResourcePermission exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission findByRoleId_Last(long roleId,
		OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByRoleId(roleId);

		List<ResourcePermission> list = findByRoleId(roleId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ResourcePermission exists with the key {");

			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		int count = countByRoleId(roleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.ResourcePermission WHERE ");

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
					resourcePermission);

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = (ResourcePermission)objArray[0];
			array[1] = (ResourcePermission)objArray[1];
			array[2] = (ResourcePermission)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourcePermission findByR_R(long resourceId, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = fetchByR_R(resourceId, roleId);

		if (resourcePermission == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ResourcePermission exists with the key {");

			msg.append("resourceId=" + resourceId);

			msg.append(", ");
			msg.append("roleId=" + roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourcePermissionException(msg.toString());
		}

		return resourcePermission;
	}

	public ResourcePermission fetchByR_R(long resourceId, long roleId)
		throws SystemException {
		return fetchByR_R(resourceId, roleId, true);
	}

	public ResourcePermission fetchByR_R(long resourceId, long roleId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourceId), new Long(roleId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_R_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("resourceId = ?");

				query.append(" AND ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				qPos.add(roleId);

				List<ResourcePermission> list = q.list();

				result = list;

				ResourcePermission resourcePermission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
						finderArgs, list);
				}
				else {
					resourcePermission = list.get(0);

					cacheResult(resourcePermission);
				}

				return resourcePermission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
						finderArgs, new ArrayList<ResourcePermission>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (ResourcePermission)result;
			}
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

	public List<ResourcePermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ResourcePermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ResourcePermission> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ResourcePermission ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<ResourcePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourcePermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourcePermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByResourceId(long resourceId) throws SystemException {
		for (ResourcePermission resourcePermission : findByResourceId(
				resourceId)) {
			remove(resourcePermission);
		}
	}

	public void removeByRoleId(long roleId) throws SystemException {
		for (ResourcePermission resourcePermission : findByRoleId(roleId)) {
			remove(resourcePermission);
		}
	}

	public void removeByR_R(long resourceId, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByR_R(resourceId, roleId);

		remove(resourcePermission);
	}

	public void removeAll() throws SystemException {
		for (ResourcePermission resourcePermission : findAll()) {
			remove(resourcePermission);
		}
	}

	public int countByResourceId(long resourceId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(resourceId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RESOURCEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("resourceId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RESOURCEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByR_R(long resourceId, long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourceId), new Long(roleId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ResourcePermission WHERE ");

				query.append("resourceId = ?");

				query.append(" AND ");

				query.append("roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.ResourcePermission");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourcePermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourcePermission>> listenersList = new ArrayList<ModelListener<ResourcePermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourcePermission>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(ResourcePermissionPersistenceImpl.class);
}