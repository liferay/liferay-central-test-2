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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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

import com.liferay.portlet.shopping.NoSuchItemFieldException;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingItemFieldPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemFieldPersistenceImpl extends BasePersistenceImpl
	implements ShoppingItemFieldPersistence {
	public ShoppingItemField create(long itemFieldId) {
		ShoppingItemField shoppingItemField = new ShoppingItemFieldImpl();

		shoppingItemField.setNew(true);
		shoppingItemField.setPrimaryKey(itemFieldId);

		return shoppingItemField;
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
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingItemFieldImpl.class,
						shoppingItemField.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingItemField);

			session.flush();

			return shoppingItemField;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingItemField.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ShoppingItemField shoppingItemField, boolean merge)</code>.
	 */
	public ShoppingItemField update(ShoppingItemField shoppingItemField)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingItemField shoppingItemField) method. Use update(ShoppingItemField shoppingItemField, boolean merge) instead.");
		}

		return update(shoppingItemField, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingItemField the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingItemField is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingItemField update(ShoppingItemField shoppingItemField,
		boolean merge) throws SystemException {
		boolean isNew = shoppingItemField.isNew();

		for (ModelListener<ShoppingItemField> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingItemField);
			}
			else {
				listener.onBeforeUpdate(shoppingItemField);
			}
		}

		shoppingItemField = updateImpl(shoppingItemField, merge);

		for (ModelListener<ShoppingItemField> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingItemField);
			}
			else {
				listener.onAfterUpdate(shoppingItemField);
			}
		}

		return shoppingItemField;
	}

	public ShoppingItemField updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingItemField, merge);

			shoppingItemField.setNew(false);

			return shoppingItemField;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingItemField.class.getName());
		}
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

	public ShoppingItemField fetchByPrimaryKey(long itemFieldId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
				new Long(itemFieldId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingItemField> findByItemId(long itemId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemFieldModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItemField.class.getName();
		String finderMethodName = "findByItemId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(itemId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingItemField WHERE ");

				query.append("itemId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("itemId ASC, ");
				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				List<ShoppingItemField> list = q.list();

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
			return (List<ShoppingItemField>)result;
		}
	}

	public List<ShoppingItemField> findByItemId(long itemId, int start, int end)
		throws SystemException {
		return findByItemId(itemId, start, end, null);
	}

	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingItemFieldModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItemField.class.getName();
		String finderMethodName = "findByItemId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(itemId),
				
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
					"FROM com.liferay.portlet.shopping.model.ShoppingItemField WHERE ");

				query.append("itemId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("itemId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				List<ShoppingItemField> list = (List<ShoppingItemField>)QueryUtil.list(q,
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
			return (List<ShoppingItemField>)result;
		}
	}

	public ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator obc) throws NoSuchItemFieldException, SystemException {
		List<ShoppingItemField> list = findByItemId(itemId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

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

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingItemField WHERE ");

			query.append("itemId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("itemId ASC, ");
				query.append("name ASC");
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
		boolean finderClassNameCacheEnabled = ShoppingItemFieldModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItemField.class.getName();
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
					"FROM com.liferay.portlet.shopping.model.ShoppingItemField ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("itemId ASC, ");
					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				List<ShoppingItemField> list = null;

				if (obc == null) {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end);
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
			return (List<ShoppingItemField>)result;
		}
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
		boolean finderClassNameCacheEnabled = ShoppingItemFieldModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItemField.class.getName();
		String finderMethodName = "countByItemId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(itemId) };

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
					"FROM com.liferay.portlet.shopping.model.ShoppingItemField WHERE ");

				query.append("itemId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

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
		boolean finderClassNameCacheEnabled = ShoppingItemFieldModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingItemField.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.shopping.model.ShoppingItemField");

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
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceImpl.class);
}