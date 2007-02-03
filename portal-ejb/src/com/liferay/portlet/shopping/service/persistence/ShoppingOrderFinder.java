/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="ShoppingOrderFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingOrderFinder {

	public static String COUNT_BY_O_G_C_U_PPPS =
		ShoppingOrderFinder.class.getName() + ".countByO_G_C_U_PPPS";

	public static String FIND_BY_O_G_C_U_PPPS =
		ShoppingOrderFinder.class.getName() + ".findByO_G_C_U_PPPS";

	public static int countByO_G_C_U_PPPS(
			String orderId, long groupId, String companyId, String userId,
			String billingFirstName, String billingLastName,
			String billingEmailAddress, String shippingFirstName,
			String shippingLastName, String shippingEmailAddress,
			String ppPaymentStatus, boolean andOperator)
		throws SystemException {

		orderId = StringUtil.upperCase(orderId);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_O_G_C_U_PPPS);

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderImpl.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);
			qPos.add(orderId);
			qPos.add(orderId);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByO_G_C_U_PPPS(
			String orderId, long groupId, String companyId, String userId,
			String billingFirstName, String billingLastName,
			String billingEmailAddress, String shippingFirstName,
			String shippingLastName, String shippingEmailAddress,
			String ppPaymentStatus, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		orderId = StringUtil.upperCase(orderId);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_O_G_C_U_PPPS);

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderImpl.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("ShoppingOrder", ShoppingOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);
			qPos.add(orderId);
			qPos.add(orderId);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

}