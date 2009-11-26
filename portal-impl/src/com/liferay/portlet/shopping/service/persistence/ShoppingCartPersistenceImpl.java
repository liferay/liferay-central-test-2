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

import com.liferay.portlet.shopping.NoSuchCartException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.impl.ShoppingCartImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingCartPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCartPersistence
 * @see       ShoppingCartUtil
 * @generated
 */
public class ShoppingCartPersistenceImpl extends BasePersistenceImpl<ShoppingCart>
	implements ShoppingCartPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingCartImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ShoppingCart shoppingCart) {
		EntityCacheUtil.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(shoppingCart.getGroupId()),
				new Long(shoppingCart.getUserId())
			}, shoppingCart);
	}

	public void cacheResult(List<ShoppingCart> shoppingCarts) {
		for (ShoppingCart shoppingCart : shoppingCarts) {
			if (EntityCacheUtil.getResult(
						ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCartImpl.class, shoppingCart.getPrimaryKey(),
						this) == null) {
				cacheResult(shoppingCart);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingCartImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingCartImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ShoppingCart create(long cartId) {
		ShoppingCart shoppingCart = new ShoppingCartImpl();

		shoppingCart.setNew(true);
		shoppingCart.setPrimaryKey(cartId);

		return shoppingCart;
	}

	public ShoppingCart remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ShoppingCart remove(long cartId)
		throws NoSuchCartException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCart shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
					new Long(cartId));

			if (shoppingCart == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingCart exists with the primary key " +
						cartId);
				}

				throw new NoSuchCartException(
					"No ShoppingCart exists with the primary key " + cartId);
			}

			return remove(shoppingCart);
		}
		catch (NoSuchCartException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCart remove(ShoppingCart shoppingCart)
		throws SystemException {
		for (ModelListener<ShoppingCart> listener : listeners) {
			listener.onBeforeRemove(shoppingCart);
		}

		shoppingCart = removeImpl(shoppingCart);

		for (ModelListener<ShoppingCart> listener : listeners) {
			listener.onAfterRemove(shoppingCart);
		}

		return shoppingCart;
	}

	protected ShoppingCart removeImpl(ShoppingCart shoppingCart)
		throws SystemException {
		shoppingCart = toUnwrappedModel(shoppingCart);

		Session session = null;

		try {
			session = openSession();

			if (shoppingCart.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingCartImpl.class,
						shoppingCart.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingCart);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ShoppingCartModelImpl shoppingCartModelImpl = (ShoppingCartModelImpl)shoppingCart;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				new Long(shoppingCartModelImpl.getOriginalGroupId()),
				new Long(shoppingCartModelImpl.getOriginalUserId())
			});

		EntityCacheUtil.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey());

		return shoppingCart;
	}

	public ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge) throws SystemException {
		shoppingCart = toUnwrappedModel(shoppingCart);

		boolean isNew = shoppingCart.isNew();

		ShoppingCartModelImpl shoppingCartModelImpl = (ShoppingCartModelImpl)shoppingCart;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingCart, merge);

			shoppingCart.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart);

		if (!isNew &&
				((shoppingCart.getGroupId() != shoppingCartModelImpl.getOriginalGroupId()) ||
				(shoppingCart.getUserId() != shoppingCartModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(shoppingCartModelImpl.getOriginalGroupId()),
					new Long(shoppingCartModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((shoppingCart.getGroupId() != shoppingCartModelImpl.getOriginalGroupId()) ||
				(shoppingCart.getUserId() != shoppingCartModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					new Long(shoppingCart.getGroupId()),
					new Long(shoppingCart.getUserId())
				}, shoppingCart);
		}

		return shoppingCart;
	}

	protected ShoppingCart toUnwrappedModel(ShoppingCart shoppingCart) {
		if (shoppingCart instanceof ShoppingCartImpl) {
			return shoppingCart;
		}

		ShoppingCartImpl shoppingCartImpl = new ShoppingCartImpl();

		shoppingCartImpl.setNew(shoppingCart.isNew());
		shoppingCartImpl.setPrimaryKey(shoppingCart.getPrimaryKey());

		shoppingCartImpl.setCartId(shoppingCart.getCartId());
		shoppingCartImpl.setGroupId(shoppingCart.getGroupId());
		shoppingCartImpl.setCompanyId(shoppingCart.getCompanyId());
		shoppingCartImpl.setUserId(shoppingCart.getUserId());
		shoppingCartImpl.setUserName(shoppingCart.getUserName());
		shoppingCartImpl.setCreateDate(shoppingCart.getCreateDate());
		shoppingCartImpl.setModifiedDate(shoppingCart.getModifiedDate());
		shoppingCartImpl.setItemIds(shoppingCart.getItemIds());
		shoppingCartImpl.setCouponCodes(shoppingCart.getCouponCodes());
		shoppingCartImpl.setAltShipping(shoppingCart.getAltShipping());
		shoppingCartImpl.setInsure(shoppingCart.isInsure());

		return shoppingCartImpl;
	}

	public ShoppingCart findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingCart findByPrimaryKey(long cartId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByPrimaryKey(cartId);

		if (shoppingCart == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingCart exists with the primary key " +
					cartId);
			}

			throw new NoSuchCartException(
				"No ShoppingCart exists with the primary key " + cartId);
		}

		return shoppingCart;
	}

	public ShoppingCart fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingCart fetchByPrimaryKey(long cartId)
		throws SystemException {
		ShoppingCart shoppingCart = (ShoppingCart)EntityCacheUtil.getResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCartImpl.class, cartId, this);

		if (shoppingCart == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
						new Long(cartId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingCart != null) {
					cacheResult(shoppingCart);
				}

				closeSession(session);
			}
		}

		return shoppingCart;
	}

	public List<ShoppingCart> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.groupId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCart>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingCart> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<ShoppingCart> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.groupId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCart.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCart>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingCart findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List<ShoppingCart> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingCart> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart[] findByGroupId_PrevAndNext(long cartId, long groupId,
		OrderByComparator obc) throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append("shoppingCart.groupId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingCart.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart);

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = (ShoppingCart)objArray[0];
			array[1] = (ShoppingCart)objArray[1];
			array[2] = (ShoppingCart)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCart> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCart>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingCart> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<ShoppingCart> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.userId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCart.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCart>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingCart findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		List<ShoppingCart> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchCartException, SystemException {
		int count = countByUserId(userId);

		List<ShoppingCart> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCartException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCart[] findByUserId_PrevAndNext(long cartId, long userId,
		OrderByComparator obc) throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append("shoppingCart.userId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingCart.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCart);

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = (ShoppingCart)objArray[0];
			array[1] = (ShoppingCart)objArray[1];
			array[2] = (ShoppingCart)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCart findByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByG_U(groupId, userId);

		if (shoppingCart == null) {
			StringBundler msg = new StringBundler();

			msg.append("No ShoppingCart exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return shoppingCart;
	}

	public ShoppingCart fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	public ShoppingCart fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.groupId = ?");

				query.append(" AND ");

				query.append("shoppingCart.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<ShoppingCart> list = q.list();

				result = list;

				ShoppingCart shoppingCart = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					shoppingCart = list.get(0);

					cacheResult(shoppingCart);

					if ((shoppingCart.getGroupId() != groupId) ||
							(shoppingCart.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, shoppingCart);
					}
				}

				return shoppingCart;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, new ArrayList<ShoppingCart>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShoppingCart)result;
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

	public List<ShoppingCart> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingCart> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingCart> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_SHOPPINGCART);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCart.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCart>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingCart shoppingCart : findByGroupId(groupId)) {
			remove(shoppingCart);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (ShoppingCart shoppingCart : findByUserId(userId)) {
			remove(shoppingCart);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByG_U(groupId, userId);

		remove(shoppingCart);
	}

	public void removeAll() throws SystemException {
		for (ShoppingCart shoppingCart : findAll()) {
			remove(shoppingCart);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.groupId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

				query.append("shoppingCart.groupId = ?");

				query.append(" AND ");

				query.append("shoppingCart.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

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

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGCART);

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCart")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingCart>> listenersList = new ArrayList<ModelListener<ShoppingCart>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingCart>)Class.forName(
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
	private static final String _SQL_SELECT_SHOPPINGCART = "SELECT shoppingCart FROM ShoppingCart shoppingCart";
	private static final String _SQL_SELECT_SHOPPINGCART_WHERE = "SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE ";
	private static final String _SQL_COUNT_SHOPPINGCART = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart";
	private static final String _SQL_COUNT_SHOPPINGCART_WHERE = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart WHERE ";
	private static Log _log = LogFactoryUtil.getLog(ShoppingCartPersistenceImpl.class);
}