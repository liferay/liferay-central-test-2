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
import com.liferay.portal.NoSuchUserIdMapperException;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserIdMapperPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapperPersistence
 * @see       UserIdMapperUtil
 * @generated
 */
public class UserIdMapperPersistenceImpl extends BasePersistenceImpl<UserIdMapper>
	implements UserIdMapperPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserIdMapperImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_E = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_E",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_E = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_E",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(UserIdMapper userIdMapper) {
		EntityCacheUtil.putResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey(), userIdMapper);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(userIdMapper.getUserId()),
				
			userIdMapper.getType()
			}, userIdMapper);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
			new Object[] {
				userIdMapper.getType(),
				
			userIdMapper.getExternalUserId()
			}, userIdMapper);
	}

	public void cacheResult(List<UserIdMapper> userIdMappers) {
		for (UserIdMapper userIdMapper : userIdMappers) {
			if (EntityCacheUtil.getResult(
						UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
						UserIdMapperImpl.class, userIdMapper.getPrimaryKey(),
						this) == null) {
				cacheResult(userIdMapper);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserIdMapperImpl.class.getName());
		EntityCacheUtil.clearCache(UserIdMapperImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public UserIdMapper create(long userIdMapperId) {
		UserIdMapper userIdMapper = new UserIdMapperImpl();

		userIdMapper.setNew(true);
		userIdMapper.setPrimaryKey(userIdMapperId);

		return userIdMapper;
	}

	public UserIdMapper remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public UserIdMapper remove(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserIdMapper userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
					new Long(userIdMapperId));

			if (userIdMapper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userIdMapperId);
				}

				throw new NoSuchUserIdMapperException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userIdMapperId);
			}

			return remove(userIdMapper);
		}
		catch (NoSuchUserIdMapperException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper remove(UserIdMapper userIdMapper)
		throws SystemException {
		for (ModelListener<UserIdMapper> listener : listeners) {
			listener.onBeforeRemove(userIdMapper);
		}

		userIdMapper = removeImpl(userIdMapper);

		for (ModelListener<UserIdMapper> listener : listeners) {
			listener.onAfterRemove(userIdMapper);
		}

		return userIdMapper;
	}

	protected UserIdMapper removeImpl(UserIdMapper userIdMapper)
		throws SystemException {
		userIdMapper = toUnwrappedModel(userIdMapper);

		Session session = null;

		try {
			session = openSession();

			if (userIdMapper.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserIdMapperImpl.class,
						userIdMapper.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userIdMapper);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		UserIdMapperModelImpl userIdMapperModelImpl = (UserIdMapperModelImpl)userIdMapper;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(userIdMapperModelImpl.getOriginalUserId()),
				
			userIdMapperModelImpl.getOriginalType()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
			new Object[] {
				userIdMapperModelImpl.getOriginalType(),
				
			userIdMapperModelImpl.getOriginalExternalUserId()
			});

		EntityCacheUtil.removeResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey());

		return userIdMapper;
	}

	public UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws SystemException {
		userIdMapper = toUnwrappedModel(userIdMapper);

		boolean isNew = userIdMapper.isNew();

		UserIdMapperModelImpl userIdMapperModelImpl = (UserIdMapperModelImpl)userIdMapper;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userIdMapper, merge);

			userIdMapper.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey(), userIdMapper);

		if (!isNew &&
				((userIdMapper.getUserId() != userIdMapperModelImpl.getOriginalUserId()) ||
				!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(userIdMapperModelImpl.getOriginalUserId()),
					
				userIdMapperModelImpl.getOriginalType()
				});
		}

		if (isNew ||
				((userIdMapper.getUserId() != userIdMapperModelImpl.getOriginalUserId()) ||
				!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(userIdMapper.getUserId()),
					
				userIdMapper.getType()
				}, userIdMapper);
		}

		if (!isNew &&
				(!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()) ||
				!Validator.equals(userIdMapper.getExternalUserId(),
					userIdMapperModelImpl.getOriginalExternalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
				new Object[] {
					userIdMapperModelImpl.getOriginalType(),
					
				userIdMapperModelImpl.getOriginalExternalUserId()
				});
		}

		if (isNew ||
				(!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()) ||
				!Validator.equals(userIdMapper.getExternalUserId(),
					userIdMapperModelImpl.getOriginalExternalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
				new Object[] {
					userIdMapper.getType(),
					
				userIdMapper.getExternalUserId()
				}, userIdMapper);
		}

		return userIdMapper;
	}

	protected UserIdMapper toUnwrappedModel(UserIdMapper userIdMapper) {
		if (userIdMapper instanceof UserIdMapperImpl) {
			return userIdMapper;
		}

		UserIdMapperImpl userIdMapperImpl = new UserIdMapperImpl();

		userIdMapperImpl.setNew(userIdMapper.isNew());
		userIdMapperImpl.setPrimaryKey(userIdMapper.getPrimaryKey());

		userIdMapperImpl.setUserIdMapperId(userIdMapper.getUserIdMapperId());
		userIdMapperImpl.setUserId(userIdMapper.getUserId());
		userIdMapperImpl.setType(userIdMapper.getType());
		userIdMapperImpl.setDescription(userIdMapper.getDescription());
		userIdMapperImpl.setExternalUserId(userIdMapper.getExternalUserId());

		return userIdMapperImpl;
	}

	public UserIdMapper findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserIdMapper findByPrimaryKey(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByPrimaryKey(userIdMapperId);

		if (userIdMapper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userIdMapperId);
			}

			throw new NoSuchUserIdMapperException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userIdMapperId);
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserIdMapper fetchByPrimaryKey(long userIdMapperId)
		throws SystemException {
		UserIdMapper userIdMapper = (UserIdMapper)EntityCacheUtil.getResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				UserIdMapperImpl.class, userIdMapperId, this);

		if (userIdMapper == null) {
			Session session = null;

			try {
				session = openSession();

				userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
						new Long(userIdMapperId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userIdMapper != null) {
					cacheResult(userIdMapper);
				}

				closeSession(session);
			}
		}

		return userIdMapper;
	}

	public List<UserIdMapper> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<UserIdMapper> list = (List<UserIdMapper>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserIdMapper>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserIdMapper> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<UserIdMapper> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserIdMapper> list = (List<UserIdMapper>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserIdMapper>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserIdMapper findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		List<UserIdMapper> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserIdMapper findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		int count = countByUserId(userId);

		List<UserIdMapper> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserIdMapper[] findByUserId_PrevAndNext(long userIdMapperId,
		long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByPrimaryKey(userIdMapperId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userIdMapper);

			UserIdMapper[] array = new UserIdMapperImpl[3];

			array[0] = (UserIdMapper)objArray[0];
			array[1] = (UserIdMapper)objArray[1];
			array[2] = (UserIdMapper)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper findByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByU_T(userId, type);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByU_T(long userId, String type)
		throws SystemException {
		return fetchByU_T(userId, type, true);
	}

	public UserIdMapper fetchByU_T(long userId, String type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

				query.append(_FINDER_COLUMN_U_T_USERID_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_U_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_T_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<UserIdMapper> list = q.list();

				result = list;

				UserIdMapper userIdMapper = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					userIdMapper = list.get(0);

					cacheResult(userIdMapper);

					if ((userIdMapper.getUserId() != userId) ||
							(userIdMapper.getType() == null) ||
							!userIdMapper.getType().equals(type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, userIdMapper);
					}
				}

				return userIdMapper;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, new ArrayList<UserIdMapper>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (UserIdMapper)result;
			}
		}
	}

	public UserIdMapper findByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByT_E(type, externalUserId);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(", externalUserId=");
			msg.append(externalUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByT_E(String type, String externalUserId)
		throws SystemException {
		return fetchByT_E(type, externalUserId, true);
	}

	public UserIdMapper fetchByT_E(String type, String externalUserId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { type, externalUserId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_E,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

				if (type == null) {
					query.append(_FINDER_COLUMN_T_E_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_E_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_E_TYPE_2);
					}
				}

				if (externalUserId == null) {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_1);
				}
				else {
					if (externalUserId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
				}

				List<UserIdMapper> list = q.list();

				result = list;

				UserIdMapper userIdMapper = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
						finderArgs, list);
				}
				else {
					userIdMapper = list.get(0);

					cacheResult(userIdMapper);

					if ((userIdMapper.getType() == null) ||
							!userIdMapper.getType().equals(type) ||
							(userIdMapper.getExternalUserId() == null) ||
							!userIdMapper.getExternalUserId()
											 .equals(externalUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
							finderArgs, userIdMapper);
					}
				}

				return userIdMapper;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
						finderArgs, new ArrayList<UserIdMapper>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (UserIdMapper)result;
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

	public List<UserIdMapper> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserIdMapper> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserIdMapper> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserIdMapper> list = (List<UserIdMapper>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_USERIDMAPPER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_USERIDMAPPER;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserIdMapper>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (UserIdMapper userIdMapper : findByUserId(userId)) {
			remove(userIdMapper);
		}
	}

	public void removeByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByU_T(userId, type);

		remove(userIdMapper);
	}

	public void removeByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByT_E(type, externalUserId);

		remove(userIdMapper);
	}

	public void removeAll() throws SystemException {
		for (UserIdMapper userIdMapper : findAll()) {
			remove(userIdMapper);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_T(long userId, String type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

				query.append(_FINDER_COLUMN_U_T_USERID_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_U_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_T_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_E(String type, String externalUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { type, externalUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_E,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

				if (type == null) {
					query.append(_FINDER_COLUMN_T_E_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_E_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_E_TYPE_2);
					}
				}

				if (externalUserId == null) {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_1);
				}
				else {
					if (externalUserId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_E, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_USERIDMAPPER);

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
						"value.object.listener.com.liferay.portal.model.UserIdMapper")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserIdMapper>> listenersList = new ArrayList<ModelListener<UserIdMapper>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserIdMapper>)Class.forName(
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
	private static final String _SQL_SELECT_USERIDMAPPER = "SELECT userIdMapper FROM UserIdMapper userIdMapper";
	private static final String _SQL_SELECT_USERIDMAPPER_WHERE = "SELECT userIdMapper FROM UserIdMapper userIdMapper WHERE ";
	private static final String _SQL_COUNT_USERIDMAPPER = "SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper";
	private static final String _SQL_COUNT_USERIDMAPPER_WHERE = "SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userIdMapper.userId = ?";
	private static final String _FINDER_COLUMN_U_T_USERID_2 = "userIdMapper.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_TYPE_1 = "userIdMapper.type IS NULL";
	private static final String _FINDER_COLUMN_U_T_TYPE_2 = "userIdMapper.type = ?";
	private static final String _FINDER_COLUMN_U_T_TYPE_3 = "(userIdMapper.type IS NULL OR userIdMapper.type = ?)";
	private static final String _FINDER_COLUMN_T_E_TYPE_1 = "userIdMapper.type IS NULL AND ";
	private static final String _FINDER_COLUMN_T_E_TYPE_2 = "userIdMapper.type = ? AND ";
	private static final String _FINDER_COLUMN_T_E_TYPE_3 = "(userIdMapper.type IS NULL OR userIdMapper.type = ?) AND ";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_1 = "userIdMapper.externalUserId IS NULL";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_2 = "userIdMapper.externalUserId = ?";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_3 = "(userIdMapper.externalUserId IS NULL OR userIdMapper.externalUserId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userIdMapper.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserIdMapper exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserIdMapper exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserIdMapperPersistenceImpl.class);
}