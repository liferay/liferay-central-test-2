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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AnnouncementsFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsFlagPersistenceImpl extends BasePersistenceImpl
	implements AnnouncementsFlagPersistence {
	public AnnouncementsFlag create(long flagId) {
		AnnouncementsFlag announcementsFlag = new AnnouncementsFlagImpl();

		announcementsFlag.setNew(true);
		announcementsFlag.setPrimaryKey(flagId);

		return announcementsFlag;
	}

	public AnnouncementsFlag remove(long flagId)
		throws NoSuchFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
					new Long(flagId));

			if (announcementsFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementsFlag exists with the primary key " +
						flagId);
				}

				throw new NoSuchFlagException(
					"No AnnouncementsFlag exists with the primary key " +
					flagId);
			}

			return remove(announcementsFlag);
		}
		catch (NoSuchFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsFlag remove(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(announcementsFlag);
			}
		}

		announcementsFlag = removeImpl(announcementsFlag);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(announcementsFlag);
			}
		}

		return announcementsFlag;
	}

	protected AnnouncementsFlag removeImpl(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(announcementsFlag);

			session.flush();

			return announcementsFlag;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(AnnouncementsFlag.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(AnnouncementsFlag announcementsFlag, boolean merge)</code>.
	 */
	public AnnouncementsFlag update(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(AnnouncementsFlag announcementsFlag) method. Use update(AnnouncementsFlag announcementsFlag, boolean merge) instead.");
		}

		return update(announcementsFlag, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        announcementsFlag the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when announcementsFlag is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public AnnouncementsFlag update(AnnouncementsFlag announcementsFlag,
		boolean merge) throws SystemException {
		boolean isNew = announcementsFlag.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(announcementsFlag);
				}
				else {
					listener.onBeforeUpdate(announcementsFlag);
				}
			}
		}

		announcementsFlag = updateImpl(announcementsFlag, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(announcementsFlag);
				}
				else {
					listener.onAfterUpdate(announcementsFlag);
				}
			}
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(announcementsFlag);
			}
			else {
				if (announcementsFlag.isNew()) {
					session.save(announcementsFlag);
				}
			}

			session.flush();

			announcementsFlag.setNew(false);

			return announcementsFlag;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(AnnouncementsFlag.class.getName());
		}
	}

	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(flagId);

		if (announcementsFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AnnouncementsFlag exists with the primary key " +
					flagId);
			}

			throw new NoSuchFlagException(
				"No AnnouncementsFlag exists with the primary key " + flagId);
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByPrimaryKey(long flagId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
				new Long(flagId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
		String finderMethodName = "findByEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(entryId) };

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
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				List<AnnouncementsFlag> list = q.list();

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
			return (List<AnnouncementsFlag>)result;
		}
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end) throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
		String finderMethodName = "findByEntryId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(entryId),
				
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)QueryUtil.list(q,
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
			return (List<AnnouncementsFlag>)result;
		}
	}

	public AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator obc) throws NoSuchFlagException, SystemException {
		List<AnnouncementsFlag> list = findByEntryId(entryId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator obc) throws NoSuchFlagException, SystemException {
		int count = countByEntryId(entryId);

		List<AnnouncementsFlag> list = findByEntryId(entryId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("entryId=" + entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator obc)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		int count = countByEntryId(entryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

			query.append("entryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsFlag);

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = (AnnouncementsFlag)objArray[0];
			array[1] = (AnnouncementsFlag)objArray[1];
			array[2] = (AnnouncementsFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByU_E_V(userId, entryId,
				value);

		if (announcementsFlag == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("entryId=" + entryId);

			msg.append(", ");
			msg.append("value=" + value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFlagException(msg.toString());
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
		String finderMethodName = "fetchByU_E_V";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("entryId = ?");

				query.append(" AND ");

				query.append("value = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				List<AnnouncementsFlag> list = q.list();

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
			List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
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

	public List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementsFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)QueryUtil.list(q,
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
			return (List<AnnouncementsFlag>)result;
		}
	}

	public void removeByEntryId(long entryId) throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findByEntryId(entryId)) {
			remove(announcementsFlag);
		}
	}

	public void removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByU_E_V(userId, entryId, value);

		remove(announcementsFlag);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findAll()) {
			remove(announcementsFlag);
		}
	}

	public int countByEntryId(long entryId) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
		String finderMethodName = "countByEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(entryId) };

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
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

				query.append("entryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

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

	public int countByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
		String finderMethodName = "countByU_E_V";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementsFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("entryId = ?");

				query.append(" AND ");

				query.append("value = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

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
		boolean finderClassNameCacheEnabled = AnnouncementsFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementsFlag.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.AnnouncementsFlag");

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsFlag")));

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

	private static Log _log = LogFactory.getLog(AnnouncementsFlagPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}