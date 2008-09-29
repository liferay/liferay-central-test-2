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

import com.liferay.portal.NoSuchConfiguredProducerException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ConfiguredProducer;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ConfiguredProducerImpl;
import com.liferay.portal.model.impl.ConfiguredProducerModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ConfiguredProducerPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfiguredProducerPersistenceImpl extends BasePersistenceImpl
	implements ConfiguredProducerPersistence {
	public ConfiguredProducer create(ConfiguredProducerPK configuredProducerPK) {
		ConfiguredProducer configuredProducer = new ConfiguredProducerImpl();

		configuredProducer.setNew(true);
		configuredProducer.setPrimaryKey(configuredProducerPK);

		return configuredProducer;
	}

	public ConfiguredProducer remove(ConfiguredProducerPK configuredProducerPK)
		throws NoSuchConfiguredProducerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ConfiguredProducer configuredProducer = (ConfiguredProducer)session.get(ConfiguredProducerImpl.class,
					configuredProducerPK);

			if (configuredProducer == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ConfiguredProducer exists with the primary key " +
						configuredProducerPK);
				}

				throw new NoSuchConfiguredProducerException(
					"No ConfiguredProducer exists with the primary key " +
					configuredProducerPK);
			}

			return remove(configuredProducer);
		}
		catch (NoSuchConfiguredProducerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ConfiguredProducer remove(ConfiguredProducer configuredProducer)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(configuredProducer);
			}
		}

		configuredProducer = removeImpl(configuredProducer);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(configuredProducer);
			}
		}

		return configuredProducer;
	}

	protected ConfiguredProducer removeImpl(
		ConfiguredProducer configuredProducer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(configuredProducer);

			session.flush();

			return configuredProducer;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ConfiguredProducer.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ConfiguredProducer configuredProducer, boolean merge)</code>.
	 */
	public ConfiguredProducer update(ConfiguredProducer configuredProducer)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ConfiguredProducer configuredProducer) method. Use update(ConfiguredProducer configuredProducer, boolean merge) instead.");
		}

		return update(configuredProducer, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        configuredProducer the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when configuredProducer is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ConfiguredProducer update(ConfiguredProducer configuredProducer,
		boolean merge) throws SystemException {
		boolean isNew = configuredProducer.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(configuredProducer);
				}
				else {
					listener.onBeforeUpdate(configuredProducer);
				}
			}
		}

		configuredProducer = updateImpl(configuredProducer, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(configuredProducer);
				}
				else {
					listener.onAfterUpdate(configuredProducer);
				}
			}
		}

		return configuredProducer;
	}

	public ConfiguredProducer updateImpl(
		com.liferay.portal.model.ConfiguredProducer configuredProducer,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(configuredProducer);
			}
			else {
				if (configuredProducer.isNew()) {
					session.save(configuredProducer);
				}
			}

			session.flush();

			configuredProducer.setNew(false);

			return configuredProducer;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ConfiguredProducer.class.getName());
		}
	}

	public ConfiguredProducer findByPrimaryKey(
		ConfiguredProducerPK configuredProducerPK)
		throws NoSuchConfiguredProducerException, SystemException {
		ConfiguredProducer configuredProducer = fetchByPrimaryKey(configuredProducerPK);

		if (configuredProducer == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ConfiguredProducer exists with the primary key " +
					configuredProducerPK);
			}

			throw new NoSuchConfiguredProducerException(
				"No ConfiguredProducer exists with the primary key " +
				configuredProducerPK);
		}

		return configuredProducer;
	}

	public ConfiguredProducer fetchByPrimaryKey(
		ConfiguredProducerPK configuredProducerPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ConfiguredProducer)session.get(ConfiguredProducerImpl.class,
				configuredProducerPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ConfiguredProducer> findByP_N(String portalId, String namespace)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ConfiguredProducerModelImpl.CACHE_ENABLED;
		String finderClassName = ConfiguredProducer.class.getName();
		String finderMethodName = "findByP_N";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { portalId, namespace };

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
					"FROM com.liferay.portal.model.ConfiguredProducer WHERE ");

				if (portalId == null) {
					query.append("portalId IS NULL");
				}
				else {
					query.append("portalId = ?");
				}

				query.append(" AND ");

				if (namespace == null) {
					query.append("namespace IS NULL");
				}
				else {
					query.append("namespace = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (portalId != null) {
					qPos.add(portalId);
				}

				if (namespace != null) {
					qPos.add(namespace);
				}

				List<ConfiguredProducer> list = q.list();

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
			return (List<ConfiguredProducer>)result;
		}
	}

	public List<ConfiguredProducer> findByP_N(String portalId,
		String namespace, int start, int end) throws SystemException {
		return findByP_N(portalId, namespace, start, end, null);
	}

	public List<ConfiguredProducer> findByP_N(String portalId,
		String namespace, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ConfiguredProducerModelImpl.CACHE_ENABLED;
		String finderClassName = ConfiguredProducer.class.getName();
		String finderMethodName = "findByP_N";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				portalId,
				
				namespace,
				
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
					"FROM com.liferay.portal.model.ConfiguredProducer WHERE ");

				if (portalId == null) {
					query.append("portalId IS NULL");
				}
				else {
					query.append("portalId = ?");
				}

				query.append(" AND ");

				if (namespace == null) {
					query.append("namespace IS NULL");
				}
				else {
					query.append("namespace = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (portalId != null) {
					qPos.add(portalId);
				}

				if (namespace != null) {
					qPos.add(namespace);
				}

				List<ConfiguredProducer> list = (List<ConfiguredProducer>)QueryUtil.list(q,
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
			return (List<ConfiguredProducer>)result;
		}
	}

	public ConfiguredProducer findByP_N_First(String portalId,
		String namespace, OrderByComparator obc)
		throws NoSuchConfiguredProducerException, SystemException {
		List<ConfiguredProducer> list = findByP_N(portalId, namespace, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ConfiguredProducer exists with the key {");

			msg.append("portalId=" + portalId);

			msg.append(", ");
			msg.append("namespace=" + namespace);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchConfiguredProducerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ConfiguredProducer findByP_N_Last(String portalId, String namespace,
		OrderByComparator obc)
		throws NoSuchConfiguredProducerException, SystemException {
		int count = countByP_N(portalId, namespace);

		List<ConfiguredProducer> list = findByP_N(portalId, namespace,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ConfiguredProducer exists with the key {");

			msg.append("portalId=" + portalId);

			msg.append(", ");
			msg.append("namespace=" + namespace);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchConfiguredProducerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ConfiguredProducer[] findByP_N_PrevAndNext(
		ConfiguredProducerPK configuredProducerPK, String portalId,
		String namespace, OrderByComparator obc)
		throws NoSuchConfiguredProducerException, SystemException {
		ConfiguredProducer configuredProducer = findByPrimaryKey(configuredProducerPK);

		int count = countByP_N(portalId, namespace);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portal.model.ConfiguredProducer WHERE ");

			if (portalId == null) {
				query.append("portalId IS NULL");
			}
			else {
				query.append("portalId = ?");
			}

			query.append(" AND ");

			if (namespace == null) {
				query.append("namespace IS NULL");
			}
			else {
				query.append("namespace = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (portalId != null) {
				qPos.add(portalId);
			}

			if (namespace != null) {
				qPos.add(namespace);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					configuredProducer);

			ConfiguredProducer[] array = new ConfiguredProducerImpl[3];

			array[0] = (ConfiguredProducer)objArray[0];
			array[1] = (ConfiguredProducer)objArray[1];
			array[2] = (ConfiguredProducer)objArray[2];

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

	public List<ConfiguredProducer> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ConfiguredProducer> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ConfiguredProducer> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ConfiguredProducerModelImpl.CACHE_ENABLED;
		String finderClassName = ConfiguredProducer.class.getName();
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

				query.append(
					"FROM com.liferay.portal.model.ConfiguredProducer ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ConfiguredProducer> list = (List<ConfiguredProducer>)QueryUtil.list(q,
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
			return (List<ConfiguredProducer>)result;
		}
	}

	public void removeByP_N(String portalId, String namespace)
		throws SystemException {
		for (ConfiguredProducer configuredProducer : findByP_N(portalId,
				namespace)) {
			remove(configuredProducer);
		}
	}

	public void removeAll() throws SystemException {
		for (ConfiguredProducer configuredProducer : findAll()) {
			remove(configuredProducer);
		}
	}

	public int countByP_N(String portalId, String namespace)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ConfiguredProducerModelImpl.CACHE_ENABLED;
		String finderClassName = ConfiguredProducer.class.getName();
		String finderMethodName = "countByP_N";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { portalId, namespace };

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
					"FROM com.liferay.portal.model.ConfiguredProducer WHERE ");

				if (portalId == null) {
					query.append("portalId IS NULL");
				}
				else {
					query.append("portalId = ?");
				}

				query.append(" AND ");

				if (namespace == null) {
					query.append("namespace IS NULL");
				}
				else {
					query.append("namespace = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (portalId != null) {
					qPos.add(portalId);
				}

				if (namespace != null) {
					qPos.add(namespace);
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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ConfiguredProducerModelImpl.CACHE_ENABLED;
		String finderClassName = ConfiguredProducer.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.ConfiguredProducer");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ConfiguredProducer")));

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

	private static Log _log = LogFactory.getLog(ConfiguredProducerPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}