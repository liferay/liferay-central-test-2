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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="AssetTagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPersistence
 * @see       AssetTagUtil
 * @generated
 */
public class AssetTagPersistenceImpl extends BasePersistenceImpl<AssetTag>
	implements AssetTagPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetTagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(AssetTag assetTag) {
		EntityCacheUtil.putResult(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagImpl.class, assetTag.getPrimaryKey(), assetTag);
	}

	public void cacheResult(List<AssetTag> assetTags) {
		for (AssetTag assetTag : assetTags) {
			if (EntityCacheUtil.getResult(
						AssetTagModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagImpl.class, assetTag.getPrimaryKey(), this) == null) {
				cacheResult(assetTag);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetTagImpl.class.getName());
		EntityCacheUtil.clearCache(AssetTagImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetTag create(long tagId) {
		AssetTag assetTag = new AssetTagImpl();

		assetTag.setNew(true);
		assetTag.setPrimaryKey(tagId);

		return assetTag;
	}

	public AssetTag remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetTag remove(long tagId)
		throws NoSuchTagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetTag assetTag = (AssetTag)session.get(AssetTagImpl.class,
					new Long(tagId));

			if (assetTag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagId);
				}

				throw new NoSuchTagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					tagId);
			}

			return remove(assetTag);
		}
		catch (NoSuchTagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTag remove(AssetTag assetTag) throws SystemException {
		for (ModelListener<AssetTag> listener : listeners) {
			listener.onBeforeRemove(assetTag);
		}

		assetTag = removeImpl(assetTag);

		for (ModelListener<AssetTag> listener : listeners) {
			listener.onAfterRemove(assetTag);
		}

		return assetTag;
	}

	protected AssetTag removeImpl(AssetTag assetTag) throws SystemException {
		assetTag = toUnwrappedModel(assetTag);

		try {
			clearAssetEntries.clear(assetTag.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (assetTag.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetTagImpl.class,
						assetTag.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetTag);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagImpl.class, assetTag.getPrimaryKey());

		return assetTag;
	}

	public AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws SystemException {
		assetTag = toUnwrappedModel(assetTag);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetTag, merge);

			assetTag.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagImpl.class, assetTag.getPrimaryKey(), assetTag);

		return assetTag;
	}

	protected AssetTag toUnwrappedModel(AssetTag assetTag) {
		if (assetTag instanceof AssetTagImpl) {
			return assetTag;
		}

		AssetTagImpl assetTagImpl = new AssetTagImpl();

		assetTagImpl.setNew(assetTag.isNew());
		assetTagImpl.setPrimaryKey(assetTag.getPrimaryKey());

		assetTagImpl.setTagId(assetTag.getTagId());
		assetTagImpl.setGroupId(assetTag.getGroupId());
		assetTagImpl.setCompanyId(assetTag.getCompanyId());
		assetTagImpl.setUserId(assetTag.getUserId());
		assetTagImpl.setUserName(assetTag.getUserName());
		assetTagImpl.setCreateDate(assetTag.getCreateDate());
		assetTagImpl.setModifiedDate(assetTag.getModifiedDate());
		assetTagImpl.setName(assetTag.getName());
		assetTagImpl.setAssetCount(assetTag.getAssetCount());

		return assetTagImpl;
	}

	public AssetTag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetTag findByPrimaryKey(long tagId)
		throws NoSuchTagException, SystemException {
		AssetTag assetTag = fetchByPrimaryKey(tagId);

		if (assetTag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagId);
			}

			throw new NoSuchTagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				tagId);
		}

		return assetTag;
	}

	public AssetTag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetTag fetchByPrimaryKey(long tagId) throws SystemException {
		AssetTag assetTag = (AssetTag)EntityCacheUtil.getResult(AssetTagModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagImpl.class, tagId, this);

		if (assetTag == null) {
			Session session = null;

			try {
				session = openSession();

				assetTag = (AssetTag)session.get(AssetTagImpl.class,
						new Long(tagId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetTag != null) {
					cacheResult(assetTag);
				}

				closeSession(session);
			}
		}

		return assetTag;
	}

	public List<AssetTag> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<AssetTag> list = (List<AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETTAG_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(AssetTagModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTag> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<AssetTag> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTag> list = (List<AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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

				query.append(_SQL_SELECT_ASSETTAG_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetTagModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<AssetTag>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTag findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTagException, SystemException {
		List<AssetTag> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTag findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTagException, SystemException {
		int count = countByGroupId(groupId);

		List<AssetTag> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTag[] findByGroupId_PrevAndNext(long tagId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchTagException, SystemException {
		AssetTag assetTag = findByPrimaryKey(tagId);

		int count = countByGroupId(groupId);

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

			query.append(_SQL_SELECT_ASSETTAG_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetTag);

			AssetTag[] array = new AssetTagImpl[3];

			array[0] = (AssetTag)objArray[0];
			array[1] = (AssetTag)objArray[1];
			array[2] = (AssetTag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetTag> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetTag> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTag> list = (List<AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ASSETTAG);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ASSETTAG.concat(AssetTagModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetTag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetTag>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (AssetTag assetTag : findByGroupId(groupId)) {
			remove(assetTag);
		}
	}

	public void removeAll() throws SystemException {
		for (AssetTag assetTag : findAll()) {
			remove(assetTag);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ASSETTAG_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
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

				Query q = session.createQuery(_SQL_COUNT_ASSETTAG);

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

	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws SystemException {
		return getAssetEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) throws SystemException {
		return getAssetEntries(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETENTRIES = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetEntries",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.asset.model.AssetEntry> list = (List<com.liferay.portlet.asset.model.AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETENTRIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETENTRIES.concat(ORDER_BY_CLAUSE)
											  .concat(orderByComparator.getOrderBy());
				}

				sql = _SQL_GETASSETENTRIES;

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetEntry",
					com.liferay.portlet.asset.model.impl.AssetEntryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.asset.model.AssetEntry>();
				}

				assetEntryPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETENTRIES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETENTRIES_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetEntriesSize", new String[] { Long.class.getName() });

	public int getAssetEntriesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETENTRIES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETENTRIESSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETENTRIES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETENTRY = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"containsAssetEntry",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(assetEntryPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETENTRY,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetEntry.contains(pk,
							assetEntryPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETENTRY,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsAssetEntries(long pk) throws SystemException {
		if (getAssetEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		try {
			addAssetEntry.add(pk, assetEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		try {
			addAssetEntry.add(pk, assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			for (long assetEntryPK : assetEntryPKs) {
				addAssetEntry.add(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				addAssetEntry.add(pk, assetEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void clearAssetEntries(long pk) throws SystemException {
		try {
			clearAssetEntries.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		try {
			removeAssetEntry.remove(pk, assetEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		try {
			removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			for (long assetEntryPK : assetEntryPKs) {
				removeAssetEntry.remove(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			Set<Long> assetEntryPKSet = SetUtil.fromArray(assetEntryPKs);

			List<com.liferay.portlet.asset.model.AssetEntry> assetEntries = getAssetEntries(pk);

			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				if (!assetEntryPKSet.contains(assetEntry.getPrimaryKey())) {
					removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
				}
				else {
					assetEntryPKSet.remove(assetEntry.getPrimaryKey());
				}
			}

			for (Long assetEntryPK : assetEntryPKSet) {
				addAssetEntry.add(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void setAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			long[] assetEntryPKs = new long[assetEntries.size()];

			for (int i = 0; i < assetEntries.size(); i++) {
				com.liferay.portlet.asset.model.AssetEntry assetEntry = assetEntries.get(i);

				assetEntryPKs[i] = assetEntry.getPrimaryKey();
			}

			setAssetEntries(pk, assetEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetTagModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetTag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetTag>> listenersList = new ArrayList<ModelListener<AssetTag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetTag>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsAssetEntry = new ContainsAssetEntry(this);

		addAssetEntry = new AddAssetEntry(this);
		clearAssetEntries = new ClearAssetEntries(this);
		removeAssetEntry = new RemoveAssetEntry(this);
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected ContainsAssetEntry containsAssetEntry;
	protected AddAssetEntry addAssetEntry;
	protected ClearAssetEntries clearAssetEntries;
	protected RemoveAssetEntry removeAssetEntry;

	protected class ContainsAssetEntry {
		protected ContainsAssetEntry(AssetTagPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETENTRY,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long tagId, long entryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(tagId), new Long(entryId)
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

	protected class AddAssetEntry {
		protected AddAssetEntry(AssetTagPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetTags (tagId, entryId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long tagId, long entryId) throws SystemException {
			if (!_persistenceImpl.containsAssetEntry.contains(tagId, entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetTag> listener : listeners) {
					listener.onBeforeAddAssociation(tagId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeAddAssociation(entryId,
						AssetTag.class.getName(), tagId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(tagId), new Long(entryId)
					});

				for (ModelListener<AssetTag> listener : listeners) {
					listener.onAfterAddAssociation(tagId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterAddAssociation(entryId,
						AssetTag.class.getName(), tagId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetTagPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetEntries {
		protected ClearAssetEntries(AssetTagPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE tagId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long tagId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
				assetEntryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetEntry> assetEntries = null;

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				assetEntries = getAssetEntries(tagId);

				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetTag> listener : listeners) {
						listener.onBeforeRemoveAssociation(tagId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onBeforeRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetTag.class.getName(), tagId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(tagId) });

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetTag> listener : listeners) {
						listener.onAfterRemoveAssociation(tagId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onAfterRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetTag.class.getName(), tagId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetEntry {
		protected RemoveAssetEntry(AssetTagPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE tagId = ? AND entryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long tagId, long entryId)
			throws SystemException {
			if (_persistenceImpl.containsAssetEntry.contains(tagId, entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetTag> listener : listeners) {
					listener.onBeforeRemoveAssociation(tagId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeRemoveAssociation(entryId,
						AssetTag.class.getName(), tagId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(tagId), new Long(entryId)
					});

				for (ModelListener<AssetTag> listener : listeners) {
					listener.onAfterRemoveAssociation(tagId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterRemoveAssociation(entryId,
						AssetTag.class.getName(), tagId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetTagPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_ASSETTAG = "SELECT assetTag FROM AssetTag assetTag";
	private static final String _SQL_SELECT_ASSETTAG_WHERE = "SELECT assetTag FROM AssetTag assetTag WHERE ";
	private static final String _SQL_COUNT_ASSETTAG = "SELECT COUNT(assetTag) FROM AssetTag assetTag";
	private static final String _SQL_COUNT_ASSETTAG_WHERE = "SELECT COUNT(assetTag) FROM AssetTag assetTag WHERE ";
	private static final String _SQL_GETASSETENTRIES = "SELECT {AssetEntry.*} FROM AssetEntry INNER JOIN AssetEntries_AssetTags ON (AssetEntries_AssetTags.entryId = AssetEntry.entryId) WHERE (AssetEntries_AssetTags.tagId = ?)";
	private static final String _SQL_GETASSETENTRIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE tagId = ?";
	private static final String _SQL_CONTAINSASSETENTRY = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE tagId = ? AND entryId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetTag.groupId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetTag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetTag exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AssetTagPersistenceImpl.class);
}