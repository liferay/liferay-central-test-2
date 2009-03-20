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
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.impl.ShoppingItemImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemPersistenceImpl extends BasePersistenceImpl
	implements ShoppingItemPersistence {
	public ShoppingItem create(long itemId) {
		ShoppingItem shoppingItem = new ShoppingItemImpl();

		shoppingItem.setNew(true);
		shoppingItem.setPrimaryKey(itemId);

		return shoppingItem;
	}

	public ShoppingItem remove(long itemId)
		throws NoSuchItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItem shoppingItem = (ShoppingItem)session.get(ShoppingItemImpl.class,
					new Long(itemId));

			if (shoppingItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingItem exists with the primary key " +
						itemId);
				}

				throw new NoSuchItemException(
					"No ShoppingItem exists with the primary key " + itemId);
			}

			return remove(shoppingItem);
		}
		catch (NoSuchItemException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem remove(ShoppingItem shoppingItem)
		throws SystemException {
		for (ModelListener<ShoppingItem> listener : listeners) {
			listener.onBeforeRemove(shoppingItem);
		}

		shoppingItem = removeImpl(shoppingItem);

		for (ModelListener<ShoppingItem> listener : listeners) {
			listener.onAfterRemove(shoppingItem);
		}

		return shoppingItem;
	}

	protected ShoppingItem removeImpl(ShoppingItem shoppingItem)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingItemImpl.class,
						shoppingItem.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingItem);

			session.flush();

			return shoppingItem;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingItem.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ShoppingItem shoppingItem, boolean merge)</code>.
	 */
	public ShoppingItem update(ShoppingItem shoppingItem)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingItem shoppingItem) method. Use update(ShoppingItem shoppingItem, boolean merge) instead.");
		}

		return update(shoppingItem, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingItem the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingItem is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingItem update(ShoppingItem shoppingItem, boolean merge)
		throws SystemException {
		boolean isNew = shoppingItem.isNew();

		for (ModelListener<ShoppingItem> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItem);
			}
			else {
				listener.onBeforeUpdate(shoppingItem);
			}
		}

		shoppingItem = updateImpl(shoppingItem, merge);

		for (ModelListener<ShoppingItem> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingItem);
			}
			else {
				listener.onAfterUpdate(shoppingItem);
			}
		}

		return shoppingItem;
	}

	public ShoppingItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingItem, merge);

			shoppingItem.setNew(false);

			return shoppingItem;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingItem.class.getName());
		}
	}

	public ShoppingItem findByPrimaryKey(long itemId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByPrimaryKey(itemId);

		if (shoppingItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingItem exists with the primary key " +
					itemId);
			}

			throw new NoSuchItemException(
				"No ShoppingItem exists with the primary key " + itemId);
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByPrimaryKey(long itemId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItem)session.get(ShoppingItemImpl.class,
				new Long(itemId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingItem> findByCategoryId(long categoryId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "findByCategoryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				List<ShoppingItem> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingItem>)result;
		}
	}

	public List<ShoppingItem> findByCategoryId(long categoryId, int start,
		int end) throws SystemException {
		return findByCategoryId(categoryId, start, end, null);
	}

	public List<ShoppingItem> findByCategoryId(long categoryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "findByCategoryId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(categoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("itemId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				List<ShoppingItem> list = (List<ShoppingItem>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingItem>)result;
		}
	}

	public ShoppingItem findByCategoryId_First(long categoryId,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		List<ShoppingItem> list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItem findByCategoryId_Last(long categoryId,
		OrderByComparator obc) throws NoSuchItemException, SystemException {
		int count = countByCategoryId(categoryId);

		List<ShoppingItem> list = findByCategoryId(categoryId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingItem[] findByCategoryId_PrevAndNext(long itemId,
		long categoryId, OrderByComparator obc)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByPrimaryKey(itemId);

		int count = countByCategoryId(categoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

			query.append("categoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("itemId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItem);

			ShoppingItem[] array = new ShoppingItemImpl[3];

			array[0] = (ShoppingItem)objArray[0];
			array[1] = (ShoppingItem)objArray[1];
			array[2] = (ShoppingItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingItem findBySmallImageId(long smallImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchBySmallImageId(smallImageId);

		if (shoppingItem == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("smallImageId=" + smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchBySmallImageId(long smallImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "fetchBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				List<ShoppingItem> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ShoppingItem> list = (List<ShoppingItem>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public ShoppingItem findByMediumImageId(long mediumImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByMediumImageId(mediumImageId);

		if (shoppingItem == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("mediumImageId=" + mediumImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByMediumImageId(long mediumImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "fetchByMediumImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(mediumImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("mediumImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mediumImageId);

				List<ShoppingItem> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ShoppingItem> list = (List<ShoppingItem>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public ShoppingItem findByLargeImageId(long largeImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByLargeImageId(largeImageId);

		if (shoppingItem == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("largeImageId=" + largeImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByLargeImageId(long largeImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "fetchByLargeImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("largeImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				List<ShoppingItem> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ShoppingItem> list = (List<ShoppingItem>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public ShoppingItem findByC_S(long companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = fetchByC_S(companyId, sku);

		if (shoppingItem == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingItem exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("sku=" + sku);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchItemException(msg.toString());
		}

		return shoppingItem;
	}

	public ShoppingItem fetchByC_S(long companyId, String sku)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "fetchByC_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), sku };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (sku == null) {
					query.append("sku IS NULL");
				}
				else {
					query.append("sku = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (sku != null) {
					qPos.add(sku);
				}

				List<ShoppingItem> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ShoppingItem> list = (List<ShoppingItem>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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

	public List<ShoppingItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingItem> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("itemId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<ShoppingItem> list = null;

				if (obc == null) {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingItem>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingItem>)result;
		}
	}

	public void removeByCategoryId(long categoryId) throws SystemException {
		for (ShoppingItem shoppingItem : findByCategoryId(categoryId)) {
			remove(shoppingItem);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findBySmallImageId(smallImageId);

		remove(shoppingItem);
	}

	public void removeByMediumImageId(long mediumImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByMediumImageId(mediumImageId);

		remove(shoppingItem);
	}

	public void removeByLargeImageId(long largeImageId)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByLargeImageId(largeImageId);

		remove(shoppingItem);
	}

	public void removeByC_S(long companyId, String sku)
		throws NoSuchItemException, SystemException {
		ShoppingItem shoppingItem = findByC_S(companyId, sku);

		remove(shoppingItem);
	}

	public void removeAll() throws SystemException {
		for (ShoppingItem shoppingItem : findAll()) {
			remove(shoppingItem);
		}
	}

	public int countByCategoryId(long categoryId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countByCategoryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(categoryId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countBySmallImageId(long smallImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countBySmallImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("smallImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByMediumImageId(long mediumImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countByMediumImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(mediumImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("mediumImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mediumImageId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByLargeImageId(long largeImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countByLargeImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(largeImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("largeImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_S(long companyId, String sku) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countByC_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), sku };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingItem WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (sku == null) {
					query.append("sku IS NULL");
				}
				else {
					query.append("sku = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (sku != null) {
					qPos.add(sku);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItem.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.shopping.model.ShoppingItem");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk) throws SystemException {
		return getShoppingItemPrices(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end) throws SystemException {
		return getShoppingItemPrices(pk, start, end, null);
	}

	public List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl.CACHE_ENABLED;

		String finderClassName = com.liferay.portlet.shopping.model.ShoppingItemPrice.class.getName();

		String finderMethodName = "getShoppingItemPrices";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETSHOPPINGITEMPRICES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("ShoppingItemPrice.itemId ASC, ");
					sb.append("ShoppingItemPrice.itemPriceId ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("ShoppingItemPrice",
					com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portlet.shopping.model.ShoppingItemPrice> list = (List<com.liferay.portlet.shopping.model.ShoppingItemPrice>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portlet.shopping.model.ShoppingItemPrice>)result;
		}
	}

	public int getShoppingItemPricesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl.CACHE_ENABLED;

		String finderClassName = com.liferay.portlet.shopping.model.ShoppingItemPrice.class.getName();

		String finderMethodName = "getShoppingItemPricesSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSHOPPINGITEMPRICESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsShoppingItemPrice(long pk, long shoppingItemPricePK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl.CACHE_ENABLED;

		String finderClassName = com.liferay.portlet.shopping.model.ShoppingItemPrice.class.getName();

		String finderMethodName = "containsShoppingItemPrices";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(shoppingItemPricePK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsShoppingItemPrice.contains(
							pk, shoppingItemPricePK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsShoppingItemPrices(long pk)
		throws SystemException {
		if (getShoppingItemPricesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingItem>> listenersList = new ArrayList<ModelListener<ShoppingItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingItem>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsShoppingItemPrice = new ContainsShoppingItemPrice(this);
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsShoppingItemPrice containsShoppingItemPrice;

	protected class ContainsShoppingItemPrice {
		protected ContainsShoppingItemPrice(
			ShoppingItemPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSHOPPINGITEMPRICE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long itemId, long itemPriceId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(itemId), new Long(itemPriceId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	private static final String _SQL_GETSHOPPINGITEMPRICES = "SELECT {ShoppingItemPrice.*} FROM ShoppingItemPrice INNER JOIN ShoppingItem ON (ShoppingItem.itemId = ShoppingItemPrice.itemId) WHERE (ShoppingItem.itemId = ?)";
	private static final String _SQL_GETSHOPPINGITEMPRICESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ?";
	private static final String _SQL_CONTAINSSHOPPINGITEMPRICE = "SELECT COUNT(*) AS COUNT_VALUE FROM ShoppingItemPrice WHERE itemId = ? AND itemPriceId = ?";
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemPersistenceImpl.class);
}