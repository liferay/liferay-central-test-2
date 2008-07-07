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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBBanPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBBanPersistenceImpl extends BasePersistenceImpl
	implements MBBanPersistence {
	public MBBan create(long banId) {
		MBBan mbBan = new MBBanImpl();

		mbBan.setNew(true);
		mbBan.setPrimaryKey(banId);

		return mbBan;
	}

	public MBBan remove(long banId) throws NoSuchBanException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBBan mbBan = (MBBan)session.get(MBBanImpl.class, new Long(banId));

			if (mbBan == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBBan exists with the primary key " + banId);
				}

				throw new NoSuchBanException(
					"No MBBan exists with the primary key " + banId);
			}

			return remove(mbBan);
		}
		catch (NoSuchBanException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan remove(MBBan mbBan) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(mbBan);
			}
		}

		mbBan = removeImpl(mbBan);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(mbBan);
			}
		}

		return mbBan;
	}

	protected MBBan removeImpl(MBBan mbBan) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(mbBan);

			session.flush();

			return mbBan;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBBan.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MBBan mbBan, boolean merge)</code>.
	 */
	public MBBan update(MBBan mbBan) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBBan mbBan) method. Use update(MBBan mbBan, boolean merge) instead.");
		}

		return update(mbBan, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbBan the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbBan is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MBBan update(MBBan mbBan, boolean merge) throws SystemException {
		boolean isNew = mbBan.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(mbBan);
				}
				else {
					listener.onBeforeUpdate(mbBan);
				}
			}
		}

		mbBan = updateImpl(mbBan, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(mbBan);
				}
				else {
					listener.onAfterUpdate(mbBan);
				}
			}
		}

		return mbBan;
	}

	public MBBan updateImpl(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(mbBan);
			}
			else {
				if (mbBan.isNew()) {
					session.save(mbBan);
				}
			}

			session.flush();

			mbBan.setNew(false);

			return mbBan;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBBan.class.getName());
		}
	}

	public MBBan findByPrimaryKey(long banId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByPrimaryKey(banId);

		if (mbBan == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBBan exists with the primary key " + banId);
			}

			throw new NoSuchBanException(
				"No MBBan exists with the primary key " + banId);
		}

		return mbBan;
	}

	public MBBan fetchByPrimaryKey(long banId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBBan)session.get(MBBanImpl.class, new Long(banId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBBan> findByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MBBan> list = q.list();

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
			return (List<MBBan>)result;
		}
	}

	public List<MBBan> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBBan> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MBBan> list = (List<MBBan>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<MBBan>)result;
		}
	}

	public MBBan findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByGroupId(groupId);

		List<MBBan> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByGroupId_PrevAndNext(long banId, long groupId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBBan> findByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBBan> list = q.list();

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
			return (List<MBBan>)result;
		}
	}

	public List<MBBan> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBBan> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MBBan> list = (List<MBBan>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<MBBan>)result;
		}
	}

	public MBBan findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByUserId(userId);

		List<MBBan> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByUserId_PrevAndNext(long banId, long userId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBBan> findByBanUserId(long banUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "findByBanUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(banUserId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("banUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

				List<MBBan> list = q.list();

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
			return (List<MBBan>)result;
		}
	}

	public List<MBBan> findByBanUserId(long banUserId, int start, int end)
		throws SystemException {
		return findByBanUserId(banUserId, start, end, null);
	}

	public List<MBBan> findByBanUserId(long banUserId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "findByBanUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(banUserId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("banUserId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

				List<MBBan> list = (List<MBBan>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<MBBan>)result;
		}
	}

	public MBBan findByBanUserId_First(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByBanUserId(banUserId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByBanUserId_Last(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByBanUserId(banUserId);

		List<MBBan> list = findByBanUserId(banUserId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByBanUserId_PrevAndNext(long banId, long banUserId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByBanUserId(banUserId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

			query.append("banUserId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(banUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByG_B(groupId, banUserId);

		if (mbBan == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBanException(msg.toString());
		}

		return mbBan;
	}

	public MBBan fetchByG_B(long groupId, long banUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "fetchByG_B";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(banUserId)
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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("banUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

				List<MBBan> list = q.list();

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
			List<MBBan> list = (List<MBBan>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<MBBan> findWithDynamicQuery(
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

	public List<MBBan> findWithDynamicQuery(
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

	public List<MBBan> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBBan> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBBan> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBBan ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<MBBan> list = (List<MBBan>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<MBBan>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBBan mbBan : findByGroupId(groupId)) {
			remove(mbBan);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBBan mbBan : findByUserId(userId)) {
			remove(mbBan);
		}
	}

	public void removeByBanUserId(long banUserId) throws SystemException {
		for (MBBan mbBan : findByBanUserId(banUserId)) {
			remove(mbBan);
		}
	}

	public void removeByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByG_B(groupId, banUserId);

		remove(mbBan);
	}

	public void removeAll() throws SystemException {
		for (MBBan mbBan : findAll()) {
			remove(mbBan);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

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

	public int countByBanUserId(long banUserId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "countByBanUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(banUserId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("banUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

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

	public int countByG_B(long groupId, long banUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
		String finderMethodName = "countByG_B";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(banUserId)
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
					"FROM com.liferay.portlet.messageboards.model.MBBan WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("banUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

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
		boolean finderClassNameCacheEnabled = MBBanModelImpl.CACHE_ENABLED;
		String finderClassName = MBBan.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.messageboards.model.MBBan");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBBan")));

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

	private static Log _log = LogFactory.getLog(MBBanPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}