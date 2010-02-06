/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingOrderPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderPersistence
 * @see       ShoppingOrderUtil
 * @generated
 */
public class ShoppingOrderPersistenceImpl extends BasePersistenceImpl<ShoppingOrder>
	implements ShoppingOrderPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingOrderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_NUMBER = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByNumber",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_NUMBER = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByNumber",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_PPTXNID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByPPTxnId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_PPTXNID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPPTxnId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_PPPS = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U_PPPS = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_PPPS = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ShoppingOrder shoppingOrder) {
		EntityCacheUtil.putResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
			new Object[] { shoppingOrder.getNumber() }, shoppingOrder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
			new Object[] { shoppingOrder.getPpTxnId() }, shoppingOrder);
	}

	public void cacheResult(List<ShoppingOrder> shoppingOrders) {
		for (ShoppingOrder shoppingOrder : shoppingOrders) {
			if (EntityCacheUtil.getResult(
						ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
						this) == null) {
				cacheResult(shoppingOrder);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingOrderImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingOrderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ShoppingOrder create(long orderId) {
		ShoppingOrder shoppingOrder = new ShoppingOrderImpl();

		shoppingOrder.setNew(true);
		shoppingOrder.setPrimaryKey(orderId);

		return shoppingOrder;
	}

	public ShoppingOrder remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ShoppingOrder remove(long orderId)
		throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
					new Long(orderId));

			if (shoppingOrder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + orderId);
				}

				throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					orderId);
			}

			return remove(shoppingOrder);
		}
		catch (NoSuchOrderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder remove(ShoppingOrder shoppingOrder)
		throws SystemException {
		for (ModelListener<ShoppingOrder> listener : listeners) {
			listener.onBeforeRemove(shoppingOrder);
		}

		shoppingOrder = removeImpl(shoppingOrder);

		for (ModelListener<ShoppingOrder> listener : listeners) {
			listener.onAfterRemove(shoppingOrder);
		}

		return shoppingOrder;
	}

	protected ShoppingOrder removeImpl(ShoppingOrder shoppingOrder)
		throws SystemException {
		shoppingOrder = toUnwrappedModel(shoppingOrder);

		Session session = null;

		try {
			session = openSession();

			if (shoppingOrder.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingOrderImpl.class,
						shoppingOrder.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingOrder);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ShoppingOrderModelImpl shoppingOrderModelImpl = (ShoppingOrderModelImpl)shoppingOrder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NUMBER,
			new Object[] { shoppingOrderModelImpl.getOriginalNumber() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PPTXNID,
			new Object[] { shoppingOrderModelImpl.getOriginalPpTxnId() });

		EntityCacheUtil.removeResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey());

		return shoppingOrder;
	}

	public ShoppingOrder updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean merge) throws SystemException {
		shoppingOrder = toUnwrappedModel(shoppingOrder);

		boolean isNew = shoppingOrder.isNew();

		ShoppingOrderModelImpl shoppingOrderModelImpl = (ShoppingOrderModelImpl)shoppingOrder;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingOrder, merge);

			shoppingOrder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder);

		if (!isNew &&
				(!Validator.equals(shoppingOrder.getNumber(),
					shoppingOrderModelImpl.getOriginalNumber()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NUMBER,
				new Object[] { shoppingOrderModelImpl.getOriginalNumber() });
		}

		if (isNew ||
				(!Validator.equals(shoppingOrder.getNumber(),
					shoppingOrderModelImpl.getOriginalNumber()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
				new Object[] { shoppingOrder.getNumber() }, shoppingOrder);
		}

		if (!isNew &&
				(!Validator.equals(shoppingOrder.getPpTxnId(),
					shoppingOrderModelImpl.getOriginalPpTxnId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PPTXNID,
				new Object[] { shoppingOrderModelImpl.getOriginalPpTxnId() });
		}

		if (isNew ||
				(!Validator.equals(shoppingOrder.getPpTxnId(),
					shoppingOrderModelImpl.getOriginalPpTxnId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
				new Object[] { shoppingOrder.getPpTxnId() }, shoppingOrder);
		}

		return shoppingOrder;
	}

	protected ShoppingOrder toUnwrappedModel(ShoppingOrder shoppingOrder) {
		if (shoppingOrder instanceof ShoppingOrderImpl) {
			return shoppingOrder;
		}

		ShoppingOrderImpl shoppingOrderImpl = new ShoppingOrderImpl();

		shoppingOrderImpl.setNew(shoppingOrder.isNew());
		shoppingOrderImpl.setPrimaryKey(shoppingOrder.getPrimaryKey());

		shoppingOrderImpl.setOrderId(shoppingOrder.getOrderId());
		shoppingOrderImpl.setGroupId(shoppingOrder.getGroupId());
		shoppingOrderImpl.setCompanyId(shoppingOrder.getCompanyId());
		shoppingOrderImpl.setUserId(shoppingOrder.getUserId());
		shoppingOrderImpl.setUserName(shoppingOrder.getUserName());
		shoppingOrderImpl.setCreateDate(shoppingOrder.getCreateDate());
		shoppingOrderImpl.setModifiedDate(shoppingOrder.getModifiedDate());
		shoppingOrderImpl.setNumber(shoppingOrder.getNumber());
		shoppingOrderImpl.setTax(shoppingOrder.getTax());
		shoppingOrderImpl.setShipping(shoppingOrder.getShipping());
		shoppingOrderImpl.setAltShipping(shoppingOrder.getAltShipping());
		shoppingOrderImpl.setRequiresShipping(shoppingOrder.isRequiresShipping());
		shoppingOrderImpl.setInsure(shoppingOrder.isInsure());
		shoppingOrderImpl.setInsurance(shoppingOrder.getInsurance());
		shoppingOrderImpl.setCouponCodes(shoppingOrder.getCouponCodes());
		shoppingOrderImpl.setCouponDiscount(shoppingOrder.getCouponDiscount());
		shoppingOrderImpl.setBillingFirstName(shoppingOrder.getBillingFirstName());
		shoppingOrderImpl.setBillingLastName(shoppingOrder.getBillingLastName());
		shoppingOrderImpl.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
		shoppingOrderImpl.setBillingCompany(shoppingOrder.getBillingCompany());
		shoppingOrderImpl.setBillingStreet(shoppingOrder.getBillingStreet());
		shoppingOrderImpl.setBillingCity(shoppingOrder.getBillingCity());
		shoppingOrderImpl.setBillingState(shoppingOrder.getBillingState());
		shoppingOrderImpl.setBillingZip(shoppingOrder.getBillingZip());
		shoppingOrderImpl.setBillingCountry(shoppingOrder.getBillingCountry());
		shoppingOrderImpl.setBillingPhone(shoppingOrder.getBillingPhone());
		shoppingOrderImpl.setShipToBilling(shoppingOrder.isShipToBilling());
		shoppingOrderImpl.setShippingFirstName(shoppingOrder.getShippingFirstName());
		shoppingOrderImpl.setShippingLastName(shoppingOrder.getShippingLastName());
		shoppingOrderImpl.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
		shoppingOrderImpl.setShippingCompany(shoppingOrder.getShippingCompany());
		shoppingOrderImpl.setShippingStreet(shoppingOrder.getShippingStreet());
		shoppingOrderImpl.setShippingCity(shoppingOrder.getShippingCity());
		shoppingOrderImpl.setShippingState(shoppingOrder.getShippingState());
		shoppingOrderImpl.setShippingZip(shoppingOrder.getShippingZip());
		shoppingOrderImpl.setShippingCountry(shoppingOrder.getShippingCountry());
		shoppingOrderImpl.setShippingPhone(shoppingOrder.getShippingPhone());
		shoppingOrderImpl.setCcName(shoppingOrder.getCcName());
		shoppingOrderImpl.setCcType(shoppingOrder.getCcType());
		shoppingOrderImpl.setCcNumber(shoppingOrder.getCcNumber());
		shoppingOrderImpl.setCcExpMonth(shoppingOrder.getCcExpMonth());
		shoppingOrderImpl.setCcExpYear(shoppingOrder.getCcExpYear());
		shoppingOrderImpl.setCcVerNumber(shoppingOrder.getCcVerNumber());
		shoppingOrderImpl.setComments(shoppingOrder.getComments());
		shoppingOrderImpl.setPpTxnId(shoppingOrder.getPpTxnId());
		shoppingOrderImpl.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
		shoppingOrderImpl.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
		shoppingOrderImpl.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
		shoppingOrderImpl.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
		shoppingOrderImpl.setSendOrderEmail(shoppingOrder.isSendOrderEmail());
		shoppingOrderImpl.setSendShippingEmail(shoppingOrder.isSendShippingEmail());

		return shoppingOrderImpl;
	}

	public ShoppingOrder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingOrder findByPrimaryKey(long orderId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByPrimaryKey(orderId);

		if (shoppingOrder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + orderId);
			}

			throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				orderId);
		}

		return shoppingOrder;
	}

	public ShoppingOrder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ShoppingOrder fetchByPrimaryKey(long orderId)
		throws SystemException {
		ShoppingOrder shoppingOrder = (ShoppingOrder)EntityCacheUtil.getResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderImpl.class, orderId, this);

		if (shoppingOrder == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
						new Long(orderId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingOrder != null) {
					cacheResult(shoppingOrder);
				}

				closeSession(session);
			}
		}

		return shoppingOrder;
	}

	public List<ShoppingOrder> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingOrder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<ShoppingOrder> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingOrder findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		List<ShoppingOrder> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingOrder> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder[] findByGroupId_PrevAndNext(long orderId,
		long groupId, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrder);

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = (ShoppingOrder)objArray[0];
			array[1] = (ShoppingOrder)objArray[1];
			array[2] = (ShoppingOrder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder findByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByNumber(number);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("number=");
			msg.append(number);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	public ShoppingOrder fetchByNumber(String number) throws SystemException {
		return fetchByNumber(number, true);
	}

	public ShoppingOrder fetchByNumber(String number, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { number };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NUMBER,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				if (number == null) {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_1);
				}
				else {
					if (number.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
					}
					else {
						query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
					}
				}

				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (number != null) {
					qPos.add(number);
				}

				List<ShoppingOrder> list = q.list();

				result = list;

				ShoppingOrder shoppingOrder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
						finderArgs, list);
				}
				else {
					shoppingOrder = list.get(0);

					cacheResult(shoppingOrder);

					if ((shoppingOrder.getNumber() == null) ||
							!shoppingOrder.getNumber().equals(number)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
							finderArgs, shoppingOrder);
					}
				}

				return shoppingOrder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
						finderArgs, new ArrayList<ShoppingOrder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShoppingOrder)result;
			}
		}
	}

	public ShoppingOrder findByPPTxnId(String ppTxnId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByPPTxnId(ppTxnId);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ppTxnId=");
			msg.append(ppTxnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	public ShoppingOrder fetchByPPTxnId(String ppTxnId)
		throws SystemException {
		return fetchByPPTxnId(ppTxnId, true);
	}

	public ShoppingOrder fetchByPPTxnId(String ppTxnId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { ppTxnId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PPTXNID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				if (ppTxnId == null) {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_1);
				}
				else {
					if (ppTxnId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
					}
					else {
						query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
					}
				}

				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ppTxnId != null) {
					qPos.add(ppTxnId);
				}

				List<ShoppingOrder> list = q.list();

				result = list;

				ShoppingOrder shoppingOrder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
						finderArgs, list);
				}
				else {
					shoppingOrder = list.get(0);

					cacheResult(shoppingOrder);

					if ((shoppingOrder.getPpTxnId() == null) ||
							!shoppingOrder.getPpTxnId().equals(ppTxnId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
							finderArgs, shoppingOrder);
					}
				}

				return shoppingOrder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
						finderArgs, new ArrayList<ShoppingOrder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShoppingOrder)result;
			}
		}
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus
			};

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_PPPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

				if (ppPaymentStatus == null) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
				}
				else {
					if (ppPaymentStatus.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
					}
				}

				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (ppPaymentStatus != null) {
					qPos.add(ppPaymentStatus);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_PPPS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end) throws SystemException {
		return findByG_U_PPPS(groupId, userId, ppPaymentStatus, start, end, null);
	}

	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U_PPPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

				if (ppPaymentStatus == null) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
				}
				else {
					if (ppPaymentStatus.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (ppPaymentStatus != null) {
					qPos.add(ppPaymentStatus);
				}

				list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U_PPPS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingOrder findByG_U_PPPS_First(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", ppPaymentStatus=");
			msg.append(ppPaymentStatus);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder findByG_U_PPPS_Last(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", ppPaymentStatus=");
			msg.append(ppPaymentStatus);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

			if (ppPaymentStatus == null) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
			}
			else {
				if (ppPaymentStatus.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (ppPaymentStatus != null) {
				qPos.add(ppPaymentStatus);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrder);

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = (ShoppingOrder)objArray[0];
			array[1] = (ShoppingOrder)objArray[1];
			array[2] = (ShoppingOrder)objArray[2];

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

	public List<ShoppingOrder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingOrder> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingOrder> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_SHOPPINGORDER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SHOPPINGORDER.concat(ShoppingOrderModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingOrder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByGroupId(groupId)) {
			remove(shoppingOrder);
		}
	}

	public void removeByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByNumber(number);

		remove(shoppingOrder);
	}

	public void removeByPPTxnId(String ppTxnId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPPTxnId(ppTxnId);

		remove(shoppingOrder);
	}

	public void removeByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByG_U_PPPS(groupId, userId,
				ppPaymentStatus)) {
			remove(shoppingOrder);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingOrder shoppingOrder : findAll()) {
			remove(shoppingOrder);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByNumber(String number) throws SystemException {
		Object[] finderArgs = new Object[] { number };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NUMBER,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

				if (number == null) {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_1);
				}
				else {
					if (number.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
					}
					else {
						query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (number != null) {
					qPos.add(number);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NUMBER,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByPPTxnId(String ppTxnId) throws SystemException {
		Object[] finderArgs = new Object[] { ppTxnId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PPTXNID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

				if (ppTxnId == null) {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_1);
				}
				else {
					if (ppTxnId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
					}
					else {
						query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ppTxnId != null) {
					qPos.add(ppTxnId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PPTXNID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_PPPS(long groupId, long userId, String ppPaymentStatus)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				ppPaymentStatus
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_PPPS,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

				query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

				if (ppPaymentStatus == null) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
				}
				else {
					if (ppPaymentStatus.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (ppPaymentStatus != null) {
					qPos.add(ppPaymentStatus);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_PPPS,
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

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGORDER);

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
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingOrder>> listenersList = new ArrayList<ModelListener<ShoppingOrder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingOrder>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	private static final String _SQL_SELECT_SHOPPINGORDER = "SELECT shoppingOrder FROM ShoppingOrder shoppingOrder";
	private static final String _SQL_SELECT_SHOPPINGORDER_WHERE = "SELECT shoppingOrder FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _SQL_COUNT_SHOPPINGORDER = "SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder";
	private static final String _SQL_COUNT_SHOPPINGORDER_WHERE = "SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "shoppingOrder.groupId = ?";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_1 = "shoppingOrder.number IS NULL";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_2 = "shoppingOrder.number = ?";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_3 = "(shoppingOrder.number IS NULL OR shoppingOrder.number = ?)";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_1 = "shoppingOrder.ppTxnId IS NULL";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_2 = "shoppingOrder.ppTxnId = ?";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_3 = "(shoppingOrder.ppTxnId IS NULL OR shoppingOrder.ppTxnId = ?)";
	private static final String _FINDER_COLUMN_G_U_PPPS_GROUPID_2 = "shoppingOrder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_PPPS_USERID_2 = "shoppingOrder.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1 = "shoppingOrder.ppPaymentStatus IS NULL";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2 = "shoppingOrder.ppPaymentStatus = ?";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3 = "(shoppingOrder.ppPaymentStatus IS NULL OR shoppingOrder.ppPaymentStatus = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingOrder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingOrder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingOrder exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderPersistenceImpl.class);
}