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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.impl.MBMailingListImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBMailingListPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListPersistence
 * @see       MBMailingListUtil
 * @generated
 */
public class MBMailingListPersistenceImpl extends BasePersistenceImpl<MBMailingList>
	implements MBMailingListPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBMailingListImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByActive",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByActive",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(MBMailingList mbMailingList) {
		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingList.getUuid(), new Long(mbMailingList.getGroupId())
			}, mbMailingList);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
			new Object[] {
				new Long(mbMailingList.getGroupId()),
				new Long(mbMailingList.getCategoryId())
			}, mbMailingList);
	}

	public void cacheResult(List<MBMailingList> mbMailingLists) {
		for (MBMailingList mbMailingList : mbMailingLists) {
			if (EntityCacheUtil.getResult(
						MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
						MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
						this) == null) {
				cacheResult(mbMailingList);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBMailingListImpl.class.getName());
		EntityCacheUtil.clearCache(MBMailingListImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBMailingList create(long mailingListId) {
		MBMailingList mbMailingList = new MBMailingListImpl();

		mbMailingList.setNew(true);
		mbMailingList.setPrimaryKey(mailingListId);

		String uuid = PortalUUIDUtil.generate();

		mbMailingList.setUuid(uuid);

		return mbMailingList;
	}

	public MBMailingList remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBMailingList remove(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMailingList mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
					new Long(mailingListId));

			if (mbMailingList == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + mailingListId);
				}

				throw new NoSuchMailingListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					mailingListId);
			}

			return remove(mbMailingList);
		}
		catch (NoSuchMailingListException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMailingList remove(MBMailingList mbMailingList)
		throws SystemException {
		for (ModelListener<MBMailingList> listener : listeners) {
			listener.onBeforeRemove(mbMailingList);
		}

		mbMailingList = removeImpl(mbMailingList);

		for (ModelListener<MBMailingList> listener : listeners) {
			listener.onAfterRemove(mbMailingList);
		}

		return mbMailingList;
	}

	protected MBMailingList removeImpl(MBMailingList mbMailingList)
		throws SystemException {
		mbMailingList = toUnwrappedModel(mbMailingList);

		Session session = null;

		try {
			session = openSession();

			if (mbMailingList.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBMailingListImpl.class,
						mbMailingList.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbMailingList);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMailingListModelImpl mbMailingListModelImpl = (MBMailingListModelImpl)mbMailingList;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbMailingListModelImpl.getOriginalUuid(),
				new Long(mbMailingListModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C,
			new Object[] {
				new Long(mbMailingListModelImpl.getOriginalGroupId()),
				new Long(mbMailingListModelImpl.getOriginalCategoryId())
			});

		EntityCacheUtil.removeResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey());

		return mbMailingList;
	}

	public MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge) throws SystemException {
		mbMailingList = toUnwrappedModel(mbMailingList);

		boolean isNew = mbMailingList.isNew();

		MBMailingListModelImpl mbMailingListModelImpl = (MBMailingListModelImpl)mbMailingList;

		if (Validator.isNull(mbMailingList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMailingList.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMailingList, merge);

			mbMailingList.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
			MBMailingListImpl.class, mbMailingList.getPrimaryKey(),
			mbMailingList);

		if (!isNew &&
				(!Validator.equals(mbMailingList.getUuid(),
					mbMailingListModelImpl.getOriginalUuid()) ||
				(mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMailingListModelImpl.getOriginalUuid(),
					new Long(mbMailingListModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(mbMailingList.getUuid(),
					mbMailingListModelImpl.getOriginalUuid()) ||
				(mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbMailingList.getUuid(),
					new Long(mbMailingList.getGroupId())
				}, mbMailingList);
		}

		if (!isNew &&
				((mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()) ||
				(mbMailingList.getCategoryId() != mbMailingListModelImpl.getOriginalCategoryId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C,
				new Object[] {
					new Long(mbMailingListModelImpl.getOriginalGroupId()),
					new Long(mbMailingListModelImpl.getOriginalCategoryId())
				});
		}

		if (isNew ||
				((mbMailingList.getGroupId() != mbMailingListModelImpl.getOriginalGroupId()) ||
				(mbMailingList.getCategoryId() != mbMailingListModelImpl.getOriginalCategoryId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
				new Object[] {
					new Long(mbMailingList.getGroupId()),
					new Long(mbMailingList.getCategoryId())
				}, mbMailingList);
		}

		return mbMailingList;
	}

	protected MBMailingList toUnwrappedModel(MBMailingList mbMailingList) {
		if (mbMailingList instanceof MBMailingListImpl) {
			return mbMailingList;
		}

		MBMailingListImpl mbMailingListImpl = new MBMailingListImpl();

		mbMailingListImpl.setNew(mbMailingList.isNew());
		mbMailingListImpl.setPrimaryKey(mbMailingList.getPrimaryKey());

		mbMailingListImpl.setUuid(mbMailingList.getUuid());
		mbMailingListImpl.setMailingListId(mbMailingList.getMailingListId());
		mbMailingListImpl.setGroupId(mbMailingList.getGroupId());
		mbMailingListImpl.setCompanyId(mbMailingList.getCompanyId());
		mbMailingListImpl.setUserId(mbMailingList.getUserId());
		mbMailingListImpl.setUserName(mbMailingList.getUserName());
		mbMailingListImpl.setCreateDate(mbMailingList.getCreateDate());
		mbMailingListImpl.setModifiedDate(mbMailingList.getModifiedDate());
		mbMailingListImpl.setCategoryId(mbMailingList.getCategoryId());
		mbMailingListImpl.setEmailAddress(mbMailingList.getEmailAddress());
		mbMailingListImpl.setInProtocol(mbMailingList.getInProtocol());
		mbMailingListImpl.setInServerName(mbMailingList.getInServerName());
		mbMailingListImpl.setInServerPort(mbMailingList.getInServerPort());
		mbMailingListImpl.setInUseSSL(mbMailingList.isInUseSSL());
		mbMailingListImpl.setInUserName(mbMailingList.getInUserName());
		mbMailingListImpl.setInPassword(mbMailingList.getInPassword());
		mbMailingListImpl.setInReadInterval(mbMailingList.getInReadInterval());
		mbMailingListImpl.setOutEmailAddress(mbMailingList.getOutEmailAddress());
		mbMailingListImpl.setOutCustom(mbMailingList.isOutCustom());
		mbMailingListImpl.setOutServerName(mbMailingList.getOutServerName());
		mbMailingListImpl.setOutServerPort(mbMailingList.getOutServerPort());
		mbMailingListImpl.setOutUseSSL(mbMailingList.isOutUseSSL());
		mbMailingListImpl.setOutUserName(mbMailingList.getOutUserName());
		mbMailingListImpl.setOutPassword(mbMailingList.getOutPassword());
		mbMailingListImpl.setActive(mbMailingList.isActive());

		return mbMailingListImpl;
	}

	public MBMailingList findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBMailingList findByPrimaryKey(long mailingListId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByPrimaryKey(mailingListId);

		if (mbMailingList == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + mailingListId);
			}

			throw new NoSuchMailingListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				mailingListId);
		}

		return mbMailingList;
	}

	public MBMailingList fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBMailingList fetchByPrimaryKey(long mailingListId)
		throws SystemException {
		MBMailingList mbMailingList = (MBMailingList)EntityCacheUtil.getResult(MBMailingListModelImpl.ENTITY_CACHE_ENABLED,
				MBMailingListImpl.class, mailingListId, this);

		if (mbMailingList == null) {
			Session session = null;

			try {
				session = openSession();

				mbMailingList = (MBMailingList)session.get(MBMailingListImpl.class,
						new Long(mailingListId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbMailingList != null) {
					cacheResult(mbMailingList);
				}

				closeSession(session);
			}
		}

		return mbMailingList;
	}

	public List<MBMailingList> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMailingList> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<MBMailingList> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMailingList findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		List<MBMailingList> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		int count = countByUuid(uuid);

		List<MBMailingList> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList[] findByUuid_PrevAndNext(long mailingListId,
		String uuid, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		int count = countByUuid(uuid);

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

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMailingList);

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = (MBMailingList)objArray[0];
			array[1] = (MBMailingList)objArray[1];
			array[2] = (MBMailingList)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMailingList findByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByUUID_G(uuid, groupId);

		if (mbMailingList == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	public MBMailingList fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public MBMailingList fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getUuid() == null) ||
							!mbMailingList.getUuid().equals(uuid) ||
							(mbMailingList.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<MBMailingList>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBMailingList)result;
			}
		}
	}

	public List<MBMailingList> findByActive(boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBMailingList> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<MBMailingList> findByActive(boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
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

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBMailingList findByActive_First(boolean active,
		OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		List<MBMailingList> list = findByActive(active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		int count = countByActive(active);

		List<MBMailingList> list = findByActive(active, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMailingListException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMailingList[] findByActive_PrevAndNext(long mailingListId,
		boolean active, OrderByComparator obc)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByPrimaryKey(mailingListId);

		int count = countByActive(active);

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

			query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

			query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMailingList);

			MBMailingList[] array = new MBMailingListImpl[3];

			array[0] = (MBMailingList)objArray[0];
			array[1] = (MBMailingList)objArray[1];
			array[2] = (MBMailingList)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMailingList findByG_C(long groupId, long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = fetchByG_C(groupId, categoryId);

		if (mbMailingList == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMailingListException(msg.toString());
		}

		return mbMailingList;
	}

	public MBMailingList fetchByG_C(long groupId, long categoryId)
		throws SystemException {
		return fetchByG_C(groupId, categoryId, true);
	}

	public MBMailingList fetchByG_C(long groupId, long categoryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_MBMAILINGLIST_WHERE);

				query.append(_FINDER_COLUMN_G_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				List<MBMailingList> list = q.list();

				result = list;

				MBMailingList mbMailingList = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
						finderArgs, list);
				}
				else {
					mbMailingList = list.get(0);

					cacheResult(mbMailingList);

					if ((mbMailingList.getGroupId() != groupId) ||
							(mbMailingList.getCategoryId() != categoryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
							finderArgs, mbMailingList);
					}
				}

				return mbMailingList;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C,
						finderArgs, new ArrayList<MBMailingList>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBMailingList)result;
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

	public List<MBMailingList> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMailingList> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMailingList> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBMailingList> list = (List<MBMailingList>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_MBMAILINGLIST);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_MBMAILINGLIST;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMailingList>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBMailingList>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (MBMailingList mbMailingList : findByUuid(uuid)) {
			remove(mbMailingList);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByUUID_G(uuid, groupId);

		remove(mbMailingList);
	}

	public void removeByActive(boolean active) throws SystemException {
		for (MBMailingList mbMailingList : findByActive(active)) {
			remove(mbMailingList);
		}
	}

	public void removeByG_C(long groupId, long categoryId)
		throws NoSuchMailingListException, SystemException {
		MBMailingList mbMailingList = findByG_C(groupId, categoryId);

		remove(mbMailingList);
	}

	public void removeAll() throws SystemException {
		for (MBMailingList mbMailingList : findAll()) {
			remove(mbMailingList);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIVE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

				query.append(_FINDER_COLUMN_ACTIVE_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIVE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_MBMAILINGLIST_WHERE);

				query.append(_FINDER_COLUMN_G_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_MBMAILINGLIST);

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMailingList")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMailingList>> listenersList = new ArrayList<ModelListener<MBMailingList>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMailingList>)Class.forName(
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
	private static final String _SQL_SELECT_MBMAILINGLIST = "SELECT mbMailingList FROM MBMailingList mbMailingList";
	private static final String _SQL_SELECT_MBMAILINGLIST_WHERE = "SELECT mbMailingList FROM MBMailingList mbMailingList WHERE ";
	private static final String _SQL_COUNT_MBMAILINGLIST = "SELECT COUNT(mbMailingList) FROM MBMailingList mbMailingList";
	private static final String _SQL_COUNT_MBMAILINGLIST_WHERE = "SELECT COUNT(mbMailingList) FROM MBMailingList mbMailingList WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbMailingList.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbMailingList.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbMailingList.uuid IS NULL OR mbMailingList.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbMailingList.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbMailingList.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbMailingList.uuid IS NULL OR mbMailingList.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbMailingList.groupId = ?";
	private static final String _FINDER_COLUMN_ACTIVE_ACTIVE_2 = "mbMailingList.active = ?";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "mbMailingList.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "mbMailingList.categoryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbMailingList.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBMailingList exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBMailingList exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(MBMailingListPersistenceImpl.class);
}