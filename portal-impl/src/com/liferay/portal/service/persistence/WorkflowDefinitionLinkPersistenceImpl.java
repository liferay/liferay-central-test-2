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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkImpl;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WorkflowDefinitionLinkPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkPersistence
 * @see       WorkflowDefinitionLinkUtil
 * @generated
 */
public class WorkflowDefinitionLinkPersistenceImpl extends BasePersistenceImpl<WorkflowDefinitionLink>
	implements WorkflowDefinitionLinkPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WorkflowDefinitionLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(WorkflowDefinitionLink workflowDefinitionLink) {
		EntityCacheUtil.putResult(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkImpl.class,
			workflowDefinitionLink.getPrimaryKey(), workflowDefinitionLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				new Long(workflowDefinitionLink.getGroupId()),
				new Long(workflowDefinitionLink.getCompanyId()),
				new Long(workflowDefinitionLink.getClassNameId())
			}, workflowDefinitionLink);
	}

	public void cacheResult(
		List<WorkflowDefinitionLink> workflowDefinitionLinks) {
		for (WorkflowDefinitionLink workflowDefinitionLink : workflowDefinitionLinks) {
			if (EntityCacheUtil.getResult(
						WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
						WorkflowDefinitionLinkImpl.class,
						workflowDefinitionLink.getPrimaryKey(), this) == null) {
				cacheResult(workflowDefinitionLink);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WorkflowDefinitionLinkImpl.class.getName());
		EntityCacheUtil.clearCache(WorkflowDefinitionLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public WorkflowDefinitionLink create(long workflowDefinitionLinkId) {
		WorkflowDefinitionLink workflowDefinitionLink = new WorkflowDefinitionLinkImpl();

		workflowDefinitionLink.setNew(true);
		workflowDefinitionLink.setPrimaryKey(workflowDefinitionLinkId);

		return workflowDefinitionLink;
	}

	public WorkflowDefinitionLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public WorkflowDefinitionLink remove(long workflowDefinitionLinkId)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WorkflowDefinitionLink workflowDefinitionLink = (WorkflowDefinitionLink)session.get(WorkflowDefinitionLinkImpl.class,
					new Long(workflowDefinitionLinkId));

			if (workflowDefinitionLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						workflowDefinitionLinkId);
				}

				throw new NoSuchWorkflowDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					workflowDefinitionLinkId);
			}

			return remove(workflowDefinitionLink);
		}
		catch (NoSuchWorkflowDefinitionLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WorkflowDefinitionLink remove(
		WorkflowDefinitionLink workflowDefinitionLink)
		throws SystemException {
		for (ModelListener<WorkflowDefinitionLink> listener : listeners) {
			listener.onBeforeRemove(workflowDefinitionLink);
		}

		workflowDefinitionLink = removeImpl(workflowDefinitionLink);

		for (ModelListener<WorkflowDefinitionLink> listener : listeners) {
			listener.onAfterRemove(workflowDefinitionLink);
		}

		return workflowDefinitionLink;
	}

	protected WorkflowDefinitionLink removeImpl(
		WorkflowDefinitionLink workflowDefinitionLink)
		throws SystemException {
		workflowDefinitionLink = toUnwrappedModel(workflowDefinitionLink);

		Session session = null;

		try {
			session = openSession();

			if (workflowDefinitionLink.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WorkflowDefinitionLinkImpl.class,
						workflowDefinitionLink.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(workflowDefinitionLink);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		WorkflowDefinitionLinkModelImpl workflowDefinitionLinkModelImpl = (WorkflowDefinitionLinkModelImpl)workflowDefinitionLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				new Long(workflowDefinitionLinkModelImpl.getOriginalGroupId()),
				new Long(workflowDefinitionLinkModelImpl.getOriginalCompanyId()),
				new Long(workflowDefinitionLinkModelImpl.getOriginalClassNameId())
			});

		EntityCacheUtil.removeResult(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkImpl.class,
			workflowDefinitionLink.getPrimaryKey());

		return workflowDefinitionLink;
	}

	public WorkflowDefinitionLink updateImpl(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge) throws SystemException {
		workflowDefinitionLink = toUnwrappedModel(workflowDefinitionLink);

		boolean isNew = workflowDefinitionLink.isNew();

		WorkflowDefinitionLinkModelImpl workflowDefinitionLinkModelImpl = (WorkflowDefinitionLinkModelImpl)workflowDefinitionLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, workflowDefinitionLink, merge);

			workflowDefinitionLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowDefinitionLinkImpl.class,
			workflowDefinitionLink.getPrimaryKey(), workflowDefinitionLink);

		if (!isNew &&
				((workflowDefinitionLink.getGroupId() != workflowDefinitionLinkModelImpl.getOriginalGroupId()) ||
				(workflowDefinitionLink.getCompanyId() != workflowDefinitionLinkModelImpl.getOriginalCompanyId()) ||
				(workflowDefinitionLink.getClassNameId() != workflowDefinitionLinkModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C,
				new Object[] {
					new Long(workflowDefinitionLinkModelImpl.getOriginalGroupId()),
					new Long(workflowDefinitionLinkModelImpl.getOriginalCompanyId()),
					new Long(workflowDefinitionLinkModelImpl.getOriginalClassNameId())
				});
		}

		if (isNew ||
				((workflowDefinitionLink.getGroupId() != workflowDefinitionLinkModelImpl.getOriginalGroupId()) ||
				(workflowDefinitionLink.getCompanyId() != workflowDefinitionLinkModelImpl.getOriginalCompanyId()) ||
				(workflowDefinitionLink.getClassNameId() != workflowDefinitionLinkModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C,
				new Object[] {
					new Long(workflowDefinitionLink.getGroupId()),
					new Long(workflowDefinitionLink.getCompanyId()),
					new Long(workflowDefinitionLink.getClassNameId())
				}, workflowDefinitionLink);
		}

		return workflowDefinitionLink;
	}

	protected WorkflowDefinitionLink toUnwrappedModel(
		WorkflowDefinitionLink workflowDefinitionLink) {
		if (workflowDefinitionLink instanceof WorkflowDefinitionLinkImpl) {
			return workflowDefinitionLink;
		}

		WorkflowDefinitionLinkImpl workflowDefinitionLinkImpl = new WorkflowDefinitionLinkImpl();

		workflowDefinitionLinkImpl.setNew(workflowDefinitionLink.isNew());
		workflowDefinitionLinkImpl.setPrimaryKey(workflowDefinitionLink.getPrimaryKey());

		workflowDefinitionLinkImpl.setWorkflowDefinitionLinkId(workflowDefinitionLink.getWorkflowDefinitionLinkId());
		workflowDefinitionLinkImpl.setGroupId(workflowDefinitionLink.getGroupId());
		workflowDefinitionLinkImpl.setCompanyId(workflowDefinitionLink.getCompanyId());
		workflowDefinitionLinkImpl.setUserId(workflowDefinitionLink.getUserId());
		workflowDefinitionLinkImpl.setUserName(workflowDefinitionLink.getUserName());
		workflowDefinitionLinkImpl.setCreateDate(workflowDefinitionLink.getCreateDate());
		workflowDefinitionLinkImpl.setModifiedDate(workflowDefinitionLink.getModifiedDate());
		workflowDefinitionLinkImpl.setClassNameId(workflowDefinitionLink.getClassNameId());
		workflowDefinitionLinkImpl.setWorkflowDefinitionName(workflowDefinitionLink.getWorkflowDefinitionName());
		workflowDefinitionLinkImpl.setWorkflowDefinitionVersion(workflowDefinitionLink.getWorkflowDefinitionVersion());

		return workflowDefinitionLinkImpl;
	}

	public WorkflowDefinitionLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		WorkflowDefinitionLink workflowDefinitionLink = fetchByPrimaryKey(workflowDefinitionLinkId);

		if (workflowDefinitionLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					workflowDefinitionLinkId);
			}

			throw new NoSuchWorkflowDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				workflowDefinitionLinkId);
		}

		return workflowDefinitionLink;
	}

	public WorkflowDefinitionLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId) throws SystemException {
		WorkflowDefinitionLink workflowDefinitionLink = (WorkflowDefinitionLink)EntityCacheUtil.getResult(WorkflowDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowDefinitionLinkImpl.class, workflowDefinitionLinkId, this);

		if (workflowDefinitionLink == null) {
			Session session = null;

			try {
				session = openSession();

				workflowDefinitionLink = (WorkflowDefinitionLink)session.get(WorkflowDefinitionLinkImpl.class,
						new Long(workflowDefinitionLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (workflowDefinitionLink != null) {
					cacheResult(workflowDefinitionLink);
				}

				closeSession(session);
			}
		}

		return workflowDefinitionLink;
	}

	public List<WorkflowDefinitionLink> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<WorkflowDefinitionLink> list = (List<WorkflowDefinitionLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_WORKFLOWDEFINITIONLINK_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(WorkflowDefinitionLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowDefinitionLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WorkflowDefinitionLink> findByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<WorkflowDefinitionLink> findByCompanyId(long companyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WorkflowDefinitionLink> list = (List<WorkflowDefinitionLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_WORKFLOWDEFINITIONLINK_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(WorkflowDefinitionLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<WorkflowDefinitionLink>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowDefinitionLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WorkflowDefinitionLink findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		List<WorkflowDefinitionLink> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowDefinitionLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowDefinitionLink findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		int count = countByCompanyId(companyId);

		List<WorkflowDefinitionLink> list = findByCompanyId(companyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowDefinitionLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId, OrderByComparator obc)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		WorkflowDefinitionLink workflowDefinitionLink = findByPrimaryKey(workflowDefinitionLinkId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_WORKFLOWDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(WorkflowDefinitionLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					workflowDefinitionLink);

			WorkflowDefinitionLink[] array = new WorkflowDefinitionLinkImpl[3];

			array[0] = (WorkflowDefinitionLink)objArray[0];
			array[1] = (WorkflowDefinitionLink)objArray[1];
			array[2] = (WorkflowDefinitionLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WorkflowDefinitionLink findByG_C_C(long groupId, long companyId,
		long classNameId)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		WorkflowDefinitionLink workflowDefinitionLink = fetchByG_C_C(groupId,
				companyId, classNameId);

		if (workflowDefinitionLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchWorkflowDefinitionLinkException(msg.toString());
		}

		return workflowDefinitionLink;
	}

	public WorkflowDefinitionLink fetchByG_C_C(long groupId, long companyId,
		long classNameId) throws SystemException {
		return fetchByG_C_C(groupId, companyId, classNameId, true);
	}

	public WorkflowDefinitionLink fetchByG_C_C(long groupId, long companyId,
		long classNameId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_WORKFLOWDEFINITIONLINK_WHERE);

				query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

				query.append(WorkflowDefinitionLinkModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				List<WorkflowDefinitionLink> list = q.list();

				result = list;

				WorkflowDefinitionLink workflowDefinitionLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					workflowDefinitionLink = list.get(0);

					cacheResult(workflowDefinitionLink);

					if ((workflowDefinitionLink.getGroupId() != groupId) ||
							(workflowDefinitionLink.getCompanyId() != companyId) ||
							(workflowDefinitionLink.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, workflowDefinitionLink);
					}
				}

				return workflowDefinitionLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, new ArrayList<WorkflowDefinitionLink>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WorkflowDefinitionLink)result;
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

	public List<WorkflowDefinitionLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WorkflowDefinitionLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WorkflowDefinitionLink> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WorkflowDefinitionLink> list = (List<WorkflowDefinitionLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_WORKFLOWDEFINITIONLINK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_WORKFLOWDEFINITIONLINK.concat(WorkflowDefinitionLinkModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<WorkflowDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WorkflowDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowDefinitionLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (WorkflowDefinitionLink workflowDefinitionLink : findByCompanyId(
				companyId)) {
			remove(workflowDefinitionLink);
		}
	}

	public void removeByG_C_C(long groupId, long companyId, long classNameId)
		throws NoSuchWorkflowDefinitionLinkException, SystemException {
		WorkflowDefinitionLink workflowDefinitionLink = findByG_C_C(groupId,
				companyId, classNameId);

		remove(workflowDefinitionLink);
	}

	public void removeAll() throws SystemException {
		for (WorkflowDefinitionLink workflowDefinitionLink : findAll()) {
			remove(workflowDefinitionLink);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_WORKFLOWDEFINITIONLINK_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C_C(long groupId, long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(companyId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WORKFLOWDEFINITIONLINK_WHERE);

				query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C,
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

				Query q = session.createQuery(_SQL_COUNT_WORKFLOWDEFINITIONLINK);

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
						"value.object.listener.com.liferay.portal.model.WorkflowDefinitionLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WorkflowDefinitionLink>> listenersList = new ArrayList<ModelListener<WorkflowDefinitionLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WorkflowDefinitionLink>)Class.forName(
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
	private static final String _SQL_SELECT_WORKFLOWDEFINITIONLINK = "SELECT workflowDefinitionLink FROM WorkflowDefinitionLink workflowDefinitionLink";
	private static final String _SQL_SELECT_WORKFLOWDEFINITIONLINK_WHERE = "SELECT workflowDefinitionLink FROM WorkflowDefinitionLink workflowDefinitionLink WHERE ";
	private static final String _SQL_COUNT_WORKFLOWDEFINITIONLINK = "SELECT COUNT(workflowDefinitionLink) FROM WorkflowDefinitionLink workflowDefinitionLink";
	private static final String _SQL_COUNT_WORKFLOWDEFINITIONLINK_WHERE = "SELECT COUNT(workflowDefinitionLink) FROM WorkflowDefinitionLink workflowDefinitionLink WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "workflowDefinitionLink.companyId = ?";
	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "workflowDefinitionLink.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_COMPANYID_2 = "workflowDefinitionLink.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "workflowDefinitionLink.classNameId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "workflowDefinitionLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WorkflowDefinitionLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WorkflowDefinitionLink exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(WorkflowDefinitionLinkPersistenceImpl.class);
}