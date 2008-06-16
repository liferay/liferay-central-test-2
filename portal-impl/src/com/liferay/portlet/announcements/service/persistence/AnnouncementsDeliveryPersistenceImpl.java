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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import com.liferay.util.dao.hibernate.QueryPos;
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
 * <a href="AnnouncementsDeliveryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsDeliveryPersistenceImpl extends BasePersistence
	implements AnnouncementsDeliveryPersistence {
	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery = new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		return announcementsDelivery;
	}

	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
					new Long(deliveryId));

			if (announcementsDelivery == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementsDelivery exists with the primary key " +
						deliveryId);
				}

				throw new NoSuchDeliveryException(
					"No AnnouncementsDelivery exists with the primary key " +
					deliveryId);
			}

			return remove(announcementsDelivery);
		}
		catch (NoSuchDeliveryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsDelivery remove(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(announcementsDelivery);
			}
		}

		announcementsDelivery = removeImpl(announcementsDelivery);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(announcementsDelivery);
			}
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(announcementsDelivery);

			session.flush();

			return announcementsDelivery;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementsDelivery.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(AnnouncementsDelivery announcementsDelivery, boolean merge)</code>.
	 */
	public AnnouncementsDelivery update(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AnnouncementsDelivery announcementsDelivery) method. Use update(AnnouncementsDelivery announcementsDelivery, boolean merge) instead.");
		}

		return update(announcementsDelivery, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        announcementsDelivery the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when announcementsDelivery is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public AnnouncementsDelivery update(
		AnnouncementsDelivery announcementsDelivery, boolean merge)
		throws SystemException {
		boolean isNew = announcementsDelivery.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(announcementsDelivery);
				}
				else {
					listener.onBeforeUpdate(announcementsDelivery);
				}
			}
		}

		announcementsDelivery = updateImpl(announcementsDelivery, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(announcementsDelivery);
				}
				else {
					listener.onAfterUpdate(announcementsDelivery);
				}
			}
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(announcementsDelivery);
			}
			else {
				if (announcementsDelivery.isNew()) {
					session.save(announcementsDelivery);
				}
			}

			session.flush();

			announcementsDelivery.setNew(false);

			return announcementsDelivery;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementsDelivery.class.getName());
		}
	}

	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(deliveryId);

		if (announcementsDelivery == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No AnnouncementsDelivery exists with the primary key " +
					deliveryId);
			}

			throw new NoSuchDeliveryException(
				"No AnnouncementsDelivery exists with the primary key " +
				deliveryId);
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
				new Long(deliveryId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsDelivery> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<AnnouncementsDelivery> list = q.list();

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
			return (List<AnnouncementsDelivery>)result;
		}
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
						getDialect(), start, end);

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
			return (List<AnnouncementsDelivery>)result;
		}
	}

	public AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator obc) throws NoSuchDeliveryException, SystemException {
		List<AnnouncementsDelivery> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator obc) throws NoSuchDeliveryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsDelivery> list = findByUserId(userId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId, OrderByComparator obc)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByPrimaryKey(deliveryId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsDelivery);

			AnnouncementsDelivery[] array = new AnnouncementsDeliveryImpl[3];

			array[0] = (AnnouncementsDelivery)objArray[0];
			array[1] = (AnnouncementsDelivery)objArray[1];
			array[2] = (AnnouncementsDelivery)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDeliveryException(msg.toString());
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByU_T(long userId, String type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
		String finderMethodName = "fetchByU_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<AnnouncementsDelivery> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<AnnouncementsDelivery> findWithDynamicQuery(
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

	public List<AnnouncementsDelivery> findWithDynamicQuery(
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

	public List<AnnouncementsDelivery> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
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
			return (List<AnnouncementsDelivery>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findByUserId(userId)) {
			remove(announcementsDelivery);
		}
	}

	public void removeByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		remove(announcementsDelivery);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	public int countByU_T(long userId, String type) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
		String finderMethodName = "countByU_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsDeliveryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsDelivery.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.AnnouncementsDelivery");

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

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsDelivery")));

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

	private static Log _log = LogFactory.getLog(AnnouncementsDeliveryPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}