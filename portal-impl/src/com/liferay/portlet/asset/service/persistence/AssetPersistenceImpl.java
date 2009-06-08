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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchAssetException;
import com.liferay.portlet.asset.model.Asset;
import com.liferay.portlet.asset.model.impl.AssetImpl;
import com.liferay.portlet.asset.model.impl.AssetModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetPersistenceImpl extends BasePersistenceImpl
	implements AssetPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Asset asset) {
		EntityCacheUtil.putResult(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetImpl.class, asset.getPrimaryKey(), asset);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(asset.getClassNameId()), new Long(asset.getClassPK())
			}, asset);
	}

	public void cacheResult(List<Asset> assets) {
		for (Asset asset : assets) {
			if (EntityCacheUtil.getResult(AssetModelImpl.ENTITY_CACHE_ENABLED,
						AssetImpl.class, asset.getPrimaryKey(), this) == null) {
				cacheResult(asset);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetImpl.class.getName());
		EntityCacheUtil.clearCache(AssetImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Asset create(long assetId) {
		Asset asset = new AssetImpl();

		asset.setNew(true);
		asset.setPrimaryKey(assetId);

		return asset;
	}

	public Asset remove(long assetId)
		throws NoSuchAssetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Asset asset = (Asset)session.get(AssetImpl.class, new Long(assetId));

			if (asset == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Asset exists with the primary key " +
						assetId);
				}

				throw new NoSuchAssetException(
					"No Asset exists with the primary key " + assetId);
			}

			return remove(asset);
		}
		catch (NoSuchAssetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Asset remove(Asset asset) throws SystemException {
		for (ModelListener<Asset> listener : listeners) {
			listener.onBeforeRemove(asset);
		}

		asset = removeImpl(asset);

		for (ModelListener<Asset> listener : listeners) {
			listener.onAfterRemove(asset);
		}

		return asset;
	}

	protected Asset removeImpl(Asset asset) throws SystemException {
		try {
			clearAssetCategories.clear(asset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetCategories");
		}

		try {
			clearAssetTags.clear(asset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetTags");
		}

		Session session = null;

		try {
			session = openSession();

			if (asset.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetImpl.class,
						asset.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(asset);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetModelImpl assetModelImpl = (AssetModelImpl)asset;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(assetModelImpl.getOriginalClassNameId()),
				new Long(assetModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetImpl.class, asset.getPrimaryKey());

		return asset;
	}

	/**
	 * @deprecated Use <code>update(Asset asset, boolean merge)</code>.
	 */
	public Asset update(Asset asset) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Asset asset) method. Use update(Asset asset, boolean merge) instead.");
		}

		return update(asset, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        asset the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when asset is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Asset update(Asset asset, boolean merge) throws SystemException {
		boolean isNew = asset.isNew();

		for (ModelListener<Asset> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(asset);
			}
			else {
				listener.onBeforeUpdate(asset);
			}
		}

		asset = updateImpl(asset, merge);

		for (ModelListener<Asset> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(asset);
			}
			else {
				listener.onAfterUpdate(asset);
			}
		}

		return asset;
	}

	public Asset updateImpl(com.liferay.portlet.asset.model.Asset asset,
		boolean merge) throws SystemException {
		boolean isNew = asset.isNew();

		AssetModelImpl assetModelImpl = (AssetModelImpl)asset;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, asset, merge);

			asset.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetImpl.class, asset.getPrimaryKey(), asset);

		if (!isNew &&
				((asset.getClassNameId() != assetModelImpl.getOriginalClassNameId()) ||
				(asset.getClassPK() != assetModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(assetModelImpl.getOriginalClassNameId()),
					new Long(assetModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((asset.getClassNameId() != assetModelImpl.getOriginalClassNameId()) ||
				(asset.getClassPK() != assetModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(asset.getClassNameId()),
					new Long(asset.getClassPK())
				}, asset);
		}

		return asset;
	}

	public Asset findByPrimaryKey(long assetId)
		throws NoSuchAssetException, SystemException {
		Asset asset = fetchByPrimaryKey(assetId);

		if (asset == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Asset exists with the primary key " + assetId);
			}

			throw new NoSuchAssetException(
				"No Asset exists with the primary key " + assetId);
		}

		return asset;
	}

	public Asset fetchByPrimaryKey(long assetId) throws SystemException {
		Asset asset = (Asset)EntityCacheUtil.getResult(AssetModelImpl.ENTITY_CACHE_ENABLED,
				AssetImpl.class, assetId, this);

		if (asset == null) {
			Session session = null;

			try {
				session = openSession();

				asset = (Asset)session.get(AssetImpl.class, new Long(assetId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (asset != null) {
					cacheResult(asset);
				}

				closeSession(session);
			}
		}

		return asset;
	}

	public List<Asset> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Asset> list = (List<Asset>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT asset FROM Asset asset WHERE ");

				query.append("asset.companyId = ?");

				query.append(" ");

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
					list = new ArrayList<Asset>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Asset> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Asset> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Asset> list = (List<Asset>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT asset FROM Asset asset WHERE ");

				query.append("asset.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("asset.");
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

				list = (List<Asset>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Asset>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Asset findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchAssetException, SystemException {
		List<Asset> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Asset exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAssetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Asset findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchAssetException, SystemException {
		int count = countByCompanyId(companyId);

		List<Asset> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Asset exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAssetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Asset[] findByCompanyId_PrevAndNext(long assetId, long companyId,
		OrderByComparator obc) throws NoSuchAssetException, SystemException {
		Asset asset = findByPrimaryKey(assetId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT asset FROM Asset asset WHERE ");

			query.append("asset.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("asset.");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, asset);

			Asset[] array = new AssetImpl[3];

			array[0] = (Asset)objArray[0];
			array[1] = (Asset)objArray[1];
			array[2] = (Asset)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Asset findByC_C(long classNameId, long classPK)
		throws NoSuchAssetException, SystemException {
		Asset asset = fetchByC_C(classNameId, classPK);

		if (asset == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Asset exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchAssetException(msg.toString());
		}

		return asset;
	}

	public Asset fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public Asset fetchByC_C(long classNameId, long classPK,
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT asset FROM Asset asset WHERE ");

				query.append("asset.classNameId = ?");

				query.append(" AND ");

				query.append("asset.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Asset> list = q.list();

				result = list;

				Asset asset = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					asset = list.get(0);

					cacheResult(asset);

					if ((asset.getClassNameId() != classNameId) ||
							(asset.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, asset);
					}
				}

				return asset;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<Asset>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (Asset)result;
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

	public List<Asset> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Asset> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Asset> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Asset> list = (List<Asset>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT asset FROM Asset asset ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("asset.");
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
					list = (List<Asset>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Asset>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Asset>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Asset asset : findByCompanyId(companyId)) {
			remove(asset);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchAssetException, SystemException {
		Asset asset = findByC_C(classNameId, classPK);

		remove(asset);
	}

	public void removeAll() throws SystemException {
		for (Asset asset : findAll()) {
			remove(asset);
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(asset) ");
				query.append("FROM Asset asset WHERE ");

				query.append("asset.companyId = ?");

				query.append(" ");

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(asset) ");
				query.append("FROM Asset asset WHERE ");

				query.append("asset.classNameId = ?");

				query.append(" AND ");

				query.append("asset.classPK = ?");

				query.append(" ");

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

				Query q = session.createQuery(
						"SELECT COUNT(asset) FROM Asset asset");

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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETCATEGORIES,
			"Assets_AssetCategories", "getAssetCategories",
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

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETASSETCATEGORIES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETCATEGORIES,
			"Assets_AssetCategories", "getAssetCategoriesSize",
			new String[] { Long.class.getName() });

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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETCATEGORIES,
			"Assets_AssetCategories", "containsAssetCategory",
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			FinderCacheUtil.clearCache("Assets_AssetCategories");
		}
	}

	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			clearAssetCategories.clear(pk);

			for (long assetCategoryPK : assetCategoryPKs) {
				addAssetCategory.add(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetCategories");
		}
	}

	public void setAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			clearAssetCategories.clear(pk);

			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				addAssetCategory.add(pk, assetCategory.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetCategories");
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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETTAGS,
			"Assets_AssetTags", "getAssetTags",
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

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETASSETTAGS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETTAGS,
			"Assets_AssetTags", "getAssetTagsSize",
			new String[] { Long.class.getName() });

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
			AssetModelImpl.FINDER_CACHE_ENABLED_ASSETS_ASSETTAGS,
			"Assets_AssetTags", "containsAssetTag",
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
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
			FinderCacheUtil.clearCache("Assets_AssetTags");
		}
	}

	public void setAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			clearAssetTags.clear(pk);

			for (long assetTagPK : assetTagPKs) {
				addAssetTag.add(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetTags");
		}
	}

	public void setAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			clearAssetTags.clear(pk);

			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				addAssetTag.add(pk, assetTag.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Assets_AssetTags");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.Asset")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Asset>> listenersList = new ArrayList<ModelListener<Asset>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Asset>)Class.forName(
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

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetPersistence assetPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence.impl")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence.impl")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence.impl")
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
		protected ContainsAssetCategory(AssetPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETCATEGORY,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long assetId, long categoryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(assetId), new Long(categoryId)
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
		protected AddAssetCategory(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Assets_AssetCategories (assetId, categoryId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long assetId, long categoryId)
			throws SystemException {
			if (!_persistenceImpl.containsAssetCategory.contains(assetId,
						categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<Asset> listener : listeners) {
					listener.onBeforeAddAssociation(assetId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeAddAssociation(categoryId,
						Asset.class.getName(), assetId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(assetId), new Long(categoryId)
					});

				for (ModelListener<Asset> listener : listeners) {
					listener.onAfterAddAssociation(assetId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterAddAssociation(categoryId,
						Asset.class.getName(), assetId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetCategories {
		protected ClearAssetCategories(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Assets_AssetCategories WHERE assetId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long assetId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
				assetCategoryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetCategory> assetCategories = null;

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				assetCategories = getAssetCategories(assetId);

				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<Asset> listener : listeners) {
						listener.onBeforeRemoveAssociation(assetId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onBeforeRemoveAssociation(assetCategory.getPrimaryKey(),
							Asset.class.getName(), assetId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(assetId) });

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<Asset> listener : listeners) {
						listener.onAfterRemoveAssociation(assetId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onBeforeRemoveAssociation(assetCategory.getPrimaryKey(),
							Asset.class.getName(), assetId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetCategory {
		protected RemoveAssetCategory(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Assets_AssetCategories WHERE assetId = ? AND categoryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long assetId, long categoryId)
			throws SystemException {
			if (_persistenceImpl.containsAssetCategory.contains(assetId,
						categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<Asset> listener : listeners) {
					listener.onBeforeRemoveAssociation(assetId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						Asset.class.getName(), assetId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(assetId), new Long(categoryId)
					});

				for (ModelListener<Asset> listener : listeners) {
					listener.onAfterRemoveAssociation(assetId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterRemoveAssociation(categoryId,
						Asset.class.getName(), assetId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetPersistenceImpl _persistenceImpl;
	}

	protected class ContainsAssetTag {
		protected ContainsAssetTag(AssetPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETTAG,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long assetId, long tagId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(assetId), new Long(tagId)
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
		protected AddAssetTag(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Assets_AssetTags (assetId, tagId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long assetId, long tagId) throws SystemException {
			if (!_persistenceImpl.containsAssetTag.contains(assetId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<Asset> listener : listeners) {
					listener.onBeforeAddAssociation(assetId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeAddAssociation(tagId,
						Asset.class.getName(), assetId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(assetId), new Long(tagId)
					});

				for (ModelListener<Asset> listener : listeners) {
					listener.onAfterAddAssociation(assetId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterAddAssociation(tagId,
						Asset.class.getName(), assetId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetTags {
		protected ClearAssetTags(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Assets_AssetTags WHERE assetId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long assetId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
				assetTagPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetTag> assetTags = null;

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				assetTags = getAssetTags(assetId);

				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<Asset> listener : listeners) {
						listener.onBeforeRemoveAssociation(assetId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onBeforeRemoveAssociation(assetTag.getPrimaryKey(),
							Asset.class.getName(), assetId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(assetId) });

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<Asset> listener : listeners) {
						listener.onAfterRemoveAssociation(assetId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onBeforeRemoveAssociation(assetTag.getPrimaryKey(),
							Asset.class.getName(), assetId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetTag {
		protected RemoveAssetTag(AssetPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Assets_AssetTags WHERE assetId = ? AND tagId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long assetId, long tagId)
			throws SystemException {
			if (_persistenceImpl.containsAssetTag.contains(assetId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<Asset> listener : listeners) {
					listener.onBeforeRemoveAssociation(assetId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeRemoveAssociation(tagId,
						Asset.class.getName(), assetId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(assetId), new Long(tagId)
					});

				for (ModelListener<Asset> listener : listeners) {
					listener.onAfterRemoveAssociation(assetId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterRemoveAssociation(tagId,
						Asset.class.getName(), assetId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETASSETCATEGORIES = "SELECT {AssetCategory.*} FROM AssetCategory INNER JOIN Assets_AssetCategories ON (Assets_AssetCategories.categoryId = AssetCategory.categoryId) WHERE (Assets_AssetCategories.assetId = ?)";
	private static final String _SQL_GETASSETCATEGORIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Assets_AssetCategories WHERE assetId = ?";
	private static final String _SQL_CONTAINSASSETCATEGORY = "SELECT COUNT(*) AS COUNT_VALUE FROM Assets_AssetCategories WHERE assetId = ? AND categoryId = ?";
	private static final String _SQL_GETASSETTAGS = "SELECT {AssetTag.*} FROM AssetTag INNER JOIN Assets_AssetTags ON (Assets_AssetTags.tagId = AssetTag.tagId) WHERE (Assets_AssetTags.assetId = ?)";
	private static final String _SQL_GETASSETTAGSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Assets_AssetTags WHERE assetId = ?";
	private static final String _SQL_CONTAINSASSETTAG = "SELECT COUNT(*) AS COUNT_VALUE FROM Assets_AssetTags WHERE assetId = ? AND tagId = ?";
	private static Log _log = LogFactoryUtil.getLog(AssetPersistenceImpl.class);
}