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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.ResourceModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePersistenceImpl extends BasePersistenceImpl
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

	public Resource remove(long resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(ResourceImpl.class,
					new Long(resourceId));

			if (resource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Resource exists with the primary key " +
						resourceId);
				}

				throw new NoSuchResourceException(
					"No Resource exists with the primary key " + resourceId);
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

	/**
	 * @deprecated Use <code>update(Resource resource, boolean merge)</code>.
	 */
	public Resource update(Resource resource) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Resource resource) method. Use update(Resource resource, boolean merge) instead.");
		}

		return update(resource, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        resource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when resource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Resource update(Resource resource, boolean merge)
		throws SystemException {
		boolean isNew = resource.isNew();

		for (ModelListener<Resource> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(resource);
			}
			else {
				listener.onBeforeUpdate(resource);
			}
		}

		resource = updateImpl(resource, merge);

		for (ModelListener<Resource> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(resource);
			}
			else {
				listener.onAfterUpdate(resource);
			}
		}

		return resource;
	}

	public Resource updateImpl(com.liferay.portal.model.Resource resource,
		boolean merge) throws SystemException {
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
				!resource.getPrimKey()
							 .equals(resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resourceModelImpl.getOriginalCodeId()),
					
				resourceModelImpl.getOriginalPrimKey()
				});
		}

		if (isNew ||
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!resource.getPrimKey()
							 .equals(resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resource.getCodeId()),
					
				resource.getPrimKey()
				}, resource);
		}

		return resource;
	}

	public Resource findByPrimaryKey(long resourceId)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByPrimaryKey(resourceId);

		if (resource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Resource exists with the primary key " +
					resourceId);
			}

			throw new NoSuchResourceException(
				"No Resource exists with the primary key " + resourceId);
		}

		return resource;
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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Resource WHERE ");

				query.append("codeId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Resource WHERE ");

				query.append("codeId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No Resource exists with the key {");

			msg.append("codeId=" + codeId);

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
			StringBuilder msg = new StringBuilder();

			msg.append("No Resource exists with the key {");

			msg.append("codeId=" + codeId);

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

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Resource WHERE ");

			query.append("codeId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No Resource exists with the key {");

			msg.append("codeId=" + codeId);

			msg.append(", ");
			msg.append("primKey=" + primKey);

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Resource WHERE ");

				query.append("codeId = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("primKey IS NULL");
				}
				else {
					query.append("primKey = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
							finderArgs, list);
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
			if (result instanceof List) {
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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Resource ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Resource WHERE ");

				query.append("codeId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Resource WHERE ");

				query.append("codeId = ?");

				query.append(" AND ");

				if (primKey == null) {
					query.append("primKey IS NULL");
				}
				else {
					query.append("primKey = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.Resource");

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

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(ResourcePersistenceImpl.class);
}