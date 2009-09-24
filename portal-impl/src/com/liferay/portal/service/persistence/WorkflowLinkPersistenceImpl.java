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

import com.liferay.portal.NoSuchWorkflowLinkException;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WorkflowLink;
import com.liferay.portal.model.impl.WorkflowLinkImpl;
import com.liferay.portal.model.impl.WorkflowLinkModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WorkflowLinkPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLinkPersistence
 * @see       WorkflowLinkUtil
 * @generated
 */
public class WorkflowLinkPersistenceImpl extends BasePersistenceImpl
	implements WorkflowLinkPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WorkflowLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_C = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_G_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_C = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_G_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(WorkflowLink workflowLink) {
		EntityCacheUtil.putResult(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkImpl.class, workflowLink.getPrimaryKey(), workflowLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_C,
			new Object[] {
				new Long(workflowLink.getCompanyId()),
				new Long(workflowLink.getGroupId()),
				new Long(workflowLink.getClassNameId())
			}, workflowLink);
	}

	public void cacheResult(List<WorkflowLink> workflowLinks) {
		for (WorkflowLink workflowLink : workflowLinks) {
			if (EntityCacheUtil.getResult(
						WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
						WorkflowLinkImpl.class, workflowLink.getPrimaryKey(),
						this) == null) {
				cacheResult(workflowLink);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WorkflowLinkImpl.class.getName());
		EntityCacheUtil.clearCache(WorkflowLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public WorkflowLink create(long workflowLinkId) {
		WorkflowLink workflowLink = new WorkflowLinkImpl();

		workflowLink.setNew(true);
		workflowLink.setPrimaryKey(workflowLinkId);

		return workflowLink;
	}

	public WorkflowLink remove(long workflowLinkId)
		throws NoSuchWorkflowLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WorkflowLink workflowLink = (WorkflowLink)session.get(WorkflowLinkImpl.class,
					new Long(workflowLinkId));

			if (workflowLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No WorkflowLink exists with the primary key " +
						workflowLinkId);
				}

				throw new NoSuchWorkflowLinkException(
					"No WorkflowLink exists with the primary key " +
					workflowLinkId);
			}

			return remove(workflowLink);
		}
		catch (NoSuchWorkflowLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WorkflowLink remove(WorkflowLink workflowLink)
		throws SystemException {
		for (ModelListener<WorkflowLink> listener : listeners) {
			listener.onBeforeRemove(workflowLink);
		}

		workflowLink = removeImpl(workflowLink);

		for (ModelListener<WorkflowLink> listener : listeners) {
			listener.onAfterRemove(workflowLink);
		}

		return workflowLink;
	}

	protected WorkflowLink removeImpl(WorkflowLink workflowLink)
		throws SystemException {
		workflowLink = toUnwrappedModel(workflowLink);

		Session session = null;

		try {
			session = openSession();

			if (workflowLink.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WorkflowLinkImpl.class,
						workflowLink.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(workflowLink);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		WorkflowLinkModelImpl workflowLinkModelImpl = (WorkflowLinkModelImpl)workflowLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_C,
			new Object[] {
				new Long(workflowLinkModelImpl.getOriginalCompanyId()),
				new Long(workflowLinkModelImpl.getOriginalGroupId()),
				new Long(workflowLinkModelImpl.getOriginalClassNameId())
			});

		EntityCacheUtil.removeResult(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkImpl.class, workflowLink.getPrimaryKey());

		return workflowLink;
	}

	/**
	 * @deprecated Use {@link #update(WorkflowLink, boolean merge)}.
	 */
	public WorkflowLink update(WorkflowLink workflowLink)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(WorkflowLink workflowLink) method. Use update(WorkflowLink workflowLink, boolean merge) instead.");
		}

		return update(workflowLink, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  workflowLink the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when workflowLink is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public WorkflowLink update(WorkflowLink workflowLink, boolean merge)
		throws SystemException {
		boolean isNew = workflowLink.isNew();

		for (ModelListener<WorkflowLink> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(workflowLink);
			}
			else {
				listener.onBeforeUpdate(workflowLink);
			}
		}

		workflowLink = updateImpl(workflowLink, merge);

		for (ModelListener<WorkflowLink> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(workflowLink);
			}
			else {
				listener.onAfterUpdate(workflowLink);
			}
		}

		return workflowLink;
	}

	public WorkflowLink updateImpl(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws SystemException {
		workflowLink = toUnwrappedModel(workflowLink);

		boolean isNew = workflowLink.isNew();

		WorkflowLinkModelImpl workflowLinkModelImpl = (WorkflowLinkModelImpl)workflowLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, workflowLink, merge);

			workflowLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
			WorkflowLinkImpl.class, workflowLink.getPrimaryKey(), workflowLink);

		if (!isNew &&
				((workflowLink.getCompanyId() != workflowLinkModelImpl.getOriginalCompanyId()) ||
				(workflowLink.getGroupId() != workflowLinkModelImpl.getOriginalGroupId()) ||
				(workflowLink.getClassNameId() != workflowLinkModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_C,
				new Object[] {
					new Long(workflowLinkModelImpl.getOriginalCompanyId()),
					new Long(workflowLinkModelImpl.getOriginalGroupId()),
					new Long(workflowLinkModelImpl.getOriginalClassNameId())
				});
		}

		if (isNew ||
				((workflowLink.getCompanyId() != workflowLinkModelImpl.getOriginalCompanyId()) ||
				(workflowLink.getGroupId() != workflowLinkModelImpl.getOriginalGroupId()) ||
				(workflowLink.getClassNameId() != workflowLinkModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_C,
				new Object[] {
					new Long(workflowLink.getCompanyId()),
					new Long(workflowLink.getGroupId()),
					new Long(workflowLink.getClassNameId())
				}, workflowLink);
		}

		return workflowLink;
	}

	protected WorkflowLink toUnwrappedModel(WorkflowLink workflowLink) {
		if (workflowLink instanceof WorkflowLinkImpl) {
			return workflowLink;
		}

		WorkflowLinkImpl workflowLinkImpl = new WorkflowLinkImpl();

		workflowLinkImpl.setNew(workflowLink.isNew());
		workflowLinkImpl.setPrimaryKey(workflowLink.getPrimaryKey());

		workflowLinkImpl.setWorkflowLinkId(workflowLink.getWorkflowLinkId());
		workflowLinkImpl.setGroupId(workflowLink.getGroupId());
		workflowLinkImpl.setCompanyId(workflowLink.getCompanyId());
		workflowLinkImpl.setUserId(workflowLink.getUserId());
		workflowLinkImpl.setUserName(workflowLink.getUserName());
		workflowLinkImpl.setModifiedDate(workflowLink.getModifiedDate());
		workflowLinkImpl.setClassNameId(workflowLink.getClassNameId());
		workflowLinkImpl.setDefinitionName(workflowLink.getDefinitionName());

		return workflowLinkImpl;
	}

	public WorkflowLink findByPrimaryKey(long workflowLinkId)
		throws NoSuchWorkflowLinkException, SystemException {
		WorkflowLink workflowLink = fetchByPrimaryKey(workflowLinkId);

		if (workflowLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WorkflowLink exists with the primary key " +
					workflowLinkId);
			}

			throw new NoSuchWorkflowLinkException(
				"No WorkflowLink exists with the primary key " +
				workflowLinkId);
		}

		return workflowLink;
	}

	public WorkflowLink fetchByPrimaryKey(long workflowLinkId)
		throws SystemException {
		WorkflowLink workflowLink = (WorkflowLink)EntityCacheUtil.getResult(WorkflowLinkModelImpl.ENTITY_CACHE_ENABLED,
				WorkflowLinkImpl.class, workflowLinkId, this);

		if (workflowLink == null) {
			Session session = null;

			try {
				session = openSession();

				workflowLink = (WorkflowLink)session.get(WorkflowLinkImpl.class,
						new Long(workflowLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (workflowLink != null) {
					cacheResult(workflowLink);
				}

				closeSession(session);
			}
		}

		return workflowLink;
	}

	public List<WorkflowLink> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<WorkflowLink> list = (List<WorkflowLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT workflowLink FROM WorkflowLink workflowLink WHERE ");

				query.append("workflowLink.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("workflowLink.definitionName ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WorkflowLink> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<WorkflowLink> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WorkflowLink> list = (List<WorkflowLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT workflowLink FROM WorkflowLink workflowLink WHERE ");

				query.append("workflowLink.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("workflowLink.");
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
					query.append("ORDER BY ");

					query.append("workflowLink.definitionName ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<WorkflowLink>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WorkflowLink findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchWorkflowLinkException, SystemException {
		List<WorkflowLink> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WorkflowLink exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowLink findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchWorkflowLinkException, SystemException {
		int count = countByCompanyId(companyId);

		List<WorkflowLink> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WorkflowLink exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWorkflowLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WorkflowLink[] findByCompanyId_PrevAndNext(long workflowLinkId,
		long companyId, OrderByComparator obc)
		throws NoSuchWorkflowLinkException, SystemException {
		WorkflowLink workflowLink = findByPrimaryKey(workflowLinkId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT workflowLink FROM WorkflowLink workflowLink WHERE ");

			query.append("workflowLink.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("workflowLink.");
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
				query.append("ORDER BY ");

				query.append("workflowLink.definitionName ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					workflowLink);

			WorkflowLink[] array = new WorkflowLinkImpl[3];

			array[0] = (WorkflowLink)objArray[0];
			array[1] = (WorkflowLink)objArray[1];
			array[2] = (WorkflowLink)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WorkflowLink findByC_G_C(long companyId, long groupId,
		long classNameId) throws NoSuchWorkflowLinkException, SystemException {
		WorkflowLink workflowLink = fetchByC_G_C(companyId, groupId, classNameId);

		if (workflowLink == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WorkflowLink exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchWorkflowLinkException(msg.toString());
		}

		return workflowLink;
	}

	public WorkflowLink fetchByC_G_C(long companyId, long groupId,
		long classNameId) throws SystemException {
		return fetchByC_G_C(companyId, groupId, classNameId, true);
	}

	public WorkflowLink fetchByC_G_C(long companyId, long groupId,
		long classNameId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId), new Long(classNameId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_G_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT workflowLink FROM WorkflowLink workflowLink WHERE ");

				query.append("workflowLink.companyId = ?");

				query.append(" AND ");

				query.append("workflowLink.groupId = ?");

				query.append(" AND ");

				query.append("workflowLink.classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("workflowLink.definitionName ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				qPos.add(classNameId);

				List<WorkflowLink> list = q.list();

				result = list;

				WorkflowLink workflowLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_C,
						finderArgs, list);
				}
				else {
					workflowLink = list.get(0);

					cacheResult(workflowLink);

					if ((workflowLink.getCompanyId() != companyId) ||
							(workflowLink.getGroupId() != groupId) ||
							(workflowLink.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_C,
							finderArgs, workflowLink);
					}
				}

				return workflowLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_C,
						finderArgs, new ArrayList<WorkflowLink>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WorkflowLink)result;
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

	public List<WorkflowLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WorkflowLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WorkflowLink> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WorkflowLink> list = (List<WorkflowLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT workflowLink FROM WorkflowLink workflowLink ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("workflowLink.");
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
					query.append("ORDER BY ");

					query.append("workflowLink.definitionName ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<WorkflowLink>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WorkflowLink>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WorkflowLink>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (WorkflowLink workflowLink : findByCompanyId(companyId)) {
			remove(workflowLink);
		}
	}

	public void removeByC_G_C(long companyId, long groupId, long classNameId)
		throws NoSuchWorkflowLinkException, SystemException {
		WorkflowLink workflowLink = findByC_G_C(companyId, groupId, classNameId);

		remove(workflowLink);
	}

	public void removeAll() throws SystemException {
		for (WorkflowLink workflowLink : findAll()) {
			remove(workflowLink);
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(workflowLink) ");
				query.append("FROM WorkflowLink workflowLink WHERE ");

				query.append("workflowLink.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByC_G_C(long companyId, long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_G_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(workflowLink) ");
				query.append("FROM WorkflowLink workflowLink WHERE ");

				query.append("workflowLink.companyId = ?");

				query.append(" AND ");

				query.append("workflowLink.groupId = ?");

				query.append(" AND ");

				query.append("workflowLink.classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_C,
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

				Query q = session.createQuery(
						"SELECT COUNT(workflowLink) FROM WorkflowLink workflowLink");

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
						"value.object.listener.com.liferay.portal.model.WorkflowLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WorkflowLink>> listenersList = new ArrayList<ModelListener<WorkflowLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WorkflowLink>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowLinkPersistence workflowLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(WorkflowLinkPersistenceImpl.class);
}