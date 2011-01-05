/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
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
 * The persistence implementation for the tasks proposal service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TasksProposalPersistence
 * @see TasksProposalUtil
 * @generated
 */
public class TasksProposalPersistenceImpl extends BasePersistenceImpl<TasksProposal>
	implements TasksProposalPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TasksProposalUtil} to access the tasks proposal persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
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

	/**
	 * Caches the tasks proposal in the entity cache if it is enabled.
	 *
	 * @param tasksProposal the tasks proposal to cache
	 */
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

	/**
	 * Caches the tasks proposals in the entity cache if it is enabled.
	 *
	 * @param tasksProposals the tasks proposals to cache
	 */
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

	/**
	 * Clears the cache for all tasks proposals.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(TasksProposalImpl.class.getName());
		EntityCacheUtil.clearCache(TasksProposalImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the tasks proposal.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(TasksProposal tasksProposal) {
		EntityCacheUtil.removeResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposal.getClassNameId()),
				
			tasksProposal.getClassPK()
			});
	}

	/**
	 * Creates a new tasks proposal with the primary key. Does not add the tasks proposal to the database.
	 *
	 * @param proposalId the primary key for the new tasks proposal
	 * @return the new tasks proposal
	 */
	public TasksProposal create(long proposalId) {
		TasksProposal tasksProposal = new TasksProposalImpl();

		tasksProposal.setNew(true);
		tasksProposal.setPrimaryKey(proposalId);

		return tasksProposal;
	}

	/**
	 * Removes the tasks proposal with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the tasks proposal to remove
	 * @return the tasks proposal that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TasksProposal remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the tasks proposal with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param proposalId the primary key of the tasks proposal to remove
	 * @return the tasks proposal that was removed
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

			return tasksProposalPersistence.remove(tasksProposal);
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

	protected TasksProposal removeImpl(TasksProposal tasksProposal)
		throws SystemException {
		tasksProposal = toUnwrappedModel(tasksProposal);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, tasksProposal);
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

	/**
	 * Finds the tasks proposal with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the tasks proposal to find
	 * @return the tasks proposal
	 * @throws com.liferay.portal.NoSuchModelException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TasksProposal findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the tasks proposal with the primary key or throws a {@link com.liferay.portlet.tasks.NoSuchProposalException} if it could not be found.
	 *
	 * @param proposalId the primary key of the tasks proposal to find
	 * @return the tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the tasks proposal with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the tasks proposal to find
	 * @return the tasks proposal, or <code>null</code> if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TasksProposal fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the tasks proposal with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param proposalId the primary key of the tasks proposal to find
	 * @return the tasks proposal, or <code>null</code> if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds all the tasks proposals where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the tasks proposals where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @return the range of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the tasks proposals where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
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

			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first tasks proposal in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the last tasks proposal in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the tasks proposals before and after the current tasks proposal in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param proposalId the primary key of the current tasks proposal
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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
		}

		else {
			query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

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

	/**
	 * Filters by the user's permissions and finds all the tasks proposals where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the tasks proposals where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @return the range of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the tasks proposals where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(TasksProposalModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				TasksProposal.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, TasksProposalImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, TasksProposalImpl.class);
			}

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

	/**
	 * Finds all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @return the matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @return the range of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
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

			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the last tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the tasks proposals before and after the current tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param proposalId the primary key of the current tasks proposal
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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
		}

		else {
			query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

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

	/**
	 * Filters by the user's permissions and finds all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @return the matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @return the range of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(TasksProposalModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(TasksProposalModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				TasksProposal.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, TasksProposalImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, TasksProposalImpl.class);
			}

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

	/**
	 * Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.tasks.NoSuchProposalException} if it could not be found.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching tasks proposal
	 * @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching tasks proposal, or <code>null</code> if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TasksProposal fetchByC_C(long classNameId, String classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the matching tasks proposal, or <code>null</code> if a matching tasks proposal could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TasksProposal fetchByC_C(long classNameId, String classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
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

			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs);
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

	/**
	 * Finds all the tasks proposals.
	 *
	 * @return the tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the tasks proposals.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @return the range of tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the tasks proposals.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of tasks proposals to return
	 * @param end the upper bound of the range of tasks proposals to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public List<TasksProposal> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
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

			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the tasks proposals where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (TasksProposal tasksProposal : findByGroupId(groupId)) {
			tasksProposalPersistence.remove(tasksProposal);
		}
	}

	/**
	 * Removes all the tasks proposals where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (TasksProposal tasksProposal : findByG_U(groupId, userId)) {
			tasksProposalPersistence.remove(tasksProposal);
		}
	}

	/**
	 * Removes the tasks proposal where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, String classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByC_C(classNameId, classPK);

		tasksProposalPersistence.remove(tasksProposal);
	}

	/**
	 * Removes all the tasks proposals from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (TasksProposal tasksProposal : findAll()) {
			tasksProposalPersistence.remove(tasksProposal);
		}
	}

	/**
	 * Counts all the tasks proposals where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

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

	/**
	 * Filters by the user's permissions and counts all the tasks proposals where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_TASKSPROPOSAL_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				TasksProposal.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	/**
	 * Counts all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @return the number of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TASKSPROPOSAL_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

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

	/**
	 * Filters by the user's permissions and counts all the tasks proposals where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param userId the user ID to search with
	 * @return the number of matching tasks proposals that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_TASKSPROPOSAL_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				TasksProposal.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	/**
	 * Counts all the tasks proposals where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID to search with
	 * @param classPK the class p k to search with
	 * @return the number of matching tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, String classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
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

			Session session = null;

			try {
				session = openSession();

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

	/**
	 * Counts all the tasks proposals.
	 *
	 * @return the number of tasks proposals
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Initializes the tasks proposal persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tasks.model.TasksProposal")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TasksProposal>> listenersList = new ArrayList<ModelListener<TasksProposal>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TasksProposal>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(TasksProposalImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
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
	private static final String _FILTER_SQL_SELECT_TASKSPROPOSAL_WHERE = "SELECT DISTINCT {tasksProposal.*} FROM TasksProposal tasksProposal WHERE ";
	private static final String _FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {TasksProposal.*} FROM (SELECT DISTINCT tasksProposal.proposalId FROM TasksProposal tasksProposal WHERE ";
	private static final String _FILTER_SQL_SELECT_TASKSPROPOSAL_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN TasksProposal ON TEMP_TABLE.proposalId = TasksProposal.proposalId";
	private static final String _FILTER_SQL_COUNT_TASKSPROPOSAL_WHERE = "SELECT COUNT(DISTINCT tasksProposal.proposalId) AS COUNT_VALUE FROM TasksProposal tasksProposal WHERE ";
	private static final String _FILTER_COLUMN_PK = "tasksProposal.proposalId";
	private static final String _FILTER_COLUMN_USERID = "tasksProposal.userId";
	private static final String _FILTER_ENTITY_ALIAS = "tasksProposal";
	private static final String _FILTER_ENTITY_TABLE = "TasksProposal";
	private static final String _ORDER_BY_ENTITY_ALIAS = "tasksProposal.";
	private static final String _ORDER_BY_ENTITY_TABLE = "TasksProposal.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TasksProposal exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TasksProposal exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(TasksProposalPersistenceImpl.class);
}