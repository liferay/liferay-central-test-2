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
	public com.liferay.portlet.shopping.model.ShoppingOrder create(
		String orderId) {
		ShoppingOrderHBM shoppingOrderHBM = new ShoppingOrderHBM();
		shoppingOrderHBM.setNew(true);
		shoppingOrderHBM.setPrimaryKey(orderId);

		return ShoppingOrderHBMUtil.model(shoppingOrderHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder remove(
		String orderId) throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)session.get(ShoppingOrderHBM.class,
					orderId);

			if (shoppingOrderHBM == null) {
				_log.warn("No ShoppingOrder exists with the primary key " +
					orderId.toString());
				throw new NoSuchOrderException(
					"No ShoppingOrder exists with the primary key " +
					orderId.toString());
			}

			session.delete(shoppingOrderHBM);
			session.flush();

			return ShoppingOrderHBMUtil.model(shoppingOrderHBM);
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
					ShoppingOrderHBM shoppingOrderHBM = new ShoppingOrderHBM();
					shoppingOrderHBM.setOrderId(shoppingOrder.getOrderId());
					shoppingOrderHBM.setGroupId(shoppingOrder.getGroupId());
					shoppingOrderHBM.setCompanyId(shoppingOrder.getCompanyId());
					shoppingOrderHBM.setUserId(shoppingOrder.getUserId());
					shoppingOrderHBM.setUserName(shoppingOrder.getUserName());
					shoppingOrderHBM.setCreateDate(shoppingOrder.getCreateDate());
					shoppingOrderHBM.setModifiedDate(shoppingOrder.getModifiedDate());
					shoppingOrderHBM.setTax(shoppingOrder.getTax());
					shoppingOrderHBM.setShipping(shoppingOrder.getShipping());
					shoppingOrderHBM.setAltShipping(shoppingOrder.getAltShipping());
					shoppingOrderHBM.setRequiresShipping(shoppingOrder.getRequiresShipping());
					shoppingOrderHBM.setInsure(shoppingOrder.getInsure());
					shoppingOrderHBM.setInsurance(shoppingOrder.getInsurance());
					shoppingOrderHBM.setCouponIds(shoppingOrder.getCouponIds());
					shoppingOrderHBM.setCouponDiscount(shoppingOrder.getCouponDiscount());
					shoppingOrderHBM.setBillingFirstName(shoppingOrder.getBillingFirstName());
					shoppingOrderHBM.setBillingLastName(shoppingOrder.getBillingLastName());
					shoppingOrderHBM.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
					shoppingOrderHBM.setBillingCompany(shoppingOrder.getBillingCompany());
					shoppingOrderHBM.setBillingStreet(shoppingOrder.getBillingStreet());
					shoppingOrderHBM.setBillingCity(shoppingOrder.getBillingCity());
					shoppingOrderHBM.setBillingState(shoppingOrder.getBillingState());
					shoppingOrderHBM.setBillingZip(shoppingOrder.getBillingZip());
					shoppingOrderHBM.setBillingCountry(shoppingOrder.getBillingCountry());
					shoppingOrderHBM.setBillingPhone(shoppingOrder.getBillingPhone());
					shoppingOrderHBM.setShipToBilling(shoppingOrder.getShipToBilling());
					shoppingOrderHBM.setShippingFirstName(shoppingOrder.getShippingFirstName());
					shoppingOrderHBM.setShippingLastName(shoppingOrder.getShippingLastName());
					shoppingOrderHBM.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
					shoppingOrderHBM.setShippingCompany(shoppingOrder.getShippingCompany());
					shoppingOrderHBM.setShippingStreet(shoppingOrder.getShippingStreet());
					shoppingOrderHBM.setShippingCity(shoppingOrder.getShippingCity());
					shoppingOrderHBM.setShippingState(shoppingOrder.getShippingState());
					shoppingOrderHBM.setShippingZip(shoppingOrder.getShippingZip());
					shoppingOrderHBM.setShippingCountry(shoppingOrder.getShippingCountry());
					shoppingOrderHBM.setShippingPhone(shoppingOrder.getShippingPhone());
					shoppingOrderHBM.setCcName(shoppingOrder.getCcName());
					shoppingOrderHBM.setCcType(shoppingOrder.getCcType());
					shoppingOrderHBM.setCcNumber(shoppingOrder.getCcNumber());
					shoppingOrderHBM.setCcExpMonth(shoppingOrder.getCcExpMonth());
					shoppingOrderHBM.setCcExpYear(shoppingOrder.getCcExpYear());
					shoppingOrderHBM.setCcVerNumber(shoppingOrder.getCcVerNumber());
					shoppingOrderHBM.setComments(shoppingOrder.getComments());
					shoppingOrderHBM.setPpTxnId(shoppingOrder.getPpTxnId());
					shoppingOrderHBM.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
					shoppingOrderHBM.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
					shoppingOrderHBM.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
					shoppingOrderHBM.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
					shoppingOrderHBM.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
					shoppingOrderHBM.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
					session.save(shoppingOrderHBM);
					session.flush();
				}
				else {
					ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)session.get(ShoppingOrderHBM.class,
							shoppingOrder.getPrimaryKey());

					if (shoppingOrderHBM != null) {
						shoppingOrderHBM.setGroupId(shoppingOrder.getGroupId());
						shoppingOrderHBM.setCompanyId(shoppingOrder.getCompanyId());
						shoppingOrderHBM.setUserId(shoppingOrder.getUserId());
						shoppingOrderHBM.setUserName(shoppingOrder.getUserName());
						shoppingOrderHBM.setCreateDate(shoppingOrder.getCreateDate());
						shoppingOrderHBM.setModifiedDate(shoppingOrder.getModifiedDate());
						shoppingOrderHBM.setTax(shoppingOrder.getTax());
						shoppingOrderHBM.setShipping(shoppingOrder.getShipping());
						shoppingOrderHBM.setAltShipping(shoppingOrder.getAltShipping());
						shoppingOrderHBM.setRequiresShipping(shoppingOrder.getRequiresShipping());
						shoppingOrderHBM.setInsure(shoppingOrder.getInsure());
						shoppingOrderHBM.setInsurance(shoppingOrder.getInsurance());
						shoppingOrderHBM.setCouponIds(shoppingOrder.getCouponIds());
						shoppingOrderHBM.setCouponDiscount(shoppingOrder.getCouponDiscount());
						shoppingOrderHBM.setBillingFirstName(shoppingOrder.getBillingFirstName());
						shoppingOrderHBM.setBillingLastName(shoppingOrder.getBillingLastName());
						shoppingOrderHBM.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
						shoppingOrderHBM.setBillingCompany(shoppingOrder.getBillingCompany());
						shoppingOrderHBM.setBillingStreet(shoppingOrder.getBillingStreet());
						shoppingOrderHBM.setBillingCity(shoppingOrder.getBillingCity());
						shoppingOrderHBM.setBillingState(shoppingOrder.getBillingState());
						shoppingOrderHBM.setBillingZip(shoppingOrder.getBillingZip());
						shoppingOrderHBM.setBillingCountry(shoppingOrder.getBillingCountry());
						shoppingOrderHBM.setBillingPhone(shoppingOrder.getBillingPhone());
						shoppingOrderHBM.setShipToBilling(shoppingOrder.getShipToBilling());
						shoppingOrderHBM.setShippingFirstName(shoppingOrder.getShippingFirstName());
						shoppingOrderHBM.setShippingLastName(shoppingOrder.getShippingLastName());
						shoppingOrderHBM.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
						shoppingOrderHBM.setShippingCompany(shoppingOrder.getShippingCompany());
						shoppingOrderHBM.setShippingStreet(shoppingOrder.getShippingStreet());
						shoppingOrderHBM.setShippingCity(shoppingOrder.getShippingCity());
						shoppingOrderHBM.setShippingState(shoppingOrder.getShippingState());
						shoppingOrderHBM.setShippingZip(shoppingOrder.getShippingZip());
						shoppingOrderHBM.setShippingCountry(shoppingOrder.getShippingCountry());
						shoppingOrderHBM.setShippingPhone(shoppingOrder.getShippingPhone());
						shoppingOrderHBM.setCcName(shoppingOrder.getCcName());
						shoppingOrderHBM.setCcType(shoppingOrder.getCcType());
						shoppingOrderHBM.setCcNumber(shoppingOrder.getCcNumber());
						shoppingOrderHBM.setCcExpMonth(shoppingOrder.getCcExpMonth());
						shoppingOrderHBM.setCcExpYear(shoppingOrder.getCcExpYear());
						shoppingOrderHBM.setCcVerNumber(shoppingOrder.getCcVerNumber());
						shoppingOrderHBM.setComments(shoppingOrder.getComments());
						shoppingOrderHBM.setPpTxnId(shoppingOrder.getPpTxnId());
						shoppingOrderHBM.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
						shoppingOrderHBM.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
						shoppingOrderHBM.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
						shoppingOrderHBM.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
						shoppingOrderHBM.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
						shoppingOrderHBM.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
						session.flush();
					}
					else {
						shoppingOrderHBM = new ShoppingOrderHBM();
						shoppingOrderHBM.setOrderId(shoppingOrder.getOrderId());
						shoppingOrderHBM.setGroupId(shoppingOrder.getGroupId());
						shoppingOrderHBM.setCompanyId(shoppingOrder.getCompanyId());
						shoppingOrderHBM.setUserId(shoppingOrder.getUserId());
						shoppingOrderHBM.setUserName(shoppingOrder.getUserName());
						shoppingOrderHBM.setCreateDate(shoppingOrder.getCreateDate());
						shoppingOrderHBM.setModifiedDate(shoppingOrder.getModifiedDate());
						shoppingOrderHBM.setTax(shoppingOrder.getTax());
						shoppingOrderHBM.setShipping(shoppingOrder.getShipping());
						shoppingOrderHBM.setAltShipping(shoppingOrder.getAltShipping());
						shoppingOrderHBM.setRequiresShipping(shoppingOrder.getRequiresShipping());
						shoppingOrderHBM.setInsure(shoppingOrder.getInsure());
						shoppingOrderHBM.setInsurance(shoppingOrder.getInsurance());
						shoppingOrderHBM.setCouponIds(shoppingOrder.getCouponIds());
						shoppingOrderHBM.setCouponDiscount(shoppingOrder.getCouponDiscount());
						shoppingOrderHBM.setBillingFirstName(shoppingOrder.getBillingFirstName());
						shoppingOrderHBM.setBillingLastName(shoppingOrder.getBillingLastName());
						shoppingOrderHBM.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
						shoppingOrderHBM.setBillingCompany(shoppingOrder.getBillingCompany());
						shoppingOrderHBM.setBillingStreet(shoppingOrder.getBillingStreet());
						shoppingOrderHBM.setBillingCity(shoppingOrder.getBillingCity());
						shoppingOrderHBM.setBillingState(shoppingOrder.getBillingState());
						shoppingOrderHBM.setBillingZip(shoppingOrder.getBillingZip());
						shoppingOrderHBM.setBillingCountry(shoppingOrder.getBillingCountry());
						shoppingOrderHBM.setBillingPhone(shoppingOrder.getBillingPhone());
						shoppingOrderHBM.setShipToBilling(shoppingOrder.getShipToBilling());
						shoppingOrderHBM.setShippingFirstName(shoppingOrder.getShippingFirstName());
						shoppingOrderHBM.setShippingLastName(shoppingOrder.getShippingLastName());
						shoppingOrderHBM.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
						shoppingOrderHBM.setShippingCompany(shoppingOrder.getShippingCompany());
						shoppingOrderHBM.setShippingStreet(shoppingOrder.getShippingStreet());
						shoppingOrderHBM.setShippingCity(shoppingOrder.getShippingCity());
						shoppingOrderHBM.setShippingState(shoppingOrder.getShippingState());
						shoppingOrderHBM.setShippingZip(shoppingOrder.getShippingZip());
						shoppingOrderHBM.setShippingCountry(shoppingOrder.getShippingCountry());
						shoppingOrderHBM.setShippingPhone(shoppingOrder.getShippingPhone());
						shoppingOrderHBM.setCcName(shoppingOrder.getCcName());
						shoppingOrderHBM.setCcType(shoppingOrder.getCcType());
						shoppingOrderHBM.setCcNumber(shoppingOrder.getCcNumber());
						shoppingOrderHBM.setCcExpMonth(shoppingOrder.getCcExpMonth());
						shoppingOrderHBM.setCcExpYear(shoppingOrder.getCcExpYear());
						shoppingOrderHBM.setCcVerNumber(shoppingOrder.getCcVerNumber());
						shoppingOrderHBM.setComments(shoppingOrder.getComments());
						shoppingOrderHBM.setPpTxnId(shoppingOrder.getPpTxnId());
						shoppingOrderHBM.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
						shoppingOrderHBM.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
						shoppingOrderHBM.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
						shoppingOrderHBM.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
						shoppingOrderHBM.setSendOrderEmail(shoppingOrder.getSendOrderEmail());
						shoppingOrderHBM.setSendShippingEmail(shoppingOrder.getSendShippingEmail());
						session.save(shoppingOrderHBM);
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

	public com.liferay.portlet.shopping.model.ShoppingOrder findByPrimaryKey(
		String orderId) throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)session.get(ShoppingOrderHBM.class,
					orderId);

			if (shoppingOrderHBM == null) {
				_log.warn("No ShoppingOrder exists with the primary key " +
					orderId.toString());
				throw new NoSuchOrderException(
					"No ShoppingOrder exists with the primary key " +
					orderId.toString());
			}

			return ShoppingOrderHBMUtil.model(shoppingOrderHBM);
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
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)itr.next();
				list.add(ShoppingOrderHBMUtil.model(shoppingOrderHBM));
			}

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
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)itr.next();
				list.add(ShoppingOrderHBMUtil.model(shoppingOrderHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_First(
		String groupId, String userId, String ppPaymentStatus,
		OrderByComparator obc) throws NoSuchOrderException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingOrder)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_Last(
		String groupId, String userId, String ppPaymentStatus,
		OrderByComparator obc) throws NoSuchOrderException, SystemException {
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
			return (com.liferay.portlet.shopping.model.ShoppingOrder)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingOrder[] findByG_U_PPPS_PrevAndNext(
		String orderId, String groupId, String userId, String ppPaymentStatus,
		OrderByComparator obc) throws NoSuchOrderException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus is null");
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
					shoppingOrder, ShoppingOrderHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingOrder[] array = new com.liferay.portlet.shopping.model.ShoppingOrder[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingOrder)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingOrder)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingOrder)objArray[2];

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
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)itr.next();
				list.add(ShoppingOrderHBMUtil.model(shoppingOrderHBM));
			}

			return list;
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
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus is null");
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
				ShoppingOrderHBM shoppingOrderHBM = (ShoppingOrderHBM)itr.next();
				session.delete(shoppingOrderHBM);
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
				"FROM ShoppingOrder IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingOrderHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" AND ");

			if (ppPaymentStatus == null) {
				query.append("ppPaymentStatus is null");
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

	private static Log _log = LogFactory.getLog(ShoppingOrderPersistence.class);
}