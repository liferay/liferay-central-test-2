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

package com.liferay.portlet.asset.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchCategoryPropertyException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetCategoryPropertyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPropertyPersistence
 * @see       AssetCategoryPropertyUtil
 * @generated
 */
public class AssetCategoryPropertyPersistenceImpl extends BasePersistenceImpl<AssetCategoryProperty>
	implements AssetCategoryPropertyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_CATEGORYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_CATEGORYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCategoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CATEGORYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CA_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByCA_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CA_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCA_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AssetCategoryProperty assetCategoryProperty) {
		EntityCacheUtil.putResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
			new Object[] {
				new Long(assetCategoryProperty.getCategoryId()),
				
			assetCategoryProperty.getKey()
			}, assetCategoryProperty);
	}

	public void cacheResult(List<AssetCategoryProperty> assetCategoryProperties) {
		for (AssetCategoryProperty assetCategoryProperty : assetCategoryProperties) {
			if (EntityCacheUtil.getResult(
						AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKey(), this) == null) {
				cacheResult(assetCategoryProperty);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetCategoryPropertyImpl.class.getName());
		EntityCacheUtil.clearCache(AssetCategoryPropertyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetCategoryProperty create(long categoryPropertyId) {
		AssetCategoryProperty assetCategoryProperty = new AssetCategoryPropertyImpl();

		assetCategoryProperty.setNew(true);
		assetCategoryProperty.setPrimaryKey(categoryPropertyId);

		return assetCategoryProperty;
	}

	public AssetCategoryProperty remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetCategoryProperty remove(long categoryPropertyId)
		throws NoSuchCategoryPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty assetCategoryProperty = (AssetCategoryProperty)session.get(AssetCategoryPropertyImpl.class,
					new Long(categoryPropertyId));

			if (assetCategoryProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						categoryPropertyId);
				}

				throw new NoSuchCategoryPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					categoryPropertyId);
			}

			return remove(assetCategoryProperty);
		}
		catch (NoSuchCategoryPropertyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetCategoryProperty remove(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		for (ModelListener<AssetCategoryProperty> listener : listeners) {
			listener.onBeforeRemove(assetCategoryProperty);
		}

		assetCategoryProperty = removeImpl(assetCategoryProperty);

		for (ModelListener<AssetCategoryProperty> listener : listeners) {
			listener.onAfterRemove(assetCategoryProperty);
		}

		return assetCategoryProperty;
	}

	protected AssetCategoryProperty removeImpl(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		assetCategoryProperty = toUnwrappedModel(assetCategoryProperty);

		Session session = null;

		try {
			session = openSession();

			if (assetCategoryProperty.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetCategoryProperty);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl = (AssetCategoryPropertyModelImpl)assetCategoryProperty;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CA_K,
			new Object[] {
				new Long(assetCategoryPropertyModelImpl.getOriginalCategoryId()),
				
			assetCategoryPropertyModelImpl.getOriginalKey()
			});

		EntityCacheUtil.removeResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey());

		return assetCategoryProperty;
	}

	public AssetCategoryProperty updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge) throws SystemException {
		assetCategoryProperty = toUnwrappedModel(assetCategoryProperty);

		boolean isNew = assetCategoryProperty.isNew();

		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl = (AssetCategoryPropertyModelImpl)assetCategoryProperty;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetCategoryProperty, merge);

			assetCategoryProperty.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

		if (!isNew &&
				((assetCategoryProperty.getCategoryId() != assetCategoryPropertyModelImpl.getOriginalCategoryId()) ||
				!Validator.equals(assetCategoryProperty.getKey(),
					assetCategoryPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CA_K,
				new Object[] {
					new Long(assetCategoryPropertyModelImpl.getOriginalCategoryId()),
					
				assetCategoryPropertyModelImpl.getOriginalKey()
				});
		}

		if (isNew ||
				((assetCategoryProperty.getCategoryId() != assetCategoryPropertyModelImpl.getOriginalCategoryId()) ||
				!Validator.equals(assetCategoryProperty.getKey(),
					assetCategoryPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
				new Object[] {
					new Long(assetCategoryProperty.getCategoryId()),
					
				assetCategoryProperty.getKey()
				}, assetCategoryProperty);
		}

		return assetCategoryProperty;
	}

	protected AssetCategoryProperty toUnwrappedModel(
		AssetCategoryProperty assetCategoryProperty) {
		if (assetCategoryProperty instanceof AssetCategoryPropertyImpl) {
			return assetCategoryProperty;
		}

		AssetCategoryPropertyImpl assetCategoryPropertyImpl = new AssetCategoryPropertyImpl();

		assetCategoryPropertyImpl.setNew(assetCategoryProperty.isNew());
		assetCategoryPropertyImpl.setPrimaryKey(assetCategoryProperty.getPrimaryKey());

		assetCategoryPropertyImpl.setCategoryPropertyId(assetCategoryProperty.getCategoryPropertyId());
		assetCategoryPropertyImpl.setCompanyId(assetCategoryProperty.getCompanyId());
		assetCategoryPropertyImpl.setUserId(assetCategoryProperty.getUserId());
		assetCategoryPropertyImpl.setUserName(assetCategoryProperty.getUserName());
		assetCategoryPropertyImpl.setCreateDate(assetCategoryProperty.getCreateDate());
		assetCategoryPropertyImpl.setModifiedDate(assetCategoryProperty.getModifiedDate());
		assetCategoryPropertyImpl.setCategoryId(assetCategoryProperty.getCategoryId());
		assetCategoryPropertyImpl.setKey(assetCategoryProperty.getKey());
		assetCategoryPropertyImpl.setValue(assetCategoryProperty.getValue());

		return assetCategoryPropertyImpl;
	}

	public AssetCategoryProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetCategoryProperty findByPrimaryKey(long categoryPropertyId)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(categoryPropertyId);

		if (assetCategoryProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					categoryPropertyId);
			}

			throw new NoSuchCategoryPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				categoryPropertyId);
		}

		return assetCategoryProperty;
	}

	public AssetCategoryProperty fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetCategoryProperty fetchByPrimaryKey(long categoryPropertyId)
		throws SystemException {
		AssetCategoryProperty assetCategoryProperty = (AssetCategoryProperty)EntityCacheUtil.getResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryPropertyImpl.class, categoryPropertyId, this);

		if (assetCategoryProperty == null) {
			Session session = null;

			try {
				session = openSession();

				assetCategoryProperty = (AssetCategoryProperty)session.get(AssetCategoryPropertyImpl.class,
						new Long(categoryPropertyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetCategoryProperty != null) {
					cacheResult(assetCategoryProperty);
				}

				closeSession(session);
			}
		}

		return assetCategoryProperty;
	}

	public List<AssetCategoryProperty> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategoryProperty findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		List<AssetCategoryProperty> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		int count = countByCompanyId(companyId);

		List<AssetCategoryProperty> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetCategoryProperty);

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = (AssetCategoryProperty)objArray[0];
			array[1] = (AssetCategoryProperty)objArray[1];
			array[2] = (AssetCategoryProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategoryProperty> findByCategoryId(long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CATEGORYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CATEGORYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategoryProperty> findByCategoryId(long categoryId,
		int start, int end) throws SystemException {
		return findByCategoryId(categoryId, start, end, null);
	}

	public List<AssetCategoryProperty> findByCategoryId(long categoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(categoryId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_CATEGORYID,
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

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_CATEGORYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategoryProperty findByCategoryId_First(long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		List<AssetCategoryProperty> list = findByCategoryId(categoryId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty findByCategoryId_Last(long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		int count = countByCategoryId(categoryId);

		List<AssetCategoryProperty> list = findByCategoryId(categoryId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("categoryId=");
			msg.append(categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

		int count = countByCategoryId(categoryId);

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

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetCategoryProperty);

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = (AssetCategoryProperty)objArray[0];
			array[1] = (AssetCategoryProperty)objArray[1];
			array[2] = (AssetCategoryProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetCategoryProperty> findByC_K(long companyId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_K, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetCategoryProperty> findByC_K(long companyId, String key,
		int start, int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	public List<AssetCategoryProperty> findByC_K(long companyId, String key,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				key,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_K,
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

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_K,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetCategoryProperty findByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		List<AssetCategoryProperty> list = findByC_K(companyId, key, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty findByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		int count = countByC_K(companyId, key);

		List<AssetCategoryProperty> list = findByC_K(companyId, key, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

		int count = countByC_K(companyId, key);

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

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (key != null) {
				qPos.add(key);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetCategoryProperty);

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = (AssetCategoryProperty)objArray[0];
			array[1] = (AssetCategoryProperty)objArray[1];
			array[2] = (AssetCategoryProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetCategoryProperty findByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCA_K(categoryId,
				key);

		if (assetCategoryProperty == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("categoryId=");
			msg.append(categoryId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryPropertyException(msg.toString());
		}

		return assetCategoryProperty;
	}

	public AssetCategoryProperty fetchByCA_K(long categoryId, String key)
		throws SystemException {
		return fetchByCA_K(categoryId, key, true);
	}

	public AssetCategoryProperty fetchByCA_K(long categoryId, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId), key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CA_K,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_CA_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_CA_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_CA_K_KEY_2);
					}
				}

				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				if (key != null) {
					qPos.add(key);
				}

				List<AssetCategoryProperty> list = q.list();

				result = list;

				AssetCategoryProperty assetCategoryProperty = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
						finderArgs, list);
				}
				else {
					assetCategoryProperty = list.get(0);

					cacheResult(assetCategoryProperty);

					if ((assetCategoryProperty.getCategoryId() != categoryId) ||
							(assetCategoryProperty.getKey() == null) ||
							!assetCategoryProperty.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
							finderArgs, assetCategoryProperty);
					}
				}

				return assetCategoryProperty;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
						finderArgs, new ArrayList<AssetCategoryProperty>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetCategoryProperty)result;
			}
		}
	}

	public List<AssetCategoryProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetCategoryProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetCategoryProperty> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ASSETCATEGORYPROPERTY.concat(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetCategoryProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetCategoryProperty>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetCategoryProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByCompanyId(
				companyId)) {
			remove(assetCategoryProperty);
		}
	}

	public void removeByCategoryId(long categoryId) throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByCategoryId(
				categoryId)) {
			remove(assetCategoryProperty);
		}
	}

	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByC_K(
				companyId, key)) {
			remove(assetCategoryProperty);
		}
	}

	public void removeByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByCA_K(categoryId, key);

		remove(assetCategoryProperty);
	}

	public void removeAll() throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findAll()) {
			remove(assetCategoryProperty);
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

				query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

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

	public int countByCategoryId(long categoryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CATEGORYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CATEGORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_K(long companyId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByCA_K(long categoryId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(categoryId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CA_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_CA_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_CA_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_CA_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CA_K,
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

				Query q = session.createQuery(_SQL_COUNT_ASSETCATEGORYPROPERTY);

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
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategoryProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategoryProperty>> listenersList = new ArrayList<ModelListener<AssetCategoryProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategoryProperty>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetLinkPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetLinkPersistence assetLinkPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY = "SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty";
	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE = "SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty WHERE ";
	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY = "SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty";
	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE = "SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetCategoryProperty.companyId = ?";
	private static final String _FINDER_COLUMN_CATEGORYID_CATEGORYID_2 = "assetCategoryProperty.categoryId = ?";
	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 = "assetCategoryProperty.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "assetCategoryProperty.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "assetCategoryProperty.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = ?)";
	private static final String _FINDER_COLUMN_CA_K_CATEGORYID_2 = "assetCategoryProperty.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_CA_K_KEY_1 = "assetCategoryProperty.key IS NULL";
	private static final String _FINDER_COLUMN_CA_K_KEY_2 = "assetCategoryProperty.key = ?";
	private static final String _FINDER_COLUMN_CA_K_KEY_3 = "(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetCategoryProperty.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetCategoryProperty exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetCategoryProperty exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPropertyPersistenceImpl.class);
}