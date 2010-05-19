/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PollsQuestionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsQuestionPersistence
 * @see       PollsQuestionUtil
 * @generated
 */
public class PollsQuestionPersistenceImpl extends BasePersistenceImpl<PollsQuestion>
	implements PollsQuestionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PollsQuestionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PollsQuestion pollsQuestion) {
		EntityCacheUtil.putResult(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionImpl.class, pollsQuestion.getPrimaryKey(),
			pollsQuestion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				pollsQuestion.getUuid(), new Long(pollsQuestion.getGroupId())
			}, pollsQuestion);
	}

	public void cacheResult(List<PollsQuestion> pollsQuestions) {
		for (PollsQuestion pollsQuestion : pollsQuestions) {
			if (EntityCacheUtil.getResult(
						PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
						PollsQuestionImpl.class, pollsQuestion.getPrimaryKey(),
						this) == null) {
				cacheResult(pollsQuestion);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PollsQuestionImpl.class.getName());
		EntityCacheUtil.clearCache(PollsQuestionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(PollsQuestion pollsQuestion) {
		EntityCacheUtil.removeResult(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionImpl.class, pollsQuestion.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				pollsQuestion.getUuid(), new Long(pollsQuestion.getGroupId())
			});
	}

	public PollsQuestion create(long questionId) {
		PollsQuestion pollsQuestion = new PollsQuestionImpl();

		pollsQuestion.setNew(true);
		pollsQuestion.setPrimaryKey(questionId);

		String uuid = PortalUUIDUtil.generate();

		pollsQuestion.setUuid(uuid);

		return pollsQuestion;
	}

	public PollsQuestion remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PollsQuestion remove(long questionId)
		throws NoSuchQuestionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsQuestion pollsQuestion = (PollsQuestion)session.get(PollsQuestionImpl.class,
					new Long(questionId));

			if (pollsQuestion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + questionId);
				}

				throw new NoSuchQuestionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					questionId);
			}

			return remove(pollsQuestion);
		}
		catch (NoSuchQuestionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsQuestion remove(PollsQuestion pollsQuestion)
		throws SystemException {
		for (ModelListener<PollsQuestion> listener : listeners) {
			listener.onBeforeRemove(pollsQuestion);
		}

		pollsQuestion = removeImpl(pollsQuestion);

		for (ModelListener<PollsQuestion> listener : listeners) {
			listener.onAfterRemove(pollsQuestion);
		}

		return pollsQuestion;
	}

	protected PollsQuestion removeImpl(PollsQuestion pollsQuestion)
		throws SystemException {
		pollsQuestion = toUnwrappedModel(pollsQuestion);

		Session session = null;

		try {
			session = openSession();

			if (pollsQuestion.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PollsQuestionImpl.class,
						pollsQuestion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pollsQuestion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PollsQuestionModelImpl pollsQuestionModelImpl = (PollsQuestionModelImpl)pollsQuestion;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				pollsQuestionModelImpl.getOriginalUuid(),
				new Long(pollsQuestionModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionImpl.class, pollsQuestion.getPrimaryKey());

		return pollsQuestion;
	}

	public PollsQuestion updateImpl(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean merge) throws SystemException {
		pollsQuestion = toUnwrappedModel(pollsQuestion);

		boolean isNew = pollsQuestion.isNew();

		PollsQuestionModelImpl pollsQuestionModelImpl = (PollsQuestionModelImpl)pollsQuestion;

		if (Validator.isNull(pollsQuestion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsQuestion.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pollsQuestion, merge);

			pollsQuestion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
			PollsQuestionImpl.class, pollsQuestion.getPrimaryKey(),
			pollsQuestion);

		if (!isNew &&
				(!Validator.equals(pollsQuestion.getUuid(),
					pollsQuestionModelImpl.getOriginalUuid()) ||
				(pollsQuestion.getGroupId() != pollsQuestionModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					pollsQuestionModelImpl.getOriginalUuid(),
					new Long(pollsQuestionModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(pollsQuestion.getUuid(),
					pollsQuestionModelImpl.getOriginalUuid()) ||
				(pollsQuestion.getGroupId() != pollsQuestionModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					pollsQuestion.getUuid(),
					new Long(pollsQuestion.getGroupId())
				}, pollsQuestion);
		}

		return pollsQuestion;
	}

	protected PollsQuestion toUnwrappedModel(PollsQuestion pollsQuestion) {
		if (pollsQuestion instanceof PollsQuestionImpl) {
			return pollsQuestion;
		}

		PollsQuestionImpl pollsQuestionImpl = new PollsQuestionImpl();

		pollsQuestionImpl.setNew(pollsQuestion.isNew());
		pollsQuestionImpl.setPrimaryKey(pollsQuestion.getPrimaryKey());

		pollsQuestionImpl.setUuid(pollsQuestion.getUuid());
		pollsQuestionImpl.setQuestionId(pollsQuestion.getQuestionId());
		pollsQuestionImpl.setGroupId(pollsQuestion.getGroupId());
		pollsQuestionImpl.setCompanyId(pollsQuestion.getCompanyId());
		pollsQuestionImpl.setUserId(pollsQuestion.getUserId());
		pollsQuestionImpl.setUserName(pollsQuestion.getUserName());
		pollsQuestionImpl.setCreateDate(pollsQuestion.getCreateDate());
		pollsQuestionImpl.setModifiedDate(pollsQuestion.getModifiedDate());
		pollsQuestionImpl.setTitle(pollsQuestion.getTitle());
		pollsQuestionImpl.setDescription(pollsQuestion.getDescription());
		pollsQuestionImpl.setExpirationDate(pollsQuestion.getExpirationDate());
		pollsQuestionImpl.setLastVoteDate(pollsQuestion.getLastVoteDate());

		return pollsQuestionImpl;
	}

	public PollsQuestion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsQuestion findByPrimaryKey(long questionId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = fetchByPrimaryKey(questionId);

		if (pollsQuestion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + questionId);
			}

			throw new NoSuchQuestionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				questionId);
		}

		return pollsQuestion;
	}

	public PollsQuestion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PollsQuestion fetchByPrimaryKey(long questionId)
		throws SystemException {
		PollsQuestion pollsQuestion = (PollsQuestion)EntityCacheUtil.getResult(PollsQuestionModelImpl.ENTITY_CACHE_ENABLED,
				PollsQuestionImpl.class, questionId, this);

		if (pollsQuestion == null) {
			Session session = null;

			try {
				session = openSession();

				pollsQuestion = (PollsQuestion)session.get(PollsQuestionImpl.class,
						new Long(questionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (pollsQuestion != null) {
					cacheResult(pollsQuestion);
				}

				closeSession(session);
			}
		}

		return pollsQuestion;
	}

	public List<PollsQuestion> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsQuestion> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<PollsQuestion> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PollsQuestion> list = (List<PollsQuestion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_POLLSQUESTION_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsQuestion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsQuestion findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		List<PollsQuestion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		int count = countByUuid(uuid);

		List<PollsQuestion> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion[] findByUuid_PrevAndNext(long questionId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByPrimaryKey(questionId);

		Session session = null;

		try {
			session = openSession();

			PollsQuestion[] array = new PollsQuestionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, pollsQuestion, uuid,
					orderByComparator, true);

			array[1] = pollsQuestion;

			array[2] = getByUuid_PrevAndNext(session, pollsQuestion, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsQuestion getByUuid_PrevAndNext(Session session,
		PollsQuestion pollsQuestion, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSQUESTION_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(pollsQuestion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PollsQuestion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public PollsQuestion findByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = fetchByUUID_G(uuid, groupId);

		if (pollsQuestion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchQuestionException(msg.toString());
		}

		return pollsQuestion;
	}

	public PollsQuestion fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public PollsQuestion fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_POLLSQUESTION_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<PollsQuestion> list = q.list();

				result = list;

				PollsQuestion pollsQuestion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					pollsQuestion = list.get(0);

					cacheResult(pollsQuestion);

					if ((pollsQuestion.getUuid() == null) ||
							!pollsQuestion.getUuid().equals(uuid) ||
							(pollsQuestion.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, pollsQuestion);
					}
				}

				return pollsQuestion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<PollsQuestion>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PollsQuestion)result;
			}
		}
	}

	public List<PollsQuestion> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsQuestion> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<PollsQuestion> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PollsQuestion> list = (List<PollsQuestion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_POLLSQUESTION_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsQuestion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PollsQuestion findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		List<PollsQuestion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		int count = countByGroupId(groupId);

		List<PollsQuestion> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion[] findByGroupId_PrevAndNext(long questionId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByPrimaryKey(questionId);

		Session session = null;

		try {
			session = openSession();

			PollsQuestion[] array = new PollsQuestionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, pollsQuestion,
					groupId, orderByComparator, true);

			array[1] = pollsQuestion;

			array[2] = getByGroupId_PrevAndNext(session, pollsQuestion,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsQuestion getByGroupId_PrevAndNext(Session session,
		PollsQuestion pollsQuestion, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSQUESTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(pollsQuestion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PollsQuestion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<PollsQuestion> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<PollsQuestion> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<PollsQuestion> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_FILTER_SQL_SELECT_POLLSQUESTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(PollsQuestionModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					PollsQuestion.class.getName(), _FILTER_COLUMN_QUESTIONID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, PollsQuestionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<PollsQuestion>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsQuestion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsQuestion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PollsQuestion> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PollsQuestion> list = (List<PollsQuestion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_POLLSQUESTION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_POLLSQUESTION.concat(PollsQuestionModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PollsQuestion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (PollsQuestion pollsQuestion : findByUuid(uuid)) {
			remove(pollsQuestion);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByUUID_G(uuid, groupId);

		remove(pollsQuestion);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (PollsQuestion pollsQuestion : findByGroupId(groupId)) {
			remove(pollsQuestion);
		}
	}

	public void removeAll() throws SystemException {
		for (PollsQuestion pollsQuestion : findAll()) {
			remove(pollsQuestion);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_POLLSQUESTION_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_POLLSQUESTION_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_POLLSQUESTION_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int filterCountByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(2);

			query.append(_FILTER_SQL_COUNT_POLLSQUESTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					PollsQuestion.class.getName(), _FILTER_COLUMN_QUESTIONID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POLLSQUESTION);

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
						"value.object.listener.com.liferay.portlet.polls.model.PollsQuestion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PollsQuestion>> listenersList = new ArrayList<ModelListener<PollsQuestion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PollsQuestion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = PollsChoicePersistence.class)
	protected PollsChoicePersistence pollsChoicePersistence;
	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(type = PollsVotePersistence.class)
	protected PollsVotePersistence pollsVotePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_POLLSQUESTION = "SELECT pollsQuestion FROM PollsQuestion pollsQuestion";
	private static final String _SQL_SELECT_POLLSQUESTION_WHERE = "SELECT pollsQuestion FROM PollsQuestion pollsQuestion WHERE ";
	private static final String _SQL_COUNT_POLLSQUESTION = "SELECT COUNT(pollsQuestion) FROM PollsQuestion pollsQuestion";
	private static final String _SQL_COUNT_POLLSQUESTION_WHERE = "SELECT COUNT(pollsQuestion) FROM PollsQuestion pollsQuestion WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "pollsQuestion.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "pollsQuestion.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(pollsQuestion.uuid IS NULL OR pollsQuestion.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "pollsQuestion.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "pollsQuestion.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(pollsQuestion.uuid IS NULL OR pollsQuestion.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "pollsQuestion.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "pollsQuestion.groupId = ?";
	private static final String _FILTER_SQL_SELECT_POLLSQUESTION_WHERE = "SELECT {pollsQuestion.*} FROM PollsQuestion pollsQuestion WHERE ";
	private static final String _FILTER_SQL_COUNT_POLLSQUESTION_WHERE = "SELECT COUNT(pollsQuestion.questionId) AS COUNT_VALUE FROM PollsQuestion pollsQuestion WHERE ";
	private static final String _FILTER_COLUMN_QUESTIONID = "pollsQuestion.questionId";
	private static final String _FILTER_COLUMN_USERID = "pollsQuestion.userId";
	private static final String _FILTER_ENTITY_ALIAS = "pollsQuestion";
	private static final String _ORDER_BY_ENTITY_ALIAS = "pollsQuestion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PollsQuestion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PollsQuestion exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PollsQuestionPersistenceImpl.class);
}