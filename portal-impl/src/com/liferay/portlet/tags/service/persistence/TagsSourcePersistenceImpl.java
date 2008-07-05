/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.tags.NoSuchSourceException;
import com.liferay.portlet.tags.model.TagsSource;
import com.liferay.portlet.tags.model.impl.TagsSourceImpl;
import com.liferay.portlet.tags.model.impl.TagsSourceModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

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
public class TagsSourcePersistenceImpl extends BasePersistence
	implements TagsSourcePersistence {
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
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsSource remove(TagsSource tagsSource) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(tagsSource);
			}
		}

		tagsSource = removeImpl(tagsSource);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(tagsSource);
			}
		}

		return tagsSource;
	}

	protected TagsSource removeImpl(TagsSource tagsSource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(tagsSource);

			session.flush();

			return tagsSource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TagsSource.class.getName());
		}
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

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(tagsSource);
				}
				else {
					listener.onBeforeUpdate(tagsSource);
				}
			}
		}

		tagsSource = updateImpl(tagsSource, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(tagsSource);
				}
				else {
					listener.onAfterUpdate(tagsSource);
				}
			}
		}

		return tagsSource;
	}

	public TagsSource updateImpl(
		com.liferay.portlet.tags.model.TagsSource tagsSource, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(tagsSource);
			}
			else {
				if (tagsSource.isNew()) {
					session.save(tagsSource);
				}
			}

			session.flush();

			tagsSource.setNew(false);

			return tagsSource;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TagsSource.class.getName());
		}
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
		Session session = null;

		try {
			session = openSession();

			return (TagsSource)session.get(TagsSourceImpl.class,
				new Long(sourceId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsSource> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsSource> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		boolean finderClassNameCacheEnabled = TagsSourceModelImpl.CACHE_ENABLED;
		String finderClassName = TagsSource.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

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

				List<TagsSource> list = (List<TagsSource>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
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
		boolean finderClassNameCacheEnabled = TagsSourceModelImpl.CACHE_ENABLED;
		String finderClassName = TagsSource.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

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

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tags.model.TagsSource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(TagsSourcePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}