/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.counter.service.persistence;

import com.liferay.counter.NoSuchCounterException;
import com.liferay.counter.model.Counter;
import com.liferay.counter.model.impl.CounterImpl;
import com.liferay.counter.model.impl.CounterModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CounterPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CounterPersistence
 * @see       CounterUtil
 * @generated
 */
public class CounterPersistenceImpl extends BasePersistenceImpl<Counter>
	implements CounterPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CounterImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Counter counter) {
		EntityCacheUtil.putResult(CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterImpl.class, counter.getPrimaryKey(), counter);
	}

	public void cacheResult(List<Counter> counters) {
		for (Counter counter : counters) {
			if (EntityCacheUtil.getResult(
						CounterModelImpl.ENTITY_CACHE_ENABLED,
						CounterImpl.class, counter.getPrimaryKey(), this) == null) {
				cacheResult(counter);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(CounterImpl.class.getName());
		EntityCacheUtil.clearCache(CounterImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Counter create(String name) {
		Counter counter = new CounterImpl();

		counter.setNew(true);
		counter.setPrimaryKey(name);

		return counter;
	}

	public Counter remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((String)primaryKey);
	}

	public Counter remove(String name)
		throws NoSuchCounterException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Counter counter = (Counter)session.get(CounterImpl.class, name);

			if (counter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + name);
				}

				throw new NoSuchCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					name);
			}

			return remove(counter);
		}
		catch (NoSuchCounterException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Counter remove(Counter counter) throws SystemException {
		for (ModelListener<Counter> listener : listeners) {
			listener.onBeforeRemove(counter);
		}

		counter = removeImpl(counter);

		for (ModelListener<Counter> listener : listeners) {
			listener.onAfterRemove(counter);
		}

		return counter;
	}

	protected Counter removeImpl(Counter counter) throws SystemException {
		counter = toUnwrappedModel(counter);

		Session session = null;

		try {
			session = openSession();

			if (counter.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CounterImpl.class,
						counter.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(counter);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterImpl.class, counter.getPrimaryKey());

		return counter;
	}

	public Counter updateImpl(com.liferay.counter.model.Counter counter,
		boolean merge) throws SystemException {
		counter = toUnwrappedModel(counter);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, counter, merge);

			counter.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CounterModelImpl.ENTITY_CACHE_ENABLED,
			CounterImpl.class, counter.getPrimaryKey(), counter);

		return counter;
	}

	protected Counter toUnwrappedModel(Counter counter) {
		if (counter instanceof CounterImpl) {
			return counter;
		}

		CounterImpl counterImpl = new CounterImpl();

		counterImpl.setNew(counter.isNew());
		counterImpl.setPrimaryKey(counter.getPrimaryKey());

		counterImpl.setName(counter.getName());
		counterImpl.setCurrentId(counter.getCurrentId());

		return counterImpl;
	}

	public Counter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((String)primaryKey);
	}

	public Counter findByPrimaryKey(String name)
		throws NoSuchCounterException, SystemException {
		Counter counter = fetchByPrimaryKey(name);

		if (counter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + name);
			}

			throw new NoSuchCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				name);
		}

		return counter;
	}

	public Counter fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((String)primaryKey);
	}

	public Counter fetchByPrimaryKey(String name) throws SystemException {
		Counter counter = (Counter)EntityCacheUtil.getResult(CounterModelImpl.ENTITY_CACHE_ENABLED,
				CounterImpl.class, name, this);

		if (counter == null) {
			Session session = null;

			try {
				session = openSession();

				counter = (Counter)session.get(CounterImpl.class, name);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (counter != null) {
					cacheResult(counter);
				}

				closeSession(session);
			}
		}

		return counter;
	}

	public List<Counter> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Counter> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Counter> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Counter> list = (List<Counter>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_COUNTER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_COUNTER;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Counter>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Counter>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Counter>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeAll() throws SystemException {
		for (Counter counter : findAll()) {
			remove(counter);
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COUNTER);

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
						"value.object.listener.com.liferay.counter.model.Counter")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Counter>> listenersList = new ArrayList<ModelListener<Counter>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Counter>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = CounterPersistence.class)
	protected CounterPersistence counterPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_COUNTER = "SELECT counter FROM Counter counter";
	private static final String _SQL_COUNT_COUNTER = "SELECT COUNT(counter) FROM Counter counter";
	private static final String _ORDER_BY_ENTITY_ALIAS = "counter.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Counter exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(CounterPersistenceImpl.class);
}