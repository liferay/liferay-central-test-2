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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.impl.TasksProposalImpl;
import com.liferay.portlet.tasks.model.impl.TasksProposalModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TasksProposalPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalPersistence
 * @see       TasksProposalUtil
 * @generated
 */
public class TasksProposalPersistenceImpl extends BasePersistenceImpl<TasksProposal>
	implements TasksProposalPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TasksProposalImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(TasksProposal tasksProposal) {
		EntityCacheUtil.putResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
			tasksProposal);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposal.getClassNameId()),
				
			tasksProposal.getClassPK()
			}, tasksProposal);
	}

	public void cacheResult(List<TasksProposal> tasksProposals) {
		for (TasksProposal tasksProposal : tasksProposals) {
			if (EntityCacheUtil.getResult(
						TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
						TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
						this) == null) {
				cacheResult(tasksProposal);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TasksProposalImpl.class.getName());
		EntityCacheUtil.clearCache(TasksProposalImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(TasksProposal tasksProposal) {
		EntityCacheUtil.removeResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposal.getClassNameId()),
				
			tasksProposal.getClassPK()
			});
	}

	public TasksProposal create(long proposalId) {
		TasksProposal tasksProposal = new TasksProposalImpl();

		tasksProposal.setNew(true);
		tasksProposal.setPrimaryKey(proposalId);

		return tasksProposal;
	}

	public TasksProposal remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public TasksProposal remove(long proposalId)
		throws NoSuchProposalException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TasksProposal tasksProposal = (TasksProposal)session.get(TasksProposalImpl.class,
					new Long(proposalId));

			if (tasksProposal == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + proposalId);
				}

				throw new NoSuchProposalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					proposalId);
			}

			return remove(tasksProposal);
		}
		catch (NoSuchProposalException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal remove(TasksProposal tasksProposal)
		throws SystemException {
		for (ModelListener<TasksProposal> listener : listeners) {
			listener.onBeforeRemove(tasksProposal);
		}

		tasksProposal = removeImpl(tasksProposal);

		for (ModelListener<TasksProposal> listener : listeners) {
			listener.onAfterRemove(tasksProposal);
		}

		return tasksProposal;
	}

	protected TasksProposal removeImpl(TasksProposal tasksProposal)
		throws SystemException {
		tasksProposal = toUnwrappedModel(tasksProposal);

		Session session = null;

		try {
			session = openSession();

			if (tasksProposal.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TasksProposalImpl.class,
						tasksProposal.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tasksProposal);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TasksProposalModelImpl tasksProposalModelImpl = (TasksProposalModelImpl)tasksProposal;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposalModelImpl.getOriginalClassNameId()),
				
			tasksProposalModelImpl.getOriginalClassPK()
			});

		EntityCacheUtil.removeResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey());

		return tasksProposal;
	}

	public TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws SystemException {
		tasksProposal = toUnwrappedModel(tasksProposal);

		boolean isNew = tasksProposal.isNew();

		TasksProposalModelImpl tasksProposalModelImpl = (TasksProposalModelImpl)tasksProposal;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tasksProposal, merge);

			tasksProposal.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
			tasksProposal);

		if (!isNew &&
				((tasksProposal.getClassNameId() != tasksProposalModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(tasksProposal.getClassPK(),
					tasksProposalModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(tasksProposalModelImpl.getOriginalClassNameId()),
					
				tasksProposalModelImpl.getOriginalClassPK()
				});
		}

		if (isNew ||
				((tasksProposal.getClassNameId() != tasksProposalModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(tasksProposal.getClassPK(),
					tasksProposalModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(tasksProposal.getClassNameId()),
					
				tasksProposal.getClassPK()
				}, tasksProposal);
		}

		return tasksProposal;
	}

	protected TasksProposal toUnwrappedModel(TasksProposal tasksProposal) {
		if (tasksProposal instanceof TasksProposalImpl) {
			return tasksProposal;
		}

		TasksProposalImpl tasksProposalImpl = new TasksProposalImpl();

		tasksProposalImpl.setNew(tasksProposal.isNew());
		tasksProposalImpl.setPrimaryKey(tasksProposal.getPrimaryKey());

		tasksProposalImpl.setProposalId(tasksProposal.getProposalId());
		tasksProposalImpl.setGroupId(tasksProposal.getGroupId());
		tasksProposalImpl.setCompanyId(tasksProposal.getCompanyId());
		tasksProposalImpl.setUserId(tasksProposal.getUserId());
		tasksProposalImpl.setUserName(tasksProposal.getUserName());
		tasksProposalImpl.setCreateDate(tasksProposal.getCreateDate());
		tasksProposalImpl.setModifiedDate(tasksProposal.getModifiedDate());
		tasksProposalImpl.setClassNameId(tasksProposal.getClassNameId());
		tasksProposalImpl.setClassPK(tasksProposal.getClassPK());
		tasksProposalImpl.setName(tasksProposal.getName());
		tasksProposalImpl.setDescription(tasksProposal.getDescription());
		tasksProposalImpl.setPublishDate(tasksProposal.getPublishDate());
		tasksProposalImpl.setDueDate(tasksProposal.getDueDate());

		return tasksProposalImpl;
	}

	public TasksProposal findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public TasksProposal findByPrimaryKey(long proposalId)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByPrimaryKey(proposalId);

		if (tasksProposal == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + proposalId);
			}

			throw new NoSuchProposalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				proposalId);
		}

		return tasksProposal;
	}

	public TasksProposal fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public TasksProposal fetchByPrimaryKey(long proposalId)
		throws SystemException {
		TasksProposal tasksProposal = (TasksProposal)EntityCacheUtil.getResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
				TasksProposalImpl.class, proposalId, this);

		if (tasksProposal == null) {
			Session session = null;

			try {
				session = openSession();

				tasksProposal = (TasksProposal)session.get(TasksProposalImpl.class,
						new Long(proposalId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tasksProposal != null) {
					cacheResult(tasksProposal);
				}

				closeSession(session);
			}
		}

		return tasksProposal;
	}

	public List<TasksProposal> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<TasksProposal> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_TASKSPROPOSAL_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksProposal findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		int count = countByGroupId(groupId);

		List<TasksProposal> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByGroupId_PrevAndNext(long proposalId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		Session session = null;

		try {
			session = openSession();

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, tasksProposal,
					groupId, orderByComparator, true);

			array[1] = tasksProposal;

			array[2] = getByGroupId_PrevAndNext(session, tasksProposal,
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

	protected TasksProposal getByGroupId_PrevAndNext(Session session,
		TasksProposal tasksProposal, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TASKSPROPOSAL_WHERE);

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(tasksProposal);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TasksProposal> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<TasksProposal> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<TasksProposal> filterFindByGroupId(long groupId, int start,
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

			query.append(_FILTER_SELECT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					TasksProposal.class.getName(), _FILTER_COLUMN_PROPOSALID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_ENTITY_ALIAS, TasksProposalImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<TasksProposal>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
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

				query.append(_SQL_SELECT_TASKSPROPOSAL_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksProposal findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		int count = countByG_U(groupId, userId);

		List<TasksProposal> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByG_U_PrevAndNext(long proposalId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		Session session = null;

		try {
			session = openSession();

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = getByG_U_PrevAndNext(session, tasksProposal, groupId,
					userId, orderByComparator, true);

			array[1] = tasksProposal;

			array[2] = getByG_U_PrevAndNext(session, tasksProposal, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TasksProposal getByG_U_PrevAndNext(Session session,
		TasksProposal tasksProposal, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TASKSPROPOSAL_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(tasksProposal);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TasksProposal> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<TasksProposal> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	public List<TasksProposal> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

			query.append(_FILTER_SELECT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					TasksProposal.class.getName(), _FILTER_COLUMN_PROPOSALID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_ENTITY_ALIAS, TasksProposalImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<TasksProposal>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal findByC_C(long classNameId, String classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByC_C(classNameId, classPK);

		if (tasksProposal == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProposalException(msg.toString());
		}

		return tasksProposal;
	}

	public TasksProposal fetchByC_C(long classNameId, String classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public TasksProposal fetchByC_C(long classNameId, String classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId), classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_TASKSPROPOSAL_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				if (classPK == null) {
					query.append(_FINDER_COLUMN_C_C_CLASSPK_1);
				}
				else {
					if (classPK.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_C_CLASSPK_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_C_CLASSPK_2);
					}
				}

				query.append(TasksProposalModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (classPK != null) {
					qPos.add(classPK);
				}

				List<TasksProposal> list = q.list();

				result = list;

				TasksProposal tasksProposal = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					tasksProposal = list.get(0);

					cacheResult(tasksProposal);

					if ((tasksProposal.getClassNameId() != classNameId) ||
							(tasksProposal.getClassPK() == null) ||
							!tasksProposal.getClassPK().equals(classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, tasksProposal);
					}
				}

				return tasksProposal;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<TasksProposal>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (TasksProposal)result;
			}
		}
	}

	public List<TasksProposal> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TasksProposal> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_TASKSPROPOSAL);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_TASKSPROPOSAL.concat(TasksProposalModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (TasksProposal tasksProposal : findByGroupId(groupId)) {
			remove(tasksProposal);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (TasksProposal tasksProposal : findByG_U(groupId, userId)) {
			remove(tasksProposal);
		}
	}

	public void removeByC_C(long classNameId, String classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByC_C(classNameId, classPK);

		remove(tasksProposal);
	}

	public void removeAll() throws SystemException {
		for (TasksProposal tasksProposal : findAll()) {
			remove(tasksProposal);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_TASKSPROPOSAL_WHERE);

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

			query.append(_FILTER_COUNT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					TasksProposal.class.getName(), _FILTER_COLUMN_PROPOSALID,
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

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_TASKSPROPOSAL_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_COUNT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					TasksProposal.class.getName(), _FILTER_COLUMN_PROPOSALID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

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

	public int countByC_C(long classNameId, String classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId), classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_TASKSPROPOSAL_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				if (classPK == null) {
					query.append(_FINDER_COLUMN_C_C_CLASSPK_1);
				}
				else {
					if (classPK.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_C_CLASSPK_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_C_CLASSPK_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (classPK != null) {
					qPos.add(classPK);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_TASKSPROPOSAL);

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
						"value.object.listener.com.liferay.portlet.tasks.model.TasksProposal")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TasksProposal>> listenersList = new ArrayList<ModelListener<TasksProposal>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TasksProposal>)Class.forName(
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
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_TASKSPROPOSAL = "SELECT tasksProposal FROM TasksProposal tasksProposal";
	private static final String _SQL_SELECT_TASKSPROPOSAL_WHERE = "SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ";
	private static final String _SQL_COUNT_TASKSPROPOSAL = "SELECT COUNT(tasksProposal) FROM TasksProposal tasksProposal";
	private static final String _SQL_COUNT_TASKSPROPOSAL_WHERE = "SELECT COUNT(tasksProposal) FROM TasksProposal tasksProposal WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "tasksProposal.groupId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "tasksProposal.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "tasksProposal.userId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "tasksProposal.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_1 = "tasksProposal.classPK IS NULL";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "tasksProposal.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_3 = "(tasksProposal.classPK IS NULL OR tasksProposal.classPK = ?)";
	private static final String _FILTER_SELECT_TASKSPROPOSAL_WHERE = "SELECT {tasksProposal.*} FROM TasksProposal tasksProposal WHERE ";
	private static final String _FILTER_COUNT_TASKSPROPOSAL_WHERE = "SELECT COUNT(DISTINCT tasksProposal.proposalId) AS COUNT_VALUE FROM TasksProposal tasksProposal WHERE ";
	private static final String _FILTER_COLUMN_PROPOSALID = "tasksProposal.proposalId";
	private static final String _FILTER_COLUMN_USERID = "tasksProposal.userId";
	private static final String _ENTITY_ALIAS = "tasksProposal";
	private static final String _ORDER_BY_ENTITY_ALIAS = "tasksProposal.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TasksProposal exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TasksProposal exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(TasksProposalPersistenceImpl.class);
}