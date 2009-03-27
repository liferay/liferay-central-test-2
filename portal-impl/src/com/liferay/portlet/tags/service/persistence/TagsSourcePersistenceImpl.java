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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchSourceException;
import com.liferay.portlet.tags.model.TagsSource;
import com.liferay.portlet.tags.model.impl.TagsSourceImpl;
import com.liferay.portlet.tags.model.impl.TagsSourceModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsSourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsSourcePersistenceImpl extends BasePersistenceImpl
	implements TagsSourcePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TagsSource.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = TagsSource.class.getName() +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
			TagsSourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
			TagsSourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(TagsSource tagsSource) {
		EntityCacheUtil.putResult(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
			TagsSource.class, tagsSource.getPrimaryKey(), tagsSource);
	}

	public void cacheResult(List<TagsSource> tagsSources) {
		for (TagsSource tagsSource : tagsSources) {
			if (EntityCacheUtil.getResult(
						TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
						TagsSource.class, tagsSource.getPrimaryKey(), this) == null) {
				cacheResult(tagsSource);
			}
		}
	}

	public TagsSource create(long sourceId) {
		TagsSource tagsSource = new TagsSourceImpl();

		tagsSource.setNew(true);
		tagsSource.setPrimaryKey(sourceId);

		return tagsSource;
	}

	public TagsSource remove(long sourceId)
		throws NoSuchSourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsSource tagsSource = (TagsSource)session.get(TagsSourceImpl.class,
					new Long(sourceId));

			if (tagsSource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsSource exists with the primary key " +
						sourceId);
				}

				throw new NoSuchSourceException(
					"No TagsSource exists with the primary key " + sourceId);
			}

			return remove(tagsSource);
		}
		catch (NoSuchSourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsSource remove(TagsSource tagsSource) throws SystemException {
		for (ModelListener<TagsSource> listener : listeners) {
			listener.onBeforeRemove(tagsSource);
		}

		tagsSource = removeImpl(tagsSource);

		for (ModelListener<TagsSource> listener : listeners) {
			listener.onAfterRemove(tagsSource);
		}

		return tagsSource;
	}

	protected TagsSource removeImpl(TagsSource tagsSource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TagsSourceImpl.class,
						tagsSource.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tagsSource);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TagsSourceModelImpl tagsSourceModelImpl = (TagsSourceModelImpl)tagsSource;

		EntityCacheUtil.removeResult(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
			TagsSource.class, tagsSource.getPrimaryKey());

		return tagsSource;
	}

	/**
	 * @deprecated Use <code>update(TagsSource tagsSource, boolean merge)</code>.
	 */
	public TagsSource update(TagsSource tagsSource) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TagsSource tagsSource) method. Use update(TagsSource tagsSource, boolean merge) instead.");
		}

		return update(tagsSource, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsSource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsSource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TagsSource update(TagsSource tagsSource, boolean merge)
		throws SystemException {
		boolean isNew = tagsSource.isNew();

		for (ModelListener<TagsSource> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tagsSource);
			}
			else {
				listener.onBeforeUpdate(tagsSource);
			}
		}

		tagsSource = updateImpl(tagsSource, merge);

		for (ModelListener<TagsSource> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tagsSource);
			}
			else {
				listener.onAfterUpdate(tagsSource);
			}
		}

		return tagsSource;
	}

	public TagsSource updateImpl(
		com.liferay.portlet.tags.model.TagsSource tagsSource, boolean merge)
		throws SystemException {
		boolean isNew = tagsSource.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tagsSource, merge);

			tagsSource.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TagsSourceModelImpl tagsSourceModelImpl = (TagsSourceModelImpl)tagsSource;

		EntityCacheUtil.putResult(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
			TagsSource.class, tagsSource.getPrimaryKey(), tagsSource);

		return tagsSource;
	}

	public TagsSource findByPrimaryKey(long sourceId)
		throws NoSuchSourceException, SystemException {
		TagsSource tagsSource = fetchByPrimaryKey(sourceId);

		if (tagsSource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsSource exists with the primary key " +
					sourceId);
			}

			throw new NoSuchSourceException(
				"No TagsSource exists with the primary key " + sourceId);
		}

		return tagsSource;
	}

	public TagsSource fetchByPrimaryKey(long sourceId)
		throws SystemException {
		TagsSource result = (TagsSource)EntityCacheUtil.getResult(TagsSourceModelImpl.ENTITY_CACHE_ENABLED,
				TagsSource.class, sourceId, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				TagsSource tagsSource = (TagsSource)session.get(TagsSourceImpl.class,
						new Long(sourceId));

				cacheResult(tagsSource);

				return tagsSource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (TagsSource)result;
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

	public List<TagsSource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TagsSource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TagsSource> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portlet.tags.model.TagsSource ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<TagsSource> list = null;

				if (obc == null) {
					list = (List<TagsSource>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TagsSource>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				cacheResult(list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TagsSource>)result;
		}
	}

	public void removeAll() throws SystemException {
		for (TagsSource tagsSource : findAll()) {
			remove(tagsSource);
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.tags.model.TagsSource");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tags.model.TagsSource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TagsSource>> listenersList = new ArrayList<ModelListener<TagsSource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TagsSource>)Class.forName(
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
	private static Log _log = LogFactoryUtil.getLog(TagsSourcePersistenceImpl.class);
}