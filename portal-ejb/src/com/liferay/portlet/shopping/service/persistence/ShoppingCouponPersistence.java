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
import com.liferay.portlet.shopping.model.ShoppingCoupon;

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
	public ShoppingCoupon create(String couponId) {
		ShoppingCoupon shoppingCoupon = new ShoppingCoupon();
		shoppingCoupon.setNew(true);
		shoppingCoupon.setPrimaryKey(couponId);

		return shoppingCoupon;
	}

	public ShoppingCoupon remove(String couponId)
		throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCoupon shoppingCoupon = (ShoppingCoupon)session.get(ShoppingCoupon.class,
					couponId);

			if (shoppingCoupon == null) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId.toString());
				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId.toString());
			}

			session.delete(shoppingCoupon);
			session.flush();

			return shoppingCoupon;
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
					ShoppingCoupon shoppingCouponModel = new ShoppingCoupon();
					shoppingCouponModel.setCouponId(shoppingCoupon.getCouponId());
					shoppingCouponModel.setGroupId(shoppingCoupon.getGroupId());
					shoppingCouponModel.setCompanyId(shoppingCoupon.getCompanyId());
					shoppingCouponModel.setUserId(shoppingCoupon.getUserId());
					shoppingCouponModel.setUserName(shoppingCoupon.getUserName());
					shoppingCouponModel.setCreateDate(shoppingCoupon.getCreateDate());
					shoppingCouponModel.setModifiedDate(shoppingCoupon.getModifiedDate());
					shoppingCouponModel.setName(shoppingCoupon.getName());
					shoppingCouponModel.setDescription(shoppingCoupon.getDescription());
					shoppingCouponModel.setStartDate(shoppingCoupon.getStartDate());
					shoppingCouponModel.setEndDate(shoppingCoupon.getEndDate());
					shoppingCouponModel.setActive(shoppingCoupon.getActive());
					shoppingCouponModel.setLimitCategories(shoppingCoupon.getLimitCategories());
					shoppingCouponModel.setLimitSkus(shoppingCoupon.getLimitSkus());
					shoppingCouponModel.setMinOrder(shoppingCoupon.getMinOrder());
					shoppingCouponModel.setDiscount(shoppingCoupon.getDiscount());
					shoppingCouponModel.setDiscountType(shoppingCoupon.getDiscountType());
					session.save(shoppingCouponModel);
					session.flush();
				}
				else {
					ShoppingCoupon shoppingCouponModel = (ShoppingCoupon)session.get(ShoppingCoupon.class,
							shoppingCoupon.getPrimaryKey());

					if (shoppingCouponModel != null) {
						shoppingCouponModel.setGroupId(shoppingCoupon.getGroupId());
						shoppingCouponModel.setCompanyId(shoppingCoupon.getCompanyId());
						shoppingCouponModel.setUserId(shoppingCoupon.getUserId());
						shoppingCouponModel.setUserName(shoppingCoupon.getUserName());
						shoppingCouponModel.setCreateDate(shoppingCoupon.getCreateDate());
						shoppingCouponModel.setModifiedDate(shoppingCoupon.getModifiedDate());
						shoppingCouponModel.setName(shoppingCoupon.getName());
						shoppingCouponModel.setDescription(shoppingCoupon.getDescription());
						shoppingCouponModel.setStartDate(shoppingCoupon.getStartDate());
						shoppingCouponModel.setEndDate(shoppingCoupon.getEndDate());
						shoppingCouponModel.setActive(shoppingCoupon.getActive());
						shoppingCouponModel.setLimitCategories(shoppingCoupon.getLimitCategories());
						shoppingCouponModel.setLimitSkus(shoppingCoupon.getLimitSkus());
						shoppingCouponModel.setMinOrder(shoppingCoupon.getMinOrder());
						shoppingCouponModel.setDiscount(shoppingCoupon.getDiscount());
						shoppingCouponModel.setDiscountType(shoppingCoupon.getDiscountType());
						session.flush();
					}
					else {
						shoppingCouponModel = new ShoppingCoupon();
						shoppingCouponModel.setCouponId(shoppingCoupon.getCouponId());
						shoppingCouponModel.setGroupId(shoppingCoupon.getGroupId());
						shoppingCouponModel.setCompanyId(shoppingCoupon.getCompanyId());
						shoppingCouponModel.setUserId(shoppingCoupon.getUserId());
						shoppingCouponModel.setUserName(shoppingCoupon.getUserName());
						shoppingCouponModel.setCreateDate(shoppingCoupon.getCreateDate());
						shoppingCouponModel.setModifiedDate(shoppingCoupon.getModifiedDate());
						shoppingCouponModel.setName(shoppingCoupon.getName());
						shoppingCouponModel.setDescription(shoppingCoupon.getDescription());
						shoppingCouponModel.setStartDate(shoppingCoupon.getStartDate());
						shoppingCouponModel.setEndDate(shoppingCoupon.getEndDate());
						shoppingCouponModel.setActive(shoppingCoupon.getActive());
						shoppingCouponModel.setLimitCategories(shoppingCoupon.getLimitCategories());
						shoppingCouponModel.setLimitSkus(shoppingCoupon.getLimitSkus());
						shoppingCouponModel.setMinOrder(shoppingCoupon.getMinOrder());
						shoppingCouponModel.setDiscount(shoppingCoupon.getDiscount());
						shoppingCouponModel.setDiscountType(shoppingCoupon.getDiscountType());
						session.save(shoppingCouponModel);
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

	public ShoppingCoupon findByPrimaryKey(String couponId)
		throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCoupon shoppingCoupon = (ShoppingCoupon)session.get(ShoppingCoupon.class,
					couponId);

			if (shoppingCoupon == null) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId.toString());
				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId.toString());
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

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon findByGroupId_First(String groupId,
		OrderByComparator obc) throws NoSuchCouponException, SystemException {
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
			return (ShoppingCoupon)list.get(0);
		}
	}

	public ShoppingCoupon findByGroupId_Last(String groupId,
		OrderByComparator obc) throws NoSuchCouponException, SystemException {
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
			return (ShoppingCoupon)list.get(0);
		}
	}

	public ShoppingCoupon[] findByGroupId_PrevAndNext(String couponId,
		String groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = findByPrimaryKey(couponId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
					shoppingCoupon);
			ShoppingCoupon[] array = new ShoppingCoupon[3];
			array[0] = (ShoppingCoupon)objArray[0];
			array[1] = (ShoppingCoupon)objArray[1];
			array[2] = (ShoppingCoupon)objArray[2];

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
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				ShoppingCoupon shoppingCoupon = (ShoppingCoupon)itr.next();
				session.delete(shoppingCoupon);
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
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ShoppingCouponPersistence.class);
}