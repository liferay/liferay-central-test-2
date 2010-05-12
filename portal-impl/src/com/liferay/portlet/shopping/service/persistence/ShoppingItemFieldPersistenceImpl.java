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

package com.liferay.portlet.shopping.service.persistence;

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

import com.liferay.portlet.shopping.NoSuchItemFieldException;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingItemFieldPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemFieldPersistence
 * @see       ShoppingItemFieldUtil
 * @generated
 */
public class ShoppingItemFieldPersistenceImpl extends BasePersistenceImpl<ShoppingItemField>
	implements ShoppingItemFieldPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingItemFieldImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByItemId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ShoppingItemField shoppingItemField) {
		EntityCacheUtil.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField);
	}

	public void cacheResult(List<ShoppingItemField> shoppingItemFields) {
		for (ShoppingItemField shoppingItemField : shoppingItemFields) {
			if (EntityCacheUtil.getResult(
						ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemFieldImpl.class,
						shoppingItemField.getPrimaryKey(), this) == null) {
				cacheResult(shoppingItemField);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingItemFieldImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingItemFieldImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(ShoppingItemField shoppingItemField) {
		EntityCacheUtil.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());
	}

	public ShoppingItemField create(long itemFieldId) {
		ShoppingItemField shoppingItemField = new ShoppingItemFieldImpl();

		shoppingItemField.setNew(true);
		shoppingItemField.setPrimaryKey(itemFieldId);

		return shoppingItemField;
	}

	public ShoppingItemField remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ShoppingItemField remove(long itemFieldId)
		throws NoSuchItemFieldException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemField shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
					new Long(itemFieldId));

			if (shoppingItemField == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + itemFieldId);
				}

				throw new NoSuchItemFieldException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					itemFieldId);
			}

			return remove(shoppingItemField);
		}
		catch (NoSuchItemFieldException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItemField remove(ShoppingItemField shoppingItemField)
		throws SystemException {
		for (ModelListener<ShoppingItemField> listener : listeners) {
			listener.onBeforeRemove(shoppingItemField);
		}

		shoppingItemField = removeImpl(shoppingItemField);

		for (ModelListener<ShoppingItemField> listener : listeners) {
			listener.onAfterRemove(shoppingItemField);
		}

		return shoppingItemField;
	}

	protected ShoppingItemField removeImpl(ShoppingItemField shoppingItemField)
		throws SystemException {
		shoppingItemField = toUnwrappedModel(shoppingItemField);

		Session session = null;

		try {
			session = openSession();

			if (shoppingItemField.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingItemFieldImpl.class,
						shoppingItemField.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingItemField);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());

		return shoppingItemField;
	}

	public ShoppingItemField updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField,
		boolean merge) throws SystemException {
		shoppingItemField = toUnwrappedModel(shoppingItemField);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingItemField, merge);

			shoppingItemField.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField);

		return shoppingItemField;
	}

	protected ShoppingItemField toUnwrappedModel(
		ShoppingItemField shoppingItemField) {
		if (shoppingItemField instanceof ShoppingItemFieldImpl) {
			return shoppingItemField;
		}

		ShoppingItemFieldImpl shoppingItemFieldImpl = new ShoppingItemFieldImpl();

		shoppingItemFieldImpl.setNew(shoppingItemField.isNew());
		shoppingItemFieldImpl.setPrimaryKey(shoppingItemField.getPrimaryKey());

		shoppingItemFieldImpl.setItemFieldId(shoppingItemField.getItemFieldId());
		shoppingItemFieldImpl.setItemId(shoppingItemField.getItemId());
		shoppingItemFieldImpl.setName(shoppingItemField.getName());
		shoppingItemFieldImpl.setValues(shoppingItemField.getValues());
		shoppingItemFieldImpl.setDescription(shoppingItemField.getDescription());

		return shoppingItemFieldImpl;
	}

	public ShoppingItemField findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingItemField findByPrimaryKey(long itemFieldId)
		throws NoSuchItemFieldException, SystemException {
		ShoppingItemField shoppingItemField = fetchByPrimaryKey(itemFieldId);

		if (shoppingItemField == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + itemFieldId);
			}

			throw new NoSuchItemFieldException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				itemFieldId);
		}

		return shoppingItemField;
	}

	public ShoppingItemField fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingItemField fetchByPrimaryKey(long itemFieldId)
		throws SystemException {
		ShoppingItemField shoppingItemField = (ShoppingItemField)EntityCacheUtil.getResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemFieldImpl.class, itemFieldId, this);

		if (shoppingItemField == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
						new Long(itemFieldId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingItemField != null) {
					cacheResult(shoppingItemField);
				}

				closeSession(session);
			}
		}

		return shoppingItemField;
	}

	public List<ShoppingItemField> findByItemId(long itemId)
		throws SystemException {
		return findByItemId(itemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItemField> findByItemId(long itemId, int start, int end)
		throws SystemException {
		return findByItemId(itemId, start, end, null);
	}

	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(itemId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ITEMID,
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

				query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE);

				query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				list = (List<ShoppingItemField>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingItemField>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ITEMID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
		List<ShoppingItemField> list = findByItemId(itemId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("itemId=");
			msg.append(itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemFieldException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemField findByItemId_Last(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
		int count = countByItemId(itemId);

		List<ShoppingItemField> list = findByItemId(itemId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("itemId=");
			msg.append(itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemFieldException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemField[] findByItemId_PrevAndNext(long itemFieldId,
		long itemId, OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
		ShoppingItemField shoppingItemField = findByPrimaryKey(itemFieldId);

		Session session = null;

		try {
			session = openSession();

			ShoppingItemField[] array = new ShoppingItemFieldImpl[3];

			array[0] = getByItemId_PrevAndNext(session, shoppingItemField,
					itemId, orderByComparator, true);

			array[1] = shoppingItemField;

			array[2] = getByItemId_PrevAndNext(session, shoppingItemField,
					itemId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingItemField getByItemId_PrevAndNext(Session session,
		ShoppingItemField shoppingItemField, long itemId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE);

		query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

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
			query.append(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(itemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(shoppingItemField);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingItemField> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<ShoppingItemField> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItemField> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SHOPPINGITEMFIELD);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SHOPPINGITEMFIELD.concat(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingItemField>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByItemId(long itemId) throws SystemException {
		for (ShoppingItemField shoppingItemField : findByItemId(itemId)) {
			remove(shoppingItemField);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingItemField shoppingItemField : findAll()) {
			remove(shoppingItemField);
		}
	}

	public int countByItemId(long itemId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(itemId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ITEMID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SHOPPINGITEMFIELD_WHERE);

				query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ITEMID,
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

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEMFIELD);

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItemField")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingItemField>> listenersList = new ArrayList<ModelListener<ShoppingItemField>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingItemField>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = ShoppingCartPersistence.class)
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(type = ShoppingCategoryPersistence.class)
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(type = ShoppingCouponPersistence.class)
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(type = ShoppingItemPersistence.class)
	protected ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(type = ShoppingItemFieldPersistence.class)
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(type = ShoppingItemPricePersistence.class)
	protected ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(type = ShoppingOrderPersistence.class)
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(type = ShoppingOrderItemPersistence.class)
	protected ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD_WHERE = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD_WHERE = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "shoppingItemField.itemId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItemField.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItemField exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItemField exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceImpl.class);
}