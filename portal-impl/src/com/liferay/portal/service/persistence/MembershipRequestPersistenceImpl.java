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

import com.liferay.portal.NoSuchMembershipRequestException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.MembershipRequestImpl;
import com.liferay.portal.model.impl.MembershipRequestModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

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
 * <a href="MembershipRequestPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MembershipRequestPersistenceImpl extends BasePersistence
	implements MembershipRequestPersistence {
	public MembershipRequest create(long membershipRequestId) {
		MembershipRequest membershipRequest = new MembershipRequestImpl();

		membershipRequest.setNew(true);
		membershipRequest.setPrimaryKey(membershipRequestId);

		return membershipRequest;
	}

	public MembershipRequest remove(long membershipRequestId)
		throws NoSuchMembershipRequestException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MembershipRequest membershipRequest = (MembershipRequest)session.get(MembershipRequestImpl.class,
					new Long(membershipRequestId));

			if (membershipRequest == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No MembershipRequest exists with the primary key " +
						membershipRequestId);
				}

				throw new NoSuchMembershipRequestException(
					"No MembershipRequest exists with the primary key " +
					membershipRequestId);
			}

			return remove(membershipRequest);
		}
		catch (NoSuchMembershipRequestException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MembershipRequest remove(MembershipRequest membershipRequest)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(membershipRequest);
			}
		}

		membershipRequest = removeImpl(membershipRequest);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(membershipRequest);
			}
		}

		return membershipRequest;
	}

	protected MembershipRequest removeImpl(MembershipRequest membershipRequest)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(membershipRequest);

			session.flush();

			return membershipRequest;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(MembershipRequest.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MembershipRequest membershipRequest, boolean merge)</code>.
	 */
	public MembershipRequest update(MembershipRequest membershipRequest)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MembershipRequest membershipRequest) method. Use update(MembershipRequest membershipRequest, boolean merge) instead.");
		}

		return update(membershipRequest, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        membershipRequest the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when membershipRequest is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MembershipRequest update(MembershipRequest membershipRequest,
		boolean merge) throws SystemException {
		boolean isNew = membershipRequest.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(membershipRequest);
				}
				else {
					listener.onBeforeUpdate(membershipRequest);
				}
			}
		}

		membershipRequest = updateImpl(membershipRequest, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(membershipRequest);
				}
				else {
					listener.onAfterUpdate(membershipRequest);
				}
			}
		}

		return membershipRequest;
	}

	public MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(membershipRequest);
			}
			else {
				if (membershipRequest.isNew()) {
					session.save(membershipRequest);
				}
			}

			session.flush();

			membershipRequest.setNew(false);

			return membershipRequest;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(MembershipRequest.class.getName());
		}
	}

	public MembershipRequest findByPrimaryKey(long membershipRequestId)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = fetchByPrimaryKey(membershipRequestId);

		if (membershipRequest == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MembershipRequest exists with the primary key " +
					membershipRequestId);
			}

			throw new NoSuchMembershipRequestException(
				"No MembershipRequest exists with the primary key " +
				membershipRequestId);
		}

		return membershipRequest;
	}

	public MembershipRequest fetchByPrimaryKey(long membershipRequestId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MembershipRequest)session.get(MembershipRequestImpl.class,
				new Long(membershipRequestId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MembershipRequest> list = q.list();

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
			return (List<MembershipRequest>)result;
		}
	}

	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<MembershipRequest> list = (List<MembershipRequest>)QueryUtil.list(q,
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
			return (List<MembershipRequest>)result;
		}
	}

	public MembershipRequest findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByGroupId(groupId);

		List<MembershipRequest> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId, OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portal.model.MembershipRequest WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					membershipRequest);

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = (MembershipRequest)objArray[0];
			array[1] = (MembershipRequest)objArray[1];
			array[2] = (MembershipRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MembershipRequest> list = q.list();

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
			return (List<MembershipRequest>)result;
		}
	}

	public List<MembershipRequest> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MembershipRequest> findByUserId(long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<MembershipRequest> list = (List<MembershipRequest>)QueryUtil.list(q,
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
			return (List<MembershipRequest>)result;
		}
	}

	public MembershipRequest findByUserId_First(long userId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest findByUserId_Last(long userId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByUserId(userId);

		List<MembershipRequest> list = findByUserId(userId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId, OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portal.model.MembershipRequest WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					membershipRequest);

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = (MembershipRequest)objArray[0];
			array[1] = (MembershipRequest)objArray[1];
			array[2] = (MembershipRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
		String finderMethodName = "findByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId)
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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("statusId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				List<MembershipRequest> list = q.list();

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
			return (List<MembershipRequest>)result;
		}
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, statusId, start, end, null);
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
		String finderMethodName = "findByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId),
				
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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("statusId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				List<MembershipRequest> list = (List<MembershipRequest>)QueryUtil.list(q,
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
			return (List<MembershipRequest>)result;
		}
	}

	public MembershipRequest findByG_S_First(long groupId, int statusId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByG_S(groupId, statusId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("statusId=" + statusId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest findByG_S_Last(long groupId, int statusId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		int count = countByG_S(groupId, statusId);

		List<MembershipRequest> list = findByG_S(groupId, statusId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No MembershipRequest exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("statusId=" + statusId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMembershipRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MembershipRequest[] findByG_S_PrevAndNext(long membershipRequestId,
		long groupId, int statusId, OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		MembershipRequest membershipRequest = findByPrimaryKey(membershipRequestId);

		int count = countByG_S(groupId, statusId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portal.model.MembershipRequest WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("statusId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(statusId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					membershipRequest);

			MembershipRequest[] array = new MembershipRequestImpl[3];

			array[0] = (MembershipRequest)objArray[0];
			array[1] = (MembershipRequest)objArray[1];
			array[2] = (MembershipRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findWithDynamicQuery(
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

	public List<MembershipRequest> findWithDynamicQuery(
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

	public List<MembershipRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MembershipRequest> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MembershipRequest> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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

				query.append("FROM com.liferay.portal.model.MembershipRequest ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<MembershipRequest> list = (List<MembershipRequest>)QueryUtil.list(q,
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
			return (List<MembershipRequest>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MembershipRequest membershipRequest : findByGroupId(groupId)) {
			remove(membershipRequest);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MembershipRequest membershipRequest : findByUserId(userId)) {
			remove(membershipRequest);
		}
	}

	public void removeByG_S(long groupId, int statusId)
		throws SystemException {
		for (MembershipRequest membershipRequest : findByG_S(groupId, statusId)) {
			remove(membershipRequest);
		}
	}

	public void removeAll() throws SystemException {
		for (MembershipRequest membershipRequest : findAll()) {
			remove(membershipRequest);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

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

	public int countByG_S(long groupId, int statusId) throws SystemException {
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
		String finderMethodName = "countByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId)
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.MembershipRequest WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("statusId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

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
		boolean finderClassNameCacheEnabled = MembershipRequestModelImpl.CACHE_ENABLED;
		String finderClassName = MembershipRequest.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.MembershipRequest");

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

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.MembershipRequest")));

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

	private static Log _log = LogFactory.getLog(MembershipRequestPersistenceImpl.class);
	private ModelListener[] _listeners;
}