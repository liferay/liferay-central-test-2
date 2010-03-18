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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.model.impl.TasksReviewImpl;
import com.liferay.portlet.tasks.model.impl.TasksReviewModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TasksReviewPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReviewPersistence
 * @see       TasksReviewUtil
 * @generated
 */
public class TasksReviewPersistenceImpl extends BasePersistenceImpl<TasksReview>
	implements TasksReviewPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TasksReviewImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByProposalId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByProposalId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByProposalId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_P = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_P = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(TasksReview tasksReview) {
		EntityCacheUtil.putResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey(), tasksReview);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
			new Object[] {
				new Long(tasksReview.getUserId()),
				new Long(tasksReview.getProposalId())
			}, tasksReview);
	}

	public void cacheResult(List<TasksReview> tasksReviews) {
		for (TasksReview tasksReview : tasksReviews) {
			if (EntityCacheUtil.getResult(
						TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
						TasksReviewImpl.class, tasksReview.getPrimaryKey(), this) == null) {
				cacheResult(tasksReview);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TasksReviewImpl.class.getName());
		EntityCacheUtil.clearCache(TasksReviewImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public TasksReview create(long reviewId) {
		TasksReview tasksReview = new TasksReviewImpl();

		tasksReview.setNew(true);
		tasksReview.setPrimaryKey(reviewId);

		return tasksReview;
	}

	public TasksReview remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public TasksReview remove(long reviewId)
		throws NoSuchReviewException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TasksReview tasksReview = (TasksReview)session.get(TasksReviewImpl.class,
					new Long(reviewId));

			if (tasksReview == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + reviewId);
				}

				throw new NoSuchReviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					reviewId);
			}

			return remove(tasksReview);
		}
		catch (NoSuchReviewException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview remove(TasksReview tasksReview)
		throws SystemException {
		for (ModelListener<TasksReview> listener : listeners) {
			listener.onBeforeRemove(tasksReview);
		}

		tasksReview = removeImpl(tasksReview);

		for (ModelListener<TasksReview> listener : listeners) {
			listener.onAfterRemove(tasksReview);
		}

		return tasksReview;
	}

	protected TasksReview removeImpl(TasksReview tasksReview)
		throws SystemException {
		tasksReview = toUnwrappedModel(tasksReview);

		Session session = null;

		try {
			session = openSession();

			if (tasksReview.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TasksReviewImpl.class,
						tasksReview.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tasksReview);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TasksReviewModelImpl tasksReviewModelImpl = (TasksReviewModelImpl)tasksReview;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_P,
			new Object[] {
				new Long(tasksReviewModelImpl.getOriginalUserId()),
				new Long(tasksReviewModelImpl.getOriginalProposalId())
			});

		EntityCacheUtil.removeResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey());

		return tasksReview;
	}

	public TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws SystemException {
		tasksReview = toUnwrappedModel(tasksReview);

		boolean isNew = tasksReview.isNew();

		TasksReviewModelImpl tasksReviewModelImpl = (TasksReviewModelImpl)tasksReview;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tasksReview, merge);

			tasksReview.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey(), tasksReview);

		if (!isNew &&
				((tasksReview.getUserId() != tasksReviewModelImpl.getOriginalUserId()) ||
				(tasksReview.getProposalId() != tasksReviewModelImpl.getOriginalProposalId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_P,
				new Object[] {
					new Long(tasksReviewModelImpl.getOriginalUserId()),
					new Long(tasksReviewModelImpl.getOriginalProposalId())
				});
		}

		if (isNew ||
				((tasksReview.getUserId() != tasksReviewModelImpl.getOriginalUserId()) ||
				(tasksReview.getProposalId() != tasksReviewModelImpl.getOriginalProposalId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
				new Object[] {
					new Long(tasksReview.getUserId()),
					new Long(tasksReview.getProposalId())
				}, tasksReview);
		}

		return tasksReview;
	}

	protected TasksReview toUnwrappedModel(TasksReview tasksReview) {
		if (tasksReview instanceof TasksReviewImpl) {
			return tasksReview;
		}

		TasksReviewImpl tasksReviewImpl = new TasksReviewImpl();

		tasksReviewImpl.setNew(tasksReview.isNew());
		tasksReviewImpl.setPrimaryKey(tasksReview.getPrimaryKey());

		tasksReviewImpl.setReviewId(tasksReview.getReviewId());
		tasksReviewImpl.setGroupId(tasksReview.getGroupId());
		tasksReviewImpl.setCompanyId(tasksReview.getCompanyId());
		tasksReviewImpl.setUserId(tasksReview.getUserId());
		tasksReviewImpl.setUserName(tasksReview.getUserName());
		tasksReviewImpl.setCreateDate(tasksReview.getCreateDate());
		tasksReviewImpl.setModifiedDate(tasksReview.getModifiedDate());
		tasksReviewImpl.setProposalId(tasksReview.getProposalId());
		tasksReviewImpl.setAssignedByUserId(tasksReview.getAssignedByUserId());
		tasksReviewImpl.setAssignedByUserName(tasksReview.getAssignedByUserName());
		tasksReviewImpl.setStage(tasksReview.getStage());
		tasksReviewImpl.setCompleted(tasksReview.isCompleted());
		tasksReviewImpl.setRejected(tasksReview.isRejected());

		return tasksReviewImpl;
	}

	public TasksReview findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public TasksReview findByPrimaryKey(long reviewId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = fetchByPrimaryKey(reviewId);

		if (tasksReview == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + reviewId);
			}

			throw new NoSuchReviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				reviewId);
		}

		return tasksReview;
	}

	public TasksReview fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public TasksReview fetchByPrimaryKey(long reviewId)
		throws SystemException {
		TasksReview tasksReview = (TasksReview)EntityCacheUtil.getResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
				TasksReviewImpl.class, reviewId, this);

		if (tasksReview == null) {
			Session session = null;

			try {
				session = openSession();

				tasksReview = (TasksReview)session.get(TasksReviewImpl.class,
						new Long(reviewId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tasksReview != null) {
					cacheResult(tasksReview);
				}

				closeSession(session);
			}
		}

		return tasksReview;
	}

	public List<TasksReview> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<TasksReview> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		int count = countByUserId(userId);

		List<TasksReview> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByUserId_PrevAndNext(long reviewId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByUserId(userId);

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

			query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByProposalId(long proposalId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PROPOSALID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_PROPOSALID_PROPOSALID_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PROPOSALID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end) throws SystemException {
		return findByProposalId(proposalId, start, end, null);
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PROPOSALID,
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

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_PROPOSALID_PROPOSALID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PROPOSALID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByProposalId_First(long proposalId,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByProposalId(proposalId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByProposalId_Last(long proposalId,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		int count = countByProposalId(proposalId);

		List<TasksReview> list = findByProposalId(proposalId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByProposalId_PrevAndNext(long reviewId,
		long proposalId, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByProposalId(proposalId);

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

			query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

			query.append(_FINDER_COLUMN_PROPOSALID_PROPOSALID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview findByU_P(long userId, long proposalId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = fetchByU_P(userId, proposalId);

		if (tasksReview == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", proposalId=");
			msg.append(proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchReviewException(msg.toString());
		}

		return tasksReview;
	}

	public TasksReview fetchByU_P(long userId, long proposalId)
		throws SystemException {
		return fetchByU_P(userId, proposalId, true);
	}

	public TasksReview fetchByU_P(long userId, long proposalId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_U_P_USERID_2);

				query.append(_FINDER_COLUMN_U_P_PROPOSALID_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(proposalId);

				List<TasksReview> list = q.list();

				result = list;

				TasksReview tasksReview = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
						finderArgs, list);
				}
				else {
					tasksReview = list.get(0);

					cacheResult(tasksReview);

					if ((tasksReview.getUserId() != userId) ||
							(tasksReview.getProposalId() != proposalId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
							finderArgs, tasksReview);
					}
				}

				return tasksReview;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
						finderArgs, new ArrayList<TasksReview>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (TasksReview)result;
			}
		}
	}

	public List<TasksReview> findByP_S(long proposalId, int stage)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_STAGE_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end) throws SystemException {
		return findByP_S(proposalId, stage, start, end, null);
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_STAGE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_First(long proposalId, int stage,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S(proposalId, stage, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_Last(long proposalId, int stage,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		int count = countByP_S(proposalId, stage);

		List<TasksReview> list = findByP_S(proposalId, stage, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_PrevAndNext(long reviewId, long proposalId,
		int stage, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S(proposalId, stage);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

			query.append(_FINDER_COLUMN_P_S_PROPOSALID_2);

			query.append(_FINDER_COLUMN_P_S_STAGE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_COMPLETED_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end) throws SystemException {
		return findByP_S_C(proposalId, stage, completed, start, end, null);
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_COMPLETED_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_C_First(long proposalId, int stage,
		boolean completed, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C(proposalId, stage, completed, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(", completed=");
			msg.append(completed);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_C_Last(long proposalId, int stage,
		boolean completed, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		int count = countByP_S_C(proposalId, stage, completed);

		List<TasksReview> list = findByP_S_C(proposalId, stage, completed,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(", completed=");
			msg.append(completed);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_C_PrevAndNext(long reviewId,
		long proposalId, int stage, boolean completed,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S_C(proposalId, stage, completed);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

			query.append(_FINDER_COLUMN_P_S_C_PROPOSALID_2);

			query.append(_FINDER_COLUMN_P_S_C_STAGE_2);

			query.append(_FINDER_COLUMN_P_S_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			qPos.add(completed);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S_C_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_R_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_R_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_R_COMPLETED_2);

				query.append(_FINDER_COLUMN_P_S_C_R_REJECTED_2);

				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S_C_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int start, int end)
		throws SystemException {
		return findByP_S_C_R(proposalId, stage, completed, rejected, start,
			end, null);
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S_C_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(6 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_R_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_R_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_R_COMPLETED_2);

				query.append(_FINDER_COLUMN_P_S_C_R_REJECTED_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S_C_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_C_R_First(long proposalId, int stage,
		boolean completed, boolean rejected, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C_R(proposalId, stage, completed,
				rejected, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(", completed=");
			msg.append(completed);

			msg.append(", rejected=");
			msg.append(rejected);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_C_R_Last(long proposalId, int stage,
		boolean completed, boolean rejected, OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		int count = countByP_S_C_R(proposalId, stage, completed, rejected);

		List<TasksReview> list = findByP_S_C_R(proposalId, stage, completed,
				rejected, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("proposalId=");
			msg.append(proposalId);

			msg.append(", stage=");
			msg.append(stage);

			msg.append(", completed=");
			msg.append(completed);

			msg.append(", rejected=");
			msg.append(rejected);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_C_R_PrevAndNext(long reviewId,
		long proposalId, int stage, boolean completed, boolean rejected,
		OrderByComparator orderByComparator)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S_C_R(proposalId, stage, completed, rejected);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_TASKSREVIEW_WHERE);

			query.append(_FINDER_COLUMN_P_S_C_R_PROPOSALID_2);

			query.append(_FINDER_COLUMN_P_S_C_R_STAGE_2);

			query.append(_FINDER_COLUMN_P_S_C_R_COMPLETED_2);

			query.append(_FINDER_COLUMN_P_S_C_R_REJECTED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksReviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			qPos.add(completed);

			qPos.add(rejected);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksReview> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TasksReview> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_TASKSREVIEW);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_TASKSREVIEW.concat(TasksReviewModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (TasksReview tasksReview : findByUserId(userId)) {
			remove(tasksReview);
		}
	}

	public void removeByProposalId(long proposalId) throws SystemException {
		for (TasksReview tasksReview : findByProposalId(proposalId)) {
			remove(tasksReview);
		}
	}

	public void removeByU_P(long userId, long proposalId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByU_P(userId, proposalId);

		remove(tasksReview);
	}

	public void removeByP_S(long proposalId, int stage)
		throws SystemException {
		for (TasksReview tasksReview : findByP_S(proposalId, stage)) {
			remove(tasksReview);
		}
	}

	public void removeByP_S_C(long proposalId, int stage, boolean completed)
		throws SystemException {
		for (TasksReview tasksReview : findByP_S_C(proposalId, stage, completed)) {
			remove(tasksReview);
		}
	}

	public void removeByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws SystemException {
		for (TasksReview tasksReview : findByP_S_C_R(proposalId, stage,
				completed, rejected)) {
			remove(tasksReview);
		}
	}

	public void removeAll() throws SystemException {
		for (TasksReview tasksReview : findAll()) {
			remove(tasksReview);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByProposalId(long proposalId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PROPOSALID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_PROPOSALID_PROPOSALID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PROPOSALID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_P(long userId, long proposalId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_U_P_USERID_2);

				query.append(_FINDER_COLUMN_U_P_PROPOSALID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(proposalId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S(long proposalId, int stage) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_STAGE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_COMPLETED_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S_C_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_TASKSREVIEW_WHERE);

				query.append(_FINDER_COLUMN_P_S_C_R_PROPOSALID_2);

				query.append(_FINDER_COLUMN_P_S_C_R_STAGE_2);

				query.append(_FINDER_COLUMN_P_S_C_R_COMPLETED_2);

				query.append(_FINDER_COLUMN_P_S_C_R_REJECTED_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S_C_R,
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

				Query q = session.createQuery(_SQL_COUNT_TASKSREVIEW);

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
						"value.object.listener.com.liferay.portlet.tasks.model.TasksReview")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TasksReview>> listenersList = new ArrayList<ModelListener<TasksReview>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TasksReview>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = TasksProposalPersistence.class)
	protected TasksProposalPersistence tasksProposalPersistence;
	@BeanReference(type = TasksReviewPersistence.class)
	protected TasksReviewPersistence tasksReviewPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_TASKSREVIEW = "SELECT tasksReview FROM TasksReview tasksReview";
	private static final String _SQL_SELECT_TASKSREVIEW_WHERE = "SELECT tasksReview FROM TasksReview tasksReview WHERE ";
	private static final String _SQL_COUNT_TASKSREVIEW = "SELECT COUNT(tasksReview) FROM TasksReview tasksReview";
	private static final String _SQL_COUNT_TASKSREVIEW_WHERE = "SELECT COUNT(tasksReview) FROM TasksReview tasksReview WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "tasksReview.userId = ?";
	private static final String _FINDER_COLUMN_PROPOSALID_PROPOSALID_2 = "tasksReview.proposalId = ?";
	private static final String _FINDER_COLUMN_U_P_USERID_2 = "tasksReview.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_P_PROPOSALID_2 = "tasksReview.proposalId = ?";
	private static final String _FINDER_COLUMN_P_S_PROPOSALID_2 = "tasksReview.proposalId = ? AND ";
	private static final String _FINDER_COLUMN_P_S_STAGE_2 = "tasksReview.stage = ?";
	private static final String _FINDER_COLUMN_P_S_C_PROPOSALID_2 = "tasksReview.proposalId = ? AND ";
	private static final String _FINDER_COLUMN_P_S_C_STAGE_2 = "tasksReview.stage = ? AND ";
	private static final String _FINDER_COLUMN_P_S_C_COMPLETED_2 = "tasksReview.completed = ?";
	private static final String _FINDER_COLUMN_P_S_C_R_PROPOSALID_2 = "tasksReview.proposalId = ? AND ";
	private static final String _FINDER_COLUMN_P_S_C_R_STAGE_2 = "tasksReview.stage = ? AND ";
	private static final String _FINDER_COLUMN_P_S_C_R_COMPLETED_2 = "tasksReview.completed = ? AND ";
	private static final String _FINDER_COLUMN_P_S_C_R_REJECTED_2 = "tasksReview.rejected = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "tasksReview.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TasksReview exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TasksReview exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(TasksReviewPersistenceImpl.class);
}