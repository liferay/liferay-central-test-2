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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBMessageFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagPersistence
 * @see       MBMessageFlagUtil
 * @generated
 */
public class MBMessageFlagPersistenceImpl extends BasePersistenceImpl<MBMessageFlag>
	implements MBMessageFlagPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBMessageFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_THREADID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_MESSAGEID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByMessageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_MESSAGEID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByMessageId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_MESSAGEID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByMessageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByT_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByT_F",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByM_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByM_F",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByM_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_T_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_U_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_T_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_T_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_U_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_M_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_M_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(MBMessageFlag mbMessageFlag) {
		EntityCacheUtil.putResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
			mbMessageFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
			new Object[] {
				new Long(mbMessageFlag.getUserId()),
				new Long(mbMessageFlag.getMessageId()),
				new Integer(mbMessageFlag.getFlag())
			}, mbMessageFlag);
	}

	public void cacheResult(List<MBMessageFlag> mbMessageFlags) {
		for (MBMessageFlag mbMessageFlag : mbMessageFlags) {
			if (EntityCacheUtil.getResult(
						MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
						this) == null) {
				cacheResult(mbMessageFlag);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBMessageFlagImpl.class.getName());
		EntityCacheUtil.clearCache(MBMessageFlagImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBMessageFlag create(long messageFlagId) {
		MBMessageFlag mbMessageFlag = new MBMessageFlagImpl();

		mbMessageFlag.setNew(true);
		mbMessageFlag.setPrimaryKey(messageFlagId);

		return mbMessageFlag;
	}

	public MBMessageFlag remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBMessageFlag remove(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessageFlag mbMessageFlag = (MBMessageFlag)session.get(MBMessageFlagImpl.class,
					new Long(messageFlagId));

			if (mbMessageFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageFlagId);
				}

				throw new NoSuchMessageFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					messageFlagId);
			}

			return remove(mbMessageFlag);
		}
		catch (NoSuchMessageFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessageFlag remove(MBMessageFlag mbMessageFlag)
		throws SystemException {
		for (ModelListener<MBMessageFlag> listener : listeners) {
			listener.onBeforeRemove(mbMessageFlag);
		}

		mbMessageFlag = removeImpl(mbMessageFlag);

		for (ModelListener<MBMessageFlag> listener : listeners) {
			listener.onAfterRemove(mbMessageFlag);
		}

		return mbMessageFlag;
	}

	protected MBMessageFlag removeImpl(MBMessageFlag mbMessageFlag)
		throws SystemException {
		mbMessageFlag = toUnwrappedModel(mbMessageFlag);

		Session session = null;

		try {
			session = openSession();

			if (mbMessageFlag.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBMessageFlagImpl.class,
						mbMessageFlag.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbMessageFlag);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMessageFlagModelImpl mbMessageFlagModelImpl = (MBMessageFlagModelImpl)mbMessageFlag;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
			new Object[] {
				new Long(mbMessageFlagModelImpl.getOriginalUserId()),
				new Long(mbMessageFlagModelImpl.getOriginalMessageId()),
				new Integer(mbMessageFlagModelImpl.getOriginalFlag())
			});

		EntityCacheUtil.removeResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey());

		return mbMessageFlag;
	}

	public MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws SystemException {
		mbMessageFlag = toUnwrappedModel(mbMessageFlag);

		boolean isNew = mbMessageFlag.isNew();

		MBMessageFlagModelImpl mbMessageFlagModelImpl = (MBMessageFlagModelImpl)mbMessageFlag;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMessageFlag, merge);

			mbMessageFlag.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
			mbMessageFlag);

		if (!isNew &&
				((mbMessageFlag.getUserId() != mbMessageFlagModelImpl.getOriginalUserId()) ||
				(mbMessageFlag.getMessageId() != mbMessageFlagModelImpl.getOriginalMessageId()) ||
				(mbMessageFlag.getFlag() != mbMessageFlagModelImpl.getOriginalFlag()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
				new Object[] {
					new Long(mbMessageFlagModelImpl.getOriginalUserId()),
					new Long(mbMessageFlagModelImpl.getOriginalMessageId()),
					new Integer(mbMessageFlagModelImpl.getOriginalFlag())
				});
		}

		if (isNew ||
				((mbMessageFlag.getUserId() != mbMessageFlagModelImpl.getOriginalUserId()) ||
				(mbMessageFlag.getMessageId() != mbMessageFlagModelImpl.getOriginalMessageId()) ||
				(mbMessageFlag.getFlag() != mbMessageFlagModelImpl.getOriginalFlag()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
				new Object[] {
					new Long(mbMessageFlag.getUserId()),
					new Long(mbMessageFlag.getMessageId()),
					new Integer(mbMessageFlag.getFlag())
				}, mbMessageFlag);
		}

		return mbMessageFlag;
	}

	protected MBMessageFlag toUnwrappedModel(MBMessageFlag mbMessageFlag) {
		if (mbMessageFlag instanceof MBMessageFlagImpl) {
			return mbMessageFlag;
		}

		MBMessageFlagImpl mbMessageFlagImpl = new MBMessageFlagImpl();

		mbMessageFlagImpl.setNew(mbMessageFlag.isNew());
		mbMessageFlagImpl.setPrimaryKey(mbMessageFlag.getPrimaryKey());

		mbMessageFlagImpl.setMessageFlagId(mbMessageFlag.getMessageFlagId());
		mbMessageFlagImpl.setUserId(mbMessageFlag.getUserId());
		mbMessageFlagImpl.setModifiedDate(mbMessageFlag.getModifiedDate());
		mbMessageFlagImpl.setThreadId(mbMessageFlag.getThreadId());
		mbMessageFlagImpl.setMessageId(mbMessageFlag.getMessageId());
		mbMessageFlagImpl.setFlag(mbMessageFlag.getFlag());

		return mbMessageFlagImpl;
	}

	public MBMessageFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBMessageFlag findByPrimaryKey(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByPrimaryKey(messageFlagId);

		if (mbMessageFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageFlagId);
			}

			throw new NoSuchMessageFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				messageFlagId);
		}

		return mbMessageFlag;
	}

	public MBMessageFlag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBMessageFlag fetchByPrimaryKey(long messageFlagId)
		throws SystemException {
		MBMessageFlag mbMessageFlag = (MBMessageFlag)EntityCacheUtil.getResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBMessageFlagImpl.class, messageFlagId, this);

		if (mbMessageFlag == null) {
			Session session = null;

			try {
				session = openSession();

				mbMessageFlag = (MBMessageFlag)session.get(MBMessageFlagImpl.class,
						new Long(messageFlagId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbMessageFlag != null) {
					cacheResult(mbMessageFlag);
				}

				closeSession(session);
			}
		}

		return mbMessageFlag;
	}

	public List<MBMessageFlag> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

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
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBMessageFlag> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByUserId(userId);

		List<MBMessageFlag> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByUserId_PrevAndNext(long messageFlagId,
		long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByThreadId(long threadId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_THREADID_THREADID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	public List<MBMessageFlag> findByThreadId(long threadId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_THREADID,
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

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_THREADID_THREADID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_THREADID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByThreadId_First(long threadId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByThreadId(threadId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByThreadId_Last(long threadId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByThreadId(threadId);

		List<MBMessageFlag> list = findByThreadId(threadId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByThreadId_PrevAndNext(long messageFlagId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByThreadId(threadId);

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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByMessageId(long messageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(messageId) };

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_MESSAGEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_MESSAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end) throws SystemException {
		return findByMessageId(messageId, start, end, null);
	}

	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(messageId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_MESSAGEID,
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

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_MESSAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByMessageId_First(long messageId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByMessageId(messageId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByMessageId_Last(long messageId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByMessageId(messageId);

		List<MBMessageFlag> list = findByMessageId(messageId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByMessageId_PrevAndNext(long messageFlagId,
		long messageId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByMessageId(messageId);

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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(messageId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByT_F(long threadId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId), new Integer(flag) };

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_T_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(flag);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByT_F(long threadId, int flag, int start,
		int end) throws SystemException {
		return findByT_F(threadId, flag, start, end, null);
	}

	public List<MBMessageFlag> findByT_F(long threadId, int flag, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Integer(flag),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_T_F_FLAG_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByT_F_First(long threadId, int flag,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByT_F(threadId, flag, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByT_F_Last(long threadId, int flag,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByT_F(threadId, flag);

		List<MBMessageFlag> list = findByT_F(threadId, flag, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByT_F_PrevAndNext(long messageFlagId,
		long threadId, int flag, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByT_F(threadId, flag);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_T_F_FLAG_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			qPos.add(flag);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByM_F(long messageId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(messageId), new Integer(flag)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_M_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

				query.append(_FINDER_COLUMN_M_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				qPos.add(flag);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_M_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByM_F(long messageId, int flag, int start,
		int end) throws SystemException {
		return findByM_F(messageId, flag, start, end, null);
	}

	public List<MBMessageFlag> findByM_F(long messageId, int flag, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(messageId), new Integer(flag),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_M_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

				query.append(_FINDER_COLUMN_M_F_FLAG_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_M_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByM_F_First(long messageId, int flag,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByM_F(messageId, flag, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByM_F_Last(long messageId, int flag,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByM_F(messageId, flag);

		List<MBMessageFlag> list = findByM_F(messageId, flag, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByM_F_PrevAndNext(long messageFlagId,
		long messageId, int flag, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByM_F(messageId, flag);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

			query.append(_FINDER_COLUMN_M_F_FLAG_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(messageId);

			qPos.add(flag);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(threadId), new Integer(flag)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_T_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_T_F_USERID_2);

				query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				qPos.add(flag);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_T_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMessageFlag> findByU_T_F(long userId, long threadId,
		int flag, int start, int end) throws SystemException {
		return findByU_T_F(userId, threadId, flag, start, end, null);
	}

	public List<MBMessageFlag> findByU_T_F(long userId, long threadId,
		int flag, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(threadId), new Integer(flag),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_U_T_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_T_F_USERID_2);

				query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_U_T_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMessageFlag findByU_T_F_First(long userId, long threadId,
		int flag, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByU_T_F(userId, threadId, flag, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByU_T_F_Last(long userId, long threadId, int flag,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByU_T_F(userId, threadId, flag);

		List<MBMessageFlag> list = findByU_T_F(userId, threadId, flag,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByU_T_F_PrevAndNext(long messageFlagId,
		long userId, long threadId, int flag, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByU_T_F(userId, threadId, flag);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_F_USERID_2);

			query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(threadId);

			qPos.add(flag);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessageFlag findByU_M_F(long userId, long messageId, int flag)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByU_M_F(userId, messageId, flag);

		if (mbMessageFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageFlagException(msg.toString());
		}

		return mbMessageFlag;
	}

	public MBMessageFlag fetchByU_M_F(long userId, long messageId, int flag)
		throws SystemException {
		return fetchByU_M_F(userId, messageId, flag, true);
	}

	public MBMessageFlag fetchByU_M_F(long userId, long messageId, int flag,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(messageId), new Integer(flag)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_M_F,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_M_F_USERID_2);

				query.append(_FINDER_COLUMN_U_M_F_MESSAGEID_2);

				query.append(_FINDER_COLUMN_U_M_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

				qPos.add(flag);

				List<MBMessageFlag> list = q.list();

				result = list;

				MBMessageFlag mbMessageFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
						finderArgs, list);
				}
				else {
					mbMessageFlag = list.get(0);

					cacheResult(mbMessageFlag);

					if ((mbMessageFlag.getUserId() != userId) ||
							(mbMessageFlag.getMessageId() != messageId) ||
							(mbMessageFlag.getFlag() != flag)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
							finderArgs, mbMessageFlag);
					}
				}

				return mbMessageFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
						finderArgs, new ArrayList<MBMessageFlag>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBMessageFlag)result;
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

	public List<MBMessageFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMessageFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMessageFlag> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_MBMESSAGEFLAG);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_MBMESSAGEFLAG;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMessageFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByUserId(userId)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByThreadId(long threadId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByThreadId(threadId)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByMessageId(long messageId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByMessageId(messageId)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByT_F(long threadId, int flag) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByT_F(threadId, flag)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByM_F(long messageId, int flag) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByM_F(messageId, flag)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByU_T_F(userId, threadId, flag)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByU_M_F(long userId, long messageId, int flag)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByU_M_F(userId, messageId, flag);

		remove(mbMessageFlag);
	}

	public void removeAll() throws SystemException {
		for (MBMessageFlag mbMessageFlag : findAll()) {
			remove(mbMessageFlag);
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

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

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

	public int countByThreadId(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_THREADID_THREADID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByMessageId(long messageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(messageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MESSAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MESSAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_F(long threadId, int flag) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(threadId), new Integer(flag) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_T_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByM_F(long messageId, int flag) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(messageId), new Integer(flag)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

				query.append(_FINDER_COLUMN_M_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(threadId), new Integer(flag)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_T_F_USERID_2);

				query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

				query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_M_F(long userId, long messageId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(messageId), new Integer(flag)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_M_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_M_F_USERID_2);

				query.append(_FINDER_COLUMN_U_M_F_MESSAGEID_2);

				query.append(_FINDER_COLUMN_U_M_F_FLAG_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_M_F,
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

				Query q = session.createQuery(_SQL_COUNT_MBMESSAGEFLAG);

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessageFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMessageFlag>> listenersList = new ArrayList<ModelListener<MBMessageFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMessageFlag>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence mbThreadPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBMESSAGEFLAG = "SELECT mbMessageFlag FROM MBMessageFlag mbMessageFlag";
	private static final String _SQL_SELECT_MBMESSAGEFLAG_WHERE = "SELECT mbMessageFlag FROM MBMessageFlag mbMessageFlag WHERE ";
	private static final String _SQL_COUNT_MBMESSAGEFLAG = "SELECT COUNT(mbMessageFlag) FROM MBMessageFlag mbMessageFlag";
	private static final String _SQL_COUNT_MBMESSAGEFLAG_WHERE = "SELECT COUNT(mbMessageFlag) FROM MBMessageFlag mbMessageFlag WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbMessageFlag.userId = ?";
	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbMessageFlag.threadId = ?";
	private static final String _FINDER_COLUMN_MESSAGEID_MESSAGEID_2 = "mbMessageFlag.messageId = ?";
	private static final String _FINDER_COLUMN_T_F_THREADID_2 = "mbMessageFlag.threadId = ? AND ";
	private static final String _FINDER_COLUMN_T_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_M_F_MESSAGEID_2 = "mbMessageFlag.messageId = ? AND ";
	private static final String _FINDER_COLUMN_M_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_U_T_F_USERID_2 = "mbMessageFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_F_THREADID_2 = "mbMessageFlag.threadId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_U_M_F_USERID_2 = "mbMessageFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_M_F_MESSAGEID_2 = "mbMessageFlag.messageId = ? AND ";
	private static final String _FINDER_COLUMN_U_M_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbMessageFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBMessageFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBMessageFlag exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(MBMessageFlagPersistenceImpl.class);
}