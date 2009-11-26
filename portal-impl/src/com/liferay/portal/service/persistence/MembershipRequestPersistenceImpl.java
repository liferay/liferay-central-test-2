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

import com.liferay.portal.NoSuchMembershipRequestException;
import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.MembershipRequestImpl;
import com.liferay.portal.model.impl.MembershipRequestModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MembershipRequestPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestPersistence
 * @see       MembershipRequestUtil
 * @generated
 */
public class MembershipRequestPersistenceImpl extends BasePersistenceImpl<MembershipRequest>
	implements MembershipRequestPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MembershipRequestImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(MembershipRequest membershipRequest) {
		EntityCacheUtil.putResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey(),
			membershipRequest);
	}

	public void cacheResult(List<MembershipRequest> membershipRequests) {
		for (MembershipRequest membershipRequest : membershipRequests) {
			if (EntityCacheUtil.getResult(
						MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
						MembershipRequestImpl.class,
						membershipRequest.getPrimaryKey(), this) == null) {
				cacheResult(membershipRequest);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MembershipRequestImpl.class.getName());
		EntityCacheUtil.clearCache(MembershipRequestImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MembershipRequest create(long membershipRequestId) {
		MembershipRequest membershipRequest = new MembershipRequestImpl();

		membershipRequest.setNew(true);
		membershipRequest.setPrimaryKey(membershipRequestId);

		return membershipRequest;
	}

	public MembershipRequest remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MembershipRequest remove(MembershipRequest membershipRequest)
		throws SystemException {
		for (ModelListener<MembershipRequest> listener : listeners) {
			listener.onBeforeRemove(membershipRequest);
		}

		membershipRequest = removeImpl(membershipRequest);

		for (ModelListener<MembershipRequest> listener : listeners) {
			listener.onAfterRemove(membershipRequest);
		}

		return membershipRequest;
	}

	protected MembershipRequest removeImpl(MembershipRequest membershipRequest)
		throws SystemException {
		membershipRequest = toUnwrappedModel(membershipRequest);

		Session session = null;

		try {
			session = openSession();

			if (membershipRequest.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MembershipRequestImpl.class,
						membershipRequest.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(membershipRequest);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey());

		return membershipRequest;
	}

	public MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws SystemException {
		membershipRequest = toUnwrappedModel(membershipRequest);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, membershipRequest, merge);

			membershipRequest.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
			MembershipRequestImpl.class, membershipRequest.getPrimaryKey(),
			membershipRequest);

		return membershipRequest;
	}

	protected MembershipRequest toUnwrappedModel(
		MembershipRequest membershipRequest) {
		if (membershipRequest instanceof MembershipRequestImpl) {
			return membershipRequest;
		}

		MembershipRequestImpl membershipRequestImpl = new MembershipRequestImpl();

		membershipRequestImpl.setNew(membershipRequest.isNew());
		membershipRequestImpl.setPrimaryKey(membershipRequest.getPrimaryKey());

		membershipRequestImpl.setMembershipRequestId(membershipRequest.getMembershipRequestId());
		membershipRequestImpl.setCompanyId(membershipRequest.getCompanyId());
		membershipRequestImpl.setUserId(membershipRequest.getUserId());
		membershipRequestImpl.setCreateDate(membershipRequest.getCreateDate());
		membershipRequestImpl.setGroupId(membershipRequest.getGroupId());
		membershipRequestImpl.setComments(membershipRequest.getComments());
		membershipRequestImpl.setReplyComments(membershipRequest.getReplyComments());
		membershipRequestImpl.setReplyDate(membershipRequest.getReplyDate());
		membershipRequestImpl.setReplierUserId(membershipRequest.getReplierUserId());
		membershipRequestImpl.setStatusId(membershipRequest.getStatusId());

		return membershipRequestImpl;
	}

	public MembershipRequest findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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

	public MembershipRequest fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MembershipRequest fetchByPrimaryKey(long membershipRequestId)
		throws SystemException {
		MembershipRequest membershipRequest = (MembershipRequest)EntityCacheUtil.getResult(MembershipRequestModelImpl.ENTITY_CACHE_ENABLED,
				MembershipRequestImpl.class, membershipRequestId, this);

		if (membershipRequest == null) {
			Session session = null;

			try {
				session = openSession();

				membershipRequest = (MembershipRequest)session.get(MembershipRequestImpl.class,
						new Long(membershipRequestId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (membershipRequest != null) {
					cacheResult(membershipRequest);
				}

				closeSession(session);
			}
		}

		return membershipRequest;
	}

	public List<MembershipRequest> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MembershipRequest> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("membershipRequest.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("membershipRequest.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MembershipRequest findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

			query.append("membershipRequest.groupId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("membershipRequest.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.userId = ?");

				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MembershipRequest> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MembershipRequest> findByUserId(long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.userId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("membershipRequest.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("membershipRequest.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MembershipRequest findByUserId_First(long userId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

			query.append("membershipRequest.userId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("membershipRequest.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				query.append(" AND ");

				query.append("membershipRequest.statusId = ?");

				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, statusId, start, end, null);
	}

	public List<MembershipRequest> findByG_S(long groupId, int statusId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				query.append(" AND ");

				query.append("membershipRequest.statusId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("membershipRequest.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("membershipRequest.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				list = (List<MembershipRequest>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MembershipRequest findByG_S_First(long groupId, int statusId,
		OrderByComparator obc)
		throws NoSuchMembershipRequestException, SystemException {
		List<MembershipRequest> list = findByG_S(groupId, statusId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

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

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MEMBERSHIPREQUEST_WHERE);

			query.append("membershipRequest.groupId = ?");

			query.append(" AND ");

			query.append("membershipRequest.statusId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("membershipRequest.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append(" ORDER BY ");

				query.append("membershipRequest.createDate DESC");
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

	public List<MembershipRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MembershipRequest> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MembershipRequest> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MembershipRequest> list = (List<MembershipRequest>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MEMBERSHIPREQUEST);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("membershipRequest.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append(" ORDER BY ");

					query.append("membershipRequest.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<MembershipRequest>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MembershipRequest>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MembershipRequest>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_S(long groupId, int statusId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(statusId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MEMBERSHIPREQUEST_WHERE);

				query.append("membershipRequest.groupId = ?");

				query.append(" AND ");

				query.append("membershipRequest.statusId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(statusId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_MEMBERSHIPREQUEST);

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
						"value.object.listener.com.liferay.portal.model.MembershipRequest")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MembershipRequest>> listenersList = new ArrayList<ModelListener<MembershipRequest>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MembershipRequest>)Class.forName(
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
	private static final String _SQL_SELECT_MEMBERSHIPREQUEST = "SELECT membershipRequest FROM MembershipRequest membershipRequest";
	private static final String _SQL_SELECT_MEMBERSHIPREQUEST_WHERE = "SELECT membershipRequest FROM MembershipRequest membershipRequest WHERE ";
	private static final String _SQL_COUNT_MEMBERSHIPREQUEST = "SELECT COUNT(membershipRequest) FROM MembershipRequest membershipRequest";
	private static final String _SQL_COUNT_MEMBERSHIPREQUEST_WHERE = "SELECT COUNT(membershipRequest) FROM MembershipRequest membershipRequest WHERE ";
	private static Log _log = LogFactoryUtil.getLog(MembershipRequestPersistenceImpl.class);
}