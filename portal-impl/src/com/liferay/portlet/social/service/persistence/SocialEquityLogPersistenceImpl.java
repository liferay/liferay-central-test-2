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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.social.NoSuchEquityLogException;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialEquityLogImpl;
import com.liferay.portlet.social.model.impl.SocialEquityLogModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogPersistence
 * @see       SocialEquityLogUtil
 * @generated
 */
public class SocialEquityLogPersistenceImpl extends BasePersistenceImpl<SocialEquityLog>
	implements SocialEquityLogPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityLogImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_AEI_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByAEI_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByAEI_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_AEI_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByAEI_AI_A_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByAEI_AI_A_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_AEI_T_A = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByAEI_T_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_T_A = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByAEI_T_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_U_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_AI_A_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_AI_A_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_AEI_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AEI_AD_AI_A_T = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_AEI_AD_AI_A_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialEquityLog socialEquityLog) {
		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
			new Object[] {
				new Long(socialEquityLog.getUserId()),
				new Long(socialEquityLog.getAssetEntryId()),
				new Integer(socialEquityLog.getActionDate()),
				
			socialEquityLog.getActionId(),
				Boolean.valueOf(socialEquityLog.getActive()),
				new Integer(socialEquityLog.getType())
			}, socialEquityLog);
	}

	public void cacheResult(List<SocialEquityLog> socialEquityLogs) {
		for (SocialEquityLog socialEquityLog : socialEquityLogs) {
			if (EntityCacheUtil.getResult(
						SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityLogImpl.class,
						socialEquityLog.getPrimaryKey(), this) == null) {
				cacheResult(socialEquityLog);
			}
		}
	}

	public void clearCache() {
		CacheRegistryUtil.clear(SocialEquityLogImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquityLogImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SocialEquityLog socialEquityLog) {
		EntityCacheUtil.removeResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
			new Object[] {
				new Long(socialEquityLog.getUserId()),
				new Long(socialEquityLog.getAssetEntryId()),
				new Integer(socialEquityLog.getActionDate()),
				
			socialEquityLog.getActionId(),
				Boolean.valueOf(socialEquityLog.getActive()),
				new Integer(socialEquityLog.getType())
			});
	}

	public SocialEquityLog create(long equityLogId) {
		SocialEquityLog socialEquityLog = new SocialEquityLogImpl();

		socialEquityLog.setNew(true);
		socialEquityLog.setPrimaryKey(equityLogId);

		return socialEquityLog;
	}

	public SocialEquityLog remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialEquityLog remove(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityLog socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
					new Long(equityLogId));

			if (socialEquityLog == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
				}

				throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityLogId);
			}

			return remove(socialEquityLog);
		}
		catch (NoSuchEquityLogException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog removeImpl(SocialEquityLog socialEquityLog)
		throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		Session session = null;

		try {
			session = openSession();

			if (socialEquityLog.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquityLogImpl.class,
						socialEquityLog.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquityLog);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquityLogModelImpl socialEquityLogModelImpl = (SocialEquityLogModelImpl)socialEquityLog;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
			new Object[] {
				new Long(socialEquityLogModelImpl.getOriginalUserId()),
				new Long(socialEquityLogModelImpl.getOriginalAssetEntryId()),
				new Integer(socialEquityLogModelImpl.getOriginalActionDate()),
				
			socialEquityLogModelImpl.getOriginalActionId(),
				Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
				new Integer(socialEquityLogModelImpl.getOriginalType())
			});

		EntityCacheUtil.removeResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey());

		return socialEquityLog;
	}

	public SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge) throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		boolean isNew = socialEquityLog.isNew();

		SocialEquityLogModelImpl socialEquityLogModelImpl = (SocialEquityLogModelImpl)socialEquityLog;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityLog, merge);

			socialEquityLog.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);

		if (!isNew &&
				((socialEquityLog.getUserId() != socialEquityLogModelImpl.getOriginalUserId()) ||
				(socialEquityLog.getAssetEntryId() != socialEquityLogModelImpl.getOriginalAssetEntryId()) ||
				(socialEquityLog.getActionDate() != socialEquityLogModelImpl.getOriginalActionDate()) ||
				!Validator.equals(socialEquityLog.getActionId(),
					socialEquityLogModelImpl.getOriginalActionId()) ||
				(socialEquityLog.getActive() != socialEquityLogModelImpl.getOriginalActive()) ||
				(socialEquityLog.getType() != socialEquityLogModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
				new Object[] {
					new Long(socialEquityLogModelImpl.getOriginalUserId()),
					new Long(socialEquityLogModelImpl.getOriginalAssetEntryId()),
					new Integer(socialEquityLogModelImpl.getOriginalActionDate()),
					
				socialEquityLogModelImpl.getOriginalActionId(),
					Boolean.valueOf(
						socialEquityLogModelImpl.getOriginalActive()),
					new Integer(socialEquityLogModelImpl.getOriginalType())
				});
		}

		if (isNew ||
				((socialEquityLog.getUserId() != socialEquityLogModelImpl.getOriginalUserId()) ||
				(socialEquityLog.getAssetEntryId() != socialEquityLogModelImpl.getOriginalAssetEntryId()) ||
				(socialEquityLog.getActionDate() != socialEquityLogModelImpl.getOriginalActionDate()) ||
				!Validator.equals(socialEquityLog.getActionId(),
					socialEquityLogModelImpl.getOriginalActionId()) ||
				(socialEquityLog.getActive() != socialEquityLogModelImpl.getOriginalActive()) ||
				(socialEquityLog.getType() != socialEquityLogModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
				new Object[] {
					new Long(socialEquityLog.getUserId()),
					new Long(socialEquityLog.getAssetEntryId()),
					new Integer(socialEquityLog.getActionDate()),
					
				socialEquityLog.getActionId(),
					Boolean.valueOf(socialEquityLog.getActive()),
					new Integer(socialEquityLog.getType())
				}, socialEquityLog);
		}

		return socialEquityLog;
	}

	protected SocialEquityLog toUnwrappedModel(SocialEquityLog socialEquityLog) {
		if (socialEquityLog instanceof SocialEquityLogImpl) {
			return socialEquityLog;
		}

		SocialEquityLogImpl socialEquityLogImpl = new SocialEquityLogImpl();

		socialEquityLogImpl.setNew(socialEquityLog.isNew());
		socialEquityLogImpl.setPrimaryKey(socialEquityLog.getPrimaryKey());

		socialEquityLogImpl.setEquityLogId(socialEquityLog.getEquityLogId());
		socialEquityLogImpl.setGroupId(socialEquityLog.getGroupId());
		socialEquityLogImpl.setCompanyId(socialEquityLog.getCompanyId());
		socialEquityLogImpl.setUserId(socialEquityLog.getUserId());
		socialEquityLogImpl.setAssetEntryId(socialEquityLog.getAssetEntryId());
		socialEquityLogImpl.setActionId(socialEquityLog.getActionId());
		socialEquityLogImpl.setActionDate(socialEquityLog.getActionDate());
		socialEquityLogImpl.setActive(socialEquityLog.isActive());
		socialEquityLogImpl.setExpiration(socialEquityLog.getExpiration());
		socialEquityLogImpl.setType(socialEquityLog.getType());
		socialEquityLogImpl.setValue(socialEquityLog.getValue());

		return socialEquityLogImpl;
	}

	public SocialEquityLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityLog findByPrimaryKey(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = fetchByPrimaryKey(equityLogId);

		if (socialEquityLog == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
			}

			throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityLogId);
		}

		return socialEquityLog;
	}

	public SocialEquityLog fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialEquityLog fetchByPrimaryKey(long equityLogId)
		throws SystemException {
		SocialEquityLog socialEquityLog = (SocialEquityLog)EntityCacheUtil.getResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityLogImpl.class, equityLogId, this);

		if (socialEquityLog == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
						new Long(equityLogId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquityLog != null) {
					cacheResult(socialEquityLog);
				}

				closeSession(session);
			}
		}

		return socialEquityLog;
	}

	public List<SocialEquityLog> findByAEI_AD_AI_A_T(long assetEntryId,
		int actionDate, String actionId, boolean active, int type)
		throws SystemException {
		return findByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId, active,
			type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findByAEI_AD_AI_A_T(long assetEntryId,
		int actionDate, String actionId, boolean active, int type, int start,
		int end) throws SystemException {
		return findByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId, active,
			type, start, end, null);
	}

	public List<SocialEquityLog> findByAEI_AD_AI_A_T(long assetEntryId,
		int actionDate, String actionId, boolean active, int type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, actionDate, actionId, active, type,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_AEI_AD_AI_A_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(7 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_AEI_AD_AI_A_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialEquityLog findByAEI_AD_AI_A_T_First(long assetEntryId,
		int actionDate, String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_AD_AI_A_T(assetEntryId,
				actionDate, actionId, active, type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog findByAEI_AD_AI_A_T_Last(long assetEntryId,
		int actionDate, String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId,
				active, type);

		List<SocialEquityLog> list = findByAEI_AD_AI_A_T(assetEntryId,
				actionDate, actionId, active, type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog[] findByAEI_AD_AI_A_T_PrevAndNext(long equityLogId,
		long assetEntryId, int actionDate, String actionId, boolean active,
		int type, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_AD_AI_A_T_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionDate, actionId, active, type,
					orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_AD_AI_A_T_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionDate, actionId, active, type,
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

	protected SocialEquityLog getByAEI_AD_AI_A_T_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long assetEntryId, int actionDate,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ASSETENTRYID_2);

		query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONDATE_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIVE_2);

		query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_TYPE_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		qPos.add(actionDate);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialEquityLog> findByAEI_AI_A_T(long assetEntryId,
		String actionId, boolean active, int type) throws SystemException {
		return findByAEI_AI_A_T(assetEntryId, actionId, active, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findByAEI_AI_A_T(long assetEntryId,
		String actionId, boolean active, int type, int start, int end)
		throws SystemException {
		return findByAEI_AI_A_T(assetEntryId, actionId, active, type, start,
			end, null);
	}

	public List<SocialEquityLog> findByAEI_AI_A_T(long assetEntryId,
		String actionId, boolean active, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, actionId, active, type,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_AEI_AI_A_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(6 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_AI_A_T_ASSETENTRYID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_AEI_AI_A_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_AEI_AI_A_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialEquityLog findByAEI_AI_A_T_First(long assetEntryId,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_AI_A_T(assetEntryId, actionId,
				active, type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog findByAEI_AI_A_T_Last(long assetEntryId,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_AI_A_T(assetEntryId, actionId, active, type);

		List<SocialEquityLog> list = findByAEI_AI_A_T(assetEntryId, actionId,
				active, type, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog[] findByAEI_AI_A_T_PrevAndNext(long equityLogId,
		long assetEntryId, String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_AI_A_T_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionId, active, type, orderByComparator,
					true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_AI_A_T_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionId, active, type, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByAEI_AI_A_T_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long assetEntryId, String actionId,
		boolean active, int type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_AI_A_T_ASSETENTRYID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIVE_2);

		query.append(_FINDER_COLUMN_AEI_AI_A_T_TYPE_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active) throws SystemException {
		return findByAEI_T_A(assetEntryId, type, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active, int start, int end) throws SystemException {
		return findByAEI_T_A(assetEntryId, type, active, start, end, null);
	}

	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, type, active,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_AEI_T_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

				query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(type);

				qPos.add(active);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_AEI_T_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialEquityLog findByAEI_T_A_First(long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_T_A(assetEntryId, type, active,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog findByAEI_T_A_Last(long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_T_A(assetEntryId, type, active);

		List<SocialEquityLog> list = findByAEI_T_A(assetEntryId, type, active,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog[] findByAEI_T_A_PrevAndNext(long equityLogId,
		long assetEntryId, int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_T_A_PrevAndNext(session, socialEquityLog,
					assetEntryId, type, active, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_T_A_PrevAndNext(session, socialEquityLog,
					assetEntryId, type, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByAEI_T_A_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

		query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

		query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		qPos.add(type);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialEquityLog> findByU_AD_AI_A_T(long userId, int actionDate,
		String actionId, boolean active, int type) throws SystemException {
		return findByU_AD_AI_A_T(userId, actionDate, actionId, active, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findByU_AD_AI_A_T(long userId, int actionDate,
		String actionId, boolean active, int type, int start, int end)
		throws SystemException {
		return findByU_AD_AI_A_T(userId, actionDate, actionId, active, type,
			start, end, null);
	}

	public List<SocialEquityLog> findByU_AD_AI_A_T(long userId, int actionDate,
		String actionId, boolean active, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, actionDate, actionId, active, type,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_AD_AI_A_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(7 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_USERID_2);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_AD_AI_A_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialEquityLog findByU_AD_AI_A_T_First(long userId, int actionDate,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByU_AD_AI_A_T(userId, actionDate,
				actionId, active, type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog findByU_AD_AI_A_T_Last(long userId, int actionDate,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByU_AD_AI_A_T(userId, actionDate, actionId, active,
				type);

		List<SocialEquityLog> list = findByU_AD_AI_A_T(userId, actionDate,
				actionId, active, type, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog[] findByU_AD_AI_A_T_PrevAndNext(long equityLogId,
		long userId, int actionDate, String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByU_AD_AI_A_T_PrevAndNext(session, socialEquityLog,
					userId, actionDate, actionId, active, type,
					orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByU_AD_AI_A_T_PrevAndNext(session, socialEquityLog,
					userId, actionDate, actionId, active, type,
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

	protected SocialEquityLog getByU_AD_AI_A_T_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long userId, int actionDate,
		String actionId, boolean active, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_U_AD_AI_A_T_USERID_2);

		query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONDATE_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIVE_2);

		query.append(_FINDER_COLUMN_U_AD_AI_A_T_TYPE_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(actionDate);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SocialEquityLog> findByU_AI_A_T(long userId, String actionId,
		boolean active, int type) throws SystemException {
		return findByU_AI_A_T(userId, actionId, active, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findByU_AI_A_T(long userId, String actionId,
		boolean active, int type, int start, int end) throws SystemException {
		return findByU_AI_A_T(userId, actionId, active, type, start, end, null);
	}

	public List<SocialEquityLog> findByU_AI_A_T(long userId, String actionId,
		boolean active, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, actionId, active, type,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_AI_A_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(6 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AI_A_T_USERID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AI_A_T_TYPE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_AI_A_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialEquityLog findByU_AI_A_T_First(long userId, String actionId,
		boolean active, int type, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByU_AI_A_T(userId, actionId, active,
				type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog findByU_AI_A_T_Last(long userId, String actionId,
		boolean active, int type, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByU_AI_A_T(userId, actionId, active, type);

		List<SocialEquityLog> list = findByU_AI_A_T(userId, actionId, active,
				type, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialEquityLog[] findByU_AI_A_T_PrevAndNext(long equityLogId,
		long userId, String actionId, boolean active, int type,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByU_AI_A_T_PrevAndNext(session, socialEquityLog,
					userId, actionId, active, type, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByU_AI_A_T_PrevAndNext(session, socialEquityLog,
					userId, actionId, active, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByU_AI_A_T_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long userId, String actionId,
		boolean active, int type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_U_AI_A_T_USERID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_U_AI_A_T_ACTIVE_2);

		query.append(_FINDER_COLUMN_U_AI_A_T_TYPE_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public SocialEquityLog findByU_AEI_AD_AI_A_T(long userId,
		long assetEntryId, int actionDate, String actionId, boolean active,
		int type) throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = fetchByU_AEI_AD_AI_A_T(userId,
				assetEntryId, actionDate, actionId, active, type);

		if (socialEquityLog == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityLogException(msg.toString());
		}

		return socialEquityLog;
	}

	public SocialEquityLog fetchByU_AEI_AD_AI_A_T(long userId,
		long assetEntryId, int actionDate, String actionId, boolean active,
		int type) throws SystemException {
		return fetchByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type, true);
	}

	public SocialEquityLog fetchByU_AEI_AD_AI_A_T(long userId,
		long assetEntryId, int actionDate, String actionId, boolean active,
		int type, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, assetEntryId, actionDate, actionId, active, type
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);

				query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_USERID_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(assetEntryId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				List<SocialEquityLog> list = q.list();

				result = list;

				SocialEquityLog socialEquityLog = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
						finderArgs, list);
				}
				else {
					socialEquityLog = list.get(0);

					cacheResult(socialEquityLog);

					if ((socialEquityLog.getUserId() != userId) ||
							(socialEquityLog.getAssetEntryId() != assetEntryId) ||
							(socialEquityLog.getActionDate() != actionDate) ||
							(socialEquityLog.getActionId() == null) ||
							!socialEquityLog.getActionId().equals(actionId) ||
							(socialEquityLog.getActive() != active) ||
							(socialEquityLog.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
							finderArgs, socialEquityLog);
					}
				}

				return socialEquityLog;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AD_AI_A_T,
						finderArgs, new ArrayList<SocialEquityLog>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialEquityLog)result;
			}
		}
	}

	public List<SocialEquityLog> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialEquityLog> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialEquityLog> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYLOG);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_SOCIALEQUITYLOG;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquityLog>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		String actionId, boolean active, int type) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_AD_AI_A_T(
				assetEntryId, actionDate, actionId, active, type)) {
			remove(socialEquityLog);
		}
	}

	public void removeByAEI_AI_A_T(long assetEntryId, String actionId,
		boolean active, int type) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_AI_A_T(assetEntryId,
				actionId, active, type)) {
			remove(socialEquityLog);
		}
	}

	public void removeByAEI_T_A(long assetEntryId, int type, boolean active)
		throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_T_A(assetEntryId,
				type, active)) {
			remove(socialEquityLog);
		}
	}

	public void removeByU_AD_AI_A_T(long userId, int actionDate,
		String actionId, boolean active, int type) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByU_AD_AI_A_T(userId,
				actionDate, actionId, active, type)) {
			remove(socialEquityLog);
		}
	}

	public void removeByU_AI_A_T(long userId, String actionId, boolean active,
		int type) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByU_AI_A_T(userId, actionId,
				active, type)) {
			remove(socialEquityLog);
		}
	}

	public void removeByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, String actionId, boolean active, int type)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByU_AEI_AD_AI_A_T(userId,
				assetEntryId, actionDate, actionId, active, type);

		remove(socialEquityLog);
	}

	public void removeAll() throws SystemException {
		for (SocialEquityLog socialEquityLog : findAll()) {
			remove(socialEquityLog);
		}
	}

	public int countByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		String actionId, boolean active, int type) throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, actionDate, actionId, active, type
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_AD_AI_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_AEI_AD_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_AD_AI_A_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByAEI_AI_A_T(long assetEntryId, String actionId,
		boolean active, int type) throws SystemException {
		Object[] finderArgs = new Object[] { assetEntryId, actionId, active, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_AI_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_AI_A_T_ASSETENTRYID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_AEI_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_AEI_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_AI_A_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { assetEntryId, type, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_T_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

				query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(type);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_T_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_AD_AI_A_T(long userId, int actionDate, String actionId,
		boolean active, int type) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, actionDate, actionId, active, type
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AD_AI_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_USERID_2);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AD_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AD_AI_A_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_AI_A_T(long userId, String actionId, boolean active,
		int type) throws SystemException {
		Object[] finderArgs = new Object[] { userId, actionId, active, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AI_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AI_A_T_USERID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AI_A_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, String actionId, boolean active, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, assetEntryId, actionDate, actionId, active, type
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AEI_AD_AI_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);

				query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_USERID_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ASSETENTRYID_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONDATE_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIVE_2);

				query.append(_FINDER_COLUMN_U_AEI_AD_AI_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(assetEntryId);

				qPos.add(actionDate);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AEI_AD_AI_A_T,
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYLOG);

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
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityLog")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityLog>> listenersList = new ArrayList<ModelListener<SocialEquityLog>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityLog>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYLOG = "SELECT socialEquityLog FROM SocialEquityLog socialEquityLog";
	private static final String _SQL_SELECT_SOCIALEQUITYLOG_WHERE = "SELECT socialEquityLog FROM SocialEquityLog socialEquityLog WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYLOG = "SELECT COUNT(socialEquityLog) FROM SocialEquityLog socialEquityLog";
	private static final String _SQL_COUNT_SOCIALEQUITYLOG_WHERE = "SELECT COUNT(socialEquityLog) FROM SocialEquityLog socialEquityLog WHERE ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AD_AI_A_T_TYPE_2 = "socialEquityLog.type = ?";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AI_A_T_TYPE_2 = "socialEquityLog.type = ?";
	private static final String _FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_T_A_TYPE_2 = "socialEquityLog.type = ? AND ";
	private static final String _FINDER_COLUMN_AEI_T_A_ACTIVE_2 = "socialEquityLog.active = ?";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AD_AI_A_T_TYPE_2 = "socialEquityLog.type = ?";
	private static final String _FINDER_COLUMN_U_AI_A_T_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AI_A_T_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AI_A_T_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AI_A_T_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AI_A_T_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AI_A_T_TYPE_2 = "socialEquityLog.type = ?";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AD_AI_A_T_TYPE_2 = "socialEquityLog.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityLog.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityLog exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityLog exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialEquityLogPersistenceImpl.class);
}