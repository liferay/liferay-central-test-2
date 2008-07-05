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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagModelImpl;

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
 * <a href="MBMessageFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagPersistenceImpl extends BasePersistence
	implements MBMessageFlagPersistence {
	public MBMessageFlag create(long messageFlagId) {
		MBMessageFlag mbMessageFlag = new MBMessageFlagImpl();

		mbMessageFlag.setNew(true);
		mbMessageFlag.setPrimaryKey(messageFlagId);

		return mbMessageFlag;
	}

	public MBMessageFlag remove(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessageFlag mbMessageFlag = (MBMessageFlag)session.get(MBMessageFlagImpl.class,
					new Long(messageFlagId));

			if (mbMessageFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBMessageFlag exists with the primary key " +
						messageFlagId);
				}

				throw new NoSuchMessageFlagException(
					"No MBMessageFlag exists with the primary key " +
					messageFlagId);
			}

			return remove(mbMessageFlag);
		}
		catch (NoSuchMessageFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessageFlag remove(MBMessageFlag mbMessageFlag)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(mbMessageFlag);
			}
		}

		mbMessageFlag = removeImpl(mbMessageFlag);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(mbMessageFlag);
			}
		}

		return mbMessageFlag;
	}

	protected MBMessageFlag removeImpl(MBMessageFlag mbMessageFlag)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(mbMessageFlag);

			session.flush();

			return mbMessageFlag;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(MBMessageFlag.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MBMessageFlag mbMessageFlag, boolean merge)</code>.
	 */
	public MBMessageFlag update(MBMessageFlag mbMessageFlag)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBMessageFlag mbMessageFlag) method. Use update(MBMessageFlag mbMessageFlag, boolean merge) instead.");
		}

		return update(mbMessageFlag, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbMessageFlag the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbMessageFlag is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MBMessageFlag update(MBMessageFlag mbMessageFlag, boolean merge)
		throws SystemException {
		boolean isNew = mbMessageFlag.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(mbMessageFlag);
				}
				else {
					listener.onBeforeUpdate(mbMessageFlag);
				}
			}
		}

		mbMessageFlag = updateImpl(mbMessageFlag, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(mbMessageFlag);
				}
				else {
					listener.onAfterUpdate(mbMessageFlag);
				}
			}
		}

		return mbMessageFlag;
	}

	public MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(mbMessageFlag);
			}
			else {
				if (mbMessageFlag.isNew()) {
					session.save(mbMessageFlag);
				}
			}

			session.flush();

			mbMessageFlag.setNew(false);

			return mbMessageFlag;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(MBMessageFlag.class.getName());
		}
	}

	public MBMessageFlag findByPrimaryKey(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByPrimaryKey(messageFlagId);

		if (mbMessageFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBMessageFlag exists with the primary key " +
					messageFlagId);
			}

			throw new NoSuchMessageFlagException(
				"No MBMessageFlag exists with the primary key " +
				messageFlagId);
		}

		return mbMessageFlag;
	}

	public MBMessageFlag fetchByPrimaryKey(long messageFlagId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBMessageFlag)session.get(MBMessageFlagImpl.class,
				new Long(messageFlagId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBMessageFlag> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<MBMessageFlag>)result;
		}
	}

	public List<MBMessageFlag> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBMessageFlag> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBMessageFlag> list = (List<MBMessageFlag>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<MBMessageFlag>)result;
		}
	}

	public MBMessageFlag findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessageFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByUserId(userId);

		List<MBMessageFlag> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessageFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByUserId_PrevAndNext(long messageFlagId,
		long userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

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
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessageFlag> findByMessageId(long messageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
		String finderMethodName = "findByMessageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(messageId) };

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

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("messageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				List<MBMessageFlag> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<MBMessageFlag>)result;
		}
	}

	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end) throws SystemException {
		return findByMessageId(messageId, start, end, null);
	}

	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
		String finderMethodName = "findByMessageId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(messageId),
				
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

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("messageId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				List<MBMessageFlag> list = (List<MBMessageFlag>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<MBMessageFlag>)result;
		}
	}

	public MBMessageFlag findByMessageId_First(long messageId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByMessageId(messageId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessageFlag exists with the key {");

			msg.append("messageId=" + messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag findByMessageId_Last(long messageId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByMessageId(messageId);

		List<MBMessageFlag> list = findByMessageId(messageId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessageFlag exists with the key {");

			msg.append("messageId=" + messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessageFlag[] findByMessageId_PrevAndNext(long messageFlagId,
		long messageId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		int count = countByMessageId(messageId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

			query.append("messageId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(messageId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag);

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = (MBMessageFlag)objArray[0];
			array[1] = (MBMessageFlag)objArray[1];
			array[2] = (MBMessageFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessageFlag findByU_M(long userId, long messageId)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByU_M(userId, messageId);

		if (mbMessageFlag == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessageFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("messageId=" + messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageFlagException(msg.toString());
		}

		return mbMessageFlag;
	}

	public MBMessageFlag fetchByU_M(long userId, long messageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
		String finderMethodName = "fetchByU_M";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), new Long(messageId) };

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

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("messageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

				List<MBMessageFlag> list = q.list();

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
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<MBMessageFlag> list = (List<MBMessageFlag>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<MBMessageFlag> findWithDynamicQuery(
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

	public List<MBMessageFlag> findWithDynamicQuery(
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

	public List<MBMessageFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMessageFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMessageFlag> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
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

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<MBMessageFlag> list = (List<MBMessageFlag>)QueryUtil.list(q,
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
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<MBMessageFlag>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByUserId(userId)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByMessageId(long messageId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByMessageId(messageId)) {
			remove(mbMessageFlag);
		}
	}

	public void removeByU_M(long userId, long messageId)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByU_M(userId, messageId);

		remove(mbMessageFlag);
	}

	public void removeAll() throws SystemException {
		for (MBMessageFlag mbMessageFlag : findAll()) {
			remove(mbMessageFlag);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

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

	public int countByMessageId(long messageId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
		String finderMethodName = "countByMessageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(messageId) };

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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("messageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

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

	public int countByU_M(long userId, long messageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
		String finderMethodName = "countByU_M";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), new Long(messageId) };

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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessageFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("messageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

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
		boolean finderClassNameCacheEnabled = MBMessageFlagModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessageFlag.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.messageboards.model.MBMessageFlag");

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
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessageFlag")));

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

	private static Log _log = LogFactory.getLog(MBMessageFlagPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}