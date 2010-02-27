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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="SCProductVersionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionPersistence
 * @see       SCProductVersionUtil
 * @generated
 */
public class SCProductVersionPersistenceImpl extends BasePersistenceImpl<SCProductVersion>
	implements SCProductVersionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByDirectDownloadURL",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByDirectDownloadURL",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCProductVersion scProductVersion) {
		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] { scProductVersion.getDirectDownloadURL() },
			scProductVersion);
	}

	public void cacheResult(List<SCProductVersion> scProductVersions) {
		for (SCProductVersion scProductVersion : scProductVersions) {
			if (EntityCacheUtil.getResult(
						SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class,
						scProductVersion.getPrimaryKey(), this) == null) {
				cacheResult(scProductVersion);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SCProductVersionImpl.class.getName());
		EntityCacheUtil.clearCache(SCProductVersionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SCProductVersion create(long productVersionId) {
		SCProductVersion scProductVersion = new SCProductVersionImpl();

		scProductVersion.setNew(true);
		scProductVersion.setPrimaryKey(productVersionId);

		return scProductVersion;
	}

	public SCProductVersion remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SCProductVersion remove(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductVersion scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
					new Long(productVersionId));

			if (scProductVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						productVersionId);
				}

				throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					productVersionId);
			}

			return remove(scProductVersion);
		}
		catch (NoSuchProductVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductVersion remove(SCProductVersion scProductVersion)
		throws SystemException {
		for (ModelListener<SCProductVersion> listener : listeners) {
			listener.onBeforeRemove(scProductVersion);
		}

		scProductVersion = removeImpl(scProductVersion);

		for (ModelListener<SCProductVersion> listener : listeners) {
			listener.onAfterRemove(scProductVersion);
		}

		return scProductVersion;
	}

	protected SCProductVersion removeImpl(SCProductVersion scProductVersion)
		throws SystemException {
		scProductVersion = toUnwrappedModel(scProductVersion);

		try {
			clearSCFrameworkVersions.clear(scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductVersion.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCProductVersionImpl.class,
						scProductVersion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scProductVersion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] {
				scProductVersionModelImpl.getOriginalDirectDownloadURL()
			});

		EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

		return scProductVersion;
	}

	public SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion,
		boolean merge) throws SystemException {
		scProductVersion = toUnwrappedModel(scProductVersion);

		boolean isNew = scProductVersion.isNew();

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scProductVersion, merge);

			scProductVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		if (!isNew &&
				(!Validator.equals(scProductVersion.getDirectDownloadURL(),
					scProductVersionModelImpl.getOriginalDirectDownloadURL()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				new Object[] {
					scProductVersionModelImpl.getOriginalDirectDownloadURL()
				});
		}

		if (isNew ||
				(!Validator.equals(scProductVersion.getDirectDownloadURL(),
					scProductVersionModelImpl.getOriginalDirectDownloadURL()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				new Object[] { scProductVersion.getDirectDownloadURL() },
				scProductVersion);
		}

		return scProductVersion;
	}

	protected SCProductVersion toUnwrappedModel(
		SCProductVersion scProductVersion) {
		if (scProductVersion instanceof SCProductVersionImpl) {
			return scProductVersion;
		}

		SCProductVersionImpl scProductVersionImpl = new SCProductVersionImpl();

		scProductVersionImpl.setNew(scProductVersion.isNew());
		scProductVersionImpl.setPrimaryKey(scProductVersion.getPrimaryKey());

		scProductVersionImpl.setProductVersionId(scProductVersion.getProductVersionId());
		scProductVersionImpl.setCompanyId(scProductVersion.getCompanyId());
		scProductVersionImpl.setUserId(scProductVersion.getUserId());
		scProductVersionImpl.setUserName(scProductVersion.getUserName());
		scProductVersionImpl.setCreateDate(scProductVersion.getCreateDate());
		scProductVersionImpl.setModifiedDate(scProductVersion.getModifiedDate());
		scProductVersionImpl.setProductEntryId(scProductVersion.getProductEntryId());
		scProductVersionImpl.setVersion(scProductVersion.getVersion());
		scProductVersionImpl.setChangeLog(scProductVersion.getChangeLog());
		scProductVersionImpl.setDownloadPageURL(scProductVersion.getDownloadPageURL());
		scProductVersionImpl.setDirectDownloadURL(scProductVersion.getDirectDownloadURL());
		scProductVersionImpl.setRepoStoreArtifact(scProductVersion.isRepoStoreArtifact());

		return scProductVersionImpl;
	}

	public SCProductVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductVersion findByPrimaryKey(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByPrimaryKey(productVersionId);

		if (scProductVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + productVersionId);
			}

			throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productVersionId);
		}

		return scProductVersion;
	}

	public SCProductVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductVersion fetchByPrimaryKey(long productVersionId)
		throws SystemException {
		SCProductVersion scProductVersion = (SCProductVersion)EntityCacheUtil.getResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, productVersionId, this);

		if (scProductVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						new Long(productVersionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scProductVersion != null) {
					cacheResult(scProductVersion);
				}

				closeSession(session);
			}
		}

		return scProductVersion;
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

				query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

				query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(productEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

				query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductVersion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductVersion findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		List<SCProductVersion> list = findByProductEntryId(productEntryId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductVersion findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductVersion> list = findByProductEntryId(productEntryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByPrimaryKey(productVersionId);

		int count = countByProductEntryId(productEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductVersion);

			SCProductVersion[] array = new SCProductVersionImpl[3];

			array[0] = (SCProductVersion)objArray[0];
			array[1] = (SCProductVersion)objArray[1];
			array[2] = (SCProductVersion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductVersion findByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByDirectDownloadURL(directDownloadURL);

		if (scProductVersion == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("directDownloadURL=");
			msg.append(directDownloadURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductVersionException(msg.toString());
		}

		return scProductVersion;
	}

	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		return fetchByDirectDownloadURL(directDownloadURL, true);
	}

	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

				if (directDownloadURL == null) {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
				}
				else {
					if (directDownloadURL.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
					}
					else {
						query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
					}
				}

				query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
				}

				List<SCProductVersion> list = q.list();

				result = list;

				SCProductVersion scProductVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, list);
				}
				else {
					scProductVersion = list.get(0);

					cacheResult(scProductVersion);

					if ((scProductVersion.getDirectDownloadURL() == null) ||
							!scProductVersion.getDirectDownloadURL()
												 .equals(directDownloadURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
							finderArgs, scProductVersion);
					}
				}

				return scProductVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, new ArrayList<SCProductVersion>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SCProductVersion)result;
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

	public List<SCProductVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductVersion> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SCPRODUCTVERSION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SCPRODUCTVERSION.concat(SCProductVersionModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductVersion scProductVersion : findByProductEntryId(
				productEntryId)) {
			remove(scProductVersion);
		}
	}

	public void removeByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByDirectDownloadURL(directDownloadURL);

		remove(scProductVersion);
	}

	public void removeAll() throws SystemException {
		for (SCProductVersion scProductVersion : findAll()) {
			remove(scProductVersion);
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

				query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

				if (directDownloadURL == null) {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
				}
				else {
					if (directDownloadURL.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
					}
					else {
						query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
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

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTVERSION);

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

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws SystemException {
		return getSCFrameworkVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end) throws SystemException {
		return getSCFrameworkVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCFrameworkVersions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (obc != null) {
					sql = _SQL_GETSCFRAMEWORKVERSIONS.concat(ORDER_BY_CLAUSE)
													 .concat(obc.getOrderBy());
				}

				else {
					sql = _SQL_GETSCFRAMEWORKVERSIONS.concat(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCFrameworkVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>();
				}

				scFrameworkVersionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCFrameworkVersionsSize", new String[] { Long.class.getName() });

	public int getSCFrameworkVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCFRAMEWORKVERSIONSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"containsSCFrameworkVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scFrameworkVersionPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCFrameworkVersion.contains(
							pk, scFrameworkVersionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsSCFrameworkVersions(long pk)
		throws SystemException {
		if (getSCFrameworkVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void clearSCFrameworkVersions(long pk) throws SystemException {
		try {
			clearSCFrameworkVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk,
				scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				removeSCFrameworkVersion.remove(pk,
					scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			Set<Long> scFrameworkVersionPKSet = SetUtil.fromArray(scFrameworkVersionPKs);

			List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions =
				getSCFrameworkVersions(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				if (!scFrameworkVersionPKSet.contains(
							scFrameworkVersion.getPrimaryKey())) {
					removeSCFrameworkVersion.remove(pk,
						scFrameworkVersion.getPrimaryKey());
				}
				else {
					scFrameworkVersionPKSet.remove(scFrameworkVersion.getPrimaryKey());
				}
			}

			for (Long scFrameworkVersionPK : scFrameworkVersionPKSet) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void setSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			long[] scFrameworkVersionPKs = new long[scFrameworkVersions.size()];

			for (int i = 0; i < scFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion =
					scFrameworkVersions.get(i);

				scFrameworkVersionPKs[i] = scFrameworkVersion.getPrimaryKey();
			}

			setSCFrameworkVersions(pk, scFrameworkVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductVersion>> listenersList = new ArrayList<ModelListener<SCProductVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductVersion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCFrameworkVersion = new ContainsSCFrameworkVersion(this);

		addSCFrameworkVersion = new AddSCFrameworkVersion(this);
		clearSCFrameworkVersions = new ClearSCFrameworkVersions(this);
		removeSCFrameworkVersion = new RemoveSCFrameworkVersion(this);
	}

	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence scLicensePersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsSCFrameworkVersion containsSCFrameworkVersion;
	protected AddSCFrameworkVersion addSCFrameworkVersion;
	protected ClearSCFrameworkVersions clearSCFrameworkVersions;
	protected RemoveSCFrameworkVersion removeSCFrameworkVersion;

	protected class ContainsSCFrameworkVersion {
		protected ContainsSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCFRAMEWORKVERSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long productVersionId,
			long frameworkVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
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

	protected class AddSCFrameworkVersion {
		protected AddSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (productVersionId, frameworkVersionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (!_persistenceImpl.containsSCFrameworkVersion.contains(
						productVersionId, frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductVersionPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCFrameworkVersions {
		protected ClearSCFrameworkVersions(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long productVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
				scFrameworkVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions =
				null;

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				scFrameworkVersions = getSCFrameworkVersions(productVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onBeforeRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(productVersionId) });

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onAfterRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCFrameworkVersion {
		protected RemoveSCFrameworkVersion(
			SCProductVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (_persistenceImpl.containsSCFrameworkVersion.contains(
						productVersionId, frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCProductVersionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_SCPRODUCTVERSION = "SELECT scProductVersion FROM SCProductVersion scProductVersion";
	private static final String _SQL_SELECT_SCPRODUCTVERSION_WHERE = "SELECT scProductVersion FROM SCProductVersion scProductVersion WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTVERSION = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion";
	private static final String _SQL_COUNT_SCPRODUCTVERSION_WHERE = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion WHERE ";
	private static final String _SQL_GETSCFRAMEWORKVERSIONS = "SELECT {SCFrameworkVersion.*} FROM SCFrameworkVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.frameworkVersionId = SCFrameworkVersion.frameworkVersionId) WHERE (SCFrameworkVersi_SCProductVers.productVersionId = ?)";
	private static final String _SQL_GETSCFRAMEWORKVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?";
	private static final String _SQL_CONTAINSSCFRAMEWORKVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?";
	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 = "scProductVersion.productEntryId = ?";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1 =
		"scProductVersion.directDownloadURL IS NULL";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2 =
		"scProductVersion.lower(directDownloadURL) = ?";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3 =
		"(scProductVersion.directDownloadURL IS NULL OR scProductVersion.lower(directDownloadURL) = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductVersion exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SCProductVersionPersistenceImpl.class);
}