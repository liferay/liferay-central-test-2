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

package com.liferay.portlet.categories.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
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

import com.liferay.portlet.categories.NoSuchVocabularyException;
import com.liferay.portlet.categories.model.CategoriesVocabulary;
import com.liferay.portlet.categories.model.impl.CategoriesVocabularyImpl;
import com.liferay.portlet.categories.model.impl.CategoriesVocabularyModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CategoriesVocabularyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesVocabularyPersistenceImpl extends BasePersistenceImpl
	implements CategoriesVocabularyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CategoriesVocabularyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(CategoriesVocabulary categoriesVocabulary) {
		EntityCacheUtil.putResult(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyImpl.class,
			categoriesVocabulary.getPrimaryKey(), categoriesVocabulary);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(categoriesVocabulary.getGroupId()),
				
			categoriesVocabulary.getName()
			}, categoriesVocabulary);
	}

	public void cacheResult(List<CategoriesVocabulary> categoriesVocabularies) {
		for (CategoriesVocabulary categoriesVocabulary : categoriesVocabularies) {
			if (EntityCacheUtil.getResult(
						CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
						CategoriesVocabularyImpl.class,
						categoriesVocabulary.getPrimaryKey(), this) == null) {
				cacheResult(categoriesVocabulary);
			}
		}
	}

	public CategoriesVocabulary create(long vocabularyId) {
		CategoriesVocabulary categoriesVocabulary = new CategoriesVocabularyImpl();

		categoriesVocabulary.setNew(true);
		categoriesVocabulary.setPrimaryKey(vocabularyId);

		return categoriesVocabulary;
	}

	public CategoriesVocabulary remove(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CategoriesVocabulary categoriesVocabulary = (CategoriesVocabulary)session.get(CategoriesVocabularyImpl.class,
					new Long(vocabularyId));

			if (categoriesVocabulary == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No CategoriesVocabulary exists with the primary key " +
						vocabularyId);
				}

				throw new NoSuchVocabularyException(
					"No CategoriesVocabulary exists with the primary key " +
					vocabularyId);
			}

			return remove(categoriesVocabulary);
		}
		catch (NoSuchVocabularyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public CategoriesVocabulary remove(
		CategoriesVocabulary categoriesVocabulary) throws SystemException {
		for (ModelListener<CategoriesVocabulary> listener : listeners) {
			listener.onBeforeRemove(categoriesVocabulary);
		}

		categoriesVocabulary = removeImpl(categoriesVocabulary);

		for (ModelListener<CategoriesVocabulary> listener : listeners) {
			listener.onAfterRemove(categoriesVocabulary);
		}

		return categoriesVocabulary;
	}

	protected CategoriesVocabulary removeImpl(
		CategoriesVocabulary categoriesVocabulary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (categoriesVocabulary.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CategoriesVocabularyImpl.class,
						categoriesVocabulary.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(categoriesVocabulary);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		CategoriesVocabularyModelImpl categoriesVocabularyModelImpl = (CategoriesVocabularyModelImpl)categoriesVocabulary;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(categoriesVocabularyModelImpl.getOriginalGroupId()),
				
			categoriesVocabularyModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyImpl.class, categoriesVocabulary.getPrimaryKey());

		return categoriesVocabulary;
	}

	/**
	 * @deprecated Use <code>update(CategoriesVocabulary categoriesVocabulary, boolean merge)</code>.
	 */
	public CategoriesVocabulary update(
		CategoriesVocabulary categoriesVocabulary) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(CategoriesVocabulary categoriesVocabulary) method. Use update(CategoriesVocabulary categoriesVocabulary, boolean merge) instead.");
		}

		return update(categoriesVocabulary, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        categoriesVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when categoriesVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public CategoriesVocabulary update(
		CategoriesVocabulary categoriesVocabulary, boolean merge)
		throws SystemException {
		boolean isNew = categoriesVocabulary.isNew();

		for (ModelListener<CategoriesVocabulary> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(categoriesVocabulary);
			}
			else {
				listener.onBeforeUpdate(categoriesVocabulary);
			}
		}

		categoriesVocabulary = updateImpl(categoriesVocabulary, merge);

		for (ModelListener<CategoriesVocabulary> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(categoriesVocabulary);
			}
			else {
				listener.onAfterUpdate(categoriesVocabulary);
			}
		}

		return categoriesVocabulary;
	}

	public CategoriesVocabulary updateImpl(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary,
		boolean merge) throws SystemException {
		boolean isNew = categoriesVocabulary.isNew();

		CategoriesVocabularyModelImpl categoriesVocabularyModelImpl = (CategoriesVocabularyModelImpl)categoriesVocabulary;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, categoriesVocabulary, merge);

			categoriesVocabulary.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesVocabularyImpl.class,
			categoriesVocabulary.getPrimaryKey(), categoriesVocabulary);

		if (!isNew &&
				((categoriesVocabulary.getGroupId() != categoriesVocabularyModelImpl.getOriginalGroupId()) ||
				!categoriesVocabulary.getName()
										 .equals(categoriesVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(categoriesVocabularyModelImpl.getOriginalGroupId()),
					
				categoriesVocabularyModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((categoriesVocabulary.getGroupId() != categoriesVocabularyModelImpl.getOriginalGroupId()) ||
				!categoriesVocabulary.getName()
										 .equals(categoriesVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(categoriesVocabulary.getGroupId()),
					
				categoriesVocabulary.getName()
				}, categoriesVocabulary);
		}

		return categoriesVocabulary;
	}

	public CategoriesVocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		CategoriesVocabulary categoriesVocabulary = fetchByPrimaryKey(vocabularyId);

		if (categoriesVocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No CategoriesVocabulary exists with the primary key " +
					vocabularyId);
			}

			throw new NoSuchVocabularyException(
				"No CategoriesVocabulary exists with the primary key " +
				vocabularyId);
		}

		return categoriesVocabulary;
	}

	public CategoriesVocabulary fetchByPrimaryKey(long vocabularyId)
		throws SystemException {
		CategoriesVocabulary categoriesVocabulary = (CategoriesVocabulary)EntityCacheUtil.getResult(CategoriesVocabularyModelImpl.ENTITY_CACHE_ENABLED,
				CategoriesVocabularyImpl.class, vocabularyId, this);

		if (categoriesVocabulary == null) {
			Session session = null;

			try {
				session = openSession();

				categoriesVocabulary = (CategoriesVocabulary)session.get(CategoriesVocabularyImpl.class,
						new Long(vocabularyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (categoriesVocabulary != null) {
					cacheResult(categoriesVocabulary);
				}

				closeSession(session);
			}
		}

		return categoriesVocabulary;
	}

	public CategoriesVocabulary findByG_N(long groupId, String name)
		throws NoSuchVocabularyException, SystemException {
		CategoriesVocabulary categoriesVocabulary = fetchByG_N(groupId, name);

		if (categoriesVocabulary == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVocabularyException(msg.toString());
		}

		return categoriesVocabulary;
	}

	public CategoriesVocabulary fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	public CategoriesVocabulary fetchByG_N(long groupId, String name,
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
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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

				List<CategoriesVocabulary> list = q.list();

				result = list;

				CategoriesVocabulary categoriesVocabulary = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					categoriesVocabulary = list.get(0);

					cacheResult(categoriesVocabulary);
				}

				return categoriesVocabulary;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, new ArrayList<CategoriesVocabulary>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (CategoriesVocabulary)result;
			}
		}
	}

	public List<CategoriesVocabulary> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<CategoriesVocabulary> list = (List<CategoriesVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					list = new ArrayList<CategoriesVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesVocabulary> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<CategoriesVocabulary> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesVocabulary> list = (List<CategoriesVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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

				list = (List<CategoriesVocabulary>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesVocabulary findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<CategoriesVocabulary> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesVocabulary findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByGroupId(groupId);

		List<CategoriesVocabulary> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesVocabulary[] findByGroupId_PrevAndNext(long vocabularyId,
		long groupId, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		CategoriesVocabulary categoriesVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					categoriesVocabulary);

			CategoriesVocabulary[] array = new CategoriesVocabularyImpl[3];

			array[0] = (CategoriesVocabulary)objArray[0];
			array[1] = (CategoriesVocabulary)objArray[1];
			array[2] = (CategoriesVocabulary)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesVocabulary> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<CategoriesVocabulary> list = (List<CategoriesVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					list = new ArrayList<CategoriesVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesVocabulary> findByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<CategoriesVocabulary> findByCompanyId(long companyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesVocabulary> list = (List<CategoriesVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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

				list = (List<CategoriesVocabulary>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesVocabulary findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<CategoriesVocabulary> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesVocabulary findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByCompanyId(companyId);

		List<CategoriesVocabulary> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		CategoriesVocabulary categoriesVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					categoriesVocabulary);

			CategoriesVocabulary[] array = new CategoriesVocabularyImpl[3];

			array[0] = (CategoriesVocabulary)objArray[0];
			array[1] = (CategoriesVocabulary)objArray[1];
			array[2] = (CategoriesVocabulary)objArray[2];

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

	public List<CategoriesVocabulary> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<CategoriesVocabulary> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<CategoriesVocabulary> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesVocabulary> list = (List<CategoriesVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary ");

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
					list = (List<CategoriesVocabulary>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CategoriesVocabulary>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByG_N(long groupId, String name)
		throws NoSuchVocabularyException, SystemException {
		CategoriesVocabulary categoriesVocabulary = findByG_N(groupId, name);

		remove(categoriesVocabulary);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (CategoriesVocabulary categoriesVocabulary : findByGroupId(groupId)) {
			remove(categoriesVocabulary);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (CategoriesVocabulary categoriesVocabulary : findByCompanyId(
				companyId)) {
			remove(categoriesVocabulary);
		}
	}

	public void removeAll() throws SystemException {
		for (CategoriesVocabulary categoriesVocabulary : findAll()) {
			remove(categoriesVocabulary);
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
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
					"FROM com.liferay.portlet.categories.model.CategoriesVocabulary WHERE ");

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
						"SELECT COUNT(*) FROM com.liferay.portlet.categories.model.CategoriesVocabulary");

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
						"value.object.listener.com.liferay.portlet.categories.model.CategoriesVocabulary")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CategoriesVocabulary>> listenersList = new ArrayList<ModelListener<CategoriesVocabulary>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CategoriesVocabulary>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence categoriesEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence categoriesPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence categoriesVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(CategoriesVocabularyPersistenceImpl.class);
}