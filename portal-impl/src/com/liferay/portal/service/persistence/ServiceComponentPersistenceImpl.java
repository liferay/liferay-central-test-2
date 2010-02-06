/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchServiceComponentException;
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
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ServiceComponentPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentPersistence
 * @see       ServiceComponentUtil
 * @generated
 */
public class ServiceComponentPersistenceImpl extends BasePersistenceImpl<ServiceComponent>
	implements ServiceComponentPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ServiceComponentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_BUILDNAMESPACE = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByBuildNamespace",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_BUILDNAMESPACE = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByBuildNamespace",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_BUILDNAMESPACE = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByBuildNamespace",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_BNS_BNU = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByBNS_BNU",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_BNS_BNU = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByBNS_BNU",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ServiceComponent serviceComponent) {
		EntityCacheUtil.putResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey(),
			serviceComponent);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
			new Object[] {
				serviceComponent.getBuildNamespace(),
				new Long(serviceComponent.getBuildNumber())
			}, serviceComponent);
	}

	public void cacheResult(List<ServiceComponent> serviceComponents) {
		for (ServiceComponent serviceComponent : serviceComponents) {
			if (EntityCacheUtil.getResult(
						ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
						ServiceComponentImpl.class,
						serviceComponent.getPrimaryKey(), this) == null) {
				cacheResult(serviceComponent);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ServiceComponentImpl.class.getName());
		EntityCacheUtil.clearCache(ServiceComponentImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ServiceComponent create(long serviceComponentId) {
		ServiceComponent serviceComponent = new ServiceComponentImpl();

		serviceComponent.setNew(true);
		serviceComponent.setPrimaryKey(serviceComponentId);

		return serviceComponent;
	}

	public ServiceComponent remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ServiceComponent remove(long serviceComponentId)
		throws NoSuchServiceComponentException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ServiceComponent serviceComponent = (ServiceComponent)session.get(ServiceComponentImpl.class,
					new Long(serviceComponentId));

			if (serviceComponent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						serviceComponentId);
				}

				throw new NoSuchServiceComponentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					serviceComponentId);
			}

			return remove(serviceComponent);
		}
		catch (NoSuchServiceComponentException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ServiceComponent remove(ServiceComponent serviceComponent)
		throws SystemException {
		for (ModelListener<ServiceComponent> listener : listeners) {
			listener.onBeforeRemove(serviceComponent);
		}

		serviceComponent = removeImpl(serviceComponent);

		for (ModelListener<ServiceComponent> listener : listeners) {
			listener.onAfterRemove(serviceComponent);
		}

		return serviceComponent;
	}

	protected ServiceComponent removeImpl(ServiceComponent serviceComponent)
		throws SystemException {
		serviceComponent = toUnwrappedModel(serviceComponent);

		Session session = null;

		try {
			session = openSession();

			if (serviceComponent.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ServiceComponentImpl.class,
						serviceComponent.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(serviceComponent);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ServiceComponentModelImpl serviceComponentModelImpl = (ServiceComponentModelImpl)serviceComponent;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_BNS_BNU,
			new Object[] {
				serviceComponentModelImpl.getOriginalBuildNamespace(),
				new Long(serviceComponentModelImpl.getOriginalBuildNumber())
			});

		EntityCacheUtil.removeResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey());

		return serviceComponent;
	}

	public ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge) throws SystemException {
		serviceComponent = toUnwrappedModel(serviceComponent);

		boolean isNew = serviceComponent.isNew();

		ServiceComponentModelImpl serviceComponentModelImpl = (ServiceComponentModelImpl)serviceComponent;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, serviceComponent, merge);

			serviceComponent.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey(),
			serviceComponent);

		if (!isNew &&
				(!Validator.equals(serviceComponent.getBuildNamespace(),
					serviceComponentModelImpl.getOriginalBuildNamespace()) ||
				(serviceComponent.getBuildNumber() != serviceComponentModelImpl.getOriginalBuildNumber()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_BNS_BNU,
				new Object[] {
					serviceComponentModelImpl.getOriginalBuildNamespace(),
					new Long(serviceComponentModelImpl.getOriginalBuildNumber())
				});
		}

		if (isNew ||
				(!Validator.equals(serviceComponent.getBuildNamespace(),
					serviceComponentModelImpl.getOriginalBuildNamespace()) ||
				(serviceComponent.getBuildNumber() != serviceComponentModelImpl.getOriginalBuildNumber()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
				new Object[] {
					serviceComponent.getBuildNamespace(),
					new Long(serviceComponent.getBuildNumber())
				}, serviceComponent);
		}

		return serviceComponent;
	}

	protected ServiceComponent toUnwrappedModel(
		ServiceComponent serviceComponent) {
		if (serviceComponent instanceof ServiceComponentImpl) {
			return serviceComponent;
		}

		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setNew(serviceComponent.isNew());
		serviceComponentImpl.setPrimaryKey(serviceComponent.getPrimaryKey());

		serviceComponentImpl.setServiceComponentId(serviceComponent.getServiceComponentId());
		serviceComponentImpl.setBuildNamespace(serviceComponent.getBuildNamespace());
		serviceComponentImpl.setBuildNumber(serviceComponent.getBuildNumber());
		serviceComponentImpl.setBuildDate(serviceComponent.getBuildDate());
		serviceComponentImpl.setData(serviceComponent.getData());

		return serviceComponentImpl;
	}

	public ServiceComponent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByPrimaryKey(serviceComponentId);

		if (serviceComponent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					serviceComponentId);
			}

			throw new NoSuchServiceComponentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				serviceComponentId);
		}

		return serviceComponent;
	}

	public ServiceComponent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ServiceComponent fetchByPrimaryKey(long serviceComponentId)
		throws SystemException {
		ServiceComponent serviceComponent = (ServiceComponent)EntityCacheUtil.getResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
				ServiceComponentImpl.class, serviceComponentId, this);

		if (serviceComponent == null) {
			Session session = null;

			try {
				session = openSession();

				serviceComponent = (ServiceComponent)session.get(ServiceComponentImpl.class,
						new Long(serviceComponentId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (serviceComponent != null) {
					cacheResult(serviceComponent);
				}

				closeSession(session);
			}
		}

		return serviceComponent;
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace)
		throws SystemException {
		Object[] finderArgs = new Object[] { buildNamespace };

		List<ServiceComponent> list = (List<ServiceComponent>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_BUILDNAMESPACE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

				if (buildNamespace == null) {
					query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
				}
				else {
					if (buildNamespace.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
					}
					else {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
					}
				}

				query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ServiceComponent>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_BUILDNAMESPACE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end) throws SystemException {
		return findByBuildNamespace(buildNamespace, start, end, null);
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				buildNamespace,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ServiceComponent> list = (List<ServiceComponent>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_BUILDNAMESPACE,
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

				query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

				if (buildNamespace == null) {
					query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
				}
				else {
					if (buildNamespace.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
					}
					else {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				list = (List<ServiceComponent>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ServiceComponent>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_BUILDNAMESPACE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ServiceComponent findByBuildNamespace_First(String buildNamespace,
		OrderByComparator obc)
		throws NoSuchServiceComponentException, SystemException {
		List<ServiceComponent> list = findByBuildNamespace(buildNamespace, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("buildNamespace=");
			msg.append(buildNamespace);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchServiceComponentException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ServiceComponent findByBuildNamespace_Last(String buildNamespace,
		OrderByComparator obc)
		throws NoSuchServiceComponentException, SystemException {
		int count = countByBuildNamespace(buildNamespace);

		List<ServiceComponent> list = findByBuildNamespace(buildNamespace,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("buildNamespace=");
			msg.append(buildNamespace);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchServiceComponentException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, String buildNamespace, OrderByComparator obc)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = findByPrimaryKey(serviceComponentId);

		int count = countByBuildNamespace(buildNamespace);

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

			query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

			if (buildNamespace == null) {
				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
			}
			else {
				if (buildNamespace.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
				}
				else {
					query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (buildNamespace != null) {
				qPos.add(buildNamespace);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					serviceComponent);

			ServiceComponent[] array = new ServiceComponentImpl[3];

			array[0] = (ServiceComponent)objArray[0];
			array[1] = (ServiceComponent)objArray[1];
			array[2] = (ServiceComponent)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ServiceComponent findByBNS_BNU(String buildNamespace,
		long buildNumber)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByBNS_BNU(buildNamespace,
				buildNumber);

		if (serviceComponent == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("buildNamespace=");
			msg.append(buildNamespace);

			msg.append(", buildNumber=");
			msg.append(buildNumber);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchServiceComponentException(msg.toString());
		}

		return serviceComponent;
	}

	public ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber) throws SystemException {
		return fetchByBNS_BNU(buildNamespace, buildNumber, true);
	}

	public ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { buildNamespace, new Long(buildNumber) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_BNS_BNU,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

				if (buildNamespace == null) {
					query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1);
				}
				else {
					if (buildNamespace.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
					}
					else {
						query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
					}
				}

				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

				query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

				List<ServiceComponent> list = q.list();

				result = list;

				ServiceComponent serviceComponent = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
						finderArgs, list);
				}
				else {
					serviceComponent = list.get(0);

					cacheResult(serviceComponent);

					if ((serviceComponent.getBuildNamespace() == null) ||
							!serviceComponent.getBuildNamespace()
												 .equals(buildNamespace) ||
							(serviceComponent.getBuildNumber() != buildNumber)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
							finderArgs, serviceComponent);
					}
				}

				return serviceComponent;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
						finderArgs, new ArrayList<ServiceComponent>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ServiceComponent)result;
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

	public List<ServiceComponent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ServiceComponent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ServiceComponent> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ServiceComponent> list = (List<ServiceComponent>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SERVICECOMPONENT);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SERVICECOMPONENT.concat(ServiceComponentModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ServiceComponent>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByBuildNamespace(String buildNamespace)
		throws SystemException {
		for (ServiceComponent serviceComponent : findByBuildNamespace(
				buildNamespace)) {
			remove(serviceComponent);
		}
	}

	public void removeByBNS_BNU(String buildNamespace, long buildNumber)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = findByBNS_BNU(buildNamespace,
				buildNumber);

		remove(serviceComponent);
	}

	public void removeAll() throws SystemException {
		for (ServiceComponent serviceComponent : findAll()) {
			remove(serviceComponent);
		}
	}

	public int countByBuildNamespace(String buildNamespace)
		throws SystemException {
		Object[] finderArgs = new Object[] { buildNamespace };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BUILDNAMESPACE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

				if (buildNamespace == null) {
					query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
				}
				else {
					if (buildNamespace.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
					}
					else {
						query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BUILDNAMESPACE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByBNS_BNU(String buildNamespace, long buildNumber)
		throws SystemException {
		Object[] finderArgs = new Object[] { buildNamespace, new Long(buildNumber) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BNS_BNU,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

				if (buildNamespace == null) {
					query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1);
				}
				else {
					if (buildNamespace.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
					}
					else {
						query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
					}
				}

				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BNS_BNU,
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

				Query q = session.createQuery(_SQL_COUNT_SERVICECOMPONENT);

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
						"value.object.listener.com.liferay.portal.model.ServiceComponent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ServiceComponent>> listenersList = new ArrayList<ModelListener<ServiceComponent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ServiceComponent>)Class.forName(
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
	private static final String _SQL_SELECT_SERVICECOMPONENT = "SELECT serviceComponent FROM ServiceComponent serviceComponent";
	private static final String _SQL_SELECT_SERVICECOMPONENT_WHERE = "SELECT serviceComponent FROM ServiceComponent serviceComponent WHERE ";
	private static final String _SQL_COUNT_SERVICECOMPONENT = "SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent";
	private static final String _SQL_COUNT_SERVICECOMPONENT_WHERE = "SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent WHERE ";
	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1 = "serviceComponent.buildNamespace IS NULL";
	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2 = "serviceComponent.buildNamespace = ?";
	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3 = "(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = ?)";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1 = "serviceComponent.buildNamespace IS NULL AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2 = "serviceComponent.buildNamespace = ? AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3 = "(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = ?) AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2 = "serviceComponent.buildNumber = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "serviceComponent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ServiceComponent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ServiceComponent exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ServiceComponentPersistenceImpl.class);
}