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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
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
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.model.impl.WorkflowInstanceLinkImpl;
import com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WorkflowInstanceLinkPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkPersistence
 * @see       WorkflowInstanceLinkUtil
 * @generated
 */
public class WorkflowInstanceLinkPersistenceImpl extends BasePersistenceImpl<WorkflowInstanceLink>
	implements WorkflowInstanceLinkPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WorkflowInstanceLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_C_C = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C_C_C = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_C = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(WorkflowInstanceLink workflowInstanceLink) {
		EntityCacheUtil.putResult(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkImpl.class,
			workflowInstanceLink.getPrimaryKey(), workflowInstanceLink);
	}

	public void cacheResult(List<WorkflowInstanceLink> workflowInstanceLinks) {
		for (WorkflowInstanceLink workflowInstanceLink : workflowInstanceLinks) {
			if (EntityCacheUtil.getResult(
						WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
						WorkflowInstanceLinkImpl.class,
						workflowInstanceLink.getPrimaryKey(), this) == null) {
				cacheResult(workflowInstanceLink);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WorkflowInstanceLinkImpl.class.getName());
		EntityCacheUtil.clearCache(WorkflowInstanceLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public WorkflowInstanceLink create(long workflowInstanceLinkId) {
		WorkflowInstanceLink workflowInstanceLink = new WorkflowInstanceLinkImpl();

		workflowInstanceLink.setNew(true);
		workflowInstanceLink.setPrimaryKey(workflowInstanceLinkId);

		return workflowInstanceLink;
	}

	public WorkflowInstanceLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public WorkflowInstanceLink remove(long workflowInstanceLinkId)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WorkflowInstanceLink workflowInstanceLink = (WorkflowInstanceLink)session.get(WorkflowInstanceLinkImpl.class,
					new Long(workflowInstanceLinkId));

			if (workflowInstanceLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						workflowInstanceLinkId);
				}

				throw new NoSuchWorkflowInstanceLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					workflowInstanceLinkId);
			}

			return remove(workflowInstanceLink);
		}
		catch (NoSuchWorkflowInstanceLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WorkflowInstanceLink remove(
		WorkflowInstanceLink workflowInstanceLink) throws SystemException {
		for (ModelListener<WorkflowInstanceLink> listener : listeners) {
			listener.onBeforeRemove(workflowInstanceLink);
		}

		workflowInstanceLink = removeImpl(workflowInstanceLink);

		for (ModelListener<WorkflowInstanceLink> listener : listeners) {
			listener.onAfterRemove(workflowInstanceLink);
		}

		return workflowInstanceLink;
	}

	protected WorkflowInstanceLink removeImpl(
		WorkflowInstanceLink workflowInstanceLink) throws SystemException {
		workflowInstanceLink = toUnwrappedModel(workflowInstanceLink);

		Session session = null;

		try {
			session = openSession();

			if (workflowInstanceLink.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WorkflowInstanceLinkImpl.class,
						workflowInstanceLink.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(workflowInstanceLink);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkImpl.class, workflowInstanceLink.getPrimaryKey());

		return workflowInstanceLink;
	}

	public WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge) throws SystemException {
		workflowInstanceLink = toUnwrappedModel(workflowInstanceLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, workflowInstanceLink, merge);

			workflowInstanceLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkImpl.class,
			workflowInstanceLink.getPrimaryKey(), workflowInstanceLink);

		return workflowInstanceLink;
	}

	protected WorkflowInstanceLink toUnwrappedModel(
		WorkflowInstanceLink workflowInstanceLink) {
		if (workflowInstanceLink instanceof WorkflowInstanceLinkImpl) {
			return workflowInstanceLink;
		}

		WorkflowInstanceLinkImpl workflowInstanceLinkImpl = new WorkflowInstanceLinkImpl();

		workflowInstanceLinkImpl.setNew(workflowInstanceLink.isNew());
		workflowInstanceLinkImpl.setPrimaryKey(workflowInstanceLink.getPrimaryKey());

		workflowInstanceLinkImpl.setWorkflowInstanceLinkId(workflowInstanceLink.getWorkflowInstanceLinkId());
		workflowInstanceLinkImpl.setGroupId(workflowInstanceLink.getGroupId());
		workflowInstanceLinkImpl.setCompanyId(workflowInstanceLink.getCompanyId());
		workflowInstanceLinkImpl.setUserId(workflowInstanceLink.getUserId());
		workflowInstanceLinkImpl.setUserName(workflowInstanceLink.getUserName());
		workflowInstanceLinkImpl.setCreateDate(workflowInstanceLink.getCreateDate());
		workflowInstanceLinkImpl.setModifiedDate(workflowInstanceLink.getModifiedDate());
		workflowInstanceLinkImpl.setClassNameId(workflowInstanceLink.getClassNameId());
		workflowInstanceLinkImpl.setClassPK(workflowInstanceLink.getClassPK());
		workflowInstanceLinkImpl.setWorkflowInstanceId(workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstanceLinkImpl;
	}

	public WorkflowInstanceLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WorkflowInstanceLink findByPrimaryKey(long workflowInstanceLinkId)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		WorkflowInstanceLink workflowInstanceLink = fetchByPrimaryKey(workflowInstanceLinkId);

		if (workflowInstanceLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					workflowInstanceLinkId);
			}

			throw new NoSuchWorkflowInstanceLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				workflowInstanceLinkId);
		}

		return workflowInstanceLink;
	}

	public WorkflowInstanceLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WorkflowInstanceLink fetchByPrimaryKey(long workflowInstanceLinkId)
		throws SystemException {
		WorkflowInstanceLink workflowInstanceLink = (WorkflowInstanceLink)EntityCacheUtil.getResult(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowInstanceLinkImpl.class, workflowInstanceLinkId, this);

		if (workflowInstanceLink == null) {
			Session session = null;

			try {
				session = openSession();

				workflowInstanceLink = (WorkflowInstanceLink)session.get(WorkflowInstanceLinkImpl.class,
						new Long(workflowInstanceLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (workflowInstanceLink != null) {
					cacheResult(workflowInstanceLink);
				}

				closeSession(session);
			}
		}

		return workflowInstanceLink;
	}

	public List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId),
				new Long(classPK)
			};

		List<WorkflowInstanceLink> list = (List<WorkflowInstanceLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_WORKFLOWINSTANCELINK_WHERE);

				query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

				query.append(WorkflowInstanceLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowInstanceLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK, int start, int end)
		throws SystemException {
		return findByG_C_C_C(groupId, companyId, classNameId, classPK, start,
			end, null);
	}

	public List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId),
				new Long(classPK),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WorkflowInstanceLink> list = (List<WorkflowInstanceLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C_C_C,
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

				query.append(_SQL_SELECT_WORKFLOWINSTANCELINK_WHERE);

				query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WorkflowInstanceLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<WorkflowInstanceLink>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowInstanceLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WorkflowInstanceLink findByG_C_C_C_First(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		List<WorkflowInstanceLink> list = findByG_C_C_C(groupId, companyId,
				classNameId, classPK, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowInstanceLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowInstanceLink findByG_C_C_C_Last(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		int count = countByG_C_C_C(groupId, companyId, classNameId, classPK);

		List<WorkflowInstanceLink> list = findByG_C_C_C(groupId, companyId,
				classNameId, classPK, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowInstanceLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowInstanceLink[] findByG_C_C_C_PrevAndNext(
		long workflowInstanceLinkId, long groupId, long companyId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		WorkflowInstanceLink workflowInstanceLink = findByPrimaryKey(workflowInstanceLinkId);

		int count = countByG_C_C_C(groupId, companyId, classNameId, classPK);

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

			query.append(_SQL_SELECT_WORKFLOWINSTANCELINK_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkflowInstanceLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, workflowInstanceLink);

			WorkflowInstanceLink[] array = new WorkflowInstanceLinkImpl[3];

			array[0] = (WorkflowInstanceLink)objArray[0];
			array[1] = (WorkflowInstanceLink)objArray[1];
			array[2] = (WorkflowInstanceLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WorkflowInstanceLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WorkflowInstanceLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WorkflowInstanceLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WorkflowInstanceLink> list = (List<WorkflowInstanceLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_WORKFLOWINSTANCELINK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_WORKFLOWINSTANCELINK.concat(WorkflowInstanceLinkModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<WorkflowInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WorkflowInstanceLink>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowInstanceLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK) throws SystemException {
		for (WorkflowInstanceLink workflowInstanceLink : findByG_C_C_C(
				groupId, companyId, classNameId, classPK)) {
			remove(workflowInstanceLink);
		}
	}

	public void removeAll() throws SystemException {
		for (WorkflowInstanceLink workflowInstanceLink : findAll()) {
			remove(workflowInstanceLink);
		}
	}

	public int countByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId),
				new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_WORKFLOWINSTANCELINK_WHERE);

				query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C_C,
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

				Query q = session.createQuery(_SQL_COUNT_WORKFLOWINSTANCELINK);

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
						"value.object.listener.com.liferay.portal.model.WorkflowInstanceLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WorkflowInstanceLink>> listenersList = new ArrayList<ModelListener<WorkflowInstanceLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WorkflowInstanceLink>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_WORKFLOWINSTANCELINK = "SELECT workflowInstanceLink FROM WorkflowInstanceLink workflowInstanceLink";
	private static final String _SQL_SELECT_WORKFLOWINSTANCELINK_WHERE = "SELECT workflowInstanceLink FROM WorkflowInstanceLink workflowInstanceLink WHERE ";
	private static final String _SQL_COUNT_WORKFLOWINSTANCELINK = "SELECT COUNT(workflowInstanceLink) FROM WorkflowInstanceLink workflowInstanceLink";
	private static final String _SQL_COUNT_WORKFLOWINSTANCELINK_WHERE = "SELECT COUNT(workflowInstanceLink) FROM WorkflowInstanceLink workflowInstanceLink WHERE ";
	private static final String _FINDER_COLUMN_G_C_C_C_GROUPID_2 = "workflowInstanceLink.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_COMPANYID_2 = "workflowInstanceLink.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2 = "workflowInstanceLink.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_CLASSPK_2 = "workflowInstanceLink.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "workflowInstanceLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WorkflowInstanceLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WorkflowInstanceLink exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(WorkflowInstanceLinkPersistenceImpl.class);
}