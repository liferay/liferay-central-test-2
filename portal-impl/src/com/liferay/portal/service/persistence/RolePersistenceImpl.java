/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="RolePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RolePersistence
 * @see       RoleUtil
 * @generated
 */
public class RolePersistenceImpl extends BasePersistenceImpl<Role>
	implements RolePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = RoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_SUBTYPE = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySubtype", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SUBTYPE = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySubtype",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SUBTYPE = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countBySubtype", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_S",
			new String[] { Integer.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_S",
			new String[] {
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_S",
			new String[] { Integer.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Role role) {
		EntityCacheUtil.putResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey(), role);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { new Long(role.getCompanyId()), role.getName() }, role);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				new Long(role.getCompanyId()), new Long(role.getClassNameId()),
				new Long(role.getClassPK())
			}, role);
	}

	public void cacheResult(List<Role> roles) {
		for (Role role : roles) {
			if (EntityCacheUtil.getResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
						RoleImpl.class, role.getPrimaryKey(), this) == null) {
				cacheResult(role);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(RoleImpl.class.getName());
		EntityCacheUtil.clearCache(RoleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Role create(long roleId) {
		Role role = new RoleImpl();

		role.setNew(true);
		role.setPrimaryKey(roleId);

		return role;
	}

	public Role remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Role remove(long roleId) throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Role role = (Role)session.get(RoleImpl.class, new Long(roleId));

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + roleId);
				}

				throw new NoSuchRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					roleId);
			}

			return remove(role);
		}
		catch (NoSuchRoleException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Role remove(Role role) throws SystemException {
		for (ModelListener<Role> listener : listeners) {
			listener.onBeforeRemove(role);
		}

		role = removeImpl(role);

		for (ModelListener<Role> listener : listeners) {
			listener.onAfterRemove(role);
		}

		return role;
	}

	protected Role removeImpl(Role role) throws SystemException {
		role = toUnwrappedModel(role);

		try {
			clearGroups.clear(role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}

		try {
			clearPermissions.clear(role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}

		try {
			clearUsers.clear(role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (role.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(RoleImpl.class,
						role.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(role);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RoleModelImpl roleModelImpl = (RoleModelImpl)role;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(roleModelImpl.getOriginalCompanyId()),
				
			roleModelImpl.getOriginalName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				new Long(roleModelImpl.getOriginalCompanyId()),
				new Long(roleModelImpl.getOriginalClassNameId()),
				new Long(roleModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey());

		return role;
	}

	public Role updateImpl(com.liferay.portal.model.Role role, boolean merge)
		throws SystemException {
		role = toUnwrappedModel(role);

		boolean isNew = role.isNew();

		RoleModelImpl roleModelImpl = (RoleModelImpl)role;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, role, merge);

			role.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey(), role);

		if (!isNew &&
				((role.getCompanyId() != roleModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(role.getName(),
					roleModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(roleModelImpl.getOriginalCompanyId()),
					
				roleModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((role.getCompanyId() != roleModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(role.getName(),
					roleModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] { new Long(role.getCompanyId()), role.getName() },
				role);
		}

		if (!isNew &&
				((role.getCompanyId() != roleModelImpl.getOriginalCompanyId()) ||
				(role.getClassNameId() != roleModelImpl.getOriginalClassNameId()) ||
				(role.getClassPK() != roleModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					new Long(roleModelImpl.getOriginalCompanyId()),
					new Long(roleModelImpl.getOriginalClassNameId()),
					new Long(roleModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((role.getCompanyId() != roleModelImpl.getOriginalCompanyId()) ||
				(role.getClassNameId() != roleModelImpl.getOriginalClassNameId()) ||
				(role.getClassPK() != roleModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					new Long(role.getCompanyId()),
					new Long(role.getClassNameId()), new Long(role.getClassPK())
				}, role);
		}

		return role;
	}

	protected Role toUnwrappedModel(Role role) {
		if (role instanceof RoleImpl) {
			return role;
		}

		RoleImpl roleImpl = new RoleImpl();

		roleImpl.setNew(role.isNew());
		roleImpl.setPrimaryKey(role.getPrimaryKey());

		roleImpl.setRoleId(role.getRoleId());
		roleImpl.setCompanyId(role.getCompanyId());
		roleImpl.setClassNameId(role.getClassNameId());
		roleImpl.setClassPK(role.getClassPK());
		roleImpl.setName(role.getName());
		roleImpl.setTitle(role.getTitle());
		roleImpl.setDescription(role.getDescription());
		roleImpl.setType(role.getType());
		roleImpl.setSubtype(role.getSubtype());

		return roleImpl;
	}

	public Role findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Role findByPrimaryKey(long roleId)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByPrimaryKey(roleId);

		if (role == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + roleId);
			}

			throw new NoSuchRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				roleId);
		}

		return role;
	}

	public Role fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Role fetchByPrimaryKey(long roleId) throws SystemException {
		Role role = (Role)EntityCacheUtil.getResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
				RoleImpl.class, roleId, this);

		if (role == null) {
			Session session = null;

			try {
				session = openSession();

				role = (Role)session.get(RoleImpl.class, new Long(roleId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (role != null) {
					cacheResult(role);
				}

				closeSession(session);
			}
		}

		return role;
	}

	public List<Role> findByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(RoleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Role> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Role> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(RoleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Role findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		List<Role> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		int count = countByCompanyId(companyId);

		List<Role> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role[] findByCompanyId_PrevAndNext(long roleId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, role, companyId,
					orderByComparator, true);

			array[1] = role;

			array[2] = getByCompanyId_PrevAndNext(session, role, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Role getByCompanyId_PrevAndNext(Session session, Role role,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Role> findBySubtype(String subtype) throws SystemException {
		Object[] finderArgs = new Object[] { subtype };

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SUBTYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ROLE_WHERE);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
					}
				}

				query.append(RoleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SUBTYPE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Role> findBySubtype(String subtype, int start, int end)
		throws SystemException {
		return findBySubtype(subtype, start, end, null);
	}

	public List<Role> findBySubtype(String subtype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				subtype,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SUBTYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ROLE_WHERE);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(RoleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SUBTYPE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Role findBySubtype_First(String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		List<Role> list = findBySubtype(subtype, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("subtype=");
			msg.append(subtype);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role findBySubtype_Last(String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		int count = countBySubtype(subtype);

		List<Role> list = findBySubtype(subtype, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("subtype=");
			msg.append(subtype);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role[] findBySubtype_PrevAndNext(long roleId, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getBySubtype_PrevAndNext(session, role, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = getBySubtype_PrevAndNext(session, role, subtype,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Role getBySubtype_PrevAndNext(Session session, Role role,
		String subtype, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public Role findByC_N(long companyId, String name)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByC_N(companyId, name);

		if (role == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRoleException(msg.toString());
		}

		return role;
	}

	public Role fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	public Role fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_N_NAME_2);
					}
				}

				query.append(RoleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Role> list = q.list();

				result = list;

				Role role = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					role = list.get(0);

					cacheResult(role);

					if ((role.getCompanyId() != companyId) ||
							(role.getName() == null) ||
							!role.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, role);
					}
				}

				return role;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, new ArrayList<Role>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Role)result;
			}
		}
	}

	public List<Role> findByT_S(int type, String subtype)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type), subtype };

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_T_S_TYPE_2);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
					}
				}

				query.append(RoleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Role> findByT_S(int type, String subtype, int start, int end)
		throws SystemException {
		return findByT_S(type, subtype, start, end, null);
	}

	public List<Role> findByT_S(int type, String subtype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type),
				
				subtype,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_T_S_TYPE_2);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(RoleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Role findByT_S_First(int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		List<Role> list = findByT_S(type, subtype, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(", subtype=");
			msg.append(subtype);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role findByT_S_Last(int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		int count = countByT_S(type, subtype);

		List<Role> list = findByT_S(type, subtype, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(", subtype=");
			msg.append(subtype);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Role[] findByT_S_PrevAndNext(long roleId, int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getByT_S_PrevAndNext(session, role, type, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = getByT_S_PrevAndNext(session, role, type, subtype,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Role getByT_S_PrevAndNext(Session session, Role role, int type,
		String subtype, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		query.append(_FINDER_COLUMN_T_S_TYPE_2);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public Role findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByC_C_C(companyId, classNameId, classPK);

		if (role == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRoleException(msg.toString());
		}

		return role;
	}

	public Role fetchByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	public Role fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				query.append(RoleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Role> list = q.list();

				result = list;

				Role role = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					role = list.get(0);

					cacheResult(role);

					if ((role.getCompanyId() != companyId) ||
							(role.getClassNameId() != classNameId) ||
							(role.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, role);
					}
				}

				return role;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, new ArrayList<Role>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Role)result;
			}
		}
	}

	public List<Role> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Role> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Role> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_ROLE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ROLE.concat(RoleModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Role>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Role>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Role>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Role role : findByCompanyId(companyId)) {
			remove(role);
		}
	}

	public void removeBySubtype(String subtype) throws SystemException {
		for (Role role : findBySubtype(subtype)) {
			remove(role);
		}
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchRoleException, SystemException {
		Role role = findByC_N(companyId, name);

		remove(role);
	}

	public void removeByT_S(int type, String subtype) throws SystemException {
		for (Role role : findByT_S(type, subtype)) {
			remove(role);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchRoleException, SystemException {
		Role role = findByC_C_C(companyId, classNameId, classPK);

		remove(role);
	}

	public void removeAll() throws SystemException {
		for (Role role : findAll()) {
			remove(role);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySubtype(String subtype) throws SystemException {
		Object[] finderArgs = new Object[] { subtype };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SUBTYPE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ROLE_WHERE);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (subtype != null) {
					qPos.add(subtype);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SUBTYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_S(int type, String subtype) throws SystemException {
		Object[] finderArgs = new Object[] { new Integer(type), subtype };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_T_S_TYPE_2);

				if (subtype == null) {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
				}
				else {
					if (subtype.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (subtype != null) {
					qPos.add(subtype);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_ROLE_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
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

				Query q = session.createQuery(_SQL_COUNT_ROLE);

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

	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETGROUPS.concat(ORDER_BY_CLAUSE)
										.concat(orderByComparator.getOrderBy());
				}

				else {
					sql = _SQL_GETGROUPS.concat(com.liferay.portal.model.impl.GroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Group>();
				}

				groupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getGroupsSize",
			new String[] { Long.class.getName() });

	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(groupPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			Set<Long> groupPKSet = SetUtil.fromArray(groupPKs);

			List<com.liferay.portal.model.Group> groups = getGroups(pk);

			for (com.liferay.portal.model.Group group : groups) {
				if (!groupPKSet.contains(group.getPrimaryKey())) {
					removeGroup.remove(pk, group.getPrimaryKey());
				}
				else {
					groupPKSet.remove(group.getPrimaryKey());
				}
			}

			for (Long groupPK : groupPKSet) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"getPermissions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETPERMISSIONS.concat(ORDER_BY_CLAUSE)
											 .concat(orderByComparator.getOrderBy());
				}

				sql = _SQL_GETPERMISSIONS;

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Permission>();
				}

				permissionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"getPermissionsSize", new String[] { Long.class.getName() });

	public int getPermissionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_PERMISSION = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"containsPermission",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(permissionPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_PERMISSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsPermission.contains(pk,
							permissionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_PERMISSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			Set<Long> permissionPKSet = SetUtil.fromArray(permissionPKs);

			List<com.liferay.portal.model.Permission> permissions = getPermissions(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				if (!permissionPKSet.contains(permission.getPrimaryKey())) {
					removePermission.remove(pk, permission.getPrimaryKey());
				}
				else {
					permissionPKSet.remove(permission.getPrimaryKey());
				}
			}

			for (Long permissionPK : permissionPKSet) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			long[] permissionPKs = new long[permissions.size()];

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = permissions.get(i);

				permissionPKs[i] = permission.getPrimaryKey();
			}

			setPermissions(pk, permissionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}

				sql = _SQL_GETUSERS;

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.User>();
				}

				userPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getUsersSize",
			new String[] { Long.class.getName() });

	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.contains(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
				else {
					userPKSet.remove(user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Role")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Role>> listenersList = new ArrayList<ModelListener<Role>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Role>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsPermission = new ContainsPermission(this);

		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup(RolePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long roleId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(roleId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Roles (roleId, groupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long roleId, long groupId) throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(roleId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeAddAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(groupId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterAddAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE roleId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long roleId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(roleId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Role> listener : listeners) {
						listener.onBeforeRemoveAssociation(roleId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(roleId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Role> listener : listeners) {
						listener.onAfterRemoveAssociation(roleId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE roleId = ? AND groupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long roleId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(roleId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeRemoveAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(groupId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterRemoveAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	protected class ContainsPermission {
		protected ContainsPermission(RolePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPERMISSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long roleId, long permissionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(roleId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddPermission {
		protected AddPermission(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Roles_Permissions (roleId, permissionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long roleId, long permissionId)
			throws SystemException {
			if (!_persistenceImpl.containsPermission.contains(roleId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeAddAssociation(roleId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeAddAssociation(permissionId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(permissionId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterAddAssociation(roleId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterAddAssociation(permissionId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions {
		protected ClearPermissions(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE roleId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long roleId) throws SystemException {
			ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
				permissionPersistence.getListeners();

			List<com.liferay.portal.model.Permission> permissions = null;

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				permissions = getPermissions(roleId);

				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<Role> listener : listeners) {
						listener.onBeforeRemoveAssociation(roleId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(roleId) });

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<Role> listener : listeners) {
						listener.onAfterRemoveAssociation(roleId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onAfterRemoveAssociation(permission.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemovePermission {
		protected RemovePermission(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE roleId = ? AND permissionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long roleId, long permissionId)
			throws SystemException {
			if (_persistenceImpl.containsPermission.contains(roleId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeRemoveAssociation(roleId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(permissionId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterRemoveAssociation(roleId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterRemoveAssociation(permissionId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(RolePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long roleId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(roleId), new Long(userId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUser {
		protected AddUser(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (roleId, userId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long roleId, long userId) throws SystemException {
			if (!_persistenceImpl.containsUser.contains(roleId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeAddAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(userId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterAddAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE roleId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long roleId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(roleId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Role> listener : listeners) {
						listener.onBeforeRemoveAssociation(roleId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(roleId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Role> listener : listeners) {
						listener.onAfterRemoveAssociation(roleId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(RolePersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE roleId = ? AND userId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long roleId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(roleId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeRemoveAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(userId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterRemoveAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private RolePersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_ROLE = "SELECT role FROM Role role";
	private static final String _SQL_SELECT_ROLE_WHERE = "SELECT role FROM Role role WHERE ";
	private static final String _SQL_COUNT_ROLE = "SELECT COUNT(role) FROM Role role";
	private static final String _SQL_COUNT_ROLE_WHERE = "SELECT COUNT(role) FROM Role role WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Roles ON (Groups_Roles.groupId = Group_.groupId) WHERE (Groups_Roles.roleId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ? AND groupId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Roles_Permissions ON (Roles_Permissions.permissionId = Permission_.permissionId) WHERE (Roles_Permissions.roleId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE roleId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE roleId = ? AND permissionId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Roles ON (Users_Roles.userId = User_.userId) WHERE (Users_Roles.roleId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "role.companyId = ?";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_1 = "role.subtype IS NULL";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_2 = "role.subtype = ?";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_3 = "(role.subtype IS NULL OR role.subtype = ?)";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "role.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "role.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "role.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(role.name IS NULL OR role.name = ?)";
	private static final String _FINDER_COLUMN_T_S_TYPE_2 = "role.type = ? AND ";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_1 = "role.subtype IS NULL";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_2 = "role.subtype = ?";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_3 = "(role.subtype IS NULL OR role.subtype = ?)";
	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "role.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "role.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "role.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "role.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Role exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Role exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(RolePersistenceImpl.class);
}