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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_C = new FinderPath(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
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

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_C,
			new Object[] {
				new Long(workflowInstanceLink.getGroupId()),
				new Long(workflowInstanceLink.getCompanyId()),
				new Long(workflowInstanceLink.getClassNameId()),
				new Long(workflowInstanceLink.getClassPK())
			}, workflowInstanceLink);
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

		WorkflowInstanceLinkModelImpl workflowInstanceLinkModelImpl = (WorkflowInstanceLinkModelImpl)workflowInstanceLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C,
			new Object[] {
				new Long(workflowInstanceLinkModelImpl.getOriginalGroupId()),
				new Long(workflowInstanceLinkModelImpl.getOriginalCompanyId()),
				new Long(workflowInstanceLinkModelImpl.getOriginalClassNameId()),
				new Long(workflowInstanceLinkModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(WorkflowInstanceLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowInstanceLinkImpl.class, workflowInstanceLink.getPrimaryKey());

		return workflowInstanceLink;
	}

	public WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge) throws SystemException {
		workflowInstanceLink = toUnwrappedModel(workflowInstanceLink);

		boolean isNew = workflowInstanceLink.isNew();

		WorkflowInstanceLinkModelImpl workflowInstanceLinkModelImpl = (WorkflowInstanceLinkModelImpl)workflowInstanceLink;

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

		if (!isNew &&
				((workflowInstanceLink.getGroupId() != workflowInstanceLinkModelImpl.getOriginalGroupId()) ||
				(workflowInstanceLink.getCompanyId() != workflowInstanceLinkModelImpl.getOriginalCompanyId()) ||
				(workflowInstanceLink.getClassNameId() != workflowInstanceLinkModelImpl.getOriginalClassNameId()) ||
				(workflowInstanceLink.getClassPK() != workflowInstanceLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C,
				new Object[] {
					new Long(workflowInstanceLinkModelImpl.getOriginalGroupId()),
					new Long(workflowInstanceLinkModelImpl.getOriginalCompanyId()),
					new Long(workflowInstanceLinkModelImpl.getOriginalClassNameId()),
					new Long(workflowInstanceLinkModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((workflowInstanceLink.getGroupId() != workflowInstanceLinkModelImpl.getOriginalGroupId()) ||
				(workflowInstanceLink.getCompanyId() != workflowInstanceLinkModelImpl.getOriginalCompanyId()) ||
				(workflowInstanceLink.getClassNameId() != workflowInstanceLinkModelImpl.getOriginalClassNameId()) ||
				(workflowInstanceLink.getClassPK() != workflowInstanceLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_C,
				new Object[] {
					new Long(workflowInstanceLink.getGroupId()),
					new Long(workflowInstanceLink.getCompanyId()),
					new Long(workflowInstanceLink.getClassNameId()),
					new Long(workflowInstanceLink.getClassPK())
				}, workflowInstanceLink);
		}

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

	public WorkflowInstanceLink findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		WorkflowInstanceLink workflowInstanceLink = fetchByG_C_C_C(groupId,
				companyId, classNameId, classPK);

		if (workflowInstanceLink == null) {
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

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchWorkflowInstanceLinkException(msg.toString());
		}

		return workflowInstanceLink;
	}

	public WorkflowInstanceLink fetchByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) throws SystemException {
		return fetchByG_C_C_C(groupId, companyId, classNameId, classPK, true);
	}

	public WorkflowInstanceLink fetchByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId),
				new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_WORKFLOWINSTANCELINK_WHERE);

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

				List<WorkflowInstanceLink> list = q.list();

				result = list;

				WorkflowInstanceLink workflowInstanceLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_C,
						finderArgs, list);
				}
				else {
					workflowInstanceLink = list.get(0);

					cacheResult(workflowInstanceLink);

					if ((workflowInstanceLink.getGroupId() != groupId) ||
							(workflowInstanceLink.getCompanyId() != companyId) ||
							(workflowInstanceLink.getClassNameId() != classNameId) ||
							(workflowInstanceLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_C,
							finderArgs, workflowInstanceLink);
					}
				}

				return workflowInstanceLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_C,
						finderArgs, new ArrayList<WorkflowInstanceLink>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WorkflowInstanceLink)result;
			}
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

	public List<WorkflowInstanceLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WorkflowInstanceLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WorkflowInstanceLink> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WorkflowInstanceLink> list = (List<WorkflowInstanceLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_WORKFLOWINSTANCELINK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_WORKFLOWINSTANCELINK;

				Query q = session.createQuery(sql);

				if (obc == null) {
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
		long classPK)
		throws NoSuchWorkflowInstanceLinkException, SystemException {
		WorkflowInstanceLink workflowInstanceLink = findByG_C_C_C(groupId,
				companyId, classNameId, classPK);

		remove(workflowInstanceLink);
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