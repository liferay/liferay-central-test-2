/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingOrderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderPersistence extends BasePersistence {
	public ShoppingOrder create(String orderId) {
		ShoppingOrder shoppingOrder = new ShoppingOrder();
		shoppingOrder.setNew(true);
		shoppingOrder.setPrimaryKey(orderId);

		return shoppingOrder;
	}

	public ShoppingOrder remove(String orderId)
		throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(ShoppingOrder.class,
					orderId);

			if (shoppingOrder == null) {
				_log.warn("No ShoppingOrder exists with the primary key " +
					orderId.toString());
				throw new NoSuchOrderException(
					"No ShoppingOrder exists with the primary key " +
					orderId.toString());
			}

			session.delete(shoppingOrder);
			session.flush();

			return shoppingOrder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder update(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingOrder.isNew() || shoppingOrder.isModified()) {
				session = openSession();

				if (shoppingOrder.isNew()) {
					ShoppingOrder shoppingOrderModel = new ShoppingOrder();
					shoppingOrderModel.setOrderId(shoppingOrder.getOrderId());
					shoppingOrderModel.setGroupId(shoppingOrder.getGroupId());
					shoppingOrderModel.setCompanyId(shoppingOrder.getCompanyId());
					shoppingOrderModel.setUserId(shoppingOrder.getUserId());
					shoppingOrderModel.setUserName(shoppingOrder.getUserName());
					shoppingOrderModel.setCreateDate(shoppingOrder.getCreateDate());
					shoppingOrderModel.setModifiedDate(shoppingOrder.getModifiedDate());
					shoppingOrderModel.setTax(shoppingOrder.getTax());
					shoppingOrderModel.setShipping(shoppingOrder.getShipping());
					shoppingOrderModel.setAltShipping(shoppingOrder.getAltShipping());
					shoppingOrderModel.setRequiresShipping(shoppingOrder.getRequiresShipping());
					shoppingOrderModel.setInsure(shoppingOrder.getInsure());
					shoppingOrderModel.setInsurance(shoppingOrder.getInsurance());
					shoppingOrderModel.setCouponIds(shoppingOrder.getCouponIds());
					shoppingOrderModel.setCouponDiscount(shoppingOrder.getCouponDiscount());
					shoppingOrderModel.setBillingFirstName(shoppingOrder.getBillingFirstName());
					shoppingOrderModel.setBillingLastName(shoppingOrder.getBillingLastName());
					shoppingOrderModel.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
					shoppingOrderModel.setBillingCompany(shoppingOrder.getBillingCompany());
					shoppingOrderModel.setBillingStreet(shoppingOrder.getBillingStreet());
					shoppingOrderModel.setBillingCity(shoppingOrder.getBillingCity());
					shoppingOrderModel.setBillingState(shoppingOrder.getBillingState());
					shoppingOrderModel.setBillingZip(shoppingOrder.getBillingZip());
					shoppingOrderModel.setBillingCountry(shoppingOrder.getBillingCountry());
					shoppingOrderModel.setBillingPhone(shoppingOrder.getBillingPhone());
					shoppingOrderModel.setShipToBilling(shoppingOrder.getShipToBilling());
					shoppingOrderModel.setShippingFirstName(shoppingOrder.getShippingFirstName());
					shoppingOrderModel.setShippingLastName(shoppingOrder.getShippingLastName());
					shoppingOrderModel.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
					shoppingOrderModel.setShippingCompany(shoppingOrder.getShippingCompany());
					shoppingOrderModel.setShippingStreet(shoppingOrder.getShippingStreet());
					shoppingOrderModel.setShippingCity(shoppingOrder.getShippingCity());
					shoppingOrderModel.setShippingState(shoppingOrder.getShippingState());
					shoppingOrderModel.setShippingZip(shoppingOrder.getShippingZip());
					shoppingOrderModel.setShippingCountry(shoppingOrder.getShippingCountry());
					shoppingOrderModel.setShippingPhone(shoppingOrder.getShippingPhone());
					shoppingOrderModel.setCcName(shoppingOrder.getCcName());
					shoppingOrderModel.setCcType(shoppingOrder.getCcType());
					shoppingOrderModel.setCcNumber(shoppingOrder.getCcNumber());
					shoppingOrderModel.setCcExpMonth(shoppingOrder.getCcExpMonth());
					shoppingOrderModel.setCcExpYear(shoppingOrder.getCcExpYear());
					shoppingOrderModel.setCcVerNumber(shoppingOrder.getCcVerNumber());
					shoppingOrderModel.setComments(shoppingOrder.getComments());
					shoppingOrderModel.setPpTxnId(shoppingOrder.getPpTxnId());
					shoppingOrderModel.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
					shoppingOrderModel.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
					shoppingOrderModel.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
					shoppingOrderModel.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
					shoppingOrderModel.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
					shoppingOrderModel.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
					session.save(shoppingOrderModel);
					session.flush();
				}
				else {
					ShoppingOrder shoppingOrderModel = (ShoppingOrder)session.get(ShoppingOrder.class,
							shoppingOrder.getPrimaryKey());

					if (shoppingOrderModel != null) {
						shoppingOrderModel.setGroupId(shoppingOrder.getGroupId());
						shoppingOrderModel.setCompanyId(shoppingOrder.getCompanyId());
						shoppingOrderModel.setUserId(shoppingOrder.getUserId());
						shoppingOrderModel.setUserName(shoppingOrder.getUserName());
						shoppingOrderModel.setCreateDate(shoppingOrder.getCreateDate());
						shoppingOrderModel.setModifiedDate(shoppingOrder.getModifiedDate());
						shoppingOrderModel.setTax(shoppingOrder.getTax());
						shoppingOrderModel.setShipping(shoppingOrder.getShipping());
						shoppingOrderModel.setAltShipping(shoppingOrder.getAltShipping());
						shoppingOrderModel.setRequiresShipping(shoppingOrder.getRequiresShipping());
						shoppingOrderModel.setInsure(shoppingOrder.getInsure());
						shoppingOrderModel.setInsurance(shoppingOrder.getInsurance());
						shoppingOrderModel.setCouponIds(shoppingOrder.getCouponIds());
						shoppingOrderModel.setCouponDiscount(shoppingOrder.getCouponDiscount());
						shoppingOrderModel.setBillingFirstName(shoppingOrder.getBillingFirstName());
						shoppingOrderModel.setBillingLastName(shoppingOrder.getBillingLastName());
						shoppingOrderModel.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
						shoppingOrderModel.setBillingCompany(shoppingOrder.getBillingCompany());
						shoppingOrderModel.setBillingStreet(shoppingOrder.getBillingStreet());
						shoppingOrderModel.setBillingCity(shoppingOrder.getBillingCity());
						shoppingOrderModel.setBillingState(shoppingOrder.getBillingState());
						shoppingOrderModel.setBillingZip(shoppingOrder.getBillingZip());
						shoppingOrderModel.setBillingCountry(shoppingOrder.getBillingCountry());
						shoppingOrderModel.setBillingPhone(shoppingOrder.getBillingPhone());
						shoppingOrderModel.setShipToBilling(shoppingOrder.getShipToBilling());
						shoppingOrderModel.setShippingFirstName(shoppingOrder.getShippingFirstName());
						shoppingOrderModel.setShippingLastName(shoppingOrder.getShippingLastName());
						shoppingOrderModel.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
						shoppingOrderModel.setShippingCompany(shoppingOrder.getShippingCompany());
						shoppingOrderModel.setShippingStreet(shoppingOrder.getShippingStreet());
						shoppingOrderModel.setShippingCity(shoppingOrder.getShippingCity());
						shoppingOrderModel.setShippingState(shoppingOrder.getShippingState());
						shoppingOrderModel.setShippingZip(shoppingOrder.getShippingZip());
						shoppingOrderModel.setShippingCountry(shoppingOrder.getShippingCountry());
						shoppingOrderModel.setShippingPhone(shoppingOrder.getShippingPhone());
						shoppingOrderModel.setCcName(shoppingOrder.getCcName());
						shoppingOrderModel.setCcType(shoppingOrder.getCcType());
						shoppingOrderModel.setCcNumber(shoppingOrder.getCcNumber());
						shoppingOrderModel.setCcExpMonth(shoppingOrder.getCcExpMonth());
						shoppingOrderModel.setCcExpYear(shoppingOrder.getCcExpYear());
						shoppingOrderModel.setCcVerNumber(shoppingOrder.getCcVerNumber());
						shoppingOrderModel.setComments(shoppingOrder.getComments());
						shoppingOrderModel.setPpTxnId(shoppingOrder.getPpTxnId());
						shoppingOrderModel.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
						shoppingOrderModel.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
						shoppingOrderModel.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
						shoppingOrderModel.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
						shoppingOrderModel.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
						shoppingOrderModel.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
						session.flush();
					}
					else {
						shoppingOrderModel = new ShoppingOrder();
						shoppingOrderModel.setOrderId(shoppingOrder.getOrderId());
						shoppingOrderModel.setGroupId(shoppingOrder.getGroupId());
						shoppingOrderModel.setCompanyId(shoppingOrder.getCompanyId());
						shoppingOrderModel.setUserId(shoppingOrder.getUserId());
						shoppingOrderModel.setUserName(shoppingOrder.getUserName());
						shoppingOrderModel.setCreateDate(shoppingOrder.getCreateDate());
						shoppingOrderModel.setModifiedDate(shoppingOrder.getModifiedDate());
						shoppingOrderModel.setTax(shoppingOrder.getTax());
						shoppingOrderModel.setShipping(shoppingOrder.getShipping());
						shoppingOrderModel.setAltShipping(shoppingOrder.getAltShipping());
						shoppingOrderModel.setRequiresShipping(shoppingOrder.getRequiresShipping());
						shoppingOrderModel.setInsure(shoppingOrder.getInsure());
						shoppingOrderModel.setInsurance(shoppingOrder.getInsurance());
						shoppingOrderModel.setCouponIds(shoppingOrder.getCouponIds());
						shoppingOrderModel.setCouponDiscount(shoppingOrder.getCouponDiscount());
						shoppingOrderModel.setBillingFirstName(shoppingOrder.getBillingFirstName());
						shoppingOrderModel.setBillingLastName(shoppingOrder.getBillingLastName());
						shoppingOrderModel.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
						shoppingOrderModel.setBillingCompany(shoppingOrder.getBillingCompany());
						shoppingOrderModel.setBillingStreet(shoppingOrder.getBillingStreet());
						shoppingOrderModel.setBillingCity(shoppingOrder.getBillingCity());
						shoppingOrderModel.setBillingState(shoppingOrder.getBillingState());
						shoppingOrderModel.setBillingZip(shoppingOrder.getBillingZip());
						shoppingOrderModel.setBillingCountry(shoppingOrder.getBillingCountry());
						shoppingOrderModel.setBillingPhone(shoppingOrder.getBillingPhone());
						shoppingOrderModel.setShipToBilling(shoppingOrder.getShipToBilling());
						shoppingOrderModel.setShippingFirstName(shoppingOrder.getShippingFirstName());
						shoppingOrderModel.setShippingLastName(shoppingOrder.getShippingLastName());
						shoppingOrderModel.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
						shoppingOrderModel.setShippingCompany(shoppingOrder.getShippingCompany());
						shoppingOrderModel.setShippingStreet(shoppingOrder.getShippingStreet());
						shoppingOrderModel.setShippingCity(shoppingOrder.getShippingCity());
						shoppingOrderModel.setShippingState(shoppingOrder.getShippingState());
						shoppingOrderModel.setShippingZip(shoppingOrder.getShippingZip());
						shoppingOrderModel.setShippingCountry(shoppingOrder.getShippingCountry());
						shoppingOrderModel.setShippingPhone(shoppingOrder.getShippingPhone());
						shoppingOrderModel.setCcName(shoppingOrder.getCcName());
						shoppingOrderModel.setCcType(shoppingOrder.getCcType());
						shoppingOrderModel.setCcNumber(shoppingOrder.getCcNumber());
						shoppingOrderModel.setCcExpMonth(shoppingOrder.getCcExpMonth());
						shoppingOrderModel.setCcExpYear(shoppingOrder.getCcExpYear());
						shoppingOrderModel.setCcVerNumber(shoppingOrder.getCcVerNumber());
						shoppingOrderModel.setComments(shoppingOrder.getComments());
						shoppingOrderModel.setPpTxnId(shoppingOrder.getPpTxnId());
						shoppingOrderModel.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
						shoppingOrderModel.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
						shoppingOrderModel.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
						shoppingOrderModel.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
						shoppingOrderModel.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
						shoppingOrderModel.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
						session.save(shoppingOrderModel);
						session.flush();
					}
				}

				shoppingOrder.setNew(false);
				shoppingOrder.setModified(false);
			}

			return shoppingOrder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder findByPrimaryKey(String orderId)
		throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(ShoppingOrder.class,
					orderId);

			if (shoppingOrder == null) {
				_log.warn("No ShoppingOrder exists with the primary key " +
					orderId.toString());
				throw new NoSuchOrderException(
					"No ShoppingOrder exists with the primary key " +
					orderId.toString());
			}

			return shoppingOrder;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_U_PPPS(String groupId, String userId,
		String ppPaymentStatus) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			List list = q.list();

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_U_PPPS(String groupId, String userId,
		String ppPaymentStatus, int begin, int end) throws SystemException {
		return findByG_U_PPPS(groupId, userId, ppPaymentStatus, begin, end, null);
	}

	public List findByG_U_PPPS(String groupId, String userId,
		String ppPaymentStatus, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingOrder findByG_U_PPPS_First(String groupId, String userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		List list = findByG_U_PPPS(groupId, userId, ppPaymentStatus, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingOrder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += ", ";
			msg += "ppPaymentStatus=";
			msg += ppPaymentStatus;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrderException(msg);
		}
		else {
			return (ShoppingOrder)list.get(0);
		}
	}

	public ShoppingOrder findByG_U_PPPS_Last(String groupId, String userId,
		String ppPaymentStatus, OrderByComparator obc)
		throws NoSuchOrderException, SystemException {
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);
		List list = findByG_U_PPPS(groupId, userId, ppPaymentStatus, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingOrder exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += ", ";
			msg += "ppPaymentStatus=";
			msg += ppPaymentStatus;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrderException(msg);
		}
		else {
			return (ShoppingOrder)list.get(0);
		}
	}

	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(String orderId,
		String groupId, String userId, String ppPaymentStatus,
		OrderByComparator obc) throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingOrder);
			ShoppingOrder[] array = new ShoppingOrder[3];
			array[0] = (ShoppingOrder)objArray[0];
			array[1] = (ShoppingOrder)objArray[1];
			array[2] = (ShoppingOrder)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByG_U_PPPS(String groupId, String userId,
		String ppPaymentStatus) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingOrder shoppingOrder = (ShoppingOrder)itr.next();
				session.delete(shoppingOrder);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_U_PPPS(String groupId, String userId,
		String ppPaymentStatus) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingOrder WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus IS NULL");
			}
			else {
				query.append("ppPaymentStatus = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (ppPaymentStatus != null) {
				q.setString(queryPos++, ppPaymentStatus);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ShoppingOrderPersistence.class);
}