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

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.service.persistence.BatchSessionUtil;
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
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
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
					_log.warn(
						"No ShoppingItemField exists with the primary key " +
						itemFieldId);
				}

				throw new NoSuchItemFieldException(
					"No ShoppingItemField exists with the primary key " +
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
				_log.warn("No ShoppingItemField exists with the primary key " +
					itemFieldId);
			}

			throw new NoSuchItemFieldException(
				"No ShoppingItemField exists with the primary key " +
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
		Object[] finderArgs = new Object[] { new Long(itemId) };

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ITEMID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append(
					"SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ");

				query.append("shoppingItemField.itemId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("shoppingItemField.itemId ASC, ");
				query.append("shoppingItemField.name ASC");

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

	public List<ShoppingItemField> findByItemId(long itemId, int start, int end)
		throws SystemException {
		return findByItemId(itemId, start, end, null);
	}

	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(itemId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ITEMID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (6 > arrayCapacity) {
					arrayCapacity = 6;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ");

				query.append("shoppingItemField.itemId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingItemField.");
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

					query.append("shoppingItemField.itemId ASC, ");
					query.append("shoppingItemField.name ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ITEMID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator obc) throws NoSuchItemFieldException, SystemException {
		List<ShoppingItemField> list = findByItemId(itemId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No ShoppingItemField exists with the key {");
			msg.append("itemId=" + itemId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchItemFieldException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemField findByItemId_Last(long itemId,
		OrderByComparator obc) throws NoSuchItemFieldException, SystemException {
		int count = countByItemId(itemId);

		List<ShoppingItemField> list = findByItemId(itemId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No ShoppingItemField exists with the key {");
			msg.append("itemId=" + itemId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchItemFieldException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItemField[] findByItemId_PrevAndNext(long itemFieldId,
		long itemId, OrderByComparator obc)
		throws NoSuchItemFieldException, SystemException {
		ShoppingItemField shoppingItemField = findByPrimaryKey(itemFieldId);

		int count = countByItemId(itemId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (6 > arrayCapacity) {
				arrayCapacity = 6;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ");

			query.append("shoppingItemField.itemId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingItemField.");
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

				query.append("shoppingItemField.itemId ASC, ");
				query.append("shoppingItemField.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(itemId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItemField);

			ShoppingItemField[] array = new ShoppingItemFieldImpl[3];

			array[0] = (ShoppingItemField)objArray[0];
			array[1] = (ShoppingItemField)objArray[1];
			array[2] = (ShoppingItemField)objArray[2];

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

	public List<ShoppingItemField> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItemField> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (4 > arrayCapacity) {
					arrayCapacity = 4;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT shoppingItemField FROM ShoppingItemField shoppingItemField ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingItemField.");
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

					query.append("shoppingItemField.itemId ASC, ");
					query.append("shoppingItemField.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(shoppingItemField) ");
				query.append("FROM ShoppingItemField shoppingItemField WHERE ");

				query.append("shoppingItemField.itemId = ?");

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
						"SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField");

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

	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceImpl.class);
}