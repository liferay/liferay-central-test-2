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
import com.liferay.portal.NoSuchResourceException;
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
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.ResourceModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePersistence
 * @see       ResourceUtil
 * @generated
 */
public class ResourcePersistenceImpl extends BasePersistenceImpl<Resource>
	implements ResourcePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCodeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCodeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCodeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Resource resource) {
		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] { new Long(resource.getCodeId()), resource.getPrimKey() },
			resource);
	}

	public void cacheResult(List<Resource> resources) {
		for (Resource resource : resources) {
			if (EntityCacheUtil.getResult(
						ResourceModelImpl.ENTITY_CACHE_ENABLED,
						ResourceImpl.class, resource.getPrimaryKey(), this) == null) {
				cacheResult(resource);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ResourceImpl.class.getName());
		EntityCacheUtil.clearCache(ResourceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Resource create(long resourceId) {
		Resource resource = new ResourceImpl();

		resource.setNew(true);
		resource.setPrimaryKey(resourceId);

		return resource;
	}

	public Resource remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Resource remove(long resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(ResourceImpl.class,
					new Long(resourceId));

			if (resource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
				}

				throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceId);
			}

			return remove(resource);
		}
		catch (NoSuchResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource remove(Resource resource) throws SystemException {
		for (ModelListener<Resource> listener : listeners) {
			listener.onBeforeRemove(resource);
		}

		resource = removeImpl(resource);

		for (ModelListener<Resource> listener : listeners) {
			listener.onAfterRemove(resource);
		}

		return resource;
	}

	protected Resource removeImpl(Resource resource) throws SystemException {
		resource = toUnwrappedModel(resource);

		Session session = null;

		try {
			session = openSession();

			if (resource.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ResourceImpl.class,
						resource.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(resource);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				new Long(resourceModelImpl.getOriginalCodeId()),
				
			resourceModelImpl.getOriginalPrimKey()
			});

		EntityCacheUtil.removeResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey());

		return resource;
	}

	public Resource updateImpl(com.liferay.portal.model.Resource resource,
		boolean merge) throws SystemException {
		resource = toUnwrappedModel(resource);

		boolean isNew = resource.isNew();

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resource, merge);

			resource.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		if (!isNew &&
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resourceModelImpl.getOriginalCodeId()),
					
				resourceModelImpl.getOriginalPrimKey()
				});
		}

		if (isNew ||
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resource.getCodeId()),
					
				resource.getPrimKey()
				}, resource);
		}

		return resource;
	}

	protected Resource toUnwrappedModel(Resource resource) {
		if (resource instanceof ResourceImpl) {
			return resource;
		}

		ResourceImpl resourceImpl = new ResourceImpl();

		resourceImpl.setNew(resource.isNew());
		resourceImpl.setPrimaryKey(resource.getPrimaryKey());

		resourceImpl.setResourceId(resource.getResourceId());
		resourceImpl.setCodeId(resource.getCodeId());
		resourceImpl.setPrimKey(resource.getPrimKey());

		return resourceImpl;
	}

	public Resource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Resource findByPrimaryKey(long resourceId)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByPrimaryKey(resourceId);

		if (resource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
			}

			throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceId);
		}

		return resource;
	}

	public Resource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Resource fetchByPrimaryKey(long resourceId)
		throws SystemException {
		Resource resource = (Resource)EntityCacheUtil.getResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
				ResourceImpl.class, resourceId, this);

		if (resource == null) {
			Session session = null;

			try {
				session = openSession();

				resource = (Resource)session.get(ResourceImpl.class,
						new Long(resourceId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resource != null) {
					cacheResult(resource);
				}

				closeSession(session);
			}
		}

		return resource;
	}

	public List<Resource> findByCodeId(long codeId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId) };

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_CODEID_CODEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Resource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CODEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Resource> findByCodeId(long codeId, int start, int end)
		throws SystemException {
		return findByCodeId(codeId, start, end, null);
	}

	public List<Resource> findByCodeId(long codeId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(codeId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_CODEID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_CODEID_CODEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				list = (List<Resource>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Resource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_CODEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Resource findByCodeId_First(long codeId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		List<Resource> list = findByCodeId(codeId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Resource findByCodeId_Last(long codeId, OrderByComparator obc)
		throws NoSuchResourceException, SystemException {
		int count = countByCodeId(codeId);

		List<Resource> list = findByCodeId(codeId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Resource[] findByCodeId_PrevAndNext(long resourceId, long codeId,
		OrderByComparator obc) throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);

		int count = countByCodeId(codeId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_RESOURCE_WHERE);

			query.append(_FINDER_COLUMN_CODEID_CODEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(codeId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, resource);

			Resource[] array = new ResourceImpl[3];

			array[0] = (Resource)objArray[0];
			array[1] = (Resource)objArray[1];
			array[2] = (Resource)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource findByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_P(codeId, primKey);

		if (resource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceException(msg.toString());
		}

		return resource;
	}

	public Resource fetchByC_P(long codeId, String primKey)
		throws SystemException {
		return fetchByC_P(codeId, primKey, true);
	}

	public Resource fetchByC_P(long codeId, String primKey,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId), primKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_C_P_CODEID_2);

				if (primKey == null) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
				}

				List<Resource> list = q.list();

				result = list;

				Resource resource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, list);
				}
				else {
					resource = list.get(0);

					cacheResult(resource);

					if ((resource.getCodeId() != codeId) ||
							(resource.getPrimKey() == null) ||
							!resource.getPrimKey().equals(primKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
							finderArgs, resource);
					}
				}

				return resource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, new ArrayList<Resource>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Resource)result;
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

	public List<Resource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Resource> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Resource> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_RESOURCE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_RESOURCE;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Resource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCodeId(long codeId) throws SystemException {
		for (Resource resource : findByCodeId(codeId)) {
			remove(resource);
		}
	}

	public void removeByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_P(codeId, primKey);

		remove(resource);
	}

	public void removeAll() throws SystemException {
		for (Resource resource : findAll()) {
			remove(resource);
		}
	}

	public int countByCodeId(long codeId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_CODEID_CODEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CODEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_P(long codeId, String primKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId), primKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_C_P_CODEID_2);

				if (primKey == null) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_RESOURCE);

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
						"value.object.listener.com.liferay.portal.model.Resource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Resource>> listenersList = new ArrayList<ModelListener<Resource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Resource>)Class.forName(
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
	private static final String _SQL_SELECT_RESOURCE = "SELECT resource FROM Resource resource";
	private static final String _SQL_SELECT_RESOURCE_WHERE = "SELECT resource FROM Resource resource WHERE ";
	private static final String _SQL_COUNT_RESOURCE = "SELECT COUNT(resource) FROM Resource resource";
	private static final String _SQL_COUNT_RESOURCE_WHERE = "SELECT COUNT(resource) FROM Resource resource WHERE ";
	private static final String _FINDER_COLUMN_CODEID_CODEID_2 = "resource.codeId = ?";
	private static final String _FINDER_COLUMN_C_P_CODEID_2 = "resource.codeId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_1 = "resource.primKey IS NULL";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_2 = "resource.primKey = ?";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_3 = "(resource.primKey IS NULL OR resource.primKey = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Resource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Resource exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ResourcePersistenceImpl.class);
}