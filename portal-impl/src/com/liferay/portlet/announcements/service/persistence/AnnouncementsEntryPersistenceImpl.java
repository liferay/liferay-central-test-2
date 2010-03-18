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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntryPersistence
 * @see       AnnouncementsEntryUtil
 * @generated
 */
public class AnnouncementsEntryPersistenceImpl extends BasePersistenceImpl<AnnouncementsEntry>
	implements AnnouncementsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_A = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsEntry announcementsEntry) {
		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);
	}

	public void cacheResult(List<AnnouncementsEntry> announcementsEntries) {
		for (AnnouncementsEntry announcementsEntry : announcementsEntries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKey(), this) == null) {
				cacheResult(announcementsEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AnnouncementsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(AnnouncementsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AnnouncementsEntry create(long entryId) {
		AnnouncementsEntry announcementsEntry = new AnnouncementsEntryImpl();

		announcementsEntry.setNew(true);
		announcementsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		announcementsEntry.setUuid(uuid);

		return announcementsEntry;
	}

	public AnnouncementsEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AnnouncementsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
					new Long(entryId));

			if (announcementsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryId);
			}

			return remove(announcementsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsEntry remove(AnnouncementsEntry announcementsEntry)
		throws SystemException {
		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			listener.onBeforeRemove(announcementsEntry);
		}

		announcementsEntry = removeImpl(announcementsEntry);

		for (ModelListener<AnnouncementsEntry> listener : listeners) {
			listener.onAfterRemove(announcementsEntry);
		}

		return announcementsEntry;
	}

	protected AnnouncementsEntry removeImpl(
		AnnouncementsEntry announcementsEntry) throws SystemException {
		announcementsEntry = toUnwrappedModel(announcementsEntry);

		Session session = null;

		try {
			session = openSession();

			if (announcementsEntry.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsEntryImpl.class,
						announcementsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey());

		return announcementsEntry;
	}

	public AnnouncementsEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry,
		boolean merge) throws SystemException {
		announcementsEntry = toUnwrappedModel(announcementsEntry);

		if (Validator.isNull(announcementsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			announcementsEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsEntry, merge);

			announcementsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsEntryImpl.class, announcementsEntry.getPrimaryKey(),
			announcementsEntry);

		return announcementsEntry;
	}

	protected AnnouncementsEntry toUnwrappedModel(
		AnnouncementsEntry announcementsEntry) {
		if (announcementsEntry instanceof AnnouncementsEntryImpl) {
			return announcementsEntry;
		}

		AnnouncementsEntryImpl announcementsEntryImpl = new AnnouncementsEntryImpl();

		announcementsEntryImpl.setNew(announcementsEntry.isNew());
		announcementsEntryImpl.setPrimaryKey(announcementsEntry.getPrimaryKey());

		announcementsEntryImpl.setUuid(announcementsEntry.getUuid());
		announcementsEntryImpl.setEntryId(announcementsEntry.getEntryId());
		announcementsEntryImpl.setCompanyId(announcementsEntry.getCompanyId());
		announcementsEntryImpl.setUserId(announcementsEntry.getUserId());
		announcementsEntryImpl.setUserName(announcementsEntry.getUserName());
		announcementsEntryImpl.setCreateDate(announcementsEntry.getCreateDate());
		announcementsEntryImpl.setModifiedDate(announcementsEntry.getModifiedDate());
		announcementsEntryImpl.setClassNameId(announcementsEntry.getClassNameId());
		announcementsEntryImpl.setClassPK(announcementsEntry.getClassPK());
		announcementsEntryImpl.setTitle(announcementsEntry.getTitle());
		announcementsEntryImpl.setContent(announcementsEntry.getContent());
		announcementsEntryImpl.setUrl(announcementsEntry.getUrl());
		announcementsEntryImpl.setType(announcementsEntry.getType());
		announcementsEntryImpl.setDisplayDate(announcementsEntry.getDisplayDate());
		announcementsEntryImpl.setExpirationDate(announcementsEntry.getExpirationDate());
		announcementsEntryImpl.setPriority(announcementsEntry.getPriority());
		announcementsEntryImpl.setAlert(announcementsEntry.isAlert());

		return announcementsEntryImpl;
	}

	public AnnouncementsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = fetchByPrimaryKey(entryId);

		if (announcementsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return announcementsEntry;
	}

	public AnnouncementsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)EntityCacheUtil.getResult(AnnouncementsEntryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsEntryImpl.class, entryId, this);

		if (announcementsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsEntry = (AnnouncementsEntry)session.get(AnnouncementsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsEntry != null) {
					cacheResult(announcementsEntry);
				}

				closeSession(session);
			}
		}

		return announcementsEntry;
	}

	public List<AnnouncementsEntry> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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

				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<AnnouncementsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<AnnouncementsEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByUuid_PrevAndNext(long entryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByUuid(uuid);

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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

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

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<AnnouncementsEntry> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsEntry> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByUserId_PrevAndNext(long entryId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByUserId(userId);

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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<AnnouncementsEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
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

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<AnnouncementsEntry> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByC_C(classNameId, classPK);

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

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, start, end, null);
	}

	public List<AnnouncementsEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_A,
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
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				list = (List<AnnouncementsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsEntry findByC_C_A_First(long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", alert=");
			msg.append(alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry findByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_C_A(classNameId, classPK, alert);

		List<AnnouncementsEntry> list = findByC_C_A(classNameId, classPK,
				alert, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", alert=");
			msg.append(alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsEntry[] findByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AnnouncementsEntry announcementsEntry = findByPrimaryKey(entryId);

		int count = countByC_C_A(classNameId, classPK, alert);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(alert);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, announcementsEntry);

			AnnouncementsEntry[] array = new AnnouncementsEntryImpl[3];

			array[0] = (AnnouncementsEntry)objArray[0];
			array[1] = (AnnouncementsEntry)objArray[1];
			array[2] = (AnnouncementsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsEntry> list = (List<AnnouncementsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ANNOUNCEMENTSENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ANNOUNCEMENTSENTRY.concat(AnnouncementsEntryModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUuid(uuid)) {
			remove(announcementsEntry);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByUserId(userId)) {
			remove(announcementsEntry);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C(classNameId,
				classPK)) {
			remove(announcementsEntry);
		}
	}

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findByC_C_A(classNameId,
				classPK, alert)) {
			remove(announcementsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsEntry announcementsEntry : findAll()) {
			remove(announcementsEntry);
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

				query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

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

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_A_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_A_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_A_ALERT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(alert);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_A,
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

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSENTRY);

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsEntry>> listenersList = new ArrayList<ModelListener<AnnouncementsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	private static final String _SQL_SELECT_ANNOUNCEMENTSENTRY = "SELECT announcementsEntry FROM AnnouncementsEntry announcementsEntry";
	private static final String _SQL_SELECT_ANNOUNCEMENTSENTRY_WHERE = "SELECT announcementsEntry FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSENTRY = "SELECT COUNT(announcementsEntry) FROM AnnouncementsEntry announcementsEntry";
	private static final String _SQL_COUNT_ANNOUNCEMENTSENTRY_WHERE = "SELECT COUNT(announcementsEntry) FROM AnnouncementsEntry announcementsEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "announcementsEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "announcementsEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(announcementsEntry.uuid IS NULL OR announcementsEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "announcementsEntry.userId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "announcementsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "announcementsEntry.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_A_CLASSNAMEID_2 = "announcementsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_A_CLASSPK_2 = "announcementsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_A_ALERT_2 = "announcementsEntry.alert = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsEntryPersistenceImpl.class);
}