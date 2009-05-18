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

package com.liferay.portlet.tags.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.model.impl.TagsVocabularyImpl;
import com.liferay.portlet.tags.model.impl.TagsVocabularyModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TagsVocabularyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsVocabularyPersistenceImpl extends BasePersistenceImpl
	implements TagsVocabularyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TagsVocabularyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_F",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_F",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(TagsVocabulary tagsVocabulary) {
		EntityCacheUtil.putResult(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyImpl.class, tagsVocabulary.getPrimaryKey(),
			tagsVocabulary);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(tagsVocabulary.getGroupId()),
				
			tagsVocabulary.getName()
			}, tagsVocabulary);
	}

	public void cacheResult(List<TagsVocabulary> tagsVocabularies) {
		for (TagsVocabulary tagsVocabulary : tagsVocabularies) {
			if (EntityCacheUtil.getResult(
						TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
						TagsVocabularyImpl.class,
						tagsVocabulary.getPrimaryKey(), this) == null) {
				cacheResult(tagsVocabulary);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TagsVocabularyImpl.class.getName());
		EntityCacheUtil.clearCache(TagsVocabularyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public TagsVocabulary create(long vocabularyId) {
		TagsVocabulary tagsVocabulary = new TagsVocabularyImpl();

		tagsVocabulary.setNew(true);
		tagsVocabulary.setPrimaryKey(vocabularyId);

		return tagsVocabulary;
	}

	public TagsVocabulary remove(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsVocabulary tagsVocabulary = (TagsVocabulary)session.get(TagsVocabularyImpl.class,
					new Long(vocabularyId));

			if (tagsVocabulary == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsVocabulary exists with the primary key " +
						vocabularyId);
				}

				throw new NoSuchVocabularyException(
					"No TagsVocabulary exists with the primary key " +
					vocabularyId);
			}

			return remove(tagsVocabulary);
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

	public TagsVocabulary remove(TagsVocabulary tagsVocabulary)
		throws SystemException {
		for (ModelListener<TagsVocabulary> listener : listeners) {
			listener.onBeforeRemove(tagsVocabulary);
		}

		tagsVocabulary = removeImpl(tagsVocabulary);

		for (ModelListener<TagsVocabulary> listener : listeners) {
			listener.onAfterRemove(tagsVocabulary);
		}

		return tagsVocabulary;
	}

	protected TagsVocabulary removeImpl(TagsVocabulary tagsVocabulary)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (tagsVocabulary.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TagsVocabularyImpl.class,
						tagsVocabulary.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tagsVocabulary);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TagsVocabularyModelImpl tagsVocabularyModelImpl = (TagsVocabularyModelImpl)tagsVocabulary;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(tagsVocabularyModelImpl.getOriginalGroupId()),
				
			tagsVocabularyModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyImpl.class, tagsVocabulary.getPrimaryKey());

		return tagsVocabulary;
	}

	/**
	 * @deprecated Use <code>update(TagsVocabulary tagsVocabulary, boolean merge)</code>.
	 */
	public TagsVocabulary update(TagsVocabulary tagsVocabulary)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TagsVocabulary tagsVocabulary) method. Use update(TagsVocabulary tagsVocabulary, boolean merge) instead.");
		}

		return update(tagsVocabulary, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TagsVocabulary update(TagsVocabulary tagsVocabulary, boolean merge)
		throws SystemException {
		boolean isNew = tagsVocabulary.isNew();

		for (ModelListener<TagsVocabulary> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tagsVocabulary);
			}
			else {
				listener.onBeforeUpdate(tagsVocabulary);
			}
		}

		tagsVocabulary = updateImpl(tagsVocabulary, merge);

		for (ModelListener<TagsVocabulary> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tagsVocabulary);
			}
			else {
				listener.onAfterUpdate(tagsVocabulary);
			}
		}

		return tagsVocabulary;
	}

	public TagsVocabulary updateImpl(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary,
		boolean merge) throws SystemException {
		boolean isNew = tagsVocabulary.isNew();

		TagsVocabularyModelImpl tagsVocabularyModelImpl = (TagsVocabularyModelImpl)tagsVocabulary;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tagsVocabulary, merge);

			tagsVocabulary.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			TagsVocabularyImpl.class, tagsVocabulary.getPrimaryKey(),
			tagsVocabulary);

		if (!isNew &&
				((tagsVocabulary.getGroupId() != tagsVocabularyModelImpl.getOriginalGroupId()) ||
				!Validator.equals(tagsVocabulary.getName(),
					tagsVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(tagsVocabularyModelImpl.getOriginalGroupId()),
					
				tagsVocabularyModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((tagsVocabulary.getGroupId() != tagsVocabularyModelImpl.getOriginalGroupId()) ||
				!Validator.equals(tagsVocabulary.getName(),
					tagsVocabularyModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(tagsVocabulary.getGroupId()),
					
				tagsVocabulary.getName()
				}, tagsVocabulary);
		}

		return tagsVocabulary;
	}

	public TagsVocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		TagsVocabulary tagsVocabulary = fetchByPrimaryKey(vocabularyId);

		if (tagsVocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsVocabulary exists with the primary key " +
					vocabularyId);
			}

			throw new NoSuchVocabularyException(
				"No TagsVocabulary exists with the primary key " +
				vocabularyId);
		}

		return tagsVocabulary;
	}

	public TagsVocabulary fetchByPrimaryKey(long vocabularyId)
		throws SystemException {
		TagsVocabulary tagsVocabulary = (TagsVocabulary)EntityCacheUtil.getResult(TagsVocabularyModelImpl.ENTITY_CACHE_ENABLED,
				TagsVocabularyImpl.class, vocabularyId, this);

		if (tagsVocabulary == null) {
			Session session = null;

			try {
				session = openSession();

				tagsVocabulary = (TagsVocabulary)session.get(TagsVocabularyImpl.class,
						new Long(vocabularyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tagsVocabulary != null) {
					cacheResult(tagsVocabulary);
				}

				closeSession(session);
			}
		}

		return tagsVocabulary;
	}

	public TagsVocabulary findByG_N(long groupId, String name)
		throws NoSuchVocabularyException, SystemException {
		TagsVocabulary tagsVocabulary = fetchByG_N(groupId, name);

		if (tagsVocabulary == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVocabularyException(msg.toString());
		}

		return tagsVocabulary;
	}

	public TagsVocabulary fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	public TagsVocabulary fetchByG_N(long groupId, String name,
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
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

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

				List<TagsVocabulary> list = q.list();

				result = list;

				TagsVocabulary tagsVocabulary = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					tagsVocabulary = list.get(0);

					cacheResult(tagsVocabulary);

					if ((tagsVocabulary.getGroupId() != groupId) ||
							(tagsVocabulary.getName() == null) ||
							!tagsVocabulary.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, tagsVocabulary);
					}
				}

				return tagsVocabulary;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, new ArrayList<TagsVocabulary>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (TagsVocabulary)result;
			}
		}
	}

	public List<TagsVocabulary> findByG_F(long groupId, boolean folksonomy)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(folksonomy)
			};

		List<TagsVocabulary> list = (List<TagsVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folksonomy);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsVocabulary> findByG_F(long groupId, boolean folksonomy,
		int start, int end) throws SystemException {
		return findByG_F(groupId, folksonomy, start, end, null);
	}

	public List<TagsVocabulary> findByG_F(long groupId, boolean folksonomy,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(folksonomy),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsVocabulary> list = (List<TagsVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

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

				qPos.add(folksonomy);

				list = (List<TagsVocabulary>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsVocabulary findByG_F_First(long groupId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<TagsVocabulary> list = findByG_F(groupId, folksonomy, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsVocabulary findByG_F_Last(long groupId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByG_F(groupId, folksonomy);

		List<TagsVocabulary> list = findByG_F(groupId, folksonomy, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsVocabulary exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsVocabulary[] findByG_F_PrevAndNext(long vocabularyId,
		long groupId, boolean folksonomy, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		TagsVocabulary tagsVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByG_F(groupId, folksonomy);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("folksonomy = ?");

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

			qPos.add(folksonomy);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsVocabulary);

			TagsVocabulary[] array = new TagsVocabularyImpl[3];

			array[0] = (TagsVocabulary)objArray[0];
			array[1] = (TagsVocabulary)objArray[1];
			array[2] = (TagsVocabulary)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsVocabulary> findByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy)
			};

		List<TagsVocabulary> list = (List<TagsVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(folksonomy);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsVocabulary> findByC_F(long companyId, boolean folksonomy,
		int start, int end) throws SystemException {
		return findByC_F(companyId, folksonomy, start, end, null);
	}

	public List<TagsVocabulary> findByC_F(long companyId, boolean folksonomy,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsVocabulary> list = (List<TagsVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

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

				qPos.add(folksonomy);

				list = (List<TagsVocabulary>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsVocabulary>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsVocabulary findByC_F_First(long companyId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		List<TagsVocabulary> list = findByC_F(companyId, folksonomy, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsVocabulary findByC_F_Last(long companyId, boolean folksonomy,
		OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		int count = countByC_F(companyId, folksonomy);

		List<TagsVocabulary> list = findByC_F(companyId, folksonomy, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsVocabulary exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("folksonomy=" + folksonomy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchVocabularyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsVocabulary[] findByC_F_PrevAndNext(long vocabularyId,
		long companyId, boolean folksonomy, OrderByComparator obc)
		throws NoSuchVocabularyException, SystemException {
		TagsVocabulary tagsVocabulary = findByPrimaryKey(vocabularyId);

		int count = countByC_F(companyId, folksonomy);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("folksonomy = ?");

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

			qPos.add(folksonomy);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsVocabulary);

			TagsVocabulary[] array = new TagsVocabularyImpl[3];

			array[0] = (TagsVocabulary)objArray[0];
			array[1] = (TagsVocabulary)objArray[1];
			array[2] = (TagsVocabulary)objArray[2];

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

	public List<TagsVocabulary> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TagsVocabulary> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TagsVocabulary> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsVocabulary> list = (List<TagsVocabulary>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary ");

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
					list = (List<TagsVocabulary>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TagsVocabulary>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsVocabulary>();
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
		TagsVocabulary tagsVocabulary = findByG_N(groupId, name);

		remove(tagsVocabulary);
	}

	public void removeByG_F(long groupId, boolean folksonomy)
		throws SystemException {
		for (TagsVocabulary tagsVocabulary : findByG_F(groupId, folksonomy)) {
			remove(tagsVocabulary);
		}
	}

	public void removeByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		for (TagsVocabulary tagsVocabulary : findByC_F(companyId, folksonomy)) {
			remove(tagsVocabulary);
		}
	}

	public void removeAll() throws SystemException {
		for (TagsVocabulary tagsVocabulary : findAll()) {
			remove(tagsVocabulary);
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
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

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

	public int countByG_F(long groupId, boolean folksonomy)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(folksonomy)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folksonomy);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_F(long companyId, boolean folksonomy)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(folksonomy)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tags.model.TagsVocabulary WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("folksonomy = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(folksonomy);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_F, finderArgs,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.tags.model.TagsVocabulary");

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
						"value.object.listener.com.liferay.portlet.tags.model.TagsVocabulary")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TagsVocabulary>> listenersList = new ArrayList<ModelListener<TagsVocabulary>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TagsVocabulary>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsSourcePersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence tagsVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(TagsVocabularyPersistenceImpl.class);
}