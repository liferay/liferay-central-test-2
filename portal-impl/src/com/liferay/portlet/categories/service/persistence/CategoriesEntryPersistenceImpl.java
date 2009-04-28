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

import com.liferay.portlet.categories.NoSuchEntryException;
import com.liferay.portlet.categories.model.CategoriesEntry;
import com.liferay.portlet.categories.model.impl.CategoriesEntryImpl;
import com.liferay.portlet.categories.model.impl.CategoriesEntryModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CategoriesEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesEntryPersistenceImpl extends BasePersistenceImpl
	implements CategoriesEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CategoriesEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_VOCABULARYID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByVocabularyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_VOCABULARYID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByVocabularyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_VOCABULARYID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByVocabularyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PARENTID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByParentId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PARENTID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByParentId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTID = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByParentId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_N = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_N = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_V_P = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByV_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_V_P = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByV_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_V_P = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByV_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(CategoriesEntry categoriesEntry) {
		EntityCacheUtil.putResult(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryImpl.class, categoriesEntry.getPrimaryKey(),
			categoriesEntry);
	}

	public void cacheResult(List<CategoriesEntry> categoriesEntries) {
		for (CategoriesEntry categoriesEntry : categoriesEntries) {
			if (EntityCacheUtil.getResult(
						CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
						CategoriesEntryImpl.class,
						categoriesEntry.getPrimaryKey(), this) == null) {
				cacheResult(categoriesEntry);
			}
		}
	}

	public CategoriesEntry create(long entryId) {
		CategoriesEntry categoriesEntry = new CategoriesEntryImpl();

		categoriesEntry.setNew(true);
		categoriesEntry.setPrimaryKey(entryId);

		return categoriesEntry;
	}

	public CategoriesEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CategoriesEntry categoriesEntry = (CategoriesEntry)session.get(CategoriesEntryImpl.class,
					new Long(entryId));

			if (categoriesEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No CategoriesEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No CategoriesEntry exists with the primary key " +
					entryId);
			}

			return remove(categoriesEntry);
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

	public CategoriesEntry remove(CategoriesEntry categoriesEntry)
		throws SystemException {
		for (ModelListener<CategoriesEntry> listener : listeners) {
			listener.onBeforeRemove(categoriesEntry);
		}

		categoriesEntry = removeImpl(categoriesEntry);

		for (ModelListener<CategoriesEntry> listener : listeners) {
			listener.onAfterRemove(categoriesEntry);
		}

		return categoriesEntry;
	}

	protected CategoriesEntry removeImpl(CategoriesEntry categoriesEntry)
		throws SystemException {
		try {
			clearTagsAssets.clear(categoriesEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
		}

		Session session = null;

		try {
			session = openSession();

			if (categoriesEntry.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CategoriesEntryImpl.class,
						categoriesEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(categoriesEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryImpl.class, categoriesEntry.getPrimaryKey());

		return categoriesEntry;
	}

	/**
	 * @deprecated Use <code>update(CategoriesEntry categoriesEntry, boolean merge)</code>.
	 */
	public CategoriesEntry update(CategoriesEntry categoriesEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(CategoriesEntry categoriesEntry) method. Use update(CategoriesEntry categoriesEntry, boolean merge) instead.");
		}

		return update(categoriesEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        categoriesEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when categoriesEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public CategoriesEntry update(CategoriesEntry categoriesEntry, boolean merge)
		throws SystemException {
		boolean isNew = categoriesEntry.isNew();

		for (ModelListener<CategoriesEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(categoriesEntry);
			}
			else {
				listener.onBeforeUpdate(categoriesEntry);
			}
		}

		categoriesEntry = updateImpl(categoriesEntry, merge);

		for (ModelListener<CategoriesEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(categoriesEntry);
			}
			else {
				listener.onAfterUpdate(categoriesEntry);
			}
		}

		return categoriesEntry;
	}

	public CategoriesEntry updateImpl(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, categoriesEntry, merge);

			categoriesEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
			CategoriesEntryImpl.class, categoriesEntry.getPrimaryKey(),
			categoriesEntry);

		return categoriesEntry;
	}

	public CategoriesEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		CategoriesEntry categoriesEntry = fetchByPrimaryKey(entryId);

		if (categoriesEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No CategoriesEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No CategoriesEntry exists with the primary key " + entryId);
		}

		return categoriesEntry;
	}

	public CategoriesEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		CategoriesEntry categoriesEntry = (CategoriesEntry)EntityCacheUtil.getResult(CategoriesEntryModelImpl.ENTITY_CACHE_ENABLED,
				CategoriesEntryImpl.class, entryId, this);

		if (categoriesEntry == null) {
			Session session = null;

			try {
				session = openSession();

				categoriesEntry = (CategoriesEntry)session.get(CategoriesEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (categoriesEntry != null) {
					cacheResult(categoriesEntry);
				}

				closeSession(session);
			}
		}

		return categoriesEntry;
	}

	public List<CategoriesEntry> findByVocabularyId(long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

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
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesEntry> findByVocabularyId(long vocabularyId,
		int start, int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	public List<CategoriesEntry> findByVocabularyId(long vocabularyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

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

				list = (List<CategoriesEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesEntry findByVocabularyId_First(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<CategoriesEntry> list = findByVocabularyId(vocabularyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry findByVocabularyId_Last(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByVocabularyId(vocabularyId);

		List<CategoriesEntry> list = findByVocabularyId(vocabularyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry[] findByVocabularyId_PrevAndNext(long entryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		CategoriesEntry categoriesEntry = findByPrimaryKey(entryId);

		int count = countByVocabularyId(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

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
					categoriesEntry);

			CategoriesEntry[] array = new CategoriesEntryImpl[3];

			array[0] = (CategoriesEntry)objArray[0];
			array[1] = (CategoriesEntry)objArray[1];
			array[2] = (CategoriesEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesEntry> findByParentId(long parentEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentEntryId) };

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PARENTID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PARENTID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesEntry> findByParentId(long parentEntryId, int start,
		int end) throws SystemException {
		return findByParentId(parentEntryId, start, end, null);
	}

	public List<CategoriesEntry> findByParentId(long parentEntryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PARENTID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

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

				qPos.add(parentEntryId);

				list = (List<CategoriesEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PARENTID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesEntry findByParentId_First(long parentEntryId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<CategoriesEntry> list = findByParentId(parentEntryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry findByParentId_Last(long parentEntryId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByParentId(parentEntryId);

		List<CategoriesEntry> list = findByParentId(parentEntryId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry[] findByParentId_PrevAndNext(long entryId,
		long parentEntryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		CategoriesEntry categoriesEntry = findByPrimaryKey(entryId);

		int count = countByParentId(parentEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

			query.append("parentEntryId = ?");

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

			qPos.add(parentEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesEntry);

			CategoriesEntry[] array = new CategoriesEntryImpl[3];

			array[0] = (CategoriesEntry)objArray[0];
			array[1] = (CategoriesEntry)objArray[1];
			array[2] = (CategoriesEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesEntry> findByP_N(long parentEntryId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentEntryId), name };

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

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

				qPos.add(parentEntryId);

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
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_N, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesEntry> findByP_N(long parentEntryId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentEntryId, name, start, end, null);
	}

	public List<CategoriesEntry> findByP_N(long parentEntryId, String name,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

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

				qPos.add(parentEntryId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<CategoriesEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesEntry findByP_N_First(long parentEntryId, String name,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<CategoriesEntry> list = findByP_N(parentEntryId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry findByP_N_Last(long parentEntryId, String name,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByP_N(parentEntryId, name);

		List<CategoriesEntry> list = findByP_N(parentEntryId, name, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry[] findByP_N_PrevAndNext(long entryId,
		long parentEntryId, String name, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		CategoriesEntry categoriesEntry = findByPrimaryKey(entryId);

		int count = countByP_N(parentEntryId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

			query.append("parentEntryId = ?");

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

			qPos.add(parentEntryId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesEntry);

			CategoriesEntry[] array = new CategoriesEntryImpl[3];

			array[0] = (CategoriesEntry)objArray[0];
			array[1] = (CategoriesEntry)objArray[1];
			array[2] = (CategoriesEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CategoriesEntry> findByV_P(long vocabularyId, long parentEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId), new Long(parentEntryId)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_V_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" AND ");

				query.append("parentEntryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				qPos.add(parentEntryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_V_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<CategoriesEntry> findByV_P(long vocabularyId,
		long parentEntryId, int start, int end) throws SystemException {
		return findByV_P(vocabularyId, parentEntryId, start, end, null);
	}

	public List<CategoriesEntry> findByV_P(long vocabularyId,
		long parentEntryId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId), new Long(parentEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_V_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" AND ");

				query.append("parentEntryId = ?");

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

				qPos.add(parentEntryId);

				list = (List<CategoriesEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_V_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public CategoriesEntry findByV_P_First(long vocabularyId,
		long parentEntryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		List<CategoriesEntry> list = findByV_P(vocabularyId, parentEntryId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(", ");
			msg.append("parentEntryId=" + parentEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry findByV_P_Last(long vocabularyId,
		long parentEntryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		int count = countByV_P(vocabularyId, parentEntryId);

		List<CategoriesEntry> list = findByV_P(vocabularyId, parentEntryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No CategoriesEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(", ");
			msg.append("parentEntryId=" + parentEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public CategoriesEntry[] findByV_P_PrevAndNext(long entryId,
		long vocabularyId, long parentEntryId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		CategoriesEntry categoriesEntry = findByPrimaryKey(entryId);

		int count = countByV_P(vocabularyId, parentEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

			query.append("vocabularyId = ?");

			query.append(" AND ");

			query.append("parentEntryId = ?");

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

			qPos.add(parentEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					categoriesEntry);

			CategoriesEntry[] array = new CategoriesEntryImpl[3];

			array[0] = (CategoriesEntry)objArray[0];
			array[1] = (CategoriesEntry)objArray[1];
			array[2] = (CategoriesEntry)objArray[2];

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

	public List<CategoriesEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<CategoriesEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<CategoriesEntry> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<CategoriesEntry> list = (List<CategoriesEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry ");

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
					list = (List<CategoriesEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CategoriesEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<CategoriesEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (CategoriesEntry categoriesEntry : findByVocabularyId(vocabularyId)) {
			remove(categoriesEntry);
		}
	}

	public void removeByParentId(long parentEntryId) throws SystemException {
		for (CategoriesEntry categoriesEntry : findByParentId(parentEntryId)) {
			remove(categoriesEntry);
		}
	}

	public void removeByP_N(long parentEntryId, String name)
		throws SystemException {
		for (CategoriesEntry categoriesEntry : findByP_N(parentEntryId, name)) {
			remove(categoriesEntry);
		}
	}

	public void removeByV_P(long vocabularyId, long parentEntryId)
		throws SystemException {
		for (CategoriesEntry categoriesEntry : findByV_P(vocabularyId,
				parentEntryId)) {
			remove(categoriesEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (CategoriesEntry categoriesEntry : findAll()) {
			remove(categoriesEntry);
		}
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
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

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

	public int countByParentId(long parentEntryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentEntryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PARENTID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PARENTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_N(long parentEntryId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(parentEntryId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("parentEntryId = ?");

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

				qPos.add(parentEntryId);

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

	public int countByV_P(long vocabularyId, long parentEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId), new Long(parentEntryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_V_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.categories.model.CategoriesEntry WHERE ");

				query.append("vocabularyId = ?");

				query.append(" AND ");

				query.append("parentEntryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				qPos.add(parentEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_V_P, finderArgs,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.categories.model.CategoriesEntry");

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
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_CATEGORIESENTRIES,
			"TagsAssets_CategoriesEntries", "getTagsAssets",
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
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_CATEGORIESENTRIES,
			"TagsAssets_CategoriesEntries", "getTagsAssetsSize",
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
			CategoriesEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_CATEGORIESENTRIES,
			"TagsAssets_CategoriesEntries", "containsTagsAsset",
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
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
			FinderCacheUtil.clearCache("TagsAssets_CategoriesEntries");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.categories.model.CategoriesEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CategoriesEntry>> listenersList = new ArrayList<ModelListener<CategoriesEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CategoriesEntry>)Class.forName(
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

	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesEntryPersistence categoriesEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesPropertyPersistence categoriesPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence.impl")
	protected com.liferay.portlet.categories.service.persistence.CategoriesVocabularyPersistence categoriesVocabularyPersistence;
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
			CategoriesEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTAGSASSET,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long assetId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(assetId)
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
		protected AddTagsAsset(CategoriesEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO TagsAssets_CategoriesEntries (entryId, assetId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long entryId, long assetId)
			throws SystemException {
			if (!_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<CategoriesEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeAddAssociation(assetId,
						CategoriesEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<CategoriesEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterAddAssociation(assetId,
						CategoriesEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private CategoriesEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearTagsAssets {
		protected ClearTagsAssets(
			CategoriesEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_CategoriesEntries WHERE entryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
				tagsAssetPersistence.getListeners();

			List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets = null;

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				tagsAssets = getTagsAssets(entryId);

				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<CategoriesEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							CategoriesEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<CategoriesEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							CategoriesEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTagsAsset {
		protected RemoveTagsAsset(
			CategoriesEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_CategoriesEntries WHERE entryId = ? AND assetId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long entryId, long assetId)
			throws SystemException {
			if (_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<CategoriesEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeRemoveAssociation(assetId,
						CategoriesEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<CategoriesEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterRemoveAssociation(assetId,
						CategoriesEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private CategoriesEntryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETTAGSASSETS = "SELECT {TagsAsset.*} FROM TagsAsset INNER JOIN TagsAssets_CategoriesEntries ON (TagsAssets_CategoriesEntries.assetId = TagsAsset.assetId) WHERE (TagsAssets_CategoriesEntries.entryId = ?)";
	private static final String _SQL_GETTAGSASSETSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_CategoriesEntries WHERE entryId = ?";
	private static final String _SQL_CONTAINSTAGSASSET = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_CategoriesEntries WHERE entryId = ? AND assetId = ?";
	private static Log _log = LogFactoryUtil.getLog(CategoriesEntryPersistenceImpl.class);
}