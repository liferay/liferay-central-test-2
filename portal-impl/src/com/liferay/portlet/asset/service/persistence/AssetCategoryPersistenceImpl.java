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

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetCategoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryPersistenceImpl extends BasePersistenceImpl
	implements AssetCategoryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
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

		return assetCategory;
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
		try {
			clearTagsAssets.clear(assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}

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

		EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey());

		return assetCategory;
	}

	/**
	 * @deprecated Use <code>update(AssetCategory assetCategory, boolean merge)</code>.
	 */
	public AssetCategory update(AssetCategory assetCategory)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AssetCategory assetCategory) method. Use update(AssetCategory assetCategory, boolean merge) instead.");
		}

		return update(assetCategory, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetCategory the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetCategory is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public AssetCategory update(AssetCategory assetCategory, boolean merge)
		throws SystemException {
		boolean isNew = assetCategory.isNew();

		for (ModelListener<AssetCategory> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(assetCategory);
			}
			else {
				listener.onBeforeUpdate(assetCategory);
			}
		}

		assetCategory = updateImpl(assetCategory, merge);

		for (ModelListener<AssetCategory> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(assetCategory);
			}
			else {
				listener.onAfterUpdate(assetCategory);
			}
		}

		return assetCategory;
	}

	public AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws SystemException {
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

		return assetCategory;
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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
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
				"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

			query.append("parentCategoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
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
				"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

			query.append("vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
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
				"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

			query.append("parentCategoryId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

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
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
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
				"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

			query.append("parentCategoryId = ?");

			query.append(" AND ");

			query.append("vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
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
					"FROM com.liferay.portlet.asset.model.AssetCategory ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
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

	public void removeAll() throws SystemException {
		for (AssetCategory assetCategory : findAll()) {
			remove(assetCategory);
		}
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("vocabularyId = ?");

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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategory WHERE ");

				query.append("parentCategoryId = ?");

				query.append(" AND ");

				query.append("vocabularyId = ?");

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.asset.model.AssetCategory");

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

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(long pk)
		throws SystemException {
		return getTagsAssets(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws SystemException {
		return getTagsAssets(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TAGSASSETS = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_ASSETCATEGORIES,
			"TagsAssets_AssetCategories", "getTagsAssets",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.tags.model.TagsAsset> list = (List<com.liferay.portlet.tags.model.TagsAsset>)FinderCacheUtil.getResult(FINDER_PATH_GET_TAGSASSETS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETTAGSASSETS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("TagsAsset",
					com.liferay.portlet.tags.model.impl.TagsAssetImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.tags.model.TagsAsset>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.tags.model.TagsAsset>();
				}

				tagsAssetPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_TAGSASSETS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TAGSASSETS_SIZE = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_ASSETCATEGORIES,
			"TagsAssets_AssetCategories", "getTagsAssetsSize",
			new String[] { Long.class.getName() });

	public int getTagsAssetsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TAGSASSETS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTAGSASSETSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_TAGSASSETS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TAGSASSET = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_ASSETCATEGORIES,
			"TagsAssets_AssetCategories", "containsTagsAsset",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(tagsAssetPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TAGSASSET,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTagsAsset.contains(pk,
							tagsAssetPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TAGSASSET,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsTagsAssets(long pk) throws SystemException {
		if (getTagsAssetsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void addTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void clearTagsAssets(long pk) throws SystemException {
		try {
			clearTagsAssets.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void removeTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				removeTagsAsset.remove(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void removeTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
	}

	public void setTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_AssetCategories");
		}
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

		containsTagsAsset = new ContainsTagsAsset(this);

		addTagsAsset = new AddTagsAsset(this);
		clearTagsAssets = new ClearTagsAssets(this);
		removeTagsAsset = new RemoveTagsAsset(this);
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence assetCategoryVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	protected ContainsTagsAsset containsTagsAsset;
	protected AddTagsAsset addTagsAsset;
	protected ClearTagsAssets clearTagsAssets;
	protected RemoveTagsAsset removeTagsAsset;

	protected class ContainsTagsAsset {
		protected ContainsTagsAsset(
			AssetCategoryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTAGSASSET,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long categoryId, long assetId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(categoryId), new Long(assetId)
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

	protected class AddTagsAsset {
		protected AddTagsAsset(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO TagsAssets_AssetCategories (categoryId, assetId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long categoryId, long assetId)
			throws SystemException {
			if (!_persistenceImpl.containsTagsAsset.contains(categoryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeAddAssociation(categoryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeAddAssociation(assetId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(assetId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterAddAssociation(categoryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterAddAssociation(assetId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	protected class ClearTagsAssets {
		protected ClearTagsAssets(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_AssetCategories WHERE categoryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long categoryId) throws SystemException {
			ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
				tagsAssetPersistence.getListeners();

			List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets = null;

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				tagsAssets = getTagsAssets(categoryId);

				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onBeforeRemoveAssociation(categoryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(categoryId) });

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onAfterRemoveAssociation(categoryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTagsAsset {
		protected RemoveTagsAsset(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_AssetCategories WHERE categoryId = ? AND assetId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long categoryId, long assetId)
			throws SystemException {
			if (_persistenceImpl.containsTagsAsset.contains(categoryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeRemoveAssociation(assetId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(assetId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterRemoveAssociation(categoryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterRemoveAssociation(assetId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETTAGSASSETS = "SELECT {TagsAsset.*} FROM TagsAsset INNER JOIN TagsAssets_AssetCategories ON (TagsAssets_AssetCategories.assetId = TagsAsset.assetId) WHERE (TagsAssets_AssetCategories.categoryId = ?)";
	private static final String _SQL_GETTAGSASSETSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_AssetCategories WHERE categoryId = ?";
	private static final String _SQL_CONTAINSTAGSASSET = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_AssetCategories WHERE categoryId = ? AND assetId = ?";
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPersistenceImpl.class);
}