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

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingItemPricePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPricePersistence
 * @see       ShoppingItemPriceUtil
 * @generated
 */
public class ShoppingItemPricePersistenceImpl extends BasePersistenceImpl<ShoppingItemPrice>
	implements ShoppingItemPricePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingItemPriceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ITEMID = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByItemId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ITEMID = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByItemId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ShoppingItemPrice shoppingItemPrice) {
		EntityCacheUtil.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey(),
			shoppingItemPrice);
	}

	public void cacheResult(List<ShoppingItemPrice> shoppingItemPrices) {
		for (ShoppingItemPrice shoppingItemPrice : shoppingItemPrices) {
			if (EntityCacheUtil.getResult(
						ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemPriceImpl.class,
						shoppingItemPrice.getPrimaryKey(), this) == null) {
				cacheResult(shoppingItemPrice);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingItemPriceImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingItemPriceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ShoppingItemPrice create(long itemPriceId) {
		ShoppingItemPrice shoppingItemPrice = new ShoppingItemPriceImpl();

		shoppingItemPrice.setNew(true);
		shoppingItemPrice.setPrimaryKey(itemPriceId);

		return shoppingItemPrice;
	}

	public ShoppingItemPrice remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ShoppingItemPrice remove(long itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
					new Long(itemPriceId));

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + itemPriceId);
				}

				throw new NoSuchItemPriceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					itemPriceId);
			}

			return remove(shoppingItemPrice);
		}
		catch (NoSuchItemPriceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItemPrice remove(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		for (ModelListener<ShoppingItemPrice> listener : listeners) {
			listener.onBeforeRemove(shoppingItemPrice);
		}

		shoppingItemPrice = removeImpl(shoppingItemPrice);

		for (ModelListener<ShoppingItemPrice> listener : listeners) {
			listener.onAfterRemove(shoppingItemPrice);
		}

		return shoppingItemPrice;
	}

	protected ShoppingItemPrice removeImpl(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		shoppingItemPrice = toUnwrappedModel(shoppingItemPrice);

		Session session = null;

		try {
			session = openSession();

			if (shoppingItemPrice.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingItemPriceImpl.class,
						shoppingItemPrice.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingItemPrice);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey());

		return shoppingItemPrice;
	}

	public ShoppingItemPrice updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge) throws SystemException {
		shoppingItemPrice = toUnwrappedModel(shoppingItemPrice);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingItemPrice, merge);

			shoppingItemPrice.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey(),
			shoppingItemPrice);

		return shoppingItemPrice;
	}

	protected ShoppingItemPrice toUnwrappedModel(
		ShoppingItemPrice shoppingItemPrice) {
		if (shoppingItemPrice instanceof ShoppingItemPriceImpl) {
			return shoppingItemPrice;
		}

		ShoppingItemPriceImpl shoppingItemPriceImpl = new ShoppingItemPriceImpl();

		shoppingItemPriceImpl.setNew(shoppingItemPrice.isNew());
		shoppingItemPriceImpl.setPrimaryKey(shoppingItemPrice.getPrimaryKey());

		shoppingItemPriceImpl.setItemPriceId(shoppingItemPrice.getItemPriceId());
		shoppingItemPriceImpl.setItemId(shoppingItemPrice.getItemId());
		shoppingItemPriceImpl.setMinQuantity(shoppingItemPrice.getMinQuantity());
		shoppingItemPriceImpl.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
		shoppingItemPriceImpl.setPrice(shoppingItemPrice.getPrice());
		shoppingItemPriceImpl.setDiscount(shoppingItemPrice.getDiscount());
		shoppingItemPriceImpl.setTaxable(shoppingItemPrice.isTaxable());
		shoppingItemPriceImpl.setShipping(shoppingItemPrice.getShipping());
		shoppingItemPriceImpl.setUseShippingFormula(shoppingItemPrice.isUseShippingFormula());
		shoppingItemPriceImpl.setStatus(shoppingItemPrice.getStatus());

		return shoppingItemPriceImpl;
	}

	public ShoppingItemPrice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingItemPrice findByPrimaryKey(long itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		ShoppingItemPrice shoppingItemPrice = fetchByPrimaryKey(itemPriceId);

		if (shoppingItemPrice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + itemPriceId);
			}

			throw new NoSuchItemPriceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				itemPriceId);
		}

		return shoppingItemPrice;
	}

	public ShoppingItemPrice fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingItemPrice fetchByPrimaryKey(long itemPriceId)
		throws SystemException {
		ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)EntityCacheUtil.getResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemPriceImpl.class, itemPriceId, this);

		if (shoppingItemPrice == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
						new Long(itemPriceId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingItemPrice != null) {
					cacheResult(shoppingItemPrice);
				}

				closeSession(session);
			}
		}

		return shoppingItemPrice;
	}

	public List<ShoppingItemPrice> findByItemId(long itemId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(itemId) };

		List<ShoppingItemPrice> list = (List<ShoppingItemPrice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ITEMID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE);

				query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

				query.append(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingItemPrice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ITEMID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingItemPrice> findByItemId(long itemId, int start, int end)
		throws SystemException {
		return findByItemId(itemId, start, end, null);
	}

	public List<ShoppingItemPrice> findByItemId(long itemId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(itemId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ShoppingItemPrice> list = (List<ShoppingItemPrice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ITEMID,
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

				query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE);

				query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				list = (List<ShoppingItemPrice>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingItemPrice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ITEMID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingItemPrice findByItemId_First(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemPriceException, SystemException {
		List<ShoppingItemPrice> list = findByItemId(itemId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("itemId=");
			msg.append(itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemPriceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemPrice findByItemId_Last(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemPriceException, SystemException {
		int count = countByItemId(itemId);

		List<ShoppingItemPrice> list = findByItemId(itemId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("itemId=");
			msg.append(itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemPriceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemPrice[] findByItemId_PrevAndNext(long itemPriceId,
		long itemId, OrderByComparator orderByComparator)
		throws NoSuchItemPriceException, SystemException {
		ShoppingItemPrice shoppingItemPrice = findByPrimaryKey(itemPriceId);

		int count = countByItemId(itemId);

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

			query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(itemId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, shoppingItemPrice);

			ShoppingItemPrice[] array = new ShoppingItemPriceImpl[3];

			array[0] = (ShoppingItemPrice)objArray[0];
			array[1] = (ShoppingItemPrice)objArray[1];
			array[2] = (ShoppingItemPrice)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingItemPrice> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItemPrice> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingItemPrice> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ShoppingItemPrice> list = (List<ShoppingItemPrice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SHOPPINGITEMPRICE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SHOPPINGITEMPRICE.concat(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingItemPrice>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByItemId(long itemId) throws SystemException {
		for (ShoppingItemPrice shoppingItemPrice : findByItemId(itemId)) {
			remove(shoppingItemPrice);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingItemPrice shoppingItemPrice : findAll()) {
			remove(shoppingItemPrice);
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

				query.append(_SQL_COUNT_SHOPPINGITEMPRICE_WHERE);

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

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEMPRICE);

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItemPrice")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingItemPrice>> listenersList = new ArrayList<ModelListener<ShoppingItemPrice>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingItemPrice>)Class.forName(
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
	private static final String _SQL_SELECT_SHOPPINGITEMPRICE = "SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice";
	private static final String _SQL_SELECT_SHOPPINGITEMPRICE_WHERE = "SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEMPRICE = "SELECT COUNT(shoppingItemPrice) FROM ShoppingItemPrice shoppingItemPrice";
	private static final String _SQL_COUNT_SHOPPINGITEMPRICE_WHERE = "SELECT COUNT(shoppingItemPrice) FROM ShoppingItemPrice shoppingItemPrice WHERE ";
	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "shoppingItemPrice.itemId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItemPrice.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItemPrice exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItemPrice exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemPricePersistenceImpl.class);
}