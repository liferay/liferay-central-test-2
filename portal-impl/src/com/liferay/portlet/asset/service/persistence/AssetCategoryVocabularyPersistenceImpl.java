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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchCategoryVocabularyException;
import com.liferay.portlet.asset.model.AssetCategoryVocabulary;
import com.liferay.portlet.asset.model.impl.AssetCategoryVocabularyImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryVocabularyModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetCategoryVocabularyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryVocabularyPersistenceImpl extends BasePersistenceImpl
	implements AssetCategoryVocabularyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryVocabularyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AssetCategoryVocabulary assetCategoryVocabulary) {
		EntityCacheUtil.putResult(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyImpl.class,
			assetCategoryVocabulary.getPrimaryKey(), assetCategoryVocabulary);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(assetCategoryVocabulary.getGroupId()),
				
			assetCategoryVocabulary.getName()
			}, assetCategoryVocabulary);
	}

	public void cacheResult(
		List<AssetCategoryVocabulary> assetCategoryVocabularies) {
		for (AssetCategoryVocabulary assetCategoryVocabulary : assetCategoryVocabularies) {
			if (EntityCacheUtil.getResult(
						AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryVocabularyImpl.class,
						assetCategoryVocabulary.getPrimaryKey(), this) == null) {
				cacheResult(assetCategoryVocabulary);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetCategoryVocabularyImpl.class.getName());
		EntityCacheUtil.clearCache(AssetCategoryVocabularyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetCategoryVocabulary create(long vocabularyId) {
		AssetCategoryVocabulary assetCategoryVocabulary = new AssetCategoryVocabularyImpl();

		assetCategoryVocabulary.setNew(true);
		assetCategoryVocabulary.setPrimaryKey(vocabularyId);

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary remove(long vocabularyId)
		throws NoSuchCategoryVocabularyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategoryVocabulary assetCategoryVocabulary = (AssetCategoryVocabulary)session.get(AssetCategoryVocabularyImpl.class,
					new Long(vocabularyId));

			if (assetCategoryVocabulary == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AssetCategoryVocabulary exists with the primary key " +
						vocabularyId);
				}

				throw new NoSuchCategoryVocabularyException(
					"No AssetCategoryVocabulary exists with the primary key " +
					vocabularyId);
			}

			return remove(assetCategoryVocabulary);
		}
		catch (NoSuchCategoryVocabularyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetCategoryVocabulary remove(
		AssetCategoryVocabulary assetCategoryVocabulary)
		throws SystemException {
		for (ModelListener<AssetCategoryVocabulary> listener : listeners) {
			listener.onBeforeRemove(assetCategoryVocabulary);
		}

		assetCategoryVocabulary = removeImpl(assetCategoryVocabulary);

		for (ModelListener<AssetCategoryVocabulary> listener : listeners) {
			listener.onAfterRemove(assetCategoryVocabulary);
		}

		return assetCategoryVocabulary;
	}

	protected AssetCategoryVocabulary removeImpl(
		AssetCategoryVocabulary assetCategoryVocabulary)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (assetCategoryVocabulary.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetCategoryVocabularyImpl.class,
						assetCategoryVocabulary.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetCategoryVocabulary);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetCategoryVocabularyModelImpl assetCategoryVocabularyModelImpl = (AssetCategoryVocabularyModelImpl)assetCategoryVocabulary;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(assetCategoryVocabularyModelImpl.getOriginalGroupId()),
				
			assetCategoryVocabularyModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyImpl.class,
			assetCategoryVocabulary.getPrimaryKey());

		return assetCategoryVocabulary;
	}

	/**
	 * @deprecated Use <code>update(AssetCategoryVocabulary assetCategoryVocabulary, boolean merge)</code>.
	 */
	public AssetCategoryVocabulary update(
		AssetCategoryVocabulary assetCategoryVocabulary)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AssetCategoryVocabulary assetCategoryVocabulary) method. Use update(AssetCategoryVocabulary assetCategoryVocabulary, boolean merge) instead.");
		}

		return update(assetCategoryVocabulary, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetCategoryVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetCategoryVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public AssetCategoryVocabulary update(
		AssetCategoryVocabulary assetCategoryVocabulary, boolean merge)
		throws SystemException {
		boolean isNew = assetCategoryVocabulary.isNew();

		for (ModelListener<AssetCategoryVocabulary> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(assetCategoryVocabulary);
			}
			else {
				listener.onBeforeUpdate(assetCategoryVocabulary);
			}
		}

		assetCategoryVocabulary = updateImpl(assetCategoryVocabulary, merge);

		for (ModelListener<AssetCategoryVocabulary> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(assetCategoryVocabulary);
			}
			else {
				listener.onAfterUpdate(assetCategoryVocabulary);
			}
		}

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary,
		boolean merge) throws SystemException {
		boolean isNew = assetCategoryVocabulary.isNew();

		AssetCategoryVocabularyModelImpl assetCategoryVocabularyModelImpl = (AssetCategoryVocabularyModelImpl)assetCategoryVocabulary;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetCategoryVocabulary, merge);

			assetCategoryVocabulary.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryVocabularyImpl.class,
			assetCategoryVocabulary.getPrimaryKey(), assetCategoryVocabulary);

		if (!isNew &&
				((assetCategoryVocabulary.getGroupId() != assetCategoryVocabularyModelImpl.getOriginalGroupId()) ||
				!assetCategoryVocabulary.getName()
											.equals(assetCategoryVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(assetCategoryVocabularyModelImpl.getOriginalGroupId()),
					
				assetCategoryVocabularyModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((assetCategoryVocabulary.getGroupId() != assetCategoryVocabularyModelImpl.getOriginalGroupId()) ||
				!assetCategoryVocabulary.getName()
											.equals(assetCategoryVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(assetCategoryVocabulary.getGroupId()),
					
				assetCategoryVocabulary.getName()
				}, assetCategoryVocabulary);
		}

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchCategoryVocabularyException, SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = fetchByPrimaryKey(vocabularyId);

		if (assetCategoryVocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No AssetCategoryVocabulary exists with the primary key " +
					vocabularyId);
			}

			throw new NoSuchCategoryVocabularyException(
				"No AssetCategoryVocabulary exists with the primary key " +
				vocabularyId);
		}

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary fetchByPrimaryKey(long vocabularyId)
		throws SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = (AssetCategoryVocabulary)EntityCacheUtil.getResult(AssetCategoryVocabularyModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryVocabularyImpl.class, vocabularyId, this);

		if (assetCategoryVocabulary == null) {
			Session session = null;

			try {
				session = openSession();

				assetCategoryVocabulary = (AssetCategoryVocabulary)session.get(AssetCategoryVocabularyImpl.class,
						new Long(vocabularyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetCategoryVocabulary != null) {
					cacheResult(assetCategoryVocabulary);
				}

				closeSession(session);
			}
		}

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary findByG_N(long groupId, String name)
		throws NoSuchCategoryVocabularyException, SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = fetchByG_N(groupId,
				name);

		if (assetCategoryVocabulary == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategoryVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryVocabularyException(msg.toString());
		}

		return assetCategoryVocabulary;
	}

	public AssetCategoryVocabulary fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	public AssetCategoryVocabulary fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("groupId = ?");

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

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<AssetCategoryVocabulary> list = q.list();

				result = list;

				AssetCategoryVocabulary assetCategoryVocabulary = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					assetCategoryVocabulary = list.get(0);

					cacheResult(assetCategoryVocabulary);

					if ((assetCategoryVocabulary.getGroupId() != groupId) ||
							(assetCategoryVocabulary.getName() == null) ||
							!assetCategoryVocabulary.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, list);
					}
				}

				return assetCategoryVocabulary;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, new ArrayList<AssetCategoryVocabulary>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (AssetCategoryVocabulary)result;
			}
		}
	}

	public List<AssetCategoryVocabulary> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<AssetCategoryVocabulary> list = (List<AssetCategoryVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategoryVocabulary> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<AssetCategoryVocabulary> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategoryVocabulary> list = (List<AssetCategoryVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("groupId = ?");

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

				qPos.add(groupId);

				list = (List<AssetCategoryVocabulary>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategoryVocabulary findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		List<AssetCategoryVocabulary> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategoryVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryVocabulary findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		int count = countByGroupId(groupId);

		List<AssetCategoryVocabulary> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategoryVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryVocabulary[] findByGroupId_PrevAndNext(
		long vocabularyId, long groupId, OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

			query.append("groupId = ?");

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

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategoryVocabulary);

			AssetCategoryVocabulary[] array = new AssetCategoryVocabularyImpl[3];

			array[0] = (AssetCategoryVocabulary)objArray[0];
			array[1] = (AssetCategoryVocabulary)objArray[1];
			array[2] = (AssetCategoryVocabulary)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategoryVocabulary> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<AssetCategoryVocabulary> list = (List<AssetCategoryVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

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
					list = new ArrayList<AssetCategoryVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategoryVocabulary> findByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<AssetCategoryVocabulary> findByCompanyId(long companyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategoryVocabulary> list = (List<AssetCategoryVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("companyId = ?");

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

				qPos.add(companyId);

				list = (List<AssetCategoryVocabulary>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategoryVocabulary findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		List<AssetCategoryVocabulary> list = findByCompanyId(companyId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategoryVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryVocabulary findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		int count = countByCompanyId(companyId);

		List<AssetCategoryVocabulary> list = findByCompanyId(companyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AssetCategoryVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId, OrderByComparator obc)
		throws NoSuchCategoryVocabularyException, SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

			query.append("companyId = ?");

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

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					assetCategoryVocabulary);

			AssetCategoryVocabulary[] array = new AssetCategoryVocabularyImpl[3];

			array[0] = (AssetCategoryVocabulary)objArray[0];
			array[1] = (AssetCategoryVocabulary)objArray[1];
			array[2] = (AssetCategoryVocabulary)objArray[2];

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

	public List<AssetCategoryVocabulary> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetCategoryVocabulary> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetCategoryVocabulary> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AssetCategoryVocabulary> list = (List<AssetCategoryVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary ");

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
					list = (List<AssetCategoryVocabulary>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetCategoryVocabulary>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByG_N(long groupId, String name)
		throws NoSuchCategoryVocabularyException, SystemException {
		AssetCategoryVocabulary assetCategoryVocabulary = findByG_N(groupId,
				name);

		remove(assetCategoryVocabulary);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (AssetCategoryVocabulary assetCategoryVocabulary : findByGroupId(
				groupId)) {
			remove(assetCategoryVocabulary);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetCategoryVocabulary assetCategoryVocabulary : findByCompanyId(
				companyId)) {
			remove(assetCategoryVocabulary);
		}
	}

	public void removeAll() throws SystemException {
		for (AssetCategoryVocabulary assetCategoryVocabulary : findAll()) {
			remove(assetCategoryVocabulary);
		}
	}

	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("groupId = ?");

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

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary WHERE ");

				query.append("companyId = ?");

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.asset.model.AssetCategoryVocabulary");

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
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategoryVocabulary")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategoryVocabulary>> listenersList = new ArrayList<ModelListener<AssetCategoryVocabulary>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategoryVocabulary>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence.impl")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryVocabularyPersistence assetCategoryVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryVocabularyPersistenceImpl.class);
}