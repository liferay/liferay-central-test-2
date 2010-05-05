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
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="AssetEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryPersistence
 * @see       AssetEntryUtil
 * @generated
 */
public class AssetEntryPersistenceImpl extends BasePersistenceImpl<AssetEntry>
	implements AssetEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(AssetEntry assetEntry) {
		EntityCacheUtil.putResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(assetEntry.getClassNameId()),
				new Long(assetEntry.getClassPK())
			}, assetEntry);
	}

	public void cacheResult(List<AssetEntry> assetEntries) {
		for (AssetEntry assetEntry : assetEntries) {
			if (EntityCacheUtil.getResult(
						AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryImpl.class, assetEntry.getPrimaryKey(), this) == null) {
				cacheResult(assetEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetEntryImpl.class.getName());
		EntityCacheUtil.clearCache(AssetEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetEntry create(long entryId) {
		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setNew(true);
		assetEntry.setPrimaryKey(entryId);

		return assetEntry;
	}

	public AssetEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetEntry assetEntry = (AssetEntry)session.get(AssetEntryImpl.class,
					new Long(entryId));

			if (assetEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryId);
			}

			return remove(assetEntry);
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

	public AssetEntry remove(AssetEntry assetEntry) throws SystemException {
		for (ModelListener<AssetEntry> listener : listeners) {
			listener.onBeforeRemove(assetEntry);
		}

		assetEntry = removeImpl(assetEntry);

		for (ModelListener<AssetEntry> listener : listeners) {
			listener.onAfterRemove(assetEntry);
		}

		return assetEntry;
	}

	protected AssetEntry removeImpl(AssetEntry assetEntry)
		throws SystemException {
		assetEntry = toUnwrappedModel(assetEntry);

		try {
			clearAssetCategories.clear(assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}

		try {
			clearAssetTags.clear(assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (assetEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetEntryImpl.class,
						assetEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetEntryModelImpl assetEntryModelImpl = (AssetEntryModelImpl)assetEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(assetEntryModelImpl.getOriginalClassNameId()),
				new Long(assetEntryModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey());

		return assetEntry;
	}

	public AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws SystemException {
		assetEntry = toUnwrappedModel(assetEntry);

		boolean isNew = assetEntry.isNew();

		AssetEntryModelImpl assetEntryModelImpl = (AssetEntryModelImpl)assetEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetEntry, merge);

			assetEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry);

		if (!isNew &&
				((assetEntry.getClassNameId() != assetEntryModelImpl.getOriginalClassNameId()) ||
				(assetEntry.getClassPK() != assetEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(assetEntryModelImpl.getOriginalClassNameId()),
					new Long(assetEntryModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((assetEntry.getClassNameId() != assetEntryModelImpl.getOriginalClassNameId()) ||
				(assetEntry.getClassPK() != assetEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(assetEntry.getClassNameId()),
					new Long(assetEntry.getClassPK())
				}, assetEntry);
		}

		return assetEntry;
	}

	protected AssetEntry toUnwrappedModel(AssetEntry assetEntry) {
		if (assetEntry instanceof AssetEntryImpl) {
			return assetEntry;
		}

		AssetEntryImpl assetEntryImpl = new AssetEntryImpl();

		assetEntryImpl.setNew(assetEntry.isNew());
		assetEntryImpl.setPrimaryKey(assetEntry.getPrimaryKey());

		assetEntryImpl.setEntryId(assetEntry.getEntryId());
		assetEntryImpl.setGroupId(assetEntry.getGroupId());
		assetEntryImpl.setCompanyId(assetEntry.getCompanyId());
		assetEntryImpl.setUserId(assetEntry.getUserId());
		assetEntryImpl.setUserName(assetEntry.getUserName());
		assetEntryImpl.setCreateDate(assetEntry.getCreateDate());
		assetEntryImpl.setModifiedDate(assetEntry.getModifiedDate());
		assetEntryImpl.setClassNameId(assetEntry.getClassNameId());
		assetEntryImpl.setClassPK(assetEntry.getClassPK());
		assetEntryImpl.setVisible(assetEntry.isVisible());
		assetEntryImpl.setStartDate(assetEntry.getStartDate());
		assetEntryImpl.setEndDate(assetEntry.getEndDate());
		assetEntryImpl.setPublishDate(assetEntry.getPublishDate());
		assetEntryImpl.setExpirationDate(assetEntry.getExpirationDate());
		assetEntryImpl.setMimeType(assetEntry.getMimeType());
		assetEntryImpl.setTitle(assetEntry.getTitle());
		assetEntryImpl.setDescription(assetEntry.getDescription());
		assetEntryImpl.setSummary(assetEntry.getSummary());
		assetEntryImpl.setUrl(assetEntry.getUrl());
		assetEntryImpl.setHeight(assetEntry.getHeight());
		assetEntryImpl.setWidth(assetEntry.getWidth());
		assetEntryImpl.setPriority(assetEntry.getPriority());
		assetEntryImpl.setViewCount(assetEntry.getViewCount());
		assetEntryImpl.setSocialInformationEquity(assetEntry.getSocialInformationEquity());

		return assetEntryImpl;
	}

	public AssetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByPrimaryKey(entryId);

		if (assetEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return assetEntry;
	}

	public AssetEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetEntry fetchByPrimaryKey(long entryId) throws SystemException {
		AssetEntry assetEntry = (AssetEntry)EntityCacheUtil.getResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryImpl.class, entryId, this);

		if (assetEntry == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntry = (AssetEntry)session.get(AssetEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetEntry != null) {
					cacheResult(assetEntry);
				}

				closeSession(session);
			}
		}

		return assetEntry;
	}

	public List<AssetEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
					list = new ArrayList<AssetEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<AssetEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<AssetEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<AssetEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, assetEntry,
					companyId, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByCompanyId_PrevAndNext(session, assetEntry,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByCompanyId_PrevAndNext(Session session,
		AssetEntry assetEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public AssetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByC_C(classNameId, classPK);

		if (assetEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return assetEntry;
	}

	public AssetEntry fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public AssetEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<AssetEntry> list = q.list();

				result = list;

				AssetEntry assetEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					assetEntry = list.get(0);

					cacheResult(assetEntry);

					if ((assetEntry.getClassNameId() != classNameId) ||
							(assetEntry.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, assetEntry);
					}
				}

				return assetEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<AssetEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetEntry)result;
			}
		}
	}

	public List<AssetEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ASSETENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_ASSETENTRY;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetEntry assetEntry : findByCompanyId(companyId)) {
			remove(assetEntry);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByC_C(classNameId, classPK);

		remove(assetEntry);
	}

	public void removeAll() throws SystemException {
		for (AssetEntry assetEntry : findAll()) {
			remove(assetEntry);
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

				query.append(_SQL_COUNT_ASSETENTRY_WHERE);

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

				query.append(_SQL_COUNT_ASSETENTRY_WHERE);

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRY);

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

	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws SystemException {
		return getAssetCategories(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end) throws SystemException {
		return getAssetCategories(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETCATEGORIES = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetCategories",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.asset.model.AssetCategory> list = (List<com.liferay.portlet.asset.model.AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETCATEGORIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETCATEGORIES.concat(ORDER_BY_CLAUSE)
												 .concat(orderByComparator.getOrderBy());
				}

				else {
					sql = _SQL_GETASSETCATEGORIES.concat(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetCategory",
					com.liferay.portlet.asset.model.impl.AssetCategoryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetCategory>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.asset.model.AssetCategory>();
				}

				assetCategoryPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETCATEGORIES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETCATEGORIES_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetCategoriesSize", new String[] { Long.class.getName() });

	public int getAssetCategoriesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETCATEGORIES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETCATEGORIESSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETCATEGORIES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETCATEGORY = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"containsAssetCategory",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(assetCategoryPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETCATEGORY,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetCategory.contains(pk,
							assetCategoryPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETCATEGORY,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsAssetCategories(long pk) throws SystemException {
		if (getAssetCategoriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		try {
			addAssetCategory.add(pk, assetCategoryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws SystemException {
		try {
			addAssetCategory.add(pk, assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			for (long assetCategoryPK : assetCategoryPKs) {
				addAssetCategory.add(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void addAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				addAssetCategory.add(pk, assetCategory.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void clearAssetCategories(long pk) throws SystemException {
		try {
			clearAssetCategories.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void removeAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		try {
			removeAssetCategory.remove(pk, assetCategoryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws SystemException {
		try {
			removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			for (long assetCategoryPK : assetCategoryPKs) {
				removeAssetCategory.remove(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void removeAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			Set<Long> assetCategoryPKSet = SetUtil.fromArray(assetCategoryPKs);

			List<com.liferay.portlet.asset.model.AssetCategory> assetCategories = getAssetCategories(pk);

			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				if (!assetCategoryPKSet.contains(assetCategory.getPrimaryKey())) {
					removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
				}
				else {
					assetCategoryPKSet.remove(assetCategory.getPrimaryKey());
				}
			}

			for (Long assetCategoryPK : assetCategoryPKSet) {
				addAssetCategory.add(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public void setAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			long[] assetCategoryPKs = new long[assetCategories.size()];

			for (int i = 0; i < assetCategories.size(); i++) {
				com.liferay.portlet.asset.model.AssetCategory assetCategory = assetCategories.get(i);

				assetCategoryPKs[i] = assetCategory.getPrimaryKey();
			}

			setAssetCategories(pk, assetCategoryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(long pk)
		throws SystemException {
		return getAssetTags(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end) throws SystemException {
		return getAssetTags(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETTAGS = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetTags",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.asset.model.AssetTag> list = (List<com.liferay.portlet.asset.model.AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETTAGS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETTAGS.concat(ORDER_BY_CLAUSE)
										   .concat(orderByComparator.getOrderBy());
				}

				else {
					sql = _SQL_GETASSETTAGS.concat(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetTag",
					com.liferay.portlet.asset.model.impl.AssetTagImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetTag>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.asset.model.AssetTag>();
				}

				assetTagPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETTAGS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETTAGS_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetTagsSize", new String[] { Long.class.getName() });

	public int getAssetTagsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETTAGS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETTAGSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETTAGS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETTAG = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"containsAssetTag",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsAssetTag(long pk, long assetTagPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(assetTagPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETTAG,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetTag.contains(pk, assetTagPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETTAG,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsAssetTags(long pk) throws SystemException {
		if (getAssetTagsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addAssetTag(long pk, long assetTagPK) throws SystemException {
		try {
			addAssetTag.add(pk, assetTagPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws SystemException {
		try {
			addAssetTag.add(pk, assetTag.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			for (long assetTagPK : assetTagPKs) {
				addAssetTag.add(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void addAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				addAssetTag.add(pk, assetTag.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void clearAssetTags(long pk) throws SystemException {
		try {
			clearAssetTags.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetTag(long pk, long assetTagPK)
		throws SystemException {
		try {
			removeAssetTag.remove(pk, assetTagPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws SystemException {
		try {
			removeAssetTag.remove(pk, assetTag.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			for (long assetTagPK : assetTagPKs) {
				removeAssetTag.remove(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void removeAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				removeAssetTag.remove(pk, assetTag.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void setAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			Set<Long> assetTagPKSet = SetUtil.fromArray(assetTagPKs);

			List<com.liferay.portlet.asset.model.AssetTag> assetTags = getAssetTags(pk);

			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				if (!assetTagPKSet.contains(assetTag.getPrimaryKey())) {
					removeAssetTag.remove(pk, assetTag.getPrimaryKey());
				}
				else {
					assetTagPKSet.remove(assetTag.getPrimaryKey());
				}
			}

			for (Long assetTagPK : assetTagPKSet) {
				addAssetTag.add(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void setAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			long[] assetTagPKs = new long[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				com.liferay.portlet.asset.model.AssetTag assetTag = assetTags.get(i);

				assetTagPKs[i] = assetTag.getPrimaryKey();
			}

			setAssetTags(pk, assetTagPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetEntry>> listenersList = new ArrayList<ModelListener<AssetEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsAssetCategory = new ContainsAssetCategory(this);

		addAssetCategory = new AddAssetCategory(this);
		clearAssetCategories = new ClearAssetCategories(this);
		removeAssetCategory = new RemoveAssetCategory(this);

		containsAssetTag = new ContainsAssetTag(this);

		addAssetTag = new AddAssetTag(this);
		clearAssetTags = new ClearAssetTags(this);
		removeAssetTag = new RemoveAssetTag(this);
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
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BookmarksEntryPersistence.class)
	protected BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(type = WikiPageResourcePersistence.class)
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	protected ContainsAssetCategory containsAssetCategory;
	protected AddAssetCategory addAssetCategory;
	protected ClearAssetCategories clearAssetCategories;
	protected RemoveAssetCategory removeAssetCategory;
	protected ContainsAssetTag containsAssetTag;
	protected AddAssetTag addAssetTag;
	protected ClearAssetTags clearAssetTags;
	protected RemoveAssetTag removeAssetTag;

	protected class ContainsAssetCategory {
		protected ContainsAssetCategory(
			AssetEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETCATEGORY,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long categoryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(categoryId)
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

	protected class AddAssetCategory {
		protected AddAssetCategory(AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetCategories (entryId, categoryId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long entryId, long categoryId)
			throws SystemException {
			if (!_persistenceImpl.containsAssetCategory.contains(entryId,
						categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeAddAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(categoryId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterAddAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetCategories {
		protected ClearAssetCategories(
			AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE entryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
				assetCategoryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetCategory> assetCategories = null;

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				assetCategories = getAssetCategories(entryId);

				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onBeforeRemoveAssociation(assetCategory.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onAfterRemoveAssociation(assetCategory.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetCategory {
		protected RemoveAssetCategory(AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE entryId = ? AND categoryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long entryId, long categoryId)
			throws SystemException {
			if (_persistenceImpl.containsAssetCategory.contains(entryId,
						categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(categoryId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterRemoveAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetEntryPersistenceImpl _persistenceImpl;
	}

	protected class ContainsAssetTag {
		protected ContainsAssetTag(AssetEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETTAG,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long tagId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(tagId)
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

	protected class AddAssetTag {
		protected AddAssetTag(AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetTags (entryId, tagId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long entryId, long tagId) throws SystemException {
			if (!_persistenceImpl.containsAssetTag.contains(entryId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeAddAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(tagId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterAddAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetTags {
		protected ClearAssetTags(AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE entryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
				assetTagPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetTag> assetTags = null;

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				assetTags = getAssetTags(entryId);

				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onBeforeRemoveAssociation(assetTag.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onAfterRemoveAssociation(assetTag.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetTag {
		protected RemoveAssetTag(AssetEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE entryId = ? AND tagId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long entryId, long tagId)
			throws SystemException {
			if (_persistenceImpl.containsAssetTag.contains(entryId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeRemoveAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(tagId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterRemoveAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetEntryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_ASSETENTRY = "SELECT assetEntry FROM AssetEntry assetEntry";
	private static final String _SQL_SELECT_ASSETENTRY_WHERE = "SELECT assetEntry FROM AssetEntry assetEntry WHERE ";
	private static final String _SQL_COUNT_ASSETENTRY = "SELECT COUNT(assetEntry) FROM AssetEntry assetEntry";
	private static final String _SQL_COUNT_ASSETENTRY_WHERE = "SELECT COUNT(assetEntry) FROM AssetEntry assetEntry WHERE ";
	private static final String _SQL_GETASSETCATEGORIES = "SELECT {AssetCategory.*} FROM AssetCategory INNER JOIN AssetEntries_AssetCategories ON (AssetEntries_AssetCategories.categoryId = AssetCategory.categoryId) WHERE (AssetEntries_AssetCategories.entryId = ?)";
	private static final String _SQL_GETASSETCATEGORIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE entryId = ?";
	private static final String _SQL_CONTAINSASSETCATEGORY = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE entryId = ? AND categoryId = ?";
	private static final String _SQL_GETASSETTAGS = "SELECT {AssetTag.*} FROM AssetTag INNER JOIN AssetEntries_AssetTags ON (AssetEntries_AssetTags.tagId = AssetTag.tagId) WHERE (AssetEntries_AssetTags.entryId = ?)";
	private static final String _SQL_GETASSETTAGSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE entryId = ?";
	private static final String _SQL_CONTAINSASSETTAG = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE entryId = ? AND tagId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetEntry.companyId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "assetEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "assetEntry.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AssetEntryPersistenceImpl.class);
}