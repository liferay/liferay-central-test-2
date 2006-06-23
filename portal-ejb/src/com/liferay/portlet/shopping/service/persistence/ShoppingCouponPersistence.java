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

import com.liferay.portlet.shopping.NoSuchCouponException;

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
 * <a href="ShoppingCouponPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponPersistence extends BasePersistence {
	public com.liferay.portlet.shopping.model.ShoppingCoupon create(
		String couponId) {
		ShoppingCouponHBM shoppingCouponHBM = new ShoppingCouponHBM();
		shoppingCouponHBM.setNew(true);
		shoppingCouponHBM.setPrimaryKey(couponId);

		return ShoppingCouponHBMUtil.model(shoppingCouponHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon remove(
		String couponId) throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)session.get(ShoppingCouponHBM.class,
					couponId);

			if (shoppingCouponHBM == null) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId.toString());
				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId.toString());
			}

			session.delete(shoppingCouponHBM);
			session.flush();

			return ShoppingCouponHBMUtil.model(shoppingCouponHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon update(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingCoupon.isNew() || shoppingCoupon.isModified()) {
				session = openSession();

				if (shoppingCoupon.isNew()) {
					ShoppingCouponHBM shoppingCouponHBM = new ShoppingCouponHBM();
					shoppingCouponHBM.setCouponId(shoppingCoupon.getCouponId());
					shoppingCouponHBM.setGroupId(shoppingCoupon.getGroupId());
					shoppingCouponHBM.setCompanyId(shoppingCoupon.getCompanyId());
					shoppingCouponHBM.setUserId(shoppingCoupon.getUserId());
					shoppingCouponHBM.setUserName(shoppingCoupon.getUserName());
					shoppingCouponHBM.setCreateDate(shoppingCoupon.getCreateDate());
					shoppingCouponHBM.setModifiedDate(shoppingCoupon.getModifiedDate());
					shoppingCouponHBM.setName(shoppingCoupon.getName());
					shoppingCouponHBM.setDescription(shoppingCoupon.getDescription());
					shoppingCouponHBM.setStartDate(shoppingCoupon.getStartDate());
					shoppingCouponHBM.setEndDate(shoppingCoupon.getEndDate());
					shoppingCouponHBM.setActive(shoppingCoupon.getActive());
					shoppingCouponHBM.setLimitCategories(shoppingCoupon.getLimitCategories());
					shoppingCouponHBM.setLimitSkus(shoppingCoupon.getLimitSkus());
					shoppingCouponHBM.setMinOrder(shoppingCoupon.getMinOrder());
					shoppingCouponHBM.setDiscount(shoppingCoupon.getDiscount());
					shoppingCouponHBM.setDiscountType(shoppingCoupon.getDiscountType());
					session.save(shoppingCouponHBM);
					session.flush();
				}
				else {
					ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)session.get(ShoppingCouponHBM.class,
							shoppingCoupon.getPrimaryKey());

					if (shoppingCouponHBM != null) {
						shoppingCouponHBM.setGroupId(shoppingCoupon.getGroupId());
						shoppingCouponHBM.setCompanyId(shoppingCoupon.getCompanyId());
						shoppingCouponHBM.setUserId(shoppingCoupon.getUserId());
						shoppingCouponHBM.setUserName(shoppingCoupon.getUserName());
						shoppingCouponHBM.setCreateDate(shoppingCoupon.getCreateDate());
						shoppingCouponHBM.setModifiedDate(shoppingCoupon.getModifiedDate());
						shoppingCouponHBM.setName(shoppingCoupon.getName());
						shoppingCouponHBM.setDescription(shoppingCoupon.getDescription());
						shoppingCouponHBM.setStartDate(shoppingCoupon.getStartDate());
						shoppingCouponHBM.setEndDate(shoppingCoupon.getEndDate());
						shoppingCouponHBM.setActive(shoppingCoupon.getActive());
						shoppingCouponHBM.setLimitCategories(shoppingCoupon.getLimitCategories());
						shoppingCouponHBM.setLimitSkus(shoppingCoupon.getLimitSkus());
						shoppingCouponHBM.setMinOrder(shoppingCoupon.getMinOrder());
						shoppingCouponHBM.setDiscount(shoppingCoupon.getDiscount());
						shoppingCouponHBM.setDiscountType(shoppingCoupon.getDiscountType());
						session.flush();
					}
					else {
						shoppingCouponHBM = new ShoppingCouponHBM();
						shoppingCouponHBM.setCouponId(shoppingCoupon.getCouponId());
						shoppingCouponHBM.setGroupId(shoppingCoupon.getGroupId());
						shoppingCouponHBM.setCompanyId(shoppingCoupon.getCompanyId());
						shoppingCouponHBM.setUserId(shoppingCoupon.getUserId());
						shoppingCouponHBM.setUserName(shoppingCoupon.getUserName());
						shoppingCouponHBM.setCreateDate(shoppingCoupon.getCreateDate());
						shoppingCouponHBM.setModifiedDate(shoppingCoupon.getModifiedDate());
						shoppingCouponHBM.setName(shoppingCoupon.getName());
						shoppingCouponHBM.setDescription(shoppingCoupon.getDescription());
						shoppingCouponHBM.setStartDate(shoppingCoupon.getStartDate());
						shoppingCouponHBM.setEndDate(shoppingCoupon.getEndDate());
						shoppingCouponHBM.setActive(shoppingCoupon.getActive());
						shoppingCouponHBM.setLimitCategories(shoppingCoupon.getLimitCategories());
						shoppingCouponHBM.setLimitSkus(shoppingCoupon.getLimitSkus());
						shoppingCouponHBM.setMinOrder(shoppingCoupon.getMinOrder());
						shoppingCouponHBM.setDiscount(shoppingCoupon.getDiscount());
						shoppingCouponHBM.setDiscountType(shoppingCoupon.getDiscountType());
						session.save(shoppingCouponHBM);
						session.flush();
					}
				}

				shoppingCoupon.setNew(false);
				shoppingCoupon.setModified(false);
			}

			return shoppingCoupon;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByPrimaryKey(
		String couponId) throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)session.get(ShoppingCouponHBM.class,
					couponId);

			if (shoppingCouponHBM == null) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId.toString());
				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId.toString());
			}

			return ShoppingCouponHBMUtil.model(shoppingCouponHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)itr.next();
				list.add(ShoppingCouponHBMUtil.model(shoppingCouponHBM));
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

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)itr.next();
				list.add(ShoppingCouponHBMUtil.model(shoppingCouponHBM));
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

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCoupon exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCouponException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCoupon)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCoupon exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCouponException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingCoupon)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon[] findByGroupId_PrevAndNext(
		String couponId, String groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon = findByPrimaryKey(couponId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCoupon, ShoppingCouponHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingCoupon[] array = new com.liferay.portlet.shopping.model.ShoppingCoupon[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingCoupon)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingCoupon)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingCoupon)objArray[2];

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
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)itr.next();
				list.add(ShoppingCouponHBMUtil.model(shoppingCouponHBM));
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingCouponHBM shoppingCouponHBM = (ShoppingCouponHBM)itr.next();
				session.delete(shoppingCouponHBM);
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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ShoppingCoupon IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingCouponHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	private static Log _log = LogFactory.getLog(ShoppingCouponPersistence.class);
}