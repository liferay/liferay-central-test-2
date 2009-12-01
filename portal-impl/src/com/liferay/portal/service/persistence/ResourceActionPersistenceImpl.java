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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchResourceActionException;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.model.impl.ResourceActionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourceActionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionPersistence
 * @see       ResourceActionUtil
 * @generated
 */
public class ResourceActionPersistenceImpl extends BasePersistenceImpl<ResourceAction>
	implements ResourceActionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceActionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByName",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByName",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByName",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_N_A = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByN_A",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_N_A = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByN_A",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ResourceAction resourceAction) {
		EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey(),
			resourceAction);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
			new Object[] { resourceAction.getName(), resourceAction.getActionId() },
			resourceAction);
	}

	public void cacheResult(List<ResourceAction> resourceActions) {
		for (ResourceAction resourceAction : resourceActions) {
			if (EntityCacheUtil.getResult(
						ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceActionImpl.class,
						resourceAction.getPrimaryKey(), this) == null) {
				cacheResult(resourceAction);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ResourceActionImpl.class.getName());
		EntityCacheUtil.clearCache(ResourceActionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ResourceAction create(long resourceActionId) {
		ResourceAction resourceAction = new ResourceActionImpl();

		resourceAction.setNew(true);
		resourceAction.setPrimaryKey(resourceActionId);

		return resourceAction;
	}

	public ResourceAction remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ResourceAction remove(long resourceActionId)
		throws NoSuchResourceActionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceAction resourceAction = (ResourceAction)session.get(ResourceActionImpl.class,
					new Long(resourceActionId));

			if (resourceAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						resourceActionId);
				}

				throw new NoSuchResourceActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceActionId);
			}

			return remove(resourceAction);
		}
		catch (NoSuchResourceActionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceAction remove(ResourceAction resourceAction)
		throws SystemException {
		for (ModelListener<ResourceAction> listener : listeners) {
			listener.onBeforeRemove(resourceAction);
		}

		resourceAction = removeImpl(resourceAction);

		for (ModelListener<ResourceAction> listener : listeners) {
			listener.onAfterRemove(resourceAction);
		}

		return resourceAction;
	}

	protected ResourceAction removeImpl(ResourceAction resourceAction)
		throws SystemException {
		resourceAction = toUnwrappedModel(resourceAction);

		Session session = null;

		try {
			session = openSession();

			if (resourceAction.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ResourceActionImpl.class,
						resourceAction.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(resourceAction);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceActionModelImpl resourceActionModelImpl = (ResourceActionModelImpl)resourceAction;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_A,
			new Object[] {
				resourceActionModelImpl.getOriginalName(),
				
			resourceActionModelImpl.getOriginalActionId()
			});

		EntityCacheUtil.removeResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey());

		return resourceAction;
	}

	public ResourceAction updateImpl(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws SystemException {
		resourceAction = toUnwrappedModel(resourceAction);

		boolean isNew = resourceAction.isNew();

		ResourceActionModelImpl resourceActionModelImpl = (ResourceActionModelImpl)resourceAction;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourceAction, merge);

			resourceAction.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey(),
			resourceAction);

		if (!isNew &&
				(!Validator.equals(resourceAction.getName(),
					resourceActionModelImpl.getOriginalName()) ||
				!Validator.equals(resourceAction.getActionId(),
					resourceActionModelImpl.getOriginalActionId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_A,
				new Object[] {
					resourceActionModelImpl.getOriginalName(),
					
				resourceActionModelImpl.getOriginalActionId()
				});
		}

		if (isNew ||
				(!Validator.equals(resourceAction.getName(),
					resourceActionModelImpl.getOriginalName()) ||
				!Validator.equals(resourceAction.getActionId(),
					resourceActionModelImpl.getOriginalActionId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
				new Object[] {
					resourceAction.getName(),
					
				resourceAction.getActionId()
				}, resourceAction);
		}

		return resourceAction;
	}

	protected ResourceAction toUnwrappedModel(ResourceAction resourceAction) {
		if (resourceAction instanceof ResourceActionImpl) {
			return resourceAction;
		}

		ResourceActionImpl resourceActionImpl = new ResourceActionImpl();

		resourceActionImpl.setNew(resourceAction.isNew());
		resourceActionImpl.setPrimaryKey(resourceAction.getPrimaryKey());

		resourceActionImpl.setResourceActionId(resourceAction.getResourceActionId());
		resourceActionImpl.setName(resourceAction.getName());
		resourceActionImpl.setActionId(resourceAction.getActionId());
		resourceActionImpl.setBitwiseValue(resourceAction.getBitwiseValue());

		return resourceActionImpl;
	}

	public ResourceAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ResourceAction findByPrimaryKey(long resourceActionId)
		throws NoSuchResourceActionException, SystemException {
		ResourceAction resourceAction = fetchByPrimaryKey(resourceActionId);

		if (resourceAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceActionId);
			}

			throw new NoSuchResourceActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceActionId);
		}

		return resourceAction;
	}

	public ResourceAction fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ResourceAction fetchByPrimaryKey(long resourceActionId)
		throws SystemException {
		ResourceAction resourceAction = (ResourceAction)EntityCacheUtil.getResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceActionImpl.class, resourceActionId, this);

		if (resourceAction == null) {
			Session session = null;

			try {
				session = openSession();

				resourceAction = (ResourceAction)session.get(ResourceActionImpl.class,
						new Long(resourceActionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resourceAction != null) {
					cacheResult(resourceAction);
				}

				closeSession(session);
			}
		}

		return resourceAction;
	}

	public List<ResourceAction> findByName(String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { name };

		List<ResourceAction> list = (List<ResourceAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_NAME,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				query.append(ResourceActionModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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
					list = new ArrayList<ResourceAction>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_NAME, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourceAction> findByName(String name, int start, int end)
		throws SystemException {
		return findByName(name, start, end, null);
	}

	public List<ResourceAction> findByName(String name, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourceAction> list = (List<ResourceAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_NAME,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ResourceActionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<ResourceAction>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceAction>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_NAME,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourceAction findByName_First(String name, OrderByComparator obc)
		throws NoSuchResourceActionException, SystemException {
		List<ResourceAction> list = findByName(name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceAction findByName_Last(String name, OrderByComparator obc)
		throws NoSuchResourceActionException, SystemException {
		int count = countByName(name);

		List<ResourceAction> list = findByName(name, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceAction[] findByName_PrevAndNext(long resourceActionId,
		String name, OrderByComparator obc)
		throws NoSuchResourceActionException, SystemException {
		ResourceAction resourceAction = findByPrimaryKey(resourceActionId);

		int count = countByName(name);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ResourceActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					resourceAction);

			ResourceAction[] array = new ResourceActionImpl[3];

			array[0] = (ResourceAction)objArray[0];
			array[1] = (ResourceAction)objArray[1];
			array[2] = (ResourceAction)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceAction findByN_A(String name, String actionId)
		throws NoSuchResourceActionException, SystemException {
		ResourceAction resourceAction = fetchByN_A(name, actionId);

		if (resourceAction == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceActionException(msg.toString());
		}

		return resourceAction;
	}

	public ResourceAction fetchByN_A(String name, String actionId)
		throws SystemException {
		return fetchByN_A(name, actionId, true);
	}

	public ResourceAction fetchByN_A(String name, String actionId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { name, actionId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_N_A,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_N_A_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_A_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_A_NAME_2);
					}
				}

				if (actionId == null) {
					query.append(_FINDER_COLUMN_N_A_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_A_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_A_ACTIONID_2);
					}
				}

				query.append(ResourceActionModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				if (actionId != null) {
					qPos.add(actionId);
				}

				List<ResourceAction> list = q.list();

				result = list;

				ResourceAction resourceAction = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
						finderArgs, list);
				}
				else {
					resourceAction = list.get(0);

					cacheResult(resourceAction);

					if ((resourceAction.getName() == null) ||
							!resourceAction.getName().equals(name) ||
							(resourceAction.getActionId() == null) ||
							!resourceAction.getActionId().equals(actionId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
							finderArgs, resourceAction);
					}
				}

				return resourceAction;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
						finderArgs, new ArrayList<ResourceAction>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ResourceAction)result;
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

	public List<ResourceAction> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ResourceAction> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ResourceAction> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ResourceAction> list = (List<ResourceAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_RESOURCEACTION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_RESOURCEACTION.concat(ResourceActionModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<ResourceAction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourceAction>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceAction>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByName(String name) throws SystemException {
		for (ResourceAction resourceAction : findByName(name)) {
			remove(resourceAction);
		}
	}

	public void removeByN_A(String name, String actionId)
		throws NoSuchResourceActionException, SystemException {
		ResourceAction resourceAction = findByN_A(name, actionId);

		remove(resourceAction);
	}

	public void removeAll() throws SystemException {
		for (ResourceAction resourceAction : findAll()) {
			remove(resourceAction);
		}
	}

	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_RESOURCEACTION_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_A(String name, String actionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { name, actionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_RESOURCEACTION_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_N_A_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_A_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_A_NAME_2);
					}
				}

				if (actionId == null) {
					query.append(_FINDER_COLUMN_N_A_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_A_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_A_ACTIONID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				if (actionId != null) {
					qPos.add(actionId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_A, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_RESOURCEACTION);

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
						"value.object.listener.com.liferay.portal.model.ResourceAction")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceAction>> listenersList = new ArrayList<ModelListener<ResourceAction>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceAction>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_RESOURCEACTION = "SELECT resourceAction FROM ResourceAction resourceAction";
	private static final String _SQL_SELECT_RESOURCEACTION_WHERE = "SELECT resourceAction FROM ResourceAction resourceAction WHERE ";
	private static final String _SQL_COUNT_RESOURCEACTION = "SELECT COUNT(resourceAction) FROM ResourceAction resourceAction";
	private static final String _SQL_COUNT_RESOURCEACTION_WHERE = "SELECT COUNT(resourceAction) FROM ResourceAction resourceAction WHERE ";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "resourceAction.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "resourceAction.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(resourceAction.name IS NULL OR resourceAction.name = ?)";
	private static final String _FINDER_COLUMN_N_A_NAME_1 = "resourceAction.name IS NULL AND ";
	private static final String _FINDER_COLUMN_N_A_NAME_2 = "resourceAction.name = ? AND ";
	private static final String _FINDER_COLUMN_N_A_NAME_3 = "(resourceAction.name IS NULL OR resourceAction.name = ?) AND ";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_1 = "resourceAction.actionId IS NULL";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_2 = "resourceAction.actionId = ?";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_3 = "(resourceAction.actionId IS NULL OR resourceAction.actionId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceAction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceAction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceAction exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ResourceActionPersistenceImpl.class);
}