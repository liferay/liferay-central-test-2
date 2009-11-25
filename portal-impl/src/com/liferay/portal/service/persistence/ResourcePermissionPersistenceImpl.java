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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePermissionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionPersistence
 * @see       ResourcePermissionUtil
 * @generated
 */
public class ResourcePermissionPersistenceImpl extends BasePersistenceImpl<ResourcePermission>
	implements ResourcePermissionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourcePermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
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
	public static final FinderPath FINDER_PATH_FIND_BY_C_N_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_N_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_N_S_P = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_N_S_P = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S_P = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N_S_P_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S_P_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});
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

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
			new Object[] {
				new Long(resourcePermission.getCompanyId()),
				
			resourcePermission.getName(),
				new Integer(resourcePermission.getScope()),
				
			resourcePermission.getPrimKey(),
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

	public void clearCache() {
		CacheRegistry.clear(ResourcePermissionImpl.class.getName());
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ResourcePermission create(long resourcePermissionId) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setNew(true);
		resourcePermission.setPrimaryKey(resourcePermissionId);

		return resourcePermission;
	}

	public ResourcePermission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
		resourcePermission = toUnwrappedModel(resourcePermission);

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

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
			new Object[] {
				new Long(resourcePermissionModelImpl.getOriginalCompanyId()),
				
			resourcePermissionModelImpl.getOriginalName(),
				new Integer(resourcePermissionModelImpl.getOriginalScope()),
				
			resourcePermissionModelImpl.getOriginalPrimKey(),
				new Long(resourcePermissionModelImpl.getOriginalRoleId())
			});

		EntityCacheUtil.removeResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey());

		return resourcePermission;
	}

	public ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws SystemException {
		resourcePermission = toUnwrappedModel(resourcePermission);

		boolean isNew = resourcePermission.isNew();

		ResourcePermissionModelImpl resourcePermissionModelImpl = (ResourcePermissionModelImpl)resourcePermission;

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

		if (!isNew &&
				((resourcePermission.getCompanyId() != resourcePermissionModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourcePermission.getName(),
					resourcePermissionModelImpl.getOriginalName()) ||
				(resourcePermission.getScope() != resourcePermissionModelImpl.getOriginalScope()) ||
				!Validator.equals(resourcePermission.getPrimKey(),
					resourcePermissionModelImpl.getOriginalPrimKey()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
				new Object[] {
					new Long(resourcePermissionModelImpl.getOriginalCompanyId()),
					
				resourcePermissionModelImpl.getOriginalName(),
					new Integer(resourcePermissionModelImpl.getOriginalScope()),
					
				resourcePermissionModelImpl.getOriginalPrimKey(),
					new Long(resourcePermissionModelImpl.getOriginalRoleId())
				});
		}

		if (isNew ||
				((resourcePermission.getCompanyId() != resourcePermissionModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourcePermission.getName(),
					resourcePermissionModelImpl.getOriginalName()) ||
				(resourcePermission.getScope() != resourcePermissionModelImpl.getOriginalScope()) ||
				!Validator.equals(resourcePermission.getPrimKey(),
					resourcePermissionModelImpl.getOriginalPrimKey()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
				new Object[] {
					new Long(resourcePermission.getCompanyId()),
					
				resourcePermission.getName(),
					new Integer(resourcePermission.getScope()),
					
				resourcePermission.getPrimKey(),
					new Long(resourcePermission.getRoleId())
				}, resourcePermission);
		}

		return resourcePermission;
	}

	protected ResourcePermission toUnwrappedModel(
		ResourcePermission resourcePermission) {
		if (resourcePermission instanceof ResourcePermissionImpl) {
			return resourcePermission;
		}

		ResourcePermissionImpl resourcePermissionImpl = new ResourcePermissionImpl();

		resourcePermissionImpl.setNew(resourcePermission.isNew());
		resourcePermissionImpl.setPrimaryKey(resourcePermission.getPrimaryKey());

		resourcePermissionImpl.setResourcePermissionId(resourcePermission.getResourcePermissionId());
		resourcePermissionImpl.setCompanyId(resourcePermission.getCompanyId());
		resourcePermissionImpl.setName(resourcePermission.getName());
		resourcePermissionImpl.setScope(resourcePermission.getScope());
		resourcePermissionImpl.setPrimKey(resourcePermission.getPrimKey());
		resourcePermissionImpl.setRoleId(resourcePermission.getRoleId());
		resourcePermissionImpl.setActionIds(resourcePermission.getActionIds());

		return resourcePermissionImpl;
	}

	public ResourcePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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

	public ResourcePermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
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

	public List<ResourcePermission> findByRoleId(long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.roleId = ?");

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

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.roleId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("resourcePermission.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
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
			StringBundler msg = new StringBundler(3);
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
			StringBundler msg = new StringBundler(3);
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

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

			query.append("resourcePermission.roleId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("resourcePermission.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
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

	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_N_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(10);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_N_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope, int start, int end) throws SystemException {
		return findByC_N_S(companyId, name, scope, start, end, null);
	}

	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_N_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 10;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("resourcePermission.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_N_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourcePermission findByC_N_S_First(long companyId, String name,
		int scope, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByC_N_S(companyId, name, scope, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No ResourcePermission exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(", ");
			msg.append("scope=" + scope);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission findByC_N_S_Last(long companyId, String name,
		int scope, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByC_N_S(companyId, name, scope);

		List<ResourcePermission> list = findByC_N_S(companyId, name, scope,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No ResourcePermission exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(", ");
			msg.append("scope=" + scope);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, String name, int scope,
		OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		int count = countByC_N_S(companyId, name, scope);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 10;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

			query.append("resourcePermission.companyId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("resourcePermission.name IS NULL");
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append("(resourcePermission.name IS NULL OR ");
				}

				query.append("resourcePermission.name = ?");

				if (name.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" AND ");

			query.append("resourcePermission.scope = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("resourcePermission.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (name != null) {
				qPos.add(name);
			}

			qPos.add(scope);

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

	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				primKey
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_N_S_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(15);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("resourcePermission.primKey IS NULL");
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.primKey IS NULL OR ");
					}

					query.append("resourcePermission.primKey = ?");

					if (primKey.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_N_S_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey, int start, int end)
		throws SystemException {
		return findByC_N_S_P(companyId, name, scope, primKey, start, end, null);
	}

	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				primKey,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_N_S_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 15;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("resourcePermission.primKey IS NULL");
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.primKey IS NULL OR ");
					}

					query.append("resourcePermission.primKey = ?");

					if (primKey.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("resourcePermission.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_N_S_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourcePermission findByC_N_S_P_First(long companyId, String name,
		int scope, String primKey, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByC_N_S_P(companyId, name, scope,
				primKey, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(9);
			msg.append("No ResourcePermission exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(", ");
			msg.append("scope=" + scope);
			msg.append(", ");
			msg.append("primKey=" + primKey);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission findByC_N_S_P_Last(long companyId, String name,
		int scope, String primKey, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByC_N_S_P(companyId, name, scope, primKey);

		List<ResourcePermission> list = findByC_N_S_P(companyId, name, scope,
				primKey, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(9);
			msg.append("No ResourcePermission exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(", ");
			msg.append("scope=" + scope);
			msg.append(", ");
			msg.append("primKey=" + primKey);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, String name, int scope,
		String primKey, OrderByComparator obc)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		int count = countByC_N_S_P(companyId, name, scope, primKey);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 15;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

			query.append("resourcePermission.companyId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("resourcePermission.name IS NULL");
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append("(resourcePermission.name IS NULL OR ");
				}

				query.append("resourcePermission.name = ?");

				if (name.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" AND ");

			query.append("resourcePermission.scope = ?");

			query.append(" AND ");

			if (primKey == null) {
				query.append("resourcePermission.primKey IS NULL");
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append("(resourcePermission.primKey IS NULL OR ");
				}

				query.append("resourcePermission.primKey = ?");

				if (primKey.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("resourcePermission.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (name != null) {
				qPos.add(name);
			}

			qPos.add(scope);

			if (primKey != null) {
				qPos.add(primKey);
			}

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

	public ResourcePermission findByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = fetchByC_N_S_P_R(companyId,
				name, scope, primKey, roleId);

		if (resourcePermission == null) {
			StringBundler msg = new StringBundler(11);
			msg.append("No ResourcePermission exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(", ");
			msg.append("scope=" + scope);
			msg.append(", ");
			msg.append("primKey=" + primKey);
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

	public ResourcePermission fetchByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId) throws SystemException {
		return fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId, true);
	}

	public ResourcePermission fetchByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				primKey, new Long(roleId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(17);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("resourcePermission.primKey IS NULL");
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.primKey IS NULL OR ");
					}

					query.append("resourcePermission.primKey = ?");

					if (primKey.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

				qPos.add(roleId);

				List<ResourcePermission> list = q.list();

				result = list;

				ResourcePermission resourcePermission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
						finderArgs, list);
				}
				else {
					resourcePermission = list.get(0);

					cacheResult(resourcePermission);

					if ((resourcePermission.getCompanyId() != companyId) ||
							(resourcePermission.getName() == null) ||
							!resourcePermission.getName().equals(name) ||
							(resourcePermission.getScope() != scope) ||
							(resourcePermission.getPrimKey() == null) ||
							!resourcePermission.getPrimKey().equals(primKey) ||
							(resourcePermission.getRoleId() != roleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
							finderArgs, resourcePermission);
					}
				}

				return resourcePermission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
						finderArgs, new ArrayList<ResourcePermission>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
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

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT resourcePermission FROM ResourcePermission resourcePermission ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("resourcePermission.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
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

	public void removeByRoleId(long roleId) throws SystemException {
		for (ResourcePermission resourcePermission : findByRoleId(roleId)) {
			remove(resourcePermission);
		}
	}

	public void removeByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		for (ResourcePermission resourcePermission : findByC_N_S(companyId,
				name, scope)) {
			remove(resourcePermission);
		}
	}

	public void removeByC_N_S_P(long companyId, String name, int scope,
		String primKey) throws SystemException {
		for (ResourcePermission resourcePermission : findByC_N_S_P(companyId,
				name, scope, primKey)) {
			remove(resourcePermission);
		}
	}

	public void removeByC_N_S_P_R(long companyId, String name, int scope,
		String primKey, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByC_N_S_P_R(companyId,
				name, scope, primKey, roleId);

		remove(resourcePermission);
	}

	public void removeAll() throws SystemException {
		for (ResourcePermission resourcePermission : findAll()) {
			remove(resourcePermission);
		}
	}

	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(roleId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(resourcePermission) ");
				query.append(
					"FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.roleId = ?");

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

	public int countByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(11);
				query.append("SELECT COUNT(resourcePermission) ");
				query.append(
					"FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N_S_P(long companyId, String name, int scope,
		String primKey) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				primKey
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(16);
				query.append("SELECT COUNT(resourcePermission) ");
				query.append(
					"FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("resourcePermission.primKey IS NULL");
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.primKey IS NULL OR ");
					}

					query.append("resourcePermission.primKey = ?");

					if (primKey.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N_S_P_R(long companyId, String name, int scope,
		String primKey, long roleId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope),
				
				primKey, new Long(roleId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S_P_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(18);
				query.append("SELECT COUNT(resourcePermission) ");
				query.append(
					"FROM ResourcePermission resourcePermission WHERE ");

				query.append("resourcePermission.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("resourcePermission.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.name IS NULL OR ");
					}

					query.append("resourcePermission.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.scope = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("resourcePermission.primKey IS NULL");
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append("(resourcePermission.primKey IS NULL OR ");
					}

					query.append("resourcePermission.primKey = ?");

					if (primKey.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("resourcePermission.roleId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S_P_R,
					finderArgs, count);

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
						"SELECT COUNT(resourcePermission) FROM ResourcePermission resourcePermission");

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

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(ResourcePermissionPersistenceImpl.class);
}