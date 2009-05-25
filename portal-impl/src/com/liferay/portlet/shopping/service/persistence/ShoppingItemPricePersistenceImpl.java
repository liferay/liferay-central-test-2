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

package com.liferay.portlet.shopping.service.persistence;

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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingItemPricePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemPricePersistenceImpl extends BasePersistenceImpl
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

	public ShoppingItemPrice remove(long itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
					new Long(itemPriceId));

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingItemPrice exists with the primary key " +
						itemPriceId);
				}

				throw new NoSuchItemPriceException(
					"No ShoppingItemPrice exists with the primary key " +
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

	/**
	 * @deprecated Use <code>update(ShoppingItemPrice shoppingItemPrice, boolean merge)</code>.
	 */
	public ShoppingItemPrice update(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingItemPrice shoppingItemPrice) method. Use update(ShoppingItemPrice shoppingItemPrice, boolean merge) instead.");
		}

		return update(shoppingItemPrice, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingItemPrice the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingItemPrice is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingItemPrice update(ShoppingItemPrice shoppingItemPrice,
		boolean merge) throws SystemException {
		boolean isNew = shoppingItemPrice.isNew();

		for (ModelListener<ShoppingItemPrice> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItemPrice);
			}
			else {
				listener.onBeforeUpdate(shoppingItemPrice);
			}
		}

		shoppingItemPrice = updateImpl(shoppingItemPrice, merge);

		for (ModelListener<ShoppingItemPrice> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingItemPrice);
			}
			else {
				listener.onAfterUpdate(shoppingItemPrice);
			}
		}

		return shoppingItemPrice;
	}

	public ShoppingItemPrice updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice,
		boolean merge) throws SystemException {
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

	public ShoppingItemPrice findByPrimaryKey(long itemPriceId)
		throws NoSuchItemPriceException, SystemException {
		ShoppingItemPrice shoppingItemPrice = fetchByPrimaryKey(itemPriceId);

		if (shoppingItemPrice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingItemPrice exists with the primary key " +
					itemPriceId);
			}

			throw new NoSuchItemPriceException(
				"No ShoppingItemPrice exists with the primary key " +
				itemPriceId);
		}

		return shoppingItemPrice;
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

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE ");

				query.append("shoppingItemPrice.itemId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("shoppingItemPrice.itemId ASC, ");
				query.append("shoppingItemPrice.itemPriceId ASC");

				Query q = session.createQuery(query.toString());

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
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(itemId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingItemPrice> list = (List<ShoppingItemPrice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ITEMID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE ");

				query.append("shoppingItemPrice.itemId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingItemPrice.");
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

					query.append("shoppingItemPrice.itemId ASC, ");
					query.append("shoppingItemPrice.itemPriceId ASC");
				}

				Query q = session.createQuery(query.toString());

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
		OrderByComparator obc) throws NoSuchItemPriceException, SystemException {
		List<ShoppingItemPrice> list = findByItemId(itemId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItemPrice exists with the key {");

			msg.append("itemId=" + itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemPriceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemPrice findByItemId_Last(long itemId,
		OrderByComparator obc) throws NoSuchItemPriceException, SystemException {
		int count = countByItemId(itemId);

		List<ShoppingItemPrice> list = findByItemId(itemId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItemPrice exists with the key {");

			msg.append("itemId=" + itemId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemPriceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemPrice[] findByItemId_PrevAndNext(long itemPriceId,
		long itemId, OrderByComparator obc)
		throws NoSuchItemPriceException, SystemException {
		ShoppingItemPrice shoppingItemPrice = findByPrimaryKey(itemPriceId);

		int count = countByItemId(itemId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE ");

			query.append("shoppingItemPrice.itemId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingItemPrice.");
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

				query.append("shoppingItemPrice.itemId ASC, ");
				query.append("shoppingItemPrice.itemPriceId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(itemId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItemPrice);

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

	public List<ShoppingItemPrice> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItemPrice> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingItemPrice> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingItemPrice> list = (List<ShoppingItemPrice>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingItemPrice.");
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

					query.append("shoppingItemPrice.itemId ASC, ");
					query.append("shoppingItemPrice.itemPriceId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(shoppingItemPrice) ");
				query.append("FROM ShoppingItemPrice shoppingItemPrice WHERE ");

				query.append("shoppingItemPrice.itemId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(shoppingItemPrice) FROM ShoppingItemPrice shoppingItemPrice");

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

	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemPricePersistenceImpl.class);
}