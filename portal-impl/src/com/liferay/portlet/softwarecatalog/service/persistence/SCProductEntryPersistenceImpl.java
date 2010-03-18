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

package com.liferay.portlet.softwarecatalog.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="SCProductEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryPersistence
 * @see       SCProductEntryUtil
 * @generated
 */
public class SCProductEntryPersistenceImpl extends BasePersistenceImpl<SCProductEntry>
	implements SCProductEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_RG_RA = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByRG_RA",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_RG_RA = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRG_RA",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCProductEntry scProductEntry) {
		EntityCacheUtil.putResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey(),
			scProductEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
			new Object[] {
				scProductEntry.getRepoGroupId(),
				
			scProductEntry.getRepoArtifactId()
			}, scProductEntry);
	}

	public void cacheResult(List<SCProductEntry> scProductEntries) {
		for (SCProductEntry scProductEntry : scProductEntries) {
			if (EntityCacheUtil.getResult(
						SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
						SCProductEntryImpl.class,
						scProductEntry.getPrimaryKey(), this) == null) {
				cacheResult(scProductEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SCProductEntryImpl.class.getName());
		EntityCacheUtil.clearCache(SCProductEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SCProductEntry create(long productEntryId) {
		SCProductEntry scProductEntry = new SCProductEntryImpl();

		scProductEntry.setNew(true);
		scProductEntry.setPrimaryKey(productEntryId);

		return scProductEntry;
	}

	public SCProductEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SCProductEntry remove(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductEntry scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
					new Long(productEntryId));

			if (scProductEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						productEntryId);
				}

				throw new NoSuchProductEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					productEntryId);
			}

			return remove(scProductEntry);
		}
		catch (NoSuchProductEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductEntry remove(SCProductEntry scProductEntry)
		throws SystemException {
		for (ModelListener<SCProductEntry> listener : listeners) {
			listener.onBeforeRemove(scProductEntry);
		}

		scProductEntry = removeImpl(scProductEntry);

		for (ModelListener<SCProductEntry> listener : listeners) {
			listener.onAfterRemove(scProductEntry);
		}

		return scProductEntry;
	}

	protected SCProductEntry removeImpl(SCProductEntry scProductEntry)
		throws SystemException {
		scProductEntry = toUnwrappedModel(scProductEntry);

		try {
			clearSCLicenses.clear(scProductEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCProductEntryImpl.class,
						scProductEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scProductEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCProductEntryModelImpl scProductEntryModelImpl = (SCProductEntryModelImpl)scProductEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_RG_RA,
			new Object[] {
				scProductEntryModelImpl.getOriginalRepoGroupId(),
				
			scProductEntryModelImpl.getOriginalRepoArtifactId()
			});

		EntityCacheUtil.removeResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey());

		return scProductEntry;
	}

	public SCProductEntry updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge) throws SystemException {
		scProductEntry = toUnwrappedModel(scProductEntry);

		boolean isNew = scProductEntry.isNew();

		SCProductEntryModelImpl scProductEntryModelImpl = (SCProductEntryModelImpl)scProductEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scProductEntry, merge);

			scProductEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey(),
			scProductEntry);

		if (!isNew &&
				(!Validator.equals(scProductEntry.getRepoGroupId(),
					scProductEntryModelImpl.getOriginalRepoGroupId()) ||
				!Validator.equals(scProductEntry.getRepoArtifactId(),
					scProductEntryModelImpl.getOriginalRepoArtifactId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_RG_RA,
				new Object[] {
					scProductEntryModelImpl.getOriginalRepoGroupId(),
					
				scProductEntryModelImpl.getOriginalRepoArtifactId()
				});
		}

		if (isNew ||
				(!Validator.equals(scProductEntry.getRepoGroupId(),
					scProductEntryModelImpl.getOriginalRepoGroupId()) ||
				!Validator.equals(scProductEntry.getRepoArtifactId(),
					scProductEntryModelImpl.getOriginalRepoArtifactId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
				new Object[] {
					scProductEntry.getRepoGroupId(),
					
				scProductEntry.getRepoArtifactId()
				}, scProductEntry);
		}

		return scProductEntry;
	}

	protected SCProductEntry toUnwrappedModel(SCProductEntry scProductEntry) {
		if (scProductEntry instanceof SCProductEntryImpl) {
			return scProductEntry;
		}

		SCProductEntryImpl scProductEntryImpl = new SCProductEntryImpl();

		scProductEntryImpl.setNew(scProductEntry.isNew());
		scProductEntryImpl.setPrimaryKey(scProductEntry.getPrimaryKey());

		scProductEntryImpl.setProductEntryId(scProductEntry.getProductEntryId());
		scProductEntryImpl.setGroupId(scProductEntry.getGroupId());
		scProductEntryImpl.setCompanyId(scProductEntry.getCompanyId());
		scProductEntryImpl.setUserId(scProductEntry.getUserId());
		scProductEntryImpl.setUserName(scProductEntry.getUserName());
		scProductEntryImpl.setCreateDate(scProductEntry.getCreateDate());
		scProductEntryImpl.setModifiedDate(scProductEntry.getModifiedDate());
		scProductEntryImpl.setName(scProductEntry.getName());
		scProductEntryImpl.setType(scProductEntry.getType());
		scProductEntryImpl.setTags(scProductEntry.getTags());
		scProductEntryImpl.setShortDescription(scProductEntry.getShortDescription());
		scProductEntryImpl.setLongDescription(scProductEntry.getLongDescription());
		scProductEntryImpl.setPageURL(scProductEntry.getPageURL());
		scProductEntryImpl.setAuthor(scProductEntry.getAuthor());
		scProductEntryImpl.setRepoGroupId(scProductEntry.getRepoGroupId());
		scProductEntryImpl.setRepoArtifactId(scProductEntry.getRepoArtifactId());

		return scProductEntryImpl;
	}

	public SCProductEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductEntry findByPrimaryKey(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByPrimaryKey(productEntryId);

		if (scProductEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + productEntryId);
			}

			throw new NoSuchProductEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productEntryId);
		}

		return scProductEntry;
	}

	public SCProductEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductEntry fetchByPrimaryKey(long productEntryId)
		throws SystemException {
		SCProductEntry scProductEntry = (SCProductEntry)EntityCacheUtil.getResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				SCProductEntryImpl.class, productEntryId, this);

		if (scProductEntry == null) {
			Session session = null;

			try {
				session = openSession();

				scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
						new Long(productEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scProductEntry != null) {
					cacheResult(scProductEntry);
				}

				closeSession(session);
			}
		}

		return scProductEntry;
	}

	public List<SCProductEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SCProductEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<SCProductEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByGroupId_PrevAndNext(long productEntryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

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

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<SCProductEntry> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByCompanyId_PrevAndNext(long productEntryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		int count = countByCompanyId(companyId);

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

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
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

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductEntry findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<SCProductEntry> list = findByG_U(groupId, userId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		int count = countByG_U(groupId, userId);

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

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductEntry findByRG_RA(String repoGroupId, String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByRG_RA(repoGroupId, repoArtifactId);

		if (scProductEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repoGroupId=");
			msg.append(repoGroupId);

			msg.append(", repoArtifactId=");
			msg.append(repoArtifactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductEntryException(msg.toString());
		}

		return scProductEntry;
	}

	public SCProductEntry fetchByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		return fetchByRG_RA(repoGroupId, repoArtifactId, true);
	}

	public SCProductEntry fetchByRG_RA(String repoGroupId,
		String repoArtifactId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_RG_RA,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

				if (repoGroupId == null) {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_1);
				}
				else {
					if (repoGroupId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_3);
					}
					else {
						query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_2);
					}
				}

				if (repoArtifactId == null) {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_1);
				}
				else {
					if (repoArtifactId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_3);
					}
					else {
						query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_2);
					}
				}

				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
				}

				List<SCProductEntry> list = q.list();

				result = list;

				SCProductEntry scProductEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
						finderArgs, list);
				}
				else {
					scProductEntry = list.get(0);

					cacheResult(scProductEntry);

					if ((scProductEntry.getRepoGroupId() == null) ||
							!scProductEntry.getRepoGroupId().equals(repoGroupId) ||
							(scProductEntry.getRepoArtifactId() == null) ||
							!scProductEntry.getRepoArtifactId()
											   .equals(repoArtifactId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
							finderArgs, scProductEntry);
					}
				}

				return scProductEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
						finderArgs, new ArrayList<SCProductEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SCProductEntry)result;
			}
		}
	}

	public List<SCProductEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SCPRODUCTENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SCPRODUCTENTRY.concat(SCProductEntryModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCProductEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SCProductEntry scProductEntry : findByGroupId(groupId)) {
			remove(scProductEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCProductEntry scProductEntry : findByCompanyId(companyId)) {
			remove(scProductEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (SCProductEntry scProductEntry : findByG_U(groupId, userId)) {
			remove(scProductEntry);
		}
	}

	public void removeByRG_RA(String repoGroupId, String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByRG_RA(repoGroupId, repoArtifactId);

		remove(scProductEntry);
	}

	public void removeAll() throws SystemException {
		for (SCProductEntry scProductEntry : findAll()) {
			remove(scProductEntry);
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

				query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RG_RA,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

				if (repoGroupId == null) {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_1);
				}
				else {
					if (repoGroupId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_3);
					}
					else {
						query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_2);
					}
				}

				if (repoArtifactId == null) {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_1);
				}
				else {
					if (repoArtifactId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_3);
					}
					else {
						query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RG_RA,
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

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTENTRY);

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

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk) throws SystemException {
		return getSCLicenses(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end) throws SystemException {
		return getSCLicenses(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCLICENSES = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"getSCLicenses",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.softwarecatalog.model.SCLicense> list = (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCLICENSES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETSCLICENSES.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}

				else {
					sql = _SQL_GETSCLICENSES.concat(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCLicense",
					com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.softwarecatalog.model.SCLicense>();
				}

				scLicensePersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCLICENSES,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCLICENSES_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"getSCLicensesSize", new String[] { Long.class.getName() });

	public int getSCLicensesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCLICENSES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCLICENSESSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCLICENSES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCLICENSE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"containsSCLicense",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCLicense(long pk, long scLicensePK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(scLicensePK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCLICENSE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCLicense.contains(pk,
							scLicensePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCLICENSE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsSCLicenses(long pk) throws SystemException {
		if (getSCLicensesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCLicense(long pk, long scLicensePK)
		throws SystemException {
		try {
			addSCLicense.add(pk, scLicensePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		try {
			addSCLicense.add(pk, scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void addSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void addSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				addSCLicense.add(pk, scLicense.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void clearSCLicenses(long pk) throws SystemException {
		try {
			clearSCLicenses.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void removeSCLicense(long pk, long scLicensePK)
		throws SystemException {
		try {
			removeSCLicense.remove(pk, scLicensePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		try {
			removeSCLicense.remove(pk, scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void removeSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				removeSCLicense.remove(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void removeSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				removeSCLicense.remove(pk, scLicense.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void setSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			Set<Long> scLicensePKSet = SetUtil.fromArray(scLicensePKs);

			List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses =
				getSCLicenses(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				if (!scLicensePKSet.contains(scLicense.getPrimaryKey())) {
					removeSCLicense.remove(pk, scLicense.getPrimaryKey());
				}
				else {
					scLicensePKSet.remove(scLicense.getPrimaryKey());
				}
			}

			for (Long scLicensePK : scLicensePKSet) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void setSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			long[] scLicensePKs = new long[scLicenses.size()];

			for (int i = 0; i < scLicenses.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCLicense scLicense = scLicenses.get(i);

				scLicensePKs[i] = scLicense.getPrimaryKey();
			}

			setSCLicenses(pk, scLicensePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductEntry>> listenersList = new ArrayList<ModelListener<SCProductEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCLicense = new ContainsSCLicense(this);

		addSCLicense = new AddSCLicense(this);
		clearSCLicenses = new ClearSCLicenses(this);
		removeSCLicense = new RemoveSCLicense(this);
	}

	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	protected ContainsSCLicense containsSCLicense;
	protected AddSCLicense addSCLicense;
	protected ClearSCLicenses clearSCLicenses;
	protected RemoveSCLicense removeSCLicense;

	protected class ContainsSCLicense {
		protected ContainsSCLicense(
			SCProductEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCLICENSE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long productEntryId, long licenseId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(productEntryId), new Long(licenseId)
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

	protected class AddSCLicense {
		protected AddSCLicense(SCProductEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCLicenses_SCProductEntries (productEntryId, licenseId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long productEntryId, long licenseId)
			throws SystemException {
			if (!_persistenceImpl.containsSCLicense.contains(productEntryId,
						licenseId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
					scLicensePersistence.getListeners();

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onBeforeAddAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onBeforeAddAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onAfterAddAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onAfterAddAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCLicenses {
		protected ClearSCLicenses(SCProductEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long productEntryId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
				scLicensePersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses =
				null;

			if ((listeners.length > 0) || (scLicenseListeners.length > 0)) {
				scLicenses = getSCLicenses(productEntryId);

				for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
					for (ModelListener<SCProductEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(productEntryId,
							com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
							scLicense.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
						listener.onBeforeRemoveAssociation(scLicense.getPrimaryKey(),
							SCProductEntry.class.getName(), productEntryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(productEntryId) });

			if ((listeners.length > 0) || (scLicenseListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
					for (ModelListener<SCProductEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(productEntryId,
							com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
							scLicense.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
						listener.onAfterRemoveAssociation(scLicense.getPrimaryKey(),
							SCProductEntry.class.getName(), productEntryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCLicense {
		protected RemoveSCLicense(SCProductEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long productEntryId, long licenseId)
			throws SystemException {
			if (_persistenceImpl.containsSCLicense.contains(productEntryId,
						licenseId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
					scLicensePersistence.getListeners();

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onBeforeRemoveAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onAfterRemoveAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductEntryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_SCPRODUCTENTRY = "SELECT scProductEntry FROM SCProductEntry scProductEntry";
	private static final String _SQL_SELECT_SCPRODUCTENTRY_WHERE = "SELECT scProductEntry FROM SCProductEntry scProductEntry WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTENTRY = "SELECT COUNT(scProductEntry) FROM SCProductEntry scProductEntry";
	private static final String _SQL_COUNT_SCPRODUCTENTRY_WHERE = "SELECT COUNT(scProductEntry) FROM SCProductEntry scProductEntry WHERE ";
	private static final String _SQL_GETSCLICENSES = "SELECT {SCLicense.*} FROM SCLicense INNER JOIN SCLicenses_SCProductEntries ON (SCLicenses_SCProductEntries.licenseId = SCLicense.licenseId) WHERE (SCLicenses_SCProductEntries.productEntryId = ?)";
	private static final String _SQL_GETSCLICENSESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?";
	private static final String _SQL_CONTAINSSCLICENSE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scProductEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scProductEntry.companyId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "scProductEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "scProductEntry.userId = ?";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_1 = "scProductEntry.repoGroupId IS NULL AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_2 = "scProductEntry.lower(repoGroupId) = ? AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_3 = "(scProductEntry.repoGroupId IS NULL OR scProductEntry.lower(repoGroupId) = ?) AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_1 = "scProductEntry.repoArtifactId IS NULL";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_2 = "scProductEntry.lower(repoArtifactId) = ?";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_3 = "(scProductEntry.repoArtifactId IS NULL OR scProductEntry.lower(repoArtifactId) = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SCProductEntryPersistenceImpl.class);
}