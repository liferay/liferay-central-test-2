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
import com.liferay.portal.NoSuchResourceCodeException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.ResourceCodeImpl;
import com.liferay.portal.model.impl.ResourceCodeModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourceCodePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodePersistence
 * @see       ResourceCodeUtil
 * @generated
 */
public class ResourceCodePersistenceImpl extends BasePersistenceImpl<ResourceCode>
	implements ResourceCodePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceCodeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_NAME = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_NAME = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByName",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N_S = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ResourceCode resourceCode) {
		EntityCacheUtil.putResult(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeImpl.class, resourceCode.getPrimaryKey(), resourceCode);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S,
			new Object[] {
				new Long(resourceCode.getCompanyId()),
				
			resourceCode.getName(), new Integer(resourceCode.getScope())
			}, resourceCode);
	}

	public void cacheResult(List<ResourceCode> resourceCodes) {
		for (ResourceCode resourceCode : resourceCodes) {
			if (EntityCacheUtil.getResult(
						ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
						ResourceCodeImpl.class, resourceCode.getPrimaryKey(),
						this) == null) {
				cacheResult(resourceCode);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ResourceCodeImpl.class.getName());
		EntityCacheUtil.clearCache(ResourceCodeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ResourceCode create(long codeId) {
		ResourceCode resourceCode = new ResourceCodeImpl();

		resourceCode.setNew(true);
		resourceCode.setPrimaryKey(codeId);

		return resourceCode;
	}

	public ResourceCode remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ResourceCode remove(long codeId)
		throws NoSuchResourceCodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceCode resourceCode = (ResourceCode)session.get(ResourceCodeImpl.class,
					new Long(codeId));

			if (resourceCode == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + codeId);
				}

				throw new NoSuchResourceCodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					codeId);
			}

			return remove(resourceCode);
		}
		catch (NoSuchResourceCodeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceCode remove(ResourceCode resourceCode)
		throws SystemException {
		for (ModelListener<ResourceCode> listener : listeners) {
			listener.onBeforeRemove(resourceCode);
		}

		resourceCode = removeImpl(resourceCode);

		for (ModelListener<ResourceCode> listener : listeners) {
			listener.onAfterRemove(resourceCode);
		}

		return resourceCode;
	}

	protected ResourceCode removeImpl(ResourceCode resourceCode)
		throws SystemException {
		resourceCode = toUnwrappedModel(resourceCode);

		Session session = null;

		try {
			session = openSession();

			if (resourceCode.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ResourceCodeImpl.class,
						resourceCode.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(resourceCode);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceCodeModelImpl resourceCodeModelImpl = (ResourceCodeModelImpl)resourceCode;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S,
			new Object[] {
				new Long(resourceCodeModelImpl.getOriginalCompanyId()),
				
			resourceCodeModelImpl.getOriginalName(),
				new Integer(resourceCodeModelImpl.getOriginalScope())
			});

		EntityCacheUtil.removeResult(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeImpl.class, resourceCode.getPrimaryKey());

		return resourceCode;
	}

	public ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws SystemException {
		resourceCode = toUnwrappedModel(resourceCode);

		boolean isNew = resourceCode.isNew();

		ResourceCodeModelImpl resourceCodeModelImpl = (ResourceCodeModelImpl)resourceCode;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourceCode, merge);

			resourceCode.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
			ResourceCodeImpl.class, resourceCode.getPrimaryKey(), resourceCode);

		if (!isNew &&
				((resourceCode.getCompanyId() != resourceCodeModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourceCode.getName(),
					resourceCodeModelImpl.getOriginalName()) ||
				(resourceCode.getScope() != resourceCodeModelImpl.getOriginalScope()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S,
				new Object[] {
					new Long(resourceCodeModelImpl.getOriginalCompanyId()),
					
				resourceCodeModelImpl.getOriginalName(),
					new Integer(resourceCodeModelImpl.getOriginalScope())
				});
		}

		if (isNew ||
				((resourceCode.getCompanyId() != resourceCodeModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourceCode.getName(),
					resourceCodeModelImpl.getOriginalName()) ||
				(resourceCode.getScope() != resourceCodeModelImpl.getOriginalScope()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S,
				new Object[] {
					new Long(resourceCode.getCompanyId()),
					
				resourceCode.getName(), new Integer(resourceCode.getScope())
				}, resourceCode);
		}

		return resourceCode;
	}

	protected ResourceCode toUnwrappedModel(ResourceCode resourceCode) {
		if (resourceCode instanceof ResourceCodeImpl) {
			return resourceCode;
		}

		ResourceCodeImpl resourceCodeImpl = new ResourceCodeImpl();

		resourceCodeImpl.setNew(resourceCode.isNew());
		resourceCodeImpl.setPrimaryKey(resourceCode.getPrimaryKey());

		resourceCodeImpl.setCodeId(resourceCode.getCodeId());
		resourceCodeImpl.setCompanyId(resourceCode.getCompanyId());
		resourceCodeImpl.setName(resourceCode.getName());
		resourceCodeImpl.setScope(resourceCode.getScope());

		return resourceCodeImpl;
	}

	public ResourceCode findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ResourceCode findByPrimaryKey(long codeId)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = fetchByPrimaryKey(codeId);

		if (resourceCode == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + codeId);
			}

			throw new NoSuchResourceCodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				codeId);
		}

		return resourceCode;
	}

	public ResourceCode fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ResourceCode fetchByPrimaryKey(long codeId)
		throws SystemException {
		ResourceCode resourceCode = (ResourceCode)EntityCacheUtil.getResult(ResourceCodeModelImpl.ENTITY_CACHE_ENABLED,
				ResourceCodeImpl.class, codeId, this);

		if (resourceCode == null) {
			Session session = null;

			try {
				session = openSession();

				resourceCode = (ResourceCode)session.get(ResourceCodeImpl.class,
						new Long(codeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resourceCode != null) {
					cacheResult(resourceCode);
				}

				closeSession(session);
			}
		}

		return resourceCode;
	}

	public List<ResourceCode> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<ResourceCode> list = (List<ResourceCode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_RESOURCECODE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
					list = new ArrayList<ResourceCode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourceCode> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<ResourceCode> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourceCode> list = (List<ResourceCode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_RESOURCECODE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<ResourceCode>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceCode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourceCode findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		List<ResourceCode> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		int count = countByCompanyId(companyId);

		List<ResourceCode> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode[] findByCompanyId_PrevAndNext(long codeId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByPrimaryKey(codeId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_RESOURCECODE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, resourceCode);

			ResourceCode[] array = new ResourceCodeImpl[3];

			array[0] = (ResourceCode)objArray[0];
			array[1] = (ResourceCode)objArray[1];
			array[2] = (ResourceCode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourceCode> findByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		List<ResourceCode> list = (List<ResourceCode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_NAME,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_RESOURCECODE_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceCode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_NAME, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ResourceCode> findByName(String name, int start, int end)
		throws SystemException {
		return findByName(name, start, end, null);
	}

	public List<ResourceCode> findByName(String name, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				name,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourceCode> list = (List<ResourceCode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_NAME,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_RESOURCECODE_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<ResourceCode>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceCode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_NAME,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ResourceCode findByName_First(String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		List<ResourceCode> list = findByName(name, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode findByName_Last(String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		int count = countByName(name);

		List<ResourceCode> list = findByName(name, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceCodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ResourceCode[] findByName_PrevAndNext(long codeId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByPrimaryKey(codeId);

		int count = countByName(name);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_RESOURCECODE_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, resourceCode);

			ResourceCode[] array = new ResourceCodeImpl[3];

			array[0] = (ResourceCode)objArray[0];
			array[1] = (ResourceCode)objArray[1];
			array[2] = (ResourceCode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ResourceCode findByC_N_S(long companyId, String name, int scope)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = fetchByC_N_S(companyId, name, scope);

		if (resourceCode == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceCodeException(msg.toString());
		}

		return resourceCode;
	}

	public ResourceCode fetchByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		return fetchByC_N_S(companyId, name, scope, true);
	}

	public ResourceCode fetchByC_N_S(long companyId, String name, int scope,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N_S,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_RESOURCECODE_WHERE);

				query.append(_FINDER_COLUMN_C_N_S_COMPANYID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_N_S_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_N_S_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_N_S_NAME_2);
					}
				}

				query.append(_FINDER_COLUMN_C_N_S_SCOPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				List<ResourceCode> list = q.list();

				result = list;

				ResourceCode resourceCode = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S,
						finderArgs, list);
				}
				else {
					resourceCode = list.get(0);

					cacheResult(resourceCode);

					if ((resourceCode.getCompanyId() != companyId) ||
							(resourceCode.getName() == null) ||
							!resourceCode.getName().equals(name) ||
							(resourceCode.getScope() != scope)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S,
							finderArgs, resourceCode);
					}
				}

				return resourceCode;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S,
						finderArgs, new ArrayList<ResourceCode>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ResourceCode)result;
			}
		}
	}

	public List<ResourceCode> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ResourceCode> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ResourceCode> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourceCode> list = (List<ResourceCode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_RESOURCECODE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_RESOURCECODE;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ResourceCode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourceCode>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ResourceCode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (ResourceCode resourceCode : findByCompanyId(companyId)) {
			remove(resourceCode);
		}
	}

	public void removeByName(String name) throws SystemException {
		for (ResourceCode resourceCode : findByName(name)) {
			remove(resourceCode);
		}
	}

	public void removeByC_N_S(long companyId, String name, int scope)
		throws NoSuchResourceCodeException, SystemException {
		ResourceCode resourceCode = findByC_N_S(companyId, name, scope);

		remove(resourceCode);
	}

	public void removeAll() throws SystemException {
		for (ResourceCode resourceCode : findAll()) {
			remove(resourceCode);
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

				query.append(_SQL_COUNT_RESOURCECODE_WHERE);

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

	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_RESOURCECODE_WHERE);

				if (name == null) {
					query.append(_FINDER_COLUMN_NAME_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NAME_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_NAME_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				name, new Integer(scope)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_RESOURCECODE_WHERE);

				query.append(_FINDER_COLUMN_C_N_S_COMPANYID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_N_S_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_N_S_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_N_S_NAME_2);
					}
				}

				query.append(_FINDER_COLUMN_C_N_S_SCOPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S,
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

				Query q = session.createQuery(_SQL_COUNT_RESOURCECODE);

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
						"value.object.listener.com.liferay.portal.model.ResourceCode")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceCode>> listenersList = new ArrayList<ModelListener<ResourceCode>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceCode>)Class.forName(
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
	private static final String _SQL_SELECT_RESOURCECODE = "SELECT resourceCode FROM ResourceCode resourceCode";
	private static final String _SQL_SELECT_RESOURCECODE_WHERE = "SELECT resourceCode FROM ResourceCode resourceCode WHERE ";
	private static final String _SQL_COUNT_RESOURCECODE = "SELECT COUNT(resourceCode) FROM ResourceCode resourceCode";
	private static final String _SQL_COUNT_RESOURCECODE_WHERE = "SELECT COUNT(resourceCode) FROM ResourceCode resourceCode WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "resourceCode.companyId = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "resourceCode.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "resourceCode.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(resourceCode.name IS NULL OR resourceCode.name = ?)";
	private static final String _FINDER_COLUMN_C_N_S_COMPANYID_2 = "resourceCode.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_1 = "resourceCode.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_2 = "resourceCode.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_3 = "(resourceCode.name IS NULL OR resourceCode.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_S_SCOPE_2 = "resourceCode.scope = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceCode.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceCode exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceCode exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ResourceCodePersistenceImpl.class);
}