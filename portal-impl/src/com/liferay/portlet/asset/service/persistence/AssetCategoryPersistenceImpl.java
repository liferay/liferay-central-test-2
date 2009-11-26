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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="AssetCategoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPersistence
 * @see       AssetCategoryUtil
 * @generated
 */
public class AssetCategoryPersistenceImpl extends BasePersistenceImpl<AssetCategory>
	implements AssetCategoryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByParentCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByParentCategoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByParentCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByVocabularyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByVocabularyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByVocabularyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByN_V",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByN_V",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByN_V",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AssetCategory assetCategory) {
		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetCategory.getUuid(), new Long(assetCategory.getGroupId())
			}, assetCategory);
	}

	public void cacheResult(List<AssetCategory> assetCategories) {
		for (AssetCategory assetCategory : assetCategories) {
			if (EntityCacheUtil.getResult(
						AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
						this) == null) {
				cacheResult(assetCategory);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetCategoryImpl.class.getName());
		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetCategory create(long categoryId) {
		AssetCategory assetCategory = new AssetCategoryImpl();

		assetCategory.setNew(true);
		assetCategory.setPrimaryKey(categoryId);

		String uuid = PortalUUIDUtil.generate();

		assetCategory.setUuid(uuid);

		return assetCategory;
	}

	public AssetCategory remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategory assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
					new Long(categoryId));

			if (assetCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No AssetCategory exists with the primary key " +
						categoryId);
				}

				throw new NoSuchCategoryException(
					"No AssetCategory exists with the primary key " +
					categoryId);
			}

			return remove(assetCategory);
		}
		catch (NoSuchCategoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetCategory remove(AssetCategory assetCategory)
		throws SystemException {
		for (ModelListener<AssetCategory> listener : listeners) {
			listener.onBeforeRemove(assetCategory);
		}

		assetCategory = removeImpl(assetCategory);

		for (ModelListener<AssetCategory> listener : listeners) {
			listener.onAfterRemove(assetCategory);
		}

		return assetCategory;
	}

	protected AssetCategory removeImpl(AssetCategory assetCategory)
		throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		try {
			clearAssetEntries.clear(assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
		}

		shrinkTree(assetCategory);

		Session session = null;

		try {
			session = openSession();

			if (assetCategory.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetCategoryImpl.class,
						assetCategory.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetCategory);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetCategoryModelImpl.getOriginalUuid(),
				new Long(assetCategoryModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey());

		return assetCategory;
	}

	public AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		boolean isNew = assetCategory.isNew();

		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		if (Validator.isNull(assetCategory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetCategory.setUuid(uuid);
		}

		if (isNew) {
			expandTree(assetCategory);
		}
		else {
			if (assetCategory.getParentCategoryId() != assetCategoryModelImpl.getOriginalParentCategoryId()) {
				shrinkTree(assetCategory);
				expandTree(assetCategory);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetCategory, merge);

			assetCategory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		if (!isNew &&
				(!Validator.equals(assetCategory.getUuid(),
					assetCategoryModelImpl.getOriginalUuid()) ||
				(assetCategory.getGroupId() != assetCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					assetCategoryModelImpl.getOriginalUuid(),
					new Long(assetCategoryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(assetCategory.getUuid(),
					assetCategoryModelImpl.getOriginalUuid()) ||
				(assetCategory.getGroupId() != assetCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					assetCategory.getUuid(),
					new Long(assetCategory.getGroupId())
				}, assetCategory);
		}

		return assetCategory;
	}

	protected AssetCategory toUnwrappedModel(AssetCategory assetCategory) {
		if (assetCategory instanceof AssetCategoryImpl) {
			return assetCategory;
		}

		AssetCategoryImpl assetCategoryImpl = new AssetCategoryImpl();

		assetCategoryImpl.setNew(assetCategory.isNew());
		assetCategoryImpl.setPrimaryKey(assetCategory.getPrimaryKey());

		assetCategoryImpl.setUuid(assetCategory.getUuid());
		assetCategoryImpl.setCategoryId(assetCategory.getCategoryId());
		assetCategoryImpl.setGroupId(assetCategory.getGroupId());
		assetCategoryImpl.setCompanyId(assetCategory.getCompanyId());
		assetCategoryImpl.setUserId(assetCategory.getUserId());
		assetCategoryImpl.setUserName(assetCategory.getUserName());
		assetCategoryImpl.setCreateDate(assetCategory.getCreateDate());
		assetCategoryImpl.setModifiedDate(assetCategory.getModifiedDate());
		assetCategoryImpl.setParentCategoryId(assetCategory.getParentCategoryId());
		assetCategoryImpl.setLeftCategoryId(assetCategory.getLeftCategoryId());
		assetCategoryImpl.setRightCategoryId(assetCategory.getRightCategoryId());
		assetCategoryImpl.setName(assetCategory.getName());
		assetCategoryImpl.setVocabularyId(assetCategory.getVocabularyId());

		return assetCategoryImpl;
	}

	public AssetCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByPrimaryKey(categoryId);

		if (assetCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AssetCategory exists with the primary key " +
					categoryId);
			}

			throw new NoSuchCategoryException(
				"No AssetCategory exists with the primary key " + categoryId);
		}

		return assetCategory;
	}

	public AssetCategory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		AssetCategory assetCategory = (AssetCategory)EntityCacheUtil.getResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryImpl.class, categoryId, this);

		if (assetCategory == null) {
			Session session = null;

			try {
				session = openSession();

				assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
						new Long(categoryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetCategory != null) {
					cacheResult(assetCategory);
				}

				closeSession(session);
			}
		}

		return assetCategory;
	}

	public List<AssetCategory> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				if (uuid == null) {
					query.append("assetCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(assetCategory.uuid IS NULL OR ");
					}

					query.append("assetCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

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
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<AssetCategory> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				if (uuid == null) {
					query.append("assetCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(assetCategory.uuid IS NULL OR ");
					}

					query.append("assetCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByUuid(uuid);

		List<AssetCategory> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByUuid_PrevAndNext(long categoryId, String uuid,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			if (uuid == null) {
				query.append("assetCategory.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(assetCategory.uuid IS NULL OR ");
				}

				query.append("assetCategory.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUUID_G(uuid, groupId);

		if (assetCategory == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryException(msg.toString());
		}

		return assetCategory;
	}

	public AssetCategory fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public AssetCategory fetchByUUID_G(String uuid, long groupId,
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

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				if (uuid == null) {
					query.append("assetCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(assetCategory.uuid IS NULL OR ");
					}

					query.append("assetCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("assetCategory.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<AssetCategory> list = q.list();

				result = list;

				AssetCategory assetCategory = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					assetCategory = list.get(0);

					cacheResult(assetCategory);

					if ((assetCategory.getUuid() == null) ||
							!assetCategory.getUuid().equals(uuid) ||
							(assetCategory.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, assetCategory);
					}
				}

				return assetCategory;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<AssetCategory>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetCategory)result;
			}
		}
	}

	public List<AssetCategory> findByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentCategoryId) };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PARENTCATEGORYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PARENTCATEGORYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end) throws SystemException {
		return findByParentCategoryId(parentCategoryId, start, end, null);
	}

	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentCategoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PARENTCATEGORYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PARENTCATEGORYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByParentCategoryId_First(long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByParentCategoryId(parentCategoryId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByParentCategoryId_Last(long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByParentCategoryId(parentCategoryId);

		List<AssetCategory> list = findByParentCategoryId(parentCategoryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByParentCategoryId_PrevAndNext(long categoryId,
		long parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByParentCategoryId(parentCategoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			query.append("assetCategory.parentCategoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentCategoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategory> findByVocabularyId(long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByVocabularyId_First(long vocabularyId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByVocabularyId(vocabularyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByVocabularyId_Last(long vocabularyId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByVocabularyId(vocabularyId);

		List<AssetCategory> list = findByVocabularyId(vocabularyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByVocabularyId_PrevAndNext(long categoryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByVocabularyId(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			query.append("assetCategory.vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategory> findByP_N(long parentCategoryId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentCategoryId), name };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_N, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentCategoryId, name, start, end, null);
	}

	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentCategoryId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByP_N_First(long parentCategoryId, String name,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByP_N(parentCategoryId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByP_N_Last(long parentCategoryId, String name,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByP_N(parentCategoryId, name);

		List<AssetCategory> list = findByP_N(parentCategoryId, name, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByP_N_PrevAndNext(long categoryId,
		long parentCategoryId, String name, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByP_N(parentCategoryId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			query.append("assetCategory.parentCategoryId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("assetCategory.name IS NULL");
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append("(assetCategory.name IS NULL OR ");
				}

				query.append("assetCategory.name = ?");

				if (name.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentCategoryId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentCategoryId), new Long(vocabularyId)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_V, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end) throws SystemException {
		return findByP_V(parentCategoryId, vocabularyId, start, end, null);
	}

	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentCategoryId), new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_V,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByP_V(parentCategoryId, vocabularyId);

		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByP_V_PrevAndNext(long categoryId,
		long parentCategoryId, long vocabularyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByP_V(parentCategoryId, vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			query.append("assetCategory.parentCategoryId = ?");

			query.append(" AND ");

			query.append("assetCategory.vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentCategoryId);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategory> findByN_V(String name, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { name, new Long(vocabularyId) };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_V, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end) throws SystemException {
		return findByN_V(name, vocabularyId, start, end, null);
	}

	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				name, new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_V,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategory findByN_V_First(String name, long vocabularyId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByN_V(name, vocabularyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("name=" + name);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory findByN_V_Last(String name, long vocabularyId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByN_V(name, vocabularyId);

		List<AssetCategory> list = findByN_V(name, vocabularyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategory exists with the key {");

			msg.append("name=" + name);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategory[] findByN_V_PrevAndNext(long categoryId, String name,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		int count = countByN_V(name, vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT assetCategory FROM AssetCategory assetCategory WHERE ");

			if (name == null) {
				query.append("assetCategory.name IS NULL");
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append("(assetCategory.name IS NULL OR ");
				}

				query.append("assetCategory.name = ?");

				if (name.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" AND ");

			query.append("assetCategory.vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("assetCategory.");
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

			else {
				query.append("ORDER BY ");

				query.append("assetCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategory);

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = (AssetCategory)objArray[0];
			array[1] = (AssetCategory)objArray[1];
			array[2] = (AssetCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<AssetCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetCategory> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT assetCategory FROM AssetCategory assetCategory ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("assetCategory.");
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

				else {
					query.append("ORDER BY ");

					query.append("assetCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (AssetCategory assetCategory : findByUuid(uuid)) {
			remove(assetCategory);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByUUID_G(uuid, groupId);

		remove(assetCategory);
	}

	public void removeByParentCategoryId(long parentCategoryId)
		throws SystemException {
		for (AssetCategory assetCategory : findByParentCategoryId(
				parentCategoryId)) {
			remove(assetCategory);
		}
	}

	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByVocabularyId(vocabularyId)) {
			remove(assetCategory);
		}
	}

	public void removeByP_N(long parentCategoryId, String name)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_N(parentCategoryId, name)) {
			remove(assetCategory);
		}
	}

	public void removeByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_V(parentCategoryId,
				vocabularyId)) {
			remove(assetCategory);
		}
	}

	public void removeByN_V(String name, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByN_V(name, vocabularyId)) {
			remove(assetCategory);
		}
	}

	public void removeAll() throws SystemException {
		for (AssetCategory assetCategory : findAll()) {
			remove(assetCategory);
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				if (uuid == null) {
					query.append("assetCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(assetCategory.uuid IS NULL OR ");
					}

					query.append("assetCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				if (uuid == null) {
					query.append("assetCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(assetCategory.uuid IS NULL OR ");
					}

					query.append("assetCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("assetCategory.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentCategoryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByVocabularyId(long vocabularyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_N(long parentCategoryId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentCategoryId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentCategoryId), new Long(vocabularyId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				query.append("assetCategory.parentCategoryId = ?");

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_V(String name, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { name, new Long(vocabularyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(assetCategory) ");
				query.append("FROM AssetCategory assetCategory WHERE ");

				if (name == null) {
					query.append("assetCategory.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(assetCategory.name IS NULL OR ");
					}

					query.append("assetCategory.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("assetCategory.vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_V, finderArgs,
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
						"SELECT COUNT(assetCategory) FROM AssetCategory assetCategory");

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
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			"AssetEntries_AssetCategories", "getAssetEntries",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.asset.model.AssetEntry> list = (List<com.liferay.portlet.asset.model.AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETENTRIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETASSETENTRIES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

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
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			"AssetEntries_AssetCategories", "getAssetEntriesSize",
			new String[] { Long.class.getName() });

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
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			"AssetEntries_AssetCategories", "containsAssetEntry",
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
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
			FinderCacheUtil.clearCache("AssetEntries_AssetCategories");
		}
	}

	public void rebuildTree(long groupId, boolean force)
		throws SystemException {
		if (force || (countOrphanTreeNodes(groupId) > 0)) {
			rebuildTree(groupId, 0, 1);

			CacheRegistry.clear(AssetCategoryImpl.class.getName());
			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}
	}

	protected long countOrphanTreeNodes(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT COUNT(*) AS COUNT_VALUE FROM AssetCategory WHERE groupId = ? AND (leftCategoryId = 0 OR leftCategoryId IS NULL OR rightCategoryId = 0 OR rightCategoryId IS NULL)");

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void expandTree(AssetCategory assetCategory)
		throws SystemException {
		long groupId = assetCategory.getGroupId();

		long lastRightCategoryId = getLastRightCategoryId(groupId,
				assetCategory.getParentCategoryId());

		long leftCategoryId = 2;
		long rightCategoryId = 3;

		if (lastRightCategoryId > 0) {
			leftCategoryId = lastRightCategoryId + 1;
			rightCategoryId = lastRightCategoryId + 2;

			expandTreeLeftCategoryId.expand(groupId, lastRightCategoryId);
			expandTreeRightCategoryId.expand(groupId, lastRightCategoryId);

			CacheRegistry.clear(AssetCategoryImpl.class.getName());
			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}

		assetCategory.setLeftCategoryId(leftCategoryId);
		assetCategory.setRightCategoryId(rightCategoryId);
	}

	protected long getLastRightCategoryId(long groupId, long parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT rightCategoryId FROM AssetCategory WHERE (groupId = ?) AND (parentCategoryId = ?) ORDER BY rightCategoryId DESC");

			q.addScalar("rightCategoryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			List<Long> list = (List<Long>)QueryUtil.list(q, getDialect(), 0, 1);

			if (list.isEmpty()) {
				if (parentCategoryId > 0) {
					AssetCategory parentAssetCategory = findByPrimaryKey(parentCategoryId);

					return parentAssetCategory.getLeftCategoryId();
				}

				return 0;
			}
			else {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(long groupId, long parentCategoryId,
		long leftCategoryId) throws SystemException {
		List<Long> categoryIds = null;

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT categoryId FROM AssetCategory WHERE groupId = ? AND parentCategoryId = ? ORDER BY categoryId ASC");

			q.addScalar("categoryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			categoryIds = q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		long rightCategoryId = leftCategoryId + 1;

		for (long categoryId : categoryIds) {
			rightCategoryId = rebuildTree(groupId, categoryId, rightCategoryId);
		}

		if (parentCategoryId > 0) {
			updateTree.update(parentCategoryId, leftCategoryId, rightCategoryId);
		}

		return rightCategoryId + 1;
	}

	protected void shrinkTree(AssetCategory assetCategory) {
		long groupId = assetCategory.getGroupId();

		long leftCategoryId = assetCategory.getLeftCategoryId();
		long rightCategoryId = assetCategory.getRightCategoryId();

		long delta = (rightCategoryId - leftCategoryId) + 1;

		shrinkTreeLeftCategoryId.shrink(groupId, rightCategoryId, delta);
		shrinkTreeRightCategoryId.shrink(groupId, rightCategoryId, delta);

		CacheRegistry.clear(AssetCategoryImpl.class.getName());
		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategory>> listenersList = new ArrayList<ModelListener<AssetCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategory>)Class.forName(
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

		expandTreeLeftCategoryId = new ExpandTreeLeftCategoryId();
		expandTreeRightCategoryId = new ExpandTreeRightCategoryId();
		shrinkTreeLeftCategoryId = new ShrinkTreeLeftCategoryId();
		shrinkTreeRightCategoryId = new ShrinkTreeRightCategoryId();
		updateTree = new UpdateTree();
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsAssetEntry containsAssetEntry;
	protected AddAssetEntry addAssetEntry;
	protected ClearAssetEntries clearAssetEntries;
	protected RemoveAssetEntry removeAssetEntry;

	protected class ContainsAssetEntry {
		protected ContainsAssetEntry(
			AssetCategoryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETENTRY,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long categoryId, long entryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(categoryId), new Long(entryId)
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

	protected class AddAssetEntry {
		protected AddAssetEntry(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetCategories (categoryId, entryId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long categoryId, long entryId)
			throws SystemException {
			if (!_persistenceImpl.containsAssetEntry.contains(categoryId,
						entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeAddAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeAddAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(entryId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterAddAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterAddAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetEntries {
		protected ClearAssetEntries(
			AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE categoryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long categoryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
				assetEntryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetEntry> assetEntries = null;

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				assetEntries = getAssetEntries(categoryId);

				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onBeforeRemoveAssociation(categoryId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onBeforeRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(categoryId) });

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onAfterRemoveAssociation(categoryId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onAfterRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetEntry {
		protected RemoveAssetEntry(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE categoryId = ? AND entryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long categoryId, long entryId)
			throws SystemException {
			if (_persistenceImpl.containsAssetEntry.contains(categoryId, entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeRemoveAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(entryId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterRemoveAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterRemoveAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	protected ExpandTreeLeftCategoryId expandTreeLeftCategoryId;
	protected ExpandTreeRightCategoryId expandTreeRightCategoryId;
	protected ShrinkTreeLeftCategoryId shrinkTreeLeftCategoryId;
	protected ShrinkTreeRightCategoryId shrinkTreeRightCategoryId;
	protected UpdateTree updateTree;

	protected class ExpandTreeLeftCategoryId {
		protected ExpandTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId + 2) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
		}

		protected void expand(long groupId, long leftCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, leftCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ExpandTreeRightCategoryId {
		protected ExpandTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId + 2) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
		}

		protected void expand(long groupId, long rightCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeLeftCategoryId {
		protected ShrinkTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId - ?) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void shrink(long groupId, long leftCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, leftCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeRightCategoryId {
		protected ShrinkTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId - ?) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void shrink(long groupId, long rightCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class UpdateTree {
		protected UpdateTree() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = ?, rightCategoryId = ? WHERE categoryId = ?",
					new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT });
		}

		protected void update(long categoryId, long leftCategoryId,
			long rightCategoryId) {
			_sqlUpdate.update(new Object[] {
					leftCategoryId, rightCategoryId, categoryId
				});
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_GETASSETENTRIES = "SELECT {AssetEntry.*} FROM AssetEntry INNER JOIN AssetEntries_AssetCategories ON (AssetEntries_AssetCategories.entryId = AssetEntry.entryId) WHERE (AssetEntries_AssetCategories.categoryId = ?)";
	private static final String _SQL_GETASSETENTRIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE categoryId = ?";
	private static final String _SQL_CONTAINSASSETENTRY = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE categoryId = ? AND entryId = ?";
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPersistenceImpl.class);
}