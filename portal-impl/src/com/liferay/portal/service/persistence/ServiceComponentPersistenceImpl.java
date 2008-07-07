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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchServiceComponentException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.hibernate.Query;
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.QueryUtil;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.search.DynamicQuery;
import com.liferay.portal.kernel.dao.search.DynamicQueryInitializer;
import com.liferay.portal.kernel.spring.hibernate.FinderCacheUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ServiceComponentPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ServiceComponentPersistenceImpl extends BasePersistenceImpl
	implements ServiceComponentPersistence {
	public ServiceComponent create(long serviceComponentId) {
		ServiceComponent serviceComponent = new ServiceComponentImpl();

		serviceComponent.setNew(true);
		serviceComponent.setPrimaryKey(serviceComponentId);

		return serviceComponent;
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
					_log.warn(
						"No ServiceComponent exists with the primary key " +
						serviceComponentId);
				}

				throw new NoSuchServiceComponentException(
					"No ServiceComponent exists with the primary key " +
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
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(serviceComponent);
			}
		}

		serviceComponent = removeImpl(serviceComponent);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(serviceComponent);
			}
		}

		return serviceComponent;
	}

	protected ServiceComponent removeImpl(ServiceComponent serviceComponent)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(serviceComponent);

			session.flush();

			return serviceComponent;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ServiceComponent.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ServiceComponent serviceComponent, boolean merge)</code>.
	 */
	public ServiceComponent update(ServiceComponent serviceComponent)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ServiceComponent serviceComponent) method. Use update(ServiceComponent serviceComponent, boolean merge) instead.");
		}

		return update(serviceComponent, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        serviceComponent the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when serviceComponent is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ServiceComponent update(ServiceComponent serviceComponent,
		boolean merge) throws SystemException {
		boolean isNew = serviceComponent.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(serviceComponent);
				}
				else {
					listener.onBeforeUpdate(serviceComponent);
				}
			}
		}

		serviceComponent = updateImpl(serviceComponent, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(serviceComponent);
				}
				else {
					listener.onAfterUpdate(serviceComponent);
				}
			}
		}

		return serviceComponent;
	}

	public ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(serviceComponent);
			}
			else {
				if (serviceComponent.isNew()) {
					session.save(serviceComponent);
				}
			}

			session.flush();

			serviceComponent.setNew(false);

			return serviceComponent;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ServiceComponent.class.getName());
		}
	}

	public ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByPrimaryKey(serviceComponentId);

		if (serviceComponent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ServiceComponent exists with the primary key " +
					serviceComponentId);
			}

			throw new NoSuchServiceComponentException(
				"No ServiceComponent exists with the primary key " +
				serviceComponentId);
		}

		return serviceComponent;
	}

	public ServiceComponent fetchByPrimaryKey(long serviceComponentId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ServiceComponent)session.get(ServiceComponentImpl.class,
				new Long(serviceComponentId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "findByBuildNamespace";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { buildNamespace };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ServiceComponent WHERE ");

				if (buildNamespace == null) {
					query.append("buildNamespace IS NULL");
				}
				else {
					query.append("buildNamespace = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("buildNamespace DESC, ");
				query.append("buildNumber DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				List<ServiceComponent> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<ServiceComponent>)result;
		}
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end) throws SystemException {
		return findByBuildNamespace(buildNamespace, start, end, null);
	}

	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "findByBuildNamespace";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				buildNamespace,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ServiceComponent WHERE ");

				if (buildNamespace == null) {
					query.append("buildNamespace IS NULL");
				}
				else {
					query.append("buildNamespace = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("buildNamespace DESC, ");
					query.append("buildNumber DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				List<ServiceComponent> list = (List<ServiceComponent>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<ServiceComponent>)result;
		}
	}

	public ServiceComponent findByBuildNamespace_First(String buildNamespace,
		OrderByComparator obc)
		throws NoSuchServiceComponentException, SystemException {
		List<ServiceComponent> list = findByBuildNamespace(buildNamespace, 0,
				1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ServiceComponent exists with the key {");

			msg.append("buildNamespace=" + buildNamespace);

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

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ServiceComponent exists with the key {");

			msg.append("buildNamespace=" + buildNamespace);

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

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.ServiceComponent WHERE ");

			if (buildNamespace == null) {
				query.append("buildNamespace IS NULL");
			}
			else {
				query.append("buildNamespace = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("buildNamespace DESC, ");
				query.append("buildNumber DESC");
			}

			Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No ServiceComponent exists with the key {");

			msg.append("buildNamespace=" + buildNamespace);

			msg.append(", ");
			msg.append("buildNumber=" + buildNumber);

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
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "fetchByBNS_BNU";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { buildNamespace, new Long(buildNumber) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.ServiceComponent WHERE ");

				if (buildNamespace == null) {
					query.append("buildNamespace IS NULL");
				}
				else {
					query.append("buildNamespace = ?");
				}

				query.append(" AND ");

				query.append("buildNumber = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("buildNamespace DESC, ");
				query.append("buildNumber DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

				List<ServiceComponent> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ServiceComponent> list = (List<ServiceComponent>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ServiceComponent> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ServiceComponent> findWithDynamicQuery(
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
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.ServiceComponent ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("buildNamespace DESC, ");
					query.append("buildNumber DESC");
				}

				Query q = session.createQuery(query.toString());

				List<ServiceComponent> list = (List<ServiceComponent>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<ServiceComponent>)result;
		}
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
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "countByBuildNamespace";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { buildNamespace };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ServiceComponent WHERE ");

				if (buildNamespace == null) {
					query.append("buildNamespace IS NULL");
				}
				else {
					query.append("buildNamespace = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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

	public int countByBNS_BNU(String buildNamespace, long buildNumber)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "countByBNS_BNU";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { buildNamespace, new Long(buildNumber) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.ServiceComponent WHERE ");

				if (buildNamespace == null) {
					query.append("buildNamespace IS NULL");
				}
				else {
					query.append("buildNamespace = ?");
				}

				query.append(" AND ");

				query.append("buildNumber = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (buildNamespace != null) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ServiceComponentModelImpl.CACHE_ENABLED;
		String finderClassName = ServiceComponent.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.ServiceComponent");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ServiceComponent")));

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

	private static Log _log = LogFactory.getLog(ServiceComponentPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}