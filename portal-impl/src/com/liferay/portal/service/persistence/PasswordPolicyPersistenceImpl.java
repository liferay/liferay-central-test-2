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
import com.liferay.portal.NoSuchPasswordPolicyException;
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
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.model.impl.PasswordPolicyModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PasswordPolicyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyPersistence
 * @see       PasswordPolicyUtil
 * @generated
 */
public class PasswordPolicyPersistenceImpl extends BasePersistenceImpl<PasswordPolicy>
	implements PasswordPolicyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordPolicyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PasswordPolicy passwordPolicy) {
		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
			new Object[] {
				new Long(passwordPolicy.getCompanyId()),
				Boolean.valueOf(passwordPolicy.getDefaultPolicy())
			}, passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(passwordPolicy.getCompanyId()),
				
			passwordPolicy.getName()
			}, passwordPolicy);
	}

	public void cacheResult(List<PasswordPolicy> passwordPolicies) {
		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			if (EntityCacheUtil.getResult(
						PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class,
						passwordPolicy.getPrimaryKey(), this) == null) {
				cacheResult(passwordPolicy);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PasswordPolicyImpl.class.getName());
		EntityCacheUtil.clearCache(PasswordPolicyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PasswordPolicy create(long passwordPolicyId) {
		PasswordPolicy passwordPolicy = new PasswordPolicyImpl();

		passwordPolicy.setNew(true);
		passwordPolicy.setPrimaryKey(passwordPolicyId);

		return passwordPolicy;
	}

	public PasswordPolicy remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PasswordPolicy remove(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicy passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
					new Long(passwordPolicyId));

			if (passwordPolicy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						passwordPolicyId);
				}

				throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					passwordPolicyId);
			}

			return remove(passwordPolicy);
		}
		catch (NoSuchPasswordPolicyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicy remove(PasswordPolicy passwordPolicy)
		throws SystemException {
		for (ModelListener<PasswordPolicy> listener : listeners) {
			listener.onBeforeRemove(passwordPolicy);
		}

		passwordPolicy = removeImpl(passwordPolicy);

		for (ModelListener<PasswordPolicy> listener : listeners) {
			listener.onAfterRemove(passwordPolicy);
		}

		return passwordPolicy;
	}

	protected PasswordPolicy removeImpl(PasswordPolicy passwordPolicy)
		throws SystemException {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		Session session = null;

		try {
			session = openSession();

			if (passwordPolicy.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PasswordPolicyImpl.class,
						passwordPolicy.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(passwordPolicy);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
			new Object[] {
				new Long(passwordPolicyModelImpl.getOriginalCompanyId()),
				Boolean.valueOf(
					passwordPolicyModelImpl.getOriginalDefaultPolicy())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(passwordPolicyModelImpl.getOriginalCompanyId()),
				
			passwordPolicyModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey());

		return passwordPolicy;
	}

	public PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws SystemException {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		boolean isNew = passwordPolicy.isNew();

		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, passwordPolicy, merge);

			passwordPolicy.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy);

		if (!isNew &&
				((passwordPolicy.getCompanyId() != passwordPolicyModelImpl.getOriginalCompanyId()) ||
				(passwordPolicy.getDefaultPolicy() != passwordPolicyModelImpl.getOriginalDefaultPolicy()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
				new Object[] {
					new Long(passwordPolicyModelImpl.getOriginalCompanyId()),
					Boolean.valueOf(
						passwordPolicyModelImpl.getOriginalDefaultPolicy())
				});
		}

		if (isNew ||
				((passwordPolicy.getCompanyId() != passwordPolicyModelImpl.getOriginalCompanyId()) ||
				(passwordPolicy.getDefaultPolicy() != passwordPolicyModelImpl.getOriginalDefaultPolicy()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
				new Object[] {
					new Long(passwordPolicy.getCompanyId()),
					Boolean.valueOf(passwordPolicy.getDefaultPolicy())
				}, passwordPolicy);
		}

		if (!isNew &&
				((passwordPolicy.getCompanyId() != passwordPolicyModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(passwordPolicy.getName(),
					passwordPolicyModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(passwordPolicyModelImpl.getOriginalCompanyId()),
					
				passwordPolicyModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((passwordPolicy.getCompanyId() != passwordPolicyModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(passwordPolicy.getName(),
					passwordPolicyModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(passwordPolicy.getCompanyId()),
					
				passwordPolicy.getName()
				}, passwordPolicy);
		}

		return passwordPolicy;
	}

	protected PasswordPolicy toUnwrappedModel(PasswordPolicy passwordPolicy) {
		if (passwordPolicy instanceof PasswordPolicyImpl) {
			return passwordPolicy;
		}

		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setNew(passwordPolicy.isNew());
		passwordPolicyImpl.setPrimaryKey(passwordPolicy.getPrimaryKey());

		passwordPolicyImpl.setPasswordPolicyId(passwordPolicy.getPasswordPolicyId());
		passwordPolicyImpl.setCompanyId(passwordPolicy.getCompanyId());
		passwordPolicyImpl.setUserId(passwordPolicy.getUserId());
		passwordPolicyImpl.setUserName(passwordPolicy.getUserName());
		passwordPolicyImpl.setCreateDate(passwordPolicy.getCreateDate());
		passwordPolicyImpl.setModifiedDate(passwordPolicy.getModifiedDate());
		passwordPolicyImpl.setDefaultPolicy(passwordPolicy.isDefaultPolicy());
		passwordPolicyImpl.setName(passwordPolicy.getName());
		passwordPolicyImpl.setDescription(passwordPolicy.getDescription());
		passwordPolicyImpl.setChangeable(passwordPolicy.isChangeable());
		passwordPolicyImpl.setChangeRequired(passwordPolicy.isChangeRequired());
		passwordPolicyImpl.setMinAge(passwordPolicy.getMinAge());
		passwordPolicyImpl.setCheckSyntax(passwordPolicy.isCheckSyntax());
		passwordPolicyImpl.setAllowDictionaryWords(passwordPolicy.isAllowDictionaryWords());
		passwordPolicyImpl.setMinLength(passwordPolicy.getMinLength());
		passwordPolicyImpl.setHistory(passwordPolicy.isHistory());
		passwordPolicyImpl.setHistoryCount(passwordPolicy.getHistoryCount());
		passwordPolicyImpl.setExpireable(passwordPolicy.isExpireable());
		passwordPolicyImpl.setMaxAge(passwordPolicy.getMaxAge());
		passwordPolicyImpl.setWarningTime(passwordPolicy.getWarningTime());
		passwordPolicyImpl.setGraceLimit(passwordPolicy.getGraceLimit());
		passwordPolicyImpl.setLockout(passwordPolicy.isLockout());
		passwordPolicyImpl.setMaxFailure(passwordPolicy.getMaxFailure());
		passwordPolicyImpl.setLockoutDuration(passwordPolicy.getLockoutDuration());
		passwordPolicyImpl.setRequireUnlock(passwordPolicy.isRequireUnlock());
		passwordPolicyImpl.setResetFailureCount(passwordPolicy.getResetFailureCount());

		return passwordPolicyImpl;
	}

	public PasswordPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByPrimaryKey(passwordPolicyId);

		if (passwordPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + passwordPolicyId);
			}

			throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				passwordPolicyId);
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PasswordPolicy fetchByPrimaryKey(long passwordPolicyId)
		throws SystemException {
		PasswordPolicy passwordPolicy = (PasswordPolicy)EntityCacheUtil.getResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, passwordPolicyId, this);

		if (passwordPolicy == null) {
			Session session = null;

			try {
				session = openSession();

				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						new Long(passwordPolicyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (passwordPolicy != null) {
					cacheResult(passwordPolicy);
				}

				closeSession(session);
			}
		}

		return passwordPolicy;
	}

	public PasswordPolicy findByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_DP(companyId, defaultPolicy);

		if (passwordPolicy == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", defaultPolicy=");
			msg.append(defaultPolicy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		return fetchByC_DP(companyId, defaultPolicy, true);
	}

	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultPolicy)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DP,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

				query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

				List<PasswordPolicy> list = q.list();

				result = list;

				PasswordPolicy passwordPolicy = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs, list);
				}
				else {
					passwordPolicy = list.get(0);

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getDefaultPolicy() != defaultPolicy)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
							finderArgs, passwordPolicy);
					}
				}

				return passwordPolicy;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs, new ArrayList<PasswordPolicy>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicy)result;
			}
		}
	}

	public PasswordPolicy findByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_N(companyId, name);

		if (passwordPolicy == null) {
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

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	public PasswordPolicy fetchByC_N(long companyId, String name,
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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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

				List<PasswordPolicy> list = q.list();

				result = list;

				PasswordPolicy passwordPolicy = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					passwordPolicy = list.get(0);

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getName() == null) ||
							!passwordPolicy.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, passwordPolicy);
					}
				}

				return passwordPolicy;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, new ArrayList<PasswordPolicy>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PasswordPolicy)result;
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

	public List<PasswordPolicy> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PasswordPolicy> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PasswordPolicy> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_PASSWORDPOLICY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PASSWORDPOLICY;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PasswordPolicy>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_DP(companyId, defaultPolicy);

		remove(passwordPolicy);
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_N(companyId, name);

		remove(passwordPolicy);
	}

	public void removeAll() throws SystemException {
		for (PasswordPolicy passwordPolicy : findAll()) {
			remove(passwordPolicy);
		}
	}

	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultPolicy)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_DP,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

				query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP,
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

				query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDPOLICY);

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
						"value.object.listener.com.liferay.portal.model.PasswordPolicy")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordPolicy>> listenersList = new ArrayList<ModelListener<PasswordPolicy>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordPolicy>)Class.forName(
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
	private static final String _SQL_SELECT_PASSWORDPOLICY = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_SELECT_PASSWORDPOLICY_WHERE = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICY = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_COUNT_PASSWORDPOLICY_WHERE = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FINDER_COLUMN_C_DP_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DP_DEFAULTPOLICY_2 = "passwordPolicy.defaultPolicy = ?";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "passwordPolicy.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "passwordPolicy.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(passwordPolicy.name IS NULL OR passwordPolicy.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicy.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordPolicy exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordPolicy exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyPersistenceImpl.class);
}