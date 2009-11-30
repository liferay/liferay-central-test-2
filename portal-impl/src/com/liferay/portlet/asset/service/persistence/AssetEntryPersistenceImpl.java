/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;

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
					_log.warn("No AssetEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No AssetEntry exists with the primary key " + entryId);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
		}

		try {
			clearAssetTags.clear(assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
				_log.warn("No AssetEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No AssetEntry exists with the primary key " + entryId);
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

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				Query q = session.createQuery(query.toString());

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
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

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
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<AssetEntry> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No AssetEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetEntry findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<AssetEntry> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No AssetEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetEntry.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetEntry);

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = (AssetEntry)objArray[0];
			array[1] = (AssetEntry)objArray[1];
			array[2] = (AssetEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByC_C(classNameId, classPK);

		if (assetEntry == null) {
			StringBundler msg = new StringBundler();

			msg.append("No AssetEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

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

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				Query q = session.createQuery(query.toString());

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

	public List<AssetEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_ASSETENTRY);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				Query q = session.createQuery(query.toString());

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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_ASSETENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				Query q = session.createQuery(query.toString());

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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES,
			"getAssetCategories",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.asset.model.AssetCategory> list = (List<com.liferay.portlet.asset.model.AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETCATEGORIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler sb = new StringBundler();

				sb.append(_SQL_GETASSETCATEGORIES);

				if (obc != null) {
					sb.append(" ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append(" ORDER BY ");

					sb.append("AssetCategory.name ASC");
				}

				String sql = sb.toString();

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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES,
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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES,
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETCATEGORIES);
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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS, "getAssetTags",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.asset.model.AssetTag> list = (List<com.liferay.portlet.asset.model.AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETTAGS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler sb = new StringBundler();

				sb.append(_SQL_GETASSETTAGS);

				if (obc != null) {
					sb.append(" ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append(" ORDER BY ");

					sb.append("AssetTag.name ASC");
				}

				String sql = sb.toString();

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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS,
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
			AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS,
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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
			FinderCacheUtil.clearCache(AssetEntryModelImpl.TABLE_ASSETENTRIES_ASSETTAGS);
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

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence wikiPageResourcePersistence;
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

		private MappingSqlQuery _mappingSqlQuery;
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

		private MappingSqlQuery _mappingSqlQuery;
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetEntry.companyId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "assetEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "assetEntry.classPK = ?";
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
	private static Log _log = LogFactoryUtil.getLog(AssetEntryPersistenceImpl.class);
}